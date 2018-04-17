package edu.temple.listenup.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import edu.temple.listenup.Objects.Toasty;
import edu.temple.listenup.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {


    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        FloatingActionButton fab = view.findViewById(R.id.profileFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Toasty toasty = new Toasty(getActivity());
                toasty.show();
                Button button= toasty.findViewById(R.id.btn_signup);
                button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                //get users settings
                EditText userAge = toasty.findViewById(R.id.input_age);
                if (userAge.getText().toString() != "") {
                    //int age = Integer.valueOf(userAge.getText().toString());
                }

                EditText radius =  toasty.findViewById(R.id.input_radius);
                if (radius.getText().toString() != "") {
                    //int userRadius = Integer.valueOf(radius.getText().toString());
                }
                //destroy this custom toast
                toasty.dismiss();

            }
        });
            }
        });

        return view;

    }

}
