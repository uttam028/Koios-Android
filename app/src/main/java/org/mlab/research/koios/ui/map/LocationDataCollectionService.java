package org.mlab.research.koios.ui.map;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.mlab.research.koios.Koios;
import org.mlab.research.koios.MainActivity;
import org.mlab.research.koios.R;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

public class LocationDataCollectionService extends Service {

    private static final String TAG = LocationDataCollectionService.class.getSimpleName() + "_debug";

    LocationRequest request;
    FusedLocationProviderClient client;
    LocationCallback callback;
    private static final long INTERVAL = 5 * 60 * 1000; //in milliseconds



    private static final String NOTIFICATION_TITLE = "Koios Visit Service";
    private static final String NOTIFICATION_TEXT = "Location Sensing in Progress..";
    private static final String NOTIFICATION_CHANNEL_ID = "Koios-Visit";
    private static final CharSequence NOTIFICATION_CHANNEL_NAME = "Koios-Visit-Notification";
    private static final String NOTIFICATION_CHANNEL_DESC = "Foreground channel for Koios visit service";

    private static WiFi currentWiFi = new WiFi("", "");
    private static final String SOURCE = "network";


    static class WiFi extends Object{
        boolean enabled;
        String SSID;
        String BSSID;

        public WiFi(String SSID, String BSSID){
            if (SSID==null||SSID.isEmpty()){
                this.enabled = false;
            }else{
                this.enabled = true;
            }
            this.SSID = SSID;
            this.BSSID = BSSID;
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            if (obj==this){
                return true;
            }
            if (obj == null || obj.getClass() != this.getClass()) {
                return false;
            }
            WiFi newWifi = (WiFi) obj;
            boolean state = (this.SSID.equals(newWifi.SSID) && this.BSSID.equals(newWifi.BSSID));
            return state;
        }
    }

    private BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            String action = intent.getAction();
            String message = "";

            if (action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {

//                Log.d(TAG, "current wifi ssid:" + currentWiFi.SSID + ", bssid:"+ currentWiFi.BSSID);
                if (wifiInfo.getNetworkId() == -1) {
//                    message = "Wifi Undetected";
                    WiFi temp = new WiFi("", "");
                    if (currentWiFi.equals(temp)){
                        //do nothing
//                        Log.d(TAG, "current state is same");
                    }else {
                        message = SOURCE + ",cell,,";
                        Log.d(TAG, message);
                        Koios.log(message);
//                        broadcast(message);
                        currentWiFi = temp;
//                        startPeriodicLocationUpdate();
                        getInstantLocation();
                    }

                } else {

                    WiFi temp = new WiFi(wifiInfo.getSSID(), wifiInfo.getBSSID());
                    if (currentWiFi.equals(temp)){
                        //do nothing
//                        Log.d(TAG, "current wifi is same with new one..");
                    }else {
                        currentWiFi = temp;
                        message = SOURCE+ ",wifi,"+ currentWiFi.SSID +"," + currentWiFi.BSSID;
                        if (!currentWiFi.BSSID.startsWith("00:00:00:")){
//                            Koios.log(message);
                            Log.d(TAG, message);
//                            broadcast(message);
//                            stopPeriodicLocationUpdate();
                            getInstantLocation();
                        }
                    }
                }
            }
        }
    };

    private void getInstantLocation(){
        if (ActivityCompat.checkSelfPermission(Koios.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(Koios.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            client = LocationServices.getFusedLocationProviderClient(Koios.getContext());
            Task<Location> task = client.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    Koios.log(SOURCE+ ",wifi,"+ currentWiFi.SSID +"," + currentWiFi.BSSID + "," + location.getLatitude()+ "," + location.getLongitude());

                    //TODO: insert location and wifi information to the location buffer
                    //current wifi information is available on currentWiFi objectr
                }
            });

        }
    }


    private void stopPeriodicLocationUpdate(){
        if (client != null && callback != null){

            client.removeLocationUpdates(callback);
        }
    }

    private void startPeriodicLocationUpdate(){
        if (ActivityCompat.checkSelfPermission(Koios.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(Koios.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            //Koios.sendNotification("Location Test 004", "start gps");
            if (client != null && callback != null){
                client.removeLocationUpdates(callback);
            }

            request = LocationRequest.create();
            request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            request.setInterval(INTERVAL); //in milliseconds

            client = LocationServices.getFusedLocationProviderClient(Koios.getContext());


            callback = new LocationCallback(){
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    if (locationResult != null){

                        float minAccuracy  = Float.MAX_VALUE;
                        Location bestLocation = null;
                        for (int i=0;i<locationResult.getLocations().size();i++){


                            Location location = locationResult.getLocations().get(i);
                            if (location.getAccuracy() < minAccuracy){
                                bestLocation = location;
                            }
                        }
                        //LocationTest.sendNotification("Location update", "count " + locationResult.getLocations().size());
                        if (bestLocation != null){
                            StringBuilder builder = new StringBuilder();
                            builder.append("gps").append(",").append(bestLocation.getLatitude()).append(",")
                                    .append(bestLocation.getLongitude()).append(",").append(bestLocation.getAccuracy())
                                    .append(",").append(bestLocation.getAltitude());
                            Koios.log(builder.toString());

                            //TODO: insert location and wifi information to the location buffer
                            //current wifi information is available on currentWiFi object
                        }
                    }
                }
            };

            client.requestLocationUpdates(request, callback, null);
        }

    }


    @Override
    public void onCreate() {
        super.onCreate();
        Koios.getContext().registerReceiver(wifiReceiver, new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                1111, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setContentTitle(NOTIFICATION_TITLE)
                .setContentText(NOTIFICATION_TEXT)
                .setSmallIcon(R.drawable.appstore)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);


        startPeriodicLocationUpdate();
        return START_STICKY;
//        return super.onStartCommand(intent, flags, startId);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, importance);
            channel.setDescription(NOTIFICATION_CHANNEL_DESC);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        Log.d(TAG, "notification channel created");
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopPeriodicLocationUpdate();
        client = null;
        callback = null;
        Koios.getContext().unregisterReceiver(wifiReceiver);
    }

}
