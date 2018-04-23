package edu.temple.listenup;

import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.SpotifyPlayer;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;

/**
 * Created by kingJ on 4/20/2018.
 */

public class SpotifyServiceApiHepler implements SpotifyPlayer.NotificationCallback, ConnectionStateCallback {



    private FirebaseAuth mAuth;
    // Request code will be used to verify if result comes from the login activity. Can be set to any integer.
    private static final int REQUEST_CODE = 1337;
    private static final String CLIENT_ID = "35c44a4ac64340ee951b71b2308ca072";//you receive the client id from the developer dashbored
    private Player mPlayer;
    private String myAccessToken;
    private DatabaseReference myDatabase;

    private SpotifyApi api;
    private SpotifyService service;
    private SharedPreferences sharedPref;

    // TODO: Replace with your redirect URI
    private static final String REDIRECT_URI = "listenup://callback";// test URI so spotify knows what app to send the info back to

    private AuthenticationResponse response;

    //test
    @Override
    public void onLoggedIn() {

    }

    @Override
    public void onLoggedOut() {

    }

    @Override
    public void onLoginFailed(Error error) {

    }

    @Override
    public void onTemporaryError() {

    }

    @Override
    public void onConnectionMessage(String s) {

    }

    @Override
    public void onPlaybackEvent(PlayerEvent playerEvent) {

    }

    @Override
    public void onPlaybackError(Error error) {

    }
}
