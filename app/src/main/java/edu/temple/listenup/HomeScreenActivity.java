package edu.temple.listenup;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import edu.temple.listenup.Fragments.MatchesFragment;
import edu.temple.listenup.Fragments.PartnerListFragment;
import edu.temple.listenup.Fragments.Profile;
import edu.temple.listenup.Fragments.UserSettingsFragment;

public class HomeScreenActivity extends AppCompatActivity {

    private String username;

    //the three fragments the user will navigate through
    Fragment userSettingsFragment = new UserSettingsFragment();
    Fragment chatList = new MatchesFragment();
    Fragment partnerList = new PartnerListFragment();
    Fragment profile = new Profile();
    Bundle bundle;
    FragmentManager fragmentManager = getFragmentManager();

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
                    profile.setArguments(bundle);
                        fragmentTransaction.replace(R.id.attachTo,profile).commit();
                    return true;
                //partner list
                //middle
                case R.id.navigation_dashboard:
                    //check if frag already attached
                    fragmentTransaction.replace(R.id.attachTo,partnerList).commit();
                    return true;
                //partner chat list
                //right
                case R.id.navigation_notifications:
                    fragmentTransaction.replace(R.id.attachTo,chatList).commit();
                    return true;
            }

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        SharedPreferences getInfo = getSharedPreferences(Constants.PREFERENCES, 0);
        username = getInfo.getString(Constants.DISPLAY_NAME, null);
        Log.i("HomeScreenActivity", "username from shared prefs: " + username);
        bundle = new Bundle();

        bundle.putString("display_name", username);



        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //check if this is the first time the user has gotten to the application
        FloatingActionButton profileFAB = findViewById(R.id.profileFab);

        SharedPreferences settingsCheck = getSharedPreferences(Constants.FIRST_TIME_USE,0);

        if (settingsCheck.getBoolean("user_first_time",true) ){
            //user enters if it is the first time getting to this point of the application
            //redirect user to settings tab and activate fab to set items
            navigation.setSelectedItemId(R.id.navigation_home);
            //activate the settings
            //todo: check if this works!!!! :/
            if (profileFAB != null) {
                profileFAB.setActivated(true);
            }

            settingsCheck.edit().putBoolean("user_first_time",false).apply();

        } else{
            navigation.setSelectedItemId(R.id.navigation_dashboard);
        }
    }
}