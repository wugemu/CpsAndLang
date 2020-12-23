package com.lxkj.dmhw.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.test.andlang.andlangutil.BaseLangActivity;
import com.example.test.andlang.util.ActivityUtil;
import com.example.test.andlang.util.ToastUtil;
import com.example.test.andlang.util.imageload.ImageLoadUtils;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.self.ProductInfoActivity;
import com.lxkj.dmhw.bean.self.GoodBean;
import com.lxkj.dmhw.bean.self.GoodSku;
import com.lxkj.dmhw.bean.self.Group;
import com.lxkj.dmhw.bean.self.Product;
import com.lxkj.dmhw.bean.self.SecondKill;
import com.lxkj.dmhw.utils.BBCUtil;
import com.lxkj.dmhw.utils.Utils;
import com.lxkj.dmhw.widget.PredicateLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductDialog extends BaseProductDialog {

    @BindView(R.id.iv_product_img)
    ImageView ivProductImg;
    @BindView(R.id.tv_product_price)
    TextView tvProductPrice;
    @BindView(R.id.tv_profit)
    TextView tvProfit;
    @BindView(R.id.ll_guest_price)
    LinearLayout llGuestPrice;
    @BindView(R.id.tv_inventory)
    TextView tvInventory;
    @BindView(R.id.tv_select_unit)
    TextView tvSelectUnit;
    @BindView(R.id.tv_post_policy)
    TextView tvPostPolicy;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.tv_sku_type)
    TextView tvSkuType;
    @BindView(R.id.pl_skulist)
    PredicateLayout plSkulist;
    @BindView(R.id.tv_limit_num)
    TextView tvLimitNum;
    @BindView(R.id.iv_minus)
    ImageView ivMinus;
    @BindView(R.id.tv_sum)
    TextView tvSum;
    @BindView(R.id.iv_plus)
    ImageView ivPlus;
    @BindView(R.id.ll_addsub)
    LinearLayout llAddsub;
    @BindView(R.id.tv_ok)
    TextView tvOk;
    @BindView(R.id.tv_add_to_cart)
    TextView tvAddToCart;
    @BindView(R.id.ll_buy)
    LinearLayout llBuy;
    @BindView(R.id.tv_buy)
    TextView tvBuy;
    @BindView(R.id.tv_economize)
    TextView tvEconomize;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    @BindView(R.id.tv_sku_jifen)
    TextView tv_sku_jifen;
    @BindView(R.id.tv_sku_backfee)
    TextView tv_sku_backfee;
    @BindView(R.id.ll_jihuo)
    LinearLayout ll_jihuo;
    @BindView(R.id.iv_jihuo_check)
    ImageView iv_jihuo_check;
    @BindView(R.id.rl_addsum)
    RelativeLayout rl_addsum;
    @BindView(R.id.tv_go_detial)
    TextView tv_go_detial;

    private Activity context;
    private Dialog overdialog;
    private List<TextView> tibList;
    private List<GoodSku> skus;
    int num = 1;// 数量
    private GoodSku sku;// 当前显示的sku
    private int type;//1=玩主，0=玩客
    private int btnFlag;//加入购物车=1，立即购买=2 -1团购 -2内购加购物车  -3内购直接购买 3新人领取
    private Handler handler;
    private GoodBean goodBean;
    private Product product;
    private boolean isJihuo;//是否勾选集货
    private boolean isOpenGroup;//是否开团

    private boolean isLive;//是否直播sku

    @Override
    public void setLive() {
        isLive = true;
        tvOk.setVisibility(View.GONE);
        llBuy.setVisibility(View.GONE);
        tvAddToCart.setVisibility(View.VISIBLE);
        tv_go_detial.setVisibility(View.VISIBLE);

    }
    public ProductDialog(Activity context, Handler handler, int btnFlag, int type, Product product,boolean isOpenGroup) {
        this.btnFlag = btnFlag;
        this.product = product;
        // TODO Auto-generated constructor stub
        this.handler = handler;
        this.context = context;
        this.type = type;
        this.isOpenGroup=isOpenGroup;
        if (product != null && product.getGoodsSkus() != null) {
            this.skus = product.getGoodsSkus();
            this.goodBean = product.getGoodsDetail();
        } else {
            this.skus = new ArrayList<>();
            return;
        }

        initView();
        setViewListener();

        overdialog.show();
    }
    @Override
    public void setBtnFlag(int btnFlag) {
        this.btnFlag = btnFlag;
        initBtn();
    }

    private void initView() {
        View overdiaView = View.inflate(context, R.layout.dialog_product_sku,
                null);
        ButterKnife.bind(this, overdiaView);
//
        tibList = new ArrayList<>();
        if (skus != null && skus.size() > 0) {
            double max = skus.get(0).getPrice();
            double min = skus.get(0).getPrice();
            for (int i = 0; i < skus.size(); i++) {
                GoodSku skuM = skus.get(i);
                TextView txt = new TextView(context);
                txt.setText(skuM.getSkuName());
                txt.setClickable(true);
                txt.setTextSize(13);
                Resources r = context.getResources();
                txt.setPadding((int) r.getDimension(R.dimen.dp_15), (int) r.getDimension(R.dimen.dp_9), (int) r.getDimension(R.dimen.dp_15), (int) r.getDimension(R.dimen.dp_9));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                txt.setLayoutParams(params);
                if (btnFlag == -1) {
                    if (skuM.getGroupStock() > 0) {
                        txt.setOnClickListener(btnClick);
                        txt.setTextColor(r.getColor(R.color.colorBlackText));
                        txt.setBackgroundResource(R.drawable.tv_sku_normal_bg);
                    } else {
                        txt.setTextColor(r.getColor(R.color.colorBlackText2));
                        txt.setBackgroundResource(R.drawable.tv_sku_no_stock_bg);
                        txt.setOnClickListener(null);
                    }
                }else{
                    if (skuM.getStockCnt() > 0) {
                        txt.setOnClickListener(btnClick);
                        txt.setTextColor(r.getColor(R.color.colorBlackText));
                        txt.setBackgroundResource(R.drawable.tv_sku_normal_bg);
                    } else {
                        txt.setTextColor(r.getColor(R.color.colorBlackText2));
                        txt.setBackgroundResource(R.drawable.tv_sku_no_stock_bg);
                        txt.setOnClickListener(null);
                    }
                }


                tibList.add(txt);
                plSkulist.addView(txt);

                if (max < skuM.getPrice()) {
                    max = skuM.getPrice();
                }
                if (min > skuM.getPrice()) {
                    min = skuM.getPrice();
                }
            }
            ivPlus.setTag("+");
            ivMinus.setTag("-");

            Group group = product.getGroupGoodsDetailDto();
            if (btnFlag == -1&&group!=null) {//团购价格
                if (group.getGroupMinPrice() != group.getGroupMaxPrice()) {
                    tvProductPrice.setText("¥" + BBCUtil.getDoubleFormat(group.getGroupMinPrice())
                            + "~" + BBCUtil.getDoubleFormat(group.getGroupMaxPrice()));
                } else {
                    tvProductPrice.setText("¥" + BBCUtil.getDoubleFormat(group.getGroupMaxPrice()));
                }
                tvInventory.setText("库存：" + group.getGroupLessStock() + "件");
            } else {
                if (goodBean.getActivityState() != 1 || product.getSeckillActivity() == null) {
                    if (goodBean.getMinPrice() != goodBean.getMaxPrice()) {
                        tvProductPrice.setText("¥" + BBCUtil.getDoubleFormat(goodBean.getMinPrice())
                                + "~" + BBCUtil.getDoubleFormat(goodBean.getMaxPrice()));//普通价格
                    } else {
                        tvProductPrice.setText("¥" + BBCUtil.getDoubleFormat(goodBean.getMinPrice()));//普通价格
                    }
                } else {
                    if (product.getSeckillActivity().getMinPrice() != product.getSeckillActivity().getMaxPrice()) {
                        tvProductPrice.setText("¥" + BBCUtil.getDoubleFormat(product.getSeckillActivity().getMinPrice())
                                + "~" + BBCUtil.getDoubleFormat(product.getSeckillActivity().getMaxPrice()));//普通价格
                    } else {
                        tvProductPrice.setText("¥" + BBCUtil.getDoubleFormat(product.getSeckillActivity().getMinPrice()));//普通价格
                    }
                }

                // // 设置输入类型为数字
                if (goodBean.getActivityState() == 1 && product.getSeckillActivity() != null) {
                    tvInventory.setText("库存：" + product.getSeckillActivity().getRemainStock() + "件");
                } else {
                    tvInventory.setText("库存：" + goodBean.getRemainStock() + "件");
                }

            }

            if (Build.VERSION.SDK_INT >= 21) {
                ivProductImg.setElevation(4);
            }
            Utils.displayImage(context,this.goodBean.getImgUrl(),ivProductImg);
            if (tibList.size() == 1) {
                tibList.get(0).performClick();
                sku = skus.get(0);
                if (sku.getLimitNum() != 0) {
                    tvLimitNum.setText("（限购" + sku.getLimitNum() + "件）");
                } else {
                    tvLimitNum.setVisibility(View.GONE);
                }
            }

            overdialog = new Dialog(context, R.style.dialog_lhp);
            overdialog.setContentView(overdiaView);

            initBtn();
        }

    }

    public void initBtn(){
        ll_jihuo.setVisibility(View.GONE);
        if (btnFlag == -1) {//团购商品
            tvOk.setVisibility(View.VISIBLE);
            tvAddToCart.setVisibility(View.GONE);
            llBuy.setVisibility(View.GONE);
            rl_addsum.setVisibility(View.GONE);
            if (product.getGroupGoodsDetailDto()!=null&&product.getGroupGoodsDetailDto().getGroupType() == 1) {
                //显示集货按钮
                ll_jihuo.setVisibility(View.VISIBLE);
            }
        } else if (btnFlag == -2||btnFlag==-3) {//内购商品
            rl_addsum.setVisibility(View.VISIBLE);
            //内购字体颜色为红色
//                tvProductPrice.setTextColor(context.getResources().getColor(R.color.colorRedMain));
            tvOk.setVisibility(View.VISIBLE);
            tvAddToCart.setVisibility(View.GONE);
            llBuy.setVisibility(View.GONE);
        }else if(btnFlag==3){
            //新人领取
            rl_addsum.setVisibility(View.GONE);
            tvProductPrice.setTextColor(context.getResources().getColor(R.color.colorRedMain));
            tvOk.setVisibility(View.VISIBLE);
            tvAddToCart.setVisibility(View.GONE);
            llBuy.setVisibility(View.GONE);
        } else {
            rl_addsum.setVisibility(View.VISIBLE);
            if (type == 1) {
                //玩主 价格字体颜色为黑色
                tvProductPrice.setTextColor(context.getResources().getColor(R.color.colorBlackText));
                tvOk.setVisibility(View.GONE);
                tvAddToCart.setVisibility(View.VISIBLE);
                llBuy.setVisibility(View.VISIBLE);
            } else {
                //玩客字体颜色为红色
                tvProductPrice.setTextColor(context.getResources().getColor(R.color.colorRedMain));
                tvOk.setVisibility(View.VISIBLE);
                tvAddToCart.setVisibility(View.GONE);
                llBuy.setVisibility(View.GONE);
            }
        }
    }

    //设置是否显示集货地址选择，只有在团购普通团团长开团时才显示
    @Override
    public void setIsJoin(boolean isJoin) {
        isOpenGroup=!isJoin;
        //隐藏集货地址选择模块
//        isJoin=true;
        isJihuo = false;
//        if (btnFlag == -1 && product.getGroupGoodsDetailDto().getGroupType() == 1 && !isJoin) {
//            ll_jihuo.setVisibility(View.VISIBLE);
//        } else {
            ll_jihuo.setVisibility(View.GONE);
//        }
        if(sku!=null) {
            changeSkuDisplayInfo(sku);
        }
    }

    private void setViewListener() {
        ivPlus.setOnClickListener(new OnButtonClickListener());
        ivMinus.setOnClickListener(new OnButtonClickListener());
        tvSum.addTextChangedListener(new OnTextChangeListener());

    }

    private void changeSkuDisplayInfo(GoodSku sku) {
        this.sku = sku;
        //特价信息
        SecondKill specialPriceInfo = product.getSpecialPriceInfo();
        if (btnFlag!=1&&btnFlag != -2&&btnFlag != -3 &&type == 1) {
            tvEconomize.setText("立省¥" + BBCUtil.getDoubleFormat(sku.getIncome()));
            tvBuy.setText("立即购买/");
            tvEconomize.setVisibility(View.VISIBLE);
            llGuestPrice.setVisibility(View.VISIBLE);
//            tvProductPrice.setTextColor(context.getResources().getColor(R.color.colorBlackText));
            tvProfit.setText(BBCUtil.getDoubleFormat(sku.getIncome()));
        } else {
            //特价预热中 存在内购 走这个分支
            tvBuy.setText("立即购买");
            llGuestPrice.setVisibility(View.GONE);
//            tvProductPrice.setTextColor(context.getResources().getColor(R.color.colorRedMain));
        }

        //积分
        tv_sku_jifen.setVisibility(View.GONE);
        tv_sku_jifen.setText("可得" + BBCUtil.getDoubleFormat(sku.getIncome()) + "积分");

        tvSelectUnit.setText("已选择：" + sku.getSkuName());
        tvSelectUnit.setTextColor(context.getResources().getColor(R.color.colorBlackText));

        tv_sku_backfee.setVisibility(View.GONE);
        if (btnFlag!=-1){//普通sku价格
            if (num > sku.getStockCnt()) {
                num = sku.getStockCnt();
            }
            tvProductPrice.setText("¥" + BBCUtil.getDoubleFormat(sku.getPrice()));
            tvInventory.setText("库存：" + sku.getStockCnt() + "件");
        }else{//团购sku价格
            tv_sku_jifen.setVisibility(View.GONE);
            if(isOpenGroup&&sku.getSkuBackPrice()>0) {
                //只有开团的情况显示 返额
                tv_sku_backfee.setVisibility(View.VISIBLE);
                tv_sku_backfee.setText("拼团成功可返额¥"+BBCUtil.getDoubleFormat(sku.getSkuBackPrice()));
            }
            num=1;
            llGuestPrice.setVisibility(View.GONE);
            tvProductPrice.setText("¥" + BBCUtil.getDoubleFormat(sku.getGroupPrice()));
            tvInventory.setText("库存：" + sku.getGroupStock() + "件");
        }
        //V1.2.5 内购显示积分
//        if (btnFlag==-2||btnFlag==-3){//内购隐藏积分
//            tv_sku_jifen.setVisibility(View.GONE);
//        }

        Utils.displayImage(context,sku.getSkuImg(),ivProductImg);
        tvSum.setText(String.valueOf(num));
        if (sku.getLimitNum() != 0) {
            tvLimitNum.setText("（限购" + sku.getLimitNum() + "件）");
            tvLimitNum.setVisibility(View.VISIBLE);
        } else {
            tvLimitNum.setVisibility(View.GONE);
        }
        if (sku.getLimitNum() != 0 && (num < 1 || (num == 1 && sku.getLimitNum() == 0))) {
            num = 1;
            tvSum.setText(String.valueOf(num));
            ivPlus.setImageResource(R.mipmap.cart_add_grey);
            ivMinus.setImageResource(R.mipmap.cart_minus_grey);
        } else if (sku.getLimitNum() != 0 && num >= sku.getLimitNum()) {
            ivPlus.setImageResource(R.mipmap.cart_add_grey);
            if (num == 1) {
                ivMinus.setImageResource(R.mipmap.cart_minus_grey);
            } else {
                ivMinus.setImageResource(R.mipmap.cart_minus_black);
            }
        } else {
            if (num == 1) {
                ivMinus.setImageResource(R.mipmap.cart_minus_grey);
            } else {
                ivMinus.setImageResource(R.mipmap.cart_minus_black);
            }
            ivPlus.setImageResource(R.mipmap.cart_add_black);
        }

        if (isLive){
            setLive();
        }
    }

    //
