package com.lxkj.dmhw.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bx.adsdk.AdSdk;
import com.bx.adsdk.CampaignFragment;
//穿山甲普通版
//import com.bytedance.sdk.openadsdk.AdSlot;
//import com.bytedance.sdk.openadsdk.TTAdConstant;
//import com.bytedance.sdk.openadsdk.TTAdManager;
//import com.bytedance.sdk.openadsdk.TTAdNative;
//import com.bytedance.sdk.openadsdk.TTAdSdk;
//import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
//import com.bytedance.sdk.openadsdk.TTRewardVideoAd;
//穿山甲OPPO版
import com.bykv.vk.openvk.TTRdVideoObject;
import com.bykv.vk.openvk.TTVfConstant;
import com.bykv.vk.openvk.TTVfManager;
import com.bykv.vk.openvk.TTVfSdk;
import com.bykv.vk.openvk.VfSlot;
import com.bykv.vk.openvk.TTVfNative;
import com.bykv.vk.openvk.TTAppDownloadListener;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.defined.BaseActivity;
import com.qq.e.ads.rewardvideo.RewardVideoAD;
import com.qq.e.ads.rewardvideo.RewardVideoADListener;
import com.qq.e.comm.util.AdError;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//广告页面
public class ADActivity extends BaseActivity {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.mTitle)
    TextView mTitle;
    CampaignFragment campaignFragment;
    //穿山甲普通版
//    private TTAdNative mTTAdNative;
//    private TTRewardVideoAd mttRewardVideoAd;
//    TTAdManager ttAdManager;
    //穿山甲OPPO
    private TTVfNative mTTAdNative;
    private TTRdVideoObject mttRdVideoObject;
    TTVfManager ttAdManager;

    //广点通
    private RewardVideoAD rewardVideoAD;
    private EditText posIdEdt;
    private boolean adLoaded;//广告加载成功标志
    private boolean videoCached;//视频素材文件下载完成标志
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight > 0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        mTitle.setText(getIntent().getStringExtra("title"));
        /**
         * @param userId 用户id，选填
         * @param placeId 资源位id：必传
         */
        AdSdk.exposure(getIntent().getStringExtra("ADplaceid"),login.getUserid());
        showDialogAD();
        /**
         2 * @param userId 用户id，必传，不能为空，不能为0
         3 * @param placeId 资源位id：必传
         4 * @param callback CampaignCallback 回调
         5 */
        campaignFragment = CampaignFragment.newInstance(login.getUserid());
        campaignFragment.setPlaceId(getIntent().getStringExtra("ADplaceid"));
        campaignFragment.setCallback(new CampaignCallback());
        //webview 是否可以返回上一页
        campaignFragment.canGoBack();
        //webview 返回上一页
        switchFragment(campaignFragment);

        //穿山甲普通版step1:初始化sdk
