package com.example.ipc247.model.nhanvien;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class T_NhanVien {
    @SerializedName("gioiTinh")
    @Expose
    private String gioiTinh;

    @SerializedName("ngaySinhText")
    @Expose
    private String ngaySinhText;

    @SerializedName("maNV")
    @Expose
    private String maNV;

    @SerializedName("hoTen")
    @Expose
    private String hoTen;

    @SerializedName("noiSinh")
    @Expose
    private String noiSinh;

    @SerializedName("phongBan")
    @Expose
    private String phongBan;

    @SerializedName("chucVu")
    @Expose
    private String chucVu;

    @SerializedName("diaChi")
    @Expose
    private String diaChi;

    @SerializedName("checkIn")
    @Expose
    private String checkIn;

    @SerializedName("checkOut")
    @Expose
    private String checkOut;

    @SerializedName("tongCong")
    @Expose
    private double tongCong;

    @SerializedName("phepNam")
    @Expose
    private double phepNam;

    @SerializedName("tongNgayNghi")
    @Expose
    private double tongNgayNghi;

    @SerializedName("iP_ChamCong")
    @Expose
    private String iP_ChamCong;

    @SerializedName("chamCongIP")
    @Expose
    private Boolean chamCongIP;

    @SerializedName("ngayVaoLamText")
    @Expose
    private String ngayVaoLamText;

    @SerializedName("hinh")
    @Expose
    private String hinh;

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgaySinhText() {
        return ngaySinhText;
    }

    public void setNgaySinhText(String ngaySinhText) {
        this.ngaySinhText = ngaySinhText;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getPhongBan() {
        return phongBan;
    }

    public void setPhongBan(String phongBan) {
        this.phongBan = phongBan;
    }

    public String getNoiSinh() {
        return noiSinh;
    }

    public void setNoiSinh(String noiSinh) {
        this.noiSinh = noiSinh;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public double getTongCong() {
        return tongCong;
    }

    public void setTongCong(double tongCong) {
        this.tongCong = tongCong;
    }

    public double getPhepNam() {
        return phepNam;
    }

    public void setPhepNam(double phepNam) {
        this.phepNam = phepNam;
    }

    public double getTongNgayNghi() {
        return tongNgayNghi;
    }

    public void setTongNgayNghi(double tongNgayNghi) {
        this.tongNgayNghi = tongNgayNghi;
    }

    public String getiP_ChamCong() {
        return iP_ChamCong;
    }

    public void setiP_ChamCong(String iP_ChamCong) {
        this.iP_ChamCong = iP_ChamCong;
    }

    public Boolean getChamCongIP() {
        return chamCongIP;
    }

    public void setChamCongIP(Boolean chamCongIP) {
        this.chamCongIP = chamCongIP;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getNgayVaoLamText() {
        return ngayVaoLamText;
    }

    public void setNgayVaoLamText(String ngayVaoLamText) {
        this.ngayVaoLamText = ngayVaoLamText;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }
}
