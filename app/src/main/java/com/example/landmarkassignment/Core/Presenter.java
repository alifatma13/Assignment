package com.example.landmarkassignment.Core;

import android.content.Context;
import androidx.test.espresso.IdlingResource;

import com.example.landmarkassignment.model.Products;

import java.util.ArrayList;


// Presenter with the list of methods for Business implementation
public class Presenter implements GetDataContract.Presenter, GetDataContract.onGetDataListener {
    private GetDataContract.View mGetDataView;
    private Interactor mInteractor;


    public Presenter(GetDataContract.View mGetDataView) {
        this.mGetDataView = mGetDataView;
        mInteractor = new Interactor(this);
    }

    @Override
    public void getDataFromURL(Context context, String url) {
        mInteractor.initRetrofitCall(context, url);
    }

    @Override
    public void convertCurrency(Context context, String toCurrency) {
        mInteractor.convertCurrency(context, toCurrency);
    }


    @Override
    public void onSuccess(String message, ArrayList<Products> products, String title) {
        mGetDataView.onGetDataSuccess(message, products, title);
    }

    @Override
    public void onFailure(String message) {
        mGetDataView.onGetDataFailure(message);
    }
}
