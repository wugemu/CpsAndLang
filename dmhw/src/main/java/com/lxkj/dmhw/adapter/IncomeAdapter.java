package com.lxkj.dmhw.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.IncomeData;
import com.lxkj.dmhw.utils.BBCUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.ViewHolder> {

    private Activity activity;
    private List<IncomeData> dataList;

    public IncomeAdapter(Activity activity, List<IncomeData> dataList) {
        this.activity = activity;
        if (dataList==null){
            this.dataList=new ArrayList<>();
        }else{
            this.dataList = dataList;
        }
    }

    @NonNull
    @Override
    public IncomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.adapter_income_data, viewGroup, false);
        return new IncomeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeAdapter.ViewHolder viewHolder, int i) {
        IncomeData data=dataList.get(i);
        viewHolder.tvIncomeCreateTime.setText("收益产生时间："+data.getDate());
        if (data.getStatus()==0){
            viewHolder.tvMoney.setText(""+ BBCUtil.getDoubleFormat2(data.getEstimatedAmount().doubleValue()));
            viewHolder.tvStatus.setText("待转入");
        }else if (data.getStatus()==2){
            viewHolder.tvStatus.setText("已转入");
            viewHolder.tvMoney.setText(""+ BBCUtil.getDoubleFormat2(data.getRealAmount().doubleValue()));
        }else {
            viewHolder.tvStatus.setText("已取消");
            viewHolder.tvMoney.setText(""+ BBCUtil.getDoubleFormat2(data.getRealAmount().doubleValue()));
        }
        viewHolder.tvOrderNo.setText(data.getTradeNo());

//        for (int j=0;i<data.getTradeNo().length;i++){
//            viewHolder.tvOrderNo.append(data.getTradeNo()[j]);
//            if (i<data.getTradeNo().length-1){
//                viewHolder.tvOrderNo.append("\n");
//            }
//        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_income_create_time)
        TextView tvIncomeCreateTime;
        @BindView(R.id.tv_order_no)
        TextView tvOrderNo;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.tv_money)
        TextView tvMoney;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
