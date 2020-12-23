package com.lxkj.dmhw.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test.andlang.andlangutil.BaseLangActivity;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.adapter.IncomeAdapter;
import com.lxkj.dmhw.bean.IncomeData;
import com.lxkj.dmhw.model.CashModel;
import com.lxkj.dmhw.myinterface.ConfirmOKI;
import com.lxkj.dmhw.presenter.CashPresenter;
import com.lxkj.dmhw.utils.BBCUtil;
import com.lxkj.dmhw.widget.dialog.Confirm3Dialog;

import java.util.Observable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 收益页面
 */
public class IncomeDataActivity extends BaseLangActivity<CashPresenter> {

    @BindView(R.id.lang_tv_my)
    TextView lang_tv_my;
    @BindView(R.id.lang_tv_share)
    TextView lang_tv_share;
    @BindView(R.id.ll_select_order)
    LinearLayout ll_select_order;
    @BindView(R.id.tv_sum_label)
    TextView tvSumLabel;
    @BindView(R.id.tv_sum)
    TextView tvSum;
    @BindView(R.id.tv_order_income_label)
    TextView tvOrderIncomeLabel;
    @BindView(R.id.tv_order_income)
    TextView tvOrderIncome;
    @BindView(R.id.tv_team_income_label)
    TextView tvTeamIncomeLabel;
    @BindView(R.id.tv_team_income)
    TextView tvTeamIncome;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.ll_team_income)
    LinearLayout ll_team_income;
    @BindView(R.id.ll_order_income)
    LinearLayout ll_order_income;

    private int type=1;//累计=1，待到账2
    private int subType=1;//订单收益=1，其他收益=2
    private IncomeAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_income_data;
    }

    @Override
    public void initView() {
        setStatusBar(1,R.color.black);
        initLoading();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvList.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void initPresenter() {
        presenter=new CashPresenter(this, CashModel.class);
    }

    @Override
    public void initData() {
        type=getIntent().getIntExtra("type",1);

        showWaitDialog();
        presenter.reqIncomeData(type);

    }

    @Override
    public void update(Observable observable, Object o) {
        if ("reqIncomeData".equals(o)){
            double sum=0;
            for (IncomeData data:presenter.model.getIncomeDataList()){
                sum+=data.getRealAmount().doubleValue();
                if (data.getPreType()==1){
                    tvOrderIncome.setText(""+data.getRealAmount());
                }else if (data.getPreType()==2){
                    tvTeamIncome.setText(""+data.getRealAmount());
                }
            }
            tvSum.setText(BBCUtil.getDoubleFormat2(sum));
            presenter.reqIncomeDataDetialList(type,subType,false);
        }else if ("reqIncomeDataDetialList".equals(o)){
            dismissWaitDialog();
            if (adapter==null){
                adapter=new IncomeAdapter(this,presenter.model.getIncomeDataDetialList());
                rvList.setAdapter(adapter);
            }else{
                adapter.notifyDataSetChanged();
            }

        }
    }



    @OnClick(R.id.ll_order_income)
    public void selectOrderIncome() {
        //订单收益
        ll_order_income.setBackgroundResource(R.color.blackBg);
        ll_team_income.setBackgroundResource(R.color.colorBlackText);
        tvOrderIncome.setTextColor(getResources().getColor(R.color.colorWhite));
        tvOrderIncomeLabel.setTextColor(getResources().getColor(R.color.colorWhite));
        tvTeamIncome.setTextColor(getResources().getColor(R.color.blackTxt));
        tvTeamIncomeLabel.setTextColor(getResources().getColor(R.color.blackTxt));
        subType=1;
        showWaitDialog();
        presenter.reqIncomeDataDetialList(type,subType,false);
    }
    @OnClick(R.id.ll_team_income)
    public void selectTeamIncome() {
        //订单收益
        ll_order_income.setBackgroundResource(R.color.colorBlackText);
        ll_team_income.setBackgroundResource(R.color.blackBg);
        tvOrderIncome.setTextColor(getResources().getColor(R.color.blackTxt));
        tvOrderIncomeLabel.setTextColor(getResources().getColor(R.color.blackTxt));
        tvTeamIncome.setTextColor(getResources().getColor(R.color.colorWhite));
        tvTeamIncomeLabel.setTextColor(getResources().getColor(R.color.colorWhite));
        subType=2;
        showWaitDialog();
        presenter.reqIncomeDataDetialList(type,subType,false);
    }

    @OnClick(R.id.lang_tv_my)
    public void selectLeft() {
        //累计收益
        lang_tv_my.setTextColor(getResources().getColor(R.color.colorRedMain));
        lang_tv_share.setTextColor(getResources().getColor(R.color.colorWhite));
        ll_select_order.setBackgroundResource(R.mipmap.income_top_left);
        type=1;
        initData();

    }


    @OnClick(R.id.lang_tv_share)
    public void selectRight() {
        //待到账
        lang_tv_my.setTextColor(getResources().getColor(R.color.colorWhite));
        lang_tv_share.setTextColor(getResources().getColor(R.color.colorRedMain));
        ll_select_order.setBackgroundResource(R.mipmap.income_top_right);
        type=2;
        initData();

    }

    @OnClick({R.id.lang_ll_back, R.id.iv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lang_ll_back:
                goBack();
                break;
            case R.id.iv_right:
                new Confirm3Dialog(this, "过了7 天无理由退货期后， 待到账收益才会\n" +
                        "转入可提现金额中， 如果产生退货相应收\n" +
                        "将被自动取消。\n" +
                        "所有收益需要关联订单为完成状后才会\n" +
                        "转入可提现金额中。", null);
                break;
        }
    }
}