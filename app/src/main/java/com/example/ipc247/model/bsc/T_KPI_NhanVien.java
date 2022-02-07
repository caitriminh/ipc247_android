package com.example.ipc247.model.bsc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class T_KPI_NhanVien {
    @SerializedName("idChiTieu")
    @Expose
    private int idChiTieu;

    @SerializedName("chiTieuCuThe")
    @Expose
    private String chiTieuCuThe;

    @SerializedName("loaiChiTieu")
    @Expose
    private String loaiChiTieu;

    @SerializedName("stt")
    @Expose
    private int stt;

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

    @SerializedName("viTriCongViec")
    @Expose
    private String viTriCongViec;

    @SerializedName("dvt")
    @Expose
    private String dvt;

    @SerializedName("mT_Thang")
    @Expose
    private double mT_Thang;

    @SerializedName("tamQuanTrong")
    @Expose
    private double tamQuanTrong;

    @SerializedName("thucHien")
    @Expose
    private double thucHien;

    @SerializedName("tyle")
    @Expose
    private double tyle;

    @SerializedName("bac")
    @Expose
    private int bac;

    public int getIdChiTieu() {
        return idChiTieu;
    }

    public void setIdChiTieu(int idChiTieu) {
        this.idChiTieu = idChiTieu;
    }

    public String getChiTieuCuThe() {
        return chiTieuCuThe;
    }

    public void setChiTieuCuThe(String chiTieuCuThe) {
        this.chiTieuCuThe = chiTieuCuThe;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getDvt() {
        return dvt;
    }

    public void setDvt(String dvt) {
        this.dvt = dvt;
    }

    public double getmT_Thang() {
        return mT_Thang;
    }

    public void setmT_Thang(double mT_Thang) {
        this.mT_Thang = mT_Thang;
    }

    public String getLoaiChiTieu() {
        return loaiChiTieu;
    }

    public void setLoaiChiTieu(String loaiChiTieu) {
        this.loaiChiTieu = loaiChiTieu;
    }

    public double getTamQuanTrong() {
        return tamQuanTrong;
    }

    public void setTamQuanTrong(double tamQuanTrong) {
        this.tamQuanTrong = tamQuanTrong;
    }

    public double getThucHien() {
        return thucHien;
    }

    public void setThucHien(double thucHien) {
        this.thucHien = thucHien;
    }

    public double getTyle() {
        return tyle;
    }

    public void setTyle(double tyle) {
        this.tyle = tyle;
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

    public String getViTriCongViec() {
        return viTriCongViec;
    }

    public void setViTriCongViec(String viTriCongViec) {
        this.viTriCongViec = viTriCongViec;
    }

    public int getBac() {
        return bac;
    }

    public void setBac(int bac) {
        this.bac = bac;
    }
}
