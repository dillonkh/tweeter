package edu.byu.cs.tweeter.model.domain;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Tweet implements Comparable<Tweet> {

//    private final String userHandle;
    private final User user;
    private final String message;
    private final String url;

    public Tweet(@NotNull User user, @NotNull String message, @NotNull String url) {
        this.user = user;
        this.message = message;
        this.url = url;
    }

    public User getUser() {
        return user;
    }

    public String getMessage() {
        return message;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tweet tweet = (Tweet) o;
        return user.equals(tweet.user) && message.equals(tweet.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }

    @NotNull
    @Override
    public String toString() {
        return "Tweet{" +
                "User='" + user.toString() + '\'' +
                ", message='" + message + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    @Override
    public int compareTo(Tweet tweet) {
        return this.getMessage().compareTo(tweet.getMessage());
    }
}
