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

    private static SharedPreferences sharedPrefs;

    private static SharedPreferences getSharedPreferences(Context context) {
         sharedPrefs = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
         return sharedPrefs;
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
