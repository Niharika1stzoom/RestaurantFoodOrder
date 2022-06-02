package com.zoom.happiestplacesrestaurant.worker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.zoom.happiestplacesrestaurant.util.AppConstants;

import java.util.Map;
import java.util.UUID;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        String orderId="";
        if (remoteMessage.getData().size() > 0) {
            Map<String,String> data=remoteMessage.getData();
            orderId = data.get(AppConstants.KEY_ORDER_ID);

            }
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
        }
        notifyNewOrder(orderId);
    }

    @Override
    public void onNewToken(String token) {
    }
    private void notifyNewOrder(String orderId){
        Intent intent = new Intent(AppConstants.INTENT_ACTION_NEWORDER);
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.KEY_ORDER_ID, orderId);
        intent.putExtras(bundle);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}