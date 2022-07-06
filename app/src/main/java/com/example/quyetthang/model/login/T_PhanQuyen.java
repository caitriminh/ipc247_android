package com.example.quyetthang.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class T_PhanQuyen {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("mamenu")
    @Expose
    private String mamenu;

    @SerializedName("idNhomQuyen")
    @Expose
    private Integer idNhomQuyen;


    @SerializedName("xacnhan")
    @Expose
    private String xacnhan;

    @SerializedName("chophep")
    @Expose
    private String chophep;


    public Integer getIdNhomQuyen() {
        return idNhomQuyen;
    }

    public void setIdNhomQuyen(Integer idNhomQuyen) {
        this.idNhomQuyen = idNhomQuyen;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMamenu() {
        return mamenu;
    }

    public void setMamenu(String mamenu) {
        this.mamenu = mamenu;
    }

    public String getChophep() {
        return chophep;
    }

    public void setChophep(String chophep) {
        this.chophep = chophep;
    }

    public String getXacnhan() {
        return xacnhan;
    }

    public void setXacnhan(String xacnhan) {
        this.xacnhan = xacnhan;
    }
}
