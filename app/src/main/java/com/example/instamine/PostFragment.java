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
import com.parse.SaveCallback;

import org.json.JSONArray;

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

            if(!post.isLiked()){
                ivLike.setImageResource(R.drawable.ufi_heart);
            }else{
                ivLike.setImageResource(R.drawable.ufi_heart_active);
            }

            ivLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//TODO LIKE GIVEN
                    if(!post.isLiked()){
                        //LIKED POST
                        post.likePost(ParseUser.getCurrentUser());
                        ivLike.setImageResource(R.drawable.ufi_heart_active);
                        post.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if(e!=null){e.printStackTrace();}
                            }
                        });
                    }else{//UNLIKE POST

                        post.unlikePost(ParseUser.getCurrentUser());
                        ivLike.setImageResource(R.drawable.ufi_heart);
                        post.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if(e!=null){e.printStackTrace();}
                            }
                        });
                    }
                    JSONArray x = post.getUsersWhoLikedPost();
                    if(x!=null){
                        tvNumberOflikes.setText(String.format("%d Likes", x.length()));
                    }else{
                        tvNumberOflikes.setText(String.format("%d Likes",0));
                    }

                }
            });

            //setting values on post
            tvDescription.setText(post.getDescription());
            tvUsername.setText(user.getUsername());
            tvGeolocalization.setText(post.getGeolocalization());
            //setting timestamp
            tvTimestamp.setText(Utilities.getRelativeTimeAgo(post.getCreatedAt()));
            JSONArray x = post.getUsersWhoLikedPost();
            if(x!=null){
                tvNumberOflikes.setText(String.format("%d Likes", x.length()));
            }else{
                tvNumberOflikes.setText(String.format("%d Likes",0));
            }
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
