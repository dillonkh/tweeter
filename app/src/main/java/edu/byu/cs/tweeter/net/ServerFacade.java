package edu.byu.cs.tweeter.net;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.Tweet;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.net.request.FeedRequest;
import edu.byu.cs.tweeter.net.request.FollowerRequest;
import edu.byu.cs.tweeter.net.request.FollowingRequest;
import edu.byu.cs.tweeter.net.request.StoryRequest;
import edu.byu.cs.tweeter.net.request.UserRequest;
import edu.byu.cs.tweeter.net.response.FeedResponse;
import edu.byu.cs.tweeter.net.response.FollowerResponse;
import edu.byu.cs.tweeter.net.response.FollowingResponse;
import edu.byu.cs.tweeter.net.response.StoryResponse;
import edu.byu.cs.tweeter.net.response.UserResponse;

public class ServerFacade {

    private static Map<User, List<User>> followeesByFollower;
    private static Map<User, List<Tweet>> tweetsByUser;

    public FollowingResponse getFollowees(FollowingRequest request) {

        assert request.getLimit() >= 0;
        assert request.getFollower() != null;

        if(followeesByFollower == null) {
            followeesByFollower = initializeFollowees();
        }

        List<User> allFollowees = followeesByFollower.get(request.getFollower());
        List<User> responseFollowees = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            if (allFollowees != null) {
                int followeesIndex = getFolloweesStartingIndex(request.getLastFollowee(), allFollowees);

                for(int limitCounter = 0; followeesIndex < allFollowees.size() && limitCounter < request.getLimit(); followeesIndex++, limitCounter++) {
                    responseFollowees.add(allFollowees.get(followeesIndex));
                }

                hasMorePages = followeesIndex < allFollowees.size();
            }
        }

        return new FollowingResponse(responseFollowees, hasMorePages);
    }

    public UserResponse getUser(UserRequest request) {
        if(followeesByFollower == null) {
            followeesByFollower = initializeFollowees();
        }

        List<User> userSignedFollowers = followeesByFollower.get(request.getUser());

        for (int i = 0; i < userSignedFollowers.size(); i++) {
            if (userSignedFollowers.get(i).getAlias().equals(request.getHandle())) {
                return new UserResponse(userSignedFollowers.get(i));
            }
        }

        return null;
    }

    public FollowerResponse getFollowers(FollowerRequest request) {

        assert request.getLimit() >= 0;
        assert request.getFollowing() != null;

        if(followeesByFollower == null) {
            followeesByFollower = initializeFollowees();
        }

        List<User> allFollowees = followeesByFollower.get(request.getFollowing());
        List<User> responseFollowees = new ArrayList<>(request.getLimit());

        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            if (allFollowees != null) {
                int followeesIndex = getFolloweesStartingIndex(request.getLastFollowee(), allFollowees);

                for(int limitCounter = 0; followeesIndex < allFollowees.size() && limitCounter < request.getLimit(); followeesIndex++, limitCounter++) {
                    responseFollowees.add(allFollowees.get(followeesIndex));
                }

                hasMorePages = followeesIndex < allFollowees.size();
            }
        }

        return new FollowerResponse(responseFollowees, hasMorePages);
    }

    public StoryResponse getTweets(StoryRequest request) {

        assert request.getLimit() >= 0;
        assert request.getUser() != null;


        if(tweetsByUser == null) {
            tweetsByUser = initializeTweets(request.getUser());
        }

        List<Tweet> responseTweets = new ArrayList<>(request.getLimit());
        List<Tweet> allTweets = tweetsByUser.get(request.getUser());
//
        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            if (allTweets != null) {
                int tweetsIndex = getTweetsStartingIndex(request.getLastTweet(), allTweets);

                for(int limitCounter = 0; tweetsIndex < allTweets.size() && limitCounter < request.getLimit(); tweetsIndex++, limitCounter++) {
                    responseTweets.add(allTweets.get(tweetsIndex));
                }

                hasMorePages = tweetsIndex < allTweets.size();
            }
        }

