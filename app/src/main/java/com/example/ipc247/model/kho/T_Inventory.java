package com.example.ipc247.model.kho;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class T_Inventory {
    @SerializedName("maPhieu")
    @Expose
    private String maPhieu;

    @SerializedName("tenNCC")
    @Expose
    private String tenNCC;

    @SerializedName("productCode")
    @Expose
    private String productCode;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("dvt")
    @Expose
    private String dvt;

    @SerializedName("soLuong")
    @Expose
    private double soLuong;

    @SerializedName("donGia")
    @Expose
    private double donGia;

    @SerializedName("thanhTien")
    @Expose
    private double thanhTien;

    @SerializedName("orderName")
    @Expose
    private String orderName;

    @SerializedName("ngayXuatText")
    @Expose
    private String ngayXuatText;

    @SerializedName("thanhTien_TyGia")
    @Expose
    private double thanhTien_TyGia;

    @SerializedName("loaiTien")
    @Expose
    private String loaiTien;

    @SerializedName("tgbH_Text")
    @Expose
    private String tgbH_Text;

    @SerializedName("ngayHetHanText")
    @Expose
    private String ngayHetHanText;


    public String getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(String maPhieu) {
        this.maPhieu = maPhieu;
    }

    public String getTenNCC() {
        return tenNCC;
    }

    public void setTenNCC(String tenNCC) {
        this.tenNCC = tenNCC;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDvt() {
        return dvt;
    }

    public void setDvt(String dvt) {
        this.dvt = dvt;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public double getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(double soLuong) {
        this.soLuong = soLuong;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getNgayXuatText() {
        return ngayXuatText;
    }

    public void setNgayXuatText(String ngayXuatText) {
        this.ngayXuatText = ngayXuatText;
    }

    public double getThanhTien_TyGia() {
        return thanhTien_TyGia;
    }

    public void setThanhTien_TyGia(double thanhTien_TyGia) {
        this.thanhTien_TyGia = thanhTien_TyGia;
    }

    public String getLoaiTien() {
        return loaiTien;
    }

    public void setLoaiTien(String loaiTien) {
        this.loaiTien = loaiTien;
    }


    public String getTgbH_Text() {
        return tgbH_Text;
    }

    public void setTgbH_Text(String tgbH_Text) {
        this.tgbH_Text = tgbH_Text;
    }

    public String getNgayHetHanText() {
        return ngayHetHanText;
    }

    public void setNgayHetHanText(String ngayHetHanText) {
        ngayHetHanText = ngayHetHanText;
    }
}
