package edu.temple.listenup;

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
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import edu.temple.listenup.Fragments.MatchesFragment;
import edu.temple.listenup.Fragments.PartnerListFragment;
import edu.temple.listenup.Fragments.ProfileFragment;
import edu.temple.listenup.Fragments.UserSettingsFragment;
import kaaes.spotify.webapi.android.models.UserPublic;
import edu.temple.listenup.Helpers.DatabaseHelper;
import edu.temple.listenup.Helpers.PreferencesUtils;
import edu.temple.listenup.Helpers.SpotifyAPIManager;
import edu.temple.listenup.Models.User;

public class HomeScreenActivity extends AppCompatActivity implements LocationListener {

    private String LOCATION_UPDATED = "action_location_updated";

    private String username, profileInfo, myID;
    private ArrayList<String> artistPicsList;

    //the three fragments the user will navigate through
    private Fragment userSettingsFragment = new UserSettingsFragment();
    private Fragment chatList = new MatchesFragment();
    private Fragment partnerList = new PartnerListFragment();
    private Fragment profile = new ProfileFragment();

    private FragmentManager fragmentManager = getFragmentManager();

    private Bundle bundle;

    private Location location;
    public static double lat, longi;

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

        //changes the action bar to a custom layout -- our logo
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);

        //get username and user pic from shared preferences
        username = PreferencesUtils.getMyDisplayName(getApplicationContext());
        profileInfo = PreferencesUtils.getMyPicInfo(getApplicationContext());
        myID = PreferencesUtils.getMySpotifyUserID(getApplicationContext());

        artistPicsList = getListOfArtistImages(PreferencesUtils.getMyFollowedArtistsAsMap(getApplicationContext()));

        Log.i("HomeScreenActivity", "from shared prefs: " + profileInfo);

        //send user info to fragments
        bundle = new Bundle();
        bundle.putString("display_name", username);
        bundle.putString("pic_url", profileInfo);
        bundle.putStringArrayList("artists_pics", artistPicsList);

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
            navigation.setSelectedItemId(R.id.navigation_home);
        }


        //location stuff
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        //checks to make sure that user gives permission to use GPS
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            return;
        }

        Log.wtf("HomeScreenActivity", "Reaches this line.");

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
        updateLocationInPreferences(location);

        String city, state, country;

        try {

            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(lat, longi, 1);


            if (addresses.size() > 0) {
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

            if (lat != 0 && longi != 0) {
                DatabaseHelper.setMyLatitude(myID, lat);
                DatabaseHelper.setMyLongitude(myID, longi);
            } else {
                Toast.makeText(this, "Not working", Toast.LENGTH_SHORT).show();
            }


        }
    }

    public void loadUserImageIntoView(String userID, View into){
        final String ID = userID;
        final View intoFinal = into;
        AsyncTask async = new AsyncTask() {
            private String imageURL;
            @Override
            protected Object doInBackground(Object[] objects) {
                UserPublic userPublic = SpotifyAPIManager.getService().getUser(ID);
                try {
                    imageURL = userPublic.images.get(0).url;

                } catch (IndexOutOfBoundsException e) {
                    System.out.println("this was the issue");
                }
                return imageURL;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                Picasso.with(HomeScreenActivity.this).load(imageURL).into((ImageView)intoFinal);
            }
        };
        async.execute();
    }

    private void updateLocationInPreferences(Location location) {
            PreferencesUtils.setMyLongitudeLatitude(location.getLongitude(), location.getLatitude(), getApplicationContext());
    }

    private ArrayList<String> getListOfArtistImages(Map<String, String> followedArtists) {
        final ArrayList<String> followed = new ArrayList<>();

        for (final String value : followedArtists.values()) {
            final String[] image = new String[1];
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    image[0] = SpotifyAPIManager.getArtistImage(value);
                    followed.add(image[0]);
                }
            });
        }

        return followed;
    }

}