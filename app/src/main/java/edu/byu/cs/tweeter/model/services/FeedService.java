package edu.byu.cs.tweeter.model.services;

import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.net.request.FeedRequest;
import edu.byu.cs.tweeter.net.request.UserRequest;
import edu.byu.cs.tweeter.net.response.FeedResponse;
import edu.byu.cs.tweeter.net.response.UserResponse;

public class FeedService {

    private static FeedService instance;

    private final ServerFacade serverFacade;

    public static FeedService getInstance() {
        if(instance == null) {
            instance = new FeedService();
        }

        return instance;
    }

    private FeedService() {
        serverFacade = new ServerFacade();
    }

    public FeedResponse getFeed(FeedRequest request) {
        FeedResponse r = serverFacade.getFeed(request);
        return r;
    }

    public UserResponse getUser(UserRequest request) {
        UserResponse r = serverFacade.getUser(request);
        return r;
    }
}
