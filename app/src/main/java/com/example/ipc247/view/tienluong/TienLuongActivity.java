package com.example.ipc247.view.tienluong;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ipc247.R;
import com.example.ipc247.adapter.Adapter_CongTac;
import com.example.ipc247.adapter.Adapter_TienLuong;
import com.example.ipc247.adapter.Adapter_TonKho;
import com.example.ipc247.api.ApiMasterData;
import com.example.ipc247.api.ApiNhanVien;
import com.example.ipc247.api.ApiTienLuong;
import com.example.ipc247.model.kho.T_TonKho;
import com.example.ipc247.model.masterdata.ResultMasterData;
import com.example.ipc247.model.masterdata.T_MasterData;
import com.example.ipc247.model.nhanvien.CustomResult;
import com.example.ipc247.model.nhanvien.T_NhanVien;
import com.example.ipc247.model.nhanvien.T_NhanVienNghiPhep;
import com.example.ipc247.model.tienluong.BangLuong_NhanVien;
import com.example.ipc247.model.tienluong.ResultKyLuong;
import com.example.ipc247.model.tienluong.ResultTienLuong;
import com.example.ipc247.model.tienluong.T_KyLuong;
import com.example.ipc247.system.IPC247;
import com.example.ipc247.system.TM_Toast;
import com.google.gson.JsonObject;

import java.text.DecimalFormat;
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
        intThang = calendar.get(Calendar.MONTH)+1;
        intNam = calendar.get(Calendar.YEAR);
        setTitle("Bảng Lương (" + intThang + '/' + intNam + ')');
        GetBangLuongNhanVien();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Làm mới dữ liệu
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetBangLuongNhanVien();
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
                                    GetBangLuongNhanVien();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_date, menu);
        return true;
    }

    private void GetBangLuongNhanVien() {
        setTitle("Bảng Lương " +
                "(" + intThang + '/' + intNam + ')');
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "GET_TINHLUONG");
        jsonObject.addProperty("thang", intThang);
        jsonObject.addProperty("nam", intNam);
        jsonObject.addProperty("maNV", IPC247.strMaNV);
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