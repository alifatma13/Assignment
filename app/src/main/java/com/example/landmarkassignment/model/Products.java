package com.example.landmarkassignment.model;


//POJO for Products
public class Products {

    private String url;

    private String name;

    private String price;

    private String currency;

    public Products(String url, String name, String price, String currency) {
        this.url = url;
        this.name = name;
        this.price = price;
        this.currency = currency;
    }

    public String getURL() {
        return url;
    }

    public void setURL(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
