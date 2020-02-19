package edu.byu.cs.tweeter.net.request;

import edu.byu.cs.tweeter.model.domain.Tweet;
import edu.byu.cs.tweeter.model.domain.User;

public class LoginRequest {

    private final String password;
    private final String handle;

    public LoginRequest(String password, String handle) {
        this.password = password;
        this.handle = handle;
    }

    public String getPassword() {
        return password;
    }

    public String getHandle() {
        return handle;
    }
}
