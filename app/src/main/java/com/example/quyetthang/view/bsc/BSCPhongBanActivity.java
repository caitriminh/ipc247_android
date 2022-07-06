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
import com.example.quyetthang.adapter.bsc.Adapter_BSCPhongBan;
import com.example.quyetthang.api.ApiMasterData;
import com.example.quyetthang.model.masterdata.ResultMasterData;
import com.example.quyetthang.model.masterdata.T_MasterData;
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

public class BSCPhongBanActivity extends AppCompatActivity {

    ArrayList<T_MasterData> lstPhongBan;
    Adapter_BSCPhongBan adapter;

    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private Unbinder unbinder;
    Context mContext;
    public static int IDPhongBan;
    public  static String PhongBan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bsc_phongban);
        mContext = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        setTitle("BSC Phòng Ban");
        unbinder = ButterKnife.bind(this);
        GetPhongBan();
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(mContext, recycleView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                IDPhongBan= lstPhongBan.get(position).getId();
                PhongBan= lstPhongBan.get(position).getValue();
                Intent intent2 = new Intent(mContext, ChiTietBSCPhongBanActivity.class);
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
        jsonObject.addProperty("action", "GET_GROUP");
        jsonObject.addProperty("group", "PHONGBAN");
        Call<ResultMasterData> call = ApiMasterData.apiMasterData.GetMasterData(jsonObject);


        call.enqueue(new Callback<ResultMasterData>() {
            @Override
            public void onResponse(Call<ResultMasterData> call, Response<ResultMasterData> response) {
                ResultMasterData result = response.body();
                if (result.getStatusCode() == 200) {
                    List<T_MasterData> lstPhongBanTemp = result.getDtMasterData();
                    if (lstPhongBanTemp.size() > 0) {
                        lstPhongBan = new ArrayList<T_MasterData>();
                        lstPhongBan.addAll(lstPhongBanTemp);
                        adapter = new Adapter_BSCPhongBan(mContext, lstPhongBan);
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
            public void onFailure(Call<ResultMasterData> call, Throwable t) {
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