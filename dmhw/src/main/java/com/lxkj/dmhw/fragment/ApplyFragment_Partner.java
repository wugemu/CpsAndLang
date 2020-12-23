package com.lxkj.dmhw.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.activity.WebViewActivity;
import com.lxkj.dmhw.bean.PartnerUpgrade;
import com.lxkj.dmhw.defined.SmoothCheckBox;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 升级合伙人
 * Created by Android on 2018/4/20.
 */

public class ApplyFragment_Partner extends BaseFragment {

    @BindView(R.id.fragment_apply_partner_number)
    TextView fragmentApplyPartnerNumber;
    @BindView(R.id.fragment_apply_partner_check_one)
    SmoothCheckBox fragmentApplyPartnerCheckOne;
    @BindView(R.id.fragment_apply_partner_check_two)
    SmoothCheckBox fragmentApplyPartnerCheckTwo;
    @BindView(R.id.fragment_apply_agreement)
    TextView fragmentApplyAgreement;
    @BindView(R.id.fragment_apply_partner_btn)
    LinearLayout fragmentApplyPartnerBtn;
    @BindView(R.id.fragment_apply_partner_condition)
    TextView fragmentApplyPartnerCondition;
    @BindView(R.id.fragment_apply_partner_explain)
    TextView fragmentApplyPartnerExplain;
    private String status = "";
    private int
            condition = 0,
            number = 0;

    public static ApplyFragment_Partner getInstance() {
        ApplyFragment_Partner fragment = new ApplyFragment_Partner();
        return fragment;
    }

    @Override
    public void onData() {

    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apply_partner, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {
        // 获取下级加盟商数量
        paramMap.clear();
        paramMap.put("userid", login.getUserid());
        NetworkRequest.getInstance().POST(handler, paramMap, "GetFranchiserQuantity", HttpCommon.GetFranchiserQuantity);
        // 获取升级合伙人条件
        paramMap.clear();
        paramMap.put("userid", login.getUserid());
        NetworkRequest.getInstance().POST(handler, paramMap, "GetPartner", HttpCommon.GetPartner);
        // 获取合伙人申请状态查询
        paramMap.clear();
        paramMap.put("userid", login.getUserid());
        NetworkRequest.getInstance().POST(handler, paramMap, "GetPartnerStatus", HttpCommon.GetPartnerStatus);
        showDialog();
        fragmentApplyPartnerCheckOne.setChecked(true);
        fragmentApplyPartnerCheckOne.setEnabled(false);
        fragmentApplyPartnerCheckTwo.setEnabled(false);
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
        dismissDialog();
        if (message.what == LogicActions.GetFranchiserQuantitySuccess) {
            number = Integer.parseInt(message.obj + "");
            fragmentApplyPartnerNumber.setText(number + "");
        }
        if (message.what == LogicActions.GetPartnerSuccess) {
            PartnerUpgrade upgrade = (PartnerUpgrade) message.obj;
            condition = Integer.parseInt(upgrade.getFranchisercnt());
            fragmentApplyPartnerCondition.setText(condition + "");
            fragmentApplyPartnerExplain.setText(upgrade.getActivitydesc());
        }
        if (message.what == LogicActions.GetPartnerStatusSuccess) {
            status = message.obj + "";
            switch (status) {
                case "00":
                    toast("审核中");
                    fragmentApplyPartnerBtn.setEnabled(false);
                    break;
                case "01":
                    toast("审核通过,请重新登录");
                    fragmentApplyPartnerBtn.setEnabled(false);
                    break;
            }
        }
        if (message.what == LogicActions.SetPartnerSuccess) {
            toast(message.obj + "");
            isFinish();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @OnClick({R.id.fragment_apply_agreement, R.id.fragment_apply_partner_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_apply_agreement:
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra(Variable.webUrl, Variable.protocl_Partner);
                startActivity(intent);
                break;
            case R.id.fragment_apply_partner_btn:
                switch (status) {
                    case "00":
                        toast("审核中");
                        break;
                    case "01":
                        toast("审核通过,请重新登录");
                        break;
                    default:
                        if (number >= condition) {
                            paramMap.clear();
                            paramMap.put("userid", login.getUserid());
                            NetworkRequest.getInstance().POST(handler, paramMap, "SetPartner", HttpCommon.SetPartner);
                            showDialog();
                            fragmentApplyPartnerBtn.setEnabled(false);
                        } else {
                            toast("下级店主人数不满足要求");
                        }
                        break;
                }
                break;
        }
    }
}
