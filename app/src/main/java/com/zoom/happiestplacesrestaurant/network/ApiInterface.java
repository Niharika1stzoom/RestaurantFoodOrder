package com.zoom.happiestplacesrestaurant.network;

import com.zoom.happiestplacesrestaurant.model.Checkout;
import com.zoom.happiestplacesrestaurant.model.Credentials;
import com.zoom.happiestplacesrestaurant.model.DateRequest;
import com.zoom.happiestplacesrestaurant.model.Order;
import com.zoom.happiestplacesrestaurant.model.Restaurant;

import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    //@POST("v0/d3/login/")
    //Call<LoggedInUser> login(@Body Credentials credentials);
    @POST("r/v0/login/")
    Call<Restaurant> login(@Body Credentials credentials);

  //  @Headers("Authorization:Token 39a5a62a4ed540386164914cd477859ef49cbdeb")
 //   @GET("r/v0/orders/")
  //  Call<List<Order>> getTodayOrders();

    @GET("r/v0/orders/")
    Call<List<Order>> getTodayOrders(@Header("Authorization") String token);

  //  @POST("r/v0/orders/status/change/{id}/")
  //  Call<Order> updateStatusOrder(@Body Checkout checkout,@Header("Authorization") String token,@Path("id") UUID id);

    @POST("r/v0/orders/status/change/{id}/")
    Call<Order> updateStatusOrder(@Body Checkout change,@Header("Authorization") String token,@Path("id") UUID id);

    @GET("r/v0/orders")
    Call<List<Order>> getOrdersDate(@Header("Authorization")
                                            String token, @Query("date") String date);


    /*
    @POST("device")
    Call<DeviceInfo> registerDevice(@Body DeviceInfo deviceInfo);

    @GET("device/{deviceId}/")
    Call<List<Playlist>> getPlaylists(@Path("deviceId") String deviceId,
                                      @Query("endDate") Date endDate);

    @GET("device/")
    Call<List<DeviceInfo>> getDevice(@Query("token") String token);

       @GET("1b2be2b2-a5e6-4fbf-b771-c6d3bf2b2759")
    Call<LoggedInUser> login();

     */
}