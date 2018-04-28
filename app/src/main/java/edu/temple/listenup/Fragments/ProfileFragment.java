package edu.temple.listenup.Fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;


import com.google.firebase.database.DatabaseReference;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;


import edu.temple.listenup.Objects.Toasty;
import edu.temple.listenup.PreferencesUtils;
import edu.temple.listenup.R;

public class ProfileFragment extends Fragment {
    private String city, state, country;
    private double lat, longi;
    private String[] cityInfo;
    private String LOCATION_UPDATED = "action_location_updated";
    private String locationInfo, myID;
    private View view;

    public ProfileFragment() {
        // Required empty public constructor
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == LOCATION_UPDATED) {
                Log.wtf("ProfileFragment", "onReceive reached");
                locationInfo = PreferencesUtils.getMyCityInfo(getActivity());
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Gets string from bundle and sets textview to display user's name
        TextView display = view.findViewById(R.id.UserDisplayName);
        display.setText(getArguments().getString("display_name"));

        // Gets string from bundle and sets textview to user's image
        //ImageView profilePic = view.findViewById(R.id.profilePicture);

        CircularImageView profilePic = (CircularImageView) view.findViewById(R.id.profilePicture);
        Picasso.with(getActivity()).load(getArguments().getString("pic_url")).resize(300, 300).centerCrop().into(profilePic);


        // Gets string from Preferences and sets textview to user location
        locationInfo = PreferencesUtils.getMyCityInfo(getActivity());

        TextView city = view.findViewById(R.id.CityLocation);
        city.setText(locationInfo);

        //onclick floating action button to spawn custom toast
        FloatingActionButton fab = view.findViewById(R.id.profileFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //custom toast
                final Toasty toasty = new Toasty(getActivity());
                //display
                toasty.show();

                //initialize seekbar
                SeekBar seekBar = toasty.findViewById(R.id.seekBar);
                //set max radius to 50
                seekBar.setMax(50);
                final TextView radiusValue = toasty.findViewById(R.id.radius_value);
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        //set textview to current value of seekbar
                        radiusValue.setText(String.valueOf(i));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

                Button button = toasty.findViewById(R.id.btn_signup);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //destroy this custom toast
                        toasty.dismiss();

                    }
                });
            }
        });

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, new IntentFilter(LOCATION_UPDATED));
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);
    }


}
