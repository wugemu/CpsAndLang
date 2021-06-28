package com.lxkj.dmhw.fragment;

import android.os.Bundle;
import android.os.Message;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.adapter.FansAdapter;
import com.lxkj.dmhw.bean.FansInfo;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.MyLayoutManager;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的粉丝
 * Created by Android on 2018/5/16.
 */

public class FansFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.fragment_fans_recycler)
    RecyclerView fragmentFansRecycler;
    private FansAdapter adapter;
    private FansInfo fansInfo = new FansInfo();

    public static FansFragment getInstance() {
        FansFragment fragment = new FansFragment();
        return fragment;
    }

    @Override
    public void onData() {

    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fans, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {
        page = 1;
        // 设置Recycler
        fragmentFansRecycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), false));
        adapter = new FansAdapter(getActivity());
        fragmentFansRecycler.setAdapter(adapter);
        // 设置预加载位置,倒数
        adapter.setPreLoadNumber(5);
        // 监听加载更多事件
        adapter.setOnLoadMoreListener(this, fragmentFansRecycler);
        // 取消第一次加载回调
        adapter.disableLoadMoreIfNotFullPage();
    }

    @Override
    public void onCustomized() {
        httpPost(fansInfo.getSearchtime());
    }

    @Override
    public void mainMessage(Message message) {

    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        if (message.what == LogicActions.MyFansSuccess) {
            fansInfo = (FansInfo) message.obj;
            if (fansInfo.getUserdata().size() > 0) {
                if (page > 1) {
                    adapter.addData(fansInfo.getUserdata());
                    adapter.notifyDataSetChanged();
                } else {
                    adapter.setNewData(fansInfo.getUserdata());
                    adapter.notifyDataSetChanged();
                }
                adapter.loadMoreComplete();
            } else {
                adapter.loadMoreEnd();
            }
        }
    }

    private void httpPost(String time) {
        paramMap = new HashMap<>();
        paramMap.put("userid", login.getUserid());
        paramMap.put("startindex", page + "");
        paramMap.put("pagesize", pageSize + "");
        paramMap.put("searchtime", time);
        NetworkRequest.getInstance().POST(handler, paramMap, "MyFans", HttpCommon.MyFans);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        httpPost(fansInfo.getSearchtime());
    }
}
