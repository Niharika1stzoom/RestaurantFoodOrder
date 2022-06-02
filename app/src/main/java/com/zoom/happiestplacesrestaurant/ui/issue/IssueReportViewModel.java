package com.zoom.happiestplacesrestaurant.ui.issue;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.zoom.happiestplacesrestaurant.model.Issue;
import com.zoom.happiestplacesrestaurant.model.Topic;
import com.zoom.happiestplacesrestaurant.repository.IssueRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class IssueReportViewModel extends AndroidViewModel {
    MutableLiveData<Topic> topicMutableLiveData=new MutableLiveData<>();
    MutableLiveData<Issue> issueResponseLiveData=new MutableLiveData<>();
    private Context mContext;
    @Inject
    IssueRepository issueRepository;
    @Inject
    public IssueReportViewModel(@NonNull Application application) {

        super(application);
        mContext=application.getApplicationContext();
    }

    public LiveData<Topic> getTopics() {
        issueRepository.getTopics(mContext,topicMutableLiveData);
        return topicMutableLiveData;
    }

    public LiveData<Issue> sendIssue(Issue issue) {
        issueRepository.sendIssue(mContext,issue,issueResponseLiveData);
        return issueResponseLiveData;
    }

}