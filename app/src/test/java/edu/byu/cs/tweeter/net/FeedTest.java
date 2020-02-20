package edu.byu.cs.tweeter.net;

import android.view.View;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import edu.byu.cs.tweeter.model.domain.Tweet;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.net.request.FeedRequest;
import edu.byu.cs.tweeter.net.request.FollowRequest;
import edu.byu.cs.tweeter.net.request.SignUpRequest;
import edu.byu.cs.tweeter.net.request.TweetRequest;
import edu.byu.cs.tweeter.net.response.FeedResponse;
import edu.byu.cs.tweeter.net.response.FollowResponse;
import edu.byu.cs.tweeter.net.response.LoginResponse;
import edu.byu.cs.tweeter.net.response.TweetResponse;
import edu.byu.cs.tweeter.presenter.FeedPresenter;
import edu.byu.cs.tweeter.presenter.LoginPresenter;
import edu.byu.cs.tweeter.presenter.MainPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.GetFollowTask;
import edu.byu.cs.tweeter.view.asyncTasks.GetSendTweetTask;

class FeedTest {

    @BeforeEach
    void setup() {
        ServerFacade facade = new ServerFacade();
        facade.clearAll();
    }

    @Test
    void testGetEmptyFeedNoFollowersOrFollowees() {

        User user = new User("Test", "User", null);
        FeedRequest request = new FeedRequest(user, 10, null);
        FeedPresenter presenter = new FeedPresenter();
        FeedResponse response = presenter.getTweets(request);

        Assertions.assertEquals(response.getTweets().size(), 6); // initialized by facade

    }

    @Test
    void testGetEmptyFeedNoFollowees() {

        User user = new User("Test", "User", null);
        User follower = new User("Follows", "Test", null);

        FollowRequest followRequest = new FollowRequest(user, follower);
        MainPresenter mainPresenter = new MainPresenter();
        FollowResponse followResponse = mainPresenter.follow(followRequest);

        int i = 0;
        while(i < 9) {
            TweetRequest tweetRequest = new TweetRequest(new Tweet(follower,"fake tweet: "+i,null));
            TweetResponse tweetResponse = mainPresenter.addTweet(tweetRequest);

            i++;
        }

        FeedRequest request = new FeedRequest(user, 10, null);
        FeedPresenter presenter = new FeedPresenter();
        FeedResponse response = presenter.getTweets(request);

        Assertions.assertEquals(response.getTweets().size(), 0);

    }

    @Test
    void testGetFeedNormal() {

        User user = new User("Test", "User", null);
        User following = new User("Following", "Test", null);

        SignUpRequest signUpRequest = new SignUpRequest(user.getFirstName(),user.getLastName(),user.getAlias(),"pass",null);
        LoginPresenter loginPresenter = new LoginPresenter();
        LoginResponse loginResponse = loginPresenter.signUp(signUpRequest);

        signUpRequest = new SignUpRequest(following.getFirstName(), following.getLastName(), following.getAlias(), "pass", null);
        loginPresenter.signUp(signUpRequest);

        FollowRequest followRequest = new FollowRequest(following, user);
        MainPresenter mainPresenter = new MainPresenter();
        FollowResponse followResponse = mainPresenter.follow(followRequest);

        int i = 0;
        while(i < 9) {
            TweetRequest tweetRequest = new TweetRequest(new Tweet(following,"fake tweet: "+i,null));
            TweetResponse tweetResponse = mainPresenter.addTweet(tweetRequest);

            i++;
        }

        FeedRequest request = new FeedRequest(user, 10, null);
        FeedPresenter presenter = new FeedPresenter();
        FeedResponse response = presenter.getTweets(request);

        Assertions.assertEquals(response.getTweets().size(), 6);
        Assertions.assertEquals(response.hasMorePages(), false);
    }

    @Test
    void testGetFeedCorrectOrder() {

        User user = new User("Test", "User", null);
        User following = new User("Following", "Test", null);

        FollowRequest followRequest = new FollowRequest(following, user);
        MainPresenter mainPresenter = new MainPresenter();
        FollowResponse followResponse = mainPresenter.follow(followRequest);

        int i = 0;
        while(i < 9) {
            TweetRequest tweetRequest = new TweetRequest(new Tweet(following,"fake tweet: "+i,null));
            TweetResponse tweetResponse = mainPresenter.addTweet(tweetRequest);

            i++;
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        FeedRequest request = new FeedRequest(user, 10, null);
        FeedPresenter presenter = new FeedPresenter();
        FeedResponse response = presenter.getTweets(request);

        i = 9;
        for (Tweet t : response.getTweets()) {
            if (t.getUser().equals(following)) {
                Assertions.assertEquals("fake tweet: "+i, t.getMessage());
                i--;
            }
        }
    }

    @Test
    void testGetFollowersFeed() {

        User user = new User("Test", "User", null);
        User follower = new User("Follower", "Test", null);

        SignUpRequest signUpRequest = new SignUpRequest(user.getFirstName(),user.getLastName(),user.getAlias(),"pass",null);
        LoginPresenter loginPresenter = new LoginPresenter();
        LoginResponse loginResponse = loginPresenter.signUp(signUpRequest);

        signUpRequest = new SignUpRequest(follower.getFirstName(), follower.getLastName(), follower.getAlias(), "pass", null);
        loginPresenter.signUp(signUpRequest);

        FollowRequest followRequest = new FollowRequest(user, follower);
        MainPresenter mainPresenter = new MainPresenter();
        FollowResponse followResponse = mainPresenter.follow(followRequest);

        int i = 0;
        while(i < 9) {
            TweetRequest tweetRequest = new TweetRequest(new Tweet(user,"fake tweet: "+i,null));
            TweetResponse tweetResponse = mainPresenter.addTweet(tweetRequest);

            i++;
        }

        FeedRequest request = new FeedRequest(follower, 10, null);
        FeedPresenter presenter = new FeedPresenter();
        FeedResponse response = presenter.getTweets(request);

        Assertions.assertEquals(response.getTweets().size(), 6);
        Assertions.assertEquals(response.hasMorePages(), false);

    }
}
