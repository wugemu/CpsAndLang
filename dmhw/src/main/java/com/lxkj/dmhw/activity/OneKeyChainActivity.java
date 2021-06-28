package com.lxkj.dmhw.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.adapter.OnekeyListAdapter;
import com.lxkj.dmhw.adapter.ShareCheck;
import com.lxkj.dmhw.bean.Alibc;
import com.lxkj.dmhw.bean.CommodityDetails290;
import com.lxkj.dmhw.bean.Coupon;
import com.lxkj.dmhw.bean.CpsType;
import com.lxkj.dmhw.bean.OneKeyItem;
import com.lxkj.dmhw.bean.PJWLink;
import com.lxkj.dmhw.bean.ShareParams;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.dialog.AlipayDialog;
import com.lxkj.dmhw.dialog.BiJiaDialog;
import com.lxkj.dmhw.dialog.CouponLinkDialog;
import com.lxkj.dmhw.dialog.TaobaoAuthLoginDialog;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.GridSpacingItemDecoration;
import com.lxkj.dmhw.utils.MyLayoutManager;
import com.lxkj.dmhw.utils.NetStateUtils;
import com.lxkj.dmhw.utils.Share;
import com.lxkj.dmhw.utils.ToastUtil;
import com.lxkj.dmhw.utils.Utils;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OneKeyChainActivity extends BaseActivity implements AlibcTradeCallback,BaseQuickAdapter.OnItemClickListener,View.OnTouchListener {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;

    @BindView(R.id.chane_type_content)
    TextView chane_type_content;
    @BindView(R.id.chane_type_del)
    TextView chane_type_del;
    @BindView(R.id.paste_img)
    ImageView paste_img;

    @BindView(R.id.onekey_edit)
    EditText onekey_edit;

    @BindView(R.id.chian_btn)
    TextView chian_btn;

    @BindView(R.id.chian_success_layout)
    LinearLayout chian_success_layout;
    @BindView(R.id.copy_txt_layout)
    LinearLayout copy_txt_layout;
    @BindView(R.id.share_content_layout)
    LinearLayout share_content_layout;


    @BindView(R.id.onekey_goods_layout)
    LinearLayout onekey_goods_layout;
    @BindView(R.id.onekey_paste_layout)
    LinearLayout onekey_paste_layout;



    @BindView(R.id.onekey_recycler)
    RecyclerView onekey_recycler;


    //同一个商品页面 点击“我知道了” 不再显示弹框
    private Boolean isshowDialog=false;
    AlipayDialog dialog;
    TaobaoAuthLoginDialog Tdialog;
    // 判断事件是否是分享
    private boolean isShare=false;
    String type = "tb";
    String chainContent = "";
    CommodityDetails290 details;
    private ArrayList<String> ImageBanerList = new ArrayList<>();
    //平台类型
    private CpsType cpsType=new CpsType();

    //优惠券
    private Coupon coupon=new Coupon();
    private OnekeyListAdapter onekeyListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onkeychain);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight > 0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams);
        }
        dialog = new AlipayDialog(this);
        onekey_edit.setEnabled(false);
        onekey_edit.setOnTouchListener(this);
        onekey_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    onekey_edit.setEnabled(true);
                    chian_btn.setEnabled(true);
                    chian_btn.setText("转换为我的链接");
                    chian_btn.setTextColor(Color.parseColor("#ffffff"));
                    chian_btn.setBackgroundResource(R.drawable.bg_rect_black_20dp);
                    onekey_paste_layout.setVisibility(View.GONE);
                } else {
                    onekey_edit.setEnabled(false);
                    chian_btn.setEnabled(false);
                    onekey_goods_layout.setVisibility(View.GONE);
                    chane_type_content.setText("待转换");
                    chane_type_content.setTextColor(Color.parseColor("#999999"));
                    chian_btn.setText("转换为我的链接");
                    chian_success_layout.setVisibility(View.GONE);
                    chian_btn.setVisibility(View.VISIBLE);
                    chian_btn.setTextColor(Color.parseColor("#999999"));
                    chian_btn.setBackgroundResource(R.drawable.onekey_button_bg);
                    onekey_paste_layout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        onekey_recycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(this, false));
        onekey_recycler.addItemDecoration(new GridSpacingItemDecoration(1, Utils.dipToPixel(R.dimen.dp_10), false));
        onekeyListAdapter = new OnekeyListAdapter(this);
        onekey_recycler.setAdapter(onekeyListAdapter);
        // 监听item点击事件
        onekeyListAdapter.setOnItemClickListener(this);
        onekey_recycler.setNestedScrollingEnabled(false);
        onekey_recycler.setHasFixedSize(true);
        //解决数据加载完成后, 没有停留在顶部的问题
        onekey_recycler.setFocusable(false);

        onekeyListAdapter.setOnClickListener(new OnekeyListAdapter.OnClickListener() {
            @Override
            public void OnShareClick(CommodityDetails290 commodityDetails290) {
                details=commodityDetails290;
                JSONArray ImageArray = JSON.parseArray(details.getImageList());
                ImageBanerList.clear();
                if (ImageArray!=null&&ImageArray.size()>0){
                    for (int i=0;i<ImageArray.size();i++) {
                        ImageBanerList.add((String) ImageArray.get(i));
                    }
                }else{
                    ImageBanerList.add(details.getImageUrl());
                }
                Object CouponObject = JSON.parseObject(details.getCouponInfo(), Coupon.class);
                coupon = (Coupon) CouponObject;
                //获取平台类型
                Object CpsTypeObject = JSON.parseObject(details.getCpsType(), CpsType.class);
                cpsType = (CpsType) CpsTypeObject;
                type=commodityDetails290.getType();
                if (NetStateUtils.isNetworkConnected(OneKeyChainActivity.this)) {
                    if (DateStorage.getLoginStatus()) {
                        switch (type){
                            case "tb":
                            case "tm":
                                clickEvent=1;
                                goodsPromotion();
                                break;
                            case "pdd":
                            case "jd":
                            case "wph":
                            case "sn":
                            case "kl":
                                isShare=true;
                                getGenByGoodsId(details.getId());
                                break;
                        }
                    } else {
                        Intent intent = new Intent(OneKeyChainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }else{
                    toast("当前网络已断开，请连接网络");
                }
            }

            @Override
            public void OnBuyClick(CommodityDetails290 commodityDetails290) {
                details=commodityDetails290;
                JSONArray ImageArray = JSON.parseArray(details.getImageList());
                ImageBanerList.clear();
                if (ImageArray!=null&&ImageArray.size()>0){
                    for (int i=0;i<ImageArray.size();i++) {
                        ImageBanerList.add((String) ImageArray.get(i));
                    }
                }else{
                    ImageBanerList.add(details.getImageUrl());
                }
                Object CouponObject = JSON.parseObject(details.getCouponInfo(), Coupon.class);
                coupon = (Coupon) CouponObject;
                //获取平台类型
                Object CpsTypeObject = JSON.parseObject(details.getCpsType(), CpsType.class);
                cpsType = (CpsType) CpsTypeObject;
                type=commodityDetails290.getType();
                if (NetStateUtils.isNetworkConnected(OneKeyChainActivity.this)) {
                    if (DateStorage.getLoginStatus()) {
                        switch (type){
                            case "tb":
                            case "tm":
                                clickEvent=0;
                                goodsPromotion();
                                break;
                            case "pdd":
                            case "jd":
                            case "wph":
                            case "sn":
                            case "kl":
                                isShare=false;
                                getGenByGoodsId(details.getId());
                                break;
                        }
                    } else {
                       Intent intent = new Intent(OneKeyChainActivity.this, LoginActivity.class);
                       startActivity(intent);
                    }
                }else{
                    toast("当前网络已断开，请连接网络");
                }
            }
        });

    }


    /**
     * 失败回调
     */
    public void Failed(int position, String content, int more) {
       if (position==3) {
           ToastUtil.showImageToast(this,"转换失败",R.mipmap.toast_error);
           onekey_goods_layout.setVisibility(View.GONE);
           chane_type_content.setText("转换失败");
           chane_type_content.setTextColor(Color.parseColor("#000000"));
           chian_btn.setText("转换为我的链接");
       }
    }

    @Override
    public void mainMessage(Message message) {
//        if (message.what == LogicActions.noAuthSuccessfulSuccess) {
//            dismissDialog();
//            Tdialog = new TaobaoAuthLoginDialog(this, message.obj.toString());
//            Tdialog.showDialog();
//        }
//        if (message.what == LogicActions.CloseTbaoAuthDialogSuccess) {
//            if(Tdialog != null) {
//                //判断dialog是否正在显示
//                if(Tdialog.isShowing()) {
//                    //get the Context object that was used to great the dialog
//                    Context context = ((ContextWrapper)Tdialog.getContext()).getBaseContext();
//                    //判断是所在Activity是否已经销毁
//                    if(context instanceof Activity) {
//                        if(!((Activity)context).isFinishing() && !((Activity)context).isDestroyed())
//                            Tdialog.dismiss();
//                    } else
//                        Tdialog.dismiss();
//                }
//                Tdialog = null;
//            }
//        }
    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        if (message.what == LogicActions.ConVertTextToGoodsSuccess) {
            dismissDialog();
            ToastUtil.showImageToast(this, "转换成功", R.mipmap.toast_img);
            chane_type_content.setText("转换完成");
            chane_type_content.setTextColor(Color.parseColor("#69F539"));
            chian_success_layout.setVisibility(View.VISIBLE);
            chian_btn.setVisibility(View.GONE);
            onekey_goods_layout.setVisibility(View.VISIBLE);

            OneKeyItem oneKeyItem= (OneKeyItem) message.obj;
            if (oneKeyItem!=null){
                chainContent = oneKeyItem.getText();
                onekey_edit.setText(chainContent);
                if (oneKeyItem.getGoodsList().size() > 0) {
                    onekey_goods_layout.setVisibility(View.VISIBLE);
                    onekeyListAdapter.setNewData(oneKeyItem.getGoodsList());
                }else{
                    onekey_goods_layout.setVisibility(View.GONE);
                }
            }

        }

        //购买
        if (message.what == LogicActions.GoodsPromotionSuccess) {
            if (type.equals("tb")||type.equals("tm")) {
                dismissDialog();
                Alibc alibc = (Alibc) message.obj;
                String Couponlink = alibc.getCouponlink();
                String pid = alibc.getPid();
                if (clickEvent == 1) {//分享
                    ArrayList<ShareCheck> list = new ArrayList<>();
                    for (String item : ImageBanerList) {
                        ShareCheck share = new ShareCheck();
                        share.setImage(item);
                        share.setCheck(0);
                        list.add(share);
                    }
                    list.get(0).setCheck(1);
                    dismissDialog();
                    startActivity(new Intent(this, ShareActivity330.class)
                            .putExtra("image", list)
                            .putExtra("name", details.getName())
                            .putExtra("sales", details.getSales())
                            .putExtra("money", details.getPrice())
                            .putExtra("commission", details.getNormalCommission())
                            .putExtra("shopprice", details.getCost())
                            .putExtra("discount", details.getCoupon())
                            .putExtra("shortLink", alibc.getShareshortlink())
                            .putExtra("recommend", details.getDesc())
                            .putExtra("countersign", alibc.getPwd())
                            .putExtra("isCheck", cpsType.getCode()));
                } else {
                    if (alibc.getTips().getType().equals("hb")){
                        //红包
                        if (alibc.getTips().getKey().equals("")) {
                            DateStorage.setIsKey("");
                            Utils.Alibc(this, Couponlink, pid, this);
                        } else {
                            if (alibc.getTips().getKey().equals(DateStorage.getIsKey()) || isshowDialog) {
                                //已经点击了不再提醒
                                Utils.Alibc(this, Couponlink, pid, this);
                            } else {
                                try {
                                    CouponLinkDialog couponLinkDialog = new CouponLinkDialog(this, alibc.getTips());
                                    couponLinkDialog.showDialog();
                                    couponLinkDialog.setOnClickListener(new CouponLinkDialog.OnBtnClickListener() {
                                        @Override
                                        public void onClick(int pos) {
                                            if (pos == 0) {//不再提醒
                                                DateStorage.setIsKey(alibc.getTips().getKey());
                                            } else {
                                                isshowDialog = true;
                                            }
                                            Utils.Alibc(OneKeyChainActivity.this, Couponlink, pid, OneKeyChainActivity.this);
                                        }
                                    });
                                } catch (Exception e) {

                                }

                            }
                        }
                    }else{
                        //比价
                        if (!DateStorage.getHasBj().equals("")) {
                            Utils.Alibc(this, Couponlink, pid, this);
                        } else {
                            try {
                                BiJiaDialog biJiaDialog = new BiJiaDialog(this, alibc.getTips());
                                biJiaDialog.showDialog();
                                biJiaDialog.setOnClickListener(new BiJiaDialog.OnBtnClickListener() {
                                    @Override
                                    public void onClick(int pos) {
                                        if (pos == 0) {//不再提醒
                                            DateStorage.setHasBj("bj");
                                            Utils.Alibc(OneKeyChainActivity.this, Couponlink, pid, OneKeyChainActivity.this);
                                        } else if(pos==1){
                                            Utils.Alibc(OneKeyChainActivity.this, Couponlink, pid, OneKeyChainActivity.this);
                                        }else{
                                            Intent intent = new Intent(OneKeyChainActivity.this, AliAuthWebViewActivity.class);
                                            intent.putExtra(Variable.webUrl, alibc.getTips().getRuleurl());
                                            intent.putExtra("isTitle", true);
                                            startActivity(intent);
                                        }
                                    }
                                });
                            } catch (Exception e) {

                            }
                        }

                    }

                }

            }
        }


        //呼起PJWAPP
        if (message.what == LogicActions.GenByGoodsIdSuccess){
            PJWLink link = (PJWLink) message.obj;
            if (isShare) {
                if (details == null ) {
                    return;
                }
                ArrayList<ShareCheck> list = new ArrayList<>();
                if (ImageBanerList == null || ImageBanerList.size() == 0) {
                    return;
                }
                for (String item : ImageBanerList) {
                    ShareCheck share = new ShareCheck();
                    share.setImage(item);
                    share.setCheck(0);
                    list.add(share);
                }
                list.get(0).setCheck(1);
                if (coupon==null){
                   coupon=new Coupon();
                }
                if (type.equals("wph")) {
                    coupon.setPrice(details.getSave());
                }
                startActivity(new Intent(OneKeyChainActivity.this, SharePJWActivity330.class)
                        .putExtra("image", list)
                        .putExtra("name", details.getName())
                        .putExtra("sales", details.getSales())
                        .putExtra("money", details.getPrice())
                        .putExtra("commission", details.getNormalCommission())
                        .putExtra("shopprice", details.getCost())
                        .putExtra("discount", coupon.getPrice())
                        .putExtra("shortLink", link.getShortUrl())
                        .putExtra("recommend", details.getDesc())
                        .putExtra("type", type));
            } else {
                if (isNullOrEmpty(link.getShortUrl())) {
                    Toast.makeText(OneKeyChainActivity.this, "URL为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                int plat;
                if (type.equals("jd")) {
                    plat=2;
                } else if (type.equals("pdd")) {
                    plat=1;
                } else if(type.equals("wph")) {
                    plat=3;
                }else if(type.equals("sn")){
                    plat=5;
                }else{
                    plat=6;
                }
                Utils.callMorePlatFrom(this,plat,link.getSchemaUrl(),link.getUrl());
            }
            dismissDialog();

        }
    }

    @OnClick({R.id.back, R.id.onekey_paste_layout, R.id.chian_btn, R.id.copy_txt_layout, R.id.share_content_layout, R.id.chane_type_del})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.back:
                isFinish();
                break;
            case R.id.chane_type_del:
                onekey_edit.setText("");
                break;
            case R.id.onekey_paste_layout:
                try{
                    onekey_edit.setEnabled(true);
                    onekey_paste_layout.setVisibility(View.GONE);
                    if (manager!=null&&manager.getPrimaryClip() != null) {
                        String content = Objects.requireNonNull(manager.getPrimaryClip()).getItemAt(0).getText().toString();
                        onekey_edit.setText(content);
                    }
                }catch (Exception e){
                }
                break;
            case R.id.copy_txt_layout:
                if (onekey_edit.getText().toString().length() > 0) {
                    Utils.copyText(onekey_edit.getText().toString());
                    ToastUtil.showImageToast(this, "复制成功", R.mipmap.toast_img);
                }
                break;
            case R.id.share_content_layout:
                if (onekey_edit.getText().toString().length() > 0) {
                    ShareParams params = new ShareParams();
                    params.setContent(onekey_edit.getText().toString());
                    params.setShareTag(0);
                    params.setUrl("");
                    Share.getInstance(0).initialize(params, 1);
                }
                break;
            case R.id.chian_btn:
                //调用接口
                if (onekey_edit.getText().toString().length() > 0) {
                    showDialog();
                    chian_btn.setText("正在转换中，耐心等候...");
                    paramMap.clear();
                    paramMap.put("text", onekey_edit.getText().toString());
                    paramMap.put("promotion", "1");
                    NetworkRequest.getInstance().POST(handler, paramMap, "ConVertTextToGoods", HttpCommon.ConVertTextToGoods);
                }
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }


    private int clickEvent=0;
    private void goodsPromotion(){
        showDialog();
        paramMap.clear();
        paramMap.put("userid", login.getUserid());
        paramMap.put("shopid", details.getId());
        paramMap.put("source", details.getSource());
        paramMap.put("sourceId", details.getSourceId());
        if (coupon != null) {
            paramMap.put("couponid", coupon.getId());
        }
        if (clickEvent == 1) {//分享
            paramMap.put("tpwd", "1");
            paramMap.put("sharelink", "1");
        } else {//领券购买
            paramMap.put("tpwd", "");
            paramMap.put("sharelink", "");
        }
        NetworkRequest.getInstance().POST(handler, paramMap, "GoodsPromotion", HttpCommon.GoodsPromotion);
    }

    //pjw转链
    private void getGenByGoodsId(String id){
        showDialog();
        paramMap.clear();
        paramMap.put("goodsId", id);
        if (type.equals("jd")) {
            if (coupon != null) {
                paramMap.put("couponLink", coupon.getLink());
                NetworkRequest.getInstance().GETNew(handler, paramMap, "GenByGoodsId", HttpCommon.JDGenByGoodsId);
            }else{
                paramMap.put("couponLink", "");
                NetworkRequest.getInstance().GETNew(handler, paramMap, "GenByGoodsId", HttpCommon.JDGenByGoodsId);
            }
        }else if(type.equals("pdd")){
            if (!isShare) {
                paramMap.put("needAuth", true + "");
            }
            NetworkRequest.getInstance().GETNew(handler, paramMap, "GenByGoodsId", HttpCommon.PDDGenByGoodsId);
        }else if(type.equals("wph")){
         NetworkRequest.getInstance().GETNew(handler, paramMap, "GenByGoodsId", HttpCommon.WPHGenByGoodsId);
        }else if(type.equals("sn")){
          NetworkRequest.getInstance().GETNew(handler, paramMap, "GenByGoodsId", HttpCommon.SNGenByGoodsId);
        }else{
          NetworkRequest.getInstance().GETNew(handler, paramMap, "GenByGoodsId", HttpCommon.KLGenByGoodsId);
        }

    }

    private boolean isNullOrEmpty(String str){
        if(str == null || str.length() == 0|| "".equals(str)){
            return true;
        }
        return false;
    }
    @Override
    public void onTradeSuccess(AlibcTradeResult alibcTradeResult) {

    }

    @Override
    public void onFailure(int i, String s) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent=null;
        details=(CommodityDetails290) adapter.getData().get(position);
        if (!DateStorage.getLoginStatus()){
            intent=new Intent(this, LoginActivity.class);
        }else{
            switch (details.getType()) {
                case "tb":
                case "tm":
                    intent=new Intent(this, CommodityActivity290.class).putExtra("shopId", details.getId()).putExtra("source", details.getSource()).putExtra("sourceId", details.getSourceId());
                    break;
                case "pdd":
                    intent=new Intent(this, CommodityActivityPJW.class).putExtra("GoodsId", details.getId()).putExtra("type", "pdd");
                    break;
                case "jd":
                    intent=new Intent(this, CommodityActivityPJW.class).putExtra("GoodsId", details.getId()).putExtra("type", "jd");
                    break;
                case "wph":
                    intent=new Intent(this, CommodityActivityPJW.class).putExtra("GoodsId", details.getId()).putExtra("type", "wph");
                    break;
                case "sn":
                    intent=new Intent(this, CommodityActivityPJW.class).putExtra("GoodsId", details.getId()).putExtra("type", "sn");
                    break;
                case "kl":
                    intent=new Intent(this, CommodityActivityPJW.class).putExtra("GoodsId", details.getId()).putExtra("type", "kl");
                    break;
            }
        }
       if (intent!=null){
           startActivity(intent);
       }
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //触摸的是EditText并且当前EditText可以滚动则将事件交给EditText处理；否则将事件交由其父类处理
        if ((view.getId() == R.id.onekey_edit && canVerticalScroll(onekey_edit))) {
            view.getParent().requestDisallowInterceptTouchEvent(true);
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                view.getParent().requestDisallowInterceptTouchEvent(false);
            }
        }
        return false;
    }

    /**
     * EditText竖直方向是否可以滚动
     * @param editText 需要判断的EditText
     * @return true：可以滚动  false：不可以滚动
     */
    private boolean canVerticalScroll(EditText editText) {
        //滚动的距离
        int scrollY = editText.getScrollY();
        //控件内容的总高度
        int scrollRange = editText.getLayout().getHeight();
        //控件实际显示的高度
        int scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop() -editText.getCompoundPaddingBottom();
        //控件内容总高度与实际显示高度的差值
        int scrollDifference = scrollRange - scrollExtent;

        if(scrollDifference == 0) {
            return false;
        }

        return (scrollY > 0) || (scrollY < scrollDifference - 1);
    }

}
