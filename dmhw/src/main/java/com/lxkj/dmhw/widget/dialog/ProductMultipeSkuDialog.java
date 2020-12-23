package com.lxkj.dmhw.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
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
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.self.ProductInfoActivity;
import com.lxkj.dmhw.bean.self.GoodBean;
import com.lxkj.dmhw.bean.self.GoodSku;
import com.lxkj.dmhw.bean.self.Group;
import com.lxkj.dmhw.bean.self.Multiple;
import com.lxkj.dmhw.bean.self.MultipleSku;
import com.lxkj.dmhw.bean.self.Product;
import com.lxkj.dmhw.bean.self.SecondKill;
import com.lxkj.dmhw.utils.BBCUtil;
import com.lxkj.dmhw.utils.Utils;
import com.lxkj.dmhw.widget.PredicateLayout;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductMultipeSkuDialog extends BaseProductDialog {

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
    //    @BindView(R.id.tv_sku_type)
//    TextView tvSkuType;
//    @BindView(R.id.pl_skulist)
//    PredicateLayout plSkulist;
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
    @BindView(R.id.ll_units)
    LinearLayout llUnits;
    @BindView(R.id.rl_addsum)
    RelativeLayout rl_addsum;
    @BindView(R.id.ll_buy_gift)
    LinearLayout ll_buy_gift;
    @BindView(R.id.ll_bottom)
    LinearLayout ll_bottom;
    @BindView(R.id.tv_buy_save)
    TextView tvBuySave;
    @BindView(R.id.tv_buy_label)
    TextView tvBuyLabel;
    @BindView(R.id.tv_sku_jifen)
    TextView tv_sku_jifen;
    @BindView(R.id.tv_sku_backfee)
    TextView tv_sku_backfee;
    @BindView(R.id.ll_jihuo)
    LinearLayout ll_jihuo;
    @BindView(R.id.iv_jihuo_check)
    ImageView iv_jihuo_check;
    @BindView(R.id.tv_go_detial)
    TextView tv_go_detial;


    private Activity context;
    private Dialog overdialog;
    private List<List<TextView>> tibList;
    private List<PredicateLayout> plLists;
    private List<GoodSku> skus;
    int num = 1;// 数量
    private GoodSku sku;// 当前显示的sku
    private int type;//1=玩主，0=玩客
    private int btnFlag;//加入购物车=1，立即购买=2 ，团购商品弹框=-1, -2内购加购物车  -3内购直接购买 3新人领取
    private boolean isGift;//是否是礼包复合sku弹框
    private Handler handler;
    private GoodBean goodBean;
    private Product product;
    //    private List<Long> targetList;
    private Map<Integer, Long> targetMap;
    private Map<Integer, String> targetMapStr;
    private StringBuilder sb = new StringBuilder();
    private String imageUrl;//记录sku弹框中显示的商品图片
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
    public ProductMultipeSkuDialog(Activity context, Handler handler, int btnFlag, int type, Product product, boolean isGift,boolean isOpenGroup) {

        this.btnFlag = btnFlag;
        this.product = product;
        this.isGift = isGift;
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

    //设置是否显示集货地址选择，只有在团购普通团团长开团时才显示
    @Override
    public void setIsJoin(boolean isJoin) {
        //隐藏集货地址选择模块
        isOpenGroup=!isJoin;
        isJoin = true;
        isJihuo = false;
        if (btnFlag == -1 &&product.getGroupGoodsDetailDto()!=null&& product.getGroupGoodsDetailDto().getGroupType() == 1 && !isJoin) {
            ll_jihuo.setVisibility(View.VISIBLE);
        } else {
            ll_jihuo.setVisibility(View.GONE);
        }

        if(sku!=null) {
            changeSkuDisplayInfo(sku);
        }
    }

    private void initView() {
        View overdiaView = View.inflate(context, R.layout.dialog_product_sku,
                null);
        ButterKnife.bind(this, overdiaView);
        llUnits.removeAllViews();
        plLists = new ArrayList<>();
        tibList = new ArrayList<>();
        targetMap = new HashMap<>();
        targetMapStr = new TreeMap<>();
        if (skus != null && skus.size() > 0) {
            double max = skus.get(0).getPrice();
            double min = skus.get(0).getPrice();

            for (GoodSku skuM : product.getGoodsSkus()) {
                if (max < skuM.getPrice()) {
                    max = skuM.getPrice();
                }
                if (min > skuM.getPrice()) {
                    min = skuM.getPrice();
                }
            }

            for (int i = 0; i < product.getSpecificationList().size(); i++) {
                View llSku = LayoutInflater.from(context).inflate(R.layout.include_units, null);

                TextView skuType = llSku.findViewById(R.id.tv_sku_type);

                PredicateLayout plSkuList = llSku.findViewById(R.id.pl_skulist);

                skuType.setText(product.getSpecificationList().get(i).getSpecificationTitle());

                plLists.add(plSkuList);

                llUnits.addView(llSku);

                List<TextView> tvItems = new ArrayList<>();

                tibList.add(tvItems);

                for (int j = 0; j < product.getSpecificationList().get(i).getPropertiesList().size(); j++) {
                    MultipleSku skuM = product.getSpecificationList().get(i).getPropertiesList().get(j);
                    if (skuM == null) {
                        continue;
                    }

                    TextView txt = new TextView(context);
                    txt.setText(skuM.getPropertyName());
                    txt.setClickable(true);
                    txt.setTextSize(13);
                    Resources r = context.getResources();
                    txt.setPadding((int) r.getDimension(R.dimen.dp_15), (int) r.getDimension(R.dimen.dp_9), (int) r.getDimension(R.dimen.dp_15), (int) r.getDimension(R.dimen.dp_9));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    txt.setLayoutParams(params);
                    txt.setOnClickListener(btnClick);
                    txt.setTextColor(r.getColor(R.color.colorBlackText));
                    txt.setBackgroundResource(R.drawable.tv_sku_normal_bg);
                    tvItems.add(txt);
                    plSkuList.addView(txt);


                }

            }

            ivPlus.setTag("+");
            ivMinus.setTag("-");

            if (Build.VERSION.SDK_INT >= 21) {
                ivProductImg.setElevation(4);
            }
            changeSkuGoodImg(goodBean.getImgUrl());

            changeSkuDisplayInfo(null);

            overdialog = new Dialog(context, R.style.dialog_lhp);
            overdialog.setContentView(overdiaView);

            initBtn();
        }
        //初始化规格可点状态
        checkSkuState();

    }

    private void initBtn(){
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
        } else if(btnFlag==3){
            //新人领取
            rl_addsum.setVisibility(View.GONE);
            tvProductPrice.setTextColor(context.getResources().getColor(R.color.colorRedMain));
            tvOk.setVisibility(View.VISIBLE);
            tvAddToCart.setVisibility(View.GONE);
            llBuy.setVisibility(View.GONE);
        } else {//其他商品
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
            if (isGift) {
                //礼包复合sku弹框 隐藏加减数量 底部按钮切换
                rl_addsum.setVisibility(View.GONE);
                ll_buy_gift.setVisibility(View.VISIBLE);
                ll_bottom.setVisibility(View.GONE);
            } else {
                rl_addsum.setVisibility(View.VISIBLE);
                ll_buy_gift.setVisibility(View.GONE);
                ll_bottom.setVisibility(View.VISIBLE);
            }
        }
    }
    //获取匹配的sku
    private GoodSku getSku() {
        for (GoodSku sku : product.getGoodsSkus()) {
            boolean flag = true;
            Iterator<Map.Entry<Integer, Long>> entries = targetMap.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<Integer, Long> entry = entries.next();
                if (!entry.getValue().equals(sku.getSpecificationList().get(entry.getKey()))) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                return sku;
            }
        }
        return null;
    }

    private void setViewListener() {
        ivPlus.setOnClickListener(new OnButtonClickListener());
        ivMinus.setOnClickListener(new OnButtonClickListener());
        tvSum.addTextChangedListener(new OnTextChangeListener());

    }

    private void changeSkuDisplayInfo(GoodSku sku) {
        if (sku == null) {
            //显示初始化
            tv_sku_jifen.setVisibility(View.GONE);
            tvBuy.setText("立即购买");
            tvEconomize.setVisibility(View.GONE);
            llGuestPrice.setVisibility(View.GONE);
            tv_sku_backfee.setVisibility(View.GONE);
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
                if (goodBean.getActivityState() != 1 || product.getSeckillActivity() == null) {//普通商品
                    if (goodBean.getMinPrice() != goodBean.getMaxPrice()) {
                        tvProductPrice.setText("¥" + BBCUtil.getDoubleFormat(goodBean.getMinPrice())
                                + "~" + BBCUtil.getDoubleFormat(goodBean.getMaxPrice()));//普通价格
                    } else {
                        tvProductPrice.setText("¥" + BBCUtil.getDoubleFormat(goodBean.getMinPrice()));//普通价格
                    }
                } else {//秒杀商品
                    if (product.getSeckillActivity().getMinPrice() != product.getSeckillActivity().getMaxPrice()) {
                        tvProductPrice.setText("¥" + BBCUtil.getDoubleFormat(product.getSeckillActivity().getMinPrice())
                                + "~" + BBCUtil.getDoubleFormat(product.getSeckillActivity().getMaxPrice()));//秒杀价格
                    } else {
                        tvProductPrice.setText("¥" + BBCUtil.getDoubleFormat(product.getSeckillActivity().getMinPrice()));
                    }
                }
                if (isGift) {
                    //礼包复合sku弹框
                    if (type == 1) {
                        tvBuySave.setText("立省¥" + BBCUtil.getDoubleFormat(goodBean.getMaxIncome()));
                        tvBuyLabel.setText("省钱购");
                        if (goodBean.getActivityState() == 1) {
                            tvBuySave.setVisibility(View.GONE);
                            llGuestPrice.setVisibility(View.GONE);
                        } else {
                            tvBuySave.setVisibility(View.VISIBLE);
                            llGuestPrice.setVisibility(View.GONE);
                        }
                        tvProductPrice.setTextColor(context.getResources().getColor(R.color.colorBlackText));
                        tvProfit.setText(BBCUtil.getDoubleFormat(goodBean.getMaxIncome()));
                    } else {
                        tvBuyLabel.setText("升级购");
                        llGuestPrice.setVisibility(View.GONE);
                        tvBuySave.setVisibility(View.GONE);
                    }
                }
                // // 设置输入类型为数字
                if (goodBean.getActivityState() == 1 && product.getSeckillActivity() != null) {
                    tvInventory.setText("库存：" + product.getSeckillActivity().getRemainStock() + "件");
                } else {
                    tvInventory.setText("库存：" + goodBean.getRemainStock() + "件");
                }

            }
            //sku取消选中 隐藏限购显示
            tvLimitNum.setVisibility(View.GONE);
            num = 1;
            tvSum.setText(String.valueOf(num));
            ivPlus.setImageResource(R.mipmap.cart_add_grey);
            ivMinus.setImageResource(R.mipmap.cart_minus_grey);

            return;
        }
        this.sku = sku;
        SecondKill specialPriceInfo = product.getSpecialPriceInfo();
        //普通的商品 前面判断了是否隐藏父布局
        if (btnFlag != -1&&btnFlag != -2&&btnFlag != -3 && type == 1) {
            tvEconomize.setText("立省¥" + BBCUtil.getDoubleFormat(sku.getIncome()));
            tvBuy.setText("立即购买/");
            tvEconomize.setVisibility(View.VISIBLE);
            llGuestPrice.setVisibility(View.VISIBLE);
//            tvProductPrice.setTextColor(context.getResources().getColor(R.color.colorBlackText));
            tvProfit.setText(BBCUtil.getDoubleFormat(sku.getIncome()));
        } else {
            //特价预热中 存在内购 走这个分支
            tvBuy.setText("立即购买");
            tvEconomize.setVisibility(View.GONE);
            llGuestPrice.setVisibility(View.GONE);
//            tvProductPrice.setTextColor(context.getResources().getColor(R.color.colorRedMain));
        }

        //积分显示
        tv_sku_jifen.setVisibility(View.GONE);
        if (isGift) {
            //礼包商品 显示后台返回积分
            tv_sku_jifen.setText("可得" + BBCUtil.getDoubleFormat(goodBean.getScore()) + "积分");
        } else {
            //普通商品 显示sku赚
            tv_sku_jifen.setText("可得" + BBCUtil.getDoubleFormat(sku.getIncome()) + "积分");
        }

        tv_sku_backfee.setVisibility(View.GONE);
        if (btnFlag != -1) {//普通sku价格
            if (num > sku.getStockCnt()) {
                num = sku.getStockCnt();
            }
            tvProductPrice.setText("¥" + BBCUtil.getDoubleFormat(sku.getPrice()));
            tvInventory.setText("库存：" + sku.getStockCnt() + "件");
        } else {//团购sku价格
            num = 1;
            tv_sku_jifen.setVisibility(View.GONE);
            if(isOpenGroup&&sku.getSkuBackPrice()>0) {
                //只有开团的情况显示 返额
                tv_sku_backfee.setVisibility(View.VISIBLE);
                tv_sku_backfee.setText("拼团成功可返额¥"+BBCUtil.getDoubleFormat(sku.getSkuBackPrice()));
            }
            tvProductPrice.setText("¥" + BBCUtil.getDoubleFormat(sku.getGroupPrice()));
            tvInventory.setText("库存：" + sku.getGroupStock() + "件");
        }

        //V1.2.5 内购显示积分
