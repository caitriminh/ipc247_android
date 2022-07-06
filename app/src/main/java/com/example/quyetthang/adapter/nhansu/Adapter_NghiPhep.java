package com.example.quyetthang.adapter.nhansu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quyetthang.R;
import com.example.quyetthang.model.nhanvien.T_NhanVienNghiPhep;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_NghiPhep extends RecyclerView.Adapter<Adapter_NghiPhep.RecyclerViewHolder> {
    private List<T_NhanVienNghiPhep> data = new ArrayList<>();
    private List<T_NhanVienNghiPhep> temp = new ArrayList<>();
    private Unbinder unbinder;
    private Context mContext;
    View view;

    public Adapter_NghiPhep(Context mContext, List<T_NhanVienNghiPhep> data) {
        this.data = data;
        this.temp.addAll(data);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.cardview_item_nghiphep, parent, false);
        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtHoTen.setText(data.get(position).getMaNV() + " - " + data.get(position).getHoTen());
        holder.txtPhongBan.setText(data.get(position).getPhongBan());
        holder.txtTuNgay.setText("Từ ngày: " + data.get(position).getTuNgayText());
        holder.txtDenNgay.setText("Đến ngày: " + data.get(position).getDenNgayText());
        holder.txtLyDo.setText(data.get(position).getLoaiNghiPhep() + " - " + data.get(position).getGhiChu());

        DecimalFormat format = new DecimalFormat("#,##0.0");
        Double dblSoNgayNghi = data.get(position).getSoNgayNghi();

        holder.txtSoNgay.setText(format.format(dblSoNgayNghi) + " (ngày)");
        holder.txtNguoiXacNhan.setText(data.get(position).getNguoiXacNhan());
        holder.txtNguoiDuyet.setText(data.get(position).getNguoiDuyet());
        if (data.get(position).getTinhTrang().equals("Đã duyệt")) {
            holder.div_nghiphep.setBackgroundColor(mContext.getResources().getColor(R.color.color_chuamua));
        } else {
            holder.div_nghiphep.setBackgroundColor(mContext.getResources().getColor(R.color.divider));
        }
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

        @BindView(R.id.txtTuNgay)
        TextView txtTuNgay;

        @BindView(R.id.txtDenNgay)
        TextView txtDenNgay;

        @BindView(R.id.txtLyDo)
        TextView txtLyDo;

        @BindView(R.id.txtSoNgay)
        TextView txtSoNgay;

        @BindView(R.id.txtNguoiDuyet)
        TextView txtNguoiDuyet;

        @BindView(R.id.txtNguoiXacNhan)
        TextView txtNguoiXacNhan;

        @BindView(R.id.div_nghiphep)
        LinearLayout div_nghiphep;

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
            for (T_NhanVienNghiPhep nghiPhep : temp) {
                if (String.valueOf(nghiPhep.getMaNV()).toLowerCase(Locale.getDefault()).contains(charText) ||
                        (String.valueOf(nghiPhep.getLoaiNghiPhep()).toLowerCase(Locale.getDefault()).contains(charText))

                ) {
                    data.add(nghiPhep);
                }
            }
        }
        notifyDataSetChanged();
    }


}