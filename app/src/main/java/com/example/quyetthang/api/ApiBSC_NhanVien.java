package com.example.quyetthang.api;

import static com.example.quyetthang.system.IPC247.BASE_URL_IPC;

import com.example.quyetthang.model.bsc.ResultBSC_NhanVien;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiBSC_NhanVien {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiBSC_NhanVien apiBSC_NhanVien = new Retrofit.Builder()
            .baseUrl(BASE_URL_IPC)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiBSC_NhanVien.class);

    @Headers("Content-Type: application/json")
    @POST("KPI/GetKPI_NhanVien")
    Call<ResultBSC_NhanVien> GetBSC_NhanVien(@Body JsonObject body);

    @Headers("Content-Type: application/json")
    @POST("KPI/GetKPI_NhanVien_KetQua")
    Call<ResultBSC_NhanVien> GetBSC_NhanVien_KetQua(@Body JsonObject body);
}
