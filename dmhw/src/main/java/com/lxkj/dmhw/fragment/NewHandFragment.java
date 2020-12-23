package com.lxkj.dmhw.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.activity.BigBrandDetailActivity;
import com.lxkj.dmhw.activity.ComCollArticleDetailActivity;
import com.lxkj.dmhw.activity.LoginActivity;
import com.lxkj.dmhw.activity.VideoActivity300;
import com.lxkj.dmhw.adapter.BigBrandAdapter290;
import com.lxkj.dmhw.adapter.BrandListAdapter;
import com.lxkj.dmhw.adapter.ComCollNewHandAdapter;
import com.lxkj.dmhw.bean.BigBrand;
import com.lxkj.dmhw.bean.BigBrandList;
import com.lxkj.dmhw.bean.ComCollegeData;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.defined.LazyFragment;
import com.lxkj.dmhw.defined.PtrClassicRefreshLayout;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.MyLayoutManager;
import com.lxkj.dmhw.utils.Utils;
import com.lxkj.dmhw.view.StrongerHorizontalScrollView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 新手视频
 *
 */

public class NewHandFragment extends BaseFragment implements PtrHandler, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener {
    //下拉刷新
    @BindView(R.id.load_more_ptr_frame)
    PtrClassicRefreshLayout loadMorePtrFrame;
    @BindView(R.id.fragment_newhand_recycler)
    RecyclerView fragment_newhand_recycler;
    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    View back;
    @BindView(R.id.newhand_title)
    TextView newhand_title;

    // 适配器
    private ComCollNewHandAdapter adapter;
    private String id;
    private String from;
    private String title;
    private boolean isfirst=true;
    private View emptyView;
    public static NewHandFragment getInstance(String from,String id,String title) {
        NewHandFragment fragment = new NewHandFragment();
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        bundle.putString("id", id);
        bundle.putString("title", title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString("id");
            from = bundle.getString("from");
            title=bundle.getString("title");
        }
    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newhand, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {
        if (from.equals("partSearch")){
        emptyView=getLayoutInflater().inflate(R.layout.view_empty_search, null);
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        TextPaint textPaint=newhand_title.getPaint();
        textPaint.setFakeBoldText(true);
        newhand_title.setText(title);
        ptrFrameLayout();
        // 设置Recycler
        fragment_newhand_recycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), false));
        if (from.equals("partThree")){
            adapter = new ComCollNewHandAdapter(getActivity(),from,1);
        }else{
            adapter = new ComCollNewHandAdapter(getActivity(),from,0);
        }
        fragment_newhand_recycler.setAdapter(adapter);
        fragment_newhand_recycler.setNestedScrollingEnabled(false);
        // 设置预加载位置,倒数
        adapter.setPreLoadNumber(5);
        // 监听item点击事件
        adapter.setOnItemClickListener(this);
        // 监听加载更多事件
        adapter.setOnLoadMoreListener(this, fragment_newhand_recycler);
        // 取消第一次加载回调
        adapter.disableLoadMoreIfNotFullPage();

    }
    @OnClick({R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:// 返回
                isFinish();
                break;
        }
    }
    @Override
    public void onCustomized() {
        initData();
    }

    @Override
    public void mainMessage(Message message) {

    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        if (message.what == LogicActions.ArticleContentDataListSuccess) {
            ArrayList<ComCollegeData.NewHand> list = (ArrayList<ComCollegeData.NewHand>) message.obj;
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
        if (emptyView!=null&&from.equals("partSearch")) {
            adapter.setEmptyView(emptyView);
        }
        dismissDialog();
    }

    //新手入门列表
    private void httpPost() {
        paramMap = new HashMap<>();
        if (from.equals("partOne")){
            paramMap.put("typeid", id);
        }else if(from.equals("partTwo")||from.equals("partOneQues")||from.equals("partThree")){
            paramMap.put("label", id);
        }else{
            paramMap.put("search", id);
        }
        paramMap.put("startindex", page + "");
        paramMap.put("pagesize", pageSize + "");
        NetworkRequest.getInstance().POST(handler, paramMap, "ArticleContentDataList", HttpCommon.ArticleContentDataList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


    protected void initData() {
        if (isfirst){
            showDialog();
        }
        page=1;
        httpPost();
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, fragment_newhand_recycler, header);
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
        loadView.setTextColor(Color.BLACK);
        loadMorePtrFrame.setLoadingMinTime(700);
        loadMorePtrFrame.setHeaderView(loadView);
        loadMorePtrFrame.addPtrUIHandler(loadView);
        loadMorePtrFrame.setPtrHandler(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ComCollegeData.NewHand hotItem= (ComCollegeData.NewHand) adapter.getData().get(position);
        if (from.equals("partOneQues")){
            //跳转文章详情
            Intent intent=new Intent(getActivity(), ComCollArticleDetailActivity.class);
            intent.putExtra("from","Ques");
            intent.putExtra("articleId",hotItem.getId());
            startActivity(intent);
        }else{
            if (!DateStorage.getLoginStatus()){
                Intent intent =new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }else {
                if (hotItem.getType().equals("1")) {
                    //视频详情
                    Intent intent = new Intent(getActivity(), VideoActivity300.class);
                    intent.putExtra("id", hotItem.getId());
                    intent.putExtra("title", hotItem.getTitle());
                    startActivity(intent);
                } else {
                    //跳转文章详情
                    Intent intent = new Intent(getActivity(), ComCollArticleDetailActivity.class);
                    intent.putExtra("from", "NoQues");
                    intent.putExtra("articleId", hotItem.getId());
                    startActivity(intent);
                }
            }
        }
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        httpPost();
    }
}
