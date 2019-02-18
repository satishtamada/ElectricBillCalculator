package com.energycostcalculator.satish.electricalenergycostcalculator.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.energycostcalculator.satish.electricalenergycostcalculator.R;
import com.energycostcalculator.satish.electricalenergycostcalculator.adapters.PreferManager;
import com.energycostcalculator.satish.electricalenergycostcalculator.adapters.StabsAdapter;
import com.energycostcalculator.satish.electricalenergycostcalculator.models.StabModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddRemoveSlabsActivity extends AppCompatActivity {

    EditText etInputMinRange;
    EditText etInputMaxRange;
    EditText etInputPrice;
    Button btnAddStab;
    RecyclerView recyclerView;
    TextView lblEmptyList;

    private PreferManager preferManager;
    private ArrayList<StabModel> stabModelArrayList;
    private StabsAdapter stabsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_remove_slabs);
        lblEmptyList=findViewById(R.id.idEmptyList);
        btnAddStab=findViewById(R.id.idAddStab);
        recyclerView=findViewById(R.id.recycler_view);
        etInputPrice=findViewById(R.id.idInputPrice);
        etInputMaxRange=findViewById(R.id.idMaxRange);
        etInputMinRange=findViewById(R.id.idMinRange);

        preferManager = new PreferManager(this);
        stabModelArrayList = preferManager.getStabs();
        stabsAdapter = new StabsAdapter(stabModelArrayList,preferManager);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(stabsAdapter);
        if (stabModelArrayList.size() != 0) {
            lblEmptyList.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }else {
            recyclerView.setVisibility(View.GONE);
            lblEmptyList.setVisibility(View.VISIBLE);
        }

        btnAddStab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddStabClicked();
            }
        });
    }

    public void onAddStabClicked() {
        String minRange = etInputMinRange.getText().toString();
        String maxRange = etInputMaxRange.getText().toString();
        String price = etInputPrice.getText().toString();

        if (TextUtils.isEmpty(minRange) || TextUtils.isEmpty(maxRange) || TextUtils.isEmpty(price)) {
            Toast.makeText(this, "Please enter details", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Integer.parseInt(price) == 0 || Integer.parseInt(maxRange) == 0) {
            Toast.makeText(this, "Please enter valid details", Toast.LENGTH_SHORT).show();
        } else if(Integer.parseInt(minRange)> Integer.parseInt(maxRange)){
            Toast.makeText(this, "Please enter valid max price", Toast.LENGTH_SHORT).show();
        }else {
            StabModel stabModel = new StabModel();
            stabModel.setMaxRange(Integer.parseInt(maxRange));
            stabModel.setMinRange(Integer.parseInt(minRange));
            stabModel.setUnitPrice(Float.parseFloat(price));
            if (!preferManager.getStabs().contains(stabModel)) {
                preferManager.getStabs().add(stabModel);
                stabModelArrayList.add(stabModel);
                preferManager.storeStab(stabModelArrayList);
                stabsAdapter.notifyDataSetChanged();
                recyclerView.setVisibility(View.VISIBLE);
                lblEmptyList.setVisibility(View.GONE);
            }
        }
    }
}
