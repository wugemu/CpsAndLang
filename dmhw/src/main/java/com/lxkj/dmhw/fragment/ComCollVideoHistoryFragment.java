package com.lxkj.dmhw.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.LoginActivity;
import com.lxkj.dmhw.activity.VideoActivity300;
import com.lxkj.dmhw.adapter.ComCollHistoryAdapter;
import com.lxkj.dmhw.bean.ComCollArticle;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.MyLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 新手视频记录
 */

public class ComCollVideoHistoryFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener{
    @BindView(R.id.fragment_newhand_recycler)
    RecyclerView fragment_newhand_recycler;

    // 适配器
    private ComCollHistoryAdapter adapter;
    private View emptyView;
    public static ComCollVideoHistoryFragment getInstance() {
        ComCollVideoHistoryFragment fragment = new ComCollVideoHistoryFragment();
        return fragment;
    }

    @Override
    public void onData() {
    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newhand_history, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {
        emptyView=getLayoutInflater().inflate(R.layout.view_empty_history, null);
        // 设置Recycler
        fragment_newhand_recycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), false));
        adapter = new ComCollHistoryAdapter(getActivity(),"");
        fragment_newhand_recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        fragment_newhand_recycler.setNestedScrollingEnabled(false);
        // 设置预加载位置,倒数
        adapter.setPreLoadNumber(5);
        // 监听item点击事件
        adapter.setOnItemClickListener(this);
        // 监听加载更多事件
        adapter.setOnLoadMoreListener(this, fragment_newhand_recycler);
        // 取消第一次加载回调
        adapter.disableLoadMoreIfNotFullPage();
    }
    @Override
    public void onCustomized() {
        showDialog();
        httpPost();
    }

    @Override
    public void mainMessage(Message message) {

    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        if (message.what == LogicActions.ArticleContentRecordSuccess) {
            ArrayList<ComCollArticle> list = (ArrayList<ComCollArticle>) message.obj;
            if (list.size() > 0) {
                if (page > 1) {
                    adapter.addData(list);
                } else {
                    adapter.setNewData(list);
                }
                adapter.loadMoreComplete();
            }else{
                adapter.loadMoreEnd();
            }
            adapter.setEmptyView(emptyView);
        }
        dismissDialog();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ComCollArticle hotItem= (ComCollArticle) adapter.getData().get(position);
            if (!DateStorage.getLoginStatus()){
                Intent intent =new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }else {
                    //视频详情
                    Intent intent = new Intent(getActivity(), VideoActivity300.class);
                    intent.putExtra("id", hotItem.getId());
                    intent.putExtra("title", hotItem.getTitle());
                    startActivity(intent);
                }
            }

    @Override
    public void onLoadMoreRequested() {
        page++;
        httpPost();
    }

    //记录列表
    private void httpPost() {
        paramMap = new HashMap<>();
        paramMap.put("type", "1");
        paramMap.put("startindex", page + "");
        paramMap.put("pagesize", pageSize + "");
        NetworkRequest.getInstance().POST(handler, paramMap, "ArticleContentRecord", HttpCommon.ArticleContentRecord);
    }
}
