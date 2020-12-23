package com.lxkj.dmhw.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chuanglan.shanyan_sdk.OneKeyLoginManager;
import com.chuanglan.shanyan_sdk.tool.ShanYanUIConfig;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.activity.BindingActivity;
import com.lxkj.dmhw.activity.LoginActivity;
import com.lxkj.dmhw.activity.SignInActivity;
import com.lxkj.dmhw.bean.WeChatUserInfo;
import com.lxkj.dmhw.wxapi.WXEntryActivity;


public class ConfigUtils {
    /**
     * 闪验三网运营商授权页配置类
     *
     * @param context
     * @return
     */
    // 微信用户信息
    public static WeChatUserInfo weChatInfo;

    /**
     * 邀请码
     */
    public static String extensionid = "";

    //手机登录沉浸式竖屏样式
    public static ShanYanUIConfig getCJSConfig(final Context context) {
        /************************************************自定义控件**************************************************************/
        Drawable logo = context.getResources().getDrawable(R.mipmap.sydmjlogo);
        Drawable backgruond = context.getResources().getDrawable(R.drawable.shanyan_demo_auth_no_bg);
        Drawable oneKeyBackgruond = context.getResources().getDrawable(R.drawable.loginself_btn_phone_style);
        Drawable returnBg = context.getResources().getDrawable(R.mipmap.shanyan_demo_return_left_bg);
        //loading自定义加载框
        LayoutInflater inflater = LayoutInflater.from(context);
        RelativeLayout view_dialog = (RelativeLayout) inflater.inflate(R.layout.shanyan_demo_dialog_layout, null);
        RelativeLayout.LayoutParams mLayoutParams3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        view_dialog.setLayoutParams(mLayoutParams3);
        view_dialog.setVisibility(View.GONE);


        //其他视图
        LayoutInflater inflater1 = LayoutInflater.from(context);
        RelativeLayout relativeLayout = (RelativeLayout) inflater1.inflate(R.layout.shanyan_demo_other_login_item, null);
        RelativeLayout.LayoutParams layoutParamsOther = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsOther.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParamsOther.setMargins(0,AbScreenUtils.dp2px(context, 400),0,0);
        relativeLayout.setLayoutParams(layoutParamsOther);
        otherLogin(context, relativeLayout);


        ShanYanUIConfig uiConfig = new ShanYanUIConfig.Builder()
                .setActivityTranslateAnim("shanyan_demo_fade_in_anim", "shanyan_dmeo_fade_out_anim")
                //授权页导航栏：
                .setNavColor(Color.parseColor("#ffffff"))  //设置导航栏颜色
                .setNavText("登录")  //设置导航栏标题文字
                .setNavTextSize(20)
                .setNavReturnBtnWidth(32)
                .setNavReturnBtnHeight(32)
                .setAuthBGImgPath(backgruond)
                .setLogoHidden(false)   //是否隐藏logo
                .setLogoImgPath(logo)
                .setLogoWidth(80)
                .setLogoHeight(80)
                .setLogoOffsetY(75)
                .setDialogDimAmount(0f)
                .setNavReturnImgPath(returnBg)
                .setFullScreen(false)
                .setStatusBarHidden(false)


                //授权页号码栏：
                .setNumberColor(Color.parseColor("#333333"))  //设置手机号码字体颜色
                .setNumFieldOffsetY(160)    //设置号码栏相对于标题栏下边缘y偏移
                .setNumberSize(25)
                .setNumFieldHeight(50)
                .setNumberBold(true)



                //授权页登录按钮：
                .setLogBtnText("本机号码一键登录")  //设置登录按钮文字
                .setLogBtnTextColor(0xffffffff)   //设置登录按钮文字颜色
                .setLogBtnImgPath(oneKeyBackgruond)   //设置登录按钮图片
                .setLogBtnTextSize(15)
                .setLogBtnHeight(45)
                .setLogBtnOffsetY(350)
                .setLogBtnWidth(AbScreenUtils.getScreenWidth(context, true) - 50)

                //授权页隐私栏：
                .setAppPrivacyOne("用户协议", Variable.protocl_Agreement_User)  //设置开发者隐私条款1名称和URL(名称，url)
                .setAppPrivacyTwo("隐私政策", Variable.protocl_Agreement_Secret)  //设置开发者隐私条款2名称和URL(名称，url)
                .setAppPrivacyColor(Color.parseColor("#999999"), Color.parseColor("#FF2741"))    //	设置隐私条款名称颜色(基础文字颜色，协议文字颜色)
                .setPrivacyText("登录即代表您已同意", "以及NN俱乐部", "与", "、", "")
                .setPrivacyOffsetBottomY(30)//设置隐私条款相对于屏幕下边缘y偏
                .setPrivacyState(true)
                .setPrivacyTextSize(12)
                .setPrivacyOffsetX(26)
                .setCheckBoxHidden(true)
                .setPrivacyOffsetX(52)


                .setSloganHidden(false)
                .setSloganTextColor(Color.parseColor("#777777"))
                .setSloganOffsetY(210)
                .setSloganTextSize(11)

                .setLoadingView(view_dialog)
                // 添加自定义控件:
                .addCustomView(relativeLayout, false, false, null)
                //标题栏下划线，可以不写
                .build();
                return uiConfig;

    }

