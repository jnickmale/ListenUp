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

import edu.temple.listenup.Fragments.ChatList;
import edu.temple.listenup.Fragments.PartnerList;
import edu.temple.listenup.Fragments.UserSettingsFragment;

public class HomeScreen extends AppCompatActivity {

    //the three fragments the user will navigate through
    Fragment userSettingsFragment = new UserSettingsFragment();
    Fragment chatList = new ChatList();
    Fragment partnerList = new PartnerList();

    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

    private TextView mTextMessage;

    //todo: replace all fragments before adding
    //cycle through the fragments
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                //settings
                case R.id.navigation_home:
                    fragmentTransaction.add(R.id.attachTo,userSettingsFragment).commit();
                    mTextMessage.setText(R.string.title_home);
                    return true;
                //partner list
                case R.id.navigation_dashboard:
                    fragmentTransaction.replace(R.id.attachTo,partnerList).commit();
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                //partner chat list
                case R.id.navigation_notifications:
                    fragmentTransaction.replace(R.id.attachTo,chatList).commit();
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
