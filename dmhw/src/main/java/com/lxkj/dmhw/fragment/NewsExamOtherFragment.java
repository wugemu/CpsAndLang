package com.lxkj.dmhw.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.NewsExamDetailActivity;
import com.lxkj.dmhw.adapter.NewsExamAdapter;
import com.lxkj.dmhw.adapter.NewsExamOtherAdapter;
import com.lxkj.dmhw.bean.NewsExam;
import com.lxkj.dmhw.bean.NewsExamOther;
import com.lxkj.dmhw.defined.LazyFragment;
import com.lxkj.dmhw.dialog.NewsExamDialog;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.MyLayoutManager;
import com.lxkj.dmhw.utils.RiseNumberTextView;
import com.lxkj.dmhw.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 运营推广
 */

public class NewsExamOtherFragment extends LazyFragment implements BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.fragment_rank_exam_recycler)
    RecyclerView fragmentNewsExamRecycler;

    @BindView(R.id.profit_rank)
    TextView profit_rank;
    @BindView(R.id.rankimg)
    ImageView rankimg;
    @BindView(R.id.rank)
    TextView rank;

    private NewsExamOtherAdapter adapter;
    private List<NewsExamOther> list= new ArrayList<>();
    private View emptyView;
    public static NewsExamOtherFragment getInstance() {
        NewsExamOtherFragment fragment = new NewsExamOtherFragment();
        return fragment;
    }

    @Override
    public void onData() {
    }
    @Override
    protected void initData() {
        httpPost();
    }
    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exam_otherlist, null);
        ButterKnife.bind(this, view);
        emptyView=getLayoutInflater().inflate(R.layout.view_no_exam_empty, null);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }

    @Override
    public void onEvent() {
        fragmentNewsExamRecycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), false));
        adapter = new NewsExamOtherAdapter(getActivity(),true);
        fragmentNewsExamRecycler.setAdapter(adapter);
        // 设置预加载位置,倒数
        adapter.setPreLoadNumber(5);
        // 监听加载更多事件
        adapter.setOnLoadMoreListener(this, fragmentNewsExamRecycler);
        // 取消第一次加载回调
        adapter.disableLoadMoreIfNotFullPage();
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
        if(message.what==LogicActions.ExtensionMerchantListSuccess){
            JSONObject jsonObject=(JSONObject)message.obj;
            profit_rank.setText(jsonObject.optString("mny"));
            String type=jsonObject.optString("type");
            switch (type){
                case "0":
                    rankimg.setImageResource(R.mipmap.exam_rank_00);
                    rank.setText("普通会员");
                    break;
                case "1":
                    rankimg.setImageResource(R.mipmap.exam_rank_01);
                    rank.setText("青铜会员");
                    break;
                case "2":
                    rankimg.setImageResource(R.mipmap.exam_rank_02);
                    rank.setText("白银会员");
                    break;
                case "3":
                    rankimg.setImageResource(R.mipmap.exam_rank_03);
                    rank.setText("黄金会员");
                    break;
                case "4":
                    rankimg.setImageResource(R.mipmap.exam_rank_04);
                    rank.setText("铂金会员");
                    break;
                case "5":
                    rankimg.setImageResource(R.mipmap.exam_rank_05);
                    rank.setText("钻石会员");
                    break;
            }
            try {
                list= JSON.parseArray(jsonObject.getJSONArray("messagedata").toString(), NewsExamOther.class);
                if (list.size() > 0) {
                    if (page > 1) {
                        adapter.addData(list);
                    } else {
                        adapter.setNewData(list);
                    }
                    adapter.loadMoreComplete();
                } else {
                    adapter.loadMoreEnd();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            adapter.setEmptyView(emptyView);
            dismissDialog();
        }

    }


    /**
     * 网络请求
     */
    private void httpPost() {
        // 获取审批列表数据
        if (page==1){
            showDialog();
        }
        paramMap = new HashMap<>();
        paramMap.put("userid", login.getUserid());
        paramMap.put("startindex", page + "");
        paramMap.put("pagesize", pageSize + "");
        NetworkRequest.getInstance().POST(handler, paramMap, "ExtensionMerchantList", HttpCommon.ExtensionMerchantList);
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
}
