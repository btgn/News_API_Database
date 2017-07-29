package com.example.btril.newsapp;

import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by btril on 07/27/17.
 */

public class RefreshNews extends com.firebase.jobdispatcher.JobService {
    AsyncTask mBackgroundTask;

    @Override
    public boolean onStartJob(final com.firebase.jobdispatcher.JobParameters job) {
        mBackgroundTask = new AsyncTask() {


            @Override
            protected void onPreExecute() {
                Toast.makeText(RefreshNews.this, "News refreshed", Toast.LENGTH_SHORT).show();
                super.onPreExecute();
            }

            /*Assigning a background task to refresh the page every 60 seconds*/
            @Override
            protected Object doInBackground(Object[] params) {
                RefreshTasks.refreshArticles(RefreshNews.this);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                jobFinished(job, false);
                super.onPostExecute(o);

            }
        };


        mBackgroundTask.execute();

        return true;
    }

    @Override
    public boolean onStopJob(com.firebase.jobdispatcher.JobParameters job) {

        if (mBackgroundTask != null)
            mBackgroundTask.cancel(false);

        return true;
    }
}
