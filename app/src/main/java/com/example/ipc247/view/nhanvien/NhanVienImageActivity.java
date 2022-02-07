package com.example.ipc247.view.nhanvien;

import static com.example.ipc247.view.nhanvien.NhanVienActivity.MaNVTemp;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ipc247.R;
import com.example.ipc247.adapter.nhansu.Adapter_NhanVien;
import com.example.ipc247.api.ApiNhanVien;
import com.example.ipc247.model.nhanvien.CustomResult;
import com.example.ipc247.model.nhanvien.T_NhanVien;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NhanVienImageActivity extends AppCompatActivity {

    ArrayList<T_NhanVien> lstNhanVien;
    Adapter_NhanVien adapter;


    private Unbinder unbinder;
    Context mContext;

    EditText txtHoTen, txtPhongBan;
    ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_nhan_vien);
        mContext = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Hồ Sơ Nhân Viên");
//        unbinder = ButterKnife.bind(this);
        GetNhanVien();

    }

    private void GetNhanVien() {
        txtHoTen = findViewById(R.id.txtHoTen);
        txtPhongBan = findViewById(R.id.txtPhongBan);
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
                    txtPhongBan.setText(lstNhanVien.get(0).getPhongBan() + " / " + lstNhanVien.get(0).getChucVu());

                    String url = lstNhanVien.get(0).getHinh();
                    Picasso.get()
                            .load(url)
                            .error(R.drawable.no_image)//hien thi hinh mac dinh khi ko co hinh
                            .placeholder(R.drawable.no_image)
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