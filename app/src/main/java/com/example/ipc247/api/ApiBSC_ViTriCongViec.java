package com.example.ipc247.api;

import static com.example.ipc247.system.IPC247.BASE_URL_IPC;

import com.example.ipc247.model.bsc.ResultBSC_PhongBan;
import com.example.ipc247.model.bsc.ResultBSC_ViTriCongViec;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiBSC_ViTriCongViec {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiBSC_ViTriCongViec apiBSC_ViTriCongViec = new Retrofit.Builder()
            .baseUrl(BASE_URL_IPC)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiBSC_ViTriCongViec.class);

    @Headers("Content-Type: application/json")
    @POST("KPI/GetKPI_ViTriCongViec")
    Call<ResultBSC_ViTriCongViec> GetBSC_ViTriCongViec(@Body JsonObject body);


}