//
    private View.OnClickListener btnClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Resources r = context.getResources();
            for (int i = 0; i < tibList.size(); i++) {
                TextView btn = tibList.get(i);
                if (v == btn) {
                    if (btn.isSelected()) {
                        //点击已选择的sku 不可以初始化购买数量
                        return;
                    }
                    sku = skus.get(i);
                    btn.setSelected(true);
                    btn.setBackgroundResource(R.drawable.tv_sku_pressed_bg);
                    btn.setTextColor(r.getColor(R.color.colorWhite));
                    num = 1;//初始化sku 购买的数量
                    changeSkuDisplayInfo(skus.get(i));
                } else {
                    if (btnFlag == -1) {
                        if (skus.get(i).getGroupStock() > 0) {
                            btn.setOnClickListener(btnClick);
                            btn.setSelected(false);
                            btn.setBackgroundResource(R.drawable.tv_sku_normal_bg);
                            btn.setTextColor(r.getColor(R.color.colorBlackText));

                        } else {
                            btn.setTextColor(r.getColor(R.color.colorBlackText2));
                            btn.setBackgroundResource(R.drawable.tv_sku_no_stock_bg);
                            btn.setOnClickListener(null);
                        }
                    }else{
                        if (skus.get(i).getStockCnt() > 0) {
                            btn.setOnClickListener(btnClick);
                            btn.setSelected(false);
                            btn.setBackgroundResource(R.drawable.tv_sku_normal_bg);
                            btn.setTextColor(r.getColor(R.color.colorBlackText));
                        } else {
                            btn.setTextColor(r.getColor(R.color.colorBlackText2));
                            btn.setBackgroundResource(R.drawable.tv_sku_no_stock_bg);
                            btn.setOnClickListener(null);
                        }
                    }
                }
            }

        }
    };

    //
    @OnClick({R.id.tv_go_detial,R.id.iv_close, R.id.tv_ok, R.id.ll_buy, R.id.tv_add_to_cart, R.id.iv_product_img,R.id.iv_jihuo_check})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_go_detial://去商品详情
                Intent intent=new Intent(context, ProductInfoActivity.class);
                intent.putExtra("goodsId",product.getGoodsDetail().getGoodsId());
                ActivityUtil.getInstance().start(context,intent);
                cancelDialog();
                break;
            case R.id.iv_jihuo_check:

                break;
            case R.id.iv_close:
                overdialog.dismiss();
                break;
            case R.id.ll_buy://玩主购买
                buy();
                break;
            case R.id.tv_add_to_cart://玩主加购
                addToCart();
                break;
            case R.id.tv_ok://玩客购买和加购
                if (btnFlag == -1) {//团购购买
                    groupBuy();
                } else  if (btnFlag == 1||btnFlag==-2||btnFlag==3) {
                    addToCart();
                } else if (btnFlag == 2||btnFlag==-3) {
                    buy();
                }
                break;
            case R.id.iv_product_img:

                String imageUrl;
                if (sku == null) {
                    imageUrl = goodBean.getImgUrl();
                } else {
                    imageUrl = sku.getSkuImg();
                }
                new LookPicDialog(context, imageUrl);

                break;
        }
    }
