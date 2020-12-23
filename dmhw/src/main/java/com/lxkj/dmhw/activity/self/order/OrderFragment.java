package com.lxkj.dmhw.activity.self.order;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.test.andlang.andlangutil.BaseLangActivity;
import com.example.test.andlang.andlangutil.BaseLangFragment;
import com.example.test.andlang.util.ActivityUtil;
import com.example.test.andlang.util.ToastUtil;
import com.lxkj.dmhw.MyApplication;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.self.CartActivity;
import com.lxkj.dmhw.activity.self.PayResultActivity;
import com.lxkj.dmhw.activity.self.PayTypeActivity;
import com.lxkj.dmhw.adapter.self.cart.OrderContent;
import com.lxkj.dmhw.adapter.self.order.OrderAdapter;
import com.lxkj.dmhw.adapter.self.order.OrderItemBottom;
import com.lxkj.dmhw.adapter.self.order.OrderItemGoods;
import com.lxkj.dmhw.adapter.self.order.OrderItemTop;
import com.lxkj.dmhw.bean.self.GoodBuyStateBean;
import com.lxkj.dmhw.bean.self.OrderBean;
import com.lxkj.dmhw.bean.self.OrderGoodBean;
import com.lxkj.dmhw.bean.self.ShareBean;
import com.lxkj.dmhw.bean.self.TradeSkuVO;
import com.lxkj.dmhw.dialog.SalePayDialog;
import com.lxkj.dmhw.model.OrderModel;
import com.lxkj.dmhw.myinterface.ConfirmOKI;
import com.lxkj.dmhw.myinterface.OrderI;
import com.lxkj.dmhw.myinterface.SalePayI;
import com.lxkj.dmhw.presenter.OrderPresenter;
import com.lxkj.dmhw.utils.BBCUtil;
import com.lxkj.dmhw.utils.SDJumpUtil;
import com.lxkj.dmhw.utils.UMShareUtil;
import com.lxkj.dmhw.widget.MyListView;
import com.lxkj.dmhw.widget.dialog.ConfirmDialog;
import com.lxkj.dmhw.widget.dialog.OrderDownProductDialog;
import com.lxkj.dmhw.widget.swipe.RefreshLayout;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 */
public class OrderFragment extends BaseLangFragment<OrderPresenter> {

    @BindView(R.id.m_refersh)
    RefreshLayout mRefersh;
    @BindView(R.id.lv_order)
    MyListView lv_order;
    @BindView(R.id.rl_nodata)
    RelativeLayout rl_nodata;
    @BindView(R.id.iv_no_data)
    ImageView iv_no_data;
    @BindView(R.id.tv_no_data)
    TextView tv_no_data;

