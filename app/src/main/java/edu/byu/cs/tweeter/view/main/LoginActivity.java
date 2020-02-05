package edu.byu.cs.tweeter.view.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.presenter.MainPresenter;

public class LoginActivity extends AppCompatActivity implements MainPresenter.View {

    private MainPresenter presenter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        presenter = new MainPresenter(this);



    }
}