package com.example.instamine;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class PostAdapter extends  RecyclerView.Adapter<PostAdapter.ViewHolder>{

    private static final String TAG = "TimelineFragment";

    private  List<Post> posts;
    private  Context context;

    //TODO ADAPTER
    public PostAdapter(List<Post> posts){
        this.posts = posts;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View postView = inflater.inflate(R.layout.item_timeline, viewGroup, false);
        ViewHolder vh = new ViewHolder(postView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        //get data according position
        final Post post = this.posts.get(i);

        //populating views according data
        viewHolder.tvDescription.setText(post.getDescription());
        final ParseUser postUser = post.getUser();
        viewHolder.tvUsername.setText(postUser.getUsername());
        viewHolder.tvGeolocalization.setText(post.getGeolocalization());
        viewHolder.tvTimestamp.setText(Utilities.getRelativeTimeAgo(post.getCreatedAt()));

        JSONArray x = post.getUsersWhoLikedPost();
        if(x!=null){
            viewHolder.tvNumberOfLikes.setText(String.format("%d Likes", x.length()));
        }else{
            viewHolder.tvNumberOfLikes.setText(String.format("%d Likes",0));
        }

        ParseFile photo = post.getImage();
        ParseFile profilePhoto = postUser.getParseFile("ProfilePhoto");

        if(!post.isLiked()){
            viewHolder.ivLike.setImageResource(R.drawable.ufi_heart);
        }else{
            viewHolder.ivLike.setImageResource(R.drawable.ufi_heart_active);
        }

        viewHolder.ivPost.setOnClickListener(new View.OnClickListener() {
            //Cambio de actividad a post
            @Override
            public void onClick(View v) {
                PostFragment postFragment= new PostFragment();
                ((HomeActivity) v.getContext()).replaceFragment(postFragment);

                //passing to new activity post
                Bundle bundle = new Bundle();
                bundle.putParcelable("post", post);
                postFragment.setArguments(bundle);

            }
        });

        viewHolder.ivLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//TODO LIKE GIVEN
                if(!post.isLiked()){
                    //LIKED POST
                    post.likePost(ParseUser.getCurrentUser());
                    viewHolder.ivLike.setImageResource(R.drawable.ufi_heart_active);
                    post.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e!=null){e.printStackTrace();}
                        }
                    });
                }else{//UNLIKE POST

                    post.unlikePost(ParseUser.getCurrentUser());
                    viewHolder.ivLike.setImageResource(R.drawable.ufi_heart);
                    post.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e!=null){e.printStackTrace();}
                        }
                    });
                }
                JSONArray x = post.getUsersWhoLikedPost();
                if(x!=null){
                    viewHolder.tvNumberOfLikes.setText(String.format("%d Likes", x.length()));
                }else{
                    viewHolder.tvNumberOfLikes.setText(String.format("%d Likes",0));
                }

            }
        });

        try {
            int radius = 70;
            int margin = 10;
            Glide.with(context)
                    .load(photo.getFile())
                    .into(viewHolder.ivPost);
            Glide.with(context)
                    .load(profilePhoto.getFile())
                    .bitmapTransform(new RoundedCornersTransformation(context, radius, margin))
                    .into(viewHolder.ivProfileUser);
            //TODO ADD IMAGES FROM POST
        }catch (ParseException e){
            Log.e(TAG, "Cant load image", new Throwable());
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() { return this.posts.size(); }

    /* Within the RecyclerView.Adapter class */
    //TODO MAKE THIS WITH SENSE, SCROLL
    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }




    // create the viewHolder
    public static class  ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPost, ivProfileUser, ivLike, ivComment, ivDirectMessage;
        TextView tvUsername, tvGeolocalization, tvDescription, tvTimestamp,tvNumberOfLikes;

        public ViewHolder(View v){
            super(v);

            ivPost = (ImageView) v.findViewById(R.id.iv_image_post);
            ivProfileUser = (ImageView) v.findViewById(R.id.iv_mini_profile_post);
            ivLike = (ImageView) v.findViewById(R.id.iv_like_post);
            ivComment = (ImageView) v.findViewById(R.id.iv_comment_post);
            ivDirectMessage = (ImageView) v.findViewById(R.id.iv_dm_post);
            tvUsername = (TextView) v.findViewById(R.id.tv_user_post);
            tvGeolocalization = (TextView) v.findViewById(R.id.tv_geolocalitation);
            tvDescription = (TextView) v.findViewById(R.id.tv_descripton);
            tvTimestamp = (TextView) v.findViewById(R.id.tv_timestamp_item);
            tvNumberOfLikes = (TextView) v.findViewById(R.id.tv_number_likes_item);



        }
    }


}
