package com.example.ipc247.view.nhanvien;

import static android.content.Context.MODE_PRIVATE;
import static com.example.ipc247.system.IPC247.getPublicIPAddress;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ipc247.R;
import com.example.ipc247.api.ApiChamCong;
import com.example.ipc247.api.ApiNhanVien;
import com.example.ipc247.model.chamcong.ResultChamCong;
import com.example.ipc247.model.chamcong.T_NhatKyChamCong;
import com.example.ipc247.model.nhanvien.CustomResult;
import com.example.ipc247.model.nhanvien.T_NhanVien;
import com.example.ipc247.system.FTP_CMD;
import com.example.ipc247.system.IPC247;
import com.example.ipc247.system.ImagePickerActivity;
import com.example.ipc247.system.TM_Toast;
import com.example.ipc247.view.hethong.LoginActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HoSo2Fragment extends Fragment {

    private Unbinder unbinder;
    @BindView(R.id.txtHoTen)
    TextInputEditText txtHoTen;

    @BindView(R.id.txtGioiTinh)
    TextInputEditText txtGioiTinh;

    @BindView(R.id.txtPhongBan)
    TextInputEditText txtPhongBan;

    @BindView(R.id.txtNgaySinh)
    TextInputEditText txtNgaySinh;

    @BindView(R.id.txtNgayVaoLam)
    TextInputEditText txtNgayVaoLam;

    @BindView(R.id.txtCheckIn)
    TextInputEditText txtCheckIn;

    @BindView(R.id.txtCheckOut)
    TextInputEditText txtCheckOut;

    @BindView(R.id.btnCheckIn)
    Button btnCheckIn;

    @BindView(R.id.btnCheckOut)
    Button btnCheckOut;

    @BindView(R.id.imgView)
    ImageView imgView;

    @BindView(R.id.txtTongNgayNghi)
    TextInputEditText txtTongNgayNghi;

    @BindView(R.id.txtTongNgayCong)
    TextInputEditText txtTongNgayCong;

    @BindView(R.id.txtPhepNam)
    TextInputEditText txtPhepNam;

//
    Context mContext;
    boolean wfh;
    String ip_chamcong;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mContext = getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hoso2, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GetNhanVien();

        btnCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ip_chamcong.equals(getPublicIPAddress())) {
                    BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder(getActivity())
                            .setTitle("Xác Nhận")
                            .setMessage("Bạn có muốn check in không?")
                            .setCancelable(false)
                            .setPositiveButton("Check In", R.drawable.ic_xacnhan_white, new BottomSheetMaterialDialog.OnClickListener() {
                                @Override
                                public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                                    CheckIn();
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
                } else {
                    //Làm việc tại nhà
                    if (wfh == false) {
                        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder(getActivity())
                                .setTitle("Xác Nhận")
                                .setMessage("Bạn có muốn check in không?")
                                .setCancelable(false)
                                .setPositiveButton("Check In", R.drawable.ic_xacnhan_white, new BottomSheetMaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                                        CheckIn();
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
                    } else {
                        TM_Toast.makeText(mContext, "IP chấm công không hợp lệ.", TM_Toast.LENGTH_SHORT, TM_Toast.WARNING, false).show();
                    }

                }
            }
        });

        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ip_chamcong.equals(getPublicIPAddress())) {
                    BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder(getActivity())
                            .setTitle("Xác Nhận")
                            .setMessage("Bạn có muốn check out không?")
                            .setCancelable(false)
                            .setPositiveButton("Check Out", R.drawable.ic_xacnhan_white, new BottomSheetMaterialDialog.OnClickListener() {
                                @Override
                                public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {

                                    CheckOut();
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
                } else {
                    //Làm việc tại nhà
                    if (wfh == false) {
                        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder(getActivity())
                                .setTitle("Xác Nhận")
                                .setMessage("Bạn có muốn check out không?")
                                .setCancelable(false)
                                .setPositiveButton("Check Out", R.drawable.ic_xacnhan_white, new BottomSheetMaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {

                                        CheckOut();
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
                    } else {
                        TM_Toast.makeText(mContext, "IP chấm công không hợp lệ.", TM_Toast.LENGTH_SHORT, TM_Toast.WARNING, false).show();
                    }
                }
            }
        });
    }

    private void GetNhanVien() {
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

                    String url = lstNhanVien.get(0).getHinh();
                    Picasso.get()
                            .load(url)
                            .error(R.drawable.logo_ipc247)//hien thi hinh mac dinh khi ko co hinh
                            .placeholder(R.drawable.logo_ipc247)
                            .into(imgView);

                    txtHoTen.setText(" " + lstNhanVien.get(0).getHoTen());
                    IPC247.strTenNV = lstNhanVien.get(0).getHoTen();
                    txtGioiTinh.setText(" " + lstNhanVien.get(0).getGioiTinh());
                    txtNgaySinh.setText(" " + lstNhanVien.get(0).getNgaySinhText());
                    txtNgayVaoLam.setText(" " + lstNhanVien.get(0).getNgayVaoLamText());
                    txtPhongBan.setText(" " + lstNhanVien.get(0).getPhongBan() + " / " + lstNhanVien.get(0).getChucVu());
                    txtCheckIn.setText(lstNhanVien.get(0).getCheckIn());
                    txtCheckOut.setText(lstNhanVien.get(0).getCheckOut());
                    if (txtCheckIn.getText().length() > 0) {
                        btnCheckIn.setEnabled(false);
                    } else {
                        btnCheckOut.setEnabled(false);
                    }
                    if (txtCheckOut.getText().length() > 0) {
                        btnCheckOut.setEnabled(false);
                        btnCheckIn.setEnabled(false);
                    }
                    DecimalFormat format = new DecimalFormat("#,##0.0");

                    Double dblTongCong = lstNhanVien.get(0).getTongCong();
                    txtTongNgayCong.setText(format.format(dblTongCong));

                    Double dblPhepNam = lstNhanVien.get(0).getPhepNam();
                    txtPhepNam.setText(format.format(dblPhepNam));

                    Double dblTongNgayNghi = lstNhanVien.get(0).getTongNgayNghi();
                    txtTongNgayNghi.setText(format.format(dblTongNgayNghi));

                            ip_chamcong = lstNhanVien.get(0).getiP_ChamCong();
                    wfh = lstNhanVien.get(0).getChamCongIP();

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

    private void CheckIn() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "CHECK_IN");
        jsonObject.addProperty("maNV", IPC247.strMaNV);
        jsonObject.addProperty("userName", IPC247.tendangnhap);
        jsonObject.addProperty("ghiChu", "IPC247 - " + getPublicIPAddress() + (" (Android)"));
        Call<ResultChamCong> call = ApiChamCong.apiChamCong.ChamCong(jsonObject);
        call.enqueue(new Callback<ResultChamCong>() {
            @Override
            public void onResponse(Call<ResultChamCong> call, Response<ResultChamCong> response) {
                ResultChamCong result = response.body();
                if (result.getStatusCode() == 200) {
                    List<T_NhatKyChamCong> lstChamCong = result.getDtChamCong();
                    if (lstChamCong.size() > 0) {
                        GetNhanVien();
                        btnCheckIn.setEnabled(false);
                        btnCheckOut.setEnabled(true);
                        TM_Toast.makeText(mContext, IPC247.tendangnhap + " đã check in lúc " + lstChamCong.get(0).getNgayChamCong(), TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                    }

                } else {
                    TM_Toast.makeText(mContext, "Không tìm thấy dữ liệu.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
                }
            }

            @Override
            public void onFailure(Call<ResultChamCong> call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
            }
        });
    }

    private void CheckOut() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "CHECK_IN");
        jsonObject.addProperty("maNV", IPC247.strMaNV);
        jsonObject.addProperty("userName", IPC247.tendangnhap);
        jsonObject.addProperty("ghiChu", "IPC247 - " + getPublicIPAddress() + (" (Android)"));
        Call<ResultChamCong> call = ApiChamCong.apiChamCong.ChamCong(jsonObject);
        call.enqueue(new Callback<ResultChamCong>() {
            @Override
            public void onResponse(Call<ResultChamCong> call, Response<ResultChamCong> response) {
                ResultChamCong result = response.body();
                if (result.getStatusCode() == 200) {
                    List<T_NhatKyChamCong> lstChamCong = result.getDtChamCong();
                    if (lstChamCong.size() > 0) {
                        if (lstChamCong.get(0).getResult() == 1) {
                            GetNhanVien();
                            btnCheckOut.setEnabled(false);
                            TM_Toast.makeText(mContext, IPC247.tendangnhap + " đã check out lúc " + lstChamCong.get(0).getNgayChamCong(), TM_Toast.LENGTH_SHORT, TM_Toast.SUCCESS, false).show();
                        } else {
                            TM_Toast.makeText(mContext, lstChamCong.get(0).getMessage(), TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
                        }
                    }

                } else {
                    TM_Toast.makeText(mContext, "Không tìm thấy dữ liệu.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
                }
            }

            @Override
            public void onFailure(Call<ResultChamCong> call, Throwable t) {
                TM_Toast.makeText(mContext, "Call API fail.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
            }
        });
    }

    private void Logout() {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder(getActivity())
                .setTitle("Xác Nhận")
                .setMessage("Bạn có muốn thoát khỏi phần mềm không?")
                .setCancelable(false)
                .setPositiveButton("Xác Nhận", R.drawable.ic_xacnhan_white, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {

                        SharedPreferences pref = getActivity().getSharedPreferences("SESSION", MODE_PRIVATE);
                        pref.edit().clear().commit();
                        Intent intent_loyout = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent_loyout);

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
            case R.id.btnLogout:
                Logout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.menu_item_logout, menu);
    }
}