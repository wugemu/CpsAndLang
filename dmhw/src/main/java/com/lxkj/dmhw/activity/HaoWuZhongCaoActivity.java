package com.lxkj.dmhw.activity;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.ahtrun.mytablayout.ProxyDrawable;
import com.ahtrun.mytablayout.ProxyDrawable2;
import com.example.test.andlang.andlangutil.BaseLangActivity;
import com.example.test.andlang.util.LogUtil;
import com.example.test.andlang.util.StatusBarUtils;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.adapter.ClassTabAdatper;
import com.lxkj.dmhw.adapter.IncomeAdapter;
import com.lxkj.dmhw.adapter.ZhongCaoAdapter;
import com.lxkj.dmhw.bean.HaoWuClass;
import com.lxkj.dmhw.bean.self.ActivityBean;
import com.lxkj.dmhw.bean.self.HomeZcBean;
import com.lxkj.dmhw.model.ZhongCaoModel;
import com.lxkj.dmhw.presenter.ZhongCaoPresenter;
import com.lxkj.dmhw.utils.BBCUtil;
import com.lxkj.dmhw.widget.banner.ShareCardItem;
import com.lxkj.dmhw.widget.banner.ShareCardView;
import com.lxkj.dmhw.widget.mytab.TabLayout;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Observable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 好物种草
 */
public class HaoWuZhongCaoActivity extends BaseLangActivity<ZhongCaoPresenter> {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.home_banner)
    ShareCardView homeBanner;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.app_bar)
    AppBarLayout appbar_todaysale;
    @BindView(R.id.m_statusBar)
    View mStatusBar;
    @BindView(R.id.lang_tv_title)
    TextView langTvTitle;
    @BindView(R.id.lang_iv_back)
    ImageView langIvBack;
    @BindView(R.id.rl_top)
    RelativeLayout rl_top;
    @BindView(R.id.lang_top_line)
    View lang_top_line;
    @BindView(R.id.top)
    LinearLayout top;
    @BindView(R.id.ll_top)
    LinearLayout ll_top;

    private int height;
