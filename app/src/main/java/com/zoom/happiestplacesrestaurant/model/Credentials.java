package com.zoom.happiestplacesrestaurant.model;

public class Credentials
{
    public String email;
    public String password;
    String fcmToken;

    public Credentials(String email, String password, String fcmToken) {
        this.email = email;
        this.password = password;
        this.fcmToken = fcmToken;
    }
}