package com.example.quyetthang.model.kho;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class T_PhieuXuat_QRCode {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("idProduct")
    @Expose
    private int idProduct;

    @SerializedName("productCode")
    @Expose
    private String productCode;

    @SerializedName("productName")
    @Expose
    private String productName;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("dvt")
    @Expose
    private String dvt;

    @SerializedName("shortName")
    @Expose
    private String shortName;

    @SerializedName("kho")
    @Expose
    private String kho;

    @SerializedName("donGiaVND")
    @Expose
    private double donGiaVND;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public String getKho() {
        return kho;
    }

    public void setKho(String kho) {
        this.kho = kho;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public double getDonGiaVND() {
        return donGiaVND;
    }

    public void setDonGiaVND(double donGiaVND) {
        this.donGiaVND = donGiaVND;
    }
}
