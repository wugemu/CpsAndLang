package com.lxkj.dmhw.adapter;

import android.graphics.Paint;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.CommodityList;
import com.lxkj.dmhw.bean.UserInfo;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.utils.Utils;

import java.util.ArrayList;
import java.util.Objects;


/**
 * 首页精选适配器
 */

public class MyOtherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    public static final int TYPE_OTHERHEADER = 2;
    TextPaint tp;
    private ArrayList<CommodityList.CommodityData> mDatas = new ArrayList<>();

    private View mHeaderView;

    private View mOtherHeaderView;

    private FragmentActivity context;
    private OnItemClickListener mListener;

    public MyOtherAdapter(FragmentActivity activity) {
        this.context = activity;
    }

    public void setOnItemClickListener(OnItemClickListener li) {
        mListener = li;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }
    public void setHeaderOtherView(View headerView) {
        mOtherHeaderView = headerView;
        notifyItemInserted(1);
    }

    public void addDatas(ArrayList<CommodityList.CommodityData> datas, int type) {
        if (type==0){
         mDatas=datas;
        }else{
         mDatas.addAll(datas);
        }
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        if(mHeaderView == null) return TYPE_NORMAL;
        if(mOtherHeaderView==null) return TYPE_NORMAL;
        if(position == 0) return TYPE_HEADER;
        if(position == 1) return TYPE_OTHERHEADER;
        return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mHeaderView != null && viewType == TYPE_HEADER) return new Holder(mHeaderView);
        if (mOtherHeaderView != null && viewType == TYPE_OTHERHEADER) return new Holder(mOtherHeaderView);
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_list_shop_linear_raudis, parent, false);
        return new Holder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(getItemViewType(position) == TYPE_HEADER||getItemViewType(position) == TYPE_OTHERHEADER) return;
        final int pos = getRealPosition(viewHolder);
        CommodityList.CommodityData item = mDatas.get(pos);
        if(viewHolder instanceof Holder) {
            tp=((Holder) viewHolder).adapter_new_one_fragment_discount.getPaint();
            tp.setFakeBoldText(true);
            tp=((Holder) viewHolder).adapter_new_one_fragment_estimate_text.getPaint();
            tp.setFakeBoldText(true);
            Utils.displayImageRounded(context, item.getShopmainpic(),((Holder) viewHolder).adapter_new_one_fragment_image, 5);
            ((Holder) viewHolder).adapter_new_one_fragment_title.setText(item.getTitle());
            ((Holder) viewHolder).adapter_new_one_fragment_discount.setText(item.getDiscount()+"元");
            ((Holder) viewHolder).adapter_new_one_fragment_number.setText("已售" +  Utils.getNumber(item.getShopmonthlysales()));
            ((Holder) viewHolder).adapter_new_one_fragment_estimate_text.setText("奖 ¥"+item.getPrecommission());
            tp=((Holder) viewHolder).adapter_new_one_fragment_price.getPaint();
            tp.setFakeBoldText(true);
            tp=((Holder) viewHolder).rmb.getPaint();
            tp.setFakeBoldText(true);
            ((Holder) viewHolder).adapter_new_one_fragment_price.setText(item.getMoney());
            ((Holder) viewHolder).adapter_new_one_fragment_shop.setText(item.getSellername());
            ((Holder) viewHolder).adapter_new_one_fragment_original_price.setText("原价¥" +item.getShopprice());
            UserInfo login = DateStorage.getInformation();
            if (DateStorage.getLoginStatus()) {
                if (Objects.equals(login.getUsertype(), "3")) {
                    ((Holder) viewHolder).adapter_new_one_fragment_estimate.setVisibility(View.GONE);
                } else {
                    ((Holder) viewHolder).adapter_new_one_fragment_estimate.setVisibility(View.VISIBLE);
                }
            } else {
                ((Holder) viewHolder).adapter_new_one_fragment_estimate.setVisibility(View.GONE);
            }
            if (item.isCheck()) {
                ((Holder) viewHolder).adapter_new_one_fragment_check.setImageResource(R.mipmap.shop_list_tmall);
            } else {
                ((Holder) viewHolder).adapter_new_one_fragment_check.setImageResource(R.mipmap.shop_list_taobao);
            }
            if(mListener == null) return;
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(pos, item);
                }
            });
        }
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();

        return mHeaderView == null ? position : position - 2;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? mDatas.size() : mDatas.size() +2;
    }

    class Holder extends RecyclerView.ViewHolder {
        ImageView adapter_new_one_fragment_image;
        TextView adapter_new_one_fragment_title;
        TextView adapter_new_one_fragment_discount;
        TextView adapter_new_one_fragment_number;
        TextView adapter_new_one_fragment_estimate_text;
        TextView adapter_new_one_fragment_price;
        TextView rmb;
        TextView adapter_new_one_fragment_shop;
        TextView adapter_new_one_fragment_original_price;
        LinearLayout adapter_new_one_fragment_estimate;
        ImageView adapter_new_one_fragment_check;
        public Holder(View itemView) {
            super(itemView);
            if(itemView == mHeaderView) return;
            adapter_new_one_fragment_image =  itemView.findViewById(R.id.adapter_new_one_fragment_image);
            adapter_new_one_fragment_title =  itemView.findViewById(R.id.adapter_new_one_fragment_title);
            adapter_new_one_fragment_discount =  itemView.findViewById(R.id.adapter_new_one_fragment_discount);
            adapter_new_one_fragment_number =  itemView.findViewById(R.id.adapter_new_one_fragment_number);
            adapter_new_one_fragment_estimate_text =  itemView.findViewById(R.id.adapter_new_one_fragment_estimate_text);
            adapter_new_one_fragment_price =  itemView.findViewById(R.id.adapter_new_one_fragment_price);
            rmb =  itemView.findViewById(R.id.rmb);
            adapter_new_one_fragment_shop =  itemView.findViewById(R.id.adapter_new_one_fragment_shop);
            adapter_new_one_fragment_original_price =  itemView.findViewById(R.id.adapter_new_one_fragment_original_price);
            adapter_new_one_fragment_estimate =  itemView.findViewById(R.id.adapter_new_one_fragment_estimate);
            adapter_new_one_fragment_check =  itemView.findViewById(R.id.adapter_new_one_fragment_check);
        }
    }
   public  interface OnItemClickListener {
        void onItemClick(int position, CommodityList.CommodityData data);
    }
}
