package com.example.quyetthang.model.bsc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class T_KPI_VitriCongViec {
    @SerializedName("idPhongBan")
    @Expose
    private int idPhongBan;

    @SerializedName("phongBan")
    @Expose
    private String phongBan;

    @SerializedName("idViTriCongViec")
    @Expose
    private int idViTriCongViec;

    @SerializedName("viTriCongViec")
    @Expose
    private String viTriCongViec;

    @SerializedName("loaiChiTieu")
    @Expose
    private String loaiChiTieu;

    @SerializedName("stt")
    @Expose
    private int stt;

    @SerializedName("chiTieuCuThe")
    @Expose
    private String chiTieuCuThe;

    @SerializedName("dvt")
    @Expose
    private String dvt;

    @SerializedName("mT_Thang")
    @Expose
    private double mT_Thang;

    @SerializedName("tamQuanTrong")
    @Expose
    private double tamQuanTrong;

    @SerializedName("bac")
    @Expose
    private int bac;

    public double getTamQuanTrong() {
        return tamQuanTrong;
    }

    public void setTamQuanTrong(double tamQuanTrong) {
        this.tamQuanTrong = tamQuanTrong;
    }

    public double getmT_Thang() {
        return mT_Thang;
    }

    public void setmT_Thang(double mT_Thang) {
        this.mT_Thang = mT_Thang;
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

    public String getDvt() {
        return dvt;
    }

    public void setDvt(String dvt) {
        this.dvt = dvt;
    }

    public String getLoaiChiTieu() {
        return loaiChiTieu;
    }

    public void setLoaiChiTieu(String loaiChiTieu) {
        this.loaiChiTieu = loaiChiTieu;
    }

    public int getBac() {
        return bac;
    }

    public void setBac(int bac) {
        this.bac = bac;
    }

    public String getViTriCongViec() {
        return viTriCongViec;
    }

    public void setViTriCongViec(String viTriCongViec) {
        this.viTriCongViec = viTriCongViec;
    }

    public String getPhongBan() {
        return phongBan;
    }

    public void setPhongBan(String phongBan) {
        this.phongBan = phongBan;
    }

    public int getIdPhongBan() {
        return idPhongBan;
    }

    public void setIdPhongBan(int idPhongBan) {
        this.idPhongBan = idPhongBan;
    }

    public int getIdViTriCongViec() {
        return idViTriCongViec;
    }

    public void setIdViTriCongViec(int idViTriCongViec) {
        this.idViTriCongViec = idViTriCongViec;
    }
}
