package org.mlab.research.koios;

public class StudySensorAction {
    /*{"studyId":32,"sensorConfigId":4,"version":0,"sensorActionCode":"accel","type":"",
            "isEnabled":1,"frequency":50.0,"timeBound":null,"batteryBound":null,"param1":null,"param2":"","param3":""}*/
    private int studyId;
    private int sensorConfigId;
    private int version;
    private String sensorActionCode;
    private String type;
    private int isEnabled;
    private double frequency;
    private String timeBound;
    private String batteryBound;
    private String param1;
    private String param2;
    private String param3;

    public int getStudyId() {
        return studyId;
    }

    public void setStudyId(int studyId) {
        this.studyId = studyId;
    }

    public int getSensorConfigId() {
        return sensorConfigId;
    }

    public void setSensorConfigId(int sensorConfigId) {
        this.sensorConfigId = sensorConfigId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getSensorActionCode() {
        return sensorActionCode;
    }

    public void setSensorActionCode(String sensorActionCode) {
        this.sensorActionCode = sensorActionCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(int isEnabled) {
        this.isEnabled = isEnabled;
    }

    public double getFrequency() {
        return frequency;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    public String getTimeBound() {
        return timeBound;
    }

    public void setTimeBound(String timeBound) {
        this.timeBound = timeBound;
    }

    public String getBatteryBound() {
        return batteryBound;
    }

    public void setBatteryBound(String batteryBound) {
        this.batteryBound = batteryBound;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    public String getParam3() {
        return param3;
    }

    public void setParam3(String param3) {
        this.param3 = param3;
    }

    @Override
    public String toString() {
        return "study Id:" + studyId + ", config Id:" + sensorConfigId + ", version:" + version + ", action code:" + sensorActionCode + ", type:" + type
                + ", isEnabled:" + isEnabled + ", frequency:" + frequency + ", timebound:" + timeBound + ", battery bound:" + batteryBound + ", param1:"
                + param1 + ", param2:" + param2 + ", param3:" + param3;
    }
}
