package com.example.ipc247.adapter.bsc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ipc247.R;
import com.example.ipc247.model.bsc.T_KPI_NhanVien;
import com.example.ipc247.model.nhanvien.T_NhanVien;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_BSCNhanVien extends RecyclerView.Adapter<Adapter_BSCNhanVien.RecyclerViewHolder> {
    private List<T_KPI_NhanVien> data = new ArrayList<>();
    private List<T_KPI_NhanVien> temp = new ArrayList<>();
    private Unbinder unbinder;
    private Context mContext;
    View view;

    public Adapter_BSCNhanVien(Context mContext, List<T_KPI_NhanVien> data) {
        this.data = data;
        this.temp.addAll(data);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.cardview_item_bsc_nhan_vien, parent, false);
        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtHoTen.setText(data.get(position).getMaNV() + " - " + data.get(position).getHoTen());
        holder.txtPhongBan.setText(data.get(position).getPhongBan() + " / " + data.get(position).getChucVu());
        holder.txtViTriCongViec.setText("Vị trí công việc: " + data.get(position).getViTriCongViec() + " / Bậc: " + data.get(position).getBac());

        DecimalFormat format1 = new DecimalFormat("#,##0.00");
        Double dblTyLe = data.get(position).getTyle();

        holder.txtTyLe.setText(format1.format(dblTyLe) + " %");
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtHoTen)
        TextView txtHoTen;

        @BindView(R.id.txtPhongBan)
        TextView txtPhongBan;

        @BindView(R.id.txtViTriCongViec)
        TextView txtViTriCongViec;

        @BindView(R.id.txtTyLe)
        TextView txtTyLe;

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
            for (T_KPI_NhanVien kpi_nhanVien : temp) {
                if (String.valueOf(kpi_nhanVien.getHoTen()).toLowerCase(Locale.getDefault()).contains(charText) ||
                        (String.valueOf(kpi_nhanVien.getMaNV()).toLowerCase(Locale.getDefault()).contains(charText)) ||
                        (String.valueOf(kpi_nhanVien.getViTriCongViec()).toLowerCase(Locale.getDefault()).contains(charText))
                ) {
                    data.add(kpi_nhanVien);
                }
            }
        }
        notifyDataSetChanged();
    }


}