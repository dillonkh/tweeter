package edu.byu.cs.tweeter.view.asyncTasks;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.net.request.FollowerRequest;
import edu.byu.cs.tweeter.net.response.FollowerResponse;
import edu.byu.cs.tweeter.presenter.FollowerPresenter;
import edu.byu.cs.tweeter.view.cache.ImageCache;
import edu.byu.cs.tweeter.view.util.ImageUtils;

public class GetFollowerTask extends AsyncTask<FollowerRequest, Void, FollowerResponse> {

    private final FollowerPresenter presenter;
    private final GetFollowersObserver observer;

    public interface GetFollowersObserver {
        void followersRetrieved(FollowerResponse followerResponse);
    }

    public GetFollowerTask(FollowerPresenter presenter, GetFollowersObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected FollowerResponse doInBackground(FollowerRequest... followerRequests) {
        FollowerResponse response = presenter.getFollower(followerRequests[0]);
        loadImages(response);
        return response;
    }

    private void loadImages(FollowerResponse response) {
        for(User user : response.getFollowees()) {

            Drawable drawable;

            try {
                drawable = ImageUtils.drawableFromUrl(user.getImageUrl());
            } catch (IOException e) {
                Log.e(this.getClass().getName(), e.toString(), e);
                drawable = null;
            }

            ImageCache.getInstance().cacheImage(user, drawable);
        }
    }

    @Override
    protected void onPostExecute(FollowerResponse followerResponse) {

        if(observer != null) {
            observer.followersRetrieved(followerResponse);
        }
    }
}
