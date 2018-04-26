package edu.temple.listenup;

import android.*;
import android.Manifest;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import edu.temple.listenup.Fragments.MatchesFragment;
import edu.temple.listenup.Fragments.PartnerListFragment;
import edu.temple.listenup.Fragments.ProfileFragment;
import edu.temple.listenup.Fragments.UserSettingsFragment;

public class HomeScreenActivity extends AppCompatActivity implements LocationListener {

    private String LOCATION_UPDATED = "action_location_updated";

    private String username, profileInfo, myID;
    List picInfo;

    //the three fragments the user will navigate through
    private Fragment userSettingsFragment = new UserSettingsFragment();
    private Fragment chatList = new MatchesFragment();
    private Fragment partnerList = new PartnerListFragment();
    private Fragment profile = new ProfileFragment();

    private FragmentManager fragmentManager = getFragmentManager();

    private Bundle bundle;

    private Location location;
    private double lat, longi;

    private DatabaseReference myDatabase;

    //cycle through the fragments
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                //settings
                //left
                case R.id.navigation_home:
                    if (profile.getArguments() == null) {
                        profile.setArguments(bundle);
                    }
                    fragmentTransaction.replace(R.id.attachTo, profile).commit();
                    return true;
                //partner list
                //middle
                case R.id.navigation_dashboard:
                    //check if frag already attached
                    fragmentTransaction.replace(R.id.attachTo, partnerList).commit();
                    return true;
                //partner chat list
                //right
                case R.id.navigation_matches:
                    fragmentTransaction.replace(R.id.attachTo, chatList).commit();
                    return true;
            }

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        //get database reference, will be used to update location information
        myDatabase = FirebaseDatabase.getInstance().getReference();


        //changes the action bar to a custom layout -- our logo
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);

        //get username and user pic from shared preferences
        username = PreferencesUtils.getMyDisplayName(getApplicationContext());
        profileInfo = PreferencesUtils.getMyPicInfo(getApplicationContext());
        myID = PreferencesUtils.getMySpotifyUserID(getApplicationContext());

        Log.i("HomeScreenActivity", "from shared prefs: " + profileInfo);

        //send user info to fragments
        bundle = new Bundle();
        bundle.putString("display_name", username);
        bundle.putString("pic_url", profileInfo);

        //set bottom navigation stuff
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //check if this is the first time the user has gotten to the application
        FloatingActionButton profileFAB = findViewById(R.id.profileFab);

        SharedPreferences settingsCheck = getSharedPreferences(PreferencesUtils.FIRST_TIME_USE, 0);

        if (settingsCheck.getBoolean("user_first_time", true)) {
            //user enters if it is the first time getting to this point of the application
            //redirect user to settings tab and activate fab to set items
            navigation.setSelectedItemId(R.id.navigation_home);
            //activate the settings
            //todo: check if this works!!!! :/
            if (profileFAB != null) {
                profileFAB.setActivated(true);
            }

            settingsCheck.edit().putBoolean("user_first_time", false).apply();

        } else {
            navigation.setSelectedItemId(R.id.navigation_dashboard);
        }


        //location stuff
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        //checks to make sure that user gives permission to use GPS
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            return;
        }


        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 10, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 10, this);

        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0: {
                if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.i("HomeScreenActivity", "Permission denied.");
                } else {
                    Log.i("HomeScreenActivity", "Permission granted.");
                }
            }
            return;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        updateLocationInDatabase(location);
        String city, state, country;

        try {

            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(lat, longi, 1);

            String[] cityInfo = new String[3];

            if (addresses.size() > 0) {
                //cityInfo[0] = addresses.get(0).getLocality(); //get city location
                //cityInfo[1] = addresses.get(0).getAdminArea(); //get state location
                //cityInfo[2] = addresses.get(0).getCountryName(); //get country name

                city = addresses.get(0).getLocality(); //get city location
                state = addresses.get(0).getAdminArea(); //get state location
                country = addresses.get(0).getCountryName(); //get country name

                PreferencesUtils.setMyCityInfo(city + ", " + state + ", " + country, getApplicationContext());
            }


               Intent intent = new Intent(LOCATION_UPDATED);

                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

                Log.i("HomeScreenActivity", addresses.get(0).getLocality());
                Log.i("HomeScreenActivity", addresses.get(0).getAdminArea());
                Log.i("HomeScreenActivity", addresses.get(0).getCountryName());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void updateLocationInDatabase(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            longi = location.getLongitude();

            Log.i("HomeScreenActivity", "longitude: " + longi + " latitude: " + lat);
            myDatabase.child("users").child(myID).child("lat").setValue(lat);
            myDatabase.child("users").child(myID).child("lon").setValue(longi);

        }
    }
}