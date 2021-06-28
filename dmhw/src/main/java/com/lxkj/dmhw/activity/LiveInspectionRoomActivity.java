package com.lxkj.dmhw.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.adapter.InspectRoomAdapter;
import com.lxkj.dmhw.adapter.RoomFreeListAdapter;
import com.lxkj.dmhw.adapter.ShareCheck;
import com.lxkj.dmhw.bean.Alibc;
import com.lxkj.dmhw.bean.H5Link;
import com.lxkj.dmhw.bean.InspectionRoom;
import com.lxkj.dmhw.bean.ShareList;
import com.lxkj.dmhw.bean.ShareParams;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.dialog.AlipayDialog;
import com.lxkj.dmhw.dialog.CouponLinkDialog;
import com.lxkj.dmhw.dialog.TaobaoAuthLoginDialog;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.AlbumNotifyHelper;
import com.lxkj.dmhw.utils.MyLayoutManager;
import com.lxkj.dmhw.utils.NetStateUtils;
import com.lxkj.dmhw.utils.Share;
import com.lxkj.dmhw.utils.ToastUtil;
import com.lxkj.dmhw.utils.Utils;
import com.lxkj.dmhw.view.ScrollTextView;
import com.lxkj.dmhw.view.inspectroom.GoodAnimationUtile;
import com.lxkj.dmhw.view.inspectroom.GoodsInitUtile;
import com.lxkj.dmhw.view.inspectroom.MessagQuenView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LiveInspectionRoomActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener,BaseQuickAdapter.OnItemLongClickListener, AlibcTradeCallback {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.room_back_iv)
    ImageView room_back_iv;
    @BindView(R.id.tips_layout)
    LinearLayout tips_layout;
    @BindView(R.id.room_gift_layout)
    LinearLayout room_gift_layout;
    @BindView(R.id.goods_pic)
    ImageView goods_pic;
    @BindView(R.id.hide_layout)
    LinearLayout hide_layout;
    @BindView(R.id.sucai_layout )
    LinearLayout sucai_layout;
    @BindView(R.id.room_free_layout )
    LinearLayout room_free_layout;

    @BindView(R.id.like_image)
    ImageView like_image;

    @BindView(R.id.room_user_iv_01)
    ImageView room_user_iv_01;
    @BindView(R.id.room_user_iv_02)
    ImageView room_user_iv_02;
    @BindView(R.id.room_user_iv_03)
    ImageView room_user_iv_03;

    @BindView(R.id.room_share_btn)
    LinearLayout room_share_btn;
    @BindView(R.id.room_buy_btn)
    LinearLayout room_buy_btn;

    @BindView(R.id.rule_layout)
    LinearLayout rule_layout;

    @BindView(R.id.room_user_layout_01 )
    RelativeLayout room_user_layout_01;
    @BindView(R.id.room_user_layout_02 )
    RelativeLayout room_user_layout_02;
    @BindView(R.id.room_user_layout_03 )
    RelativeLayout room_user_layout_03;


    @BindView(R.id.activity_inspection_room)
    RecyclerView activity_inspection_room;
    private InspectRoomAdapter adapter;
    //详情数据
    private InspectionRoom inspectionRoom=new InspectionRoom();
    List<InspectionRoom.Detail> details = new ArrayList<>();
    List<InspectionRoom.Detail> itemdetails;
    List<InspectionRoom.Free> frees = new ArrayList<>();
    List<InspectionRoom.userInfo> userInfos = new ArrayList<>();



    private String isLike="0";
    private String notice="";


    private String id="";
    private String goodsTitle="";
    private String lookNum="";
    private String goodsPic="";
    private String afterCouponPrice="";
    private String yongjin="";
    private String couponPrice="";
    private String shopPirce="";

    @BindView(R.id.goods_title)
    TextView goods_title;
    @BindView(R.id.title02)
    TextView title02;
    @BindView(R.id.adapter_new_one_fragment_price)
    TextView adapter_new_one_fragment_price;
    @BindView(R.id.commodity_save_money)
    TextView commodity_save_money;
    @BindView(R.id.adapter_new_one_fragment_discount)
    TextView adapter_new_one_fragment_discount;
    @BindView(R.id.adapter_new_one_fragment_original_price)
    TextView adapter_new_one_fragment_original_price;
    @BindView(R.id.tips_btn_text)
    ScrollTextView tips_btn_text;

    @BindView(R.id.free_layout)
    LinearLayout free_layout;
    @BindView(R.id.free_tios)
    TextView free_tios;

    @BindView(R.id.shop_goods_layout)
    LinearLayout shop_goods_layout;

    @BindView(R.id.commodity_save_money_layout)
    LinearLayout commodity_save_money_layout;

    @BindView(R.id.activity_inspection_room_scroll)
    ScrollView activity_inspection_room_scroll;


    private Animation myAnim;

    @BindView(R.id.msgQuenView)
    MessagQuenView msgweQueeView;
    @BindView(R.id.thumbContainer)
    RelativeLayout thumbContainer;
    private List<ImageView> lsImgGoods = new ArrayList<ImageView>();
    private boolean ishide=false;

    private Thread myThread;

    private boolean isStop=true;

    //赞执行线程
    Timer goodtimer;
    NewTimerTask goodtimerTask ;
    private int goodinterval=400;
    //XXX去买 正在去买
    Timer jointimer;
    joinRoomTimerTask jointimerTask;
    private int joininterval=1500;

    //聊天间隔时间
    private int detailinterval=3000;

    // 支付宝跳转
    AlipayDialog dialog;

    //下载的数量 图片和视频
    private int load_num=0;//计数
    private int total_load_num=0;//总数
    List<InspectionRoom.Detail> sucaidetails=new ArrayList<>();

    //同一个商品页面 点击“我知道了” 不再显示弹框
    private Boolean isshowDialog=false;

    private Boolean isToast=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_room);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        //控制推荐接受广播不弹授权框
        Variable.AuthShowStatus=true;
        id = getIntent().getStringExtra("id");
        goodsTitle = getIntent().getStringExtra("goodsTitle");
        lookNum = getIntent().getStringExtra("lookNum");
        goodsPic = getIntent().getStringExtra("goodsPic");
        afterCouponPrice = getIntent().getStringExtra("afterCouponPrice");
        yongjin = getIntent().getStringExtra("yongjin");
        couponPrice = getIntent().getStringExtra("couponPrice");
        shopPirce = getIntent().getStringExtra("shopPirce");

        goods_title.setText(goodsTitle);
        title02.setText(lookNum+"人观看");
        adapter_new_one_fragment_price.setText(afterCouponPrice);
        commodity_save_money.setText("¥"+yongjin);
        adapter_new_one_fragment_discount.setText(couponPrice);
        adapter_new_one_fragment_original_price.setText("¥"+shopPirce);
        adapter_new_one_fragment_original_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        Utils.displayImageRounded(this,goodsPic,goods_pic,5);
        RecyclerView.LayoutManager layoutManager=MyLayoutManager.getInstance().LayoutManager(this, false);
        activity_inspection_room.setLayoutManager(layoutManager);
        activity_inspection_room.setNestedScrollingEnabled(false);//禁止滑动
        adapter = new InspectRoomAdapter(this,width,height);
        activity_inspection_room.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemLongClickListener(this);
        dialog = new AlipayDialog(this);

        if (!DateStorage.getLoginStatus()){
            //没登录隐藏显示佣金
            commodity_save_money_layout.setVisibility(View.GONE);
        }

        //请求数据
        httpPost();
    }
    TaobaoAuthLoginDialog Tdialog;
    //登录刷新标志
    private  boolean loginRefreshFlag=false;
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
        if (message.what == LogicActions.RoomDetailDataSuccess) {
            Object object = message.obj;
            inspectionRoom = (InspectionRoom) object;
            if (loginRefreshFlag) {
                loginRefreshFlag=false;
                commodity_save_money_layout.setVisibility(View.VISIBLE);
                commodity_save_money.setText(inspectionRoom.getPrecommission());
            } else {
             details = inspectionRoom.getDetailList();
            if (details.size() > 0) {
                itemdetails = new ArrayList<>();
                itemdetails.add(details.get(0));
                adapter.setNewData(itemdetails);
                totalimage++;
                myThread = new MyThread();
                myThread.start();
            }
            //获取可下载素材
            sucaidetails = new ArrayList<>();
            for (int i = 0; i < details.size(); i++) {
                if (!details.get(i).getType().equals("0")) {
                    total_load_num++;
                    sucaidetails.add(details.get(i));
                }
            }
            if (sucaidetails.size() == 0) {
                sucai_layout.setVisibility(View.GONE);
            } else {
                sucai_layout.setVisibility(View.VISIBLE);
            }
            //免单数据
            frees = inspectionRoom.getFreeList();
            showFreeDialog();
            if (frees.size() > 0) {
                free_layout.setVisibility(View.VISIBLE);
                free_tios.setVisibility(View.GONE);
                switch (frees.size()) {
                    case 1:
                        room_user_layout_01.setVisibility(View.VISIBLE);
                        room_user_layout_02.setVisibility(View.GONE);
                        room_user_layout_03.setVisibility(View.GONE);
                        Utils.displayImageCircular(this, frees.get(0).getUserpicurl(), room_user_iv_01);
                        break;
                    case 2:
                        room_user_layout_01.setVisibility(View.VISIBLE);
                        room_user_layout_02.setVisibility(View.VISIBLE);
                        room_user_layout_03.setVisibility(View.GONE);
                        Utils.displayImageCircular(this, frees.get(0).getUserpicurl(), room_user_iv_01);
                        Utils.displayImageCircular(this, frees.get(1).getUserpicurl(), room_user_iv_02);
                        break;
                    case 3:
                        room_user_layout_01.setVisibility(View.VISIBLE);
                        room_user_layout_02.setVisibility(View.VISIBLE);
                        room_user_layout_03.setVisibility(View.VISIBLE);
                        Utils.displayImageCircular(this, frees.get(0).getUserpicurl(), room_user_iv_01);
                        Utils.displayImageCircular(this, frees.get(1).getUserpicurl(), room_user_iv_02);
                        Utils.displayImageCircular(this, frees.get(2).getUserpicurl(), room_user_iv_03);
                        break;
                }
            } else {
                free_layout.setVisibility(View.GONE);
                free_tios.setVisibility(View.VISIBLE);
            }
            //用户都买数据
            if (inspectionRoom.getUserList() != null && inspectionRoom.getUserList().size() > 0) {
                userInfos = inspectionRoom.getUserList();
                doJoinRoom(joininterval);
            }

            //是否喜欢 执行点赞动作
            isLike = inspectionRoom.getIslike();
            if (isLike.equals("1")) {
                like_image.setImageResource(R.mipmap.room_liked);
                dosendThumbs(goodinterval);
            } else {
                like_image.setImageResource(R.mipmap.room_like);
            }

            notice = inspectionRoom.getNotice();
            if (notice.equals("")) {
                tips_layout.setVisibility(View.GONE);
            } else {
                tips_layout.setVisibility(View.VISIBLE);
                tips_btn_text.setText("  " + notice);
                tips_btn_text.setSpeed(-2);
            }
        }
    }


        if (message.what == LogicActions.RoomLikeSuccess) {
           isLike="1";
           like_image.setImageResource(R.mipmap.room_liked);
           dosendThumbs(goodinterval);
        }

        if (message.what == LogicActions.GetCommissionSuccess) {
            ShareList shareList = (ShareList) message.obj;
            if (shareList.getTips().getKey().equals("")){
                DateStorage.setIsKey("");
                GetCommissionSuccess(shareList);
            }else {
                if (shareList.getTips().getKey().equals(DateStorage.getIsKey()) || isshowDialog) {
                    //已经点击了不再提醒
                    GetCommissionSuccess(shareList);
                } else {
                    try{
                        CouponLinkDialog couponLinkDialog = new CouponLinkDialog(LiveInspectionRoomActivity.this,shareList.getTips());
                        couponLinkDialog.showDialog();
                        couponLinkDialog.setOnClickListener(new CouponLinkDialog.OnBtnClickListener() {
                            @Override
                            public void onClick(int pos) {
                                if (pos == 0) {//不再提醒
                                    DateStorage.setIsKey(shareList.getTips().getKey());
                                }else{
                                    isshowDialog=true;
                                }
                                GetCommissionSuccess(shareList);
                            }
                        });
                    }catch(Exception e){

                    }

                }
            }
        }


        if (message.what == LogicActions.PurchaseSuccess) {
            dismissDialog();
            Alibc alibc = (Alibc) message.obj;
            String Couponlink=alibc.getCouponlink();
            String pid=alibc.getPid();
            if (alibc.getTips().getKey().equals("")){
                DateStorage.setIsKey("");
                dialog.showDialog();
                Utils.Alibc(this, Couponlink, pid, this);
                new Handler().postDelayed(dialog::hideDialog, 3500);
            }else {
                if (alibc.getTips().getKey().equals(DateStorage.getIsKey()) || isshowDialog) {
                    //已经点击了不再提醒
                    dialog.showDialog();
                    Utils.Alibc(this, Couponlink, pid, this);
                    new Handler().postDelayed(dialog::hideDialog, 3500);
                } else {
                    try{
                        CouponLinkDialog couponLinkDialog = new CouponLinkDialog(this,alibc.getTips());
                        couponLinkDialog.showDialog();
                        couponLinkDialog.setOnClickListener(new CouponLinkDialog.OnBtnClickListener() {
                            @Override
                            public void onClick(int pos) {
                                if (pos == 0) {//不再提醒
                                    DateStorage.setIsKey(alibc.getTips().getKey());
                                }else{
                                    isshowDialog=true;
                                }
                                dialog.showDialog();
                                Utils.Alibc(LiveInspectionRoomActivity.this, Couponlink, pid, LiveInspectionRoomActivity.this);
                                new Handler().postDelayed(dialog::hideDialog, 3500);
                            }
                        });
                    }catch(Exception e){

                    }

                }
            }
        }
        // 获取H5地址
        if (message.what == LogicActions.GetH5Success) {
            ArrayList<H5Link> h5Links = (ArrayList<H5Link>) message.obj;
            startActivity(new Intent(this, WebViewActivity.class).putExtra("isTitle" , true).putExtra(Variable.webUrl, h5Links.get(0).getUrl()));
            }
        dismissDialog();
    }

    @OnClick({R.id.room_back_iv,R.id.room_gift_layout,R.id.hide_layout,R.id.room_free_layout,R.id.room_buy_btn,R.id.room_share_btn,R.id.rule_layout,R.id.sucai_layout,R.id.shop_goods_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.room_back_iv:
                isFinish();
                break;
            case R.id.room_gift_layout:
                //点击送礼物
                if (DateStorage.getLoginStatus()) {
                if (isLike.equals("0")){
                 httpPostLike();
                }else{
                    sendThumbs();
                }
                } else {
                   Intent intent = new Intent(this, LoginActivity.class);
                   startActivity(intent);
                }
                break;
            case R.id.hide_layout:
                //隐藏
               if(!ishide){
                   ishide=true;
                   tips_layout.setVisibility(View.GONE);
                   msgweQueeView.setVisibility(View.GONE);
                   thumbContainer.setVisibility(View.GONE);
                   room_gift_layout.setVisibility(View.GONE);
                  stopTimer();
               }else{
                   ishide=false;
                   if (notice.equals("")){
                       tips_layout.setVisibility(View.GONE);
                   }else{
                       tips_layout.setVisibility(View.VISIBLE);
                   }
                   msgweQueeView.setVisibility(View.VISIBLE);
                   thumbContainer.setVisibility(View.VISIBLE);
                   if (sucaidetails.size()==0){
                       sucai_layout.setVisibility(View.GONE);
                   }else{
                       sucai_layout.setVisibility(View.VISIBLE);
                   }
                   room_gift_layout.setVisibility(View.VISIBLE);
                   if (userInfos!=null&&userInfos.size()>0) {
                       doJoinRoom(joininterval);
                   }
                   if (isLike.equals("1")){
                       dosendThumbs(goodinterval);
                   }
               }
                break;

            case R.id.room_free_layout:
                if (frees.size()>0){
                    showFreeListDialog();
                }else{
                    toast("暂无免单用户");
                }
             break;
            case R.id.room_share_btn:
                if (NetStateUtils.isNetworkConnected(this)) {
                    if (DateStorage.getLoginStatus()) {
                        // 获取高佣链接
                        paramMap.clear();
                        paramMap.put("userid", login.getUserid());
                        paramMap.put("shopid", inspectionRoom.getShopid());
                        paramMap.put("couponid", inspectionRoom.getCouponid());
                        paramMap.put("shopname", inspectionRoom.getShopname());
                        paramMap.put("shopmainpic", inspectionRoom.getShopmainpic());
                        paramMap.put("reqsource", "0");
                        NetworkRequest.getInstance().POST(handler, paramMap, "GetCommission", HttpCommon.GetCommission);
                        showDialog();
                    } else {
                      Intent  intent = new Intent(this, LoginActivity.class);
                      startActivity(intent);
                    }
                }else{
                    toast("当前网络已断开，请连接网络");
                }
                break;
            case R.id.room_buy_btn:
               goBuy();
                break;
            case R.id.shop_goods_layout:
                String id=inspectionRoom.getShopid();
                if(id.equals("")){
                  id=getIntent().getStringExtra("shopid");
                }
                startActivity(new Intent(this, CommodityActivity290.class).putExtra("shopId", id).putExtra("source", "all").putExtra("sourceId",""));
                break;
            case R.id.rule_layout:
                // 获取H5地址
                paramMap.clear();
                paramMap.put("type", "4");
                NetworkRequest.getInstance().POST(handler, paramMap, "GetH5", HttpCommon.GetH5);
                break;
            case R.id.sucai_layout:
              //素材下载
               isToast=true;
               showDownLoadDialog();
                break;
        }
    }


    private void httpPost() {
        showDialog();
        paramMap.clear();
        paramMap.put("id", id);
        NetworkRequest.getInstance().POST(handler, paramMap, "RoomDetailData", HttpCommon.RoomDetailData);
    }


    private void Animation() {
        // TODO Auto-generated method stub
        // TODO Auto-generated method stub
        AnimationSet animationSet = new AnimationSet(true);

        TranslateAnimation translateAnimation = new TranslateAnimation(
                // X轴的开始位置
                Animation.RELATIVE_TO_PARENT, 1f,
                // X轴的结束位置
                android.view.animation.Animation.RELATIVE_TO_PARENT, -1f,
                // Y轴的开始位置
                android.view.animation.Animation.RELATIVE_TO_PARENT, 0f,
                // Y轴的结束位置
                android.view.animation.Animation.RELATIVE_TO_PARENT, 0f);
        translateAnimation.setDuration(18000);
        translateAnimation.setRepeatCount(Animation.INFINITE);  //  设置动画无限循环
        translateAnimation.setRepeatMode(Animation.RESTART);    //重新从头执行
        animationSet.addAnimation(translateAnimation);
        tips_layout.setAnimation(animationSet);

    }

     private int countNum=0;
    private final Handler msgReceivHandle = new Handler() {

        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {//送礼物
                View addView = null;
                if (msg.arg1 == 1) {
                    setGoodsUiTouch(GoodsInitUtile.getGoodsType(msg.arg2));
                } else if (msg.arg1 == 2) {
                    countNum++;
                    switch (msg.arg2){
                        case 0:
                            addView = getLayoutInflater().inflate(R.layout.live_im_join_like_view, null);
                            break;
                        case 1:
                            addView = getLayoutInflater().inflate(R.layout.live_im_join_buy_view, null);
                            break;
                        case 2:
                            addView = getLayoutInflater().inflate(R.layout.live_im_join_hasbuy_view, null);
                            break;

                    }
                    String msgtxt = (String) msg.obj;
                    ((TextView) addView.findViewById(R.id.live_im_comes_tv)).setText(msgtxt);
                    if (addView != null) {
                        msgweQueeView.addNewView(addView,countNum);
                    }
                }

            }
        }

    };

    /**
     * 触发发送消息的事件
     */
    private void sendMessages() {
        Message msgMsg = msgReceivHandle.obtainMessage();
        msgMsg.what = 1;
        msgMsg.arg1 = 3;
        msgReceivHandle.sendMessage(msgMsg);
    }
    private void dosendThumbs(int interval){
        initParams();//初始化送礼物
        goodtimer= new Timer();
        goodtimerTask = new NewTimerTask();
        //程序运行后立刻执行任务，每隔100ms执行一次
        goodtimer.schedule(goodtimerTask, 0, interval);
    }


    private void doJoinRoom(int interval){
        jointimer= new Timer();
        jointimerTask = new joinRoomTimerTask();
        //程序运行后立刻执行任务，每隔100ms执行一次
        jointimer.schedule(jointimerTask, 0, interval);
    }

    int totalimage=0;
    private void doIMMessage(){
                int maxSize=details.size();
                if (totalimage<maxSize){
                    itemdetails=new ArrayList<>();
                    itemdetails.add(details.get(totalimage));
                    adapter.addData(itemdetails);
                    totalimage++;
                    activity_inspection_room_scroll.post(new Runnable() {
                        public void run() {
                            activity_inspection_room_scroll.fullScroll(View.FOCUS_DOWN);
                        }
                    });
                    }else{
                    //关闭线程
                   isStop=false;
                }
    }

    Handler ImHandler = new Handler() {
        @Override
          public void handleMessage(Message msg) {
                       if (msg.what == 1){
                         doIMMessage();
                            }
                            super.handleMessage(msg);
                    }
     };

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        String type= details.get(position).getType();
        Intent intent = null;
        switch (type){
            case "0"://文字
                break;
            case "1"://点击查看图片
                intent = new Intent(this, ImageActivity.class);
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(details.get(position).getImgurl());
                intent.putStringArrayListExtra("imageList", arrayList);
                intent.putExtra("position", 0);
                startActivity(intent);
                break;
            case "2"://点击看视频
                intent = new Intent(this, VideoActivity.class);
                intent.putExtra("videoUrl", details.get(position).getVideourl());
                intent.putExtra("title", details.get(position).getContent());
                startActivity(intent);
                break;

        }


    }


    @Override
    public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
        String type= details.get(position).getType();
        String content= details.get(position).getContent();
        switch (type){
            case "0"://文字
               showSaveShareDialog(0+"",content,"","");
                break;
            case "1"://图片
                showSaveShareDialog(1+"",content,details.get(position).getImgurl(),"");
                break;
            case "2"://视频
                showSaveShareDialog(2+"",content,details.get(position).getImgurl(),details.get(position).getVideourl());
                break;
        }
        return false;
    }

    @Override
    public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {

    }

    @Override
    public void onFailure(int i, String s) {

    }

    class MyThread extends Thread {//这里也可用Runnable接口实现
         @Override
        public void run() {
            while (isStop){
             try {
                 Thread.sleep(detailinterval);//每隔3s执行一次
                    Message msg = new Message();
                      msg.what = 1;
                     ImHandler.sendMessage(msg);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }

            }
         }
    }
    /**
     * TimerTask类是一个抽象类
     */
    public class NewTimerTask extends TimerTask {
        @Override
        public void run() {
            sendThumbs();
        }

    }
    /**
     * TimerTask类是一个抽象类
     */
    public class joinRoomTimerTask extends TimerTask {
        @Override
        public void run() {
            joinRoom();
        }

    }



    /**
     * 触发点赞的事件
     */
    private void sendThumbs() {
        Message msgMsg = msgReceivHandle.obtainMessage();
        msgMsg.what = 1;
        msgMsg.arg1 = 1;//点赞
        msgReceivHandle.sendMessage(msgMsg);
    }

    /**
     * 点赞的话，开始设置赞的效果
     */
    public void setGoodsUiTouch(int resource) {
         ArrayList<Integer> templist=GoodsInitUtile.getRandomImages();
         for (int i=0;i<templist.size();i++){
             ImageView goodImgView = getGoodImageView();
             if (goodImgView == null) {
                 return;
             }
             ((GoodHolder) goodImgView.getTag()).isAdd = true;
             ((GoodHolder) goodImgView.getTag()).time = System.currentTimeMillis();
             goodImgView.setImageResource(templist.get(i));
             thumbContainer.addView(goodImgView);
             goodImgView.setVisibility(View.INVISIBLE);
             Random rand = new Random();
             int animType=rand.nextInt(3);
             Animation anim = GoodAnimationUtile.createAnimation(this,animType);
             anim.setAnimationListener(new GoodMsgAnimaionList(goodImgView));
             goodImgView.startAnimation(anim);
         }

    }

    private ImageView getGoodImageView() {
        for (int i = 0; i < lsImgGoods.size(); i++) {
            ImageView imgtem = lsImgGoods.get(i);
            GoodHolder tag = (GoodHolder) imgtem.getTag();
            if (tag.isAdd == false) {
                return imgtem;
            }
        }
        return null;
    }



    private void initParams() {

        initGoodImageViews();

    }
    /*
     * 初始化赞的数量， 并设置赞的位置
     * 设置赞的出现位置 居中 还是靠右
     */
    private void initGoodImageViews() {
        for (int i = 0; i < 25; i++) {
            ImageView img = new ImageView(this);
            RelativeLayout.LayoutParams rlPram = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            rlPram.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            // 水平居中
            rlPram.addRule(RelativeLayout.CENTER_HORIZONTAL);
            img.setLayoutParams(rlPram);
            img.setImageResource(R.mipmap.live_like_1);
            GoodHolder holderTag = new GoodHolder();
            holderTag.time = System.currentTimeMillis();
            img.setTag(holderTag);
            img.setScaleX(0.1f);
            img.setScaleY(0.1f);
            lsImgGoods.add(img);

        }
    }
    /**
     * 加入聊天室
     */
    private void joinRoom() {
            Message msgMsg = msgReceivHandle.obtainMessage();
            msgMsg.what = 1;
            msgMsg.arg1 = 2;
            Random rand = new Random();
            msgMsg.arg2 = rand.nextInt(3);
            msgMsg.obj = userInfos.get(rand.nextInt(userInfos.size())).getName();
            msgReceivHandle.sendMessage(msgMsg);
    }
    class GoodHolder {
        public boolean isAdd = false;
        public long time;
    }
    class GoodMsgAnimaionList implements Animation.AnimationListener {
        private ImageView imgv;

        public GoodMsgAnimaionList(ImageView imgv) {
            this.imgv = imgv;
        }

        @Override
        public void onAnimationEnd(Animation arg0) {
            imgv.clearAnimation();
            imgv.setVisibility(View.INVISIBLE);

            Message msg = moveHandler.obtainMessage();
            msg.what = 1;
            msg.obj = ((GoodHolder) (imgv.getTag())).time;
            moveHandler.sendMessage(msg);

        }

        @Override
        public void onAnimationRepeat(Animation arg0) {
        }

        @Override
        public void onAnimationStart(Animation arg0) {
        }

    }

    /**
     * 回收点赞的view
     */
    private Handler moveHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            for (int i = 0; i < thumbContainer.getChildCount(); i++) {
                ImageView imgv = null;
                try {
                    imgv = (ImageView) thumbContainer.getChildAt(i);
                } catch (Exception e) {
                    imgv = null;
                }
                if (imgv == null) {
                    continue;
                }

                long time = ((GoodHolder) imgv.getTag()).time;
                long before = (Long) msg.obj;

                if (time == before) {
                    ((GoodHolder) imgv.getTag()).isAdd = false;
                    thumbContainer.removeView(imgv);
                }
            }
        }

    };

    //底部免单名单列表
    private void showFreeListDialog(){
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(this,R.style.DialogTheme);
        //2、设置布局
        View view = View.inflate(this,R.layout.dialog_room_free_list,null);
        dialog.setContentView(view);


        RecyclerView  activity_room_free_list=dialog.findViewById(R.id.activity_room_free_list);
        activity_room_free_list.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(this, false));
        RoomFreeListAdapter roomFreeListAdapter=new RoomFreeListAdapter(this);
        activity_room_free_list.setAdapter(roomFreeListAdapter);
        roomFreeListAdapter.setNewData(frees);

        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawableResource(R.color.dialogTransparent);

        dialog.show();
        dialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog

        dialog.findViewById(R.id.room_back_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.free_list_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }
    //进入弹出免单窗口
    private void showFreeDialog(){
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(this,R.style.DialogTheme11);
        //2、设置布局
        View view = View.inflate(this,R.layout.dialog_room_free,null);
        dialog.setContentView(view);
        TextView numText=dialog.findViewById(R.id.title5);
        numText.setText("累计"+frees.size()+"人获得免单");
        ImageView room_user_iv_01=dialog.findViewById(R.id.room_user_iv_01);
        ImageView room_user_iv_02=dialog.findViewById(R.id.room_user_iv_02);
        ImageView room_user_iv_03=dialog.findViewById(R.id.room_user_iv_03);
        RelativeLayout room_user_layout_01=dialog.findViewById(R.id.room_user_layout_01);
        RelativeLayout room_user_layout_02=dialog.findViewById(R.id.room_user_layout_02);
        RelativeLayout room_user_layout_03=dialog.findViewById(R.id.room_user_layout_03);
        RelativeLayout free_num_layout=dialog.findViewById(R.id.free_num_layout);
        TextView title23=dialog.findViewById(R.id.title23);
        if (frees.size()==0){
           free_num_layout.setVisibility(View.GONE);
           title23.setVisibility(View.VISIBLE);
        }else{
            title23.setVisibility(View.GONE);
            free_num_layout.setVisibility(View.VISIBLE);
            switch (frees.size()){
                case 1:
                    room_user_layout_01.setVisibility(View.VISIBLE);
                    room_user_layout_02.setVisibility(View.GONE);
                    room_user_layout_03.setVisibility(View.GONE);
                    Utils.displayImageCircular(this,frees.get(0).getUserpicurl(),room_user_iv_01);
                    break;
                case 2:
                    room_user_layout_01.setVisibility(View.VISIBLE);
                    room_user_layout_02.setVisibility(View.VISIBLE);
                    room_user_layout_03.setVisibility(View.GONE);
                    Utils.displayImageCircular(this,frees.get(0).getUserpicurl(),room_user_iv_01);
                    Utils.displayImageCircular(this,frees.get(1).getUserpicurl(),room_user_iv_02);
                    break;
                case 3:
                    room_user_layout_01.setVisibility(View.VISIBLE);
                    room_user_layout_02.setVisibility(View.VISIBLE);
                    room_user_layout_03.setVisibility(View.VISIBLE);
                    Utils.displayImageCircular(this,frees.get(0).getUserpicurl(),room_user_iv_01);
                    Utils.displayImageCircular(this,frees.get(1).getUserpicurl(),room_user_iv_02);
                    Utils.displayImageCircular(this,frees.get(2).getUserpicurl(),room_user_iv_03);
                    break;
            }

        }
        free_num_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                showFreeListDialog();
            }
        });
        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.CENTER);
        //设置弹出动画
        window.setWindowAnimations(R.style.super_search_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawableResource(R.color.dialogTransparent);

        dialog.show();
        dialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog

        dialog.findViewById(R.id.room_back_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        //我也要买
        dialog.findViewById(R.id.room_dialog_buy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              dialog.dismiss();
              goBuy();
            }
        });

    }
    //长按显示保存 分享 弹框
    private void showSaveShareDialog(String type,String content,String imageurl,String vediourl){
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(this,R.style.DialogTheme11);
        //2、设置布局
        View view = View.inflate(this,R.layout.dialog_room_save_share,null);
        dialog.setContentView(view);
        TextView shareTv=dialog.findViewById(R.id.share_tv);
        TextView saveTv=dialog.findViewById(R.id.save_tv);
        TextPaint tp = shareTv.getPaint();
        tp.setFakeBoldText(true);
        TextPaint tp1 = saveTv.getPaint();
        tp1.setFakeBoldText(true);
        switch (type){
            case "0"://文字
                shareTv.setVisibility(View.VISIBLE);
                saveTv.setVisibility(View.GONE);
                shareTv.setText("复制");
                break;
            case "1"://图片
                shareTv.setVisibility(View.VISIBLE);
                saveTv.setVisibility(View.VISIBLE);
                shareTv.setText("分享");
                saveTv.setText("保存图片");
                break;
            case "2"://视频
                shareTv.setVisibility(View.VISIBLE);
                saveTv.setVisibility(View.VISIBLE);
                shareTv.setText("分享");
                saveTv.setText("保存视频");
                break;
        }
        shareTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.checkPermision(LiveInspectionRoomActivity.this,1007,false)) {
                    return;
                }
                switch (type){
                    case "0"://文字
                        Utils.copyText(content);
                        toast("已复制到剪贴板");
                        dialog.dismiss();
                        break;
                    case "1"://分享图片
                        ShareParams params= new ShareParams();
                        ArrayList<String> images = new ArrayList<>();
                        images.add(imageurl);
                        params.setImage(images);
                        Share.getInstance(0).initialize(params,1);
                        dialog.dismiss();
                        break;
                    case "2"://分享视频
                        params = new ShareParams();
                        if (content.equals("")){
                            params.setTitle(Variable.shareTitle);
                            params.setContent(Variable.shareContent);
                        }else{
                            params.setTitle(content);
                            params.setContent(content);
                        }
                        params.setUrl(vediourl);
                        params.setThumbData(imageurl);
                        Share.getInstance(0).initialize(params,1);
                        dialog.dismiss();
                        break;
                }
            }
        });
        saveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (type){
                    case "1"://保存图片
                        Glide.with(LiveInspectionRoomActivity.this).asBitmap()
                                .load(imageurl).into(new SimpleTarget<Bitmap>()
                        {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    Utils.saveFile(Variable.imagesPath, resource, 100, true);
                            }
                        });
                        ToastUtil.showImageToast(LiveInspectionRoomActivity.this,"图片已保存至相册",R.mipmap.toast_img);
                        dialog.dismiss();
                        break;
                    case "2"://保存视频
                        Share.getInstance(0).savevideo(LiveInspectionRoomActivity.this,vediourl);
                        dialog.dismiss();
                        break;
                }
            }
        });
        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.CENTER);
        //设置弹出动画
        window.setWindowAnimations(R.style.search_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawableResource(R.color.dialogTransparent);

        dialog.show();
        dialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog

    }
    TextView title7;
     Dialog downdialog=null;
    //弹出下载素材窗口
    private void showDownLoadDialog(){
        //1、使用Dialog、设置style
        downdialog = new Dialog(this,R.style.DialogTheme11);
        //2、设置布局
        View view = View.inflate(this,R.layout.dialog_room_download,null);
        downdialog.setContentView(view);
        ImageView download_back_iv=downdialog.findViewById(R.id.download_back_iv);
        Button load_btn=downdialog.findViewById(R.id.room_download_btn);
        LinearLayout download_num_layout=downdialog.findViewById(R.id.download_num_layout);
        TextView title5=downdialog.findViewById(R.id.title5);
        TextView title6=downdialog.findViewById(R.id.title6);
        title7=downdialog.findViewById(R.id.title7);
        download_back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downdialog.dismiss();
            }
        });
        load_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load_num=0;
                title5.setVisibility(View.GONE);
                download_num_layout.setVisibility(View.VISIBLE);
                title6.setText("总数："+total_load_num);
                title7.setText("已下载："+load_num);
                load_btn.setEnabled(false);
                load_btn.setText("下载中...");
                for (int i=0;i<sucaidetails.size();i++) {
                    String type = sucaidetails.get(i).getType();
                    switch (type) {
                        case "1":
                            downLoadSucai(Integer.parseInt(type), sucaidetails.get(i).getImgurl());
                            break;
                        case "2":
                            downLoadSucai(Integer.parseInt(type), sucaidetails.get(i).getVideourl());
                            break;
                    }
                }
            }
        });

        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.CENTER);
        //设置弹出动画
        window.setWindowAnimations(R.style.search_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawableResource(R.color.dialogTransparent);

        downdialog.show();
        downdialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog
    }


    //去购买
    private void goBuy(){
        if (NetStateUtils.isNetworkConnected(this)) {
            if (DateStorage.getLoginStatus()) {
                paramMap.clear();
                paramMap.put("userid", login.getUserid());
                paramMap.put("shopid", inspectionRoom.getShopid());
                paramMap.put("couponid", inspectionRoom.getCouponid());
                paramMap.put("shopname", inspectionRoom.getShopname());
                paramMap.put("shopmainpic", inspectionRoom.getShopmainpic());
                paramMap.put("reqsource", "0");
                NetworkRequest.getInstance().POST(handler, paramMap, "Purchase", HttpCommon.Purchase);
                showDialog();
            } else {
                Intent  intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
        }else{
            toast("当前网络已断开，请连接网络");
        }
    }

    private void httpPostLike() {
        paramMap.clear();
        paramMap.put("userid", login.getUserid());
        paramMap.put("id", id);
        NetworkRequest.getInstance().POST(handler, paramMap, "RoomLike", HttpCommon.RoomLike);
    }

    //停止计时执行
    private void stopTimer(){
        if (goodtimer!=null){
            goodtimer.cancel();
            goodtimer=null;
        }
        if (jointimer!=null){
            jointimer.cancel();
            jointimer=null;
        }
        if (goodtimerTask!=null){
            goodtimerTask.cancel();
            goodtimerTask=null;
        }
        if (jointimerTask!=null){
            jointimerTask.cancel();
            jointimerTask=null;
        }
    }

    private void stopJoinRoomTimer(){
        if (jointimer!=null){
            jointimer.cancel();
            jointimer=null;
        }
        if (jointimerTask!=null){
            jointimerTask.cancel();
            jointimerTask=null;
        }
    }

    private void startJoinRoomTimer(){
        if (jointimer==null){
            if (userInfos!=null&&userInfos.size()>0) {
                doJoinRoom(joininterval);
            }
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
      stopJoinRoomTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (loginRefreshFlag){
            //请求数据
            httpPost();
        }
        startJoinRoomTimer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //关闭线程
        if (myThread!=null){
        myThread.interrupt();
        myThread=null;
        }
        isStop=false;
       stopTimer();
    }


    /**
     * 素材下载
     */
    private void downLoadSucai(int type,String loadUrl) {
        Message Msg = DownLoadHandler.obtainMessage();
        Msg.what = type;
        Msg.obj=loadUrl;
        DownLoadHandler.sendMessage(Msg);
    }

    Handler DownLoadHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1://图片
                    Glide.with(LiveInspectionRoomActivity.this).asBitmap()
                            .load(msg.obj.toString()).into(new SimpleTarget<Bitmap>()
                    {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            Utils.saveFile(Variable.imagesPath, resource, 100, true);
                            load_num++;
                            title7.setText("已下载："+load_num);
                            if (load_num==total_load_num&&isToast){
                                isToast=false;
                                ToastUtil.showImageToast(LiveInspectionRoomActivity.this,"素材下载成功",R.mipmap.toast_img);
                                //关闭弹窗
                                if (downdialog!=null){
                                    downdialog.dismiss();
                                }
                            }
                        }
                    });
                    break;
                case 2://视频
                   savevideo(msg.obj.toString());
                    break;
            }


        }
    };

    private void savevideo(String path) {
        int start = path.lastIndexOf("/");
        int end = path.lastIndexOf(".");
        String name = path.substring(start + 1, end);
        String Suffix = path.substring(end);
        FileDownloader.getImpl().create(path).setPath(Variable.videoPath + "/" + name + Suffix).setListener(new FileDownloadListener() {
            @Override
            protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

            }

            @Override
            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {

            }

            @Override
            protected void completed(BaseDownloadTask task) {
                AlbumNotifyHelper.insertVideoToMediaStore(LiveInspectionRoomActivity.this, task.getPath(), 0, 2000);
                //scanFile不可少，才能插入视频数据库
                AlbumNotifyHelper.scanFile(LiveInspectionRoomActivity.this,task.getPath());
                load_num++;
                title7.setText("已下载："+load_num);
                if (load_num==total_load_num&&isToast){
                    //关闭弹窗
                    isToast=false;
                    ToastUtil.showImageToast(LiveInspectionRoomActivity.this,"素材下载成功",R.mipmap.toast_img);
                    if (downdialog!=null){
                        downdialog.dismiss();
                    }
                }
            }

            @Override
            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

            }

            @Override
            protected void error(BaseDownloadTask task, Throwable e) {

            }

            @Override
            protected void warn(BaseDownloadTask task) {

            }

        }).start();
    }

    //获取高佣之后的执行动作
    private void GetCommissionSuccess(ShareList shareList){
        ArrayList<ShareCheck> list = new ArrayList<>();
        for (InspectionRoom.imageUrl item : inspectionRoom.getShoppiclist()) {
            ShareCheck share = new ShareCheck();
            share.setImage(item.getShoppicurl());
            share.setCheck(0);
            list.add(share);
        }
        list.get(0).setCheck(1);
        startActivity(new Intent(this, ShareActivity330.class)
                .putExtra("image", list)
                .putExtra("name", inspectionRoom.getShopname())
                .putExtra("sales", inspectionRoom.getShopmonthlysales())
                .putExtra("money", inspectionRoom.getMoney())
                .putExtra("shopprice", inspectionRoom.getShopprice())
                .putExtra("discount", inspectionRoom.getDiscount())
                .putExtra("shortLink",shareList.getShareshortlink())
                .putExtra("recommend", inspectionRoom.getRecommended())
                .putExtra("countersign", shareList.getTpwd())//淘口令
                .putExtra("isCheck", inspectionRoom.isCheck())
                .putExtra("commission", inspectionRoom.getPrecommission())
                );


    }
}
