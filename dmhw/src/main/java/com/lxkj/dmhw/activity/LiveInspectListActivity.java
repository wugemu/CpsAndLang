package com.lxkj.dmhw.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.adapter.MainRoomAdapter;
import com.lxkj.dmhw.adapter.MainRoomListAdapter;
import com.lxkj.dmhw.bean.RoomList;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.defined.PtrClassicRefreshLayout;
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
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class LiveInspectListActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener, PtrHandler {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.live_inspect_recycler)
    RecyclerView live_inspect_recycler;
    @BindView(R.id.refresh_frame)
     PtrClassicRefreshLayout refresh_frame;

    // 适配器
    private MainRoomListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_inspect_list);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }

        // 设置Recycler
        live_inspect_recycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(this, false));
        adapter = new MainRoomListAdapter(this,1);
        live_inspect_recycler.setAdapter(adapter);
        // 设置预加载位置,倒数
        adapter.setPreLoadNumber(5);
        // 监听加载更多事件
        adapter.setOnLoadMoreListener(this, live_inspect_recycler);
        // 取消第一次加载回调
        adapter.disableLoadMoreIfNotFullPage();

        //下拉刷新设置
        ptrFrameLayout();

        showDialog();

        initData();



    }
    //初始加载
    private void initData(){
        page = 1;
        if (page ==1&&isfirst){
            isfirst=false;
            showDialog();
        }
        httpPost();
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
    protected void onResume() {
        super.onResume();
        if (loginRefreshFlag){
            loginRefreshFlag=false;
            initData();
        }
    }
    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        refresh_frame.refreshComplete();
        if (message.what == LogicActions.RoomListSuccess) {
            ArrayList<RoomList> tempList = (ArrayList<RoomList>) message.obj;
            if (tempList.size() > 0) {
                if (page > 1) {
                    adapter.addData(tempList);
                    adapter.notifyDataSetChanged();
                } else {
                    adapter.setNewData(tempList);
                    adapter.notifyDataSetChanged();
                }
                adapter.loadMoreComplete();
            }else{
                adapter.loadMoreEnd();
            }
        }
            dismissDialog();
    }

    private void httpPost() {
        paramMap = new HashMap<>();
        paramMap.put("userid", login.getUserid());
        paramMap.put("startindex", page + "");
        paramMap.put("pagesize", pageSize + "");
        NetworkRequest.getInstance().POST(handler, paramMap, "RoomList", HttpCommon.RoomList);
    }



    @Override
    public void onLoadMoreRequested() {
        page++;
        httpPost();
    }

    @OnClick({R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                isFinish();
                break;

        }
    }


    /**
     * 下拉刷新设置
     */
    private void ptrFrameLayout() {
        loadView.setTextColor(Color.parseColor("#ffffff"));
        refresh_frame.setLoadingMinTime(700);
        refresh_frame.setHeaderView(loadView);
        refresh_frame.addPtrUIHandler(loadView);
        refresh_frame.setPtrHandler(this);
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, live_inspect_recycler, header);
    }
    private boolean isfirst=true;
    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        page = 1;
        if (page ==1&&isfirst){
            isfirst=false;
            showDialog();
        }
        httpPost();
    }
}
