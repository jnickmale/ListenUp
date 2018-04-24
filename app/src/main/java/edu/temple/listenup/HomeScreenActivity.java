package edu.temple.listenup;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import java.util.List;

import edu.temple.listenup.Fragments.MatchesFragment;
import edu.temple.listenup.Fragments.PartnerListFragment;
import edu.temple.listenup.Fragments.ProfileFragment;
import edu.temple.listenup.Fragments.UserSettingsFragment;

public class HomeScreenActivity extends AppCompatActivity {

    private String username, profileInfo;

    List picInfo;

    //the three fragments the user will navigate through
    Fragment userSettingsFragment = new UserSettingsFragment();
    Fragment chatList = new MatchesFragment();
    Fragment partnerList = new PartnerListFragment();
    Fragment profile = new ProfileFragment();
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
                    if(profile.getArguments() == null) {
                        profile.setArguments(bundle);
                    }
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



            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.actionbar);

        username = PreferencesUtils.getMyDisplayName(getApplicationContext());
        profileInfo = PreferencesUtils.getMyPicInfo(getApplicationContext());
        Log.i("HomeScreenActivity", "from shared prefs: " + profileInfo);

        bundle = new Bundle();
        bundle.putString("display_name", username);
        bundle.putString("pic_url", profileInfo);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //check if this is the first time the user has gotten to the application
        FloatingActionButton profileFAB = findViewById(R.id.profileFab);

        SharedPreferences settingsCheck = getSharedPreferences(PreferencesUtils.FIRST_TIME_USE,0);

        if (settingsCheck.getBoolean("user_first_time",true) ){
            //user enters if it is the first time getting to this point of the application
            //redirect user to settings tab and activate fab to set items
            navigation.setSelectedItemId(R.id.navigation_home);
            //activate the settings
            if (profileFAB != null) {
                profileFAB.setActivated(true);
            }

            settingsCheck.edit().putBoolean("user_first_time", false).apply();

        } else{
            navigation.setSelectedItemId(R.id.navigation_dashboard);
        }
    }
}