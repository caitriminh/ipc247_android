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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_VeSom extends RecyclerView.Adapter<Adapter_VeSom.RecyclerViewHolder> {
    private List<T_NhanVienNghiPhep> data = new ArrayList<>();
    private List<T_NhanVienNghiPhep> temp = new ArrayList<>();
    private Unbinder unbinder;
    private Context mContext;
    View view;

    public Adapter_VeSom(Context mContext, List<T_NhanVienNghiPhep> data) {
        this.data = data;
        this.temp.addAll(data);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.cardview_item_vesom, parent, false);
        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtHoTen.setText(data.get(position).getMaNV() + " - " + data.get(position).getHoTen());
        holder.txtPhongBan.setText(data.get(position).getPhongBan());
        holder.txtNgayNghi.setText("Ngày về sớm: " + data.get(position).getNgayNghiText());
        holder.txtLyDo.setText(data.get(position).getLoaiNghiPhep() + " - " + data.get(position).getGhiChu());

        holder.txtNguoiXacNhan.setText(data.get(position).getNguoiXacNhan());
        holder.txtNguoiDuyet.setText(data.get(position).getNguoiDuyet());
        if (data.get(position).getTinhTrang().equals("Đã duyệt")) {
            holder.div_vesom.setBackgroundColor(mContext.getResources().getColor(R.color.color_chuamua));
        } else {
            holder.div_vesom.setBackgroundColor(mContext.getResources().getColor(R.color.divider));
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

        @BindView(R.id.txtNgayNghi)
        TextView txtNgayNghi;

        @BindView(R.id.txtLyDo)
        TextView txtLyDo;

        @BindView(R.id.txtNguoiDuyet)
        TextView txtNguoiDuyet;

        @BindView(R.id.txtNguoiXacNhan)
        TextView txtNguoiXacNhan;

        @BindView(R.id.div_vesom)
        LinearLayout div_vesom;

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