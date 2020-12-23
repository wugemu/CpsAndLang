package com.lxkj.dmhw.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.activity.ComCollArticleDetailActivity;
import com.lxkj.dmhw.activity.ComCollHistoryActivity;
import com.lxkj.dmhw.activity.ComCollSearchFragmentActivity;
import com.lxkj.dmhw.activity.LoginActivity;
import com.lxkj.dmhw.activity.NewHandListActivity;
import com.lxkj.dmhw.activity.VideoActivity300;
import com.lxkj.dmhw.adapter.BannerM1Adapter;
import com.lxkj.dmhw.adapter.ComCollAdapter;
import com.lxkj.dmhw.adapter.ComCollPartOneAdapter;
import com.lxkj.dmhw.adapter.ComCollPartTwoAdapter;
import com.lxkj.dmhw.adapter.LimitScrollViewAdapter;
import com.lxkj.dmhw.bean.ComCollegeData;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.defined.ObserveGridView;
import com.lxkj.dmhw.defined.PtrClassicRefreshLayout;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.MyLayoutManager;
import com.lxkj.dmhw.utils.Utils;
import com.lxkj.dmhw.view.LimitScrollerView;
import com.lxkj.dmhw.view.RoundLayoutNew;
import com.lxkj.dmhw.view.StrongerRecyclelView;

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
 * 商学院
 *
 */

public class CommercialCollegeFragment extends BaseFragment implements PtrHandler, BaseQuickAdapter.OnItemClickListener,ComCollPartOneAdapter.OnPersonalAppIconClickListener, View.OnClickListener {
    //下拉刷新
    @BindView(R.id.load_more_ptr_frame)
    PtrClassicRefreshLayout loadMorePtrFrame;
    @BindView(R.id.fragment_comcoll_recycler)
    RecyclerView fragment_comcoll_recycler;
    @BindView(R.id.bar)
    View bar;

    @BindView(R.id.search_layout)
    RelativeLayout search_layout;
    @BindView(R.id.history_layout)
    RelativeLayout history_layout;

    // 主适配器  热门课程
    //首页数据实体
    private ComCollegeData comCollegeData = new ComCollegeData();
    private ComCollAdapter adapter;
//    private String id;
    private View header;
    RelativeLayout hotcourse_more;

    //轮播图
    ConvenientBanner header_comcoll_banner;
    RoundLayoutNew header_comcoll_banner_layout;
    LinearLayout indicator_view_layout;
    RelativeLayout comcoll_part01_layout;
    //轮播图的点
    private ArrayList<ImageView> mPointViews = new ArrayList<ImageView>();
    private int[] page_indicatorId;
    //轮播图之下
    ObserveGridView comcoll_gv;
    ComCollPartOneAdapter comCollPartOneAdapter;
    //热门问题
    RelativeLayout arr_iv_layout;
    private LimitScrollerView limitScrollerView;
    private LimitScrollViewAdapter limitScrollViewAdapter;
    //新手视频
    StrongerRecyclelView comcoll_newhand_recycler;
    ComCollPartTwoAdapter comCollPartTwoAdapter;
    RelativeLayout newhand_more;
    TextView hotcourse_title,newhand_title;

