package com.lxkj.dmhw.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.adapter.FourFragmentAdapter;
import com.lxkj.dmhw.bean.Billboard;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 全民疯抢
 * Created by Android on 2018/6/9.
 */

public class FourFragment_Two extends BaseFragment implements AbsListView.OnScrollListener, PtrHandler {

    @BindView(R.id.load_more_ptr_frame)
    PtrClassicFrameLayout loadMorePtrFrame;
    @BindView(R.id.return_top)
    ImageView returnTop;
    @BindView(R.id.fragment_four_two_load_bottom)
    LinearLayout fragmentFourTwoLoadBottom;
    @BindView(R.id.fragment_four_two_list)
    ListView fragmentFourTwoList;
    private FourFragmentAdapter adapter;
    private boolean isCheck = true;
    private String time;
    private int position;

    public static FourFragment_Two getInstance(int number) {
        FourFragment_Two fragment = new FourFragment_Two();
        Bundle bundle = new Bundle();
        bundle.putInt("number", number);
        fragment.setArguments(bundle);
        isfirst=false;
        return fragment;
    }

    @Override
    public void onData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            position = bundle.getInt("number");
        }
    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_four_two, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {
        // 初始化adapter
        adapter = new FourFragmentAdapter(getActivity(), true);
        // 装入adapter
        fragmentFourTwoList.setAdapter(adapter);
        fragmentFourTwoList.setOnScrollListener(this);
        ptrFrameLayout();
        httpPost("");
    }

    @Override
    public void onCustomized() {

    }

    @Override
    public void mainMessage(Message message) {
        if (message.what == LogicActions.LoginStatusSuccess) {
            loadMorePtrFrame.autoRefresh();
        }
    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        dismissDialog();
        loadMorePtrFrame.refreshComplete();
        if (message.what == LogicActions.BillboardSuccess) {
            fragmentFourTwoLoadBottom.setVisibility(View.GONE);
            Billboard billboard = (Billboard) message.obj;
            time = billboard.getSearchtime();
            if (billboard.getShopdata().size() > 0) {
                if (page > 1) {
                    adapter.addData(billboard.getShopdata());
                } else {
                    adapter.setData(billboard.getShopdata());
                }
                isCheck = true;
            } else {

                isCheck = false;
            }
        }
    }
    private static boolean isfirst =true;
    private void httpPost(String time) {
        if (page ==1&&isfirst){
            isfirst=false;
            showDialog();
        }
        // 请求商品搜索（标签）
        paramMap = new HashMap<>();
        paramMap.put("userid", login.getUserid());
        paramMap.put("labeltype", "10");
        paramMap.put("startindex", page + "");
        paramMap.put("searchtime", time);
        paramMap.put("pagesize", pageSize + "");
        paramMap.put("shopclassone", position + "");
        paramMap.put("sortdesc", "20");
        paramMap.put("screendesc", "");
        paramMap.put("pricerange", "");
        NetworkRequest.getInstance().POST(handler, paramMap, "Billboard", HttpCommon.ShopLabel);
        if (page == 1) {
            adapter.setData(new ArrayList<>());
        }
    }

    /**
     * 下拉刷新设置
     */
    private void ptrFrameLayout() {
        loadView.setTextColor(Color.WHITE);
        loadMorePtrFrame.setLoadingMinTime(700);
        loadMorePtrFrame.setHeaderView(loadView);
        loadMorePtrFrame.addPtrUIHandler(loadView);
        loadMorePtrFrame.setPtrHandler(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem >= 1) {
            returnTop.setVisibility(View.VISIBLE);
            if (isCheck) {
                if ((adapter.getCount() - firstVisibleItem) <= 6) {
                    page++;
                    httpPost(time);
                    isCheck = false;
                    fragmentFourTwoLoadBottom.setVisibility(View.VISIBLE);
                }
            }
        } else {
            returnTop.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, fragmentFourTwoList, header);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        page = 1;
        httpPost("");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @OnClick(R.id.return_top)
    public void onViewClicked() {
        fragmentFourTwoList.setSelection(0);
    }
}
