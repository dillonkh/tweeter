package edu.byu.cs.tweeter.view.main.follower;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.net.request.FollowerRequest;
import edu.byu.cs.tweeter.net.request.UserRequest;
import edu.byu.cs.tweeter.net.response.FollowerResponse;
import edu.byu.cs.tweeter.presenter.FollowerPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.GetFollowerTask;
import edu.byu.cs.tweeter.view.asyncTasks.GetUserTask;
import edu.byu.cs.tweeter.view.cache.ImageCache;

public class FollowerFragment extends Fragment implements FollowerPresenter.View {

    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;

    private static final int PAGE_SIZE = 10;

    private FollowerPresenter presenter;

    private FollowerRecyclerViewAdapter followerRecyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_follower, container, false);

        presenter = new FollowerPresenter(this);

        RecyclerView followerRecyclerView = view.findViewById(R.id.followerRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        followerRecyclerView.setLayoutManager(layoutManager);

        followerRecyclerViewAdapter = new FollowerRecyclerViewAdapter();
        followerRecyclerView.setAdapter(followerRecyclerViewAdapter);

        followerRecyclerView.addOnScrollListener(new FollowRecyclerViewPaginationScrollListener(layoutManager));

        return view;
    }


    private class FollowerHolder extends RecyclerView.ViewHolder {

        private final ImageView userImage;
        private final TextView userAlias;
        private final TextView userName;

        FollowerHolder(@NonNull View itemView) {
            super(itemView);

            userImage = itemView.findViewById(R.id.userImage);
            userAlias = itemView.findViewById(R.id.userAlias);
            userName = itemView.findViewById(R.id.userName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "You selected '" + userName.getText() + "'.", Toast.LENGTH_SHORT).show();
                }
            });
        }

        void bindUser(User user) {
            userImage.setImageDrawable(ImageCache.getInstance().getImageDrawable(user));
            userAlias.setText(user.getAlias());
            userName.setText(user.getName());

            userAlias.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GetUserTask getUserTask = new GetUserTask(presenter, getActivity(), presenter.getUserShown(), userAlias.toString());
                    UserRequest request = new UserRequest(presenter.getUserShown(), userAlias.getText().toString());
                    getUserTask.execute(request);
                }
            });
        }
    }

    private class FollowerRecyclerViewAdapter extends RecyclerView.Adapter<FollowerHolder> implements GetFollowerTask.GetFollowersObserver {

        private final List<User> users = new ArrayList<>();

        private edu.byu.cs.tweeter.model.domain.User lastFollowee;

        private boolean hasMorePages;
        private boolean isLoading = false;

        FollowerRecyclerViewAdapter() {
            loadMoreItems();
        }

        void addItems(List<User> newUsers) {
            int startInsertPosition = users.size();
            users.addAll(newUsers);
            this.notifyItemRangeInserted(startInsertPosition, newUsers.size());
        }

        void addItem(User user) {
            users.add(user);
            this.notifyItemInserted(users.size() - 1);
        }

        void removeItem(User user) {
            int position = users.indexOf(user);
            users.remove(position);
            this.notifyItemRemoved(position);
        }

        @NonNull
        @Override
        public FollowerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(FollowerFragment.this.getContext());
            View view;

            if(isLoading) {
                view =layoutInflater.inflate(R.layout.loading_row, parent, false);

            } else {
                view = layoutInflater.inflate(R.layout.user_row, parent, false);
            }

            return new FollowerHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FollowerHolder followerHolder, int position) {
            if(!isLoading) {
                followerHolder.bindUser(users.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return users.size();
        }

        @Override
        public int getItemViewType(int position) {
            return (position == users.size() - 1 && isLoading) ? LOADING_DATA_VIEW : ITEM_VIEW;
        }


        void loadMoreItems() {
            isLoading = true;
            addLoadingFooter();

            GetFollowerTask getFollowerTask = new GetFollowerTask(presenter, this);
            FollowerRequest request = new FollowerRequest(presenter.getUserShown(), PAGE_SIZE, lastFollowee);
            getFollowerTask.execute(request);
        }

        @Override
        public void followersRetrieved(FollowerResponse followerResponse) {
            List<User> followees = followerResponse.getFollowees();

            lastFollowee = (followees.size() > 0) ? followees.get(followees.size() -1) : null;
            hasMorePages = followerResponse.hasMorePages();

            isLoading = false;
            removeLoadingFooter();
            followerRecyclerViewAdapter.addItems(followees);
        }

        private void addLoadingFooter() {
            addItem(new User("Dummy", "User", ""));
        }

        private void removeLoadingFooter() {
            removeItem(users.get(users.size() - 1));
        }
    }

    private class FollowRecyclerViewPaginationScrollListener extends RecyclerView.OnScrollListener {

        private final LinearLayoutManager layoutManager;

        FollowRecyclerViewPaginationScrollListener(LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        @Override
        public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!followerRecyclerViewAdapter.isLoading && followerRecyclerViewAdapter.hasMorePages) {
                if ((visibleItemCount + firstVisibleItemPosition) >=
                        totalItemCount && firstVisibleItemPosition >= 0) {
                    followerRecyclerViewAdapter.loadMoreItems();
                }
            }
        }
    }
}
