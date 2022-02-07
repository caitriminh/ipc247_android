package com.example.ipc247.adapter.bsc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ipc247.R;
import com.example.ipc247.model.bsc.T_KPI_PhongBan;
import com.example.ipc247.model.bsc.T_KPI_VitriCongViec;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_ChiTiet_BSC_ViTriCongViec extends RecyclerView.Adapter<Adapter_ChiTiet_BSC_ViTriCongViec.RecyclerViewHolder> {
    private List<T_KPI_VitriCongViec> data = new ArrayList<>();
    private List<T_KPI_VitriCongViec> temp = new ArrayList<>();
    private Unbinder unbinder;
    private Context mContext;
    View view;

    public Adapter_ChiTiet_BSC_ViTriCongViec(Context mContext, List<T_KPI_VitriCongViec> data) {
        this.data = data;
        this.temp.addAll(data);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.cardview_item_detail_bsc_vitri_congviec, parent, false);
        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        DecimalFormat format1 = new DecimalFormat("#,##0.00");

        holder.txtChiTieuCuThe.setText(data.get(position).getStt() + " - " + data.get(position).getChiTieuCuThe());
        holder.txtLoaiChiTieu.setText(data.get(position).getLoaiChiTieu());
        Double dblTamQuanTrong = data.get(position).getTamQuanTrong();
        Double dblMucTieu = data.get(position).getmT_Thang();

        holder.txtTamQuanTrong.setText(format1.format(dblTamQuanTrong) + " %");
        holder.txtMucTieu.setText(format1.format(dblMucTieu) + " " + data.get(position).getDvt());
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtChiTieuCuThe)
        TextView txtChiTieuCuThe;

        @BindView(R.id.txtLoaiChiTieu)
        TextView txtLoaiChiTieu;

        @BindView(R.id.txtMucTieu)
        TextView txtMucTieu;

        @BindView(R.id.txtTamQuanTrong)
        TextView txtTamQuanTrong;



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
            for (T_KPI_VitriCongViec kpi_vitriCongViec : temp) {
                if (String.valueOf(kpi_vitriCongViec.getChiTieuCuThe()).toLowerCase(Locale.getDefault()).contains(charText)
                ) {
                    data.add(kpi_vitriCongViec
                    );
                }
            }
        }
        notifyDataSetChanged();
    }


}