package com.example.quyetthang.view.giaoviec;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.quyetthang.R;
import com.example.quyetthang.adapter.giaoviec.Adapter_GiaoViec;
import com.example.quyetthang.api.ApiGiaoViec;
import com.example.quyetthang.model.giaoviec.ResultGiaoViec;
import com.example.quyetthang.model.giaoviec.T_GiaoViec;
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

public class GiaoViecFragment extends Fragment {

    private Unbinder unbinder;

    ArrayList<T_GiaoViec> lstGiaoViec;
    Adapter_GiaoViec adapter;

    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;

    Context mContext;
    String TuNgay, DenNgay;
    Integer timkiem = 1;
    String name = "a";

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
        View rootView = inflater.inflate(R.layout.fragment_giaoviec, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Date currentTime = Calendar.getInstance().getTime();
        DateFormat formatter1 = new SimpleDateFormat("yyyy-01-01");
        DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
        TuNgay = formatter1.format(currentTime);
        DenNgay = formatter2.format(currentTime);

        GetGiaoViec();
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(mContext, recycleView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                T_GiaoViec giaoViec = lstGiaoViec.get(position);
                GetChucNang(giaoViec, position);
            }
        }));
        //L??m m???i d??? li???u
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetGiaoViec();
            }
        });
    }

    private void GetGiaoViec() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "GET_BY_DATE");
        jsonObject.addProperty("fromDate", TuNgay);
        jsonObject.addProperty("toDate", DenNgay);
        jsonObject.addProperty("timKiem", timkiem);
        jsonObject.addProperty("userName", IPC247.tendangnhap);
        Call<ResultGiaoViec> call = ApiGiaoViec.apiGiaoViec.GetGiaoViec(jsonObject);
        call.enqueue(new Callback<ResultGiaoViec>() {
            @Override
            public void onResponse(Call<ResultGiaoViec> call, Response<ResultGiaoViec> response) {
                ResultGiaoViec result = response.body();
                if (result == null) {
                    Toast.makeText(mContext, "Kh??ng t??m th???y d??? li???u.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (result.getStatusCode() == 200) {
                    List<T_GiaoViec> lstGiaoViecTemp = result.getDtGiaoViec();
                    if (lstGiaoViecTemp.size() > 0) {
                        lstGiaoViec = new ArrayList<T_GiaoViec>();
                        lstGiaoViec.addAll(lstGiaoViecTemp);
                        adapter = new Adapter_GiaoViec(mContext, lstGiaoViec);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                        recycleView.setLayoutManager(layoutManager);
                        recycleView.setAdapter(adapter);
                        //L??m m???i d??? li???u
                        swiperefresh.setRefreshing(false);
                    }
                } else {
                    Toast.makeText(mContext, "Kh??ng t??m th???y d??? li???u.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultGiaoViec> call, Throwable t) {
                Toast.makeText(mContext, "Call API fail.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void HoanThanhGiaoViec(final T_GiaoViec giaoViec) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                .setTitle("X??c Nh???n")
                .setMessage("B???n c?? mu???n x??c nh???n kh??ng?")
                .setCancelable(false)
                .setPositiveButton("X??c Nh???n", R.drawable.ic_xacnhan_white, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {

                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("action", "HOANTAT");
                        jsonObject.addProperty("id", giaoViec.getId());
                        jsonObject.addProperty("userName", IPC247.tendangnhap);

                        Call<ResultGiaoViec> call = ApiGiaoViec.apiGiaoViec.HoanThanh(jsonObject);
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
                                        GetGiaoViec();
                                    }
                                } else {
                                    TM_Toast.makeText(mContext, "Kh??ng t??m th???y d??? li???u.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
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
                .setNegativeButton("????ng", R.drawable.ic_close_white, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();
        mBottomSheetDialog.show();
    }

    private void Xoa(final T_GiaoViec giaoViec) {
        BottomSheetMaterialDialog mBottomSheetDialog = new BottomSheetMaterialDialog.Builder((Activity) mContext)
                .setTitle("X??c Nh???n")
                .setMessage("B???n c?? mu???n x??a kh??ng?")
                .setCancelable(false)
                .setPositiveButton("X??c Nh???n", R.drawable.ic_xacnhan_white, new BottomSheetMaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {

                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("action", "DELETE");
                        jsonObject.addProperty("id", giaoViec.getId());
                        jsonObject.addProperty("userName", IPC247.tendangnhap);

                        Call<ResultGiaoViec> call = ApiGiaoViec.apiGiaoViec.HoanThanh(jsonObject);
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
                                        GetGiaoViec();
                                    }
                                } else {
                                    TM_Toast.makeText(mContext, "Kh??ng t??m th???y d??? li???u.", TM_Toast.LENGTH_SHORT, TM_Toast.ERROR, false).show();
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
                .setNegativeButton("????ng", R.drawable.ic_close_white, new BottomSheetMaterialDialog.OnClickListener() {
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
            case R.id.btnTimKiemTheoNgay:
                TimKiemTheoNgay();
                break;
            case R.id.btnThem:
                Intent sub = new Intent(mContext, ThemGiaoViecActivity.class);
                sub.putExtra("name", name);
                startActivityForResult(sub, 100);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void TimKiemTheoNgay() {
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        MaterialDatePicker picker = builder.setTheme(R.style.ThemeOverlay_MaterialComponents_MaterialCalendar).build();
        picker.show(this.getParentFragmentManager(), picker.toString());
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
                //T??m ki???m theo ng??y th??ng
                timkiem = 3;
                GetGiaoViec();
            }
        });
    }

    public void GetChucNang(T_GiaoViec giaoViec, int position) {
        final String[] chucnangs = {"Ho??n Th??nh", "T???m D???ng", "X??a", "S???a", "????nh gi??", "Tho??t"};
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Ch???c n??ng");
        builder.setCancelable(false);
        int i = 0;

        builder.setItems(chucnangs, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String chucnang = chucnangs[i];
                dialogInterface.dismiss(); // Close Dialog
                if (chucnang.equals("Ho??n Th??nh")) {
                    HoanThanhGiaoViec(giaoViec);
                } else if (chucnang.equals("X??a")) {
                    Xoa(giaoViec);
                } else if (chucnang.equals("Duy???t")) {
                    // GetPhanQuyenDuyet(nhanVienNghiPhep);
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.menu_search_item, menu);
        menuInflater.inflate(R.menu.menu_item_add, menu);
        menuInflater.inflate(R.menu.menu_item_date, menu);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.menuSearch).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setQueryHint("T??m ki???m...");
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
                try {
                    adapter.filter(newText);
                } catch (Exception e) {

                }
                return false;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 100) {
                GetGiaoViec();
            }
        }
    }
}