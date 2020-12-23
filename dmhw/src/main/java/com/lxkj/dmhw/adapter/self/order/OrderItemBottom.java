package com.lxkj.dmhw.adapter.self.order;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.test.andlang.util.ActivityUtil;
import com.example.test.andlang.util.BaseLangUtil;
import com.example.test.andlang.util.ButtonUtil;
import com.example.test.andlang.util.DateUtil;
import com.example.test.andlang.util.TimeCalculate;
import com.example.test.andlang.util.ToastUtil;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.self.AddrListActivity;
import com.lxkj.dmhw.activity.self.order.AnonymousOrderActivity;
import com.lxkj.dmhw.activity.self.order.LogisticsDetailActivity;
import com.lxkj.dmhw.activity.self.order.OrderDetialActivity;
import com.lxkj.dmhw.activity.self.order.OrderListActivity;
import com.lxkj.dmhw.adapter.self.cart.OrderContent;
import com.lxkj.dmhw.bean.self.GoodBuyStateBean;
import com.lxkj.dmhw.bean.self.OrderBean;
import com.lxkj.dmhw.bean.self.OrderGoodBean;
import com.lxkj.dmhw.myinterface.ConfirmOKI;
import com.lxkj.dmhw.presenter.OrderPresenter;
import com.lxkj.dmhw.utils.BBCUtil;
import com.lxkj.dmhw.widget.dialog.ConfirmDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 1 on 2017/4/18.
 */

public class OrderItemBottom implements OrderContent {

    private Map<String, ViewHolder> holderMap;
    private OrderBean orderBean;
    private int totalnum;
    private OrderPresenter presenter;
    private int status;//1全部 2待付款 3待发货 4待收货 5已完成
    private int orderType = 1;//1全部 2我的 3分享

    public OrderItemBottom(OrderBean orderBean, int totalnum, int status, int orderType, OrderPresenter presenter) {
        this.orderBean = orderBean;
        this.totalnum = totalnum;
        this.presenter = presenter;
        this.status = status;
        this.orderType = orderType;
        holderMap = new HashMap<String, ViewHolder>();
    }

    @Override
    public int getLayout() {
        return R.layout.child_order_item_bottom;
    }

    @Override
    public boolean isClickable() {
        return true;
    }

