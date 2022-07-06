package com.example.quyetthang.model.tienluong;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class T_KyLuong {
    @SerializedName("kyLuongText")
    @Expose
    private String kyLuongText;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("thang")
    @Expose
    private Integer thang;

    @SerializedName("nam")
    @Expose
    private Integer nam;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKyLuongText() {
        return kyLuongText;
    }

    public void setKyLuongText(String kyLuongText) {
        this.kyLuongText = kyLuongText;
    }

    public Integer getNam() {
        return nam;
    }

    public void setNam(Integer nam) {
        this.nam = nam;
    }

    public Integer getThang() {
        return thang;
    }

    public void setThang(Integer thang) {
        this.thang = thang;
    }
}
