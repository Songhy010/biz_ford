package com.sabayosja.fordcambodia.android.model;


import org.json.JSONArray;

import java.util.ArrayList;

public class ModelBooking {
    private static final ModelBooking ourInstance = new ModelBooking();

    public static ModelBooking getInstance() {
        return ourInstance;
    }

    private ModelBooking() {
    }

    private String carID;
    private String stationID;
    private String MileageID;
    private String ServiceTypeID;
    private ArrayList<String> arrRepairID = new ArrayList<>();
    private ArrayList<String> arrRepairName = new ArrayList<>();
    private JSONArray arrRepair = new JSONArray();
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public JSONArray getArrRepair() {
        return arrRepair;
    }

    public void setArrRepair(JSONArray arrRepair) {
        this.arrRepair = arrRepair;
    }

    public ArrayList<String> getArrRepairID() {
        return arrRepairID;
    }

    public ArrayList<String> getArrRepairName() {
        return arrRepairName;
    }

    public void setArrRepairName(ArrayList<String> arrRepairName) {
        this.arrRepairName = arrRepairName;
    }

    public void setArrRepairID(ArrayList<String> arrRepairID) {
        this.arrRepairID = arrRepairID;
    }

    public String getServiceTypeID() {
        return ServiceTypeID;
    }

    public void setServiceTypeID(String serviceTypeID) {
        ServiceTypeID = serviceTypeID;
    }

    public String getMileageID() {
        return MileageID;
    }

    public void setMileageID(String mileageID) {
        MileageID = mileageID;
    }

    public String getStationID() {
        return stationID;
    }

    public void setStationID(String stationID) {
        this.stationID = stationID;
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }
}
