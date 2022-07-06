package com.example.quyetthang.view.kho;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.quyetthang.R;
import com.example.quyetthang.adapter.kho.Adapter_TonKho;
import com.example.quyetthang.api.ApiTonKho;
import com.example.quyetthang.model.kho.ResultTonKho;
import com.example.quyetthang.model.kho.T_TonKho;
import com.example.quyetthang.system.TM_Toast;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TonKhoActivity extends AppCompatActivity {

    Context mContext;
    private Unbinder unbinder;

    ArrayList<T_TonKho> lstTonKho;
    Adapter_TonKho adapter;

    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    //swiperefresh
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tonkho);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        unbinder = ButterKnife.bind(this);
        mContext = this;
        this.setTitle("Tồn Kho");

        GetTonKho();
        //Làm mới dữ liệu
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetTonKho();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private void GetTonKho() {
        Calendar calendar = Calendar.getInstance();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "GET_DATA");
        jsonObject.addProperty("thang", calendar.get(Calendar.MONTH)+1);
        jsonObject.addProperty("nam", calendar.get(Calendar.YEAR));
        jsonObject.addProperty("idKho", 20311);
        Call<ResultTonKho> call = ApiTonKho.apiTonKho.getTonKho(jsonObject);
        call.enqueue(new Callback<ResultTonKho>() {
            @Override
            public void onResponse(Call<ResultTonKho> call, Response<ResultTonKho> response) {
                ResultTonKho result = response.body();
                if (result == null) {
                    TM_Toast.makeText(mContext, "Không tìm thấy dữ liệu.", TM_Toast.LENGTH_SHORT, TM_Toast.WARNING, false).show();
                    return;
                }
                if (result.getStatusCode() == 200) {
                    List<T_TonKho> lstTonKhoTemps = result.getDtTonKho();

                    lstTonKho = new ArrayList<T_TonKho>();
                    lstTonKho.addAll(lstTonKhoTemps);
                    adapter = new Adapter_TonKho(mContext, lstTonKho);
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
            public void onFailure(Call<ResultTonKho> call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_item, menu);
        getMenuInflater().inflate(R.menu.menu_item_date, menu);
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