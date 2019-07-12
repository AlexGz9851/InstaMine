package com.example.instamine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ToolbarFragment extends Fragment {

    private ImageView home, profile, add;

    // it is called when you want create the layout of the fragment. Dinamically or using an XML made.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
            return inflater.inflate(R.layout.fragment_toolbar, parent,false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        home = view.findViewById(R.id.icon_home);
        add = view.findViewById(R.id.icon_add);
        profile = view.findViewById(R.id.icon_profile);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // return ( or keep ) to hometimeline
                ((HomeActivity)getActivity()).replaceFragment(new HomeTimeLineFragment());
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // take a picture, go to preview activity
                Intent i = new Intent(getContext(), PreviewActivity.class);
                startActivity(i);
            }
        });


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // send to profile bio fragment
                ((HomeActivity)getActivity()).replaceFragment(new BioFragment());
            }
        });

    }
}
