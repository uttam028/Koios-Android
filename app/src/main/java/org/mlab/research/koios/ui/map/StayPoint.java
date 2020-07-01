package org.mlab.research.koios.ui.map;

import android.os.Parcel;
import android.os.Parcelable;

public class StayPoint implements Parcelable {
    private String id;
    private String Name;
    private double latitude;
    private double longitude;

    public StayPoint(String id, String name, double latitude, double longitude) {
        this.id = id;
        Name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }



    protected StayPoint(Parcel in) {
        id = in.readString();
        Name = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(Name);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<StayPoint> CREATOR = new Parcelable.Creator<StayPoint>() {
        @Override
        public StayPoint createFromParcel(Parcel in) {
            return new StayPoint(in);
        }

        @Override
        public StayPoint[] newArray(int size) {
            return new StayPoint[size];
        }
    };
}
