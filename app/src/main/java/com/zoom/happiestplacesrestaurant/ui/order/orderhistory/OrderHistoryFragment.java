package com.zoom.happiestplacesrestaurant.ui.order.orderhistory;

import androidx.lifecycle.ViewModelProvider;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.zoom.happiestplacesrestaurant.R;
import com.zoom.happiestplacesrestaurant.databinding.OrderFragmentBinding;
import com.zoom.happiestplacesrestaurant.databinding.OrderHistoryFragmentBinding;
import com.zoom.happiestplacesrestaurant.model.Order;
import com.zoom.happiestplacesrestaurant.ui.order.OrderRepository;
import com.zoom.happiestplacesrestaurant.ui.order.OrderViewModel;
import com.zoom.happiestplacesrestaurant.ui.order.OrdersAdapter;
import com.zoom.happiestplacesrestaurant.ui.order.display.OrderDisplayViewModel;
import com.zoom.happiestplacesrestaurant.util.AppConstants;
import com.zoom.happiestplacesrestaurant.util.AppUtils;
import com.zoom.happiestplacesrestaurant.util.DateUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class OrderHistoryFragment extends Fragment {
    private OrderHistoryFragmentBinding mBinding;
    private OrderHistoryViewModel mViewModel;
    private OrderHistoryAdapter mAdapter;
    LocalBroadcastManager broadcastManager;
    @Inject
    OrderRepository orderRepository;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(this).get(OrderHistoryViewModel.class);
        mBinding = OrderHistoryFragmentBinding.inflate(inflater, container, false);
        mBinding.dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               MaterialDatePicker datePicker= MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select date")
                        .build();
               datePicker.show(getParentFragmentManager(),"Select Date");


               datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                   @Override
                   public void onPositiveButtonClick(Object selection) {
                       Date date=new Date(datePicker.getHeaderText());
                       mViewModel.getOrders(DateUtil.getTodayDate(date));
                       mBinding.dateButton.setText(datePicker.getHeaderText());
                       displayLoader();
                   }
               });
            }
        });
        getOrders();
        Log.d(AppConstants.TAG,DateUtil.getTodayDate(Calendar.getInstance().getTime()));
        mViewModel.getOrders(DateUtil.getTodayDate(Calendar.getInstance().getTime()));
        return mBinding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        broadcastManager = LocalBroadcastManager.getInstance(context);
        IntentFilter actionReceiver = new IntentFilter();
        actionReceiver.addAction(AppConstants.INTENT_ACTION_NEWORDER);
        broadcastManager.registerReceiver(onNewOrderReceived, actionReceiver);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        broadcastManager.unregisterReceiver(onNewOrderReceived);
    }

    private BroadcastReceiver onNewOrderReceived = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null)
                if (intent.getAction() != null && intent.getAction().equals(AppConstants.INTENT_ACTION_NEWORDER)) {
                    String orderId = intent.getStringExtra(AppConstants.KEY_ORDER_ID);
                    //data = new JSONObject(json);
                    Log.d(AppConstants.TAG, "Received a new order" + orderId);
                    //TODO:for testing, remove later
                    //mViewModel.getCurrentOrders(UUID.randomUUID());
                    mViewModel.getOrders("2022-05-12");

                }
        }
    };


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    private void getOrders() {
        displayLoader();
        //TODO:Save restaurant details in menu this has to be done once
        mViewModel.getOrderList()
                .observe(getViewLifecycleOwner(), orderList -> {
                    hideLoader();
                    if (orderList == null || orderList.size() == 0) {
                        if (!AppUtils.isNetworkAvailableAndConnected(getContext()))
                            AppUtils.showSnackbar(getView(), getString(R.string.network_err));
                        displayEmptyView();

                    } else {


                            mAdapter.setList(orderList);
                            if (orderList.size() == 0) {
                                displayEmptyView();
                            }
                        }

                });
    }

    private void hideLoader() {
        mBinding.viewLoader.rootView.setVisibility(View.GONE);
        mBinding.recyclerView.setVisibility(View.VISIBLE);
    }

    private void displayEmptyView() {
        mBinding.recyclerView.setVisibility(View.GONE);
        mBinding.viewEmpty.emptyText.setText(getString(R.string.empty_order));
        mBinding.viewEmpty.emptyContainer.setVisibility(View.VISIBLE);
    }

    private void displayLoader() {
        mBinding.viewLoader.rootView.setVisibility(View.VISIBLE);
        mBinding.recyclerView.setVisibility(View.GONE);
    }

    private void initRecyclerView() {
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new OrderHistoryAdapter(getContext(), orderRepository);
        mBinding.recyclerView.setAdapter(mAdapter);
    }
}