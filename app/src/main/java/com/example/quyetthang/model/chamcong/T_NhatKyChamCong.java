package com.example.quyetthang.model.chamcong;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class T_NhatKyChamCong {
    @SerializedName("maNV")
    @Expose
    private String maNV;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("ngayChamCong")
    @Expose
    private String ngayChamCong;

    @SerializedName("ngayChamCongText")
    @Expose
    private String ngayChamCongText;

    @SerializedName("vao1")
    @Expose
    private String vao1;

    @SerializedName("ra1")
    @Expose
    private String ra1;

    @SerializedName("tongCong")
    @Expose
    private Double tongCong;

    @SerializedName("cong100")
    @Expose
    private Double cong100;

    @SerializedName("cong150")
    @Expose
    private Double cong150;

    @SerializedName("cong200")
    @Expose
    private Double cong200;

    @SerializedName("cong300")
    @Expose
    private Double cong300;

    @SerializedName("tinhTrang")
    @Expose
    private String tinhTrang;

    @SerializedName("result")
    @Expose
    private int result;



    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNgayChamCong() {
        return ngayChamCong;
    }

    public void setNgayChamCong(String ngayChamCong) {
        this.ngayChamCong = ngayChamCong;
    }

    public String getVao1() {
        return vao1;
    }

    public void setVao1(String vao1) {
        this.vao1 = vao1;
    }


    public String getRa1() {
        return ra1;
    }

    public void setRa1(String ra1) {
        this.ra1 = ra1;
    }

    public String getNgayChamCongText() {
        return ngayChamCongText;
    }

    public void setNgayChamCongText(String ngayChamCongText) {
        this.ngayChamCongText = ngayChamCongText;
    }

    public Double getTongCong() {
        return tongCong;
    }

    public void setTongCong(Double tongCong) {
        this.tongCong = tongCong;
    }

    public Double getCong100() {
        return cong100;
    }

    public void setCong100(Double cong100) {
        this.cong100 = cong100;
    }

    public Double getCong150() {
        return cong150;
    }

    public void setCong150(Double cong150) {
        this.cong150 = cong150;
    }

    public Double getCong200() {
        return cong200;
    }

    public void setCong200(Double cong200) {
        this.cong200 = cong200;
    }

    public Double getCong300() {
        return cong300;
    }

    public void setCong300(Double cong300) {
        this.cong300 = cong300;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
