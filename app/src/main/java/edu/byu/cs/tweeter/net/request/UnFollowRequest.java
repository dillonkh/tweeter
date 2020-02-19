package edu.byu.cs.tweeter.net.request;

import edu.byu.cs.tweeter.model.domain.User;

public class UnFollowRequest {

    private final User userToFollow;
    private final User userLoggedIn;


    public UnFollowRequest(User userToFollow, User userLoggedIn) {
        this.userToFollow = userToFollow;
        this.userLoggedIn = userLoggedIn;
    }

    public User getUserToFollow() {
        return userToFollow;
    }

    public User getUserLoggedIn() {
        return userLoggedIn;
    }
}
