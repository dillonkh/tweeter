package edu.byu.cs.tweeter.net;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.byu.cs.tweeter.model.domain.Tweet;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.net.request.SignUpRequest;
import edu.byu.cs.tweeter.net.request.StoryRequest;
import edu.byu.cs.tweeter.net.request.TweetRequest;
import edu.byu.cs.tweeter.net.response.LoginResponse;
import edu.byu.cs.tweeter.net.response.StoryResponse;
import edu.byu.cs.tweeter.net.response.TweetResponse;
import edu.byu.cs.tweeter.presenter.LoginPresenter;
import edu.byu.cs.tweeter.presenter.MainPresenter;
import edu.byu.cs.tweeter.presenter.StoryPresenter;

class PostStatusTest {

    @BeforeEach
    void setup() {
        ServerFacade facade = new ServerFacade();
        facade.clearAll();
    }

    @Test
    void testPostStatusValid() {

        User user = new User("Test", "User", null);

        SignUpRequest signUpRequest = new SignUpRequest(user.getFirstName(),user.getLastName(),user.getAlias(),"pass",null);
        LoginPresenter loginPresenter = new LoginPresenter();
        LoginResponse loginResponse = loginPresenter.signUp(signUpRequest);

        TweetRequest tweetRequest = new TweetRequest(new Tweet(user,"fake tweet",null));
        MainPresenter mainPresenter = new MainPresenter();
        TweetResponse tweetResponse = mainPresenter.addTweet(tweetRequest);

        Assertions.assertEquals(tweetResponse.isSent(), true);

        StoryRequest storyRequest = new StoryRequest(user, 10, null);
        StoryPresenter storyPresenter = new StoryPresenter();
        StoryResponse storyResponse = storyPresenter.getTweets(storyRequest);

        Assertions.assertEquals(storyResponse.getTweets().size(), 1);

    }

    @Test
    void testPostStatusUserNotSignedIn() {

        User user = new User("Test", "User", null);
        User bad = new User("Bad", "Test", null);

        SignUpRequest signUpRequest = new SignUpRequest(user.getFirstName(),user.getLastName(),user.getAlias(),"pass",null);
        LoginPresenter loginPresenter = new LoginPresenter();
        LoginResponse loginResponse = loginPresenter.signUp(signUpRequest);

        TweetRequest tweetRequest = new TweetRequest(new Tweet(bad,"fake tweet",null));
        MainPresenter mainPresenter = new MainPresenter();
        TweetResponse tweetResponse = mainPresenter.addTweet(tweetRequest);

        Assertions.assertEquals(tweetResponse.isSent(), false);

        StoryRequest storyRequest = new StoryRequest(bad, 10, null);
        StoryPresenter storyPresenter = new StoryPresenter();
        StoryResponse storyResponse = storyPresenter.getTweets(storyRequest);

        Assertions.assertEquals(storyResponse.getMessage(), "Error");

    }

}
