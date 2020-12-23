package com.lxkj.dmhw.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.activity.CommodityActivity290;
import com.lxkj.dmhw.activity.CommodityActivityPJW;
import com.lxkj.dmhw.activity.LoginActivity;
import com.lxkj.dmhw.activity.MorePlSearchListActivity;
import com.lxkj.dmhw.activity.MorePlatformNewActivity340;
import com.lxkj.dmhw.adapter.MorePlAdapter;
import com.lxkj.dmhw.adapter.MorePlAdapterSearchStyle;
import com.lxkj.dmhw.adapter.ShopListRecyclerAdapterNew;
import com.lxkj.dmhw.adapter.ShopListRecyclerAdapterSearchStyle;
import com.lxkj.dmhw.bean.CommodityList;
import com.lxkj.dmhw.bean.JDGoodsBean;
import com.lxkj.dmhw.bean.LoginToken;
import com.lxkj.dmhw.bean.PJWLink;
import com.lxkj.dmhw.bean.ShareParams;
import com.lxkj.dmhw.bean.UserInfo;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.defined.LazyFragment;
import com.lxkj.dmhw.defined.PopupWindows;
import com.lxkj.dmhw.dialog.ShareLittleAppDialog;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.MyLayoutManager;
import com.lxkj.dmhw.utils.Share;
import com.lxkj.dmhw.utils.ToastUtil;
import com.lxkj.dmhw.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 搜索结果列表
 */

