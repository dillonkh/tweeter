package edu.byu.cs.tweeter.net.response;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;

public class FollowerResponse extends PagedResponse {

    private List<User> followers; // TODO: this may need to change

    public FollowerResponse(String message) {
        super(false, message, false);
    }

    public FollowerResponse(List<User> followees, boolean hasMorePages) { // TODO: this may need to change
        super(true, hasMorePages);
        this.followers = followees; // TODO: this may need to change
    }

    public List<User> getFollowees() {
        return followers;
    } // TODO: this may need to change
}
