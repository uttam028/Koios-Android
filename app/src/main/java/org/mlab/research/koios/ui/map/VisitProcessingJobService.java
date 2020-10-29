package org.mlab.research.koios.ui.map;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import org.mlab.research.koios.Koios;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VisitProcessingJobService extends JobService {

    private static final String TAG = VisitProcessingJobService.class.getSimpleName() + "_debug";
    boolean isWorking = false;
    boolean jobCancelled = false;



    // Called by the Android system when it's time to run the job
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d(TAG, "Job started!");
        isWorking = true;

        // We need 'jobParameters' so we can call 'jobFinished'
        startWorkOnNewThread(jobParameters); // Services do NOT run on a separate thread

        return isWorking;
    }

    private void startWorkOnNewThread(final JobParameters jobParameters) {
        new Thread(new Runnable() {
            public void run() {
                doWork(jobParameters);
            }
        }).start();
    }

    private void doWork(JobParameters jobParameters) {

        //TODO: process new visits

        isWorking = false;
        boolean needsReschedule = false;
        jobFinished(jobParameters, needsReschedule);
    }

    // Called if the job was cancelled before being finished
    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(TAG, "Job cancelled before being completed.");
        jobCancelled = true;
        boolean needsReschedule = isWorking;
        jobFinished(jobParameters, needsReschedule);
        return needsReschedule;
    }

}
