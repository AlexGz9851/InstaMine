package com.example.instamine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.parse.ParseUser;

public class HomeActivity extends AppCompatActivity {

    ParseUser user;
    TextView dummyTx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        user = ParseUser.getCurrentUser();

    }

    public void logOut(View v){
        ParseUser.logOut();
        Intent i = new Intent(HomeActivity.this, LogInActivity.class);
        startActivity(i);
        finish();
    }
}

