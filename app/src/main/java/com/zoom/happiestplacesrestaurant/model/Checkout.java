package com.zoom.happiestplacesrestaurant.model;

public class Checkout {
    String status;
    int redeem;
    public Checkout(String s, int pts) {
        status=s;
        redeem=pts;
    }

    public Checkout(String status) {
        this.status=status;
    }

    public String getStatus() {
        return status;
    }

    public int getRedeem() {
        return redeem;
    }
}
