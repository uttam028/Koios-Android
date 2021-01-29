package org.mlab.research.koios;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Util {

    private static final String TAG = Util.class.getSimpleName() + "_debug";

    public static final int ENROLL_REQUEST_CODE=100;
    public static final int STUDY_ENROLLMENT_SUCCESS = 101;
    public static final int STUDY_ENROLLMENT_FAILURE = -101;

    public static final int LEAVE_REQUEST_CODE = 102;
    public static final int STUDY_LEAVE_SUCCESS = 103;
    public static final int STUDY_LEAVE_FAILURE = -103;

    public static final int SURVEY_SUBMIT_REQUEST = 104;
    public static final int SURVEY_SUBMIT_SUCCESS = 105;
    public static final int SURVEY_SUBMIT_FAILURE = -105;

    public static final int RECORDING_UPLOAD_REQUEST = 106;
    public static final int RECORDING_UPLOAD_SUCCESS = 107;
    public static final int RECORDING_UPLOAD_FAILURE = -107;

    public static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;


    static String baseUrl() {
        //return "https://koiosplatform.com/mcsweb/cimoninterface/";
        return "https://koiosplatform.com/cimoninterface/";
    }


    public static void saveDataToSharedPref(String key, String value) {
        //PreferenceManager.getDefaultSharedPreferences()
        Context context = Koios.getContext();
        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.sharedPrefFileName), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void saveDataToSharedPrefLast3Resp(String key, Set<String> value) {
        //PreferenceManager.getDefaultSharedPreferences()
        Context context = Koios.getContext();
        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.sharedPrefFileName), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(key, value);
        editor.apply();
    }

    public static String getPreferenceData(String key) {
        Context context = Koios.getContext();
        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.sharedPrefFileName), Context.MODE_PRIVATE);
        return prefs.getString(key, "");

    }

    public static Set<String> getPreferenceDataLast3Resp(String key) {
        Context context = Koios.getContext();
        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.sharedPrefFileName), Context.MODE_PRIVATE);
        return prefs.getStringSet(key, null);
    }

    public static String getUniqueDeviceId() {
        Context context = Koios.getContext();
        String uuid = getPreferenceData(context.getString(R.string.uuid));
        if (uuid.isEmpty()) {
            uuid = UUID.randomUUID().toString();
            saveDataToSharedPref(context.getString(R.string.uuid), uuid);
        }
        return uuid;
    }

    static String getAndroidVersion() {
        String release = Build.VERSION.RELEASE;
        int sdkVersion = Build.VERSION.SDK_INT;
//        String codeName = Build.VERSION.CODENAME;
        return "sdk:" + sdkVersion + "_release:" + release;// + "_codename:" + codeName;
    }

    static boolean isWiFiConnected() {
        Context context = Koios.getContext();
        ConnectivityManager manager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (manager == null) {
            return false;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network network = manager.getActiveNetwork();
            NetworkCapabilities capabilities = manager.getNetworkCapabilities(network);
            if (capabilities == null) {
                return false;
            }
            return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
        } else {
            NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (networkInfo == null) {
                return false;
            }
            return networkInfo.isConnected();
        }
    }

    static String getConnectionStatus() {
        Context context = Koios.getContext();
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (manager == null) {
            return "";
        }

        Network network = manager.getActiveNetwork();
        NetworkCapabilities capabilities = manager.getNetworkCapabilities(network);
        if (capabilities == null) {
            return "";
        }

        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
            return "wifi";
        } else if(capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)){
            return "cellular";
        }else {
            return "";
        }
    }

    static void uploadPing(){
        //upload ping
        try {
            String uuid = Util.getUniqueDeviceId();
            String email = Util.getPreferenceData(Koios.getContext().getString(R.string.userEmail));
            String network = Util.getConnectionStatus();
            String osType = "android";
            String osVersion = Util.getAndroidVersion();
            String appVersion = BuildConfig.VERSION_NAME;
            String data = isBatteryOptimized() ? "App Optimized" : "App Not Optimized";
            Log.d(TAG, "uuid:" + uuid + ", email:" + email + ", network:" + network + "ostype:" + osType + ", os version:" + osVersion + ", app version:" + appVersion);
            Call<CimonResponse> call = Koios.getService().pingToPlatform(email, uuid, network, osType, osVersion, appVersion, data);
            call.enqueue(new Callback<CimonResponse>() {
                @Override
                public void onResponse(Call<CimonResponse> call, Response<CimonResponse> response) {
                    Log.d(TAG, "success to call ping service");
                }

                @Override
                public void onFailure(Call<CimonResponse> call, Throwable t) {
                    Log.d(TAG, "Failed to call ping service");
                }
            });
        }catch (Exception e){
            Log.d(TAG, "Exception while sending ping data " + e.getMessage());

        }

    }

    static void uploadPing(String directory){
        //upload ping
        try {
            String uuid = Util.getUniqueDeviceId();
            String email = Util.getPreferenceData(Koios.getContext().getString(R.string.userEmail));
            String network = Util.getConnectionStatus();
            String osType = "android";
            String osVersion = Util.getAndroidVersion();
            String appVersion = BuildConfig.VERSION_NAME;
            String data = isBatteryOptimized() ? "App Optimized" : "App Not Optimized";
            String compressedData = getSizeOfFiles(directory, "zip");
            String uncompressedData = getSizeOfFiles(directory, "log");

            data = data + ", " + getSensorConfig();

            data = data + ", Compressed: " + compressedData + ", Uncompressed: " + uncompressedData;

            Log.d(TAG, "uuid:" + uuid + ", email:" + email + ", network:" + network + "ostype:" + osType + ", os version:" + osVersion + ", app version:" + appVersion);
            Call<CimonResponse> call = Koios.getService().pingToPlatform(email, uuid, network, osType, osVersion, appVersion, data);
            call.enqueue(new Callback<CimonResponse>() {
                @Override
                public void onResponse(Call<CimonResponse> call, Response<CimonResponse> response) {
                    Log.d(TAG, "success to call ping service");
                }

                @Override
                public void onFailure(Call<CimonResponse> call, Throwable t) {
                    Log.d(TAG, "Failed to call ping service");
                }
            });
        }catch (Exception e){
            Log.d(TAG, "Exception while sending ping data " + e.getMessage());

        }

    }


    static void uploadTokenIfRequired(){
        try {
            String fcmStatus = getPreferenceData("is_fcm_token_uploaded");
            if (fcmStatus == "true"){
                Log.d(TAG, "Token is already uploaded, no action required");
            }else {
                String uuid = Util.getUniqueDeviceId();
                String email = Util.getPreferenceData(Koios.getContext().getString(R.string.userEmail));
                String token = getPreferenceData("fcm_token");
                Log.d(TAG, "uuid:" + uuid + ", email:" + email + ", token:" + token);
                if (!token.isEmpty() && !email.isEmpty()){
                    Call<CimonResponse> call = Koios.getService().uploadToken(email, uuid, token);
                    call.enqueue(new Callback<CimonResponse>() {
                        @Override
                        public void onResponse(Call<CimonResponse> call, Response<CimonResponse> response) {
                            Log.d(TAG, "success to call fcm service "+ response.toString());
                            if (response.body().getCode() == 0){
                                saveDataToSharedPref("is_fcm_token_uploaded", "true");
                            }else {
                                saveDataToSharedPref("is_fcm_token_uploaded", "false");
                            }
                        }

                        @Override
                        public void onFailure(Call<CimonResponse> call, Throwable t) {
                            Log.d(TAG, "Failed to call fcm service");
                            saveDataToSharedPref("is_fcm_token_uploaded", "false");
                        }
                    });

                }

            }


        }catch (Exception e){

        }
    }

    public static boolean isBatteryOptimized(){
        PowerManager manager = (PowerManager) Koios.getContext().getSystemService(Context.POWER_SERVICE);
        String name = Koios.getContext().getPackageName();
        Log.d(TAG, "package name:" + name);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return !manager.isIgnoringBatteryOptimizations(name);
        }
        return false;
    }

    public static int numberOfFile(String path, String fileType){
        File directory = new File(path);
        if (directory.exists()) {
            File[] files = directory.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File file, String name) {
                    return name.endsWith(fileType);
                }
            });
            return files.length;
        }
        return 0;
    }

    public static String getSizeOfFiles(String path, String fileType){
        File directory = new File(path);
        if (directory.exists()){
            File [] files = directory.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File file, String name) {
                    return name.endsWith(fileType);
                }
            });
            long bytes = 0;
            for (File file:files){
                bytes += file.length();
            }
            //greater than 1 MB
            if (bytes > 1024 * 1024){
                return (int)(bytes/(1024 * 1024)) + " MB";
            }

            //greater than 1 KB
            if (bytes > 1024){
                return (int)(bytes/1024) + " KB";
            }
            return bytes + " B";
        }
        return "0 B";
    }

    public static String getCurrentTimeUptoMillisAndZone(){
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"),
                Locale.getDefault());
        DateFormat formatUptoZone = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSSS Z");
        Date currentLocalTime = calendar.getTime();
        String localTime = formatUptoZone.format(currentLocalTime);
        return localTime;
    }

    public static String getCurrentTimeUptoMillisAndZone(long timestamp){
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"),
                Locale.getDefault());
        DateFormat formatUptoZone = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSSS Z");
        String localTime = formatUptoZone.format(timestamp);
        return localTime;
    }

    public static String getCurrentTimeUptoSecond(){
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"),
                Locale.getDefault());
        Date currentLocalTime = calendar.getTime();
        DateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String localTime = date.format(currentLocalTime);
        return localTime;
    }

    public static int getDayDifference(String givenDate){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date given = sdf.parse(givenDate);
//            Date current = sdf.parse()
//            String currentTime = sdf.format(new Date());
            long difference = new Date().getTime() - given.getTime();
            long differenceInDays = TimeUnit.MILLISECONDS.toDays(difference);
            return (int)differenceInDays;
        }catch (Exception e){

        }
        return -100;
    }


    public static boolean isValidDate(String givenDate){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date given = sdf.parse(givenDate);
            return true;
        }catch (Exception e){

        }
        return false;
    }

    public static int handleSpecialCase(int surveyId){
        if (surveyId==22){
            String eventKey = "survey-23-last3responses";
            Set<String> lastResponseOfEvent = getPreferenceDataLast3Resp(eventKey);

            Log.d("test", "null");
            if (lastResponseOfEvent != null) {
                for (String time : lastResponseOfEvent) {
                    Log.d("values_stored", "this " + time);
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                        int differenceInDaysEventSurvey = getDayDifference(time);
                        if (differenceInDaysEventSurvey > -1 && differenceInDaysEventSurvey < 3) {
                            //return R.color.colorError;
                            continue;
                        }

                    } catch (Exception e) {

                    }
                }
            }

        }
        return R.color.colorNdBlue;
    }

    private static String getSensorConfig(){

//        CoreSensorAction accelAction = Prosthesis.getDbHelper().getCoreSensorAction("accel");
//        CoreSensorAction gyroAction = Prosthesis.getDbHelper().getCoreSensorAction("gyro");
//        CoreSensorAction pressureAction = Prosthesis.getDbHelper().getCoreSensorAction("pressure");
//        CoreSensorAction locationAction = Prosthesis.getDbHelper().getCoreSensorAction("location");

        String result = "";
//        if (locationAction!=null){
//            double freq = locationAction.getFrequency();
//            boolean enabled = locationAction.getIsEnabled()==0?false:true;
//            result += ("location," + String.valueOf(enabled)+ "," + freq + "|");
//        }
//        if (accelAction!=null){
//            double freq = accelAction.getFrequency();
//            boolean enabled = accelAction.getIsEnabled()==0?false:true;
//            result += ("accel," + String.valueOf(enabled)+ "," + freq + "|");
//        }
//        if (gyroAction!=null){
//            double freq = gyroAction.getFrequency();
//            boolean enabled = gyroAction.getIsEnabled()==0?false:true;
//            result += ("gyro," + String.valueOf(enabled)+ "," + freq + "|");
//        }
//        if (pressureAction!=null){
//            double freq = pressureAction.getFrequency();
//            boolean enabled = pressureAction.getIsEnabled()==0?false:true;
//            result += ("pressure," + String.valueOf(enabled)+ "," + freq + "|");
//        }
//        if (result.endsWith("|")){
//            result = result.substring(0, result.length()-1);
//        }
        return result;
    }
}
