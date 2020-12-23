package com.lxkj.dmhw.adapter.self.order;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lxkj.dmhw.adapter.self.cart.OrderContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1 on 2017/4/18.
 * 订单详情商品列表
 */

public class OrderDetialAdapter extends BaseAdapter {

    private Context context;
    private List<OrderContent> orderContents;
    private LayoutInflater mIflater;
    private final int TYPE_COUNT = 3;
    private final int TYPE_TOP = 0;
    private final int TYPE_CONTENT = 1;
    private final int TYPE_SEPARATE = 2;
    public OrderDetialAdapter(Context context, List<OrderContent> orderContents) {
        this.context = context;
        if(orderContents!=null)
        {
            this.orderContents = orderContents;
        }
        else
        {
            this.orderContents  =new ArrayList<>();
        }
        mIflater= LayoutInflater.from(context);
    }
    @Override
    public int getItemViewType(int position) {
        if(orderContents.get(position)instanceof OrderDetialTop){
            return TYPE_TOP;
        }else if (orderContents.get(position) instanceof OrderDetialGoods){
            return TYPE_CONTENT;
        }else{
            return TYPE_SEPARATE;
        }
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

    public List<OrderContent> getOrderContents() {
        return orderContents;
    }

    @Override
    public int getCount() {
        return orderContents.size();
    }

    @Override
    public Object getItem(int position) {
        return orderContents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean isEnabled(int position) {
        // TODO Auto-generated method stub
        return orderContents.get(position).isClickable();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return orderContents.get(position).getView(context,convertView,mIflater);
    }

    public void upateList(List<OrderContent> orderContents) {
        // TODO Auto-generated method stub
            this.orderContents=orderContents;
            this.notifyDataSetChanged();
    }

    public void addList(List<OrderContent> orderContents){
        this.orderContents.addAll(orderContents);
        this.notifyDataSetChanged();
    }


    public void clearListView() {
        // TODO Auto-generated method stub
        this.orderContents.clear();
    }


}