//        return new FollowerResponse(responseFollowees, hasMorePages);
        return new StoryResponse(responseTweets,hasMorePages);

    }

    public FeedResponse getTweets(FeedRequest request) {

        assert request.getLimit() >= 0;
        assert request.getUser() != null;


        if(tweetsByUser == null) {
            tweetsByUser = initializeTweets(request.getUser());
        }

        List<Tweet> responseTweets = new ArrayList<>(request.getLimit());
        List<Tweet> allTweets = new ArrayList<>();

        for (Map.Entry<User, List<Tweet> > entry : tweetsByUser.entrySet()) {
            allTweets.addAll(0, entry.getValue());
        }
//
        boolean hasMorePages = false;

        if(request.getLimit() > 0) {
            if (allTweets != null) {
                int tweetsIndex = getTweetsStartingIndex(request.getLastTweet(), allTweets);

                for(int limitCounter = 0; tweetsIndex < allTweets.size() && limitCounter < request.getLimit(); tweetsIndex++, limitCounter++) {
                    responseTweets.add(allTweets.get(tweetsIndex));
                }

                hasMorePages = tweetsIndex < allTweets.size();
            }
        }

        Collections.sort(responseTweets, Collections.<Tweet>reverseOrder());
        return new FeedResponse(responseTweets,hasMorePages);

    }

    public void addTweet(Tweet tweet) {
        if(tweetsByUser == null) {
            tweetsByUser = initializeTweets(tweet.getUser());
        }
        List<Tweet> tweets = tweetsByUser.get(tweet.getUser());
        if (tweets == null) {
            tweets = new ArrayList<Tweet>();
        }
        tweets.add(tweet);
        tweetsByUser.put(tweet.getUser(),tweets);
    }

    private int getFolloweesStartingIndex(User lastFollowee, List<User> allFollowees) {

        int followeesIndex = 0;

        if(lastFollowee != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allFollowees.size(); i++) {
                if(lastFollowee.equals(allFollowees.get(i))) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    followeesIndex = i + 1;
                }
            }
        }

        return followeesIndex;
    }

    private int getTweetsStartingIndex(Tweet lastTweet, List<Tweet> allTweets) {

        int tweetIndex = 0;

        if(lastTweet != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allTweets.size(); i++) {
                if(lastTweet.equals(allTweets.get(i))) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    tweetIndex = i + 1;
                }
            }
        }

        return tweetIndex;
    }

    /**
     * Generates the followee data.
     */
    private Map<User, List<User>> initializeFollowees() {

        Map<User, List<User>> followeesByFollower = new HashMap<>();

        List<Follow> follows = getFollowGenerator().generateUsersAndFollows(1,
                0, 1, FollowGenerator.Sort.FOLLOWER_FOLLOWEE);

        // Populate a map of followees, keyed by follower so we can easily handle followee requests
        for(Follow follow : follows) {
            List<User> followees = followeesByFollower.get(follow.getFollower());

            if(followees == null) {
                followees = new ArrayList<>();
                followeesByFollower.put(follow.getFollower(), followees);
            }

            followees.add(follow.getFollowee());
        }

        return followeesByFollower;
    }

    /**
     * Generates the tweet data.
     */
    private Map<User, List<Tweet>> initializeTweets(User user) {

        Map<User, List<Tweet>> tweetsByUser = new HashMap<>();
        ArrayList<Tweet> allTweets = new ArrayList<>();

        if(followeesByFollower == null) {
            followeesByFollower = initializeFollowees();
        }

        List<User> peopleIFollow = followeesByFollower.get(user);

        for (User person : peopleIFollow) {
            allTweets.addAll(person.getTweets());
            tweetsByUser.put(person, allTweets);
        }
        Collections.sort(allTweets);


        return tweetsByUser;
    }

    /**
     * Returns an instance of FollowGenerator that can be used to generate Follow data. This is
     * written as a separate method to allow mocking of the generator.
     *
     * @return the generator.
     */
    FollowGenerator getFollowGenerator() {
        return FollowGenerator.getInstance();
    }

}
