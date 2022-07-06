package com.example.quyetthang.model.masterdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class T_LoaiTangCa {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("loaiTangCa")
    @Expose
    private String loaiTangCa;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoaiTangCa() {
        return loaiTangCa;
    }

    public void setLoaiTangCa(String loaiTangCa) {
        this.loaiTangCa = loaiTangCa;
    }
}
