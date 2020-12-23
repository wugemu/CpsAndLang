package com.lxkj.dmhw.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.adapter.NewsDetailsAdapter;
import com.lxkj.dmhw.bean.NewsDetails;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.InternalMessage;
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
 * 我的资产
 */
public class AssetsActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.assets_recycler)
    RecyclerView assetsRecycler;
    private String time;
    private NewsDetailsAdapter adapter;
    private LinearLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assets);
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
        manager = MyLayoutManager.getInstance().LayoutManager(this, false);
        assetsRecycler.setLayoutManager(manager);
        adapter = new NewsDetailsAdapter(this);
        assetsRecycler.setAdapter(adapter);
        // 设置预加载位置,倒数
        adapter.setPreLoadNumber(5);
        // 监听加载更多事件
        adapter.setOnLoadMoreListener(this, assetsRecycler);
        // 取消第一次加载回调
        adapter.disableLoadMoreIfNotFullPage();
        httpPost("");
    }

    @Override
    public void mainMessage(Message message) {

    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        dismissDialog();
        if (message.what == LogicActions.GetNewsDetailsSuccess) {
            NewsDetails details = (NewsDetails) message.obj;
            time = details.getSearchtime();
            if (details.getMessagedata().size() > 0) {
                if (page > 1) {
                    adapter.addData(details.getMessagedata());
                    adapter.notifyDataSetChanged();
                } else {
                    adapter.setNewData(details.getMessagedata());
                    adapter.notifyDataSetChanged();
                }
                adapter.loadMoreComplete();
                manager.scrollToPositionWithOffset(0, 0);
                manager.setStackFromEnd(true);
            } else {

                adapter.loadMoreEnd();
            }
            paramMap = new HashMap<>();
            paramMap.put("userid", login.getUserid());
            paramMap.put("messagetype", "1");
            NetworkRequest.getInstance().POST(handler, paramMap, "SetNewsDetails", HttpCommon.SetNewsDetails);
        }
        if (message.what == LogicActions.SetNewsDetailsSuccess) {
            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("UpdateNewsDetails"), false, 0);
        }
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        httpPost(time);
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        isFinish();
    }

    private void httpPost(String time) {
        paramMap = new HashMap<>();
        paramMap.put("userid", login.getUserid());
        paramMap.put("messagetype", "1");
        paramMap.put("startindex", page + "");
        paramMap.put("searchtime", time);
        paramMap.put("pagesize", pageSize + "");
        NetworkRequest.getInstance().POST(handler, paramMap, "GetNewsDetails", HttpCommon.GetNewsDetails);
        showDialog();
    }
}
