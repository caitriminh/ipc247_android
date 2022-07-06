package com.example.quyetthang.adapter.kho;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quyetthang.R;
import com.example.quyetthang.model.kho.T_TonKho;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_TonKho extends RecyclerView.Adapter<Adapter_TonKho.RecyclerViewHolder> {
    private List<T_TonKho> data = new ArrayList<>();
    private List<T_TonKho> temp = new ArrayList<>();
    private Unbinder unbinder;
    private Context mContext;
    View view;

    public Adapter_TonKho(Context mContext, List<T_TonKho> data) {
        this.data = data;
        this.temp.addAll(data);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.cardview_item_tonkho, parent, false);
        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        DecimalFormat format1 = new DecimalFormat("#,##0.0");
        Double dblSoDuCuoiKy = data.get(position).getSoDuCuoiKy();
        holder.txtSoDuCuoiKy.setText("Số lượng: " + format1.format(dblSoDuCuoiKy));
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

        @BindView(R.id.txtSoDuCuoiKy)
        TextView txtSoDuCuoiKy;

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
            for (T_TonKho tonKho : temp) {
                if (String.valueOf(tonKho.getProductCode()).toLowerCase(Locale.getDefault()).contains(charText) ||
                        String.valueOf(tonKho.getDescription()).toLowerCase(Locale.getDefault()).contains(charText) ||
                        String.valueOf(tonKho.getShortName()).toLowerCase(Locale.getDefault()).contains(charText)) {
                    data.add(tonKho);
                }
            }
        }
        notifyDataSetChanged();
    }


}