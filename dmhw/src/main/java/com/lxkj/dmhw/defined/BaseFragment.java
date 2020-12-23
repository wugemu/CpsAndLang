package com.lxkj.dmhw.defined;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.lxkj.dmhw.MyApplication;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.bean.UserInfo;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.dialog.ProgressDialog;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.NetStateUtils;
import com.lxkj.dmhw.utils.ToastUtil;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Objects;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Fragment父类
 * Created by Zyhant on 2017/2/10.
 */

public abstract class BaseFragment extends SupportFragment {

    // 布局
    private View view;
    // 弹窗
    private ProgressDialog dialog;
    // post请求参数集合
    public HashMap<String, String> paramMap = new HashMap<>();
    // 页码
    public int page = 1;
    // 请求条数
    public int pageSize = 10;
    // 用户信息
    public UserInfo login;
    // 下拉刷新样式
    public LoadMoreFooterView loadView;
    public LoadMoreFooterView290 loadViewnew;
    //px值
    public  int width;
    public  int height;
    //dp值
    public  int dpWidth;
    public  int dpHeight;

    //是分享商品和分享app的标记
    public boolean isshare=true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            // 初始化弹窗
            dialog = new ProgressDialog(getActivity(), "");
            dialog.getWindow().setDimAmount(0f);
            // 设置用户信息
            login = DateStorage.getInformation();
            // 发布/订阅事件
            EventBus.getDefault().register(this);
            // 设置下拉刷新样式
            loadView = new LoadMoreFooterView(getActivity());
            loadViewnew = new LoadMoreFooterView290(getActivity());
            onData();

            WindowManager manager = getActivity().getWindowManager();
            DisplayMetrics outMetrics = new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(outMetrics);
             width = outMetrics.widthPixels;
             height = outMetrics.heightPixels;
            float density = outMetrics.density;// 屏幕密度（0.75 / 1.0 / 1.5）
            int densityDpi = outMetrics.densityDpi;// 屏幕密度dpi（120 / 160 / 240）
            // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
            dpWidth = (int) (width / density);  // 屏幕宽度(dp)
            dpHeight = (int) (height / density);// 屏幕高度(dp)
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            view = onInit(inflater, container, savedInstanceState);
            onEvent();
            onCustomized();
        } catch (Exception e) {
            Logger.e(e, "");
        }
        return view;
    }

    /**
     * 加载中弹窗提示
     */
    public void showDialog() {
        try {
            new Handler().postDelayed(dialog::hideDialog, 10000);
            // 判断弹窗是否已显示
            if (!dialog.isDialog()) {
                // 启动弹窗
                dialog.showDialog();
                // 设置弹窗状态
                Variable.dialogStatus = false;
            }
        } catch (Exception e) {
            Logger.e(e, "加载中弹窗提示");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dismissDialog();
    }

    /**
     * 隐藏弹窗
     */
    public void dismissDialog() {
        try {
            // 判断弹窗是否已显示
            if (dialog!=null&&dialog.isDialog()) {
                // 隐藏弹窗
                dialog.hideDialog();
                // 设置弹窗状态
                Variable.dialogStatus = true;
            }
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }

    /**
     *弹出提示
     * @param str
     */
    public void toast(String str) {
        try {
            ToastUtil.showImageToast(getActivity(),str,R.mipmap.toast_error);
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }

    /**
     * 失败回调
     * @param position
     * @param content
     * @param more 每次都弹
     */
    public void Failed(int position, String content,int more) {
        if (position == NetworkRequest.FAILED) {
            if (more == 0) {
                if (!NetStateUtils.isNetworkConnected(MyApplication.getContext()) && Variable.countConnect == 0) {
                    Variable.countConnect++;
                    toast(getResources().getString(R.string.net_work_unconnect));
                } else if ((content.contains("time out") || content.contains("after 30000ms") || content.contains("timeout") || content.contains("timed out")||content.contains("SSL handshare time out")) && Variable.countTimeout == 0) {
                    Variable.countTimeout++;
                    toast(getResources().getString(R.string.net_work_timeout));
                } else {
                    if (Variable.countConnect == 0 && Variable.countConnect == 0 && Variable.countOther == 0) {
                        Variable.countOther++;
                        // 请求失败
                        if (content.startsWith(Variable.unableResolve)) {
                            content = "无法解析主机，请确认网络连接!";
                        } else if (content.startsWith(Variable.connectFailed) || content.contains("Failed to connect")) {
                            content = "网络连接异常，请稍后重试!";
                        } else {
                            if (content.equals("店铺不存在")) {

                            } else {
                                toast(content);
                            }
                        }
                    }
                }
                switch (content) {
                    case "代理申请失败":
                        InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ApplyDialog"), "", 0);
                        break;
                }
            }
            else{
                if (!content.equals("您的手机还未注册，请重试！")){
                    toast(content);
                }

            }
        }
    }

    protected Handler handler = new Handler(message -> {
        try {
            if (message.what == LogicActions.Failed) {
                Failed(message.arg1, message.obj + "",message.arg2);
                dismissDialog();
            }
            handlerMessage(message);
        } catch (Exception e) {
            Logger.e(e, "");
        }
        return false;
    });

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventMain(Message message) {
        try {
            if (message.what == LogicActions.LoginStatusSuccess) {
                if ((Boolean) message.obj) {
                    login = DateStorage.getInformation();
                }
            }
            mainMessage(message);
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void eventChild(Message message) {
        try {
            childMessage(message);
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }

    /**
     * 关闭页面
     */
    public void isFinish() {
        ((BaseActivity) Objects.requireNonNull(getActivity())).isFinish();
    }

    /**
     * 当Activity销毁的时候调用
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Variable.countTimeout=0;
        Variable.countConnect=0;
        Variable.countOther=0;
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        Variable.countTimeout=0;
        Variable.countConnect=0;
        Variable.countOther=0;
    }

    /**
     * 获取数据
     */
    abstract public void onData();

    /**
     * 初始化
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    abstract public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * 控件绑定和事件执行
     */
    abstract public void onEvent();

    /**
     * 自定义事件执行
     */
    abstract public void onCustomized();

    abstract public void mainMessage(Message message);

    abstract  public void childMessage(Message message);

    abstract public void handlerMessage(Message message);


}
