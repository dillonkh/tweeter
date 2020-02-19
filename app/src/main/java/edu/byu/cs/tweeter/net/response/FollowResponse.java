package edu.byu.cs.tweeter.net.response;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;

public class FollowResponse {

    private boolean success;

    public FollowResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
