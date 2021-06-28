package com.lxkj.dmhw.fragment.self;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import com.google.android.material.appbar.AppBarLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.test.andlang.util.ActivityUtil;
import com.example.test.andlang.util.BaseLangUtil;
import com.example.test.andlang.util.LogUtil;
import com.example.test.andlang.util.StatusBarUtils;
import com.lxkj.dmhw.MyApplication;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.HaoWuZhongCaoActivity;
import com.lxkj.dmhw.activity.self.coupon.CouponActivity;
import com.lxkj.dmhw.adapter.FragmentAdapter;
import com.lxkj.dmhw.adapter.cps.HomeAThemeAdapter;
import com.lxkj.dmhw.adapter.cps.HomeThemeNewAdapter;
import com.lxkj.dmhw.adapter.cps.HomeTimeAdatper;
import com.lxkj.dmhw.adapter.cps.NewHomeGuideAdapter;
import com.lxkj.dmhw.adapter.cps.TodaySaleNewAdapter;
import com.lxkj.dmhw.bean.self.ActivityBean;
import com.lxkj.dmhw.bean.self.ActivityGoodBean;
import com.lxkj.dmhw.bean.self.ActivityGoods;
import com.lxkj.dmhw.bean.self.ActivityNewBean;
import com.lxkj.dmhw.bean.self.ActivityitemitemBean;
import com.lxkj.dmhw.bean.self.GoodBean;
import com.lxkj.dmhw.bean.self.HomeBean;
import com.lxkj.dmhw.bean.self.HomeBigActBean;
import com.lxkj.dmhw.bean.self.HomeLimitBean;
import com.lxkj.dmhw.bean.self.HomeZcBean;
import com.lxkj.dmhw.bean.self.LimitTimeBean;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.model.HomeModel;
import com.lxkj.dmhw.model.LimitSaleModel;
import com.lxkj.dmhw.presenter.HomePresenter;
import com.lxkj.dmhw.utils.BBCUtil;
import com.lxkj.dmhw.utils.MyItemDecoration;
import com.lxkj.dmhw.utils.Utils;
import com.lxkj.dmhw.view.CircleImageView;
import com.lxkj.dmhw.widget.MyListView;
import com.lxkj.dmhw.widget.MyStaggeredGridLayoutManager;
import com.lxkj.dmhw.widget.banner.ShareCardItem;
import com.lxkj.dmhw.widget.banner.ShareCardView;
import com.lxkj.dmhw.widget.swipe.RefreshLayout;
import com.yan.pullrefreshlayout.PullRefreshLayout;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class SelfHomeFragment extends BaseFragment {
    @BindView(R.id.rl_top)
    RelativeLayout rl_top;
    @BindView(R.id.ll_search)
    LinearLayout ll_search;
    @BindView(R.id.sw_todaysale)
    RefreshLayout sw_todaysale;
    @BindView(R.id.appbar_todaysale)
    AppBarLayout appbar_todaysale;
    @BindView(R.id.iv_back_to_top)
    ImageView ivBackTop;
    @BindView(R.id.rv_todaysale)
    RecyclerView rv_todaysale;
    @BindView(R.id.rl_limitime)
    RelativeLayout rl_limitime;//秒杀场tab
    @BindView(R.id.pr_refreshLayout)
    PullRefreshLayout pr_refreshLayout;
    @BindView(R.id.empty_view)
    View empty_view;
    @BindView(R.id.tv_bottom_tip)
    TextView tv_bottom_tip;
    @BindView(R.id.iv_bottom_tip)
    ImageView iv_bottom_tip;
    @BindView(R.id.iv_notice)
    ImageView iv_notice;
    @BindView(R.id.iv_search_top)
    ImageView iv_search_top;
    @BindView(R.id.fragment_one_left_iv)
    CircleImageView fragment_one_left_iv;
    @BindView(R.id.tv_one_left_name)
    TextView tv_one_left_name;
    @BindView(R.id.rl_banner)
    RelativeLayout rl_banner;
    @BindView(R.id.home_banner)
    ShareCardView home_banner;
    @BindView(R.id.rv_home_guide)
    RecyclerView rv_home_guide;
    @BindView(R.id.home_atheme)
    MyListView home_atheme;
    @BindView(R.id.good_new_content)
    ViewPager good_new_content;
    @BindView(R.id.good_new_magicindicator)
    MagicIndicator good_new_magicindicator;
    @BindView(R.id.good_new_progress_bar)
    SeekBar good_new_progress_bar;
    @BindView(R.id.ll_home_theme)
    LinearLayout ll_home_theme;
    @BindView(R.id.home_theme)
    MyListView home_theme;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.ll_good_new)
    LinearLayout ll_good_new;
    @BindView(R.id.tb_limittime)
    TabLayout tb_limittime;
    @BindView(R.id.layout_bottom_loading)
    RelativeLayout layout_bottom_loading;//未起作用

    private NewHomeGuideAdapter newHomeGuideAdapter;
    private HomeAThemeAdapter homeAThemeAdapter;
    private HomeThemeNewAdapter homeThemeNewAdapter;
    private List<LimitSaleModel> limitSaleModelList;//秒杀商品列表
    private List<GoodBean> hotGoodBeanList;
    private boolean backTop = false;//true回到顶部，false回到秒杀
    private ObjectAnimator animator;
    private float percentage;
    private long currentTime;//今天最后一次打开页面或刷新的时间
    private HomeTimeAdatper homeTimeAdatper;
    private TodaySaleNewAdapter goodAdapter;
    private boolean isAddHotTitle = false;//是否添加了热门推荐的标题
    private boolean haveMore;
    private HomeBigActBean homeBigActBean;//首页大活动配置图片和色值
    private String bannerBgColorStr;//记录当前banner的背景色
    private boolean isUserbannerBgColor = true;//是否切换使用banner背景色
    private int bannerPostion = 0;//记录banner最后滑动的位置 左划右划
    private boolean isScrolling = false;//是否开始滑动
    // 声明FragmentManager对象
    private FragmentManager goodNewfragmentManager;
    // Fragment页面
    private ArrayList<Fragment> goodNewfragments;
    private FragmentAdapter goodNewfragmentAdapter;

    private HomePresenter homePresenter;
    private boolean reloadHotGoods = true;//是否要重新请求热门推荐商品
    private boolean isOpenBottomLoadMore = true;//是否开启底部加载更多功能 转至下一场

    @Override
    public void onData() {

    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_self_home, null);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onEvent() {
        int topheight = (int) getResources().getDimension(R.dimen.dp_50) + StatusBarUtils.getStatusHeight(getActivity());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, topheight);
        rl_top.setLayoutParams(layoutParams);

        //设置下拉刷新
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.include_loading, null);
        final View image = header.findViewById(R.id.iv_loading_top);

        animator = ObjectAnimator.ofFloat(image, "rotation", 0f, 360f);
        sw_todaysale.setRefreshHeader(header);
        if (sw_todaysale != null) {
            // 刷新状态的回调
            sw_todaysale.setRefreshListener2(new RefreshLayout.OnRefreshListener2() {
                @Override
                public void onRefreshing() {
                    refreshData();
                }

                @Override
                public void onPull() {
                    sw_todaysale.startAnim(animator);
                }

                @Override
                public void onReset() {
                    sw_todaysale.stopAnim(animator);
                }

                @Override
                public void onComplete() {

                }
            });

        }
        int screenHeight = BaseLangUtil.getDisplayHeight(getActivity());
        //设置可下拉刷新位置
        appbar_todaysale.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (getActivity() != null) {
                    int offest = Math.abs(verticalOffset);

                }

                int maxScroll = appBarLayout.getTotalScrollRange();
                percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;
                if (percentage == 0.0f) {
                    sw_todaysale.setEnabled(true);
                } else {
                    sw_todaysale.setEnabled(false);
                }
                if (getActivity() == null) {
                    return;
                }
                if (!isAdded()) {
                    //Fragment没有添加到Activity
                    //getResources()会崩溃
                    return;
                }

                if (homeBigActBean == null) {
                    //没有大型活动配置时
                    if (Math.abs(verticalOffset) >= 50) {
                        isUserbannerBgColor = false;
                        rl_top.setBackgroundColor(getActivity().getResources().getColor(R.color.colorWhite));
                        ll_search.setBackgroundResource(R.drawable.bg_rect_white2_18dp);
                        iv_notice.setImageResource(R.mipmap.fragment_one_message_common);
                        iv_search_top.setImageResource(R.mipmap.search);
//                        homeI.setStatusBar("#FFFFFF");

                    } else {
                        isUserbannerBgColor = true;
                        rl_top.setBackgroundColor(getActivity().getResources().getColor(R.color.Transparent00000000));
                        ll_search.setBackgroundResource(R.drawable.bg_rect_white_18dp);
                        iv_notice.setImageResource(R.mipmap.fragment_one_message_common_white);
                        iv_search_top.setImageResource(R.mipmap.icon_search_white);
//                        homeI.setStatusBar(bannerBgColorStr);
                    }
                }
            }
        });

        //防止上下无法滑动
        appbar_todaysale.post(new Runnable() {
            @Override
            public void run() {
                CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) appbar_todaysale.getLayoutParams();
                AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) layoutParams.getBehavior();

                behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
                    @Override
                    public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                        return true;
                    }
                });
            }
        });
        //商品信息
        int topbottomPadding=(int) getActivity().getResources().getDimension(R.dimen.dp_10);
        int leftrightPadding=(int) getActivity().getResources().getDimension(R.dimen.dp_5);
        rv_todaysale.setLayoutManager(new MyStaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rv_todaysale.addItemDecoration(new MyItemDecoration(getActivity(),leftrightPadding,topbottomPadding));
        rv_todaysale.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                LogUtil.d("appbar_todaysale", "滑动状态：" + newState + "");

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                    ivBackTop.setImageResource(R.mipmap.first_back_top);
//                    ivBackTop.setVisibility(View.VISIBLE);

//                if (headH<0.5*BBCUtil.getDisplayHeight(getActivity())){
//                    totalDy-=dy;
//                    Log.i("0.0","滑动距离："+totalDy);
//                    if(Math.abs(totalDy-headH)>=0.5*BBCUtil.getDisplayHeight(getActivity())){
//                        ivBackTop.setVisibility(View.VISIBLE);
//                    }else{
//                        ivBackTop.setVisibility(View.GONE);
//                    }
//
//                }
//                if (dy >= 0) {
//                    //上拉时  显示底部加载更多
//                    isBottomLoadMore = true;
//                } else {
//                    isBottomLoadMore = false;
//                }
//                LogUtil.d("0.0","底部item的type："+goodAdapter.getItemViewType(goodAdapter.getItemCount()-1));

                if (goodAdapter != null) {
                    StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                    int[] positonsArr = new int[manager.getSpanCount()];
                    int firstItemPosition = BBCUtil.findMin(manager.findFirstVisibleItemPositions(positonsArr));
                    int firstCompletePosition = BBCUtil.findMin(manager.findFirstCompletelyVisibleItemPositions(positonsArr));
                    int lastItemPostition = BBCUtil.findMax(manager.findLastVisibleItemPositions(positonsArr));

                    //是否隐藏置顶的秒杀tab逻辑
                    int itemType = goodAdapter.getItemViewType(firstItemPosition);
                    int itemCompleteType = goodAdapter.getItemViewType(firstCompletePosition);
                    if (itemType == LimitSaleModel.TYPE_TITLE) {
                        //在热门商品标题
                        AppBarLayout.LayoutParams appBarParams = (AppBarLayout.LayoutParams) rl_limitime.getLayoutParams();
                        if (appBarParams.getScrollFlags() != (AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS)) {
                            LogUtil.d("0.0", "在热门商品标题,tab设置为下拉置顶");
                            appBarParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);//tab设置为置顶
                            rl_limitime.setLayoutParams(appBarParams);
                        }
                    } else if (itemType == LimitSaleModel.TYPE_RECYCLE) {
                        //在热门商品区域
//                        rl_limitime.setVisibility(View.GONE);
                        AppBarLayout.LayoutParams appBarParams = (AppBarLayout.LayoutParams) rl_limitime.getLayoutParams();
                        if (appBarParams.getScrollFlags() != AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL) {
                            LogUtil.d("0.0", "在热门商品区域 tab设置为滑动");
                            appBarParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL);//tab设置为滑动
                            rl_limitime.setLayoutParams(appBarParams);
                        }
                    } else if (itemType == LimitSaleModel.TYPE_GOOD || itemType == LimitSaleModel.TYPE_BANNERSALE) {
                        //在秒杀商品区域
                        AppBarLayout.LayoutParams appBarParams = (AppBarLayout.LayoutParams) rl_limitime.getLayoutParams();
                        if (appBarParams.getScrollFlags() != 0) {
                            LogUtil.d("0.0", "在秒杀商品区域 tab设置为置顶");
                            appBarParams.setScrollFlags(0);//tab设置为置顶
                            rl_limitime.setLayoutParams(appBarParams);
                        }
                    }
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        //上拉加载
        initLoadMore();

        //首页轮播图适配 375:440
        LinearLayout.LayoutParams linearParamsOne =(LinearLayout.LayoutParams)rl_banner.getLayoutParams();
        linearParamsOne.width=BaseLangUtil.getDisplayWidth(getActivity());
        linearParamsOne.height=width;// width*440/375
        rl_banner.setLayoutParams(linearParamsOne);
        //banner 设置
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        home_banner.setLayoutParams(params);
    }
    @Override
    public void onCustomized() {
        homePresenter=new HomePresenter(null,null,HomeModel.class);
        refreshData();
    }

    public void refreshData(){
        showDialog();
        homePresenter.reqHomeIndexOne(getActivity(),reqHandler);
        homePresenter.reqHomeIndexTwo(getActivity(),reqHandler);
        homePresenter.reqHomeIndexThree(getActivity(),reqHandler);
    }

    @Override
    public void mainMessage(Message message) {

    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        String arg=(String)message.obj;
        sw_todaysale.refreshComplete();
        if ("reqHomeIndexOne".equals(arg)) {
            HomeBean homeBean=homePresenter.model.getHomeBean();
            tv_search.setHint(homeBean.getHotSearch());
            Utils.displayImage(getActivity(),homeBean.getHeadUrl(),fragment_one_left_iv);
            tv_one_left_name.setText(homeBean.getUserLevel());
            bannerImage(homeBean.getBannerTheme());
            initGuide(homeBean.getNavigation());
            initTagram(homeBean.getNavigationAfterTmeme());
            initGoodTheme(homeBean.getNavigationAfterTwoThemeList());

        }else if("reqHomeIndexTwo".equals(arg)){
            List<HomeZcBean> zcBeanList=homePresenter.model.getHomeZcBeanList();
            initGoodNewMagic(zcBeanList);
        }else if("reqHomeIndexThree".equals(arg)){
            HomeLimitBean homeLimitBean=homePresenter.model.getHomeLimitBean();
            loadTimeList(homeLimitBean);
            loadGoodInfo(homeLimitBean.getNowActivityGoods());
            if (reloadHotGoods) {
                //请求热门商品
                reloadHotGoods = false;//不重复请求标示 下拉刷新重置
                homePresenter.reqHotGoods(getActivity(),reqHandler,false);
            }
        }else if("reqHotGoods".equals(arg)){
            //热门商品
            hotGoodBeanList = homePresenter.model.getGoodBeanList();
            loadHotGoods(homePresenter.model.getAddHotGoodList());
        }else if("reqActivityGoods".equals(arg)){
            loadGoodInfo(homePresenter.model.getNowActivityGoods());
        }
        dismissDialog();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    public void refresh(){

    }

    public void loadGoodInfo(ActivityGoods activityGoods) {
        currentTime = MyApplication.NOW_TIME;
        if (limitSaleModelList == null) {
            limitSaleModelList = new ArrayList<>();
        } else {
            limitSaleModelList.clear();
        }
        //加载商品数据
        if (activityGoods != null) {
            List<GoodBean> bannerGoods = activityGoods.getBannerGoods();
            List<GoodBean> notBannerGoods = activityGoods.getNotBannerGoods();


            if (notBannerGoods != null) {
                for (int i = 0; i < notBannerGoods.size(); i++) {
                    LimitSaleModel model = new LimitSaleModel();
                    model.setGood(notBannerGoods.get(i));
                    model.setType(LimitSaleModel.TYPE_GOOD);
                    limitSaleModelList.add(model);
                }
            }

            if (homeTimeAdatper == null) {
                return;
            }
            LimitTimeBean limitTimeBean = homeTimeAdatper.getItemSelect(homeTimeAdatper.getSelectPostition());
            if (limitTimeBean == null) {
                return;
            }

            if (goodAdapter == null) {
                goodAdapter = new TodaySaleNewAdapter(getActivity(), limitSaleModelList, limitTimeBean.getId(), myhandler);
                goodAdapter.setMobclickAgent(homeTimeAdatper.getSelectPostition());
                rv_todaysale.setAdapter(goodAdapter);
            } else {
                goodAdapter.setActivityId(limitTimeBean.getId());
                goodAdapter.setMobclickAgent(homeTimeAdatper.getSelectPostition());
                goodAdapter.notifyDataSetChanged();
            }

            if (hotGoodBeanList != null && hotGoodBeanList.size() > 0) {
                //切换tab时  有热门推荐商品 需要添加进来
                isAddHotTitle = false;
                loadHotGoods(hotGoodBeanList);
            }
        }
    }

    //热门商品列表
    private void loadHotGoods(List<GoodBean> addHotGoods) {
        int beforeCount = 0;
        //添加标题item
        if (addHotGoods != null && addHotGoods.size() > 0) {
            if (limitSaleModelList == null) {
                limitSaleModelList = new ArrayList<>();
            }
            int positionStart = limitSaleModelList.size();
            if (!isAddHotTitle) {
                //第一次添加
                isAddHotTitle = true;
                LimitSaleModel titleModel = new LimitSaleModel();
                titleModel.setType(LimitSaleModel.TYPE_TITLE);
                limitSaleModelList.add(titleModel);
                beforeCount = limitSaleModelList.size();
            }

            for (int i = 0; i < addHotGoods.size(); i++) {
                LimitSaleModel model = new LimitSaleModel();
                model.setGood(addHotGoods.get(i));
                model.setType(LimitSaleModel.TYPE_RECYCLE);
                limitSaleModelList.add(model);
            }

//            LogUtil.d("0.0","item的数量"+limitSaleModelList.size());

            if (goodAdapter == null) {
                //说明没有秒杀商品
                goodAdapter = new TodaySaleNewAdapter(getActivity(), limitSaleModelList, "", myhandler);
                rv_todaysale.setAdapter(goodAdapter);
            } else {
                if (addHotGoods.size() != hotGoodBeanList.size()) {
                    goodAdapter.notifyItemRangeInserted(positionStart, limitSaleModelList.size() - positionStart);
                } else {
                    goodAdapter.notifyDataSetChanged();
                }
            }
            if (beforeCount > 0) {
                //记录热门商品前的item数量
                goodAdapter.setBeforeItemCount(beforeCount);
            }

            //停止惯性滑动
//            rv_todaysale.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_CANCEL, 0, 0, 0));

            //有热门数居不能隐藏
            empty_view.setVisibility(View.GONE);

        }

    }

    public void initLoadMore() {
        pr_refreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoading() {
                if (haveMore) {
                    pr_refreshLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            tv_bottom_tip.setText("上拉加载更多");
                            iv_bottom_tip.setVisibility(View.VISIBLE);
                            iv_bottom_tip.setImageResource(R.mipmap.pullup);
                            pr_refreshLayout.loadMoreComplete();
                        }
                    });
                } else {
                    pr_refreshLayout.post(new Runnable() {
                        @Override
                        public void run() {
                            tv_bottom_tip.setText("别拉了，已经到底咯");
                            iv_bottom_tip.setVisibility(View.GONE);
                            pr_refreshLayout.loadMoreComplete();
                        }
                    });
                }
            }
        });
    }

    /**
     * 轮播图事件
     */
    private void bannerImage(final List<ActivityBean> bannerList) {
        ArrayList<String> netWorkImage = new ArrayList<>();
        for (int i = 0; i < bannerList.size(); i++) {
            netWorkImage.add(bannerList.get(i).getImgUrl());
        }

//        int [] colors=new int[list.size()];
//        for (int i =0; i <list.size(); i++) {
//            String itemColor=list.get(i).getLBgClolor();
//            if (itemColor.equals("")||itemColor.contains("#")){
//                try{
//                    colors[i]=Color.parseColor(itemColor);
//                }catch (Exception e){
//                    colors[i]=Color.parseColor("#ee2532");
//                }
//            }else{
//                try{
//                    colors[i]=Color.parseColor("#"+itemColor);
//                }catch (Exception e){
//                    colors[i]=Color.parseColor("#ee2532");
//                }
//
//            }
//        }

        //banner 设置
        if (bannerList != null && bannerList.size() > 0) {
            ActivityBean activityBean = bannerList.get(0);
            if (homeBigActBean == null && BBCUtil.isColorStr(activityBean.getBgColor())) {
                bannerBgColorStr = activityBean.getBgColor();
//                homeI.setStatusBar(bannerBgColorStr);
//                view_hometop_bg.setBackgroundColor(Color.parseColor(bannerBgColorStr));
            }

            ShareCardItem shareCardItem = new ShareCardItem();
            shareCardItem.setDataList(bannerList);
            home_banner.setCardData(shareCardItem);
            home_banner.setOnItemClickL(new ShareCardView.OnItemClickL() {
                @Override
                public void onItemClick(Object object, int postion) {
                    ActivityBean bean = (ActivityBean) object;
                    if (bean == null) {
                        return;
                    }
                }
            });
            home_banner.setBannerScrollStateI(new ShareCardView.BannerScrollStateI() {
                @Override
                public void onPageScrollStateChanged(int state) {
                    //下拉刷新 和 banner 横向滑动手势冲突处理
                    if (state == 1) {
                        //开始滑动
                        isScrolling = true;
                        bannerPostion = home_banner.mViewPager.getCurrentItem();
                    } else if (state == 2) {
                        //结束滑动
                        isScrolling = false;
                    }
                    sw_todaysale.setEnabled(state == ViewPager.SCROLL_STATE_IDLE && percentage == 0.0f);
                }

                @Override
                public void changeView(int position) {
                    //banner图片切换
                    if (bannerList == null || bannerList.size() <= position) {
                        return;
                    }
                    ActivityBean activityBean = bannerList.get(position);
                    if (homeBigActBean == null && activityBean != null && BBCUtil.isColorStr(activityBean.getBgColor())) {
                        bannerBgColorStr = activityBean.getBgColor();
//                        view_hometop_bg.setBackgroundColor(Color.parseColor(bannerBgColorStr));
//                        if (isUserbannerBgColor) {
//                            homeI.setStatusBar(bannerBgColorStr);
//                        }
                    }
                    bannerPostion = position;
                }

                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                    LogUtil.e("0.0","position:"+position+"----bannerPostion:"+bannerPostion);
                    if (!isScrolling) {
                        return;
                    }
                    boolean isLeft = false;
                    if (bannerPostion == position) {
                        isLeft = false;
                    } else {
                        isLeft = true;
                    }

                    int nextposiont = position + 1;//右滑
                    if (isLeft) {
                        //左滑
                        nextposiont = position;
//                        LogUtil.e("0.0","左滑");
                    } else {
//                        LogUtil.e("0.0","右滑");
                    }
                    if (nextposiont >= bannerList.size()) {
                        nextposiont = 0;
                    }
                    if (nextposiont < 0) {
                        nextposiont = bannerList.size() - 1;
                    }
//                    LogUtil.e("0.0","position:"+position+"----nextposiont:"+nextposiont);

                    if (bannerList == null || bannerList.size() <= nextposiont) {
                        return;
                    }
                    ActivityBean activityBean = bannerList.get(nextposiont);
                    String nextBgColorStr = activityBean.getBgColor();
                    if (homeBigActBean == null && BBCUtil.isColorStr(nextBgColorStr)) {
//                        view_hometop_bg.setBackgroundColor(BBCUtil.colorOffset(bannerBgColorStr, nextBgColorStr, positionOffset, isLeft));
//                        if (isUserbannerBgColor) {
//                            homeI.scrollingBar(nextBgColorStr, positionOffset, isLeft);
//                        }
                    }
                }
            });
        } else {
            home_banner.setVisibility(View.GONE);
        }
    }

    /*
    * 金刚区
    * */
    public void initGuide(List<ActivityBean> navigation){
        if (navigation != null && navigation.size() > 0) {
            RecyclerView recyclerView = rv_home_guide;
//            RecyclerView recyclerView=rv_home_guide.getRefreshableView();
//            rv_home_guide.setMode(PullToRefreshBase.Mode.DISABLED);
//            rv_home_guide.userTouchEvent=true;
//            rv_home_guide.setHasPullDownFriction(true);
            GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
            layoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(layoutManager);
            newHomeGuideAdapter = new NewHomeGuideAdapter(getActivity(), navigation);
            recyclerView.setAdapter(newHomeGuideAdapter);
        }
    }

    //七巧板
    public void initTagram(List<ActivityNewBean> navigationBelowTheme){
        if (navigationBelowTheme != null && navigationBelowTheme.size() > 0) {

            home_atheme.setVisibility(View.VISIBLE);
            homeAThemeAdapter = new HomeAThemeAdapter(getActivity(), navigationBelowTheme);
            home_atheme.setAdapter(homeAThemeAdapter);
            BBCUtil.setListViewHeightBasedOnChildren(home_atheme);
        } else {
            home_atheme.setVisibility(View.GONE);
        }
    }
    //好物种草
    private void initGoodNewMagic(List<HomeZcBean> list) {
        if(list!=null&&list.size()>0) {
            ll_good_new.setVisibility(View.VISIBLE);
            good_new_progress_bar.setPadding(0, 0, 0, 0);
            good_new_progress_bar.setThumbOffset(0);
            // 初始化FragmentManager对象
            goodNewfragmentManager = getChildFragmentManager();
            // 初始化Fragment页面
            goodNewfragments = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                GoodNewFragment goodNewFragment = GoodNewFragment.getInstance(list.get(i));
                goodNewfragments.add(goodNewFragment);
            }
            goodNewfragmentAdapter = new FragmentAdapter(goodNewfragmentManager, goodNewfragments);
            good_new_content.setAdapter(goodNewfragmentAdapter);
            good_new_content.setOffscreenPageLimit(list.size());
            CommonNavigator commonNavigator = new CommonNavigator(getActivity());
            commonNavigator.setScrollPivotX(0.5f);
            commonNavigator.setAdapter(new CommonNavigatorAdapter() {
                @Override
                public int getCount() {
                    return list.size();
                }

                @Override
                public IPagerTitleView getTitleView(Context context, int index) {
                    CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
                    return commonPagerTitleView;
                }

                @Override
                public IPagerIndicator getIndicator(Context context) {
                    return null;
                }
            });
            good_new_magicindicator.setNavigator(commonNavigator);
            ViewPagerHelper.bind(good_new_magicindicator, good_new_content);
            int a = width - Utils.dipToPixel(R.dimen.dp_20);
            good_new_content.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {
                    // 显示区域的宽度。
                    int extent = a;
                    //整体的宽度，注意是整体，包括在显示区域之外的。
                    int range = list.size() * a;
                    //已经向下滚动的距离，为0时表示已处于顶部。
                    int offset = i * a;
                    //设置可滚动区域
                    good_new_progress_bar.setMax((int) (range - extent));
                    good_new_progress_bar.setProgress(offset);
                }

                @Override
                public void onPageSelected(int i) {

                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });
        }else {
            ll_good_new.setVisibility(View.GONE);
        }
    }
    public void initGoodTheme(List<ActivityGoodBean> navigationBelowTwoTheme){
        //商品主题场设置
        if (navigationBelowTwoTheme != null && navigationBelowTwoTheme.size() > 0) {
            ll_home_theme.setVisibility(View.VISIBLE);
            homeThemeNewAdapter = new HomeThemeNewAdapter(getActivity(), navigationBelowTwoTheme, 0);
            home_theme.setAdapter(homeThemeNewAdapter);
            BBCUtil.setListViewHeightBasedOnChildren(home_theme);
        } else {
            ll_home_theme.setVisibility(View.GONE);
        }
    }

    public void loadTimeList(HomeLimitBean homeLimitBean) {
        //加载秒杀时间列表
        List<LimitTimeBean> limitTimeBeans = homeLimitBean.getActivityList();
        if (limitTimeBeans != null && limitTimeBeans.size() > 0) {
            rl_limitime.setVisibility(View.VISIBLE);
            if (homeTimeAdatper == null) {
                homeTimeAdatper = new HomeTimeAdatper(getActivity(), tb_limittime, myhandler);
            }
            homeTimeAdatper.creatTimeTab(limitTimeBeans);
            homeTimeAdatper.refreshTimeTab();
            for (int i = 0; i < limitTimeBeans.size(); i++) {
                LimitTimeBean timeBean = limitTimeBeans.get(i);
                if (timeBean.getFlag() == 1) {
                    //当前场
                    homeTimeAdatper.setSelectPostition(i);
                    selectLimitTimeItem(i, false);
                }
            }
            empty_view.setVisibility(View.GONE);
        } else {
            if (homeTimeAdatper != null) {
                //清空tab
                homeTimeAdatper.creatTimeTab(limitTimeBeans);
            }
            rl_limitime.setVisibility(View.GONE);
//            empty_view.setVisibility(View.VISIBLE);
        }
    }
    public void selectLimitTimeItem(int position, boolean isReq) {
        if (isReq && homeTimeAdatper != null) {
            //请求商品数据
            LimitTimeBean limitTimeBean = homeTimeAdatper.getItemSelect(position);
            if (limitTimeBean != null) {
                showDialog();//即将加载下一场 等待加载
                homePresenter.reqActivityGoods(getActivity(),reqHandler,limitTimeBean.getId());
            }
        }
    }

    private Handler myhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (isOpenBottomLoadMore && layout_bottom_loading != null) {
                        //隐藏底部加载
                        layout_bottom_loading.setVisibility(View.GONE);
                    }
                    goTopType(true);//回到顶部
                    int select = msg.arg1;
                    selectLimitTimeItem(select, true);
                    break;
                case 10:
                    Bundle bundle = msg.getData();
                    break;
                case 11:
                    //热门加载更多
                    if (homePresenter.haveMore) {
                        homePresenter.reqHotGoods(getActivity(),reqHandler,true);
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private Handler reqHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            handlerMessage(msg);
            super.handleMessage(msg);
        }
    };

    public void goTopType(boolean isFast) {
        //滚动到顶部
        if (!isFast) {
            rv_todaysale.scrollToPosition(0);
            CoordinatorLayout.Behavior behavior =
                    ((CoordinatorLayout.LayoutParams) appbar_todaysale.getLayoutParams()).getBehavior();
            if (behavior instanceof AppBarLayout.Behavior) {
                AppBarLayout.Behavior appBarLayoutBehavior = (AppBarLayout.Behavior) behavior;
                int topAndBottomOffset = appBarLayoutBehavior.getTopAndBottomOffset();
                if (topAndBottomOffset != 0) {
                    appBarLayoutBehavior.setTopAndBottomOffset(0);
                }
            }
//            rv_todaysale.smoothScrollToPosition(0);
//            rv_todaysale.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    //保证滑动到顶部
//                    CoordinatorLayout.Behavior behavior =
//                            ((CoordinatorLayout.LayoutParams) appbar_todaysale.getLayoutParams()).getBehavior();
//                    if (behavior instanceof AppBarLayout.Behavior) {
//                        AppBarLayout.Behavior appBarLayoutBehavior = (AppBarLayout.Behavior) behavior;
//                        int topAndBottomOffset = appBarLayoutBehavior.getTopAndBottomOffset();
//                        if (topAndBottomOffset != 0) {
//                            appBarLayoutBehavior.setTopAndBottomOffset(0);
//                        }
//                    }
//                }
//            }, 500);
        } else {
            rv_todaysale.scrollToPosition(0);
        }
        ivBackTop.setVisibility(View.GONE);
    }

    @OnClick(R.id.iv_self_mine)
    public void clickSelfMine(){
        // 点击自营我的
        Intent intent=new Intent(getActivity(), HaoWuZhongCaoActivity.class);
        ActivityUtil.getInstance().start(getActivity(),intent);
    }
    @OnClick(R.id.iv_self_cart)
    public void clickSelfCart(){
        //购物车
        Intent intent=new Intent(getActivity(), CouponActivity.class);
        ActivityUtil.getInstance().start(getActivity(),intent);
    }

    //测试数据
    public ArrayList<ActivityBean> getGuideList(){
        ArrayList<ActivityBean> guideList = new ArrayList<ActivityBean>();
        for (int i=0;i<5;i++){
            ActivityBean guide=new ActivityBean();
            guide.setImgUrl("https://image.sudian178.com/sd/withinbuy/banner010.jpg");
            guide.setName("标题"+i);
            guideList.add(guide);
        }
        return guideList;
    }
    public ArrayList<ActivityBean> getBannerList(){
        ArrayList<ActivityBean> bannerList = new ArrayList<ActivityBean>();
        for (int i=0;i<5;i++){
            ActivityBean banner=new ActivityBean();
            banner.setImgUrl("https://image.sudian178.com/sd/withinbuy/banner010.jpg");
            bannerList.add(banner);
        }
        return bannerList;
    }
    public List<GoodBean> getGoodBeanList() {
        List<GoodBean> goodBeanList = new ArrayList<GoodBean>();
        for (int j = 0; j < 14; j++) {
            GoodBean item = new GoodBean();
            item.setId("263240180005043");
            if(j==0){
                item.setImgUrl("https://image.sudian178.com/sd/withinbuy/banner010.jpg");
            }else {
                item.setImgUrl("https://image.sudian178.com/sd/bannerImg/4562112666847184.jpg");
            }
            goodBeanList.add(item);
        }
        return goodBeanList;
    }
    public List<ActivityNewBean> getHomeThemeNewList() {
        List<ActivityNewBean> themeAdapterModelList = new ArrayList<ActivityNewBean>();
        for (int i = 0; i < 5; i++) {
            ActivityNewBean itemBean = new ActivityNewBean();
            if (i % 2 == 0) {
                itemBean.setBgColor("#213456");
            } else {
                itemBean.setBgColor("#998654");
            }
            List<ActivityitemitemBean> activityitemitemBeanList = new ArrayList<ActivityitemitemBean>();
            for (int j = 0; j < 4; j++) {
                ActivityitemitemBean item = new ActivityitemitemBean();
                item.setAdrUrl("https://image.sudian178.com/sd/bannerImg/4562112666847184.jpg");
                item.setImgUrl("https://image.sudian178.com/sd/bannerImg/4562112666847184.jpg");
                item.setHeight(100);
                item.setWidth(100);
                item.setThemeId(257202630019216L);
                activityitemitemBeanList.add(item);
            }
            itemBean.setDetailList(activityitemitemBeanList);
            themeAdapterModelList.add(itemBean);
        }
        return themeAdapterModelList;
    }
    public List<ActivityGoodBean> getGoodThemeList(){
        List<ActivityGoodBean> themeList = new ArrayList<ActivityGoodBean>();
        for (int i = 0; i < 5; i++) {
            ActivityGoodBean itemBean = new ActivityGoodBean();
            itemBean.setId(263240180005043L);
            List<GoodBean> goodBeanList = new ArrayList<GoodBean>();
            List<GoodBean> goodBeans=new ArrayList<>();
            if(i==0) {
                goodBeanList.addAll(goodBeans);
                itemBean.setImgUrl("https://image.sudian178.com/sd/bannerImg/4562112666847184.jpg");
            }else if(i==1){
                itemBean.setImgUrl("https://image.sudian178.com/sd/withinbuy/banner001.png");
            }else {
                goodBeanList.addAll(goodBeans);
                itemBean.setImgUrl("https://image.sudian178.com/sd/withinbuy/banner010.jpg");
            }
            itemBean.setTitle("标题"+i);


            for (int j = 0; j < 10; j++) {
                GoodBean item = new GoodBean();
                if(j==0){
                    item.setImgUrl("https://image.sudian178.com/sd/withinbuy/banner010.jpg");
                }else {
                    item.setImgUrl("https://image.sudian178.com/sd/bannerImg/4562112666847184.jpg");
                }
                goodBeanList.add(item);
            }
            itemBean.setGoodsList(goodBeanList);
            themeList.add(itemBean);
        }
        return themeList;
    }

}