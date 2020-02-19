package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs.tweeter.net.request.LoginRequest;
import edu.byu.cs.tweeter.net.request.SignUpRequest;
import edu.byu.cs.tweeter.net.response.FeedResponse;
import edu.byu.cs.tweeter.net.response.LoginResponse;
import edu.byu.cs.tweeter.presenter.LoginPresenter;


public class GetSignUpTask extends AsyncTask<SignUpRequest, Void, LoginResponse> {

    private final LoginPresenter presenter;
    private final GetLoginObserver observer;

    public interface GetLoginObserver {
        void userLoginResponded(LoginResponse r);
    }

    public GetSignUpTask(LoginPresenter presenter, GetLoginObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected LoginResponse doInBackground(SignUpRequest... signUpRequests) {
        LoginResponse response = presenter.signUp(signUpRequests[0]);
//        loadImages(response);
        return response;
    }

    @Override
    protected void onPostExecute(LoginResponse signInResponse) {

        if(observer != null) {
            observer.userLoginResponded(signInResponse);
        }
    }
}
