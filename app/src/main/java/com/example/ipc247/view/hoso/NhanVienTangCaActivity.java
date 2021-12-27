package com.example.ipc247.view.hoso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.SearchManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.ipc247.R;
import com.example.ipc247.adapter.Adapter_NhanVienTangCa;
import com.example.ipc247.api.ApiMasterData;
import com.example.ipc247.api.ApiNhanVienTangCa;
import com.example.ipc247.api.ApiPhanQuyen;
import com.example.ipc247.model.login.ResultPhanQuyen;
import com.example.ipc247.model.login.T_PhanQuyen;
import com.example.ipc247.model.masterdata.ResultLoaiTangCa;
import com.example.ipc247.model.masterdata.T_LoaiTangCa;
import com.example.ipc247.model.nhanvien.ResultNhanVienTangCa;
import com.example.ipc247.model.nhanvien.T_NhanVienTangCa;
import com.example.ipc247.system.ClickListener;
import com.example.ipc247.system.IPC247;
import com.example.ipc247.system.RecyclerTouchListener;
import com.example.ipc247.system.TM_Toast;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;

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

public class NhanVienTangCaActivity extends AppCompatActivity {

    Context mContext;
    private Unbinder unbinder;
    ArrayList<T_LoaiTangCa> lstLoaiTangCa;

    ArrayList<T_NhanVienTangCa> lstNhanVienTangCa;
    Adapter_NhanVienTangCa adapter;

    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    //swiperefresh
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;

    String TuNgay, DenNgay, strNgayTangCa, strTuGio, strDenGio;
    Integer intLoaiTangCa;
    Integer mMinute = 0, mHour = 0;

