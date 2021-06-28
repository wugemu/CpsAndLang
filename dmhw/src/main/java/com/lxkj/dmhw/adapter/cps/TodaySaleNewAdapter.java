package com.lxkj.dmhw.adapter.cps;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.test.andlang.util.ActivityUtil;
import com.example.test.andlang.util.BaseLangUtil;
import com.example.test.andlang.widget.RoundedImageView;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.self.ProductInfoActivity;
import com.lxkj.dmhw.bean.self.GoodBean;
import com.lxkj.dmhw.model.LimitSaleModel;
import com.lxkj.dmhw.utils.BBCUtil;
import com.lxkj.dmhw.utils.Utils;
import com.lxkj.dmhw.view.CircleImageView;
import com.lxkj.dmhw.widget.PredicateLayout;
import com.lxkj.dmhw.widget.TextPrograssBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TodaySaleNewAdapter extends RecyclerView.Adapter<TodaySaleNewAdapter.TodaySaleVH> {
    private List<LimitSaleModel> limitSaleModels;//商品列表
    private int type=0;//1=玩主,0=玩客
    private LayoutInflater inflater;
    private Activity context;
    private String activityId;
    private boolean begin;//即将开始
    private Handler handler;
    private TodaySaleVH todayholder;

    private int fPostion;//秒杀主题场位置
    private int imgWidth;
    private int beforeCount;
    public TodaySaleNewAdapter(Activity activity, List<LimitSaleModel> limitSaleModels, int type, String activityId){

        this.limitSaleModels=limitSaleModels;
        this.context=activity;
        this.inflater = LayoutInflater.from(activity);
        this.activityId=activityId;
        imgWidth= (int)(BaseLangUtil.getDisplayWidth(activity)-activity.getResources().getDimension(R.dimen.dp_10)*3)/2;

    }
    public TodaySaleNewAdapter(Activity activity, List<LimitSaleModel> limitSaleModels,String activityId, Handler handler){

        this.limitSaleModels=limitSaleModels;
        this.context=activity;
        this.inflater = LayoutInflater.from(activity);
        this.activityId=activityId;
        this.handler=handler;
        imgWidth= (int)(BaseLangUtil.getDisplayWidth(activity)-activity.getResources().getDimension(R.dimen.dp_10)*3)/2;
    }

    public void setBegin(boolean begin) {
        this.begin = begin;
    }

    public boolean isBegin() {
        return begin;
    }

    @Override
    public TodaySaleVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TodaySaleVH(inflater.inflate(R.layout.layout_todaysale_good, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull TodaySaleVH holder, int position) {
        todayholder=holder;
        final LimitSaleModel limitSaleModel=limitSaleModels.get(position);
        if(limitSaleModel.getType()==LimitSaleModel.TYPE_BANNERSALE){
            holder.layout_todaysale_product.setVisibility(View.GONE);
            holder.layout_common_bottom.setVisibility(View.GONE);
            holder.layout_homehot_title.setVisibility(View.GONE);
            holder.layout_good_recycle.setVisibility(View.GONE);
            //banner 商品列表
//            loadBannerGood(holder,limitSaleModel.getBannerGoodBean());

        }else if(limitSaleModel.getType()==LimitSaleModel.TYPE_GOOD){
            holder.layout_todaysale_product.setVisibility(View.VISIBLE);
            holder.layout_common_bottom.setVisibility(View.GONE);
            holder.layout_homehot_title.setVisibility(View.GONE);
            holder.layout_good_recycle.setVisibility(View.GONE);
            //商品
            loadLimitGood(holder,limitSaleModel.getGood(),position);
            holder.layout_todaysale_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, ProductInfoActivity.class);
                    intent.putExtra("goodsId",limitSaleModel.getGood().getGoodsId());
                    ActivityUtil.getInstance().start(context,intent);
                }
            });
        }else if (limitSaleModel.getType()==LimitSaleModel.BOTTOM){
            holder.layout_common_bottom.setBackgroundColor(context.getResources().getColor(R.color.colorBackGroud));
            holder.layout_todaysale_product.setVisibility(View.GONE);
            holder.layout_homehot_title.setVisibility(View.GONE);
            holder.layout_common_bottom.setVisibility(View.VISIBLE);
            holder.layout_good_recycle.setVisibility(View.GONE);

            if(limitSaleModel.getBootomState()==0){
                holder.tv_bottom_tip.setText("上拉加载更多");
                holder.iv_bottom_tip.setVisibility(View.VISIBLE);
                holder.iv_bottom_tip.setImageResource(R.mipmap.pullup);
            }else if(limitSaleModel.getBootomState()==1){
                holder.tv_bottom_tip.setText("松开加载更多");
                holder.iv_bottom_tip.setVisibility(View.VISIBLE);
                holder.iv_bottom_tip.setImageResource(R.mipmap.pulldown);
            }else if(limitSaleModel.getBootomState()==2){
                holder.tv_bottom_tip.setText("别拉了，已经到底咯");
                holder.iv_bottom_tip.setVisibility(View.GONE);
            }

        }else if(limitSaleModel.getType()==LimitSaleModel.TYPE_TITLE){
            holder.layout_todaysale_product.setVisibility(View.GONE);
            holder.layout_homehot_title.setVisibility(View.VISIBLE);
            holder.layout_common_bottom.setVisibility(View.GONE);
            holder.layout_good_recycle.setVisibility(View.GONE);
        }else if(limitSaleModel.getType()==LimitSaleModel.TYPE_RECYCLE){
            holder.layout_todaysale_product.setVisibility(View.GONE);
            holder.layout_homehot_title.setVisibility(View.GONE);
            holder.layout_common_bottom.setVisibility(View.GONE);
            holder.layout_good_recycle.setVisibility(View.VISIBLE);
            //瀑布流商品列表
            loadRecycleGood(holder,limitSaleModel.getGood(),position);
            holder.layout_good_recycle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, ProductInfoActivity.class);
                    intent.putExtra("goodsId",limitSaleModel.getGood().getGoodsId());
                    ActivityUtil.getInstance().start(context,intent);
                }
            });
        }
    }

    public void loadLimitGood(TodaySaleVH limitGoodVH, final GoodBean goodsStandard, final int position){
        //商品信息
//        int w= (int)(BBCUtil.getDisplayWidth(context)-context.getResources().getDimension(R.dimen.home_banner_margin)*2);
//        int h= (int) (w*Constants.BANNER_SIZE);
//        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(w,h);
//        limitGoodVH.iv_image.setLayoutParams(params);
//        limitGoodVH.rl_image.setLayoutParams(params);

        limitGoodVH.btn_buy.setVisibility(View.GONE);
        limitGoodVH.btn_share.setVisibility(View.GONE);
        int goodsW= (int) context.getResources().getDimension(R.dimen.dp_110);
        Utils.displayImageRounded(context,goodsStandard.getImgUrl(),limitGoodVH.iv_image,(int)context.getResources().getDimension(R.dimen.dp_3));
        //判断是否售罄
        if(goodsStandard.isSellOut()){
            limitGoodVH.rl_image2.setVisibility(View.VISIBLE);
        }else {
            limitGoodVH.rl_image2.setVisibility(View.GONE);
        }
        limitGoodVH.tv_product_name.setText(goodsStandard.getGoodsName());
        limitGoodVH.tv_product_desc.setText(goodsStandard.getDescription());
        limitGoodVH.tv_sale_volume.setText("销量" + goodsStandard.getTotalSales());

        ///测试
//        goodsStandard.setSpecialTag("直降100元");
//        goodsStandard.setIfTaxFree(true);

        BBCUtil.addGoodTag(context,limitGoodVH.pl_taglist2,goodsStandard);

        SpannableString ss=new SpannableString ("¥" + goodsStandard.getSalePrice());
        ss.setSpan(new AbsoluteSizeSpan((int)context.getResources().getDimension(R.dimen.sp_11)),0,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new StyleSpan(Typeface.BOLD),0,ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        limitGoodVH.tv_sale_price2.setText(ss);
//            limitGoodVH.tv_sale_price.getPaint().setFakeBoldText(true);
        limitGoodVH.tv_price2.setText("¥" + goodsStandard.getMarketPrice());
        limitGoodVH.ll_host_price2.setVisibility(View.GONE);
        limitGoodVH.ll_guest_price2.setVisibility(View.VISIBLE);
        limitGoodVH.tv_price2.setText("¥" + goodsStandard.getMarketPrice());
        limitGoodVH.tv_price2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//下划线
        limitGoodVH.tv_price2.getPaint().setAntiAlias(true);// 抗锯齿

        if(goodsStandard.isIfSpecOfApp()){
            //秒杀商品
            limitGoodVH.tv_msjs.setText("仅剩"+goodsStandard.getRemainStock()+"件");
            limitGoodVH.progress_bar.setLeftText("");
            limitGoodVH.progress_bar.setRightText("");
            limitGoodVH.progress_bar.setCenterText("");
            limitGoodVH.progress_bar.setBgStype(R.drawable.special_prograss_bar_bg2);
            limitGoodVH.progress_bar.setPrograss(goodsStandard.getBuyStock() * 1.0f / goodsStandard.getSumStock() * 100);
            limitGoodVH.progress_bar.setVisibility(View.VISIBLE);
            limitGoodVH.btn_guest_buy.setVisibility(View.VISIBLE);
            limitGoodVH.btn_guest_buy.setText("立即抢购");
        }else{
            //预热中商品 不区分玩主玩客
            limitGoodVH.tv_msjs.setText("限量"+goodsStandard.getSumStock()+"件");
            limitGoodVH.progress_bar.setVisibility(View.GONE);
            limitGoodVH.btn_guest_buy.setVisibility(View.VISIBLE);


            //玩主 预热中商品显示即将开始
            if (begin){
//                limitGoodVH.btn_guest_buy.setBackgroundResource(R.drawable.bg_rect_red_13dp);
                limitGoodVH.btn_guest_buy.setText("即将开始");
                limitGoodVH.btn_guest_buy.setTextColor(context.getResources().getColor(R.color.colorWhite));
                limitGoodVH.btn_guest_buy.setOnClickListener(null);
            }
        }

    }

    private void loadRecycleGood(TodaySaleVH holder,GoodBean goodBean,int position){
        RelativeLayout.LayoutParams imgLayoutParams=(RelativeLayout.LayoutParams) holder.rl_stagger_good.getLayoutParams();
        imgLayoutParams.width=imgWidth;
        imgLayoutParams.height=imgWidth;
        imgLayoutParams.setMargins(0,0,0,0);
        holder.rl_stagger_good.setLayoutParams(imgLayoutParams);

        if(goodBean!=null){
            //方法1 原始方法 默认图无圆角
//            ImageLoadUtils.doLoadImageUrl(holder.iv_image_good,goodBean.getImgUrl());
            //方法2 卡顿
//            ImageLoadUtils.doLoadImageRound(holder.iv_image_good,goodBean.getImgUrl(), BitmapFillet.CornerType.TOP,imgWidth,imgWidth,context.getResources().getDimension(R.dimen.dp7));
            //方法3 部分圆角 优化后的 且默认图有圆角
//            ImageLoadUtils.doLoadImageRound(holder.iv_image_good,goodBean.getImgUrl(),R.drawable.bg_rect_grey_7dp_top2);
//            Utils.displayImage(context,goodBean.getImgUrl(),holder.iv_image_good);
            Utils.displayImageRoundedNew(context,goodBean.getImgUrl(),holder.iv_image_good,(int)context.getResources().getDimension(R.dimen.dp_2));
            holder.rl_image.setVisibility(View.GONE);

            //判断是否售罄
            if(goodBean.isSellOut()){
                holder.iv_product_status.setImageResource(R.mipmap.cart_is_empty);
                holder.rl_image.setVisibility(View.VISIBLE);
            }
            //判断是否下架
            if (goodBean.getStatus()==8) {
                holder.iv_product_status.setImageResource(R.mipmap.cart_is_down);
                holder.rl_image.setVisibility(View.VISIBLE);
            }

            //测试 start
//            if(position%4==0){
//                goodBean.setIfLivePrice(1);
//            }else if(position%4==1){
//                goodBean.setIfDead(true);
//            }else if(position%4==2){
//                goodBean.setIfSpecOfApp(false);
//                goodBean.setSpecialTag("直降100");
//                goodBean.setSpecialAmount(new BigDecimal("0"));
//            }
            //测试 end

            //直播>秒杀>特价>清仓
            holder.rl_top_activity.setVisibility(View.GONE);
            holder.iv_zb_tag_top.setVisibility(View.GONE);


            //首页热门商品列表显示国家品牌
            if (!BaseLangUtil.isEmpty(goodBean.getCountryIcon())) {
                holder.ll_country_tip.setVisibility(View.GONE);
                if (!BaseLangUtil.isEmpty(goodBean.getCountryIcon())) {
//                    GlideUtil.getInstance().displayHeadNoBg(context, goodBean.getCountryIcon(), holder.iv_good_country);
                    Utils.displayImageRoundedAll(context,goodBean.getCountryIcon(),holder.iv_good_country,(int)context.getResources().getDimension(R.dimen.dp_7));
                }
                holder.tv_good_country.setText(goodBean.getCountryBrandName());
            } else {
                holder.ll_country_tip.setVisibility(View.GONE);
            }

            holder.tv_name_good.setText(goodBean.getGoodsName());
//            BBCUtil.addGoodTag(context,holder.pl_taglist,goodBean);


            holder.tv_sale_price.setText("¥" + goodBean.getAppPrice());
            holder.tv_price.setText(goodBean.getMarketPrice()+"");
            holder.ll_host_price.setVisibility(View.GONE);
            holder.ll_guest_price.setVisibility(View.VISIBLE);
            holder.tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//下划线
            holder.tv_price.getPaint().setAntiAlias(true);// 抗锯齿

            holder.ll_inbuy_price.setVisibility(View.GONE);

        }

        holder.layout_good_recycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        if(position==getItemCount()-3&&handler!=null){
            handler.sendEmptyMessage(11);
        }
    }

    @Override
    public int getItemCount() {
        return limitSaleModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(limitSaleModels!=null&&position<limitSaleModels.size()&&position>=0){
            LimitSaleModel model=limitSaleModels.get(position);
            return model.getType();
        }
        return LimitSaleModel.BOTTOM;
    }
    @Override
    public void onViewAttachedToWindow(TodaySaleVH holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if(lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            int type=getItemViewType(holder.getLayoutPosition());
            if(type!=LimitSaleModel.TYPE_RECYCLE) {
                //不是瀑布流的item 显示成一行
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }
    }

    public LimitSaleModel getItem(int position){
        if(position==-1){
            return null;
        }
        if(limitSaleModels!=null&&position<limitSaleModels.size()){
            LimitSaleModel model=limitSaleModels.get(position);
            return model;
        }
        return null;
    }

    public void setBeforeItemCount(int beforeCount){
        //热门商品前的item数量
        this.beforeCount=beforeCount;
    }
    public int getBeforeItemCount(){
        return beforeCount;
    }

    public class TodaySaleVH extends RecyclerView.ViewHolder {
        @BindView(R.id.layout_todaysale_product)
        RelativeLayout layout_todaysale_product;
        @BindView(R.id.layout_common_bottom)
        LinearLayout layout_common_bottom;

        @BindView(R.id.layout_homehot_title)
        LinearLayout layout_homehot_title;
        @BindView(R.id.layout_good_recycle)
        LinearLayout layout_good_recycle;

        @BindView(R.id.iv_image)
        ImageView iv_image;
        @BindView(R.id.rl_image2)
        RelativeLayout rl_image2;
        @BindView(R.id.tv_product_sale_price)
        TextView tv_product_name;
        @BindView(R.id.tv_product_desc)
        TextView tv_product_desc;
        @BindView(R.id.tv_sale_volume)
        TextView tv_sale_volume;
        @BindView(R.id.pl_taglist2)
        PredicateLayout pl_taglist2;
        @BindView(R.id.ll_guest_price2)
        LinearLayout ll_guest_price2;
        @BindView(R.id.ll_host_price2)
        LinearLayout ll_host_price2;
        @BindView(R.id.btn_guest_buy)
        TextView btn_guest_buy;
        @BindView(R.id.btn_buy)
        TextView btn_buy;
        @BindView(R.id.btn_share)
        TextView btn_share;
        @BindView(R.id.tv_sale_price2)
        TextView tv_sale_price2;
        @BindView(R.id.tv_price2)
        TextView tv_price2;
        @BindView(R.id.tv_host_price2)
        TextView tv_host_price2;
        @BindView(R.id.tv_host_get_price2)
        TextView tv_host_get_price2;
        @BindView(R.id.ll_zhuan2)
        LinearLayout ll_zhuan2;
        @BindView(R.id.tv_hua_price)
        TextView tv_hua_price;
        @BindView(R.id.progress_bar)
        TextPrograssBar progress_bar;
        @BindView(R.id.tv_msjs)
        TextView tv_msjs;

        @BindView(R.id.iv_bottom_tip)
        ImageView iv_bottom_tip;
        @BindView(R.id.tv_bottom_tip)
        TextView tv_bottom_tip;

        @BindView(R.id.rl_stagger_good)
        RelativeLayout rl_stagger_good;
        @BindView(R.id.rl_image)
        RelativeLayout rl_image;
        @BindView(R.id.iv_image_good)
        RoundedImageView iv_image_good;
        @BindView(R.id.iv_product_status)
        ImageView iv_product_status;
        @BindView(R.id.pl_taglist)
        PredicateLayout pl_taglist;
        @BindView(R.id.ll_guest_price)
        LinearLayout ll_guest_price;
        @BindView(R.id.tv_sale_price)
        TextView tv_sale_price;
        @BindView(R.id.tv_price)
        TextView tv_price;
        @BindView(R.id.ll_host_price)
        LinearLayout ll_host_price;
        @BindView(R.id.tv_host_price)
        TextView tv_host_price;
        @BindView(R.id.ll_zhuan)
        LinearLayout ll_zhuan;
        @BindView(R.id.tv_host_get_price)
        TextView tv_host_get_price;

        @BindView(R.id.tv_name_good)
        TextView tv_name_good;

        @BindView(R.id.rl_top_activity)
        RelativeLayout rl_top_activity;
        @BindView(R.id.view_good_top)
        View view_good_top;
        @BindView(R.id.iv_good_tag)
        ImageView iv_good_tag;
        @BindView(R.id.tv_good_state)
        TextView tv_good_state;
        @BindView(R.id.tv_price_qitag)
        TextView tv_price_qitag;
        @BindView(R.id.tv_price_rc)
        TextView tv_price_rc;
        @BindView(R.id.iv_good_tag_tip)
        ImageView iv_good_tag_tip;
        @BindView(R.id.iv_zb_tag_top)
        ImageView iv_zb_tag_top;
        @BindView(R.id.tv_price_zj)
        TextView tv_price_zj;

        @BindView(R.id.ll_country_tip)
        LinearLayout ll_country_tip;
        @BindView(R.id.iv_good_country)
        CircleImageView iv_good_country;
        @BindView(R.id.tv_good_country)
        TextView tv_good_country;

        @BindView(R.id.ll_inbuy_price)
        LinearLayout ll_inbuy_price;
        @BindView(R.id.tv_inbuy_price)
        TextView tv_inbuy_price;
        @BindView(R.id.tv_inbuy_price_label)
        TextView tv_inbuy_price_label;

        public TodaySaleVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public void setType(int type){
        this.type=type;
    }
    public void setActivityId(String activityId){
        this.activityId=activityId;
    }


    public void setMobclickAgent(int fPostion){
        this.fPostion=fPostion;
    }
}
