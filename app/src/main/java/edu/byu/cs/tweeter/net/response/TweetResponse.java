package edu.byu.cs.tweeter.net.response;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Tweet;

public class TweetResponse {

    private boolean sent;

    public TweetResponse(boolean sent) {
        this.sent = sent;
    }

    public boolean isSent() {
        return sent;
    }
}
