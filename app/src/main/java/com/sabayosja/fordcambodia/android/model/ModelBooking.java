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
    private String station;
    private String mileageID;
    private String serviceTypeID;
    private ArrayList<String> arrRepairID = new ArrayList<>();
    private ArrayList<String> arrRepairName = new ArrayList<>();
    private JSONArray arrRepair = new JSONArray();
    private String date;
    private String userName;
    private String model;
    private String modelYear;
    private String plateNumber;
    private String mileage;
    private String time;

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModelYear() {
        return modelYear;
    }

    public void setModelYear(String modelYear) {
        this.modelYear = modelYear;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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
        return serviceTypeID;
    }

    public void setServiceTypeID(String serviceTypeID) {
        this.serviceTypeID = serviceTypeID;
    }

    public String getMileageID() {
        return mileageID;
    }

    public void setMileageID(String mileageID) {

        this.mileageID = mileageID;
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