    private static void otherLogin(final Context context, RelativeLayout relativeLayout) {
        RelativeLayout other = relativeLayout.findViewById(R.id.login_phone_other);
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                // 关闭登录页面
                OneKeyLoginManager.getInstance().finishAuthActivity();
                OneKeyLoginManager.getInstance().removeAllListener();
            }
        });
    }


    //微信登录绑定手机沉浸式竖屏样式
    public static ShanYanUIConfig getWXCJSConfig(final Context context) {
        /************************************************自定义控件**************************************************************/
        Drawable logo = context.getResources().getDrawable(R.mipmap.sydmjlogo);
        Drawable backgruond = context.getResources().getDrawable(R.drawable.shanyan_demo_auth_no_bg);
        Drawable oneKeyBackgruond = context.getResources().getDrawable(R.drawable.loginself_btn_phone_style);
        Drawable returnBg = context.getResources().getDrawable(R.mipmap.shanyan_demo_return_left_bg);
        //loading自定义加载框
        LayoutInflater inflater = LayoutInflater.from(context);
        RelativeLayout view_dialog = (RelativeLayout) inflater.inflate(R.layout.shanyan_demo_dialog_layout, null);
        RelativeLayout.LayoutParams mLayoutParams3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        view_dialog.setLayoutParams(mLayoutParams3);
        view_dialog.setVisibility(View.GONE);


        //其他视图
        LayoutInflater inflater1 = LayoutInflater.from(context);
        RelativeLayout relativeLayout = (RelativeLayout) inflater1.inflate(R.layout.shanyan_demo_other_bind_item, null);
        RelativeLayout.LayoutParams layoutParamsOther = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsOther.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParamsOther.setMargins(0,AbScreenUtils.dp2px(context, 400),0,0);
        relativeLayout.setLayoutParams(layoutParamsOther);
        otherBind(context, relativeLayout);


        ShanYanUIConfig uiConfig = new ShanYanUIConfig.Builder()
                .setActivityTranslateAnim("shanyan_demo_fade_in_anim", "shanyan_dmeo_fade_out_anim")
                //授权页导航栏：
                .setNavColor(Color.parseColor("#ffffff"))  //设置导航栏颜色
                .setNavText("手机绑定")  //设置导航栏标题文字
                .setNavTextSize(20)
                .setNavReturnBtnWidth(32)
                .setNavReturnBtnHeight(32)
                .setAuthBGImgPath(backgruond)
                .setLogoHidden(false)   //是否隐藏logo
                .setLogoImgPath(logo)
                .setLogoWidth(80)
                .setLogoHeight(80)
                .setLogoOffsetY(75)
                .setDialogDimAmount(0f)
                .setNavReturnImgPath(returnBg)
                .setFullScreen(false)
                .setStatusBarHidden(false)


                //授权页号码栏：
                .setNumberColor(Color.parseColor("#333333"))  //设置手机号码字体颜色
                .setNumFieldOffsetY(160)    //设置号码栏相对于标题栏下边缘y偏移
                .setNumberSize(25)
                .setNumFieldHeight(50)
                .setNumberBold(true)



                //授权页登录按钮：
                .setLogBtnText("本机号码一键绑定")  //设置登录按钮文字
                .setLogBtnTextColor(0xffffffff)   //设置登录按钮文字颜色
                .setLogBtnImgPath(oneKeyBackgruond)   //设置登录按钮图片
                .setLogBtnTextSize(15)
                .setLogBtnHeight(45)
                .setLogBtnOffsetY(350)
                .setLogBtnWidth(AbScreenUtils.getScreenWidth(context, true) - 50)

                //授权页隐私栏：
                .setAppPrivacyOne("用户协议", Variable.protocl_Agreement_User)  //设置开发者隐私条款1名称和URL(名称，url)
                .setAppPrivacyTwo("隐私政策", Variable.protocl_Agreement_Secret)  //设置开发者隐私条款2名称和URL(名称，url)
                .setAppPrivacyColor(Color.parseColor("#999999"), Color.parseColor("#FF2741"))    //	设置隐私条款名称颜色(基础文字颜色，协议文字颜色)
                .setPrivacyText("绑定即代表您已同意", "以及NN俱乐部", "与", "、", "")
                .setPrivacyOffsetBottomY(30)//设置隐私条款相对于屏幕下边缘y偏
                .setPrivacyState(true)
                .setPrivacyTextSize(12)
                .setPrivacyOffsetX(26)
                .setCheckBoxHidden(true)
                .setPrivacyOffsetX(52)


                .setSloganHidden(false)
                .setSloganTextColor(Color.parseColor("#777777"))
                .setSloganOffsetY(210)
                .setSloganTextSize(11)

                .setLoadingView(view_dialog)
                // 添加自定义控件:
                .addCustomView(relativeLayout, false, false, null)
                //标题栏下划线，可以不写
                .build();
        return uiConfig;

    }

    private static void otherBind(final Context context, RelativeLayout relativeLayout) {
        RelativeLayout other = relativeLayout.findViewById(R.id.login_bind_other);
        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BindingActivity.class);
                intent.putExtra("wechat", weChatInfo);
                intent.putExtra("invitation", extensionid);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                // 关闭登录页面
                OneKeyLoginManager.getInstance().finishAuthActivity();
                OneKeyLoginManager.getInstance().removeAllListener();
                ActivityManagerDefine.getInstance().popClass(WXEntryActivity.class);
            }
        });
    }
}
