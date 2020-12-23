package com.lxkj.dmhw.adapter.self.settle;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test.andlang.andlangutil.BaseLangAdapter;
import com.example.test.andlang.andlangutil.BaseLangViewHolder;
import com.example.test.andlang.util.imageload.ImageLoadUtils;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.self.GoodSku;
import com.lxkj.dmhw.bean.self.Group;
import com.lxkj.dmhw.myinterface.Confirm2OKI;
import com.lxkj.dmhw.utils.BBCUtil;
import com.lxkj.dmhw.widget.CenterAlignImageSpan;
import com.lxkj.dmhw.widget.dialog.CommonTipDialogNew;

import java.util.List;

public class SettlementAdapter extends BaseLangAdapter<GoodSku> {
    public static final int GOOD_NORMAL=1;
    public static final int GOOD_GIFT=2;
    private int type;//1=玩主,0=玩客
    private int goodType;//1普通商品 2礼包商品
    private Group group;//团购商品数据
    public SettlementAdapter(Activity context, List<GoodSku> data,int type,int goodType) {
        super(context, R.layout.listview_settlement_good, data);
        this.type=type;
        this.goodType=goodType;
    }

    @Override
    public void convert(BaseLangViewHolder helper, int postion,final GoodSku item) {
        LinearLayout ll_settlement_top=helper.getView(R.id.ll_settlement_top);
        LinearLayout ll_fanquan=helper.getView(R.id.ll_fanquan);
        TextView tv_left_tip=helper.getView(R.id.tv_left_tip);
        ImageView iv_help_fanquan=helper.getView(R.id.iv_help_fanquan);
        TextView tv_fanquan=helper.getView(R.id.tv_fanquan);
        TextView tv_post_tip=helper.getView(R.id.tv_post_tip);
        ImageView iv_product_img=helper.getView(R.id.iv_product_img);
        TextView tv_product_name=helper.getView(R.id.tv_product_name);
        TextView tv_unit=helper.getView(R.id.tv_unit);
        TextView tv_tax_fee=helper.getView(R.id.tv_tax_fee);
        TextView tv_price=helper.getView(R.id.tv_price);
        TextView tv_discount=helper.getView(R.id.tv_discount);
        TextView tv_goodnum=helper.getView(R.id.tv_goodnum);
        TextView tv_post_tip_tip=helper.getView(R.id.tv_post_tip_tip);

        if(!BBCUtil.isEmpty(item.getSettlementTopPost())){
            ll_settlement_top.setVisibility(View.VISIBLE);
            tv_post_tip.setText(item.getSettlementTopPost());
        }else {
            ll_settlement_top.setVisibility(View.GONE);
        }
        if(!BBCUtil.isEmpty(item.getNoDeliveryAranTips())){
            tv_post_tip_tip.setVisibility(View.VISIBLE);
            tv_post_tip_tip.setText(item.getNoDeliveryAranTips());
        }else {
            tv_post_tip_tip.setVisibility(View.GONE);
        }
        ImageLoadUtils.doLoadImageUrl(iv_product_img,item.getImgUrl());

        if(group != null&&group.getGroupType() == 2&&BBCUtil.isEmpty(group.getId())) {
            //团长免费领
            SpannableString spannableString = new SpannableString("  " + item.getGoodsName());
            Drawable d = context.getResources().getDrawable(R.mipmap.group_free);
            int iconwidth = (int) context.getResources().getDimension(R.dimen.dp_38);//27
            int iconHeight = (int) context.getResources().getDimension(R.dimen.dp_14);//15
            d.setBounds(0, 0, iconwidth, iconHeight);
            //居中对齐imageSpan
            CenterAlignImageSpan imageSpan1 = new CenterAlignImageSpan(d);
            spannableString.setSpan(imageSpan1, 0, 1, ImageSpan.ALIGN_BASELINE);
            tv_product_name.setText(spannableString);
        }else if(group != null&&group.isIfBackPrice()&&BBCUtil.isEmpty(group.getId())){
            //团长开团返额
            SpannableString spannableString = new SpannableString("  " + item.getGoodsName());
            Drawable d = context.getResources().getDrawable(R.mipmap.icon_group_backfee);
            int iconwidth = (int) context.getResources().getDimension(R.dimen.dp_38);//27
            int iconHeight = (int) context.getResources().getDimension(R.dimen.dp_14);//15
            d.setBounds(0, 0, iconwidth, iconHeight);
            //居中对齐imageSpan
            CenterAlignImageSpan imageSpan1 = new CenterAlignImageSpan(d);
            spannableString.setSpan(imageSpan1, 0, 1, ImageSpan.ALIGN_BASELINE);
            tv_product_name.setText(spannableString);
        }else if (group != null){
            //拼团标
            SpannableString spannableString = new SpannableString("  " + item.getGoodsName());
            Drawable d = context.getResources().getDrawable(R.mipmap.group_tag);
            int iconwidth = (int) context.getResources().getDimension(R.dimen.dp_22);//27
            int iconHeight = (int) context.getResources().getDimension(R.dimen.dp_13);//15
            d.setBounds(0, 0, iconwidth, iconHeight);
            //居中对齐imageSpan
            CenterAlignImageSpan imageSpan1 = new CenterAlignImageSpan(d);
            spannableString.setSpan(imageSpan1, 0, 1, ImageSpan.ALIGN_BASELINE);
            tv_product_name.setText(spannableString);
        }else{
            tv_product_name.setText(item.getGoodsName());
        }
        tv_unit.setText(item.getSkuName());
        Double taxfee=item.getTaxRate()*item.getAppPrice();
        if(taxfee>0) {
            tv_tax_fee.setVisibility(View.VISIBLE);
            tv_tax_fee.setText("税费:¥" + BBCUtil.getDoubleFormat(BBCUtil.getDoubleRoundOf(taxfee)));
        }else {
            tv_tax_fee.setVisibility(View.GONE);
        }
        tv_price.setText("¥"+BBCUtil.getDoubleFormat(item.getAppPrice()));
        if(type==1){
            tv_discount.setVisibility(View.VISIBLE);
            tv_discount.setText("(已省"+BBCUtil.getDoubleFormat(item.getProfit())+"元)");
            if(item.getProfit()<=0){
                //是团订单 已省金额为0元时隐藏
                tv_discount.setVisibility(View.GONE);
            }
        }else {
            tv_discount.setVisibility(View.GONE);
        }
        tv_goodnum.setText("x"+item.getNum());

        //购买礼包返券 普通商品反额
        if(item.getReturnAmount()>0){
            ll_fanquan.setVisibility(View.VISIBLE);
            if(goodType==2) {
                tv_left_tip.setText("返券");
                if(!BBCUtil.isEmpty(item.getReturnTitle())) {
                    tv_fanquan.setText(item.getReturnTitle());
                }else {
                    tv_fanquan.setText("");
                }
                iv_help_fanquan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //返券说明
                        if(!BBCUtil.isEmpty(item.getReturnDesc())) {
                            CommonTipDialogNew dialog = new CommonTipDialogNew(context, "返券说明", item.getReturnDesc(), new Confirm2OKI() {
                                @Override
                                public void executeOk() {

                                }

                                @Override
                                public void cancel() {

                                }
                            });
                        }
                    }
                });
            }else if(goodType==1){
                tv_left_tip.setText("返额");
                if(!BBCUtil.isEmpty(item.getReturnTitle())) {
                    tv_fanquan.setText(item.getReturnTitle());
                }else {
                    tv_fanquan.setText("");
                }
                iv_help_fanquan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //返券说明
                        if(!BBCUtil.isEmpty(item.getReturnDesc())) {
                            CommonTipDialogNew dialog = new CommonTipDialogNew(context, "返额说明", item.getReturnDesc(), new Confirm2OKI() {
                                @Override
                                public void executeOk() {

                                }

                                @Override
                                public void cancel() {

                                }
                            });
                        }
                    }
                });
            }
        }else {
            ll_fanquan.setVisibility(View.GONE);
        }

    }

    public void setGroup(Group group){
        this.group=group;
    }
}
