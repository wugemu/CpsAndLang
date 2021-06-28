package com.lxkj.dmhw.fragment.self;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.test.andlang.util.ActivityUtil;
import com.example.test.andlang.util.ButtonUtil;
import com.example.test.andlang.util.ToastUtil;
import com.example.test.andlang.util.imageload.ImageLoadUtils;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.activity.WebViewActivity;
import com.lxkj.dmhw.activity.self.SettlementActivity;
import com.lxkj.dmhw.adapter.self.CouponGetAdapter;
import com.lxkj.dmhw.adapter.self.cart.CartGoodsSeparate;
import com.lxkj.dmhw.adapter.self.cart.CartInvalidTop;
import com.lxkj.dmhw.adapter.self.cart.CartItemGoods;
import com.lxkj.dmhw.adapter.self.cart.CartItemTop;
import com.lxkj.dmhw.adapter.self.cart.CartListAdapter;
import com.lxkj.dmhw.adapter.self.cart.CartResultSeparate;
import com.lxkj.dmhw.adapter.self.cart.OrderContent;
import com.lxkj.dmhw.bean.self.CarCouponPolicyBean;
import com.lxkj.dmhw.bean.self.CartResult;
import com.lxkj.dmhw.bean.self.CartResultNewBean;
import com.lxkj.dmhw.bean.self.GoodSku;
import com.lxkj.dmhw.bean.self.TopNotifyBean;
import com.lxkj.dmhw.bean.self.TradeGoodsCar;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.logic.Constants;
import com.lxkj.dmhw.model.CartModel;
import com.lxkj.dmhw.myinterface.ConfirmOKI;
import com.lxkj.dmhw.presenter.CartPresenter;
import com.lxkj.dmhw.utils.BBCUtil;
import com.lxkj.dmhw.utils.SDJumpUtil;
import com.lxkj.dmhw.widget.dialog.ConfirmDialog;
import com.lxkj.dmhw.widget.dialog.GetCouponDialog;
import com.lxkj.dmhw.widget.swipe.SwipeListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lenovo on 2018/9/20.
 */

public class CartFragment extends BaseFragment implements ConfirmOKI {
    @BindView(R.id.lv_carts)
    SwipeListView lvCarts;
    @BindView(R.id.lang_tv_title)
    TextView langTvTitle;
    @BindView(R.id.lang_ll_back)
    LinearLayout langLlBack;
    @BindView(R.id.lang_tv_right)
    TextView langTvRight;
    @BindView(R.id.ll_cart_info)
    LinearLayout llCartInfo;
    @BindView(R.id.tv_sum_price)
    TextView tvSumPrice;
    @BindView(R.id.tv_discount_fee)
    TextView tv_discount_fee;
    @BindView(R.id.tv_tax_fee)
    TextView tvTaxFee;
    @BindView(R.id.tv_settlement)
    TextView tvSettlement;
    @BindView(R.id.tv_delete_all)
    TextView tvDeleteAll;
    @BindView(R.id.cb_check_all)
    CheckBox cbCheckAll;
    @BindView(R.id.ll_check_all)
    LinearLayout llCheckall;
    @BindView(R.id.rl_bottom)
    LinearLayout rl_bottom;
    @BindView(R.id.ll_tip)
    LinearLayout ll_tip;

    @BindView(R.id.rl_nodata)
    RelativeLayout rl_nodata;
    @BindView(R.id.iv_no_data)
    ImageView iv_no_data;
    @BindView(R.id.tv_no_data)
    TextView tv_no_data;
    @BindView(R.id.tv_nodata_reload)
    TextView tv_nodata_reload;

    private CartListAdapter adapter;
    private TopNotifyBean shareBean;
    private boolean isEdit;
    private boolean checkAllFlag = true;
    private int delFlag;//0=删除购物车商品，1=清空失效商品
    private ImageView headView;
    private ArrayList<GoodSku> buySkuList;//购买商品列表

    private CartResultNewBean cartResultNewBean;
    private GetCouponDialog getCouponDialog;
    private Map<String,Boolean> canCouponMap;
    private CartPresenter presenter;
    private boolean ifCanBack;

