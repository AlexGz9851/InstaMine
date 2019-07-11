package com.example.instamine;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class HomeTimeLineFragment extends Fragment {
    public static final int NUMBER_OF_RESULTS = 20; // Maximum number of results.
    public static final String TAG = HomeTimeLineFragment.class.getSimpleName();

    private SwipeRefreshLayout swipeContainer;
    ParseUser user;
    PostAdapter postAdapter;
    ArrayList<Post> posts;
    RecyclerView rvPosts;

    public HomeTimeLineFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_time_line, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        //find user to make the call to the server.
        user = ParseUser.getCurrentUser();

        //find the Recyclerview
        rvPosts = (RecyclerView) view.findViewById(R.id.rvPosts);
        //init the arraylist
        posts = new ArrayList<Post>();
        // construct the adapter
        postAdapter = new PostAdapter(posts);
        // RecyclerView setup (iayout manager, use adapter)
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        // set the adapter
        rvPosts.setAdapter(postAdapter);

        swipeContainer = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                //TODO CALL QUERY.
                queryPosts();
            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        queryPosts();
    }



    private void queryPosts(){
        final ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
        //Defining constraints
        postQuery.setLimit(NUMBER_OF_RESULTS); // limit to at most N results
        postQuery.include(Post.KEY_USER); // including object ( not just reference) of user. ( natural join implicit)
        //executing query in a bkg thread.
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> postsAnswer, ParseException e) {
                if(e!=null){
                    Log.e("HomeActivity", "Unable to query from the server");
                    e.printStackTrace();
                }else{
                    // Remember to CLEAR OUT old items before appending in the new ones
                    postAdapter.clear();
                    //populate timeline with new posts.
                    ArrayList<Post> mPost = HomeTimeLineFragment.this.posts;
                    for(int i = 0 ;i<postsAnswer.size();i++){
                        mPost.add(0,postsAnswer.get(i));
                        postAdapter.notifyItemInserted(0);
                        rvPosts.scrollToPosition(0);
                    }
                    // Now we call setRefreshing(false) to signal refresh has finished
                    swipeContainer.setRefreshing(false);

                }

            }
        });
    }
    public void scrollToStart(){
        rvPosts.scrollToPosition(0);
    }
}
