package com.lxkj.dmhw.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.test.andlang.andlangutil.BaseLangActivity;
import com.lxkj.dmhw.R;

import java.util.Observable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 绑定银行卡
 */
public class BindBankCardActivity extends BaseLangActivity {

    @BindView(R.id.et_select_bank)
    EditText etSelectBank;
    @BindView(R.id.et_bank_account)
    EditText etBankAccount;
    @BindView(R.id.et_bank_mobile)
    EditText etBankMobile;
    @BindView(R.id.et_idcard)
    EditText etIdcard;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.btn_ok)
    TextView btnOk;

    @Override
    public int getLayoutId() {
        return R.layout.activity_bind_bank_card;
    }

    @Override
    public void initView() {
        initTitleBar("绑定银行卡");


    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void update(Observable observable, Object o) {

    }


    @OnClick({R.id.et_select_bank, R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_select_bank:
                break;
            case R.id.btn_ok:
                break;
        }
    }
}