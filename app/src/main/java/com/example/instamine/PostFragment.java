package com.example.instamine;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


public class PostFragment extends Fragment {

    public Post post;
    public ParseUser user;
    Context context;

    TextView tvDescription, tvUsername, tvGeolocalization, tvTimestamp, tvNumberOflikes;
    ImageView ivProfilePhoto, ivPost, ivComment, ivLike;

    int radius, margin;

    public PostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
            radius = 70;
            margin = 10;
            context = getContext();
            //get post
            Bundle bundle = getArguments();
            post = (Post) bundle.getParcelable("post");
            user = post.getUser();

            tvDescription = view.findViewById(R.id.tv_post_description);
            tvGeolocalization = view.findViewById(R.id.tv_geolocalitation);
            tvNumberOflikes = view.findViewById(R.id.tv_number_likes);
            tvTimestamp = view.findViewById(R.id.tv_timestamp);
            tvUsername = view.findViewById(R.id.tv_user_post);

            ivProfilePhoto = view.findViewById(R.id.iv_mini_profile_post);
            ivComment = view.findViewById(R.id.iv_comment_post);
            ivPost = view.findViewById(R.id.iv_image_post);
            ivLike = view.findViewById(R.id.iv_like_post);

            //setting values on post
            tvDescription.setText(post.getDescription());
            tvUsername.setText(user.getUsername());
            tvGeolocalization.setText(post.getGeolocalization());
            //setting timestamp
            tvTimestamp.setText(Utilities.getRelativeTimeAgo(post.getCreatedAt()));
            tvNumberOflikes.setText(String.format("%d Likes", 0));//TODO number of likes, make relation on Database, like --> i++

            //setting images
            try {
                ParseFile profilePhoto = user.getParseFile("ProfilePhoto");
                Glide.with(context)
                    .load(profilePhoto.getFile())
                    .bitmapTransform(new RoundedCornersTransformation(context, radius, margin))
                    .into(ivProfilePhoto);
            }catch (ParseException e){
                e.printStackTrace();
            }

            try {
                Glide.with(context)
                    .load(post.getImage().getFile())
                    .into(ivPost);
            }catch (ParseException e){
                e.printStackTrace();
            }
    }


}
