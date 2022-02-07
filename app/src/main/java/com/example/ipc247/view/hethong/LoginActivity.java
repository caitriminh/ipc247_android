package com.example.ipc247.view.hethong;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.ipc247.Ipc247Activity;
import com.example.ipc247.R;
import com.example.ipc247.api.ApiLogin;
import com.example.ipc247.model.login.ResultUser;
import com.example.ipc247.model.login.T_User;
import com.example.ipc247.system.IPC247;
import com.example.ipc247.system.TM_Toast;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    TextView txtTenDangNhap, txtMatKhau, btnLogin;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        pref = getSharedPreferences("SESSION", MODE_PRIVATE);
        boolean isLogin = pref.getBoolean("isLogin", false);
        if (isLogin) {
            IPC247.tendangnhap = pref.getString("username", "").toUpperCase();
            IPC247.strMaNV = pref.getString("manv", "").toUpperCase();
            IPC247.IdNhomQuyen = pref.getString("IdNhomQuyen", "").toUpperCase();
            IPC247.strTenNV = pref.getString("tennv", "").toUpperCase();
            //Mở chương trình
            Intent intent = new Intent(LoginActivity.this, Ipc247Activity.class);
            startActivity(intent);
            finish();
        }
        txtTenDangNhap = findViewById(R.id.txtTenDangNhap);
        txtMatKhau = findViewById(R.id.txtMatKhau);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtTenDangNhap.getText().equals("") || txtMatKhau.getText().equals("")) {
                    TM_Toast.makeText(LoginActivity.this, "Vui lòng nhập tên đăng nhập và mật khẩu.", TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                    return;
                }
                Login();
            }
        });
    }

    private void Login() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "LOGIN_ANDROID");
        jsonObject.addProperty("userName", txtTenDangNhap.getText().toString());
        jsonObject.addProperty("password2", txtMatKhau.getText().toString());
        Call<ResultUser> call = ApiLogin.apiLogin.Login(jsonObject);
        call.enqueue(new Callback<ResultUser>() {
            @Override
            public void onResponse(Call<ResultUser> call, Response<ResultUser> response) {
                ResultUser result = response.body();
                if (result.getStatusCode() == 200) {
                    List<T_User> lstUser = result.getDtUser();
                    if (lstUser.size() > 0) {
                        IPC247.tendangnhap = lstUser.get(0).getUserName().toUpperCase();
                        IPC247.objUser = result.getDtUser().get(0);
                        TM_Toast.makeText(LoginActivity.this, "User (" + lstUser.get(0).getUserName() + ") đã đăng nhập thành công.", TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();

                        pref.edit().putBoolean("isLogin", true).commit();
                        pref.edit().putString("username", lstUser.get(0).getUserName()).commit();
                        pref.edit().putString("manv", lstUser.get(0).getMaNV()).commit();
                        pref.edit().putString("tennv", lstUser.get(0).getName()).commit();
                        pref.edit().putString("IdNhomQuyen",lstUser.get(0).getRoleGroup()).commit();

                        IPC247.strMaNV = lstUser.get(0).getMaNV();
                        IPC247.strTenNV = lstUser.get(0).getName();
                        IPC247.IdNhomQuyen=lstUser.get(0).getRoleGroup();
                        //Mở chương trình
                        Intent intent = new Intent(LoginActivity.this, Ipc247Activity.class);
                        startActivity(intent);
                        finish();
                    } else
                        TM_Toast.makeText(LoginActivity.this, "Tên đăng nhập hoặc mật khẩu không đúng.", TM_Toast.LENGTH_LONG, TM_Toast.WARNING, false).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Không tìm thấy dữ liệu.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultUser> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Call API fail.", Toast.LENGTH_SHORT).show();
            }
        });
    }


}