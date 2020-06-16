package com.example.model;

import java.io.Serializable;

public class MyLocation implements Serializable {

    private String location;
    private String startTime;
    private String endTime;
    private String sumTime;
    private String date;
    private double latitude;
    private double longitude;

    public void setLocation(String location) {
        this.location = location;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setSumTime(String sumTime) {
        this.sumTime = sumTime;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getSumTime() {
        return sumTime;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
