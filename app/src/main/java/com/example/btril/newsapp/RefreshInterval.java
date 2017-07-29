package com.example.btril.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;


/**
 * Created by btril on 07/27/17.
 */


public class RefreshInterval {
    private static final int REFRESH_TIME_IN_MINUTES = 360;
    private static final int REFRESH_TIME_IN_SECONDS = 60; // setting the refresh rate to 60 seconds
    private static final String NEWS_JOB_TAG = "news_job_tag";
    private static boolean initialisedTime;

    synchronized public static void refreshTime(@NonNull final Context context) {
        if(initialisedTime)
            return;

        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        Job refreshJob = dispatcher.newJobBuilder()
                .setService(RefreshNews.class)
                .setTag(NEWS_JOB_TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(REFRESH_TIME_IN_MINUTES,
                        REFRESH_TIME_IN_MINUTES + REFRESH_TIME_IN_SECONDS))
                .setReplaceCurrent(true)
                .build();

        dispatcher.schedule(refreshJob);
        initialisedTime = true;
    }

}

