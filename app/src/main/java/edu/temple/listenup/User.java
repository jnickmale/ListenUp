package edu.temple.listenup;

/**
 * Created by kingJ on 4/11/2018.
 */

public class User {
    private String spotify_access_Token, username,sex,email;
    double lon,lat, distance;

    public User(String spotify_access_Token, String username, String sex, String email, double lon, double lat, double distance) {
        this.spotify_access_Token = spotify_access_Token;
        this.username = username;
        this.sex = sex;
        this.email = email;
        this.lon = lon;
        this.lat = lat;
        this.distance = distance;
    }

    public User(String spotify_access_Token, String username, String sex, String email) {
        this.spotify_access_Token = spotify_access_Token;
        this.username = username;
        this.sex = sex;
        this.email = email;
    }
}
