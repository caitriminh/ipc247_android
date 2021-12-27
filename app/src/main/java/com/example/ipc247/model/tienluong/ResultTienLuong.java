package com.example.ipc247.model.tienluong;

import com.example.ipc247.model.nhanvien.T_NhanVien;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultTienLuong {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("access_token")
    @Expose
    private Object accessToken;

    @SerializedName("result")
    @Expose
    private List<BangLuong_NhanVien> dtBangLuong_NhanVien = null;

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

    public List<BangLuong_NhanVien> getDtBangLuong_NhanVien() {
        return dtBangLuong_NhanVien;
    }

    public void setDtBangLuong_NhanVien(List<BangLuong_NhanVien> dtBangLuong_NhanVien) {
        this.dtBangLuong_NhanVien = dtBangLuong_NhanVien;
    }
}
