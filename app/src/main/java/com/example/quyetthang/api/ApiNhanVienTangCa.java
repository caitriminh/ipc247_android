package com.example.quyetthang.api;

import static com.example.quyetthang.system.IPC247.BASE_URL_IPC;

import com.example.quyetthang.model.nhanvien.ResultNhanVienTangCa;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiNhanVienTangCa {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiNhanVienTangCa apiNhanVienTangCa = new Retrofit.Builder()
            .baseUrl(BASE_URL_IPC)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiNhanVienTangCa.class);

    @Headers("Content-Type: application/json")
    @POST("NhanSu/GetNhanVienTangCa")
    Call<ResultNhanVienTangCa> getNhanVienTangCa(@Body JsonObject body);


    @Headers("Content-Type: application/json")
    @POST("NhanSu/UpdateNhanVienTangCa")
    Call<ResultNhanVienTangCa> Delete(@Body JsonObject body);

    @Headers("Content-Type: application/json")
    @POST("NhanSu/UpdateNhanVienTangCa")
    Call<ResultNhanVienTangCa> Update(@Body JsonObject body);

    @Headers("Content-Type: application/json")
    @POST("NhanSu/XacNhan_Duyet_TangCa")
    Call<ResultNhanVienTangCa> XacNhan_Duyet(@Body JsonObject body);
}
