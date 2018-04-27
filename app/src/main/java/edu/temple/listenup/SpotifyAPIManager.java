package edu.temple.listenup;

import android.os.AsyncTask;

import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.SpotifyPlayer;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.UserPrivate;

/**
 * Created by kingJ on 4/20/2018.
 */

public class SpotifyAPIManager {

    private static final SpotifyApi api = new SpotifyApi();

    public static SpotifyService getService() {
        return api.getService();
    }

    public static void setMyAccessToken(String token) {
        api.setAccessToken(token);
    }


}
