package com.zoom.happiestplacesrestaurant.ui.order.display;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.zoom.happiestplacesrestaurant.util.SharedPrefUtils;

import java.util.UUID;

public class OrderDisplayViewModel extends AndroidViewModel {
    Context mContext;
    public OrderDisplayViewModel(@NonNull Application application) {
        super(application);
        mContext=application.getApplicationContext();
    }

    public UUID getRestaurantId() {
        return SharedPrefUtils.getUser(mContext).getId();
    }
    // TODO: Implement the ViewModel
}