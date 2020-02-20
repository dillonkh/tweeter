package edu.byu.cs.tweeter.presenter;

import edu.byu.cs.tweeter.model.services.FollowerService;
import edu.byu.cs.tweeter.model.services.FollowingService;
import edu.byu.cs.tweeter.net.request.FollowerRequest;
import edu.byu.cs.tweeter.net.request.FollowingRequest;
import edu.byu.cs.tweeter.net.request.UserRequest;
import edu.byu.cs.tweeter.net.response.FollowerResponse;
import edu.byu.cs.tweeter.net.response.FollowingResponse;
import edu.byu.cs.tweeter.net.response.UserResponse;

public class FollowerPresenter extends Presenter {

    private final View view;

    @Override
    public UserResponse getUser(UserRequest request) {
        return FollowerService.getInstance().getUser(request);
    }

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, Specify methods here that will be called on the view in response to model updates
    }

    public FollowerPresenter(View view) {
        this.view = view;
    }

    public FollowerPresenter() {
        view = null;
    }

    public FollowerResponse getFollower(FollowerRequest request) {
        return FollowerService.getInstance().getFollowers(request);
    }
}
