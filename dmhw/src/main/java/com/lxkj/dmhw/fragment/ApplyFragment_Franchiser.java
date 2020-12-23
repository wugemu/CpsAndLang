package com.lxkj.dmhw.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.activity.WebViewActivity;
import com.lxkj.dmhw.bean.AgentQuantity;
import com.lxkj.dmhw.bean.FranchiserUpgrade;
import com.lxkj.dmhw.defined.SmoothCheckBox;
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
 * 升级加盟商
 * Created by Android on 2018/4/20.
 */

public class ApplyFragment_Franchiser extends BaseFragment {

    @BindView(R.id.fragment_apply_franchiser_one_number)
    TextView fragmentApplyFranchiserOneNumber;
    @BindView(R.id.fragment_apply_franchiser_two_number)
    TextView fragmentApplyFranchiserTwoNumber;
    @BindView(R.id.fragment_apply_franchiser_check_one)
    SmoothCheckBox fragmentApplyFranchiserCheckOne;
    @BindView(R.id.fragment_apply_franchiser_check_two)
    SmoothCheckBox fragmentApplyFranchiserCheckTwo;
    @BindView(R.id.fragment_apply_franchiser_btn)
    LinearLayout fragmentApplyFranchiserBtn;
    @BindView(R.id.fragment_apply_agreement)
    TextView fragmentApplyAgreement;
    @BindView(R.id.fragment_apply_franchiser_one_condition)
    TextView fragmentApplyFranchiserOneCondition;
    @BindView(R.id.fragment_apply_franchiser_two_condition)
    TextView fragmentApplyFranchiserTwoCondition;
    @BindView(R.id.fragment_apply_franchiser_money_condition)
    TextView fragmentApplyFranchiserMoneyCondition;
    @BindView(R.id.fragment_apply_franchiser_explain)
    TextView fragmentApplyFranchiserExplain;
    private int
            agentOneNumber = 0,
            agentTwoNumber = 0,
            agentOne = 0,
            agentTwo = 0;
    private float
            income = 0,
            incomeCondition = 0;

    public static ApplyFragment_Franchiser getInstance() {
        ApplyFragment_Franchiser fragment = new ApplyFragment_Franchiser();
        return fragment;
    }

    @Override
    public void onData() {

    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apply_franchiser, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {
        // 获取下级代理商数量
        paramMap.clear();
        paramMap.put("userid", login.getUserid());
        NetworkRequest.getInstance().POST(handler, paramMap, "GetAgentQuantity", HttpCommon.GetAgentQuantity);
        // 获取升级加盟商条件
        paramMap.clear();
        paramMap.put("userid", login.getUserid());
        NetworkRequest.getInstance().POST(handler, paramMap, "GetFranchiser", HttpCommon.GetFranchiser);
        showDialog();
        fragmentApplyFranchiserCheckOne.setChecked(true);
        fragmentApplyFranchiserCheckOne.setEnabled(false);
        fragmentApplyFranchiserCheckTwo.setEnabled(false);
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
        if (message.what == LogicActions.GetAgentQuantitySuccess) {
            AgentQuantity quantity = (AgentQuantity) message.obj;
            agentOneNumber = Integer.parseInt(quantity.getAagentcnt());
            agentTwoNumber = Integer.parseInt(quantity.getBagentcnt());
            income = Float.parseFloat(quantity.getCurrentamount());
            fragmentApplyFranchiserOneNumber.setText(agentOneNumber + "");
            fragmentApplyFranchiserTwoNumber.setText(agentTwoNumber + "");
        }
        if (message.what == LogicActions.GetFranchiserSuccess) {
            FranchiserUpgrade upgrade = (FranchiserUpgrade) message.obj;
            agentOne = Integer.parseInt(upgrade.getAagentcnt());
            agentTwo = Integer.parseInt(upgrade.getBagentcnt());
            incomeCondition = Float.parseFloat(upgrade.getCurrentamount());
            fragmentApplyFranchiserOneCondition.setText(agentOne + "");
            fragmentApplyFranchiserTwoCondition.setText(agentTwo + "");
            fragmentApplyFranchiserMoneyCondition.setText(upgrade.getCurrentamount());
            fragmentApplyFranchiserExplain.setText(upgrade.getActivitydesc());

        }
        if (message.what == LogicActions.SetFranchiserSuccess) {
            if ((message.obj + "").equals("申请成功,等待审核！")) {
                showDialog();
                new Handler().postDelayed(() -> {
                    login.setUsertype("4");
                    InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("RefreshStatus"), true, 0);
                    dismissDialog();
                    isFinish();
                }, 3000);
            }
            DateStorage.setFranchiserStatus(true);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @OnClick({R.id.fragment_apply_agreement, R.id.fragment_apply_franchiser_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_apply_agreement:
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra(Variable.webUrl, Variable.protocl_Franchiser);
                startActivity(intent);
                break;
            case R.id.fragment_apply_franchiser_btn:
                if (DateStorage.getFranchiserStatus()) {
                    toast("呆萌店主已开通，请重新登录");
                } else {
                    if (agentOneNumber >= agentOne) {
                        if (agentTwoNumber >= agentTwo) {
                            if (income >= incomeCondition) {
                                paramMap.clear();
                                paramMap.put("userid", login.getUserid());
                                NetworkRequest.getInstance().POST(handler, paramMap, "SetFranchiser", HttpCommon.SetFranchiser);
                                showDialog();
                                fragmentApplyFranchiserBtn.setEnabled(false);
                            } else {
                                toast("累计收益不满足要求");
                            }
                        } else {
                            toast("B级会员不满足要求");
                        }
                    } else {
                        toast("A级会员不满足要求");
                    }
                }
                break;
        }
    }

}
