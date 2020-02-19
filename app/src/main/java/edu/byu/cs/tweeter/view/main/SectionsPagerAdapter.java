package edu.byu.cs.tweeter.view.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.view.main.feed.FeedFragment;
import edu.byu.cs.tweeter.view.main.follower.FollowerFragment;
import edu.byu.cs.tweeter.view.main.following.FollowingFragment;
import edu.byu.cs.tweeter.view.main.story.StoryFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter extends FragmentPagerAdapter {

    private static final int FEED_FRAGMENT_POSITION = 0;
    private static final int STORY_FRAGMENT_POSITION = 1;
    private static final int FOLLOWING_FRAGMENT_POSITION = 2;
    private static final int FOLLOWER_FRAGMENT_POSITION = 3;

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.feedTabTitle, R.string.storyTabTitle, R.string.followingTabTitle, R.string.followersTabTitle};
    private final Context mContext;

    private FollowerFragment followerFragment;
    private FollowingFragment followingFragment;
    private StoryFragment storyFragment;
    private FeedFragment feedFragment;


    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        followerFragment = new FollowerFragment();
        followingFragment = new FollowingFragment();
        storyFragment = new StoryFragment();
        feedFragment = new FeedFragment();
    }

    public FollowerFragment getFollowerFragment() {
        return followerFragment;
    }

    public FollowingFragment getFollowingFragment() {
        return followingFragment;
    }

    public StoryFragment getStoryFragment() {
        return storyFragment;
    }

    public FeedFragment getFeedFragment() {
//        feedFragm
        return feedFragment;
    }

    @Override
    public Fragment getItem(int position) {

        if (position == FOLLOWING_FRAGMENT_POSITION) {
            return followingFragment;
        }
        else if (position == FOLLOWER_FRAGMENT_POSITION) {
            return followerFragment;
        }
        else if (position == STORY_FRAGMENT_POSITION) {
            return storyFragment;
        }
        else if (position == FEED_FRAGMENT_POSITION) {
            return feedFragment;
        }
        else {
            return PlaceholderFragment.newInstance(position + 1);
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 4 total pages.
        return 4;
    }
}