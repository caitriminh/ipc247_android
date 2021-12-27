package com.example.ipc247.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class T_User {
    @SerializedName("userName")
    @Expose
    private String userName;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("maNV")
    @Expose
    private String maNV;

    @SerializedName("result")
    @Expose
    private Integer result;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("roleGroup")
    @Expose
    private String roleGroup;

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRoleGroup() {
        return roleGroup;
    }

    public void setRoleGroup(String roleGroup) {
        this.roleGroup = roleGroup;
    }
}
