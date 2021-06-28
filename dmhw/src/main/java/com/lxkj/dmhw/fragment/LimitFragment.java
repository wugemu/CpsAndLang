package com.lxkj.dmhw.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxkj.dmhw.activity.CommodityActivity290;
import com.lxkj.dmhw.activity.LimitActivity;
import com.lxkj.dmhw.defined.LazyFragment;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.adapter.LimitAdapter;
import com.lxkj.dmhw.bean.Limit;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.MyLayoutManager;
import com.lxkj.dmhw.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 限时抢购
 * Created by Android on 2018/7/27.
 */

public class LimitFragment extends LazyFragment implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.fragment_limit_recycler)
    RecyclerView fragmentLimitRecycler;
    private int tips;
    private String limitTime="";
    private int  currPos=0;
    // 列表数据
    private Limit limit = new Limit();
    // 适配器
    private LimitAdapter adapter;

    public static LimitFragment getInstance(int pos,String limitTime) {
        LimitFragment fragment = new LimitFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("currPos", pos);
        bundle.putString("limitTime", limitTime);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            currPos=bundle.getInt("currPos");
            limitTime = bundle.getString("limitTime");
        }
    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_limit, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {
        // 设置Recycler
        fragmentLimitRecycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), false));
        if (Utils.getStringToDate(limitTime, "yyyy-MM-dd HH:mm") > Utils.getStringToDate(Utils.getCurrentDate("yyyy-MM-dd HH:mm"), "yyyy-MM-dd HH:mm")) {
            if (Utils.getStringToDate(limitTime, "yyyy-MM-dd HH:mm") >= Utils.getStringToDate(Utils.getNextDate("yyyy-MM-dd"), "yyyy-MM-dd")) {
                tips = 2;
            } else {
                tips = 1;
            }
        } else {
            tips = 0;
        }
        adapter = new LimitAdapter(getActivity(), tips);
        fragmentLimitRecycler.setAdapter(adapter);
        // 设置预加载位置,倒数
        adapter.setPreLoadNumber(5);
        // 监听item点击事件
        adapter.setOnItemClickListener(this);
        // 监听加载更多事件
        adapter.setOnLoadMoreListener(this, fragmentLimitRecycler);
        // 取消第一次加载回调
        adapter.disableLoadMoreIfNotFullPage();
    }

    @Override
    public void onCustomized() {
    }

    @Override
    public void onResume() {
        super.onResume();
        if (loginRefreshFlag&&LimitActivity.position ==currPos){
           loginRefreshFlag=false;
            refresh();
        }
    }
    //登录刷新标志
    private  boolean loginRefreshFlag=false;
    @Override
    public void mainMessage(Message message) {
        //登录之后刷新页面 该页面标记成可刷新
        if (message.what == LogicActions.LoginStatusSuccess&&(Boolean) message.obj) {
            loginRefreshFlag=true;
        }

    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        if (message.what == LogicActions.LimitListSuccess) {
            limit = (Limit) message.obj;
            if (limit.getShopdata().size() > 0) {
                if (page > 1) {
                    adapter.addData(limit.getShopdata());
                    adapter.notifyDataSetChanged();
                } else {
                    adapter.setNewData(limit.getShopdata());
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
        paramMap.put("couponstart", limitTime);
        paramMap.put("startindex", page + "");
        paramMap.put("searchtime", time);
        paramMap.put("pagesize", pageSize + "");
        NetworkRequest.getInstance().POST(handler, paramMap, "LimitList", HttpCommon.LimitList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (Objects.equals(((ArrayList<Limit.LimitData>) adapter.getData()).get(position).getProgress(), ((ArrayList<Limit.LimitData>) adapter.getData()).get(position).getCouponcount())) {
            toast("商品已抢光");
        } else {
            Intent intent = new Intent(getActivity(), CommodityActivity290.class);
            intent.putExtra("shopId", ((List<Limit.LimitData>) adapter.getData()).get(position).getShopid());
            intent.putExtra("source", "dmj_preview");
            intent.putExtra("sourceId", "");
            intent.putExtra("time", ((List<Limit.LimitData>) adapter.getData()).get(position).getCouponstart());
            getActivity().startActivity(intent);
        }
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        httpPost(limit.getSearchtime());
    }

    @Override
    protected void initData() {
        page = 1;
        httpPost("");
    }

    public void refresh() {
        page = 1;
        httpPost("");
    }
}
