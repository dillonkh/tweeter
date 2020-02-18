package edu.byu.cs.tweeter.net.response;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Tweet;
import edu.byu.cs.tweeter.model.domain.User;

public class UserResponse {

    private User user;

    public UserResponse(User user) {
//        super(true, hasMorePages);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
