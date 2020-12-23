package com.lxkj.dmhw.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
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
import com.lxkj.dmhw.activity.MorePlatformNewActivity;
import com.lxkj.dmhw.activity.MorePlatformNewActivity340;
import com.lxkj.dmhw.adapter.MorePlAdapter;
import com.lxkj.dmhw.bean.Coupon;
import com.lxkj.dmhw.bean.JDGoodsBean;
import com.lxkj.dmhw.bean.LoginToken;
import com.lxkj.dmhw.bean.PJWLink;
import com.lxkj.dmhw.bean.ShareParams;
import com.lxkj.dmhw.bean.UserInfo;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.LazyFragment;
import com.lxkj.dmhw.dialog.ShareLittleAppDialog;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.InternalMessage;
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

/**
 * 多平台其他品类
 */

public class MorePlFragment extends LazyFragment implements BaseQuickAdapter.RequestLoadMoreListener{
    @BindView(R.id.more_pl_list)
    RecyclerView more_pl_list;

    private MorePlAdapter adapter;

    private String position = "";
    private int platform=1;
    private int posNum=0;
    private String shareOrSave="share";//默认分享朋友圈
    private boolean isCheck = true;
    ArrayList<JDGoodsBean> jdGoodsBeanArrayList= new ArrayList<>();
    private JDGoodsBean jdGoodsBean;
    private String path;
    private String currentShareUrl="";
    private String currentShareTitle="";
    private String currentShareType="";
    public static MorePlFragment getInstance(String  capId,int posNum,int plattype) {
        MorePlFragment fragment = new MorePlFragment();
        Bundle bundle = new Bundle();
        bundle.putString("number", capId);
        bundle.putInt("posNum", posNum);
        bundle.putInt("platform", plattype);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            position = bundle.getString("number");
            platform = bundle.getInt("platform");
            posNum = bundle.getInt("posNum");

        }
    }


    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.more_pl_fragment, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {
        more_pl_list.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), false));
        adapter = new MorePlAdapter(getActivity(),platform,false);
        more_pl_list.setAdapter(adapter);
