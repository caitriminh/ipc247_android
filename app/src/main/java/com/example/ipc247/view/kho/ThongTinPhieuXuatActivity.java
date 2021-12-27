package com.example.ipc247.view.kho;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ipc247.R;
import com.example.ipc247.api.ApiXuatKho;
import com.example.ipc247.model.kho.ResultPhieuXuatQRCode;
import com.example.ipc247.model.kho.ResultXuatTemp;
import com.example.ipc247.model.kho.T_PhieuXuat_QRCode;
import com.example.ipc247.model.kho.T_XuatTemp;
import com.example.ipc247.system.IPC247;
import com.example.ipc247.system.TM_Toast;
import com.google.gson.JsonObject;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThongTinPhieuXuatActivity extends AppCompatActivity {

    Context mContext;
    private Unbinder unbinder;

    @BindView(R.id.txtProductCode)
    TextView txtProductCode;

    @BindView(R.id.txtDescription)
    TextView txtDescription;

    @BindView(R.id.txtDVT)
    TextView txtDVT;

    @BindView(R.id.txtHangSanXuat)
    TextView txtHangSanXuat;

    @BindView(R.id.txtDonGia)
    TextView txtDonGia;

    @BindView(R.id.btnXacNhan)
    Button btnXacNhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_phieu_xuat);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        unbinder = ButterKnife.bind(this);
        this.setTitle("Thông Tin Xuất Kho");
        GetThongTinPhieuXuat();

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Luu();
                finish();
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
                Intent intent = new Intent(ThongTinPhieuXuatActivity.this, BarcodeActivity.class);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void GetThongTinPhieuXuat() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "GET_DATA");
        jsonObject.addProperty("id", IPC247.IDQRCode);
        Call<ResultPhieuXuatQRCode> call = ApiXuatKho.apiXuatKho.getInfoPhieuXuat(jsonObject);
        call.enqueue(new Callback<ResultPhieuXuatQRCode>() {
            @Override
            public void onResponse(Call<ResultPhieuXuatQRCode> call, Response<ResultPhieuXuatQRCode> response) {
                ResultPhieuXuatQRCode result = response.body();
                if (result == null) {
                    TM_Toast.makeText(mContext, "Không tìm thấy dữ liệu.", TM_Toast.LENGTH_SHORT, TM_Toast.WARNING, false).show();
                    return;
                }
                if (result.getStatusCode() == 200) {
                    List<T_PhieuXuat_QRCode> lstInfoPhieuXuat = result.getDtPhieuXuatQRCode();
                    if (lstInfoPhieuXuat.size() > 0) {
                        txtProductCode.setText(lstInfoPhieuXuat.get(0).getProductCode());
                        txtDescription.setText(lstInfoPhieuXuat.get(0).getDescription());
                        txtDVT.setText(lstInfoPhieuXuat.get(0).getDvt());
                        txtHangSanXuat.setText(lstInfoPhieuXuat.get(0).getShortName());

                        DecimalFormat format = new DecimalFormat("#,##0");

                        Double dblDonGia = lstInfoPhieuXuat.get(0).getDonGiaVND();
                        txtDonGia.setText(format.format(dblDonGia));
                    }
                } else {
                    TM_Toast.makeText(mContext, "Không tìm thấy dữ liệu.", TM_Toast.LENGTH_SHORT, TM_Toast.WARNING, false).show();
                }
            }

            @Override
            public void onFailure(Call<ResultPhieuXuatQRCode> call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
            }
        });
    }

    public void Luu() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "UPDATE");
        jsonObject.addProperty("idqrCode", IPC247.IDQRCode);
        jsonObject.addProperty("userName", IPC247.tendangnhap);
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

                        TM_Toast.makeText(ThongTinPhieuXuatActivity.this, lstXuatTemp.get(0).getMessage(), TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();

                    }

                } else {
                    TM_Toast.makeText(ThongTinPhieuXuatActivity.this, "Không tìm thấy dữ liệu.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
                }
            }

            @Override
            public void onFailure(Call<ResultXuatTemp> call, Throwable t) {
                TM_Toast.makeText(ThongTinPhieuXuatActivity.this, "Call API fail.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
            }
        });
    }
}