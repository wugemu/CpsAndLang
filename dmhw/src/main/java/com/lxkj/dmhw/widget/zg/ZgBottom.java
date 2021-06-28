package com.lxkj.dmhw.widget.zg;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.self.TradeGoodsCar;
import com.lxkj.dmhw.bean.self.ZgCartInfo;
import com.lxkj.dmhw.bean.self.ZgCartList;
import com.lxkj.dmhw.myinterface.ConfirmOKI;
import com.lxkj.dmhw.widget.MaxHeightRecyclerView;
import com.lxkj.dmhw.widget.dialog.ConfirmDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created By lhp
 * on 2020/6/3
 */
public class ZgBottom extends LinearLayout {
    @BindView(R.id.rv_zg_goods)
    MaxHeightRecyclerView rvZgGoods;
    @BindView(R.id.ll_zg_dialog)
    LinearLayout llZgDialog;
    @BindView(R.id.tv_zg_dot)
    TextView tvZgDot;
    @BindView(R.id.tv_zg_sum_price)
    TextView tvZgSumPrice;
    @BindView(R.id.tv_zg_fd)
    TextView tvZgFd;
    @BindView(R.id.iv_gwd)
    ImageView iv_gwd;
    @BindView(R.id.tv_jiesuan)
    TextView tv_jiesuan;
    @BindView(R.id.tv_no_select)
    TextView tvNoSelect;
    private boolean needShowDialog;
    private Context context;
    private ZgCallback zgCallback;
    private ZgGoodsAdapter zgGoodsAdapter;
    private List<TradeGoodsCar> carList = new ArrayList<>();

    public ZgBottom(Context context) {
        this(context, null, 0);
    }

    public ZgBottom(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZgBottom(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_zg_bottom, null);
        ButterKnife.bind(this, view);
        addView(view);
        dismissDialog();
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvZgGoods.setLayoutManager(layoutManager);
    }

    //初始化数据和监听
    public void initData(ZgCallback zgCallback, ZgCartInfo zgCartInfo) {
        this.zgCallback = zgCallback;
        refersh(zgCartInfo);
    }

    public void setZgCartList(ZgCartList zgCartList) {
        carList.clear();
        if (zgCartList.getAvailable() != null) {
            carList.addAll(zgCartList.getAvailable());
        }
        if (zgCartList.getInvalid() != null) {
            for (TradeGoodsCar car : zgCartList.getInvalid()) {
                car.setInvalid(1);
                carList.add(car);
            }
        }
        setCartList(carList);
    }


    private void setCartList(List<TradeGoodsCar> carList) {
        if (carList==null||carList.size()==0){
            dismissDialog();
            return;
        }
        //初始化话数据
        if (zgGoodsAdapter == null) {
            zgGoodsAdapter = new ZgGoodsAdapter((Activity) context, parseModel(carList));
            zgGoodsAdapter.setHandler(handler);
            rvZgGoods.setAdapter(zgGoodsAdapter);
        } else {
            zgGoodsAdapter.setTradeGoodsCarList(parseModel(carList));
        }

    }

    private List<ZgCarModel> parseModel(List<TradeGoodsCar> carList) {
        List<ZgCarModel> modelList = new ArrayList<>();
        for (TradeGoodsCar car : carList) {
            if (car.getInvalid() == 1 && !hasInvalidTop(modelList)) {
                ZgCarModel invalidTop = new ZgCarModel();
                invalidTop.setType(ZgCarModel.INVLID_TOP_TYPE);
                modelList.add(invalidTop);
            }
            ZgCarModel carModel = new ZgCarModel();
            carModel.setType(ZgCarModel.GOOD_TYPE);
            carModel.setCar(car);
            modelList.add(carModel);
            ZgCarModel line = new ZgCarModel();
            line.setType(ZgCarModel.LINE_TYPE);
            modelList.add(line);
        }
        return modelList;
    }

    private boolean hasInvalidTop(List<ZgCarModel> modelList) {
        for (ZgCarModel model : modelList) {
            if (model.getType() == ZgCarModel.INVLID_TOP_TYPE) {
                return true;
            }
        }
        return false;
    }

