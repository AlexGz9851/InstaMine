package com.example.instamine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LogInActivity extends AppCompatActivity {

    private EditText password_et;
    private TextView username_et;
    private Button login_btn, signUp_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_btn = findViewById(R.id.btn_login);
        username_et = findViewById(R.id.et_username);
        password_et = findViewById(R.id.et_password);
        signUp_btn = findViewById(R.id.btn_signUp);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = username_et.getText().toString();
                final String password = password_et.getText().toString();
                login(username, password );
            }
        });

        signUp_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Intent i = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(i);
                finish();
            }
        });

    }


    private void login( String username, String password ){
        ParseUser.logInInBackground(username, password, new LogInCallback(){

            @Override
            public void done(ParseUser user, ParseException e) {
                if(e==null){
                    Log.d("LoginActivity","Login successful");
                    //TODO if intent, make sure finish this activity, user cannot have saved their info.
                }else{
                    Log.e("LoginActivity", "Login Failure");
                    e.printStackTrace();
                }
            }
        });

    }
}
