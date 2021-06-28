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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxkj.dmhw.adapter.MarketListAdapter;
import com.lxkj.dmhw.bean.AppInfo;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.BuildConfig;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.bean.Version;
import com.lxkj.dmhw.dialog.VersionDialog;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.MyLayoutManager;
import com.lxkj.dmhw.utils.ToastUtil;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity340 extends BaseActivity {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.about_version)
    LinearLayout aboutVersion;
    @BindView(R.id.about_agreement)
    LinearLayout aboutAgreement;
    @BindView(R.id.about_code)
    TextView aboutCode;
    @BindView(R.id.about_cache_do)
    TextView aboutCache;
    @BindView(R.id.about_cache_text)
    TextView aboutCacheText;
    @BindView(R.id.about_comment)
    LinearLayout about_comment;
    @BindView(R.id.app_head_version_layout)
    LinearLayout app_head_version_layout;
    @BindView(R.id.agreement_user)
    RelativeLayout agreement_user;
    @BindView(R.id.agreement_secret)
    RelativeLayout agreement_secret;

    @BindView(R.id.compony_name)
    TextView compony_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        compony_name.setText(Variable.ComponyName);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams);
        }
        try {
            aboutCacheText.setText(Utils.getTotalCacheSize(this));
        } catch (Exception e) {
            Logger.e(e, "清除缓存");
        }
        String version;
        switch (BuildConfig.BUILD_TYPE) {
            case "debug":
                version = "（Debug）";
                break;
            case "alpha":
                version = "（Alpha）";
                break;
            default:
                version = "";
                break;
        }
        aboutCode.setText("版本号 " + Utils.getAppVersionName() + version);
        //运营商特殊权利 数据处理
        if(DateStorage.getLoginStatus()&&login.getUsertype().equals("1")) {
            app_head_version_layout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                        Intent intent = new Intent(AboutActivity340.this, TempDataSetActivity.class);
                        startActivity(intent);
                    return false;
                }
            });
        }
    }

    @Override
    public void mainMessage(Message message) {

    }
    @Override
    public void childMessage(Message message) {

    }
    @Override
    public void handlerMessage(Message message) {
        if (message.what == LogicActions.VersionSuccess) {
            Version version = (Version) message.obj;
            if (Integer.parseInt(Utils.getLocalAppVersion(this))>=Integer.parseInt(version.getDevversion())){
                ToastUtil.showImageToast(AboutActivity340.this,"已是最新版本，无需更新",R.mipmap.toast_img);
            }else{
                if (!Objects.equals(version.getUpdateflag(), "0")){
                    boolean isCheck;
                    isCheck = version.getUpdateflag().equals("2");
                    String versionCode = version.getDevversion();
                    String content = version.getVersiondesc();
                    String url = version.getDownloadurl();
                    VersionDialog versionDialog = new VersionDialog(this, isCheck, versionCode, content, url);
                    versionDialog.getDialog().show();
                }
            }
        }
    }

    @OnClick({R.id.back, R.id.about_version, R.id.about_agreement, R.id.about_cache_do,R.id.about_comment,R.id.agreement_user,R.id.agreement_secret,R.id.useracount_delete})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.back:
                isFinish();
                break;
            case R.id.about_version:
                // 检查更新
                paramMap = new HashMap<>();
                paramMap.put("userid", login.getUserid());
                NetworkRequest.getInstance().POST(handler, paramMap, "Version", HttpCommon.Version);
                break;
            case R.id.about_cache_do:
                Utils.clearAllCache(this);
                try {
                    aboutCacheText.setText(Utils.getTotalCacheSize(this));
                } catch (Exception e) {
                    Logger.e(e, "清除缓存");
                }
                ToastUtil.showImageToast(AboutActivity340.this,"清除成功",R.mipmap.toast_img);
                break;
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
            case R.id.agreement_user://用户协议
                intent = new Intent(this, WebViewActivity.class);
                intent.putExtra(Variable.webUrl, Variable.protocl_Agreement_User);
                break;
            case R.id.agreement_secret://隐私政策
                intent = new Intent(this, WebViewActivity.class);
                intent.putExtra(Variable.webUrl, Variable.protocl_Agreement_Secret);
                break;
            case R.id.useracount_delete://账号注销
                intent = new Intent(this, AccountDeleteActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
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
                Utils.toMarket(AboutActivity340.this, Utils.getAppPakageName(),markets.get(position).getPackageName());
            }
        });

    }
}
