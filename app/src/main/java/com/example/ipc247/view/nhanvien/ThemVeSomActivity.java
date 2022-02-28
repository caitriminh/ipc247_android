package com.example.ipc247.view.nhanvien;

import static com.example.ipc247.view.nhanvien.NhanVienActivity.MaNVTemp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.ipc247.R;
import com.example.ipc247.api.ApiMasterData;
import com.example.ipc247.api.ApiNghiPhep;
import com.example.ipc247.api.ApiNhanVien;
import com.example.ipc247.model.masterdata.ResultMasterData;
import com.example.ipc247.model.masterdata.T_MasterData;
import com.example.ipc247.model.nhanvien.CustomResult;
import com.example.ipc247.model.nhanvien.ResultNghiPhep;
import com.example.ipc247.model.nhanvien.T_NhanVien;
import com.example.ipc247.model.nhanvien.T_NhanVienNghiPhep;
import com.example.ipc247.system.IPC247;
import com.example.ipc247.system.TM_Toast;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemVeSomActivity extends AppCompatActivity {

    ArrayList<T_MasterData> lstLyDo;

    TextInputEditText txtHoTen, txtPhongBan, txtNgayDangKy, txtGioVe, txtGhiChu;
    AutoCompleteTextView txtLyDo;
    Button btnDangKy;

    String strNgayDangKy, strGio;
    Integer mMinute = 0, mHour = 0, intLoaiNghiPhep;

    ArrayAdapter<String> adapterLyDo;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_ve_som);
        mContext = this;
        //Home
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Đăng Ký Về Sớm");

        txtLyDo = findViewById(R.id.txtLyDo);
        txtHoTen = findViewById(R.id.txtHoTen);
        txtPhongBan = findViewById(R.id.txtPhongBan);
        txtNgayDangKy = findViewById(R.id.txtNgayDangKy);
        txtGioVe = findViewById(R.id.txtGioVe);
        txtGhiChu = findViewById(R.id.txtGhiChu);
        btnDangKy = findViewById(R.id.btnDangKy);

        GetNhanVien();
        GetLyDo();

        //Ngày đăng ký mặc định
        Date currentTime = Calendar.getInstance().getTime();
        DateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
        strNgayDangKy = formatter2.format(currentTime);
        txtNgayDangKy.setText(formatter1.format(currentTime));

        txtGioVe.setOnClickListener(view -> {
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, (view1, hourOfDay, minute) -> {
                String gio, phut;
                gio = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
                phut = minute < 10 ? "0" + minute : "" + minute;
                txtGioVe.setText(gio + ":" + phut);
                strGio = hourOfDay + ":" + minute;

            }, mHour, mMinute, false);
            timePickerDialog.show();

        });

        btnDangKy.setOnClickListener(v -> Update());
    }

    public void GetLyDo() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "GET_GROUP");
        jsonObject.addProperty("group", "LOAINGHIPHEP");
        Call<ResultMasterData> call = ApiMasterData.apiMasterData.GetMasterData(jsonObject);
        call.enqueue(new Callback<ResultMasterData>() {
            @Override
            public void onResponse(Call<ResultMasterData> call, Response<ResultMasterData> response) {
                ResultMasterData result = response.body();
                if (result.getStatusCode() == 200) {
                    List<T_MasterData> lstLyDos = result.getDtMasterData();
                    if (lstLyDos.size() > 0) {
                        lstLyDo = new ArrayList<T_MasterData>();
                        lstLyDo.addAll(lstLyDos);

                        String[] arrayLyDo = new String[lstLyDo.size()];
                        int i = 0;
                        for (T_MasterData lydo : lstLyDo) {
                            arrayLyDo[i] = String.valueOf(lydo.getValue());
                            i++;
                        }

                        adapterLyDo = new ArrayAdapter<String>(mContext, R.layout.dropdown_item, arrayLyDo);
                        txtLyDo.setAdapter(adapterLyDo);

                        txtLyDo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                T_MasterData lydo = lstLyDo.get((int) id);
                                intLoaiNghiPhep = lydo.getId();
                                //Toast.makeText(mContext, lydo.getValue(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                } else {
                    TM_Toast.makeText(mContext, "Không tìm thấy dữ liệu.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
                }
            }

            @Override
            public void onFailure(Call<ResultMasterData> call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
            }
        });
    }

    private void GetNhanVien() {
        txtHoTen = findViewById(R.id.txtHoTen);
        txtPhongBan = findViewById(R.id.txtPhongBan);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "GET_BY_NV");
        jsonObject.addProperty("MaNV", IPC247.strMaNV);
        Call<CustomResult> call = ApiNhanVien.apiNhanVien.getNhanVien(jsonObject);
        call.enqueue(new Callback<CustomResult>() {
            @Override
            public void onResponse(Call<CustomResult> call, Response<CustomResult> response) {
                CustomResult result = response.body();
                if (result == null) {
                    Toast.makeText(mContext, "Không tìm thấy dữ liệu.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (result.getStatusCode() == 200) {
                    List<T_NhanVien> lstNhanVien = result.getDtNhanVien();
                    txtHoTen.setText(lstNhanVien.get(0).getHoTen());
                    txtPhongBan.setText(lstNhanVien.get(0).getPhongBan() + " / " + lstNhanVien.get(0).getChucVu());

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

    private void Update() {
        if (txtLyDo.getText().toString().equals("")) {
            TM_Toast.makeText(mContext, "Bạn vui lòng nhập vào lý do về sớm.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
            return;
        }

        if (txtGhiChu.getText().toString().equals("")) {
            TM_Toast.makeText(mContext, "Bạn vui lòng nhập vào nội dung xin về sớm.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
            return;
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "UPDATE");
        jsonObject.addProperty("id", 0);
        jsonObject.addProperty("maNV", IPC247.strMaNV);
        jsonObject.addProperty("ngayDangKy", strNgayDangKy);
        jsonObject.addProperty("tuNgay", strNgayDangKy + " " + strGio);
        jsonObject.addProperty("denNgay", "");
        jsonObject.addProperty("idLoaiNghiPhep", intLoaiNghiPhep);
        jsonObject.addProperty("ghiChu", txtGhiChu.getText().toString());
        jsonObject.addProperty("userName", IPC247.tendangnhap);
        Call<ResultNghiPhep> call = ApiNghiPhep.apiNghiPhep.UpdateVeSom(jsonObject);
        call.enqueue(new Callback<ResultNghiPhep>() {
            @Override
            public void onResponse(Call<ResultNghiPhep> call, Response<ResultNghiPhep> response) {
                ResultNghiPhep result = response.body();
                if (result == null) {
                    TM_Toast.makeText(mContext, "Call API fail.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
                    return;
                }
                if (result.getStatusCode() == 200) {
                    List<T_NhanVienNghiPhep> lstNghiPhep = result.getDtNghiPhep();
                    if (lstNghiPhep.size() > 0) {
                        TM_Toast.makeText(mContext, lstNghiPhep.get(0).getMessage(), TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                        //Đòng và quay trở lại
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("name", "vesom");
                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();
                    }

                } else {
                    TM_Toast.makeText(mContext, "Không tìm thấy dữ liệu.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
                }
            }

            @Override
            public void onFailure(Call<ResultNghiPhep> call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
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
}