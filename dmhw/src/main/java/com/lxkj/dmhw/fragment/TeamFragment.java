package com.lxkj.dmhw.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.adapter.TeamAdapter;
import com.lxkj.dmhw.bean.AgentList;
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
import butterknife.OnClick;

/**
 * 我的团队
 * Created by Administrator on 2017/12/18 0018.
 */

public class TeamFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.fragment_team_recycler)
    RecyclerView fragmentTeamRecycler;
    @BindView(R.id.fragment_team_title_one_image)
    ImageView fragmentTeamTitleOneImage;
    @BindView(R.id.fragment_team_title_one)
    LinearLayout fragmentTeamTitleOne;
    @BindView(R.id.fragment_team_title_two_image)
    ImageView fragmentTeamTitleTwoImage;
    @BindView(R.id.fragment_team_title_two)
    LinearLayout fragmentTeamTitleTwo;
    @BindView(R.id.fragment_team_title_three_image)
    ImageView fragmentTeamTitleThreeImage;
    @BindView(R.id.fragment_team_title_three)
    LinearLayout fragmentTeamTitleThree;
    private TeamAdapter adapter;
    private AgentList agentList = new AgentList();
    private int number;
    private int positionOne = 2;
    private int positionTwo = 3;
    private int positionThree = 3;
    private String sort = "01";

    public static TeamFragment getInstance(int number) {
        TeamFragment fragment = new TeamFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("number", number);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            number = bundle.getInt("number");
        }
    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {
        page = 1;
        // 设置Recycler
        fragmentTeamRecycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), false));
        adapter = new TeamAdapter(getActivity());
        fragmentTeamRecycler.setAdapter(adapter);
        // 设置预加载位置,倒数
        adapter.setPreLoadNumber(5);
        // 监听加载更多事件
        adapter.setOnLoadMoreListener(this, fragmentTeamRecycler);
        // 取消第一次加载回调
        adapter.disableLoadMoreIfNotFullPage();
    }

    @Override
    public void onCustomized() {
        httpPost(agentList.getSearchtime());
    }

    @Override
    public void mainMessage(Message message) {

    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        if (page == 1) {
            dismissDialog();
        }
        if (message.what == LogicActions.AgentOneSuccess) {
            agentList = (AgentList) message.obj;
            if (agentList.getUserdata().size() > 0) {
                if (page > 1) {
                    adapter.addData(agentList.getUserdata());
                    adapter.notifyDataSetChanged();
                } else {
                    adapter.setNewData(agentList.getUserdata());
                    adapter.notifyDataSetChanged();
                }
                adapter.loadMoreComplete();
            } else {

                adapter.loadMoreEnd();
            }
        }
        if (message.what == LogicActions.AgentTwoSuccess) {
            agentList = (AgentList) message.obj;
            if (agentList.getUserdata().size() > 0) {
                if (page > 1) {
                    adapter.addData(agentList.getUserdata());
                    adapter.notifyDataSetChanged();
                } else {
                    adapter.setNewData(agentList.getUserdata());
                    adapter.notifyDataSetChanged();
                }
                adapter.loadMoreComplete();
            } else {

                adapter.loadMoreEnd();
            }
        }
    }

    private void httpPost(String time) {
        paramMap = new HashMap<>();
        paramMap.put("userid", login.getUserid());
        paramMap.put("startindex", page + "");
        paramMap.put("pagesize", pageSize + "");
        paramMap.put("searchtime", time);
        paramMap.put("sortdesc", sort);
        switch (number) {
            case 0:
                NetworkRequest.getInstance().POST(handler, paramMap, "AgentOne", HttpCommon.AgentOne);
                break;
            case 1:
                NetworkRequest.getInstance().POST(handler, paramMap, "AgentTwo", HttpCommon.AgentTwo);
                break;
        }
        if (page == 1) {
            adapter.setNewData(new ArrayList<>());
            adapter.notifyDataSetChanged();
            showDialog();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        httpPost(agentList.getSearchtime());
    }

    @OnClick({R.id.fragment_team_title_one, R.id.fragment_team_title_two, R.id.fragment_team_title_three})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_team_title_one:
                positionTwo = 3;
                positionThree = 3;
                fragmentTeamTitleTwoImage.setImageResource(R.mipmap.fragment_team_default);
                fragmentTeamTitleThreeImage.setImageResource(R.mipmap.fragment_team_default);
                switch (positionOne) {
                    case 1:
                        fragmentTeamTitleOneImage.setImageResource(R.mipmap.fragment_team_bottom);
                        positionOne = 2;
                        sort = "01";
                        page = 1;
                        httpPost(agentList.getSearchtime());
                        break;
                    case 2:
                        fragmentTeamTitleOneImage.setImageResource(R.mipmap.fragment_team_top);
                        positionOne = 1;
                        sort = "00";
                        page = 1;
                        httpPost(agentList.getSearchtime());
                        break;
                    case 3:
                        fragmentTeamTitleOneImage.setImageResource(R.mipmap.fragment_team_bottom);
                        positionOne = 2;
                        sort = "01";
                        page = 1;
                        httpPost(agentList.getSearchtime());
                        break;
                }
                break;
            case R.id.fragment_team_title_two:
                positionOne = 3;
                positionThree = 3;
                fragmentTeamTitleOneImage.setImageResource(R.mipmap.fragment_team_default);
                fragmentTeamTitleThreeImage.setImageResource(R.mipmap.fragment_team_default);
                switch (positionTwo) {
                    case 1:
                        fragmentTeamTitleTwoImage.setImageResource(R.mipmap.fragment_team_bottom);
                        positionTwo = 2;
                        sort = "11";
                        page = 1;
                        httpPost(agentList.getSearchtime());
                        break;
                    case 2:
                        fragmentTeamTitleTwoImage.setImageResource(R.mipmap.fragment_team_top);
                        positionTwo = 1;
                        sort = "10";
                        page = 1;
                        httpPost(agentList.getSearchtime());
                        break;
                    case 3:
                        fragmentTeamTitleTwoImage.setImageResource(R.mipmap.fragment_team_bottom);
                        positionTwo = 2;
                        sort = "11";
                        page = 1;
                        httpPost(agentList.getSearchtime());
                        break;
                }
                break;
            case R.id.fragment_team_title_three:
                positionOne = 3;
                positionTwo = 3;
                fragmentTeamTitleOneImage.setImageResource(R.mipmap.fragment_team_default);
                fragmentTeamTitleTwoImage.setImageResource(R.mipmap.fragment_team_default);
                switch (positionThree) {
                    case 1:
                        fragmentTeamTitleThreeImage.setImageResource(R.mipmap.fragment_team_bottom);
                        positionThree = 2;
                        sort = "21";
                        page = 1;
                        httpPost(agentList.getSearchtime());
                        break;
                    case 2:
                        fragmentTeamTitleThreeImage.setImageResource(R.mipmap.fragment_team_top);
                        positionThree = 1;
                        sort = "20";
                        page = 1;
                        httpPost(agentList.getSearchtime());
                        break;
                    case 3:
                        fragmentTeamTitleThreeImage.setImageResource(R.mipmap.fragment_team_bottom);
                        positionThree = 2;
                        sort = "21";
                        page = 1;
                        httpPost(agentList.getSearchtime());
                        break;
                }
                break;
        }
    }
}
