package com.energycostcalculator.satish.electricalenergycostcalculator.models;

public class BillModel {
    private String serviceNumber;
    private float price;
    private int reading;

    public BillModel() {
    }

    public String getServiceNumber() {
        return serviceNumber;
    }

    public void setServiceNumber(String serviceNumber) {
        this.serviceNumber = serviceNumber;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getReading() {
        return reading;
    }

    public void setReading(int reading) {
        this.reading = reading;
    }
}
