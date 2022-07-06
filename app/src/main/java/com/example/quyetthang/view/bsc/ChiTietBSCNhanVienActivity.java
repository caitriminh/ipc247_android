package com.example.quyetthang.view.bsc;

import static com.example.quyetthang.view.bsc.BSCNhanVienActivity.HoTen;
import static com.example.quyetthang.view.bsc.BSCNhanVienActivity.KPI;
import static com.example.quyetthang.view.bsc.BSCNhanVienActivity.MaNV;
import static com.example.quyetthang.view.bsc.BSCNhanVienActivity.intNam;
import static com.example.quyetthang.view.bsc.BSCNhanVienActivity.intThang;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.quyetthang.R;
import com.example.quyetthang.adapter.bsc.Adapter_ChiTiet_BSCNhanVien;
import com.example.quyetthang.api.ApiBSC_NhanVien;
import com.example.quyetthang.model.bsc.ResultBSC_NhanVien;
import com.example.quyetthang.model.bsc.T_KPI_NhanVien;
import com.example.quyetthang.system.ClickListener;
import com.example.quyetthang.system.RecyclerTouchListener;
import com.google.gson.JsonObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietBSCNhanVienActivity extends AppCompatActivity {

    ArrayList<T_KPI_NhanVien> lstKPINhanVien;
    Adapter_ChiTiet_BSCNhanVien adapter;

    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.txtKPI)
    TextView txtKPI;

    private Unbinder unbinder;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bsc_nhanvien);
        mContext = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle(HoTen);
        unbinder = ButterKnife.bind(this);

        GetKPINhanVien();
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(mContext, recycleView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        //Làm mới dữ liệu
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetKPINhanVien();
            }
        });
    }

    private void GetKPINhanVien() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "GET_BY_MANV");
        jsonObject.addProperty("maNV", MaNV);
        jsonObject.addProperty("thang", intThang);
        jsonObject.addProperty("nam", intNam);

        Call<ResultBSC_NhanVien> call = ApiBSC_NhanVien.apiBSC_NhanVien.GetBSC_NhanVien(jsonObject);
        call.enqueue(new Callback<ResultBSC_NhanVien>() {
            @Override
            public void onResponse(Call<ResultBSC_NhanVien> call, Response<ResultBSC_NhanVien> response) {
                ResultBSC_NhanVien result = response.body();
                if (result.getStatusCode() == 200) {
                    List<T_KPI_NhanVien> lstKPINhanVienTemp = result.getDtBSC_NhanVien();
                    if (lstKPINhanVienTemp.size() > 0) {
                        lstKPINhanVien = new ArrayList<T_KPI_NhanVien>();
                        lstKPINhanVien.addAll(lstKPINhanVienTemp);
                        adapter = new Adapter_ChiTiet_BSCNhanVien(mContext, lstKPINhanVien);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                        recycleView.setLayoutManager(layoutManager);
                        recycleView.setAdapter(adapter);

                        DecimalFormat format = new DecimalFormat("#,##0.00");
                        txtKPI.setText(format.format(KPI) + " %");
                        //Làm mới dữ liệu
                        swipeRefreshLayout.setRefreshing(false);
                    }

                } else {
                    Toast.makeText(mContext, "Không tìm thấy dữ liệu.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultBSC_NhanVien> call, Throwable t) {
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