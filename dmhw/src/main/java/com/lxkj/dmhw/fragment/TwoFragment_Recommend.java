package com.lxkj.dmhw.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.activity.AliAuthWebViewActivity;
import com.lxkj.dmhw.activity.CommodityActivity290;
import com.lxkj.dmhw.activity.LoginActivity;
import com.lxkj.dmhw.activity.MainActivity;
import com.lxkj.dmhw.activity.MorePlatformNewActivity;
import com.lxkj.dmhw.activity.NewActivity;
import com.lxkj.dmhw.activity.ShareActivity330;
import com.lxkj.dmhw.adapter.RecommendAdapter;
import com.lxkj.dmhw.adapter.ShareCheck;
import com.lxkj.dmhw.bean.Recommend;
import com.lxkj.dmhw.bean.ShareInfo;
import com.lxkj.dmhw.bean.ShareList;
import com.lxkj.dmhw.bean.ShareParams;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.LazyFragment;
import com.lxkj.dmhw.dialog.AlipayDialog;
import com.lxkj.dmhw.dialog.OneKeyShareDialog;
import com.lxkj.dmhw.dialog.TaobaoAuthLoginDialog;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.MyLayoutManager;
import com.lxkj.dmhw.utils.Share;
import com.lxkj.dmhw.utils.ToastUtil;
import com.lxkj.dmhw.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 爆款推荐
 * Created by Android on 2018/5/8.
 */

public class TwoFragment_Recommend extends LazyFragment implements PtrHandler, BaseQuickAdapter.RequestLoadMoreListener, RecommendAdapter.OnClickListener {

    @BindView(R.id.fragment_two_recycler)
    RecyclerView fragmentTwoRecycler;
    @BindView(R.id.load_more_ptr_frame)
    PtrClassicFrameLayout loadMorePtrFrame;
    @BindView(R.id.fragment_two_recommend_layout)
    LinearLayout fragmentTwoRecommendLayout;
    private RecommendAdapter adapter;
    private Recommend recommend = new Recommend();
    private Recommend.RecommendList recommendList;
    private boolean isCheck = false;
    private boolean isPlay = false;
    private boolean isshare=true;

    private String recommendContent="";
    private String type = "";
    private int currPos=0;
    public static TwoFragment_Recommend getInstance(int pos,String type) {
        TwoFragment_Recommend fragment = new TwoFragment_Recommend();
        Bundle bundle = new Bundle();
        bundle.putInt("currPos",pos);
        bundle.putString("Type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getString("Type");
            currPos=bundle.getInt("currPos");
        }
    }