    private OrderI orderI;
    private ObjectAnimator animator;
    private List<OrderContent> orderContentList;
    private OrderAdapter orderAdapter;
    private int status;//0全部 1待付款 5待发货 6待收货 7已完成
    private int orderType = 2;//2我的 3分享
    private SalePayDialog salePayDialog;//支付校验
    private View footView;
    private BaseLangActivity myActivity;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    public void initView() {
        //设置下拉刷新
        View header = LayoutInflater.from(activity).inflate(R.layout.include_loading, null);
        footView= LayoutInflater.from(activity).inflate(R.layout.lang_common_bottom, null);
        final View image = header.findViewById(R.id.iv_loading_top);
        animator = ObjectAnimator.ofFloat(image, "rotation", 0f, 360f);
        mRefersh.setRefreshHeader(header);
        if (mRefersh != null) {
            // 刷新状态的回调
            mRefersh.setRefreshListener(new RefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    mRefersh.startAnim(animator);
                    if (presenter != null) {
                        refresh(orderType);
                    }
                }
            });

        }

        tv_no_data.setVisibility(View.VISIBLE);
        tv_no_data.setText("您还没有相关的订单噢～");
        iv_no_data.setImageResource(R.mipmap.no_order);
        myActivity=(BaseLangActivity) activity;
    }

    //处理订单列表偶尔图片不显示问题
    public void notifyDataSetChanged(){
        if (orderAdapter!=null){
            orderAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void initPresenter() {
        presenter = new OrderPresenter(this, myActivity, OrderModel.class);
    }

    @Override
    public void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            status = bundle.getInt("status");
        }
        myActivity.showWaitDialog();
        refresh(orderType);
    }

    public void refresh(int orderType) {
        this.orderType = orderType;
        if(presenter!=null) {
            presenter.reqOrderList(status, orderType, false);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        myActivity.dismissWaitDialog();
        mRefersh.refreshComplete();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                //execute the task
                mRefersh.stopAnim(animator);
            }
        }, 3000);
        if ("reqOrderList".equals(arg)) {
            if (orderContentList == null) {
                orderContentList = new ArrayList<OrderContent>();
            }
            orderContentList.clear();
            orderContentList.addAll(getOrderContents(presenter.model.getOrderBeanList()));
            if (orderAdapter == null) {
                orderAdapter = new OrderAdapter(activity, orderContentList, status, orderType, presenter);
                lv_order.setAdapter(orderAdapter);
            } else {
                orderAdapter.setOrderType(orderType);
                orderAdapter.setStatus(status);
                orderAdapter.notifyDataSetChanged();
            }
            //隐藏底部 我是有底线的
//            if (!presenter.haveMore&&lv_order.getFooterViewsCount()==0){
//                lv_order.addFooterView(footView);
//            }else{
//                lv_order.removeFooterView(footView);
//            }

            if (orderContentList.size() > 0) {
                rl_nodata.setVisibility(View.GONE);
            } else {
                rl_nodata.setVisibility(View.VISIBLE);
            }
        } else if ("reqDelOrder".equals(arg)) {
            //删除订单
            orderI.setRefreshState();//设置其他页面刷新状态
            orderI.refreshFragment(-1);
        } else if ("reqCancelOrder".equals(arg)) {
            //取消订单 刷新玩主玩客首页 余额相关
//            SDJumpUtil.refreshHomeDataIndex(HomeActivity.GO_MAINPLAY);
            orderI.setRefreshState();//设置其他页面刷新状态
            orderI.refreshFragment(-1);
            presenter.getUserAccountInfo();//更新用户身份
        } else if ("reqConfirmOrder".equals(arg)) {
            //确定收货
            orderI.setRefreshState();//设置其他页面刷新状态
            orderI.refreshFragment(-1);
        } else if ("reqChangeCart".equals(arg)) {
            //再次购买
            ToastUtil.show(activity, "加入购物车成功");
            Intent intent = new Intent(activity, CartActivity.class);
            ActivityUtil.getInstance().start(activity, intent);
        } else if ("reqOrderToPay".equals(arg)) {
            //订单去支付
            if (presenter.model == null) {
                return;
            }
            //余额支付
            if (presenter.model.getIfCheckSms() == 1) {
                //需要短信验证
                if (salePayDialog == null) {
                    salePayDialog = new SalePayDialog(activity, presenter.model.getMobile(), new SalePayI() {
                        @Override
                        public void sendCode() {
                            presenter.sendSafeCode(presenter.model.getTradeNo());
                        }

                        @Override
                        public void commit(String captcha) {
                            presenter.checkSmsForCachOrder(captcha, presenter.model.getTradeNo());
                        }

                        @Override
                        public void clickClose() {

                        }
                    });
                } else {
                    salePayDialog.showDialog();
                }
            } else {
                //不需要短信验证
                if (presenter.model.getAmount() > 0) {
                    //需要第三方支付
                    Intent intent = new Intent(activity, PayTypeActivity.class);
                    intent.putExtra("realPayAmount", BBCUtil.getDoubleFormat2(presenter.model.getAmount()));
                    intent.putExtra("tradeNo", presenter.model.getTradeNo());
                    intent.putExtra("payCountDown", presenter.model.getPayCountDown());
                    intent.putExtra("isPayGift", presenter.model.isPayGift());
                    intent.putExtra("isGroupOrder",presenter.model.isPayGroup());
                    ActivityUtil.getInstance().startResult(activity, intent, OrderListActivity.REQ_PAYRESULT);
                }
            }
        } else if ("sendSafeCode".equals(arg)) {
            //支付发送短信成功
            if (salePayDialog != null) {
                salePayDialog.startDownTime();
            }
        } else if ("checkSmsForCachOrder".equals(arg)) {
            //短信支付校验成功
            if (salePayDialog != null) {
                salePayDialog.cancelDialog();
            }

//            if (presenter.model.isPayGift()) {
//                //礼包支付结果
//                SuDianApp.PAY_RESULT_TRADE_NO=presenter.model.getTradeNo();
//                Intent intent = new Intent(activity, PayResultGiftActivity.class);
//                ActivityUtil.getInstance().startResult(activity, intent, OrderListActivity.REQ_PAYRESULT);
//            }else if(presenter.model.isPayGroup()){
//                //团订单支付结果
//                Intent intent = new Intent(activity, GroupInfoActivity.class);
//                intent.putExtra("tradeNo",presenter.model.getTradeNo());
//                intent.putExtra("isPayResult",true);
//                ActivityUtil.getInstance().startResult(activity, intent, OrderListActivity.REQ_PAYRESULT);
//            } else {
//                //普通支付结果
//                SuDianApp.PAY_RESULT_TRADE_NO=presenter.model.getTradeNo();
//                Intent intent = new Intent(activity, PayResultActivity.class);
//                ActivityUtil.getInstance().startResult(activity, intent, OrderListActivity.REQ_PAYRESULT);
//            }
            //普通支付结果
            MyApplication.PAY_RESULT_TRADE_NO=presenter.model.getTradeNo();
            Intent intent = new Intent(activity, PayResultActivity.class);
            ActivityUtil.getInstance().startResult(activity, intent, OrderListActivity.REQ_PAYRESULT);
        } else if ("checkOrderBuy".equals(arg)) {
            //请求商品是否可以加入购物车
            againAddCar(presenter.model.getGoodBuyStateBeanList());
        }else if("confirmTradeClose".equals(arg)){
            //订单完结
            orderI.setRefreshState();//设置其他页面刷新状态
            orderI.refreshFragment(-1);
        }else if ("reqShareGroupInfo".equals(arg)) {//团购分享到小程序
            ShareBean shareBean = presenter.model.getShareBean();
            if (shareBean != null) {
                UMShareUtil.getInstance().umengShareMin(getActivity(), shareBean, SHARE_MEDIA.WEIXIN, 0);
            }
        }
    }

    //再次购买
    public void againAddCar(List<GoodBuyStateBean> goodBuyStateBeanList) {
        if (goodBuyStateBeanList == null) {
            goodBuyStateBeanList = new ArrayList<GoodBuyStateBean>();
        }
        List<GoodBuyStateBean> shixiaoList = new ArrayList<GoodBuyStateBean>();
        final List<TradeSkuVO> tradeSkuVOList = new ArrayList<TradeSkuVO>();
        if (goodBuyStateBeanList != null) {
            for (GoodBuyStateBean stateBean : goodBuyStateBeanList
                    ) {
                if (!stateBean.isState()) {
                    //已失效
                    shixiaoList.add(stateBean);
                    continue;
                }
                TradeSkuVO tradeSkuVO = new TradeSkuVO();
                tradeSkuVO.setSkuId(stateBean.getSkuId());
                tradeSkuVO.setNum(stateBean.getNum());//后台未返回商品数量 默认1
                tradeSkuVO.setWithinBuyId(stateBean.getWithinBuyId());//内购场id
                tradeSkuVOList.add(tradeSkuVO);
            }
        }

        if (tradeSkuVOList.size() == 0) {
            //订单中的商品都失效了，再看看 其他商品吧～
            new ConfirmDialog(activity, "订单中的商品都失效了，再看看 其他商品吧～", new ConfirmOKI() {
                @Override
                public void executeOk() {

                }

                @Override
                public void executeCancel() {

                }
            });
            return;
        }
        if (shixiaoList.size() > 0) {
            //部分商品失效
            new OrderDownProductDialog(activity, shixiaoList, new ConfirmOKI() {
                @Override
                public void executeOk() {
                    presenter.reqChangeCart(tradeSkuVOList);
                }

                @Override
                public void executeCancel() {

                }
            });
            return;
        }

        presenter.reqChangeCart(tradeSkuVOList);

    }


    public void updateTime() {
        if (orderAdapter != null) {
            for (OrderContent orderContent : orderAdapter.getOrderContents()) {
                if (orderContent instanceof OrderItemBottom) {
                    ((OrderItemBottom) orderContent).setTime();
                }
            }
        }
    }


    private List<OrderContent> getOrderContents(List<OrderBean> orderBeanList) {
        List<OrderContent> orderContents = new ArrayList<OrderContent>();
        if (orderBeanList == null) {
            orderBeanList = new ArrayList<OrderBean>();
        }
        for (int i = 0; i < orderBeanList.size(); i++) {
            OrderBean orderBean = orderBeanList.get(i);
            //头
            OrderItemTop top = new OrderItemTop(orderBean);
            orderContents.add(top);

            int totalnum = 0;//每个sku购买数量之和
            //商品信息
            if (!orderBean.isIfHideBuy()) {
                //非匿名
                if(orderBean.getGoodsList()!=null){
                    for (int j = 0; j < orderBean.getGoodsList().size(); j++) {
                        OrderGoodBean orderGoodBean = orderBean.getGoodsList().get(j);
                        OrderItemGoods content = new OrderItemGoods(orderBean, orderGoodBean, orderBean.getHideCount());
                        orderContents.add(content);
                        totalnum += orderGoodBean.getSellCount();
                        if (j == orderBean.getGoodsList().size() - 1) {
                            content.setLast(true);
                        }
                    }
                }

            } else {
                //匿名
                OrderItemGoods content = new OrderItemGoods(orderBean, null, orderBean.getHideCount());
                orderContents.add(content);
                totalnum = orderBean.getHideCount();
                content.setLast(true);
            }

            //底
            OrderItemBottom bottom = new OrderItemBottom(orderBean, totalnum, status, orderType, presenter);
            orderContents.add(bottom);
        }
        return orderContents;
    }

    public void setOrderI(OrderI orderI) {
        this.orderI = orderI;
    }


    public void onActivityFragmentResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == activity.RESULT_OK) {
//            if(requestCode== SafeImgPwdActivity.REQ_SAFEIMG){
//                if(data!=null){
////                String url=data.getStringExtra("url");
//                    String tag=data.getStringExtra("tag");
//                    presenter.model.notifyData(tag);
//                }
//            }
        }
    }

}
