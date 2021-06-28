package com.lxkj.dmhw.adapter;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test.andlang.util.imageload.ImageLoadUtils;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.self.HomeZcBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ZhongCaoAdapter extends RecyclerView.Adapter<ZhongCaoAdapter.ViewHolder> {
    private Activity activity;
    private List<HomeZcBean> zcBeanList;

    public ZhongCaoAdapter(Activity activity, List<HomeZcBean> zcBeanList) {
        this.activity = activity;
        if (zcBeanList==null){
            zcBeanList=new ArrayList<>();
        }else{
            this.zcBeanList = zcBeanList;
        }
    }

    @NonNull
    @Override
    public ZhongCaoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(activity).inflate(R.layout.adapter_zhong_cao, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ZhongCaoAdapter.ViewHolder viewHolder, int i) {
        HomeZcBean homeZcBean=zcBeanList.get(i);
        ImageLoadUtils.doLoadImageUrl(viewHolder.ivImage,homeZcBean.getImgUrl());
        viewHolder.tvTitle.setText(homeZcBean.getName());
        viewHolder.tvSubTitle.setText(homeZcBean.getDescription());
        viewHolder.llContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转种草h5


            }
        });
    }

    @Override
    public int getItemCount() {
        return zcBeanList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_image)
        ImageView ivImage;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_sub_title)
        TextView tvSubTitle;
        @BindView(R.id.ll_content)
        LinearLayout llContent;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