    @Override
    protected void initData() {
        httpPost("");
    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two_recommend, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {
        // 设置Recycler
        fragmentTwoRecycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), false));
        adapter = new RecommendAdapter(getActivity());
        fragmentTwoRecycler.setAdapter(adapter);
        // 设置预加载位置,倒数
        adapter.setPreLoadNumber(1);
        // 监听加载更多事件
        adapter.setOnLoadMoreListener(this, fragmentTwoRecycler);
        // 取消第一次加载回调
        adapter.disableLoadMoreIfNotFullPage();
        // 监听事件
        adapter.setOnClickListener(this);
    }

    @Override
    public void onCustomized() {
        ptrFrameLayout();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (loginRefreshFlag&&currPos== TwoFragment_RecommendAll.currentPos&&MainActivity.currentPos==3&&TwoFragment.currPos==0){
            loginRefreshFlag=false;
                page=1;
                httpPost("");
        }
    }
    TaobaoAuthLoginDialog Tdialog;
    //登录刷新标志
    private  boolean loginRefreshFlag=false;
    @Override
    public void mainMessage(Message message) {
        if (message.what == LogicActions.LoginStatusSuccess&&(Boolean) message.obj) {
            if (currPos == TwoFragment_RecommendAll.currentPos && TwoFragment.currPos == 0) {//退出登录刷新
                loginRefreshFlag = false;
                page = 1;
                httpPost("");
            }else{
                loginRefreshFlag = true;
            }
        }
        if (message.what == LogicActions.noAuthSuccessfulSuccess&&TwoFragment_RecommendAll.currentFragmentName.equals(type)) {
            dismissDialog();
        }
//        if (message.what == LogicActions.CloseTbaoAuthDialogSuccess&&TwoFragment_RecommendAll.currentFragmentName.equals(type)) {
//            if(Tdialog != null) {
//                //判断dialog是否正在显示
//                if(Tdialog.isShowing()) {
//                    //get the Context object that was used to great the dialog
//                    Context context = ((ContextWrapper)Tdialog.getContext()).getBaseContext();
//                    //判断是所在Activity是否已经销毁
//                    if(context instanceof Activity) {
//                        if(!((Activity)context).isFinishing() && !((Activity)context).isDestroyed())
//                            Tdialog.dismiss();
//                    } else
//                        Tdialog.dismiss();
//                }
//                Tdialog = null;
//            }
//        }
        //弹出一键发圈对话框
        if (message.what == LogicActions.oneKeyShareSuccess&&message.arg1==0&&TwoFragment_RecommendAll.currentFragmentName.equals(type)) {
            dismissDialog();
            JSONObject obj =  JSON.parseObject(message.obj.toString());
            String msgstr=obj.getString("msgstr");
            String url=obj.getString("url");
            OneKeyShareDialog oneKeyShareDialog=new OneKeyShareDialog(getActivity(),msgstr);
            oneKeyShareDialog.setOneKeyClickListener(new OneKeyShareDialog.OnOneKeyClickListener() {
                @Override
                public void onClick() {
                    if (oneKeyShareDialog!=null&&oneKeyShareDialog.isShowing()){
                        oneKeyShareDialog.hideDialog();
                    }
                    Intent intent = new Intent(getActivity(), AliAuthWebViewActivity.class);
                    intent.putExtra(Variable.webUrl, url);
                    intent.putExtra("isTitle", true);
                    startActivity(intent);
                }
            });
            oneKeyShareDialog.show();
        }
    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        loadMorePtrFrame.refreshComplete();
        dismissDialog();
        if (message.what == LogicActions.RecommendSuccess) {
            recommend = (Recommend) message.obj;
            if (recommend.getRecommendList().size() > 0) {
                if (page > 1) {
                    adapter.addData(recommend.getRecommendList());
                    adapter.notifyDataSetChanged();
                } else {
                    adapter.setNewData(recommend.getRecommendList());
                    adapter.notifyDataSetChanged();
                }
                adapter.loadMoreComplete();
            } else {

                adapter.loadMoreEnd();
            }
        }
        if (message.what == LogicActions.RecommendShopSuccess) {
            HashMap<String, ShareInfo> map = (HashMap<String, ShareInfo>) message.obj;
            ArrayList<Recommend.RecommendList.RecommendData> imageUrls = recommendList.getShopList();
            String title = recommendList.getContent();
            if (isCheck) {
                for (int i = 0; i < imageUrls.size(); i++) {
                    int finalI = i;
                    Glide.with(getActivity()).asBitmap()
                            .load(imageUrls.get(finalI).getImage()).into(new SimpleTarget<Bitmap>()
                         {
                             @Override
                             public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                 if (map.get(imageUrls.get(finalI).getId()) != null) {
                                     Utils.saveFile(Variable.imagesPath, Utils.jointBitmapNew(getActivity(), resource, map.get(imageUrls.get(finalI).getId())), 100, true);
                                 } else {
                                     Utils.saveFile(Variable.imagesPath, resource, 100, true);
                                 }
                             }
                            });

                }
                ToastUtil.showImageToast(getActivity(),"图片已保存至相册",R.mipmap.toast_img);
            } else {
                ShareParams params = new ShareParams();
                if (isPlay) {
                    ArrayList<String> image = new ArrayList<>();
                    image.add(imageUrls.get(0).getImage());
                    params.setImage(image);
                    ArrayList<ShareInfo> infos = new ArrayList<>();
                    infos.add(map.get(imageUrls.get(0).getId()));
                    params.setShareInfo(infos);
                } else {
                    ArrayList<String> image = new ArrayList<>();
                    ArrayList<ShareInfo> infos = new ArrayList<>();
                    for (Recommend.RecommendList.RecommendData imageUrl : imageUrls) {
                        image.add(imageUrl.getImage());
                        infos.add(map.get(imageUrl.getId()));
                    }
                    params.setImage(image);
                    params.setShareInfo(infos);
                }
                params.setSplice(Share.SPLICE_ALL);
                params.setContent(title);
                Share.getInstance(0).initialize(params,1);
                Utils.copyText(title);
                ToastUtil.showImageToast(getActivity(),"复制成功",R.mipmap.toast_img);
            }
        }
        if (message.what == LogicActions.GetCommissionSuccess) {
            ShareList shareList = (ShareList) message.obj;
                GetCommissionSuccess(shareList);
        }

        //一键发圈
        if (message.what == LogicActions.SendCircleSuccess) {
            dismissDialog();
           ToastUtil.showImageToast(getActivity(),message.obj+"",R.mipmap.toast_img);
//         toast(message.obj+"");
        }
    }
    private boolean isfirst=true;
    private void httpPost(String time) {
        // 每日推荐
        paramMap.clear();
        paramMap.put("userid", login.getUserid());
        paramMap.put("startindex", page + "");
        paramMap.put("searchtime", time);
        paramMap.put("pagesize", "3");
        paramMap.put("itemtype", type);
        NetworkRequest.getInstance().POST(handler, paramMap, "Recommend", HttpCommon.Recommend);
        if (page ==1&&isfirst){
            isfirst=false;
            showDialog();
        }

    }

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
    /**
     * 下拉刷新设置
     */
    private void ptrFrameLayout() {
        loadMorePtrFrame.setLoadingMinTime(700);
        loadMorePtrFrame.setHeaderView(loadView);
        loadMorePtrFrame.addPtrUIHandler(loadView);
        loadMorePtrFrame.setPtrHandler(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        httpPost("");
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, fragmentTwoRecycler, header);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        page = 1;
        httpPost("");
    }

    @Override
    public void onShare(Recommend.RecommendList item) {
        if (DateStorage.getLoginStatus()) {
            recommendList = item;
            AppShare(item);
        } else {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }
    //获取高佣之后的执行动作
    private void GetCommissionSuccess(ShareList shareList){
        recommendContent=recommendList.getContent().replaceAll("[<br>]{0,}","").replaceAll("(?m)^\\s*$(\\n|\\r\\n)", "");//去掉空行
        ArrayList<ShareCheck> list = new ArrayList<>();
        for (Recommend.RecommendList.RecommendData imageUrl : recommendList.getShopList()) {
            ShareCheck share = new ShareCheck();
            share.setImage(imageUrl.getImage());
            share.setCheck(0);
            list.add(share);
        }
        list.get(0).setCheck(1);
        dismissDialog();
        ShareInfo info=recommendList.getInfo();
        startActivity(new Intent(getActivity(), ShareActivity330.class)
                .putExtra("image", list)
                .putExtra("name", info.getName())
                .putExtra("noprice", true)
                .putExtra("sales", info.getSales())
                .putExtra("money", info.getMoney())
                .putExtra("commission", info.getCommission())
                .putExtra("shopprice", info.getShopprice())
                .putExtra("discount", info.getDiscount())
                .putExtra("shortLink", shareList.getShareshortlink())
                .putExtra("recommend", recommendContent)
                .putExtra("countersign", shareList.getTpwd())
                .putExtra("isCheck", info.isCheck()));
    }
    @Override
    public void onBuy(Recommend.RecommendList item) {
        if (DateStorage.getLoginStatus()) {
            if (Objects.equals(item.getType(), "0")) {// 单商品
                if (Objects.equals(item.getShopId(), "")) {
                    AlipayDialog dialog = new AlipayDialog(getActivity());
                    dialog.showDialog();
                    Utils.Alibc(getActivity(), item.getDiscount(), null, new AlibcTradeCallback() {
                        @Override
                        public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {
                            // 用户操作中成功信息回调
                            dialog.hideDialog();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            // 用户操作中错误信息回调
                            dialog.hideDialog();
                        }
                    });
                    new Handler().postDelayed(dialog::hideDialog, 3000);
                } else {
                    Intent intent = new Intent(getActivity(), CommodityActivity290.class);
                    intent.putExtra("shopId", item.getShopId());
                    intent.putExtra("source", "dmj");
                    intent.putExtra("sourceId", "");
                    startActivity(intent);
                }
            } else {// 多商品
                Intent intent = new Intent(getActivity(), NewActivity.class);
                intent.putExtra("name", item.getTopicName());
                intent.putExtra("labelType", "09");
                intent.putExtra("topicId", item.getTopicId());
                startActivity(intent);
            }
        } else {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }

    @Override
    public void onOneKey(Recommend.RecommendList item) {
        if (DateStorage.getLoginStatus()) {
          //调用一键分享接口
            showDialog();
            paramMap.clear();
            paramMap.put("recommentid", item.getId());
            NetworkRequest.getInstance().POST(handler, paramMap, "SendCircle", HttpCommon.SendCircle);
        } else {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }

    private void AppShare(Recommend.RecommendList item) {
        isCheck = false;
        isPlay = false;
        if (Objects.equals(item.getType(), "0")) {// 单商品
         // 获取高佣链接
            paramMap.clear();
            paramMap.put("userid", login.getUserid());
            paramMap.put("shopid", item.getShopId());
            paramMap.put("couponid", item.getCouponid());
            paramMap.put("shopname", item.getShopname());
            paramMap.put("shopmainpic",item.getImgpic1());
            paramMap.put("reqsource", "1");
            NetworkRequest.getInstance().POST(handler, paramMap, "GetCommission", HttpCommon.GetCommission);
            showDialog();
        } else {// 多商品
            paramMap.clear();
                    paramMap.put("userid", login.getUserid());
                    StringBuilder shopId = new StringBuilder();
                    ArrayList<Recommend.RecommendList.RecommendData> list = item.getShopList();
                    for (int i = 0; i < list.size(); i++) {
                        if (i == list.size() - 1) {
                    shopId.append(list.get(i).getId());
                } else {
                    shopId.append(list.get(i).getId()).append(",");
                }
            }
            paramMap.put("ids", shopId.toString());
            NetworkRequest.getInstance().POST(handler, paramMap, "RecommendShop", HttpCommon.RecommendShop);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
