package com.sabayosja.fordcambodia.android.model;



public class ModelBooking {
    private static final ModelBooking ourInstance = new ModelBooking();

    public static ModelBooking getInstance() {
        return ourInstance;
    }

    private ModelBooking() {
    }

    private String carID;

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }
}
