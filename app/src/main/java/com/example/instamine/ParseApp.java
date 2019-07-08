package com.example.instamine;

import android.app.Application;

import com.parse.Parse;

public class ParseApp extends Application {

    @Override
    public void onCreate(){
        super.onCreate();

        final Parse.Configuration conf = new Parse.Configuration.Builder(this)
                .applicationId("zzz12300212")
                .clientKey("alex")
                .server("http://instamine.herokuapp.com/parse")
                .build();

        Parse.initialize(conf);

    }
}
