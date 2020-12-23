package com.lxkj.dmhw.dialog;

import android.content.Context;
import android.text.TextPaint;
import android.view.View;
import android.widget.TextView;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.defined.SimpleDialog;
import com.lxkj.dmhw.utils.Utils;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;

public class WeixinLoginDialog extends SimpleDialog<String> {

    private TextView wx_txt,wx_bind_title;
    Context context;
    /**
     * 验证码登录微信授权弹窗
     * @param context  上下文
     * @param data     数据
     */
    public WeixinLoginDialog(Context context, String data) {
        super(context, R.layout.dialog_weixin_login, data, true, false);
        // 初始化微信SDK
        api = Utils.weChat(context, false);
        this.context=context;
    }
    @Override
    protected void convert(ViewHolder helper) {
        TextView wx_bind_title= helper.getView(R.id.wx_bind_title);
        TextPaint tp=wx_bind_title.getPaint();
        tp.setFakeBoldText(true);
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
                if (!api.isWXAppInstalled()) {
                toast("请先安装微信");
               } else {
                Variable.BindingWeChat = false;
                Variable.isHasDialog=false;
                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = Variable.weChatState;
                api.sendReq(req);
            }
                break;
        }
    }


}
