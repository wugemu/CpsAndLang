package com.lxkj.dmhw.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.AlipayActivity;
import com.lxkj.dmhw.bean.WithdrawalDMB;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.defined.CashierInputFilter;
import com.lxkj.dmhw.dialog.TaskDMBDialog;
import com.lxkj.dmhw.dialog.WithdrawalsDialog;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.Utils;

import org.json.JSONObject;

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

public class WithdrawalsTaskDMBFragment_Alipay extends BaseFragment {

    @BindView(R.id.fragment_withdrawals_alipay_btn)
    LinearLayout fragmentWithdrawalsAlipayBtn;
    @BindView(R.id.fragment_withdrawals_alipay_account_text)
    TextView fragmentWithdrawalsAlipayAccountText;
    @BindView(R.id.fragment_withdrawals_alipay_money_edit)
    EditText fragmentWithdrawalsAlipayMoneyEdit;
    @BindView(R.id.fragment_withdrawals_alipay_account)
    LinearLayout fragmentWithdrawalsAlipayAccount;
    @BindView(R.id.fragment_withdrawals_alipay_money_text_one)
    TextView fragmentWithdrawalsAlipayMoneyTextOne;
    @BindView(R.id.fragment_withdrawals_alipay_name_text)
    TextView fragmentWithdrawalsAlipayNameText;
    @BindView(R.id.fragment_withdrawals_alipay_min_money_text)
    TextView fragmentWithdrawalsAlipayMinMoneyText;
    @BindView(R.id. fragment_withdrawals_title)
    TextView  fragment_withdrawals_title;
    @BindView(R.id.fragment_withdrawals_alipay_submission_btn)
    LinearLayout fragmentWithdrawalsAlipaySubmissionBtn;
    @BindView(R.id.fragment_withdrawals_alipay_account_amend)
    LinearLayout fragmentWithdrawalsAlipayAccountAmend;
    @BindView(R.id.fragment_withdrawals_alipay_money_text_wait)
    TextView  fragment_withdrawals_alipay_money_text_wait;
    @BindView(R.id.fragment_withdrawals_alipay_min_money_textlayout)
    LinearLayout fragment_withdrawals_alipay_min_money_textlayout;
    @BindView(R.id.toast_text)
    TextView  toast_text;
    @BindView(R.id.w_question_iv)
    ImageView w_question_iv;
    @BindView(R.id.w_question_iv1)
    ImageView w_question_iv1;

    private WithdrawalDMB withdrawals;


    public static WithdrawalsTaskDMBFragment_Alipay getInstance() {
        WithdrawalsTaskDMBFragment_Alipay fragment = new WithdrawalsTaskDMBFragment_Alipay();
        return fragment;
    }

    @Override
    public void onData() {
    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_withdrawals_task_dmb_alipay, null);
        ButterKnife.bind(this, view);
        TextPaint tp= fragment_withdrawals_title.getPaint();
        tp.setFakeBoldText(true);

        fragmentWithdrawalsAlipayMoneyEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()==0){
                    fragmentWithdrawalsAlipaySubmissionBtn.setEnabled(false);
                    fragmentWithdrawalsAlipaySubmissionBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.dmb_btn_style));
                    toast_text.setVisibility(View.GONE);
                    fragment_withdrawals_alipay_min_money_textlayout.setVisibility(View.VISIBLE);
                    return;
                }
                if (fragmentWithdrawalsAlipayMoneyEdit.getText().toString().trim().startsWith(".")){
                    return;
                }
                if (withdrawals!=null&&Float.parseFloat(fragmentWithdrawalsAlipayMoneyEdit.getText().toString()) > Float.parseFloat(withdrawals.getWithdrawalmax())) {
                    fragmentWithdrawalsAlipaySubmissionBtn.setEnabled(false);
                    fragmentWithdrawalsAlipaySubmissionBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.dmb_btn_style));
                    toast_text.setVisibility(View.VISIBLE);
                    toast_text.setText("超出最大可提现金额");
                    fragment_withdrawals_alipay_min_money_textlayout.setVisibility(View.GONE);
                    return;
                }
                if (Float.parseFloat(fragmentWithdrawalsAlipayMoneyEdit.getText().toString()) > Float.parseFloat(withdrawals.getScoremny())) {
                    fragmentWithdrawalsAlipaySubmissionBtn.setEnabled(false);
                    fragmentWithdrawalsAlipaySubmissionBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.dmb_btn_style));
                    toast_text.setVisibility(View.VISIBLE);
                    toast_text.setText("超出可提现金额");
                    fragment_withdrawals_alipay_min_money_textlayout.setVisibility(View.GONE);
                    return;
                }
                toast_text.setVisibility(View.GONE);
                fragment_withdrawals_alipay_min_money_textlayout.setVisibility(View.VISIBLE);
                fragmentWithdrawalsAlipaySubmissionBtn.setEnabled(true);
                fragmentWithdrawalsAlipaySubmissionBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.dmb_check_btn_style));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    @Override
    public void onEvent() {
        //获取提现信息
        paramMap.clear();
        paramMap.put("userid", login.getUserid());
        NetworkRequest.getInstance().POST(handler, paramMap, "GetFrozenMny", HttpCommon.GetFrozenMny);
        showDialog();
        fragmentWithdrawalsAlipayMoneyEdit.setFilters(new InputFilter[]{new CashierInputFilter()});
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
            // 获取提现信息
            paramMap.clear();
            paramMap.put("userid", login.getUserid());
            NetworkRequest.getInstance().POST(handler, paramMap, "GetFrozenMny", HttpCommon.GetFrozenMny);
        }
    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        if (message.what == LogicActions.BindingAlipaySuccess) {
            toast(message.obj + "");
            // 获取提现信息
            paramMap.clear();
            paramMap.put("userid", login.getUserid());
            NetworkRequest.getInstance().POST(handler, paramMap, "GetFrozenMny", HttpCommon.GetFrozenMny);
        }
        if (message.what == LogicActions.WithdrawalSuccess) {
            toast("提现成功");
            fragmentWithdrawalsAlipayMoneyEdit.setText("");
            fragmentWithdrawalsAlipaySubmissionBtn.setEnabled(false);
            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("RefreshTaskList"), "", 0);
            //获取提现信息
            paramMap.clear();
            paramMap.put("userid", login.getUserid());
            NetworkRequest.getInstance().POST(handler, paramMap, "GetFrozenMny", HttpCommon.GetFrozenMny);
            dismissDialog();
        }

        if (message.what == LogicActions.GetFrozenMnySuccess) {
            withdrawals = (WithdrawalDMB) message.obj;
            fragment_withdrawals_alipay_money_text_wait.setText(withdrawals.getMny());
            fragmentWithdrawalsAlipayMoneyTextOne.setText(withdrawals.getScoremny());
            fragmentWithdrawalsAlipayMinMoneyText.setText("最低提现金额为"+ withdrawals.getWithdrawallimit()+"元");
            if (Objects.equals(withdrawals.getAlipayacount(), "")) {
                fragmentWithdrawalsAlipayAccount.setVisibility(View.GONE);
                fragmentWithdrawalsAlipayBtn.setVisibility(View.VISIBLE);
                fragmentWithdrawalsAlipaySubmissionBtn.setEnabled(false);
            } else {
                fragmentWithdrawalsAlipayAccount.setVisibility(View.VISIBLE);
                fragmentWithdrawalsAlipayBtn.setVisibility(View.GONE);
                fragmentWithdrawalsAlipayAccountText.setText(Utils.phoneEncryption(withdrawals.getAlipayacount()));
                fragmentWithdrawalsAlipayNameText.setText(withdrawals.getAlipayname().substring(0, 1) + "**");
            }
        }
        dismissDialog();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @OnClick({R.id.fragment_withdrawals_alipay_btn, R.id.fragment_withdrawals_alipay_submission_btn, R.id.fragment_withdrawals_alipay_account_amend,R.id.w_question_iv,R.id.w_question_iv1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_withdrawals_alipay_btn:// 绑定支付宝
                Intent intent = new Intent(getActivity(), AlipayActivity.class);
                intent.putExtra("isHasAlipay",false);
                startActivity(intent);
                break;
            case R.id.fragment_withdrawals_alipay_submission_btn:// 提交申请
                    if (fragmentWithdrawalsAlipayMoneyEdit.getText().toString().equals("")){
                        return;
                        }
                        if (Float.parseFloat(fragmentWithdrawalsAlipayMoneyEdit.getText().toString()) < Float.parseFloat(withdrawals.getWithdrawallimit())) {
                            toast_text.setVisibility(View.VISIBLE);
                            toast_text.setText("提现金额必须大于" + withdrawals.getWithdrawallimit() + "元");
                            fragment_withdrawals_alipay_min_money_textlayout.setVisibility(View.GONE);
                            toast("提现金额必须大于" + withdrawals.getWithdrawallimit() + "元");
                        } else {
                            if (Float.parseFloat(fragmentWithdrawalsAlipayMoneyEdit.getText().toString()) > Float.parseFloat(withdrawals.getWithdrawalmax())) {
                                toast("超过最大可提现金额");
                            }else {
                                paramMap.clear();
                                paramMap.put("userid", login.getUserid());
                                paramMap.put("mny",fragmentWithdrawalsAlipayMoneyEdit.getText().toString());
                                NetworkRequest.getInstance().POST(handler, paramMap, "Withdrawal", HttpCommon.Withdrawal);
                                showDialog();
                            }
                        }
                break;
            case R.id.fragment_withdrawals_alipay_account_amend:
                startActivity(new Intent(getActivity(), AlipayActivity.class).putExtra("isHasAlipay",true));
                break;
            case R.id.w_question_iv:
                TaskDMBDialog dialog1 = new TaskDMBDialog(getActivity());
                dialog1.showDialog("提现说明",withdrawals.getWithdrawaldesc());
                break;
            case R.id.w_question_iv1:
                TaskDMBDialog dialog2 = new TaskDMBDialog(getActivity());
                dialog2.showDialog("待结算金额？",withdrawals.getFrozenmnyesc());
                break;
        }
    }
}
