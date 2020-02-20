package edu.byu.cs.tweeter.presenter;

import edu.byu.cs.tweeter.model.domain.Tweet;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.services.FollowService;
import edu.byu.cs.tweeter.model.services.StoryService;
import edu.byu.cs.tweeter.net.request.FollowRequest;
import edu.byu.cs.tweeter.net.request.TweetRequest;
import edu.byu.cs.tweeter.net.request.UnFollowRequest;
import edu.byu.cs.tweeter.net.request.UserRequest;
import edu.byu.cs.tweeter.net.response.FollowResponse;
import edu.byu.cs.tweeter.net.response.TweetResponse;
import edu.byu.cs.tweeter.net.response.UnFollowResponse;
import edu.byu.cs.tweeter.net.response.UserResponse;

public class MainPresenter extends Presenter {

    private final View view;

    @Override
    public UserResponse getUser(UserRequest request) {
        return StoryService.getInstance().getUser(request);
    }

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, Specify methods here that will be called on the view in response to model updates
    }

    public MainPresenter(View view) {
        this.view = view;
    }

    public MainPresenter() {
        view = null;
    }

    public TweetResponse addTweet(TweetRequest request) {
        return StoryService.getInstance().addTweet(request);
    }

    public FollowResponse follow(FollowRequest request) {
        return FollowService.getInstance().followUser(request);
    }

    public UnFollowResponse unfollow(UnFollowRequest request) {
        return FollowService.getInstance().unfollowUser(request);
    }

    public boolean isFollowing(User userLoggedIn, User userShown) {
        return FollowService.getInstance().isFollowing(userLoggedIn, userShown);
    }
}
