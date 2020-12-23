package com.lxkj.dmhw.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.activity.AliAuthWebViewActivity;
import com.lxkj.dmhw.activity.ExchangeDMBActivity;
import com.lxkj.dmhw.activity.InvitationActivity;
import com.lxkj.dmhw.activity.LoginActivity;
import com.lxkj.dmhw.activity.WebViewActivity;
import com.lxkj.dmhw.activity.WithdrawalsActivity;
import com.lxkj.dmhw.adapter.BannerAdapter1;
import com.lxkj.dmhw.adapter.BannerM1Adapter;
import com.lxkj.dmhw.adapter.MyTaskAdapter;
import com.lxkj.dmhw.adapter.MyTaskMainAdapter;
import com.lxkj.dmhw.bean.Banner;
import com.lxkj.dmhw.bean.MyTaskList;
import com.lxkj.dmhw.bean.Poster;
import com.lxkj.dmhw.bean.ShareParams;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.dialog.FirstBuyTaskDialog;
import com.lxkj.dmhw.dialog.GetuiTaskDialog;
import com.lxkj.dmhw.dialog.RegisterTaskDialog;
import com.lxkj.dmhw.dialog.SignDialog;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.MyLayoutManager;
import com.lxkj.dmhw.utils.RiseNumberTextView;
import com.lxkj.dmhw.utils.Share;
import com.lxkj.dmhw.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 我的任务 呆萌币主页
 */
public class MyTaskFragment extends BaseFragment implements PtrHandler,View.OnClickListener{
    @BindView(R.id.load_more_ptr_frame)
    PtrClassicFrameLayout loadMorePtrFrame;
    ImageView task_top_unlogin_bg_iv;
    @BindView(R.id.fragment_task_list)
    RecyclerView fragment_task_list;


    View bar;

    private MyTaskMainAdapter adapter;
    RecyclerView task_new;
    RecyclerView task_everyday;
    MyTaskAdapter adapter_new;
    MyTaskAdapter adapter_every;
    LinearLayout user_info_layout,task_new_layout_ll,backlayout;
    TextView exchange_txt,withdraw_txt,day_cnt_title1,day_cnt_title2,fresh_cnt_title1,fresh_cnt_title2;
    RiseNumberTextView money_top_tv,dmb_top_tv;
    private View TaskHeader;
    private int shareNum=0;
    //签名奖励个数
    private String signCount="0";
    private String mny="";
    private String friendsCount="";

    ConvenientBanner headerFragmentOneNewSixBanner;

    private String totalDmbCount;
    private String exchangeRatio;
//    private String canWidthdrawmoney="0.0";

    private Poster poster = new Poster();


    private int theLastbeforeScore=0;
    private float theLastbeforeMonney=0;
    MyTaskList myTaskList =new MyTaskList();

