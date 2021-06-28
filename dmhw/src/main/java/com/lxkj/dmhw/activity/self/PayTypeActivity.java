package com.lxkj.dmhw.activity.self;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.example.test.andlang.andlangutil.BaseLangActivity;
import com.example.test.andlang.http.ExecutorServiceUtil;
import com.example.test.andlang.util.ActivityUtil;
import com.example.test.andlang.util.BaseLangUtil;
import com.example.test.andlang.util.ButtonUtil;
import com.example.test.andlang.util.TimeCalculate;
import com.example.test.andlang.util.ToastUtil;
import com.lxkj.dmhw.MyApplication;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.self.order.OrderListActivity;
import com.lxkj.dmhw.adapter.self.settle.PayTypeAdapter;
import com.lxkj.dmhw.bean.self.PayOrder;
import com.lxkj.dmhw.bean.self.PayResult;
import com.lxkj.dmhw.bean.self.PayTypeBean;
import com.lxkj.dmhw.bean.self.WxPrePayBean;
import com.lxkj.dmhw.logic.Constants;
import com.lxkj.dmhw.model.PayModel;
import com.lxkj.dmhw.myinterface.ConfirmOKI;
import com.lxkj.dmhw.presenter.PayPresenter;
import com.lxkj.dmhw.service.TimeService;
import com.lxkj.dmhw.utils.BBCUtil;
import com.lxkj.dmhw.utils.JsonParseUtil;
import com.lxkj.dmhw.widget.dialog.ConfirmDialog;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import butterknife.BindView;
import butterknife.OnClick;

public class PayTypeActivity extends BaseLangActivity<PayPresenter> {
    private final int REQ_PAYRESULT=200;//支付结果

    private static final int SDK_PAY_FLAG = 2;

    @BindView(R.id.lv_paytype)
    ListView lv_paytype;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.tv_time)
    TextView tv_time;

    private PayTypeAdapter adapter;
    private long payCountDown;
    private String tradeNo;
    private IWXAPI api;
    private boolean isPayGift;
    private WxPrePayBean wxPrePayBean;//小程序支付
    private boolean isUseMinWx;//是否使用小程序支付
    private boolean isGroupOrder;
    private boolean isMinWxTwo;//一键开团使用微信小程序支付
    @Override
    public int getLayoutId() {
        return R.layout.activity_pay_type;
    }

    @Override
    public void initView() {
        initTitleBar(true,"选择付款方式");
    }

    @Override
    public void initPresenter() {
        presenter=new PayPresenter(PayTypeActivity.this, PayModel.class);
    }

    @Override
    public void initData() {
        Intent intent=getIntent();
        if(intent!=null){
            String realPayAmount=intent.getStringExtra("realPayAmount");
            tradeNo=intent.getStringExtra("tradeNo");
            payCountDown=intent.getLongExtra("payCountDown",30*60);
            payCountDown= MyApplication.NOW_TIME+payCountDown;
            isPayGift=intent.getBooleanExtra("isPayGift",false);
            isGroupOrder=intent.getBooleanExtra("isGroupOrder",false);
            tv_price.setText("¥"+realPayAmount);
        }
        adapter=new PayTypeAdapter(PayTypeActivity.this,getPayTypeBean());
        lv_paytype.setAdapter(adapter);
        BaseLangUtil.setListViewHeightBasedOnChildren(lv_paytype);

        LocalBroadcastManager.getInstance(this).registerReceiver(timeReceiver,new IntentFilter(Constants.TIME_TASK));
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("PAY_RESULT"));

        api = WXAPIFactory.createWXAPI(PayTypeActivity.this, Constants.WXAPPID);

