package com.example.landmarkassignment.Core;

import android.content.Context;
import androidx.test.espresso.IdlingResource;


import com.example.landmarkassignment.model.Products;

import java.util.ArrayList;


//Base contract for the views and the presenters
public class GetDataContract {

    public interface View {
        void onGetDataSuccess(String message, ArrayList<Products> products, String title);

        void onGetDataFailure(String message);
    }

    interface Presenter {
        void getDataFromURL(Context context, String url);

        void convertCurrency(Context context, String toCurrency);
    }

    interface Interactor {
        void initRetrofitCall(Context context, String url);

        void convertCurrency(Context context, String toCurrency);

    }

    interface onGetDataListener {
        void onSuccess(String message, ArrayList<Products> products, String title);

        void onFailure(String message);
    }
}
