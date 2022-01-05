package com.example.ipc247.model.tienluong;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BangLuong_NhanVien {
    @SerializedName("tinhTrang")
    @Expose
    private String tinhTrang;

    @SerializedName("maNV")
    @Expose
    private String maNV;

    @SerializedName("hoTen")
    @Expose
    private String hoTen;

    @SerializedName("phongBan")
    @Expose
    private String phongBan;

    @SerializedName("chucVu")
    @Expose
    private String chucVu;

    @SerializedName("bacLuong")
    @Expose
    private Integer bacLuong;

    @SerializedName("ngayCongThucTe")
    @Expose
    private double ngayCongThucTe;

    @SerializedName("congTangCa")
    @Expose
    private double congTangCa;

    @SerializedName("mucLuong")
    @Expose
    private double mucLuong;

    @SerializedName("nghiPhepNam")
    @Expose
    private double nghiPhepNam;

    @SerializedName("luongCoBan")
    @Expose
    private double luongCoBan;

    @SerializedName("luongKPI")
    @Expose
    private double luongKPI;

    @SerializedName("luongThucTe")
    @Expose
    private double luongThucTe;

    @SerializedName("luongTangCa")
    @Expose
    private double luongTangCa;

    @SerializedName("luongPhepNam")
    @Expose
    private double luongPhepNam;

    @SerializedName("thuNhapThucTe")
    @Expose
    private double thuNhapThucTe;

    @SerializedName("luongPhuCap")
    @Expose
    private double luongPhuCap;

    @SerializedName("tienHoaHong")
    @Expose
    private double tienHoaHong;

    @SerializedName("thuNhapTruocThue")
    @Expose
    private double thuNhapTruocThue;

    @SerializedName("congTacPhi")
    @Expose
    private double congTacPhi;

    @SerializedName("xangXe_DienThoai")
    @Expose
    private double xangXe_DienThoai;

    @SerializedName("luongToiThieuVung")
    @Expose
    private double luongToiThieuVung;

    @SerializedName("bhxh")
    @Expose
    private double bhxh;

    @SerializedName("thucLanh")
    @Expose
    private double thucLanh;

    @SerializedName("thuNhapKhongChiuThue")
    @Expose
    private double thuNhapKhongChiuThue;

    @SerializedName("thuNhapChiuThue")
    @Expose
    private double thuNhapChiuThue;

    @SerializedName("thueTNCN")
    @Expose
    private double thueTNCN;

    @SerializedName("chuyenCan")
    @Expose
    private double chuyenCan;

    @SerializedName("phat")
    @Expose
    private double phat;

    @SerializedName("ngayCongChuan")
    @Expose
    private double ngayCongChuan;

    @SerializedName("luong100")
    @Expose
    private double luong100;

    @SerializedName("luong70")
    @Expose
    private double luong70;

    @SerializedName("luong50")
    @Expose
    private double luong50;

    @SerializedName("congHeSo1")
    @Expose
    private double congHeSo1;

    @SerializedName("congHeSo2")
    @Expose
    private double congHeSo2;

    @SerializedName("congHeSo3")
    @Expose
    private double congHeSo3;

    @SerializedName("thamNien")
    @Expose
    private double thamNien;

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public double getBhxh() {
        return bhxh;
    }

    public void setBhxh(double bhxh) {
        this.bhxh = bhxh;
    }

    public double getChuyenCan() {
        return chuyenCan;
    }

    public void setChuyenCan(double chuyenCan) {
        this.chuyenCan = chuyenCan;
    }

    public double getCongTacPhi() {
        return congTacPhi;
    }

    public void setCongTacPhi(double congTacPhi) {
        this.congTacPhi = congTacPhi;
    }

    public double getCongTangCa() {
        return congTangCa;
    }

    public void setCongTangCa(double congTangCa) {
        this.congTangCa = congTangCa;
    }

    public double getLuongCoBan() {
        return luongCoBan;
    }

    public void setLuongCoBan(double luongCoBan) {
        this.luongCoBan = luongCoBan;
    }

    public double getLuongKPI() {
        return luongKPI;
    }

    public void setLuongKPI(double luongKPI) {
        this.luongKPI = luongKPI;
    }

    public Integer getBacLuong() {
        return bacLuong;
    }

    public void setBacLuong(Integer bacLuong) {
        this.bacLuong = bacLuong;
    }

    public double getLuongPhepNam() {
        return luongPhepNam;
    }

    public void setLuongPhepNam(double luongPhepNam) {
        this.luongPhepNam = luongPhepNam;
    }

    public double getLuongPhuCap() {
        return luongPhuCap;
    }

    public void setLuongPhuCap(double luongPhuCap) {
        this.luongPhuCap = luongPhuCap;
    }

    public double getLuongTangCa() {
        return luongTangCa;
    }

    public void setLuongTangCa(double luongTangCa) {
        this.luongTangCa = luongTangCa;
    }

    public double getLuongThucTe() {
        return luongThucTe;
    }

    public void setLuongThucTe(double luongThucTe) {
        this.luongThucTe = luongThucTe;
    }

    public double getLuongToiThieuVung() {
        return luongToiThieuVung;
    }

    public void setLuongToiThieuVung(double luongToiThieuVung) {
        this.luongToiThieuVung = luongToiThieuVung;
    }

    public double getMucLuong() {
        return mucLuong;
    }

    public void setMucLuong(double mucLuong) {
        this.mucLuong = mucLuong;
    }

    public double getNgayCongThucTe() {
        return ngayCongThucTe;
    }

    public void setNgayCongThucTe(double ngayCongThucTe) {
        this.ngayCongThucTe = ngayCongThucTe;
    }

    public double getNghiPhepNam() {
        return nghiPhepNam;
    }

    public void setNghiPhepNam(double nghiPhepNam) {
        this.nghiPhepNam = nghiPhepNam;
    }

    public double getThucLanh() {
        return thucLanh;
    }

    public void setThucLanh(double thucLanh) {
        this.thucLanh = thucLanh;
    }

    public double getThueTNCN() {
        return thueTNCN;
    }

    public void setThueTNCN(double thueTNCN) {
        this.thueTNCN = thueTNCN;
    }

    public double getThuNhapChiuThue() {
        return thuNhapChiuThue;
    }

    public void setThuNhapChiuThue(double thuNhapChiuThue) {
        this.thuNhapChiuThue = thuNhapChiuThue;
    }

    public double getThuNhapKhongChiuThue() {
        return thuNhapKhongChiuThue;
    }

    public void setThuNhapKhongChiuThue(double thuNhapKhongChiuThue) {
        this.thuNhapKhongChiuThue = thuNhapKhongChiuThue;
    }

    public double getThuNhapThucTe() {
        return thuNhapThucTe;
    }

    public void setThuNhapThucTe(double thuNhapThucTe) {
        this.thuNhapThucTe = thuNhapThucTe;
    }

    public double getThuNhapTruocThue() {
        return thuNhapTruocThue;
    }

    public void setThuNhapTruocThue(double thuNhapTruocThue) {
        this.thuNhapTruocThue = thuNhapTruocThue;
    }


    public double getXangXe_DienThoai() {
        return xangXe_DienThoai;
    }

    public void setXangXe_DienThoai(double xangXe_DienThoai) {
        this.xangXe_DienThoai = xangXe_DienThoai;
    }

    public String getPhongBan() {
        return phongBan;
    }

    public void setPhongBan(String phongBan) {
        this.phongBan = phongBan;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public double getPhat() {
        return phat;
    }

    public void setPhat(double phat) {
        this.phat = phat;
    }

    public double getNgayCongChuan() {
        return ngayCongChuan;
    }

    public void setNgayCongChuan(double ngayCongChuan) {
        this.ngayCongChuan = ngayCongChuan;
    }

    public double getLuong100() {
        return luong100;
    }

    public void setLuong100(double luong100) {
        this.luong100 = luong100;
    }

    public double getLuong70() {
        return luong70;
    }

    public void setLuong70(double luong70) {
        this.luong70 = luong70;
    }

    public double getLuong50() {
        return luong50;
    }

    public void setLuong50(double luong50) {
        this.luong50 = luong50;
    }

    public double getCongHeSo1() {
        return congHeSo1;
    }

    public void setCongHeSo1(double congHeSo1) {
        this.congHeSo1 = congHeSo1;
    }

    public double getCongHeSo2() {
        return congHeSo2;
    }

    public void setCongHeSo2(double congHeSo2) {
        this.congHeSo2 = congHeSo2;
    }

    public double getCongHeSo3() {
        return congHeSo3;
    }

    public void setCongHeSo3(double congHeSo3) {
        this.congHeSo3 = congHeSo3;
    }

    public double getTienHoaHong() {
        return tienHoaHong;
    }

    public void setTienHoaHong(double tienHoaHong) {
        this.tienHoaHong = tienHoaHong;
    }

    public double getThamNien() {
        return thamNien;
    }

    public void setThamNien(double thamNien) {
        this.thamNien = thamNien;
    }
}
