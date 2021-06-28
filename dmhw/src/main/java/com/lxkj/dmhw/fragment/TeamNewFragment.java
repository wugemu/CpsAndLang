package com.lxkj.dmhw.fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Message;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.ChooseDataActivity;
import com.lxkj.dmhw.activity.PosterActivity;
import com.lxkj.dmhw.adapter.TeamNewAdapter;
import com.lxkj.dmhw.bean.MyTeamTotalDetailnfo;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.dialog.TeamMenberDetailDialog;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.MyLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的团队数据明细
 * Created by Administrator on 2017/12/18 0018.
 */

public class TeamNewFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener {

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
    @BindView(R.id.top_split_01)
    TextView top_split_01;
    @BindView(R.id.top_split_02)
    TextView top_split_02;
    @BindView(R.id.top_split_03)
    TextView top_split_03;
    private TeamNewAdapter adapter;
    private ArrayList<MyTeamTotalDetailnfo> menberList;

    private ArrayList<MyTeamTotalDetailnfo> totalMenberList=new ArrayList<>();

    private int positionOne = 2;
    private int positionTwo = 3;
    private int positionThree = 3;

    private String  userid;
    private int index;//请求的索引
    private String  key="";//手机号码或者昵称

    private String  startdate="";//加入起始时间
    private String  enddate="";//加入截止时间
    private String  startcnt="";//起始会员数量
    private String  endcnt="";//截止会员数量
    private String  startpre="";//起始本月预估
    private String  endpre="";//截至本月预估
    private String  startmonth="";//起始上月预估
    private String  endmonth="";//起始上月预估
    private String  startprofit="";//起始累计收益
    private String  endprofit="";//截止累计收益
    private String order="createtime";//createtime加入时间，acnt会员数，preamount本月预估
    private String sort="desc";//asc升序，desc降序
    // 筛选条件
     private ArrayList<String> list = new ArrayList<>();
     private View emptyView;

