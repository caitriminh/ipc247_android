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
import com.example.ipc247.model.nhanvien.T_PhepNam;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_PhepNam extends RecyclerView.Adapter<Adapter_PhepNam.RecyclerViewHolder> {
    private List<T_PhepNam> data = new ArrayList<>();
    private List<T_PhepNam> temp = new ArrayList<>();
    private Unbinder unbinder;
    private Context mContext;
    View view;

    public Adapter_PhepNam(Context mContext, List<T_PhepNam> data) {
        this.data = data;
        this.temp.addAll(data);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.cardview_item_phep_nam, parent, false);
        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.txtHoTen.setText(data.get(position).getMaNV() + " - " + data.get(position).getHoTen());
        holder.txtPhongBan.setText(data.get(position).getPhongBan() + " / " + data.get(position).getChucVu());
        DecimalFormat format2 = new DecimalFormat("#,##0.0");
        Double dblPhepNamConDu = data.get(position).getPhepBoSung();
        Double dblTongNgayPhep = data.get(position).getTongNgayPhep();
        Double dblSoNgayNghi = data.get(position).getSoNgayNghi();
        Double dblSoNgayConlai = data.get(position).getPhepNamConLai();

        holder.txtPhepNamConDu.setText(format2.format(dblPhepNamConDu));
        holder.txtTongNgayPhep.setText(format2.format(dblTongNgayPhep));
        holder.txtSoNgayNghi.setText(format2.format(dblSoNgayNghi));
        holder.txtSoNgayConLai.setText(format2.format(dblSoNgayConlai));
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

        @BindView(R.id.txtPhepNamConDu)
        TextView txtPhepNamConDu;

        @BindView(R.id.txtTongNgayPhep)
        TextView txtTongNgayPhep;

        @BindView(R.id.txtSoNgayNghi)
        TextView txtSoNgayNghi;

        @BindView(R.id.txtSoNgayConLai)
        TextView txtSoNgayConLai;

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
            for (T_PhepNam phepNam : temp) {
                if (String.valueOf(phepNam.getHoTen()).toLowerCase(Locale.getDefault()).contains(charText) ||
                        String.valueOf(phepNam.getMaNV()).toLowerCase(Locale.getDefault()).contains(charText)) {
                    data.add(phepNam);
                }
            }
        }
        notifyDataSetChanged();
    }


}