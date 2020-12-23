package com.lxkj.dmhw.fragment;

import android.os.Bundle;
import android.os.Message;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lxkj.dmhw.activity.TempDataSetActivity;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.Popularize;
import com.lxkj.dmhw.defined.LazyFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 推广数据
 * Created by Android on 2018/5/11.
 */

public class ProfitFragment extends LazyFragment {

    @BindView(R.id.fragment_profit_estimate_text)
    TextView fragmentProfitEstimateText;
    @BindView(R.id.fragment_profit_number_my_text)
    TextView fragmentProfitNumberMyText;
    @BindView(R.id.fragment_profit_estimate_my_text)
    TextView fragmentProfitEstimateMyText;
    @BindView(R.id.fragment_profit_number_team_text)
    TextView fragmentProfitNumberTeamText;
    @BindView(R.id.fragment_profit_estimate_team_text)
    TextView fragmentProfitEstimateTeamText;
    // 推广数据
    private Popularize.PopularizeList popularize;
    TextPaint tp;
    public static ProfitFragment getInstance(Popularize.PopularizeList popularize) {
        ProfitFragment fragment = new ProfitFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("popularize", popularize);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            popularize = (Popularize.PopularizeList) bundle.getSerializable("popularize");
        }
    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profit, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {
        if (!TempDataSetActivity.isTaoCanEdit) {
            fragmentProfitEstimateText.setText(popularize.getSettlerincome());
            fragmentProfitNumberMyText.setText(popularize.getMyturnovercount());
            fragmentProfitEstimateMyText.setText(popularize.getMyturnoverincome());
            fragmentProfitNumberTeamText.setText(popularize.getTeamturnovercount());
            fragmentProfitEstimateTeamText.setText(popularize.getTeamturnoverincome());
        }else{
            if(TempDataSetActivity.ProfitTaobaoDetailData!=null) {
                fragmentProfitEstimateText.setText(TempDataSetActivity.ProfitTaobaoDetailData.get("taoDetailjiesuan"));
                fragmentProfitNumberMyText.setText(TempDataSetActivity.ProfitTaobaoDetailData.get("taoDetailMyNum"));
                fragmentProfitEstimateMyText.setText(TempDataSetActivity.ProfitTaobaoDetailData.get("taoDetailMyYugu"));
                fragmentProfitNumberTeamText.setText(TempDataSetActivity.ProfitTaobaoDetailData.get("taoDetailTeamNum"));
                fragmentProfitEstimateTeamText.setText(TempDataSetActivity.ProfitTaobaoDetailData.get("taoDetailTeamYugu"));
            }
        }
        tp=fragmentProfitEstimateText.getPaint();
        tp.setFakeBoldText(true);
        tp=fragmentProfitNumberMyText.getPaint();
        tp.setFakeBoldText(true);
        tp=fragmentProfitEstimateMyText.getPaint();
        tp.setFakeBoldText(true);
        tp=fragmentProfitNumberTeamText.getPaint();
        tp.setFakeBoldText(true);
        tp=fragmentProfitEstimateTeamText.getPaint();
        tp.setFakeBoldText(true);

    }

    @Override
    public void onCustomized() {

    }
    @Override
    protected void initData() {

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

}