    TextInputEditText txtNgayTangCa, txtLoaiTangCa, txtTuGio, txtDenGio, txtGhiChu;
    Button btnLuu, btnDong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_vien_tang_ca);
        mContext = this;
        unbinder = ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Đăng Ký Tăng Ca");

        Date currentTime = Calendar.getInstance().getTime();
        DateFormat formatter1 = new SimpleDateFormat("yyyy-MM-01");
        DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
        TuNgay = formatter1.format(currentTime);
        DenNgay = formatter2.format(currentTime);

        GetPhanQuyenTangCa();
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(mContext, recycleView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                T_NhanVienTangCa nhanVienTangCa = lstNhanVienTangCa.get(position);
                GetChucNang(nhanVienTangCa, position);
            }
        }));
        //Làm mới dữ liệu
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetPhanQuyenTangCa();
            }
        });
    }

    private void GetPhanQuyenTangCa() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "GET_BY_ID");
        jsonObject.addProperty("idNhomQuyen", IPC247.IdNhomQuyen);
        jsonObject.addProperty("mamenu", "NS019");
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
                        return;
                    }
                    String chophep = lstPhanQuyen.get(0).getChophep();
                    if (chophep.equals("OK")) {
                        GetNhanVienTangCa(1);
                    } else {
                        GetNhanVienTangCa(2);
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

    private void GetNhanVienTangCa(int Timkiem) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "GET_DATA");
        jsonObject.addProperty("fromDate", TuNgay);
        jsonObject.addProperty("toDate", DenNgay);
        jsonObject.addProperty("maNV", IPC247.strMaNV);
        jsonObject.addProperty("timKiem", Timkiem);
        Call<ResultNhanVienTangCa> call = ApiNhanVienTangCa.apiNhanVienTangCa.getNhanVienTangCa(jsonObject);
        call.enqueue(new Callback<ResultNhanVienTangCa>() {
            @Override
            public void onResponse(Call<ResultNhanVienTangCa> call, Response<ResultNhanVienTangCa> response) {
                ResultNhanVienTangCa result = response.body();
                if (result == null) {
                    TM_Toast.makeText(mContext, "Không tìm thấy dữ liệu.", TM_Toast.LENGTH_SHORT, TM_Toast.WARNING, false).show();
                    return;
                }
                if (result.getStatusCode() == 200) {
                    List<T_NhanVienTangCa> lstTangCaTemps = result.getDtNhanVienTangCa();

                    lstNhanVienTangCa = new ArrayList<T_NhanVienTangCa>();
                    lstNhanVienTangCa.addAll(lstTangCaTemps);
                    adapter = new Adapter_NhanVienTangCa(mContext, lstNhanVienTangCa);
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
            public void onFailure(Call<ResultNhanVienTangCa> call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
            }
        });
    }

    private void GetPhanQuyenDuyet(T_NhanVienTangCa nhanVienTangCa) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "GET_BY_ID");
        jsonObject.addProperty("idNhomQuyen", IPC247.IdNhomQuyen);
        jsonObject.addProperty("mamenu", "NS019");
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
                        TM_Toast.makeText(mContext, "Không được phép xác nhận tăng ca.", TM_Toast.LENGTH_SHORT, TM_Toast.WARNING, false).show();
                        return;
                    }
                    String chophep = lstPhanQuyen.get(0).getChophep();
                    if (chophep.equals("OK")) {
                        Duyet(nhanVienTangCa);
                    } else {
                        TM_Toast.makeText(mContext, "Không được phép xác nhận tăng ca.", TM_Toast.LENGTH_SHORT, TM_Toast.WARNING, false).show();
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
                GetPhanQuyenTangCa();
            }
        });
    }

    private void Delete(final T_NhanVienTangCa nhanVienTangCa, final int position) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                .setTitle("Xác Nhận")
                .setMessage("Bạn có muốn xóa này không?")
                .setCancelable(false)
                .setPositiveButton("Xóa", R.drawable.ic_delete_white, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {

                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("action", "DELETE");
                        jsonObject.addProperty("id", nhanVienTangCa.getId());
                        jsonObject.addProperty("userName", IPC247.tendangnhap);
                        Call<ResultNhanVienTangCa> call = ApiNhanVienTangCa.apiNhanVienTangCa.Delete(jsonObject);
                        call.enqueue(new Callback<ResultNhanVienTangCa>() {
                            @Override
                            public void onResponse(Call<ResultNhanVienTangCa> call, Response<ResultNhanVienTangCa> response) {
                                ResultNhanVienTangCa result = response.body();
                                if (result == null) {
                                    TM_Toast.makeText(mContext, "Call API fail.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
                                    return;
                                }
                                if (result.getStatusCode() == 200) {
                                    List<T_NhanVienTangCa> lstTangCaTemps = result.getDtNhanVienTangCa();
                                    if (lstTangCaTemps.size() > 0) {
                                        int kq = lstTangCaTemps.get(0).getResult();
                                        if (kq == 1) {
                                            TM_Toast.makeText(mContext, lstTangCaTemps.get(0).getMessage(), TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                                            GetPhanQuyenTangCa();
                                        } else {
                                            TM_Toast.makeText(mContext, lstTangCaTemps.get(0).getMessage(), TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                                        }

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

    public void GetChucNang(T_NhanVienTangCa nhanVienTangCa, int position) {
        final String[] chucnangs = {"Duyệt", "Xác nhận", "Xóa"};
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Chức năng");
        builder.setCancelable(false);
        int i = 0;

        builder.setItems(chucnangs, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String chucnang = chucnangs[i];
                dialogInterface.dismiss(); // Close Dialog
                if (chucnang.equals("Xóa")) {
                    Delete(nhanVienTangCa, position);
                } else if (chucnang.equals("Xác nhận")) {
                    XacNhan(nhanVienTangCa);
                } else if (chucnang.equals("Duyệt")) {
                    GetPhanQuyenDuyet(nhanVienTangCa);
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void ThemTangCa() {
        View view_bottom_sheet = LayoutInflater.from(this).inflate(R.layout.bottomsheet_dangky_tangca, null);
        txtNgayTangCa = view_bottom_sheet.findViewById(R.id.txtNgayTangCa);
        txtLoaiTangCa = view_bottom_sheet.findViewById(R.id.txtLoaiTangCa);
        txtTuGio = view_bottom_sheet.findViewById(R.id.txtTuGio);
        txtDenGio = view_bottom_sheet.findViewById(R.id.txtDenGio);
        txtGhiChu = view_bottom_sheet.findViewById(R.id.txtGhiChu);

        btnLuu = view_bottom_sheet.findViewById(R.id.btnLuu);
        btnDong = view_bottom_sheet.findViewById(R.id.btnDong);

        BottomSheetDialog dialog = new BottomSheetDialog(this, R.style.DialogBottomStyle);
        dialog.setContentView(view_bottom_sheet);
        dialog.setCancelable(false);
        dialog.show();


        GetLoaiTangCa(txtLoaiTangCa);


        txtNgayTangCa.setOnClickListener(new View.OnClickListener() {
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

                                txtNgayTangCa.setText(strDate);
                                //Lấy giá trị gửi lên server
                                SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
                                strNgayTangCa = formatter2.format(calendar.getTime());

                            }
                        }, year, month, dayOfMonth);

                datePickerDialog.show();
            }
        });

        txtTuGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        txtTuGio.setText(hourOfDay + ":" + minute);
                        strTuGio = hourOfDay + ":" + minute;

                    }
                }, mHour, mMinute, false);
                timePickerDialog.show();

            }
        });

        txtDenGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        txtDenGio.setText(hourOfDay + ":" + minute);
                        strDenGio = hourOfDay + ":" + minute;

                    }
                }, mHour, mMinute, false);
                timePickerDialog.show();

            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                jsonObject.addProperty("idLoaiTangCa", intLoaiTangCa);
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
                                GetPhanQuyenTangCa();
                                TM_Toast.makeText(mContext, lstTangCa.get(0).getMessage(), TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                                //đóng dialog
                                dialog.setCancelable(true);
                                dialog.dismiss();
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
        });

        btnDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setCancelable(true);
                dialog.dismiss();
            }
        });
    }

    private void XacNhan(final T_NhanVienTangCa nhanVienTangCa) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                .setTitle("Xác Nhận")
                .setMessage("Bạn có muốn xác nhận không?")
                .setCancelable(false)
                .setPositiveButton("Xác Nhận", R.drawable.ic_xacnhan_white, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {

                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("action", "XACNHAN");
                        jsonObject.addProperty("id", nhanVienTangCa.getId());
                        jsonObject.addProperty("xacNhan", 1);
                        jsonObject.addProperty("userName", IPC247.tendangnhap);
                        Call<ResultNhanVienTangCa> call = ApiNhanVienTangCa.apiNhanVienTangCa.XacNhan_Duyet(jsonObject);
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
                                        int Kq = lstTangCa.get(0).getResult();
                                        if (Kq == 1) {
                                            TM_Toast.makeText(mContext, lstTangCa.get(0).getMessage(), TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                                            GetPhanQuyenTangCa();
                                        } else {
                                            TM_Toast.makeText(mContext, lstTangCa.get(0).getMessage(), TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                                        }

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

    private void Duyet(final T_NhanVienTangCa nhanVienTangCa) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                .setTitle("Xác Nhận")
                .setMessage("Bạn có muốn duyệt không?")
                .setCancelable(false)
                .setPositiveButton("Xác Nhận", R.drawable.ic_xacnhan_white, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {

                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("action", "DUYET");
                        jsonObject.addProperty("id", nhanVienTangCa.getId());
                        jsonObject.addProperty("duyet", 1);
                        jsonObject.addProperty("userName", IPC247.tendangnhap);
                        Call<ResultNhanVienTangCa> call = ApiNhanVienTangCa.apiNhanVienTangCa.XacNhan_Duyet(jsonObject);
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
                                        int Kq = lstTangCa.get(0).getResult();
                                        if (Kq == 1) {
                                            TM_Toast.makeText(mContext, lstTangCa.get(0).getMessage(), TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                                            GetPhanQuyenTangCa();
                                        } else {
                                            TM_Toast.makeText(mContext, lstTangCa.get(0).getMessage(), TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                                        }

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

    public void GetLoaiTangCa(TextView txtLoaiTangca) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "GET_DATA");
        Call<ResultLoaiTangCa> call = ApiMasterData.apiMasterData.GetLoaiTangCa(jsonObject);
        call.enqueue(new Callback<ResultLoaiTangCa>() {
            @Override
            public void onResponse(Call<ResultLoaiTangCa> call, Response<ResultLoaiTangCa> response) {
                ResultLoaiTangCa result = response.body();
                if (result.getStatusCode() == 200) {
                    List<T_LoaiTangCa> lstLyDos = result.getDtLoaiTangCa();
                    if (lstLyDos.size() > 0) {
                        lstLoaiTangCa = new ArrayList<T_LoaiTangCa>();
                        lstLoaiTangCa.addAll(lstLyDos);
                        txtLoaiTangca.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                builder.setTitle("Loại tăng ca");
                                builder.setCancelable(false);
                                String[] arrayLyDo = new String[lstLoaiTangCa.size()];
                                int i = 0;
                                for (T_LoaiTangCa loaitangca : lstLoaiTangCa) {
                                    arrayLyDo[i] = String.valueOf(loaitangca.getLoaiTangCa());
                                    i++;
                                }

                                builder.setSingleChoiceItems(arrayLyDo, -1, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        T_LoaiTangCa loaiTangCa = lstLoaiTangCa.get(i);
                                        txtLoaiTangca.setText(loaiTangCa.getLoaiTangCa());
                                        intLoaiTangCa = loaiTangCa.getId();
                                        dialogInterface.dismiss();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.btnThem:
                ThemTangCa();
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
        getMenuInflater().inflate(R.menu.menu_item_add, menu);
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