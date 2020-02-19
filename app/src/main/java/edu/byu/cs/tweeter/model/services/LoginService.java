package edu.byu.cs.tweeter.model.services;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.net.ServerFacade;
import edu.byu.cs.tweeter.net.request.LoginRequest;
import edu.byu.cs.tweeter.net.request.SignUpRequest;
import edu.byu.cs.tweeter.net.response.LoginResponse;

public class LoginService {

    private static LoginService instance;

    private final ServerFacade serverFacade;

    private User currentUser;

    public static LoginService getInstance() {
        if(instance == null) {
            instance = new LoginService();
        }

        return instance;
    }

    private LoginService() {
        // TODO: Remove when the actual login functionality exists.
//        currentUser = new User("Test", "User",
//                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
//        setCurrentUser(currentUser);
        serverFacade = new ServerFacade();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public LoginResponse login(LoginRequest request) {
        LoginResponse r = serverFacade.login(request);
        // some other stuff
        if (r.isAuthentcated() && r.getUserSignedIn() != null) {
            setCurrentUser(r.getUserSignedIn());
        }

        return r;
    }

    public LoginResponse signUp(SignUpRequest request) {
        LoginResponse r = serverFacade.signUp(request);
        // some other stuff
        if (r.isAuthentcated() && r.getUserSignedIn() != null) {
            setCurrentUser(r.getUserSignedIn());
        }

        return r;
    }
}
