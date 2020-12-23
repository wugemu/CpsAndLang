package com.lxkj.dmhw.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.AccountDeleteActivity;
import com.lxkj.dmhw.defined.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 账户注销第三页
 */

public class AccountDelThree extends BaseFragment {

    @BindView(R.id.content02)
    TextView content02;
    @BindView(R.id.next03)
    LinearLayout next03;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.phone_edit_text01)
    EditText phone_edit_text01;


    public static AccountDelThree getInstance() {
        AccountDelThree fragment = new AccountDelThree();
        return fragment;
    }

    @Override
    public void onData() {

    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.accout_del_three, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {
        AccountDeleteActivity accountDeleteActivity= (AccountDeleteActivity)getActivity();
        accountDeleteActivity.setDel_title("注销确认");
        SpannableStringBuilder style=new SpannableStringBuilder(getResources().getString(R.string.str_delaccount_content));
        style.setSpan(new ForegroundColorSpan(Color.parseColor("#FF2741")),7,24, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); //设置指定位置textview的背景颜色
        content02.setText(style);

        phone_edit_text01.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals(getResources().getString(R.string.str_delaccount_checkcontent))) {
                    next03.setEnabled(true);
                    title.setTextColor(Color.WHITE);
                    next03.setBackgroundResource(R.drawable.accountdel_btn_style);
                }else{
                    next03.setEnabled(false);
                    title.setTextColor(Color.parseColor("#999999"));
                    next03.setBackgroundResource(R.drawable.account_delete_one_bg);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onCustomized() {

    }

    @Override
    public void mainMessage(Message message) {
    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
    }

    @OnClick(R.id.next03)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.next03:
                    //切换下一步
                    AccountDeleteActivity accountDeleteActivity = (AccountDeleteActivity) getActivity();
                    accountDeleteActivity.switchFragment(AccountDelFour.getInstance());
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
