package com.zoom.happiestplacesrestaurant.network;

import com.zoom.happiestplacesrestaurant.model.Checkout;
import com.zoom.happiestplacesrestaurant.model.Credentials;
import com.zoom.happiestplacesrestaurant.model.Issue;
import com.zoom.happiestplacesrestaurant.model.LogoutRequest;
import com.zoom.happiestplacesrestaurant.model.Order;
import com.zoom.happiestplacesrestaurant.model.EmailRequest;
import com.zoom.happiestplacesrestaurant.model.Restaurant;
import com.zoom.happiestplacesrestaurant.model.Message;
import com.zoom.happiestplacesrestaurant.model.Topic;

import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST("r/v0/login/")
    Call<Restaurant> login(@Body Credentials credentials);

    @GET("r/v0/orders/")
    Call<List<Order>> getTodayOrders(@Header("Authorization") String token);


    @POST("r/v0/orders/status/change/{id}/")
    Call<Order> updateStatusOrder(@Body Checkout change,@Header("Authorization") String token,@Path("id") UUID id);

    @GET("r/v0/orders")
    Call<List<Order>> getOrdersDate(@Header("Authorization")
                                            String token, @Query("date") String date);
    @POST("/api/r/v0/logout/")
    Call<Message> logout(@Header("Authorization") String token, @Body LogoutRequest logoutRequest);


    @GET("report/restaurant/")
    Call<Topic> getTopics(@Header("Authorization") String token);


    @POST("report/restaurant/")
    Call<Issue> sendIssue(@Body Issue issue,@Header("Authorization") String token);

    @GET("r/v0/email/send/verification/")
    Call<Message> resendVerificationLink(@Header("Authorization") String token);

    @POST("r/v0/email/send/rpassword/")
    Call<Message> forgetPassword(@Body EmailRequest email);


}