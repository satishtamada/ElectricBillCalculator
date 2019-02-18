package com.energycostcalculator.satish.electricalenergycostcalculator.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.energycostcalculator.satish.electricalenergycostcalculator.R;
import com.energycostcalculator.satish.electricalenergycostcalculator.models.BillModel;

import java.util.List;

public class BillsHistoryAdapter extends RecyclerView.Adapter<BillsHistoryAdapter.MyViewHolder> {

    private List<BillModel> stabModelList;

    public BillsHistoryAdapter(List<BillModel> stabModelList) {
        this.stabModelList = stabModelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bill_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final BillModel billModel = stabModelList.get(position);
        holder.lblReadingValue.setText("Reading :" + billModel.getReading());
        holder.lblPrice.setText("Price :" + billModel.getPrice());
        holder.lblServiceName.setText(billModel.getServiceNumber());
    }

    @Override
    public int getItemCount() {
        return stabModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView lblReadingValue, lblPrice;
        public TextView lblServiceName;

        public MyViewHolder(View view) {
            super(view);
            lblPrice = (TextView) view.findViewById(R.id.idPrice);
            lblReadingValue = (TextView) view.findViewById(R.id.idReading);
            lblServiceName = (TextView) view.findViewById(R.id.idServiceNumber);
        }
    }
}
