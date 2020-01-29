package edu.byu.cs.tweeter.net.request;

import edu.byu.cs.tweeter.model.domain.User;

public class FollowerRequest {

    private final User follower;
    private final int limit;
    private final User lastFollowee;

    public FollowerRequest(User follower, int limit, User lastFollowee) {
        this.follower = follower;
        this.limit = limit;
        this.lastFollowee = lastFollowee;
    }

    public User getFollowing() {
        return follower;
    }

    public int getLimit() {
        return limit;
    }

    public User getLastFollowee() {
        return lastFollowee;
    }
}
