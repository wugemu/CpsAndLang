package com.lxkj.dmhw.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.adapter.BannerMainAdapter;
import com.lxkj.dmhw.adapter.FragmentAdapter;
import com.lxkj.dmhw.bean.JDBanner;
import com.lxkj.dmhw.bean.JDSort;
import com.lxkj.dmhw.defined.ArcPagerIndicator;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.defined.PtrClassicRefreshLayout;
import com.lxkj.dmhw.defined.ScaleTransitionPagerTitleView;
import com.lxkj.dmhw.fragment.MorePlFragment;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.Utils;
import com.lxkj.dmhw.view.RoundLayoutNew;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

//唯品会 苏宁易购共用页面
public class MorePlatformNewActivity extends BaseActivity implements PtrHandler {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.pl_search)
    LinearLayout pl_search;
    @BindView(R.id.load_more_ptr_frame)
    PtrClassicRefreshLayout loadMorePtrFrame;

    @BindView(R.id.pl_jd_img)
    View pl_jd_img;
    @BindView(R.id.pl_pdd_img)
    View pl_pdd_img;
    @BindView(R.id.pl_wei_img)
    View pl_wei_img;
    @BindView(R.id.pl_sn_img)
    View pl_sn_img;
    @BindView(R.id.pl_kl_img)
    View pl_kl_img;
    @BindView(R.id.more_pl_iv)
    ImageView more_pl_iv;
    @BindView(R.id.wei_back)
    ImageView wei_back;
    @BindView(R.id.wei_search)
    ImageView wei_search;

    @BindView(R.id.jd_back)
    ImageView jd_back;
    @BindView(R.id.jd_search)
    ImageView jd_search;

    @BindView(R.id.header_more_pl_banner)
    ConvenientBanner headerBanner;
    @BindView(R.id.fragment_more_pl_magic)
    MagicIndicator MorePlMagic;
    @BindView(R.id.fragment_more_pl_content)
    ViewPager fragmentOneContent;
    @BindView(R.id.fragment_more_pl_magic_layout)
    LinearLayout fragment_more_pl_magic_layout;
    @BindView(R.id.header_new_banner_layout)
    RoundLayoutNew header_new_banner_layout;

    @BindView(R.id.Coordinator_Layout)
    CoordinatorLayout Coordinator_Layout;

    @BindView(R.id.AppBar_Layout)
    AppBarLayout AppBar_Layout;


    //头部分类列表
    ArrayList<JDSort> list =new ArrayList<>();

    // 声明FragmentAdapter对象
    private FragmentAdapter fragmentAdapter;
    // 声明FragmentManager对象
    private FragmentManager fragmentManager;

    //分类对应的页面
    private MorePlFragment oneFragmentNew;

    private int platformType=0;
    public static int currentPos=0;

    LinearLayout.LayoutParams MorePlMagiclayoutParams;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moreplatform_new);
        ButterKnife.bind(this);
        platformType=getIntent().getIntExtra("type",0);
        //声明头部组件
        pl_jd_img.setVisibility(View.GONE);
        pl_pdd_img.setVisibility(View.GONE);
        pl_wei_img.setVisibility(View.GONE);
        pl_sn_img.setVisibility(View.GONE);
        if (platformType==1){
       //拼多多
            pl_pdd_img.setVisibility(View.VISIBLE);
            MorePlMagic.setBackground(null);
            fragment_more_pl_magic_layout.setBackgroundColor(Color.parseColor("#f4f4f4"));
        }else if(platformType==2) {
            //京东
            pl_jd_img.setVisibility(View.VISIBLE);
            MorePlMagic.setBackground(null);
            fragment_more_pl_magic_layout.setBackgroundColor(Color.parseColor("#f4f4f4"));
        }else if(platformType==5){
                //苏宁
            wei_back.setVisibility(View.VISIBLE);
            jd_back.setVisibility(View.GONE);
            jd_search.setImageResource(R.mipmap.search);
            pl_sn_img.setVisibility(View.VISIBLE);
            fragment_more_pl_magic_layout.setBackgroundColor(0);
            LinearLayout.LayoutParams linearParams1 = (LinearLayout.LayoutParams) header_new_banner_layout.getLayoutParams();
            linearParams1.setMargins(0, 0, 0, Utils.dipToPixel(R.dimen.dp_10));
            header_new_banner_layout.setLayoutParams(linearParams1);
            MorePlMagiclayoutParams = (LinearLayout.LayoutParams) MorePlMagic.getLayoutParams();
            MorePlMagiclayoutParams.setMarginStart(Utils.dipToPixel(R.dimen.dp_10));
            MorePlMagiclayoutParams.setMarginEnd(Utils.dipToPixel(R.dimen.dp_10));
            MorePlMagiclayoutParams.height = Utils.dipToPixel(R.dimen.dp_45);
            MorePlMagic.setLayoutParams(MorePlMagiclayoutParams);
            MorePlMagic.setBackground(getResources().getDrawable(R.drawable.personal_grid_bg));
            more_pl_iv.setImageResource(0);
            more_pl_iv.setBackgroundColor(Color.parseColor("#00000000"));
        }else{
            fragment_more_pl_magic_layout.setBackgroundColor(0);
            LinearLayout.LayoutParams linearParams1 = (LinearLayout.LayoutParams) header_new_banner_layout.getLayoutParams();
            linearParams1.setMargins(0, 0, 0, Utils.dipToPixel(R.dimen.dp_10));
            header_new_banner_layout.setLayoutParams(linearParams1);
            MorePlMagiclayoutParams = (LinearLayout.LayoutParams) MorePlMagic.getLayoutParams();
            MorePlMagiclayoutParams.setMarginStart(Utils.dipToPixel(R.dimen.dp_10));
            MorePlMagiclayoutParams.setMarginEnd(Utils.dipToPixel(R.dimen.dp_10));
            MorePlMagiclayoutParams.height = Utils.dipToPixel(R.dimen.dp_45);
            MorePlMagic.setLayoutParams(MorePlMagiclayoutParams);
            MorePlMagic.setBackground(getResources().getDrawable(R.drawable.personal_grid_bg));
            MorePlMagic.setPadding(Utils.dipToPixel(R.dimen.dp_50), 0, Utils.dipToPixel(R.dimen.dp_50), 0);
            more_pl_iv.setImageResource(0);
            more_pl_iv.setBackgroundColor(Color.parseColor("#00000000"));
            if (platformType==3) {
                //唯品会
                wei_back.setVisibility(View.VISIBLE);
                jd_back.setVisibility(View.GONE);
                jd_search.setVisibility(View.GONE);
                wei_search.setVisibility(View.VISIBLE);
                pl_wei_img.setVisibility(View.VISIBLE);
            }else{
                //考拉
                jd_search.setVisibility(View.GONE);
                wei_search.setVisibility(View.VISIBLE);
                pl_kl_img.setVisibility(View.VISIBLE);
                wei_back.setVisibility(View.VISIBLE);
                jd_back.setVisibility(View.GONE);
            }
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        ptrFrameLayout();//下拉刷新初始化

        //首页轮播图适配 宽高比例是1080:450
        header_new_banner_layout.setCornerRadius(Utils.dipToPixel(R.dimen.dp_10));
        LinearLayout.LayoutParams linearParamsOne =(LinearLayout.LayoutParams)header_new_banner_layout.getLayoutParams();
        linearParamsOne.width=width-Utils.dipToPixel(R.dimen.dp_20);
        linearParamsOne.height=width*450/1080-Utils.dipToPixel(R.dimen.dp_8);
        header_new_banner_layout.setLayoutParams(linearParamsOne);

        //添加网络请求  轮播图和分类
        httpPost();
    }

    private void httpPost() {
        // 请求广告  列表  以及专区数据
        paramMap.clear();
        paramMap.put("userid", login.getUserid());
        if (platformType==2){
//           NetworkRequest.getInstance().GETNew(handler, paramMap, "JDBanner", HttpCommon.JDBanner);
//           NetworkRequest.getInstance().GETNew(handler, paramMap, "JDSortList", HttpCommon.JDSortList);
             NetworkRequest.getInstance().GETNew(handler, paramMap, "JDInitMain", HttpCommon.JDInitMain);
        }else if (platformType==1){
            NetworkRequest.getInstance().GETNew(handler, paramMap, "JDBanner", HttpCommon.PDDBanner);
            NetworkRequest.getInstance().GETNew(handler, paramMap, "JDSortList", HttpCommon.PDDSortList);
        }else if(platformType==3){
            NetworkRequest.getInstance().GETNew(handler, paramMap, "JDBanner", HttpCommon.WPHBanner);
            NetworkRequest.getInstance().GETNew(handler, paramMap, "JDSortList", HttpCommon.WPHSortList);
        }else if(platformType==5){
            NetworkRequest.getInstance().GETNew(handler, paramMap, "JDBanner", HttpCommon.SNBanner);
            NetworkRequest.getInstance().GETNew(handler, paramMap, "JDSortList", HttpCommon.SNSortList);
        }else{
            NetworkRequest.getInstance().GETNew(handler, paramMap, "JDBanner", HttpCommon.KLBanner);
            NetworkRequest.getInstance().GETNew(handler, paramMap, "JDSortList", HttpCommon.KLSortList);
        }


    }

     //计算是否滑动到底部
    protected boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }
    @Override
    public void mainMessage(Message message) {
        if (message.what == LogicActions.ShareStatusSuccess&&message.arg1==1) {
            paramMap.clear();
            paramMap.put("type", "shareshop");
            NetworkRequest.getInstance().POST(handler, paramMap, "DayBuy", HttpCommon.finishTask);
        }

    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        if (message.what == LogicActions.JDBannerSuccess) {
            if (message.obj!=null&&((ArrayList<JDBanner>) message.obj).size()>0){
                LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams)MorePlMagic.getLayoutParams();
                linearParams.setMargins(Utils.dipToPixel(R.dimen.dp_10),0,Utils.dipToPixel(R.dimen.dp_10),0);
                MorePlMagic.setLayoutParams(linearParams);
                header_new_banner_layout.setVisibility(View.VISIBLE);
                bannerImage((ArrayList<JDBanner>) message.obj);
                loadMorePtrFrame.setBackground(null);
                loadMorePtrFrame.setBackgroundColor(Color.TRANSPARENT);
            }else{
                LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams)MorePlMagic.getLayoutParams();
                linearParams.setMargins(Utils.dipToPixel(R.dimen.dp_10),0,Utils.dipToPixel(R.dimen.dp_10),0);
                MorePlMagic.setLayoutParams(linearParams);
                loadMorePtrFrame.setBackground(null);
                loadMorePtrFrame.setBackgroundColor(Color.parseColor("#f4f4f4"));
                header_new_banner_layout.setVisibility(View.GONE);
            }
            dismissDialog();
        }
        if (message.what == LogicActions.JDSortListSuccess) {
             list=(ArrayList<JDSort>) message.obj;
             if (list.size()>0){
               magic(list);
             }
        }




        //完成任务
        if (message.what == LogicActions.DayBuySuccess) {
            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ShareFinish"), false, 0);
        }

    }

    /**
     * 设置轮播图
     *
     * @param list
     */
    private void bannerImage(final ArrayList<JDBanner> list) {
        ArrayList<String> netWorkImage = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            netWorkImage.add(list.get(i).getImageUrl());
        }
        headerBanner.setPages(() -> new BannerMainAdapter(), netWorkImage);
        headerBanner.setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused});
        if (netWorkImage.size() > 1) {
            headerBanner.setPointViewVisible(true);
            headerBanner.startTurning(4000);
            headerBanner.setCanLoop(true);
        } else {
            headerBanner.setPointViewVisible(false);
            headerBanner.setCanLoop(false);
        }
        headerBanner.setOnItemClickListener(position -> {
            if(list.get(position).getNeedLogin()==null){
                list.get(position).setNeedLogin(false);
            }
            Utils.getJumpTypeForMorePl(this,list.get(position).getClickType(),list.get(position).getClickVaule(),platformType,list.get(position).getNeedLogin(),list.get(position).getName(),list.get(position).getSysParam());
        });
    }

    /**
     * 设置导航和viewpager
     */
    private void magic(final ArrayList<JDSort> list) {
        // 初始化FragmentManager对象
        fragmentManager = getSupportFragmentManager();
        // 初始化Fragment页面
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            oneFragmentNew = MorePlFragment.getInstance(list.get(i).getId(),i,platformType);
            fragments.add(oneFragmentNew);
        }
        fragmentAdapter = new FragmentAdapter(fragmentManager, fragments);
        fragmentOneContent.setAdapter(fragmentAdapter);
        fragmentOneContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }
            @Override
            public void onPageSelected(int index) {
                currentPos= index;
                MorePlFragment morePlFragment = (MorePlFragment) fragments.get(index);
                morePlFragment.onResume();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        CommonNavigator commonNavigator = new CommonNavigator(this);
        if (platformType==3||platformType==6){//唯品会 考拉 分类不同样式
        if (fragments.size()==3){
        commonNavigator.setAdjustMode(true);
        MorePlMagic.setPadding(Utils.dipToPixel(R.dimen.dp_25),0,Utils.dipToPixel(R.dimen.dp_25),0);
        }else if(fragments.size()==2){
            commonNavigator.setAdjustMode(true);
            MorePlMagic.setPadding(Utils.dipToPixel(R.dimen.dp_40),0,Utils.dipToPixel(R.dimen.dp_40),0);
        }else if (fragments.size()>3){
         commonNavigator.setScrollPivotX(0.5f);
        MorePlMagic.setPadding(Utils.dipToPixel(R.dimen.dp_10),0,Utils.dipToPixel(R.dimen.dp_10),0);
        }
        }else{
         commonNavigator.setScrollPivotX(0.5f);
        }
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(list.get(index).getName());
                if (platformType==3) {//唯品会分类不同样式
                    simplePagerTitleView.setNormalColor(Color.parseColor("#666666"));
                    simplePagerTitleView.setSelectedColor(Color.parseColor("#000000"));
                }else if(platformType==6){//考拉
                    simplePagerTitleView.setNormalColor(Color.parseColor("#666666"));
                    simplePagerTitleView.setSelectedColor(Color.parseColor("#000000"));
                }else{
                    simplePagerTitleView.setNormalColor(Color.parseColor("#666666"));
                    simplePagerTitleView.setSelectedColor(Color.parseColor("#000000"));
                }
                simplePagerTitleView.setTextSize(17);
                ((ScaleTransitionPagerTitleView) simplePagerTitleView).setMinScale(0.88f);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentPos= index;
                        fragmentOneContent.setCurrentItem(index);
                        MorePlFragment morePlFragment = (MorePlFragment) fragments.get(index);
                        morePlFragment.onResume();
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                if (platformType==3){
                    LinePagerIndicator indicator = new LinePagerIndicator(context);
                    indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                    indicator.setLineWidth(Utils.dipToPixel(R.dimen.dp_29));
                    indicator.setLineHeight(Utils.dipToPixel(R.dimen.dp_3));
                    indicator.setRoundRadius(5);
                    indicator.setYOffset(Utils.dipToPixel(R.dimen.dp_5));
                    indicator.setColors(Color.parseColor("#000000"));
                    return indicator;
                }else if(platformType==6){
                    LinePagerIndicator indicator = new LinePagerIndicator(context);
                    indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                    indicator.setLineWidth(Utils.dipToPixel(R.dimen.dp_29));
                    indicator.setLineHeight(Utils.dipToPixel(R.dimen.dp_3));
                    indicator.setRoundRadius(5);
                    indicator.setYOffset(Utils.dipToPixel(R.dimen.dp_5));
                    indicator.setColors(Color.parseColor("#000000"));
                    return indicator;
                }else{
//                    ArcPagerIndicator indicator = new ArcPagerIndicator(context);
//                    indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
//                    indicator.setLineWidth(Utils.dipToPixel(R.dimen.dp_18));
//                    indicator.setLineHeight(Utils.dipToPixel(R.dimen.dp_13));
//                    indicator.setColors(Color.parseColor("#000000"));
//                    indicator.setYOffset(Utils.dipToPixel(R.dimen.dp_2));
//                    return indicator;
                    LinePagerIndicator indicator = new LinePagerIndicator(context);
                    indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                    indicator.setLineWidth(Utils.dipToPixel(R.dimen.dp_29));
                    indicator.setLineHeight(Utils.dipToPixel(R.dimen.dp_3));
                    indicator.setRoundRadius(5);
                    indicator.setYOffset(Utils.dipToPixel(R.dimen.dp_5));
                    indicator.setColors(Color.parseColor("#000000"));
                    return indicator;
                }
            }
        });
        MorePlMagic.setNavigator(commonNavigator);
        ViewPagerHelper.bind(MorePlMagic, fragmentOneContent);
    }


    @OnClick({R.id.back,R.id.pl_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                isFinish();
                break;
            case R.id.pl_search:
                Intent intent = new Intent(this, MorePlSearchNewActivity.class);
                intent.putExtra("isCheck", false);
                if(platformType==2){
                    intent.putExtra("platType","2");
                }else if(platformType==1){
                    intent.putExtra("platType","1");
                }else if(platformType==3){
                    intent.putExtra("platType","3");
                }else{
                    intent.putExtra("platType","5");
                }
                startActivity(intent);
                break;
        }
    }


    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) AppBar_Layout.getLayoutParams()).getBehavior();
        if (behavior instanceof AppBarLayout.Behavior) {
            AppBarLayout.Behavior appBarLayoutBehavior = (AppBarLayout.Behavior) behavior;
            int topAndBottomOffset = appBarLayoutBehavior.getTopAndBottomOffset();
            if (topAndBottomOffset == 0) {
                return true;
            }
        }
        return false;
    }
    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        isFirst=false;
        refreshBanner();
        InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("RefreshSortList"), false, currentPos);
    }

    /**
     * 下拉刷新设置
     */
    private void ptrFrameLayout() {
        if (platformType==3){
            loadView.setTextColor(Color.BLACK);
        }else{
            loadView.setTextColor(Color.WHITE);
        }
        loadView.setTextColor(Color.parseColor("#333333"));
        loadMorePtrFrame.setLoadingMinTime(700);
        loadMorePtrFrame.setHeaderView(loadView);
        loadMorePtrFrame.addPtrUIHandler(loadView);
        loadMorePtrFrame.setPtrHandler(this);
    }
