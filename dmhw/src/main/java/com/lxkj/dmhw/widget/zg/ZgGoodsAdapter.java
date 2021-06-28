package com.lxkj.dmhw.widget.zg;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.test.andlang.util.ActivityUtil;
import com.example.test.andlang.util.ButtonUtil;
import com.example.test.andlang.util.ToastUtil;
import com.example.test.andlang.util.imageload.ImageLoadUtils;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.self.ProductInfoActivity;
import com.lxkj.dmhw.bean.self.SkuPrice;
import com.lxkj.dmhw.bean.self.TradeGoodsCar;
import com.lxkj.dmhw.utils.BBCUtil;
import com.lxkj.dmhw.widget.PredicateLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ZgGoodsAdapter extends RecyclerView.Adapter<ZgGoodsAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private Activity activity;
    private List<ZgCarModel> tradeGoodsCarList;
    private Handler handler;

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public ZgGoodsAdapter(Activity activity, List<ZgCarModel> tradeGoodsCarList) {
        this.activity = activity;
        if (tradeGoodsCarList != null) {
            this.tradeGoodsCarList = tradeGoodsCarList;
        } else {
            this.tradeGoodsCarList = new ArrayList<>();
        }
        inflater = LayoutInflater.from(activity);
    }

    public void setTradeGoodsCarList(List<ZgCarModel> tradeGoodsCarList) {
        if (tradeGoodsCarList == null) {
            this.tradeGoodsCarList = new ArrayList<>();
        } else {
            this.tradeGoodsCarList = tradeGoodsCarList;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapter_zg_goods, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        ZgCarModel model = tradeGoodsCarList.get(position);
        holder.tvZgWxDelete.setOnClickListener(null);
        if (model.getType() == ZgCarModel.GOOD_TYPE) {
            holder.llInvlidTop.setVisibility(View.GONE);
            holder.llGoods.setVisibility(View.VISIBLE);
            holder.viewLine.setVisibility(View.GONE);
            loadGoods(holder, model.getCar());
        } else if (model.getType() == ZgCarModel.INVLID_TOP_TYPE) {
            holder.llInvlidTop.setVisibility(View.VISIBLE);
            holder.llGoods.setVisibility(View.GONE);
            holder.viewLine.setVisibility(View.GONE);
            holder.tvZgWxDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handler.sendEmptyMessage(1);//清除失效商品
                }
            });
        } else {
            holder.llInvlidTop.setVisibility(View.GONE);
            holder.llGoods.setVisibility(View.GONE);
            holder.viewLine.setVisibility(View.VISIBLE);
        }

        holder.llGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ProductInfoActivity.class);
                intent.putExtra("goodsId", model.getCar().getGoodsId() + "");
                ActivityUtil.getInstance().start(activity, intent);
            }
        });
        holder.llPlusMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    private void loadGoods(ViewHolder holder, TradeGoodsCar car) {

        holder.tvProductName.setText(car.getGoodsName());
        holder.tvUnit.setText(car.getSkuName());
        ImageLoadUtils.doLoadImageRound(holder.ivProductImg, car.getImgUrl(), activity.getResources().getDimension(R.dimen.dp_7), R.drawable.bg_rect_grey_7dp2);

        if (car.getInvalid() != 1) {
            holder.viewShadow.setVisibility(View.GONE);
            holder.ivProductStatus.setVisibility(View.GONE);
            holder.llPlusMinus.setVisibility(View.VISIBLE);
            holder.tvSum.setText("" + car.getNum());
            holder.ivMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ButtonUtil.isFastDoubleClick(500)) {
                        ToastUtil.show(activity, R.string.tip_btn_fast);
                        return;
                    }
                    car.setNum(car.getNum() - 1);
                    Message msg = handler.obtainMessage();
                    msg.what = 2;
                    msg.arg1 = -1;
                    msg.obj = car;
                    handler.sendMessage(msg);
                    if (car.getNum() == 0) {//不需要移除的时候要刷新数量
                        notifyDataSetChanged();
                    }else{
                        holder.tvSum.setText(""+car.getNum());
                    }
                }
            });

            holder.ivPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ButtonUtil.isFastDoubleClick(500)) {
                        ToastUtil.show(activity, R.string.tip_btn_fast);
                        return;
                    }
                    car.setNum(car.getNum() + 1);
                    Message msg = handler.obtainMessage();
                    msg.what = 2;
                    msg.arg1 = 1;
                    msg.obj = car;
                    handler.sendMessage(msg);
                    holder.tvSum.setText(""+car.getNum());
                }
            });
        } else {
            holder.viewShadow.setVisibility(View.VISIBLE);
            holder.ivProductStatus.setVisibility(View.VISIBLE);
            holder.llPlusMinus.setVisibility(View.GONE);
            if (car.isSellOut()) {
                holder.ivProductStatus.setImageResource(R.mipmap.cart_is_empty);
            } else {
                holder.ivProductStatus.setImageResource(R.mipmap.cart_is_down);
            }
            holder.ivMinus.setOnClickListener(null);
            holder.ivPlus.setOnClickListener(null);
        }
        RelativeLayout.LayoutParams plSkuPriceParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        plSkuPriceParams.setMargins(0, (int) activity.getResources().getDimension(R.dimen.design_15dp), 0, 0);
        plSkuPriceParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        holder.plSkuPrice.setLayoutParams(plSkuPriceParams);

        holder.plSkuPrice.removeAllViews();
        for (int i = 0; i < car.getSkuPriceList().size(); i++) {
            SkuPrice skuPrice = car.getSkuPriceList().get(i);
            LinearLayout content = new LinearLayout(activity);
            content.setOrientation(LinearLayout.VERTICAL);
            PredicateLayout.LayoutParams params = new PredicateLayout.LayoutParams(
                    PredicateLayout.LayoutParams.WRAP_CONTENT, PredicateLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, (int) activity.getResources().getDimension(R.dimen.design_15dp), 0);
            params.gravity = Gravity.CENTER;
            content.setLayoutParams(params);
            TextView tvNum = new TextView(activity);
            tvNum.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
            tvNum.setTextSize(11);
            tvNum.setTextColor(activity.getResources().getColor(R.color.colorBlackText2));
            content.addView(tvNum);
            if (i != car.getSkuPriceList().size() - 1) {
                tvNum.setText(skuPrice.getMinNum() + "-" + skuPrice.getMaxNum() + "件");
            } else {
                tvNum.setText(skuPrice.getMinNum() + "件以上");
            }

            TextView tvPrice = new TextView(activity);
            LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(-2, -2);
            p2.setMargins(0, (int) activity.getResources().getDimension(R.dimen.design_5dp), 0, 0);
            p2.gravity = Gravity.CENTER;
            tvPrice.setLayoutParams(p2);
            tvPrice.setTextSize(13);
            tvPrice.setTextColor(activity.getResources().getColor(R.color.colorBlackText));
            SpannableString ss = new SpannableString("¥" + skuPrice.getPrice());
            ss.setSpan(new AbsoluteSizeSpan((int) activity.getResources().getDimension(R.dimen.sp_9)), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvPrice.setText(ss);
            content.addView(tvPrice);
            holder.plSkuPrice.addView(content);
        }

        holder.plSkuPrice.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        holder.plSkuPrice.getViewTreeObserver().removeOnPreDrawListener(this);
                        int h = holder.plSkuPrice.getWidth(); // 获取宽度
//                        Log.i("0.0", "getViewTreeObserver===" + h);
                        int width = View.MeasureSpec.makeMeasureSpec(0,
                                View.MeasureSpec.UNSPECIFIED);
                        int height = View.MeasureSpec.makeMeasureSpec(0,
                                View.MeasureSpec.UNSPECIFIED);
                        holder.llPlusMinus.measure(width, height);
                        int h2 = BBCUtil.getDisplayWidth(activity) - (int) (holder.llPlusMinus.getMeasuredWidth() + activity.getResources().getDimension(R.dimen.dp_135)); // 获取宽度
                        RelativeLayout.LayoutParams params2;
                        params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        if (h > h2) {//实际宽度大于可用宽度
                            plSkuPriceParams.addRule(RelativeLayout.BELOW, R.id.pl_sku_price);
                        } else {
                            plSkuPriceParams.addRule(RelativeLayout.BELOW, 0);
                        }
                        holder.plSkuPrice.setLayoutParams(params2);
                        holder.llPlusMinus.setLayoutParams(plSkuPriceParams);
//                        Log.i("0.0", "" + h2);
                        return true;
                    }
                });
    }


    @Override
    public int getItemCount() {
        return tradeGoodsCarList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.view_line)
        View viewLine;
        @BindView(R.id.tv_zg_wx_delete)
        TextView tvZgWxDelete;
        @BindView(R.id.ll_invlid_top)
        LinearLayout llInvlidTop;
        @BindView(R.id.iv_product_img)
        ImageView ivProductImg;
        @BindView(R.id.view_shadow)
        View viewShadow;
        @BindView(R.id.iv_product_status)
        ImageView ivProductStatus;
        @BindView(R.id.rl_cart_img)
        RelativeLayout rlCartImg;
        @BindView(R.id.tv_product_name)
        TextView tvProductName;
        @BindView(R.id.tv_unit)
        TextView tvUnit;
        //        @BindView(R.id.pl_sku_price)
//        PredicateLayout plSkuPrice;
        @BindView(R.id.pl_sku_price)
        LinearLayout plSkuPrice;
        @BindView(R.id.iv_minus)
        ImageView ivMinus;
        @BindView(R.id.tv_sum)
        TextView tvSum;
        @BindView(R.id.iv_plus)
        ImageView ivPlus;
        @BindView(R.id.ll_plus_minus)
        LinearLayout llPlusMinus;
        @BindView(R.id.ll_goods)
        LinearLayout llGoods;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
