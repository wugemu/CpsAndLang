package com.lxkj.dmhw.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.activity.AccountDeleteActivity;
import com.lxkj.dmhw.activity.WebViewActivity;
import com.lxkj.dmhw.adapter.AccountDelAdapter;
import com.lxkj.dmhw.adapter.CollectionAdapter;
import com.lxkj.dmhw.bean.AccountDel;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.defined.SmoothCheckBox;
import com.lxkj.dmhw.dialog.ApplyDialog;
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

/**
 * 账户注销第一页
 */

public class AccountDelOne extends BaseFragment {

    @BindView(R.id.content01)
    TextView content01;
    @BindView(R.id.button_title)
    TextView button_title;
    @BindView(R.id.checkbox)
    SmoothCheckBox checkbox;
    @BindView(R.id.next01)
    LinearLayout next01;

    @BindView(R.id.data_recycler)
    RecyclerView data_recycler;
    private AccountDelAdapter accountDelAdapter;

    public static AccountDel accountDel;

    private Activity activity;

    public static AccountDelOne getInstance() {
        AccountDelOne fragment = new AccountDelOne();
        return fragment;
    }

    @Override
    public void onData() {
    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.accout_del_one, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {
        next01.setEnabled(false);
        data_recycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), false));
        accountDelAdapter = new AccountDelAdapter();
        data_recycler.setAdapter(accountDelAdapter);
        showDialog();
        paramMap.clear();
        NetworkRequest.getInstance().POST(handler, paramMap, "UserCancelInit", HttpCommon.UserCancelInit);
    }

    @Override
    public void onCustomized() {
      checkbox.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
            if (isChecked){
                next01.setEnabled(true);
                next01.setBackgroundResource(R.drawable.accountdel_btn_style);
                button_title.setTextColor(Color.WHITE);
            }else{
                next01.setEnabled(false);
                next01.setBackgroundResource(R.drawable.account_delete_one_bg);
                button_title.setTextColor(Color.parseColor("#999999"));
            }
          }
      });
    }

    @Override
    public void mainMessage(Message message) {
    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        if (message.what == LogicActions.UserCancelInitSuccess) {
            accountDel=(AccountDel) message.obj;
            content01.setText(accountDel.getAftermath());
            accountDelAdapter.setNewData(accountDel.getLoseList());
            dismissDialog();
        }
    }

    @OnClick({R.id.next01,R.id.account_del_agreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.next01:
             //切换下一步
             AccountDeleteActivity accountDeleteActivity= (AccountDeleteActivity)getActivity();
             accountDeleteActivity.switchFragment(AccountDelTwo.getInstance());
             break;
            case R.id.account_del_agreement:
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra(Variable.webUrl,accountDel.getRuleUrl());
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