//    private ClassTabAdatper classTabAdatper;
    private ZhongCaoAdapter zhongCaoAdapter;
    private List<HaoWuClass> haoWuClasses;
    private LinearLayout mTabStrip;
    @Override
    public int getLayoutId() {
        return R.layout.activity_hao_wu_zhong_cao;
    }

    @Override
    public void initView() {
        mStatusBar.setVisibility(View.VISIBLE);
        mStatusBar.setBackgroundResource(R.color.transparent);
        mStatusBar.getLayoutParams().height = StatusBarUtils.getStatusHeight(this);
        lang_top_line.setBackgroundResource(R.color.transparent);

        langTvTitle.setText("好物种草");
        height = (int) (BBCUtil.getDisplayWidth(this) * 360.0 / 375);
        LinearLayout.LayoutParams homeBannerParams = (LinearLayout.LayoutParams) homeBanner.getLayoutParams();
        homeBannerParams.height = height;
        //设置可下拉刷新位置
        appbar_todaysale.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) ll_top.getLayoutParams();
                if (Math.abs(verticalOffset) < (homeBannerParams.height - tab.getMeasuredHeight())) {//不需要margin
                    ll_top.setPadding(0, 0, 0, 0);
                    params.height = (int) getResources().getDimension(R.dimen.dp_60);
                } else {
                    params.height = (int) getResources().getDimension(R.dimen.dp_60) + Math.abs(verticalOffset) - homeBannerParams.height + tab.getMeasuredHeight();
                    ll_top.setPadding(0, Math.abs(verticalOffset) - homeBannerParams.height + tab.getMeasuredHeight(), 0, 0);
                }

                if (Math.abs(verticalOffset) >= rl_top.getHeight()) {
                    rl_top.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    langIvBack.setImageResource(R.mipmap.back2);
                    langTvTitle.setTextColor(getResources().getColor(R.color.colorBlackText));
                    mStatusBar.setBackgroundResource(R.color.colorWhite);
                } else {
                    rl_top.setBackgroundColor(getResources().getColor(R.color.transparent));
                    langIvBack.setImageResource(R.mipmap.back);
                    langTvTitle.setTextColor(getResources().getColor(R.color.colorWhite));
                    mStatusBar.setBackgroundResource(R.color.transparent);
                }
            }
        });
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvList.setLayoutManager(linearLayoutManager);
        updateTabTextView(tab.getTabAt(tab.getSelectedTabPosition()), true);

        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                updateTabTextView(tab, true);
                refershData(haoWuClasses.get(tab.getPosition()),false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                updateTabTextView(tab, false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        initLoading();
    }

    private void updateTabTextView(TabLayout.Tab tab, boolean isSelect) {
        if (isSelect) {
            try {
                java.lang.reflect.Field fieldView= tab.getClass().getDeclaredField("mView");
                fieldView.setAccessible(true);
                View view= (View) fieldView.get(tab);
                java.lang.reflect.Field fieldTxt= view.getClass().getDeclaredField("mTextView");
                fieldTxt.setAccessible(true);
                TextView tabSelect= (TextView) fieldTxt.get(view);
                tabSelect.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                tabSelect.setText(tab.getText());
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            try {
                java.lang.reflect.Field fieldView= tab.getClass().getDeclaredField("mView");
                fieldView.setAccessible(true);
                View view= (View) fieldView.get(tab);
                java.lang.reflect.Field fieldTxt= view.getClass().getDeclaredField("mTextView");
                fieldTxt.setAccessible(true);
                TextView tabSelect= (TextView) fieldTxt.get(view);
                tabSelect.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                tabSelect.setText(tab.getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void initPresenter() {
        presenter=new ZhongCaoPresenter(this, ZhongCaoModel.class);
    }

    @Override
    public void initData() {
        showWaitDialog();
        presenter.reqMaterialBase();
    }

    @Override
    public void update(Observable observable, Object o) {
        if ("reqMaterialBase".equals(o)){
            if (presenter.model.getZhongCao().getBanner()!=null&&presenter.model.getZhongCao().getBanner().size()>0){
                initBanner(presenter.model.getZhongCao().getBanner());
            }
            tab.setTabMode(TabLayout.MODE_FIXED);
            haoWuClasses=presenter.model.getZhongCao().getCoreMaterialList();
//            classTabAdatper = new ClassTabAdatper(this, tab, handler);
//            classTabAdatper.creatTimeTab(presenter.model.getZhongCao().getCoreMaterialList());
//            classTabAdatper.refreshTimeTab();
            for (int i = 0; i < haoWuClasses.size(); i++) {
                HaoWuClass timeBean = haoWuClasses.get(i);
                TabLayout.Tab t=tab.newTab();
                t.setText(timeBean.getName());
                tab.addTab(t);
            }
            reflex(this);
            tab.getTabAt(0).select();
            refershData(haoWuClasses.get(0),false);
        }else if ("findMaterialDetailPage".equals(o)){
            dismissWaitDialog();
            if (zhongCaoAdapter==null){
                zhongCaoAdapter=new ZhongCaoAdapter(this,presenter.model.getZcList());
                rvList.setAdapter(zhongCaoAdapter);
            }else{
                zhongCaoAdapter.notifyDataSetChanged();
            }
        }

    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
//                case 1:
//                    refershData(classTabAdatper.getItemSelect(msg.arg1),false);//切换tab
//                    break;
//                case 0:
//                    refershData(classTabAdatper.getItemSelect(msg.arg1),true);//加载更多
//                    break;
            }
        }
    };

    private void refershData(HaoWuClass timeBean,boolean loadMore) {
        presenter.findMaterialDetailPage(timeBean.getId(),loadMore);

    }

    private void initBanner(List<ActivityBean> bannerList) {
        ShareCardItem shareCardItem = new ShareCardItem();
        shareCardItem.setDataList(bannerList);
        homeBanner.setCardData(shareCardItem);
        homeBanner.setOnItemClickL(new ShareCardView.OnItemClickL() {
            @Override
            public void onItemClick(Object object, int postion) {
                ActivityBean bean = (ActivityBean) object;
                if (bean == null) {
                    return;
                }
            }
        });
    }
    public void reflex(final Activity activity) {
        tab.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    mTabStrip = (LinearLayout) tab.getChildAt(0);
                    mTabStrip.setBackgroundDrawable(new ProxyDrawable2(mTabStrip));
                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {

                        View tabView = mTabStrip.getChildAt(i);
//                        tabView.setId(i);
//                        tabView.setOnClickListener(btnClick);
                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);
                        TextView mTextView = (TextView) mTextViewField.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);
                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        int margin = (int) getResources().getDimension(R.dimen.view_toview_two);
                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
//                        int width= (int) (getResources().getDimension(R.dimen.tag_text_size)*2);
                        int width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }
                        if (tab.getTabMode() == com.lxkj.dmhw.widget.mytab.TabLayout.MODE_FIXED) {
                            //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                            tab.measure(w, h);
                            int screenWidth = tab.getMeasuredWidth();
//                            margin = (int) ((screenWidth / mTabStrip.getChildCount()) / 2.0);
//                            margin = (int) ((screenWidth / mTabStrip.getChildCount() - width) / 2.0);
                        }
                        params.leftMargin = margin;
                        params.rightMargin = margin;
                        tabView.setLayoutParams(params);
                        tabView.invalidate();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private View.OnClickListener btnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int pos=v.getId();
            refershData(haoWuClasses.get(pos),false);
        }
    };


    @OnClick(R.id.lang_ll_back)
    public void onViewClicked() {
        goBack();
    }
}