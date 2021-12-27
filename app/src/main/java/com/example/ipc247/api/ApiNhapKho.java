package com.example.ipc247.api;

import static com.example.ipc247.system.IPC247.BASE_URL_IPC;

import com.example.ipc247.model.kho.ResultInventory;
import com.example.ipc247.model.kho.ResultKhachHang;
import com.example.ipc247.model.kho.ResultPhieuXuatQRCode;
import com.example.ipc247.model.kho.ResultSaleOrder;
import com.example.ipc247.model.kho.ResultXuatTemp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiNhapKho {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiNhapKho apiNhapKho = new Retrofit.Builder()
            .baseUrl(BASE_URL_IPC)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiNhapKho.class);

    @Headers("Content-Type: application/json")
    @POST("Kho/GetInventory")
    Call<ResultInventory> GetPhieuNhap(@Body JsonObject body);
}
