package com.lxkj.dmhw.adapter.cps;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.test.andlang.util.ActivityUtil;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.self.ProductInfoActivity;
import com.lxkj.dmhw.bean.self.ActivityGoodBean;
import com.lxkj.dmhw.bean.self.GoodBean;
import com.lxkj.dmhw.utils.BBCUtil;
import com.lxkj.dmhw.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeBannerGoodAdapter2 extends RecyclerView.Adapter<HomeBannerGoodAdapter2.MyViewHolder>  {
    private LayoutInflater inflater;
    private Activity activity;
    private List<GoodBean> goodBeanList;
    private int width;
    private int type;//1=玩主,0=玩客
    private ActivityGoodBean item;
    private int margins;
    private boolean isShowTag;
    private int fPosition;

    //内购相关参数
    private boolean isPreView;//是否预览
    private int mainType= 0;//0进行中的内购主页 1预热中的内购主页 2往期内购主页
    private int isFromType=-1;//默认-1非内购页面 0生成内购页面 1内购预览页面 2内购列表页面
    private boolean ownMarket=true;//是否自己市场 默认true
    private int state=2;//0-未确认发布 1-已确认发布(包含过期下线数据) 2-已开始 8-已下线不会存在 9-已删除不会存在
    private Handler handler;

    //新版首页专用，老版慎用
    public void setData(List<GoodBean> goodBeanList, int type, ActivityGoodBean item, int fPosition){
        if (goodBeanList!=null){
            this.goodBeanList=goodBeanList;
        }
        this.type=type;
        this.item=item;
        this.fPosition=fPosition;
        notifyDataSetChanged();
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setMainType(int mainType) {
        this.mainType = mainType;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void setFromType(int fromType) {
        this.isFromType = fromType;
    }
    public void setOwnMarket(boolean ownMarket){
        this.ownMarket=ownMarket;
    }

    public void setPreView(boolean preView) {
        isPreView = preView;
    }

    public HomeBannerGoodAdapter2(Activity activity, List<GoodBean> goodBeanList, int type, ActivityGoodBean item, int fPosition) {
        this.activity = activity;
        this.goodBeanList=goodBeanList;
        width = (int) ((BBCUtil.getDisplayWidth(activity)) / 2.5);
        margins=(int) activity.getResources().getDimension(R.dimen.dp_5);
        this.inflater = LayoutInflater.from(activity);
        this.type=type;
        this.item=item;
        this.fPosition=fPosition;
        if(goodBeanList!=null){
            for (GoodBean product:goodBeanList
                 ) {
                if(!BBCUtil.isEmpty(product.getSpecialTag())){
                    isShowTag=true;
                }
            }
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = inflater.inflate(R.layout.adapter_horizontal_product2,
                viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int postion) {
        myViewHolder.iv_inbuy_delgood.setVisibility(View.GONE);

        if(goodBeanList!=null){
            final GoodBean product = goodBeanList.get(postion);
            FrameLayout.LayoutParams p2 = (FrameLayout.LayoutParams) myViewHolder.ivProductImg.getLayoutParams();
            p2.width = width-margins*2;
            p2.height = p2.width;

            LinearLayout.LayoutParams p1=new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT);
            myViewHolder.llProduct.setLayoutParams(p1);

            LinearLayout.LayoutParams p3=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);

            myViewHolder.tvProductName.setText(product.getGoodsName());
            if(postion==0&&postion==getItemCount()-1){
                p1=new LinearLayout.LayoutParams(width+margins, LinearLayout.LayoutParams.WRAP_CONTENT);
                myViewHolder.llProduct.setLayoutParams(p1);
                p2.setMargins(margins,0,0,0);
                myViewHolder.ivProductImg.setLayoutParams(p2);
                myViewHolder.rl_image.setLayoutParams(p2);
                p3.setMargins(margins,0,0,0);
                myViewHolder.ll_good_bottom.setLayoutParams(p3);

//                myViewHolder.view_firstitem.setVisibility(View.VISIBLE);
                if(goodBeanList.size()>=10) {
                    myViewHolder.view_lastitem.setVisibility(View.VISIBLE);
                    myViewHolder.rl_lastitem.setVisibility(View.VISIBLE);
                }else {
                    myViewHolder.view_lastitem.setVisibility(View.GONE);
                    myViewHolder.rl_lastitem.setVisibility(View.GONE);
                }
            }else if(postion==0){
                p1=new LinearLayout.LayoutParams(width+margins, LinearLayout.LayoutParams.WRAP_CONTENT);
                myViewHolder.llProduct.setLayoutParams(p1);
                p2.setMargins(margins,0,0,0);
                myViewHolder.ivProductImg.setLayoutParams(p2);
                myViewHolder.rl_image.setLayoutParams(p2);
                p3.setMargins(margins,0,0,0);
                myViewHolder.ll_good_bottom.setLayoutParams(p3);

//                myViewHolder.view_firstitem.setVisibility(View.VISIBLE);
                myViewHolder.view_lastitem.setVisibility(View.GONE);
                myViewHolder.rl_lastitem.setVisibility(View.GONE);
            }else if(postion==getItemCount()-1){
                p2.setMargins(0,0,0,0);
                myViewHolder.ivProductImg.setLayoutParams(p2);
                myViewHolder.rl_image.setLayoutParams(p2);
                p3.setMargins(0,0,0,0);
                myViewHolder.ll_good_bottom.setLayoutParams(p3);

//                myViewHolder.view_firstitem.setVisibility(View.GONE);
                if(goodBeanList.size()>=10) {
                    myViewHolder.view_lastitem.setVisibility(View.VISIBLE);
                    myViewHolder.rl_lastitem.setVisibility(View.VISIBLE);
                }else {
                    myViewHolder.view_lastitem.setVisibility(View.GONE);
                    myViewHolder.rl_lastitem.setVisibility(View.GONE);
                }
                myViewHolder.llProduct.setBackgroundResource(R.color.colorWhite);
            }else {
                p2.setMargins(0,0,0,0);
                myViewHolder.ivProductImg.setLayoutParams(p2);
                myViewHolder.rl_image.setLayoutParams(p2);
                p3.setMargins(0,0,0,0);
                myViewHolder.ll_good_bottom.setLayoutParams(p3);

//                myViewHolder.view_firstitem.setVisibility(View.GONE);
                myViewHolder.view_lastitem.setVisibility(View.GONE);
                myViewHolder.rl_lastitem.setVisibility(View.GONE);
                myViewHolder.llProduct.setBackgroundResource(R.color.colorWhite);
            }



            Utils.displayImage(activity,product.getImgUrl(),myViewHolder.ivProductImg);
            myViewHolder.ivProductImg.setBackgroundColor(activity.getResources().getColor(R.color.colorBackGroud));

            //测试
//            if(postion%2==0) {
//                product.setSpecialTag("直降99元");
//            }else {
//                product.setSpecialTag("");
//            }

            //判断是否特价
            if(!BBCUtil.isEmpty(product.getSpecialTag())&&(isFromType==0||isFromType==-1)){
                //特价标签 只有生成内购页面显示
                myViewHolder.tv_tjtag.setText(product.getSpecialTag());
                myViewHolder.tv_tjtag.setVisibility(View.VISIBLE);
            }else {
                if(isShowTag&&(isFromType==0||isFromType==-1)) {
                    //至少有一个有tag 只有生成内购页面显示
                    myViewHolder.tv_tjtag.setVisibility(View.INVISIBLE);
                }else {
                    myViewHolder.tv_tjtag.setVisibility(View.GONE);
                }
            }

            myViewHolder.rl_image.setVisibility(View.GONE);
            //是否售罄
            if (product.isSellOut()) {
                myViewHolder.rl_image.setVisibility(View.VISIBLE);
                myViewHolder.iv_product_status.setImageResource(R.mipmap.cart_is_empty);
//                if(postion==0){
//                    myViewHolder.view_shadow.setBackgroundResource(R.drawable.bg_empty_topleft_4dp);
//                }else {
//                    myViewHolder.view_shadow.setBackgroundColor(activity.getResources().getColor(R.color.bg_empty));
//                }
            }
            //判断是否下架
            if (product.getStatus()==8) {
                myViewHolder.iv_product_status.setImageResource(R.mipmap.cart_is_down);
                myViewHolder.rl_image.setVisibility(View.VISIBLE);
            }


            if(type==1){
                myViewHolder.tv_host_price.setText(product.getAppPrice()+"");
                myViewHolder.tv_host_get_price.setText(product.getProfit()+"");
                myViewHolder.ll_host_price.setVisibility(View.VISIBLE);
                myViewHolder.ll_guest_price.setVisibility(View.GONE);
                //秒杀商品也显示赚
                myViewHolder.ll_zhuan.setVisibility(View.VISIBLE);
                myViewHolder.tv_hua_price.setVisibility(View.GONE);
//                if(product.isIfSpecOfApp()){
//                    //秒杀商品
//                    myViewHolder.ll_zhuan.setVisibility(View.GONE);
//                    myViewHolder.tv_hua_price.setVisibility(View.VISIBLE);
//                    myViewHolder.tv_hua_price.setText("¥" + product.getMarketPrice());
//                    myViewHolder.tv_hua_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//下划线
//                    myViewHolder.tv_hua_price.getPaint().setAntiAlias(true);// 抗锯齿
//                }else {
//                    myViewHolder.ll_zhuan.setVisibility(View.VISIBLE);
//                    myViewHolder.tv_hua_price.setVisibility(View.GONE);
//                }
            }else {
                myViewHolder.tvPrice.setText("¥" + product.getAppPrice());
                myViewHolder.tvOldPrice.setText("¥" + product.getMarketPrice());
                myViewHolder.tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//下划线
                myViewHolder.tvOldPrice.getPaint().setAntiAlias(true);// 抗锯齿
                myViewHolder.ll_host_price.setVisibility(View.GONE);
                myViewHolder.ll_guest_price.setVisibility(View.VISIBLE);
            }

            myViewHolder.llProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(activity, ProductInfoActivity.class);
                    intent.putExtra("goodsId",product.getGoodsId());
                    ActivityUtil.getInstance().start(activity,intent);
                }
            });

            myViewHolder.rl_lastitem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(goodBeanList!=null){
            return goodBeanList.size();
        }
        return 0;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ll_guest_price)
        LinearLayout ll_guest_price;
        @BindView(R.id.ll_host_price)
        LinearLayout ll_host_price;
        @BindView(R.id.iv_product_img)
        ImageView ivProductImg;
        @BindView(R.id.rl_image)
        RelativeLayout rl_image;
        @BindView(R.id.iv_zb_tag_top2)
        ImageView iv_zb_tag_top2;
        @BindView(R.id.iv_product_status)
        ImageView iv_product_status;
        @BindView(R.id.tv_product_sale_price)
        TextView tvProductName;
        @BindView(R.id.tv_host_price)
        TextView tv_host_price;
        @BindView(R.id.tv_host_get_price)
        TextView tv_host_get_price;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_old_price)
        TextView tvOldPrice;
        @BindView(R.id.ll_product)
        LinearLayout llProduct;
        @BindView(R.id.view_firstitem)
        View view_firstitem;
        @BindView(R.id.view_lastitem)
        View view_lastitem;
        @BindView(R.id.rl_lastitem)
        RelativeLayout rl_lastitem;
        @BindView(R.id.ll_good_bottom)
        LinearLayout ll_good_bottom;
        @BindView(R.id.ll_zhuan)
        LinearLayout ll_zhuan;
        @BindView(R.id.tv_hua_price)
        TextView tv_hua_price;
        @BindView(R.id.view_shadow)
        View view_shadow;
        @BindView(R.id.tv_tjtag)
        TextView tv_tjtag;

        @BindView(R.id.ll_inbuy_price)
        LinearLayout ll_inbuy_price;
        @BindView(R.id.tv_inbuy_price)
        TextView tv_inbuy_price;
        @BindView(R.id.tv_inbuy_price_label)
        TextView tv_inbuy_price_label;

        @BindView(R.id.iv_inbuy_delgood)
        ImageView iv_inbuy_delgood;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
