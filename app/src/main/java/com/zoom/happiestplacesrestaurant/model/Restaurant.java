package com.zoom.happiestplacesrestaurant.model;

import android.util.Log;

import com.zoom.happiestplacesrestaurant.util.AppConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
    UUID user_id;
    String role;
    List<String> order_status;
    Boolean verified;

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

    public List<String> getOrder_status() {
        return order_status;
    }
    public String[] getOrder_statusOwner() {
        ArrayList<String> sList=new ArrayList<String>();
        for(String s:order_status)
        {
            if(!s.equals(AppConstants.Status.Placed.toString()))
                sList.add(s);


        }
       String [] sArr=new String[sList.size()];
        for(int i=0;i<sList.size();i++){
            sArr[i]=sList.get(i);
        }
        return sArr;
    }
    public String[] getOrder_statusOthers() {
        ArrayList<String> sList=new ArrayList<String>();
        for(String s:order_status)
        {
            if(s.equals(AppConstants.Status.Placed.toString()))
            {

            }else
            if(!access.getCheck_out() && s.equals(AppConstants.Status.Paid.toString())){

            }
            else{
            sList.add(s);
            }
        }
        String [] sArr=new String[sList.size()];
        for(int i=0;i<sList.size();i++){
            sArr[i]=sList.get(i);
        }
        return sArr;
    }

    public Boolean getVerified() {
        return verified;
    }

    public UUID getUser_id() {
        return user_id;
    }
}
