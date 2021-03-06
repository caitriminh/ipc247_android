package com.example.quyetthang.view.nhanvien;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.quyetthang.R;
import com.example.quyetthang.adapter.nhansu.Adapter_CongTac;
import com.example.quyetthang.api.ApiNghiPhep;
import com.example.quyetthang.api.ApiPhanQuyen;
import com.example.quyetthang.model.login.ResultPhanQuyen;
import com.example.quyetthang.model.login.T_PhanQuyen;
import com.example.quyetthang.model.nhanvien.ResultNghiPhep;
import com.example.quyetthang.model.nhanvien.T_NhanVienNghiPhep;
import com.example.quyetthang.system.ClickListener;
import com.example.quyetthang.system.IPC247;
import com.example.quyetthang.system.RecyclerTouchListener;
import com.example.quyetthang.system.TM_Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
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

public class CongTacActivity extends AppCompatActivity {

    ArrayList<T_NhanVienNghiPhep> lstNhanVienNghiPhep;
    Adapter_CongTac adapter;

    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;


    private Unbinder unbinder;
    Context mContext;

    String TuNgay, DenNgay, name = "congtac";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cong_tac);
        mContext = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("????ng K?? C??ng T??c");
        unbinder = ButterKnife.bind(this);

        Date currentTime = Calendar.getInstance().getTime();
        DateFormat formatter1 = new SimpleDateFormat("yyyy-01-01");
        DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
        TuNgay = formatter1.format(currentTime);
        DenNgay = formatter2.format(currentTime);

        GetCongTac();
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(mContext, recycleView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                T_NhanVienNghiPhep nhanVienNghiPhep = lstNhanVienNghiPhep.get(position);
                GetChucNang(nhanVienNghiPhep, position);
            }
        }));
        //L??m m???i d??? li???u
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetCongTac();
            }
        });
    }


    private void GetCongTac() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "GET_DATA_NGAY_DANGKY_CONGTAC");
        jsonObject.addProperty("fromDate", TuNgay);
        jsonObject.addProperty("toDate", DenNgay);
        jsonObject.addProperty("maNV", IPC247.strMaNV);

        Call<ResultNghiPhep> call = ApiNghiPhep.apiNghiPhep.NghiPhep(jsonObject);
        call.enqueue(new Callback<ResultNghiPhep>() {
            @Override
            public void onResponse(Call<ResultNghiPhep> call, Response<ResultNghiPhep> response) {
                ResultNghiPhep result = response.body();
                if (result.getStatusCode() == 200) {
                    List<T_NhanVienNghiPhep> lstNghiPhep = result.getDtNghiPhep();
                    if (lstNghiPhep.size() > 0) {
                        lstNhanVienNghiPhep = new ArrayList<T_NhanVienNghiPhep>();
                        lstNhanVienNghiPhep.addAll(lstNghiPhep);
                        adapter = new Adapter_CongTac(mContext, lstNhanVienNghiPhep);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                        recycleView.setLayoutManager(layoutManager);
                        recycleView.setAdapter(adapter);


                        //L??m m???i d??? li???u
                        swipeRefreshLayout.setRefreshing(false);
                    }

                } else {
                    Toast.makeText(mContext, "Kh??ng t??m th???y d??? li???u.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultNghiPhep> call, Throwable t) {
                Toast.makeText(mContext, "Call API fail.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void TimKiemTheoNgay() {
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        MaterialDatePicker picker = builder.setTheme(R.style.ThemeOverlay_MaterialComponents_MaterialCalendar).build();
        //MaterialDatePicker picker = builder.setTheme(R.style.Them).build();
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

                GetCongTac();
            }
        });

    }

    private void Delete(final T_NhanVienNghiPhep nhanVienNghiPhep) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                .setTitle("X??c Nh???n")
                .setMessage("B???n c?? mu???n x??a n??y kh??ng?")
                .setCancelable(false)
                .setPositiveButton("X??a", R.drawable.ic_delete_white, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {

                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("action", "DELETE");
                        jsonObject.addProperty("id", nhanVienNghiPhep.getId());
                        jsonObject.addProperty("userName", IPC247.tendangnhap);
                        Call<ResultNghiPhep> call = ApiNghiPhep.apiNghiPhep.UpdateNghiPhep(jsonObject);
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
                                        int kq = lstNghiPhep.get(0).getResult();
                                        if (kq == 1) {
                                            TM_Toast.makeText(mContext, "X??a ????ng k?? c??ng t??c th??nh c??ng.", TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                                            GetCongTac();
                                        } else {
                                            TM_Toast.makeText(mContext, lstNghiPhep.get(0).getMessage(), TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                                        }
                                    }

                                } else {
                                    TM_Toast.makeText(mContext, "Kh??ng t??m th???y d??? li???u.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResultNghiPhep> call, Throwable t) {
                                TM_Toast.makeText(mContext, "Call API fail.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
                            }
                        });
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("????ng", R.drawable.ic_close_white, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();
        mBottomSheetDialog.show();
    }

    private void XacNhan(final T_NhanVienNghiPhep nhanVienNghiPhep) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                .setTitle("X??c Nh???n")
                .setMessage("B???n c?? mu???n x??c nh???n kh??ng?")
                .setCancelable(false)
                .setPositiveButton("X??c Nh???n", R.drawable.ic_xacnhan_white, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {

                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("action", "XACNHAN");
                        jsonObject.addProperty("id", nhanVienNghiPhep.getId());
                        jsonObject.addProperty("xacNhan", 1);
                        jsonObject.addProperty("userName", IPC247.tendangnhap);
                        Call<ResultNghiPhep> call = ApiNghiPhep.apiNghiPhep.XacNhan_Duyet(jsonObject);
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
                                        int Kq = lstNghiPhep.get(0).getResult();
                                        if (Kq == 1) {
                                            TM_Toast.makeText(mContext, lstNghiPhep.get(0).getMessage(), TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                                            GetCongTac();
                                        } else {
                                            TM_Toast.makeText(mContext, lstNghiPhep.get(0).getMessage(), TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                                        }

                                    }
                                } else {
                                    TM_Toast.makeText(mContext, "Kh??ng t??m th???y d??? li???u.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResultNghiPhep> call, Throwable t) {
                                TM_Toast.makeText(mContext, "Call API fail.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
                            }
                        });
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("????ng", R.drawable.ic_close_white, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();
        mBottomSheetDialog.show();
    }

    private void Duyet(final T_NhanVienNghiPhep nhanVienNghiPhep) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                .setTitle("X??c Nh???n")
                .setMessage("B???n c?? mu???n x??c nh???n kh??ng?")
                .setCancelable(false)
                .setPositiveButton("X??c Nh???n", R.drawable.ic_xacnhan_white, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {

                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("action", "DUYET");
                        jsonObject.addProperty("id", nhanVienNghiPhep.getId());
                        jsonObject.addProperty("duyet", 1);
                        jsonObject.addProperty("userName", IPC247.tendangnhap);
                        Call<ResultNghiPhep> call = ApiNghiPhep.apiNghiPhep.XacNhan_Duyet(jsonObject);
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
                                        int Kq = lstNghiPhep.get(0).getResult();
                                        if (Kq == 1) {
                                            TM_Toast.makeText(mContext, lstNghiPhep.get(0).getMessage(), TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                                            GetCongTac();
                                        } else {
                                            TM_Toast.makeText(mContext, lstNghiPhep.get(0).getMessage(), TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                                        }

                                    }
                                } else {
                                    TM_Toast.makeText(mContext, "Kh??ng t??m th???y d??? li???u.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResultNghiPhep> call, Throwable t) {
                                TM_Toast.makeText(mContext, "Call API fail.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
                            }
                        });
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("????ng", R.drawable.ic_close_white, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();
        mBottomSheetDialog.show();
    }

    private void GetPhanQuyenDuyet(T_NhanVienNghiPhep nhanVienNghiPhep) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "GET_BY_ID");
        jsonObject.addProperty("idNhomQuyen", IPC247.IdNhomQuyen);
        jsonObject.addProperty("mamenu", "NS015");
        Call<ResultPhanQuyen> call = ApiPhanQuyen.apiPhanQuyen.GetPhanQuyen(jsonObject);
        call.enqueue(new Callback<ResultPhanQuyen>() {
            @Override
            public void onResponse(Call<ResultPhanQuyen> call, Response<ResultPhanQuyen> response) {
                ResultPhanQuyen result = response.body();
                if (result == null) {
                    TM_Toast.makeText(mContext, "Kh??ng t??m th???y d??? li???u.", TM_Toast.LENGTH_SHORT, TM_Toast.WARNING, false).show();
                    return;
                }
                if (result.getStatusCode() == 200) {
                    List<T_PhanQuyen> lstPhanQuyen = result.getDtPhanQuyen();
                    if (lstPhanQuyen.size() == 0) {
                        TM_Toast.makeText(mContext, "Kh??ng ???????c ph??p x??c nh???n ngh??? ph??p.", TM_Toast.LENGTH_SHORT, TM_Toast.WARNING, false).show();
                        return;
                    }
                    String chophep = lstPhanQuyen.get(0).getChophep();
                    if (chophep.equals("OK")) {
                        Duyet(nhanVienNghiPhep);
                    } else {
                        TM_Toast.makeText(mContext, "Kh??ng ???????c ph??p x??c nh???n ngh??? ph??p.", TM_Toast.LENGTH_SHORT, TM_Toast.WARNING, false).show();
                    }

                } else {
                    TM_Toast.makeText(mContext, "Kh??ng t??m th???y d??? li???u.", TM_Toast.LENGTH_SHORT, TM_Toast.WARNING, false).show();
                }
            }

            @Override
            public void onFailure(Call<ResultPhanQuyen> call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
            }
        });
    }

    public void GetChucNang(T_NhanVienNghiPhep nhanVienNghiPhep, int position) {
        final String[] chucnangs = {"Duy???t", "X??c nh???n", "X??a"};
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Ch???c n??ng");
        builder.setCancelable(false);
        int i = 0;

        builder.setItems(chucnangs, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String chucnang = chucnangs[i];
                dialogInterface.dismiss(); // Close Dialog
                if (chucnang.equals("X??a")) {
                    Delete(nhanVienNghiPhep);
                } else if (chucnang.equals("X??c nh???n")) {
                    XacNhan(nhanVienNghiPhep);
                } else if (chucnang.equals("Duy???t")) {
                    GetPhanQuyenDuyet(nhanVienNghiPhep);
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 100) {
                GetCongTac();
            }
        }
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
            case R.id.btnThem:
                Intent sub = new Intent(mContext, ThemCongTacActivity.class);
                sub.putExtra("name", name);
                startActivityForResult(sub, 100);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_add, menu);
        getMenuInflater().inflate(R.menu.menu_item_date, menu);
        return true;
    }
}