    //刷新底部显示
    public void refersh(ZgCartInfo zgCartInfo) {
        tvZgFd.setText("可得进货返点¥" + zgCartInfo.getCartSumProfit());
        SpannableString ss = new SpannableString("¥" + zgCartInfo.getCartSumAmount());
        ss.setSpan(new AbsoluteSizeSpan((int) context.getResources().getDimension(R.dimen.sp_12)), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvZgSumPrice.setText(ss);
        if (zgCartInfo.getCartNum() > 0) {
            tvZgDot.setVisibility(VISIBLE);
            if (zgCartInfo.getCartNum() > 99) {
                tvZgDot.setText("99+");
            } else {
                tvZgDot.setText("" + zgCartInfo.getCartNum());
            }
            iv_gwd.setImageResource(R.mipmap.live_video_product_icon);
            tvNoSelect.setVisibility(GONE);
            tvZgFd.setVisibility(VISIBLE);
            tvZgSumPrice.setVisibility(VISIBLE);
        } else {
            tvZgDot.setText("0");
            tvZgDot.setVisibility(GONE);
            iv_gwd.setImageResource(R.mipmap.zg_empty_car);
            tvNoSelect.setVisibility(VISIBLE);
            tvZgFd.setVisibility(GONE);
            tvZgSumPrice.setVisibility(GONE);
        }

        if (zgCartInfo.getNeedNumToBuy() > 0) {
            if (zgCartInfo.getCartNum() > 0){
                tv_jiesuan.setText("还差"+zgCartInfo.getNeedNumToBuy() + "件起购");
            }else{
                tv_jiesuan.setText(zgCartInfo.getNeedNumToBuy() + "件起购");
            }
            tv_jiesuan.setBackgroundResource(R.color.colorBlackText2);
        } else {
            tv_jiesuan.setText("去结算");
            tv_jiesuan.setBackgroundResource(R.color.colorRedMain);
        }

    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    //清除失效商品
                    if (zgCallback != null) {
                        zgCallback.clearCarList(true);
                    }
                    break;
                case 2://数量+-
                    TradeGoodsCar car = (TradeGoodsCar) msg.obj;
                    if (zgCallback != null) {
                        zgCallback.changeCarCount(car.getSkuId() + "",car.getGoodsId()+"", msg.arg1);
                    }
                    if (car.getNum() <= 0) {
                        carList.remove(car);
                        setCartList(carList);
                    }
                    break;

            }
        }
    };

    @OnClick({R.id.view_bg, R.id.tv_zg_delete, R.id.tv_jiesuan, R.id.ll_zg_bottom})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.view_bg:
                dismissDialog();
                break;
            case R.id.tv_zg_delete:
                new ConfirmDialog((Activity) context, "清空购物车中所有商品？", new ConfirmOKI() {
                    @Override
                    public void executeOk() {
                        if (zgCallback != null) {
                            zgCallback.clearCarList(false);
                            dismissDialog();
                        }
                    }

                    @Override
                    public void executeCancel() {

                    }
                });
                break;
            case R.id.tv_jiesuan:
                if (zgCallback != null) {//等具体参数
                    if (parseParams().size()>0&&canJiesuan()){
                        Map<String,Object> pa=new HashMap<>();
                        pa.put("tradeSkuVO",parseParams());
                        pa.put("ifFromCart",true);
                        zgCallback.jiesuan(pa);
                        dismissDialog();
                    }

                }
                break;
            case R.id.ll_zg_bottom:
                if (llZgDialog.getVisibility()==VISIBLE){
                    dismissDialog();
                }else{
                    if (zgCallback != null&&!"0".equals(tvZgDot.getText().toString())) {
                        needShowDialog=true;
                        zgCallback.reqCarList();
                    }
                }
                break;
        }
    }

    private boolean canJiesuan(){
        if ("去结算".equals(tv_jiesuan.getText().toString())){
            return true;
        }else {
            return false;
        }
    }

    private List<Map<String,Object>> parseParams(){
        List<Map<String,Object>> list=new ArrayList<>();
        for (TradeGoodsCar car:carList){
            Map<String,Object> param=new HashMap<>();
            param.put("skuId",car.getSkuId());
            param.put("num",car.getNum());
            list.add(param);
        }
        return list;
    }

    public void showDialog() {
        if (needShowDialog){
            llZgDialog.setVisibility(VISIBLE);
            needShowDialog=false;
        }
    }


    public void dismissDialog() {
        llZgDialog.setVisibility(GONE);
    }
}
