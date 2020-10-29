package org.mlab.research.koios.ui.map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.mlab.research.koios.Koios;

import java.util.ArrayList;

public class MapDBHelper extends SQLiteOpenHelper {

    private static final String TAG = MapDBHelper.class.getName() + "_debug";
    private static final String DATABASE_NAME = "MapDB";
    private static final int DATABASE_VERSION = 1;

    //TODO: Table Names
    //Table location buffer Name
    private static final String TABLE_LOCATION_BUFFER = "location_buffer";

    //Table wifi location mapping Name
    private static final String TABLE_WIFI_LOCATION_MAPPING = "wifi_location_mapping";

    //Table Visit History Name
    private static final String TABLE_VISIT_HISTORY = "visit_history";

    //TODO: Column Names
    //Location Buffer Table - Column names
    private static final String COLUMN_BUFFER_TIME_STAMP = "time_stamp";
    private static final String COLUMN_BUFFER_TIME_ZONE = "time_zone";
    private static final String COLUMN_BUFFER_SSID = "ssid";
    private static final String COLUMN_BUFFER_BSSID = "bssid";
    private static final String COLUMN_BUFFER_LATITUDE = "latitude";
    private static final String COLUMN_BUFFER_LONGITUDE = "longitude";
    private static final String COLUMN_BUFFER_ACCURACY = "accuracy";

    //Table wifi location mapping - Column names
    private static final String COLUMN_MAPPING_BSSID = "bssid";
    private static final String COLUMN_MAPPING_SSID = "ssid";
    private static final String COLUMN_MAPPING_PLACE_NAME = "place_name";
    private static final String COLUMN_MAPPING_PLACE_ID = "place_id";
    private static final String COLUMN_MAPPING_LATITUDE = "latitude";
    private static final String COLUMN_MAPPING_LONGITUDE = "longitude";
    private static final String COLUMN_MAPPING_ACCURACY = "accuracy";
    private static final String COLUMN_MAPPING_COUNTER = "counter";

    //Table Visit History unique column names
    private static final String COLUMN_VISIT_START_TIME = "start_time";
    private static final String COLUMN_VISIT_END_TIME = "end_time";
    private static final String COLUMN_VISIT_PLACE_ID = "place_id";
    private static final String COLUMN_VISIT_PLACE_NAME = "place_name";
    private static final String COLUMN_VISIT_LATITUDE = "latitude";
    private static final String COLUMN_VISIT_LONGITUDE = "longitude";



    //TODO: Create Tables
    //Create Tables
    private static final String CREATE_TABLE_LOCATION_BUFFER = "create table " + TABLE_LOCATION_BUFFER + " (" + COLUMN_BUFFER_TIME_STAMP + " integer, "
            + COLUMN_BUFFER_TIME_ZONE + " text not null, " + COLUMN_BUFFER_SSID + " text not null, " + COLUMN_BUFFER_BSSID + " text not null, " + COLUMN_BUFFER_LATITUDE + " real, "
            + COLUMN_BUFFER_LONGITUDE + " real, " + COLUMN_BUFFER_ACCURACY + " integer)";


    private static final String CREATE_TABLE_WIFI_LOCATION_MAPPING = "create table " + TABLE_WIFI_LOCATION_MAPPING + " (" + COLUMN_MAPPING_BSSID + " text not null, "
            + COLUMN_MAPPING_SSID + " text not null, " + COLUMN_MAPPING_PLACE_NAME + " text not null, " + COLUMN_MAPPING_PLACE_ID + " text not null, " + COLUMN_MAPPING_LATITUDE + " real, "
            + COLUMN_MAPPING_LONGITUDE + " real, " + COLUMN_MAPPING_ACCURACY + " integer, " + COLUMN_MAPPING_COUNTER + " integer)";

    private static final String CREATE_TABLE_VISIT_HISTORY = "create table " + TABLE_VISIT_HISTORY + " (" + COLUMN_VISIT_START_TIME + " text not null, " + COLUMN_VISIT_END_TIME + " text not null, "
            + COLUMN_VISIT_PLACE_ID + " text not null, " + COLUMN_VISIT_PLACE_NAME + " text not null, " + COLUMN_VISIT_LATITUDE + " real, " + COLUMN_VISIT_LONGITUDE + " real)";


