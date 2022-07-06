package com.example.quyetthang.view.nhanvien;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.quyetthang.R;
import com.example.quyetthang.adapter.nhansu.Adapter_NhanVien;
import com.example.quyetthang.api.ApiNhanVien;
import com.example.quyetthang.model.nhanvien.CustomResult;
import com.example.quyetthang.model.nhanvien.T_NhanVien;
import com.example.quyetthang.system.ClickListener;
import com.example.quyetthang.system.IPC247;
import com.example.quyetthang.system.RecyclerTouchListener;
import com.google.gson.JsonObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NhanVienActivity extends AppCompatActivity {

    ArrayList<T_NhanVien> lstNhanVien;
    Adapter_NhanVien adapter;

    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private Unbinder unbinder;
    Context mContext;

    String TuNgay, DenNgay;
    public static String MaNVTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_vien);
        mContext = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Hồ Sơ Nhân Viên");
        unbinder = ButterKnife.bind(this);

        Date currentTime = Calendar.getInstance().getTime();
        DateFormat formatter1 = new SimpleDateFormat("yyyy-01-01");
        DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
        TuNgay = formatter1.format(currentTime);
        DenNgay = formatter2.format(currentTime);

        GetNhanVien();
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(mContext, recycleView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                MaNVTemp = lstNhanVien.get(position).getMaNV();
                Intent intent2 = new Intent(mContext, NhanVienImageActivity.class);
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
                GetNhanVien();
            }
        });
    }

    private void GetNhanVien() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "GET_DATA");
        jsonObject.addProperty("fromDate", TuNgay);
        jsonObject.addProperty("toDate", DenNgay);
        jsonObject.addProperty("timKiem", 1);
        jsonObject.addProperty("maNV", IPC247.strMaNV);

        Call<CustomResult> call = ApiNhanVien.apiNhanVien.getNhanVien(jsonObject);
        call.enqueue(new Callback<CustomResult>() {
            @Override
            public void onResponse(Call<CustomResult> call, Response<CustomResult> response) {
                CustomResult result = response.body();
                if (result.getStatusCode() == 200) {
                    List<T_NhanVien> lstNhanVienTemp = result.getDtNhanVien();
                    if (lstNhanVienTemp.size() > 0) {
                        lstNhanVien = new ArrayList<T_NhanVien>();
                        lstNhanVien.addAll(lstNhanVienTemp);
                        adapter = new Adapter_NhanVien(mContext, lstNhanVien);
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
            public void onFailure(Call<CustomResult> call, Throwable t) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_item, menu);
        SearchManager searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.menuSearch).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));
        searchView.setQueryHint("Tìm kiếm...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapter == null) {
                    Log.w("adapter null", "null");
                }
                adapter.filter(newText);
                return false;
            }
        });
        return true;
    }
}