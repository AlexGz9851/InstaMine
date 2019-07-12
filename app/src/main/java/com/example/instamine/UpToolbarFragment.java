package com.example.instamine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class UpToolbarFragment extends Fragment {
    // it is called when you want create the layout of the fragment. Dinamically or using an XML made.
    private ImageView close, logo, camera;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_up_toolbar, parent,false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {


        close= v.findViewById(R.id.ivClose);
        logo = v.findViewById(R.id.ivLogo);
        camera = v.findViewById(R.id.ivCamera);

        // Setting listeners to each imageView ( pseudo Btns)
        close.setOnClickListener(new View.OnClickListener() {
            // Log Out.
            @Override
            public void onClick(View v) {
                ((HomeActivity)getActivity()).logOut(v);
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // take a picture, go to preview activity
                Intent i = new Intent(getContext(), PreviewActivity.class);
                startActivity(i);
            }
        });
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // (return  to | or keep on )  hometimeline
                ((HomeActivity)getActivity()).replaceFragment(new HomeTimeLineFragment());
            }
        });
    }

}
