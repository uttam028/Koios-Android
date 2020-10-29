package org.mlab.research.koios.framework.sensor;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import org.mlab.research.koios.MainActivity;
import org.mlab.research.koios.R;

import androidx.core.app.NotificationCompat;
import androidx.lifecycle.MutableLiveData;

public class CoreSensorListener extends Service implements SensorEventListener {

    private static final String TAG = CoreSensorListener.class.getSimpleName() + "_debug";

    private static final String NOTIFICATION_TITLE = "Koios Sensor Engine";
    private static final String NOTIFICATION_TEXT = "Sensor Management Service";
    private static final String NOTIFICATION_CHANNEL_ID = "Koios-Sensor";
    private static final CharSequence NOTIFICATION_CHANNEL_NAME = "Koios-Sensor-Notification";
    private static final String NOTIFICATION_CHANNEL_DESC = "Foreground channel for Koios sensor service";


    private static SensorManager sensorManager;
    private static Sensor accelSensor;

    private static MutableLiveData<String> accelData = new MutableLiveData<>();



    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "on create method");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                3333, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setContentTitle(NOTIFICATION_TITLE)
                .setContentText(NOTIFICATION_TEXT)
                .setSmallIcon(R.drawable.appstore)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(3, notification);


        sensorManager = (SensorManager) getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        try {
            sensorManager.unregisterListener(this);
        }catch (Exception e){

        }


        //TODO: get accel frequency from core sensor action db table
        //for the time being set a temporary value
//        if (accelSensor != null){
//            double accelFreq = 10;
//            if (accelFreq > 0){
//                int accelDuration = (int)((1.0/accelFreq) * 1000 * 1000);
//                Log.d(TAG, "accel duration " + accelDuration + ", freq:" + accelFreq);
                sensorManager.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_NORMAL);
//                sensorManager.reg
//            }

//        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "on destroy method");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
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



    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

//        Message message = new Message();
//        message.arg1 = 1;
//        message.arg2 = sensorEvent.sensor.getType();
//        message.obj = sensorEvent;

//        looperThread.mHandler.dispatchMessage(message);

        StringBuilder builder = new StringBuilder();
        if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION){
            builder.append("accel").append(",").append(String.format("%f", event.values[0])).append(",").append(String.format("%f", event.values[1])).append(",").append(String.format("%f", event.values[2]));
        }else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE){
            builder.append("gyro").append(",").append(String.format("%f", event.values[0])).append(",").append(String.format("%f", event.values[1])).append(",").append(String.format("%f", event.values[2]));
        }else if (event.sensor.getType() == Sensor.TYPE_PRESSURE){
            builder.append("pressure").append(",").append(event.values[0]);
        }
//        Prosthesis.logData(builder.toString());
        accelData.postValue(builder.toString());
    }


    public static MutableLiveData<String> getCurrent(){
        return accelData;
    }

}
