package com.lxkj.dmhw.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.BigBrandDetailActivity;
import com.lxkj.dmhw.adapter.BigBrandAdapter290;
import com.lxkj.dmhw.adapter.BrandListAdapter;
import com.lxkj.dmhw.bean.BigBrand;
import com.lxkj.dmhw.bean.BigBrandList;
import com.lxkj.dmhw.defined.LazyFragment;
import com.lxkj.dmhw.defined.PtrClassicRefreshLayout;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.MyLayoutManager;
import com.lxkj.dmhw.utils.Utils;
import com.lxkj.dmhw.view.StrongerHorizontalScrollView;
import com.tencent.mm.opensdk.utils.Log;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 大牌精选
 *
 */

public class BigBrandFragment extends LazyFragment implements PtrHandler, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener {
    //下拉刷新
    @BindView(R.id.load_more_ptr_frame)
    PtrClassicRefreshLayout loadMorePtrFrame;
    @BindView(R.id.fragment_bbrand_recycler)
    RecyclerView fragment_bbrand_recycler;


    StrongerHorizontalScrollView brand_content_layout_hs;

    private boolean isCheck = false;
    // 适配器
    private BigBrandAdapter290 adapter;
    private String id;
    private View header;
    private RecyclerView bigbrand_head_recycler;
    private SeekBar slide_seek;
    private LinearLayout bigbrand_head_layout;

    private ArrayList<BigBrand> bigBrandlist;
    private boolean isfirst=true;
    public static BigBrandFragment getInstance(String id) {
        BigBrandFragment fragment = new BigBrandFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString("id");
        }
    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bigbrand, null);
        header = View.inflate(getActivity(), R.layout.bigbrand_fragment_headview, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {
        bigbrand_head_layout= findViewHeader(R.id.bigbrand_head_layout);
        slide_seek= findViewHeader(R.id.slide_seek);
        bigbrand_head_recycler = findViewHeader(R.id.bigbrand_head_recycler);
        brand_content_layout_hs= findViewHeader(R.id.brand_content_layout_hs);
        bigbrand_head_recycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), true));
        bigbrand_head_recycler.setNestedScrollingEnabled(false);
        // 设置Recycler
        fragment_bbrand_recycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), false));
        adapter = new BigBrandAdapter290(getActivity());
        adapter.setHeaderView(header);
        fragment_bbrand_recycler.setAdapter(adapter);
        fragment_bbrand_recycler.setNestedScrollingEnabled(false);
        // 设置预加载位置,倒数
        adapter.setPreLoadNumber(5);
        // 监听item点击事件
        adapter.setOnItemClickListener(this);
        // 监听加载更多事件
        adapter.setOnLoadMoreListener(this, fragment_bbrand_recycler);
        // 取消第一次加载回调
        adapter.disableLoadMoreIfNotFullPage();

        ptrFrameLayout();
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
        if (message.what == LogicActions.BrandInfoListSuccess) {
            bigBrandlist = (ArrayList<BigBrand>) message.obj;
         if (bigBrandlist.size()>0)
         {
             bigbrand_head_layout.setVisibility(View.VISIBLE);
             BrandListAdapter brandListAdapter =new BrandListAdapter(getActivity(),(width-Utils.dipToPixel(R.dimen.dp_20))/5);
             bigbrand_head_recycler.setAdapter(brandListAdapter);
             brandListAdapter.addData(bigBrandlist);
             brandListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                 @Override
                 public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                     startActivity(new Intent(getActivity(), BigBrandDetailActivity.class).putExtra("id",bigBrandlist.get(position).getId()));
                 }
             });

             slide_seek.setPadding(0, 0, 0, 0);
             slide_seek.setThumbOffset(0);
             brand_content_layout_hs.setScrollViewListener(new StrongerHorizontalScrollView.ScrollViewListener() {
                 @Override
                 public void onScrollChanged(StrongerHorizontalScrollView scrollView, int x, int y, int oldx, int oldy) {
                     // 显示区域的高度。
                     int extent = scrollView.getWidth();
                     //整体的高度，注意是整体，包括在显示区域之外的。
                     int range = (width-Utils.dipToPixel(R.dimen.dp_20))/5*(bigBrandlist.size());
                     //已经向下滚动的距离，为0时表示已处于顶部。
                     int offset = scrollView.getScrollX();
                     //此处获取seekbar的getThumb，就是可以滑动的小的滚动游标
//                   GradientDrawable gradientDrawable =(GradientDrawable) slide_indicator_point.getThumb();
//                   //根据列表的个数，动态设置游标的大小，设置游标的时候，progress进度的颜色设置为和seekbar的颜色设置的一样的，所以就不显示进度了。
//                   gradientDrawable.setSize(Utils.dipToPixel(R.dimen.dp_14),Utils.dipToPixel(R.dimen.dp_4));
                     //设置可滚动区域
                     slide_seek.setMax((int)(range-extent));
                     if (x==0){
                         slide_seek.setProgress(0);
                     }else if (x>0){
                         slide_seek.setProgress(offset);
                     }else if (x<0){
                         slide_seek.setProgress(offset);
                     }
                 }

             });
         }else{
             bigbrand_head_layout.setVisibility(View.GONE);
         }
        }

        if (message.what == LogicActions.BrandAndGoodsListSuccess) {
            ArrayList<BigBrandList> list = (ArrayList<BigBrandList>) message.obj;
            if (list.size() > 0) {
                    if (page > 1) {
                        adapter.addData(list);
                    } else {
                        adapter.setNewData(list);
                    }
                adapter.loadMoreComplete();
            }else{
               adapter.loadMoreEnd();
            }
        }
        loadMorePtrFrame.refreshComplete();
        dismissDialog();
    }

    //推荐品牌商品列表
    private void httpPost() {
        paramMap.clear();
        paramMap.put("brandcat", id);
        paramMap.put("startindex", page + "");
        paramMap.put("pagesize", pageSize + "");
        NetworkRequest.getInstance().POST(handler, paramMap, "BrandAndGoodsList", HttpCommon.BrandAndGoodsList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    public <T extends View> T findViewHeader(int id) {
        return (T) header.findViewById(id);
    }

    @Override
    protected void initData() {
        if (isfirst){
            showDialog();
        }
        page=1;
        paramMap.clear();
        paramMap.put("brandcat", id);
        NetworkRequest.getInstance().POST(handler, paramMap, "BrandInfoList", HttpCommon.BrandInfoList);
        httpPost();
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, fragment_bbrand_recycler, header);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        isfirst=false;
        initData();
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
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        httpPost();
    }
}
