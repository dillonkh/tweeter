package edu.byu.cs.tweeter.view.main;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;
import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.Tweet;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.net.request.FollowRequest;
import edu.byu.cs.tweeter.net.request.TweetRequest;
import edu.byu.cs.tweeter.net.request.UnFollowRequest;
import edu.byu.cs.tweeter.net.response.TweetResponse;
import edu.byu.cs.tweeter.presenter.MainPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.GetFollowTask;
import edu.byu.cs.tweeter.view.asyncTasks.GetSendTweetTask;
import edu.byu.cs.tweeter.view.asyncTasks.GetUnFollowTask;
import edu.byu.cs.tweeter.view.asyncTasks.LoadImageTask;
import edu.byu.cs.tweeter.view.cache.ImageCache;
import edu.byu.cs.tweeter.view.main.feed.FeedFragment;
import edu.byu.cs.tweeter.view.main.story.StoryFragment;


public class UserViewActivity extends AppCompatActivity implements LoadImageTask.LoadImageObserver, MainPresenter.View, GetFollowTask.GetFollowObserver, GetUnFollowTask.GetUnFollowObserver, GetSendTweetTask.GetTweetObserver {

    private MainPresenter presenter;
    private User user;
    private ImageView userImageView;
    private boolean following = true; // TODO: this should come from the user itself
    private StoryFragment storyFragment;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userview);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        Toolbar toolbar = findViewById(R.id.userviewToolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);

        presenter = new MainPresenter(this);
        user = presenter.getUserShown();


        ImageView optionDots = findViewById(R.id.optionDots);
        FloatingActionButton fab = findViewById(R.id.fab);
        final CardView optionsCard = findViewById(R.id.settingsCard);
        final CardView tweetCard = findViewById(R.id.makeTweetCard);
        TextView optionsCardCancel = findViewById(R.id.optionsCardCancel);
        TextView tweetCardCancel = findViewById(R.id.tweetCardCancel);
        Button signOutButton = findViewById(R.id.signOutButton);
        Button sendTweetButton = findViewById(R.id.sendTweetButton);
        final Button followButton = findViewById(R.id.followButton);
        setBackgroundColorOfFollowButton(followButton);
//
        storyFragment = sectionsPagerAdapter.getStoryFragment();
        final FeedFragment feedFragment = sectionsPagerAdapter.getFeedFragment();

//
//
        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFollowing()) { // unfollow them

                    GetUnFollowTask getUnFollowTask = new GetUnFollowTask(presenter, UserViewActivity.this);
                    UnFollowRequest request = new UnFollowRequest(presenter.getUserShown(), presenter.getCurrentUser());
                    getUnFollowTask.execute(request);

                    followButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    followButton.setText("Follow");
                }
                else { // follow them

                    GetFollowTask getFollowTask = new GetFollowTask(presenter, UserViewActivity.this);
                    FollowRequest request = new FollowRequest(presenter.getUserShown(), presenter.getCurrentUser());
                    getFollowTask.execute(request);

                    followButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    followButton.setText("Following");
                }

//                Toast.makeText(view.getContext(),"TODO: implement follow and unfollow", Toast.LENGTH_SHORT).show();
            }
        });

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToSignInView(view);
            }
        });

        optionsCardCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionsCard.setVisibility(View.INVISIBLE);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText text = (EditText)findViewById(R.id.tweetMessage);
                text.setText("");
                tweetCard.setVisibility(View.VISIBLE);
            }
        });

        tweetCardCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tweetCard.setVisibility(View.INVISIBLE);
            }
        });

        optionDots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionsCard.setVisibility(View.VISIBLE);
            }
        });

        userImageView = findViewById(R.id.userImage);


//        presenter.setShownUser(user);

        sendTweetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(view.getContext(),"TODO: implement send tweet", Toast.LENGTH_SHORT).show();
                EditText text = (EditText)findViewById(R.id.tweetMessage);
                String message = text.getText().toString();
                Tweet tweet = new Tweet(presenter.getCurrentUser(), message, "make URL");

                TweetRequest request = new TweetRequest(tweet);
                GetSendTweetTask getSendTweetTask = new GetSendTweetTask(presenter, UserViewActivity.this);
                getSendTweetTask.execute(request);
                tweetCard.setVisibility(View.INVISIBLE);
            }
        });


        // Asynchronously load the user's image

        LoadImageTask loadImageTask = new LoadImageTask(this);

        loadImageTask.execute(presenter.getUserShown().getImageUrl());

        TextView userName = findViewById(R.id.userName);
        userName.setText(presenter.getUserShown().getName());

        TextView userAlias = findViewById(R.id.userAlias);
        userAlias.setText(presenter.getUserShown().getAlias());
    }

    private void setBackgroundColorOfFollowButton(Button followButton) {
        if (isFollowing()) {
            followButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            followButton.setText("Following");
        }
        else {
            followButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            followButton.setText("Follow");
        }
    }

    private void switchToSignInView (View view) {
//        Toast.makeText(view.getContext(),"TODO: switch to sign in page", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private boolean isFollowing () {
        return presenter.isFollowing(presenter.getCurrentUser(), presenter.getUserShown());
    }



    @Override
    public void imageLoadProgressUpdated(Integer progress) {
        // We're just loading one image. No need to indicate progress.
    }

    @Override
    public void imagesLoaded(Drawable[] drawables) {
        ImageCache.getInstance().cacheImage(user, drawables[0]);

        if(drawables[0] != null) {
            userImageView.setImageDrawable(drawables[0]);
        }
    }

    @Override
    public void tweetResponded(TweetResponse tweetResponse) {
        if (tweetResponse.isSent()) {
            storyFragment.listChanged();
        }
    }
}