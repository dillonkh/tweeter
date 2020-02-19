package edu.byu.cs.tweeter.view.asyncTasks;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.Tweet;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.net.request.FollowerRequest;
import edu.byu.cs.tweeter.net.request.StoryRequest;
import edu.byu.cs.tweeter.net.response.FollowerResponse;
import edu.byu.cs.tweeter.net.response.StoryResponse;
import edu.byu.cs.tweeter.presenter.FollowerPresenter;
import edu.byu.cs.tweeter.presenter.StoryPresenter;
import edu.byu.cs.tweeter.view.cache.ImageCache;
import edu.byu.cs.tweeter.view.util.ImageUtils;

public class GetStoryTask extends AsyncTask<StoryRequest, Void, StoryResponse> {

    private final StoryPresenter presenter;
    private final GetStoryObserver observer;

    public interface GetStoryObserver {
        void tweetsRetrieved(StoryResponse storyResponse);
    }

    public GetStoryTask(StoryPresenter presenter, GetStoryObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected StoryResponse doInBackground(StoryRequest... storyRequests) {
        StoryResponse response = presenter.getTweets(storyRequests[0]);
//        loadImages(response);
        return response;
    }

    private void loadImages(StoryResponse response) {
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
    protected void onPostExecute(StoryResponse storyResponse) {

        if(observer != null) {
            observer.tweetsRetrieved(storyResponse);
        }

    }
}
