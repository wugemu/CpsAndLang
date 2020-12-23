package com.lxkj.dmhw.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.activity.CommodityActivityPJW;
import com.lxkj.dmhw.adapter.CollectionAdapterPJW;
import com.lxkj.dmhw.bean.CommodityDetails290;
import com.lxkj.dmhw.bean.CpsType;
import com.lxkj.dmhw.bean.ShareInfo;
import com.lxkj.dmhw.bean.ShareParams;
import com.lxkj.dmhw.defined.LazyFragment;
import com.lxkj.dmhw.defined.SmoothCheckBox;
import com.lxkj.dmhw.dialog.TaobaoAuthLoginDialog;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.MyLayoutManager;
import com.lxkj.dmhw.utils.Share;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CollectionFragmentPJW extends LazyFragment implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener, SmoothCheckBox.OnCheckedChangeListener {
    @BindView(R.id.collection_recycler)
    RecyclerView collectionRecycler;
    @BindView(R.id.collection_check_all)
    SmoothCheckBox collectionCheckAll;
    @BindView(R.id.collection_check_all_layout)
    LinearLayout collectionCheckAllLayout;
    @BindView(R.id.collection_check_number)
    TextView collectionCheckNumber;
    @BindView(R.id.collection_delete)
    LinearLayout collectionDelete;
    @BindView(R.id.collection_share)
    LinearLayout collectionShare;
    // 适配器
    private CollectionAdapterPJW adapter;
    // 请求事件
//    private String time;
    // 点击状态
    private boolean isCheck = true;
    private int position = 0;
    private int number = 0;
    private View emptyView;
    TaobaoAuthLoginDialog Tdialog;
    private CommodityDetails290 details;
    ArrayList<CommodityDetails290>collectionList;
    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_collection, null);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onEvent() {
        emptyView=getLayoutInflater().inflate(R.layout.view_empty, null);
        ImageView image=emptyView.findViewById(R.id.empty_img);
        TextView textView1=emptyView.findViewById(R.id.empty_txt);
        TextView textView2=emptyView.findViewById(R.id.empty_txt2);
        image.setImageResource(R.mipmap.no_order);
        textView1.setText(getString(R.string.collect_empty_txt1));
        textView2.setText(getString(R.string.collect_empty_txt2));
        //控制推荐接受广播不弹授权框
        Variable.AuthShowStatus=true;
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        // 设置Recycler
        collectionRecycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), false));
        adapter = new CollectionAdapterPJW(getActivity());
        collectionRecycler.setAdapter(adapter);
        // 设置预加载位置,倒数
        adapter.setPreLoadNumber(5);
        // 监听item点击事件
        adapter.setOnItemClickListener(this);
        // 监听加载更多事件
        adapter.setOnLoadMoreListener(this, collectionRecycler);
        // 取消第一次加载回调
        adapter.disableLoadMoreIfNotFullPage();
        // CheckBox选中事件
        collectionCheckAll.setOnCheckedChangeListener(this);
    }
    @Override
    protected void initData() {
        showDialog();
        httpPost("");
    }
    @Override
    public void onCustomized() {

    }

    @Override
    public void onData() {

    }

    @Override
    public void mainMessage(Message message) {
        if (message.what == LogicActions.CollectionCheckSuccess) {
            isCheckAll();
        }
//        if (message.what == LogicActions.noAuthSuccessfulSuccess) {
//            dismissDialog();
//            Tdialog = new TaobaoAuthLoginDialog(getActivity(), message.obj.toString());
//            Tdialog.showDialog();
//        }
//        if (message.what == LogicActions.CloseTbaoAuthDialogSuccess) {
////            if (Tdialog!=null){
////                Tdialog.dismiss();
////            }
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
        if (message.what == LogicActions.CpsGoodsCollectSuccess) {
           collectionList = (ArrayList<CommodityDetails290>) message.obj;
            if (collectionList.size() > 0) {
                if (page > 1) {
                    adapter.addData(collectionList);
                } else {
                    adapter.setNewData(collectionList);
                }
                collectionCheckAll.setChecked(false, true);
                adapter.loadMoreComplete();
            } else {
                adapter.loadMoreEnd();
            }
            adapter.setEmptyView(emptyView);
        }
        if (message.what == LogicActions.DelCpsGoodsCollectSuccess) {
            toast(message.obj + "");
            collectionCheckNumber.setText("当前选中0个");
            page = 1;
            httpPost("");
        }
        dismissDialog();

        //完成任务
        if (message.what == LogicActions.DayBuySuccess) {
            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ShareFinish"), false, 0);
        }
    }

    private void httpPost(String time) {
        paramMap.clear();
        paramMap.put("userid", login.getUserid());
        paramMap.put("page", page + "");
        paramMap.put("pagesize", pageSize + "");
        NetworkRequest.getInstance().GETNew(handler, paramMap, "CpsGoodsCollect", HttpCommon.CpsGoodsCollect);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
       CommodityDetails290 collectionList = (CommodityDetails290) adapter.getData().get(position);
        if (collectionList.getIsEffective()) {
            Object CpsTypeObject = JSON.parseObject(collectionList.getCpsType(), CpsType.class);
            CpsType objtype=(CpsType) CpsTypeObject;
            switch (objtype.getCode()){
                case "pdd":
                    startActivity(new Intent(getActivity(), CommodityActivityPJW.class).putExtra("GoodsId", collectionList.getId()).putExtra("type", "pdd"));
                    break;
                case "jd":
                    startActivity(new Intent(getActivity(), CommodityActivityPJW.class).putExtra("GoodsId", collectionList.getId()).putExtra("type", "jd"));
                    break;
                case "wph":
                    startActivity(new Intent(getActivity(), CommodityActivityPJW.class).putExtra("GoodsId", collectionList.getId()).putExtra("type", "wph"));
                    break;
                case "sn":
                    startActivity(new Intent(getActivity(), CommodityActivityPJW.class).putExtra("GoodsId", collectionList.getId()).putExtra("type", "sn"));
                    break;
                case "kl":
                    startActivity(new Intent(getActivity(), CommodityActivityPJW.class).putExtra("GoodsId", collectionList.getId()).putExtra("type", "kl"));
                    break;
            }
        } else {
            toast("商品优惠期已过期！");
        }
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        httpPost("");
    }

    @OnClick({R.id.collection_check_all_layout, R.id.collection_delete, R.id.collection_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.collection_check_all_layout:
                collectionCheckAll.performClick();
                break;
            case R.id.collection_delete:
                if (position == 0) {
                    toast("请先选择商品！");
                } else {
                    paramMap.clear();
                    paramMap.put("userid", login.getUserid());
                    String ids="";
                    for (CommodityDetails290 item: adapter.getData()) {
                         if (item.isPlay()) {
                           ids+=item.getCollectid()+",";
                          }
                        }
                    paramMap.put("collectIds", ids);
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "DelCpsGoodsCollect", HttpCommon.DelCpsGoodsCollect);
                    showDialog();
                }
                break;
            case R.id.collection_share:
                //批量存图不可见
                Variable.isVisible = 0;
                if (position == 0) {
                    toast("请先选择商品！");
                } else if (position > 9) {
                    toast("分享商品不能超过9件！");
                } else {
                    ArrayList<String> imageUrls = new ArrayList<>();
                    ArrayList<ShareInfo> info = new ArrayList<>();
                    Boolean iscanshare=false;
                    for (CommodityDetails290 item : adapter.getData()) {
                        if (item.isPlay()) {
                            if (item.getIsEffective()) {
                                ShareInfo shareInfo = new ShareInfo();
                                imageUrls.add(item.getImageUrl());
                                shareInfo.setName(item.getName());
                                shareInfo.setSales(item.getSales());
                                shareInfo.setMoney(item.getPrice());
                                shareInfo.setShopprice(item.getCost());
                                shareInfo.setDiscount(item.getSave());
                                if (item.getCpsPromotionVo()!=null){
                                    Object CPSObject = JSON.parseObject(item.getCpsPromotionVo());
                                    shareInfo.setShortLink(((JSONObject) CPSObject).getString("shortUrl"));
                                }
                                shareInfo.setRecommended(item.getDesc());
                                if (item.getCpsType()!=null){
                                Object CpsTypeObject = JSON.parseObject(item.getCpsType(), CpsType.class);
                                CpsType objtype=(CpsType) CpsTypeObject;
                                shareInfo.setType(objtype.getCode());
                                }
                                info.add(shareInfo);
                                iscanshare=true;
                            } else {
                                toast("请先取消失效的商品！");
                                iscanshare=false;
                                break;
                            }
                        }
                    }
                    if (iscanshare){
                    share(imageUrls, info);
                    }
                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
        if (isChecked) {
            adapter.setCheckAll(true);
        } else {
            adapter.setCheckAll(false);
        }
        selectedPosition();
    }

    /**
     * 判断是否全选
     */
    private void isCheckAll() {
        boolean isCheck = false;
        for (CommodityDetails290 list : adapter.getData()) {
            if (list.isPlay()) {
                isCheck = true;
            } else {
                isCheck = false;
                break;
            }
        }
        if (isCheck) {
            collectionCheckAll.setChecked(true, true);
        } else {
            if (collectionCheckAll.isChecked()) {
                collectionCheckAll.setChecked(false, true);
            }
        }
        selectedPosition();
    }

    /**
     * 选中数量
     */
    private void selectedPosition() {
        position = 0;
        for (CommodityDetails290 item : adapter.getData()) {
            if (item.isPlay()) {
                position++;
            }
        }
        collectionCheckNumber.setText("当前选中" + position + "个");
    }

    private void share(ArrayList<String> imageUrls, ArrayList<ShareInfo> info) {
        ShareParams params = new ShareParams();
        params.setSplice(Share.SPLICE_ALL);
        params.setImage(imageUrls);
        params.setShareTag(1);
        params.setShareInfo(info);
        params.setIsPJW(1);
        Share.getInstance(0).initialize(params,1);
        collectionShare.setEnabled(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Variable.AuthShowStatus=false;
    }

}
