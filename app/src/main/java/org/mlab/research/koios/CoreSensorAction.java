package org.mlab.research.koios;

public class CoreSensorAction {
    private String sensorName;
    private String type;
    private int isEnabled;
    private double frequency;
    private String timeBound;
    private String batteryBound;
    private String customSensingCode;

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
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

    public String getCustomSensingCode() {
        return customSensingCode;
    }

    public void setCustomSensingCode(String customSensingCode) {
        this.customSensingCode = customSensingCode;
    }

    @Override
    public String toString() {
        return "sensor name:" + sensorName + ", type:" + type + ", is enabled:" + isEnabled + ", frequency:" + frequency
                + ", time bound:" + timeBound + ", battery bound:" + batteryBound +", custom sensing code:" + customSensingCode;
    }
}
