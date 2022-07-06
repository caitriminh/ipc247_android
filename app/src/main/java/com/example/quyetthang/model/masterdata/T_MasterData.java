package com.example.quyetthang.model.masterdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class T_MasterData {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("key")
    @Expose
    private String key;

    @SerializedName("value")
    @Expose
    private String value;

    @SerializedName("value2")
    @Expose
    private String value2;

    @SerializedName("group")
    @Expose
    private String group;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
