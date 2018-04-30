package edu.temple.listenup.Helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesUtils {
    private static final String PREFERENCES = "my_prefs";
    public static final String FIRST_TIME_USE = "first_time_check";
    private static final String ACCESS_TOKEN_KEY = "accessToken";
    private static final String USER_ID = "userID";
    private static final String DISPLAY_NAME = "display_name";
    private static final String CITY_INFO = "city_info";
    private static final String PIC_INFO = "pic_info";
    private static final String RADIUS = "radius";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";

    private static SharedPreferences sharedPrefs;

    private static SharedPreferences getSharedPreferences(Context context) {
         sharedPrefs = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
         return sharedPrefs;
    }

    public static void setMyLongitudeLatitude(double longitude, double latitude, Context context) {
        getSharedPreferences(context).edit().putString(RADIUS, String.valueOf(longitude)).apply();
        getSharedPreferences(context).edit().putString(RADIUS, String.valueOf(latitude)).apply();

    }

    public static void setMyRadius(int radius, Context context) {
        getSharedPreferences(context).edit().putInt(RADIUS, radius).apply();
    }

    public static void setMyAccessToken(String accessToken, Context context) {
        getSharedPreferences(context).edit().putString(ACCESS_TOKEN_KEY, accessToken).apply();
    }

    public static void setMySpotifyUserID(String userID, Context context) {
        getSharedPreferences(context).edit().putString(USER_ID, userID).apply();
    }

    public static void setMyDisplayName(String name, Context context) {
        getSharedPreferences(context).edit().putString(DISPLAY_NAME, name).apply();
    }

    public static void setMyPicInfo(String picURL, Context context) {
        getSharedPreferences(context).edit().putString(PIC_INFO, picURL).apply();
    }

    public static void setMyCityInfo(String info, Context context) {
        getSharedPreferences(context).edit().putString(CITY_INFO, info).apply();
    }

    public static String getMyRadius(Context context) {
        return getSharedPreferences(context).getString(RADIUS, null);
    }

    public static String getMyLongitude(Context context) {
        return getSharedPreferences(context).getString(LONGITUDE, null);
    }

    public static String getMyLatitude(Context context) {
        return getSharedPreferences(context).getString(LATITUDE, null);
    }

    public static String getMyAccessToken(Context context) {
        return getSharedPreferences(context).getString(ACCESS_TOKEN_KEY, null);
    }

    public static String getMySpotifyUserID(Context context) {
        return getSharedPreferences(context).getString(USER_ID, null);
    }

    public static String getMyDisplayName(Context context) {
        return getSharedPreferences(context).getString(DISPLAY_NAME, null);
    }

    public static String getMyPicInfo(Context context) {
        return getSharedPreferences(context).getString(PIC_INFO, null);
    }

    public static String getMyCityInfo(Context context) {
        return getSharedPreferences(context).getString(CITY_INFO, null);
    }



}
