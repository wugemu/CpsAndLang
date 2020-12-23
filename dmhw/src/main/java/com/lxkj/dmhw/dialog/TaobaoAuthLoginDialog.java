package com.lxkj.dmhw.dialog;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.baichuan.trade.biz.login.AlibcLogin;
import com.alibaba.baichuan.trade.biz.login.AlibcLoginCallback;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.defined.SimpleDialog;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;

import org.json.JSONObject;

public class TaobaoAuthLoginDialog extends SimpleDialog<String> {

    private LinearLayout tlogin;
    private TextView tlogin_txt2;
    private String mdata="";
    private String messageTxt="";
    Context context;
    JSONObject jsonObject=null;
    /**
     * 授权弹窗
     * @param context  上下文
     * @param data     数据
     */
    public TaobaoAuthLoginDialog(Context context, String data) {
        super(context, R.layout.dialog_taobao_auth_login, data, true, false);
        this.context=context;
        try{
            this.jsonObject= new JSONObject(data);
            if (jsonObject!=null){
                this.mdata=jsonObject.getString("url");
                this.messageTxt=jsonObject.getString("msgstr");
                tlogin_txt2.setText(messageTxt);
            }
        }catch(Exception e){
        }

    }

    @Override
    protected void convert(ViewHolder helper) {
        tlogin= helper.getView(R.id.tlogin_confirm);
        tlogin_txt2= helper.getView(R.id.tlogin_txt2);
        helper.setOnClickListener(R.id.tlogin_confirm, this);
        helper.setOnClickListener(R.id.layout_root, this);
        helper.setOnClickListener(R.id.layout_root01, this);

    }

    @Override
    public void onClick(View v) {
        AlibcLogin alibcLogin = AlibcLogin.getInstance();
        switch (v.getId()) {
            case R.id.layout_root:
                dismiss();
                break;
            case R.id.layout_root01:
                break;
            case R.id.tlogin_confirm:
              // 淘宝授权
                if (alibcLogin.isLogin()) {
                    InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("GotbAuth"), mdata, 0);
                }else{
                    alibcLogin.showLogin(new AlibcLoginCallback() {
                        @Override
                        public void onSuccess(int i, String s, String s1) {
                        InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("GotbAuth"), mdata, 0);
                        }
                        @Override
                        public void onFailure(int i, String s) {
                        }
                    });
                }
                dismiss();
                break;
        }
    }


}
