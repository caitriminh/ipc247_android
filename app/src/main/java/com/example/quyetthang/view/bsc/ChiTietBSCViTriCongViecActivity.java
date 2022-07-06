package com.example.quyetthang.view.bsc;

import static com.example.quyetthang.view.bsc.BSCViTriCongViecActivity.Bac;
import static com.example.quyetthang.view.bsc.BSCViTriCongViecActivity.IDViTriCongViec;
import static com.example.quyetthang.view.bsc.BSCViTriCongViecActivity.ViTriCongViec;

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
import com.example.quyetthang.adapter.bsc.Adapter_ChiTiet_BSC_ViTriCongViec;
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

public class ChiTietBSCViTriCongViecActivity extends AppCompatActivity {

    ArrayList<T_KPI_VitriCongViec> lstKPIViTriCongViec;
    Adapter_ChiTiet_BSC_ViTriCongViec adapter;

    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private Unbinder unbinder;
    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bsc_vitri_congviec);
        mContext = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("BSC (" + ViTriCongViec + ")");
        unbinder = ButterKnife.bind(this);

        GetKPIViTriCongViec();
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
                GetKPIViTriCongViec();
            }
        });
    }

    private void GetKPIViTriCongViec() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "GET_DATA");
        jsonObject.addProperty("idViTriCongViec", IDViTriCongViec);
        jsonObject.addProperty("bac", Bac);

        Call<ResultBSC_ViTriCongViec> call = ApiBSC_ViTriCongViec.apiBSC_ViTriCongViec.GetBSC_ViTriCongViec(jsonObject);
        call.enqueue(new Callback<ResultBSC_ViTriCongViec>() {
            @Override
            public void onResponse(Call<ResultBSC_ViTriCongViec> call, Response<ResultBSC_ViTriCongViec> response) {
                ResultBSC_ViTriCongViec result = response.body();
                if (result.getStatusCode() == 200) {
                    List<T_KPI_VitriCongViec> lstKPIViTriCongViecTemp = result.getDtBSC_ViTriCongViec();
                    if (lstKPIViTriCongViecTemp.size() > 0) {
                        lstKPIViTriCongViec = new ArrayList<T_KPI_VitriCongViec>();
                        lstKPIViTriCongViec.addAll(lstKPIViTriCongViecTemp);
                        adapter = new Adapter_ChiTiet_BSC_ViTriCongViec(mContext, lstKPIViTriCongViec);
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