package com.lxkj.dmhw.dialog;

import android.content.Context;
import android.view.View;

import com.ali.auth.third.core.MemberSDK;
import com.ali.auth.third.core.callback.LoginCallback;
import com.ali.auth.third.core.model.Session;
import com.ali.auth.third.login.LoginService;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.SimpleDialog;

/**
 * 购物车授权SDK提示弹窗
 */

public class CartAuthDialog extends SimpleDialog<String> {

    /**
     * 初始化
     * @param context  上下文
     * @param data     数据
     */
    public CartAuthDialog(Context context, String data) {
        super(context, R.layout.dialog_cart_auth, data, true, true);
        //初始化淘宝关联按钮状态
        loginService = MemberSDK.getService(LoginService.class);
    }

    @Override
    protected void convert(ViewHolder helper) {
        helper.setText(R.id.dialog_text, "查看购物车需要先进行淘宝授权");
        helper.setOnClickListener(R.id.dialog_installed, this);
        helper.setOnClickListener(R.id.dialog_close, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_installed:
                showTbAuthLogin();
                break;
            case R.id.dialog_close:
                hideDialog();
                break;
        }
    }
    private LoginService loginService;
    //登录组件
    public void showTbAuthLogin() {
        loginService.auth(new LoginCallback() {
            @Override
            public void onFailure(int i, String s) {
                DateStorage.setIsAuth(false);
                //未关联
                hideDialog();
            }

            @Override
            public void onSuccess(Session session) {
                DateStorage.setIsAuth(true);//已关联
                DateStorage.setnick(loginService.getSession().nick);
                hideDialog();
            }
        });
    }
}