//        if (btnFlag==-2||btnFlag==-3){//内购隐藏积分
//            tv_sku_jifen.setVisibility(View.GONE);
//        }

        //修改sku图片
        changeSkuGoodImg(sku.getSkuImg());


        tvSum.setText(String.valueOf(num));
        if (sku.getLimitNum() != 0) {
            tvLimitNum.setText("（限购" + sku.getLimitNum() + "件）");
            tvLimitNum.setVisibility(View.VISIBLE);
        } else {
            tvLimitNum.setVisibility(View.GONE);
        }

        if (isGift) {
            //礼包复合sku 不显示限购数量
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

    /**
     * 让 Map按key进行排序
     */
    public static Map<Integer, String> sortMapByKey(Map<Integer, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<Integer, String> sortMap = new TreeMap<Integer, String>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        sortMap.putAll(map);
        return sortMap;
    }


    private String getSkuName() {
        sb.delete(0, sb.length());
        Map<Integer, String> resMap = sortMapByKey(targetMapStr);
        if (resMap == null) {
            return sb.toString();
        }
        for (Map.Entry<Integer, String> entry : resMap.entrySet()) {
            if (!BBCUtil.isEmpty(entry.getValue())) {
                sb.append(entry.getValue());
                sb.append(",");
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    private View.OnClickListener btnClick = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            long id = -1;//复合skuid
            for (int i = 0; i < tibList.size(); i++) {
                for (int j = 0; j < tibList.get(i).size(); j++) {

                    TextView btn = tibList.get(i).get(j);
                    if (v == btn) {
                        if (btn.isSelected()) {
                            id = product.getSpecificationList().get(i).getPropertiesList().get(j).getSpecificationId();
                            //点击已选择的sku 可取消选择
                            targetMap.remove(i);
                            targetMapStr.remove(i);
                        } else {
                            id = product.getSpecificationList().get(i).getPropertiesList().get(j).getSpecificationId();
                            targetMap.put(i, id);
                            targetMapStr.put(i, product.getSpecificationList().get(i).getPropertiesList().get(j).getPropertyName());
                        }
                    }

                }

            }
            checkSkuState();
        }

    };

    private void checkSkuState() {
        if (product == null || product.getSpecificationList() == null || goodBean == null) {
            return;
        }
        String imgUrl = goodBean.getImgUrl();

        List<Long> longList = getAntherSpecificationIds();//需要校验的sku行对应
        for (int m = 0; m < product.getSpecificationList().size(); m++) {
            Resources r = context.getResources();
            List<TextView> tvList = tibList.get(m);
            Multiple multiple = product.getSpecificationList().get(m);
            List<MultipleSku> multipleSkuList = multiple.getPropertiesList();
            if (multipleSkuList == null) {
                continue;
            }
            for (int x = 0; x < multipleSkuList.size(); x++) {
                TextView btn = tvList.get(x);
                if (targetMap.size() > 0) {
                    //已经选中至少一个skud规格
                    if (targetMap.get(m) != null && multipleSkuList.get(x).getSpecificationId() == targetMap.get(m)) {
                        //选中点击的sku规格
                        btn.setSelected(true);
                        btn.setBackgroundResource(R.drawable.tv_sku_pressed_bg);
                        btn.setTextColor(r.getColor(R.color.colorWhite));

                        if (!BBCUtil.isEmpty(multipleSkuList.get(x).getImgUrl())) {
                            //选中的规格 图片不为空时
                            imgUrl = multipleSkuList.get(x).getImgUrl();
                        }

                    } else if (!longList.contains(multipleSkuList.get(x).getSpecificationId())) {
                        //判断其它规格是否可选中
                        //设置不可以选中该规格
                        btn.setTextColor(r.getColor(R.color.colorBlackText2));
                        btn.setBackgroundResource(R.drawable.tv_sku_no_stock_bg);
                        btn.setOnClickListener(null);
                    } else {
                        //设置可以选中该规格
                        btn.setOnClickListener(btnClick);
                        btn.setSelected(false);
                        btn.setBackgroundResource(R.drawable.tv_sku_normal_bg);
                        btn.setTextColor(r.getColor(R.color.colorBlackText));
                    }
                } else {
                    //初始化所有规格可选中
                    btn.setOnClickListener(btnClick);
                    btn.setSelected(false);
                    btn.setBackgroundResource(R.drawable.tv_sku_normal_bg);
                    btn.setTextColor(r.getColor(R.color.colorBlackText));
                }

                //初始数据sku规格库存为0 置灰
                if (btnFlag == -1) {
                    if (multipleSkuList.get(x).getStockGroupNum() <= 0) {
                        btn.setTextColor(r.getColor(R.color.colorBlackText2));
                        btn.setBackgroundResource(R.drawable.tv_sku_no_stock_bg);
                        btn.setOnClickListener(null);
                    }
                } else {
                    if (multipleSkuList.get(x).getStockNum() <= 0) {
                        btn.setTextColor(r.getColor(R.color.colorBlackText2));
                        btn.setBackgroundResource(R.drawable.tv_sku_no_stock_bg);
                        btn.setOnClickListener(null);
                    }
                }
            }
        }

        //修改图片
        changeSkuGoodImg(imgUrl);

        String skuStr = getSkuName();
        if (!BBCUtil.isEmpty(skuStr)) {
            tvSelectUnit.setText("已选择：" + skuStr);
            tvSelectUnit.setTextColor(context.getResources().getColor(R.color.colorBlackText));
        } else {
            tvSelectUnit.setText("请选择规格");
            tvSelectUnit.setTextColor(context.getResources().getColor(R.color.colorBlackText2));
        }

        if (targetMap.size() == product.getSpecificationList().size()) {
            sku = getSku();
        } else {
            sku = null;
        }
        changeSkuDisplayInfo(sku);

    }

    //根据传入选中的复合skuid遍历商品的sku列表得到跟选中sku相对应的另一个复合sku的id列表
    private List<Long> getAntherSpecificationIds() {
        List<Long> longList = new ArrayList<>();
        if (product.getGoodsSkus() == null) {
            return longList;
        }
        for (GoodSku sku : product.getGoodsSkus()) {
            if (sku.getSpecificationList() == null) {
                continue;
            }
            int stock;
            if (btnFlag == -1) {
                stock = sku.getGroupStock();
            } else {
                stock = sku.getStockCnt();
            }
            if (stock > 0) {
                //有库存的sku
                if (isHaveThisSku(targetMap, sku)) {
                    //包含此级sku的其它sku可选项
                    for (int j = 0; j < sku.getSpecificationList().size(); j++) {
                        longList.add(sku.getSpecificationList().get(j));
                    }
                } else {
                    //选中的sku同级可选则的sku
                    if (targetMap.size() > 0 && targetMap.size() < sku.getSpecificationList().size()) {
                        for (int j = 0; j < sku.getSpecificationList().size(); j++) {
                            Iterator<Map.Entry<Integer, Long>> entries = targetMap.entrySet().iterator();
                            while (entries.hasNext()) {
                                Map.Entry<Integer, Long> entry = entries.next();
                                if (entry.getKey() == j) {
                                    longList.add(sku.getSpecificationList().get(j));
                                }
                            }
                        }
                    } else if (targetMap.size() == 2 && sku.getSpecificationList().size() == 2) {
                        //最后一次规格选择后 其它是否可选  --2个规格时
                        Map<Integer, Long> targetMap1 = new HashMap<Integer, Long>();
                        targetMap1.put(0, targetMap.get(0));
                        if (isHaveThisSku(targetMap1, sku)) {
                            for (int j = 0; j < sku.getSpecificationList().size(); j++) {
                                longList.add(sku.getSpecificationList().get(j));
                            }
                        }

                        Map<Integer, Long> targetMap2 = new HashMap<Integer, Long>();
                        targetMap2.put(1, targetMap.get(1));
                        if (isHaveThisSku(targetMap2, sku)) {
                            for (int j = 0; j < sku.getSpecificationList().size(); j++) {
                                longList.add(sku.getSpecificationList().get(j));
                            }
                        }
                    } else if (targetMap.size() == 3 && sku.getSpecificationList().size() == 3) {
                        //最后一次规格选择后 其它是否可选 --3个规格时
                        Map<Integer, Long> targetMap1 = new HashMap<Integer, Long>();
                        targetMap1.put(0, targetMap.get(0));
                        targetMap1.put(1, targetMap.get(1));
                        if (isHaveThisSku(targetMap1, sku)) {
                            for (int j = 0; j < sku.getSpecificationList().size(); j++) {
                                longList.add(sku.getSpecificationList().get(j));
                            }
                        }

                        Map<Integer, Long> targetMap2 = new HashMap<Integer, Long>();
                        targetMap2.put(0, targetMap.get(0));
                        targetMap2.put(2, targetMap.get(2));
                        if (isHaveThisSku(targetMap2, sku)) {
                            for (int j = 0; j < sku.getSpecificationList().size(); j++) {
                                longList.add(sku.getSpecificationList().get(j));
                            }
                        }

                        Map<Integer, Long> targetMap3 = new HashMap<Integer, Long>();
                        targetMap3.put(1, targetMap.get(1));
                        targetMap3.put(2, targetMap.get(2));
                        if (isHaveThisSku(targetMap3, sku)) {
                            for (int j = 0; j < sku.getSpecificationList().size(); j++) {
                                longList.add(sku.getSpecificationList().get(j));
                            }
                        }
                    }
                }
            }

        }
        return longList;
    }


    //此sku是否包含所有选中的sku规格
    public boolean isHaveThisSku(Map<Integer, Long> targetMap, GoodSku sku) {
        if (targetMap.size() == 0) {
            return true;
        }
        Iterator<Map.Entry<Integer, Long>> entries = targetMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<Integer, Long> entry = entries.next();
            if (sku.getSpecificationList().get(entry.getKey()).equals(entry.getValue())) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    //
    @OnClick({R.id.tv_go_detial,R.id.iv_close, R.id.tv_ok, R.id.ll_buy, R.id.ll_buy_gift, R.id.tv_add_to_cart, R.id.iv_product_img, R.id.iv_jihuo_check})
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
            case R.id.ll_buy_gift://礼包购买
                buy();
                break;
            case R.id.tv_add_to_cart://玩主加购
                addToCart();
                break;
            case R.id.tv_ok://玩客购买和加购
                if (btnFlag == -1) {//团购购买
                    groupBuy();
                } else if (btnFlag == 1||btnFlag==-2||btnFlag==3) {
                    addToCart();
                } else if (btnFlag == 2||btnFlag==-3) {
                    buy();
                }

                break;
            case R.id.iv_product_img:
                if (!BBCUtil.isEmpty(imageUrl)) {
                    new LookPicDialog(context, imageUrl);
                }
                break;
        }
    }
//

    private void addToCart() {
        if (sku == null) {
            for (int i = 0; i < product.getSpecificationList().size(); i++) {
                if (targetMap.get(i) == null) {
                    ToastUtil.show(context, "请先选择" + product.getSpecificationList().get(i).getSpecificationTitle());
                    return;
                }
            }
        }
        if (sku.getStockCnt() == 0) {
            ToastUtil.show(context, "您手点慢了，该商品已经被人抢光拉！");
        } else if (num > sku.getStockCnt()) {
            ToastUtil.show(context, "商品库存仅剩" + sku.getStockCnt() + "件");
        } else if (sku.getLimitNum() != 0 && num > sku.getLimitNum()) {
            ToastUtil.show(context, "商品单笔限购" + sku.getLimitNum() + "件");
        } else {
            Message msg = handler.obtainMessage();
            if (isGift) {
                //礼包复合sku handler 传2
                msg.what = 2;
            } else {
                msg.what = 1;
            }
            msg.obj = sku;
            msg.arg1 = num;
            handler.sendMessage(msg);
        }
    }

    private void groupBuy() {
        if (sku == null) {
            for (int i = 0; i < product.getSpecificationList().size(); i++) {
                if (targetMap.get(i) == null) {
                    ToastUtil.show(context, "请先选择" + product.getSpecificationList().get(i).getSpecificationTitle());
                    return;
                }
            }
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
            for (int i = 0; i < product.getSpecificationList().size(); i++) {
                if (targetMap.get(i) == null) {
                    ToastUtil.show(context, "请先选择" + product.getSpecificationList().get(i).getSpecificationTitle());
                    return;
                }
            }
        }
        if (sku.getStockCnt() == 0) {
            ToastUtil.show(context, "您手点慢了，该商品已经被人抢光拉！");
        } else if (num > sku.getStockCnt()) {
            ToastUtil.show(context, "商品库存仅剩" + sku.getStockCnt() + "件");
        } else if (!isGift && sku.getLimitNum() != 0 && num > sku.getLimitNum()) {
            //不是礼包商品需要判断限购
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

            overdialog.show();
            Window win = overdialog.getWindow();
            win.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = win.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//			lp.height=MihuiUtil.getDisplayHeight(context)/2;
            int maxHeight = (int) (BBCUtil.getDisplayHeight(context) * 0.35);

            scrollView.getLayoutParams().height = WindowManager.LayoutParams.WRAP_CONTENT;
            if (product != null) {
                if (product.getSpecificationList() != null && product.getSpecificationList().size() > 0) {
                    int skuNum = 0;//记录所有子规格的数量
                    for (int i = 0; i < product.getSpecificationList().size(); i++) {
                        if (product.getSpecificationList().get(i).getPropertiesList() != null) {
                            skuNum = skuNum + product.getSpecificationList().get(i).getPropertiesList().size();
                        }
                    }

                    if (skuNum > 6) {
                        //大于两个规格时高度控制
                        scrollView.getLayoutParams().height = maxHeight;
                    }
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


    private void changeSkuGoodImg(String imgUrl) {
        this.imageUrl = imgUrl;
        Utils.displayImage(context,this.imageUrl,ivProductImg);
    }

}
