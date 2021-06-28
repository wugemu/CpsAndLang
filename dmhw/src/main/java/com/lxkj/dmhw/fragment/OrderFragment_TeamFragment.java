package com.lxkj.dmhw.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxkj.dmhw.activity.CommodityActivity290;
import com.lxkj.dmhw.activity.CommodityActivityPJW;
import com.lxkj.dmhw.activity.OrderActivity;
import com.lxkj.dmhw.adapter.OrderAdapterMorePl;
import com.lxkj.dmhw.bean.OrderMorePl;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.LazyFragment;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.adapter.OrderAdapter;
import com.lxkj.dmhw.bean.Order;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.MyLayoutManager;
import com.lxkj.dmhw.utils.Utils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 团队推广订单
 * Created by Administrator on 2017/12/25 0025.
 */

public class OrderFragment_TeamFragment extends LazyFragment implements PtrHandler, BaseQuickAdapter.RequestLoadMoreListener,BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.fragment_order_team_fragment_recycler)
    RecyclerView fragmentOrderTeamFragmentRecycler;
    @BindView(R.id.load_more_ptr_frame)
    PtrClassicFrameLayout loadMorePtrFrame;
    private OrderAdapter adapter;
    private int number;
    private Order order;
    private String startTime = "", endTime = "";
    private View emptyView;
    private OrderAdapterMorePl adapterMore;
    public static OrderFragment_TeamFragment getInstance(int number) {
        OrderFragment_TeamFragment fragment = new OrderFragment_TeamFragment();
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
        View view = inflater.inflate(R.layout.fragment_order_team_fragment, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {
        // 设置Recycler
        emptyView=getLayoutInflater().inflate(R.layout.view_empty, null);
        ImageView image=emptyView.findViewById(R.id.empty_img);
        TextView textView1=emptyView.findViewById(R.id.empty_txt);
        TextView textView2=emptyView.findViewById(R.id.empty_txt2);
        image.setImageResource(R.mipmap.no_order);
        textView1.setText(getString(R.string.order_empty_txt1));
        textView2.setText(getString(R.string.order_empty_txt2));
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));


        if (OrderActivity.platForm==0){//淘宝
            fragmentOrderTeamFragmentRecycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), false));
            adapter = new OrderAdapter(getActivity(), number, false);
            fragmentOrderTeamFragmentRecycler.setAdapter(adapter);
            adapter.setIsRate(DateStorage.getInformation().getRate());
            // 设置预加载位置,倒数
            adapter.setPreLoadNumber(5);
            // 监听加载更多事件
            adapter.setOnLoadMoreListener(this, fragmentOrderTeamFragmentRecycler);
            // 取消第一次加载回调
            adapter.disableLoadMoreIfNotFullPage();
            adapter.setOnItemClickListener(new OrderAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Order.OrderList data, int type) {
                    //跳转全网搜的商品详情
                    Order.OrderList orderlist = data;
                    startActivity(new Intent(getActivity(), CommodityActivity290.class).putExtra("shopId", data.getShopid()).putExtra("source", "tb").putExtra("sourceId",""));
                }
            });
        }else{//多平台
            fragmentOrderTeamFragmentRecycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), false));
            adapterMore = new OrderAdapterMorePl(getActivity(),number,OrderActivity.platForm,0);
            fragmentOrderTeamFragmentRecycler.setAdapter(adapterMore);
            adapterMore.setIsRate(DateStorage.getInformation().getRate());
            // 设置预加载位置,倒数
            adapterMore.setPreLoadNumber(5);
            // 监听加载更多事件
            adapterMore.setOnLoadMoreListener(this, fragmentOrderTeamFragmentRecycler);
            // 取消第一次加载回调
            adapterMore.disableLoadMoreIfNotFullPage();
            adapterMore.setOnItemClickListener(new OrderAdapterMorePl.OnItemClickListener() {
                @Override
                public void onItemClick(OrderMorePl.OrderList data, int type) {
                    //跳转相应的平台详情页面
                    OrderMorePl.OrderList orderlist = data;
                    switch (OrderActivity.platForm){
                        case 1:
                            startActivity(new Intent(getActivity(), CommodityActivityPJW.class).putExtra("GoodsId", orderlist.getGoodsId()).putExtra("type", "pdd"));
                            break;
                        case 2:
                            startActivity(new Intent(getActivity(), CommodityActivityPJW.class).putExtra("GoodsId", orderlist.getGoodsId()).putExtra("type", "jd"));
                            break;
                        case 3:
                            startActivity(new Intent(getActivity(), CommodityActivityPJW.class).putExtra("GoodsId", orderlist.getGoodsId()).putExtra("type", "wph"));
                            break;
                        case 5:
                            startActivity(new Intent(getActivity(), CommodityActivityPJW.class).putExtra("GoodsId", orderlist.getGoodsId()).putExtra("type", "sn"));
                            break;
                        case 6:
                            startActivity(new Intent(getActivity(), CommodityActivityPJW.class).putExtra("GoodsId", orderlist.getGoodsId()).putExtra("type", "kl"));
                            break;
                    }
                }
            });
        }


    }

    @Override
    public void onCustomized() {
        ptrFrameLayout();
    }

    @Override
    public void mainMessage(Message message) {

    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        loadMorePtrFrame.refreshComplete();
        if (message.what == LogicActions.GetOrderSuccess) {
            order = (Order) message.obj;
            if (order.getOrderdata().size() > 0) {
                if (order.getOrderdata().size()<pageSize){
                    adapter.setHasNoData(1);
                    if (page > 1) {
                        adapter.addData(order.getOrderdata());
                        adapter.notifyDataSetChanged();
                    } else {
                        adapter.setNewData(order.getOrderdata());
                    }
                    adapter.loadMoreEnd();
                }else{
                    if (page > 1) {
                        adapter.addData(order.getOrderdata());
                        adapter.notifyDataSetChanged();
                    } else {
                        adapter.setNewData(order.getOrderdata());
                    }
                    adapter.loadMoreComplete();
                }
            } else {
                adapter.setHasNoData(1);
                adapter.loadMoreEnd();
            }
            dismissDialog();
            adapter.setEmptyView(emptyView);
        }

        //多平台订单列表
        if (message.what == LogicActions.MorePlTeamGetOrderSuccess) {
            OrderMorePl ordermore = (OrderMorePl) message.obj;
            if (ordermore.getData().size() > 0) {
                if (ordermore.getData().size()<pageSize){
                    adapterMore.setHasNoData(1);
                    if (page > 1) {
                        adapterMore.addData(ordermore.getData());
                    } else {
                        adapterMore.setNewData(ordermore.getData());
                    }
                    adapterMore.loadMoreEnd();
                }else{
                    if (page > 1) {
                        adapterMore.addData(ordermore.getData());
                    } else {
                        adapterMore.setNewData(ordermore.getData());
                    }
                    adapterMore.loadMoreComplete();
                }
            } else {
                adapterMore.setHasNoData(1);
                adapterMore.loadMoreEnd();
            }
            dismissDialog();
            //添加空视图
            adapterMore.setEmptyView(emptyView);
        }
    }

    /**
     * 日期筛选
     * @param startTime
     * @param endTime
     */
    public void calendar(String startTime, String endTime) {
        this.startTime = startTime + " 00:00:00";
        this.endTime = endTime + " " + Utils.getCurrentDate("HH:mm:ss");
        loadMorePtrFrame.autoRefresh();
    }

    /**
     * 网络请求
     *
     * @param time
     */
    boolean isfirst =true;
    private void httpPost(String time) {
        if (page ==1&&isfirst){
            isfirst=false;
            showDialog();
        }
        if (OrderActivity.platForm==0) {
            // 获取订单
            paramMap = new HashMap<>();
            paramMap.put("userid", login.getUserid());
            paramMap.put("ordertype", "01");
            switch (number) {
                case 0:
                    paramMap.put("orderstatus", "00");
                    break;
                case 1:
                    paramMap.put("orderstatus", "01");
                    break;
                case 2:
                    paramMap.put("orderstatus", "02");
                    break;
                case 3:
                    paramMap.put("orderstatus", "03");
                    break;
                case 4:
                    paramMap.put("orderstatus", "04");
                    break;
            }
            paramMap.put("orderstarttime", startTime);
            paramMap.put("orderendtime", endTime);
            paramMap.put("startindex", page + "");
            paramMap.put("searchtime", time);
            paramMap.put("pagesize", pageSize + "");
            NetworkRequest.getInstance().POST(handler, paramMap, "GetOrder", HttpCommon.GetOrder);
        }else{
          //其他平台订单
            switch (number) {
                case 0:
                    paramMap.put("orderStatus", "");
                    break;
                case 1:
                    paramMap.put("orderStatus", "1");
                    break;
                case 2:
                    paramMap.put("orderStatus", "2");
                    break;
                case 3:
                    paramMap.put("orderStatus", "3");
                    break;
                case 4:
                    paramMap.put("orderStatus", "4");
                    break;

            }
            paramMap.put("endTime", endTime);
            paramMap.put("startTime", startTime);
            paramMap.put("page", page + "");
            paramMap.put("pagesize", pageSize + "");
            switch(OrderActivity.platForm){
                case 1:
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "MorePlTeamGetOrder", HttpCommon.PDDTeamOrderList);
                    break;
                case 2:
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "MorePlTeamGetOrder", HttpCommon.JDTeamOrderList);
                    break;
                case 3:
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "MorePlTeamGetOrder", HttpCommon.WPHTeamOrderList);
                    break;
                case 4:
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "MorePlTeamGetOrder", HttpCommon.MTTeamOrderList);
                    break;
                case 5:
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "MorePlTeamGetOrder", HttpCommon.SNTeamOrderList);
                    break;
                case 6:
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "MorePlTeamGetOrder", HttpCommon.KLTeamOrderList);
                    break;

            }

        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    /**
     * 下拉刷新设置
     */
    private void ptrFrameLayout() {
        loadMorePtrFrame.setLoadingMinTime(700);
        loadMorePtrFrame.setBackgroundColor(Color.parseColor("#f2f2f2"));
        loadMorePtrFrame.setHeaderView(loadView);
        loadMorePtrFrame.addPtrUIHandler(loadView);
        loadView.setBackgroundColor(Color.parseColor("#f2f2f2"));
        loadView.setTextColor(Color.parseColor("#000000"));
        loadMorePtrFrame.setPtrHandler(this);
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        httpPost("");
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, fragmentOrderTeamFragmentRecycler, header);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        page = 1;
        httpPost("");
    }

    @Override
    protected void initData() {
//        startTime = ((OrderActivity) getActivity()).getStart();
//        endTime = ((OrderActivity) getActivity()).getEnd();
        page = 1;
        httpPost("");
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
