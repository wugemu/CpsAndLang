package com.lxkj.dmhw.fragment;

import android.os.Bundle;
import android.os.Message;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.WeChatAbout;
import com.lxkj.dmhw.bean.Withdrawals;
import com.lxkj.dmhw.defined.CashierInputFilter;
import com.lxkj.dmhw.dialog.WechatDialog;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 提现-微信
 * Created by Android on 2018/5/22.
 */

public class WithdrawalsFragment_Wechat extends BaseFragment {

    @BindView(R.id.fragment_withdrawals_wechat_money_text_one)
    TextView fragmentWithdrawalsWechatMoneyTextOne;
    @BindView(R.id.fragment_withdrawals_wechat_btn)
    LinearLayout fragmentWithdrawalsWechatBtn;
    @BindView(R.id.fragment_withdrawals_wechat_layout)
    LinearLayout fragmentWithdrawalsWechatLayout;
    @BindView(R.id.fragment_withdrawals_wechat_money_edit)
    EditText fragmentWithdrawalsWechatMoneyEdit;
    @BindView(R.id.fragment_withdrawals_wechat_money_text_two)
    TextView fragmentWithdrawalsWechatMoneyTextTwo;
    @BindView(R.id.fragment_withdrawals_wechat_min_money_text)
    TextView fragmentWithdrawalsWechatMinMoneyText;
    @BindView(R.id.fragment_withdrawals_wechat_account)
    LinearLayout fragmentWithdrawalsWechatAccount;
    @BindView(R.id.fragment_withdrawals_wechat_explain)
    TextView fragmentWithdrawalsWechatExplain;
    @BindView(R.id.fragment_withdrawals_wechat_submission_btn)
    LinearLayout fragmentWithdrawalsWechatSubmissionBtn;
    private Withdrawals withdrawals;
    private String openId;

    public static WithdrawalsFragment_Wechat getInstance() {
        WithdrawalsFragment_Wechat fragment = new WithdrawalsFragment_Wechat();
        return fragment;
    }

    @Override
    public void onData() {

    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_withdrawals_wechat, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {
        // 获取提现信息
        paramMap.clear();
        paramMap.put("userid", login.getUserid());
        NetworkRequest.getInstance().POST(handler, paramMap, "Withdrawals", HttpCommon.Withdrawals);
        showDialog();
        fragmentWithdrawalsWechatMoneyEdit.setFilters(new InputFilter[]{new CashierInputFilter()});
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
        if (message.what == LogicActions.WithdrawalsSuccess) {
            withdrawals = (Withdrawals) message.obj;
            // 微信是否绑定
            paramMap.clear();
            paramMap.put("userId", login.getUserid());
            NetworkRequest.getInstance().POST(handler, paramMap, "WeChatPublic", HttpCommon.WeChatPublic);
        }
        if (message.what == LogicActions.WeChatPublicSuccess) {
            dismissDialog();
            if (message.obj.equals("未绑定微信公众号")) {
                fragmentWithdrawalsWechatAccount.setVisibility(View.GONE);
                fragmentWithdrawalsWechatLayout.setVisibility(View.VISIBLE);
                fragmentWithdrawalsWechatSubmissionBtn.setVisibility(View.GONE);
                fragmentWithdrawalsWechatMoneyTextOne.setText(withdrawals.getWithdrawalamount());
            } else {
                openId = (String) message.obj;
                fragmentWithdrawalsWechatAccount.setVisibility(View.VISIBLE);
                fragmentWithdrawalsWechatLayout.setVisibility(View.GONE);
                fragmentWithdrawalsWechatSubmissionBtn.setVisibility(View.VISIBLE);
                fragmentWithdrawalsWechatMoneyTextTwo.setText(withdrawals.getWithdrawalamount() + "元");
                fragmentWithdrawalsWechatMinMoneyText.setText("最低提现金额为" + withdrawals.getWithdrawallimit() + "元");
            }
            fragmentWithdrawalsWechatExplain.setText(withdrawals.getWithdrawalexp());
        }
        if (message.what == LogicActions.GetWeChatAboutSuccess) {
            dismissDialog();
            WechatDialog dialog = new WechatDialog(getActivity());
            dialog.showDialog((WeChatAbout) message.obj);
        }
        if (message.what == LogicActions.SetWithdrawalsWeChatSuccess) {
            toast(message.obj + "");
            // 获取提现信息
            paramMap.clear();
            paramMap.put("userid", login.getUserid());
            NetworkRequest.getInstance().POST(handler, paramMap, "Withdrawals", HttpCommon.Withdrawals);
            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("SignUserInfo"), "", 0);
        }
    }

    public String getMoney() {
        return fragmentWithdrawalsWechatMoneyEdit.getText().toString();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @OnClick({R.id.fragment_withdrawals_wechat_btn, R.id.fragment_withdrawals_wechat_submission_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_withdrawals_wechat_btn:// 绑定微信
                // 获取微信公众号信息
                paramMap.clear();
                NetworkRequest.getInstance().POST(handler, paramMap, "GetWeChatAbout", HttpCommon.GetWeChatAbout);
                break;
            case R.id.fragment_withdrawals_wechat_submission_btn:// 申请提现
                if (TextUtils.isEmpty(fragmentWithdrawalsWechatMoneyEdit.getText().toString())) {
                    toast("金额不能为空");
                } else {
                    if (Float.parseFloat(fragmentWithdrawalsWechatMoneyEdit.getText().toString()) < Integer.parseInt(withdrawals.getWithdrawallimit())) {
                        toast("提现金额必须大于" + withdrawals.getWithdrawallimit() + "元");
                    } else {
                        if (Float.parseFloat(fragmentWithdrawalsWechatMoneyEdit.getText().toString()) > Float.parseFloat(withdrawals.getWithdrawalamount())) {
                            toast("输入金额超过可提现金额");
                        } else {
                            paramMap.clear();
                            paramMap.put("openId", openId);
                            paramMap.put("transferAmount", fragmentWithdrawalsWechatMoneyEdit.getText().toString());
                            paramMap.put("transferType", "01");
                            paramMap.put("transferSource", "01");
                            NetworkRequest.getInstance().POST(handler, paramMap, "SetWithdrawalsWeChat", HttpCommon.SetWithdrawalsWeChat);
                            showDialog();
                        }
                    }
                }
                break;
        }
    }
}
