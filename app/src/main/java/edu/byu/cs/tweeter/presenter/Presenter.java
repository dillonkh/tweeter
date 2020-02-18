package edu.byu.cs.tweeter.presenter;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.services.LoginService;
import edu.byu.cs.tweeter.model.services.UserService;

public abstract class Presenter {

//    private User shownUser;

    public User getCurrentUser() {
        return LoginService.getInstance().getCurrentUser();
    }

    public User getUserShown() {
        return UserService.getInstance().getUserShown();
    }
    public void setShownUser(User user) {
        UserService.getInstance().setCurrentUser(user);
    }
}
