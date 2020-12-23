package com.lxkj.dmhw.dialog;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ali.auth.third.core.MemberSDK;
import com.ali.auth.third.core.callback.LoginCallback;
import com.ali.auth.third.core.model.Session;
import com.ali.auth.third.login.LoginService;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.activity.AliAuthWebViewActivity;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.defined.SimpleDialog;
import com.lxkj.dmhw.defined.SimpleDialogNocanel;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.Utils;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import org.json.JSONObject;

import java.util.HashMap;

public class WeixinAuthLoginDialog extends SimpleDialog<String> {

    private TextView wx_txt;
    private String messageTxt="";
    Context context;
    /**
     * 个人中心微信绑定弹窗
     * @param context  上下文
     * @param data     数据
     */
    public WeixinAuthLoginDialog(Context context, String data) {
        super(context, R.layout.dialog_weixin_auth_login, data, true, false);
        // 初始化微信SDK
        api = Utils.weChat(context, false);
        this.context=context;
        this.messageTxt=data;
        wx_txt.setText(messageTxt);
    }
    @Override
    protected void convert(ViewHolder helper) {
        wx_txt= helper.getView(R.id.wx_text);
        helper.setOnClickListener(R.id.wx_bing_btn, this);
        helper.setOnClickListener(R.id.layout_root, this);
        helper.setOnClickListener(R.id.layout_root01, this);
    }
    // 微信SDK
    private IWXAPI api;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_root01:
                break;
            case R.id.layout_root:
                dismiss();
                break;
            case R.id.wx_bing_btn:
                Variable.BindingWeChat = true;
                Variable.isBindingWeChatFromSetting=false;
                Variable.weChatCheck = true;
                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = Variable.weChatState;
                api.sendReq(req);
                break;
        }
    }


}