//        showWaitDialog();
//        presenter.reqPreWXPay(tradeNo);
    }

    @Override
    public void update(Observable o, Object arg) {
        dismissWaitDialog();
        if("reqPrePayDetail".equals(arg)){
            //支付参数获取
            if(BBCUtil.isEmpty(presenter.model.getPayResult())){
                return;
            }
//            if (!BBCUtil.isEmpty(JsonParseUtil.getStringValue(presenter.model.getPayResult(),"notJoinGroupTitle"))){//不能参团了
//                new ConfirmDialog(this, JsonParseUtil.getStringValue(presenter.model.getPayResult(), "notJoinGroupTitle"), new ConfirmOKI() {
//                    @Override
//                    public void executeOk() {
//                        //新开一单，接口未出
//                        showWaitDialog();
//                        presenter.reqChangeTradeGroup(tradeNo);
//
//                    }
//
//                    @Override
//                    public void executeCancel() {
//
//                    }
//                });
//
//                return;
//            }

            try {
                int type = adapter.getItem(adapter.getSelectPosition()).getType();
                if (type == 4) {
                    //支付宝
                    startAlipay(presenter.model.getPayResult());
                } else if (type == 8) {
                    //微信
                    if (BBCUtil.isWXAppInstalledAndSupported(PayTypeActivity.this)) {
                        PayOrder payOrder = JsonParseUtil.getPayOrder(presenter.model.getPayResult());
                        ToastUtil.show(PayTypeActivity.this, "微信支付启动中，请稍候...");
                        MyApplication.PAY_RESULT_TRADE_NO=tradeNo;
                        startWXPay(payOrder);
                    } else {
                        ToastUtil.show(PayTypeActivity.this, "未检测到微信客户端，请安装");
                    }
                }
            }catch (Exception e){
                Toast.makeText(PayTypeActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
            }
        }else if("reqPreWXPay".equals(arg)){
            wxPrePayBean=presenter.model.getWxPrePayBean();
            if(isMinWxTwo) {
                isMinWxTwo=false;
                goPayByType();
            }
        }else if("payTradeQuery".equals(arg)){
            //小程序支付查询结果
            if(isUseMinWx){
                isUseMinWx=false; //回执状态
                String payResultStr=presenter.model.getPayResultStr();
                if("SUCCESS".equals(payResultStr)){
                    //支付成功
                    paySuccess();
                }else {
                    Toast.makeText(PayTypeActivity.this, "付款失败，请重新支付。", Toast.LENGTH_SHORT).show();
                }
            }
        }else if ("reqChangeTradeGroup".equals(arg)){
            //订单号变更
            if(!BBCUtil.isEmpty(presenter.model.getChangeTradeNo())) {
                //返回新的订单号就使用新的订单号
                tradeNo = presenter.model.getChangeTradeNo();
            }
            if(isMinWxTwo) {
                //重新获取小程序参数
                presenter.reqPreWXPay(tradeNo);
            }else{
                //不是微信小程序支付
                goPayByType();
            }
        }else if("reqGroupPayCheck".equals(arg)){
            //团支付 小程序支付校验
            String checkResult=presenter.model.getPayResult();
            if (!BBCUtil.isEmpty(checkResult)&&!BBCUtil.isEmpty(JsonParseUtil.getStringValue(checkResult,"notJoinGroupTitle"))){//不能参团了
                new ConfirmDialog(this, JsonParseUtil.getStringValue(checkResult, "notJoinGroupTitle"), new ConfirmOKI() {
                    @Override
                    public void executeOk() {
                        //新开一单，接口未出
                        isMinWxTwo=true;
                        showWaitDialog();
                        presenter.reqChangeTradeGroup(tradeNo);
                    }

                    @Override
                    public void executeCancel() {

                    }
                });
            }else {
                //小程序支付
                wxMinPay();
            }
        }
    }

    @OnClick(R.id.btn_ok)
    public void goPay(){
        if(ButtonUtil.isFastDoubleClick(R.id.btn_ok)){
            ToastUtil.show(PayTypeActivity.this,R.string.tip_btn_fast);
            return;
        }
        //去支付
        goPayByType();
    }
    public void goPayByType(){
        if(adapter==null){
            return;
        }
        int type = adapter.getItem(adapter.getSelectPosition()).getType();
        if(type == 8&&BBCUtil.isWXAppInstalledAndSupported(PayTypeActivity.this)&&wxPrePayBean!=null&&wxPrePayBean.isIfMinProgramPay()) {
            //判断是否需要一键开团
            showWaitDialog();
            presenter.reqGroupPayCheck(tradeNo);
        }else {
            //走老的app支付逻辑
            showWaitDialog();
            presenter.reqPrePayDetail(tradeNo, type);
            isUseMinWx=false;
        }
    }

    public void wxMinPay(){
        //调起微信小程序支付
        //自己根据微信源码写的方法
//            WXLaunchMiniUtil miniUtil = new WXLaunchMiniUtil(PayTypeActivity.this);
//            miniUtil.appId = NativeHelper.getWXAPPID();
//            miniUtil.userName = wxPrePayBean.getWxUserName();
//            miniUtil.path = wxPrePayBean.getMiniProgramPath();
//            miniUtil.miniprogramType = WXLaunchMiniUtil.MINIPTOGRAM_TYPE_RELEASE;
//            miniUtil.sendReq();
        //微信官方方法
        WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
        req.userName = wxPrePayBean.getWxUserName(); // 填小程序原始id gh_566bfcb97796
        req.path = wxPrePayBean.getMiniProgramPath(); // page/default/pages/appment/appment?tradeNo=SD1911280028007535 拉起小程序页面的可带参路径，不填默认拉起小程序首页，对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"。
        req.miniprogramType = 0;// 可选打开 正式版:0，测试版:1，体验版:2
        api.registerApp(Constants.WXAPPID);
        api.sendReq(req);

        isUseMinWx=true;
    }

    public List<PayTypeBean> getPayTypeBean(){
        List<PayTypeBean> payTypeBeanList=new ArrayList<PayTypeBean>();
        PayTypeBean payTypeBean2=new PayTypeBean();
        payTypeBean2.setType(4);//后台配置固定
        payTypeBean2.setTitle("支付宝");
        payTypeBeanList.add(payTypeBean2);

        PayTypeBean payTypeBean1=new PayTypeBean();
        payTypeBean1.setType(8);//后台配置固定
        payTypeBean1.setTitle("微信支付");
        payTypeBeanList.add(payTypeBean1);

        return payTypeBeanList;
    }

    private BroadcastReceiver timeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long nowtime = MyApplication.NOW_TIME;
            if (payCountDown > nowtime) {
                Log.i("sudian","倒计时任务接收中:"+ MyApplication.NOW_TIME);
                String deadline = TimeCalculate.getTime2(nowtime, payCountDown);
                tv_time.setText("00:"+deadline);
            }else{
                ActivityUtil.getInstance().exit(PayTypeActivity.this);
            }
        }
    };
    //微信支付成功回调广播
    private BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(presenter!=null){
                presenter.payTradeQuery(2,tradeNo);
            }
            ActivityUtil.getInstance().exitResult(PayTypeActivity.this,null,RESULT_OK);
        }
    };


    private void startWXPay(PayOrder payOrder) {
        final PayReq req = new PayReq();
        req.appId = payOrder.getAppid();
        req.partnerId = payOrder.getPartnerid();
        req.prepayId = payOrder.getPrepay_id();
        req.nonceStr = payOrder.getNonce_str();
        req.timeStamp = payOrder.getTimestamp();
        req.packageValue = payOrder.getPackageDto();
        req.sign = payOrder.getSign();
        if(isPayGift) {
            req.extData = "isPayGift"; // optional
        }if(isGroupOrder){
            req.extData = "isGroupOrder"; // optional
        }else {
            req.extData = "isPayNormal"; // optional
        }
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        api.registerApp(Constants.WXAPPID);
        api.sendReq(req);

    }

    private void startAlipay(final String payInfo) {
        ToastUtil.show(this, "支付宝正在启动中，请稍候...");
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(PayTypeActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        };
        // 必须异步调用
        ExecutorServiceUtil.getInstence().execute(payRunnable);
    }
    public void paySuccess(){
//        if(isPayGift) {
//            MyApplication.PAY_RESULT_TRADE_NO=tradeNo;
//            Intent intent = new Intent(PayTypeActivity.this, PayResultGiftActivity.class);
//            ActivityUtil.getInstance().startResult(PayTypeActivity.this, intent,REQ_PAYRESULT);
//        }else if (isGroupOrder){
//            Intent intent = new Intent(PayTypeActivity.this, GroupInfoActivity.class);
//            intent.putExtra("tradeNo",tradeNo);
//            intent.putExtra("isPayResult",true);
//            ActivityUtil.getInstance().startResult(PayTypeActivity.this, intent, REQ_PAYRESULT);
//        }else{
//            MyApplication.PAY_RESULT_TRADE_NO=tradeNo;
//            Intent intent = new Intent(PayTypeActivity.this, PayResultActivity.class);
//            ActivityUtil.getInstance().startResult(PayTypeActivity.this, intent,REQ_PAYRESULT);
//        }
        MyApplication.PAY_RESULT_TRADE_NO=tradeNo;
        Intent intent = new Intent(PayTypeActivity.this, PayResultActivity.class);
        ActivityUtil.getInstance().startResult(PayTypeActivity.this, intent,REQ_PAYRESULT);
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 10:
                    if(presenter!=null){
                        presenter.payTradeQuery(1,tradeNo);
                    }
                    paySuccess();
                    break;
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        handler.sendEmptyMessageDelayed(10, 300);
                        Toast.makeText(PayTypeActivity.this, "支付成功", Toast.LENGTH_SHORT).show();

                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(PayTypeActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(PayTypeActivity.this, "付款失败，请重新支付。", Toast.LENGTH_SHORT).show();
                        }

                    }
                    break;
                }
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK){
            if(requestCode==REQ_PAYRESULT){
                ActivityUtil.getInstance().exitResult(PayTypeActivity.this,null,RESULT_OK);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isUseMinWx){
            //使用的小程序支付 延迟1s 调用查询支付结果状态
            showWaitDialog();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(presenter!=null){
                        presenter.payTradeQuery(3,tradeNo);
                    }
                }
            },1000);
        }
        try {
            Intent i = new Intent(PayTypeActivity.this, TimeService.class);
            startService(i);
        }catch (Exception e){

        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(timeReceiver);
            LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void goBack() {
        ConfirmDialog confirmDialog=new ConfirmDialog(PayTypeActivity.this, "确定要离开吗？", "我再想想", "去意已决", new ConfirmOKI() {
            @Override
            public void executeOk() {
            }

            @Override
            public void executeCancel() {
                ActivityUtil.getInstance().exitActivity(OrderListActivity.class);

                Intent intent = new Intent(PayTypeActivity.this, OrderListActivity.class);
                intent.putExtra("flag", 1);//待付款页面
                ActivityUtil.getInstance().start(PayTypeActivity.this, intent);
                ActivityUtil.getInstance().exitResult(PayTypeActivity.this, null, RESULT_OK);
            }
        });
    }
}