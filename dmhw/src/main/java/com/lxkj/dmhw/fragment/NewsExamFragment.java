package com.lxkj.dmhw.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.NewsExamDetailActivity;
import com.lxkj.dmhw.adapter.NewsExamAdapter;
import com.lxkj.dmhw.bean.NewsExam;
import com.lxkj.dmhw.defined.LazyFragment;
import com.lxkj.dmhw.dialog.NewsExamDialog;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.MyLayoutManager;

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

public class NewsExamFragment extends LazyFragment implements BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.fragment_news_exam_recycler)
    RecyclerView fragmentNewsExamRecycler;
    private NewsExamAdapter adapter;
    private List<NewsExam> list= new ArrayList<>();
    private int number;
    private View emptyView;
    public static NewsExamFragment getInstance(int number) {
        NewsExamFragment fragment = new NewsExamFragment();
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
    protected void initData() {
        httpPost();
    }
    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exam_list, null);
        ButterKnife.bind(this, view);
        emptyView=getLayoutInflater().inflate(R.layout.view_no_exam_empty, null);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }

    @Override
    public void onEvent() {
        fragmentNewsExamRecycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), false));
        adapter = new NewsExamAdapter(getActivity(), number, true);
        fragmentNewsExamRecycler.setAdapter(adapter);
        // 设置预加载位置,倒数
        adapter.setPreLoadNumber(5);
        // 监听加载更多事件
        adapter.setOnLoadMoreListener(this, fragmentNewsExamRecycler);
        // 取消第一次加载回调
        adapter.disableLoadMoreIfNotFullPage();

        adapter.setOnItemClickListener(new NewsExamAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, NewsExam data,int type) {
                switch (type){
                    case 0:
                        Intent intent=new Intent(getActivity(), NewsExamDetailActivity.class);
                        intent.putExtra("examItemInfo", data);
                        if (number==1){
                         intent.putExtra("state", 0);   //待审批
                        }else{
                         intent.putExtra("state", 1);//待审批之外的状态
                        }
                        startActivity(intent);
                        break;
                    case 1:
                        NewsExamDialog newsExamDialog=new NewsExamDialog(getActivity(),"确定通过");
                        newsExamDialog.setSureClickListener(new NewsExamDialog.OnSureClickListener() {
                            @Override
                            public void onClick() {
                                showDialog();
                                paramMap.clear();
                                paramMap.put("msguserid", data.getMsguserid());
                                paramMap.put("state", "1");
                                NetworkRequest.getInstance().POST(handler, paramMap, "MerchantReqApprove", HttpCommon.MerchantReqApprove);
                            }
                        });
                        newsExamDialog.show();
                        break;
                    case 2:
                        showDialog();
                        NewsExamDialog newsExamDialog1=new NewsExamDialog(getActivity(),"确定拒绝");
                        newsExamDialog1.setSureClickListener(new NewsExamDialog.OnSureClickListener() {
                            @Override
                            public void onClick() {
                                paramMap.clear();
                                paramMap.put("msguserid", data.getMsguserid());
                                paramMap.put("state", "2");
                                NetworkRequest.getInstance().POST(handler, paramMap, "MerchantReqApprove", HttpCommon.MerchantReqApprove);
                            }
                        });
                        newsExamDialog1.show();
                        break;
                }
            }

        });
    }

    @Override
    public void onCustomized() {
    }

    @Override
    public void mainMessage(Message message) {
        if(message.what==LogicActions.MerchantReqApproveStatusSuccess){
            httpPost();
        }
    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        if(message.what==LogicActions.MerchantReqListSuccess){
            list= (List<NewsExam>) message.obj;
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
            adapter.setEmptyView(emptyView);
            dismissDialog();
        }
        if(message.what==LogicActions.MerchantReqApproveSuccess){
            dismissDialog();
            page=1;
            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("MerchantReqApproveStatus"), false, 0);
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
        paramMap.clear();
        paramMap.put("userid", login.getUserid());
        paramMap.put("startindex", page + "");
        paramMap.put("pagesize", pageSize + "");
        switch (number) {
            case 1://待审批
                paramMap.put("state", "0");
                break;
            case 2://已通过
                paramMap.put("state", "1");
                break;
            case 3://已拒绝
                paramMap.put("state", "2");
                break;
        }
        NetworkRequest.getInstance().POST(handler, paramMap, "MerchantReqList", HttpCommon.MerchantReqList);
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
