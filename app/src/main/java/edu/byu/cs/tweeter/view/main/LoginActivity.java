package edu.byu.cs.tweeter.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.net.request.LoginRequest;
import edu.byu.cs.tweeter.net.request.SignUpRequest;
import edu.byu.cs.tweeter.net.response.LoginResponse;
import edu.byu.cs.tweeter.presenter.LoginPresenter;
import edu.byu.cs.tweeter.presenter.MainPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.GetLoginTask;
import edu.byu.cs.tweeter.view.asyncTasks.GetSignUpTask;

public class LoginActivity extends AppCompatActivity implements LoginPresenter.View, GetSignUpTask.GetLoginObserver, GetLoginTask.GetLoginObserver {

    private LoginPresenter presenter;

    private EditText signUpFirst;
    private EditText signUpLast;
    private EditText signUpHandle;
    private EditText signUpPassword;
    private EditText signUpURL;

    private EditText handleInput;
    private EditText passwordInput;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        presenter = new LoginPresenter(this);

        TextView signUpPrompt = findViewById(R.id.signUpPrompt);
        final CardView signInCard = findViewById(R.id.signInCard);
        signInCard.setVisibility(View.VISIBLE);
        final CardView signUpCard = findViewById(R.id.signUpCard);
        Button signInButton = findViewById(R.id.signInButton);
        Button signUpButton = findViewById(R.id.signUpButton);

        signUpFirst = findViewById(R.id.firstNameInput);
        signUpLast = findViewById(R.id.lastNameInput);
        signUpHandle = findViewById(R.id.signUpHandleInput);
        signUpPassword = findViewById(R.id.signUpPasswordInput);
        signUpURL = findViewById(R.id.signUpImageURL);

        handleInput = findViewById(R.id.handleInput);
        passwordInput = findViewById(R.id.passwordInput);

        signUpPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInCard.setVisibility(View.GONE);
                signUpCard.setVisibility(View.VISIBLE);
            }
        });
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn(view);
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp(view);
            }
        });

    }

    private void signIn(View v) {
        if (userInfoIsValid() && authenticated()) {
            GetLoginTask getLoginTask = new GetLoginTask(presenter,LoginActivity.this);
            LoginRequest request = new LoginRequest(
                    passwordInput.getText().toString(),
                    handleInput.getText().toString()
            );

            getLoginTask.execute(request);
        }

    }

    private void signUp(View v) {
        if (userInfoIsValid() && !alreadySignedUp()) {
//            switchToMainActivity(v);
            GetSignUpTask getSignUpTask = new GetSignUpTask(presenter,LoginActivity.this);
            SignUpRequest request = new SignUpRequest(
                    signUpFirst.getText().toString(),
                    signUpLast.getText().toString(),
                    signUpHandle.getText().toString(),
                    signUpPassword.getText().toString(),
                    signUpURL.getText().toString()
            );
            getSignUpTask.execute(request);
        }
    }

    private  boolean authenticated() {
        return true;
    }

    private boolean userInfoIsValid() {
        return true;
    }

    private boolean alreadySignedUp() {
        return false;
    }

    private void switchToMainActivity() {
//        Toast.makeText(v.getContext(),"TODO: Actually sign in/sign up",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void userLoginResponded(LoginResponse r) {
        if (r.isAuthentcated() && r.getUserSignedIn() != null) {
            switchToMainActivity();
        }
    }
}