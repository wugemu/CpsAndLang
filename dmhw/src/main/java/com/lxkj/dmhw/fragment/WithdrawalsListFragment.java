package com.lxkj.dmhw.fragment;

import android.os.Bundle;
import android.os.Message;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.adapter.WithdrawListAdapter;
import com.lxkj.dmhw.adapter.WithdrawListTaoAdapter;
import com.lxkj.dmhw.bean.WithdrawalsList;
import com.lxkj.dmhw.bean.WithdrawalsListMorePl;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.MyLayoutManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WithdrawalsListFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.withdrawals_list)
    RecyclerView withdrawalsList;
    // 资金记录adapter
    private WithdrawListTaoAdapter adapter;
    private WithdrawListAdapter adaptermore;
    private int platform=0;
    public static WithdrawalsListFragment getInstance(int type) {
        WithdrawalsListFragment fragment = new WithdrawalsListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("platform", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            platform = bundle.getInt("platform");
        }
    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_withdrawals_list_fragment, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {
        withdrawalsList.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), false));
        if (platform==0){
            adapter = new WithdrawListTaoAdapter(getActivity());
            withdrawalsList.setAdapter(adapter);
        }else {
            adaptermore = new WithdrawListAdapter(getActivity());
            withdrawalsList.setAdapter(adaptermore);
            // 设置预加载位置,倒数
            adaptermore.setPreLoadNumber(5);
            // 监听加载更多事件
            adaptermore.setOnLoadMoreListener(this, withdrawalsList);
            // 取消第一次加载回调
            adaptermore.disableLoadMoreIfNotFullPage();
        }
        httpgetNew();
    }

    @Override
    public void onCustomized() {

    }

    private void  httpgetNew(){
        showDialog();
        if (platform==0){
            paramMap.clear();
            paramMap.put("userid", login.getUserid());
            NetworkRequest.getInstance().POST(handler, paramMap, "WithdrawalsStatus", HttpCommon.WithdrawalsStatus);
        }else {
            paramMap.clear();
            paramMap.put("userid", login.getUserid());
            paramMap.put("page", page+"");
            paramMap.put("pageSize", pageSize+"");
            paramMap.put("type", "");
            NetworkRequest.getInstance().GETNew(handler, paramMap, "WithdrawalsInfoList", HttpCommon.WithdrawalsInfoList);
        }
    }


    @Override
    public void mainMessage(Message message) {

    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        if (message.what == LogicActions.WithdrawalsStatusSuccess) {
                ArrayList<WithdrawalsList> list = (ArrayList<WithdrawalsList>) message.obj;
                adapter.setNewData(list);
                adapter.notifyDataSetChanged();
                dismissDialog();
            }
        if (message.what == LogicActions.WithdrawalsInfoListSuccess) {
            ArrayList<WithdrawalsListMorePl> list = (ArrayList<WithdrawalsListMorePl>) message.obj;
            if (list.size()>0){
             if (list.size()<pageSize){
                 if(page>1){
                  adaptermore.addData(list);
                 }else{
                  adaptermore.setNewData(list);
                 }
                 adaptermore.loadMoreEnd();
             }else{
                 if(page>1){
                     adaptermore.addData(list);
                 }else{
                     adaptermore.setNewData(list);
                 }
                 adaptermore.loadMoreComplete();
             }
            }else{
                adaptermore.loadMoreEnd();
            }
            dismissDialog();
        }
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        httpgetNew();
    }
}
