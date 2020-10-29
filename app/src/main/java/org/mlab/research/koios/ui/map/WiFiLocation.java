package org.mlab.research.koios.ui.map;

public class WiFiLocation {

    private String bssid;
    private String ssid;
    private String placeId;
    private String placeName;
    private double latitude;
    private double longitude;
    private int accuracy;
    private int counter;

    public WiFiLocation(String bssid, String ssid, String placeId, String placeName, double latitude, double longitude, int accuracy, int counter) {
        this.bssid = bssid;
        this.ssid = ssid;
        this.placeId = placeId;
        this.placeName = placeName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.accuracy = accuracy;
        this.counter = counter;
    }

    public String getSsid() {
        return ssid;
    }

    public String getBssid() {
        return bssid;
    }

    public String getPlaceId() {
        return placeId;
    }

    public String getPlaceName() {
        return placeName;
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

    public int getCounter() {
        return counter;
    }
}
