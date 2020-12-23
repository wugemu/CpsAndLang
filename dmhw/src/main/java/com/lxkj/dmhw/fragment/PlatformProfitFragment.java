package com.lxkj.dmhw.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.TempDataSetActivity;
import com.lxkj.dmhw.adapter.PlTimeItemAdapter;
import com.lxkj.dmhw.bean.ProfitEveryPL;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.defined.LazyFragment;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.MyLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 多平台收益推广数据
 */

public class PlatformProfitFragment extends LazyFragment implements BaseQuickAdapter.OnItemClickListener{

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
    @BindView(R.id.search_time_recycler)
    RecyclerView search_time_recycler;
    private String type="";
    private ArrayList<ProfitEveryPL> profitEveryPLS;
    PlTimeItemAdapter plTimeItemAdapter;
    TextPaint tp;
    public static PlatformProfitFragment getInstance(String type) {
        PlatformProfitFragment fragment = new PlatformProfitFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getString("type");
        }
    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_platform_profit, null);
        ButterKnife.bind(this, view);
        // 设置Recycler
        search_time_recycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), true));
        // 初始化adapter
        plTimeItemAdapter = new PlTimeItemAdapter(getActivity());
        search_time_recycler.setAdapter(plTimeItemAdapter);
        plTimeItemAdapter.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onEvent() {
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
    protected void initData() {
        // 推广数据
        paramMap = new HashMap<>();
        paramMap.put("userid", login.getUserid());
        paramMap.put("cpsType", type);
        NetworkRequest.getInstance().GETNew(handler, paramMap, "ProfitCpsInfo", HttpCommon.ProfitCpsInfo);
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
        if (message.what == LogicActions.ProfitCpsInfoSuccess) {
            profitEveryPLS = (ArrayList<ProfitEveryPL>) message.obj;
            if (!TempDataSetActivity.isMoreCanEdit) {
                if (profitEveryPLS.size() > 0) {
                    //填充查询时间
                    profitEveryPLS.get(0).setCheck(true);
                    plTimeItemAdapter.setNewData(profitEveryPLS);
                    fragmentProfitEstimateText.setText(((JSONObject) profitEveryPLS.get(0).getSettled()).getString("mny"));
                    fragmentProfitNumberMyText.setText(((JSONObject) profitEveryPLS.get(0).getSelf()).getString("cnt"));
                    fragmentProfitEstimateMyText.setText(((JSONObject) profitEveryPLS.get(0).getSelf()).getString("mny"));
                    fragmentProfitNumberTeamText.setText(((JSONObject) profitEveryPLS.get(0).getTeam()).getString("cnt"));
                    fragmentProfitEstimateTeamText.setText(((JSONObject) profitEveryPLS.get(0).getTeam()).getString("mny"));
                }
            }else{
                if (TempDataSetActivity.ProfitMoreDetailData!=null) {
                    fragmentProfitEstimateText.setText(TempDataSetActivity.ProfitMoreDetailData.get("moreDetailjiesuan"));
                    fragmentProfitNumberMyText.setText(TempDataSetActivity.ProfitMoreDetailData.get("moreDetailMyNum"));
                    fragmentProfitEstimateMyText.setText(TempDataSetActivity.ProfitMoreDetailData.get("moreDetailMyYugu"));
                    fragmentProfitNumberTeamText.setText(TempDataSetActivity.ProfitMoreDetailData.get("moreDetailTeamNum"));
                    fragmentProfitEstimateTeamText.setText(TempDataSetActivity.ProfitMoreDetailData.get("moreDetailTeamYugu"));
                }

            }
            dismissDialog();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
       for(int i=0; i<profitEveryPLS.size();i++){
        profitEveryPLS.get(i).setCheck(false);
       }
       profitEveryPLS.get(position).setCheck(true);
       plTimeItemAdapter.setNewData(profitEveryPLS);

        fragmentProfitEstimateText.setText(((JSONObject) profitEveryPLS.get(position).getSettled()).getString("mny"));
        fragmentProfitNumberMyText.setText(((JSONObject) profitEveryPLS.get(position).getSelf()).getString("cnt"));
        fragmentProfitEstimateMyText.setText(((JSONObject) profitEveryPLS.get(position).getSelf()).getString("mny"));
        fragmentProfitNumberTeamText.setText(((JSONObject) profitEveryPLS.get(position).getTeam()).getString("cnt"));
        fragmentProfitEstimateTeamText.setText(((JSONObject) profitEveryPLS.get(position).getTeam()).getString("mny"));

    }

}