//        ttAdManager = TTAdSdk.getAdManager();
//        //step2:(可选，强烈建议在合适的时机调用):申请部分权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题。
//        ttAdManager.requestPermissionIfNecessary(this);
//        //step3:创建TTAdNative对象,用于调用广告请求接口
//        mTTAdNative = ttAdManager.createAdNative(getApplicationContext());
        //穿山甲OPPO
        ttAdManager = TTVfSdk.getVfManager();
        //step2:(可选，强烈建议在合适的时机调用):申请部分权限，如read_phone_state,防止获取不了imei时候，下载类广告没有填充的问题。
        ttAdManager.requestPermissionIfNecessary(this);
        //step3:创建TTAdNative对象,用于调用广告请求接口
        mTTAdNative = ttAdManager.createVfNative(getApplicationContext());

    }

    @Override
    public void mainMessage(Message message) {

    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {

    }

    @OnClick({R.id.back})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.back:
                if (campaignFragment!=null&&campaignFragment.canGoBack()){
                    campaignFragment.goBack();
                }else{
                    isFinish();
                }
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    /**
     * 切换fragment
     *
     * @param targetFragment
     */
    private void switchFragment(Fragment targetFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment, targetFragment);
        transaction.commitAllowingStateLoss();
    }

    //回调
    public class CampaignCallback extends com.bx.adsdk.CampaignCallback {
        /**
         * 调用激励视频
         *
         * @param params {"requestId":"","adType":0,"pid":""}
         *               requestId : 请求唯一标识
         *               3.8.2 ⼴告数据上报
         *               adType: 1 穿山甲激励视频，2 广点通激励视频
         *               pid: 媒体方广告位id
         */
        public void showAd(String params) {
            //在激励视频的对应回调中调用,一定要全部调用
            // requestId ： showAd 方法参数中获取
            //视频加载或者曝光时调用
            try {
                showDialog();
                JSONObject jsonObject= new JSONObject(params);
                String pid=jsonObject.getString("pid");
                String requestId=jsonObject.getString("requestId");
                int adType=jsonObject.getInt("adType");
                campaignFragment.setVideoLoad(requestId);
                //视频播放完成时
                campaignFragment.setVideoComplete(requestId);
                // 视频点击关闭按钮时
                campaignFragment.setVideoClose(requestId);
                // 视频播放失败
                campaignFragment.setVideoError(requestId);
                if (adType==1){
                //穿山甲普通版
//                loadAd(pid, TTAdConstant.VERTICAL);
                //穿山甲OPPO
                 loadAd(pid, TTVfConstant.VERTICAL);
                }else{
                //广点通
                loadGDTAd(pid);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
          if (campaignFragment!=null&&campaignFragment.canGoBack()){
           campaignFragment.goBack();
           return true;
          }
        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean mIsExpress = false; //是否请求模板广告
    private boolean mIsLoaded = false; //视频是否加载完成
    private boolean mHasShowDownloadActive = false;
    //穿山甲普通版
//    private void loadAd(final String codeId, int orientation) {
//        //step4:创建广告请求参数AdSlot,具体参数含义参考文档
//        AdSlot adSlot;
//        if (mIsExpress) {
//            //个性化模板广告需要传入期望广告view的宽、高，单位dp，
//            adSlot = new AdSlot.Builder()
//                    .setCodeId(codeId)
//                    //模板广告需要设置期望个性化模板广告的大小,单位dp,激励视频场景，只要设置的值大于0即可
//                    .setExpressViewAcceptedSize(500,500)
//                    .build();
//        } else {
//            //模板广告需要设置期望个性化模板广告的大小,单位dp,代码位是否属于个性化模板广告，请在穿山甲平台查看
//            adSlot = new AdSlot.Builder()
//                    // 必选参数 设置您的CodeId
//                    .setCodeId(codeId)
//                            // 必选参数 设置广告图片的最大尺寸及期望的图片宽高比，单位Px
//                            // 注：必填字段，期望的图片尺寸，返回尺寸可能有差异
//                            .setImageAcceptedSize(1080, 1920)
//                            //模板广告需要设置期望个性化模板广告的大小,单位dp,代码位是否属于个性化模板广告，请在穿山甲平台查看
//                            .setExpressViewAcceptedSize(1080, 1920)
//                            // 可选参数，针对信息流广告设置每次请求的广告返回个数，最多支持3个
//                            .setAdCount(3)
//                            //请求原生广告时候需要设置，参数为TYPE_BANNER或TYPE_INTERACTION_AD
//                            .setNativeAdType(AdSlot.TYPE_BANNER)
//                            //非必传参数，仅奖励发放服务端回调时需要使用
//                            .setUserID(login.getUserid())
//                            //非必传参数，仅奖励发放服务端回调时需要使用
//                            .setMediaExtra("media_extra")
//                            .build();
//        }
//        //step5:请求广告
//        mTTAdNative.loadRewardVideoAd(adSlot, new TTAdNative.RewardVideoAdListener() {
//            @Override
//            public void onError(int code, String message) {
//                dismissDialog();
//                Log.e("ttttttttt", "Callback --> onError:"+message);
//            }
//
//            //视频广告加载后，视频资源缓存到本地的回调，在此回调后，播放本地视频，流畅不阻塞。
//            @Override
//            public void onRewardVideoCached() {
//                mIsLoaded = true;
//                dismissDialog();
//                if (mttRewardVideoAd != null&&mIsLoaded) {
//                    //step6:在获取到广告后展示,强烈建议在onRewardVideoCached回调后，展示广告，提升播放体验
//                    //该方法直接展示广告
//                    //展示广告，并传入广告展示的场景
//                    mttRewardVideoAd.showRewardVideoAd(ADActivity.this, TTAdConstant.RitScenes.CUSTOMIZE_SCENES, "scenes_test");
//                    mttRewardVideoAd = null;
//                } else {
//                }
//            }
//
//            //视频广告的素材加载完毕，比如视频url等，在此回调后，可以播放在线视频，网络不好可能出现加载缓冲，影响体验。
//            @Override
//            public void onRewardVideoAdLoad(TTRewardVideoAd ad) {
//                mIsLoaded = false;
//                mttRewardVideoAd = ad;
//                mttRewardVideoAd.setRewardAdInteractionListener(new TTRewardVideoAd.RewardAdInteractionListener() {
//
//                    @Override
//                    public void onAdShow() {
//                    }
//
//                    @Override
//                    public void onAdVideoBarClick() {
//                    }
//
//                    @Override
//                    public void onAdClose() {
//                    }
//
//                    //视频播放完成回调
//                    @Override
//                    public void onVideoComplete() {
//                    }
//
//                    @Override
//                    public void onVideoError() {
//                    }
//
//                    //视频播放完成后，奖励验证回调，rewardVerify：是否有效，rewardAmount：奖励梳理，rewardName：奖励名称
//                    @Override
//                    public void onRewardVerify(boolean rewardVerify, int rewardAmount, String rewardName, int errorCode, String errorMsg) {
//                    }
//
//                    @Override
//                    public void onSkippedVideo() {
//                    }
//                });
//                mttRewardVideoAd.setDownloadListener(new TTAppDownloadListener() {
//                    @Override
//                    public void onIdle() {
//                        mHasShowDownloadActive = false;
//                    }
//
//                    @Override
//                    public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
//
//                        if (!mHasShowDownloadActive) {
//                            mHasShowDownloadActive = true;
//                        }
//                    }
//
//                    @Override
//                    public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
//                    }
//
//                    @Override
//                    public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
//                    }
//
//                    @Override
//                    public void onDownloadFinished(long totalBytes, String fileName, String appName) {
//                    }
//
//                    @Override
//                    public void onInstalled(String fileName, String appName) {
//                    }
//                });
//            }
//        });
//    }

    //穿山甲OPPO
    private void loadAd(final String codeId, int orientation) {
        //step4:创建广告请求参数AdSlot,具体参数含义参考文档
        VfSlot adSlot;
        if (mIsExpress) {
            //个性化模板广告需要传入期望广告view的宽、高，单位dp，
            adSlot = new VfSlot.Builder()
                    .setCodeId(codeId)
                    //模板广告需要设置期望个性化模板广告的大小,单位dp,激励视频场景，只要设置的值大于0即可
                    .setExpressViewAcceptedSize(500,500)
                    .build();
        } else {
            //模板广告需要设置期望个性化模板广告的大小,单位dp,代码位是否属于个性化模板广告，请在穿山甲平台查看
            adSlot = new VfSlot.Builder()
                    // 必选参数 设置您的CodeId
                    .setCodeId(codeId)
                    // 必选参数 设置广告图片的最大尺寸及期望的图片宽高比，单位Px
                    // 注：必填字段，期望的图片尺寸，返回尺寸可能有差异
                    .setImageAcceptedSize(1080, 1920)
                    //模板广告需要设置期望个性化模板广告的大小,单位dp,代码位是否属于个性化模板广告，请在穿山甲平台查看
                    .setExpressViewAcceptedSize(1080, 1920)
                    // 可选参数，针对信息流广告设置每次请求的广告返回个数，最多支持3个
                    .setAdCount(3)
                    //请求原生广告时候需要设置，参数为TYPE_BANNER或TYPE_INTERACTION_AD
                    .setNativeAdType(VfSlot.TYPE_BANNER)
                    //非必传参数，仅奖励发放服务端回调时需要使用
                    .setUserID(login.getUserid())
                    //非必传参数，仅奖励发放服务端回调时需要使用
                    .setMediaExtra("media_extra")
                    .build();
        }
        //step5:请求广告
        mTTAdNative.loadRdVideoVr(adSlot, new TTVfNative.RdVideoVfListener() {
            @Override
            public void onError(int code, String message) {
            }

            //视频广告加载后，视频资源缓存到本地的回调，在此回调后，播放本地视频，流畅不阻塞。
            @Override
            public void onRdVideoCached() {
                mIsLoaded = true;
                dismissDialog();
                if (mttRdVideoObject!= null&&mIsLoaded) {
                    //step6:在获取到广告后展示,强烈建议在onRewardVideoCached回调后，展示广告，提升播放体验
                    //该方法直接展示广告
                    //展示广告，并传入广告展示的场景
                    mttRdVideoObject.showRdVideoVr(ADActivity.this, TTVfConstant.RitScenes.CUSTOMIZE_SCENES, "scenes_test");
                    mttRdVideoObject = null;
                }
            }

            //视频广告的素材加载完毕，比如视频url等，在此回调后，可以播放在线视频，网络不好可能出现加载缓冲，影响体验。
            @Override
            public void onRdVideoVrLoad(TTRdVideoObject ad) {
                mIsLoaded = false;
                mttRdVideoObject = ad;
                mttRdVideoObject.setRdVrInteractionListener(new TTRdVideoObject.RdVrInteractionListener() {

                    @Override
                    public void onShow() {
                    }

                    @Override
                    public void onVideoBarClick() {
                    }

                    @Override
                    public void onClose() {
                    }

                    //视频播放完成回调
                    @Override
                    public void onVideoComplete() {
                    }

                    @Override
                    public void onVideoError() {
                    }

                    //视频播放完成后，奖励验证回调，rewardVerify：是否有效，rewardAmount：奖励梳理，rewardName：奖励名称
                    @Override
                    public void onRdVerify(boolean rewardVerify, int rewardAmount, String rewardName) {
                    }

                    @Override
                    public void onSkippedVideo() {
                    }
                });
                mttRdVideoObject.setDownloadListener(new TTAppDownloadListener() {
                    @Override
                    public void onIdle() {
                        mHasShowDownloadActive = false;
                    }

                    @Override
                    public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                        if (!mHasShowDownloadActive) {
                            mHasShowDownloadActive = true;
                        }
                    }

                    @Override
                    public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                    }

                    @Override
                    public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                    }

                    @Override
                    public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                    }

                    @Override
                    public void onInstalled(String fileName, String appName) {
                    }
                });
            }
        });
    }

    //广点通
    private void loadGDTAd(String placeId){
        adLoaded = false;
        videoCached = false;
        rewardVideoAD = new RewardVideoAD(this, placeId, new RewardVideoADListener() {
            @Override
            public void onADClick() {

            }

            @Override
            public void onADClose() {

            }

            @Override
            public void onADExpose() {

            }

            @Override
            public void onADLoad() {
                adLoaded = true;
                // 3.展示广告
                if (adLoaded&&rewardVideoAD != null) {//广告展示检查1：广告成功加载，此处也可以使用videoCached来实现视频预加载完成后再展示激励视频广告的逻辑
                    if (!rewardVideoAD.hasShown()) {//广告展示检查2：当前广告数据还没有展示过
                        long delta = 1000;//建议给广告过期时间加个buffer，单位ms，这里demo采用1000ms的buffer
                        //广告展示检查3：展示广告前判断广告数据未过期
                        if (SystemClock.elapsedRealtime() < (rewardVideoAD.getExpireTimestamp() - delta)) {
                            rewardVideoAD.showAD();
                        } else {
                            Toast.makeText(ADActivity.this, "激励视频广告已过期，请再次请求广告后进行广告展示！", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(ADActivity.this, "此条广告已经展示过，请再次请求广告后进行广告展示！", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ADActivity.this, "成功加载广告后再进行广告展示！", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onADShow() {

            }

            @Override
            public void onError(AdError adError) {

            }

            @Override
            public void onReward() {

            }

            @Override
            public void onVideoCached() {
                videoCached = true;
            }

            @Override
            public void onVideoComplete() {

            }
        }, true);
        // 加载激励视频广告
        rewardVideoAD.loadAD();
    }
}