    @Override
    public void onData() {

    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, null);
        ButterKnife.bind(this, view);
        return view;
    }
    @Override
    public void onEvent() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            ifCanBack = bundle.getBoolean("ifCanBack");
        }

        initView();
        if(ifCanBack){
            langLlBack.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onCustomized() {
        presenter=new CartPresenter(null,null, CartModel.class);
        refreshData();
    }

    @Override
    public void mainMessage(Message message) {

    }
    @Override
    public void childMessage(Message message) {

    }
    @Override
    public void handlerMessage(Message message) {
        String arg=(String)message.obj;
        if ("reqInvalidCartList".equals(arg)) {
            if (presenter.model.getInvalidList() != null && presenter.model.getInvalidList().size() > 0) {
                rl_nodata.setVisibility(View.GONE);
                if (adapter == null) {
                    adapter = new CartListAdapter(getActivity(), getInvalidContents(parseInvalidList(presenter.model.getInvalidList()).get(0), true), handler);
                    if (shareBean != null && shareBean.getIfShow() == 1) {
                        initHeadView();
                    }

                    lvCarts.setAdapter(adapter);
                    setLabel();
                } else {
                    setLabel();
                    boolean flag = false;
                    if(adapter==null||adapter.getOrderContents()==null){
                        return;
                    }
                    for (OrderContent orderContent : adapter.getOrderContents()) {
                        if (orderContent instanceof CartInvalidTop) {
                            flag = true;
                        }
                    }
                    if (flag) {
                        adapter.addList(addOrderContents(parseInvalidList(presenter.model.getInvalidList()).get(0)));
                    } else {
                        adapter.addList(getInvalidContents(parseInvalidList(presenter.model.getInvalidList()).get(0), false));
                    }

                }
            } else if (adapter == null || presenter.model.getCartList() == null || presenter.model.getCartList().size() == 0) {//显示空购物车
                rl_bottom.setVisibility(View.GONE);
                ll_tip.setVisibility(View.GONE);
                rl_nodata.setVisibility(View.VISIBLE);
                tv_no_data.setText("购物车里空空如也，快去逛逛吧~");
                iv_no_data.setImageResource(R.mipmap.cart_empty);
                tv_no_data.setVisibility(View.VISIBLE);
                tv_nodata_reload.setText("去逛逛");
                tv_nodata_reload.setTextColor(getActivity().getResources().getColor(R.color.colorRedMain));
                tv_nodata_reload.setBackgroundResource(R.drawable.bg_rect_stroke_red17);
                tv_nodata_reload.setVisibility(View.VISIBLE);

            }
        } else if ("reqCartList".equals(arg)) {//加载购物车数据
            //无效的商品初始化加载更多
            presenter.page=1;
            presenter.haveMore=true;

            cartResultNewBean=presenter.model.getCartResultNewBean();

            if(cartResultNewBean!=null){
                tvTaxFee.setText("税费预计:¥" + cartResultNewBean.getTotalGoodsTaxAmount());
                tvSumPrice.setText("¥" + cartResultNewBean.getShopCarTotalAmount());
                if(cartResultNewBean.isIfShowDiscountAmount()) {
                    tv_discount_fee.setVisibility(View.VISIBLE);
                    tv_discount_fee.setText("活动已减:¥" + cartResultNewBean.getDiscountAmount());
                }else {
                    tv_discount_fee.setVisibility(View.GONE);
                }
            }

            List<OrderContent> orderContents = new ArrayList<>();
            if (presenter.model.getCartList() != null && presenter.model.getCartList().size() > 0) {
                rl_bottom.setVisibility(View.VISIBLE);
                ll_tip.setVisibility(View.VISIBLE);
                langTvRight.setVisibility(View.VISIBLE);
                orderContents.addAll(getOrderContents(presenter.model.getCartList()));
                if (adapter == null) {
                    adapter = new CartListAdapter(getActivity(), orderContents, handler);
                    if (shareBean != null && shareBean.getIfShow() == 1) {
                        initHeadView();
                    }
                    lvCarts.setAdapter(adapter);
                } else {
                    adapter.upateList(orderContents);
                }
                setLabel();
            }else {
                rl_bottom.setVisibility(View.GONE);
                ll_tip.setVisibility(View.GONE);
                langTvRight.setVisibility(View.GONE);
            }
        } else if ("reqCartListEmpty".equals(arg)) {
            if(adapter!=null&&adapter.getOrderContents()!=null){
                adapter.getOrderContents().clear();
                adapter=null;
            }
            rl_bottom.setVisibility(View.GONE);
            ll_tip.setVisibility(View.GONE);
            langTvRight.setVisibility(View.GONE);
            presenter.reqInvalidCartList(getActivity(),reqHandler,false);
        } else if ("reqCartLogisticsInfo".equals(arg)) {
            shareBean = presenter.model.getInfo();
            presenter.reqCartList(getActivity(),reqHandler,"","");
        } else if("reqCartLogisticsInfoError".equals(arg)){
            //获取头部物流信息失败
            presenter.reqCartList(getActivity(),reqHandler,"","");
        } else if ("reqClearInvalidList".equals(arg)) {
//            ((HomeActivity) activity).onResume();
            refreshData();
        } else if ("reqDeleteCart".equals(arg)) {
            ToastUtil.show(getContext(), "删除成功");
//            ((HomeActivity) activity).onResume();
//            refreshData();
            showDialog();
            presenter.reqCartList(getActivity(),reqHandler,"","");
        } else if ("reqChangeCart".equals(arg)) {
            if(adapter==null||adapter.getOrderContents()==null){
                return;
            }
            for (OrderContent orderContent : adapter.getOrderContents()) {
                if (orderContent instanceof CartItemGoods && ((CartItemGoods) orderContent).getTradeGoodsCar().getCartId() == presenter.model.getCar().getCartId()) {
                    TradeGoodsCar cart = ((CartItemGoods) orderContent).getTradeGoodsCar();
                    cart.setNum(cart.getNum() + presenter.model.getChangeNum());
                }
            }
            adapter.notifyDataSetChanged();
            setLabel();

            //修改商品数量 让接口不走领券逻辑
            String cancelPickOnShopCarIds="";
            String pickOnShopCarIds="";
            String cartId=presenter.model.getCar().getCartId()+"";
            if(presenter.model.getCar().isIfPickOn()){
                pickOnShopCarIds=cartId;
            }else {
                cancelPickOnShopCarIds=cartId;
            }
            lvCarts.post(new Runnable() {
                @Override
                public void run() {
                    showDialog();
                }
            });
            presenter.reqCartList(getActivity(),reqHandler,cancelPickOnShopCarIds,pickOnShopCarIds);
        }else if("reqCheckStock".equals(arg)){
            //库存校验成功
            Intent intent = new Intent(getActivity(), SettlementActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("skuList", buySkuList);
            intent.putExtras(bundle);
            ActivityUtil.getInstance().start(getActivity(), intent);
        }else if("reqCanGetCouponList".equals(arg)){
            getCouponDialog = new GetCouponDialog(getActivity(), presenter.model.getCouponList(), handler);
        }else if ("reqGetCoupon".equals(arg)) {
            ToastUtil.show(getActivity(), "领取成功");
            getCouponDialog.refershData(presenter.model.getCouponId());
        }
        dismissDialog();
    }

    public void initView() {
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(timeReceiver, new IntentFilter(Constants.TIME_TASK));
        langTvTitle.setText("购物车");
        langLlBack.setVisibility(View.GONE);
        llCheckall.setOnClickListener(cbCheckAllListener);
        cbCheckAll.setOnClickListener(cbCheckAllListener);
        cbCheckAll.setChecked(checkAllFlag);
        lvCarts.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                if (scrollState == 1) {
//                    for (OrderContent orderContent : adapter.getOrderContents()) {
//                        if (orderContent instanceof CartItemGoods) {
//                            ((CartItemGoods) orderContent).setRefershImage(true);
//                        }
//                    }
//                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        checkEdit();
        tv_discount_fee.setVisibility(View.GONE);
    }

    private Handler reqHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            handlerMessage(msg);
            super.handleMessage(msg);
        }
    };

    private BroadcastReceiver timeReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (adapter!=null&&adapter.getOrderContents()!=null){
                for (OrderContent orderContent:adapter.getOrderContents()){
                    if (orderContent instanceof CartItemGoods) {
                        ((CartItemGoods) orderContent).setTime(getContext());
                    }
                }
            }
        }
    };
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(timeReceiver);
    }

    private void checkEdit() {
        if (isEdit) {
            llCartInfo.setVisibility(View.INVISIBLE);
            tvDeleteAll.setVisibility(View.VISIBLE);
            tvSettlement.setVisibility(View.GONE);
            langTvRight.setText("完成");
        } else {
            langTvRight.setText("编辑");
            tvSettlement.setVisibility(View.VISIBLE);
            tvDeleteAll.setVisibility(View.GONE);
            llCartInfo.setVisibility(View.VISIBLE);
        }
    }

    private View.OnClickListener cbCheckAllListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            checkAllFlag = !checkAllFlag;
            cbCheckAll.setChecked(checkAllFlag);
            checkAll(checkAllFlag);

        }
    };

    private void checkAll(boolean isCheck) {
        String cancelPickOnShopCarIds="";
        String pickOnShopCarIds="";
        String cartId="";
        StringBuffer cartsIdBuffer=new StringBuffer("");
        if(adapter==null||adapter.getOrderContents()==null){
            return;
        }
        for (OrderContent orderContent : adapter.getOrderContents()) {
            if (orderContent instanceof CartItemGoods) {
                ((CartItemGoods) orderContent).getTradeGoodsCar().setIfPickOn(isCheck);
                cartsIdBuffer.append(((CartItemGoods) orderContent).getTradeGoodsCar().getCartId());
                cartsIdBuffer.append(",");
            }
            if (orderContent instanceof CartItemTop) {
                ((CartItemTop) orderContent).setCheckAll(isCheck);
            }
        }
        adapter.notifyDataSetChanged();
        setLabel();

        cartId=cartsIdBuffer.toString();
        if(!BBCUtil.isEmpty(cartId)){
            cartId=cartId.substring(0,cartId.length()-1);
        }
        if(isCheck){
            pickOnShopCarIds=cartId;
        }else {
            cancelPickOnShopCarIds=cartId;
        }

//        showDialog();
//        presenter.reqCartList(getActivity(),reqHandler,cancelPickOnShopCarIds,pickOnShopCarIds);
    }

    private void isCheckAll(Integer deliveryType, boolean isCheck) {
        if(adapter==null||adapter.getOrderContents()==null){
            return;
        }
        boolean flag = true;
        for (OrderContent orderContent : adapter.getOrderContents()) {
            if (orderContent instanceof CartItemTop) {
                if (!"0".equals(((CartItemTop) orderContent).getCartResult().getDeliveryType())) {
                    if (((CartItemTop) orderContent).getCartResult().getDeliveryType().equals(deliveryType.toString())) {
                        ((CartItemTop) orderContent).setCheckAll(isCheck);
                    }
                    if (!isCheck || !flag || !((CartItemTop) orderContent).isCheckAll()) {
                        flag = false;
                    }
                }
            }
//            if (orderContent instanceof CartItemGoods) {
//                ((CartItemGoods) orderContent).setRefershImage(false);
//            }
        }
        adapter.notifyDataSetChanged();
        cbCheckAll.setChecked(flag);
        checkAllFlag = isCheck;
    }

