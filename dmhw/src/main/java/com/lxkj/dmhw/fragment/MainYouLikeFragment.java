package com.lxkj.dmhw.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.CommodityActivity290;
import com.lxkj.dmhw.adapter.MyAdapter290;
import com.lxkj.dmhw.bean.MainBottomListItem;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.defined.LazyFragment;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.GridSpacingItemDecoration;
import com.lxkj.dmhw.utils.MyItemDecoration;
import com.lxkj.dmhw.utils.MyLayoutManager;
import com.lxkj.dmhw.utils.NetStateUtils;
import com.lxkj.dmhw.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *首页底部导航的页面
 * Created by Android on 2019/12/27.
 */

public class MainYouLikeFragment extends BaseFragment {

    @BindView(R.id.fragment_nav_recycler)
    RecyclerView fragment_nav_recycler;
    @BindView(R.id.topsplit)
    View topsplit;
    private MyAdapter290 adapter;
    private ArrayList<MainBottomListItem> lingquanList=new ArrayList<>();
    private boolean isCheck = true;
    //页面类型
    private String type;
    //登录刷新标志
    private  boolean loginRefreshFlag=false;

    public static MainYouLikeFragment getInstance(String type) {
        MainYouLikeFragment fragment = new MainYouLikeFragment();
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
        View view = inflater.inflate(R.layout.fragment_main_nav, null);
        ButterKnife.bind(this, view);
        return view;
    }
    @Override
    public void onEvent() {
        Utils.checkPermision(getActivity(),1000,false);
        if (type.equals("")){
            topsplit.setVisibility(View.GONE);
        }else{
            topsplit.setVisibility(View.GONE);
        }
        fragment_nav_recycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), 2));
        fragment_nav_recycler.addItemDecoration(new MyItemDecoration(getActivity(),Utils.dipToPixel(R.dimen.dp_5),Utils.dipToPixel(R.dimen.dp_10)));
        adapter = new MyAdapter290(getActivity(),"tb");
        fragment_nav_recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyAdapter290.OnItemClickListener() {
            @Override
            public void onItemClick(int position, MainBottomListItem data) {
                startActivity(new Intent(getActivity(), CommodityActivity290.class).putExtra("shopId", data.getId()).putExtra("source", data.getSource()).putExtra("sourceId", data.getSourceId()));
            }
        });
        fragment_nav_recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = fragment_nav_recycler.getLayoutManager();
                //判断是当前layoutManager是否为LinearLayoutManager
                // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    //获取最后一个可见view的位置
                    int lastItemPosition = linearManager.findLastVisibleItemPosition();
                    //获取第一个可见view的位置
                    int firstItemPosition = linearManager.findFirstVisibleItemPosition();
                    if(firstItemPosition >0)
                    {
                        if (isCheck) {
                            if (firstItemPosition >= (adapter.getItemCount() -8)) {
                                page++;
                                isCheck = false;
                                httpPost();
                            }
                        }

                    }
                }
            }
        });

    }
    @Override
    public void onCustomized() {
        page = 1;
        httpPost();
    }

    @Override
    public void mainMessage(Message message) {
        //登录之后刷新页面 该页面标记成可刷新
        if (message.what == LogicActions.LoginStatusSuccess&&(Boolean) message.obj) {
            loginRefreshFlag=true;
        }
    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        if (message.what == LogicActions.ShopLikeListSuccess) {
            lingquanList = (ArrayList<MainBottomListItem>) message.obj;
            if (lingquanList.size() > 0) {
                if (page > 1) {
                    adapter.addDatas(lingquanList,1);
                } else {
                    adapter.addDatas(lingquanList,0);
                }
                isCheck = true;
            } else {
                isCheck = false;
            }
        }
    }

    private void httpPost() {
     if (NetStateUtils.isNetworkConnected(getActivity())) {
         // 首页底部列表请求商品搜索（标签）
         paramMap = new HashMap<>();
         paramMap.put("userid", login.getUserid());
         paramMap.put("startindex", page + "");
         paramMap.put("pagesize", pageSize + "");
         paramMap.put("material", type);
         NetworkRequest.getInstance().POST(handler, paramMap, "ShopLikeList", HttpCommon.MainBottomList);
     }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (loginRefreshFlag&&OneFragment290.currPos==1){
            loginRefreshFlag=false;
            onCustomized();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

}
