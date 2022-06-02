package com.zoom.happiestplacesrestaurant.ui.order.orderhistory;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.zoom.happiestplacesrestaurant.model.Order;
import com.zoom.happiestplacesrestaurant.repository.OrderRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
    public class OrderHistoryViewModel extends AndroidViewModel {
    @Inject
    OrderRepository orderRepository;
    Context mContext;
    MutableLiveData<List<Order>> mOrderList = new MutableLiveData<>();

    @Inject
    public OrderHistoryViewModel(@NonNull Application application) {
        super(application);
        mContext = application.getApplicationContext();
    }
    public void getOrders(String s) {
        orderRepository.getOrdersDate(s, mOrderList, mContext);
    }


    public LiveData<List<Order>> getOrderList() {
        return mOrderList;
    }


}

