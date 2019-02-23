package com.example.landmarkassignment.model;

//POJO for the conversion rates
public class Conversion {

    private String from;

    private String to;

    private String rate;

    public Conversion(String from, String to, String rate, int code) {
        this.from = from;
        this.to = to;
        this.rate = rate;

    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
