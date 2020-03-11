package edu.byu.cs.tweeter.model.services;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.net.request.FollowRequest;
import edu.byu.cs.tweeter.net.request.UnFollowRequest;
import edu.byu.cs.tweeter.net.response.FollowResponse;
import edu.byu.cs.tweeter.net.response.UnFollowResponse;

public class FollowService {

    private static FollowService instance;

    private final ServerFacade serverFacade;

    public static FollowService getInstance() {
        if(instance == null) {
            instance = new FollowService();
        }

        return instance;
    }

    private FollowService() {
        serverFacade = new ServerFacade();
    }

//    public FeedResponse getFeed(FeedRequest request) {
//        FeedResponse r = serverFacade.getFeed(request);
//        return r;
//    }
//
//    public UserResponse getUser(UserRequest request) {
//        UserResponse r = serverFacade.getUser(request);
//        return r;
//    }

    public FollowResponse followUser(FollowRequest request) {
        FollowResponse response = serverFacade.followUser(request);
        return response;

    }

    public UnFollowResponse unfollowUser(UnFollowRequest request) {
        UnFollowResponse response = serverFacade.unfollowUser(request);
        return response;

    }

    public boolean isFollowing(User userLoggedIn, User userShown) {
        return serverFacade.isFollowing(userLoggedIn,userShown);
    }

}
