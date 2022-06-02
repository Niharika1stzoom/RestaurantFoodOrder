package com.zoom.happiestplacesrestaurant.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.zoom.happiestplacesrestaurant.model.Checkout;
import com.zoom.happiestplacesrestaurant.model.DateRequest;
import com.zoom.happiestplacesrestaurant.model.Message;
import com.zoom.happiestplacesrestaurant.model.Order;
import com.zoom.happiestplacesrestaurant.model.Restaurant;
import com.zoom.happiestplacesrestaurant.network.ApiInterface;
import com.zoom.happiestplacesrestaurant.util.AppConstants;
import com.zoom.happiestplacesrestaurant.util.SharedPrefUtils;

import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderRepository {
    ApiInterface mApiInterface;
    public OrderRepository(ApiInterface apiInterface) {
        mApiInterface = apiInterface;
    }

    public void getTodayOrders( MutableLiveData<List<Order>> liveData, Context context) {
        Call<List<Order>> call =
                mApiInterface.getTodayOrders(getHeaderToken(context));
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(@NonNull Call<List<Order>> call,
                                   @NonNull Response<List<Order>> response) {
                if (response.isSuccessful()) {
                    liveData.postValue(response.body());
                } else {
                    liveData.postValue(null);
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Order>> call, @NonNull Throwable t) {
                Log.d(AppConstants.TAG, "Failure" + t.getLocalizedMessage());
                liveData.postValue(null);
            }
        });
    }

    private String getHeaderToken(Context context) {
        return "Token "+SharedPrefUtils.getUser(context).getToken();
    }


    public void markStatus(UUID id, Checkout status, Context context, MutableLiveData<Order> liveData) {
        Call<Order> call = mApiInterface.updateStatusOrder(status,getHeaderToken(context),id);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(@NonNull Call<Order> call,
                                   @NonNull Response<Order> response) {
                if (response.isSuccessful()) {
                    liveData.postValue(response.body());
                } else {
                    liveData.postValue(null);
                }
            }
            @Override
            public void onFailure(@NonNull Call<Order> call, @NonNull Throwable t) {
                Log.d(AppConstants.TAG, "Failure status" + t.getLocalizedMessage());
                liveData.postValue(null);
            }
        });
    }

    public void getOrdersDate(String date, MutableLiveData<List<Order>> liveData, Context context) {
        Call<List<Order>> call = mApiInterface.getOrdersDate(getHeaderToken(context),date);
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(@NonNull Call<List<Order>> call,
                                   @NonNull Response<List<Order>> response) {
                if (response.isSuccessful()) {
                    liveData.postValue(response.body());
                } else {
                    liveData.postValue(null);
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Order>> call, @NonNull Throwable t) {
                Log.d(AppConstants.TAG, "Failure" + t.getLocalizedMessage());
                liveData.postValue(null);
            }
        });
    }
//Using for verification
    public void getTodaysOrders(MutableLiveData<Message> liveData,Context context) {
        Call<List<Order>> call =  mApiInterface.getTodayOrders(getHeaderToken(context));
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(@NonNull Call<List<Order>> call,
                                   @NonNull Response<List<Order>> response) {
                if (response.isSuccessful()) {
                    liveData.postValue(new Message(AppConstants.SUCCESS));
                } else {
                    liveData.postValue(null);
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Order>> call, @NonNull Throwable t) {
                Log.d(AppConstants.TAG, "Failure" + t.getLocalizedMessage());
                liveData.postValue(null);
            }
        });
    }
}

