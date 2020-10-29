package org.mlab.research.koios.framework.engine;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import org.mlab.research.koios.Koios;
import org.mlab.research.koios.MainActivity;
import org.mlab.research.koios.R;
import org.mlab.research.koios.framework.sensor.CoreSensorListener;
import org.mlab.research.koios.framework.source.ScreenDisplayGenerator;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.Observer;

public class RuleManagementService extends Service {

    private final static String TAG = RuleManagementService.class.getName() + "_debug";

    private static final String NOTIFICATION_TITLE = "Koios Rule Engine";
    private static final String NOTIFICATION_TEXT = "Rule Management Service";
    private static final String NOTIFICATION_CHANNEL_ID = "Koios-Rule";
    private static final CharSequence NOTIFICATION_CHANNEL_NAME = "Koios-Rule-Notification";
    private static final String NOTIFICATION_CHANNEL_DESC = "Foreground channel for Koios rule service";

    private ScreenDisplayGenerator screenDisplaySource;

    @Override
    public void onCreate() {
        super.onCreate();
        screenDisplaySource = new ScreenDisplayGenerator();
//        screenDisplaySource.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                2222, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setContentTitle(NOTIFICATION_TITLE)
                .setContentText(NOTIFICATION_TEXT)
                .setSmallIcon(R.drawable.appstore)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);


        final Observer<String> displayObserver = new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d(TAG, "receive display state from screen live data " + s);
                if (s.equalsIgnoreCase("off")){
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(Koios.getContext(), NOTIFICATION_CHANNEL_ID)
                            .setContentTitle("Loop Test")
                            .setContentText("Screen is off")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setSmallIcon(R.drawable.appstore);
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(Koios.getContext());

// notificationId is a unique int for each notification that you must define
                    notificationManager.notify((int)System.currentTimeMillis(), builder.build());
                }
            }
        };
        if (screenDisplaySource != null){
            screenDisplaySource.getCurrent().observeForever(displayObserver);
        }

//        final Observer<String> accelObserver = new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                Log.d(TAG, "live accel data " + s);
//            }
//        };
//
//        CoreSensorListener.getCurrent().observeForever(accelObserver);

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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

}
