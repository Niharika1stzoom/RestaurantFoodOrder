
package com.zoom.happiestplacesrestaurant.ui.order.display;

import androidx.activity.OnBackPressedCallback;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayoutMediator;
import com.zoom.happiestplacesrestaurant.R;
import com.zoom.happiestplacesrestaurant.databinding.OrderDisplayFragmentBinding;
import com.zoom.happiestplacesrestaurant.databinding.OrderFragmentBinding;
import com.zoom.happiestplacesrestaurant.ui.order.OrderViewModel;
import com.zoom.happiestplacesrestaurant.util.SharedPrefUtils;

import java.util.UUID;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class OrderDisplayFragment extends Fragment {

    private OrderDisplayViewModel mViewModel;
    private OrderDisplayFragmentBinding mBinding;

    public static OrderDisplayFragment newInstance() {
        return new OrderDisplayFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //TODO:Put in the home fragment

        mViewModel = new ViewModelProvider(this).get(OrderDisplayViewModel.class);
        mBinding = OrderDisplayFragmentBinding.inflate(inflater, container, false);
        if(mViewModel.getRestaurantId()!=null)
            initTabLayout(mViewModel.getRestaurantId());
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                if (mBinding.pager.getCurrentItem() != 0) {
                    mBinding.pager.setCurrentItem(
                            mBinding.pager.getCurrentItem() - 1,false);
                }else{
                    getActivity().finish();
                }

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        return mBinding.getRoot();
    }

    private void initTabLayout(UUID restId) {
        ViewPagerAdapter adapter=new ViewPagerAdapter(this,restId);
        mBinding.pager.setAdapter(adapter);
        new TabLayoutMediator(mBinding.tabLayout, mBinding.pager,
                (tab, position) ->{
            tab.setText(adapter.getTabName(position));
        }).attach();
    }

}