package com.energycostcalculator.satish.electricalenergycostcalculator.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.energycostcalculator.satish.electricalenergycostcalculator.R;
import com.energycostcalculator.satish.electricalenergycostcalculator.adapters.BillsHistoryAdapter;
import com.energycostcalculator.satish.electricalenergycostcalculator.adapters.PreferManager;
import com.energycostcalculator.satish.electricalenergycostcalculator.models.BillModel;
import com.energycostcalculator.satish.electricalenergycostcalculator.models.StabModel;

import java.util.ArrayList;

public class CalcaulatePriceActivity extends BaseActivity {
    private int reading;
    private String serviceNumber;
    private ArrayList<StabModel> stabModelArrayList;
    private ArrayList<BillModel> billModelArrayList;
    private PreferManager preferManager;
    private ArrayList<Float> units = new ArrayList<>();
    private TextView lblTotalPrice;
    private BillsHistoryAdapter billsHistoryAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_price);
        lblTotalPrice = findViewById(R.id.idPrice);
        recyclerView = findViewById(R.id.recycler_view);
        reading = getIntent().getIntExtra("readingValue", 0);
        serviceNumber = getIntent().getStringExtra("serviceNumber");
        preferManager = new PreferManager(this);
        stabModelArrayList = preferManager.getStabs();
        billModelArrayList = new ArrayList<>();
        billsHistoryAdapter = new BillsHistoryAdapter(billModelArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(billsHistoryAdapter);
        getBillingHistory(serviceNumber);

        if (billModelArrayList.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
        }
        if (stabModelArrayList.size() > 0) {
            calculatePrice(reading);
        } else {
            Toast.makeText(this, "Please Add stabs", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, AddRemoveSlabsActivity.class));
            finish();
        }
    }

    private void getBillingHistory(String serviceNumber) {
        for (BillModel billModel : preferManager.getBills()) {
            if (billModel.getServiceNumber().equals(serviceNumber)) {
                billModelArrayList.add(billModel);
            }
        }
        if (billModelArrayList.size() > 0) {
            billsHistoryAdapter.notifyDataSetChanged();
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void calculatePrice(int reading) {
        float unitprice;
        int tempReading = reading;
        float total = 0.0f;

        for (int i = 0; i < stabModelArrayList.size(); i++) {
            StabModel stabModel = stabModelArrayList.get(i);
            if (reading > stabModel.getMinRange() && reading < stabModel.getMaxRange()+1) {
                unitprice = stabModel.getUnitPrice();
                tempReading=reading-(stabModel.getMinRange()-1);
                total=total+(tempReading*unitprice);
                break;
            }else{
                unitprice=stabModel.getUnitPrice();
                tempReading=(reading-stabModel.getMaxRange());
                total=total+((reading-tempReading)*unitprice);
            }
        }
        lblTotalPrice.setText("Total" + total);
        saveUserData(reading, total, serviceNumber);
    }

    private void saveUserData(int reading, float total, String serviceNumber) {
        BillModel billModel = new BillModel();
        billModel.setPrice(total);
        billModel.setReading(reading);
        billModel.setServiceNumber(serviceNumber);
        billModelArrayList=preferManager.getBills();
        billModelArrayList.add(billModel);
        preferManager.storeBills(billModelArrayList);
        if (billModelArrayList.size() > 0) {
            billsHistoryAdapter.notifyDataSetChanged();
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}
