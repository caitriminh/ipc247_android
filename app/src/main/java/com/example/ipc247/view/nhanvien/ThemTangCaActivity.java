package com.example.ipc247.view.nhanvien;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
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
import com.example.ipc247.api.ApiNhanVien;
import com.example.ipc247.api.ApiNhanVienTangCa;
import com.example.ipc247.model.masterdata.ResultLoaiTangCa;
import com.example.ipc247.model.masterdata.ResultMasterData;
import com.example.ipc247.model.masterdata.T_LoaiTangCa;
import com.example.ipc247.model.masterdata.T_MasterData;
import com.example.ipc247.model.nhanvien.CustomResult;
import com.example.ipc247.model.nhanvien.ResultNhanVienTangCa;
import com.example.ipc247.model.nhanvien.T_NhanVien;
import com.example.ipc247.model.nhanvien.T_NhanVienTangCa;
import com.example.ipc247.system.IPC247;
import com.example.ipc247.system.TM_Toast;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemTangCaActivity extends AppCompatActivity {

    ArrayList<T_LoaiTangCa> lstLoaiTangCa;

    TextInputEditText txtHoTen, txtPhongBan, txtNgayDangKy, txtTuGio, txtDenGio, txtGhiChu, txtNgaytangCa;
    AutoCompleteTextView txtLoaiTangCa;
    Button btnDangKy;

    String strNgayDangKy, strNgayTangCa, strTuGio, strDenGio;
    Integer Idloaitangca, mHour, mMinute;

    ArrayAdapter<String> adapterLoaiTangCa;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_tang_ca);
        mContext = this;
        //Home
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Đăng Ký Tăng Ca");

        txtLoaiTangCa = findViewById(R.id.txtLoaiTangCa);
        txtHoTen = findViewById(R.id.txtHoTen);
        txtPhongBan = findViewById(R.id.txtPhongBan);
        txtNgayDangKy = findViewById(R.id.txtNgayDangKy);
        txtTuGio = findViewById(R.id.txtTuGio);
        txtDenGio = findViewById(R.id.txtDenGio);
        txtGhiChu = findViewById(R.id.txtGhiChu);
        txtNgaytangCa = findViewById(R.id.txtNgaytangCa);
        btnDangKy = findViewById(R.id.btnDangKy);

        //Ngày đăng ký mặc định
        Date currentTime = Calendar.getInstance().getTime();
        DateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
        strNgayDangKy = formatter2.format(currentTime);
        txtNgayDangKy.setText(formatter1.format(currentTime));

        txtNgaytangCa.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,AlertDialog.THEME_DEVICE_DEFAULT_DARK,
                    (datePicker, year12, month12, day) -> {

                        calendar.set(year12, month12, day);
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        String strDate = formatter.format(calendar.getTime());

                        txtNgaytangCa.setText(strDate);
                        //Lấy giá trị gửi lên server
                        SimpleDateFormat formatter212 = new SimpleDateFormat("yyyy-MM-dd");
                        strNgayTangCa = formatter212.format(calendar.getTime());

                    }, year, month, dayOfMonth);

            datePickerDialog.show();
        });

        txtTuGio.setOnClickListener(view -> {
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(mContext,AlertDialog.THEME_DEVICE_DEFAULT_DARK, (view1, hourOfDay, minute) -> {
                String gio, phut;
                gio = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
                phut = minute < 10 ? "0" + minute : "" + minute;
                txtTuGio.setText(gio + ":" + phut);
                strTuGio = hourOfDay + ":" + minute;
            }, mHour, mMinute, false);
            timePickerDialog.show();

        });

        txtDenGio.setOnClickListener(view -> {
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, AlertDialog.THEME_DEVICE_DEFAULT_DARK, (view12, hourOfDay, minute) -> {
                String gio, phut;
                gio = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
                phut = minute < 10 ? "0" + minute : "" + minute;
                txtDenGio.setText(gio + ":" + phut);
                strDenGio = hourOfDay + ":" + minute;
            }, mHour, mMinute, false);
            timePickerDialog.show();

        });

        btnDangKy.setOnClickListener(v -> Update());

        GetNhanVien();
        GetLoaiTangCa();

    }

    public void GetLoaiTangCa() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "GET_DATA");
        Call<ResultLoaiTangCa> call = ApiMasterData.apiMasterData.GetLoaiTangCa(jsonObject);
        call.enqueue(new Callback<ResultLoaiTangCa>() {
            @Override
            public void onResponse(Call<ResultLoaiTangCa> call, Response<ResultLoaiTangCa> response) {
                ResultLoaiTangCa result = response.body();
                if (result.getStatusCode() == 200) {
                    List<T_LoaiTangCa> lstLoaiTangCaTemp = result.getDtLoaiTangCa();
                    if (lstLoaiTangCaTemp.size() > 0) {
                        lstLoaiTangCa = new ArrayList<>();
                        lstLoaiTangCa.addAll(lstLoaiTangCaTemp);

                        String[] arrayLyDo = new String[lstLoaiTangCa.size()];
                        int i = 0;
                        for (T_LoaiTangCa loaitangca : lstLoaiTangCa) {
                            arrayLyDo[i] = String.valueOf(loaitangca.getLoaiTangCa());
                            i++;
                        }

                        adapterLoaiTangCa = new ArrayAdapter<String>(mContext, R.layout.dropdown_item, arrayLyDo);
                        txtLoaiTangCa.setAdapter(adapterLoaiTangCa);

                        txtLoaiTangCa.setOnItemClickListener((parent, view, position, id) -> {
                            T_LoaiTangCa loaiTangCa = lstLoaiTangCa.get((int) id);
                            Idloaitangca = loaiTangCa.getId();
                        });
                    }

                } else {
                    TM_Toast.makeText(mContext, "Không tìm thấy dữ liệu.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
                }
            }

            @Override
            public void onFailure(Call<ResultLoaiTangCa> call, Throwable t) {
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

    public void Update() {

        if (txtLoaiTangCa.getText().toString().equals("")) {
            TM_Toast.makeText(mContext, "Bạn vui lòng nhập vào loại tăng ca.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
            return;
        }
        if (txtGhiChu.getText().toString().equals("")) {
            TM_Toast.makeText(mContext, "Bạn vui lòng nhập vào ghi chú nội dung tăng ca.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
            return;
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "UPDATE");
        jsonObject.addProperty("id", 0);
        jsonObject.addProperty("maNV", IPC247.strMaNV);
        jsonObject.addProperty("ngayTangCa", strNgayTangCa);
        jsonObject.addProperty("idLoaiTangCa", Idloaitangca);
        jsonObject.addProperty("ghiChu", txtGhiChu.getText().toString());
        jsonObject.addProperty("userName", IPC247.tendangnhap);
        jsonObject.addProperty("tuGio", strNgayTangCa + " " + strTuGio);
        jsonObject.addProperty("denGio", strNgayTangCa + " " + strDenGio);
        Call<ResultNhanVienTangCa> call = ApiNhanVienTangCa.apiNhanVienTangCa.Update(jsonObject);
        call.enqueue(new Callback<ResultNhanVienTangCa>() {
            @Override
            public void onResponse(Call<ResultNhanVienTangCa> call, Response<ResultNhanVienTangCa> response) {
                ResultNhanVienTangCa result = response.body();
                if (result == null) {
                    TM_Toast.makeText(mContext, "Call API fail.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
                    return;
                }
                if (result.getStatusCode() == 200) {
                    List<T_NhanVienTangCa> lstTangCa = result.getDtNhanVienTangCa();
                    if (lstTangCa.size() > 0) {
                        TM_Toast.makeText(mContext, lstTangCa.get(0).getMessage(), TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();

                        //Đòng và quay trở lại
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("name", "tangca");
                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();
                    }

                } else {
                    TM_Toast.makeText(mContext, "Không tìm thấy dữ liệu.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
                }
            }

            @Override
            public void onFailure(Call<ResultNhanVienTangCa> call, Throwable t) {
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