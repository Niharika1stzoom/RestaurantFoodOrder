package com.zoom.happiestplacesrestaurant.ui.login;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.zoom.happiestplacesrestaurant.MainActivity;
import com.zoom.happiestplacesrestaurant.R;
import com.zoom.happiestplacesrestaurant.databinding.FragmentVerificationBinding;
import com.zoom.happiestplacesrestaurant.databinding.IssueReportFragmentBinding;
import com.zoom.happiestplacesrestaurant.ui.issue.IssueReportViewModel;
import com.zoom.happiestplacesrestaurant.util.AppUtils;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class VerificationFragment extends Fragment {


    private FragmentVerificationBinding mBinding;
    private VerificationViewModel mViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding= FragmentVerificationBinding.inflate(inflater,container,false);
        mViewModel = new ViewModelProvider(this).get(VerificationViewModel.class);
        checkVerification();
        updateUI();
       return mBinding.getRoot();
    }

    private void checkVerification() {
        displayLoader();
        mViewModel.checkVerification().observe(getViewLifecycleOwner(), message -> {
            hideLoader();
            if(message==null) {
                if(!AppUtils.isNetworkAvailableAndConnected(getContext()))
                    AppUtils.showSnackbar(getView(),getString(R.string.network_err));
                else
                    AppUtils.showSnackbar(getView(),getString(R.string.confirmEmailMsg));
            }
            else {
                NavHostFragment.findNavController(getParentFragment()).popBackStack();
                NavHostFragment.findNavController(getParentFragment()).navigate(R.id.orderDisplayFragment);
            }
        });
    }


    private void updateUI() {
        mBinding.refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayLoader();
                mViewModel.checkVerification();
            }

        });
        mBinding.resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               displayLoader();
                mViewModel.resendVerification().observe(getViewLifecycleOwner(), message -> {
                    if(message==null) {
                        if(!AppUtils.isNetworkAvailableAndConnected(getContext()))
                            AppUtils.showSnackbar(getView(),getString(R.string.network_err));
                    }
                    else {
                        hideLoader();
                     AppUtils.showSnackbar(getView(),getString(R.string.verEmailMsg));
                    }
                });
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    private void displayLoader() {
        mBinding.viewLoader.rootView.setVisibility(View.VISIBLE);
       mBinding.layoutGroup.setVisibility(View.GONE);
    }
    private void hideLoader() {
        mBinding.viewLoader.rootView.setVisibility(View.GONE);
        mBinding.layoutGroup .setVisibility(View.VISIBLE);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}