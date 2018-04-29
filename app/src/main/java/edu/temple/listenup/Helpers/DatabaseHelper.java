package edu.temple.listenup.Helpers;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.temple.listenup.Models.User;
import kaaes.spotify.webapi.android.models.UserPublic;

/**
 * Created by Jessica on 4/26/2018.
 */

public class DatabaseHelper {

    private static List<User> userList = new ArrayList<>();
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

    public interface DatabaseUsersReceivedListener {
        void onDataReceived(List<User> data);
    }

    public static void getAllUsers(final DatabaseUsersReceivedListener listener) {
        final List<User> list = new ArrayList<>();
        DatabaseReference ref = myDatabase.child("users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    final String name, lon, lat, id;
                    final User newUser = new User();
                    Map singleUser = (Map) dsp.getValue();


                    name = String.valueOf(singleUser.get("displayName"));
                    lon = String.valueOf(singleUser.get("lon"));
                    lat = String.valueOf(singleUser.get("lat"));
                    id = String.valueOf(singleUser.get("id"));

                    newUser.setDisplayName(name);
                    newUser.setLat(Double.valueOf(lat));
                    newUser.setLon(Double.valueOf(lon));
                    newUser.setID(id);

                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            UserPublic user = SpotifyAPIManager.getService().getUser(id);
                            String image;

                            try {
                                image = user.images.get(0).url;
                                Log.i("DatabaseHelperImage", image);
                                newUser.setUserImage(image);

                            } catch (IndexOutOfBoundsException e) {
                                System.out.println("this was the issue");
                            }

                            Log.i("DatabaseCheckService", user.display_name);

                        }
                    });

                    //UserPublic userPublic = new UserPublic();

                    Log.i("DatabaseHelperMap", name);
                    Log.i("DatabaseHelperList", newUser.getDisplayName());
                    list.add(newUser); //add result into array list

                }

                listener.onDataReceived(list);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    /*
    public double getLongitude() {
        return
    }
    */
}
