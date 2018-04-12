package edu.temple.listenup;

/**
 * Created by kingJ on 4/11/2018.
 */

public class User {
    private String spotify_access_Token, username,sex,email;



    private double lon,lat, distance;


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
    public String getSpotify_access_Token() {
        return spotify_access_Token;
    }

    public void setSpotify_access_Token(String spotify_access_Token) {
        this.spotify_access_Token = spotify_access_Token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

}
