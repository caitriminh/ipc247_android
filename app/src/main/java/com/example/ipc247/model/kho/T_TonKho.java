package com.example.ipc247.model.kho;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class T_TonKho {

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

    @SerializedName("soDuCuoiKy")
    @Expose
    private double soDuCuoiKy;

    @SerializedName("tienCuoiKy")
    @Expose
    private double tienCuoiKy;

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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDvt() {
        return dvt;
    }

    public void setDvt(String dvt) {
        this.dvt = dvt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getSoDuCuoiKy() {
        return soDuCuoiKy;
    }

    public void setSoDuCuoiKy(double soDuCuoiKy) {
        this.soDuCuoiKy = soDuCuoiKy;
    }

    public double getTienCuoiKy() {
        return tienCuoiKy;
    }

    public void setTienCuoiKy(double tienCuoiKy) {
        this.tienCuoiKy = tienCuoiKy;
    }
}
