package com.example.ipc247.api;

import static com.example.ipc247.system.IPC247.BASE_URL_IPC;

import com.example.ipc247.model.chamcong.ResultChamCong;
import com.example.ipc247.model.masterdata.ResultLoaiTangCa;
import com.example.ipc247.model.masterdata.ResultMasterData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiMasterData {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiMasterData apiMasterData = new Retrofit.Builder()
            .baseUrl(BASE_URL_IPC)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiMasterData.class);

    @Headers("Content-Type: application/json")
    @POST("DanhMuc/GetMasterData")
    Call<ResultMasterData> GetMasterData(@Body JsonObject body);

    @Headers("Content-Type: application/json")
    @POST("DanhMuc/GetLoaiTangCa")
    Call<ResultLoaiTangCa> GetLoaiTangCa(@Body JsonObject body);


}
