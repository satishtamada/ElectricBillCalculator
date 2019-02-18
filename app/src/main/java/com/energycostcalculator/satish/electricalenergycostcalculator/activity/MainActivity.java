package com.energycostcalculator.satish.electricalenergycostcalculator.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.energycostcalculator.satish.electricalenergycostcalculator.R;
import com.energycostcalculator.satish.electricalenergycostcalculator.adapters.PreferManager;
import com.energycostcalculator.satish.electricalenergycostcalculator.models.StabModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etInputReadingValue;
    EditText etInputServiceNumber;
    private PreferManager preferManager;
    private ArrayList<StabModel> stabModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etInputReadingValue = findViewById(R.id.idReadingValue);
        etInputServiceNumber = findViewById(R.id.idServiceNumber);
        preferManager = new PreferManager(this);
        Button btnAddStab = findViewById(R.id.idAddStab);
        Button btnCalculatePrice = findViewById(R.id.idBtnCalculatePrice);

        stabModelArrayList = preferManager.getStabs();
        /**
         * here iam adding defalut stubs for calculating price based on it size
         */
        if (stabModelArrayList.size()==0) {
            StabModel stabModel = new StabModel();
            stabModel.setMinRange(1);
            stabModel.setMaxRange(100);
            stabModel.setUnitPrice(5);
            stabModelArrayList.add(stabModel);
            StabModel stabModel1 = new StabModel();
            stabModel1.setMinRange(101);
            stabModel1.setMaxRange(500);
            stabModel1.setUnitPrice(8);
            stabModelArrayList.add(stabModel1);
            StabModel stabModel2 = new StabModel();
            stabModel2.setMinRange(501);
            stabModel2.setMaxRange(1000);
            stabModel2.setUnitPrice(10);
            stabModelArrayList.add(stabModel2);
            preferManager.storeStab(stabModelArrayList);
        }
        btnCalculatePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBtnCalucatePriceClicked();
            }
        });

        btnAddStab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddStabsClicked();
            }
        });


    }


    public void onBtnCalucatePriceClicked() {
        String readingValue = etInputReadingValue.getText().toString();
        String serviceNumber = etInputServiceNumber.getText().toString().trim();

        if (TextUtils.isEmpty(serviceNumber)||TextUtils.isEmpty(readingValue)) {
            Toast.makeText(this, "Please enter your details", Toast.LENGTH_SHORT).show();
            return;
        }

        if (readingValue.equals("0") || readingValue.equals("00")) {
            Toast.makeText(this, "Please enter valid reading", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Float.parseFloat(readingValue) != 0.0) {
            etInputServiceNumber.setText("");
            etInputReadingValue.setText("");
            Intent intent = new Intent(this, CalcaulatePriceActivity.class);
            intent.putExtra("readingValue", Integer.parseInt(readingValue));
            intent.putExtra("serviceNumber", serviceNumber);
            startActivity(intent);
        }
    }


    public void onAddStabsClicked() {
        startActivity(new Intent(this, AddRemoveSlabsActivity.class));
    }
}
