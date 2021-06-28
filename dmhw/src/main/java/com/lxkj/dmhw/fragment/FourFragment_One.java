package com.lxkj.dmhw.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.MainActivity;
import com.lxkj.dmhw.adapter.FourFragmentAdapter340;
import com.lxkj.dmhw.bean.CommodityDetails290;
import com.lxkj.dmhw.defined.LazyFragment;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.MyLayoutManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 进两小时
 * Created by Android on 2018/6/9.
 */

public class FourFragment_One extends LazyFragment implements  PtrHandler, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.load_more_ptr_frame)
    PtrClassicFrameLayout loadMorePtrFrame;
    @BindView(R.id.return_top)
    ImageView returnTop;
    @BindView(R.id.fragment_four_one_list)
    RecyclerView fragmentFourOneList;
    private FourFragmentAdapter340 adapter;
    private boolean isCheck = true;
    private String time, name;
    private int currPos=0;
    public static FourFragment_One getInstance(int pos,String name) {
        FourFragment_One fragment = new FourFragment_One();
        Bundle bundle = new Bundle();
        bundle.putInt("currPos",pos);
        bundle.putString("name", name);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            currPos= bundle.getInt("currPos");
            name = bundle.getString("name");
        }
    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_four_one, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {
        ptrFrameLayout();
        // 初始化adapter
        fragmentFourOneList.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), false));
        adapter = new FourFragmentAdapter340(getActivity(),false);
        fragmentFourOneList.setAdapter(adapter);
        // 设置预加载位置,倒数
        adapter.setPreLoadNumber(5);
        // 监听加载更多事件
        adapter.setOnLoadMoreListener(this, fragmentFourOneList);
        // 取消第一次加载回调
        adapter.disableLoadMoreIfNotFullPage();




        fragmentFourOneList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = fragmentFourOneList.getLayoutManager();
                //判断是当前layoutManager是否为LinearLayoutManager
                // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    //获取最后一个可见view的位置
                    int lastItemPosition = linearManager.findLastVisibleItemPosition();
                    //获取第一个可见view的位置
                    int firstItemPosition = linearManager.findFirstVisibleItemPosition();
                    if (firstItemPosition >= 10) {
                        returnTop.setVisibility(View.VISIBLE);
                    } else {
                        returnTop.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    @Override
    protected void initData() {
        httpPost("");
    }
    @Override
    public void onCustomized() {

    }
    @Override
    public void onResume() {
        super.onResume();
        if (loginRefreshFlag&&currPos==FourFragment.currPos&&MainActivity.currentPos==1){
            loginRefreshFlag=false;
            page=1;
            httpPost("");
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
        dismissDialog();
        loadMorePtrFrame.refreshComplete();
        if (message.what == LogicActions.RankingListSuccess) {
            ArrayList<CommodityDetails290>  List = (ArrayList<CommodityDetails290>) message.obj;
            if (List.size() > 0) {
                if (page > 1) {
                    adapter.addData(List);
                } else {
                    adapter.setNewData(List);
                }
                adapter.loadMoreComplete();
            } else {
                adapter.loadMoreEnd();
            }
        }
    }
    boolean isfirst =true;
    private void httpPost(String time) {
        if (page ==1&&isfirst){
            isfirst=false;
            showDialog();
        }
        // 请求商品搜索（标签）
        paramMap.clear();
        paramMap.put("userid", login.getUserid());
        paramMap.put("cid" , name);
        paramMap.put("startindex", page + "");
        paramMap.put("pagesize", pageSize + "");
        NetworkRequest.getInstance().POST(handler, paramMap, "RankingList", HttpCommon.RankingList);

    }

    /**
     * 下拉刷新设置
     */
    private void ptrFrameLayout() {
        loadView.setTextColor(Color.WHITE);
        loadMorePtrFrame.setLoadingMinTime(700);
        loadMorePtrFrame.setHeaderView(loadView);
        loadMorePtrFrame.addPtrUIHandler(loadView);
        loadMorePtrFrame.setPtrHandler(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, fragmentFourOneList, header);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        page=1;
        httpPost("");
    }

    @OnClick(R.id.return_top)
    public void onViewClicked() {
        fragmentFourOneList.scrollToPosition(0);
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        httpPost("");
    }
}
