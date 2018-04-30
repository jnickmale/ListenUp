package edu.temple.listenup.Models;

import android.content.Context;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import edu.temple.listenup.Helpers.PreferencesUtils;
import edu.temple.listenup.HomeScreenActivity;
import edu.temple.listenup.MainActivity;
import kaaes.spotify.webapi.android.models.UserPrivate;

/**
 * Created by kingJ on 4/11/2018.
 */

public class User implements Parcelable {

    private String id, displayName,sex,email, userImage;
    private int rating;

    private double lon,lat, distance;
    public User() {

    }
    public User(UserPrivate user) {
        id = user.id;
        displayName = user.display_name;
        email = user.email;

        String image = user.images.get(0).url;

        if (image != null) {
            userImage = image;
        }

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

    public User(String id, String displayName, String sex, String email, double lon, double lat, double distance, String userImage) {
        this.id = id;
        this.displayName = displayName;
        this.sex = sex;
        this.email = email;
        this.lon = lon;
        this.lat = lat;
        this.distance = distance;
        this.userImage = userImage;
    }



    public User(String id, String displayName, String sex, String email) {
        this.id = id;
        this.displayName = displayName;
        this.sex = sex;
        this.email = email;
    }

    protected User(Parcel in) {
        id = in.readString();
        displayName = in.readString();
        sex = in.readString();
        email = in.readString();
        userImage = in.readString();
        lon = in.readDouble();
        lat = in.readDouble();
        distance = in.readDouble();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

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

    public String getUserImage() {
        return userImage;
    }


    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.displayName);
        dest.writeDouble(this.lat);
        dest.writeDouble(this.lon);
        dest.writeString(this.userImage);
    }

    public void setDistanceFromUser() {
        Location locationMyUser = new Location("point A");
        Location locationThisUser = new Location("point B");

        double distance;

        locationMyUser.setLatitude(HomeScreenActivity.lat);
        locationMyUser.setLongitude(HomeScreenActivity.longi);

        locationThisUser.setLatitude(getLat());
        locationThisUser.setLongitude(getLon());

        distance = (int) locationMyUser.distanceTo(locationThisUser);
        this.distance = distance;

        //return distance;
    }

}
