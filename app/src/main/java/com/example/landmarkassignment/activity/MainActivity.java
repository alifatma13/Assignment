package com.example.landmarkassignment.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.landmarkassignment.Core.GetDataContract;
import com.example.landmarkassignment.Core.Presenter;
import com.example.landmarkassignment.R;
import com.example.landmarkassignment.adapter.ProductAdapter;
import com.example.landmarkassignment.model.Products;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetDataContract.View, View.OnClickListener {
    private Presenter mPresenter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ProductAdapter productAdapter;
    private TextView titleView;
    private Button AED;
    private Button SAR;
    private Button INR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titleView = (TextView) findViewById(R.id.Title);
        mPresenter = new Presenter(this);
        mPresenter.getDataFromURL(getApplicationContext(), "" );
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        AED = (Button) findViewById(R.id.button_AED);
        SAR = (Button) findViewById(R.id.button_SAR);
        INR = (Button) findViewById(R.id.button_INR);
        AED.setOnClickListener(this);
        SAR.setOnClickListener(this);
        INR.setOnClickListener(this);

    }
   //update the recycler view on success of API call
    @Override
    public void onGetDataSuccess(String message, ArrayList<Products> products, String title) {
        productAdapter = new ProductAdapter(getApplicationContext(), products);
        recyclerView.setAdapter(productAdapter);
        titleView.setText(title);
    }

    @Override
    public void onGetDataFailure(String message) {
        Log.d("Status", message);
    }


    //call to currency converter on button click
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button_AED:
                mPresenter.convertCurrency(getApplicationContext(), getString(R.string.AED));
                productAdapter.notifyDataSetChanged();
                break;

            case R.id.button_SAR:
                mPresenter.convertCurrency(getApplicationContext(), getString(R.string.SAR));
                productAdapter.notifyDataSetChanged();
                break;

            case R.id.button_INR:
                mPresenter.convertCurrency(getApplicationContext(), getString(R.string.INR));
                productAdapter.notifyDataSetChanged();
                break;
        }
    }
}
