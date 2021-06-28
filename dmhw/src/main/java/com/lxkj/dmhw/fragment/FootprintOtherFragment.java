package com.lxkj.dmhw.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.CommodityActivityPJW;
import com.lxkj.dmhw.adapter.FootPrintOtherAdapter;
import com.lxkj.dmhw.bean.CommodityDetails290;
import com.lxkj.dmhw.bean.CpsType;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.MyLayoutManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 足迹 拼京唯等
 * Created by Android on 2020/09/21.
 */

public class FootprintOtherFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener{
    @BindView(R.id.footprint_recycler)
    RecyclerView footprintRecycler;
    // 适配器
    private FootPrintOtherAdapter adapter;
    private View emptyView;
    ArrayList<CommodityDetails290>footList;
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
        adapter = new FootPrintOtherAdapter(getActivity());
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
        if (message.what == LogicActions.CpsGoodsFootprintSuccess) {
            footList = (ArrayList<CommodityDetails290>) message.obj;
            if (footList.size() > 0) {
                if (page > 1) {
                    adapter.addData(footList);
                } else {
                    adapter.setNewData(footList);
                }
                adapter.loadMoreComplete();
            } else {
                adapter.loadMoreEnd();
            }
            adapter.setEmptyView(emptyView);
        }
        dismissDialog();
    }


    private void httpPost(String time) {
        if (page==1){
            showDialog();
        }
        paramMap.clear();
        paramMap.put("userid", login.getUserid());
        paramMap.put("page", page + "");
        paramMap.put("pagesize", pageSize + "");
        NetworkRequest.getInstance().GETNew(handler, paramMap, "CpsGoodsFootprint", HttpCommon.CpsGoodsFootprint);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        CommodityDetails290 collectionList = (CommodityDetails290) adapter.getData().get(position);
            Object CpsTypeObject = JSON.parseObject(collectionList.getCpsType(), CpsType.class);
            CpsType objtype=(CpsType) CpsTypeObject;
            switch (objtype.getCode()){
                case "pdd":
                    startActivity(new Intent(getActivity(), CommodityActivityPJW.class).putExtra("GoodsId", collectionList.getId()).putExtra("type", "pdd"));
                    break;
                case "jd":
                    startActivity(new Intent(getActivity(), CommodityActivityPJW.class).putExtra("GoodsId", collectionList.getId()).putExtra("type", "jd"));
                    break;
                case "wph":
                    startActivity(new Intent(getActivity(), CommodityActivityPJW.class).putExtra("GoodsId", collectionList.getId()).putExtra("type", "wph"));
                    break;
                case "sn":
                    startActivity(new Intent(getActivity(), CommodityActivityPJW.class).putExtra("GoodsId", collectionList.getId()).putExtra("type", "sn"));
                    break;
                case "kl":
                    startActivity(new Intent(getActivity(), CommodityActivityPJW.class).putExtra("GoodsId", collectionList.getId()).putExtra("type", "kl"));
                    break;
            }
    }
    @Override
    public void onLoadMoreRequested() {
        page++;
        httpPost("");
    }
}
