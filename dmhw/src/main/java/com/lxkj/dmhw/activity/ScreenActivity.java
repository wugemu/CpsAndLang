package com.lxkj.dmhw.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.bean.ActivtyChain;
import com.lxkj.dmhw.bean.JDBanner;
import com.lxkj.dmhw.bean.Screen;
import com.lxkj.dmhw.bean.UserInfo;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.data.ScreenOpen;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.utils.ImageProgressUtil.ProgressInterceptor;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScreenActivity extends BaseActivity {

    @BindView(R.id.screen_image)
    ImageView screenImage;
    @BindView(R.id.screen_clear_time)
    TextView screenClearTime;
    @BindView(R.id.screen_clear)
    LinearLayout screenClear;
    @BindView(R.id.dmj_image_layout)
    RelativeLayout dmj_image_layout;
    private Screen screen;
    private CountDownTimer count;
    private UserInfo login;

    private ActivtyChain activtyChain=new ActivtyChain();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);
        ButterKnife.bind(this);
        // 设置动画
        overridePendingTransition(R.anim.in_from_fade, R.anim.out_to_fade);
        // 设置用户信息
        login = DateStorage.getInformation();
        ScreenOpen screenOpen = new ScreenOpen();
        screen = screenOpen.find();
        if (screen!=null) {
            screenImage.setImageBitmap(Utils.getLocalBitmap(screen.getPath(), 1080));
            countDown();
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void mainMessage(Message message) {
    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        if (message.what == LogicActions.ActivtyChainSuccess) {
            //官方活动 tapbao://开头 打开淘宝
            com.alibaba.fastjson.JSONObject jsonObject= (com.alibaba.fastjson.JSONObject) message.obj;
            if (jsonObject.getString("result").equals("3")) {
                toast(jsonObject.getString("msgstr"));
            }else{
                //官方活动 tapbao://开头 打开淘宝
                ArrayList<ActivtyChain> tempList = (ArrayList<ActivtyChain>)JSON.parseArray(jsonObject.getJSONArray("activitylist").toString(), ActivtyChain.class);
                openTaobao(tempList.get(0).getActivityurl(),"","");
            }
            isFinish();
        }
    }

    @OnClick({R.id.screen_image, R.id.screen_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.screen_image:
                startActivity(new Intent(ScreenActivity.this, MainActivity.class));
                switch (screen.getJumptype()) {
                    case "1":
                         startActivity(new Intent(this, CommodityActivity290.class).putExtra("shopId", screen.getJumpvaue()).putExtra("source", "all").putExtra("sourceId",""));
                         isFinish();
                         break;
                    case "2":
                        startActivity(new Intent(ScreenActivity.this, OnlyUrlWebViewActivity.class).putExtra(Variable.webUrl, screen.getJumpvaue()));
                        isFinish();
                        break;
                    case "3":
                        startActivity(new Intent(ScreenActivity.this, AliAuthWebViewActivity.class).putExtra(Variable.webUrl, screen.getJumpvaue()).putExtra("isTitle" , true));
                        isFinish();
                        break;
                    case "4":
                        startActivity(new Intent(ScreenActivity.this, NewActivity.class).putExtra("topicId", screen.getJumpvaue()).putExtra("labelType" , "09"));
                        isFinish();
                        break;
                    //官方活动
                    case "11":
                        // 请求活动链接
                        if (DateStorage.getLoginStatus()){
                            getActivityChain(login.getUserid(),screen.getJumpvaue());
                        }else{
                          Intent intent =new Intent(this,LoginActivity.class);
                          startActivity(intent);
                          isFinish();
                        }
                        break;
                    case "12":
                       Intent intent = new Intent(this, AliAuthWebViewActivity.class);
                        intent.putExtra(Variable.webUrl, screen.getJumpvaue());
                        intent.putExtra("isTitle" , false);
                        intent.putExtra("noGoTaoBaoH5",12);
                        startActivity(intent);
                        isFinish();
                        break;
                    case "14":
                        if (screen.getJumpvaue()!=null) {
                            Object bannerObject = JSON.parseObject(screen.getJumpvaue(), JDBanner.class);
                            JDBanner banner = (JDBanner) bannerObject;
                            int platformType=1;
                            switch (banner.getCpsType()){
                                case "pdd":
                                    platformType=1;
                                    break;
                                case "jd":
                                    platformType=2;
                                    break;
                                case "wph":
                                    platformType=3;
                                    break;
                                case "sn":
                                    platformType=5;
                                    break;
                                case "kl":
                                    platformType=6;
                                    break;
                            }
                            Utils.getJumpTypeForMorePl(this,banner.getClickType(),banner.getClickVaule(),platformType,banner.getNeedLogin(),banner.getName(),banner.getSysParam());
                        }
                        isFinish();
                        break;
                }
                count.cancel();
                break;
            case R.id.screen_clear:
                startActivity(new Intent(ScreenActivity.this, MainActivity.class));
                isFinish();
                count.cancel();
                break;
        }
    }

    /**
     * 倒计时
     */
    private void countDown() {
        count = new CountDownTimer(Integer.parseInt(screen.getCountdown()) * 1000, 1000) {

            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                try {
                    screenClearTime.setText(millisUntilFinished / 1000 + "");
                } catch (Exception e) {
                    Logger.e(e, "倒计时");
                }
            }

            @Override
            public void onFinish() {
                try {
                    screenClearTime.setText("0");
                    startActivity(new Intent(ScreenActivity.this, MainActivity.class));
                    isFinish();
                } catch (Exception e) {
                    Logger.e(e, "倒计时");
                }
            }
        }.start();
    }

    /**
     * 关闭页面
     */
    public void isFinish() {
        try {
            finish();
//            ActivityManagerDefine.getInstance().popActivity(this);//退出页面时调用
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
}
