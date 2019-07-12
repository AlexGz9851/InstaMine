package com.example.instamine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.parse.ParseException;

import java.util.List;

public class BioAdapter  extends
            RecyclerView.Adapter<BioAdapter.ViewHolder> {
        private List<Post> posts;
        private Context context;
        private ImageView bioPost;

        // Pass in the contact array into the constructor
        public BioAdapter(List<Post> ps) {
            posts = ps;
        }

        @Override
        public BioAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            // Inflate the custom layout
            View bioPostView = inflater.inflate(R.layout.item_photo, parent, false);
            // Return a new holder instance
            ViewHolder vh = new ViewHolder(bioPostView);
            return vh;
        }

        // Involves populating data into the item through holder
        @Override
        public void onBindViewHolder(BioAdapter.ViewHolder vh, int i) {
            // Get the data model based on position
            Post p = posts.get(i);
            try {
                Glide.with(context)
                    .load(p.getImage().getFile())
                    .into(vh.photo);
            }catch (ParseException e){
               e.printStackTrace();
        }
    }

        @Override
        public int getItemCount() { return posts.size(); }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView photo;

            public ViewHolder(View v) {
                // Stores the itemView in a public final member variable that can be used
                // to access the context from any ViewHolder instance.
                super(v);
                photo = (ImageView) v.findViewById(R.id.iv_uniquePhoto);
            }
        }

    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }
    }