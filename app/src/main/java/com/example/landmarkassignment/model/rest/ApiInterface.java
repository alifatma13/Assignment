package com.example.landmarkassignment.model.rest;

import com.example.landmarkassignment.model.ProductResponse;

import retrofit2.Call;
import retrofit2.http.GET;

// Get method call for the retrofit API
public interface ApiInterface {

    @GET("assignment.json")
    Call<ProductResponse> getProductResponse();
}
