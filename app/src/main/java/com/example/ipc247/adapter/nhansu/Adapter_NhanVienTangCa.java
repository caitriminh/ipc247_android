package com.example.ipc247.adapter.nhansu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ipc247.R;
import com.example.ipc247.model.nhanvien.T_NhanVienTangCa;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_NhanVienTangCa extends RecyclerView.Adapter<Adapter_NhanVienTangCa.RecyclerViewHolder> {
    private List<T_NhanVienTangCa> data = new ArrayList<>();
    private List<T_NhanVienTangCa> temp = new ArrayList<>();
    private Unbinder unbinder;
    private Context mContext;
    View view;

    public Adapter_NhanVienTangCa(Context mContext, List<T_NhanVienTangCa> data) {
        this.data = data;
        this.temp.addAll(data);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.cardview_item_tangca, parent, false);
        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtHoTen.setText(data.get(position).getHoTen());
        holder.txtNgayTangCa.setText("Ngày tăng ca: " + data.get(position).getNgayTangCaText());
        holder.txtTuGio.setText("Từ " + data.get(position).getTuGioText() + " - " + data.get(position).getDenGioText());
        holder.txtPhongBan.setText(data.get(position).getPhongBan());
        holder.txtLyDo.setText(data.get(position).getGhiChu());
        holder.txtNguoiXacNhan.setText(data.get(position).getNguoiXacNhan());
        holder.txtNguoiDuyet.setText(data.get(position).getNguoiDuyet());

        if (data.get(position).getTinhTrang().equals("Đã duyệt")) {
            holder.div_TangCa.setBackgroundColor(mContext.getResources().getColor(R.color.color_chuamua));
        } else {
            holder.div_TangCa.setBackgroundColor(mContext.getResources().getColor(R.color.divider));
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtHoTen)
        TextView txtHoTen;

        @BindView(R.id.txtNgayTangCa)
        TextView txtNgayTangCa;

        @BindView(R.id.txtTuGio)
        TextView txtTuGio;

        @BindView(R.id.txtPhongBan)
        TextView txtPhongBan;

        @BindView(R.id.txtLyDo)
        TextView txtLyDo;

        @BindView(R.id.txtNguoiXacNhan)
        TextView txtNguoiXacNhan;

        @BindView(R.id.txtNguoiDuyet)
        TextView txtNguoiDuyet;

        @BindView(R.id.div_TangCa)
        LinearLayout div_TangCa;

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
            for (T_NhanVienTangCa nhanVienTangCa : temp) {
                if (String.valueOf(nhanVienTangCa.getHoTen()).toLowerCase(Locale.getDefault()).contains(charText) ||
                        String.valueOf(nhanVienTangCa.getPhongBan()).toLowerCase(Locale.getDefault()).contains(charText)) {
                    data.add(nhanVienTangCa);
                }
            }
        }
        notifyDataSetChanged();
    }


}