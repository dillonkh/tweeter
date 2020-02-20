package edu.byu.cs.tweeter.presenter;

import edu.byu.cs.tweeter.model.services.FeedService;
import edu.byu.cs.tweeter.model.services.LoginService;
import edu.byu.cs.tweeter.net.request.FeedRequest;
import edu.byu.cs.tweeter.net.request.LoginRequest;
import edu.byu.cs.tweeter.net.request.SignUpRequest;
import edu.byu.cs.tweeter.net.request.UserRequest;
import edu.byu.cs.tweeter.net.response.FeedResponse;
import edu.byu.cs.tweeter.net.response.LoginResponse;
import edu.byu.cs.tweeter.net.response.UserResponse;

public class LoginPresenter extends Presenter {

    private final View view;

    @Override
    public UserResponse getUser(UserRequest request) {
        return null;
    }

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, Specify methods here that will be called on the view in response to model updates
    }

    public LoginPresenter(View view) {
        this.view = view;
    }

    public LoginPresenter() {
        view = null;
    }

    public LoginResponse login (LoginRequest request) {
        return LoginService.getInstance().login(request);
    }

    public LoginResponse signUp (SignUpRequest request) {
        return LoginService.getInstance().signUp(request);
    }
}
