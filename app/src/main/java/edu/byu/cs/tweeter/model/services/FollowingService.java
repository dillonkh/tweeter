package edu.byu.cs.tweeter.model.services;

import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.net.request.FollowingRequest;
import edu.byu.cs.tweeter.net.request.UserRequest;
import edu.byu.cs.tweeter.net.response.FollowingResponse;
import edu.byu.cs.tweeter.net.response.UserResponse;

public class FollowingService {

    private static FollowingService instance;

    private final ServerFacade serverFacade;

    public static FollowingService getInstance() {
        if(instance == null) {
            instance = new FollowingService();
        }

        return instance;
    }

    private FollowingService() {
        serverFacade = new ServerFacade();
    }

    public FollowingResponse getFollowees(FollowingRequest request) {
        return serverFacade.getFollowees(request);
    }

    public UserResponse getUser(UserRequest request) {
        UserResponse r = serverFacade.getUser(request);
        return r;
    }
}
