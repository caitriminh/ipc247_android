package com.example.ipc247.model.nhanvien;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class T_PhepNam {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("nam")
    @Expose
    private Integer nam;

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

    @SerializedName("tongNgayPhep")
    @Expose
    private double tongNgayPhep;

    @SerializedName("phepBoSung")
    @Expose
    private double phepBoSung;

    @SerializedName("soNgayNghi")
    @Expose
    private double soNgayNghi;

    @SerializedName("phepNamConLai")
    @Expose
    private double phepNamConLai;

    public String getPhongBan() {
        return phongBan;
    }

    public void setPhongBan(String phongBan) {
        this.phongBan = phongBan;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public double getPhepBoSung() {
        return phepBoSung;
    }

    public void setPhepBoSung(double phepBoSung) {
        this.phepBoSung = phepBoSung;
    }

    public double getPhepNamConLai() {
        return phepNamConLai;
    }

    public void setPhepNamConLai(double phepNamConLai) {
        this.phepNamConLai = phepNamConLai;
    }

    public double getSoNgayNghi() {
        return soNgayNghi;
    }

    public void setSoNgayNghi(double soNgayNghi) {
        this.soNgayNghi = soNgayNghi;
    }

    public double getTongNgayPhep() {
        return tongNgayPhep;
    }

    public void setTongNgayPhep(double tongNgayPhep) {
        this.tongNgayPhep = tongNgayPhep;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNam() {
        return nam;
    }

    public void setNam(Integer nam) {
        this.nam = nam;
    }
}
