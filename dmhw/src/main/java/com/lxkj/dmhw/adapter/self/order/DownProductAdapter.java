package com.lxkj.dmhw.adapter.self.order;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.test.andlang.util.imageload.ImageLoadUtils;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.self.GoodBuyStateBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1 on 2016/2/26.
 * 首页主题场横向商品列表
 */
public class DownProductAdapter extends RecyclerView.Adapter<DownProductAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    private Activity activity;
    private List<GoodBuyStateBean> tradeGoodes;

    public DownProductAdapter(Activity activity, List<GoodBuyStateBean> tradeGoodes) {
        if(tradeGoodes!=null){
            this.tradeGoodes = tradeGoodes;
        }else{
            this.tradeGoodes = new ArrayList<GoodBuyStateBean>();
        }
        this.activity = activity;
        this.inflater = LayoutInflater.from(activity);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = inflater.inflate(R.layout.child_down_product,
                viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(view,activity);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        ImageLoadUtils.doLoadImageUrl(myViewHolder.ivProduct,tradeGoodes.get(i).getGoodsImgUrl());
    }

    @Override
    public int getItemCount() {
        return tradeGoodes.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProduct;
        public MyViewHolder(View view, Activity activity) {
            super(view);
            ivProduct = (ImageView) view.findViewById(R.id.iv_product);
        }
    }
}
