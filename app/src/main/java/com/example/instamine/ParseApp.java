package com.example.instamine;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApp extends Application {

    @Override
    public void onCreate(){
        super.onCreate();

        ParseObject.registerSubclass(Post.class);
        final Parse.Configuration conf = new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.app_name_server))
                .clientKey(getString(R.string.master_key))
                .server(getString(R.string.server_url))
                .build();

        Parse.initialize(conf);

    }
}
