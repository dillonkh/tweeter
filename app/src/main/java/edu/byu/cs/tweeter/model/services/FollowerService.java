package edu.byu.cs.tweeter.model.services;

import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.net.request.FollowerRequest;
import edu.byu.cs.tweeter.net.request.UserRequest;
import edu.byu.cs.tweeter.net.response.FollowerResponse;
import edu.byu.cs.tweeter.net.response.UserResponse;

public class FollowerService {

    private static FollowerService instance;

    private final ServerFacade serverFacade;

    public static FollowerService getInstance() {
        if(instance == null) {
            instance = new FollowerService();
        }

        return instance;
    }

    private FollowerService() {
        serverFacade = new ServerFacade();
    }

    public FollowerResponse getFollowers(FollowerRequest request) {
        return serverFacade.getFollowers(request);
    }

    public UserResponse getUser(UserRequest request) {
        UserResponse r = serverFacade.getUser(request);
        return r;
    }
}
