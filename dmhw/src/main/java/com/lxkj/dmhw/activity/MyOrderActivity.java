package com.lxkj.dmhw.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.bean.H5Link;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyOrderActivity extends BaseActivity {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.order_item_layout0)
    LinearLayout order_item_layout0;
    @BindView(R.id.order_item_layout1)
    LinearLayout order_item_layout1;
    @BindView(R.id.order_item_layout2)
    LinearLayout order_item_layout2;
    @BindView(R.id.order_item_layout3)
    LinearLayout order_item_layout3;
    @BindView(R.id.order_item_layout4)
    LinearLayout order_item_layout4;
    @BindView(R.id.order_item_layout5)
    LinearLayout order_item_layout5;
    @BindView(R.id.order_item_mt_layout)
    LinearLayout order_item_mt_layout;
    @BindView(R.id.order_item_sn_layout)
    LinearLayout order_item_sn_layout;
    @BindView(R.id.order_item_kl_layout)
    LinearLayout order_item_kl_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
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
        // 获取H5地址
        if (message.what == LogicActions.GetH5Success) {
            ArrayList<H5Link> h5Links = (ArrayList<H5Link>) message.obj;
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra(Variable.webUrl, h5Links.get(0).getUrl());
            intent.putExtra("isTitle", true);
            startActivity(intent);
            }
    }


    @OnClick({R.id.back,R.id.order_item_layout0,R.id.order_item_layout1,R.id.order_item_layout2,R.id.order_item_layout3,R.id.order_item_layout4,R.id.order_item_layout5,R.id.order_item_mt_layout,R.id.order_item_sn_layout,R.id.order_item_kl_layout})
    public void onViewClicked(View view) {
        Intent intent=null;
        switch (view.getId()) {
            case R.id.back:
                isFinish();
                break;
            case R.id.order_item_layout0:
                intent = new Intent(this, OrderActivity.class);
                intent.putExtra("platform",0);
                break;
            case R.id.order_item_layout1:
              //跳小程序
                Utils.launchLittleApp(this,Variable.selfOrderPath);
                break;
            case R.id.order_item_layout2:
                intent = new Intent(this, OrderActivity.class);
                intent.putExtra("platform",1);
                break;
            case R.id.order_item_layout3:
                intent = new Intent(this, OrderActivity.class);
                intent.putExtra("platform",2);
                break;
            case R.id.order_item_layout4:
                intent = new Intent(this, OrderActivity.class);
                intent.putExtra("platform",3);
                break;
            case R.id.order_item_mt_layout://美团
                intent = new Intent(this, OrderActivity.class);
                intent.putExtra("platform",4);
                break;
            case R.id.order_item_sn_layout://苏宁易购
                intent = new Intent(this, OrderActivity.class);
                intent.putExtra("platform",5);
                break;
            case R.id.order_item_kl_layout://考拉
                intent = new Intent(this, OrderActivity.class);
                intent.putExtra("platform",6);
                break;
            case R.id.order_item_layout5:
              //h5页面
                if (Variable.FastClickTime()){
                    return;
                }
                 paramMap.clear();
                 paramMap.put("type", "7");
                 NetworkRequest.getInstance().POST(handler, paramMap, "GetH5", HttpCommon.GetH5);
                break;
        }
        if (intent!=null){
            startActivity(intent);
        }
    }
}
