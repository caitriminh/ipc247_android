package com.example.quyetthang.api;

import static com.example.quyetthang.system.IPC247.BASE_URL_IPC;

import com.example.quyetthang.model.giaoviec.ResultGiaoViec;
import com.example.quyetthang.model.giaoviec.ResultNhomCongViec;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiGiaoViec {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiGiaoViec apiGiaoViec = new Retrofit.Builder()
            .baseUrl(BASE_URL_IPC)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiGiaoViec.class);

    @Headers("Content-Type: application/json")
    @POST("TroGiup/GetGiaoViec")
    Call<ResultGiaoViec> GetGiaoViec(@Body JsonObject body);

    @Headers("Content-Type: application/json")
    @POST("TroGiup/UpdateGiaoViec")
    Call<ResultGiaoViec> HoanThanh(@Body JsonObject body);

    @Headers("Content-Type: application/json")
    @POST("TroGiup/UpdateGiaoViec")
    Call<ResultGiaoViec> Update(@Body JsonObject body);

    @Headers("Content-Type: application/json")
    @POST("TroGiup/GetNhomCongViec")
    Call<ResultNhomCongViec> GetNhomCongViec(@Body JsonObject body);
}
