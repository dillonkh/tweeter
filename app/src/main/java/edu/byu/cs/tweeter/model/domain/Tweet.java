package edu.byu.cs.tweeter.model.domain;

import android.text.SpannableString;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class Tweet implements Comparable<Tweet> {

//    private final String userHandle;
    private final User user;
    private final String message;
    private final String url;
    private final String timeStamp;
    private final LocalDateTime now;

    public Tweet(@NotNull User user, @NotNull String message, String url) {
        this.user = user;
        this.message = message;
        this.url = url;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        this.now = LocalDateTime.now();
        this.timeStamp = dtf.format(now);

    }

    public Tweet(@NotNull User user, @NotNull SpannableString message, String url) {
        this.user = user;
        this.message = message.toString();
        this.url = url;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        this.now = LocalDateTime.now();
        this.timeStamp = dtf.format(now);

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

    public String getTimeStamp() {
        return timeStamp;
    }

    public LocalDateTime getNow() {
        return now;
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
        return this.now.compareTo(tweet.getNow());
    }
}
