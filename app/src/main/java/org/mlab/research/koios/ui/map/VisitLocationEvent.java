package org.mlab.research.koios.ui.map;

class VisitLocationEvent {

    private String timestamp;
    private String timezone;
    private String ssid;
    private String bssid;
    private double latitude;
    private double longitude;
    private int accuracy;

    public VisitLocationEvent(String timestamp, String timezone, String ssid, String bssid, double latitude, double longitude, int accuracy) {
        this.timestamp = timestamp;
        this.timezone = timezone;
        this.ssid = ssid;
        this.bssid = bssid;
        this.latitude = latitude;
        this.longitude = longitude;
        this.accuracy = accuracy;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getTimezone() {
        return timezone;
    }

    public String getSsid() {
        return ssid;
    }

    public String getBssid() {
        return bssid;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getAccuracy() {
        return accuracy;
    }

}
