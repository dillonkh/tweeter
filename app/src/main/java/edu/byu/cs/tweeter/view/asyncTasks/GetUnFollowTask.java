package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs.tweeter.net.request.FollowRequest;
import edu.byu.cs.tweeter.net.request.UnFollowRequest;
import edu.byu.cs.tweeter.net.response.FollowResponse;
import edu.byu.cs.tweeter.net.response.UnFollowResponse;
import edu.byu.cs.tweeter.presenter.MainPresenter;

public class GetUnFollowTask extends AsyncTask<UnFollowRequest, Void, UnFollowResponse> {

    private final MainPresenter presenter;
    private final GetUnFollowObserver observer;

    public interface GetUnFollowObserver {
//        void followersRetrieved(FollowerResponse followerResponse);
    }

    public GetUnFollowTask(MainPresenter presenter, GetUnFollowObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected UnFollowResponse doInBackground(UnFollowRequest... unfollowRequests) {
        UnFollowResponse response = presenter.unfollow(unfollowRequests[0]);

        return response;
    }

    @Override
    protected void onPostExecute(UnFollowResponse unfollowResponse) {

//        if(observer != null) {
//            observer.followersRetrieved(followerResponse);
//        }
    }
}
