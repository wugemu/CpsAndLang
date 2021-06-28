package com.lxkj.dmhw.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test.andlang.util.ActivityUtil;
import com.example.test.andlang.util.DateUtil;
import com.example.test.andlang.util.imageload.ImageLoadUtils;
import com.lxkj.dmhw.MyApplication;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.self.ProductInfoActivity;
import com.lxkj.dmhw.bean.self.GoodBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopRankAdapter extends RecyclerView.Adapter {
    public static final int FOOT_TYPE = 1000;
    public static final int GOODS_LEFT = 1001;//商品图在左边
    public static final int GOODS_RIGHT = 1002;//商品图在右边
    private List<GoodBean> goodsList;
    private Activity activity;
    private Handler handler;

    public TopRankAdapter(Activity activity, List<GoodBean> goodsList, Handler handler) {
        if (goodsList==null){
            this.goodsList=new ArrayList<>();
        }else{
            this.goodsList=goodsList;
        }
        this.activity = activity;
        this.handler = handler;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {//最后一个是回到顶部
            return FOOT_TYPE;
        } else if (position % 2 == 1) {
            return GOODS_RIGHT;
        } else {
            return GOODS_LEFT;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        if (getItemViewType(position) == GOODS_LEFT) {
            View view = LayoutInflater.from(activity).inflate(R.layout.adapter_top_rank_left, viewGroup, false);
            return new LeftViewHolder(view);
        } else if (getItemViewType(position) == GOODS_RIGHT) {
            View view = LayoutInflater.from(activity).inflate(R.layout.adapter_top_rank_right, viewGroup, false);
            return new RightViewHolder(view);
        } else {
            View view = LayoutInflater.from(activity).inflate(R.layout.foot_top_rank, viewGroup, false);
            return new FootViewHolder(view);
        }
    }

    private void goGoodsDetial(GoodBean goodBean){
        Intent intent=new Intent(activity, ProductInfoActivity.class);
        intent.putExtra("goodsId",goodBean.getGoodsId());
        ActivityUtil.getInstance().start(activity,intent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == GOODS_LEFT) {
            GoodBean goodBean=goodsList.get(position);
            LeftViewHolder holder= (LeftViewHolder) viewHolder;
            holder.tvGoodsName.setText(goodBean.getGoodsName());
            holder.tvGoodPrice.setText(""+goodBean.getAppPrice());
            holder.tvGoodsDes.setText(goodBean.getDescription());
            ImageLoadUtils.doLoadImageUrl(holder.ivProductImg,goodBean.getImgUrl());
            holder.btnBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goGoodsDetial(goodBean);
                }
            });
        } else if (getItemViewType(position) == GOODS_RIGHT) {
            GoodBean goodBean=goodsList.get(position);
            RightViewHolder holder= (RightViewHolder) viewHolder;
            holder.tvGoodsName.setText(goodBean.getGoodsName());
            holder.tvGoodPrice.setText(""+goodBean.getAppPrice());
            holder.tvGoodsDes.setText(goodBean.getDescription());
            ImageLoadUtils.doLoadImageUrl(holder.ivProductImg,goodBean.getImgUrl());
            holder.btnBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goGoodsDetial(goodBean);
                }
            });
        } else {
          FootViewHolder holder= (FootViewHolder) viewHolder;
            holder.tvFootInfo.setText("数据截止"+ DateUtil.getDateTime(new Date(MyApplication.NOW_TIME*1000),"MM月dd号") +"，按商品销量/浏览/加购指标综合计算。");
            holder.btnBackTop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    handler.sendEmptyMessage(1);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return goodsList.size() + 1;
    }

    static class LeftViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_product_img)
        ImageView ivProductImg;
        @BindView(R.id.tv_goods_name)
        TextView tvGoodsName;
        @BindView(R.id.tv_goods_des)
        TextView tvGoodsDes;
        @BindView(R.id.tv_good_price)
        TextView tvGoodPrice;
        @BindView(R.id.btn_buy)
        TextView btnBuy;

        LeftViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class RightViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_goods_name)
        TextView tvGoodsName;
        @BindView(R.id.tv_goods_des)
        TextView tvGoodsDes;
        @BindView(R.id.tv_good_price)
        TextView tvGoodPrice;
        @BindView(R.id.btn_buy)
        TextView btnBuy;
        @BindView(R.id.iv_product_img)
        ImageView ivProductImg;

        RightViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class FootViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_foot_info)
        TextView tvFootInfo;
        @BindView(R.id.btn_back_top)
        LinearLayout btnBackTop;

        FootViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
