package edu.byu.cs.tweeter.view.asyncTasks;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
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
import edu.byu.cs.tweeter.presenter.Presenter;
import edu.byu.cs.tweeter.view.cache.ImageCache;
import edu.byu.cs.tweeter.view.main.MainActivity;
import edu.byu.cs.tweeter.view.main.UserViewActivity;
import edu.byu.cs.tweeter.view.util.ImageUtils;

public class GetUserTask extends AsyncTask<UserRequest, Void, UserResponse> {

//    private final GetUserTask.GetFeedObserver observer;

    public interface GetFeedObserver {
        void tweetsRetrieved(FeedResponse feedResponse);
    }

    private final FeedPresenter presenter;
    private final FragmentActivity activity;
    private final User user;
    private final String userHandle;

    public GetUserTask(FeedPresenter presenter, FragmentActivity activity, User user, String userHandle) {
        this.presenter = presenter;
        this.activity = activity;
        this.user = user;
        this.userHandle = userHandle;
    }


    @Override
    protected UserResponse doInBackground(UserRequest... userRequests) {
        UserResponse response = presenter.getUser(userRequests[0]);
//        loadImages(response);
        return response;
    }

    @Override
    protected void onPostExecute(UserResponse userResponse) {

        if (userResponse != null) {
            presenter.setShownUser(userResponse.getUser());
            User u = presenter.getUserShown();
            Intent intent = new Intent(activity, UserViewActivity.class);
            activity.startActivity(intent);
        }

//        if(observer != null) {
//            observer.followersRetrieved(followerResponse);
//        }
    }
}
