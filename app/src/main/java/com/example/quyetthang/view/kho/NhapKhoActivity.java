package com.example.quyetthang.view.kho;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.quyetthang.R;
import com.example.quyetthang.adapter.kho.Adapter_NhapKho;
import com.example.quyetthang.api.ApiNhapKho;
import com.example.quyetthang.model.kho.ResultInventory;
import com.example.quyetthang.model.kho.T_Inventory;
import com.example.quyetthang.system.ClickListener;
import com.example.quyetthang.system.RecyclerTouchListener;
import com.example.quyetthang.system.TM_Toast;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
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

public class NhapKhoActivity extends AppCompatActivity {

    Context mContext;
    private Unbinder unbinder;

    ArrayList<T_Inventory> lstInventory;
    Adapter_NhapKho adapter;

    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    //swiperefresh
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;

    String TuNgay, DenNgay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhap_kho);
        mContext = this;
        unbinder = ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Nhập Kho");

        Date currentTime = Calendar.getInstance().getTime();
        DateFormat formatter1 = new SimpleDateFormat("yyyy-MM-01");
        DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
        TuNgay = formatter1.format(currentTime);
        DenNgay = formatter2.format(currentTime);

        GetNhapKho();
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(mContext, recycleView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        //Làm mới dữ liệu
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetNhapKho();
            }
        });
    }

    private void GetNhapKho() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "GET_NHATKY_PHIEUNHAP");
        jsonObject.addProperty("fromDate", TuNgay);
        jsonObject.addProperty("toDate", DenNgay);
        Call<ResultInventory> call = ApiNhapKho.apiNhapKho.GetPhieuNhap(jsonObject);
        call.enqueue(new Callback<ResultInventory>() {
            @Override
            public void onResponse(Call<ResultInventory> call, Response<ResultInventory> response) {
                ResultInventory result = response.body();
                if (result == null) {
                    TM_Toast.makeText(mContext, "Không tìm thấy dữ liệu.", TM_Toast.LENGTH_SHORT, TM_Toast.WARNING, false).show();
                    return;
                }
                if (result.getStatusCode() == 200) {
                    List<T_Inventory> lstInventoryTemps = result.getDtInventory();

                    lstInventory = new ArrayList<T_Inventory>();
                    lstInventory.addAll(lstInventoryTemps);
                    adapter = new Adapter_NhapKho(mContext, lstInventory);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                    recycleView.setLayoutManager(layoutManager);
                    recycleView.setAdapter(adapter);
                    swiperefresh.setRefreshing(false);

                } else {
                    TM_Toast.makeText(mContext, "Không tìm thấy dữ liệu.", TM_Toast.LENGTH_SHORT, TM_Toast.WARNING, false).show();
                }
            }

            @Override
            public void onFailure(Call<ResultInventory> call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
            }
        });
    }

    public void TimKiemTheoNgay() {
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        MaterialDatePicker picker = builder.setTheme(R.style.ThemeOverlay_MaterialComponents_MaterialCalendar).build();
        picker.show(this.getSupportFragmentManager(), picker.toString());
        picker.addOnNegativeButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picker.dismiss();
            }
        });

        picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                Object data = selection;
                Pair<Long, Long> result = (Pair<Long, Long>) selection;
                long startDate = result.first;
                long endDate = result.second;
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

                Calendar calendarStart = Calendar.getInstance();
                calendarStart.setTimeInMillis(startDate);

                Calendar calendarEnd = Calendar.getInstance();
                calendarEnd.setTimeInMillis(endDate);

                TuNgay = formatter.format(calendarStart.getTime());
                DenNgay = formatter.format(calendarEnd.getTime());
                GetNhapKho();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.btnThem:
                Intent intent2 = new Intent(mContext, XuatKhoTempActivity.class);
                startActivity(intent2);
                break;
            case R.id.btnTimKiemTheoNgay:
                TimKiemTheoNgay();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_item, menu);
        getMenuInflater().inflate(R.menu.menu_item_date, menu);
//        getMenuInflater().inflate(R.menu.menu_item_add, menu);
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