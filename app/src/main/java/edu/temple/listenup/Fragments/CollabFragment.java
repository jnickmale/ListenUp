package edu.temple.listenup.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

import edu.temple.listenup.PreferencesUtils;
import edu.temple.listenup.R;
import edu.temple.listenup.SpotifyAPIManager;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Playlist;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static edu.temple.listenup.SpotifyAPIManager.getService;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollabFragment extends Fragment {

    private String userID, userName, withUserID, withUserName, matchID;
    private DatabaseReference database;


    public CollabFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        userID = args.getString("userID");
        userName = args.getString("userName");
        withUserID = args.getString("withUserID");
        withUserName = args.getString("withUserName");
        matchID = args.getString("matchID");

        database = FirebaseDatabase.getInstance().getReference().child("matches");
    }

    public CollabFragment getInstance(String userID, String userName, String withUserID, String withUserName, String matchID){
        CollabFragment collabFragment = new CollabFragment();
        Bundle args = new Bundle();
        args.putString("userID", userID);
        args.putString("userName", userName);
        args.putString("withUserID", withUserID);
        args.putString("withUserName", withUserName);
        args.putString("matchID", matchID);
        collabFragment.setArguments(args);

        return collabFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_collab, container, false);
    }

    public void createPlaylist(){
        final DatabaseReference ref = database.child(matchID).getRef();
        ref.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("playlistID")){
                    SpotifyAPIManager.setMyAccessToken(PreferencesUtils.getMyAccessToken(getActivity().getApplicationContext()));
                    final SpotifyService spotifyService = SpotifyAPIManager.getService();




                    HashMap map = new HashMap<String, Object>();
                    map.put("collaborative", true);
                    map.put("description", "Your playlist with" + withUserName);
                    //map.put("tracks", trackURIS);

                    spotifyService.createPlaylist(userID, map, new Callback<Playlist>() {
                        @Override
                        public void success(Playlist playlist, Response response) {
                            final String playlistID = playlist.id;
                            //write the playlist id to firebase
                            ref.child(userID).child("playlistID").setValue(playlistID);

                            //add songs to the playlist
                            final JSONArray trackURIS = new JSONArray();
                            //read common artists and add tracks
                            ref.child("artistIDs").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    int songsAdded = 0;
                                    int numArtists = (int)dataSnapshot.getChildrenCount();
                                    for(DataSnapshot ds: dataSnapshot.getChildren()) {
                                        Tracks topTracks = spotifyService.getArtistTopTrack(ds.getValue().toString(), "US");
                                        int songsFromArtistAdded;
                                        for(songsFromArtistAdded = 0; songsFromArtistAdded < Math.ceil(25 / numArtists);songsFromArtistAdded++){
                                                trackURIS.put(topTracks.tracks.get(songsFromArtistAdded).uri);
                                                songsAdded++;
                                                songsFromArtistAdded++;
                                        }
                                        if(songsAdded >= 25){
                                            break;
                                        }
                                    }
                                    HashMap map = new HashMap<String, Object>();
                                    map.put("uris", trackURIS);
                                    spotifyService.addTracksToPlaylist(userID, playlistID, null, map);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }

                        @Override
                        public void failure(RetrofitError error) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