//
    public void refreshComplete(){
        loadMorePtrFrame.refreshComplete();
    }

    private boolean isFirst=true;
    private void refreshBanner(){
            // 请求广告
            paramMap.clear();
            paramMap.put("userid", login.getUserid());
            if (platformType==2){
                NetworkRequest.getInstance().GETNew(handler, paramMap, "JDBanner", HttpCommon.JDBanner);
            }else if (platformType==1){
                NetworkRequest.getInstance().GETNew(handler, paramMap, "JDBanner", HttpCommon.PDDBanner);
            }else if(platformType==3){
                NetworkRequest.getInstance().GETNew(handler, paramMap, "JDBanner", HttpCommon.WPHBanner);
            }else if(platformType==5){
                NetworkRequest.getInstance().GETNew(handler, paramMap, "JDBanner", HttpCommon.SNBanner);
            }else{
                NetworkRequest.getInstance().GETNew(handler, paramMap, "JDBanner", HttpCommon.KLBanner);
            }

            if (list!=null&&list.size()<=0){
                //请求头部分类列表
                paramMap.clear();
                if (platformType==2){
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "JDSortList", HttpCommon.JDSortList);
                }else if (platformType==1){
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "JDSortList", HttpCommon.PDDSortList);
                }else if(platformType==3){
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "JDSortList", HttpCommon.WPHSortList);
                }else if(platformType==5){
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "JDSortList", HttpCommon.SNSortList);
                }else{
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "JDSortList", HttpCommon.KLSortList);
                }
            }
    }

}