    public static MyTaskFragment getInstance() {
        MyTaskFragment fragment = new MyTaskFragment();
        return fragment;
    }
    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_task, null);
        TaskHeader = View.inflate(getActivity(), R.layout.activity_my_task_headview, null);
        ButterKnife.bind(this, view);
        return view;
    }
    @Override
    public void onData() {

    }
    @Override
    public void onEvent() {
        task_top_unlogin_bg_iv=findViewHeader(R.id.task_top_unlogin_bg_iv);
        user_info_layout=findViewHeader(R.id.user_info_layout);
        backlayout=findViewHeader(R.id.back);
        exchange_txt=findViewHeader(R.id.exchange_txt);
        money_top_tv=findViewHeader(R.id.money_top_tv);
        dmb_top_tv=findViewHeader(R.id.dmb_top_tv);
        bar=findViewHeader(R.id.bar);
        day_cnt_title1=findViewHeader(R.id.day_cnt_title1);
        day_cnt_title2=findViewHeader(R.id.day_cnt_title2);
        fresh_cnt_title1=findViewHeader(R.id.fresh_cnt_title1);
        fresh_cnt_title2=findViewHeader(R.id.fresh_cnt_title2);

        task_new_layout_ll=findViewHeader(R.id.task_new_layout_ll);

        withdraw_txt=findViewHeader(R.id.withdraw_txt);
        task_everyday=findViewHeader(R.id.task_everyday);
        task_new=findViewHeader(R.id.task_new);
        headerFragmentOneNewSixBanner=findViewHeader(R.id.my_task_new_vertical_banner);
        exchange_txt.setOnClickListener(this);
        withdraw_txt.setOnClickListener(this);
        backlayout.setOnClickListener(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        if (DateStorage.getLoginStatus()){
            task_top_unlogin_bg_iv.setImageResource(R.mipmap.four_fragment_top_bg);
            user_info_layout.setVisibility(View.VISIBLE);
        }else{
            user_info_layout.setVisibility(View.GONE);
            task_top_unlogin_bg_iv.setImageResource(R.mipmap.four_fragment_top_bg);
        }

        adapter_every=new MyTaskAdapter(getActivity());
        task_everyday.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), false));
        task_everyday.setNestedScrollingEnabled(false);
        task_everyday.setAdapter(adapter_every);

        adapter_new=new MyTaskAdapter(getActivity());
        task_new.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), false));
        task_new.setNestedScrollingEnabled(false);
        task_new.setAdapter(adapter_new);


        task_everyday.setFocusableInTouchMode(false);
        task_everyday.setFocusable(false);
        task_everyday.requestFocus(); //设置焦点不需要
        task_new.setFocusableInTouchMode(false);
        task_new.setFocusable(false);
        task_new.requestFocus(); //设置焦点不需要

        //头部banner 轮播图适配 宽高比例是351:74
        LinearLayout.LayoutParams linearParamsOne =(LinearLayout.LayoutParams)headerFragmentOneNewSixBanner.getLayoutParams();
        linearParamsOne.width=width;
        linearParamsOne.height=width*151/375;
        headerFragmentOneNewSixBanner.setLayoutParams(linearParamsOne);

        adapter_new.setOnItemClickListener(new MyTaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, MyTaskList.dailyItem data) {
                if (!DateStorage.getLoginStatus()){
                    Intent intent=new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                String cnt = data.getCnt();
                switch (data.getType()){
                   case "fristbuy":
                       if (cnt.equals("0")) {
                           InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("GoMain"), false, 0);
                       }else if(cnt.equals("2")){
                           mny=data.getMny();
                           paramMap.clear();
                           paramMap.put("ssid", data.getSsid());
                           NetworkRequest.getInstance().POST(handler, paramMap, "FirstBuy", HttpCommon.GetFristBuy);
                       }
                        break;
                    case "regist":
                        if (cnt.equals("0")) {
                            Intent intent = new Intent(getActivity(), InvitationActivity.class);
                            intent.putExtra("isCheck", false);
                            startActivity(intent);
                        }
                        break;
                    case "search":
                        if (cnt.equals("0")) {
                            if (!data.getUrl().equals("")){
                                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                                intent.putExtra(Variable.webUrl, data.getUrl());
                                intent.putExtra("isTitle" , true);
                                startActivity(intent);
                            }
                        }
                        break;
                }
            }
        });

        adapter_every.setOnItemClickListener(new MyTaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, MyTaskList.dailyItem data) {
                if (!DateStorage.getLoginStatus()){
                    Intent intent=new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                String cnt = data.getCnt();
                switch (data.getType()){
                    case "sign":
                        if (cnt.equals("0")) {
                            signCount = data.getValue();
                            paramMap.clear();
                            paramMap.put("type", "sign");
                            NetworkRequest.getInstance().POST(handler, paramMap, "DailySign", HttpCommon.finishTask);
                        }
                        break;
                    case "daybuy":
                        if (cnt.equals("0")) {
                            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("GoMain"), false, 0);
                        }
                        break;
                    case "childfristbuy":
                        if (Integer.parseInt(cnt) <= 0) {
                            httpShareApp();
                        }else{
                            mny=data.getMny();
                            friendsCount=data.getCnt();
                            paramMap.clear();
                            paramMap.put("ssid",data.getSsid());
                            NetworkRequest.getInstance().POST(handler, paramMap, "ChildFristBuy", HttpCommon.ChildFristBuy);
                        }
                        break;
                    case "push":
                        if (cnt.equals("0")) {
                            GetuiTaskDialog getuiTaskDialog=new GetuiTaskDialog(getActivity());
                            getuiTaskDialog.showDialog();
                        }
                        break;
                    case "shareshop":
                        if (Integer.parseInt(cnt) < Integer.parseInt(data.getMax())) {
                            if (!data.getUrl().equals("")){
                                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                                intent.putExtra(Variable.webUrl, data.getUrl());
                                intent.putExtra("isTitle" , true);
                                startActivity(intent);
                            }
                        }
                        break;
                    case "invite":
                        if (Integer.parseInt(cnt) < Integer.parseInt(data.getMax())) {
                            httpShareApp();
                        }
                        break;
                }

            }
        });
        fragment_task_list.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), false));
        adapter = new MyTaskMainAdapter(getActivity());
        fragment_task_list.setAdapter(adapter);
        adapter.setHeaderView(TaskHeader);
        initTaskList();

    }
    boolean isfirst = true;
    private void initTaskList(){
        if (isfirst){
            isfirst=false;
            showDialog();
        }

        if (myTaskList.getScoremny().equals("")){
         myTaskList.setScoremny("0");
        }
        theLastbeforeMonney=Float.parseFloat(myTaskList.getScoremny());

        if(myTaskList.getUserscore().equals("")){
        myTaskList.setUserscore("0");
        }
        theLastbeforeScore=Integer.parseInt(myTaskList.getUserscore());

        //任务页面轮播图
        paramMap.clear();
        paramMap.put("userid", login.getUserid());
        paramMap.put("advertisementposition", "61");
        NetworkRequest.getInstance().POST(handler, paramMap, "OneFragmentBanner", HttpCommon.Banner);

        paramMap.clear();
        paramMap.put("userid", login.getUserid());
        NetworkRequest.getInstance().POST(handler, paramMap, "InitTaskList", HttpCommon.GetInitTaskList);
    }



    @Override
    public void onCustomized() {
        ptrFrameLayout();
    }

    @Override
    public void mainMessage(Message message) {
        if (message.what == LogicActions.LoginStatusSuccess) {
            if (DateStorage.getLoginStatus()){
                task_top_unlogin_bg_iv.setImageResource(R.mipmap.four_fragment_top_bg);
                user_info_layout.setVisibility(View.VISIBLE);
            }else{
                user_info_layout.setVisibility(View.GONE);
                task_top_unlogin_bg_iv.setImageResource(R.mipmap.four_fragment_top_bg);
            }
            //初始化数值
            myTaskList.setUserscore("0");
            myTaskList.setScoremny("0");
            initTaskList();
        }

        if (message.what == LogicActions.ShareFinishSuccess){
            initTaskList();
        }

        //注册成功,分享好友成功，刷新数据
        if (message.what == LogicActions.RegisterStatusSuccess) {
          initTaskList();
        }
        //分享成功
        if (message.what == LogicActions.ShareStatusSuccess&&message.arg1==4){
            paramMap.clear();
            paramMap.put("type", "invite");
            NetworkRequest.getInstance().POST(handler, paramMap, "DayBuy", HttpCommon.finishTask);
        }
        //刷新通知 提现或者兑换之后刷新
        if (message.what == LogicActions.RefreshTaskListSuccess) {
          initTaskList();
        }

    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
     if (message.what==LogicActions.InitTaskListSuccess){
         loadMorePtrFrame.refreshComplete();
      myTaskList= (MyTaskList) message.obj;
      totalDmbCount=myTaskList.getUserscore();
      exchangeRatio=myTaskList.getExchange();

         if (!myTaskList.getScoremny().equals("")) {
             money_top_tv.withNumber(Float.parseFloat(myTaskList.getScoremny()), theLastbeforeMonney).start();
         }
         if(!myTaskList.getUserscore().equals("")){
             dmb_top_tv.withNumber(Integer.parseInt(myTaskList.getUserscore()),theLastbeforeScore).start();
         }
       fresh_cnt_title1.setText(myTaskList.getFreshcnt());
       fresh_cnt_title2.setText(myTaskList.getFreshtotalcnt());
      day_cnt_title1.setText(myTaskList.getDailycnt());
      day_cnt_title2.setText(myTaskList.getDailytotalcnt());
      if (myTaskList.getNewHandList().size()>0){
          task_new_layout_ll.setVisibility(View.VISIBLE);
          adapter_new.setNewData(myTaskList.getNewHandList());
          adapter_new.notifyDataSetChanged();
      }else{
          task_new_layout_ll.setVisibility(View.GONE);
      }
     adapter_every.setNewData(myTaskList.getDailyList());
     adapter_every.notifyDataSetChanged();
     dismissDialog();
     }
     //轮播图数据处理
    if (message.what == LogicActions.OneFragmentBannerSuccess) {
            if (message.obj!=null&&((ArrayList<Banner>) message.obj).size()>0) {
                headerFragmentOneNewSixBanner.setVisibility(View.VISIBLE);
                OneFragmentBanner((ArrayList<Banner>) message.obj);
            }else{
                headerFragmentOneNewSixBanner.setVisibility(View.GONE);
            }
        }
     //签到处理
   if (message.what==LogicActions.DailySignSuccess){
       //签到奖励弹框
        SignDialog dialog = new SignDialog(getActivity());
        dialog.showDialog(signCount);
        initTaskList();
   }
        //好友首购奖励
        if (message.what==LogicActions.ChildFristBuySuccess){
        FirstBuyTaskDialog dialog = new FirstBuyTaskDialog(getActivity());
        dialog.showDialog(1,friendsCount,mny);
        initTaskList();
        }
        //自身首购奖励
        if (message.what==LogicActions.FristBuySuccess){
            //自己首购以及好友首购奖励弹框
            FirstBuyTaskDialog dialog= new FirstBuyTaskDialog(getActivity());
            dialog.showDialog(0,"",mny);
            initTaskList();
        }
        // 获取APP分享地址
        if (message.what == LogicActions.AppShareSuccess) {
            poster = (Poster) message.obj;
            shareAppDialog();
        }
        //完成任务
        if (message.what == LogicActions.DayBuySuccess) {
          initTaskList();
        }
    }
    private boolean isshare=false;
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            // fragment隐藏时调用
            isshare=false;
        } else {
            // fragment显示时调用
            isshare=true;
        }
    }

    @Override
    public void onClick(View view) {
     Intent intent=null;
     switch (view.getId()){
         //跳转呆萌币兑换页面
         case R.id.exchange_txt:
             if (!DateStorage.getLoginStatus()){
                 intent = new Intent(getActivity(), LoginActivity.class);
             }else {
                 intent = new Intent(getActivity(), ExchangeDMBActivity.class);
                 intent.putExtra("totalDmbCount", totalDmbCount);
                 intent.putExtra("exchangeRatio", exchangeRatio);
             }
             break;
         //跳转呆萌币提现页面
         case R.id.withdraw_txt:
             if (!DateStorage.getLoginStatus()){
                 intent = new Intent(getActivity(), LoginActivity.class);
             }else {
                 intent = new Intent(getActivity(), WithdrawalsActivity.class);
                 intent.putExtra("widthdrawpos", "1");
             }
             break;
         case R.id.back:
             isFinish();
             break;
     }
     if (intent!=null){
      startActivity(intent);
     }
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T findViewHeader(int id) {
        return (T) TaskHeader.findViewById(id);
    }


    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, fragment_task_list, header);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
     initTaskList();
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    private void OneFragmentBanner(ArrayList<Banner> list) {
        ArrayList<String> netWorkImage = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            netWorkImage.add(list.get(i).getAdvertisementpic());
        }
        headerFragmentOneNewSixBanner.setPages(() -> new BannerAdapter1(), netWorkImage);
        headerFragmentOneNewSixBanner.setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused});
        if (netWorkImage.size() > 1) {
            headerFragmentOneNewSixBanner.setPointViewVisible(true);
            headerFragmentOneNewSixBanner.startTurning(4000);
            headerFragmentOneNewSixBanner.setCanLoop(true);
        } else {
            headerFragmentOneNewSixBanner.setPointViewVisible(false);
            headerFragmentOneNewSixBanner.setCanLoop(false);
        }
        headerFragmentOneNewSixBanner.setOnItemClickListener(position -> {
//            if (login.getUsertype().equals("3")){
//                toast("普通会员不支持该功能");
//                return;
//            }
//            if(login.getUsertype().equals("1")){
//                toast("运营商不支持该功能");
//                return;
//            }
            if (list.get(position).getJumptype().equals("61")){
                httpShareApp();
            }else {
                Utils.getJumpType(getActivity(), list.get(position).getJumptype(), list.get(position).getAdvertisementlink(), list.get(position).getNeedlogin(),list.get(position).getAdvertisemenid());
            }
            });
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
        //左右滑动时刷新控件禁止掉
        loadMorePtrFrame.disableWhenHorizontalMove(true);
    }

    //去邀请直接弹出分享APP的功能
    private void httpShareApp(){
        // 获取分享地址
        if (!DateStorage.getLoginStatus())
        {
            startActivity(new Intent(getActivity(),LoginActivity.class));
            return;
        }
        if (poster.getAppsharelink().equals("")){
            paramMap.clear();
            paramMap.put("userid", login.getUserid());
            NetworkRequest.getInstance().POST(handler, paramMap, "AppShare", HttpCommon.AppShare);
        }else{
            shareAppDialog();
        }

    }
    //调出分享窗口
    private void shareAppDialog(){
        ShareParams params = new ShareParams();
        params.setContent(Variable.shareContent);
        params.setShareTag(4);
        params.setUrl(poster.getAppsharelink());
        Share.getInstance(0).initialize(params,1);
    }
}
