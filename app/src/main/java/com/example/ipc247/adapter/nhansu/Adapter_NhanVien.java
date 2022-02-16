package com.example.ipc247.adapter.nhansu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ipc247.R;
import com.example.ipc247.model.nhanvien.T_NhanVien;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_NhanVien extends RecyclerView.Adapter<Adapter_NhanVien.RecyclerViewHolder> {
    private List<T_NhanVien> data = new ArrayList<>();
    private List<T_NhanVien> temp = new ArrayList<>();
    private Unbinder unbinder;
    private Context mContext;
    View view;

    public Adapter_NhanVien(Context mContext, List<T_NhanVien> data) {
        this.data = data;
        this.temp.addAll(data);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.cardview_item_nhan_vien, parent, false);
        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtHoTen.setText(data.get(position).getMaNV() + " - " + data.get(position).getHoTen() + " / " + data.get(position).getGioiTinh());
        holder.txtPhongBan.setText(data.get(position).getPhongBan() + " / " + data.get(position).getChucVu());
        holder.txtNgaySinh.setText(data.get(position).getNgaySinhText());
        holder.txtNoiSinh.setText(data.get(position).getNoiSinh());
        holder.txtDiaChi.setText(data.get(position).getDiaChi());
        holder.txtNgayVaoLam.setText(data.get(position).getNgayVaoLamText());
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

        @BindView(R.id.txtNgaySinh)
        TextView txtNgaySinh;

        @BindView(R.id.txtNgayVaoLam)
        TextView txtNgayVaoLam;

        @BindView(R.id.txtNoiSinh)
        TextView txtNoiSinh;

        @BindView(R.id.txtDiaChi)
        TextView txtDiaChi;



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
            for (T_NhanVien nhanVien : temp) {
                if (String.valueOf(nhanVien.getHoTen()).toLowerCase(Locale.getDefault()).contains(charText) ||
                        String.valueOf(nhanVien.getMaNV()).toLowerCase(Locale.getDefault()).contains(charText)) {
                    data.add(nhanVien);
                }
            }
        }
        notifyDataSetChanged();
    }


}