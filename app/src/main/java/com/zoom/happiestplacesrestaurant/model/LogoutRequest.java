package com.zoom.happiestplacesrestaurant.model;

public class LogoutRequest {
    String fcmToken;

    public LogoutRequest(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
