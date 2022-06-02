package com.zoom.happiestplacesrestaurant.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.zoom.happiestplacesrestaurant.model.Issue;
import com.zoom.happiestplacesrestaurant.model.Topic;
import com.zoom.happiestplacesrestaurant.network.ApiInterface;
import com.zoom.happiestplacesrestaurant.util.AppConstants;
import com.zoom.happiestplacesrestaurant.util.AppUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IssueRepository {
    private ApiInterface mApiInterface;;
    public IssueRepository(ApiInterface apiInterface) {
        mApiInterface=apiInterface;
    }

    public void getTopics(Context context, MutableLiveData<Topic> liveData) {
        Call<Topic> call = mApiInterface.getTopics(AppUtils.getHeaderToken(context));
        call.enqueue(new Callback<Topic>() {
            @Override
            public void onResponse(@NonNull Call<Topic> call,
                                   @NonNull Response<Topic> response) {
                if (response.isSuccessful()) {
                    liveData.postValue(response.body());

                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Topic> call, @NonNull Throwable t) {
                Log.d(AppConstants.TAG, "Topics fetching issue" + t.getLocalizedMessage());
                liveData.postValue(null);
            }
        });
    }

    public void sendIssue(Context context,Issue issue, MutableLiveData<Issue> liveData) {
        Call<Issue> call = mApiInterface.sendIssue(issue,AppUtils.getHeaderToken(context));
        call.enqueue(new Callback<Issue>() {
            @Override
            public void onResponse(@NonNull Call<Issue> call,
                                   @NonNull Response<Issue> response) {
                if (response.isSuccessful()) {
                    liveData.postValue(response.body());
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Issue> call, @NonNull Throwable t) {
                Log.d(AppConstants.TAG, "Issue Posting issue" + t.getLocalizedMessage());
                liveData.postValue(null);
            }
        });
    }
}

