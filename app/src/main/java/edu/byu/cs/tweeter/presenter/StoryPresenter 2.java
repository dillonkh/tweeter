package edu.byu.cs.tweeter.presenter;

import edu.byu.cs.tweeter.model.services.StoryService;
import edu.byu.cs.tweeter.net.request.StoryRequest;
import edu.byu.cs.tweeter.net.response.StoryResponse;

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

    public StoryResponse getTweets(StoryRequest request) {
        return StoryService.getInstance().getTweets(request);
    }
}
