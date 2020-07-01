package org.mlab.research.koios.ui.map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

class MapDBHelper extends SQLiteOpenHelper {

    private static final String TAG = MapDBHelper.class.getName() + "_debug";
    private static final String DATABASE_NAME = "MapDB";
    private static final int DATABASE_VERSION = 1;

    //TODO: Table Names
    private static final String TABLE_LOCATION_BUFFER = "location_buffer";

    //TODO: Column Names



    //TODO: Create Tables

    //TODO: Drop Tables

    public MapDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //TODO: on create function
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //TODO: on upgrade function
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    public void insertIntoBuffer(VisitLocationEvent locationEvent){
        //TODO: insert data into location buffer table
    }

    public ArrayList<VisitLocationEvent> getLocationsFromBuffer(){
        //TODO: read all data from buffer
        return null;
    }

    public void deleteRecordsFromBuffer(int threshold){
        //TODO: delete records older than the time threshold (in milliseconds)
    }
}