    public static TeamNewFragment getInstance(int index) {
        TeamNewFragment fragment = new TeamNewFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            index = bundle.getInt("index");
        }
    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_team, null);
        ButterKnife.bind(this, view);
        top_split_01.setTextColor(getResources().getColor(R.color.black));
        top_split_01.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        return view;
    }

    @Override
    public void onEvent() {
        page = 1;
        // 设置Recycler

        emptyView=getLayoutInflater().inflate(R.layout.view_no_agent_empty, null);
        ImageView image=emptyView.findViewById(R.id.an_img);
        TextView textView1=emptyView.findViewById(R.id.an_txt);
        Button btn=emptyView.findViewById(R.id.refresh_btn);
        image.setImageResource(R.mipmap.no_agent);
        textView1.setText(getString(R.string.agnet_empty_txt1));
        btn.setText(getString(R.string.agnet_empty_txt2));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PosterActivity.class));
            }
        });


        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));


        fragmentTeamRecycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), false));
        adapter = new TeamNewAdapter(getActivity());
        fragmentTeamRecycler.setAdapter(adapter);
        // 设置预加载位置,倒数
        adapter.setPreLoadNumber(5);
        // 监听加载更多事件
        adapter.setOnLoadMoreListener(this, fragmentTeamRecycler);
        // 取消第一次加载回调
        adapter.disableLoadMoreIfNotFullPage();
        adapter.setOnItemClickListener(this);
        userid=login.getUserid();//获取用户登录信息
    }

    @Override
    public void onCustomized() {
        httpPost();
    }

    @Override
    public void mainMessage(Message message) {
        if (message.what == LogicActions.ChooseDataSuccess) {
            showDialog();
            list = (ArrayList<String>) message.obj;
            key=list.get(0);
            startdate=list.get(1);
            enddate=list.get(2);
            startcnt=list.get(3);
            endcnt=list.get(4);
            startpre=list.get(5);
            endpre=list.get(6);
            startmonth=list.get(7);
            endmonth=list.get(8);
            startprofit=list.get(9);
            endprofit=list.get(10);
            page=1;
             httpPost();
            //执行获取总数据
        }
    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        if (page == 1) {
            dismissDialog();
        }
        if (message.what == LogicActions.MyTeamHistoryDetailSuccess) {
            menberList = (ArrayList<MyTeamTotalDetailnfo>) message.obj;
            if (menberList.size() > 0) {
                if (page > 1) {
                    adapter.addData(menberList);
                    adapter.notifyDataSetChanged();
                } else {
                    adapter.setNewData(menberList);
                    adapter.notifyDataSetChanged();
                }
                adapter.loadMoreComplete();
            } else {

                adapter.loadMoreEnd();
            }
        }
        totalMenberList.addAll(menberList);
        adapter.setEmptyView(emptyView);
    }


    //请求团队数据明细
    private void httpPost() {
        if (page==1){
            totalMenberList.clear();
            showDialog();
        }
        paramMap = new HashMap<>();
        paramMap.put("userid",login.getUserid());
        paramMap.put("index",index+"");
        paramMap.put("startindex", page + "");
        paramMap.put("pagesize", pageSize + "");
        paramMap.put("key",key);
        paramMap.put("startdate",startdate);
        paramMap.put("enddate",enddate);
        paramMap.put("startcnt",startcnt);
        paramMap.put("endcnt",endcnt);
        paramMap.put("startpre",startpre);
        paramMap.put("endpre",endpre);
        paramMap.put("startmonth",startmonth);
        paramMap.put("endmonth",endmonth);
        paramMap.put("startprofit",startprofit);
        paramMap.put("endprofit",endprofit);
        paramMap.put("order",order);
        paramMap.put("sort",sort);
        NetworkRequest.getInstance().POST(handler, paramMap, "MyTeamHistoryDetail", HttpCommon.MyTeamHistoryDetail);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        httpPost();
    }

    @OnClick({R.id.fragment_team_title_one, R.id.fragment_team_title_two, R.id.fragment_team_title_three,R.id.fragment_team_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_team_title_one:
                order="createtime";
                positionTwo = 3;
                positionThree = 3;
                fragmentTeamTitleTwoImage.setImageResource(R.mipmap.fragment_team_default);
                fragmentTeamTitleThreeImage.setImageResource(R.mipmap.fragment_team_default);
                top_split_01.setTextColor(getResources().getColor(R.color.black));
                top_split_01.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                top_split_02.setTextColor(getResources().getColor(R.color.color_666666));
                top_split_03.setTextColor(getResources().getColor(R.color.color_666666));
                switch (positionOne) {
                    case 1:
                        fragmentTeamTitleOneImage.setImageResource(R.mipmap.fragment_team_bottom);
                        positionOne = 2;
                        sort = "desc";
                        page = 1;
                        httpPost();
                        break;
                    case 2:
                        fragmentTeamTitleOneImage.setImageResource(R.mipmap.fragment_team_top);
                        positionOne = 1;
                        sort = "asc";
                        page = 1;
                        httpPost();
                        break;
                    case 3:
                        fragmentTeamTitleOneImage.setImageResource(R.mipmap.fragment_team_bottom);
                        positionOne = 2;
                        sort = "desc";
                        page = 1;
                        httpPost();
                        break;
                }
                break;
            case R.id.fragment_team_title_two:
                order="acnt";
                positionOne = 3;
                positionThree = 3;
                fragmentTeamTitleOneImage.setImageResource(R.mipmap.fragment_team_default);
                fragmentTeamTitleThreeImage.setImageResource(R.mipmap.fragment_team_default);
                top_split_01.setTextColor(getResources().getColor(R.color.color_666666));
                top_split_02.setTextColor(getResources().getColor(R.color.black));
                top_split_02.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                top_split_03.setTextColor(getResources().getColor(R.color.color_666666));
                switch (positionTwo) {
                    case 1:
                        fragmentTeamTitleTwoImage.setImageResource(R.mipmap.fragment_team_bottom);
                        positionTwo = 2;
                        sort = "desc";
                        page = 1;
                        httpPost();
                        break;
                    case 2:
                        fragmentTeamTitleTwoImage.setImageResource(R.mipmap.fragment_team_top);
                        positionTwo = 1;
                        sort = "asc";
                        page = 1;
                        httpPost();
                        break;
                    case 3:
                        fragmentTeamTitleTwoImage.setImageResource(R.mipmap.fragment_team_bottom);
                        positionTwo = 2;
                        sort = "desc";
                        page = 1;
                        httpPost();
                        break;
                }
                break;
            case R.id.fragment_team_title_three:
                order="preamount";
                positionOne = 3;
                positionTwo = 3;
                fragmentTeamTitleOneImage.setImageResource(R.mipmap.fragment_team_default);
                fragmentTeamTitleTwoImage.setImageResource(R.mipmap.fragment_team_default);
                top_split_01.setTextColor(getResources().getColor(R.color.color_666666));
                top_split_02.setTextColor(getResources().getColor(R.color.color_666666));
                top_split_03.setTextColor(getResources().getColor(R.color.black));
                top_split_03.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                switch (positionThree) {
                    case 1:
                        fragmentTeamTitleThreeImage.setImageResource(R.mipmap.fragment_team_bottom);
                        positionThree = 2;
                        sort = "desc";
                        page = 1;
                        httpPost();
                        break;
                    case 2:
                        fragmentTeamTitleThreeImage.setImageResource(R.mipmap.fragment_team_top);
                        positionThree = 1;
                        sort = "asc";
                        page = 1;
                        httpPost();
                        break;
                    case 3:
                        fragmentTeamTitleThreeImage.setImageResource(R.mipmap.fragment_team_bottom);
                        positionThree = 2;
                        sort = "desc";
                        page = 1;
                        httpPost();
                        break;
                }
                break;
            case R.id.fragment_team_search:
             startActivity(new Intent(getActivity(), ChooseDataActivity.class));
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        TeamMenberDetailDialog dialog = new TeamMenberDetailDialog(getActivity(),totalMenberList.get(position));
        dialog.showDialog();

    }
}
