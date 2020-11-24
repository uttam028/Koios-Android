package org.mlab.research.koios;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudySyncer {

    private static final String TAG = StudySyncer.class.getSimpleName() + "_debug";

    private static volatile StudySyncer INSTANCE;
//    private static CimonService service;
    private static boolean isSensorSyncRequired;

    private StudySyncer() {
        if (INSTANCE != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static StudySyncer getInstance() {
        if (INSTANCE == null) {
            synchronized (StudySyncer.class) {
                if (INSTANCE == null) {
                    INSTANCE = new StudySyncer();
                }
            }
        }
        return INSTANCE;
    }

    private void triggerSensorSyncing() {

//        Log.d(TAG, "going to trigger syncing through pending intent");
//        Context context = Prosthesis.getContext();
//        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(context, SensorSyncingRequestReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//        Calendar time = Calendar.getInstance();
//        time.setTimeInMillis(System.currentTimeMillis());
//        time.add(Calendar.SECOND, 5);
//        manager.set(AlarmManager.RTC_WAKEUP, time.getTimeInMillis(), pendingIntent);

    }

    public void syncStudies() {
        String uuid = Util.getUniqueDeviceId();
        String email = Util.getPreferenceData(Koios.getContext().getString(R.string.userEmail));

        if (email.isEmpty()) {
            return;
        }

        Log.d(TAG, "syncing study, email: " + email + ", uuid:" + uuid);

        Call<ArrayList<KoiosStudy>> call = Koios.getService().getEnrolledStudies(email, uuid);
        try {
            call.enqueue(new Callback<ArrayList<KoiosStudy>>() {
                @Override
                public void onResponse(Call<ArrayList<KoiosStudy>> call, final Response<ArrayList<KoiosStudy>> response) {

                    Log.d(TAG, "study sync response " + response.toString());


                    Log.d(TAG, "db syncing on main thread " + (Looper.myLooper() == Looper.getMainLooper()));
                    HashMap<Integer, KoiosStudy> localStudies = new HashMap<>();
                    for (KoiosStudy localStudy : Koios.getDbHelper().getAllStudies()) {
                        Log.d(TAG, "local study : " + localStudy.toString());
                        localStudies.put(localStudy.getId(), localStudy);
                    }

                    ArrayList<KoiosStudy> serverStudies = response.body() == null ? new ArrayList<>() : response.body();
                    Log.d(TAG, "studies in server count:" + serverStudies.size());
                    for (int i = 0; i < serverStudies.size(); i++) {
                        KoiosStudy serverStudy = serverStudies.get(i);
                        Log.d(TAG, "server study desc: " + serverStudy.toString());
                        int serverStudyId = serverStudy.getId();
                        String serverStudyModificationTime = serverStudy.getModificationTime() == null ? "" : serverStudy.getModificationTime();

                        if (localStudies.containsKey(serverStudyId)) {
                            Log.d(TAG, "study present in local database");
                            //study present in local db
                            String localModificationTime = localStudies.get(serverStudyId).getModificationTime() == null ? "" : localStudies.get(serverStudyId).getModificationTime();
                            if (localModificationTime.equals(serverStudyModificationTime)) {
                                //both time are same, no need to update
                                Log.d(TAG, "both are same, no need to update");
                            } else {
                                Log.d(TAG, "server published new version, replacing local one");
                                Koios.getDbHelper().insertStudyData(serverStudy);
                                //need to sync sensor config with server
                                Log.d(TAG, "need to sync sensor config with server");
                                triggerSensorSyncing();
                                syncSensorConfigsOfStudy(serverStudyId);
                                syncSurveyConfigsOfStudy(serverStudyId);
                            }
                            localStudies.remove(serverStudyId);
                            Log.d(TAG, "this one is removed from local cache, see count :" + localStudies.size());
                        } else {
                            Log.d(TAG, "study not present in local db, need to insert");
                            //study not present in local db, need to insert data
                            Koios.getDbHelper().insertStudyData(serverStudy);
                            Log.d(TAG, "also need to sync sensor config with server");
                            //no sensor config in local db, need to sync with server
                            triggerSensorSyncing();
                            syncSensorConfigsOfStudy(serverStudyId);
                            syncSurveyConfigsOfStudy(serverStudyId);
                        }
                    }


                    Log.d(TAG, "remaining items in local cache, count: " + localStudies.size());
                    //remove remaining items from hashmap and local db
                    for (int key : localStudies.keySet()) {
                        int deletedStudies = Koios.getDbHelper().deleteStudy(key);
                        int deletedSensorConfigs = Koios.getDbHelper().deleteAllSensorConfigsOfTheStudy(key);
                        int deletedSensorActions = Koios.getDbHelper().deleteStudySensorActions(key);
                        Log.d(TAG, "deletedStudies:" + deletedStudies + ", deletedSensorConfigs:" + deletedSensorConfigs + ", deletedSensorActions:" + deletedSensorActions);

                        triggerSensorSyncing();
                    }


                }

                @Override
                public void onFailure(Call<ArrayList<KoiosStudy>> call, Throwable t) {
                    Log.d(TAG, "failed to sync data " + t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.d(TAG, "error in syncing data " + e.getMessage());
        }
    }

    private void syncSensorConfigsOfStudy(final int studyId) {
        Call<ArrayList<StudySensorConfig>> call = Koios.getService().getSensorConfigs(studyId);
        try {
            call.enqueue(new Callback<ArrayList<StudySensorConfig>>() {
                @Override
                public void onResponse(Call<ArrayList<StudySensorConfig>> call, final Response<ArrayList<StudySensorConfig>> response) {

                    HashMap<Integer, StudySensorConfig> localConfigs = new HashMap<>();
                    for (StudySensorConfig localConfig : Koios.getDbHelper().getSensorConfigsOfTheStudy(studyId)) {
                        localConfigs.put(localConfig.getId(), localConfig);
                    }

                    ArrayList<StudySensorConfig> serverConfigs = response.body();
                    for (int i = 0; i < serverConfigs.size(); i++) {
                        int serverConfigId = serverConfigs.get(i).getId();
                        String serverPublishTime = serverConfigs.get(i).getPublishTime() == null ? "" : serverConfigs.get(i).getPublishTime();
                        int serverPublishVersion = serverConfigs.get(i).getPublishedVersion();
                        if (localConfigs.containsKey(serverConfigId)) {
                            //config present in local db
                            String localPublishTime = localConfigs.get(serverConfigId).getPublishTime() == null ? "" : localConfigs.get(serverConfigId).getPublishTime();
                            int localPublishVersion = localConfigs.get(serverConfigId).getPublishedVersion();
                            if (serverPublishTime.equals(localPublishTime) && serverPublishVersion == localPublishVersion) {
                                //both are same, no need to update
                            } else {
                                Koios.getDbHelper().insertStudySensorConfig(serverConfigs.get(i));
                                //Sync Sensor Action
//                                triggerSensorSyncing();
                                syncStudySensorActions(studyId, serverConfigId);
                            }
                            localConfigs.remove(serverConfigId);
                        } else {
                            //config not present in local db, need to insert
                            Koios.getDbHelper().insertStudySensorConfig(serverConfigs.get(i));
                            //Sync Sensor Action
//                            triggerSensorSyncing();
                            syncStudySensorActions(studyId, serverConfigId);
                        }
                    }

                    //remove remaining items from hashmap and local db
                    for (int key : localConfigs.keySet()) {
                        Koios.getDbHelper().deleteStudySensorConfig(key);
                        Koios.getDbHelper().deleteStudySensorActions(studyId, key);
//                        triggerSensorSyncing();
                    }


                }

                @Override
                public void onFailure(Call<ArrayList<StudySensorConfig>> call, Throwable t) {

                }
            });
        } catch (Exception e) {

        }

    }

    private void syncStudySensorActions(final int studyId, final int configId) {
        Call<ArrayList<StudySensorAction>> call = Koios.getService().getSensorActions(studyId, configId);
        try {
            call.enqueue(new Callback<ArrayList<StudySensorAction>>() {
                @Override
                public void onResponse(Call<ArrayList<StudySensorAction>> call, final Response<ArrayList<StudySensorAction>> response) {

                    int deletedActions = Koios.getDbHelper().deleteStudySensorActions(studyId, configId);
                    Log.d(TAG, "deleted actions in syncStudySensorActions method:" + deletedActions);
                    ArrayList<StudySensorAction> actions = response.body();
                    for (StudySensorAction sensorAction : actions) {
                        Log.d(TAG, "inserting sensor action in syncStudySensorActions method : " + sensorAction.toString());
                        Koios.getDbHelper().insertStudySensorAction(sensorAction);
                    }

                }

                @Override
                public void onFailure(Call<ArrayList<StudySensorAction>> call, Throwable t) {

                }
            });
        } catch (Exception e) {

        }
    }


    private void syncSurveyConfigsOfStudy(final int studyId) {
        Log.d(TAG, "syncing surveys of study id:"+ studyId);
        Call<ArrayList<StudySurveyConfig>> call = Koios.getService().getSurveyConfigs(studyId);
        try {
            call.enqueue(new Callback<ArrayList<StudySurveyConfig>>() {
                @Override
                public void onResponse(Call<ArrayList<StudySurveyConfig>> call, final Response<ArrayList<StudySurveyConfig>> response) {
                    Log.d(TAG, "call success for sync survey");
                    HashMap<Integer, StudySurveyConfig> localConfigs = new HashMap<>();
                    for (StudySurveyConfig localConfig : Koios.getDbHelper().getSurveyConfigsOfTheStudy(studyId)) {
                        Log.d(TAG, "survey in local, id:" + localConfig.getId());
                        localConfigs.put(localConfig.getId(), localConfig);
                    }

                    ArrayList<StudySurveyConfig> serverConfigs = response.body();
                    Log.d(TAG, "surveys in server:" + serverConfigs.size());
                    for (int i = 0; i < serverConfigs.size(); i++) {
                        int serverSurveyId = serverConfigs.get(i).getId();
                        String serverModificationTime = serverConfigs.get(i).getModificationTime() == null ? "" : serverConfigs.get(i).getModificationTime();
                        String serverPublishTime = serverConfigs.get(i).getPublishTime() == null ? "" : serverConfigs.get(i).getPublishTime();
                        int serverPublishVersion = serverConfigs.get(i).getPublishedVersion();
                        Log.d(TAG, "server survey id:"+ serverSurveyId + ", server modif time:" + serverModificationTime +
                                ", server pub time:" + serverPublishTime + ", server pub version:" + serverPublishVersion);
                        if (localConfigs.containsKey(serverSurveyId)) {
                            //config present in local db
                            String localModificationTime = localConfigs.get(serverSurveyId).getModificationTime() == null ? "" : localConfigs.get(serverSurveyId).getModificationTime();
                            String localPublishTime = localConfigs.get(serverSurveyId).getPublishTime() == null ? "" : localConfigs.get(serverSurveyId).getPublishTime();
                            int localPublishVersion = localConfigs.get(serverSurveyId).getPublishedVersion();
                            Log.d(TAG, "local modif timee:"+ localModificationTime + ", local pub time:" + localPublishTime + ", local pub version:"+ localPublishVersion);
                            if (serverModificationTime.equals(localModificationTime) && serverPublishTime.equals(localPublishTime) && serverPublishVersion == localPublishVersion) {
                                //both are same, no need to update
                                Log.d(TAG, "local and server survey time is same, no need to update");
                            } else {
                                Log.d(TAG, "local and server survey time is not same, replacing local version");
                                Koios.getDbHelper().insertStudySurveyConfig(serverConfigs.get(i));
                                syncSurveyTasks(studyId, serverSurveyId);
                            }
                            localConfigs.remove(serverSurveyId);
                        } else {
                            //config not present in local db, need to insert
                            Log.d(TAG, "survey config not present in local db, need to insert");
                            Koios.getDbHelper().insertStudySurveyConfig(serverConfigs.get(i));
                            syncSurveyTasks(studyId, serverSurveyId);
                        }
                    }

                    //remove remaining items from hashmap and local db
                    Log.d(TAG, "remove remaining surveys from hashmap and local db");
                    for (int key : localConfigs.keySet()) {
                        Log.d(TAG, "remove survey for id:"+ key);
                        Koios.getDbHelper().deleteSurveyConfig(key);
                        Koios.getDbHelper().deleteSurveyTasks(studyId, key);
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<StudySurveyConfig>> call, Throwable t) {
                    Log.d(TAG, "call failure:" + t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.d(TAG, "exception:" + e.getMessage());
        }

    }


    private void syncSurveyTasks(final int studyId, final int surveyId) {
        Call<ArrayList<StudySurveyTask>> call = Koios.getService().getSurveyTasks(studyId, surveyId);
        try {
            call.enqueue(new Callback<ArrayList<StudySurveyTask>>() {
                @Override
                public void onResponse(Call<ArrayList<StudySurveyTask>> call, final Response<ArrayList<StudySurveyTask>> response) {

                    int deletedActions = Koios.getDbHelper().deleteStudySensorActions(studyId, surveyId);
                    Log.d(TAG, "deleted actions in syncStudySensorActions method:" + deletedActions);
                    ArrayList<StudySurveyTask> taskList = response.body();
                    for (StudySurveyTask task : taskList) {
//                        Log.d(TAG, "inserting survey task in sync method : " + sensorAction.toString());
                        Koios.getDbHelper().insertSurveyTask(task);
                    }

                }

                @Override
                public void onFailure(Call<ArrayList<StudySurveyTask>> call, Throwable t) {

                }
            });
        } catch (Exception e) {

        }
    }

}
