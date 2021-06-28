package com.lxkj.dmhw.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ali.auth.third.login.LoginService;
import com.ali.auth.third.ui.context.CallbackContext;
import com.alibaba.baichuan.trade.biz.login.AlibcLogin;
import com.alibaba.baichuan.trade.biz.login.AlibcLoginCallback;
import com.android.sdklibrary.admin.KDFInterface;
import com.android.sdklibrary.admin.OnComplete;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.adapter.MarketListAdapter;
import com.lxkj.dmhw.bean.AppInfo;
import com.lxkj.dmhw.bean.UserInfo;
import com.lxkj.dmhw.bean.Withdrawals;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.dialog.ClearCacheDialog;
import com.lxkj.dmhw.dialog.DownSwitchDialog;
import com.lxkj.dmhw.dialog.ImageShareDialog;
import com.lxkj.dmhw.dialog.NameSettingDialog;
import com.lxkj.dmhw.dialog.ShareDialog;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.MyLayoutManager;
import com.lxkj.dmhw.utils.ToastUtil;
import com.lxkj.dmhw.utils.Utils;
import com.qiyukf.unicorn.api.Unicorn;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * 设置页
 */
public class SettingActivity340 extends BaseActivity implements GalleryFinal.OnHanlderResultCallback {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.setting_avatar_image)
    ImageView settingAvatarImage;
    @BindView(R.id.setting_avatar_btn)
    RelativeLayout settingAvatarBtn;
    @BindView(R.id.setting_name_text)
    TextView settingNameText;
    @BindView(R.id.setting_name_btn)
    LinearLayout settingNameBtn;
    @BindView(R.id.setting_customer_btn)
    RelativeLayout settingCustomerBtn;
    @BindView(R.id.setting_ali_pay_text)
    TextView settingAliPayText;
