package com.lxkj.dmhw.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.AlipayActivity;
import com.lxkj.dmhw.bean.Withdrawals;
import com.lxkj.dmhw.bean.WithdrawalsMorePl;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.defined.CashierInputFilter;
import com.lxkj.dmhw.dialog.WithdrawalsDialog;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.ToastUtil;
import com.lxkj.dmhw.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 提现-支付宝
 * Created by Android on 2018/5/22.
 */

public class WithdrawalsFragment_Alipay extends BaseFragment {

    @BindView(R.id.fragment_withdrawals_alipay_btn)
    LinearLayout fragmentWithdrawalsAlipayBtn;
    @BindView(R.id.fragment_withdrawals_alipay_account_text)
    TextView fragmentWithdrawalsAlipayAccountText;
    @BindView(R.id.fragment_withdrawals_alipay_money_edit)
    EditText fragmentWithdrawalsAlipayMoneyEdit;
    @BindView(R.id.fragment_withdrawals_alipay_account)
    LinearLayout fragmentWithdrawalsAlipayAccount;
    @BindView(R.id.fragment_withdrawals_alipay_layout)
    LinearLayout fragmentWithdrawalsAlipayLayout;
    @BindView(R.id.fragment_withdrawals_alipay_name_text)
    TextView fragmentWithdrawalsAlipayNameText;
    @BindView(R.id.fragment_withdrawals_alipay_money_text_two)
    TextView fragmentWithdrawalsAlipayMoneyTextTwo;
    @BindView(R.id.fragment_withdrawals_alipay_min_money_text)
    TextView fragmentWithdrawalsAlipayMinMoneyText;
    @BindView(R.id.all_withdraw_text)
    TextView all_withdraw_text;
    @BindView(R.id.toast_tv)
    TextView toast_tv;
    @BindView(R.id.fragment_withdrawals_alipay_explain)
    TextView fragmentWithdrawalsAlipayExplain;
    @BindView(R.id.fragment_withdrawals_alipay_submission_btn)
    LinearLayout fragmentWithdrawalsAlipaySubmissionBtn;
    @BindView(R.id.fragment_withdrawals_alipay_account_amend)
    LinearLayout fragmentWithdrawalsAlipayAccountAmend;
    private Withdrawals withdrawals;
    private int platform=0;
    public static WithdrawalsFragment_Alipay getInstance(int type) {
        WithdrawalsFragment_Alipay fragment = new WithdrawalsFragment_Alipay();
        Bundle bundle = new Bundle();
        bundle.putInt("platform", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            platform = bundle.getInt("platform");
        }
    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_withdrawals_alipay, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {
        // 获取提现信息
        if (platform==0){
            showDialog();
            paramMap.clear();
            paramMap.put("userid", login.getUserid());
            NetworkRequest.getInstance().POST(handler, paramMap, "Withdrawals", HttpCommon.Withdrawals);
        }else{
            paramMap.clear();
            paramMap.put("userid", login.getUserid());
            NetworkRequest.getInstance().GETNew(handler, paramMap, "WithdrawalsMorePl", HttpCommon.WithdrawalsInfo);
        }
        fragmentWithdrawalsAlipayMoneyEdit.setFilters(new InputFilter[]{new CashierInputFilter()});
        fragmentWithdrawalsAlipayMoneyEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                fragmentWithdrawalsAlipayMoneyEdit.setSelection(s.length());
                if (s.length() > 0) {
                    fragmentWithdrawalsAlipayMinMoneyText.setVisibility(View.GONE);
                } else {
                    fragmentWithdrawalsAlipayMinMoneyText.setVisibility(View.VISIBLE);
                }
                if (s.length() == 0) {
                    fragmentWithdrawalsAlipaySubmissionBtn.setEnabled(false);
                    fragmentWithdrawalsAlipaySubmissionBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.withdraw_btn_style));
                    toast_tv.setVisibility(View.GONE);
                    return;
                }
                if (withdrawals==null){
                    return;
                }
                if (withdrawals.getWithdrawalamount().equals("")){
                    return;
                }
                if (fragmentWithdrawalsAlipayMoneyEdit.getText().toString().equals("")){
                    return;
                }
                if (fragmentWithdrawalsAlipayMoneyEdit.getText().toString().trim().startsWith(".")){
                    return;
                }
                if (Float.parseFloat(fragmentWithdrawalsAlipayMoneyEdit.getText().toString()) > Float.parseFloat(withdrawals.getWithdrawalamount())) {
                    fragmentWithdrawalsAlipaySubmissionBtn.setEnabled(false);
                    fragmentWithdrawalsAlipaySubmissionBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.withdraw_btn_style));
                    toast_tv.setVisibility(View.VISIBLE);
                    toast_tv.setText("超过可提现金额");
                    return;
                }
                if (withdrawals.getWithdrawallimit().equals("")){
                    withdrawals.setWithdrawallimit("10");
                }

                if (Float.parseFloat(fragmentWithdrawalsAlipayMoneyEdit.getText().toString()) < Float.parseFloat(withdrawals.getWithdrawallimit())){
                  //小于最小提现金额
                    fragmentWithdrawalsAlipaySubmissionBtn.setEnabled(false);
                    fragmentWithdrawalsAlipaySubmissionBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.withdraw_btn_style));
                    toast_tv.setVisibility(View.VISIBLE);
                    toast_tv.setText("最低提现金额为"+withdrawals.getWithdrawallimit()+"元");
                    return;
                }

                toast_tv.setVisibility(View.GONE);
                fragmentWithdrawalsAlipaySubmissionBtn.setEnabled(true);
                fragmentWithdrawalsAlipaySubmissionBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.withdraw_btn_check_style));
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
        if (message.what == LogicActions.WithdrawalsDialogSuccess) {
            ArrayList<String> arrayList = (ArrayList<String>) message.obj;
            paramMap.clear();
            paramMap.put("userid", login.getUserid());
            paramMap.put("type", "0");
            paramMap.put("alipayacount", arrayList.get(0));
            paramMap.put("alipayname", arrayList.get(1));
            NetworkRequest.getInstance().POST(handler, paramMap, "BindingAlipay", HttpCommon.BindingAlipay);
            showDialog();
        }
        if (message.what == LogicActions.AmendAliPaySuccess) {
            if (platform==0){
                // 获取提现信息
                paramMap.clear();
                paramMap.put("userid", login.getUserid());
                NetworkRequest.getInstance().POST(handler, paramMap, "Withdrawals", HttpCommon.Withdrawals);
            }else{
                //多平台提现信息
                paramMap.clear();
                paramMap.put("userid", login.getUserid());
                NetworkRequest.getInstance().GETNew(handler, paramMap, "WithdrawalsMorePl", HttpCommon.WithdrawalsInfo);
            }

        }
    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        if (message.what == LogicActions.WithdrawalsSuccess) {
            dismissDialog();
            withdrawals = (Withdrawals) message.obj;
            if (Objects.equals(withdrawals.getAlipayacount(), "")) {
                fragmentWithdrawalsAlipayBtn.setVisibility(View.VISIBLE);
                fragmentWithdrawalsAlipayAccount.setVisibility(View.GONE);
                fragmentWithdrawalsAlipayLayout.setVisibility(View.VISIBLE);
                fragmentWithdrawalsAlipaySubmissionBtn.setVisibility(View.GONE);
                fragmentWithdrawalsAlipayMoneyTextTwo.setText(withdrawals.getWithdrawalamount());
            } else {
                fragmentWithdrawalsAlipayBtn.setVisibility(View.GONE);
                fragmentWithdrawalsAlipayAccount.setVisibility(View.VISIBLE);
                fragmentWithdrawalsAlipayLayout.setVisibility(View.GONE);
                fragmentWithdrawalsAlipaySubmissionBtn.setVisibility(View.VISIBLE);
                fragmentWithdrawalsAlipayMoneyEdit.setText("");
                fragmentWithdrawalsAlipayAccountText.setText(Utils.phoneEncryption(withdrawals.getAlipayacount()));
                fragmentWithdrawalsAlipayNameText.setText(withdrawals.getAlipayname().substring(0, 1) + "**");
                fragmentWithdrawalsAlipayMoneyTextTwo.setText(withdrawals.getWithdrawalamount() + "元");
                fragmentWithdrawalsAlipayMinMoneyText.setText(" 最低提现金额为" + withdrawals.getWithdrawallimit() + "元");
            }
            fragmentWithdrawalsAlipayExplain.setText(withdrawals.getWithdrawalexp());
        }
        if (message.what == LogicActions.BindingAlipaySuccess) {
            toast(message.obj + "");
            // 获取提现信息
            paramMap.clear();
            paramMap.put("userid", login.getUserid());
            NetworkRequest.getInstance().POST(handler, paramMap, "Withdrawals", HttpCommon.Withdrawals);
        }

        //淘宝提现
        if (message.what == LogicActions.SetWithdrawalsSuccess) {
            toast(message.obj + "");
            dismissDialog();
                // 获取提现信息
                paramMap.clear();
                paramMap.put("userid", login.getUserid());
                NetworkRequest.getInstance().POST(handler, paramMap, "Withdrawals", HttpCommon.Withdrawals);
            //提现成功刷新个人中心数据（可提现那块）
            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("WithdrawalsRefreshUserInfo"), "", 0);
        }


        //多平台提现
        if (message.what == LogicActions.SetWithdrawalsMorePLSuccess) {
            toast(message.obj + "");
            dismissDialog();
            paramMap.clear();
            paramMap.put("userid", login.getUserid());
            NetworkRequest.getInstance().GETNew(handler, paramMap, "WithdrawalsMorePl", HttpCommon.WithdrawalsInfo);
            //提现成功刷新个人中心数据（可提现那块）
            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("WithdrawalsRefreshUserInfo"), "", 0);
        }



        if (message.what == LogicActions.WithdrawalsMorePlSuccess) {
            dismissDialog();
            WithdrawalsMorePl wmpl= (WithdrawalsMorePl) message.obj;
            withdrawals=new Withdrawals();//实体转化
            withdrawals.setAlipayacount(wmpl.getAlipayAccount());
            withdrawals.setAlipayname(wmpl.getAlipayName());
            withdrawals.setWithdrawalamount(wmpl.getAvailableAmt());
            withdrawals.setWithdrawalexp(wmpl.getDesc());
            withdrawals.setWithdrawallimit(wmpl.getMin());
            if (Objects.equals(withdrawals.getAlipayacount(), "")) {
                fragmentWithdrawalsAlipayBtn.setVisibility(View.VISIBLE);
                fragmentWithdrawalsAlipayAccount.setVisibility(View.GONE);
                fragmentWithdrawalsAlipayLayout.setVisibility(View.VISIBLE);
                fragmentWithdrawalsAlipaySubmissionBtn.setVisibility(View.GONE);
                fragmentWithdrawalsAlipayMoneyTextTwo.setText(withdrawals.getWithdrawalamount());
            } else {
                fragmentWithdrawalsAlipayBtn.setVisibility(View.GONE);
                fragmentWithdrawalsAlipayAccount.setVisibility(View.VISIBLE);
                fragmentWithdrawalsAlipayLayout.setVisibility(View.GONE);
                fragmentWithdrawalsAlipaySubmissionBtn.setVisibility(View.VISIBLE);
                fragmentWithdrawalsAlipayMoneyEdit.setText("");
                fragmentWithdrawalsAlipayAccountText.setText(Utils.phoneEncryption(withdrawals.getAlipayacount()));
                fragmentWithdrawalsAlipayNameText.setText(withdrawals.getAlipayname().substring(0, 1) + "**");
                fragmentWithdrawalsAlipayMoneyTextTwo.setText(withdrawals.getWithdrawalamount() + "元");
                fragmentWithdrawalsAlipayMinMoneyText.setText(" 最低提现金额为" + withdrawals.getWithdrawallimit() + "元");
            }
            fragmentWithdrawalsAlipayExplain.setText(withdrawals.getWithdrawalexp());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @OnClick({R.id.fragment_withdrawals_alipay_btn, R.id.fragment_withdrawals_alipay_submission_btn, R.id.fragment_withdrawals_alipay_account_amend,R.id.all_withdraw_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_withdrawals_alipay_btn:// 绑定支付宝
               Intent intent = new Intent(getActivity(), AlipayActivity.class);
               intent.putExtra("isHasAlipay",false);
               startActivity(intent);
//                WithdrawalsDialog dialog = new WithdrawalsDialog(getActivity(), "WithdrawalsDialog");
//                dialog.getDialog().show();
                break;
            case R.id.fragment_withdrawals_alipay_submission_btn:// 提交申请
                if (withdrawals==null){
                    return;
                }
                if (!Objects.equals(withdrawals.getAlipayacount(), "")) {
                    if (TextUtils.isEmpty(fragmentWithdrawalsAlipayMoneyEdit.getText().toString())) {
                        toast("金额不能为空");
                    } else {
                        if (Float.parseFloat(fragmentWithdrawalsAlipayMoneyEdit.getText().toString()) < Float.parseFloat(withdrawals.getWithdrawallimit())) {
                            toast("提现金额必须大于" + withdrawals.getWithdrawallimit() + "元");
                        } else {
                            if (Float.parseFloat(fragmentWithdrawalsAlipayMoneyEdit.getText().toString()) > Float.parseFloat(withdrawals.getWithdrawalamount())) {
                                toast("输入金额超过可提现金额");
                            } else {
                                showDialog();
                                if (platform==0) {
                                    paramMap.clear();
                                    paramMap.put("userid", login.getUserid());
                                    paramMap.put("withdrawalsmoney", fragmentWithdrawalsAlipayMoneyEdit.getText().toString());
                                    paramMap.put("alipayacount", withdrawals.getAlipayacount());
                                    paramMap.put("alipayname", withdrawals.getAlipayname());
                                    NetworkRequest.getInstance().POST(handler, paramMap, "SetWithdrawals", HttpCommon.SetWithdrawals);
                                }else{//多平台提现操作
                                    paramMap.clear();
                                    paramMap.put("userid", login.getUserid());
                                    paramMap.put("type", "");//默认支付宝
                                    paramMap.put("mny", fragmentWithdrawalsAlipayMoneyEdit.getText().toString());
                                    NetworkRequest.getInstance().GETNew(handler, paramMap, "SetWithdrawalsMorePL", HttpCommon.WithdrawalsExtract);
                                }
                            }
                        }
                    }
                }
                break;
            case R.id.fragment_withdrawals_alipay_account_amend:
                startActivity(new Intent(getActivity(), AlipayActivity.class).putExtra("isHasAlipay",true));
                break;
            case R.id.all_withdraw_text:
                if (withdrawals==null){
                    return;
                }
                fragmentWithdrawalsAlipayMoneyEdit.setText(withdrawals.getWithdrawalamount());
                break;
        }
    }
}
