package com.example.ipc247.view.giaoviec;

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
import com.example.ipc247.api.ApiGiaoViec;
import com.example.ipc247.api.ApiMasterData;
import com.example.ipc247.api.ApiNhanVien;
import com.example.ipc247.model.giaoviec.ResultGiaoViec;
import com.example.ipc247.model.giaoviec.ResultNhomCongViec;
import com.example.ipc247.model.giaoviec.T_GiaoViec;
import com.example.ipc247.model.giaoviec.T_NhomCongViec;
import com.example.ipc247.model.masterdata.ResultMasterData;
import com.example.ipc247.model.masterdata.T_MasterData;
import com.example.ipc247.model.nhanvien.CustomResult;
import com.example.ipc247.model.nhanvien.T_NhanVien;
import com.example.ipc247.system.IPC247;
import com.example.ipc247.system.TM_Toast;
import com.example.ipc247.view.kho.BarcodeScanActivity;
import com.example.ipc247.view.kho.ThongTinPhieuXuatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemGiaoViecActivity extends AppCompatActivity {

    ArrayList<T_MasterData> lstPhongBan;
    ArrayList<T_NhanVien> lstNhanVien;
    ArrayList<T_NhanVien> lstNhanVienLienQuan;
    ArrayList<T_NhomCongViec> lstNhomCongViec;
    Context mContext;

    TextInputEditText txtNgayBatDau, txtGio, txtCauHinh, txtDienGiai;
    AutoCompleteTextView txtNguoiNhanViec, txtNguoiLienQuan, txtPhongBan, txtNhomCongViec;
    Button btnXacNhan;

    Integer IdPhongBan, IdNhomCongViec;
    String strMaNV_NhanViec, strMaNV_NguoiLienQuan, strGio, strNgayBatDau;
    Integer mMinute = 0, mHour = 0;

    ArrayAdapter<String> adapterNhanVien;
    ArrayAdapter<String> adapterNhanVienNhanViec;
    ArrayAdapter<String> adapterPhongBan;
    ArrayAdapter<String> adapterNhomCongViec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_giao_viec);
        mContext = this;
        //Home
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Thêm Giao Việc");

        txtPhongBan = findViewById(R.id.txtPhongBan);
        txtNguoiNhanViec = findViewById(R.id.txtNguoiNhanViec);
        txtNguoiLienQuan = findViewById(R.id.txtNguoiLienQuan);
        txtNgayBatDau = findViewById(R.id.txtNgayBatDau);
        txtNhomCongViec = findViewById(R.id.txtNhomCongViec);
        txtCauHinh = findViewById(R.id.txtCauHinh);
        txtDienGiai = findViewById(R.id.txtDienGiai);
        txtGio = findViewById(R.id.txtGio);
        btnXacNhan = findViewById(R.id.btnXacNhan);
        //Ngày đăng ký mặc định
        Date currentTime = Calendar.getInstance().getTime();
        DateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
        strNgayBatDau = formatter2.format(currentTime);
        txtNgayBatDau.setText(formatter1.format(currentTime));

        GetPhongBan();
        GetNguoiLienQuan();

        txtNgayBatDau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                                calendar.set(year, month, day);
                                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                                String strDate = formatter.format(calendar.getTime());

                                txtNgayBatDau.setText(strDate);
                                //Lấy giá trị gửi lên server
                                SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
                                strNgayBatDau = formatter2.format(calendar.getTime());

                            }
                        }, year, month, dayOfMonth);

                datePickerDialog.show();
            }
        });

        txtGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String gio, phut;
                        gio = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
                        phut = minute < 10 ? "0" + minute : "" + minute;
                        txtGio.setText(gio + ":" + phut);
                        strGio = hourOfDay + ":" + minute;

                    }
                }, mHour, mMinute, false);
                timePickerDialog.show();

            }
        });

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Update();
            }
        });
    }

    public void GetNguoiNhanViec() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "GET_DATA");
        jsonObject.addProperty("idPhongBan", IdPhongBan);//20032
        jsonObject.addProperty("timKiem", 7);
        Call<CustomResult> call = ApiNhanVien.apiNhanVien.getNhanVien(jsonObject);
        call.enqueue(new Callback<CustomResult>() {
            @Override
            public void onResponse(Call<CustomResult> call, Response<CustomResult> response) {
                CustomResult result = response.body();
                if (result.getStatusCode() == 200) {
                    List<T_NhanVien> lstNhanVienTemp = result.getDtNhanVien();
                    if (lstNhanVienTemp.size() > 0) {
                        lstNhanVien = new ArrayList<T_NhanVien>();
                        lstNhanVien.addAll(lstNhanVienTemp);


                        String[] arrayNhanVien = new String[lstNhanVien.size()];
                        int i = 0;
                        for (T_NhanVien nhanVien : lstNhanVien) {
                            arrayNhanVien[i] = nhanVien.getMaNV() + " - " + nhanVien.getHoTen();
                            i++;
                        }
                        adapterNhanVienNhanViec = new ArrayAdapter<String>(mContext, R.layout.dropdown_item, arrayNhanVien);
                        txtNguoiNhanViec.setAdapter(adapterNhanVienNhanViec);

                        txtNguoiNhanViec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                T_NhanVien nhanVien = lstNhanVien.get((int) id);
                                strMaNV_NhanViec = nhanVien.getMaNV();
                                //  Toast.makeText(mContext, nhanVien.getMaNV(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                } else {
                    TM_Toast.makeText(mContext, "Không tìm thấy dữ liệu.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
                }
            }

            @Override
            public void onFailure(Call<CustomResult> call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
            }
        });
    }

    public void GetNhomCongViec() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "GET_BY_PHONGBAN");
        jsonObject.addProperty("idPhongBan", IdPhongBan);//20032
        Call<ResultNhomCongViec> call = ApiGiaoViec.apiGiaoViec.GetNhomCongViec(jsonObject);
        call.enqueue(new Callback<ResultNhomCongViec>() {
            @Override
            public void onResponse(Call<ResultNhomCongViec> call, Response<ResultNhomCongViec> response) {
                ResultNhomCongViec result = response.body();
                if (result.getStatusCode() == 200) {
                    List<T_NhomCongViec> lstNhomCongViecTemp = result.getDtNhomCongViec();
                    if (lstNhomCongViecTemp.size() > 0) {
                        lstNhomCongViec = new ArrayList<T_NhomCongViec>();
                        lstNhomCongViec.addAll(lstNhomCongViecTemp);


                        String[] arrayNhomCongViec = new String[lstNhomCongViec.size()];
                        int i = 0;
                        for (T_NhomCongViec nhomCongViec : lstNhomCongViec) {
                            arrayNhomCongViec[i] = nhomCongViec.getNhomCongViec();
                            i++;
                        }
                        adapterNhomCongViec = new ArrayAdapter<String>(mContext, R.layout.dropdown_item, arrayNhomCongViec);
                        txtNhomCongViec.setAdapter(adapterNhomCongViec);

                        txtNhomCongViec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                T_NhomCongViec nhomCongViec = lstNhomCongViec.get((int) id);
                                IdNhomCongViec = nhomCongViec.getId();
                                //   Toast.makeText(mContext, nhomCongViec.getNhomCongViec(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                } else {
                    TM_Toast.makeText(mContext, "Không tìm thấy dữ liệu.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
                }
            }

            @Override
            public void onFailure(Call<ResultNhomCongViec> call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
            }
        });
    }

    public void GetNguoiLienQuan() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "GET_DATA");
        jsonObject.addProperty("timKiem", 1);
        Call<CustomResult> call = ApiNhanVien.apiNhanVien.getNhanVien(jsonObject);
        call.enqueue(new Callback<CustomResult>() {
            @Override
            public void onResponse(Call<CustomResult> call, Response<CustomResult> response) {
                CustomResult result = response.body();
                if (result.getStatusCode() == 200) {
                    List<T_NhanVien> lstNhanVienTemp = result.getDtNhanVien();
                    if (lstNhanVienTemp.size() > 0) {
                        lstNhanVienLienQuan = new ArrayList<T_NhanVien>();
                        lstNhanVienLienQuan.addAll(lstNhanVienTemp);

                        String[] arrayNhanVien = new String[lstNhanVienLienQuan.size()];
                        int i = 0;
                        for (T_NhanVien nhanVien : lstNhanVienLienQuan) {
                            arrayNhanVien[i] = nhanVien.getMaNV() + " - " + nhanVien.getHoTen();
                            i++;
                        }
                        adapterNhanVien = new ArrayAdapter<String>(mContext, R.layout.dropdown_item, arrayNhanVien);
                        txtNguoiLienQuan.setAdapter(adapterNhanVien);

                        txtNguoiLienQuan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                T_NhanVien nhanVien = lstNhanVienLienQuan.get((int) id);
                                strMaNV_NguoiLienQuan = nhanVien.getMaNV();
                                Toast.makeText(mContext, nhanVien.getMaNV(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                } else {
                    TM_Toast.makeText(mContext, "Không tìm thấy dữ liệu.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
                }
            }

            @Override
            public void onFailure(Call<CustomResult> call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
            }
        });
    }

    public void GetPhongBan() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "GET_GROUP");
        jsonObject.addProperty("group", "PHONGBAN");
        Call<ResultMasterData> call = ApiMasterData.apiMasterData.GetMasterData(jsonObject);
        call.enqueue(new Callback<ResultMasterData>() {
            @Override
            public void onResponse(Call<ResultMasterData> call, Response<ResultMasterData> response) {
                ResultMasterData result = response.body();
                if (result.getStatusCode() == 200) {
                    List<T_MasterData> lstPhongBanTemp = result.getDtMasterData();
                    if (lstPhongBanTemp.size() > 0) {
                        lstPhongBan = new ArrayList<T_MasterData>();
                        lstPhongBan.addAll(lstPhongBanTemp);

                        String[] arrayPhongBan = new String[lstPhongBan.size()];
                        int i = 0;
                        for (T_MasterData phongban : lstPhongBan) {
                            arrayPhongBan[i] = String.valueOf(phongban.getValue());
                            i++;
                        }
                        adapterPhongBan = new ArrayAdapter<String>(mContext, R.layout.dropdown_item, arrayPhongBan);
                        txtPhongBan.setAdapter(adapterPhongBan);

                        txtPhongBan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                T_MasterData phongban = lstPhongBan.get((int) id);
                                // String item = parent.getItemAtPosition(position).toString();
                                IdPhongBan = phongban.getId();
                                txtNguoiNhanViec.setText("Chọn người nhận việc");
                                GetNguoiNhanViec();
                                GetNhomCongViec();
                                Toast.makeText(mContext, phongban.getValue(), Toast.LENGTH_LONG).show();
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

    private void Update() {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                .setTitle("Xác Nhận")
                .setMessage("Bạn có muốn lưu thông tin giao việc không?")
                .setCancelable(false)
                .setPositiveButton("Xác Nhận", R.drawable.ic_xacnhan_white, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {

                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("action", "UPDATE");
                        jsonObject.addProperty("idNhomCongViec", IdNhomCongViec);
                        jsonObject.addProperty("ngayBatDau", strNgayBatDau + " " + strGio);
                        jsonObject.addProperty("cauHinh", txtCauHinh.getText().toString());
                        jsonObject.addProperty("nguoiNhanViec", strMaNV_NhanViec);
                        jsonObject.addProperty("nguoiLienQuan", strMaNV_NguoiLienQuan);
                        jsonObject.addProperty("idPhongBan", IdPhongBan);
                        jsonObject.addProperty("ghiChu", txtDienGiai.getText().toString());
                        jsonObject.addProperty("userName", IPC247.tendangnhap);

                        Call<ResultGiaoViec> call = ApiGiaoViec.apiGiaoViec.Update(jsonObject);
                        call.enqueue(new Callback<ResultGiaoViec>() {
                            @Override
                            public void onResponse(Call<ResultGiaoViec> call, Response<ResultGiaoViec> response) {
                                ResultGiaoViec result = response.body();
                                if (result == null) {
                                    TM_Toast.makeText(mContext, "Call API fail.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
                                    return;
                                }
                                if (result.getStatusCode() == 200) {
                                    List<T_GiaoViec> lstGiaoViec = result.getDtGiaoViec();
                                    if (lstGiaoViec.size() > 0) {
                                        TM_Toast.makeText(mContext, lstGiaoViec.get(0).getMessage(), TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                                        //Đòng và quay trở lại
                                        Intent resultIntent = new Intent();
                                        resultIntent.putExtra("name", "your_editext_value");
                                        setResult(Activity.RESULT_OK, resultIntent);
                                        finish();
                                    }
                                } else {
                                    TM_Toast.makeText(mContext, "Không tìm thấy dữ liệu.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResultGiaoViec> call, Throwable t) {
                                TM_Toast.makeText(mContext, "Call API fail.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
                            }
                        });
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Đóng", R.drawable.ic_close_white, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();
        mBottomSheetDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                Intent resultIntent = new Intent();
                resultIntent.putExtra("name", "your_editext_value");
                setResult(Activity.RESULT_OK, resultIntent);

                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}