//    @BindView(R.id.setting_ali_pay_right_text)
//    TextView settingAliPayRightText;
    @BindView(R.id.setting_ali_pay_btn)
    RelativeLayout settingAliPayBtn;

    @BindView(R.id.setting_phone_btn)
    RelativeLayout settingPhoneBtn;

    @BindView(R.id.setting_share_right_text)
    TextView settingShareRightText;
    @BindView(R.id.setting_share_btn)
    RelativeLayout settingShareBtn;
    @BindView(R.id.setting_image_right_text)
    TextView settingImageRightText;
    @BindView(R.id.setting_image_btn)
    RelativeLayout settingImageBtn;
    @BindView(R.id.setting_out_btn)
    LinearLayout settingOutBtn;
    @BindView(R.id.setting_push_btn)
    RelativeLayout settingPushBtn;
    @BindView(R.id.setting_down_right_text)
    TextView settingDownRightText;
    @BindView(R.id.setting_down_btn)
    RelativeLayout settingDownBtn;

    @BindView(R.id.push_id)
    TextView push_id;

    @BindView(R.id.service_name)
    TextView service_name;
    @BindView(R.id.phone_txt)
    TextView phone_txt;
    @BindView(R.id.setting_taoabo_text)
    TextView setting_taoabo_text;
    @BindView(R.id.about_cache)
    RelativeLayout about_cache;
    @BindView(R.id.about_cache_text)
    TextView about_cache_text;
    @BindView(R.id.setting_pdd_text)
    TextView setting_pdd_text;
    @BindView(R.id.setting_pdd_auth)
    RelativeLayout setting_pdd_auth;

    private LoginService loginService;
    // 昵称设置
    private NameSettingDialog dialog;
    // 微信SDK
    private IWXAPI api;
    // 分享设置
    private boolean isCheck = true;
    // 选取图像回调
    private int REQUEST_IMAGE = 1024;

    private String PddShortUrl,pddUrl;

    private boolean refreshPddAuth=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        // 初始化微信SDK
        api = Utils.weChat(this, false);
         Utils.displayImageCircular(this, login.getUserpicurl(), settingAvatarImage);
        // 设置昵称
        settingNameText.setText(login.getUsername());
        service_name.setText(login.getUserwx());
        phone_txt.setText(login.getUserphone());
        // 初始化Dialog
        dialog = new NameSettingDialog(this, "");

        //判断推送是否开启
        if (Utils.isNotification(this)) {
            DateStorage.setPush("已开启");
        } else {
            DateStorage.setPush("未开启");
        }
        push_id.setText(DateStorage.getPush());


        if (login.getIsauth().equals("1")){
            setting_taoabo_text.setText("已授权");
        }

        try {
            about_cache_text.setText(Utils.getTotalCacheSize(this));
        } catch (Exception e) {
        }
        // 网络请求
        request();
    }

    @Override
    public void mainMessage(Message message) {
        if (message.what == LogicActions.ModifyUserInfoSuccess) {
            UserInfo login = DateStorage.getInformation();
            if (login.getUserpicurl().equals("")) {
                Utils.displayImageCircular(this, Variable.VatarImagepath, settingAvatarImage);
            }
            else {
                Utils.displayImageCircular(this, login.getUserpicurl(), settingAvatarImage);
            }
            settingNameText.setText(login.getUsername());
            service_name.setText(login.getUserwx());
            phone_txt.setText(login.getUserphone());
            if (login.getIsauth().equals("1")){
                setting_taoabo_text.setText("已授权");
            }
        }
        if (message.what == LogicActions.WithdrawalsDialogSuccess) {
            ArrayList<String> arrayList = (ArrayList<String>) message.obj;
            paramMap.clear();
            paramMap.put("userid", login.getUserid());
            paramMap.put("type", "0");
            paramMap.put("alipayacount", arrayList.get(0));
            paramMap.put("alipayname", arrayList.get(1));
            NetworkRequest.getInstance().POST(handler, paramMap, "BindingAlipay", HttpCommon.BindingAlipay);
            showDialog();
        }
        if (message.what == LogicActions.UserInfoActivitySuccess) {
            Variable.BindingWeChat = true;
            Variable.isBindingWeChatFromSetting=true;
            Variable.weChatCheck = true;
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = Variable.weChatState;
            api.sendReq(req);
            showDialog();
        }
        if (message.what == LogicActions.ImageShareDialogSuccess) {
            if (message.obj.equals("带二维码")) {
                paramMap.clear();
                paramMap.put("userid", login.getUserid());
                paramMap.put("sharemodel", "1");
            } else {
                paramMap.clear();
                paramMap.put("userid", login.getUserid());
                paramMap.put("sharemodel", "0");
            }
            NetworkRequest.getInstance().POST(handler, paramMap, "SetShareModel", HttpCommon.SetShareModel);
            showDialog();
            settingImageRightText.setText(message.obj + "");
        }
        if (message.what == LogicActions.ShareDialogSuccess) {
            if (message.obj.equals("短链接")) {
                paramMap.clear();
                paramMap.put("userid", login.getUserid());
                paramMap.put("shopshare", "1");
                isCheck = true;
            } else if (message.obj.equals("淘口令")) {
                paramMap.clear();
                paramMap.put("userid", login.getUserid());
                paramMap.put("shopshare", "0");
                isCheck = true;
            } else if (message.obj.equals("仅文案")) {
                paramMap.clear();
                paramMap.put("userid", login.getUserid());
                paramMap.put("shopshare", "2");
                isCheck = false;
                settingShareRightText.setText("带二维码");
            }else{//仅淘口令
                paramMap.clear();
                paramMap.put("userid", login.getUserid());
                paramMap.put("shopshare", "3");
                isCheck = true;
            }
            NetworkRequest.getInstance().POST(handler, paramMap, "SetShare", HttpCommon.SetShare);
            showDialog();
            settingShareRightText.setText(message.obj + "");
        }
        if (message.what == LogicActions.NameSettingDialogSuccess) {
            paramMap.clear();
            paramMap.put("userid", login.getUserid());
            paramMap.put("username", message.obj + "");
            paramMap.put("userpicurl", "");
            NetworkRequest.getInstance().POST(handler, paramMap, "UserInfo", HttpCommon.UserInfo);
            showDialog();
        }
        if (message.what == LogicActions.DownSwitchDialogSuccess) {
            settingDownRightText.setText((String) message.obj);
            if (message.obj.equals("显示")) {
                paramMap.clear();
                paramMap.put("domainapp", "1");
            } else {
                paramMap.clear();
                paramMap.put("domainapp", "0");
            }
            NetworkRequest.getInstance().POST(handler, paramMap, "SetDown", HttpCommon.SetDown);
        }

        //重新获取绑定支付宝信息
        if (message.what == LogicActions.AmendAliPaySuccess) {
            paramMap.clear();
            paramMap.put("userid", login.getUserid());
            NetworkRequest.getInstance().POST(handler, paramMap, "GetAlipay", HttpCommon.GetAlipay);
        }
        if (message.what == LogicActions.UpdateUserInfoSuccess) {
            paramMap.clear();
            paramMap.put("userid", login.getUserid());
            NetworkRequest.getInstance().POST(handler, paramMap, "GetUserInfo", HttpCommon.GetUserInfo);
        }

        //淘宝授权刷新用户信息
        if (message.what == LogicActions.CloseTbaoAuthDialogSuccess) {
            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("UpdateUserInfo"), "", 0);
        }
        //注销账号
        if (message.what == LogicActions.LoginStatusSuccess) {
            if ((Boolean) message.obj) {
                isFinish();
            }
        }
    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        dismissDialog();
        if (message.what == LogicActions.GetAlipaySuccess) {
            if (message.obj.equals("0")) {
                // 获取提现信息
                paramMap.clear();
                paramMap.put("userid", login.getUserid());
                NetworkRequest.getInstance().POST(handler, paramMap, "Withdrawals", HttpCommon.Withdrawals);
            } else {
                settingAliPayText.setText("未设置");
            }
        }
        if (message.what == LogicActions.GetShareModelSuccess) {
            if ((Boolean) message.obj) {
                settingImageRightText.setText("带二维码");
            } else {
                settingImageRightText.setText("无二维码");
            }
        }
        if (message.what == LogicActions.GetShareSuccess) {
            if (message.obj.equals("0")) {
                settingShareRightText.setText("淘口令");
                isCheck = true;
            } else if (message.obj.equals("1")) {
                settingShareRightText.setText("短链接");
                isCheck = true;
            } else if(message.obj.equals("2")) {
                settingShareRightText.setText("仅文案");
                settingImageRightText.setText("带二维码");
                isCheck = false;
            }else{
                settingShareRightText.setText("仅淘口令");
                isCheck = true;
            }
        }
        if (message.what == LogicActions.WithdrawalsSuccess) {
            Withdrawals withdrawals = (Withdrawals) message.obj;
            settingAliPayText.setText(withdrawals.getAlipayacount());
        }
        if (message.what == LogicActions.UserInfoSuccess) {
            toast(message.obj + "");
            paramMap.clear();
            paramMap.put("userid", login.getUserid());
            NetworkRequest.getInstance().POST(handler, paramMap, "GetUserInfo", HttpCommon.GetUserInfo);
        }
        if (message.what == LogicActions.GetUserInfoSuccess) {
            UserInfo login = (UserInfo) message.obj;
            login.setUserid(login.getUserid());
            if (login.getIsauth().equals("1")){
                setting_taoabo_text.setText("已授权");
            }
            // 储存用户信息
            DateStorage.setInformation(login);
            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ModifyUserInfo"), "", 0);
        }
        if (message.what == LogicActions.BindingAlipaySuccess) {
            toast(message.obj + "");
            // 判断是够绑定过支付宝
            paramMap.clear();
            paramMap.put("userid", login.getUserid());
            NetworkRequest.getInstance().POST(handler, paramMap, "GetAlipay", HttpCommon.GetAlipay);
        }
        if (message.what == LogicActions.SetShareModelSuccess) {
            toast(message.obj + "");
        }
        if (message.what == LogicActions.SetShareSuccess) {
            toast(message.obj + "");
        }
        if (message.what == LogicActions.GetDownSuccess) {
            String content = (String) message.obj;
            if (content.equals("0")) {
                settingDownRightText.setText("不显示下载入口");
            } else {
                settingDownRightText.setText("显示下载入口");
            }
        }
        if (message.what == LogicActions.SetDownSuccess) {
            toast((String) message.obj);
        }
        //pdd授权
        if (message.what == LogicActions.PDDIsBindAuthSuccess) {
            try {
                refreshPddAuth=false;
                JSONObject jsonObject= new JSONObject(message.obj.toString());
                if (jsonObject.optString("code").equals("601")){
                 //未授权
                  setting_pdd_auth.setEnabled(true);
                  setting_pdd_text.setText("尚未授权，点此授权");
                  JSONObject data= jsonObject.getJSONObject("data");
                  PddShortUrl=data.getString("shortUrl");
                  pddUrl=data.getString("url");
                }else{
                  setting_pdd_auth.setEnabled(false);
                  setting_pdd_text.setText("已授权");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick({R.id.back, R.id.setting_avatar_btn, R.id.setting_name_btn, R.id.setting_customer_btn,
            R.id.setting_ali_pay_btn, R.id.setting_phone_btn,
            R.id.setting_push_btn, R.id.setting_share_btn, R.id.setting_image_btn, R.id.setting_out_btn,
            R.id.setting_down_btn,R.id.setting_avatar_image,R.id.setting_taobao_auth,R.id.setting_pdd_auth,R.id.about_cache,R.id.about_comment,
             R.id.setting_feedback,R.id.about_dmj})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.back:// 返回
                isFinish();
                break;
            case R.id.setting_avatar_btn:// 昵称
                intent = new Intent(SettingActivity340.this, UpdateNickActivity.class);
                intent.putExtra("type",111);
                intent.putExtra("nickname", settingNameText.getText().toString());
                intent.putExtra("title", "修改昵称");
                intent.putExtra("hint", "请输入新的昵称");
                intent.putExtra("alert", "注意：与NN俱乐部业务或品牌冲突的昵称，NN俱乐部将有权收回");
                break;
            case R.id.setting_avatar_image://头像
                try{
                  GalleryFinal.openGallerySingle(REQUEST_IMAGE, this);
                }catch (Exception e){}
                break;
            case R.id.setting_customer_btn:// 设置客服
                intent = new Intent(this, UpdateNickActivity.class);
                intent.putExtra("type",222);
                intent.putExtra("title", "设置客服");
                intent.putExtra("hint", "请输入您要作为客服的微信号");
                intent.putExtra("alert", "注意：设置成功后，该微信号将通过专属客服页面展示给您的下级，方便下级与您取得联系；若不设置，则默认显示运营商的客服微信号");
                break;
            case R.id.setting_ali_pay_btn:// 支付宝修改
                intent = new Intent(this, AlipayActivity.class);
                if (settingAliPayText.getText().equals("未设置")) {
                    intent.putExtra("isHasAlipay",false);
                } else {
                    intent.putExtra("isHasAlipay",true);
                }
                break;
            case R.id.setting_phone_btn:// 修改手机号
                intent = new Intent(this, PhoneActivity.class);
                break;
            case R.id.setting_push_btn:// 推送设置
                intent = new Intent(this, PushActivity.class);
                break;
            case R.id.setting_share_btn:// 分享设置
                ShareDialog dialog = new ShareDialog(this);
                dialog.getDialog().show();
                break;
            case R.id.setting_image_btn:// 图片模式
                if (isCheck) {
                    ImageShareDialog imageShareDialog = new ImageShareDialog(this, "ImageShareDialog");
                    imageShareDialog.showDialog();
                } else {
                    toast("仅文案默认必须带二维码");
                }
                break;
            case R.id.setting_out_btn:// 退出登录
                DateStorage.setLoginStatus(false);
                DateStorage.setMyToken("");
                DateStorage.setMicroAppId("");
                DateStorage.setLittleAppId("");
                InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("LoginStatus"), true, 100);
                logoutCreditCard();
                Unicorn.logout();//退出七鱼登录
                break;
            case R.id.setting_down_btn:// 分享中间页app下载开关
                DownSwitchDialog downSwitchDialog = new DownSwitchDialog(this, "DownSwitchDialog");
                downSwitchDialog.showDialog();
                break;
            case R.id.setting_taobao_auth:
                if (login.getIsauth().equals("0")) {
                    AlibcLogin alibcLogin = AlibcLogin.getInstance();
                    // 淘宝授权
                    if (alibcLogin.isLogin()) {
                        InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("GotbAuth"), login.getAuthurl(), 0);
                    } else {
                        alibcLogin.showLogin(new AlibcLoginCallback() {
                            @Override
                            public void onSuccess(int i, String s, String s1) {
                             InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("GotbAuth"), login.getAuthurl(), 0);
                            }

                            @Override
                            public void onFailure(int i, String s) {
                            }
                        });
                    }
                }
                break;
             //拼多多授权
            case R.id.setting_pdd_auth:
                refreshPddAuth=true;
                Utils.callMorePlatFrom(this,1,PddShortUrl,pddUrl);
                break;
            case R.id.about_cache:
                ClearCacheDialog clearCacheDialog=new ClearCacheDialog(this);
                clearCacheDialog.setOnClickListener(new ClearCacheDialog.OnBtnClickListener() {
                    @Override
                    public void onClick(int pos) {
                     if (pos==1){//确认
                         Utils.clearAllCache(SettingActivity340.this);
                         try {
                             about_cache_text.setText(Utils.getTotalCacheSize(SettingActivity340.this));
                         } catch (Exception e) {
                             ToastUtil.showImageToast(SettingActivity340.this,"清除失败",R.mipmap.toast_error);
                         }
                            ToastUtil.showImageToast(SettingActivity340.this,"清除成功",R.mipmap.toast_img);
                     }
                    }
                });
                clearCacheDialog.showDialog();
                break;
              //给个评价
            case R.id.about_comment:
                //必须打开读取应用列表的权限 否则会导致无法获取正常信息
                ArrayList<AppInfo> markets;
                ArrayList<String>  pkgList = new ArrayList<>();
                pkgList.add("com.xiaomi.market");
                pkgList.add("com.meizu.mstore");
                pkgList.add("com.huawei.appmarket");
                pkgList.add("com.oppo.market");
                pkgList.add("com.bbk.appstore");
                pkgList.add("com.lenovo.leos.appstore");
                pkgList.add("com.tencent.android.qqdownloader");
                markets= Utils.getFilterInstallMarkets(this, pkgList);
                switch (markets.size()){
                    case 0:
                        toast("您还未安装应用市场");
                        break;
                    case 1:
                        Utils.toMarket(this,Utils.getAppPakageName(),markets.get(0).getPackageName());
                        break;
                    default:
                        /**
                         *是否有应用宝
                         */
                        boolean isTencentMarket = false;
                        for (int i=0;i<markets.size();i++){
                            if (markets.get(i).getPackageName().equals("com.tencent.android.qqdownloader")){
                                isTencentMarket=true;
                                break;
                            }
                        }
                        if (isTencentMarket)
                        {
                            Utils.toMarket(this,Utils.getAppPakageName(),"com.tencent.android.qqdownloader");
                        }else{
                            showMarketListDialog(markets);
                        }

                        break;
                }

                break;

            case R.id.about_dmj://关于我们
                intent = new Intent(this, AboutActivity340.class);
                break;
            case R.id.setting_feedback://意见反馈
                intent = new Intent(this, FeedbackActivity.class);
                break;
        }


        if (intent != null) {
            if(view.getId()==R.id.setting_name_btn){
                startActivityForResult(intent, 1); //REQUESTCODE--->1
            }else{
                startActivity(intent);
            }

        }
    }

    @Override
    public void onHanlderSuccess(int requestCode, List<PhotoInfo> resultList) {
        // 处理成功
        if (requestCode == REQUEST_IMAGE) {
            String data = resultList.get(0).getPhotoPath();
            paramMap.clear();
            paramMap.put("userid", login.getUserid());
            paramMap.put("username", login.getUsername());
            if (Utils.getImageStr(data)==null){
                return;
            }
            paramMap.put("userpicurl", Utils.getImageStr(data));
            NetworkRequest.getInstance().POST(handler, paramMap, "UserInfo", HttpCommon.UserInfo);
            showDialog();
        }
    }

    @Override
    public void onHanlderFailure(int requestCode, String errorMsg) {
        // 处理失败或异常
        toast(errorMsg);
    }

    /**
     * 网络请求
     */
    private void request() {
        // 判断是够绑定过支付宝
        paramMap.clear();
        paramMap.put("userid", login.getUserid());
        NetworkRequest.getInstance().POST(handler, paramMap, "GetAlipay", HttpCommon.GetAlipay);
        // 图片分享模式
        paramMap.clear();
        paramMap.put("userid", login.getUserid());
        NetworkRequest.getInstance().POST(handler, paramMap, "GetShareModel", HttpCommon.GetShareModel);
        // 分享模式
        paramMap.clear();
        paramMap.put("userid", login.getUserid());
        NetworkRequest.getInstance().POST(handler, paramMap, "GetShare", HttpCommon.GetShare);
        // 中间页app下载开关
        paramMap.clear();
        NetworkRequest.getInstance().POST(handler, paramMap, "GetDown", HttpCommon.GetDown);
        //获取拼多多授权状态
        paramMap.clear();
        NetworkRequest.getInstance().GETNew(handler, paramMap, "PDDIsBindAuth", HttpCommon.PDDIsBindAuth);
        //用户信息
        paramMap.clear();
        paramMap.put("userid", login.getUserid());
        NetworkRequest.getInstance().POST(handler, paramMap, "GetUserInfo", HttpCommon.GetUserInfo);
        showDialog();
    }
    // 为了获取页面返回结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //百川登录须重写onActivityResult方法
        CallbackContext.onActivityResult(requestCode, resultCode, data);
               // RESULT_OK，判断另外一个activity已经结束数据输入功能，Standard activity result:
                // operation succeeded. 默认值是-1
                 if (resultCode == 2) {
                         if (requestCode == 1) {
                             settingNameText.setText(data.getStringExtra("nick"));
                           }
                    }
           }




    private void  logoutCreditCard(){
        KDFInterface.getInstance().logout(this, new OnComplete() {
            @Override
            public void onSuccess(JSONObject o) {
            }

            @Override
            public void onError(String error) {
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dismissDialog();
        push_id.setText(DateStorage.getPush());
        if (refreshPddAuth){
            //获取拼多多授权状态
            paramMap.clear();
            NetworkRequest.getInstance().GETNew(handler, paramMap, "PDDIsBindAuth", HttpCommon.PDDIsBindAuth);
        }
    }

    //弹出手机安装的应用市场列表
    private void showMarketListDialog( ArrayList<AppInfo> markets){
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(this,R.style.DialogTheme);
        //2、设置布局
        View view = View.inflate(this,R.layout.dialog_about_market_list,null);
        dialog.setContentView(view);
        RecyclerView activity_room_free_list=dialog.findViewById(R.id.activity_room_free_list);
        activity_room_free_list.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(this, false));
        MarketListAdapter marketListAdapter=new MarketListAdapter(this);
        activity_room_free_list.setAdapter(marketListAdapter);
        marketListAdapter.setNewData(markets);

        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawableResource(R.color.dialogTransparent);

        dialog.show();
        dialog.setCanceledOnTouchOutside(true);//设置点击Dialog外部任意区域关闭Dialog

        dialog.findViewById(R.id.room_back_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.free_list_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        marketListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Utils.toMarket(SettingActivity340.this, Utils.getAppPakageName(),markets.get(position).getPackageName());
            }
        });

    }
}
