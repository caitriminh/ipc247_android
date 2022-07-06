package com.example.quyetthang.adapter.bsc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quyetthang.R;
import com.example.quyetthang.model.masterdata.T_MasterData;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_BSCPhongBan extends RecyclerView.Adapter<Adapter_BSCPhongBan.RecyclerViewHolder> {
    private List<T_MasterData> data = new ArrayList<>();
    private List<T_MasterData> temp = new ArrayList<>();
    private Unbinder unbinder;
    private Context mContext;
    View view;

    public Adapter_BSCPhongBan(Context mContext, List<T_MasterData> data) {
        this.data = data;
        this.temp.addAll(data);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.item_row_bsc_phongban, parent, false);
        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        holder.txtMaPhongBan.setText(data.get(position).getKey());
        holder.txtPhongBan.setText(data.get(position).getValue());

    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtMaPhongBan)
        TextView txtMaPhongBan;

        @BindView(R.id.txtPhongBan)
        TextView txtPhongBan;


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
            for (T_MasterData phongban : temp) {
                if (String.valueOf(phongban.getValue()).toLowerCase(Locale.getDefault()).contains(charText)
                ) {
                    data.add(phongban);
                }
            }
        }
        notifyDataSetChanged();
    }


}