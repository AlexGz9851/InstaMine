package com.example.instamine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class BioFragment extends Fragment {
    public static int NUMBER_OF_RESULTS= 21;// 3 divides 21 (grid)
    private static final String KEY_USERNAME = "username";
    private ParseUser currentUser;
    private ImageView ivProfilePic;
    private TextView tvEmail, tvNumberOfPosts,tvUsername,tvDescription;
    private int radius = 1000;
    private int margin = 10;
    private ArrayList<Post> bioPosts;
    private BioAdapter adapter;
    private RecyclerView rvBio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_bio, parent,false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        currentUser = ParseUser.getCurrentUser();

        ivProfilePic=(ImageView) view.findViewById(R.id.iv_profile_pic);
        tvEmail=(TextView) view.findViewById(R.id.tv_email);
        tvNumberOfPosts=(TextView) view.findViewById(R.id.tv_number_of_posts);// TODO QUERY
        tvUsername=(TextView) view.findViewById(R.id.tv_username);
        tvDescription=(TextView) view.findViewById(R.id.tv_description);

        rvBio = (RecyclerView) view.findViewById(R.id.rvBio);

        bioPosts = new ArrayList<Post>();
        // Create adapter passing in the sample user data
        adapter = new BioAdapter(bioPosts);
        // Attach the adapter to the recyclerview to populate items
        rvBio.setAdapter(adapter);
        // Set layout manager to position the items
        rvBio.setLayoutManager(new GridLayoutManager(getContext(),3));

        //setting values bio
        try {
            ParseFile profilePhoto = currentUser.getParseFile("ProfilePhoto");
            Glide.with(getContext())
                    .load(profilePhoto.getFile())
                    .override(70,70)
                    .bitmapTransform(new RoundedCornersTransformation(getContext(), radius, margin))
                    .into(ivProfilePic);
        }catch (ParseException e){
            e.printStackTrace();
        }
        tvEmail.setText(currentUser.getEmail());
        tvUsername.setText(currentUser.getUsername());
        tvDescription.setText(currentUser.getString("Description"));
        queryPostsBio();
    }

    private void queryPostsBio(){
        final ParseQuery<Post> bioPostQ = new ParseQuery<Post>(Post.class);
        //Defining constraints
        bioPostQ.setLimit(NUMBER_OF_RESULTS); // limit to at most N results
        bioPostQ.include(Post.KEY_USER);
        bioPostQ.whereEqualTo("user", ParseUser.getCurrentUser().getObjectId()); // only photos of user. WHERE query
        //executing query in a bkg thread.
        bioPostQ.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> postsAnswer, ParseException e) {
                if(e!=null){
                    Log.e("HomeActivity", "Unable to query from the server");
                    e.printStackTrace();
                }else{
                    // Remember to CLEAR OUT old items before appending in the new ones
                    adapter.clear();
                    //populate timeline with new posts.
                    ArrayList<Post> mPost = BioFragment.this.bioPosts;
                    for(int i = 0 ;i<postsAnswer.size();i++){
                        mPost.add(0,postsAnswer.get(i));
                        adapter.notifyItemInserted(0);
                        rvBio.scrollToPosition(0);
                    }
                }

            }
        });
    }
}
