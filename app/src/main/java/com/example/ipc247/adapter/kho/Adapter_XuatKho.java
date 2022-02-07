package com.example.ipc247.adapter.kho;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ipc247.R;
import com.example.ipc247.model.kho.T_Inventory;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_XuatKho extends RecyclerView.Adapter<Adapter_XuatKho.RecyclerViewHolder> {
    private List<T_Inventory> data = new ArrayList<>();
    private List<T_Inventory> temp = new ArrayList<>();
    private Unbinder unbinder;
    private Context mContext;
    View view;

    public Adapter_XuatKho(Context mContext, List<T_Inventory> data) {
        this.data = data;
        this.temp.addAll(data);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.cardview_item_xuatkho, parent, false);
        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        DecimalFormat format1 = new DecimalFormat("#,##0");
        Double dblSoLuong = data.get(position).getSoLuong();
        holder.txtSoLuong.setText(format1.format(dblSoLuong));

        Double dblDonGia = data.get(position).getDonGia();
        holder.txtSoLuong.setText("Số lượng: " + format1.format(dblSoLuong) + " / Đơn giá: " + format1.format(dblDonGia) + " (VNĐ)");

        holder.txtKhachHang.setText(data.get(position).getTenNCC());
        holder.txtOrderName.setText(data.get(position).getOrderName());
        holder.txtProductCode.setText(data.get(position).getProductCode());
        holder.txtDescription.setText(data.get(position).getDescription());
        holder.txtNgayXuat.setText("Ngày xuất: " + data.get(position).getNgayXuatText());
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

        @BindView(R.id.txtKhachHang)
        TextView txtKhachHang;

        @BindView(R.id.txtOrderName)
        TextView txtOrderName;

        @BindView(R.id.txtSoLuong)
        TextView txtSoLuong;


        @BindView(R.id.txtNgayXuat)
        TextView txtNgayXuat;

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
            for (T_Inventory inventory : temp) {
                if (String.valueOf(inventory.getProductCode()).toLowerCase(Locale.getDefault()).contains(charText) ||
                        String.valueOf(inventory.getDescription()).toLowerCase(Locale.getDefault()).contains(charText)) {
                    data.add(inventory);
                }
            }
        }
        notifyDataSetChanged();
    }


}