package com.example.quyetthang.api;

import static com.example.quyetthang.system.IPC247.BASE_URL_IPC;

import com.example.quyetthang.model.nhanvien.ResultNghiPhep;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiNghiPhep {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiNghiPhep apiNghiPhep = new Retrofit.Builder()
            .baseUrl(BASE_URL_IPC)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiNghiPhep.class);

    @Headers("Content-Type: application/json")
    @POST("NhanSu/GetNhanVienNghiPhep")
    Call<ResultNghiPhep> NghiPhep(@Body JsonObject body);

    @Headers("Content-Type: application/json")
    @POST("NhanSu/UpdateNhanVienNghiPhep")
    Call<ResultNghiPhep> UpdateNghiPhep(@Body JsonObject body);

    @Headers("Content-Type: application/json")
    @POST("NhanSu/UpdateNhanVienVeSom")
    Call<ResultNghiPhep> UpdateVeSom(@Body JsonObject body);

    @Headers("Content-Type: application/json")
    @POST("NhanSu/UpdateNhanVienCongTac")
    Call<ResultNghiPhep> UpdateCongTac(@Body JsonObject body);

    @Headers("Content-Type: application/json")
    @POST("NhanSu/XacNhan_Duyet_NghiPhep")
    Call<ResultNghiPhep> XacNhan_Duyet(@Body JsonObject body);


}
