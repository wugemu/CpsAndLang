package com.lxkj.dmhw.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.adapter.PopularAdapter;
import com.lxkj.dmhw.bean.Popular;
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

public class PopularActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.popular_recycler)
    RecyclerView popularRecycler;
    private String time;
    private PopularAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular);
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
        popularRecycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(this, false));
        adapter = new PopularAdapter(this);
        popularRecycler.setAdapter(adapter);
        // 设置预加载位置,倒数
        adapter.setPreLoadNumber(5);
        // 监听加载更多事件
        adapter.setOnLoadMoreListener(this, popularRecycler);
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
        if (message.what == LogicActions.GetShopSuccess) {
            Popular popular = (Popular) message.obj;
            time = popular.getSearchtime();
            if (popular.getBurstingList().size() > 0) {
                if (page > 1) {
                    adapter.addData(popular.getBurstingList());
                    adapter.notifyDataSetChanged();
                } else {
                    adapter.setNewData(popular.getBurstingList());
                    adapter.notifyDataSetChanged();
                }
                adapter.loadMoreComplete();
            } else {

                adapter.loadMoreEnd();
            }
        }
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        isFinish();
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        httpPost(time);
    }

    private void httpPost(String time) {
        paramMap = new HashMap<>();
        paramMap.put("userid", login.getUserid());
        paramMap.put("startindex", page + "");
        paramMap.put("searchtime", time);
        paramMap.put("pagesize", pageSize + "");
        NetworkRequest.getInstance().POST(handler, paramMap, "GetShop", HttpCommon.GetShop);
        showDialog();
    }
}
