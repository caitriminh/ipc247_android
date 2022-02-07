package com.example.ipc247.adapter.kho;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ipc247.R;
import com.example.ipc247.model.kho.T_XuatTemp;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_XuatTemp extends RecyclerView.Adapter<Adapter_XuatTemp.RecyclerViewHolder> {
    private List<T_XuatTemp> data = new ArrayList<>();
    private List<T_XuatTemp> temp = new ArrayList<>();
    private Unbinder unbinder;
    private Context mContext;
    View view;

    public Adapter_XuatTemp(Context mContext, List<T_XuatTemp> data) {
        this.data = data;
        this.temp.addAll(data);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.cardview_item_xuattemp, parent, false);
        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        DecimalFormat format1 = new DecimalFormat("#,##0");
        Double dblPrice= data.get(position).getPrice();

        holder.txtPrice.setText("Số lượng: 1 / Đơn giá: " + format1.format(dblPrice) + " (VNĐ)");
        holder.txtHangSanXuat.setText(data.get(position).getShortName());

        holder.txtProductCode.setText(data.get(position).getProductCode());
        holder.txtDescription.setText(data.get(position).getDescription());
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtProductCode)
        TextView txtProductCode;

        @BindView(R.id.txtDescription)
        TextView txtDescription;

        @BindView(R.id.txtPrice)
        TextView txtPrice;
//
        @BindView(R.id.txtHangSanXuat)
        TextView txtHangSanXuat;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }

    public void filter(String keys) {
        data.clear();
        String charText = keys.toLowerCase(Locale.getDefault());
        if (charText.length() == 0) {
            data.addAll(temp);
        } else {
            for (T_XuatTemp xuatTemp : temp) {
                if (String.valueOf(xuatTemp.getProductCode()).toLowerCase(Locale.getDefault()).contains(charText) ||
                        String.valueOf(xuatTemp.getDescription()).toLowerCase(Locale.getDefault()).contains(charText)) {
                    data.add(xuatTemp);
                }
            }
        }
        notifyDataSetChanged();
    }


}