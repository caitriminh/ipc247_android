package com.example.quyetthang.model.masterdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultLoaiTangCa {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("access_token")
    @Expose
    private Object accessToken;

    @SerializedName("result")
    @Expose
    private List<T_LoaiTangCa> dtLoaiTangCa = null;

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

    public List<T_LoaiTangCa> getDtLoaiTangCa() {
        return dtLoaiTangCa;
    }

    public void setDtLoaiTangCa(List<T_LoaiTangCa> dtLoaiTangCa) {
        this.dtLoaiTangCa = dtLoaiTangCa;
    }
}