//        // 设置预加载位置,倒数
        adapter.setPreLoadNumber(5);
        // 监听加载更多事件
        adapter.setOnLoadMoreListener(this, more_pl_list);
        // 取消第一次加载回调
        adapter.disableLoadMoreIfNotFullPage();


      adapter.setOnItemClickListener(new MorePlAdapter.OnItemClickListener() {
          @Override
          public void onItemClick(JDGoodsBean data,int type) {
              if (!DateStorage.getLoginStatus()){
                  startActivity(new Intent(getActivity(), LoginActivity.class));
                  return;
              }
           switch (type){
               case 1:
                   //分享
                   InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ShareStatus"), false, 1);
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
                    switch (platform){
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
                        case 6:
                            startActivity(new Intent(getActivity(), CommodityActivityPJW.class).putExtra("GoodsId", data.getId()).putExtra("type", "kl"));
                            break;
                  }
                   break;
           }

          }
      });

    }

    @Override
    public void onCustomized() {
        page=1;
        httpPost("");
    }


    private boolean isfirst=true;
    public void httpPost(String time) {
        if (page ==1&&isfirst){
            isfirst=false;
            showDialog();
        }
        // 请求商品搜索（标签）
        paramMap.clear();
        paramMap.put("userid", login.getUserid());
        paramMap.put("catId", position+"");
        paramMap.put("page", page + "");
        paramMap.put("pagesize", pageSize + "");
        if (platform==2){
            NetworkRequest.getInstance().GETNew(handler, paramMap, "JDGoods", HttpCommon.JDGoods);
        }else if(platform==1){
            NetworkRequest.getInstance().GETNew(handler, paramMap, "JDGoods", HttpCommon.PDDGoods);
        }else if(platform==3){
            NetworkRequest.getInstance().GETNew(handler, paramMap, "JDGoods", HttpCommon.WPHGoods);
        }else if(platform==5){
            NetworkRequest.getInstance().GETNew(handler, paramMap, "JDGoods", HttpCommon.SNGoods);
        }else{
            NetworkRequest.getInstance().GETNew(handler, paramMap, "JDGoods", HttpCommon.KLGoods);
        }

    }

    //登录刷新标志
    private  boolean loginRefreshFlag=false;
    @Override
    public void onResume() {
        super.onResume();
        if (loginRefreshFlag&&posNum== MorePlatformNewActivity.currentPos){
            loginRefreshFlag=false;
            //获取token
            if (DateStorage.getMyToken().equals("")||DateStorage.getMicroAppId().equals("")||DateStorage.getLittleAppId().equals("")){
                paramMap.clear();
                paramMap.put("userid", login.getUserid());
                NetworkRequest.getInstance().GETNew(handler, paramMap, "LoginToken", HttpCommon.LoginToken);
            }else{
                page=1;
                httpPost("");
            }
        }
    }
    @Override
    public void mainMessage(Message message) {
        //登录之后刷新页面 该页面标记成可刷新
        if (message.what == LogicActions.LoginStatusSuccess&&(Boolean) message.obj) {
            loginRefreshFlag=true;
        }
     if (message.what==LogicActions.RefreshSortListSuccess&&posNum==message.arg1){
        refreshData();
     }
    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        if (message.what == LogicActions.JDGoodsSuccess) {
            dismissDialog();
            jdGoodsBeanArrayList = (ArrayList<JDGoodsBean>) message.obj;
            if (jdGoodsBeanArrayList.size() > 0) {
                if (jdGoodsBeanArrayList.size() < pageSize) {
                    if (page > 1) {
                        adapter.addData(jdGoodsBeanArrayList);
                    } else {
                        adapter.setNewData(jdGoodsBeanArrayList);
                        adapter.notifyDataSetChanged();
                        ((MorePlatformNewActivity) getActivity()).refreshComplete();
                    }
                    adapter.loadMoreEnd();
                }else{
                    if (page > 1) {
                        adapter.addData(jdGoodsBeanArrayList);
                    } else {
                        adapter.setNewData(jdGoodsBeanArrayList);
                        adapter.notifyDataSetChanged();
                        ((MorePlatformNewActivity) getActivity()).refreshComplete();
                    }
                    adapter.loadMoreComplete();
                }
                isCheck = true;
            } else {
                adapter.loadMoreEnd();
                isCheck = false;
            }
        }
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
                                share.WeChatLittleApp(getActivity(),Utils.jointBitmapLittleApp(getActivity(), bitmap, goodsbitmap[0], userbitmap[0],jdGoodsBean,platform), false);//分享朋友圈
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
                                Utils.saveFile(Variable.imagesPath, Utils.jointBitmapLittleApp(getActivity(), bitmap, goodsbitmap[0], userbitmap[0], jdGoodsBean,platform), 100, true);
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

        if (message.what == LogicActions.LoginTokenSuccess) {
            LoginToken loginToken= (LoginToken) message.obj;
            DateStorage.setMyToken(loginToken.getToken());
            DateStorage.setLittleAppId(loginToken.getMicroId());
            DateStorage.setMicroAppId(loginToken.getWechatMicroId());
            page=1;
            httpPost("");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }



    @Override
    protected void initData() {

    }

    public void refreshData(){
        page=1;
        httpPost("");
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
            case 5:
                sceneType="sn";
                break;
            case 6:
                sceneType="kl";
                break;
        }
        paramMap.clear();
        paramMap.put("userid", login.getUserid());
        paramMap.put("scene", sceneType+"&"+goodsId);
        paramMap.put("page", "pagesGoods/detail/detail");
        NetworkRequest.getInstance().GETNew(handler, paramMap, "LittleAppImage", HttpCommon.LittleAppImage);
    }


    @Override
    public void onLoadMoreRequested() {
        page++;
        httpPost("");
    }


    //pjw转链
    //优惠券
    private void getGenByGoodsId(JDGoodsBean jdGoodsBean){
        showDialog();
        paramMap.clear();
        paramMap.put("goodsId", jdGoodsBean.getId());
        switch (platform){
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
                NetworkRequest.getInstance().GETNew(handler, paramMap, "GenByGoodsId", HttpCommon.SNGenByGoodsId);
                break;
            case 6:
                NetworkRequest.getInstance().GETNew(handler, paramMap, "GenByGoodsId", HttpCommon.KLGenByGoodsId);
                break;
        }
    }
}
