package com.zoom.happiestplacesrestaurant.ui.login;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.zoom.happiestplacesrestaurant.model.Message;

import com.zoom.happiestplacesrestaurant.repository.LoginRepository;
import com.zoom.happiestplacesrestaurant.repository.OrderRepository;

import com.zoom.happiestplacesrestaurant.util.DateUtil;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;


@HiltViewModel
public class VerificationViewModel extends AndroidViewModel {
    @Inject
    OrderRepository orderRepository;
    @Inject
    LoginRepository loginRepository;

    Context mContext;
    MutableLiveData<Message> refreshLiveData=new MutableLiveData<>();
    MutableLiveData<Message> resendLiveData=new MutableLiveData<>();


    @Inject
    public VerificationViewModel(@NonNull Application application) {
        super(application);
        mContext=application.getApplicationContext();
    }
    public LiveData<Message> checkVerification() {
    orderRepository.getTodaysOrders(refreshLiveData,mContext);
    return  refreshLiveData;
    }

    public LiveData<Message> resendVerification() {
        loginRepository.resendVerification(resendLiveData,mContext);
        return resendLiveData;
    }


}

