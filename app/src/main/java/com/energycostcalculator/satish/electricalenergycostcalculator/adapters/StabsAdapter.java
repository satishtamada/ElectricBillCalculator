package com.energycostcalculator.satish.electricalenergycostcalculator.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.energycostcalculator.satish.electricalenergycostcalculator.R;
import com.energycostcalculator.satish.electricalenergycostcalculator.models.StabModel;

import java.util.ArrayList;
import java.util.List;

public class StabsAdapter extends RecyclerView.Adapter<StabsAdapter.MyViewHolder> {

    private PreferManager preferManager;
    private List<StabModel> stabModelList;

    public StabsAdapter(List<StabModel> stabModelList, PreferManager preferManager) {
        this.stabModelList = stabModelList;
        this.preferManager = preferManager;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stab_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final StabModel stabModel = stabModelList.get(position);
        holder.lblRange.setText(stabModel.getMinRange() + " - " + stabModel.getMaxRange());
        holder.lblPrice.setText(stabModel.getUnitPrice() + " /-");
        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stabModelList.remove(position);
                preferManager.storeStab((ArrayList<StabModel>) stabModelList);
                notifyDataSetChanged();

            }
        });
    }

    @Override
    public int getItemCount() {
        return stabModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView lblRange, lblPrice;
        public Button btnRemove;

        public MyViewHolder(View view) {
            super(view);
            lblPrice = (TextView) view.findViewById(R.id.idPrice);
            lblRange = (TextView) view.findViewById(R.id.idRange);
            btnRemove = (Button) view.findViewById(R.id.idRemove);
        }
    }
}
