package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs.tweeter.net.request.FeedRequest;
import edu.byu.cs.tweeter.net.response.FeedResponse;
import edu.byu.cs.tweeter.presenter.FeedPresenter;


public class GetFeedTask extends AsyncTask<FeedRequest, Void, FeedResponse> {

    private final FeedPresenter presenter;
    private final GetFeedObserver observer;

    public interface GetFeedObserver {
        void tweetsRetrieved(FeedResponse feedResponse);
    }

    public GetFeedTask(FeedPresenter presenter, GetFeedObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected FeedResponse doInBackground(FeedRequest... feedRequests) {
        FeedResponse response = presenter.getTweets(feedRequests[0]);
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
    protected void onPostExecute(FeedResponse feedResponse) {

        if(observer != null) {
            observer.tweetsRetrieved(feedResponse);
        }
    }
}
