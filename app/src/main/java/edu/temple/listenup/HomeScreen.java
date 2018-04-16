package edu.temple.listenup;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import edu.temple.listenup.Fragments.MatchesFragment;
import edu.temple.listenup.Fragments.PartnerListFragment;
import edu.temple.listenup.Fragments.UserSettingsFragment;

public class HomeScreen extends AppCompatActivity {

    //the three fragments the user will navigate through
    Fragment userSettingsFragment = new UserSettingsFragment();
    Fragment chatList = new MatchesFragment();
    Fragment partnerList = new PartnerListFragment();

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
                    fragmentTransaction.replace(R.id.attachTo,partnerList).commit();
                    return true;
                //partner list
                //midle
                case R.id.navigation_dashboard:
                    //check if frag already attached
                    fragmentTransaction.add(R.id.attachTo,userSettingsFragment).commit();
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
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
