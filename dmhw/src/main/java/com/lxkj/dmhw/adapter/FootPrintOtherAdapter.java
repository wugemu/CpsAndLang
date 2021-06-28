package com.lxkj.dmhw.adapter;

import android.app.Activity;

import androidx.recyclerview.widget.RecyclerView;
import android.text.TextPaint;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.CommodityDetails290;
import com.lxkj.dmhw.bean.Coupon;
import com.lxkj.dmhw.bean.CpsType;
import com.lxkj.dmhw.bean.UserInfo;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

import java.util.Objects;

import static android.graphics.Color.WHITE;

/**
 * 收藏适配器
 * Created by Android on 2018/4/16.
 */

public class FootPrintOtherAdapter extends BaseQuickAdapter<CommodityDetails290, BaseViewHolder> {

    private Activity activity;
    TextPaint tp;
    public FootPrintOtherAdapter(Activity activity) {
        super(R.layout.adapter_footprint_otherlist);
        this.activity = activity;
    }

    @Override
    protected void convert(BaseViewHolder helper, CommodityDetails290 item) {
        try {
            LinearLayout location_layout = helper.getView(R.id.location_layout);
            RelativeLayout.LayoutParams linearParamschild = (RelativeLayout.LayoutParams) location_layout.getLayoutParams();
            RelativeLayout total_layout = helper.getView(R.id.total_layout);
            RecyclerView.LayoutParams linearParamstotal = (RecyclerView.LayoutParams) total_layout.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            if (helper.getAdapterPosition()== 0) {//第一项
                total_layout.setBackground(activity.getResources().getDrawable(R.drawable.personal_grid_bgtop));
                linearParamstotal.setMargins(0,Utils.dipToPixel(R.dimen.dp_10),0,0);
                total_layout.setLayoutParams(linearParamstotal); //使设置好的布局参数应用到控件
                linearParamschild.setMargins(0,Utils.dipToPixel(R.dimen.dp_10),0,0);
                location_layout.setLayoutParams(linearParamschild);
            }else if(helper.getAdapterPosition()==getItemCount()-2){
                linearParamschild.setMargins(0,Utils.dipToPixel(R.dimen.dp_10),0,Utils.dipToPixel(R.dimen.dp_10));
                location_layout.setLayoutParams(linearParamschild);
                total_layout.setBackground(activity.getResources().getDrawable(R.drawable.personal_grid_bg01));
            }else{
                total_layout.setBackgroundColor(WHITE);
                linearParamstotal.setMargins(0,0,0,0);
                total_layout.setLayoutParams(linearParamstotal); //使设置好的布局参数应用到控件
                linearParamschild.setMargins(0,Utils.dipToPixel(R.dimen.dp_10),0,0);
                location_layout.setLayoutParams(linearParamschild);
            }
            TextView textView=helper.getView(R.id.adapter_collection_price);
            tp= textView.getPaint();
            tp.setFakeBoldText(true);
            TextView quanhou11=helper.getView(R.id.quanhou11);
            tp= quanhou11.getPaint();
            tp.setFakeBoldText(true);
            TextView adapter_new_one_fragment_discount=helper.getView(R.id.adapter_collection_discount);
            tp=adapter_new_one_fragment_discount.getPaint();
            tp.setFakeBoldText(true);
            TextView adapter_new_one_fragment_estimate_text=helper.getView(R.id.adapter_collection_estimate_text);
            tp=adapter_new_one_fragment_estimate_text.getPaint();
            tp.setFakeBoldText(true);
            Utils.displayImageRounded(activity, item.getImageUrl(), helper.getView(R.id.adapter_collection_image), 5);
            helper.setText(R.id.adapter_collection_title, item.getName());
            helper.setText(R.id.adapter_collection_price, item.getPrice());
            // 设置商品类型图片
            Object CpsTypeObject = JSON.parseObject(item.getCpsType(), CpsType.class);
            CpsType objtype=(CpsType) CpsTypeObject;
            String code=objtype.getCode();
            Utils.displayImage(activity, objtype.getLogo(), helper.getView(R.id.adapter_collection_check));
            switch (code){
                case "jd":
                case "pdd":
                case "sn":
                    helper.setGone(R.id.adapter_new_one_fragment_discount_layout,true);
                    helper.setGone(R.id.adapter_new_one_fragment_discount_wph,false);
                    if (item.getHasCoupon()){
                        Object CouponObject = JSON.parseObject(item.getCouponInfo(), Coupon.class);
                        if ((Coupon) CouponObject!=null){
                            helper.setText(R.id.adapter_collection_discount, ((Coupon) CouponObject).getPrice()+"元");
                        }
                    }else{
                        helper.setText(R.id.adapter_collection_discount, "¥0");
                    }
                    break;
                case "wph":
                case "kl":
                    helper.setGone(R.id.adapter_new_one_fragment_discount_layout,false);
                    helper.setGone(R.id.adapter_new_one_fragment_discount_wph,true);
                    helper.setText(R.id.adapter_new_one_text_discount_wph, item.getDiscount()+"折");
                    break;
            }
            helper.setText(R.id.adapter_collection_estimate_text, "奖 ¥"+item.getNormalCommission());
            helper.setText(R.id.adapter_collection_original_price, "原价¥"+item.getCost());
            UserInfo login = DateStorage.getInformation();
            if (DateStorage.getLoginStatus()) {
                if (Objects.equals(login.getUsertype(), "3")){
                    helper.setGone(R.id.adapter_new_one_fragment_estimate, false);
                } else {
                    helper.setGone(R.id.adapter_new_one_fragment_estimate, true);
                }
            } else {
                helper.setGone(R.id.adapter_new_one_fragment_estimate, false);
            }

        } catch (Exception e) {
            Logger.e(e, "");
        }
    }

}
