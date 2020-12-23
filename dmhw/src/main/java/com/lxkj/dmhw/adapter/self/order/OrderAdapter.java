package com.lxkj.dmhw.adapter.self.order;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lxkj.dmhw.adapter.self.cart.OrderContent;
import com.lxkj.dmhw.presenter.OrderPresenter;

import java.util.ArrayList;
import java.util.List;


public class OrderAdapter extends BaseAdapter {
    private Context context;
    private List<OrderContent> orderContents;
    private LayoutInflater mIflater;
    private OrderPresenter presenter;
    private int status;//1全部 2待付款 3待发货 4待收货 5已完成
    private int orderType=1;//1全部 2我的 3分享
    private final int TYPE_COUNT = 3;

    private final int TYPE_TOP = 0;
    private final int TYPE_CONTENT = 1;
    private final int TYPE_BOTTOM = 2;

    public OrderAdapter(Context context, List<OrderContent> orderContents,int status,int orderType,OrderPresenter presenter) {
        this.context = context;
        if(orderContents!=null)
        {
            this.orderContents = orderContents;
        }
        else
        {
            this.orderContents  =new ArrayList<OrderContent>();
        }
        mIflater=LayoutInflater.from(context);
        this.presenter=presenter;
        this.status=status;
        this.orderType=orderType;
    }

    @Override
    public int getItemViewType(int position) {
        if(orderContents.get(position)instanceof OrderItemTop){
            return TYPE_TOP;
        }else if(orderContents.get(position)instanceof OrderItemBottom){
            return TYPE_BOTTOM;
        }else{
            return TYPE_CONTENT;
        }
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
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
        if(position==getCount()-2&&presenter!=null&&presenter.haveMore){
            presenter.reqOrderList(status,orderType,true);
        }
        return orderContents.get(position).getView(context,convertView,mIflater);
    }

    public List<OrderContent> getOrderContents() {
        return orderContents;
    }


    public void setStatus(int status) {
        this.status = status;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }
}
