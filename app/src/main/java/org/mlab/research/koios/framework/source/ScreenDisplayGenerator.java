package org.mlab.research.koios.framework.source;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import org.mlab.research.koios.Koios;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ScreenDisplayGenerator{

    private static final String TAG = ScreenDisplayGenerator.class.getName() + "_debug";
    private static final String SOURCE = "screen";

    private static MutableLiveData<String> screenDisplay = new MutableLiveData<>();

    private BroadcastReceiver displayState = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String message = "displaystate,";
            if(action.equals(intent.ACTION_SCREEN_OFF)) {
                message += "off";

                screenDisplay.postValue("off");
                Log.d(TAG, "Screen is off");
            }
            if(action.equals(intent.ACTION_SCREEN_ON)) {
                message += "on";
                screenDisplay.postValue("on");
                Log.d(TAG, "screen is on");
            }
            Log.d(TAG, "Display State Change - "+ message);
//            logData(message);
        }
    };

    public LiveData<String> getCurrent(){
        return screenDisplay;
    }


    public void start(){
        Koios.getContext().registerReceiver(displayState, new IntentFilter(Intent.ACTION_SCREEN_ON));
        Koios.getContext().registerReceiver(displayState, new IntentFilter(Intent.ACTION_SCREEN_OFF));
    }

    public void stop(){
        Koios.getContext().unregisterReceiver(displayState);
    }

    private static void broadcast(String message){

    }

//    @Override
//    protected void postValue(String value) {
//        super.postValue(value);
//    }
}
