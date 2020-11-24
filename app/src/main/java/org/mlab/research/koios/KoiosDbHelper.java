package org.mlab.research.koios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class KoiosDbHelper extends SQLiteOpenHelper {

    private static final String TAG = KoiosDbHelper.class.getSimpleName() + "_debug";
    private static final String DATABASE_NAME = "Koios";
    private static final int DATABASE_VERSION = 2;

    //Study Table-----------------------------------------------------------------------------------
    private static final String TABLE_STUDY = "study";

    //Study Table - Column Names
    private static final String COLUMN_STUDY_ID = "study_id";
    private static final String COLUMN_STUDY_NAME = "name";
    private static final String COLUMN_STUDY_ORGANIZATION = "organization";
    private static final String COLUMN_STUDY_DESCRIPTION = "description";
    private static final String COLUMN_STUDY_MODIFICATION_TIME = "modification_time";
    private static final String COLUMN_STUDY_STATE = "state";
    private static final String COLUMN_STUDY_TYPE = "study_type";
    private static final String COLUMN_STUDY_INSTRUCTION = "instruction";
    private static final String COLUMN_STUDY_ICON_URL = "icon_url";

    //Study Table - Create Entry
    private static final String CREATE_TABLE_STUDY = "create table " + TABLE_STUDY
            + "("+ COLUMN_STUDY_ID +" integer primary key, "+ COLUMN_STUDY_NAME + " text, " + COLUMN_STUDY_ORGANIZATION + " text, " + COLUMN_STUDY_DESCRIPTION + " text,"
            + COLUMN_STUDY_MODIFICATION_TIME + " text not null, "+ COLUMN_STUDY_STATE +" integer default 0, "+ COLUMN_STUDY_TYPE +" integer default 1, "+ COLUMN_STUDY_INSTRUCTION
            +" text, "+ COLUMN_STUDY_ICON_URL +" text)";

    //Study Table - Delete Entry
    private static final String DELETE_TABLE_STUDY = "drop table if exists " + TABLE_STUDY;


    //Table Study Sensor Config---------------------------------------------------------------------
    private static final String TABLE_STUDY_SENSOR_CONFIG = "study_sensor_config";

    //Study Sensor Config - Column Names
    private static final String COLUMN_STUDY_SENSOR_CONFIG_ID = "id";
    private static final String COLUMN_STUDY_SENSOR_CONFIG_STUDY_ID = "study_id";
    private static final String COLUMN_STUDY_SENSOR_CONFIG_NAME = "name";
    private static final String COLUMN_STUDY_SENSOR_CONFIG_DESCRIPTION = "description";
    private static final String COLUMN_STUDY_SENSOR_CONFIG_CREATED_BY = "created_by";
    private static final String COLUMN_STUDY_SENSOR_CONFIG_CREATION_TIME = "creation_time";
    private static final String COLUMN_STUDY_SENSOR_CONFIG_CREATION_TIME_ZONE = "creation_time_zone";
    private static final String COLUMN_STUDY_SENSOR_CONFIG_MODIFICATION_TIME = "modification_time";
    private static final String COLUMN_STUDY_SENSOR_CONFIG_MODIFICATION_TIME_ZONE = "modification_time_zone";
    private static final String COLUMN_STUDY_SENSOR_CONFIG_PUBLISH_TIME = "publish_time";
    private static final String COLUMN_STUDY_SENSOR_CONFIG_PUBLISH_TIME_ZONE = "publish_time_zone";
    private static final String COLUMN_STUDY_SENSOR_CONFIG_PUBLISHED_VERSION = "published_version";
    private static final String COLUMN_STUDY_SENSOR_CONFIG_STATE = "state";
    private static final String COLUMN_STUDY_SENSOR_CONFIG_RESPONSE_COUNT = "response_count";
    private static final String COLUMN_STUDY_SENSOR_CONFIG_START_TIME = "start_time";
    private static final String COLUMN_STUDY_SENSOR_CONFIG_START_TIME_ZONE = "start_time_zone";
    private static final String COLUMN_STUDY_SENSOR_CONFIG_END_TIME = "end_time";
    private static final String COLUMN_STUDY_SENSOR_CONFIG_END_TIME_ZONE = "end_time_zone";
    private static final String COLUMN_STUDY_SENSOR_CONFIG_SCHEDULE = "schedule";

    //Study Sensor Config - Create Entry
    private static final String CREATE_TABLE_STUDY_SENSOR_CONFIG = "create table " + TABLE_STUDY_SENSOR_CONFIG + "(" + COLUMN_STUDY_SENSOR_CONFIG_ID + " integer primary key, "
            + COLUMN_STUDY_SENSOR_CONFIG_STUDY_ID + " integer, " + COLUMN_STUDY_SENSOR_CONFIG_NAME + " text, " + COLUMN_STUDY_SENSOR_CONFIG_DESCRIPTION + " text, "
            + COLUMN_STUDY_SENSOR_CONFIG_CREATED_BY + " text, " + COLUMN_STUDY_SENSOR_CONFIG_CREATION_TIME + " text, " + COLUMN_STUDY_SENSOR_CONFIG_CREATION_TIME_ZONE + " text, "
            + COLUMN_STUDY_SENSOR_CONFIG_MODIFICATION_TIME + " text, " + COLUMN_STUDY_SENSOR_CONFIG_MODIFICATION_TIME_ZONE + " text, " + COLUMN_STUDY_SENSOR_CONFIG_PUBLISH_TIME + " text, "
            + COLUMN_STUDY_SENSOR_CONFIG_PUBLISH_TIME_ZONE + " text, " + COLUMN_STUDY_SENSOR_CONFIG_PUBLISHED_VERSION + " text, " + COLUMN_STUDY_SENSOR_CONFIG_STATE + " integer, "
            + COLUMN_STUDY_SENSOR_CONFIG_RESPONSE_COUNT + " integer, " + COLUMN_STUDY_SENSOR_CONFIG_START_TIME + " text, " + COLUMN_STUDY_SENSOR_CONFIG_START_TIME_ZONE + " text, "
            + COLUMN_STUDY_SENSOR_CONFIG_END_TIME + " text, " + COLUMN_STUDY_SENSOR_CONFIG_END_TIME_ZONE + " text, " + COLUMN_STUDY_SENSOR_CONFIG_SCHEDULE + " text)";

    //Study Sensor Config - Delete Entry
    private static final String DELETE_TABLE_STUDY_SENSOR_CONFIG = "drop table if exists " + TABLE_STUDY_SENSOR_CONFIG;


    //Table Study Sensor Action---------------------------------------------------------------------
    private static final String TABLE_STUDY_SENSOR_ACTION = "study_sensor_action";

    //Study Sensor Action - Column Names
    private static final String COLUMN_STUDY_SENSOR_ACTION_STUDY_ID = "study_id";
    private static final String COLUMN_STUDY_SENSOR_ACTION_CONFIG_ID = "config_id";
    private static final String COLUMN_STUDY_SENSOR_ACTION_VERSION = "version";
    private static final String COLUMN_STUDY_SENSOR_ACTION_CODE = "action_code";
    private static final String COLUMN_STUDY_SENSOR_ACTION_TYPE = "type";
    private static final String COLUMN_STUDY_SENSOR_ACTION_ENABLED = "is_enabled";
    private static final String COLUMN_STUDY_SENSOR_ACTION_FREQUENCY = "frequency";
    private static final String COLUMN_STUDY_SENSOR_ACTION_TIME_BOUND = "time_bound";
    private static final String COLUMN_STUDY_SENSOR_ACTION_BATTERY_BOUND = "battery_bound";
    private static final String COLUMN_STUDY_SENSOR_ACTION_PARAM1 = "param1";
    private static final String COLUMN_STUDY_SENSOR_ACTION_PARAM2 = "param2";
    private static final String COLUMN_STUDY_SENSOR_ACTION_PARAM3 = "param3";

    //Study Sensor Action - Create Entry
    private static final String CREATE_TABLE_STUDY_SENSOR_ACTION = "create table " + TABLE_STUDY_SENSOR_ACTION + "(" + COLUMN_STUDY_SENSOR_ACTION_STUDY_ID + " integer, "
            + COLUMN_STUDY_SENSOR_ACTION_CONFIG_ID + " integer, " + COLUMN_STUDY_SENSOR_ACTION_VERSION + " integer, " + COLUMN_STUDY_SENSOR_ACTION_CODE + " text not null, "
            + COLUMN_STUDY_SENSOR_ACTION_TYPE + " text, " + COLUMN_STUDY_SENSOR_ACTION_ENABLED + " integer, " + COLUMN_STUDY_SENSOR_ACTION_FREQUENCY + " real, "
            + COLUMN_STUDY_SENSOR_ACTION_TIME_BOUND + " text, " + COLUMN_STUDY_SENSOR_ACTION_BATTERY_BOUND + " text, " + COLUMN_STUDY_SENSOR_ACTION_PARAM1 + " text, "
            + COLUMN_STUDY_SENSOR_ACTION_PARAM2 + " text, " + COLUMN_STUDY_SENSOR_ACTION_PARAM3 + " text, unique (" + COLUMN_STUDY_SENSOR_ACTION_STUDY_ID
            + "," + COLUMN_STUDY_SENSOR_ACTION_CONFIG_ID + "," + COLUMN_STUDY_SENSOR_ACTION_CODE + "))";

    //Study Sensor Action - Delete Entry
    private static final String DELETE_TABLE_STUDY_SENSOR_ACTION = "drop table if exists " + TABLE_STUDY_SENSOR_ACTION;


    //Table Core Sensor Action----------------------------------------------------------------------
    private static final String TABLE_CORE_SENSOR_ACTION = "core_sensor_action";

    //Core Sensor Action - Column Names
    private static final String COLUMN_CORE_SENSOR_ACTION_SENSOR_NAME = "sensor_name";
    private static final String COLUMN_CORE_SENSOR_ACTION_TYPE = "type";
    private static final String COLUMN_CORE_SENSOR_ACTION_IS_ENABLED = "is_enabled";
    private static final String COLUMN_CORE_SENSOR_ACTION_FREQUENCY = "frequency";
    private static final String COLUMN_CORE_SENSOR_ACTION_TIME_BOUND = "time_bound";
    private static final String COLUMN_CORE_SENSOR_ACTION_BATTERY_BOUND = "battery_bound";
    private static final String COLUMN_CORE_SENSOR_ACTION_CUSTOM_SENSING_CODE = "custom_sensing_code";

    //Core Sensor Action - Create Entry
    private static final String CREATE_TABLE_CORE_SENSOR_ACTION = "create table " + TABLE_CORE_SENSOR_ACTION + "(" + COLUMN_CORE_SENSOR_ACTION_SENSOR_NAME + " text primary key, "
            + COLUMN_CORE_SENSOR_ACTION_TYPE + " text, "  + COLUMN_CORE_SENSOR_ACTION_IS_ENABLED + " integer, " + COLUMN_CORE_SENSOR_ACTION_FREQUENCY + " real, "
            + COLUMN_CORE_SENSOR_ACTION_TIME_BOUND + " text, " + COLUMN_CORE_SENSOR_ACTION_BATTERY_BOUND + " text, " + COLUMN_CORE_SENSOR_ACTION_CUSTOM_SENSING_CODE + " text)";

    //Core Sensor Action - Delete Entry
    private static final String DELETE_TABLE_CORE_SENSOR_ACTION = "drop table if exists " + TABLE_CORE_SENSOR_ACTION;


    //Table Survey Config---------------------------------------------------------------------------
    private static final String TABLE_SURVEY_CONFIG = "study_survey_config";

    //Survey Config - Column Names
    private static final String COLUMN_SURVEY_CONFIG_ID = "id";
    private static final String COLUMN_SURVEY_CONFIG_STUDY_ID = "study_id";
    private static final String COLUMN_SURVEY_CONFIG_NAME = "name";
    private static final String COLUMN_SURVEY_CONFIG_DESCRIPTION = "description";
    private static final String COLUMN_SURVEY_CONFIG_MODIFICATION_TIME = "modification_time";
    private static final String COLUMN_SURVEY_CONFIG_MODIFICATION_TIME_ZONE = "modification_time_zone";
    private static final String COLUMN_SURVEY_CONFIG_PUBLISH_TIME = "publish_time";
    private static final String COLUMN_SURVEY_CONFIG_PUBLISH_TIME_ZONE = "publish_time_zone";
    private static final String COLUMN_SURVEY_CONFIG_PUBLISHED_VERSION = "published_version";
    private static final String COLUMN_SURVEY_CONFIG_STATE = "state";
    private static final String COLUMN_SURVEY_CONFIG_START_TIME = "start_time";
    private static final String COLUMN_SURVEY_CONFIG_START_TIME_ZONE = "start_time_zone";
    private static final String COLUMN_SURVEY_CONFIG_END_TIME = "end_time";
    private static final String COLUMN_SURVEY_CONFIG_END_TIME_ZONE = "end_time_zone";
    private static final String COLUMN_SURVEY_CONFIG_SCHEDULE = "schedule";
    private static final String COLUMN_SURVEY_CONFIG_LIFECYCLE = "lifecycle";
//    private static final String COLUMN_SURVEY_CONFIG_PARTICIPATED = "participated";
//    private static final String COLUMN_SURVEY_CONFIG_NOTIFIED = "notified";
//    private static final String COLUMN_SURVEY_CONFIG_TOTAL_PARTICIPATION = "total_participation";
//    private static final String COLUMN_SURVEY_CONFIG_LAST_PARTICIPATION = "last_participation";

    //Study Survey Config - Create Entry
    private static final String CREATE_TABLE_SURVEY_CONFIG = "create table " + TABLE_SURVEY_CONFIG + "(" + COLUMN_SURVEY_CONFIG_ID + " integer primary key, "
            + COLUMN_SURVEY_CONFIG_STUDY_ID + " integer, " + COLUMN_SURVEY_CONFIG_NAME + " text, " + COLUMN_SURVEY_CONFIG_DESCRIPTION + " text, "
            + COLUMN_SURVEY_CONFIG_MODIFICATION_TIME + " text, " + COLUMN_SURVEY_CONFIG_MODIFICATION_TIME_ZONE + " text, " + COLUMN_SURVEY_CONFIG_PUBLISH_TIME + " text, "
            + COLUMN_SURVEY_CONFIG_PUBLISH_TIME_ZONE + " text, " + COLUMN_SURVEY_CONFIG_PUBLISHED_VERSION + " text, " + COLUMN_SURVEY_CONFIG_STATE + " integer, "
            + COLUMN_SURVEY_CONFIG_START_TIME + " text, " + COLUMN_SURVEY_CONFIG_START_TIME_ZONE + " text, " + COLUMN_SURVEY_CONFIG_END_TIME + " text, "
            + COLUMN_SURVEY_CONFIG_END_TIME_ZONE + " text, " + COLUMN_SURVEY_CONFIG_SCHEDULE + " text, " + COLUMN_SURVEY_CONFIG_LIFECYCLE + " integer)";
//            + COLUMN_SURVEY_CONFIG_PARTICIPATED + " integer, " + COLUMN_SURVEY_CONFIG_NOTIFIED + " integer, " + COLUMN_SURVEY_CONFIG_TOTAL_PARTICIPATION + " integer, "
//            + COLUMN_SURVEY_CONFIG_LAST_PARTICIPATION + " text)";

    //Study Survey Config - Delete Entry
    private static final String DELETE_TABLE_SURVEY_CONFIG = "drop table if exists " + TABLE_SURVEY_CONFIG;


    //Table Survey Task-----------------------------------------------------------------------------
    private static final String TABLE_SURVEY_TASK = "study_survey_task";

    //Survey task - Column Names
    private static final String COLUMN_SURVEY_TASK_STUDY_ID = "study_id";
    private static final String COLUMN_SURVEY_TASK_SURVEY_ID = "survey_id";
    private static final String COLUMN_SURVEY_TASK_VERSION = "version";
    private static final String COLUMN_SURVEY_TASK_ID = "task_id";
    private static final String COLUMN_SURVEY_TASK_TEXT = "task_text";
    private static final String COLUMN_SURVEY_TASK_TYPE = "type";
    private static final String COLUMN_SURVEY_TASK_POSSIBLE_INPUT = "possible_input";
    private static final String COLUMN_SURVEY_TASK_ORDER_ID = "order_id";
    private static final String COLUMN_SURVEY_TASK_IS_ACTIVE = "is_actcive";
    private static final String COLUMN_SURVEY_TASK_IS_REQUIRED = "is_required";
    private static final String COLUMN_SURVEY_TASK_HAS_COMMENT = "has_comment";
    private static final String COLUMN_SURVEY_TASK_HAS_URL = "has_url";
    private static final String COLUMN_SURVEY_TASK_PARENT_TASK_ID = "parent_task_id";
    private static final String COLUMN_SURVEY_TASK_HAS_CHILD = "has_child";
    private static final String COLUMN_SURVEY_TASK_CHILD_TRIGGERING_INPUT = "child_triggering_input";
    private static final String COLUMN_SURVEY_TASK_DEFAULT_INPUT = "default_input";


    //Study Survey Task - Create Entry
    private static final String CREATE_TABLE_SURVEY_TASK = "create table " + TABLE_SURVEY_TASK + "(" + COLUMN_SURVEY_TASK_STUDY_ID + " integer, "
            + COLUMN_SURVEY_TASK_SURVEY_ID + " integer, " + COLUMN_SURVEY_TASK_VERSION + " integer, " + COLUMN_SURVEY_TASK_ID + " integer, "
            + COLUMN_SURVEY_TASK_TEXT + " text, " + COLUMN_SURVEY_TASK_TYPE + " text, " + COLUMN_SURVEY_TASK_POSSIBLE_INPUT + " text, "
            + COLUMN_SURVEY_TASK_ORDER_ID + " integer, " + COLUMN_SURVEY_TASK_IS_ACTIVE + " integer, " + COLUMN_SURVEY_TASK_IS_REQUIRED + " integer, "
            + COLUMN_SURVEY_TASK_HAS_COMMENT + " integer, " + COLUMN_SURVEY_TASK_HAS_URL + " integer, " + COLUMN_SURVEY_TASK_PARENT_TASK_ID + " integer, "
            + COLUMN_SURVEY_TASK_HAS_CHILD + " integer, " + COLUMN_SURVEY_TASK_CHILD_TRIGGERING_INPUT + " text, " + COLUMN_SURVEY_TASK_DEFAULT_INPUT + " text, "
            + " unique (" + COLUMN_SURVEY_TASK_STUDY_ID
            + "," + COLUMN_SURVEY_TASK_SURVEY_ID + "," + COLUMN_SURVEY_TASK_ID + "))";


    //Study Survey Task - Delete Entry
    private static final String DELETE_TABLE_SURVEY_TASK = "drop table if exists " + TABLE_SURVEY_TASK;


    //Table Survey Response-------------------------------------------------------------------------
    private static final String TABLE_SURVEY_RESPONSE = "study_survey_response";


    public KoiosDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "going to create table..");
        db.execSQL(CREATE_TABLE_STUDY);
        db.execSQL(CREATE_TABLE_STUDY_SENSOR_CONFIG);
        db.execSQL(CREATE_TABLE_STUDY_SENSOR_ACTION);
        db.execSQL(CREATE_TABLE_CORE_SENSOR_ACTION);
        db.execSQL(CREATE_TABLE_SURVEY_CONFIG);
        db.execSQL(CREATE_TABLE_SURVEY_TASK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "version upgraded, old: "+ oldVersion + ", new:"+ newVersion);
        Log.d(TAG, "on upgrade will delete table...");
        db.execSQL(DELETE_TABLE_STUDY);
        db.execSQL(DELETE_TABLE_STUDY_SENSOR_CONFIG);
        db.execSQL(DELETE_TABLE_STUDY_SENSOR_ACTION);
        db.execSQL(DELETE_TABLE_CORE_SENSOR_ACTION);
        db.execSQL(DELETE_TABLE_SURVEY_CONFIG);
        db.execSQL(DELETE_TABLE_SURVEY_TASK);
        Log.d(TAG, "on upgrade will create db...");
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


    //Queries for Study Table
    public void insertStudyData(KoiosStudy study){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_STUDY_ID, study.getId());
        values.put(COLUMN_STUDY_NAME, study.getName());
        values.put(COLUMN_STUDY_ORGANIZATION, study.getOrganization());
        values.put(COLUMN_STUDY_DESCRIPTION, study.getDescription());
        values.put(COLUMN_STUDY_MODIFICATION_TIME, study.getModificationTime());
        values.put(COLUMN_STUDY_STATE, study.getState());
        values.put(COLUMN_STUDY_TYPE, study.getStudyType());
        values.put(COLUMN_STUDY_INSTRUCTION, study.getInstruction());
        values.put(COLUMN_STUDY_ICON_URL, study.getIconUrl());

        db.insertWithOnConflict(TABLE_STUDY, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        Koios.getStudyEnrolled().postValue(true);
    }


    public KoiosStudy getStudy(int studyId){
        KoiosStudy study = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from "+ TABLE_STUDY + " where " + COLUMN_STUDY_ID + " = " + studyId;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToNext()){
            study = new KoiosStudy();
            study.setId(studyId);
            String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_NAME));
            study.setName(name);
            String organization = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_ORGANIZATION));
            study.setOrganization(organization);
            String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_DESCRIPTION));
            study.setDescription(description);
            String modificationTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_MODIFICATION_TIME));
            study.setModificationTime(modificationTime);
            int state = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STUDY_STATE));
            study.setState(state);
            int type = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STUDY_TYPE));
            study.setState(type);
            String instruction = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_INSTRUCTION));
            study.setInstruction(instruction);
            String iconUrl = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_ICON_URL));
            study.setIconUrl(iconUrl);
        }
        cursor.close();
        return study;
    }

    public ArrayList<KoiosStudy> getAllStudies(){
        ArrayList<KoiosStudy> studies = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from "+ TABLE_STUDY;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()){
            KoiosStudy study = new KoiosStudy();
            int studyId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STUDY_ID));
            study.setId(studyId);
            String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_NAME));
            study.setName(name);
            String organization = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_ORGANIZATION));
            study.setOrganization(organization);
            String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_DESCRIPTION));
            study.setDescription(description);
            String modificationTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_MODIFICATION_TIME));
            study.setModificationTime(modificationTime);
            int state = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STUDY_STATE));
            study.setState(state);
            int type = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STUDY_TYPE));
            study.setState(type);
            String instruction = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_INSTRUCTION));
            study.setInstruction(instruction);
            String iconUrl = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_ICON_URL));
            study.setIconUrl(iconUrl);

            studies.add(study);
        }
        cursor.close();
        return studies;

    }

    public int deleteStudy(int studyId){
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_STUDY_ID + " = ?";
        String [] selectionArgs = {String.valueOf(studyId)};
        int deletedRows = db.delete(TABLE_STUDY, selection, selectionArgs);
        return deletedRows;
    }

    public int deleteAllStudies(){
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete(TABLE_STUDY, null, null);
        return deletedRows;

    }


    //Queries for Study Sensor Config Table
    public void insertStudySensorConfig(StudySensorConfig config){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_STUDY_SENSOR_CONFIG_ID, config.getId());
        values.put(COLUMN_STUDY_SENSOR_CONFIG_STUDY_ID, config.getStudyId());
        values.put(COLUMN_STUDY_SENSOR_CONFIG_NAME, config.getName());
        values.put(COLUMN_STUDY_SENSOR_CONFIG_DESCRIPTION, config.getDescription());
        values.put(COLUMN_STUDY_SENSOR_CONFIG_CREATED_BY, config.getCreatedBy());
        values.put(COLUMN_STUDY_SENSOR_CONFIG_CREATION_TIME, config.getCreationTime());
        values.put(COLUMN_STUDY_SENSOR_CONFIG_CREATION_TIME_ZONE, config.getCreationTimeZone());
        values.put(COLUMN_STUDY_SENSOR_CONFIG_MODIFICATION_TIME, config.getModificationTime());
        values.put(COLUMN_STUDY_SENSOR_CONFIG_MODIFICATION_TIME_ZONE, config.getModificationTimeZone());
        values.put(COLUMN_STUDY_SENSOR_CONFIG_PUBLISH_TIME, config.getPublishTime());
        values.put(COLUMN_STUDY_SENSOR_CONFIG_PUBLISH_TIME_ZONE, config.getPublishTimeZone());
        values.put(COLUMN_STUDY_SENSOR_CONFIG_PUBLISHED_VERSION, config.getPublishedVersion());
        values.put(COLUMN_STUDY_SENSOR_CONFIG_STATE, config.getState());
        values.put(COLUMN_STUDY_SENSOR_CONFIG_RESPONSE_COUNT, config.getResponseCount());
        values.put(COLUMN_STUDY_SENSOR_CONFIG_START_TIME, config.getStartTime());
        values.put(COLUMN_STUDY_SENSOR_CONFIG_START_TIME_ZONE, config.getStartTimeZone());
        values.put(COLUMN_STUDY_SENSOR_CONFIG_END_TIME, config.getEndTime());
        values.put(COLUMN_STUDY_SENSOR_CONFIG_END_TIME_ZONE, config.getEndTimeZone());
        values.put(COLUMN_STUDY_SENSOR_CONFIG_SCHEDULE, config.getSchedule());

        db.insertWithOnConflict(TABLE_STUDY_SENSOR_CONFIG, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public ArrayList<StudySensorConfig> getSensorConfigsOfTheStudy(int studyId){
        ArrayList<StudySensorConfig> configs = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + TABLE_STUDY_SENSOR_CONFIG + " where " + COLUMN_STUDY_SENSOR_CONFIG_STUDY_ID + " = " + studyId;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            StudySensorConfig config = new StudySensorConfig();
            int configId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_ID));
            config.setId(configId);
            config.setStudyId(studyId);

            String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_NAME));
            config.setName(name);
            String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_DESCRIPTION));
            config.setDescription(description);
            String createdBy = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_CREATED_BY));
            config.setCreatedBy(createdBy);
            String creationTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_CREATION_TIME));
            config.setCreationTime(creationTime);
            String creationTimeZone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_CREATION_TIME_ZONE));
            config.setCreationTimeZone(creationTimeZone);
            String modificationTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_MODIFICATION_TIME));
            config.setModificationTime(modificationTime);
            String modificationTimeZone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_MODIFICATION_TIME_ZONE));
            config.setModificationTimeZone(modificationTimeZone);
            String publishTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_PUBLISH_TIME));
            config.setPublishTime(publishTime);
            String publishTimeZone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_PUBLISH_TIME_ZONE));
            config.setPublishTimeZone(publishTimeZone);

            int publishedVersion = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_PUBLISHED_VERSION));
            config.setPublishedVersion(publishedVersion);
            int state = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_STATE));
            config.setState(state);
            int responseCount = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_RESPONSE_COUNT));
            config.setResponseCount(responseCount);

            String startTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_START_TIME));
            config.setStartTime(startTime);
            String startTimeZone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_START_TIME_ZONE));
            config.setStartTimeZone(startTimeZone);
            String endTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_END_TIME));
            config.setEndTime(endTime);
            String endTimeZone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_END_TIME_ZONE));
            config.setEndTimeZone(endTimeZone);
            String schedule = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_SCHEDULE));
            config.setSchedule(schedule);

            configs.add(config);

        }
        cursor.close();
        return configs;

    }

    public StudySensorConfig getStudySensorConfig(int configId){
        StudySensorConfig config = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from "+ TABLE_STUDY_SENSOR_CONFIG + " where " + COLUMN_STUDY_SENSOR_CONFIG_ID + " = " + configId;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToNext()){
            config = new StudySensorConfig();
            config.setId(configId);
            int studyId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_STUDY_ID));
            config.setStudyId(studyId);
            String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_NAME));
            config.setName(name);
            String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_DESCRIPTION));
            config.setDescription(description);
            String createdBy = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_CREATED_BY));
            config.setCreatedBy(createdBy);
            String creationTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_CREATION_TIME));
            config.setCreationTime(creationTime);
            String creationTimeZone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_CREATION_TIME_ZONE));
            config.setCreationTimeZone(creationTimeZone);
            String modificationTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_MODIFICATION_TIME));
            config.setModificationTime(modificationTime);
            String modificationTimeZone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_MODIFICATION_TIME_ZONE));
            config.setModificationTimeZone(modificationTimeZone);
            String publishTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_PUBLISH_TIME));
            config.setPublishTime(publishTime);
            String publishTimeZone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_PUBLISH_TIME_ZONE));
            config.setPublishTimeZone(publishTimeZone);

            int publishedVersion = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_PUBLISHED_VERSION));
            config.setPublishedVersion(publishedVersion);
            int state = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_STATE));
            config.setState(state);
            int responseCount = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_RESPONSE_COUNT));
            config.setResponseCount(responseCount);

            String startTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_START_TIME));
            config.setStartTime(startTime);
            String startTimeZone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_START_TIME_ZONE));
            config.setStartTimeZone(startTimeZone);
            String endTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_END_TIME));
            config.setEndTime(endTime);
            String endTimeZone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_END_TIME_ZONE));
            config.setEndTimeZone(endTimeZone);
            String schedule = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_SCHEDULE));
            config.setSchedule(schedule);

        }
        cursor.close();
        return config;

    }

    public ArrayList<StudySensorConfig> getAllStudySensorConfig() {
        ArrayList<StudySensorConfig> configs = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + TABLE_STUDY_SENSOR_CONFIG;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            StudySensorConfig config = new StudySensorConfig();
            int configId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_ID));
            config.setId(configId);
            int studyId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_STUDY_ID));
            config.setStudyId(studyId);

            String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_NAME));
            config.setName(name);
            String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_DESCRIPTION));
            config.setDescription(description);
            String createdBy = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_CREATED_BY));
            config.setCreatedBy(createdBy);
            String creationTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_CREATION_TIME));
            config.setCreationTime(creationTime);
            String creationTimeZone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_CREATION_TIME_ZONE));
            config.setCreationTimeZone(creationTimeZone);
            String modificationTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_MODIFICATION_TIME));
            config.setModificationTime(modificationTime);
            String modificationTimeZone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_MODIFICATION_TIME_ZONE));
            config.setModificationTimeZone(modificationTimeZone);
            String publishTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_PUBLISH_TIME));
            config.setPublishTime(publishTime);
            String publishTimeZone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_PUBLISH_TIME_ZONE));
            config.setPublishTimeZone(publishTimeZone);

            int publishedVersion = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_PUBLISHED_VERSION));
            config.setPublishedVersion(publishedVersion);
            int state = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_STATE));
            config.setState(state);
            int responseCount = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_RESPONSE_COUNT));
            config.setResponseCount(responseCount);

            String startTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_START_TIME));
            config.setStartTime(startTime);
            String startTimeZone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_START_TIME_ZONE));
            config.setStartTimeZone(startTimeZone);
            String endTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_END_TIME));
            config.setEndTime(endTime);
            String endTimeZone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_END_TIME_ZONE));
            config.setEndTimeZone(endTimeZone);
            String schedule = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_CONFIG_SCHEDULE));
            config.setSchedule(schedule);

            configs.add(config);

        }
        cursor.close();
        return configs;
    }

    public int deleteAllSensorConfigsOfTheStudy(int studyId){
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_STUDY_SENSOR_CONFIG_STUDY_ID + " = ?";
        String [] selectionArgs = {String.valueOf(studyId)};
        int deletedRows = db.delete(TABLE_STUDY_SENSOR_CONFIG, selection, selectionArgs);
        return deletedRows;
    }

    public int deleteStudySensorConfig(int configId){
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_STUDY_SENSOR_CONFIG_ID + " = ?";
        String [] selectionArgs = {String.valueOf(configId)};
        int deletedRows = db.delete(TABLE_STUDY_SENSOR_CONFIG, selection, selectionArgs);
        return deletedRows;
    }

    public int deleteAllStudySensorConfig(){
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete(TABLE_STUDY_SENSOR_CONFIG, null, null);
        return deletedRows;
    }



    //Queries for Study Sensor Action Table
    public void insertStudySensorAction(StudySensorAction action){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_STUDY_SENSOR_ACTION_STUDY_ID, action.getStudyId());
        values.put(COLUMN_STUDY_SENSOR_ACTION_CONFIG_ID, action.getSensorConfigId());
        values.put(COLUMN_STUDY_SENSOR_ACTION_VERSION, action.getVersion());
        values.put(COLUMN_STUDY_SENSOR_ACTION_CODE, action.getSensorActionCode());
        values.put(COLUMN_STUDY_SENSOR_ACTION_TYPE, action.getType());
        values.put(COLUMN_STUDY_SENSOR_ACTION_ENABLED, action.getIsEnabled());
        values.put(COLUMN_STUDY_SENSOR_ACTION_FREQUENCY, action.getFrequency());
        values.put(COLUMN_STUDY_SENSOR_ACTION_TIME_BOUND, action.getTimeBound());
        values.put(COLUMN_STUDY_SENSOR_ACTION_BATTERY_BOUND, action.getBatteryBound());
        values.put(COLUMN_STUDY_SENSOR_ACTION_PARAM1, action.getParam1());
        values.put(COLUMN_STUDY_SENSOR_ACTION_PARAM2, action.getParam2());
        values.put(COLUMN_STUDY_SENSOR_ACTION_PARAM3, action.getParam3());

        db.insertWithOnConflict(TABLE_STUDY_SENSOR_ACTION, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public ArrayList<StudySensorAction> getStudySensorActions(String sensorName){
        ArrayList<StudySensorAction> actions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + TABLE_STUDY_SENSOR_ACTION + " where lower("+ COLUMN_STUDY_SENSOR_ACTION_CODE +") = '"
                + sensorName.toLowerCase() + "' and " + COLUMN_STUDY_SENSOR_ACTION_ENABLED + " = 1";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            try {
                StudySensorAction action = new StudySensorAction();
                action.setStudyId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_ACTION_STUDY_ID)));
                action.setSensorConfigId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_ACTION_CONFIG_ID)));
                action.setVersion(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_ACTION_VERSION)));
                action.setSensorActionCode(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_ACTION_CODE)));
                action.setType(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_ACTION_TYPE)));
                action.setIsEnabled(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_ACTION_ENABLED)));
                action.setFrequency(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_ACTION_FREQUENCY)));
                action.setTimeBound(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_ACTION_TIME_BOUND)));
                action.setBatteryBound(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDY_SENSOR_ACTION_BATTERY_BOUND)));

                actions.add(action);
            }catch (Exception e){

            }
        }
        cursor.close();
        return actions;
    }


    public int deleteStudySensorActions(int studyId){
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_STUDY_SENSOR_ACTION_STUDY_ID + " = ?";
        String [] selectionArgs = {String.valueOf(studyId)};
        int deletedRows = db.delete(TABLE_STUDY_SENSOR_ACTION, selection, selectionArgs);
        return deletedRows;
    }

    public int deleteStudySensorActions(int studyId, int configId){
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_STUDY_SENSOR_ACTION_STUDY_ID + " = ? and " + COLUMN_STUDY_SENSOR_ACTION_CONFIG_ID + " = ? ";
        String [] selectionArgs = {String.valueOf(studyId), String.valueOf(configId)};
        int deletedRows = db.delete(TABLE_STUDY_SENSOR_ACTION, selection, selectionArgs);
        return deletedRows;
    }

    public int deleteAllStudySensorAction(){
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete(TABLE_STUDY_SENSOR_ACTION, null, null);
        return deletedRows;
    }

    //Queries for Core Sensor Action
    public void insertCoreSensing(CoreSensorAction action){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CORE_SENSOR_ACTION_SENSOR_NAME, action.getSensorName());
        values.put(COLUMN_CORE_SENSOR_ACTION_TYPE, action.getType());
        values.put(COLUMN_CORE_SENSOR_ACTION_IS_ENABLED, action.getIsEnabled());
        values.put(COLUMN_CORE_SENSOR_ACTION_FREQUENCY, action.getFrequency());
        values.put(COLUMN_CORE_SENSOR_ACTION_TIME_BOUND, action.getTimeBound());
        values.put(COLUMN_CORE_SENSOR_ACTION_BATTERY_BOUND, action.getBatteryBound());
        values.put(COLUMN_CORE_SENSOR_ACTION_CUSTOM_SENSING_CODE, action.getCustomSensingCode());

        db.insertWithOnConflict(TABLE_CORE_SENSOR_ACTION, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public CoreSensorAction getCoreSensorAction(String sensorName) {
        SQLiteDatabase db = this.getReadableDatabase();
        CoreSensorAction action = null;
        String query = "select * from " + TABLE_CORE_SENSOR_ACTION + " where "
                + COLUMN_CORE_SENSOR_ACTION_SENSOR_NAME + " = '" + sensorName + "'" ;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToNext()) {
            try {
                action = new CoreSensorAction();
                action.setSensorName(sensorName);
                int isEnabled = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CORE_SENSOR_ACTION_IS_ENABLED));
                action.setIsEnabled(isEnabled);
                double frequency = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_CORE_SENSOR_ACTION_FREQUENCY));
                action.setFrequency(frequency);
            }catch (Exception e){

            }
        }
        cursor.close();
        return action;
    }

    public int deleteAllCoreSensorAction(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_CORE_SENSOR_ACTION, null, null);
    }


    //Queries for Study Survey Config Table
    public void insertStudySurveyConfig(StudySurveyConfig config){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_SURVEY_CONFIG_ID, config.getId());
        values.put(COLUMN_SURVEY_CONFIG_STUDY_ID, config.getStudyId());
        values.put(COLUMN_SURVEY_CONFIG_NAME, config.getName());
        values.put(COLUMN_SURVEY_CONFIG_DESCRIPTION, config.getDescription());
        values.put(COLUMN_SURVEY_CONFIG_MODIFICATION_TIME, config.getModificationTime());
        values.put(COLUMN_SURVEY_CONFIG_MODIFICATION_TIME_ZONE, config.getModificationTimeZone());
        values.put(COLUMN_SURVEY_CONFIG_PUBLISH_TIME, config.getPublishTime());
        values.put(COLUMN_SURVEY_CONFIG_PUBLISH_TIME_ZONE, config.getPublishTimeZone());
        values.put(COLUMN_SURVEY_CONFIG_PUBLISHED_VERSION, config.getPublishedVersion());
        values.put(COLUMN_SURVEY_CONFIG_STATE, config.getState());
        values.put(COLUMN_SURVEY_CONFIG_START_TIME, config.getStartTime());
        values.put(COLUMN_SURVEY_CONFIG_START_TIME_ZONE, config.getStartTimeZone());
        values.put(COLUMN_SURVEY_CONFIG_END_TIME, config.getEndTime());
        values.put(COLUMN_SURVEY_CONFIG_END_TIME_ZONE, config.getEndTimeZone());
        values.put(COLUMN_SURVEY_CONFIG_SCHEDULE, config.getSchedule());
        values.put(COLUMN_SURVEY_CONFIG_LIFECYCLE, config.getLifecycle());

        db.insertWithOnConflict(TABLE_SURVEY_CONFIG, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        Koios.getSurveyListChanged().postValue(true);
    }

    public ArrayList<StudySurveyConfig> getAllSurveys(){
        return getSurveyConfigsOfTheStudy(0);
    }

    public ArrayList<StudySurveyConfig> getSurveyConfigsOfTheStudy(int studyId){
        ArrayList<StudySurveyConfig> configs = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
//        String query = "select * from " + TABLE_SURVEY_CONFIG + " where " + COLUMN_SURVEY_CONFIG_STUDY_ID + " = " + studyId;
        String query = "select * from " + TABLE_SURVEY_CONFIG;
        if (studyId > 0){
            query += ( " where " + COLUMN_SURVEY_CONFIG_STUDY_ID + " = " + studyId);
        }
        Log.d(TAG, "query:" + query);
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            StudySurveyConfig config = new StudySurveyConfig();
            int configId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SURVEY_CONFIG_ID));
            config.setId(configId);
            config.setStudyId(studyId);

            String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SURVEY_CONFIG_NAME));
            config.setName(name);
            String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SURVEY_CONFIG_DESCRIPTION));
            config.setDescription(description);
            String modificationTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SURVEY_CONFIG_MODIFICATION_TIME));
            config.setModificationTime(modificationTime);
            String modificationTimeZone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SURVEY_CONFIG_MODIFICATION_TIME_ZONE));
            config.setModificationTimeZone(modificationTimeZone);
            String publishTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SURVEY_CONFIG_PUBLISH_TIME));
            config.setPublishTime(publishTime);
            String publishTimeZone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SURVEY_CONFIG_PUBLISH_TIME_ZONE));
            config.setPublishTimeZone(publishTimeZone);

            int publishedVersion = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SURVEY_CONFIG_PUBLISHED_VERSION));
            config.setPublishedVersion(publishedVersion);
            int state = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SURVEY_CONFIG_STATE));
            config.setState(state);

            String startTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SURVEY_CONFIG_START_TIME));
            config.setStartTime(startTime);
            String startTimeZone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SURVEY_CONFIG_START_TIME_ZONE));
            config.setStartTimeZone(startTimeZone);
            String endTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SURVEY_CONFIG_END_TIME));
            config.setEndTime(endTime);
            String endTimeZone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SURVEY_CONFIG_END_TIME_ZONE));
            config.setEndTimeZone(endTimeZone);
            String schedule = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SURVEY_CONFIG_SCHEDULE));
            config.setSchedule(schedule);
            int lifecycle = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SURVEY_CONFIG_LIFECYCLE));
            config.setLifecycle(lifecycle);

            configs.add(config);

        }
        cursor.close();

        return configs;

    }

    public int deleteAllSurveyConfigsOfTheStudy(int studyId){
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_SURVEY_CONFIG_STUDY_ID + " = ?";
        String [] selectionArgs = {String.valueOf(studyId)};
        int deletedRows = db.delete(TABLE_SURVEY_CONFIG, selection, selectionArgs);
        return deletedRows;
    }

    public int deleteSurveyConfig(int configId){
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_SURVEY_CONFIG_ID + " = ?";
        String [] selectionArgs = {String.valueOf(configId)};
        Log.d(TAG, "delete survey config, selection:"+ selection + ", for id:" + configId);
        int deletedRows = db.delete(TABLE_SURVEY_CONFIG, selection, selectionArgs);
        Log.d(TAG, "number of rows deleted:" + deletedRows);
        if (deletedRows>0){
            Koios.getSurveyListChanged().postValue(true);
        }
        return deletedRows;
//        SQLiteDatabase db = this.getWritableDatabase();
//        String query = "delete from " + TABLE_SURVEY_CONFIG + "where "
    }


    //Queries for Study Survey Task Table
    public void insertSurveyTask(StudySurveyTask task){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_SURVEY_TASK_STUDY_ID, task.getStudyId());
        values.put(COLUMN_SURVEY_TASK_SURVEY_ID, task.getSurveyId());
        values.put(COLUMN_SURVEY_TASK_VERSION, task.getVersion());
        values.put(COLUMN_SURVEY_TASK_ID, task.getTaskId());
        values.put(COLUMN_SURVEY_TASK_TEXT, task.getTaskText());
        values.put(COLUMN_SURVEY_TASK_TYPE, task.getType());
        values.put(COLUMN_SURVEY_TASK_POSSIBLE_INPUT, task.getPossibleInput());
        values.put(COLUMN_SURVEY_TASK_ORDER_ID, task.getOrderId());
        values.put(COLUMN_SURVEY_TASK_IS_ACTIVE, task.getIsActive());
        values.put(COLUMN_SURVEY_TASK_IS_REQUIRED, task.getIsRequired());
        values.put(COLUMN_SURVEY_TASK_HAS_COMMENT, task.getHasComment());
        values.put(COLUMN_SURVEY_TASK_HAS_URL, task.getHasUrl());
        values.put(COLUMN_SURVEY_TASK_PARENT_TASK_ID, task.getParentTaskId());
        values.put(COLUMN_SURVEY_TASK_HAS_CHILD, task.getHasChild());
        values.put(COLUMN_SURVEY_TASK_CHILD_TRIGGERING_INPUT, task.getChildTriggeringInput());
        values.put(COLUMN_SURVEY_TASK_DEFAULT_INPUT, task.getDefaultInput());

        db.insertWithOnConflict(TABLE_SURVEY_TASK, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public ArrayList<StudySurveyTask> getTaskListOfTheSurvey(int studyId, int surveyId){
        ArrayList<StudySurveyTask> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + TABLE_SURVEY_TASK + " where " + COLUMN_SURVEY_TASK_STUDY_ID + " = " + studyId + " and "
                + COLUMN_SURVEY_TASK_SURVEY_ID + "=" + surveyId + " order by " + COLUMN_SURVEY_TASK_ORDER_ID;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            StudySurveyTask task = new StudySurveyTask();
            task.setStudyId(studyId);
            task.setSurveyId(surveyId);

            int version = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SURVEY_TASK_VERSION));
            task.setVersion(version);
            int taskId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SURVEY_TASK_ID));
            task.setTaskId(taskId);
            String taskText = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SURVEY_TASK_TEXT));
            task.setTaskText(taskText);
            String type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SURVEY_TASK_TYPE));
            task.setType(type);
            String possibleInput = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SURVEY_TASK_POSSIBLE_INPUT));
            task.setPossibleInput(possibleInput);
            int orderId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SURVEY_TASK_ORDER_ID));
            task.setOrderId(orderId);
            int isActive = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SURVEY_TASK_IS_ACTIVE));
            task.setIsActive(isActive);
            int isRequired = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SURVEY_TASK_IS_REQUIRED));
            task.setIsRequired(isRequired);
            int hasComment = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SURVEY_TASK_HAS_COMMENT));
            task.setHasComment(hasComment);
            int hasUrl = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SURVEY_TASK_HAS_URL));
            task.setHasUrl(hasUrl);
            int parentTaskId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SURVEY_TASK_PARENT_TASK_ID));
            task.setParentTaskId(parentTaskId);
            int hasChild = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SURVEY_TASK_HAS_CHILD));
            task.setHasChild(hasChild);
            String childTriggeringInput = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SURVEY_TASK_CHILD_TRIGGERING_INPUT));
            task.setChildTriggeringInput(childTriggeringInput);
            String defaultInput = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SURVEY_TASK_DEFAULT_INPUT));
            task.setDefaultInput(defaultInput);

            taskList.add(task);

        }
        cursor.close();
        return taskList;

    }

    public int deleteSurveyTasksOfAStudy(int studyId){
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_SURVEY_TASK_STUDY_ID + " = ?";
        String [] selectionArgs = {String.valueOf(studyId)};
        int deletedRows = db.delete(TABLE_SURVEY_TASK, selection, selectionArgs);
        return deletedRows;
    }

    public int deleteSurveyTasks(int studyId, int configId){
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_SURVEY_TASK_STUDY_ID + " = ? and " + COLUMN_SURVEY_TASK_SURVEY_ID + " = ? ";
        String [] selectionArgs = {String.valueOf(studyId), String.valueOf(configId)};
        int deletedRows = db.delete(TABLE_SURVEY_TASK, selection, selectionArgs);
        return deletedRows;
    }

}
