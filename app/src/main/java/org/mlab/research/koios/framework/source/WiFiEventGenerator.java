package org.mlab.research.koios.framework.source;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import org.mlab.research.koios.Koios;

import androidx.annotation.Nullable;

public class WiFiEventGenerator {

    private static final String TAG = WiFiEventGenerator.class.getName() + "_debug";
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
//            Log.d(TAG, "object equality check "+ state + ", this ssid:" + this.SSID + ", new ssid:" + newWifi.SSID
//                    + ", this bssid:"+this.BSSID + ", new bssid:"+ newWifi.BSSID);
            return state;
        }
    }

    private static BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
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
//                        Koios.log(message);
                        broadcast(message);
                        currentWiFi = temp;
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
                            broadcast(message);
                        }
                    }
                }
            }
        }
    };

    public void start(){
        Koios.getContext().registerReceiver(wifiReceiver, new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION));
    }

    public void stop(){
        Koios.getContext().unregisterReceiver(wifiReceiver);
    }

    private static void broadcast(String message){

    }

}
