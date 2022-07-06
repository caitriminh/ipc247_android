package com.example.quyetthang.view.menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quyetthang.R;
import com.example.quyetthang.adapter.hethong.Adapter_Menu;
import com.example.quyetthang.api.ApiMenu;
import com.example.quyetthang.model.menu.ResultMenu;
import com.example.quyetthang.model.menu.T_Menu;
import com.example.quyetthang.system.ClickListener;
import com.example.quyetthang.system.IPC247;
import com.example.quyetthang.system.RecyclerTouchListener;
import com.example.quyetthang.view.bsc.BSCNhanVienActivity;
import com.example.quyetthang.view.bsc.BSCPhongBanActivity;
import com.example.quyetthang.view.bsc.BSCViTriCongViecActivity;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BSCFragment extends Fragment {

    ArrayList<T_Menu> lstMenu;
    ArrayList<T_Menu> lstMenu2;
    Adapter_Menu adapter;
    List<T_Menu> lstMenuTemp;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    Context mContext;

    private Unbinder unbinder;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        setHasOptionsMenu(true);

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GetMenu();
        recycleView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recycleView, new ClickListener() {

            @Override
            public void onClick(View view, int position) {

                IPC247.objMenu = lstMenuTemp.get(position);
                if (IPC247.objMenu.getMamenu().equals("BSC003")) {
                    Intent intent = new Intent(mContext, BSCNhanVienActivity.class);
                    startActivity(intent);

                } else if (IPC247.objMenu.getMamenu().equals("BSC002")) {
                    Intent intent = new Intent(mContext, BSCPhongBanActivity.class);
                    startActivity(intent);

                } else if (IPC247.objMenu.getMamenu().equals("BSC004")) {
                    Intent intent2 = new Intent(mContext, BSCViTriCongViecActivity.class);
                    startActivity(intent2);
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    private void GetMenu() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "GET_BY_MOBILE");
        jsonObject.addProperty("idNhomQuyen", IPC247.IdNhomQuyen);
        jsonObject.addProperty("idphanhe", 8);
        Call<ResultMenu> call = ApiMenu.apiMenu.Menu(jsonObject);
        call.enqueue(new Callback<ResultMenu>() {
            @Override
            public void onResponse(Call<ResultMenu> call, Response<ResultMenu> response) {
                ResultMenu result = response.body();
                if (result.getStatusCode() == 200) {
                    lstMenuTemp = result.getDtMenu();
                    if (lstMenuTemp.size() > 0) {
                        lstMenu = new ArrayList<T_Menu>();
                        lstMenu.addAll(lstMenu);

                        adapter = new Adapter_Menu(mContext, lstMenuTemp);
                        recycleView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                        recycleView.setAdapter(adapter);

                    }

                } else {
                    Toast.makeText(mContext, "Không tìm thấy dữ liệu.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResultMenu> call, Throwable t) {
                Toast.makeText(mContext, "Call API fail.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}