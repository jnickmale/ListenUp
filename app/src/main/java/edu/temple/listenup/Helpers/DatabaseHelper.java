package edu.temple.listenup.Helpers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

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
    private static PreferencesUtils preferencesUtils = new PreferencesUtils();

    public static void setMyLongitude(String ID, double lon) {
        myDatabase.child("users").child(ID).child("lon").setValue(lon);
    }

    public static void setMyLatitude(String ID, double lat) {
        myDatabase.child("users").child(ID).child("lat").setValue(lat);
    }

    public static void setMyUserID(User user, String ID) {
        myDatabase.child("users").child(ID).setValue(user);
    }

    public static void setMyFollowedArtists(String ID, Map<String, String> followedArtists) throws JSONException {
        JSONObject jsonObject = new JSONObject(followedArtists.toString());
        myDatabase.child("users").child(ID).child("followed_artists").setValue(jsonObject);
    }


    public interface DatabaseUsersReceivedListener {
        void onDataReceived(List<User> data);
    }

    public static List<String> getUserArtists(String userId,Context context){
        final List<String> artistList = new ArrayList<>();

        DatabaseReference reference = myDatabase.child("users").child(userId).child("followedArtists");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dsp : dataSnapshot.getChildren()){
                    final String artist;
                    String name = (String) dsp.getValue();
                    artistList.add(name);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return artistList;
    }

    public static List<String> getPartnerArtists(String partnerId,Context context){
        final List<String> artistList = new ArrayList<>();

        DatabaseReference reference = myDatabase.child("users").child(partnerId).child("followedArtists");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               for(DataSnapshot dsp : dataSnapshot.getChildren()){
                   final String artist;
                   String name = (String) dsp.getValue();
                   artistList.add(name);
               }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return artistList;
    }

    public static void getAllUsersWithinRadius(final DatabaseUsersReceivedListener listener, final Context context) {
        final List<User> list = new ArrayList<>();
        DatabaseReference ref = myDatabase.child("users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    final String name, lon, lat, id;
                    final double distance;
                    final User newUser = new User();

                    //create Map object to take in dataSnapshot from Firebase
                    Map singleUser = (Map) dsp.getValue();

                    //get values from Firebase and save to our variables
                    name = String.valueOf(singleUser.get("displayName"));
                    lon = String.valueOf(singleUser.get("lon"));
                    lat = String.valueOf(singleUser.get("lat"));
                    id = String.valueOf(singleUser.get("id"));
                    if (!(preferencesUtils.getMySpotifyUserID(context).equals(id))) {

                        //save values into our User object
                        newUser.setDisplayName(name);
                        newUser.setLat(Double.valueOf(lat));
                        newUser.setLon(Double.valueOf(lon));
                        newUser.setID(id);

                        //set distance from user in the User object
                        newUser.setDistanceFromUser();
                        distance = newUser.getDistance();
                        double radius;

                        //checks if new user is within the radius specified by current user
                        if (PreferencesUtils.getMyRadius(context) == null) {
                            radius = 1.0;
                        } else {
                            radius = Double.valueOf(PreferencesUtils.getMyRadius(context));
                        }
                        if (distance < radius) {

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

                            list.add(newUser); //add result into array list
                        }
                    }

                    listener.onDataReceived(list); //send list to any receivers (e.g. PartnerListFragment)
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    //function to get all users from the database, probably won't need this
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

                    list.add(newUser); //add result into array list

                }

                listener.onDataReceived(list);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
