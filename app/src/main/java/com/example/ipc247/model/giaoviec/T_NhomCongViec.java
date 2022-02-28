package com.example.ipc247.model.giaoviec;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class T_NhomCongViec {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("nhomCongViec")
    @Expose
    private String nhomCongViec;

    public String getNhomCongViec() {
        return nhomCongViec;
    }

    public void setNhomCongViec(String nhomCongViec) {
        this.nhomCongViec = nhomCongViec;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