//    private List<CartResult> parseNomalList(List<TradeGoodsCar> carList, String deliveryName) {
//        List<CartResult> cartResults = new ArrayList<>();
//        CartResult cartResult = new CartResult();
//        cartResult.setDeliveryType(carList.get(0).getDeliveryType());
//        cartResult.setWarehouseName(deliveryName);
//        List<TradeGoodsCar> dotList = new ArrayList<>();
//        cartResult.setShopCarDtos(dotList);
//        cartResults.add(cartResult);
//        for (TradeGoodsCar car : carList) {
//            cartResult.getShopCarDtos().add(car);
//        }
//        return cartResults;
//    }

    private List<CartResult> parseInvalidList(List<TradeGoodsCar> carList) {
        List<CartResult> cartResults = new ArrayList<>();
        if (adapter != null && adapter.getCount() > 0) {
            if (adapter.getLastContent() != null) {//
                String deliveryType = adapter.getLastContent().getDeliveryType();
                if (!"0".equals(deliveryType)) {//未创建过失效仓
                    CartResult cartResult = new CartResult();
                    cartResult.setDeliveryType("0");
                    cartResult.setShopCarDtos(carList);
                    cartResults.add(cartResult);
                } else {
                    adapter.getLastContent().getCartResult().setShopCarDtos(carList);
                    cartResults.add(adapter.getLastContent().getCartResult());
                }
            }
        } else {
            CartResult cartResult = new CartResult();
            cartResult.setDeliveryType("0");
            cartResult.setShopCarDtos(carList);
            cartResults.add(cartResult);
        }
        return cartResults;
    }

    private List<OrderContent> addOrderContents(CartResult cartResult) {
        List<OrderContent> orderContents = new ArrayList<>();
        if(cartResult.getShopCarDtos()!=null) {
            for (TradeGoodsCar goods : cartResult.getShopCarDtos()) {
//                goods.setIfPickOn(true);
                CartItemGoods content = new CartItemGoods(cartResult, goods, handler);
                orderContents.add(content);
            }
        }

        return orderContents;
    }

    private List<OrderContent> getOrderContents(List<CartResult> cartList) {
        checkAllFlag = true;
        List<OrderContent> orderContents = new ArrayList<>();
        for (int i = 0; i < cartList.size(); i++) {
            if (i > 0) {//第一个仓头部不需要添加分割线
                CartResultSeparate cartResultSeparate = new CartResultSeparate();
                orderContents.add(cartResultSeparate);
            }
            CartResult cartResult = cartList.get(i);
            cartResult.setDeliveryType("" + (i + 1));

            if(canCouponMap==null){
                canCouponMap=new HashMap<>();
            }
            if(presenter.model.isIfUseInterface()){
                canCouponMap.put(cartResult.getWarehouseName(),cartResult.isIfCanReceiveCoupon());
            }else {
                Boolean ifCanReceiveCoupon=canCouponMap.get(cartResult.getWarehouseName());
                if(ifCanReceiveCoupon!=null){
                    cartResult.setIfCanReceiveCoupon(ifCanReceiveCoupon);
                }
            }

            CartItemTop top = new CartItemTop(cartResult, handler);
            orderContents.add(top);

            //活动商品列表数据处理
            if(cartResult.getShopCarDtos()==null){
                cartResult.setShopCarDtos(new ArrayList<>());
            }else {
                cartResult.getShopCarDtos().clear();
            }
            if(cartResult.getShopCarCouponPolicys()!=null){
                for (CarCouponPolicyBean carCouponPolicyBean:cartResult.getShopCarCouponPolicys()){
                    if(carCouponPolicyBean.getShopCarDtos()!=null){
                        for (int j=0;j<carCouponPolicyBean.getShopCarDtos().size();j++){
                            TradeGoodsCar goodsCar=carCouponPolicyBean.getShopCarDtos().get(j);
                            if(j==0){
                                goodsCar.setCouponPolicyTag(carCouponPolicyBean.getCouponPolicyTag());
                                goodsCar.setReachTitle(carCouponPolicyBean.getReachTitle());
                                goodsCar.setCouponPolicyId(carCouponPolicyBean.getCouponPolicyId());
                                goodsCar.setCouponPolicyName(carCouponPolicyBean.getCouponPolicyName());
                            }
                            goodsCar.setReachState(carCouponPolicyBean.getReachState());
                            if(carCouponPolicyBean.getReachState()==0||(carCouponPolicyBean.getReachState()!=0&&j==carCouponPolicyBean.getShopCarDtos().size()-1)){
                                goodsCar.setIfShowLine(true);
                            }else {
                                goodsCar.setIfShowLine(false);
                            }
                        }
                        cartResult.getShopCarDtos().addAll(carCouponPolicyBean.getShopCarDtos());
                    }
                }
            }


            boolean isTopCheck=true;
            if(cartResult.getShopCarDtos()!=null) {
                for (TradeGoodsCar goods : cartResult.getShopCarDtos()) {
//                    goods.setIfPickOn(true);
                    CartItemGoods content = new CartItemGoods(cartResult, goods, handler);
                    orderContents.add(content);
                    if (goods.getTaxRate() == 0 && goods.isIfShowLine()) {
                        //没有活动或活动最后一个商品 有分割线
                        CartGoodsSeparate cartGoodsSeparate = new CartGoodsSeparate();
                        orderContents.add(cartGoodsSeparate);
                    }
                    if(!goods.isIfPickOn()){
                        isTopCheck=false;
                        checkAllFlag=false;
                    }
                }
            }
            //设置仓库是否全选
            top.setCheckAll(isTopCheck);

            if (orderContents.get(orderContents.size() - 1) instanceof CartGoodsSeparate) {
                orderContents.remove(orderContents.size() - 1);//每个仓最后一个商品不需要分割线
            }
        }

        //设置底部全部 是否全选
        cbCheckAll.setChecked(checkAllFlag);


        return orderContents;
    }

    private List<OrderContent> getInvalidContents(CartResult cartResult, boolean isFirst) {
        List<OrderContent> orderContents = new ArrayList<>();
        if (!isFirst) {//第一个仓头部不需要添加分割线
            CartResultSeparate cartResultSeparate = new CartResultSeparate();
            orderContents.add(cartResultSeparate);
        }
        CartInvalidTop top = new CartInvalidTop(handler);
        top.setTotal(presenter.model.getInvalidCount());
        orderContents.add(top);
        if(cartResult.getShopCarDtos()!=null) {
            for (TradeGoodsCar goods : cartResult.getShopCarDtos()) {
//                goods.setIfPickOn(true);
                CartItemGoods content = new CartItemGoods(cartResult, goods, handler);
                orderContents.add(content);

                CartGoodsSeparate cartGoodsSeparate = new CartGoodsSeparate();
                orderContents.add(cartGoodsSeparate);

            }
        }
        if (orderContents.get(orderContents.size() - 1) instanceof CartGoodsSeparate) {
            orderContents.remove(orderContents.size() - 1);//每个仓最后一个商品不需要分割线
        }
        return orderContents;
    }

    @OnClick(R.id.tv_nodata_reload)
    public void goHome(){
        //去逛逛
//        if(ActivityUtil.getInstance().isTopActivity(HomeActivity.class)){
//            HomeActivity act=(HomeActivity) ActivityUtil.getInstance().getLastActivity();
//            act.click(1);
//        }else {
//            SDJumpUtil.goHomeActivity(activity,HomeActivity.GO_HOME);
//        }
        SDJumpUtil.goHomeActivity(getActivity(),0);
    }

    private void initHeadView() {
        if (lvCarts.getHeaderViewsCount() == 0) {
            headView = new ImageView(getContext());
            int w = BBCUtil.getDisplayWidth(getActivity());
            int h = (int) (w * 60.0 / 375);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(w, h);
            headView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            headView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (shareBean != null && !BBCUtil.isEmpty(shareBean.getLinkUrl())) {
                        //跳转到H5页面
                        Intent intent = new Intent(getActivity(), WebViewActivity.class);
                        intent.putExtra(Variable.webUrl, shareBean.getLinkUrl());
                        ActivityUtil.getInstance().start(getActivity(), intent);
                    }
                }
            });
            ImageLoadUtils.doLoadImageUrl(headView, shareBean.getPicUrl());
            headView.setLayoutParams(params);
            lvCarts.addHeaderView(headView);

        } else {
            if (headView != null) {
                ImageLoadUtils.doLoadImageUrl(headView, shareBean.getPicUrl());
            }
        }

    }

    @OnClick(R.id.lang_tv_right)
    public void onViewClicked() {
        isEdit = !isEdit;
        checkEdit();
    }


    @OnClick({R.id.tv_settlement, R.id.tv_delete_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_settlement://结算
                if(adapter==null||adapter.getOrderContents()==null){
                    return;
                }
                buySkuList = new ArrayList<GoodSku>();
                for (OrderContent orderContent : adapter.getOrderContents()) {
                    if (orderContent instanceof CartItemGoods) {
                        TradeGoodsCar cartItemGoods = ((CartItemGoods) orderContent).getTradeGoodsCar();
                        if (!"0".equals(((CartItemGoods) orderContent).getCartResult().getDeliveryType()) && cartItemGoods.isIfPickOn()) {
                            GoodSku sku = new GoodSku();
                            sku.setCartId(cartItemGoods.getCartId());
                            sku.setSkuId(cartItemGoods.getSkuId());
                            sku.setVideoId(cartItemGoods.getVideoId());
                            sku.setNum(cartItemGoods.getNum());
                            sku.setWithinBuyId(cartItemGoods.getWithinBuyId());
                            buySkuList.add(sku);
                        }
                    }
                }
                if (buySkuList.size() == 0) {
                    ToastUtil.show(getActivity(), "请选择要购买的商品");
                    return;
                }
                //库存校验
                showDialog();
                presenter.reqCheckStock(getActivity(),reqHandler,buySkuList);
                break;
            case R.id.tv_delete_all://删除
                if(adapter==null||adapter.getOrderContents()==null){
                    return;
                }
                for (OrderContent orderContent : adapter.getOrderContents()) {
                    if (orderContent instanceof CartItemGoods) {
                        TradeGoodsCar cartItemGoods = ((CartItemGoods) orderContent).getTradeGoodsCar();
                        if (cartItemGoods.isIfPickOn()&&!"0".equals(((CartItemGoods) orderContent).getCartResult().getDeliveryType())) {
                            delFlag = 0;
                            new ConfirmDialog(getActivity(), "确认要删除所选商品吗？", this);
                            return;
                        }
                    }
                }
                ToastUtil.show(getActivity(), "请选择要删除的商品");

                break;
        }
    }

    private void setLabel() {
        double sumprice = 0d;
        int sumcount = 0;
        double sumTaxFee = 0d;
        boolean isNotCheck = true;
        if(adapter==null||adapter.getOrderContents()==null){
            return;
        }
        for (OrderContent orderContent : adapter.getOrderContents()) {
            if (orderContent instanceof CartItemTop) {
                sumcount += ((CartItemTop) orderContent).getTotal();
                sumprice += ((CartItemTop) orderContent).getAllFee();
                sumTaxFee += ((CartItemTop) orderContent).getAllTaxFee();
            }
            if (orderContent instanceof CartItemGoods) {
//                ((CartItemGoods) orderContent).setRefershImage(isRefersh);
                if (((CartItemGoods) orderContent).getTradeGoodsCar().isIfPickOn()) {
                    isNotCheck = false;
                }
            }
        }

        if (sumcount > 999) {
            tvSettlement.setText("结算(999+)");
        } else {
            tvSettlement.setText("结算(" + sumcount + ")");
        }
        if (isNotCheck) {
            tvDeleteAll.setBackgroundColor(getResources().getColor(R.color.colorBlackText2));
        } else {
//            tvDeleteAll.setBackgroundColor(getResources().getColor(R.color.colorBlackText));
            tvDeleteAll.setBackgroundResource(R.drawable.bg_btn_gradient);
        }
        tvDeleteAll.setText("删除所选");
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int positon;
            String cancelPickOnShopCarIds="";
            String pickOnShopCarIds="";
            String cartId="";
            super.handleMessage(msg);
            switch (msg.what) {
                case 11:
                    showDialog();
                    presenter.reqCartList(getActivity(),reqHandler,"","");
                    break;
                case 10://无效商品加载
                    if (presenter.haveMore) {
                        presenter.reqInvalidCartList(getActivity(),reqHandler,true);
                    }
                    break;

                case 1://删除购物车商品
                    showDialog();
                    presenter.reqDeleteCart(getActivity(),reqHandler,msg.obj.toString());
                    break;
                case 2://清空失效商品
                    delFlag = 1;
                    new ConfirmDialog(getActivity(), "确认要清空失效商品吗？", CartFragment.this);
                    break;
                case 3://修改商品数量
                    presenter.model.setCar((TradeGoodsCar) msg.obj);
                    presenter.model.setChangeNum(msg.arg2);
                    presenter.reqChangeCart(getActivity(),reqHandler);
                    break;
                case 4:
                    //单仓全选反选操作
                    if(adapter==null||adapter.getOrderContents()==null){
                        return;
                    }

                    positon=msg.arg1-1;
                    cartId = getCartIdFromPosition(positon);
                    if(!BBCUtil.isEmpty(cartId)){
                        cartId=cartId.substring(0,cartId.length()-1);
                    }

                    for (OrderContent orderContent : adapter.getOrderContents()) {
                        if (orderContent instanceof CartItemGoods && ((CartItemGoods) orderContent).getCartResult().getDeliveryType().equals(String.valueOf(msg.arg1))) {
                            ((CartItemGoods) orderContent).getTradeGoodsCar().setIfPickOn((boolean) msg.obj);
//                            ((CartItemGoods) orderContent).setRefershImage(false);
                        }
                    }
                    if ((boolean) msg.obj) {
                        pickOnShopCarIds=cartId;
                        boolean flag = true;
                        for (OrderContent orderContent : adapter.getOrderContents()) {
                            if (orderContent instanceof CartItemTop && !((CartItemTop) orderContent).getCartResult().getDeliveryType().equals(String.valueOf(msg.arg1))) {
                                if (!((CartItemTop) orderContent).isCheckAll()) {
                                    flag = false;
                                    break;
                                }
                            }
                        }
                        if (flag) {
                            cbCheckAll.setChecked(true);
                            checkAllFlag = true;
                        } else {
                            cbCheckAll.setChecked(false);
                            checkAllFlag = false;
                        }

                    } else {
                        cancelPickOnShopCarIds=cartId;
                        cbCheckAll.setChecked(false);
                        checkAllFlag = false;
                    }
                    setLabel();
                    adapter.notifyDataSetChanged();
                    showDialog();
                    presenter.reqCartList(getActivity(),reqHandler,cancelPickOnShopCarIds,pickOnShopCarIds);
                    break;
                case 5://单个商品选中操作
                    Bundle bundle=msg.getData();
                    if(bundle!=null){
                        cartId=bundle.getString("cartId");
                    }
                    if ((boolean) msg.obj) {
                        pickOnShopCarIds=cartId;
                        boolean flag = true;
                        if(adapter==null||adapter.getOrderContents()==null){
                            return;
                        }
                        for (OrderContent orderContent : adapter.getOrderContents()) {
                            if (orderContent instanceof CartItemGoods) {
                                if (((CartItemGoods) orderContent).getCartResult().getDeliveryType().equals(String.valueOf(msg.arg1)) && !((CartItemGoods) orderContent).isCheck()) {
                                    flag = false;
                                    break;
                                }
                            }
                        }
                        isCheckAll(msg.arg1, flag);
                    } else {
                        cancelPickOnShopCarIds=cartId;
                        isCheckAll(msg.arg1, false);
                    }
                    setLabel();
//                    showDialog();
//                    presenter.reqCartList(getActivity(),reqHandler,cancelPickOnShopCarIds,pickOnShopCarIds);
                    break;
                case 6:
                    //领券
                    positon=msg.arg1-1;
                    String goodsId = getGoodsIdFromPosition(positon);
                    if(!BBCUtil.isEmpty(goodsId)){
                        goodsId=goodsId.substring(0,goodsId.length()-1);
                    }
                    showDialog();
                    presenter.reqCanGetCouponList(goodsId);
                    break;
                case CouponGetAdapter.HANDLER_CODE_GET://领取优惠券
                    if (ButtonUtil.isFastDoubleClick(CouponGetAdapter.HANDLER_CODE_GET)) {
                        ToastUtil.show(getActivity(), R.string.tip_btn_fast);
                        return;
                    }
                    presenter.model.setCouponId(msg.obj.toString());
                    presenter.reqGetCoupon(msg.obj.toString());

                    break;
                case CouponGetAdapter.HANDLER_CODE_USERGOHOME:
                    //优惠券券去使用 全场通用类型
                    //去逛逛
                    if(getCouponDialog!=null){
                        getCouponDialog.cancelDialog();
                    }
                    SDJumpUtil.goHomeActivity(getActivity(),0);
                    break;

            }

        }
    };

    @Override
    public void executeOk() {
        if (delFlag == 0) {
            if(adapter==null||adapter.getOrderContents()==null){
                return;
            }
            StringBuilder sb = new StringBuilder();
            for (OrderContent orderContent : adapter.getOrderContents()) {
                if (orderContent instanceof CartItemGoods) {
                    TradeGoodsCar cartItemGoods = ((CartItemGoods) orderContent).getTradeGoodsCar();
                    if (cartItemGoods.isIfPickOn()&&!"0".equals(((CartItemGoods) orderContent).getCartResult().getDeliveryType())) {
                        sb.append(((CartItemGoods) orderContent).getTradeGoodsCar().getCartId());
                        sb.append(",");
                    }
                }
            }
            sb.deleteCharAt(sb.length() - 1);
            presenter.reqDeleteCart(getActivity(),reqHandler,sb.toString());
        } else {
            showDialog();
            presenter.reqClearInvalidList(getActivity(),reqHandler);
        }
    }

    @Override
    public void executeCancel() {

    }

    public void refreshData() {
        if (presenter != null && getActivity() != null) {
            if(adapter!=null&&adapter.getOrderContents()!=null) {
                adapter.getOrderContents().clear();
                adapter = null;
            }
            if(langTvRight!=null){
                langTvRight.setVisibility(View.GONE);
            }
            if(rl_bottom!=null) {
                rl_bottom.setVisibility(View.GONE);
            }
            if(ll_tip!=null) {
                ll_tip.setVisibility(View.GONE);
            }
            presenter.page=1;
            presenter.haveMore=true;//删除初始化加载更多
            showDialog();
            presenter.reqCartLogisticsInfo(getActivity(),reqHandler);
        }
    }

    private String getGoodsIdFromPosition(int position) {
        StringBuffer goodsIdBuffer=new StringBuffer("");
        if(cartResultNewBean==null){
            return goodsIdBuffer.toString();
        }
        List<CartResult> shopDotList=cartResultNewBean.getShopDtoList();
        if(shopDotList==null){
            return goodsIdBuffer.toString();
        }
        if(position>=shopDotList.size()){
            return goodsIdBuffer.toString();
        }
        CartResult cartResult=shopDotList.get(position);
        if(cartResult==null){
            return goodsIdBuffer.toString();
        }

        if(cartResult.getShopCarCouponPolicys()!=null){
            for (CarCouponPolicyBean carCouponPolicyBean:cartResult.getShopCarCouponPolicys()){
                if(carCouponPolicyBean.getShopCarDtos()!=null){
                    for (int j=0;j<carCouponPolicyBean.getShopCarDtos().size();j++){
                        TradeGoodsCar goodsCar=carCouponPolicyBean.getShopCarDtos().get(j);
                        goodsIdBuffer.append(goodsCar.getGoodsId());
                        goodsIdBuffer.append(",");
                    }
                }
            }
        }

        return goodsIdBuffer.toString();
    }

    private String getCartIdFromPosition(int position) {
        StringBuffer cartsIdBuffer=new StringBuffer("");
        if(cartResultNewBean==null){
            return cartsIdBuffer.toString();
        }
        List<CartResult> shopDotList=cartResultNewBean.getShopDtoList();
        if(shopDotList==null){
            return cartsIdBuffer.toString();
        }
        if(position>=shopDotList.size()){
            return cartsIdBuffer.toString();
        }
        CartResult cartResult=shopDotList.get(position);
        if(cartResult==null){
            return cartsIdBuffer.toString();
        }

        if(cartResult.getShopCarCouponPolicys()!=null){
            for (CarCouponPolicyBean carCouponPolicyBean:cartResult.getShopCarCouponPolicys()){
                if(carCouponPolicyBean.getShopCarDtos()!=null){
                    for (int j=0;j<carCouponPolicyBean.getShopCarDtos().size();j++){
                        TradeGoodsCar goodsCar=carCouponPolicyBean.getShopCarDtos().get(j);
                        cartsIdBuffer.append(goodsCar.getCartId());
                        cartsIdBuffer.append(",");
                    }
                }
            }
        }

        return cartsIdBuffer.toString();
    }

    @OnClick(R.id.lang_ll_back)
    protected void goBack() {
        ActivityUtil.getInstance().exit(getActivity());
    }
}
