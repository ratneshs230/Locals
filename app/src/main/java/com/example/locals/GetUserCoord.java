package com.example.locals;

public class GetUserCoord {

    private Double Latitude;
    private Double Longitude;
    private String uid;

    public String getuid() {
        return uid;
    }

    public GetUserCoord() {
    }

    public void setuid(String uid) {
        this.uid = uid;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public GetUserCoord(String uid, Double latitude, Double longitude) {
        this.uid = uid;
        Latitude = latitude;
        Longitude = longitude;
    }
}
