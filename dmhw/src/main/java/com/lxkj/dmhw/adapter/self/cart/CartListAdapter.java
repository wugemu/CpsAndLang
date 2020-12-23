package com.lxkj.dmhw.adapter.self.cart;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1 on 2017/4/18.
 * 购物车列表
 */

public class CartListAdapter extends BaseAdapter {

    private Context context;
    private List<OrderContent> orderContents;
    private LayoutInflater mIflater;
    private final int TYPE_COUNT = 5;
    private final int TYPE_TOP = 0;
    private final int TYPE_CONTENT = 1;
    private final int TYPE_INVALID_TOP = 2;
    private final int TYPE_RESULT_SEPARATE = 3;
    private final int TYPE_GOODS_SEPARATE = 4;
    private Handler handler;
    public CartListAdapter(Context context, List<OrderContent> orderContents,Handler handler) {
        this.context = context;
        this.handler=handler;
        if(orderContents!=null)
        {
            this.orderContents = orderContents;
        }
        else
        {
            this.orderContents  =new ArrayList<OrderContent>();
        }
        mIflater= LayoutInflater.from(context);
    }
    @Override
    public int getItemViewType(int position) {
        if(orderContents.get(position)instanceof CartItemTop){
            return TYPE_TOP;
        }else if(orderContents.get(position)instanceof CartItemGoods){
            return TYPE_CONTENT;
        }else if(orderContents.get(position)instanceof CartInvalidTop){
            return TYPE_INVALID_TOP;
        }else if(orderContents.get(position)instanceof CartResultSeparate){
            return TYPE_RESULT_SEPARATE;
        }else {
            return TYPE_GOODS_SEPARATE;
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
        if(orderContents!=null&&position<orderContents.size()){
            return orderContents.get(position).isClickable();
        }else {
            return false;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (handler!=null&&getCount()-2==position){
            handler.sendEmptyMessage(10);
        }
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
    public CartItemGoods getLastContent(){
        if (orderContents.size()>0){
            if (orderContents.get(orderContents.size()-1) instanceof CartItemGoods){
                return (CartItemGoods) orderContents.get(orderContents.size()-1);
            }
        }
        return null;
    }

    public void clearListView() {
        // TODO Auto-generated method stub
        this.orderContents.clear();
    }


}
