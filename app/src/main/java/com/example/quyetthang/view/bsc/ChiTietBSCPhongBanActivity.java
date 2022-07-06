package com.example.quyetthang.view.bsc;

import static com.example.quyetthang.view.bsc.BSCPhongBanActivity.IDPhongBan;
import static com.example.quyetthang.view.bsc.BSCPhongBanActivity.PhongBan;

import android.app.SearchManager;
import android.content.Context;
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
import com.example.quyetthang.adapter.bsc.Adapter_ChiTiet_BSCPhongBan;
import com.example.quyetthang.api.ApiBSC_PhongBan;
import com.example.quyetthang.model.bsc.ResultBSC_PhongBan;
import com.example.quyetthang.model.bsc.T_KPI_PhongBan;
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

public class ChiTietBSCPhongBanActivity extends AppCompatActivity {

    ArrayList<T_KPI_PhongBan> lstKPIPhongBan;
    Adapter_ChiTiet_BSCPhongBan adapter;

    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private Unbinder unbinder;
    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bsc_phongban);
        mContext = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Chi Tiết BSC (" + PhongBan + ")");
        unbinder = ButterKnife.bind(this);

        GetKPIPhongBan();
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
                GetKPIPhongBan();
            }
        });
    }

    private void GetKPIPhongBan() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "GET_DATA");
        jsonObject.addProperty("idPhongBan", IDPhongBan);

        Call<ResultBSC_PhongBan> call = ApiBSC_PhongBan.apiBSC_PhongBan.GetBSC_PhongBan(jsonObject);
        call.enqueue(new Callback<ResultBSC_PhongBan>() {
            @Override
            public void onResponse(Call<ResultBSC_PhongBan> call, Response<ResultBSC_PhongBan> response) {
                ResultBSC_PhongBan result = response.body();
                if (result.getStatusCode() == 200) {
                    List<T_KPI_PhongBan> lstKPIPhongBanTemp = result.getDtBSC_PhongBan();
                    if (lstKPIPhongBanTemp.size() > 0) {
                        lstKPIPhongBan = new ArrayList<T_KPI_PhongBan>();
                        lstKPIPhongBan.addAll(lstKPIPhongBanTemp);
                        adapter = new Adapter_ChiTiet_BSCPhongBan(mContext, lstKPIPhongBan);
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
            public void onFailure(Call<ResultBSC_PhongBan> call, Throwable t) {
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