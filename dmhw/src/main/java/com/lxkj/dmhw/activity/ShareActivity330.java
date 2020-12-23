package com.lxkj.dmhw.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.adapter.ShareAdapter;
import com.lxkj.dmhw.adapter.ShareAdapter330;
import com.lxkj.dmhw.adapter.ShareCheck;
import com.lxkj.dmhw.bean.H5Link;
import com.lxkj.dmhw.bean.ShareInfo;
import com.lxkj.dmhw.bean.ShareParams;
import com.lxkj.dmhw.bean.Template;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.defined.LinearItemDecoration;
import com.lxkj.dmhw.dialog.ShareChangeMainImageDialog;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.MyLayoutManager;
import com.lxkj.dmhw.utils.Share;
import com.lxkj.dmhw.utils.ToastUtil;
import com.lxkj.dmhw.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShareActivity330 extends BaseActivity implements  View.OnTouchListener {

    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.share_check_text)
    TextView shareCheckText;
    @BindView(R.id.share_recycler)
    RecyclerView shareRecycler;
    @BindView(R.id.share_edit)
    EditText shareEdit;
    @BindView(R.id.share_copy_text)
    LinearLayout shareCopyText;
    @BindView(R.id.share_wechat)
    LinearLayout shareWechat;
    @BindView(R.id.share_wechat_friends)
    LinearLayout shareWechatFriends;
    @BindView(R.id.share_qq)
    LinearLayout shareQQ;
    @BindView(R.id.share_qq_zone)
    LinearLayout shareQQZone;
    @BindView(R.id.dialog_app_save_layout)
    LinearLayout dialog_app_save_layout;
    @BindView(R.id.share_erweima_text)
    TextView share_erweima_text;
    @BindView(R.id.bar)
    View bar;


    @BindView(R.id.image_one_layout)
    LinearLayout image_one_layout;
    @BindView(R.id.image_two_layout)
    LinearLayout image_two_layout;
    @BindView(R.id.image_three_layout)
    LinearLayout image_three_layout;
    @BindView(R.id.image_four_layout)
    LinearLayout image_four_layout;

    @BindView(R.id.image_one)
    ImageView image_one;
    @BindView(R.id.image_two)
    ImageView image_two;
    @BindView(R.id.image_three)
    ImageView image_three;
    @BindView(R.id.image_four)
    ImageView image_four;

    @BindView(R.id.text_one)
    TextView text_one;
    @BindView(R.id.text_two)
    TextView text_two;
    @BindView(R.id.text_three)
    TextView text_three;
    @BindView(R.id.text_four)
    TextView text_four;
    @BindView(R.id.share_bottom_alert_layout)
    RelativeLayout share_bottom_alert_layout;
    private ShareAdapter330 adapter;
    private int number = 1;
    private int share = 0;
    private boolean imageShare=true;// 必须带二维码
    private String countersign;
    // 原价
    private String price;
    // 商品信息
    private ShareInfo shareInfo = new ShareInfo();
    // 分享模板
    private Template template;
    /**
     * 微信好友
     */
    private final int WE_CHAT = 0;
    /**
     * 微信朋友圈
     */
    private final int FRIENDS_CIRCLE = 1;
    /**
     * QQ好友
     */
    private final int QQ = 2;
    /**
     * QQ空间
     */
    private final int QQ_ZONE = 3;


    private String taokouling="";//淘口令替换
    private String duanlianjie="";//短连接替换
    private String erweima="";//二维码替换

    private boolean taoCheck=false;
    private boolean lianjieCheck=false;
    private boolean erweimaCheck=true;


    private String tempReplaceStr="";

    ArrayList<ShareCheck> tempImageList;

    ShareChangeMainImageDialog shareChangeMainImageDialog;
    private int mainImagePos=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share330);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        template = DateStorage.getTemplate();
        shareInfo.setName(getIntent().getExtras().getString("name"));
        shareInfo.setSales(getIntent().getExtras().getString("sales"));
        shareInfo.setMoney(getIntent().getExtras().getString("money"));
        shareInfo.setShopprice(getIntent().getExtras().getString("shopprice"));
        shareInfo.setDiscount(getIntent().getExtras().getString("discount"));
        shareInfo.setShortLink(getIntent().getExtras().getString("shortLink"));
        shareInfo.setRecommended(getIntent().getExtras().getString("recommend"));
        shareInfo.setCommission(getIntent().getExtras().getString("commission"));
        shareInfo.setCheck(getIntent().getExtras().getBoolean("isCheck"));
        if (getIntent().getExtras().getString("money")!=null&&getIntent().getExtras().getString("discount")!=null) {
            price = Utils.getFloat(Float.parseFloat(getIntent().getExtras().getString("money")) + Float.parseFloat(getIntent().getExtras().getString("discount"))) + "";
        }
        countersign = getIntent().getExtras().getString("countersign");
        shareCheckText.setText("已选" + number + "张");
        // 设置Recycler
        shareRecycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(this, true));
        shareRecycler.addItemDecoration(new LinearItemDecoration(Utils.dipToPixel(R.dimen.dp_10), 0, 0, 0));
        adapter = new ShareAdapter330(this);
        shareRecycler.setAdapter(adapter);
        try{
        tempImageList=(ArrayList<ShareCheck>) getIntent().getSerializableExtra("image");
        tempImageList.get(0).setIsMainFirstQr("1");
        tempImageList.get(0).setMainImageCheck(1);
        tempImageList.get(0).setIsFirstQr("1");
        shareChangeMainImageDialog=new ShareChangeMainImageDialog(this,tempImageList);
        adapter.setNewData(tempImageList);
        taokouling= template.getSharetpwdnew().replace("{淘口令}", countersign).replace("{标题}", shareInfo.getName()).replace("{原价}", price).replace("{券后价}", shareInfo.getMoney()).replace("{券额}", shareInfo.getDiscount()).replace("{推荐内容}", shareInfo.getRecommended()).replace("{佣金}", shareInfo.getCommission());
        duanlianjie=template.getSharelinknew().replace("{短链接}", shareInfo.getShortLink()).replace("{标题}", shareInfo.getName()).replace("{原价}", price).replace("{券后价}", shareInfo.getMoney()).replace("{券额}", shareInfo.getDiscount()).replace("{推荐内容}", shareInfo.getRecommended()).replace("{佣金}", shareInfo.getCommission());

        if (getIntent().getBooleanExtra("noprice",false)){
            erweima=shareInfo.getRecommended();
        }else {
            erweima = template.getSharetextnew().replace("{标题}", shareInfo.getName()).replace("{原价}", price).replace("{券后价}", shareInfo.getMoney()).replace("{券额}", shareInfo.getDiscount()).replace("{推荐内容}", shareInfo.getRecommended()).replace("{佣金}", shareInfo.getCommission());
        }
        }catch (Exception e){

        }
        radioGroupChange(DateStorage.getShareTextType());//仅文案

        adapter.setOnClickListener(new ShareAdapter330.OnClickListener() {
            @Override
            public void onShareClick(int num) {
                number = num;
                shareCheckText.setText("已选" + number + "张");
            }
        });
       shareEdit.setOnTouchListener(this);


        shareChangeMainImageDialog.setOnClickListener(new ShareChangeMainImageDialog.OnBtnClickListener() {
            @Override
            public void onClickMainImage(int pos) {
                mainImagePos=pos;
                adapter.getData().get(mainImagePos).setCheck(1);
                adapter.getData().get(mainImagePos).setIsFirstQr("1");
                adapter.tempList.clear();
                for (int i=0;i<adapter.getData().size();i++) {
                    if (i!=mainImagePos){
                    adapter.getData().get(i).setIsFirstQr("0");
                    }
                    if (adapter.getData().get(i).getCheck()==1&&adapter.getData().get(i).getIsFirstQr().equals("0")){
                        adapter.tempList.add(adapter.getData().get(i));
                    }
                    if (adapter.getData().get(i).getCheck()==1&&adapter.getData().get(i).getIsFirstQr().equals("1")){
                        adapter.tempList.add(0,adapter.getData().get(i));
                    }
                }
                adapter.notifyDataSetChanged();
                shareCheckText.setText("已选" +adapter.tempList.size() + "张");
            }
        });


    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //触摸的是EditText并且当前EditText可以滚动则将事件交给EditText处理；否则将事件交由其父类处理
        if ((view.getId() == R.id.share_edit && canVerticalScroll(shareEdit))) {
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


    @Override
    public void mainMessage(Message message) {
        if (message.what == LogicActions.ShareStatusSuccess&&message.arg1==5) {
            paramMap.clear();
            paramMap.put("type", "shareshop");
            NetworkRequest.getInstance().POST(handler, paramMap, "DayBuy", HttpCommon.finishTask);
        }
    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        //完成任务
        if (message.what == LogicActions.DayBuySuccess) {
            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ShareFinish"), false, 0);
        }
        // 获取H5地址
        if (message.what == LogicActions.GetH5Success) {
            ArrayList<H5Link> h5Links = (ArrayList<H5Link>) message.obj;
            if (h5Links.size()>0) {
                startActivity(new Intent(this, WebViewActivity.class).putExtra("isTitle", true).putExtra(Variable.webUrl, h5Links.get(0).getUrl()));
            }
            }
    }

    @OnClick({
            R.id.back, R.id.share_copy_text, R.id.share_wechat, R.id.share_wechat_friends,
            R.id.share_qq, R.id.share_qq_zone,R.id.dialog_app_save_layout,R.id.image_two_layout,R.id.image_one_layout,R.id.image_three_layout,R.id.image_four_layout,R.id.share_bottom_alert_layout,R.id.share_erweima_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                Utils.deleteDir(Variable.sharePath);
                isFinish();
                break;
            case R.id.share_bottom_alert_layout:
                // 获取H5地址
                paramMap.clear();
                paramMap.put("type", "6");
                NetworkRequest.getInstance().POST(handler, paramMap, "GetH5", HttpCommon.GetH5);
                break;
            case R.id.share_copy_text:
                Utils.copyText(shareEdit.getText().toString());
                ToastUtil.showImageToast(this,"复制成功",R.mipmap.toast_img);
                break;
            case R.id.share_wechat:
                if (!Utils.checkPermision(this,1001,false)) {
                    return;
                }
                if (number == 0) {
                    ToastUtil.showImageToast(this,"最少选择一张图片",R.mipmap.toast_error);
                } else {
                    Utils.copyText(shareEdit.getText().toString());
                    ToastUtil.showImageToast(this,"复制成功",R.mipmap.toast_img);
                    share(WE_CHAT);
                }
                break;
            case R.id.share_wechat_friends:
                if (!Utils.checkPermision(this,1001,false)) {
                    return;
                }
                if (number == 0) {
                    ToastUtil.showImageToast(this,"最少选择一张图片",R.mipmap.toast_error);
                } else {
                    Utils.copyText(shareEdit.getText().toString());
                    ToastUtil.showImageToast(this,"复制成功",R.mipmap.toast_img);
                    share(FRIENDS_CIRCLE);
                }
                break;
            case R.id.share_qq:
                if (!Utils.checkPermision(this,1001,false)) {
                    return;
                }
                if (number == 0) {
                    ToastUtil.showImageToast(this,"最少选择一张图片",R.mipmap.toast_error);
                } else {
                    Utils.copyText(shareEdit.getText().toString());
                    ToastUtil.showImageToast(this,"复制成功",R.mipmap.toast_img);
                    share(QQ);
                }
                break;
            case R.id.share_qq_zone:
                if (!Utils.checkPermision(this,1001,false)) {
                    return;
                }
                if (number == 0) {
                    ToastUtil.showImageToast(this,"最少选择一张图片",R.mipmap.toast_error);
                } else {
                    Utils.copyText(shareEdit.getText().toString());
                    ToastUtil.showImageToast(this,"复制成功",R.mipmap.toast_img);
                    share(QQ_ZONE);
                }
                break;
                //淘口令
            case R.id.image_two_layout:
                if (getTypeUnion()==5&&taoCheck){
                    //提示不能全不选
                    ToastUtil.showImageToast(this,"最少选择一个商品文案",R.mipmap.toast_error);
                    return;
                }
                if (taoCheck){
                    taoCheck=false;
                }else{
                    taoCheck=true;
                }
                if (lianjieCheck){
                 taoCheck=true;
                 lianjieCheck=false;
                }
                radioGroupChange(getTypeUnion());
                break;
                //短连接
            case R.id.image_three_layout:
                if (getTypeUnion()==5&&lianjieCheck){
                    //提示不能全不选
                    ToastUtil.showImageToast(this,"最少选择一个商品文案",R.mipmap.toast_error);
                    return;
                }
                if (lianjieCheck){
                    lianjieCheck=false;
                }else{
                    lianjieCheck=true;
                }
                if (taoCheck){
                    taoCheck=false;
                    lianjieCheck=true;
                }
                radioGroupChange(getTypeUnion());
                break;
                //仅文案
            case R.id.image_four_layout:
                if (getTypeUnion()==5&&erweimaCheck){
                    //提示不能全不选
                    ToastUtil.showImageToast(this,"最少选择一个商品文案",R.mipmap.toast_error);
                    return;
                }
                if (erweimaCheck){
                 erweimaCheck=false;
                }else{
                  erweimaCheck=true;
                }
                radioGroupChange(getTypeUnion());
                break;
             //更换主图
            case R.id.share_erweima_text:
                shareChangeMainImageDialog.showShareMainImageDialog(shareInfo,mainImagePos,"tb");
                break;
                //批量存图
            case R.id.dialog_app_save_layout:
                if (!Utils.checkPermision(this,1001,false)) {
                    return;
                }
                if (number == 0) {
                    ToastUtil.showImageToast(this,"最少选择一张图片",R.mipmap.toast_error);
                } else {
                    showDialog();
                    ShareParams params = new ShareParams();
                    ArrayList<ShareInfo> shareInfos = new ArrayList<>();
                    shareInfos.add(shareInfo);
                    params.setShareInfo(shareInfos);
                    ArrayList<String> images = new ArrayList<>();
                    for (int i=0;i<adapter.tempList.size();i++) {
                        images.add(adapter.tempList.get(i).getImage());
                    }
                    params.setImage(images);
                    for (int i = 0; i < images.size(); i++) {
                        int finalI = i;
                        Glide.with(this).asBitmap().load(images.get(finalI)).into(new SimpleTarget<Bitmap>(){
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                if (finalI==0){
                                            Utils.saveFile(Variable.imagesPath, Utils.jointBitmapNew(ShareActivity330.this, resource,params.getShareInfo().get(finalI)), 100, true);
                                        }else{
                                            Utils.saveFile(Variable.imagesPath, resource, 100, true);
                                        }
                            }
                        });
                    }
                    dismissDialog();
                    ToastUtil.showImageToast(this,"图片已保存至相册",R.mipmap.toast_img);
                }
                break;
        }
    }


    /**
     * 监听返回键
     */
    @Override
    public void onBackPressedSupport() {
        Utils.deleteDir(Variable.sharePath);
        isFinish();
    }

    /**
     * 分享
     */
    private void share(int type) {
        ShareParams params = new ShareParams();
        params.setShareTag(5);
        if (share == 2) {// 带二维码
            params.setSplice(Share.SPLICE_ONE);
        } else {
            if (imageShare) {// 带二维码
                params.setSplice(Share.SPLICE_ONE);
            } else {// 无二维码
                params.setSplice(Share.SPLICE_NO);
            }
        }
        ArrayList<ShareInfo> shareInfos = new ArrayList<>();
        shareInfos.add(shareInfo);
        params.setShareInfo(shareInfos);
        ArrayList<String> image = new ArrayList<>();
        for (int i=0;i<adapter.tempList.size();i++) {
           image.add(adapter.tempList.get(i).getImage());
        }
        params.setImage(image);
        switch (type) {
            case WE_CHAT:// 微信好友
                Share.getInstance(0).WeChat(params, true);
                break;
            case FRIENDS_CIRCLE:// 微信朋友圈
                Share.getInstance(0).WeChat(params, false);
                break;
            case QQ:// QQ好友
                Share.getInstance(0).QQ(params, true);
                break;
            case QQ_ZONE:// QQ空间
                params.setContent("");
                Share.getInstance(0).QQ(params, false);
                break;
        }
    }

    //处理点击事件
    private void radioGroupChange(int textType){
        if (textType==5){
            //提示不能全不选
            ToastUtil.showImageToast(this,"最少选择一个商品文案",R.mipmap.toast_error);
             return;
        }
        DateStorage.setShareTextType(textType);
        image_four.setImageResource(R.mipmap.share_copywriting_check_default);
        image_two.setImageResource(R.mipmap.share_copywriting_check_default);
        image_three.setImageResource(R.mipmap.share_copywriting_check_default);
        text_four.setTextColor(Color.parseColor("#FF333333"));
        text_two.setTextColor(Color.parseColor("#FF333333"));
        text_three.setTextColor(Color.parseColor("#FF333333"));

        switch (textType){
            //单独淘口令
            case 0:
                taoCheck=true;
                lianjieCheck=false;
                erweimaCheck=false;
                image_two.setImageResource(R.mipmap.share_copywriting_check_selected);
                text_two.setTextColor(Color.parseColor("#000000"));
                tempReplaceStr = taokouling.replace("---------------\r\n","");
                shareEdit.setText(tempReplaceStr);
                break;
                //单独短连接
            case 1:
                taoCheck=false;
                lianjieCheck=true;
                erweimaCheck=false;
                image_three.setImageResource(R.mipmap.share_copywriting_check_selected);
                text_three.setTextColor(Color.parseColor("#000000"));
                tempReplaceStr = duanlianjie.replace("---------------\r\n","");
                shareEdit.setText(tempReplaceStr);
                break;
                //单独文案
            case 2:
                taoCheck=false;
                lianjieCheck=false;
                erweimaCheck=true;
                image_four.setImageResource(R.mipmap.share_copywriting_check_selected);
                text_four.setTextColor(Color.parseColor("#000000"));
                if(shareInfo.getRecommended().equals("")){
                    tempReplaceStr = erweima.replace("\r\n-------------\r\n","");
                    shareEdit.setText(tempReplaceStr);
                }else{
                    shareEdit.setText(erweima);
                }
                break;
            //文案加淘口令
            case 3:
                taoCheck=true;
                lianjieCheck=false;
                erweimaCheck=true;
                image_two.setImageResource(R.mipmap.share_copywriting_check_selected);
                text_two.setTextColor(Color.parseColor("#000000"));
                image_four.setImageResource(R.mipmap.share_copywriting_check_selected);
                text_four.setTextColor(Color.parseColor("#000000"));
                if(shareInfo.getRecommended().equals("")){
                    shareEdit.setText(erweima+taokouling);
                }else{
                    shareEdit.setText(erweima+"\n---------------\n"+taokouling);
                }
                break;
            //文案加链接
            case 4:
                taoCheck=false;
                lianjieCheck=true;
                erweimaCheck=true;
                image_three.setImageResource(R.mipmap.share_copywriting_check_selected);
                text_three.setTextColor(Color.parseColor("#000000"));
                image_four.setImageResource(R.mipmap.share_copywriting_check_selected);
                text_four.setTextColor(Color.parseColor("#000000"));
                if(shareInfo.getRecommended()!=null&&shareInfo.getRecommended().equals("")){
                    shareEdit.setText(erweima+duanlianjie);
                }else{
                    shareEdit.setText(erweima+"\n---------------\n"+duanlianjie);
                }
                break;
        }
        shareEdit.setSelection(shareEdit.getText().length());//将光标移至文字末尾
    }

    private int getTypeUnion(){
        if (erweimaCheck&&!taoCheck&&!lianjieCheck){
            return 2;
        }
        if (erweimaCheck&&!taoCheck&&lianjieCheck){
            return 4;
        }
       if (erweimaCheck&&taoCheck&&!lianjieCheck){
           return 3;
       }
        if (!erweimaCheck&&taoCheck){
           return 0;
        }
        if (!erweimaCheck&&lianjieCheck){
            return 1;
        }
        if (!erweimaCheck&&!lianjieCheck&&!taoCheck){
            return 5;
        }
        return 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Utils.deleteDir(Variable.sharePath);
    }
}
