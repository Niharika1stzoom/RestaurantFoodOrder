package com.zoom.happiestplacesrestaurant.util;

import com.google.gson.Gson;
import com.zoom.happiestplacesrestaurant.model.Restaurant;
import com.zoom.happiestplacesrestaurant.ui.login.data.model.LoggedInUser;

public class GsonUtils {


    public static String getGsonObject(Restaurant user) {
        Gson gson = new Gson();
        return gson.toJson(user);
    }
    public static Restaurant getModelObjectCustomer(String user) {
        Gson gson = new Gson();
        return gson.fromJson(user, Restaurant.class);

    }
}
