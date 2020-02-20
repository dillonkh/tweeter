package edu.byu.cs.tweeter.presenter;

import edu.byu.cs.tweeter.model.services.FeedService;
import edu.byu.cs.tweeter.model.services.StoryService;
import edu.byu.cs.tweeter.net.request.StoryRequest;
import edu.byu.cs.tweeter.net.request.UserRequest;
import edu.byu.cs.tweeter.net.response.StoryResponse;
import edu.byu.cs.tweeter.net.response.UserResponse;

public class StoryPresenter extends Presenter {

    private final View view;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, Specify methods here that will be called on the view in response to model updates
        void listChanged();
    }

    public StoryPresenter(View view) {
        this.view = view;
    }

    public StoryPresenter() {
        view = null;
    }

    public StoryResponse getTweets(StoryRequest request) {
        return StoryService.getInstance().getTweets(request);
    }

    public UserResponse getUser(UserRequest request) {
        return StoryService.getInstance().getUser(request);
    }
}
