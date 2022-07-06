package com.example.quyetthang.view.bsc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.quyetthang.R;
import com.example.quyetthang.adapter.bsc.Adapter_BSC_ViTriCongViec;
import com.example.quyetthang.api.ApiBSC_ViTriCongViec;
import com.example.quyetthang.model.bsc.ResultBSC_ViTriCongViec;
import com.example.quyetthang.model.bsc.T_KPI_VitriCongViec;
import com.example.quyetthang.system.ClickListener;
import com.example.quyetthang.system.RecyclerTouchListener;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BSCViTriCongViecActivity extends AppCompatActivity {

    ArrayList<T_KPI_VitriCongViec> lstKPIPhongBan;
    Adapter_BSC_ViTriCongViec adapter;

    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private Unbinder unbinder;
    Context mContext;
    public static int IDViTriCongViec, Bac;
    public static String ViTriCongViec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bsc_vitri_congviec);
        mContext = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        setTitle("BSC Vị Trí Công Việc");
        unbinder = ButterKnife.bind(this);
        GetPhongBan();
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(mContext, recycleView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                IDViTriCongViec = lstKPIPhongBan.get(position).getIdViTriCongViec();
                ViTriCongViec = lstKPIPhongBan.get(position).getViTriCongViec();
                Bac = lstKPIPhongBan.get(position).getBac();

                Intent intent2 = new Intent(mContext, ChiTietBSCViTriCongViecActivity.class);
                startActivity(intent2);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        //Làm mới dữ liệu
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetPhongBan();
            }
        });
    }

    private void GetPhongBan() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "GET_POSITION");

        Call<ResultBSC_ViTriCongViec> call = ApiBSC_ViTriCongViec.apiBSC_ViTriCongViec.GetBSC_ViTriCongViec(jsonObject);
        call.enqueue(new Callback<ResultBSC_ViTriCongViec>() {
            @Override
            public void onResponse(Call<ResultBSC_ViTriCongViec> call, Response<ResultBSC_ViTriCongViec> response) {
                ResultBSC_ViTriCongViec result = response.body();
                if (result.getStatusCode() == 200) {
                    List<T_KPI_VitriCongViec> lstPhongBanTemp = result.getDtBSC_ViTriCongViec();
                    if (lstPhongBanTemp.size() > 0) {
                        lstKPIPhongBan = new ArrayList<T_KPI_VitriCongViec>();
                        lstKPIPhongBan.addAll(lstPhongBanTemp);
                        adapter = new Adapter_BSC_ViTriCongViec(mContext, lstKPIPhongBan);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                        recycleView.setLayoutManager(layoutManager);
                        recycleView.setAdapter(adapter);

                        //Làm mới dữ liệu
                        swipeRefreshLayout.setRefreshing(false);
                    }

                } else {
                    Toast.makeText(mContext, "Không tìm thấy dữ liệu.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultBSC_ViTriCongViec> call, Throwable t) {
                Toast.makeText(mContext, "Call API fail.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}