package com.zoom.happiestplacesrestaurant.ui.order;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.zoom.happiestplacesrestaurant.model.Order;
import com.zoom.happiestplacesrestaurant.model.Restaurant;
import com.zoom.happiestplacesrestaurant.repository.OrderRepository;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class OrderViewModel extends AndroidViewModel {
    @Inject
    OrderRepository orderRepository;
    Context mContext;
    MutableLiveData<List<Order>> mOrderList=new MutableLiveData<>();;
    MutableLiveData<Restaurant> mRestaurant=new MutableLiveData<>();

    @Inject
    public OrderViewModel(@NonNull Application application) {
        super(application);
        mContext=application.getApplicationContext();
    }

    public void getCurrentOrders(UUID id) {
        orderRepository.getTodayOrders(mOrderList,mContext);
    }
    public LiveData<List<Order>> getOrderList() {
        return mOrderList;
    }
}