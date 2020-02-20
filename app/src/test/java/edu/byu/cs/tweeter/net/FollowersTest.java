package edu.byu.cs.tweeter.net;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.net.request.FollowRequest;
import edu.byu.cs.tweeter.net.request.FollowerRequest;
import edu.byu.cs.tweeter.net.request.FollowingRequest;
import edu.byu.cs.tweeter.net.request.SignUpRequest;
import edu.byu.cs.tweeter.net.response.FollowResponse;
import edu.byu.cs.tweeter.net.response.FollowerResponse;
import edu.byu.cs.tweeter.net.response.FollowingResponse;
import edu.byu.cs.tweeter.net.response.LoginResponse;
import edu.byu.cs.tweeter.presenter.FollowerPresenter;
import edu.byu.cs.tweeter.presenter.FollowingPresenter;
import edu.byu.cs.tweeter.presenter.LoginPresenter;
import edu.byu.cs.tweeter.presenter.MainPresenter;

class FollowersTest {

    @BeforeEach
    void setup() {
        ServerFacade facade = new ServerFacade();
        facade.clearAll();
    }

    @Test
    void testGetEmptyFollowersInitial() {

        User user = new User("Test", "User", null);
        FollowerRequest followerRequest = new FollowerRequest(user, 10, null);
        FollowerPresenter presenter = new FollowerPresenter();
        FollowerResponse response = presenter.getFollower(followerRequest);

        Assertions.assertEquals(response.getFollowees().size(), 3); // initialized in facade
    }

    @Test
    void testGetFollowersNormal() {

        User user = new User("Test", "User", null);
        User follower = new User("Follower", "Test", null);

        SignUpRequest signUpRequest = new SignUpRequest(user.getFirstName(),user.getLastName(),user.getAlias(),"pass",null);
        LoginPresenter loginPresenter = new LoginPresenter();
        LoginResponse loginResponse = loginPresenter.signUp(signUpRequest);

        signUpRequest = new SignUpRequest(follower.getFirstName(),follower.getLastName(),follower.getAlias(),"pass",null);
        loginPresenter = new LoginPresenter();
        loginResponse = loginPresenter.signUp(signUpRequest);

        FollowRequest followRequest = new FollowRequest(user, follower);
        MainPresenter mainPresenter = new MainPresenter();
        FollowResponse followResponse = mainPresenter.follow(followRequest);

        FollowerRequest followerRequest = new FollowerRequest(user, 10, null);
        FollowerPresenter presenter = new FollowerPresenter();
        FollowerResponse response = presenter.getFollower(followerRequest);

        Assertions.assertEquals(response.getFollowees().size(), 1); // initialized in facade
    }

}
