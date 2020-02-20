package edu.byu.cs.tweeter.model.services;

import edu.byu.cs.tweeter.model.domain.Tweet;
import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.net.request.StoryRequest;
import edu.byu.cs.tweeter.net.request.TweetRequest;
import edu.byu.cs.tweeter.net.request.UserRequest;
import edu.byu.cs.tweeter.net.response.StoryResponse;
import edu.byu.cs.tweeter.net.response.TweetResponse;
import edu.byu.cs.tweeter.net.response.UserResponse;

public class StoryService {

    private static StoryService instance;
    private final ServerFacade serverFacade;

    public static StoryService getInstance() {
        if(instance == null) {
            instance = new StoryService();
        }

        return instance;
    }

    private StoryService() {
        serverFacade = new ServerFacade();
    }

    public StoryResponse getTweets(StoryRequest request) {
        StoryResponse r = serverFacade.getStory(request);
        return r;
    }

    public TweetResponse addTweet(TweetRequest request) {
        return serverFacade.addTweet(request);
    }

    public UserResponse getUser(UserRequest request) {
        UserResponse r = serverFacade.getUser(request);
        return r;
    }
}
