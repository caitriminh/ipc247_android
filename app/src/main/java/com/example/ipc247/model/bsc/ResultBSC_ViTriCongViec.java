package com.example.ipc247.model.bsc;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultBSC_ViTriCongViec {

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("access_token")
    @Expose
    private Object accessToken;

    @SerializedName("result")
    @Expose
    private List<T_KPI_VitriCongViec> dtBSC_ViTriCongViec = null;

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

    public List<T_KPI_VitriCongViec> getDtBSC_ViTriCongViec() {
        return dtBSC_ViTriCongViec;
    }

    public void setDtBSC_ViTriCongViec(List<T_KPI_VitriCongViec> dtBSC_ViTriCongViec) {
        this.dtBSC_ViTriCongViec = dtBSC_ViTriCongViec;
    }
}
