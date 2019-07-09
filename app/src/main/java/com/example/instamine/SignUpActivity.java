package com.example.instamine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {

    ParseUser user;
    EditText usernameET, passwordET, emailET;
    Button signUp_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        usernameET = findViewById(R.id.et_username);
        passwordET = findViewById(R.id.et_password);
        emailET = findViewById(R.id.et_email);
        signUp_btn = findViewById(R.id.btn_signUp2);

        signUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameET.getText().toString();
                String password = passwordET.getText().toString();
                String email = emailET.getText().toString();
                signUp(username,password,email);
            }
        });

    }


    private void signUp(String username, String password, String email){
        // Create the ParseUser
        user = new ParseUser();
        // Set core properties
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // user was created.
                    // GO TO HOME ACTIVITY
                    // if intent, make sure finish this activity, user cannot have saved their info.
                    Log.d("SignUpActivity", " sign up successful");
                    Intent i = new Intent(SignUpActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();

                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                    //TODO
                    Log.e("SignUpActivity", "Couldnt sign up");
                }
            }
        });
    }
}