//

    private void addToCart() {
        if (sku == null) {
            ToastUtil.show(context, "请先选择规格");
            return;
        }
        if (sku.getStockCnt() == 0) {
            ToastUtil.show(context, "您手点慢了，该商品已经被人抢光拉！");
        } else if (num > sku.getStockCnt()) {
            ToastUtil.show(context, "商品库存仅剩" + sku.getStockCnt() + "件");
        } else if (sku.getLimitNum() != 0 && num > sku.getLimitNum()) {
            ToastUtil.show(context, "商品单笔限购" + sku.getLimitNum() + "件");
        } else {
            Message msg = handler.obtainMessage();
            msg.what = 1;
            msg.obj = sku;
            msg.arg1 = num;
            handler.sendMessage(msg);
        }
    }

    private void groupBuy() {
        if (sku == null) {
            ToastUtil.show(context, "请先选择规格");
            return;
        }
            //可以购买
            Message msg = handler.obtainMessage();
            msg.what = 10;
            if (isJihuo) {
                msg.arg1 = 1;
            } else {
                msg.arg1 = 0;
            }
            msg.obj = sku;
            handler.sendMessage(msg);
    }


    private void buy() {
        if (sku == null) {
            ToastUtil.show(context, "请先选择规格");
            return;
        }
        if (sku.getStockCnt() == 0) {
            ToastUtil.show(context, "您手点慢了，该商品已经被人抢光拉！");
        } else if (num > sku.getStockCnt()) {
            ToastUtil.show(context, "商品库存仅剩" + sku.getStockCnt() + "件");
        } else if (sku.getLimitNum() != 0 && num > sku.getLimitNum()) {
            ToastUtil.show(context, "商品单笔限购" + sku.getLimitNum() + "件");
        } else {
            Message msg = handler.obtainMessage();
            msg.what = 2;
            msg.obj = sku;
            msg.arg1 = num;
            handler.sendMessage(msg);
        }
    }

    /**
     * 加减按钮事件监听器
     */
    class OnButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (sku == null) {
//                ToastUtil.show(context, "请先选择规格");
                return;
            }

            String numString = tvSum.getText().toString();
            if (numString.equals("")) {
                num = 1;
                tvSum.setText("1");
            } else {
                if (v.getTag().equals("+")) {
                    num++;

                } else if (v.getTag().equals("-")) {
                    num--;
                }
            }
            ivPlus.setImageResource(R.mipmap.cart_add_black);
            ivMinus.setImageResource(R.mipmap.cart_minus_black);
            if (num <= 1) {
                num = 1;
                ivMinus.setImageResource(R.mipmap.cart_minus_grey);
            }
            if (sku.getStockCnt() == 0) {
                num = 1;
                ToastUtil.show(context, "您手点慢了，该商品已经被人抢光拉！");
                ivMinus.setImageResource(R.mipmap.cart_minus_grey);
                ivPlus.setImageResource(R.mipmap.cart_add_grey);
                return;
            } else if (sku.getLimitNum() != 0 && num != 1 && num > sku.getLimitNum()) {
                num--;
                ivPlus.setImageResource(R.mipmap.cart_add_grey);
                if (mkeyTime == 0 || (System.currentTimeMillis() - mkeyTime) > 3000) {
                    mkeyTime = System.currentTimeMillis();
                    ToastUtil.show(context, "商品单笔限购" + sku.getLimitNum() + "件");
                }
            } else if (num != 1 && num > sku.getStockCnt()) {
                num = sku.getStockCnt();
                ivPlus.setImageResource(R.mipmap.cart_add_grey);
                if (mkeyTime == 0 || (System.currentTimeMillis() - mkeyTime) > 3000) {
                    mkeyTime = System.currentTimeMillis();
                    ToastUtil.show(context, "商品库存仅剩" + sku.getStockCnt() + "件");
                }
            }
            if (sku.getLimitNum() != 0 && sku.getLimitNum() <= 1 || sku.getStockCnt() <= 1) {
                ivMinus.setImageResource(R.mipmap.cart_minus_grey);
                ivPlus.setImageResource(R.mipmap.cart_add_grey);
            }
            if (num < 1) {
                tvSum.setText(String.valueOf(1));
            } else {
                tvSum.setText(String.valueOf(num));
            }
        }
    }

    private long mkeyTime = 0;

    /**
     * EditText输入变化事件监听器
     */
    class OnTextChangeListener implements TextWatcher {

        @Override
        public void afterTextChanged(Editable s) {
            String numString = s.toString();
            if (numString == null || numString.equals("")) {
                num = 1;
            } else {
                int numInt = Integer.parseInt(numString);
                if (numInt < 1) {
                    // Toast.makeText(context, "请输入一个大于0的数字",
                    // Toast.LENGTH_SHORT)
                    // .show();
                } else {
                    // 设置EditText光标位置 为文本末端
                    // tvSum.setSelection(tvSum.getText().toString().length());
                    num = numInt;

                }
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {

        }

    }

    @Override
    public void showDialog() {
        if (overdialog != null) {
            Window win = overdialog.getWindow();
            overdialog.show();
            win.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = win.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//			lp.height=MihuiUtil.getDisplayHeight(context)/2;
            int maxHeight = (int) (BBCUtil.getDisplayHeight(context) * 0.35);

            scrollView.getLayoutParams().height = WindowManager.LayoutParams.WRAP_CONTENT;
            if (skus != null && skus.size() > 0) {
                int skuNum = skus.size();
                if (skuNum > 6) {
                    scrollView.getLayoutParams().height = maxHeight;
                }
            }

            win.setGravity(Gravity.BOTTOM);
            win.setAttributes(lp);
        }

    }

    @Override
    public void cancelDialog() {
        if (overdialog != null) {
            overdialog.dismiss();
        }
    }


}
