package com.example.ipc247.model.bsc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class T_KPI_PhongBan {
    @SerializedName("idChiTieu")
    @Expose
    private int idChiTieu;

    @SerializedName("chiTieuCuThe")
    @Expose
    private String chiTieuCuThe;

    @SerializedName("loaiChiTieu")
    @Expose
    private String loaiChiTieu;

    @SerializedName("nhomChiTieu")
    @Expose
    private String nhomChiTieu;

    @SerializedName("stt")
    @Expose
    private int stt;

    @SerializedName("mT_Thang")
    @Expose
    private double mT_Thang;

    @SerializedName("tamQuanTrong")
    @Expose
    private double tamQuanTrong;

    @SerializedName("dvt")
    @Expose
    private String dvt;

    public int getIdChiTieu() {
        return idChiTieu;
    }

    public void setIdChiTieu(int idChiTieu) {
        this.idChiTieu = idChiTieu;
    }

    public String getLoaiChiTieu() {
        return loaiChiTieu;
    }

    public void setLoaiChiTieu(String loaiChiTieu) {
        this.loaiChiTieu = loaiChiTieu;
    }

    public String getDvt() {
        return dvt;
    }

    public void setDvt(String dvt) {
        this.dvt = dvt;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }

    public String getChiTieuCuThe() {
        return chiTieuCuThe;
    }

    public void setChiTieuCuThe(String chiTieuCuThe) {
        this.chiTieuCuThe = chiTieuCuThe;
    }

    public String getNhomChiTieu() {
        return nhomChiTieu;
    }

    public void setNhomChiTieu(String nhomChiTieu) {
        this.nhomChiTieu = nhomChiTieu;
    }

    public double getmT_Thang() {
        return mT_Thang;
    }

    public void setmT_Thang(double mT_Thang) {
        this.mT_Thang = mT_Thang;
    }

    public double getTamQuanTrong() {
        return tamQuanTrong;
    }

    public void setTamQuanTrong(double tamQuanTrong) {
        this.tamQuanTrong = tamQuanTrong;
    }
}
