package com.example.ipc247.model.kho;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class T_KhachHang {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("taxCode")
    @Expose
    private String taxCode;

    @SerializedName("company")
    @Expose
    private String company;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
