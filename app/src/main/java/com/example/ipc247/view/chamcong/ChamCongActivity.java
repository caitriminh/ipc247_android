package com.example.ipc247.view.chamcong;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

import com.example.ipc247.R;
import com.example.ipc247.adapter.Adapter_ChamCong;
import com.example.ipc247.api.ApiChamCong;
import com.example.ipc247.model.chamcong.ResultChamCong;
import com.example.ipc247.model.chamcong.T_NhatKyChamCong;
import com.example.ipc247.system.IPC247;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.gson.JsonObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
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

public class ChamCongActivity extends AppCompatActivity {

    ArrayList<T_NhatKyChamCong> lstNhatKyChamCong;
    Adapter_ChamCong adapter;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private Unbinder unbinder;
    Context mContext;

    String TuNgay, DenNgay;

    @BindView(R.id.txtTongCong)
    TextView txtTongCong;

    @BindView(R.id.txtTongTangCa)
    TextView txtTongTangCa;

    @BindView(R.id.txtTongCong100)
    TextView txtTongCong100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cham_cong);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        unbinder = ButterKnife.bind(this);
        mContext = this;
        this.setTitle("Bảng Công Nhân Viên");

        Date currentTime = Calendar.getInstance().getTime();
        DateFormat formatter1 = new SimpleDateFormat("yyyy-MM-01");
        DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
        TuNgay = formatter1.format(currentTime);
        DenNgay = formatter2.format(currentTime);

        GetChamCongNhanVien();
        //Làm mới dữ liệu
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetChamCongNhanVien();
            }
        });
    }

    private void GetChamCongNhanVien() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "GET_CHAMCONG");
        jsonObject.addProperty("fromDate", TuNgay);
        jsonObject.addProperty("toDate", DenNgay);
        jsonObject.addProperty("maNV", IPC247.strMaNV);
        jsonObject.addProperty("userName", IPC247.tendangnhap);
        jsonObject.addProperty("android", 1);

        Call<ResultChamCong> call = ApiChamCong.apiChamCong.GetChamCongNhanVien(jsonObject);
        call.enqueue(new Callback<ResultChamCong>() {
            @Override
            public void onResponse(Call<ResultChamCong> call, Response<ResultChamCong> response) {
                ResultChamCong result = response.body();
                if (result.getStatusCode() == 200) {
                    List<T_NhatKyChamCong> lstChamCong = result.getDtChamCong();
                    if (lstChamCong.size() > 0) {
                        lstNhatKyChamCong = new ArrayList<T_NhatKyChamCong>();
                        lstNhatKyChamCong.addAll(lstChamCong);
                        adapter = new Adapter_ChamCong(mContext, lstNhatKyChamCong);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                        recycleView.setLayoutManager(layoutManager);
                        recycleView.setAdapter(adapter);

                        double dbltongCong = 0;
                        double dblTongTangCa = 0;
                        double dblTongCong100 = 0;
                        int i = 0;
                        for (i = 0; i < lstChamCong.size(); i++) {
                            dblTongCong100 += lstChamCong.get(i).getCong100();
                            dbltongCong += lstChamCong.get(i).getTongCong();
                            dblTongTangCa += lstChamCong.get(i).getCong150() + lstChamCong.get(i).getCong200() + lstChamCong.get(i).getCong300();
                        }
                        DecimalFormat format = new DecimalFormat("#,##0.00");
                        txtTongCong100.setText(format.format(dblTongCong100));
                        txtTongCong.setText(format.format(dbltongCong));
                        if (dblTongTangCa > 0) {
                            txtTongTangCa.setText(format.format(dblTongTangCa));
                        } else {
                            txtTongTangCa.setText("");
                        }
                        //Làm mới dữ liệu
                        swipeRefreshLayout.setRefreshing(false);
                    }

                } else {
                    Toast.makeText(mContext, "Không tìm thấy dữ liệu.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultChamCong> call, Throwable t) {
                Toast.makeText(mContext, "Call API fail.", Toast.LENGTH_SHORT).show();
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

                GetChamCongNhanVien();
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
                TimKiemTheoNgay();
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