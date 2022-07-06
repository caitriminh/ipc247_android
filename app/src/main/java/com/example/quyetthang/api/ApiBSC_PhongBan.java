package com.example.quyetthang.api;

import static com.example.quyetthang.system.IPC247.BASE_URL_IPC;

import com.example.quyetthang.model.bsc.ResultBSC_PhongBan;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiBSC_PhongBan {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiBSC_PhongBan apiBSC_PhongBan = new Retrofit.Builder()
            .baseUrl(BASE_URL_IPC)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiBSC_PhongBan.class);

    @Headers("Content-Type: application/json")
    @POST("KPI/GetKPI_PhongBan")
    Call<ResultBSC_PhongBan> GetBSC_PhongBan(@Body JsonObject body);


}
