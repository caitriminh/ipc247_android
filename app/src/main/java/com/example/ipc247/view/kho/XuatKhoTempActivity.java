package com.example.ipc247.view.kho;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.example.ipc247.R;
import com.example.ipc247.adapter.kho.Adapter_XuatTemp;
import com.example.ipc247.api.ApiXuatKho;
import com.example.ipc247.model.kho.ResultKhachHang;
import com.example.ipc247.model.kho.ResultSaleOrder;
import com.example.ipc247.model.kho.ResultXuatTemp;
import com.example.ipc247.model.kho.T_KhachHang;
import com.example.ipc247.model.kho.T_SaleOrder;
import com.example.ipc247.model.kho.T_XuatTemp;
import com.example.ipc247.system.ClickListener;
import com.example.ipc247.system.IPC247;
import com.example.ipc247.system.RecyclerTouchListener;
import com.example.ipc247.system.TM_Toast;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class XuatKhoTempActivity extends AppCompatActivity {

    Context mContext;
    private Unbinder unbinder;

    ArrayList<T_XuatTemp> lstXuatTemp;
    Adapter_XuatTemp adapter;

    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    //swiperefresh
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;

    ArrayList<T_KhachHang> lstKhachHang;
    ArrayList<T_SaleOrder> lstDonHang;

    String name = "a";
    TextInputEditText txtKhachHang, txtSoDH;
    Button btnLuu, btnDong;
    String IDComp="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xuat_kho_temp);
        mContext = this;
        unbinder = ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Xuất Kho");
        GetXuatTemp();
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(mContext, recycleView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                T_XuatTemp xuatTemp = lstXuatTemp.get(position);
                Delete(xuatTemp, position);
            }
        }));
        //Làm mới dữ liệu
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetXuatTemp();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.btnQRCode:
                Intent sub = new Intent(this, BarcodeScanActivity.class);
                sub.putExtra("name", name);
                startActivityForResult(sub, 100);
                break;
            case R.id.btnXacNhan:
                XacNhan();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 100) {
                GetXuatTemp();
            }
        }
    }

    private void GetXuatTemp() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userName", IPC247.tendangnhap);
        Call<ResultXuatTemp> call = ApiXuatKho.apiXuatKho.GetXuatTemp(jsonObject);
        call.enqueue(new Callback<ResultXuatTemp>() {
            @Override
            public void onResponse(Call<ResultXuatTemp> call, Response<ResultXuatTemp> response) {
                ResultXuatTemp result = response.body();
                if (result == null) {
                    TM_Toast.makeText(mContext, "Không tìm thấy dữ liệu.", TM_Toast.LENGTH_SHORT, TM_Toast.WARNING, false).show();
                    return;
                }
                if (result.getStatusCode() == 200) {
                    List<T_XuatTemp> lstXuatTemps = result.getDtXuatTemp();

                    lstXuatTemp = new ArrayList<T_XuatTemp>();
                    lstXuatTemp.addAll(lstXuatTemps);
                    adapter = new Adapter_XuatTemp(mContext, lstXuatTemp);
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
            public void onFailure(Call<ResultXuatTemp> call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
            }
        });
    }

    public void GetKhachHang(TextView txtKhachHang) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "GET_KHACHHANG");
        Call<ResultKhachHang> call = ApiXuatKho.apiXuatKho.getKhachHang(jsonObject);
        call.enqueue(new Callback<ResultKhachHang>() {
            @Override
            public void onResponse(Call<ResultKhachHang> call, Response<ResultKhachHang> response) {
                ResultKhachHang result = response.body();
                if (result.getStatusCode() == 200) {
                    List<T_KhachHang> lstKhachHangTemp = result.getDtKhachHang();
                    if (lstKhachHangTemp.size() > 0) {
                        lstKhachHang = new ArrayList<T_KhachHang>();
                        lstKhachHang.addAll(lstKhachHangTemp);
                        txtKhachHang.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                builder.setTitle("Khách Hàng");
                                builder.setCancelable(false);
                                String[] arrayLyDo = new String[lstKhachHang.size()];
                                int i = 0;
                                for (T_KhachHang lydo : lstKhachHang) {
                                    arrayLyDo[i] = String.valueOf(lydo.getCompany());
                                    i++;
                                }

                                builder.setSingleChoiceItems(arrayLyDo, -1, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        T_KhachHang lydo = lstKhachHang.get(i);
                                        txtKhachHang.setText(lydo.getCompany());
                                        IDComp = lydo.getId();
                                        GetDonHang(txtSoDH);
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
            public void onFailure(Call<ResultKhachHang> call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
            }
        });
    }

    public void GetDonHang(TextView txtSoDH) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "GET_ORDER_BY_COMPANY");
        jsonObject.addProperty("idCompany", IDComp);
        Call<ResultSaleOrder> call = ApiXuatKho.apiXuatKho.getOrder(jsonObject);
        call.enqueue(new Callback<ResultSaleOrder>() {
            @Override
            public void onResponse(Call<ResultSaleOrder> call, Response<ResultSaleOrder> response) {
                ResultSaleOrder result = response.body();
                if (result.getStatusCode() == 200) {
                    List<T_SaleOrder> lstSaleOrderTemps = result.getDtSaleOrder();
                    if (lstSaleOrderTemps.size() > 0) {
                        lstDonHang = new ArrayList<T_SaleOrder>();
                        lstDonHang.addAll(lstSaleOrderTemps);

                        txtSoDH.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                builder.setTitle("Số Đơn Hàng");
                                builder.setCancelable(false);
                                String[] arrayLyDo = new String[lstDonHang.size()];
                                int i = 0;
                                for (T_SaleOrder donhang : lstDonHang) {
                                    arrayLyDo[i] = String.valueOf(donhang.getQuoteNum());
                                    i++;
                                }

                                builder.setSingleChoiceItems(arrayLyDo, -1, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        T_SaleOrder donhang = lstDonHang.get(i);
                                        txtSoDH.setText(donhang.getQuoteNum());
                                        IPC247.IDDonHang = String.valueOf(donhang.getIdQuote());
                                        dialogInterface.dismiss();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }

                        });
                    }

                } else {
                   // TM_Toast.makeText(mContext, "Không tìm thấy dữ liệu.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
                }
            }

            @Override
            public void onFailure(Call<ResultSaleOrder> call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
            }
        });
    }

    private void Delete(final T_XuatTemp xuatTemp, final int position) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                .setTitle("Xác Nhận")
                .setMessage("Bạn có muốn xóa không?")
                .setCancelable(false)
                .setPositiveButton("Xóa", R.drawable.ic_delete_white, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {

                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("action", "DELETE");
                        jsonObject.addProperty("id", xuatTemp.getId());

                        Call<ResultXuatTemp> call = ApiXuatKho.apiXuatKho.XuatTemp(jsonObject);
                        call.enqueue(new Callback<ResultXuatTemp>() {
                            @Override
                            public void onResponse(Call<ResultXuatTemp> call, Response<ResultXuatTemp> response) {
                                ResultXuatTemp result = response.body();
                                if (result == null) {
                                    TM_Toast.makeText(mContext, "Call API fail.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
                                    return;
                                }
                                if (result.getStatusCode() == 200) {
                                    List<T_XuatTemp> lstXuatTemp = result.getDtXuatTemp();
                                    if (lstXuatTemp.size() > 0) {
                                        TM_Toast.makeText(mContext, lstXuatTemp.get(0).getMessage(), TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                                        GetXuatTemp();
                                    }

                                } else {
                                    TM_Toast.makeText(mContext, "Không tìm thấy dữ liệu.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResultXuatTemp> call, Throwable t) {
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

    public void XacNhan() {
        View view_bottom_sheet = LayoutInflater.from(this).inflate(R.layout.bottomsheet_xacnhan_xuatkho, null);
        txtKhachHang = view_bottom_sheet.findViewById(R.id.txtKhachHang);
        txtSoDH = view_bottom_sheet.findViewById(R.id.txtSoDH);

        btnLuu = view_bottom_sheet.findViewById(R.id.btnLuu);
        btnDong = view_bottom_sheet.findViewById(R.id.btnDong);

        BottomSheetDialog dialog = new BottomSheetDialog(this, R.style.DialogBottomStyle);
        dialog.setContentView(view_bottom_sheet);
        dialog.setCancelable(false);
        dialog.show();

        GetKhachHang(txtKhachHang);

       GetDonHang(txtSoDH);

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (txtKhachHang.getText().toString().equals("")) {
                    TM_Toast.makeText(mContext, "Bạn vui lòng nhập vào tên khách hàng.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                    return;
                }

                if (txtSoDH.getText().toString().equals("")) {
                    TM_Toast.makeText(mContext, "Bạn vui lòng nhập vào số đơn hàng.", Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                    return;
                }

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("idSupplier", IDComp);
                jsonObject.addProperty("idOrder", IPC247.IDDonHang);
                jsonObject.addProperty("userName", IPC247.tendangnhap);
                Call<ResultXuatTemp> call = ApiXuatKho.apiXuatKho.XuatKhoQRCode(jsonObject);
                call.enqueue(new Callback<ResultXuatTemp>() {
                    @Override
                    public void onResponse(Call<ResultXuatTemp> call, Response<ResultXuatTemp> response) {
                        ResultXuatTemp result = response.body();
                        if (result == null) {
                            TM_Toast.makeText(mContext, "Call API fail.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
                            return;
                        }
                        if (result.getStatusCode() == 200) {
                            List<T_XuatTemp> lstXuatTemps = result.getDtXuatTemp();
                            if (lstXuatTemps.size() > 0) {

                                TM_Toast.makeText(mContext, lstXuatTemps.get(0).getMessage(), TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                                //đóng dialog
                                dialog.setCancelable(true);
                                dialog.dismiss();

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
                    public void onFailure(Call<ResultXuatTemp> call, Throwable t) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_barcode, menu);
        getMenuInflater().inflate(R.menu.menu_item_xacnhan, menu);
        return true;
    }

}