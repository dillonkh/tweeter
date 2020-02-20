package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs.tweeter.net.request.FeedRequest;
import edu.byu.cs.tweeter.net.request.TweetRequest;
import edu.byu.cs.tweeter.net.response.FeedResponse;
import edu.byu.cs.tweeter.net.response.TweetResponse;
import edu.byu.cs.tweeter.presenter.FeedPresenter;
import edu.byu.cs.tweeter.presenter.MainPresenter;


public class GetSendTweetTask extends AsyncTask<TweetRequest, Void, TweetResponse> {

    private final MainPresenter presenter;
    private final GetTweetObserver observer;

    public interface GetTweetObserver {
        void tweetResponded(TweetResponse TweetResponse);
    }

    public GetSendTweetTask(MainPresenter presenter, GetTweetObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected TweetResponse doInBackground(TweetRequest... tweetRequests) {
        TweetResponse response = presenter.addTweet(tweetRequests[0]);
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
    protected void onPostExecute(TweetResponse tweetResponse) {

        if(observer != null) {
            observer.tweetResponded(tweetResponse);
        }
    }
}
