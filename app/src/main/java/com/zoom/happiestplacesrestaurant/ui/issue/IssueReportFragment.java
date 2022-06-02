package com.zoom.happiestplacesrestaurant.ui.issue;

import androidx.lifecycle.ViewModelProvider;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.zoom.happiestplacesrestaurant.R;
import com.zoom.happiestplacesrestaurant.databinding.IssueReportFragmentBinding;
import com.zoom.happiestplacesrestaurant.model.Customer;
import com.zoom.happiestplacesrestaurant.model.Issue;
import com.zoom.happiestplacesrestaurant.model.Restaurant;
import com.zoom.happiestplacesrestaurant.util.AppConstants;
import com.zoom.happiestplacesrestaurant.util.AppUtils;
import com.zoom.happiestplacesrestaurant.util.SharedPrefUtils;

import java.util.UUID;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class IssueReportFragment extends Fragment {

    private IssueReportViewModel mViewModel;
    private IssueReportFragmentBinding mBinding;

    public static IssueReportFragment newInstance() {
        return new IssueReportFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding=IssueReportFragmentBinding.inflate(inflater,container,false);
        mViewModel = new ViewModelProvider(this).get(IssueReportViewModel.class);
        setTopics();
        updateUI();
        return mBinding.getRoot();
    }
    private void displayLoader() {
        mBinding.viewLoader.rootView.setVisibility(View.VISIBLE);
        mBinding.layoutGroup.setVisibility(View.GONE);
    }
    private void hideLoader() {
        mBinding.viewLoader.rootView.setVisibility(View.GONE);
         mBinding.layoutGroup .setVisibility(View.VISIBLE);
    }

    private void setTopics() {
        displayLoader();
        mViewModel.getTopics().observe(getViewLifecycleOwner(), topics -> {

            if(topics==null) {
                if(!AppUtils.isNetworkAvailableAndConnected(getContext()))
                    AppUtils.showSnackbar(getView(),getString(R.string.network_err));
            }
            else {
                hideLoader();
                ArrayAdapter<String> adapter=
                        new ArrayAdapter<String>(getContext(),
                                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                                topics.getTopics());
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mBinding.spinner.setAdapter(adapter);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBinding=null;
    }

    private void updateUI() {
        mBinding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayLoader();
                Restaurant restaurant= SharedPrefUtils.getUser(getActivity().getApplicationContext());
                UUID custId=null;
                if(restaurant!=null)
                    custId=restaurant.getUser_id();
                String text;
                if(mBinding.issueText.getText()==null ||
                        TextUtils.isEmpty(mBinding.issueText.getText())) {
                    mBinding.issueText.setError(getString(R.string.issueEmpty_err));
                    hideLoader();
                    return;
                }
                else
                    text=mBinding.issueText.getText().toString();

                Issue issue=new Issue(custId,text,
                        mBinding.spinner.getSelectedItem().toString().trim());
                mViewModel.sendIssue(issue).observe(getViewLifecycleOwner(), issueResponse -> {
                    if(issueResponse==null) {
                        if(!AppUtils.isNetworkAvailableAndConnected(getContext()))
                            AppUtils.showSnackbar(getView(),getString(R.string.network_err));
                    }
                    else {
                        hideLoader();
                        Toast.makeText(getContext(),getString(R.string.thankyouFeedback),Toast.LENGTH_SHORT).show();
                        NavHostFragment.findNavController(getParentFragment()).popBackStack();
                        NavHostFragment.findNavController(getParentFragment()).navigate(R.id.orderDisplayFragment);

                    }
                });

            }
        });
    }


}