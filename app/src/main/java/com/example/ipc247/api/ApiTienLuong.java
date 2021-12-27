package com.example.ipc247.api;

import static com.example.ipc247.system.IPC247.BASE_URL_IPC;

import com.example.ipc247.model.nhanvien.CustomResult;
import com.example.ipc247.model.tienluong.ResultKyLuong;
import com.example.ipc247.model.tienluong.ResultTienLuong;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiTienLuong {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiTienLuong apiTienLuong = new Retrofit.Builder()
            .baseUrl(BASE_URL_IPC)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiTienLuong.class);

    @Headers("Content-Type: application/json")
    @POST("TienLuong/GetBangLuong_NhanVien")
    Call<ResultTienLuong> GetBangLuong_NhanVien(@Body JsonObject body);

    @Headers("Content-Type: application/json")
    @POST("TienLuong/GetKyLuong")
    Call<ResultKyLuong> GetKyLuong(@Body JsonObject body);
}
