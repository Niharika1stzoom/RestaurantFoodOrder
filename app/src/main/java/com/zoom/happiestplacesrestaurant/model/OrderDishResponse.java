package com.zoom.happiestplacesrestaurant.model;

import com.google.gson.annotations.SerializedName;


import java.util.UUID;

//Used to get order dish response after placing order
public class OrderDishResponse {
    UUID id;
    @SerializedName("quantity")
    int qty;
    @SerializedName("dish")
    MenuItem menuItem;
    public UUID getId() {
        return id;
    }
    public int getQty() {
        return qty;
    }
    public MenuItem getMenuItem() {
        return menuItem;
    }
}
