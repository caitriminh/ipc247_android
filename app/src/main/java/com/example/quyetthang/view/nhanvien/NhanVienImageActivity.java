package com.example.quyetthang.view.nhanvien;

import static com.example.quyetthang.view.nhanvien.NhanVienActivity.MaNVTemp;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quyetthang.R;
import com.example.quyetthang.api.ApiNhanVien;
import com.example.quyetthang.model.nhanvien.CustomResult;
import com.example.quyetthang.model.nhanvien.T_NhanVien;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NhanVienImageActivity extends AppCompatActivity {


    private Unbinder unbinder;
    Context mContext;

    TextInputEditText txtHoTen, txtPhongBan, txtNgaySinh, txtGioiTinh, txtNoiSinh, txtNgayVaoLam, txtDiaChi, txtTonGiao, txtDanToc, txtSoCMND, txtNgayCap;
    TextInputEditText txtNoiCap, txtSoBHXH, txtSoDT, txtEmail, txtMST, txtSoTK, txtNganHang;
    ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_nhanvien2);
        mContext = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Hồ Sơ Nhân Viên");
//        unbinder = ButterKnife.bind(this);
        GetNhanVien();
        imgView = findViewById(R.id.imgView);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent2 = new Intent(mContext, TestActivity.class);
//                startActivity(intent2);
            }
        });
    }

    private void GetNhanVien() {
        txtHoTen = findViewById(R.id.txtHoTen);
        txtPhongBan = findViewById(R.id.txtPhongBan);
        txtNgaySinh = findViewById(R.id.txtNgaySinh);
        txtNgayVaoLam = findViewById(R.id.txtNgayVaoLam);
        txtGioiTinh = findViewById(R.id.txtGioiTinh);
        txtNoiSinh = findViewById(R.id.txtNoiSinh);
        txtDiaChi = findViewById(R.id.txtDiaChi);
        txtTonGiao = findViewById(R.id.txtTonGiao);
        txtDanToc = findViewById(R.id.txtDanToc);
        txtSoCMND = findViewById(R.id.txtSoCMND);
        txtNgayCap = findViewById(R.id.txtNgayCap);
        txtNoiCap = findViewById(R.id.txtNoiCap);
        txtSoBHXH = findViewById(R.id.txtSoBHXH);
        txtSoDT = findViewById(R.id.txtSoDT);
        txtEmail = findViewById(R.id.txtEmail);
        txtSoTK = findViewById(R.id.txtSoTK);
        txtNganHang = findViewById(R.id.txtNganHang);
        txtMST = findViewById(R.id.txtMST);
        imgView = findViewById(R.id.imgView);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "GET_BY_NV");
        jsonObject.addProperty("MaNV", MaNVTemp);
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
                    txtGioiTinh.setText(lstNhanVien.get(0).getGioiTinh());
                    txtPhongBan.setText(lstNhanVien.get(0).getPhongBan() + " / " + lstNhanVien.get(0).getChucVu());
                    txtNgaySinh.setText(lstNhanVien.get(0).getNgaySinhText());
                    txtNgayVaoLam.setText(lstNhanVien.get(0).getNgayVaoLamText());
                    txtNoiSinh.setText(lstNhanVien.get(0).getNoiSinh());
                    txtDiaChi.setText(lstNhanVien.get(0).getDiaChi());
                    txtDanToc.setText(lstNhanVien.get(0).getDanToc());
                    txtTonGiao.setText(lstNhanVien.get(0).getTonGiao());
                    txtSoCMND.setText(lstNhanVien.get(0).getSoCMND());
                    txtNgayCap.setText(lstNhanVien.get(0).getNgayCapText());
                    txtNoiCap.setText(lstNhanVien.get(0).getNoiCap());
                    txtSoBHXH.setText(lstNhanVien.get(0).getSoBHXH());
                    txtEmail.setText(lstNhanVien.get(0).getEmail());
                    txtSoDT.setText(lstNhanVien.get(0).getSoDT());
                    txtSoTK.setText(lstNhanVien.get(0).getSoTK());
                    txtNganHang.setText(lstNhanVien.get(0).getNganHang());
                    txtMST.setText(lstNhanVien.get(0).getMst());

                    String url = lstNhanVien.get(0).getHinh();
                    Picasso.get()
                            .load(url)
                            .error(R.drawable.logo_ipc247)//hien thi hinh mac dinh khi ko co hinh
                            .placeholder(R.drawable.logo_ipc247)
                            .into(imgView);

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