package com.zoom.happiestplacesrestaurant.ui.order;

import androidx.activity.OnBackPressedCallback;
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
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zoom.happiestplacesrestaurant.R;
import com.zoom.happiestplacesrestaurant.databinding.FragmentLoginBinding;
import com.zoom.happiestplacesrestaurant.databinding.OrderFragmentBinding;
import com.zoom.happiestplacesrestaurant.model.Order;
import com.zoom.happiestplacesrestaurant.util.AppConstants;
import com.zoom.happiestplacesrestaurant.util.AppUtils;
import com.zoom.happiestplacesrestaurant.util.SharedPrefUtils;

import java.util.ArrayList;
import java.util.UUID;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class OrderFragment extends Fragment {
    private OrderFragmentBinding mBinding;
    private OrderViewModel mViewModel;
    private OrdersAdapter mAdapter;
    LocalBroadcastManager broadcastManager;
    private UUID mRestId;
    private String status;
    @Inject
    OrderRepository orderRepository;

    public static Fragment newInstance(UUID mRestId,String status) {
        final OrderFragment fragment = new OrderFragment();
        fragment.setArguments(AppUtils.getBundleForOrderFragment(mRestId,status));
        Log.d(AppConstants.TAG,"Arg set"+fragment.getArguments().toString());
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null && getArguments().containsKey(AppConstants.KEY_RESTAURANT_ID)) {
            mRestId = UUID.fromString(getArguments().getString(AppConstants.KEY_RESTAURANT_ID));
            status=getArguments().getString(AppConstants.KEY_STATUS);
            Log.d(AppConstants.TAG,"data found in arguments"+mRestId);
        }
        else
            Log.d(AppConstants.TAG,"No data found in arguments");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //TODO:Put in the home fragment

        mViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        mBinding = OrderFragmentBinding.inflate(inflater, container, false);
        getOrders();
        mViewModel.getCurrentOrders(mRestId);

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
                     mViewModel.getCurrentOrders(mRestId);

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
                        Log.d(AppConstants.TAG, "Refreshed list"+status);

                            ArrayList paidList=new ArrayList();
                            ArrayList currentList=new ArrayList();
                            for(Order o:orderList)
                            {
                                if(o.getStatus().equals(AppConstants.Status.Paid.toString()))
                                paidList.add(o);
                                else
                                    currentList.add(o);
                            }
                        if(status.equals(AppConstants.Status.Paid.toString())) {
                            mAdapter.setList(paidList);
                            if(paidList.size()==0){
                                displayEmptyView();
                            }
                        }
                        else {
                            mAdapter.setList(currentList);
                            if(currentList.size()==0){
                                displayEmptyView();
                            }
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
        mAdapter = new OrdersAdapter(getContext(),status,orderRepository);
        mBinding.recyclerView.setAdapter(mAdapter);
    }
}