package com.example.quyetthang.model.bsc;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultBSC_NhanVien {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("access_token")
    @Expose
    private Object accessToken;

    @SerializedName("result")
    @Expose
    private List<T_KPI_NhanVien> dtBSC_NhanVien = null;

    @SerializedName("optional")
    @Expose
    private Integer optional;

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setAccessToken(Object accessToken) {
        this.accessToken = accessToken;
    }

    public Integer getOptional() {
        return optional;
    }

    public void setOptional(Integer optional) {
        this.optional = optional;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public List<T_KPI_NhanVien> getDtBSC_NhanVien() {
        return dtBSC_NhanVien;
    }

    public void setDtBSC_NhanVien(List<T_KPI_NhanVien> dtBSC_NhanVien) {
        this.dtBSC_NhanVien = dtBSC_NhanVien;
    }
}
