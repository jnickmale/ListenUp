package edu.temple.listenup.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import edu.temple.listenup.R;
/**
 * A simple {@link Fragment} subclass.
 */
public class UserSettingsFragment extends Fragment {
    EditText userAge;
    EditText radius;
    int userRadius;
    int age;
    //if we want a seekbar instead
    //SeekBar radius;


    public UserSettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_settings, container, false);
        /*
        // Inflate the layout for this fragment
        userAge = view.findViewById(R.id.input_age);
       if (userAge.getText().toString() !="") {
           age = Integer.valueOf(userAge.getText().toString());
       }

       radius =  view.findViewById(R.id.input_radius);
        if (radius.getText().toString() !="") {
            userRadius = Integer.valueOf(radius.getText().toString());
        }*/
        return view;
    }

}
