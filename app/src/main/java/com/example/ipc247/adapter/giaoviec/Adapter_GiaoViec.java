package com.example.ipc247.adapter.giaoviec;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ipc247.R;
import com.example.ipc247.model.bsc.T_KPI_VitriCongViec;
import com.example.ipc247.model.giaoviec.T_GiaoViec;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_GiaoViec extends RecyclerView.Adapter<Adapter_GiaoViec.RecyclerViewHolder> {
    private List<T_GiaoViec> data = new ArrayList<>();
    private List<T_GiaoViec> temp = new ArrayList<>();
    private Unbinder unbinder;
    private Context mContext;
    View view;

    public Adapter_GiaoViec(Context mContext, List<T_GiaoViec> data) {
        this.data = data;
        this.temp.addAll(data);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.cardview_item_giaoviec, parent, false);
        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        holder.txtNhomCongViec.setText(data.get(position).getNhomCongViec());
        holder.txtCauHinh.setText(data.get(position).getCauHinh());
        if (data.get(position).getCompany() == null) {
            holder.txtKhachHang.setText("-");
        } else {
            holder.txtKhachHang.setText(data.get(position).getCompany());
        }

        holder.txtNguoiNhanViec.setText(data.get(position).getHoTenNhanViec());
        if (data.get(position).getIdDanhGia() == null) {
            holder.txtDanhGia.setText("Chưa đánh giá");
        } else {
            holder.txtDanhGia.setText(data.get(position).getIdDanhGia() + " sao");
        }


        holder.txtNgayBatDau.setText("Bắt đầu: " + data.get(position).getNgayBatDauText());
        holder.txtNgayKetThuc.setText("Kết thúc: " + data.get(position).getNgayKetThucDuKienText());
        holder.txtNgayHoanTat.setText(data.get(position).getNgayHoanThanhText());

        if (data.get(position).getNgayHoanThanhText().equals("-")) {
            holder.div_giaoviec.setBackgroundColor(mContext.getResources().getColor(R.color.divider));
        } else {
            holder.div_giaoviec.setBackgroundColor(mContext.getResources().getColor(R.color.colorBackground));
        }

        if (data.get(position).getIdDanhGia() == null) {
            holder.div_giaoviec.setBackgroundColor(mContext.getResources().getColor(R.color.colorBackground));
        } else {
            holder.div_giaoviec.setBackgroundColor(mContext.getResources().getColor(R.color.md_cyan_200));
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtNhomCongViec)
        TextView txtNhomCongViec;

        @BindView(R.id.txtCauHinh)
        TextView txtCauHinh;

        @BindView(R.id.txtKhachHang)
        TextView txtKhachHang;

        @BindView(R.id.txtDanhGia)
        TextView txtDanhGia;

        @BindView(R.id.txtNgayBatDau)
        TextView txtNgayBatDau;

        @BindView(R.id.txtNgayKetThuc)
        TextView txtNgayKetThuc;

        @BindView(R.id.txtNgayHoanTat)
        TextView txtNgayHoanTat;

        @BindView(R.id.txtNguoiNhanViec)
        TextView txtNguoiNhanViec;

        @BindView(R.id.div_giaoviec)
        LinearLayout div_giaoviec;

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
            for (T_GiaoViec giaoViec : temp) {
                if (String.valueOf(giaoViec.getNhomCongViec()).toLowerCase(Locale.getDefault()).contains(charText) ||
                        String.valueOf(giaoViec.getHoTenNhanViec()).toLowerCase(Locale.getDefault()).contains(charText) ||
                        String.valueOf(giaoViec.getCauHinh()).toLowerCase(Locale.getDefault()).contains(charText)
                ) {
                    data.add(giaoViec);
                }
            }
        }
        notifyDataSetChanged();
    }


}