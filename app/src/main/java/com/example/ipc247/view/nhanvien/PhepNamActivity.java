package com.example.ipc247.view.nhanvien;

import android.app.AlertDialog;
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

import com.example.ipc247.R;
import com.example.ipc247.adapter.nhansu.Adapter_NhanVien;
import com.example.ipc247.adapter.nhansu.Adapter_PhepNam;
import com.example.ipc247.api.ApiNhanVien;
import com.example.ipc247.api.ApiPhepNam;
import com.example.ipc247.api.ApiTienLuong;
import com.example.ipc247.model.nhanvien.CustomResult;
import com.example.ipc247.model.nhanvien.ResultPhepNam;
import com.example.ipc247.model.nhanvien.T_NhanVien;
import com.example.ipc247.model.nhanvien.T_PhepNam;
import com.example.ipc247.model.tienluong.ResultKyLuong;
import com.example.ipc247.model.tienluong.T_KyLuong;
import com.example.ipc247.system.ClickListener;
import com.example.ipc247.system.IPC247;
import com.example.ipc247.system.RecyclerTouchListener;
import com.example.ipc247.system.TM_Toast;
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

public class PhepNamActivity extends AppCompatActivity {

    ArrayList<T_PhepNam> lstPhepNam;
    ArrayList<T_KyLuong> lstKyLuong;
    Adapter_PhepNam adapter;

    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private Unbinder unbinder;
    Context mContext;

    int intThang, intNam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phep_nam);
        mContext = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Phép Năm");
        unbinder = ButterKnife.bind(this);

        Calendar calendar = Calendar.getInstance();
        intThang = calendar.get(Calendar.MONTH) + 1;
        intNam = calendar.get(Calendar.YEAR);
        GetPhepNam();
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
                GetPhepNam();
            }
        });
    }

    public void GetKyLuong() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "GET_DATA");
        Call<ResultKyLuong> call = ApiTienLuong.apiTienLuong.GetKyLuong(jsonObject);
        call.enqueue(new Callback<ResultKyLuong>() {
            @Override
            public void onResponse(Call<ResultKyLuong> call, Response<ResultKyLuong> response) {
                ResultKyLuong result = response.body();
                if (result.getStatusCode() == 200) {
                    List<T_KyLuong> lstKyLuongTemps = result.getDtKyLuong();
                    if (lstKyLuongTemps.size() > 0) {
                        lstKyLuong = new ArrayList<T_KyLuong>();
                        lstKyLuong.addAll(lstKyLuongTemps);

                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setTitle("Chọn kỳ");
                        builder.setCancelable(false);
                        String[] arrayKhachHang = new String[lstKyLuongTemps.size()];
                        int i = 0;

                        for (T_KyLuong kyLuong : lstKyLuong) {
                            arrayKhachHang[i] = kyLuong.getKyLuongText();
                            i++;
                        }
                        builder.setSingleChoiceItems(arrayKhachHang, -1, (dialogInterface, i1) -> {
                            if (lstKyLuongTemps.size() > 0) {
                                T_KyLuong kyLuong = lstKyLuongTemps.get(i1);
                                intThang = kyLuong.getThang();
                                intNam = kyLuong.getNam();

                                GetPhepNam();
                                dialogInterface.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }

                } else {
                    TM_Toast.makeText(mContext, "Không tìm thấy dữ liệu.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
                }
            }

            @Override
            public void onFailure(Call<ResultKyLuong> call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
            }
        });
    }

    private void GetPhepNam() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "GET_DATA");
        jsonObject.addProperty("nam", intNam);
        jsonObject.addProperty("thang", intThang);
        jsonObject.addProperty("userName", IPC247.tendangnhap);


        Call<ResultPhepNam> call = ApiPhepNam.apiPhepNam.GetPhepNam(jsonObject);
        call.enqueue(new Callback<ResultPhepNam>() {
            @Override
            public void onResponse(Call<ResultPhepNam> call, Response<ResultPhepNam> response) {
                ResultPhepNam result = response.body();
                if (result.getStatusCode() == 200) {
                    List<T_PhepNam> lstPhepNamTemp = result.getDtPhepNam();
                    if (lstPhepNamTemp.size() > 0) {
                        lstPhepNam = new ArrayList<T_PhepNam>();
                        lstPhepNam.addAll(lstPhepNamTemp);
                        adapter = new Adapter_PhepNam(mContext, lstPhepNam);
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
            public void onFailure(Call<ResultPhepNam> call, Throwable t) {
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
            case R.id.btnTimKiemTheoNgay:
                GetKyLuong();
                break;
        }
        return super.onOptionsItemSelected(item);
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