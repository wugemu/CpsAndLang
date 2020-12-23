package com.lxkj.dmhw.wxapi;

import android.util.Log;
import android.widget.Toast;

import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.utils.Utils;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

/**
 * QQ分享回调
 * Created by zyhant on 18-3-7.
 */

public class IuiListener implements IUiListener {
    @Override
    public void onComplete(Object o) {
        if (Variable.QQCheck) {
            Variable.QQCheck = false;
            Utils.deleteDir(Variable.sharePath);
        }
    }

    @Override
    public void onError(UiError uiError) {
        // 分享异常
        if (Variable.QQCheck) {
            Variable.QQCheck = false;
            Utils.deleteDir(Variable.sharePath);
        }
    }

    @Override
    public void onCancel() {
        // 取消分享
        if (Variable.QQCheck) {
            Variable.QQCheck = false;
            Utils.deleteDir(Variable.sharePath);
        }
    }
}
