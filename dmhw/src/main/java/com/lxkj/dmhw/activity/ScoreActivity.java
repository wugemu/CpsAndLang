package com.lxkj.dmhw.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.bean.DMBDetail;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.adapter.ScoreAdapter;
import com.lxkj.dmhw.bean.ScoreOverview;
import com.lxkj.dmhw.dialog.ScoreDialog;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.InternalMessage;
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

public class ScoreActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener{

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.score_recycler)
    RecyclerView scoreRecycler;
    private ScoreAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        if (getIntent().getStringExtra("positionname").equals("dmb")){
            title.setText("NN币明细");
        }else{
            title.setText("资金明细");
        }

        // 设置Recycler
        scoreRecycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(this, false));
        adapter = new ScoreAdapter(getIntent().getStringExtra("positionname"));
        scoreRecycler.setAdapter(adapter);
        // 监听item点击事件
        adapter.setOnItemClickListener(this);
        // 设置预加载位置,倒数
        adapter.setPreLoadNumber(5);
        // 监听item点击事件
        adapter.setOnItemClickListener(this);
        // 监听加载更多事件
        adapter.setOnLoadMoreListener(this, scoreRecycler);
        // 取消第一次加载回调
        adapter.disableLoadMoreIfNotFullPage();

        httpPost();
    }

    private void httpPost(){
        if (page==1){
            showDialog();
        }
        paramMap = new HashMap<>();
        paramMap.put("userid", login.getUserid());
        paramMap.put("startindex",page+"");
        paramMap.put("pagesize", pageSize+"");
        if (getIntent().getStringExtra("positionname").equals("dmb"))
        NetworkRequest.getInstance().POST(handler, paramMap, "ScorereCord", HttpCommon.ScorereCord);
        else
        NetworkRequest.getInstance().POST(handler, paramMap, "ScorereCord", HttpCommon.GetScoreaccount);
    }

    @Override
    public void mainMessage(Message message) {

    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        if (message.what == LogicActions.ScorereCordSuccess) {
            ArrayList<DMBDetail> list= (ArrayList<DMBDetail>) message.obj;
            if (list!=null&&list.size()>0){
                if (page==1){
                    adapter.setNewData(list);
                }else{
                   adapter.addData(list);
                }
                adapter.loadMoreComplete();
            }else {
                adapter.loadMoreEnd();
            }

        }
        dismissDialog();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
    }

    @OnClick({R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                isFinish();
                break;
        }
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        httpPost();
    }
}
