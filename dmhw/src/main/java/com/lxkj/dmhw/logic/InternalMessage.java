package com.lxkj.dmhw.logic;

import android.os.Handler;
import android.os.Message;

import org.greenrobot.eventbus.EventBus;

/**
 * 内部通知，发布/订阅事件
 * Created by Zyhant on 2018/1/22.
 */

public class InternalMessage {

    private static InternalMessage instance;

    private InternalMessage() {

    }

    public static InternalMessage getInstance() {
        if (instance == null) {
            instance = new InternalMessage();
        }
        return instance;
    }

    /**
     * 发布事件
     * @param what 调用值
     * @param object 数据内容
     * @param state 状态
     */
    public void eventMessage(int what, Object object, int state) {
        Message message = new Message();
        message.what = what;
        message.obj = object;
        message.arg1 = state;
        EventBus.getDefault().post(message);
    }

    /**
     * 异步请求回调
     * @param handler
     * @param what
     * @param object
     * @param state
     * @param more 相同页面可以弹错误信息多次
     */
    public void sendMessage(Handler handler, int what, Object object, int state,int more) {
        Message message = new Message();
        message.what = what;
        message.obj = object;
        message.arg1 = state;
        message.arg2=more;
        handler.sendMessage(message);
    }

}
