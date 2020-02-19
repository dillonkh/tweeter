package edu.byu.cs.tweeter.view.asyncTasks;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import java.io.IOException;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.net.request.FollowerRequest;
import edu.byu.cs.tweeter.net.request.UserRequest;
import edu.byu.cs.tweeter.net.response.FeedResponse;
import edu.byu.cs.tweeter.net.response.FollowerResponse;
import edu.byu.cs.tweeter.net.response.UserResponse;
import edu.byu.cs.tweeter.presenter.FeedPresenter;
import edu.byu.cs.tweeter.presenter.FollowerPresenter;
import edu.byu.cs.tweeter.presenter.FollowingPresenter;
import edu.byu.cs.tweeter.presenter.MainPresenter;
import edu.byu.cs.tweeter.presenter.Presenter;
import edu.byu.cs.tweeter.presenter.StoryPresenter;
import edu.byu.cs.tweeter.view.cache.ImageCache;
import edu.byu.cs.tweeter.view.main.MainActivity;
import edu.byu.cs.tweeter.view.main.UserViewActivity;
import edu.byu.cs.tweeter.view.util.ImageUtils;


public class GetUserTask extends AsyncTask<UserRequest, Void, UserResponse> {

//    private final GetUserTask.GetFeedObserver observer;

    public interface GetFeedObserver {
        void tweetsRetrieved(FeedResponse feedResponse);
    }

    private final StoryPresenter storyPresenter;
    private final FeedPresenter feedPresenter;
    private final FollowerPresenter followerPresenter;
    private final FollowingPresenter followingPresenter;
    private final MainPresenter mainPresenter;


    private final FragmentActivity activity;
    private final User user;
    private final String userHandle;

    public GetUserTask(FeedPresenter presenter, FragmentActivity activity, User user, String userHandle) {
        this.feedPresenter = presenter;
        this.activity = activity;
        this.user = user;
        this.userHandle = userHandle;

        storyPresenter = null;
        followerPresenter = null;
        followingPresenter = null;
        mainPresenter = null;
    }

    public GetUserTask(StoryPresenter presenter, FragmentActivity activity, User user, String userHandle) {
        this.storyPresenter = presenter;
        this.activity = activity;
        this.user = user;
        this.userHandle = userHandle;

        feedPresenter = null;
        followerPresenter = null;
        followingPresenter = null;
        mainPresenter = null;
    }

    public GetUserTask(FollowerPresenter presenter, FragmentActivity activity, User user, String userHandle) {
        this.followerPresenter = presenter;
        this.activity = activity;
        this.user = user;
        this.userHandle = userHandle;

        feedPresenter = null;
        storyPresenter = null;
        followingPresenter = null;
        mainPresenter = null;
    }

    public GetUserTask(FollowingPresenter presenter, FragmentActivity activity, User user, String userHandle) {
        this.followingPresenter = presenter;
        this.activity = activity;
        this.user = user;
        this.userHandle = userHandle;

        feedPresenter = null;
        storyPresenter = null;
        followerPresenter = null;
        mainPresenter = null;
    }

    public GetUserTask(MainPresenter presenter, FragmentActivity activity, User user, String userHandle) {
        this.mainPresenter = presenter;
        this.activity = activity;
        this.user = user;
        this.userHandle = userHandle;

        feedPresenter = null;
        storyPresenter = null;
        followerPresenter = null;
        followingPresenter = null;
    }

    private Presenter getActivePresenter() {
        if (storyPresenter != null) {
            return storyPresenter;
        }
        else if (feedPresenter != null) {
            return feedPresenter;
        }
        else if (followerPresenter != null) {
            return followerPresenter;
        }
        else if (followingPresenter != null) {
            return followingPresenter;
        }
        else if (mainPresenter != null) {
            return mainPresenter;
        }
        else {
            return null;
        }
    }


    @Override
    protected UserResponse doInBackground(UserRequest... userRequests) {
        UserResponse response;
        response = getActivePresenter().getUser(userRequests[0]);

        return response;
    }

    @Override
    protected void onPostExecute(UserResponse userResponse) {

        if (userResponse != null) {
            getActivePresenter().setShownUser(userResponse.getUser());
            User u = getActivePresenter().getUserShown();
            Intent intent = new Intent(activity, UserViewActivity.class);
            activity.startActivity(intent);
        }

//        if(observer != null) {
//            observer.followersRetrieved(followerResponse);
//        }
    }
}
