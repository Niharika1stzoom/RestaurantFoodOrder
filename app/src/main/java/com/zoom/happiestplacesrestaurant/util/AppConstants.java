package com.zoom.happiestplacesrestaurant.util;

import android.content.Intent;

public class AppConstants {
    public static final String TAG="FoodDebug";
    public static final String KEY_USER ="user" ;
    public static final String CURRENCY = "Rs";
    public static final String KEY_RESTAURANT_ID = "restId";
    public static final String KEY_FCM_TOKEN ="fcm_token" ;
    public static final String INTENT_ACTION_NEWORDER = "new_order";
    public static final String KEY_ORDER_ID ="orderId" ;
    public static final String KEY_STATUS = "status";

    public enum Status{Delivered,Paid,Placed,Cancel};
    public static final String[] statusArray={"Placed","Delivered","Cancel","Paid"};
    public static final String[] staffStatusArray={"Placed","Delivered","Cancel"};
}
