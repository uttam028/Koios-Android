package org.mlab.research.koios.framework.action;

import android.util.Log;

public class DataLogging extends KoiosAction {

    private static final String TAG = DataLogging.class.getName() + "_debug";

    @Override
    public void takeAction() {
        super.takeAction();
    }

    public void takeAction(String message){
        Log.d(TAG, message);
    }
}
