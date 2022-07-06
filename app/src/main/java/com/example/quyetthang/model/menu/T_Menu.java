package com.example.quyetthang.model.menu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class T_Menu {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("mamenu")
    @Expose
    private String mamenu;

    @SerializedName("idNhomQuyen")
    @Expose
    private int idNhomQuyen;

    @SerializedName("menu")
    @Expose
    private String menu;

    @SerializedName("idphanhe")
    @Expose
    private int idphanhe;

    @SerializedName("hinh")
    @Expose
    private String hinh;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMamenu() {
        return mamenu;
    }

    public void setMamenu(String mamenu) {
        this.mamenu = mamenu;
    }

    public int getIdNhomQuyen() {
        return idNhomQuyen;
    }

    public void setIdNhomQuyen(int idNhomQuyen) {
        this.idNhomQuyen = idNhomQuyen;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public int getIdphanhe() {
        return idphanhe;
    }

    public void setIdphanhe(int idphanhe) {
        this.idphanhe = idphanhe;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }
}
