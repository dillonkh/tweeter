package edu.byu.cs.tweeter.net;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.net.request.LoginRequest;
import edu.byu.cs.tweeter.net.request.SignUpRequest;
import edu.byu.cs.tweeter.net.request.UserRequest;
import edu.byu.cs.tweeter.net.response.LoginResponse;
import edu.byu.cs.tweeter.net.response.UserResponse;
import edu.byu.cs.tweeter.presenter.FeedPresenter;
import edu.byu.cs.tweeter.presenter.LoginPresenter;

class SignOutPresenterTest {

    @BeforeEach
    void setup() {
        ServerFacade facade = new ServerFacade();
        facade.clearAll();
    }

    @Test
    void testSignOutValid() {

        User user = new User("Test", "User", null);

        SignUpRequest signUpRequest = new SignUpRequest(user.getFirstName(),user.getLastName(),user.getAlias(),"pass",null);
        LoginPresenter loginPresenter = new LoginPresenter();
        LoginResponse loginResponse = loginPresenter.signUp(signUpRequest);

        LoginRequest loginRequest = new LoginRequest("pass",user.getAlias());
        loginPresenter = new LoginPresenter();
        loginResponse = loginPresenter.login(loginRequest);

        Assertions.assertEquals(loginResponse.isAuthentcated(), true);
        Assertions.assertEquals(loginResponse.getUserSignedIn(), user);

        UserRequest userRequest = new UserRequest(user,user.getAlias());
        FeedPresenter presenter = new FeedPresenter();
        UserResponse response = presenter.getUser(userRequest);

        Assertions.assertEquals(response.getUser(), user);
    }

    @Test
    void testSignOutNotCorrectUser() {

        User user = new User("Test", "User", null);

        SignUpRequest signUpRequest = new SignUpRequest(user.getFirstName(),user.getLastName(),user.getAlias(),"pass",null);
        LoginPresenter loginPresenter = new LoginPresenter();
        LoginResponse loginResponse = loginPresenter.signUp(signUpRequest);

        LoginRequest loginRequest = new LoginRequest("pass",user.getAlias());
        loginPresenter = new LoginPresenter();
        loginResponse = loginPresenter.login(loginRequest);

        Assertions.assertEquals(loginResponse.isAuthentcated(), true);
        Assertions.assertEquals(loginResponse.getUserSignedIn(), user);

        UserRequest userRequest = new UserRequest(user,user.getAlias());
        FeedPresenter presenter = new FeedPresenter();
        UserResponse response = presenter.getUser(userRequest);

        Assertions.assertEquals(response.getUser(), user);
    }

}
