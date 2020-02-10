package edu.byu.cs.tweeter.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.presenter.MainPresenter;

public class LoginActivity extends AppCompatActivity implements MainPresenter.View {

    private MainPresenter presenter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        presenter = new MainPresenter(this);

        TextView signUpPrompt = findViewById(R.id.signUpPrompt);
        final CardView signInCard = findViewById(R.id.signInCard);
        signInCard.setVisibility(View.VISIBLE);
        final CardView signUpCard = findViewById(R.id.signUpCard);
        Button signInButton = findViewById(R.id.signInButton);
        Button signUpButton = findViewById(R.id.signUpButton);

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
                switchToMainActivity(view);
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToMainActivity(view);
            }
        });

    }

    private void switchToMainActivity(View v) {
        Toast.makeText(v.getContext(),"TODO: Actually sign in/sign up",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}