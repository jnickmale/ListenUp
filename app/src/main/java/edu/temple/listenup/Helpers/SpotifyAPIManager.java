package edu.temple.listenup.Helpers;

import android.os.AsyncTask;
import android.util.Log;

import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.SpotifyPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private static final List<String> artistString = new ArrayList<>();
    private static final Map<String, String> artistList = new HashMap<>();

    public static SpotifyService getService() {
        return api.getService();
    }

    public static void setMyAccessToken(String token) {
        api.setAccessToken(token);
    }

    public static String getArtistImage(String ID) {
        Artist artist = getService().getArtist(ID);
        String image = artist.images.get(0).url;

        if (image != null) {
            return image;
        }

        return null;
    }

    public static Map<String, String> getMyFollowedArtists() {
        //List<String> artistString = new ArrayList<>();
        Map<String, String> artistList = new HashMap<>();

        List<Artist> artists = getService().getFollowedArtists().artists.items;
        String artist, id;

        for (int i = 0 ; i < artists.size() ; i++){
          //  artist = artists.get(i).id;
          //  artistString.add(artist);
            if( !(artists.get(i).name.contains("/") || artists.get(i).name.contains(".") || artists.get(i).name.contains("#") || artists.get(i).name.contains("$")
                    || artists.get(i).name.contains("[") || artists.get(i).name.contains("]")) ){
                artistList.put(artists.get(i).name, artists.get(i).id);
            }else{
                artistList.put("whatevv", artists.get(i).id);
            }

        }

        if (artists != null) {
            Artist justChecking = artists.get(0);
            Log.wtf("JustChecking", justChecking.id);
        }

        return artistList;
    }
}
