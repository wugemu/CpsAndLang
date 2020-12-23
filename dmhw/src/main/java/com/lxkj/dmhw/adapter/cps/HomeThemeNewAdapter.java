package com.lxkj.dmhw.adapter.cps;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.test.andlang.andlangutil.BaseLangAdapter;
import com.example.test.andlang.andlangutil.BaseLangViewHolder;
import com.example.test.andlang.util.ActivityUtil;
import com.example.test.andlang.util.imageload.ImageLoadUtils;
import com.example.test.andlang.widget.RoundedImageView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.extras.recyclerview.PullToRefreshRecyclerView;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.self.ActivityGoodBean;
import com.lxkj.dmhw.bean.self.GoodBean;
import com.lxkj.dmhw.utils.BBCUtil;
import com.lxkj.dmhw.utils.Utils;
import com.lxkj.dmhw.widget.LoadingMoreFootLayout2;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeThemeNewAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Activity context;
    private List<ActivityGoodBean> data;
    private int type;//1=玩主,0=玩客

    public HomeThemeNewAdapter(Activity context,List<ActivityGoodBean> data,int type) {
        this.context=context;
        this.data=data;
        this.type=type;
        inflater = LayoutInflater.from(context);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ThemeNewVH themeNewVHnew=null;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.listview_themenew_item, null);
            themeNewVHnew=new ThemeNewVH(convertView);
            convertView.setTag(themeNewVHnew);
        }
        themeNewVHnew=(ThemeNewVH)convertView.getTag();
        final ThemeNewVH themeNewVH=themeNewVHnew;

        ActivityGoodBean item=data.get(position);

        int w= BBCUtil.getDisplayWidth(context)-(int)context.getResources().getDimension(R.dimen.dp_15)*2;
        int h= (int) (w);
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(w,h);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        themeNewVH.iv_good_banner.setLayoutParams(params);
//        ImageLoadUtils.doLoadImageUrl(themeNewVH.iv_good_banner,item.getImgUrl());
        Utils.displayImage(context,item.getImgUrl(),themeNewVH.iv_good_banner);
        themeNewVH.iv_good_banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //主题场列表页

            }
        });

        if(themeNewVH.rv_bannergood==null){
            themeNewVH.rv_bannergood=themeNewVH.rv_bannergood_list.getRefreshableView();
            //设置布局管理器
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            themeNewVH.rv_bannergood_list.setHasPullDownFriction(true); // 设置没有上拉阻力
            themeNewVH.rv_bannergood_list.userTouchEvent=false;//是否重写滑动事件
            LoadingMoreFootLayout2 moreLayout=new LoadingMoreFootLayout2(context,true);
            themeNewVH.rv_bannergood_list.setFooterLayout(moreLayout);
            themeNewVH.rv_bannergood.setLayoutManager(linearLayoutManager);
            themeNewVH.rv_bannergood_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {

                @Override
                public void onPullUpStart(boolean isStartRefresh) {
                    //加载更多显示 是否开始
                    if(isStartRefresh) {
                        themeNewVH.rv_bannergood_list.userTouchEvent = true;//是否重写滑动事件
                    }else {
                        themeNewVH.rv_bannergood_list.userTouchEvent = false;//是否重写滑动事件
                    }
                }

                @Override
                public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {

                }

                @Override
                public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                    //主题场列表页

                }
            });



            themeNewVH.rv_bannergood.addOnScrollListener(new RecyclerView.OnScrollListener() {

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {


                    super.onScrollStateChanged(recyclerView, newState);
                    if(recyclerView.getLayoutManager() != null) {
                        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                        //获取可视的第一个view
                        int lastpostion = layoutManager.findLastCompletelyVisibleItemPosition();
                        if(themeNewVH.adapter!=null&&lastpostion==themeNewVH.adapter.getItemCount()-1){
                            themeNewVH.rv_bannergood_list.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
                        }else {
                            themeNewVH.rv_bannergood_list.setMode(PullToRefreshBase.Mode.MANUAL_REFRESH_ONLY);
                        }
                    }
                }
            });
        }
        if(item.getGoodsList()!=null&&item.getGoodsList().size()>0) {
            themeNewVH.rv_bannergood_list.setVisibility(View.VISIBLE);
            if (item.getGoodsList().size() < 3) {
                themeNewVH.rv_bannergood_list.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
            } else {
                themeNewVH.rv_bannergood_list.setMode(PullToRefreshBase.Mode.MANUAL_REFRESH_ONLY);
            }
            themeNewVH.adapter = new HomeBannerGoodAdapter2(context, item.getGoodsList(), type,item,position);
            themeNewVH.adapter.setFromType(-1);
            themeNewVH.rv_bannergood.setAdapter(themeNewVH.adapter);
        }else {
            themeNewVH.rv_bannergood_list.setVisibility(View.GONE);
        }

        return convertView;
    }



    public class ThemeNewVH{
        @BindView(R.id.iv_good_banner)
        RoundedImageView iv_good_banner;
        @BindView(R.id.rv_bannergood_list)
        PullToRefreshRecyclerView rv_bannergood_list;
        private RecyclerView rv_bannergood;
        private HomeBannerGoodAdapter2 adapter;

        public ThemeNewVH(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }
}
