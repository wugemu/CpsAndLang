package com.lxkj.dmhw.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.activity.CommodityActivityPJW;
import com.lxkj.dmhw.activity.LoginActivity;
import com.lxkj.dmhw.adapter.MorePlAdapter;
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
import butterknife.OnClick;

/**
 * 多平台 主题和频道商品 页面
 */

public class MorePlThemeFragment extends LazyFragment implements BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.more_pl_list)
    RecyclerView more_pl_list;

    private MorePlAdapter adapter;
    private String currentShareUrl="";
    private String currentShareTitle="";
    private String currentShareType="";
    private int position = 0;
    private int platform=1;
    private String shareOrSave="share";//默认分享朋友圈
    private boolean isCheck = true;
    ArrayList<JDGoodsBean> jdGoodsBeanArrayList= new ArrayList<>();
    private JDGoodsBean jdGoodsBean;
    private String path;
    private String themeId;
    private String type;
    private String name;
    public static MorePlThemeFragment getInstance(int plattype,String type,String Id,String name) {
        MorePlThemeFragment fragment = new MorePlThemeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("platform", plattype);
        bundle.putString("themeId", Id);
        bundle.putString("type", type);
        bundle.putString("name", name);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            platform = bundle.getInt("platform");
            themeId = bundle.getString("themeId");
            type = bundle.getString("type");
            name = bundle.getString("name");
        }
    }


    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.more_pl_theme_fragment, null);
        ButterKnife.bind(this, view);
        title.setText(name);
        return view;
    }

    @Override
    public void onEvent() {
        more_pl_list.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), false));
        more_pl_list.setNestedScrollingEnabled(false);
        adapter = new MorePlAdapter(getActivity(),platform,true);
        if (type.equals("03")){//频道商品 有分页 主题商品没有分页
            // 设置预加载位置,倒数
            adapter.setPreLoadNumber(5);
            // 监听加载更多事件
            adapter.setOnLoadMoreListener(this, more_pl_list);
            // 取消第一次加载回调
            adapter.disableLoadMoreIfNotFullPage();
        }
        more_pl_list.setAdapter(adapter);
      adapter.setOnItemClickListener(new MorePlAdapter.OnItemClickListener() {
          @Override
          public void onItemClick(JDGoodsBean data,int type) {
              if (!DateStorage.getLoginStatus()) {
                  startActivity(new Intent(getActivity(), LoginActivity.class));
                  return;
              }
              switch (type) {
                  case 1:
                      //分享
                      InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ShareStatus"), false, 1);
                      ShareLittleAppDialog shareLittleAppDialog = new ShareLittleAppDialog(getActivity());
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
                      switch (platform) {
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
        httpPost("");
    }

    public void refresh(){
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
        if (type.equals("03")){
            paramMap.put("page", page + "");
            paramMap.put("pagesize", pageSize + "");
        }
        String httpUrl="";
        switch (platform){
            case 1://拼多多
                if (type.equals("03")){
                    httpUrl=HttpCommon.PDDChannelGoods;
                    paramMap.put("channel",themeId);
                }else{
                    httpUrl=HttpCommon.PDDThemeGoods;
                    paramMap.put("themeId",themeId);
                }
                break;
            case 2://京东
                if (type.equals("03")){
                    httpUrl=HttpCommon.JDChannelGoods;
                    paramMap.put("channel",themeId);
                }else{
                    httpUrl=HttpCommon.JDThemeGoods;
                    paramMap.put("themeId",themeId);
                }
                break;
            case 3://唯品会
                if (type.equals("03")){
                    httpUrl=HttpCommon.WPHChannelGoods;
                    paramMap.put("channel",themeId);
                }else{
                    httpUrl=HttpCommon.WPHThemeGoods;
                    paramMap.put("themeId",themeId);
                }
                break;
            case 5://苏宁
                if (type.equals("03")){
                    httpUrl=HttpCommon.SNChannelGoods;
                    paramMap.put("channel",themeId);
                }else{
                    httpUrl=HttpCommon.SNThemeGoods;
                    paramMap.put("themeId",themeId);
                }
                break;
            case 6://考拉
                if (type.equals("03")){
                    httpUrl=HttpCommon.KLChannelGoods;
                    paramMap.put("channel",themeId);
                }else{
                    httpUrl=HttpCommon.KLThemeGoods;
                    paramMap.put("themeId",themeId);
                }
                break;

        }
        NetworkRequest.getInstance().GETNew(handler, paramMap, "ThemeGoods", httpUrl);
    }
    @Override
    public void mainMessage(Message message) {
        if (message.what == LogicActions.ShareStatusSuccess&&message.arg1==6) {
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
        if (message.what == LogicActions.ThemeGoodsSuccess) {
            dismissDialog();
            jdGoodsBeanArrayList = (ArrayList<JDGoodsBean>) message.obj;
            if(!type.equals("03")){
                if (jdGoodsBeanArrayList.size() > 0) {
                  adapter.setNewData(jdGoodsBeanArrayList);
                }
            }else{
                if (jdGoodsBeanArrayList.size() > 0) {
                    if (jdGoodsBeanArrayList.size()<pageSize){
                        if (page > 1) {
                            adapter.addData(jdGoodsBeanArrayList);
                        } else {
                            adapter.setNewData(jdGoodsBeanArrayList);
                        }
                        adapter.loadMoreEnd();
                    }else{
                        if (page > 1) {
                            adapter.addData(jdGoodsBeanArrayList);
                        } else {
                            adapter.setNewData(jdGoodsBeanArrayList);
                        }
                        adapter.loadMoreComplete();
                    }

                } else{
                    adapter.loadMoreEnd();
                }
            }

        }

        //获取小程序的码图
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
        //完成任务
        if (message.what == LogicActions.DayBuySuccess) {
            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ShareFinish"), false, 0);
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }



    @Override
    protected void initData() {

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
    @OnClick({R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                isFinish();
                break;
        }
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
                if (jdGoodsBean.getCouponInfo() != null) {
                    JSONObject coupon=(JSONObject) jdGoodsBean.getCouponInfo();
                    paramMap.put("couponLink",  coupon.getString("link"));
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "GenByGoodsId", HttpCommon.SNGenByGoodsId);
                }else{
                    paramMap.put("couponLink", "");
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "GenByGoodsId", HttpCommon.SNGenByGoodsId);
                }
                break;
            case 6:
                NetworkRequest.getInstance().GETNew(handler, paramMap, "GenByGoodsId", HttpCommon.KLGenByGoodsId);
                break;
        }
    }
}
