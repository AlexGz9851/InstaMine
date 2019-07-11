package com.example.instamine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.parse.ParseUser;

public class HomeActivity extends AppCompatActivity {


    public static final String TAG = HomeActivity.class.getSimpleName();



    private  Fragment currentCentralFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // Always we start this activity, we want TimeLineFragment as initial content
        replaceFragment(new HomeTimeLineFragment());

    }

    public void replaceFragment(Fragment f){
        // Begin the transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        if(currentCentralFragment == null || !currentCentralFragment.getClass().equals(f.getClass())){
            // fragments are from different classes,
            // different fragments, must change fragment
            currentCentralFragment = f;
            ft.replace(R.id.home_central_placeholder, f);
            // or ft.add(R.id.your_placeholder, new FooFragment());
            // Complete the changes added above
            ft.commit();
        }else{
            if(currentCentralFragment!= null || currentCentralFragment.getClass().equals(HomeTimeLineFragment.class)){
                ((HomeTimeLineFragment)currentCentralFragment).scrollToStart();
            }
        }
    }


    public void logOut(View v){
        ParseUser.logOut();
        Intent i = new Intent(HomeActivity.this, LogInActivity.class);
        startActivity(i);
        finish();
    }

    public Fragment getCurrentCentralFragment() {
        return currentCentralFragment;
    }

    public void setCurrentCentralFragment(Fragment currentCentralFragment) {
        this.currentCentralFragment = currentCentralFragment;
    }


}