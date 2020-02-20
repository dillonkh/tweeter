package edu.byu.cs.tweeter.presenter;

import edu.byu.cs.tweeter.model.services.FeedService;
import edu.byu.cs.tweeter.net.request.FeedRequest;
import edu.byu.cs.tweeter.net.request.UserRequest;
import edu.byu.cs.tweeter.net.response.FeedResponse;
import edu.byu.cs.tweeter.net.response.UserResponse;

public class FeedPresenter extends Presenter {

    private final View view;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, Specify methods here that will be called on the view in response to model updates
        void listChanged();
    }

    public FeedPresenter(View view) {
        this.view = view;
    }

    public FeedPresenter() {
        view = null;
    }

    public FeedResponse getTweets(FeedRequest request) {
        return FeedService.getInstance().getFeed(request);
    }

    public UserResponse getUser(UserRequest request) {
        return FeedService.getInstance().getUser(request);
    }
}
