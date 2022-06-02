package com.zoom.happiestplacesrestaurant.ui;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zoom.happiestplacesrestaurant.R;
import com.zoom.happiestplacesrestaurant.databinding.OrderFragmentBinding;
import com.zoom.happiestplacesrestaurant.databinding.SignOutFragmentBinding;
import com.zoom.happiestplacesrestaurant.ui.login.LoginViewModel;
import com.zoom.happiestplacesrestaurant.util.AppUtils;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SignOutFragment extends Fragment {
    SignOutFragmentBinding mBinding;
    private LoginViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding = SignOutFragmentBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        signout();
        return mBinding.getRoot();
    }
    private void displayLoader() {
        mBinding.viewLoader.rootView.setVisibility(View.VISIBLE);
    }
    private void hideLoader() {
        mBinding.viewLoader.rootView.setVisibility(View.VISIBLE);
    }

    private void signout() {
        displayLoader();
       /* FirebaseMessaging.getInstance().deleteToken()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });*/
        mViewModel.logout()
                .observe(getViewLifecycleOwner(), message -> {
                    //hideLoader();
                    if (message == null) {
                        if (!AppUtils.isNetworkAvailableAndConnected(getContext()))
                            AppUtils.showSnackbar(getView(), getString(R.string.network_err));
                        else
                            AppUtils.showSnackbar(getView(), getString(R.string.logout_err));
                    } else {
                        NavHostFragment.findNavController(this).navigate(R.id.loginFragment);
                    }});
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}