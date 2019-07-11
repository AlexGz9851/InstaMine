package com.example.instamine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.parse.ParseUser;

public class HomeActivity extends AppCompatActivity {


    public static final String TAG = HomeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Always we start this activity, we want TimeLineFragment as initial content

        // Begin the transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.home_central_placeholder, new HomeTimeLineFragment());
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        ft.commit();

    }


    public void logOut(View v){
        ParseUser.logOut();
        Intent i = new Intent(HomeActivity.this, LogInActivity.class);
        startActivity(i);
        finish();
    }


}