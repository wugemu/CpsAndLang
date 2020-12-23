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
import com.lxkj.dmhw.utils.Utils;

import org.json.JSONObject;

public class PddAuthLoginDialog extends SimpleDialog<String> {

    private LinearLayout tlogin;
    private TextView tlogin_txt2;
    private String mdata="";
    private String url="";
    private String messageTxt="";
    Context context;
    JSONObject jsonObject=null;
    /**
     * 拼多多授权弹窗
     * @param context  上下文
     * @param data     数据
     */
    public PddAuthLoginDialog(Context context, String data) {
        super(context, R.layout.dialog_pdd_auth_login, data, true, false);
        this.context=context;
        try{
            this.jsonObject= new JSONObject(data);
            if (jsonObject!=null){
                JSONObject dataObj= jsonObject.getJSONObject("data");
                mdata=dataObj.getString("shortUrl");
                url=dataObj.getString("url");
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
        switch (v.getId()) {
            case R.id.layout_root:
                dismiss();
                break;
            case R.id.layout_root01:
                break;
            case R.id.tlogin_confirm:
              // 拼多多授权 跳转pdd
                Utils.callMorePlatFrom(context,1,mdata,url);
                dismiss();
                break;
        }
    }


}
