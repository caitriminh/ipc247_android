package com.example.ipc247.api;

import static com.example.ipc247.system.IPC247.BASE_URL_IPC;

import com.example.ipc247.model.kho.ResultInventory;
import com.example.ipc247.model.kho.ResultKhachHang;
import com.example.ipc247.model.kho.ResultPhieuXuatQRCode;
import com.example.ipc247.model.kho.ResultSaleOrder;
import com.example.ipc247.model.kho.ResultXuatTemp;
import com.example.ipc247.model.nhanvien.CustomResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiXuatKho {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiXuatKho apiXuatKho = new Retrofit.Builder()
            .baseUrl(BASE_URL_IPC)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiXuatKho.class);

    @Headers("Content-Type: application/json")
    @POST("Kho/GetInventoryQRCode")
    Call<ResultPhieuXuatQRCode> getInfoPhieuXuat(@Body JsonObject body);

    @Headers("Content-Type: application/json")
    @POST("KhachHang/GetKhachHang")
    Call<ResultKhachHang> getKhachHang(@Body JsonObject body);

    @Headers("Content-Type: application/json")
    @POST("DonHang/GetSalesOrder")
    Call<ResultSaleOrder> getOrder(@Body JsonObject body);

    @Headers("Content-Type: application/json")
    @POST("Kho/UpdateXuatTemp")
    Call<ResultXuatTemp> XuatTemp(@Body JsonObject body);

    @Headers("Content-Type: application/json")
    @POST("Kho/GetXuatTemp")
    Call<ResultXuatTemp> GetXuatTemp(@Body JsonObject body);

    @Headers("Content-Type: application/json")
    @POST("Kho/XuatKhoQRCode")
    Call<ResultXuatTemp> XuatKhoQRCode(@Body JsonObject body);

    @Headers("Content-Type: application/json")
    @POST("Kho/GetInventory")
    Call<ResultInventory> GetPhieuXuat(@Body JsonObject body);
}
