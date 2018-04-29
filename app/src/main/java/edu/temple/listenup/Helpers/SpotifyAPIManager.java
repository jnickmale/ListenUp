package edu.temple.listenup.Helpers;

import android.os.AsyncTask;
import android.util.Log;

import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.SpotifyPlayer;

import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Artists;
import kaaes.spotify.webapi.android.models.ArtistsCursorPager;
import kaaes.spotify.webapi.android.models.CursorPager;
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

    public static void getMyFollowedArtists() {
        List<Artist> artists = getService().getFollowedArtists().artists.items;
        Artist justChecking = artists.get(0);
        Log.wtf("JustChecking", justChecking.name);
    }
}
