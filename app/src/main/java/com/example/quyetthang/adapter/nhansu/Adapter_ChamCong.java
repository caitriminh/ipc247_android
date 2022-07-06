package com.example.quyetthang.adapter.nhansu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.quyetthang.R;
import com.example.quyetthang.model.chamcong.T_NhatKyChamCong;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Adapter_ChamCong extends RecyclerView.Adapter<Adapter_ChamCong.RecyclerViewHolder> {
    private List<T_NhatKyChamCong> data = new ArrayList<>();
    private List<T_NhatKyChamCong> temp = new ArrayList<>();
    private Unbinder unbinder;
    private Context mContext;
    View view;

    public Adapter_ChamCong(Context mContext, List<T_NhatKyChamCong> data) {
        this.data = data;
        this.temp.addAll(data);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.item_row_cham_cong, parent, false);
        return new RecyclerViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        DecimalFormat format1 = new DecimalFormat("#,##0.00");
        Double dblCong100 = data.get(position).getCong100();
        holder.txtCong100.setText(format1.format(dblCong100));

        holder.txtNgayChamCong.setText(data.get(position).getNgayChamCongText());
        holder.txtGioVao.setText(data.get(position).getVao1());
        holder.txtGioRa.setText(data.get(position).getRa1());

        DecimalFormat format = new DecimalFormat("#,##0.00");
        Double dblTongCong = data.get(position).getTongCong();
        holder.txtTongNgayCong.setText(format.format(dblTongCong));

        DecimalFormat format2 = new DecimalFormat("#,##0.00");
        Double dblCongTangCa = data.get(position).getCong150()+ data.get(position).getCong200()+data.get(position).getCong300();
        if(dblCongTangCa==0){
            holder.txtCongTangCa.setText("");
        }
        else{
            holder.txtCongTangCa.setText(format2.format(dblCongTangCa));
        }

        if(data.get(position).getTinhTrang().equals("Mặc định")){
            holder.div_ChamCong.setBackgroundColor(mContext.getResources().getColor(R.color.color_chuamua));
        }
        else if(data.get(position).getTinhTrang().equals("Không quét giờ ra")) {
            holder.txtGioRa.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        }
        else{
            holder.txtCongTangCa.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
            holder.txtTongNgayCong.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtCong100)
        TextView txtCong100;

        @BindView(R.id.txtNgayChamCong)
        TextView txtNgayChamCong;

        @BindView(R.id.txtGioVao)
        TextView txtGioVao;

        @BindView(R.id.txtGioRa)
        TextView txtGioRa;

        @BindView(R.id.txtTongNgayCong)
        TextView txtTongNgayCong;

        @BindView(R.id.txtCongTangCa)
        TextView txtCongTangCa;

        @BindView(R.id.div_ChamCong)
        LinearLayout div_ChamCong;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }

    public void filter(String keys) {
        data.clear();
        String charText = keys.toLowerCase(Locale.getDefault());
        if (charText.length() == 0) {
            data.addAll(temp);
        } else {
            for (T_NhatKyChamCong nhatKyChamCong : temp) {
                if (String.valueOf(nhatKyChamCong.getMaNV()).toLowerCase(Locale.getDefault()).contains(charText)

                ) {
                    data.add(nhatKyChamCong);
                }
            }
        }
        notifyDataSetChanged();
    }


}