    @Override
    public View getView(final Context context, View convertView, LayoutInflater inflater) {
        ViewHolder holder = null;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(getLayout(), null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        holder.order_item_time.setText(orderBean.getRegTimeStr());
        holder.tv_product_num.setText("共" + totalnum + "件");
        holder.tv_yf_tip.setText("应付总额:");
        holder.tv_product_sum_price.setText("¥" + BBCUtil.getDoubleFormat2(orderBean.getPayAmount()));

        //测试
//        orderBean.setHelpTradeType(2);
//        orderBean.setTradeStatus(7);
//        orderBean.setGroupType(2);
//        orderBean.setIfCreateGroup(true);
//        orderBean.setIfShowLogistic(true);
//        orderBean.setHelpTradeStr("还差1任助力");

        if (!orderBean.isIfShareTrade()) {
            //我的订单
            holder.rl_bottom.setVisibility(View.VISIBLE);
            holder.bt_order_left_left.setVisibility(View.GONE);
            holder.bt_order_left.setVisibility(View.VISIBLE);
            holder.bt_order_right.setVisibility(View.VISIBLE);
            holder.bt_order_group_share.setVisibility(View.GONE);
            holder.bt_order_right.setBackgroundResource(R.drawable.bg_rect_red_25dp);
            holder.bt_order_right.setTextColor(context.getResources().getColor(R.color.colorWhite));
            holder.bt_order_right.setTag(orderBean.getTradeNo());
            switch (orderBean.getTradeStatus()) {
                case 0:
                    //交易关闭
                    if (isRefundState1or2()) {
                        //申请售后中不能删除订单
                        holder.bt_order_left.setVisibility(View.GONE);
                    } else {
                        holder.bt_order_left.setText("删除订单");
                        holder.bt_order_left.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //删除订单
                                ConfirmDialog confirmDialog = new ConfirmDialog((Activity) context, "确定要删除此订单吗？", new ConfirmOKI() {
                                    @Override
                                    public void executeOk() {
                                        if (presenter != null) {
                                            presenter.reqDelOrder(orderBean.getTradeId() + "");
                                        }
                                    }

                                    @Override
                                    public void executeCancel() {

                                    }
                                });
                            }
                        });
                    }
                    if (orderBean.getTradeProperty() != 1) {
                        //普通订单
                        if (orderBean.getIfUserRealVip() == 1 || orderBean.getTradeGroupCreateId()>0) {
                            //二次购买礼包订单的普通订单或团购订单
                            holder.bt_order_right.setVisibility(View.GONE);
                        } else {
                            holder.bt_order_right.setVisibility(View.VISIBLE);
                            holder.bt_order_right.setBackgroundResource(R.drawable.bg_rect_stroke_black2_9dp);
                            holder.bt_order_right.setTextColor(context.getResources().getColor(R.color.colorBlackText3));
                            holder.bt_order_right.setText("再次购买");
                            holder.bt_order_right.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //再次购买
                                    List<GoodBuyStateBean> goodBuyStateBeanList=new ArrayList<>();
                                    if(orderBean.getGoodsList()!=null){
                                        for (int j = 0; j < orderBean.getGoodsList().size(); j++) {
                                            OrderGoodBean orderGoodBean = orderBean.getGoodsList().get(j);
                                            GoodBuyStateBean goodBuyStateBean=new GoodBuyStateBean();
                                            goodBuyStateBean.setState(true);
                                            goodBuyStateBean.setNum(orderGoodBean.getSellCount());
                                            goodBuyStateBean.setSkuId(orderGoodBean.getSkuId());
                                            goodBuyStateBeanList.add(goodBuyStateBean);
                                        }
                                    }
                                    presenter.checkOrderBuy(goodBuyStateBeanList);
                                }
                            });
                        }
                    } else {
                        holder.bt_order_right.setVisibility(View.GONE);
                    }

                    //Ver1.0.9 版本助力订单隐藏
                    if (orderBean.getHelpTradeType() == 1 || orderBean.getHelpTradeType() == 2) {
                        // 1下单返现订单 2免费领取订单 隐藏再次购买 和 删除订单
                        holder.bt_order_left.setVisibility(View.GONE);
                        holder.bt_order_right.setVisibility(View.GONE);
                    }
                    break;
                case 1:
                    //等待付款
                    holder.bt_order_left.setText("取消订单");
                    holder.bt_order_left.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ConfirmDialog confirmDialog = new ConfirmDialog((Activity) context, "确定要取消此订单吗？", new ConfirmOKI() {
                                @Override
                                public void executeOk() {
                                    if (presenter != null) {
                                        presenter.reqCancelOrder(orderBean.getTradeId() + "");
                                    }
                                }

                                @Override
                                public void executeCancel() {

                                }
                            });
                        }
                    });
                    String deadline = "";
                    if (orderBean.getLessSecond() > 0) {
                        deadline = TimeCalculate.getTime2(0, orderBean.getLessSecond());
                    }
                    holder.bt_order_right.setText("付款" + deadline);
                    holder.bt_order_right.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //付款
                            if (orderBean.getTradeProperty() == 1) {
                                //礼包订单
                                presenter.model.setPayGift(true);
                            } else {
                                presenter.model.setPayGift(false);
                            }

                            if (orderBean.getTradeGroupCreateId() > 0) {
                                //团购订单
                                presenter.model.setPayGroup(true);
                            } else {
                                presenter.model.setPayGroup(false);
                            }

                            presenter.reqOrderToPay(orderBean.getTradeNo());
                        }
                    });
                    holderMap.put(orderBean.getTradeNo(), holder);//倒计时队列
                    break;
                case 2:
                    //等待审核
                    holder.bt_order_right.setVisibility(View.GONE);
                    if (orderBean.isIfGroupTrade()) {
                        //团订单
                        if (orderBean.getTradeGroupCreate() != null && orderBean.getTradeGroupCreate().isEqualAddress() && !orderBean.getTradeGroupCreate().isIfCreateGroup()) {
                            holder.bt_order_left.setVisibility(View.GONE);//集货参团用户不能修改地址
                        }
                        if (orderBean.isIfUpdateTradeAdr()){
                            //修改过地址了
                            holder.bt_order_left.setVisibility(View.GONE);
                        }
                        holder.bt_order_left.setText("修改地址");
                        holder.bt_order_left.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //修改地址
                                if (orderBean == null) {
                                    return;
                                }
                                if(orderBean.isIfGroupTrade()&& orderBean.isEqualAddress()){
                                    ToastUtil.show(context,"此拼团暂不支持修改地址");
                                    return;
                                }
                                Intent intent = new Intent(context, AddrListActivity.class);
                                intent.putExtra("selectOrderAddr", true);
                                intent.putExtra("tradeId", orderBean.getTradeId() + "");
                                ActivityUtil.getInstance().startResult((Activity)context, intent, OrderListActivity.REQ_UPDATAADDR);
                            }
                        });
                        if (orderBean.getGroupType()==1&&orderBean.getGroupStatus() == 1) {
                            //普通团待成团时显示倒计时
                            holder.bt_order_group_share.setVisibility(View.VISIBLE);
                            String groupdeadline = "";
                            if (orderBean.getLessGroupEndTime() > 0) {
                                orderBean.setLessGroupEndTime(orderBean.getLessGroupEndTime() - 1);
                                groupdeadline = TimeCalculate.getTime3(0, orderBean.getLessGroupEndTime());
                            }
                            holder.bt_order_group_share.setText("邀请好友" + groupdeadline);
                            holder.bt_order_group_share.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //邀请好友
                                    if (presenter != null) {
                                        presenter.reqShareInfo(orderBean.getTradeGroupCreateId() + "", "21");
                                    }

                                }
                            });
                            holderMap.put(orderBean.getTradeNo(), holder);//倒计时队列
                        }
                    } else {
                        holder.bt_order_left.setText("取消订单");
                        holder.bt_order_left.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String tipStr = "确定要取消此订单吗？";
                                if (!BBCUtil.isEmpty(orderBean.getCancelCopywriting())) {
                                    tipStr = orderBean.getCancelCopywriting();
                                }
                                ConfirmDialog confirmDialog = new ConfirmDialog((Activity) context, tipStr, new ConfirmOKI() {
                                    @Override
                                    public void executeOk() {
                                        if (presenter != null) {
                                            presenter.reqCancelOrder(orderBean.getTradeId() + "");
                                        }
                                    }

                                    @Override
                                    public void executeCancel() {

                                    }
                                });
                                if (tipStr.length() > 15) {
                                    //礼包订单
                                    confirmDialog.textLeft();
                                }
                            }
                        });

                        holder.bt_order_left_left.setVisibility(View.VISIBLE);
                        holder.bt_order_left_left.setText("修改地址");
                        holder.bt_order_left_left.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //修改地址
                                if (orderBean == null) {
                                    return;
                                }
                                if(orderBean.isIfGroupTrade()&& orderBean.isEqualAddress()){
                                    ToastUtil.show(context,"此拼团暂不支持修改地址");
                                    return;
                                }
                                Intent intent = new Intent(context, AddrListActivity.class);
                                intent.putExtra("selectOrderAddr", true);
                                intent.putExtra("tradeId", orderBean.getTradeId() + "");
                                ActivityUtil.getInstance().startResult((Activity)context, intent, OrderListActivity.REQ_UPDATAADDR);
                            }
                        });
                        if (orderBean.isIfUpdateTradeAdr()){
                            //修改过地址了
                            holder.bt_order_left_left.setVisibility(View.GONE);
                        }
                    }
                    break;
                case 5:
                    //等待发货
                    if (orderBean.isIfShowLogistic()&&!orderBean.isOnlyReturn()) {
                        //可查看物流 且不是团购返点订单
                        holder.bt_order_right.setVisibility(View.GONE);
                        holder.bt_order_left.setText("查看物流");
                        holder.bt_order_left.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //查看物流
                                Intent intent = new Intent(context, LogisticsDetailActivity.class);
                                intent.putExtra("postid", orderBean.getPostId());
                                intent.putExtra("tradeId", orderBean.getTradeId());
                                ActivityUtil.getInstance().start((Activity) context, intent);
                            }
                        });
                    }else {
                        holder.bt_order_right.setVisibility(View.GONE);
                        holder.bt_order_left.setVisibility(View.GONE);
                    }
                    //需求确认 订单列表待发货状态不显示修改地址
                    holder.bt_order_left_left.setVisibility(View.GONE);
                    holder.bt_order_left_left.setText("修改地址");
                    holder.bt_order_left_left.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //修改地址
                            if (orderBean == null) {
                                return;
                            }
                            if(orderBean.isIfGroupTrade()&& orderBean.getTradeGroupCreate()!=null && orderBean.getTradeGroupCreate().isEqualAddress()){
                                ToastUtil.show(context,"此拼团暂不支持修改地址");
                                return;
                            }
                            Intent intent = new Intent(context, AddrListActivity.class);
                            intent.putExtra("selectOrderAddr", true);
                            intent.putExtra("tradeId", orderBean.getTradeId() + "");
                            ActivityUtil.getInstance().startResult((Activity)context, intent, OrderListActivity.REQ_UPDATAADDR);
                        }
                    });
                    if (orderBean.isIfUpdateTradeAdr()){
                        //修改过地址了
                        holder.bt_order_left_left.setVisibility(View.GONE);
                    }
                    if(holder.bt_order_right.getVisibility()==View.GONE
                            &&holder.bt_order_left.getVisibility()==View.GONE
                            &&holder.bt_order_left_left.getVisibility()==View.GONE){
                        holder.rl_bottom.setVisibility(View.GONE);
                    }

                    break;
                case 6:
                    //等待收货
                    holder.bt_order_left.setText("查看物流");
                    holder.bt_order_left.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //查看物流
                            Intent intent = new Intent(context, LogisticsDetailActivity.class);
                            intent.putExtra("postid", orderBean.getPostId());
                            intent.putExtra("tradeId", orderBean.getTradeId());
                            ActivityUtil.getInstance().start((Activity) context, intent);
                        }
                    });
                    if(orderBean.isOnlyReturn()){
                        //团购返点订单隐藏查看物流
                        holder.bt_order_left.setVisibility(View.GONE);
                    }
                    if (orderBean.getTradeProperty() == 1) {
                        if (orderBean.isIfOverOder()) {
                            //礼包订单 显示完结订单
                            //未完结
                            holder.bt_order_right.setBackgroundResource(R.drawable.bg_rect_stroke_black2_9dp);
                            holder.bt_order_right.setTextColor(context.getResources().getColor(R.color.colorBlackText3));

                            holder.bt_order_right.setText("完结订单");
                            holder.bt_order_right.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //确认收货
                                    ConfirmDialog confirmDialog = new ConfirmDialog((Activity) context, "确认完结订单后，即表示您默认该商品没有质量问题，自愿放弃退换货权利，订单达成最终交易。", new ConfirmOKI() {
                                        @Override
                                        public void executeOk() {
                                            //完结接口
                                            presenter.confirmTradeClose(orderBean.getTradeId() + "");
                                        }

                                        @Override
                                        public void executeCancel() {

                                        }
                                    });
                                    confirmDialog.textLeft();
                                }
                            });
                        } else {
                            holder.bt_order_right.setVisibility(View.GONE);
                        }
                    } else {
                        holder.bt_order_right.setBackgroundResource(R.drawable.bg_rect_stroke_red_25dp);
                        holder.bt_order_right.setTextColor(context.getResources().getColor(R.color.colorRedMain));
                        holder.bt_order_right.setText("确认收货");
                        holder.bt_order_right.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //确认收货
                                ConfirmDialog confirmDialog = new ConfirmDialog((Activity) context, "确定要收货吗？", new ConfirmOKI() {
                                    @Override
                                    public void executeOk() {
                                        if (presenter != null) {
                                            presenter.reqConfirmOrder(orderBean.getTradeId() + "");
                                        }
                                    }

                                    @Override
                                    public void executeCancel() {

                                    }
                                });
                            }
                        });
                    }
                    break;
                case 7:
                    //交易成功
                    if (isRefundState1or2()) {
                        //申请售后中不能删除订单
                        holder.bt_order_left_left.setVisibility(View.GONE);
                    } else {
                        holder.bt_order_left_left.setVisibility(View.VISIBLE);
                        if (orderBean.getTradeProperty() == 1) {
                            //礼包订单 完成订单 不能删除
                            holder.bt_order_left_left.setVisibility(View.GONE);
                        }
                        holder.bt_order_left_left.setText("删除订单");
                        holder.bt_order_left_left.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //删除订单
                                ConfirmDialog confirmDialog = new ConfirmDialog((Activity) context, "确定要删除此订单吗？", new ConfirmOKI() {
                                    @Override
                                    public void executeOk() {
                                        if (presenter != null) {
                                            presenter.reqDelOrder(orderBean.getTradeId() + "");
                                        }
                                    }

                                    @Override
                                    public void executeCancel() {

                                    }
                                });
                            }
                        });

                        //Ver1.0.9 版本助力订单隐藏
                        if (orderBean.getHelpTradeType() == 1 || orderBean.getHelpTradeType() == 2) {
                            // 1下单返现订单 2免费领取订单 隐藏再次购买 和 删除订单
                            holder.bt_order_left_left.setVisibility(View.GONE);
                        }
                    }
                    holder.bt_order_left.setText("查看物流");
                    holder.bt_order_left.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //查看物流
                            Intent intent = new Intent(context, LogisticsDetailActivity.class);
                            intent.putExtra("postid", orderBean.getPostId());
                            intent.putExtra("tradeId", orderBean.getTradeId());
                            ActivityUtil.getInstance().start((Activity) context, intent);
                        }
                    });
                    if(orderBean.isOnlyReturn()){
                        //团购返点订单隐藏查看物流
                        holder.bt_order_left.setVisibility(View.GONE);
                    }
                    if (orderBean.getTradeProperty() != 1) {
                        //不是礼包订单 0:大众订单 1:礼包订单 2:批零订单
                        if (orderBean.getIfUserRealVip() == 1 || orderBean.getTradeGroupCreateId()>0) {
                            //二次购买礼包订单的普通订单或团购订单
                            holder.bt_order_right.setVisibility(View.GONE);
                        } else {
                            holder.bt_order_right.setVisibility(View.VISIBLE);
                            holder.bt_order_right.setBackgroundResource(R.drawable.bg_rect_stroke_black2_9dp);
                            holder.bt_order_right.setTextColor(context.getResources().getColor(R.color.colorBlackText3));
                            holder.bt_order_right.setText("再次购买");
                            holder.bt_order_right.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //再次购买
                                    List<GoodBuyStateBean> goodBuyStateBeanList=new ArrayList<>();
                                    if(orderBean.getGoodsList()!=null){
                                        for (int j = 0; j < orderBean.getGoodsList().size(); j++) {
                                            OrderGoodBean orderGoodBean = orderBean.getGoodsList().get(j);
                                            GoodBuyStateBean goodBuyStateBean=new GoodBuyStateBean();
                                            goodBuyStateBean.setState(true);
                                            goodBuyStateBean.setNum(orderGoodBean.getSellCount());
                                            goodBuyStateBean.setSkuId(orderGoodBean.getSkuId());
                                            goodBuyStateBeanList.add(goodBuyStateBean);
                                        }
                                    }
                                    presenter.checkOrderBuy(goodBuyStateBeanList);
                                }
                            });
                        }

                        //Ver1.0.9 版本助力订单隐藏
                        if (orderBean.getHelpTradeType() == 1 || orderBean.getHelpTradeType() == 2) {
                            // 1下单返现订单 2免费领取订单 隐藏再次购买 和 删除订单
                            holder.bt_order_right.setVisibility(View.GONE);
                        }

                    } else {
                        //是礼包订单
                        //礼包订单 完成状态 显示完结订单
                        if (orderBean.isIfOverOder() && !orderBean.isIfServenAfter()) {
                            //未完结 不超过7天
                            holder.bt_order_right.setBackgroundResource(R.drawable.bg_rect_stroke_black2_9dp);
                            holder.bt_order_right.setTextColor(context.getResources().getColor(R.color.colorBlackText3));

                            holder.bt_order_right.setText("完结订单");
                            holder.bt_order_right.setVisibility(View.VISIBLE);
                            holder.bt_order_right.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //确认收货
                                    ConfirmDialog confirmDialog = new ConfirmDialog((Activity) context, "确认完结订单后，即表示您默认该商品没有质量问题，自愿放弃退换货权利，订单达成最终交易。", new ConfirmOKI() {
                                        @Override
                                        public void executeOk() {
                                            //完结接口
                                            presenter.confirmTradeClose(orderBean.getTradeId() + "");
                                        }

                                        @Override
                                        public void executeCancel() {

                                        }
                                    });
                                    confirmDialog.textLeft();
                                }
                            });
                        } else {
                            //已完结或超过7天
                            holder.bt_order_right.setVisibility(View.GONE);
                        }
                    }
                    break;
            }

            if(orderBean.getGroupType()==2&&orderBean.isIfCreateGroup()&&orderBean.getLessGroupEndTime() > 0 && orderBean.getGroupStatus() != 3){
                // 团长免费团 且是团长  倒计时没有结束 且团状态不是失败的状态 显示邀请好友
                holder.rl_bottom.setVisibility(View.VISIBLE);
                holder.bt_order_group_share.setVisibility(View.VISIBLE);
                String groupdeadline = "";
                orderBean.setLessGroupEndTime(orderBean.getLessGroupEndTime() - 1);
                groupdeadline = TimeCalculate.getTime3(0, orderBean.getLessGroupEndTime());
                holder.bt_order_group_share.setText("邀请好友" + groupdeadline);
                holder.bt_order_group_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //邀请好友
                        if (presenter != null) {
                            presenter.reqShareInfo(orderBean.getTradeGroupCreateId() + "", "21");
                        }

                    }
                });
                holderMap.put(orderBean.getTradeNo(), holder);//倒计时队列
            }
        } else {
            //分享订单
            holder.rl_bottom.setVisibility(View.GONE);
            holder.tv_yf_tip.setText("订单总价:");
            holder.tv_product_sum_price.setText("¥" + BBCUtil.getDoubleFormat2(orderBean.getRcvTotal()));
        }


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int orderType = 1;//我的订单
                if (orderBean.isIfShareTrade()) {
                    //分享订单
                    orderType = 2;
                }
                if (!orderBean.isIfHideBuy()) {
                    //非匿名订单
                    Intent intent = new Intent(context, OrderDetialActivity.class);
                    intent.putExtra("orderType", orderType);
                    intent.putExtra("tradeId", orderBean.getTradeId());
                    ActivityUtil.getInstance().startResult((Activity) context, intent, OrderListActivity.REQ_ORDERDETAIL);
                } else {
                    //匿名订单
                    Intent intent = new Intent(context, AnonymousOrderActivity.class);
                    intent.putExtra("tradeId", orderBean.getTradeId());
                    ActivityUtil.getInstance().start((Activity) context, intent);
                }
            }
        });

        return convertView;
    }


    class ViewHolder {
        @BindView(R.id.order_item_time)
        TextView order_item_time;
        @BindView(R.id.tv_product_num)
        TextView tv_product_num;
        @BindView(R.id.rl_bottom)
        LinearLayout rl_bottom;
        @BindView(R.id.bt_order_left_left)
        TextView bt_order_left_left;
        @BindView(R.id.bt_order_left)
        TextView bt_order_left;
        @BindView(R.id.bt_order_right)
        TextView bt_order_right;
        @BindView(R.id.tv_product_sum_price)
        TextView tv_product_sum_price;
        @BindView(R.id.tv_yf_tip)
        TextView tv_yf_tip;
        @BindView(R.id.bt_order_group_share)
        TextView bt_order_group_share;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public boolean isRefundState1or2() {
        //判断每个商品是否处于售后状态  退款 0-正常,1-退款处理中,2-退款成功,3-退款驳回 4-售后关闭)
        if (orderBean == null) {
            return false;
        }
        if (orderBean.getGoodsList() == null) {
            return false;
        }
        for (int j = 0; j < orderBean.getGoodsList().size(); j++) {
            OrderGoodBean orderGoodBean = orderBean.getGoodsList().get(j);
            if (!BBCUtil.isEmpty(orderGoodBean.getRefundState())) {
                if ("1".equals(orderGoodBean.getRefundState()) || "2".equals(orderGoodBean.getRefundState())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setTime() {
        if (holderMap.size() > 0) {
            ViewHolder holder = holderMap.get(orderBean.getTradeNo());
            if (holder != null) {
                if (orderBean.getTradeNo().equals(holder.bt_order_right.getTag())) {
                    if (orderBean.isIfGroupTrade()) {
                        String groupdeadline = "";
                        if (orderBean.getLessGroupEndTime() > 0) {
                            orderBean.setLessGroupEndTime(orderBean.getLessGroupEndTime() - 1);
                            groupdeadline = TimeCalculate.getTime3(0, orderBean.getLessGroupEndTime());
                            holder.bt_order_group_share.setText("邀请好友" + groupdeadline);
                        } else {
                            holderMap.remove(orderBean.getTradeNo());
                            //刷新列表
                            if (presenter != null&&holder.bt_order_group_share.getVisibility()==View.VISIBLE) {
                                holder.bt_order_group_share.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        presenter.reqOrderList(status, orderType, false);
                                    }
                                },500);
                            }
                        }
                    } else if (orderBean.getTradeStatus() == 1) {
                        String deadline = "";
                        if (orderBean.getLessSecond() > 0) {
                            orderBean.setLessSecond(orderBean.getLessSecond() - 1);
                            deadline = TimeCalculate.getTime2(0, orderBean.getLessSecond());
                            holder.bt_order_right.setText("付款" + deadline);
                        } else {
                            //取消订单
                            holderMap.remove(orderBean.getTradeNo());
                            if (presenter != null) {
                                presenter.reqOrderList(status, orderType, false);
                            }
                        }
                    }
                }
            }
        }
    }
}
