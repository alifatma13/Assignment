package com.example.landmarkassignment.Core;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.test.espresso.IdlingResource;
import android.util.Log;

import com.example.landmarkassignment.R;
import com.example.landmarkassignment.model.Conversion;
import com.example.landmarkassignment.model.ProductResponse;
import com.example.landmarkassignment.model.Products;
import com.example.landmarkassignment.model.rest.ApiClient;
import com.example.landmarkassignment.model.rest.ApiInterface;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//Interactor for the API interaction
public class Interactor implements GetDataContract.Interactor {
    private GetDataContract.onGetDataListener mOnGetDatalistener;
    String title = "";
    ArrayList<Products> products = new ArrayList<>();
    List<Conversion> conversion = new ArrayList<>();
    ArrayList<Products> modifiedForView = new ArrayList<>();
    SharedPreferences prefs;


    public Interactor(GetDataContract.onGetDataListener mOnGetDatalistener) {
        this.mOnGetDatalistener = mOnGetDatalistener;
    }


    //API call to get the response from server
    @Override
    public void initRetrofitCall(final Context context, String url) {

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<ProductResponse> call = apiService.getProductResponse();
        call.enqueue(new Callback<ProductResponse>() {

            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                ProductResponse jsonResponse = response.body();
                products = jsonResponse.getProducts();
                conversion = jsonResponse.getConversionRates();
                title = jsonResponse.getTitle();
                Log.d("Data", "Refreshed");
                mOnGetDatalistener.onSuccess("Data Refreshed", products, title);
                saveConversionRates(context);
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.v("Error", t.getMessage());
                mOnGetDatalistener.onFailure(t.getMessage());
            }
        });
    }


    //convert the prices of product according to the currency chosen by the user
    @Override
    public void convertCurrency(Context context, String toCurrency) {

        Gson gson = new Gson();
        String json = prefs.getString("rate_map","");
        java.lang.reflect.Type type = new TypeToken<HashMap<String,Double>>(){}.getType();
        HashMap<String,Double> conversionMap = gson.fromJson(json, type);


        switch(toCurrency){

            case "AED":
                for (Products product : products)
                {
                    if(product.getCurrency()!="AED")
                    {
                        switch(product.getCurrency()){

                            case "SAR":
                                product.setPrice(String.format("%.2f",Double.parseDouble(product.getPrice()) * conversionMap.get("AEDtoSAR")));
                                break;

                            case "INR":
                                product.setPrice(String.format("%.2f",Double.parseDouble(product.getPrice()) * conversionMap.get("AEDtoINR")));
                                break;
                        }
                        product.setCurrency("AED");    }

                }
                break;

            case "SAR":
                for (Products product : products)
                {
                    if(product.getCurrency()!="SAR")
                    {
                        switch(product.getCurrency()){

                            case "AED":
                                product.setPrice(String.format("%.2f",Double.parseDouble(product.getPrice()) * conversionMap.get("SARtoAED")));
                                break;

                            case "INR":
                                product.setPrice(String.format("%.2f",Double.parseDouble(product.getPrice()) * conversionMap.get("SARtoINR")));
                                break;
                        }
                        product.setCurrency("SAR");
                    }
                }
                break;

            case "INR":
                for (Products product : products)
                {
                    if(product.getCurrency()!="INR")
                    {
                        switch(product.getCurrency()){

                            case "AED":
                                product.setPrice(String.format("%.2f",Double.parseDouble(product.getPrice()) * conversionMap.get("INRtoAED")));
                                break;

                            case "SAR":
                                product.setPrice(String.format("%.2f",Double.parseDouble(product.getPrice()) * conversionMap.get("INRtoSAR")));
                                break;
                        }
                        product.setCurrency("INR");
                    }
                }
                break;
        }

    }

    //Method to save the conversion rates
    public void saveConversionRates(Context context) {
        Map map = new HashMap();

        for (Conversion rate : conversion) {
            if (rate.getFrom().equals(context.getString(R.string.AED)) && rate.getTo().equals(context.getString(R.string.SAR))) {
                map.put(context.getString(R.string.AEDtoSAR), Double.parseDouble(rate.getRate()));
            } else if (rate.getFrom().equals(context.getString(R.string.AED)) && rate.getTo().equals(context.getString(R.string.INR))) {
                map.put(context.getString(R.string.AEDtoINR), Double.parseDouble(rate.getRate()));
            } else if (rate.getFrom().equals(context.getString(R.string.SAR)) && rate.getTo().equals(context.getString(R.string.INR))) {
                map.put(context.getString(R.string.SARtoINR), Double.parseDouble(rate.getRate()));
            } else if (rate.getFrom().equals((context.getString(R.string.INR))) && rate.getTo().equals(context.getString(R.string.AED))) {
                map.put(context.getString(R.string.INRtoAED), Double.parseDouble(rate.getRate()));
            }
        }

        //SAR->INR->AED
        Double aedSAR = (Double) map.get(context.getString(R.string.AEDtoSAR));
        Double SARtoAEDRate = 1 / aedSAR;
        map.put(context.getString(R.string.SARtoAED), SARtoAEDRate);

        //INR->AED->SAR
        Double inrAED = (Double) map.get("SARtoINR");
        Double INRtoSARRate = Double.parseDouble(String.format("%.3f", 1 / inrAED));
        map.put(context.getString(R.string.INRtoSAR), INRtoSARRate);

         prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(map);
        editor.putString("rate_map",json);
        editor.apply();
        editor.commit();
    }


}