    //TODO: Drop Tables
    //Drop Tables
    private static final String DELETE_TABLE_LOCATION_BUFFER = "drop table if exists " + TABLE_LOCATION_BUFFER;

    private static final String DELETE_TABLE_WIFI_LOCATION_MAPPING = "drop table if exists " + TABLE_WIFI_LOCATION_MAPPING;

    private static final String DELETE_TABLE_VISIT_HISTORY = "drop table if exists " + TABLE_VISIT_HISTORY;


    public MapDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //TODO: on create function
        db.execSQL(CREATE_TABLE_LOCATION_BUFFER);
        db.execSQL(CREATE_TABLE_WIFI_LOCATION_MAPPING);
        db.execSQL(CREATE_TABLE_VISIT_HISTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //TODO: on upgrade function
        db.execSQL(DELETE_TABLE_LOCATION_BUFFER);
        db.execSQL(DELETE_TABLE_WIFI_LOCATION_MAPPING);
        db.execSQL(DELETE_TABLE_VISIT_HISTORY);
        onCreate(db);

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    public void insertIntoBuffer(VisitLocationEvent locationEvent){
        //TODO: insert data into location buffer table
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_BUFFER_TIME_STAMP, locationEvent.getTimestamp());
        cv.put(COLUMN_BUFFER_TIME_ZONE, locationEvent.getTimezone());
        cv.put(COLUMN_BUFFER_SSID, locationEvent.getSsid());
        cv.put(COLUMN_BUFFER_BSSID, locationEvent.getBssid());
        cv.put(COLUMN_BUFFER_LATITUDE, locationEvent.getLatitude());
        cv.put(COLUMN_BUFFER_LONGITUDE, locationEvent.getLongitude());

//        Koios.log(locationEvent.getTimestamp() + "," + locationEvent.getTimezone() + "," + locationEvent.getSsid()+ ","
//        + locationEvent.getBssid() + "," + locationEvent.getLatitude() + "," + locationEvent.getLongitude());

        db.insert(TABLE_LOCATION_BUFFER, null, cv);
    }

    public ArrayList<VisitLocationEvent> getLocationsFromBuffer(){
        //TODO: read all data from buffer
        ArrayList<VisitLocationEvent> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + TABLE_LOCATION_BUFFER;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            String timestamp = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BUFFER_TIME_STAMP));
            String timezone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BUFFER_TIME_ZONE));
            String ssid = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BUFFER_SSID));
            String bssid = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BUFFER_BSSID));
            double latitude = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_BUFFER_LATITUDE));
            double longitude = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_BUFFER_LONGITUDE));
            int accuracy = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BUFFER_ACCURACY));

            VisitLocationEvent visitLocationEvent = new VisitLocationEvent(timestamp, timezone, ssid, bssid, latitude, longitude, accuracy);
            list.add(visitLocationEvent);
        }
        cursor.close();
        return list;
    }

    public void deleteRecordsFromBuffer(int threshold){
        //TODO: delete records older than the time threshold (in milliseconds)
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "delete from " + TABLE_LOCATION_BUFFER + " where " + COLUMN_BUFFER_TIME_STAMP + " < " + (System.currentTimeMillis() - threshold);
        db.rawQuery(query, null);
    }

    public void insertWiFiLocation(WiFiLocation wifiLocation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MAPPING_BSSID, wifiLocation.getBssid());
        values.put(COLUMN_MAPPING_SSID, wifiLocation.getSsid());
        values.put(COLUMN_MAPPING_PLACE_NAME, wifiLocation.getPlaceName());
        values.put(COLUMN_MAPPING_PLACE_ID, wifiLocation.getPlaceId());
        values.put(COLUMN_MAPPING_LATITUDE, wifiLocation.getLatitude());
        values.put(COLUMN_MAPPING_LONGITUDE, wifiLocation.getLongitude());
        values.put(COLUMN_MAPPING_ACCURACY, wifiLocation.getAccuracy());
        values.put(COLUMN_MAPPING_COUNTER, wifiLocation.getCounter());

        long replaced_rows = db.update(TABLE_WIFI_LOCATION_MAPPING, values, COLUMN_MAPPING_BSSID + "=?", new String[]{wifiLocation.getBssid()});

        if (replaced_rows == 0) {
            db.insert(TABLE_WIFI_LOCATION_MAPPING, null, values);
        }
        db.close();
    }

}
