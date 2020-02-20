package edu.byu.cs.tweeter.net.request;

import edu.byu.cs.tweeter.model.domain.Tweet;
import edu.byu.cs.tweeter.model.domain.User;

public class TweetRequest {

    private Tweet tweet;

    public TweetRequest(Tweet tweet) {
        this.tweet = tweet;
    }

    public Tweet getTweet() {
        return tweet;
    }
}
