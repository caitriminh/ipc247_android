package com.example.quyetthang.adapter.nhansu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quyetthang.R;
import com.example.quyetthang.model.tienluong.BangLuong_NhanVien;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_TienLuong extends RecyclerView.Adapter<Adapter_TienLuong.RecyclerViewHolder> {
    private List<BangLuong_NhanVien> data = new ArrayList<>();
    private List<BangLuong_NhanVien> temp = new ArrayList<>();
    private Unbinder unbinder;
    private Context mContext;
    View view;

    public Adapter_TienLuong(Context mContext, List<BangLuong_NhanVien> data) {
        this.data = data;
        this.temp.addAll(data);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.cardview_item_tien_luong, parent, false);
        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        DecimalFormat format1 = new DecimalFormat("#,##0");
        DecimalFormat format2 = new DecimalFormat("#,##0.00");

        Double dblMucLuong = data.get(position).getMucLuong();
        Double dblLuongCoBan = data.get(position).getLuongCoBan();
        Double dblLuongKPI = data.get(position).getLuongKPI();
        Double dblCongThucTe = data.get(position).getNgayCongThucTe();
        Double dblLuongThucTe = data.get(position).getLuongThucTe();
        Double dblLuongPhepNam = data.get(position).getLuongPhepNam();
        Double dblThuNhapNgayCong = data.get(position).getThuNhapThucTe();

        Double dblNghiPhepNam = data.get(position).getNghiPhepNam();
        Double dblLuongChuyenCan = data.get(position).getChuyenCan();

        Double dblCongTangCa = data.get(position).getCongTangCa();
        Double dblLuongTangCa = data.get(position).getLuongTangCa();

        Double dblXangXe = data.get(position).getXangXe_DienThoai();
        Double dblCongTacPhi = data.get(position).getCongTacPhi();
        Double dblHoaHong = data.get(position).getTienHoaHong();
        Double dblPhuCapKhac = data.get(position).getLuongPhuCap();
        Double dblThuNhapTruocThue = data.get(position).getThuNhapTruocThue();

        Double dblThueTNCN = data.get(position).getThueTNCN();
        Double dblBHXH = data.get(position).getBhxh();
        Double dblPhat = data.get(position).getPhat();
        Double dblThucLanh = data.get(position).getThucLanh();

        Double dblCong100 = data.get(position).getCongHeSo1();
        Double dblCong70 = data.get(position).getCongHeSo2();
        Double dblCong50 = data.get(position).getCongHeSo3();

        Double dblLuong100 = data.get(position).getLuong100();
        Double dblLuong70 = data.get(position).getLuong70();
        Double dblLuong50 = data.get(position).getLuong50();

        Double dblThamNien = data.get(position).getThamNien();
        Double dblUngLuong = data.get(position).getUngLuong();

        Double dblCongLeTet = data.get(position).getCongLeTet();
        Double dblLuongLeTet = data.get(position).getLuongLeTet();

        Double dblHeSoLuong = data.get(position).getHeSoLuong();
        Double dblKPI = data.get(position).getKpi();
        Double dblLuongKPI2 = data.get(position).getLuongHS_KPI();
        Double dblThucNhan = data.get(position).getThucLanhSauKPI();

        holder.txtHoTen.setText(data.get(position).getMaNV() + " - " + data.get(position).getHoTen() + " (" + data.get(position).getNgayCongChuan() + ")");
        holder.txtPhongBan.setText(data.get(position).getPhongBan() + " / " + data.get(position).getChucVu());
        holder.txtBacLuong.setText("Bậc lương: " + data.get(position).getBacLuong().toString());
        holder.txtMucLuong.setText(format1.format(dblMucLuong));
        holder.txtLuongKPI.setText(format1.format(dblLuongKPI));
        holder.txtLuongCoban.setText(format1.format(dblLuongCoBan));
        holder.txtCongThucTe.setText(format2.format(dblCongThucTe));
        holder.txtLuongThucTe.setText(format1.format(dblLuongThucTe));

        holder.txtCongTangCa.setText(format2.format(dblCongTangCa));
        holder.txtLuongTangCa.setText(format1.format(dblLuongTangCa));

        holder.txtNghiPhepNam.setText(format2.format(dblNghiPhepNam));
        holder.txtLuongPhepNam.setText(format1.format(dblLuongPhepNam));

        holder.txtThuNhapNgayCong.setText(format1.format(dblThuNhapNgayCong));
        holder.txtChuyenCan.setText(format1.format(dblLuongChuyenCan));
        holder.txtXangXe.setText(format1.format(dblXangXe));
        holder.txtCongTacPhi.setText(format1.format(dblCongTacPhi));
        holder.txtHoaHong.setText(format1.format(dblHoaHong));
        holder.txtPhuCapKhac.setText(format1.format(dblPhuCapKhac));
        holder.txtThuNhapTruocThue.setText(format1.format(dblThuNhapTruocThue));

        holder.txtThueTNCN.setText(format1.format(dblThueTNCN));
        holder.txtBHXH.setText(format1.format(dblBHXH));
        holder.txtPhat.setText(format1.format(dblPhat));
        holder.txtThucLanh.setText(format1.format(dblThucLanh));

        holder.txtCong100.setText(format2.format(dblCong100));
        holder.txtCong70.setText(format2.format(dblCong70));
        holder.txtCong50.setText(format2.format(dblCong50));

        holder.txtLuong100.setText(format1.format(dblLuong100));
        holder.txtLuong70.setText(format1.format(dblLuong70));
        holder.txtLuong50.setText(format1.format(dblLuong50));

        holder.txtThamNien.setText(format1.format(dblThamNien));
        holder.txtTamUng.setText(format1.format(dblUngLuong));

        holder.txtCongLeTet.setText(format2.format(dblCongLeTet));
        holder.txtLuongLeTet.setText(format1.format(dblLuongLeTet));

        holder.txtXepLoai.setText("Xếp Loại: " + data.get(position).getXepLoai());
        holder.txtKPI.setText(format2.format(dblKPI));
        holder.txtHeSoLuong.setText(format2.format(dblHeSoLuong));
        holder.txtLuongKPI2.setText(format1.format(dblLuongKPI2));

        holder.txtThucNhan.setText(format1.format(dblThucNhan));
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

        @BindView(R.id.txtBacLuong)
        TextView txtBacLuong;

        @BindView(R.id.txtMucLuong)
        TextView txtMucLuong;

        @BindView(R.id.txtLuongCoban)
        TextView txtLuongCoban;

        @BindView(R.id.txtLuongKPI)
        TextView txtLuongKPI;

        @BindView(R.id.txtCongThucTe)
        TextView txtCongThucTe;

        @BindView(R.id.txtLuongThucTe)
        TextView txtLuongThucTe;

        @BindView(R.id.txtCongTangCa)
        TextView txtCongTangCa;

        @BindView(R.id.txtLuongTangCa)
        TextView txtLuongTangCa;

        @BindView(R.id.txtNghiPhepNam)
        TextView txtNghiPhepNam;

        @BindView(R.id.txtLuongPhepNam)
        TextView txtLuongPhepNam;

        @BindView(R.id.txtThuNhapNgayCong)
        TextView txtThuNhapNgayCong;

        @BindView(R.id.txtChuyenCan)
        TextView txtChuyenCan;

        @BindView(R.id.txtXangXe)
        TextView txtXangXe;

        @BindView(R.id.txtCongTacPhi)
        TextView txtCongTacPhi;

        @BindView(R.id.txtHoaHong)
        TextView txtHoaHong;

        @BindView(R.id.txtPhuCapKhac)
        TextView txtPhuCapKhac;

        @BindView(R.id.txtThuNhapTruocThue)
        TextView txtThuNhapTruocThue;

        @BindView(R.id.txtThueTNCN)
        TextView txtThueTNCN;

        @BindView(R.id.txtBHXH)
        TextView txtBHXH;

        @BindView(R.id.txtThucLanh)
        TextView txtThucLanh;

        @BindView(R.id.txtPhat)
        TextView txtPhat;

        @BindView(R.id.txtCong100)
        TextView txtCong100;

        @BindView(R.id.txtCong70)
        TextView txtCong70;

        @BindView(R.id.txtCong50)
        TextView txtCong50;

        @BindView(R.id.txtLuong100)
        TextView txtLuong100;

        @BindView(R.id.txtLuong70)
        TextView txtLuong70;

        @BindView(R.id.txtLuong50)
        TextView txtLuong50;

        @BindView(R.id.txtThamNien)
        TextView txtThamNien;

        @BindView(R.id.txtTamUng)
        TextView txtTamUng;

        @BindView(R.id.txtCongLeTet)
        TextView txtCongLeTet;

        @BindView(R.id.txtLuongLeTet)
        TextView txtLuongLeTet;

        @BindView(R.id.txtXepLoai)
        TextView txtXepLoai;

        @BindView(R.id.txtKPI)
        TextView txtKPI;

        @BindView(R.id.txtHeSoLuong)
        TextView txtHeSoLuong;

        @BindView(R.id.txtLuongKPI2)
        TextView txtLuongKPI2;

        @BindView(R.id.txtThucNhan)
        TextView txtThucNhan;

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
            for (BangLuong_NhanVien bangLuong_nhanVien : temp) {
                if (String.valueOf(bangLuong_nhanVien.getHoTen()).toLowerCase(Locale.getDefault()).contains(charText) ||
                        String.valueOf(bangLuong_nhanVien.getMaNV()).toLowerCase(Locale.getDefault()).contains(charText)) {
                    data.add(bangLuong_nhanVien);
                }
            }
        }
        notifyDataSetChanged();
    }


}