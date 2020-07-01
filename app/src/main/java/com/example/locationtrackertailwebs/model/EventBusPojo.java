package com.example.locationtrackertailwebs.model;

import java.util.ArrayList;

public class EventBusPojo {

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public EventBusPojo(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    Double latitude, longitude;
    public EventBusPojo(ArrayList<Double> latlist, ArrayList<Double> longlist) {
        this.latlist = latlist;
        this.longlist = longlist;
    }


    ArrayList<Double> latlist;

    public ArrayList<Double> getLatlist() {
        return latlist;
    }

    public void setLatlist(ArrayList<Double> latlist) {
        this.latlist = latlist;
    }

    public ArrayList<Double> getLonglist() {
        return longlist;
    }

    public void setLonglist(ArrayList<Double> longlist) {
        this.longlist = longlist;
    }

    ArrayList<Double> longlist;
}
