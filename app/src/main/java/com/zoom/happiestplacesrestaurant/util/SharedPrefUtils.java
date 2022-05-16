package com.zoom.happiestplacesrestaurant.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.zoom.happiestplacesrestaurant.model.Restaurant;
import com.zoom.happiestplacesrestaurant.ui.login.data.model.LoggedInUser;

import java.util.UUID;

public class SharedPrefUtils {
    synchronized public static void setUser(Context context, Restaurant customer) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(AppConstants.KEY_USER,GsonUtils.getGsonObject(customer));
        editor.apply();
    }

    public static Restaurant getUser(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String user = prefs.getString(AppConstants.KEY_USER, "");
        if(TextUtils.isEmpty(user))
            return null;
        else
            return GsonUtils.getModelObjectCustomer(user) ;
    }

    synchronized public static void delUser(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(AppConstants.KEY_USER);
        editor.apply();
    }
    synchronized public static void setRestaurantId(Context context, UUID id) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(AppConstants.KEY_RESTAURANT_ID,id.toString());
        editor.apply();
    }

    public static UUID getRestaurantId(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String id = prefs.getString(AppConstants.KEY_RESTAURANT_ID, "");
        if(TextUtils.isEmpty(id))
            return null;
        else
            return UUID.fromString(id) ;
    }


    public synchronized static void saveFcmToken(Context context,String token) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(AppConstants.KEY_FCM_TOKEN,token);
        editor.apply();
    }
    public synchronized static String getFcmToken(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String token=prefs.getString(AppConstants.KEY_FCM_TOKEN,"");
        return token;
    }

}
