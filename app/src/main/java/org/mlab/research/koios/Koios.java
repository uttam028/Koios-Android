package org.mlab.research.koios;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class Koios extends Application implements Application.ActivityLifecycleCallbacks {

    private static final String TAG = Koios.class.getSimpleName() + "_debug";

    private static Context context;
    private int activityReferences = 0;
    private boolean isActivityChangingConfigurations = false;

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
        context = getApplicationContext();
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        Log.d(TAG, "activity created, name: " + activity.getLocalClassName());
        if (activity.getLocalClassName().equalsIgnoreCase(MainActivity.class.getSimpleName())){
//            String logName = Util.getPreferenceData(getString(R.string.userEmail)) + "." + Util.getUniqueDeviceId();
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.d(TAG, "activity destroyed, name:" + activity.getLocalClassName());
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Log.d(TAG, "activity paused, name:" + activity.getLocalClassName());
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Log.d(TAG, "activity resumed, name:" + activity.getLocalClassName());
        if(activity instanceof MainActivity){
            Log.d(TAG, "I can start syncing study");
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        Log.d(TAG, "activity save instance state, name:" + activity.getLocalClassName());
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Log.d(TAG, "activity started, name:" + activity.getLocalClassName());
        if (++activityReferences == 1 && !isActivityChangingConfigurations) {
            // App enters foreground
            Log.d(TAG, "App enters foreground?");

        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Log.d(TAG, "activity stopped, name:" + activity.getLocalClassName());
        isActivityChangingConfigurations = activity.isChangingConfigurations();
        if (--activityReferences == 0 && !isActivityChangingConfigurations) {
            // App enters background
            Log.d(TAG, "App enter background?");
        }
    }

    public static Context getContext() {
        return context;
    }


}