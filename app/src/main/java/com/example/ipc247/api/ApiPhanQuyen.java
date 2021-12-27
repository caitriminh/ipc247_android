package com.example.ipc247.api;

import static com.example.ipc247.system.IPC247.BASE_URL_IPC;

import com.example.ipc247.model.login.ResultPhanQuyen;
import com.example.ipc247.model.login.ResultUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiPhanQuyen {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiPhanQuyen apiPhanQuyen = new Retrofit.Builder()
            .baseUrl(BASE_URL_IPC)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiPhanQuyen.class);

    @Headers("Content-Type: application/json")
    @POST("HeThong/GetChucNangNhomQuyen")
    Call<ResultPhanQuyen> GetPhanQuyen(@Body JsonObject body);
}
