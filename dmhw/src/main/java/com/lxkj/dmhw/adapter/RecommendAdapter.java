package com.lxkj.dmhw.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Vibrator;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.NewActivity;
import com.lxkj.dmhw.bean.Recommend;
import com.lxkj.dmhw.bean.UserInfo;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.utils.MyLayoutManager;
import com.lxkj.dmhw.utils.ToastUtil;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

import java.util.Objects;

/**
 * 每日推荐
 * Created by Zyhant on 2018/1/19.
 */

public class RecommendAdapter extends BaseQuickAdapter<Recommend.RecommendList, BaseViewHolder> {

    private Context context;
    private OnClickListener onClickListener;
    private RecommendShopAdapter adapter;
    TextPaint tp;
    public RecommendAdapter(Context context) {
        super(R.layout.adapter_recommend);
        this.context = context;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, Recommend.RecommendList item) {
        try {
            TextView adapter_new_one_fragment_original_price=helper.getView(R.id.adapter_new_one_fragment_original_price);
            TextView adapter_new_one_fragment_discount=helper.getView(R.id.adapter_new_one_fragment_discount);
            tp=adapter_new_one_fragment_discount.getPaint();
            tp.setFakeBoldText(true);
            TextView adapter_new_one_fragment_estimate_text=helper.getView(R.id.adapter_new_one_fragment_estimate_text);
            tp=adapter_new_one_fragment_estimate_text.getPaint();
            tp.setFakeBoldText(true);
            Utils.displayImageCircular(context, item.getAvatar(), helper.getView(R.id.adapter_recommend_image));
            helper.setText(R.id.adapter_recommend_name, item.getName());
            helper.setText(R.id.adapter_recommend_content, item.getContent());
            if (item.getReason().equals("")){
                helper.setGone(R.id.reason_txt, false);
            }else{
                helper.setGone(R.id.reason_txt, true);
                helper.setText(R.id.reason_txt, item.getReason());
            }

            helper.setText(R.id.adapter_recommend_time, item.getTime());
            helper.setText(R.id.adapter_recommend_position, Utils.Conversion(item.getPosition(), "w"));

            switch (item.getType()){
             case "0":// 单商品
                 helper.setGone(R.id.onekey_layout, true);
                 helper.setGone(R.id.single_goods_layout, true);
                 helper.setGone(R.id.adapter_recommend_more, false);
                 if (!item.getShopinfoid().equals("")){
                     helper.setGone(R.id.adapter_new_one_fragment_nogoods, false);
                     helper.setImageResource(R.id.recommend_share_img,R.mipmap.recommend_share_iv);
                     helper.getView(R.id.adapter_recommend_share).setBackgroundResource(R.drawable.adapter_recommend_share_bg);
                     helper.getView(R.id.adapter_recommend_purchase).setBackgroundResource(R.drawable.adapter_recommend_buy_bg);
                     helper.getView(R.id.onekey_layout).setBackgroundResource(R.drawable.recommend_share_onkey);
                     helper.getView(R.id.adapter_recommend_share).setEnabled(true);
                     helper.getView(R.id.adapter_recommend_purchase).setEnabled(true);
                     helper.getView(R.id.onekey_layout).setEnabled(true);
                 }else{//失效
                     helper.setGone(R.id.adapter_new_one_fragment_nogoods, true);
                     helper.setImageResource(R.id.recommend_share_img,R.mipmap.recommend_share_unuse_iv);
                     helper.getView(R.id.adapter_recommend_share).setBackgroundResource(R.drawable.adapter_recommend_share_bg1);
                     helper.getView(R.id.adapter_recommend_purchase).setBackgroundResource(R.mipmap.recommend_nogdoos_buy);
                     helper.getView(R.id.onekey_layout).setBackgroundResource(R.drawable.recommend_share_onkey1);
                     helper.getView(R.id.adapter_recommend_share).setEnabled(false);
                     helper.getView(R.id.adapter_recommend_purchase).setEnabled(false);
                     helper.getView(R.id.onekey_layout).setEnabled(false);
                 }
                 if (DateStorage.getLoginStatus()) {
                     UserInfo login = DateStorage.getInformation();
                     if (Objects.equals(login.getUsertype(), "3")) {
                         helper.setVisible(R.id.adapter_new_one_fragment_estimate, false);
                     } else {
                         helper.setVisible(R.id.adapter_new_one_fragment_estimate, true);
                     }
                 } else {
                     helper.setVisible(R.id.adapter_new_one_fragment_estimate, false);
                 }
                 Utils.displayImageRoundedAll(context, item.getImgpic1(), helper.getView(R.id.adapter_new_one_fragment_image),10);
                 helper.setText(R.id.adapter_new_one_fragment_title, item.getTitles());
                 helper.setText(R.id.adapter_new_one_fragment_discount, item.getCoupondenomination()+ "元");
                 helper.setText(R.id.adapter_new_one_fragment_estimate_text, item.getPrecommission() + "元");
                 helper.setText(R.id.adapter_new_one_fragment_price, item.getPostcouponprice());
                 helper.setText(R.id.adapter_new_one_fragment_original_price, "原价 " + context.getResources().getString(R.string.money) + Utils.getFloat(Float.parseFloat(item.getShopprice())));
                 adapter_new_one_fragment_original_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//下划线
                 adapter_new_one_fragment_original_price.getPaint().setAntiAlias(true);// 抗锯齿
                 break;
             case "1":// 多商品
                 helper.setGone(R.id.onekey_layout, false);
                 helper.setGone(R.id.single_goods_layout, false);
                 helper.setGone(R.id.adapter_recommend_more, true);
                 //遍历商品是否全部失效
                 if (item.getShopList()!=null&&item.getShopList().size()>0){
                     int tempInt=0;//记录失效商品数
                  for(int i=0;i<item.getShopList().size();i++){
                         if(item.getShopList().get(i).getStatus().equals("1")){
                          tempInt++;
                         }
                  }
                  //商品全部失效 禁用分享按钮
                  if (tempInt!=item.getShopList().size()){
                      helper.setImageResource(R.id.recommend_share_img,R.mipmap.recommend_share_iv);
                      helper.getView(R.id.adapter_recommend_share).setBackgroundResource(R.drawable.adapter_recommend_share_bg);
                      helper.getView(R.id.adapter_recommend_share).setEnabled(true);
                  }else{
                      helper.setImageResource(R.id.recommend_share_img,R.mipmap.recommend_share_unuse_iv);
                      helper.getView(R.id.adapter_recommend_share).setBackgroundResource(R.drawable.adapter_recommend_share_bg1);
                      helper.getView(R.id.adapter_recommend_share).setEnabled(false);
                  }
                   //查看更多商品 显示与否
                     if (item.getShopList().size()-tempInt>=9){
                         helper.setGone(R.id.adapter_recommend_more, true);
                     }else{
                         helper.setGone(R.id.adapter_recommend_more, false);
                     }
                 }

                 helper.getView(R.id.adapter_recommend_more).setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         Intent intent = new Intent(context, NewActivity.class);
                         intent.putExtra("name", item.getTopicName());
                         intent.putExtra("labelType", "09");
                         intent.putExtra("topicId", item.getTopicId());
                         context.startActivity(intent);
                     }
                 });
                 break;
            }
            RecyclerView recyclerView = helper.getView(R.id.adapter_recommend_recycler);
            recyclerView.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(context, 3));
            adapter = new RecommendShopAdapter((Activity) context, item.getInfo(), item.getType());
            recyclerView.setFocusableInTouchMode(false);
            recyclerView.setFocusable(false);
            recyclerView.requestFocus(); //设置焦点不需要
            recyclerView.setAdapter(adapter);
            adapter.setNewData(item.getShopList());
            adapter.notifyDataSetChanged();
            helper.getView(R.id.adapter_recommend_purchase).setOnClickListener(v -> onClickListener.onBuy(item));
            helper.getView(R.id.adapter_recommend_share).setOnClickListener(v -> onClickListener.onShare(item));
            helper.getView(R.id.adapter_recommend_content).setOnLongClickListener(v -> {
                Utils.copyText(((TextView) helper.getView(R.id.adapter_recommend_content)).getText().toString());
                ((Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(50);
                ToastUtil.showImageToast(context,"复制成功",R.mipmap.toast_img);
                return false;
            });
            helper.getView(R.id.copy_txt).setOnClickListener(v ->{
                Utils.copyText(item.getReason());
                ((Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(50);
                ToastUtil.showImageToast(context,"复制成功",R.mipmap.toast_img);
            });
            helper.getView(R.id.onekey_layout).setOnClickListener(v -> onClickListener.onOneKey(item));
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }

    /**
     * 点击事件
     */
    public interface OnClickListener {
        void onShare(Recommend.RecommendList item);
        void onBuy(Recommend.RecommendList item);
        void onOneKey(Recommend.RecommendList item);
    }
}
