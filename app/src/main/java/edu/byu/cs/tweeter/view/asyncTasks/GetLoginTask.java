package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs.tweeter.net.request.FeedRequest;
import edu.byu.cs.tweeter.net.request.LoginRequest;
import edu.byu.cs.tweeter.net.response.FeedResponse;
import edu.byu.cs.tweeter.net.response.LoginResponse;
import edu.byu.cs.tweeter.presenter.FeedPresenter;
import edu.byu.cs.tweeter.presenter.LoginPresenter;


public class GetLoginTask extends AsyncTask<LoginRequest, Void, LoginResponse> {

    private final LoginPresenter presenter;
    private final GetLoginObserver observer;

    public interface GetLoginObserver {
        void userLoginResponded(LoginResponse r);
    }

    public GetLoginTask(LoginPresenter presenter, GetLoginObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected LoginResponse doInBackground(LoginRequest... loginRequests) {
        LoginResponse response = presenter.login(loginRequests[0]);
//        loadImages(response);
        return response;
    }

    private void loadImages(FeedResponse response) {
//        for(Tweet tweet : response.getTweets()) {
//
//            Drawable drawable;
//
//            try {
//                drawable = ImageUtils.drawableFromUrl(tweet.getUrl());
//            } catch (IOException e) {
//                Log.e(this.getClass().getName(), e.toString(), e);
//                drawable = null;
//            }
//
//            ImageCache.getInstance().cacheImage(tweet, drawable);
//        }
    }

    @Override
    protected void onPostExecute(LoginResponse loginResponse) {

        if(observer != null) {
            observer.userLoginResponded(loginResponse);
        }
    }
}
