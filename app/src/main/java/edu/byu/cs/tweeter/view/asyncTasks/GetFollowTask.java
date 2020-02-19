package edu.byu.cs.tweeter.view.asyncTasks;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.net.request.FollowRequest;
import edu.byu.cs.tweeter.net.request.FollowerRequest;
import edu.byu.cs.tweeter.net.response.FollowResponse;
import edu.byu.cs.tweeter.net.response.FollowerResponse;
import edu.byu.cs.tweeter.presenter.FollowerPresenter;
import edu.byu.cs.tweeter.presenter.MainPresenter;
import edu.byu.cs.tweeter.view.cache.ImageCache;
import edu.byu.cs.tweeter.view.util.ImageUtils;

public class GetFollowTask extends AsyncTask<FollowRequest, Void, FollowResponse> {

    private final MainPresenter presenter;
    private final GetFollowObserver observer;

    public interface GetFollowObserver {
//        void followersRetrieved(FollowerResponse followerResponse);
    }

    public GetFollowTask(MainPresenter presenter, GetFollowObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected FollowResponse doInBackground(FollowRequest... followRequests) {
        FollowResponse response = presenter.follow(followRequests[0]);

        return response;
    }

    @Override
    protected void onPostExecute(FollowResponse followResponse) {

//        if(observer != null) {
//            observer.followersRetrieved(followerResponse);
//        }
    }
}
