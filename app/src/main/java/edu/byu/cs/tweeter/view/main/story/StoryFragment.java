package edu.byu.cs.tweeter.view.main.story;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.Tweet;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.net.request.StoryRequest;
import edu.byu.cs.tweeter.net.request.UserRequest;
import edu.byu.cs.tweeter.net.response.StoryResponse;
import edu.byu.cs.tweeter.presenter.StoryPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.GetStoryTask;
import edu.byu.cs.tweeter.view.asyncTasks.GetUserTask;
import edu.byu.cs.tweeter.view.cache.ImageCache;

public class StoryFragment extends Fragment implements StoryPresenter.View {

    private static RecyclerView storyRecyclerView;

    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;

    private static final int PAGE_SIZE = 10;

    private StoryPresenter presenter;

    private StoryRecyclerViewAdapter storyRecyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_story, container, false);

        presenter = new StoryPresenter(this);

        storyRecyclerView = view.findViewById(R.id.storyRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        storyRecyclerView.setLayoutManager(layoutManager);

        storyRecyclerViewAdapter = new StoryRecyclerViewAdapter();
        storyRecyclerView.setAdapter(storyRecyclerViewAdapter);

        storyRecyclerView.addOnScrollListener(new FollowRecyclerViewPaginationScrollListener(layoutManager));

        return view;
    }

    @Override
    public void listChanged() {
//        storyRecyclerViewAdapter.notifyThereAreMoreItems();
//        storyRecyclerViewAdapter.notifyDataSetChanged();
        storyRecyclerViewAdapter.loadMoreItems();
    }

    public void scrollToTop(){
        storyRecyclerView.smoothScrollToPosition(0);
    }


    private class StoryHolder extends RecyclerView.ViewHolder {

        private final ImageView userImage;
        private final TextView userAlias;
        private final TextView userFirstName;
        private final TextView userLastName;
        private final TextView userTweet;
        private final TextView timeStamp;

        StoryHolder(@NonNull View itemView) {
            super(itemView);

            userImage = itemView.findViewById(R.id.tweetUserImage);
            userAlias = itemView.findViewById(R.id.tweetUserHandle);
            userFirstName = itemView.findViewById(R.id.tweetUserFirstName);
            userLastName = itemView.findViewById(R.id.tweetUserLastName);
            userTweet = itemView.findViewById(R.id.tweetUserTweet);
            timeStamp = itemView.findViewById(R.id.timeStamp);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    GetUserTask getUserTask = new GetUserTask(presenter, getActivity(), presenter.getUserShown(), userAlias.toString());
//                    UserRequest request = new UserRequest(presenter.getUserShown(), userAlias.getText().toString());
//                    getUserTask.execute(request);
//                }
//            });

        }

        void bindTweet(Tweet tweet) {
            userImage.setImageDrawable(ImageCache.getInstance().getImageDrawable(tweet.getUser()));
            userAlias.setText(tweet.getUser().getAlias());
            userFirstName.setText(tweet.getUser().getFirstName());
            userLastName.setText(tweet.getUser().getLastName());

            SpannableString ss = parseMessage(tweet.getMessage());
            if (ss != null) {
                userTweet.setText(ss);
            }
            else {
                userTweet.setText(tweet.getMessage());
            }
            userTweet.setMovementMethod(LinkMovementMethod.getInstance());
            userTweet.setHighlightColor(Color.TRANSPARENT);
            timeStamp.setText(tweet.getTimeStamp());

            userAlias.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GetUserTask getUserTask = new GetUserTask(presenter, getActivity(), presenter.getUserShown(), userAlias.toString());
                    UserRequest request = new UserRequest(presenter.getUserShown(), userAlias.getText().toString());
                    getUserTask.execute(request);
                }
            });
        }
    }

    private SpannableString parseMessage(String message) {
        String parsed = new String();
        SpannableString ss = new SpannableString(message);

        int start = -1;
        int end = -1;
        for (int i = 0; i < message.length(); i++) {
            if (start < 0) {
                if (message.charAt(i) == '@') {
                    start = i;
                }
            }
            else {
                if (message.charAt(i) == ' ' || i == message.length()) {
                    end = i;
                    break;
                }
            }
        }
        if (start == -1) {
            return null;
        }
        else if (end == -1) {
            end = message.length() - 1;
        }
        while (true) {
            if ( (message.charAt(end) >= 48 && message.charAt(end) <= 57)
                    || (message.charAt(end) >= 65 && message.charAt(end) <= 90)
                    || (message.charAt(end) >= 97 && message.charAt(end) <= 122) ) {

                break;
            }
            else {
                end = end - 1;
            }
        }

        ClickableSpan clickableSpan = new MyClickableSpan(message.substring(start, end + 1));

        ss.setSpan(clickableSpan, start, end+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return ss;
    }

    class MyClickableSpan extends ClickableSpan {

        String message;

        public MyClickableSpan(String message) {
            this.message = message;
        }

        @Override
        public void onClick(View textView) {
//                startActivity(new Intent(MyActivity.this, NextActivity.class));
                Toast.makeText(textView.getContext(),message,Toast.LENGTH_SHORT).show();
            GetUserTask getUserTask = new GetUserTask(presenter, getActivity(), presenter.getUserShown(), message);
            UserRequest request = new UserRequest(presenter.getUserShown(), message);
            getUserTask.execute(request);
        }
        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
        }
    }

    private class StoryRecyclerViewAdapter extends RecyclerView.Adapter<StoryHolder> implements GetStoryTask.GetStoryObserver {

        private final List<Tweet> tweets = new ArrayList<>();

        private edu.byu.cs.tweeter.model.domain.Tweet lastTweet;

        private boolean hasMorePages;
        private boolean isLoading = false;

        StoryRecyclerViewAdapter() {
            loadMoreItems();
        }

        void addItems(List<Tweet> newTweets) {
//            int startInsertPosition = tweets.size();
            tweets.addAll(0,newTweets);
            this.notifyItemRangeInserted(0, newTweets.size());
        }

        void addItem(Tweet tweet) {
            tweets.add(0, tweet);
            this.notifyItemInserted(0);
        }

        void removeItem(Tweet tweet) {
            int position = tweets.indexOf(tweet);
            tweets.remove(position);
            this.notifyItemRemoved(position);
        }


        @NonNull
        @Override
        public StoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(StoryFragment.this.getContext());
            View view;

            if(isLoading) {
                view = layoutInflater.inflate(R.layout.loading_row, parent, false);

            } else {
                view = layoutInflater.inflate(R.layout.tweet, parent, false);
            }

            return new StoryHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull StoryHolder storyHolder, int position) {
            if(!isLoading) {
                storyHolder.bindTweet(tweets.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return tweets.size();
        }

        @Override
        public int getItemViewType(int position) {
            return (position == tweets.size() - 1 && isLoading) ? LOADING_DATA_VIEW : ITEM_VIEW;
        }


        void loadMoreItems() {

            isLoading = true;
            addLoadingFooter();

            GetStoryTask getStoryTask = new GetStoryTask(presenter, this);
            StoryRequest request = new StoryRequest(presenter.getUserShown(), PAGE_SIZE, lastTweet);
            getStoryTask.execute(request);
        }

        void notifyThereAreMoreItems() {
            GetStoryTask getStoryTask = new GetStoryTask(presenter, this);
            StoryRequest request = new StoryRequest(presenter.getUserShown(), PAGE_SIZE, lastTweet);
            getStoryTask.execute(request);
        }

        @Override
        public void tweetsRetrieved(StoryResponse storyResponse) {
            List<Tweet> tweets = storyResponse.getTweets();

            lastTweet = (tweets.size() > 0) ? tweets.get(tweets.size() -1) : null;
            hasMorePages = storyResponse.hasMorePages();

            isLoading = false;
            removeLoadingFooter();
            storyRecyclerViewAdapter.addItems(tweets);

//            notifyThereAreMoreItems();
        }

        private void addLoadingFooter() {
            addItem(
                    new Tweet(
                            new User("fakeFirst", "fakeLast", null),
                            "This is placeholder text for tweet",
                            null
                    )
            );
        }

        private void removeLoadingFooter() {
            if (tweets.size() > 0) {
                removeItem(tweets.get(tweets.size() - 1));
            }
        }
    }

    private class FollowRecyclerViewPaginationScrollListener extends RecyclerView.OnScrollListener {

        private final LinearLayoutManager layoutManager;

        FollowRecyclerViewPaginationScrollListener(LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        @Override
        public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!storyRecyclerViewAdapter.isLoading && storyRecyclerViewAdapter.hasMorePages) {
                if ((visibleItemCount + firstVisibleItemPosition) >=
                        totalItemCount && firstVisibleItemPosition >= 0) {
                    storyRecyclerViewAdapter.loadMoreItems();
                }
            }
        }
    }
}
