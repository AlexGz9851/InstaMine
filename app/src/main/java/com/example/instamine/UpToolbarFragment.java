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
    ImageView close, logo, camera;
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

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity)getActivity()).logOut(v);
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), PreviewActivity.class);
                startActivity(i);
            }
        });
        //TODO LINKS A DIFERENTES ACTIVIDADES O FRAGMENTS.


    }

}
