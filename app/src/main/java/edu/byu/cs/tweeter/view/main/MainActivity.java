package edu.byu.cs.tweeter.view.main;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.presenter.MainPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.LoadImageTask;
import edu.byu.cs.tweeter.view.cache.ImageCache;

public class MainActivity extends AppCompatActivity implements LoadImageTask.LoadImageObserver, MainPresenter.View {

    private MainPresenter presenter;
    private User user;
    private ImageView userImageView;
    private boolean following = true; // TODO: this should come from the user itself
//    private View theView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

//        theView =

        ImageView optionDots = findViewById(R.id.optionDots);
        FloatingActionButton fab = findViewById(R.id.fab);
        final CardView optionsCard = findViewById(R.id.settingsCard);
        final CardView tweetCard = findViewById(R.id.makeTweetCard);
        TextView optionsCardCancel = findViewById(R.id.optionsCardCancel);
        TextView tweetCardCancel = findViewById(R.id.tweetCardCancel);
        Button signOutButton = findViewById(R.id.signOutButton);
        Button sendTweetButton = findViewById(R.id.sendTweetButton);
        final Button followButton = findViewById(R.id.followButton);


        sendTweetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"TODO: implement send tweet", Toast.LENGTH_SHORT).show();
            }
        });

        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFollowing()) {
                    followButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    followButton.setText("Follow");
                }
                else {
                    followButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    followButton.setText("Following");
                }

                Toast.makeText(view.getContext(),"TODO: implement follow and unfollow", Toast.LENGTH_SHORT).show();
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

        user = presenter.getCurrentUser();

        // Asynchronously load the user's image
        LoadImageTask loadImageTask = new LoadImageTask(this);
        loadImageTask.execute(presenter.getCurrentUser().getImageUrl());

        TextView userName = findViewById(R.id.userName);
        userName.setText(user.getName());

        TextView userAlias = findViewById(R.id.userAlias);
        userAlias.setText(user.getAlias());
    }

    private void switchToSignInView (View view) {
//        Toast.makeText(view.getContext(),"TODO: switch to sign in page", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private boolean isFollowing () {
        following = !following;

        return following;
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
}