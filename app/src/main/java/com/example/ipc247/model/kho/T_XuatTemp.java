package com.example.ipc247.model.kho;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class T_XuatTemp {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("idqrCode")
    @Expose
    private int idqrCode;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("productCode")
    @Expose
    private String productCode;

    @SerializedName("productName")
    @Expose
    private String productName;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("shortName")
    @Expose
    private String shortName;

    @SerializedName("price")
    @Expose
    private Double price;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getIdqrCode() {
        return idqrCode;
    }

    public void setIdqrCode(int idqrCode) {
        this.idqrCode = idqrCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
