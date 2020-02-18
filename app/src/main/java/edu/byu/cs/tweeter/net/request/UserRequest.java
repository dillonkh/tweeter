package edu.byu.cs.tweeter.net.request;

import edu.byu.cs.tweeter.model.domain.Tweet;
import edu.byu.cs.tweeter.model.domain.User;

public class UserRequest {

    private final User user;
    private final String handle;

    public UserRequest(User user, String handle) {
        this.user = user;
        this.handle = handle;
    }

    public User getUser() {
        return user;
    }

    public String getHandle() {
        return handle;
    }
}
