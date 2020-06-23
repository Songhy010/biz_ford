package com.sabayosja.fordcambodia.android.model;


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
    private ArrayList<String> arrRepairID;

    public ArrayList<String> getArrRepairID() {
        return arrRepairID;
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
