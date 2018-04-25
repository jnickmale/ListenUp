package edu.temple.listenup;

import kaaes.spotify.webapi.android.models.UserPrivate;

/**
 * Created by kingJ on 4/11/2018.
 */

public class User {

    private String id, displayName,sex,email,userimage;

    private double lon,lat, distance;
    public User() {

    }
    public User(UserPrivate user) {
        id = user.id;
        displayName = user.display_name;
        email = user.email;
    }

    public User(String id, String displayName, String sex, String email, double lon, double lat, double distance) {
        this.id = id;
        this.displayName = displayName;
        this.sex = sex;
        this.email = email;
        this.lon = lon;
        this.lat = lat;
        this.distance = distance;
    }

    public User(String id, String displayName, String sex, String email, double lon, double lat, double distance, String userimage) {
        this.id = id;
        this.displayName = displayName;
        this.sex = sex;
        this.email = email;
        this.lon = lon;
        this.lat = lat;
        this.distance = distance;
        this.userimage=userimage;
    }



    public User(String id, String displayName, String sex, String email) {
        this.id = id;
        this.displayName = displayName;
        this.sex = sex;
        this.email = email;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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

    public String getUserimage() {
        return userimage;
    }


    public void setUserimage(String userimage) {
        this.userimage = userimage;
    }
}
