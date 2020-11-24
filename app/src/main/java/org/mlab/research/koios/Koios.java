package org.mlab.research.koios;

import android.app.Activity;
import android.app.Application;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.mlab.research.koios.framework.engine.RuleManagementService;
import org.mlab.research.koios.ui.map.MapDBHelper;
import org.mlab.research.koios.ui.map.VisitProcessingJobService;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Koios extends Application implements Application.ActivityLifecycleCallbacks {

    private static final String TAG = Koios.class.getSimpleName() + "_debug";

    private static Context context;
    private static KoiosDbHelper dbHelper;
    private int activityReferences = 0;
    private boolean isActivityChangingConfigurations = false;

    private static MutableLiveData<Boolean> surveyListChanged = new MutableLiveData<>();
    private static MutableLiveData<Boolean> studyEnrolled = new MutableLiveData<>();


    private static CimonService service;

//    private static Logger systemLogger;
    private static MapDBHelper mapDBHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
        context = getApplicationContext();

//        initializeLogger();

        initializeCimonService();
        dbHelper = new KoiosDbHelper(context);
        mapDBHelper = new MapDBHelper(context);

//        scheduleVisitProcessing();

        //start generating wifi events
//        new WiFiEventGenerator().start();


//        Intent sensorIntent = new Intent(this, CoreSensorListener.class);
//        ContextCompat.startForegroundService(this, sensorIntent);


//        Intent intent = new Intent(this, RuleManagementService.class);
//        ContextCompat.startForegroundService(this, intent);


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
//            String appState = Util.getPreferenceData(getString(R.string.appState));
//            if (appState.equalsIgnoreCase(getString(R.string.verified))) {
//                Log.d(TAG, "syncing study from Koios");
//                StudySyncer.getInstance().syncStudies();
//            }
            if (activity instanceof MainActivity){
                StudySyncer.getInstance().syncStudies();
            }

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


//    public static void initializeLogger(){
//        if (systemLogger == null){
//            systemLogger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
//        }
//    }
//
//    public static void log(String msg){
//        if (systemLogger != null){
////            Log.d(TAG, "system logger event "+ systemLogger.getName() + "," + systemLogger.isDebugEnabled());
//            systemLogger.debug(msg);
//        }
//    }

    private void scheduleVisitProcessing(){
        ComponentName componentName = new ComponentName(this, VisitProcessingJobService.class);
        JobInfo jobInfo = new JobInfo.Builder(31, componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPeriodic(2 * 60 * 60 * 1000)//1 hour in milliseconds
                .build();

        JobScheduler jobScheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = jobScheduler.schedule(jobInfo);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Visit processing Job scheduled!");
        } else {
            Log.d(TAG, "visit processing Job not scheduled");
        }

    }
    private void initializeCimonService() {
        // Build the CIMON web service
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Util.baseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(CimonService.class);

    }

    public static MapDBHelper getMapDBHelper() {
        return mapDBHelper;
    }

    public static CimonService getService() {
        return service;
    }

    public static KoiosDbHelper getDbHelper() {
        return dbHelper;
    }

    public static MutableLiveData<Boolean> getSurveyListChanged() {
        return surveyListChanged;
    }

    public static MutableLiveData<Boolean> getStudyEnrolled() {
        return studyEnrolled;
    }
}