    public static CommercialCollegeFragment getInstance(String id) {
        CommercialCollegeFragment fragment = new CommercialCollegeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onData() {
//        Bundle bundle = getArguments();
//        if (bundle != null) {
//            id = bundle.getString("id");
//        }
    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comcoll, null);
        header = View.inflate(getActivity(), R.layout.comcoll_fragment_headview, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams();
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        ptrFrameLayout();
        hotcourse_more=findViewHeader(R.id.hotcourse_more);
        hotcourse_more.setOnClickListener(this);
        //头部空间声明
        //轮播图
        header_comcoll_banner=findViewHeader(R.id.header_comcoll_banner);
        indicator_view_layout=findViewHeader(R.id.indicator_view_layout);
        header_comcoll_banner_layout=findViewHeader(R.id.header_comcoll_banner_layout);
        header_comcoll_banner_layout.setCornerRadius(Utils.dipToPixel(R.dimen.dp_8));
        header_comcoll_banner_layout.setRoundMode(RoundLayoutNew.MODE_TOP);
        comcoll_part01_layout=findViewHeader(R.id.comcoll_part01_layout);
        //轮播图下面 六宫格
        comcoll_gv=findViewHeader(R.id.comcoll_gv);
        comCollPartOneAdapter = new ComCollPartOneAdapter(getActivity());
        comcoll_gv.setAdapter(comCollPartOneAdapter);
        comCollPartOneAdapter.setOnPersonalAppIconClickListener(this);

        //热门问题
        arr_iv_layout= findViewHeader(R.id.arr_iv_layout);
        arr_iv_layout.setOnClickListener(this);
        limitScrollerView= findViewHeader(R.id.limitScroll);
        limitScrollViewAdapter = new LimitScrollViewAdapter();
        limitScrollerView.setDataAdapter(limitScrollViewAdapter);
        //设置条目点击事件
        limitScrollerView.setOnItemClickListener(new LimitScrollerView.OnItemClickListener() {
            @Override
            public void onItemClick(Object obj) {
                if (!DateStorage.getLoginStatus()){
                   Intent intent=new Intent(getActivity(),LoginActivity.class);
                   startActivity(intent);
                    return;
                }
                if(obj instanceof  ComCollegeData.Question) {
                    ComCollegeData.Question data= (ComCollegeData.Question) obj;
                    //跳转问题详情
                    Intent intent=new Intent(getActivity(), ComCollArticleDetailActivity.class);
                    intent.putExtra("from","Ques");
                    intent.putExtra("articleId",data.getId());
                    startActivity(intent);
                }
            }
        });

        hotcourse_title= findViewHeader(R.id.hotcourse_title);
        newhand_title= findViewHeader(R.id.newhand_title);
        TextPaint tp= hotcourse_title.getPaint();
        TextPaint tp1= newhand_title.getPaint();
        tp.setFakeBoldText(true);
        tp1.setFakeBoldText(true);
        //新手视频
        comcoll_newhand_recycler=findViewHeader(R.id.comcoll_newhand_recycler);
        newhand_more=findViewHeader(R.id.newhand_more);
        newhand_more.setOnClickListener(this);
        // 设置Recycler
        comcoll_newhand_recycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), true));
        // 初始化adapter
        comCollPartTwoAdapter = new ComCollPartTwoAdapter(getActivity());
        comcoll_newhand_recycler.setAdapter(comCollPartTwoAdapter);
        comCollPartTwoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ComCollegeData.NewHand hotItem= (ComCollegeData.NewHand) adapter.getData().get(position);
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
        });

        
        // 设置Recycler 热门教程
        fragment_comcoll_recycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), false));
        fragment_comcoll_recycler.setNestedScrollingEnabled(false);
        adapter = new ComCollAdapter(getActivity());
        adapter.setHeaderView(header);
        fragment_comcoll_recycler.setAdapter(adapter);
        // 监听item点击事件
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onCustomized() {
        showDialog();
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
        if (message.what == LogicActions.ComCollegeInitSuccess) {
            comCollegeData = (ComCollegeData) message.obj;
            //填充轮播图数据
            if (comCollegeData.getBannerList().size() > 0) {
                comcoll_part01_layout.setVisibility(View.VISIBLE);
                bannerImage(comCollegeData.getBannerList());
            } else {
                comcoll_part01_layout.setVisibility(View.GONE);
            }
            //填充分类数据
            if (comCollegeData.getPartOneList()!=null&&comCollegeData.getPartOneList().size()>0){
                comCollPartOneAdapter.setData(comCollegeData.getPartOneList());
            }
            //填充常见问题
            if (comCollegeData.getQuestionList()!=null&&comCollegeData.getQuestionList().size()>0){
                limitScrollViewAdapter.setDatas(getActivity(),comCollegeData.getQuestionList(),limitScrollerView);
            }
            //新手视频
            if (comCollegeData.getNewHandList()!=null&&comCollegeData.getNewHandList().size()>0){
                comCollPartTwoAdapter.setNewData(comCollegeData.getNewHandList());
            }

            //热门课程
            if (comCollegeData.getHotList()!=null&&comCollegeData.getHotList().size()>0){
                adapter.setNewData(comCollegeData.getHotList());
            }
        }
        loadMorePtrFrame.refreshComplete();
        dismissDialog();
    }

    //商学院首页接口
    private void httpPost() {
        paramMap = new HashMap<>();
        NetworkRequest.getInstance().POST(handler, paramMap, "ComCollegeInit", HttpCommon.ComCollegeInit);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    public <T extends View> T findViewHeader(int id) {
        return (T) header.findViewById(id);
    }

    private void initData() {
        page=1;
        httpPost();
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, fragment_comcoll_recycler, header);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
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
        ComCollegeData.NewHand hotItem= (ComCollegeData.NewHand) adapter.getData().get(position);
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


    /**
     * 轮播图数据填充
     * @param list
     */
    private void bannerImage(final ArrayList<ComCollegeData.Banner> list) {
        ArrayList<String> netWorkImage = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            netWorkImage.add(list.get(i).getAdvertisementpic());
        }
        header_comcoll_banner.setPages(() -> new BannerM1Adapter(), netWorkImage);
        indicator_view_layout.removeAllViews();
        mPointViews.clear();
        page_indicatorId= new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused};
        if (netWorkImage!=null&&netWorkImage.size()>0) {
            for (int count = 0; count < netWorkImage.size(); count++) {
                // 翻页指示的点
                ImageView pointView = new ImageView(getContext());
                pointView.setPadding(5, 0, 5, 0);
                if (mPointViews.isEmpty())
                    pointView.setImageResource(page_indicatorId[1]);
                else
                    pointView.setImageResource(page_indicatorId[0]);
                mPointViews.add(pointView);
                indicator_view_layout.addView(pointView);
            }
        }

        header_comcoll_banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (mPointViews.size() > 0) {
                    for (int i = 0; i < mPointViews.size(); i++) {
                        mPointViews.get(position).setImageResource(page_indicatorId[1]);
                        if (position != i) {
                            mPointViews.get(i).setImageResource(page_indicatorId[0]);
                        }
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        header_comcoll_banner.setPointViewVisible(false);

        if (netWorkImage.size() > 1) {
            header_comcoll_banner.startTurning(4000);
            header_comcoll_banner.setCanLoop(true);
        } else {
            indicator_view_layout.setVisibility(View.GONE);
            header_comcoll_banner.setCanLoop(false);
        }
        header_comcoll_banner.setOnItemClickListener(position -> {
        Utils.getJumpType(getActivity(), list.get(position).getJumptype(), list.get(position).getAdvertisementlink(), list.get(position).getNeedlogin(), list.get(position).getAdvertisemenid());
        });
    }

    //跳转视频列表
    @Override
    public void onPersonalAppIconClick(ComCollegeData.PartOne item) {
        Intent intent=new Intent(getActivity(), NewHandListActivity.class);
        intent.putExtra("from","partOne");
        intent.putExtra("id",item.getId());
        intent.putExtra("title",item.getTitle());
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        Intent intent=null;
       switch (v.getId()){
           case R.id.arr_iv_layout:
               intent = new Intent(getActivity(), NewHandListActivity.class);
               intent.putExtra("from", "partOneQues");
               intent.putExtra("id", "0");
               intent.putExtra("title", "常见问题");
               break;
           case R.id.newhand_more:
               intent=new Intent(getActivity(), NewHandListActivity.class);
               intent.putExtra("from","partTwo");
               intent.putExtra("id","1");
               intent.putExtra("title","新手视频");
               break;
           case R.id.hotcourse_more:
               intent=new Intent(getActivity(), NewHandListActivity.class);
               intent.putExtra("from","partThree");
               intent.putExtra("id","2");
               intent.putExtra("title","热门课程");
               break;
       }
       if (intent!=null)
       {
       startActivity(intent);
       }
    }


    @OnClick({R.id.history_layout,R.id.search_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.history_layout:
                if (!DateStorage.getLoginStatus()){
                    Intent intent =new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getActivity(), ComCollHistoryActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.search_layout:
                Intent intent1=new Intent(getActivity(), ComCollSearchFragmentActivity.class);
                startActivity(intent1);
                break;

        }
    }
}
