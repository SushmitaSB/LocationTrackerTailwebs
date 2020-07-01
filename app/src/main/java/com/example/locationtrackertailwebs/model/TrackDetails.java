package com.example.locationtrackertailwebs.model;

import java.util.ArrayList;
import java.util.Date;

public class TrackDetails {
    private long id;
    private String totalTime;
    private ArrayList<Double> latList;
    private ArrayList<Double> longList;
    private Date date;

    private String userEmail;

    public TrackDetails(){

    }
    public TrackDetails(long id, String totalTime, ArrayList<Double> latList, ArrayList<Double> longList, Date date,String userEmail) {
        this.id = id;
        this.totalTime = totalTime;
        this.latList = latList;
        this.longList = longList;
        this.date = date;
        this.userEmail = userEmail;
    }


    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public ArrayList<Double> getLatList() {
        return latList;
    }

    public void setLatList(ArrayList<Double> latList) {
        this.latList = latList;
    }

    public ArrayList<Double> getLongList() {
        return longList;
    }

    public void setLongList(ArrayList<Double> longList) {
        this.longList = longList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }



}