public class SearchListFragment extends LazyFragment implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.search_list_check)
    CheckBox searchListCheck;
    @BindView(R.id.search_recycler)
    RecyclerView searchRecycler;
    @BindView(R.id.search_list_screen_layout)
    LinearLayout searchListScreenLayout;
    @BindView(R.id.fragment_search_list_one)
    LinearLayout fragmentSearchListOne;
    @BindView(R.id.fragment_search_list_two_image)
    ImageView fragmentSearchListTwoImage;
    @BindView(R.id.fragment_search_list_two)
    LinearLayout fragmentSearchListTwo;
    @BindView(R.id.fragment_search_list_three_image)
    ImageView fragmentSearchListThreeImage;
    @BindView(R.id.fragment_search_list_three)
    LinearLayout fragmentSearchListThree;
    @BindView(R.id.fragment_search_list_four_image)
    ImageView fragmentSearchListFourImage;
    @BindView(R.id.fragment_search_list_four)
    LinearLayout fragmentSearchListFour;
    @BindView(R.id.fragment_search_list_one_text)
    TextView fragmentSearchListOneText;
    @BindView(R.id.fragment_search_list_two_text)
    TextView fragmentSearchListTwoText;
    @BindView(R.id.fragment_search_list_three_text)
    TextView fragmentSearchListThreeText;
    @BindView(R.id.fragment_search_list_four_text)
    TextView fragmentSearchListFourText;
    @BindView(R.id.fragment_search_list_zh)
    ImageView fragment_search_list_zh;
    @BindView(R.id.fragment_search_list_jingxuan)
    LinearLayout fragment_search_list_jingxuan;
    @BindView(R.id.fragment_search_list_jingxuan_text)
    TextView fragment_search_list_jingxuan_text;
    @BindView(R.id.shaixuan_layout)
    LinearLayout shaixuan_layout;
    @BindView(R.id.coupon_layout)
    LinearLayout coupon_layout;
    @BindView(R.id.wph_split)
    View wph_split;

    private String sort = "20";
    private int positionTwo = 3;
    private int positionThree = 3;
    private int positionFour = 3;
    // 天猫or淘宝
    private int
            screen = 0,
            searchScreen = 0;
    // 价格区间
    private String range = "";
    // 搜索内容
    private String content;
    // 列表数据
    private CommodityList commodityList;
    // 适配器
    private ShopListRecyclerAdapterSearchStyle adapter;

    //京东 拼多多 唯品会公用适配器
    private MorePlAdapterSearchStyle morePlAdapter;
    ArrayList<JDGoodsBean> jdGoodsBeanArrayList;


    //-------------多平台需要的筛选参数---------------
    private String hasCoupon="0";
    private String priceStart="";
    private String priceEnd="";
    private String sortType="0";

    private int pageJD=1;
    private int pagePDD=1;
    private int pageWPH=1;
    private int pageSN=1;


    @BindView(R.id.network_list_switch)
    Switch network_list_switch;
    private int PlatType;
    private String currentShareUrl="";
    private String currentShareTitle="";
    private String currentShareType="";
    private String shareOrSave="share";//默认分享朋友圈
    private boolean isCheck = true;
    private JDGoodsBean jdGoodsBean;
    private String path;
    private boolean hasData=false;//站内有数据
    private boolean isDoNetwork=false;//开始加载全网搜

    private boolean taoSearch=false;//搜索淘宝商品 筛选时只搜全网

    //登录刷新标志
    private  boolean loginRefreshFlag=false;
    //当前选项卡的索引
    public static int currPos=0;

    public static SearchListFragment getInstance(int pos,String searcon) {
        SearchListFragment fragment = new SearchListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("platType",pos);
        bundle.putString("searcon",searcon);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            PlatType = bundle.getInt("platType");
            content=bundle.getString("searcon");
        }
    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_list, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {
        taoSearch=false;
        // 设置搜索标题内容
        if (PlatType==0){
            //淘宝
            shaixuan_layout.setVisibility(View.VISIBLE);
            fragment_search_list_jingxuan.setVisibility(View.GONE);
            fragmentSearchListThree.setVisibility(View.GONE);
            // 初始化列表数据
            commodityList = new CommodityList();
            // 设置Recycler
            searchRecycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), false));
            adapter = new ShopListRecyclerAdapterSearchStyle(getActivity());
            searchRecycler.setAdapter(adapter);
            // 设置预加载位置,倒数
            adapter.setPreLoadNumber(5);
            // 监听item点击事件
            adapter.setOnItemClickListener(this);
            // 监听加载更多事件
            adapter.setOnLoadMoreListener(this, searchRecycler);
            // 取消第一次加载回调
            adapter.disableLoadMoreIfNotFullPage();
        }else {
            if (PlatType == 1||PlatType == 2||PlatType==5) {
                shaixuan_layout.setVisibility(View.VISIBLE);
                fragment_search_list_jingxuan.setVisibility(View.GONE);
                fragmentSearchListThree.setVisibility(View.GONE);
              }else { //唯品会
                fragmentSearchListOne.setVisibility(View.GONE);
                fragmentSearchListTwo.setVisibility(View.GONE);
                shaixuan_layout.setVisibility(View.GONE);
                fragment_search_list_jingxuan.setVisibility(View.VISIBLE);
                fragmentSearchListThree.setVisibility(View.VISIBLE);
                coupon_layout.setVisibility(View.GONE);
                wph_split.setVisibility(View.VISIBLE);

            }

            if (PlatType==5){
                fragmentSearchListTwoImage.setVisibility(View.GONE);
            }

            // 初始化列表数据
            jdGoodsBeanArrayList = new ArrayList<JDGoodsBean>();
            // 设置Recycler
            searchRecycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), false));
            morePlAdapter = new MorePlAdapterSearchStyle(getActivity(),PlatType,true);
            searchRecycler.setAdapter(morePlAdapter);
            // 设置预加载位置,倒数
            morePlAdapter.setPreLoadNumber(5);
            // 监听item点击事件
            morePlAdapter.setOnItemClickListener(this);
            // 监听加载更多事件
            morePlAdapter.setOnLoadMoreListener(this, searchRecycler);
            // 取消第一次加载回调
            morePlAdapter.disableLoadMoreIfNotFullPage();
            morePlAdapter.setOnItemClickListener(new MorePlAdapterSearchStyle.OnItemClickListener() {
                @Override
                public void onItemClick(JDGoodsBean data,int type) {
                    if (!DateStorage.getLoginStatus()){
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        return;
                    }
                    switch (type){
                        case 1:
                            //分享
                            ShareLittleAppDialog shareLittleAppDialog=new ShareLittleAppDialog(getActivity());
                            shareLittleAppDialog.setShareClickListener(new ShareLittleAppDialog.OnShareClickListener() {
                                @Override
                                public void onShareClick(String type) {
                                    currentShareUrl = data.getImageUrl();
                                    currentShareTitle = data.getName();
                                    currentShareType = type;
                                    getGenByGoodsId(data);
                                }
                            });
                            shareLittleAppDialog.getDialog().show();
                            break;
                        case 2:
                            switch (PlatType){
                                case 1:
                                    startActivity(new Intent(getActivity(), CommodityActivityPJW.class).putExtra("GoodsId", data.getId()).putExtra("type", "pdd"));
                                    break;
                                case 2:
                                    startActivity(new Intent(getActivity(), CommodityActivityPJW.class).putExtra("GoodsId", data.getId()).putExtra("type", "jd"));
                                    break;
                                case 3:
                                    startActivity(new Intent(getActivity(), CommodityActivityPJW.class).putExtra("GoodsId", data.getId()).putExtra("type", "wph"));
                                    break;
                                case 5:
                                    startActivity(new Intent(getActivity(), CommodityActivityPJW.class).putExtra("GoodsId", data.getId()).putExtra("type", "sn"));
                                    break;
                            }
                            break;
                    }

                }
            });
        }

    }

    private void setPageToOne(){
        switch (PlatType){
            case 0:
                page=1;
                pageNetwork=1;
                break;
            case 1:
                pagePDD=1;
                break;
            case 2:
                pageJD=1;
                break;
            case 3:
                pageWPH=1;
                break;
            case 5:
                pageSN=1;
                break;


        }
    }
    @Override
    public void onCustomized() {
        // 设置图片大小
        checkBitmap(false);
    }
    @Override
    protected void initData() {
        // 网络请求
        httpPost("");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (loginRefreshFlag&&PlatType== MorePlSearchListActivity.currentType) {
            loginRefreshFlag = false;
            if (DateStorage.getLoginStatus()) {
                //获取token
                if (DateStorage.getMyToken().equals("") || DateStorage.getMicroAppId().equals("") || DateStorage.getLittleAppId().equals("")) {
                    paramMap.clear();
                    paramMap.put("userid", login.getUserid());
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "LoginToken", HttpCommon.LoginToken);
                }else{
                 page=1;
                 pageNetwork=1;
                 pageJD=1;
                 pagePDD=1;
                 pageSN=1;
                 pageWPH=1;
                 initData();
                }
            }
        }
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
        dismissDialog();
        if (message.what == LogicActions.ShopNameSuccess) {
            commodityList = (CommodityList) message.obj;
            int listSize=commodityList.getShopdata().size();
            //以加载数据为空 作为加载结束标志
            if (listSize> 0) {
                if (page > 1) {
                    adapter.addData(commodityList.getShopdata());
                } else {
                    adapter.setNewData(commodityList.getShopdata());
                }
                adapter.loadMoreComplete();
            }else{
                if (page==1){
                    //站内搜不到 跳转全网搜
                    hasData=false;
                    isDoNetwork=true;
                    httpPostNetwork();
                }else{
                    //站内加载完毕 加入全网搜
                    adapter.loadMoreComplete();
                    hasData=true;
                    isDoNetwork=true;
                    pageNetwork=0;
                }
            }

            //不使用判断小于10条为加载结束标志。
//            if (listSize> 0) {
//                if (listSize<10){//小于10条 说明站内商品搜索完成
//                    if (page > 1) {
//                        adapter.addData(commodityList.getShopdata());
//                    } else {
//                        adapter.setNewData(commodityList.getShopdata());
//                    }
//                 //站内加载完毕 加入全网搜
//                    adapter.loadMoreComplete();
//                    hasData=true;
//                    isDoNetwork=true;
//                    pageNetwork=0;
//                }else{
//                    if (page > 1) {
//                        adapter.addData(commodityList.getShopdata());
//                    } else {
//                        adapter.setNewData(commodityList.getShopdata());
//                    }
//                    adapter.loadMoreComplete();
//                }
//            } else {
//                    //站内搜不到 跳转全网搜
//                    hasData=false;
//                    isDoNetwork=true;
//                    httpPostNetwork();
//            }
        }

        //填充全网搜
        if (message.what == LogicActions.SearchAllChangeCommoritySuccess) {
            dismissDialog();
            ArrayList<CommodityList.CommodityData> commodityDataArrayList= (ArrayList<CommodityList.CommodityData>)message.obj;
            if (commodityDataArrayList!=null&&commodityDataArrayList.size() > 0) {
                if (pageNetwork > 1) {
                    adapter.addData(commodityDataArrayList);
                }else {
                    if (taoSearch){
                        commodityDataArrayList.get(0).setIsFirstNetwork(0);
                        adapter.setNewData(commodityDataArrayList);
                    }else {
                        commodityDataArrayList.get(0).setIsFirstNetwork(1);
                        if (hasData){
                            adapter.addData(commodityDataArrayList);
                        }else{
                            adapter.setNewData(commodityDataArrayList);
                        }
                    }
                }
                adapter.loadMoreComplete();
            }else{
                if (pageNetwork==1&&!hasData&&!taoSearch){
                    adapter.setNewData(commodityDataArrayList);
                }
                adapter.loadMoreEnd();
            }



//            if (commodityDataArrayList!=null&&commodityDataArrayList.size() > 0) {
//                if (commodityDataArrayList.size() < pageSize) {
//                    if (pageNetwork > 1) {
//                     adapter.addData(commodityDataArrayList);
//                    }else {
//                        if (taoSearch){
//                            commodityDataArrayList.get(0).setIsFirstNetwork(0);
//                            adapter.setNewData(commodityDataArrayList);
//                        }else {
//                            commodityDataArrayList.get(0).setIsFirstNetwork(1);
//                            if (hasData){
//                                adapter.addData(commodityDataArrayList);
//                            }else{
//                                adapter.setNewData(commodityDataArrayList);
//                            }
//                        }
//                    }
//                    adapter.loadMoreEnd();
//                } else {
//                    if (pageNetwork > 1) {
//                            adapter.addData(commodityDataArrayList);
//                    } else {
//                        if (taoSearch){
//                            commodityDataArrayList.get(0).setIsFirstNetwork(0);
//                            adapter.setNewData(commodityDataArrayList);
//                        }else {
//                            commodityDataArrayList.get(0).setIsFirstNetwork(1);
//                            if (hasData){
//                                adapter.addData(commodityDataArrayList);
//                            }else{
//                                adapter.setNewData(commodityDataArrayList);
//                            }
//                        }
//                    }
//                    adapter.loadMoreComplete();
//                }
//            } else {
//                if (pageNetwork==1&&!hasData&&!taoSearch){
//                    adapter.setNewData(commodityDataArrayList);
//                }
//                adapter.loadMoreEnd();
//            }
            // 设置空布局
            adapter.setEmptyView(R.layout.default_empty);
        }

        if (message.what == LogicActions.JDSearchGoodsSuccess) {
            jdGoodsBeanArrayList = (ArrayList<JDGoodsBean>) message.obj;
            if (jdGoodsBeanArrayList.size() > 0) {
               switch (PlatType){
                   case 1:
                       if (pagePDD > 1) {
                           morePlAdapter.addData(jdGoodsBeanArrayList);
                       } else {
                           morePlAdapter.setNewData(jdGoodsBeanArrayList);
                       }
                       morePlAdapter.loadMoreComplete();

//                       if (jdGoodsBeanArrayList.size()<pageSize){
//                           if (pagePDD > 1) {
//                               morePlAdapter.addData(jdGoodsBeanArrayList);
//                           } else {
//                               morePlAdapter.setNewData(jdGoodsBeanArrayList);
//                           }
//                           morePlAdapter.loadMoreEnd();
//                       }else{
//                           if (pagePDD > 1) {
//                               morePlAdapter.addData(jdGoodsBeanArrayList);
//                           } else {
//                               morePlAdapter.setNewData(jdGoodsBeanArrayList);
//                           }
//                           morePlAdapter.loadMoreComplete();
//                       }
                       break;
                   case 2:
                       if (pageJD > 1) {
                           morePlAdapter.addData(jdGoodsBeanArrayList);
                       } else {
                           morePlAdapter.setNewData(jdGoodsBeanArrayList);
                       }
                       morePlAdapter.loadMoreComplete();
//                       if (jdGoodsBeanArrayList.size()<pageSize){
//                           if (pageJD > 1) {
//                               morePlAdapter.addData(jdGoodsBeanArrayList);
//                           } else {
//                               morePlAdapter.setNewData(jdGoodsBeanArrayList);
//                           }
//                           morePlAdapter.loadMoreEnd();
//                       }else{
//                           if (pageJD > 1) {
//                               morePlAdapter.addData(jdGoodsBeanArrayList);
//                           } else {
//                               morePlAdapter.setNewData(jdGoodsBeanArrayList);
//                           }
//                           morePlAdapter.loadMoreComplete();
//                       }
                       break;
                   case 3:
                       if (pageWPH > 1) {
                           morePlAdapter.addData(jdGoodsBeanArrayList);
                       } else {
                           morePlAdapter.setNewData(jdGoodsBeanArrayList);
                       }
                       morePlAdapter.loadMoreComplete();

//                       if (jdGoodsBeanArrayList.size()<pageSize){
//                           if (pageWPH > 1) {
//                               morePlAdapter.addData(jdGoodsBeanArrayList);
//                           } else {
//                               morePlAdapter.setNewData(jdGoodsBeanArrayList);
//                           }
//                           morePlAdapter.loadMoreEnd();
//                       }else{
//                           if (pageWPH > 1) {
//                               morePlAdapter.addData(jdGoodsBeanArrayList);
//                           } else {
//                               morePlAdapter.setNewData(jdGoodsBeanArrayList);
//                           }
//                           morePlAdapter.loadMoreComplete();
//                       }
                       break;
                   case 5:
                       if (pageSN > 1) {
                           morePlAdapter.addData(jdGoodsBeanArrayList);
                       } else {
                           morePlAdapter.setNewData(jdGoodsBeanArrayList);
                       }
                       morePlAdapter.loadMoreComplete();

//                       if (jdGoodsBeanArrayList.size()<pageSize){
//                           if (pageSN > 1) {
//                               morePlAdapter.addData(jdGoodsBeanArrayList);
//                           } else {
//                               morePlAdapter.setNewData(jdGoodsBeanArrayList);
//                           }
//                           morePlAdapter.loadMoreEnd();
//                       }else{
//                           if (pageSN > 1) {
//                               morePlAdapter.addData(jdGoodsBeanArrayList);
//                           } else {
//                               morePlAdapter.setNewData(jdGoodsBeanArrayList);
//                           }
//                           morePlAdapter.loadMoreComplete();
//                       }
                       break;
               }
            } else {
                if (PlatType==1&&pagePDD==1){
                    morePlAdapter.setNewData(jdGoodsBeanArrayList);
                }else if (PlatType==2&&pageJD==1){
                    morePlAdapter.setNewData(jdGoodsBeanArrayList);
                }else if(PlatType==3&&pageWPH==1){
                    morePlAdapter.setNewData(jdGoodsBeanArrayList);
                }else if(PlatType==5&&pageSN==1){
                    morePlAdapter.setNewData(jdGoodsBeanArrayList);
                }
                morePlAdapter.loadMoreEnd();
            }
            // 设置空布局
            morePlAdapter.setEmptyView(R.layout.default_empty);
        }

        //获取小程序码图成功
        if (message.what == LogicActions.LittleAppImageSuccess) {
            String str2=message.obj.toString().replace(" ", "");//去掉所用空格
            List<String> list= Arrays.asList(str2.split(","));
            Bitmap bitmap=Utils.stringtoBitmap(list.get(1));
            final Bitmap[] goodsbitmap = {null};
            final Bitmap[] userbitmap = {null};
            UserInfo login = DateStorage.getInformation();
            if (shareOrSave.equals("share")) {
                Glide.with(getActivity()).asBitmap().load(login.getUserpicurl()).into(new SimpleTarget<Bitmap>(){
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        userbitmap[0] =resource;
                        Glide.with(getActivity()).asBitmap().load(jdGoodsBean.getImageUrl()).into(new SimpleTarget<Bitmap>(){
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                goodsbitmap[0] =resource;
                                //调用绘制图片
                                Share share=new Share();
                                share.WeChatLittleApp(getActivity(),Utils.jointBitmapLittleApp(getActivity(), bitmap, goodsbitmap[0], userbitmap[0],jdGoodsBean,PlatType), false);//分享朋友圈
                            }
                        });
                    }
                });
            }else{//保存图片
                Glide.with(getActivity()).asBitmap().load(login.getUserpicurl()).into(new SimpleTarget<Bitmap>(){
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        userbitmap[0] =resource;
                        Glide.with(getActivity()).asBitmap().load(jdGoodsBean.getImageUrl()).into(new SimpleTarget<Bitmap>(){
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                goodsbitmap[0] =resource;
                                Utils.saveFile(Variable.imagesPath, Utils.jointBitmapLittleApp(getActivity(), bitmap, goodsbitmap[0], userbitmap[0], jdGoodsBean,PlatType), 100, true);
                                ToastUtil.showImageToast(getActivity(),"已保存至系统相册",R.mipmap.toast_img);
                            }
                        });
                    }
                });
            }
            dismissDialog();
        }

        //转链后分享动作
        if (message.what == LogicActions.GenByGoodsIdSuccess) {
            PJWLink link = (PJWLink) message.obj;
            ShareParams params = new ShareParams();
            params.setShareTag(1);
            params.setTitle(currentShareTitle);
            params.setContent("");
            params.setThumbData(currentShareUrl);
            params.setUrl(link.getShortUrl());
            switch (currentShareType){
                case "wechat"://微信
                    Share.getInstance(0).WeChat(params, true);
                    break;
                case "wechatFriends"://朋友圈
                    Share.getInstance(0).WeChat(params, false);
                    break;
                case "qq"://qq
                    Share.getInstance(0).QQ(params, true);
                    break;
                case "qqzone"://qq空间
                    Share.getInstance(0).QQ(params, false);
                    break;
            }
            dismissDialog();
        }
        //获取token 保存本地
        if (message.what == LogicActions.LoginTokenSuccess) {
            LoginToken loginToken= (LoginToken) message.obj;
            DateStorage.setMyToken(loginToken.getToken());
            DateStorage.setLittleAppId(loginToken.getMicroId());
            DateStorage.setMicroAppId(loginToken.getWechatMicroId());
            page=1;
            pageNetwork=1;
            pageJD=1;
            pagePDD=1;
            pageSN=1;
            pageWPH=1;
            initData();
        }
    }

    @OnClick({R.id.back,R.id.search_list_check, R.id.fragment_search_list_one,
            R.id.fragment_search_list_two, R.id.fragment_search_list_three, R.id.fragment_search_list_four,R.id.fragment_search_list_jingxuan,R.id.network_list_switch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                isFinish();
                break;
            case R.id.search_list_check:
                taoSearch=true;
                showScreen();
                break;
            case R.id.fragment_search_list_one:
                taoSearch=true;
                positionTwo = 3;
                positionThree = 3;
                positionFour = 3;
                fragmentSearchListTwoImage.setImageResource(R.mipmap.more_pl_normal);
                fragmentSearchListThreeImage.setImageResource(R.mipmap.more_pl_normal);
                fragmentSearchListFourImage.setImageResource(R.mipmap.more_pl_normal);
                fragment_search_list_zh.setImageResource(R.mipmap.more_pl_down);
                fragmentSearchListOneText.setTextColor(Color.parseColor("#000000"));
                fragmentSearchListTwoText.setTextColor(0xff666666);
                fragmentSearchListThreeText.setTextColor(0xff666666);
                fragmentSearchListFourText.setTextColor(0xff666666);
                showPopWindow();
                break;
            case R.id.fragment_search_list_two:
                taoSearch=true;
                positionThree = 3;
                positionFour = 3;
                fragmentSearchListThreeImage.setImageResource(R.mipmap.more_pl_normal);
                fragmentSearchListFourImage.setImageResource(R.mipmap.more_pl_normal);
                fragment_search_list_zh.setImageResource(R.mipmap.zh_select_uncheck);
                fragmentSearchListOneText.setTextColor(0xff666666);
                fragmentSearchListTwoText.setTextColor(Color.parseColor("#000000"));
                fragmentSearchListThreeText.setTextColor(0xff666666);
                fragmentSearchListFourText.setTextColor(0xff666666);
                switch (positionTwo) {
                    case 1:
                        fragmentSearchListTwoImage.setImageResource(R.mipmap.more_pl_twodown);
                        positionTwo = 2;
                        if (PlatType==0){
                            sort = "11";
                        }else{
                            sortType="04";
                        }
                        setPageToOne();
                        httpPost("");
                        break;
                    case 2:
                        fragmentSearchListTwoImage.setImageResource(R.mipmap.more_pl_twoup);
                        positionTwo = 1;
                        if (PlatType==0){
                            sort = "10";
                        }else{
                            sortType="03";
                        }
                        setPageToOne();
                        httpPost("");
                        break;
                    case 3:
                        fragmentSearchListTwoImage.setImageResource(R.mipmap.more_pl_twodown);
                        positionTwo = 2;
                        if (PlatType==0){
                            sort = "11";
                        }else{
                            sortType="04";
                        }
                        setPageToOne();
                        httpPost("");
                        break;
                }
                break;
            case R.id.fragment_search_list_three:
                positionTwo = 3;
                positionFour = 3;
                fragmentSearchListTwoImage.setImageResource(R.mipmap.more_pl_normal);
                fragmentSearchListFourImage.setImageResource(R.mipmap.more_pl_normal);
                fragment_search_list_zh.setImageResource(R.mipmap.zh_select_uncheck);
                fragment_search_list_jingxuan_text.setTextColor(0xff666666);
                fragmentSearchListOneText.setTextColor(0xff666666);
                fragmentSearchListTwoText.setTextColor(0xff666666);
                fragmentSearchListThreeText.setTextColor(Color.parseColor("#000000"));
                fragmentSearchListFourText.setTextColor(0xff666666);
                switch (positionThree) {
                    case 1:
                        fragmentSearchListThreeImage.setImageResource(R.mipmap.more_pl_twodown);
                        positionThree = 2;
                        sortType="08";
                        setPageToOne();
                        httpPost("");
                        break;
                    case 2:
                        fragmentSearchListThreeImage.setImageResource(R.mipmap.more_pl_twoup);
                        positionThree = 1;
                        sortType="07";
                        setPageToOne();
                        httpPost("");
                        break;
                    case 3:
                        fragmentSearchListThreeImage.setImageResource(R.mipmap.more_pl_twodown);
                        positionThree = 2;
                        sortType="08";
                        setPageToOne();
                        httpPost("");
                        break;
                }
                break;
            case R.id.fragment_search_list_four:
                taoSearch=true;
                positionTwo = 3;
                positionThree = 3;
                fragmentSearchListTwoImage.setImageResource(R.mipmap.more_pl_normal);
                fragmentSearchListThreeImage.setImageResource(R.mipmap.more_pl_normal);
                fragment_search_list_zh.setImageResource(R.mipmap.zh_select_uncheck);
                fragment_search_list_jingxuan_text.setTextColor(0xff666666);
                fragmentSearchListOneText.setTextColor(0xff666666);
                fragmentSearchListTwoText.setTextColor(0xff666666);
                fragmentSearchListThreeText.setTextColor(0xff666666);
                fragmentSearchListFourText.setTextColor(Color.parseColor("#000000"));
                switch (positionFour) {
                    case 1:
                        fragmentSearchListFourImage.setImageResource(R.mipmap.more_pl_twodown);
                        positionFour = 2;
                        if (PlatType==0){
                            sort = "01";
                        }else{
                            sortType="02";
                        }
                        setPageToOne();
                        httpPost("");
                        break;
                    case 2:
                        fragmentSearchListFourImage.setImageResource(R.mipmap.more_pl_twoup);
                        positionFour = 1;
                        if (PlatType==0){
                            sort = "00";
                        }else{
                            sortType="01";
                        }
                        setPageToOne();
                        httpPost("");
                        break;
                    case 3:
                        fragmentSearchListFourImage.setImageResource(R.mipmap.more_pl_twodown);
                        positionFour = 2;
                        sort = "01";
                        if (PlatType==0){
                            sort = "01";
                        }else{
                            sortType="02";
                        }
                        setPageToOne();
                        httpPost("");
                        break;
                }
                break;
            case R.id.fragment_search_list_jingxuan:
                fragmentSearchListFourImage.setImageResource(R.mipmap.more_pl_normal);
                fragmentSearchListThreeImage.setImageResource(R.mipmap.more_pl_normal);
                fragmentSearchListThreeText.setTextColor(0xff666666);
                fragmentSearchListFourText.setTextColor(0xff666666);
                fragment_search_list_jingxuan_text.setTextColor(Color.parseColor("#000000"));
                sortType="0";
                setPageToOne();
                httpPost("");
                break;
            case R.id.network_list_switch:
                taoSearch=true;
                setPageToOne();
                if (PlatType==0){
                    adapter.setNewData(null);
                }else{
                    morePlAdapter.setNewData(null);
                }
                httpPost("");
                break;
        }
    }

    /**
     * 网络请求
     *
     * @param time
     */
    private void httpPost(String time) {
        // 商品搜索
        if (PlatType==0) {
            if (page == 1) {
                showDialog();
            }
            if (taoSearch) {
                if (pageNetwork==1){
                    showDialog();
                }
                httpPostNetwork();
            } else {
            paramMap.clear();
            paramMap.put("userid", login.getUserid());
            paramMap.put("shopname", content);
            paramMap.put("startindex", page + "");
            paramMap.put("searchtime", time);
            if (network_list_switch!=null) {
                paramMap.put("hasCoupon", network_list_switch.isChecked() ? "1" : "");
            }
            paramMap.put("pagesize", pageSize + "");
            paramMap.put("sortdesc", sort);
            switch (searchScreen) {
                case 0:
                    paramMap.put("screendesc", "");
                    break;
                case 1:
                    paramMap.put("screendesc", "00");
                    break;
                case 2:
                    paramMap.put("screendesc", "01");
                    break;
            }
            paramMap.put("pricerange", range);
            NetworkRequest.getInstance().POST(handler, paramMap, "ShopName", HttpCommon.ShopName);
        }
        }else{
            paramMap.clear();
            paramMap.put("userid", login.getUserid());
            if (network_list_switch!=null) {
                paramMap.put("hasCoupon", network_list_switch.isChecked() ? "1" : "0");
            }
            paramMap.put("pageSize ", pageSize + "");
            paramMap.put("keyword", content);
            paramMap.put("priceStart", priceStart);
            paramMap.put("priceEnd", priceEnd);
            paramMap.put("sortType", sortType);
            if (PlatType==2){
                if(pageJD==1){
                    showDialog();
                }
                paramMap.put("page", pageJD + "");
                NetworkRequest.getInstance().GETNew(handler, paramMap, "JDSearchGoods", HttpCommon.JDSearchGoods);
            }else if(PlatType==1){
                if(pagePDD==1){
                    showDialog();
                }
                paramMap.put("needAuth", true+"");
                paramMap.put("page", pagePDD + "");
                NetworkRequest.getInstance().GETNew(handler, paramMap, "JDSearchGoods", HttpCommon.PDDSearchGoods);
            }else if(PlatType==3){
                if(pageWPH==1){
                    showDialog();
                }
                paramMap.put("page", pageWPH + "");
                NetworkRequest.getInstance().GETNew(handler, paramMap, "JDSearchGoods", HttpCommon.WPHSearchGoods);
            }else if(PlatType==5){
                if(pageSN==1){
                    showDialog();
                }
                paramMap.put("page", pageSN + "");
                NetworkRequest.getInstance().GETNew(handler, paramMap, "JDSearchGoods", HttpCommon.SNSearchGoods);
            }
        }
    }

    /**
     * 设置图片大小
     */
    private void checkBitmap(boolean isCheck) {
        Drawable drawable;
        Resources resources = getResources();
        if (isCheck) {
            drawable = resources.getDrawable(R.mipmap.more_pl_sx_check);
            drawable.setBounds(0, 0, Utils.dipToPixel(R.dimen.dp_11), Utils.dipToPixel(R.dimen.dp_11));
            searchListCheck.setTextColor(Color.parseColor("#000000"));
            searchListCheck.setCompoundDrawables(null, null, drawable, null);
        } else {
            drawable = resources.getDrawable(R.mipmap.more_pl_sx);
            drawable.setBounds(0, 0, Utils.dipToPixel(R.dimen.dp_11), Utils.dipToPixel(R.dimen.dp_11));
            searchListCheck.setTextColor(0xff666666);
            searchListCheck.setCompoundDrawables(null, null, drawable, null);
        }
    }

    /**
     * 显示筛选
     */
    private void showScreen() {
        final EditText
                popupScreenLow,
                popupScreenHigh;
        final CheckBox
                popupScreenTmall,
                popupScreenTaoBao;
        ImageView pop_check_taobao,pop_check_tmall;
        LinearLayout popupScreenYes,popupScreenReset,shoptype_layout;
        TextView shoptype;
        View view = View.inflate(getActivity(), R.layout.popup_screen, null);
        final PopupWindows window = new PopupWindows(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(0x60000000));
        window.setFocusable(true);
        window.setOutsideTouchable(false);
        window.setContentView(view);
        window.showAsDropDown(searchListScreenLayout);
        window.update();
        view.findViewById(R.id.popup_cancel).setOnClickListener(v -> window.dismiss());
        popupScreenLow = view.findViewById(R.id.popup_screen_low);
        popupScreenHigh =  view.findViewById(R.id.popup_screen_high);
        popupScreenTmall =  view.findViewById(R.id.popup_screen_tmall);
        popupScreenTaoBao =view.findViewById(R.id.popup_screen_taobao);
        pop_check_taobao =  view.findViewById(R.id.pop_check_taobao);
        pop_check_tmall = view.findViewById(R.id.pop_check_tmall);
        popupScreenYes = view.findViewById(R.id.popup_screen_yes);
        popupScreenReset= view.findViewById(R.id.popup_screen_reset);
        shoptype=view.findViewById(R.id.shoptype);
        shoptype_layout=view.findViewById(R.id.shoptype_layout);
        if(PlatType==0){
            shoptype.setVisibility(View.VISIBLE);
            shoptype_layout.setVisibility(View.VISIBLE);
        }else{
            shoptype.setVisibility(View.GONE);
            shoptype_layout.setVisibility(View.GONE);
        }
        if (range.equals("")) {
            popupScreenLow.setText("");
            popupScreenHigh.setText("");
        } else {
            popupScreenLow.setText(range.substring(0, range.indexOf("-")));
            String high = range.substring(range.indexOf("-") + 1);
            if (high.equals("20180118")) {
                popupScreenHigh.setText("");
            } else {
                popupScreenHigh.setText(high);
            }
        }
        switch (searchScreen) {
            case 0:
                popupScreenTmall.setChecked(false);
                popupScreenTaoBao.setChecked(false);
                pop_check_tmall.setVisibility(View.GONE);
                pop_check_taobao.setVisibility(View.GONE);
                break;
            case 1:
                popupScreenTmall.setChecked(false);
                popupScreenTaoBao.setChecked(true);
                pop_check_tmall.setVisibility(View.GONE);
                pop_check_taobao.setVisibility(View.VISIBLE);
                break;
            case 2:
                popupScreenTmall.setChecked(true);
                popupScreenTaoBao.setChecked(false);
                pop_check_tmall.setVisibility(View.VISIBLE);
                pop_check_taobao.setVisibility(View.GONE);
                break;
        }
        popupScreenTmall.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (screen == 1) {
                popupScreenTaoBao.setChecked(false);
            }
            if (isChecked) {
                screen = 2;
                pop_check_taobao.setVisibility(View.GONE);
                pop_check_tmall.setVisibility(View.VISIBLE);
            } else {
                screen = 0;
                pop_check_taobao.setVisibility(View.GONE);
                pop_check_tmall.setVisibility(View.GONE);
            }
        });
        popupScreenTaoBao.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (screen == 2) {
                popupScreenTmall.setChecked(false);
            }
            if (isChecked) {
                screen = 1;
                pop_check_taobao.setVisibility(View.VISIBLE);
                pop_check_tmall.setVisibility(View.GONE);
            } else {
                screen = 0;
                pop_check_taobao.setVisibility(View.GONE);
                pop_check_tmall.setVisibility(View.GONE);
            }
        });
        popupScreenYes.setOnClickListener(v -> {
            setPageToOne();
            window.dismiss();
            if (PlatType==0) {
                if (popupScreenLow.getText().toString().equals("") && popupScreenHigh.getText().toString().equals("") && !popupScreenTmall.isChecked() && !popupScreenTaoBao.isChecked()) {
                    checkBitmap(false);
                    if (searchScreen != 0 || !range.equals("")) {
                        searchScreen = 0;
                        range = "";
                        httpPost("");
                    }
                } else {
                    checkBitmap(true);
                    searchScreen = screen;
                    if (popupScreenLow.getText().toString().equals("") && popupScreenHigh.getText().toString().equals("")) {
                        range = "";
                    } else {
                        if (popupScreenLow.getText().toString().equals("")) {
                            range = "0-" + popupScreenHigh.getText().toString();
                        } else if (popupScreenHigh.getText().toString().equals("")) {
                            range = popupScreenLow.getText().toString() + "-999999";
                        } else {
                            if (Integer.parseInt(popupScreenLow.getText().toString()) > Integer.parseInt(popupScreenHigh.getText().toString())) {
                                range = popupScreenHigh.getText().toString() + "-" + popupScreenLow.getText().toString();
                            } else {
                                range = popupScreenLow.getText().toString() + "-" + popupScreenHigh.getText().toString();
                            }
                        }
                    }
                    httpPost("");
                }
            }else{
                if (!popupScreenLow.getText().toString().equals("") || !popupScreenHigh.getText().toString().equals("")){
                    checkBitmap(true);
                }else{
                    checkBitmap(false);
                }
                priceStart=popupScreenLow.getText().toString();
                priceEnd=popupScreenHigh.getText().toString();
                httpPost("");
            }

        });

        //重置
        popupScreenReset.setOnClickListener(v -> {
            checkBitmap(false);
            popupScreenTmall.setChecked(false);
            popupScreenTaoBao.setChecked(false);
            pop_check_taobao.setVisibility(View.GONE);
            pop_check_tmall.setVisibility(View.GONE);
            popupScreenLow.setText("");
            popupScreenHigh.setText("");
            setPageToOne();
            window.dismiss();
            if (popupScreenLow.getText().toString().equals("") && popupScreenHigh.getText().toString().equals("") && !popupScreenTmall.isChecked() && !popupScreenTaoBao.isChecked()) {
                checkBitmap(false);
                if (searchScreen != 0 || !range.equals("")) {
                    searchScreen = 0;
                    range = "";
                    httpPost("");
                }
            } else {
                checkBitmap(true);
                searchScreen = screen;
                if (popupScreenLow.getText().toString().equals("") && popupScreenHigh.getText().toString().equals("")) {
                    range = "";
                } else {
                    if (popupScreenLow.getText().toString().equals("")) {
                        range = "0-" + popupScreenHigh.getText().toString();
                    } else if (popupScreenHigh.getText().toString().equals("")) {
                        range = popupScreenLow.getText().toString() + "-999999";
                    } else {
                        if (Integer.parseInt(popupScreenLow.getText().toString()) > Integer.parseInt(popupScreenHigh.getText().toString())) {
                            range = popupScreenHigh.getText().toString() + "-" + popupScreenLow.getText().toString();
                        } else {
                            range = popupScreenLow.getText().toString() + "-" + popupScreenHigh.getText().toString();
                        }
                    }
                }
                httpPost("");
            }
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (PlatType == 0) {
            ArrayList<CommodityList.CommodityData> list = (ArrayList<CommodityList.CommodityData>) adapter.getData();
            if (list.get(position).getIsNetwork() == 0) {
                startActivity(new Intent(getActivity(), CommodityActivity290.class).putExtra("shopId", ((List<CommodityList.CommodityData>) adapter.getData()).get(position).getShopid()).putExtra("source", "dmj").putExtra("sourceId",""));
            }else{
                startActivity(new Intent(getActivity(), CommodityActivity290.class).putExtra("shopId", ((List<CommodityList.CommodityData>) adapter.getData()).get(position).getShopid()).putExtra("source", "tb").putExtra("sourceId",""));
            }
        }
    }

    private void setPageTojiajia(){
        switch (PlatType){
            case 0:
                page++;
                break;
            case 1:
                pagePDD++;
                break;
            case 2:
                pageJD++;
                break;
            case 3:
                pageWPH++;
                break;
            case 5:
                pageSN++;
                break;
        }
    }

    @Override
    public void onLoadMoreRequested() {
            setPageTojiajia();
            if (PlatType == 0) {
                if (taoSearch){
                    pageNetwork++;
                    httpPostNetwork();
                }else {
                    if (isDoNetwork) {
                        pageNetwork++;
                        httpPostNetwork();
                    } else {
                        httpPost(commodityList.getSearchtime());
                    }
                }
            } else {
                httpPost("");
            }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    private void showPopWindow(){
//准备PopupWindow的布局View
        View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.search_zh_list, null);
//初始化一个PopupWindow，width和height都是WRAP_CONTENT
        PopupWindow popupWindow = new PopupWindow(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//设置PopupWindow的视图内容
        popupWindow.setContentView(popupView);
//点击空白区域PopupWindow消失，这里必须先设置setBackgroundDrawable，否则点击无反应
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(true);
//设置PopupWindow动画
        popupWindow.setAnimationStyle(R.style.one_fragemnt_animStyle);
        TextView zh01=popupView.findViewById(R.id.zh_01);
        TextView zh02=popupView.findViewById(R.id.zh_02);
        TextView zh03=popupView.findViewById(R.id.zh_03);


        if(PlatType==0){
            zh03.setVisibility(View.GONE);
            if (sort.equals("20")) {
                zh01.setTextColor(0xFFEF2533);
                zh02.setTextColor(0xFF333333);
            }
           else if(sort.equals("30")){
                zh02.setTextColor(0xFFEF2533);
                zh01.setTextColor(0xFF333333);
            }
        }else{
            zh03.setVisibility(View.VISIBLE);
            if (sortType.equals("0")){
                zh01.setTextColor(0xFFEF2533);
                zh02.setTextColor(0xFF333333);
                zh03.setTextColor(0xFF333333);
            }else if(sortType.equals("10")){
                zh02.setTextColor(0xFFEF2533);
                zh01.setTextColor(0xFF333333);
                zh03.setTextColor(0xFF333333);
            }else if(sortType.equals("06")){
                zh03.setTextColor(0xFFEF2533);
                zh02.setTextColor(0xFF333333);
                zh01.setTextColor(0xFF333333);
            }
        }
        zh01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zh01.setTextColor(0xFFEF2533);
                zh02.setTextColor(0xFF333333);
                zh03.setTextColor(0xFF333333);
                if (PlatType==0){
                    sort = "20";
                    setPageToOne();
                    httpPost(commodityList.getSearchtime());
                }else{
                    sortType = "0";
                    setPageToOne();
                    httpPost("");
                }
                popupWindow.dismiss();
            }
        });
        zh02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zh02.setTextColor(0xFFEF2533);
                zh01.setTextColor(0xFF333333);
                zh03.setTextColor(0xFF333333);
             if (PlatType==0){
               sort="31";
               setPageToOne();
               httpPost(commodityList.getSearchtime());
             }else {
                 sortType = "10";
                 setPageToOne();
                 httpPost("");
             }
                popupWindow.dismiss();
            }

        });
        zh03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zh03.setTextColor(0xFFEF2533);
                zh02.setTextColor(0xFF333333);
                zh01.setTextColor(0xFF333333);
                    sortType = "06";
                    setPageToOne();
                    httpPost("");
                popupWindow.dismiss();
            }
        });
        popupView.findViewById(R.id.lable_list_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    //设置PopupWindow消失监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
        //Android7.0中PopupWindow(showAsDropDown())弹出位置异常问题
        if (Build.VERSION.SDK_INT < 24) {
            popupWindow.showAsDropDown(fragmentSearchListOne);
        } else {
            Rect visibleFrame = new Rect();
            fragmentSearchListOne.getGlobalVisibleRect(visibleFrame);
            int height = fragmentSearchListOne.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            popupWindow.setHeight(height);
            int[] location = new int[2];
            int y = location[1];
            popupWindow.showAsDropDown(fragmentSearchListOne, 0, y);
        }
    }

    //外部调用搜索
    public void searchGoods(String searchContent){
        content=searchContent;
        page=1;
       pagePDD=1;
       pageJD=1;
       pageWPH=1;
        httpPost("");
    }

    private int pageNetwork=1;
    //全网搜数据请求
    private void httpPostNetwork() {
        // 全网搜索
        paramMap.clear();
        paramMap.put("pageSize", pageSize + "");
        paramMap.put("pageNo", pageNetwork + "");
        paramMap.put("search", content);
        if (network_list_switch!=null){
        paramMap.put("hasCoupon", network_list_switch.isChecked() ? "1" : "");
        }
        paramMap.put("sort", sort);
        switch (searchScreen) {
            case 0:
                paramMap.put("tmall", "");
                break;
            case 1:
                paramMap.put("tmall", "00");
                break;
            case 2:
                paramMap.put("tmall", "01");
                break;
        }
        paramMap.put("sectionPrice", range);
        NetworkRequest.getInstance().POST(handler, paramMap, "SearchAllChangeCommority", HttpCommon.SearchAll);
    }


    //获取小程序码图
    private void getLittleAppImage(String goodsId,int type){
        showDialog();
        String sceneType="";
        switch (type){
            case 1:
                sceneType="pdd";
                break;
            case 2:
                sceneType="jd";
                break;
            case 3:
                sceneType="wph";
                break;
        }
        paramMap.clear();
        paramMap.put("userid", login.getUserid());
        paramMap.put("scene", sceneType+"&"+goodsId);
        paramMap.put("page", "pagesGoods/detail/detail");
        NetworkRequest.getInstance().GETNew(handler, paramMap, "LittleAppImage", HttpCommon.LittleAppImage);
    }


    //pjw转链
    //优惠券
    private void getGenByGoodsId(JDGoodsBean jdGoodsBean){
        showDialog();
        paramMap.clear();
        paramMap.put("goodsId", jdGoodsBean.getId());
        switch (PlatType){
            case 1:
                NetworkRequest.getInstance().GETNew(handler, paramMap, "GenByGoodsId", HttpCommon.PDDGenByGoodsId);
                break;
            case 2:
                if (jdGoodsBean.getCouponInfo()!=null) {
                    JSONObject coupon=(JSONObject) jdGoodsBean.getCouponInfo();
                    paramMap.put("couponLink", coupon.getString("link"));
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "GenByGoodsId", HttpCommon.JDGenByGoodsId);
                }else{
                    paramMap.put("couponLink", "");
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "GenByGoodsId", HttpCommon.JDGenByGoodsId);
                }
                break;
            case 3:
                NetworkRequest.getInstance().GETNew(handler, paramMap, "GenByGoodsId", HttpCommon.WPHGenByGoodsId);
                break;
            case 5:
                if (jdGoodsBean.getCouponInfo()!=null) {
                    JSONObject coupon=(JSONObject) jdGoodsBean.getCouponInfo();
                    paramMap.put("couponLink", coupon.getString("link"));
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "GenByGoodsId", HttpCommon.SNGenByGoodsId);
                }else{
                    paramMap.put("couponLink", "");
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "GenByGoodsId", HttpCommon.SNGenByGoodsId);
                }
                break;
        }
    }

}
