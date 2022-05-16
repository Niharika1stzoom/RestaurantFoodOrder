package com.zoom.happiestplacesrestaurant.model;

import com.zoom.happiestplacesrestaurant.ui.login.data.model.Access;

import java.io.Serializable;
import java.util.UUID;

public class Restaurant implements Serializable {
    UUID id;
    String name;
    Address address;
    String token;
    Boolean owner;
    Access access;
    Ratings ratings;
    String user_name;
    String user_email;
    String role;

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public String getToken() {
        return token;
    }

    public Boolean getOwner() {
        return owner;
    }

    public Access getAccess() {
        return access;
    }

    public Ratings getRatings() {
        return ratings;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public String getRole() {
        return role;
    }
}
