package com.lxkj.dmhw.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.activity.CommodityActivity290;
import com.lxkj.dmhw.adapter.FootPrintListAdapter;
import com.lxkj.dmhw.bean.CommodityList;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.MyLayoutManager;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 足迹 淘宝
 * Created by Android on 2020/09/21.
 */

public class FootprintTaobaoFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener{
    @BindView(R.id.footprint_recycler)
    RecyclerView footprintRecycler;
    // 适配器
    private FootPrintListAdapter adapter;
    private String time;
    private View emptyView;


    @Override
    public void onData() {

    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_footprint, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {
        emptyView=getLayoutInflater().inflate(R.layout.view_empty, null);
        ImageView image=emptyView.findViewById(R.id.empty_img);
        TextView textView1=emptyView.findViewById(R.id.empty_txt);
        TextView textView2=emptyView.findViewById(R.id.empty_txt2);
        image.setImageResource(R.mipmap.no_foot);
        textView1.setText(getString(R.string.foot_empty_txt1));
        textView2.setText(getString(R.string.foot_empty_txt2));

        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        footprintRecycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), false));
        adapter = new FootPrintListAdapter(getActivity());
        footprintRecycler.setAdapter(adapter);
        // 设置预加载位置,倒数
        adapter.setPreLoadNumber(5);
        // 监听item点击事件
        adapter.setOnItemClickListener(this);
        // 监听加载更多事件
        adapter.setOnLoadMoreListener(this, footprintRecycler);
        // 取消第一次加载回调
        adapter.disableLoadMoreIfNotFullPage();

        httpPost("");
    }

    @Override
    public void onCustomized() {

    }

    @Override
    public void mainMessage(Message message) {
    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        if (message.what == LogicActions.GetFootprintSuccess) {
            CommodityList commodityList = (CommodityList) message.obj;
            time = commodityList.getSearchtime();
            if (commodityList.getShopdata().size() > 0) {
                if (page > 1) {
                    adapter.addData(commodityList.getShopdata());
                    adapter.notifyDataSetChanged();
                } else {
                    adapter.setNewData(commodityList.getShopdata());
                    adapter.notifyDataSetChanged();
                }
                adapter.loadMoreComplete();
            } else {

                adapter.loadMoreEnd();
            }
        }
        adapter.setEmptyView(emptyView);
        dismissDialog();
    }


    private void httpPost(String time) {
        if (page==1){
            showDialog();
        }
        paramMap = new HashMap<>();
        paramMap.put("userid", login.getUserid());
        paramMap.put("startindex", page + "");
        paramMap.put("searchtime", time);
        paramMap.put("pagesize", pageSize + "");
        NetworkRequest.getInstance().POST(handler, paramMap, "GetFootprint", HttpCommon.GetFootprint);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        CommodityList.CommodityData data = (CommodityList.CommodityData) adapter.getData().get(position);
        startActivity(new Intent(getActivity(), CommodityActivity290.class).putExtra("shopId", data.getShopid()).putExtra("source", "all").putExtra("sourceId",""));
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        httpPost(time);
    }
}
