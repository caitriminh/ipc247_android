package com.example.quyetthang.view.tienluong;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.quyetthang.R;
import com.example.quyetthang.adapter.nhansu.Adapter_TienLuong;
import com.example.quyetthang.api.ApiPhanQuyen;
import com.example.quyetthang.api.ApiTienLuong;
import com.example.quyetthang.model.login.ResultPhanQuyen;
import com.example.quyetthang.model.login.T_PhanQuyen;
import com.example.quyetthang.model.tienluong.BangLuong_NhanVien;
import com.example.quyetthang.model.tienluong.ResultKyLuong;
import com.example.quyetthang.model.tienluong.ResultTienLuong;
import com.example.quyetthang.model.tienluong.T_KyLuong;
import com.example.quyetthang.system.ClickListener;
import com.example.quyetthang.system.IPC247;
import com.example.quyetthang.system.RecyclerTouchListener;
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

public class TienLuongActivity extends AppCompatActivity {

    private Unbinder unbinder;

    ArrayList<BangLuong_NhanVien> lstBangLuong;
    Adapter_TienLuong adapter;

    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    Context mContext;
    ArrayList<T_KyLuong> lstKyLuong;
    Integer intThang, intNam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tien_luong);
        unbinder = ButterKnife.bind(this);
        mContext = this;

        Calendar calendar = Calendar.getInstance();
        intThang = calendar.get(Calendar.MONTH) + 1;
        intNam = calendar.get(Calendar.YEAR);
        setTitle("Bảng Lương (" + intThang + '/' + intNam + ')');

        GetBangLuongNhanVien(IPC247.strMaNV);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recycleView.addOnItemTouchListener(new RecyclerTouchListener(mContext, recycleView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                GetChucNang();
            }
        }));

        //Làm mới dữ liệu
        swipeRefreshLayout.setOnRefreshListener(() -> GetPhanQuyenBangLuong());


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

    private void GetPhanQuyenBangLuong() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "GET_BY_ID");
        jsonObject.addProperty("idNhomQuyen", IPC247.IdNhomQuyen);
        jsonObject.addProperty("mamenu", "NS018");
        Call<ResultPhanQuyen> call = ApiPhanQuyen.apiPhanQuyen.GetPhanQuyen(jsonObject);
        call.enqueue(new Callback<ResultPhanQuyen>() {
            @Override
            public void onResponse(Call<ResultPhanQuyen> call, Response<ResultPhanQuyen> response) {
                ResultPhanQuyen result = response.body();
                if (result == null) {
                    TM_Toast.makeText(mContext, "Không tìm thấy dữ liệu.", TM_Toast.LENGTH_SHORT, TM_Toast.WARNING, false).show();
                    return;
                }
                if (result.getStatusCode() == 200) {
                    List<T_PhanQuyen> lstPhanQuyen = result.getDtPhanQuyen();
                    if (lstPhanQuyen.size() == 0) {
                        TM_Toast.makeText(mContext, "Không được phép xem bảng lương.", TM_Toast.LENGTH_SHORT, TM_Toast.WARNING, false).show();
                        return;
                    }
                    String chophep = lstPhanQuyen.get(0).getXacnhan();
                    if (chophep.equals("OK")) {
                        GetBangLuongNhanVien("");
                    } else {
                        GetBangLuongNhanVien(IPC247.strMaNV);
                    }

                } else {
                    TM_Toast.makeText(mContext, "Không tìm thấy dữ liệu.", TM_Toast.LENGTH_SHORT, TM_Toast.WARNING, false).show();
                }
            }

            @Override
            public void onFailure(Call<ResultPhanQuyen> call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
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
                        builder.setTitle("Chọn kỳ lương");
                        builder.setCancelable(false);
                        String[] arrayKhachHang = new String[lstKyLuongTemps.size()];
                        int i = 0;

                        for (T_KyLuong kyLuong : lstKyLuong) {
                            arrayKhachHang[i] = kyLuong.getKyLuongText();
                            i++;
                        }
                        ;
                        builder.setSingleChoiceItems(arrayKhachHang, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (lstKyLuongTemps.size() > 0) {
                                    T_KyLuong kyLuong = lstKyLuongTemps.get(i);
                                    intThang = kyLuong.getThang();
                                    intNam = kyLuong.getNam();
                                    GetPhanQuyenBangLuong();
                                    dialogInterface.dismiss();
                                }
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

    public void GetChucNang() {
        final String[] chucnangs = {"Chỉ mình tôi", "Tất cả", "Đóng"};
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Chức năng");
        builder.setCancelable(false);
        int i = 0;

        builder.setItems(chucnangs, (dialogInterface, i1) -> {
            String chucnang = chucnangs[i1];
            dialogInterface.dismiss(); // Close Dialog
            if (chucnang.equals("Chỉ mình tôi")) {
                GetBangLuongNhanVien(IPC247.strMaNV);
            } else if (chucnang.equals("Tất cả")) {
                GetPhanQuyenBangLuong();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
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

    private void GetBangLuongNhanVien(String strMaNV) {
        setTitle("Bảng Lương " +
                "(" + intThang + '/' + intNam + ')');
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "GET_TINHLUONG");
        jsonObject.addProperty("thang", intThang);
        jsonObject.addProperty("nam", intNam);
        jsonObject.addProperty("maNV", strMaNV);
        Call<ResultTienLuong> call = ApiTienLuong.apiTienLuong.GetBangLuong_NhanVien(jsonObject);
        call.enqueue(new Callback<ResultTienLuong>() {
            @Override
            public void onResponse(Call<ResultTienLuong> call, Response<ResultTienLuong> response) {
                ResultTienLuong result = response.body();
                if (result == null) {
                    Toast.makeText(getApplicationContext(), "Không tìm thấy dữ liệu.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (result.getStatusCode() == 200) {
                    List<BangLuong_NhanVien> lstBangLuongTemp = result.getDtBangLuong_NhanVien();

                    lstBangLuong = new ArrayList<BangLuong_NhanVien>();
                    lstBangLuong.addAll(lstBangLuongTemp);
                    adapter = new Adapter_TienLuong(mContext, lstBangLuong);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                    recycleView.setLayoutManager(layoutManager);
                    recycleView.setAdapter(adapter);
                    swipeRefreshLayout.setRefreshing(false);


                } else {
                    Toast.makeText(getApplicationContext(), "Không tìm thấy dữ liệu.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultTienLuong> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Call API fail.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}