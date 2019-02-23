package com.example.landmarkassignment.model;

import java.util.ArrayList;


//POJO for Response from the API
public class ProductResponse {

    private String title;

    private ArrayList<Products> products = new ArrayList<Products>();

    private ArrayList<Conversion> conversion = new ArrayList<Conversion>();

    public ProductResponse(String title, ArrayList<Products> products, ArrayList<Conversion> conversion) {

        this.title = title;
        this.products = products;
        this.conversion = conversion;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Products> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Products> products) {
        this.products = products;
    }

    public ArrayList<Conversion> getConversionRates() {
        return conversion;
    }

    public void setConversionRates(ArrayList<Conversion> conversion) {
        this.conversion = conversion;
    }

}
