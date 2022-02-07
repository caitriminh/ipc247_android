package com.example.ipc247.adapter.bsc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ipc247.R;
import com.example.ipc247.model.bsc.T_KPI_VitriCongViec;
import com.example.ipc247.model.masterdata.T_MasterData;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_BSC_ViTriCongViec extends RecyclerView.Adapter<Adapter_BSC_ViTriCongViec.RecyclerViewHolder> {
    private List<T_KPI_VitriCongViec> data = new ArrayList<>();
    private List<T_KPI_VitriCongViec> temp = new ArrayList<>();
    private Unbinder unbinder;
    private Context mContext;
    View view;

    public Adapter_BSC_ViTriCongViec(Context mContext, List<T_KPI_VitriCongViec> data) {
        this.data = data;
        this.temp.addAll(data);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.item_row_bsc_vitri_congviec, parent, false);
        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        holder.txtPhongBan.setText(data.get(position).getPhongBan());
        holder.txtViTriCongViec.setText(data.get(position).getViTriCongViec());
        holder.txtBac.setText(String.valueOf(data.get(position).getBac()));
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtPhongBan)
        TextView txtPhongBan;

        @BindView(R.id.txtViTriCongViec)
        TextView txtViTriCongViec;

        @BindView(R.id.txtBac)
        TextView txtBac;

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
            for (T_KPI_VitriCongViec vitricongviec : temp) {
                if (String.valueOf(vitricongviec.getViTriCongViec()).toLowerCase(Locale.getDefault()).contains(charText) ||
                        String.valueOf(vitricongviec.getPhongBan()).toLowerCase(Locale.getDefault()).contains(charText)
                ) {
                    data.add(vitricongviec);
                }
            }
        }
        notifyDataSetChanged();
    }


}