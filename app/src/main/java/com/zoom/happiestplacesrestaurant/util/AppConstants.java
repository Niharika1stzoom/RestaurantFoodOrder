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
    public static final String KEY_LOGOUT = "logout";
    public static final String[] ISSUES_ARRAY ={
            "Orders",
            "Status of Order",
            "New Order",
            "Others"
    } ;
    public static final String KEY_FUSER_EMAIL ="user_email" ;
    public static final String SUCCESS ="success" ;

    public enum Status{Delivered,Paid,Placed,Cancel,Preparing,Ready};
    public static  String[] statusArray={"Preparing","Ready","Delivered","Cancel","Paid"};
    public static final String[] staffStatusArray={"Preparing","Ready","Delivered","Cancel"};

    public static void setStatus(String[] s)
    {
        statusArray=s;
    }
}
