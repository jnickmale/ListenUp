package edu.temple.listenup;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Jessica on 4/26/2018.
 */

public class DatabaseHelper {

    private static DatabaseReference myDatabase = FirebaseDatabase.getInstance().getReference();

    public static void setMyLongitude(String ID, double lon) {
        myDatabase.child("users").child(ID).child("lon").setValue(lon);
    }

    public static void setMyLatitude(String ID, double lat) {
        myDatabase.child("users").child(ID).child("lat").setValue(lat);
    }

    public static void setMyUserID(User user, String ID) {
        myDatabase.child("users").child(ID).setValue(user);
    }


    /*
    public double getLongitude() {
        return
    }
    */

}
