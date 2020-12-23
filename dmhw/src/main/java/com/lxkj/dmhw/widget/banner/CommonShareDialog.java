package com.lxkj.dmhw.widget.banner;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.test.andlang.util.ActivityUtil;
import com.example.test.andlang.util.ToastUtil;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.self.NewsBean;
import com.lxkj.dmhw.bean.self.ShareBean;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.utils.BBCUtil;
import com.lxkj.dmhw.utils.UMShareUtil;
import com.lxkj.dmhw.widget.dialog.ShareTipDialog;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商品分享
 */
public class CommonShareDialog {
    @BindView(R.id.ll_share_qzone)
    LinearLayout ll_share_qzone;
    @BindView(R.id.ll_share_sina2)
    LinearLayout ll_share_sina2;
    @BindView(R.id.ll_share_bottom2)
    LinearLayout ll_share_bottom2;
    @BindView(R.id.ll_tip_list)
    LinearLayout ll_tip_list;
    @BindView(R.id.lv_tip_list)
    ListView lv_tip_list;
    @BindView(R.id.tv_profit)
    TextView tvProfit;
    @BindView(R.id.tv_profit_info)
    TextView tvProfitInfo;
    @BindView(R.id.tv_qrname)
    TextView tv_qrname;
    @BindView(R.id.ll_qrcode)
    LinearLayout ll_qrcode;
    @BindView(R.id.ll_downsc)
    LinearLayout ll_downsc;
    private Activity context;
    private Dialog overdialog;
    private ShareBean shareBean;
    private boolean isShowPro;//是否显示至少赚
    private boolean isShowQR;//是否显示商品二维码
    private boolean isGift;//是否是礼包
    private boolean isShowDown;//是否显示下载素材
    private List<NewsBean> newsBeanList;
    public boolean isShortVideo;//是否短视频分享
    private Handler handler;


    //右上角商品列表分享
    public CommonShareDialog(Activity context, ShareBean shareBean) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.shareBean = shareBean;
        isShowPro=false;
        isShowQR=false;
        initView();
    }

    //H5分享
    public CommonShareDialog(Activity context, ShareBean shareBean,String profit,boolean isShowQR,boolean isGift) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.shareBean = shareBean;
        if(!BBCUtil.isEmpty(profit)){
            isShowPro=true;
        }else {
            isShowPro = false;
        }
        this.isShowQR=isShowQR;
        this.isGift=isGift;
        initView();
    }

    //秒杀商品 普通商品玩主玩客分享
    public CommonShareDialog(Activity context, ShareBean shareBean, int type,boolean ifSpecOfApp) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.shareBean = shareBean;
        isShowQR=true;
        if (type==1) {
            //秒杀 商品不显示至少赚  普通商品玩主显示 至少赚
            isShowPro=true;
        }else {
            isShowPro=false;
        }
        initView();
    }

    //秒杀商品 普通商品玩主玩客分享
    public CommonShareDialog(Activity context, ShareBean shareBean, int type,boolean ifSpecOfApp,List<NewsBean> newsBeanList,boolean isWithIn) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.shareBean = shareBean;
        this.newsBeanList=newsBeanList;
        isShowQR=true;
        if (type==1&&!isWithIn) {
            //玩主 商品显示至少赚  内购商品不显示至少赚
            isShowPro=true;
        }else {
            isShowPro=false;
        }
        isShowDown=true;
        initView();
    }

    //是礼包商品分享 不区分用户角色 一直显示至少赚
    public CommonShareDialog(Activity context, ShareBean shareBean, boolean isGift,List<NewsBean> newsBeanList) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.shareBean = shareBean;
        isShowQR=true;
        this.isGift=isGift;
        this.newsBeanList=newsBeanList;
        //是礼包商品分享 不区分用户角色 一直显示至少赚
        if (isGift) {
            //普通商品 玩主显示 至少赚
            isShowPro=true;
        }else {
            isShowPro=false;
        }
        isShowDown=true;
        initView();
    }

    //Weex H5 调用 显示赚
    public CommonShareDialog(Activity context, ShareBean shareBean, double profit,boolean isGift) {
        // TODO Auto-generated constructor stub
        this.context = context;
        shareBean.setProfit(profit);//设置赚
        this.shareBean = shareBean;
        isShowQR=true;
        this.isGift=isGift;
        //显示至少赚
        isShowPro=true;
        initView();
    }

    //Weex H5 调用 不显示赚
    public CommonShareDialog(Activity context, ShareBean shareBean,boolean isGift) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.shareBean = shareBean;
        isShowQR=true;
        this.isGift=isGift;
        //显示至少赚
        isShowPro=false;
        initView();
    }

    public void setShortVideo(boolean shortVideo) {
        isShortVideo = shortVideo;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    private void initView() {
        View overdiaView = View.inflate(context, R.layout.dialog_common_share,
                null);
        ButterKnife.bind(this, overdiaView);
        overdialog = new Dialog(context, R.style.dialog_lhp);
        overdialog.setContentView(overdiaView);
        if (isShowPro&&shareBean.getProfit()>0){
            tvProfit.setVisibility(View.VISIBLE);
            tvProfit.setText("至少赚¥"+BBCUtil.getDoubleFormat(shareBean.getProfit()));
            tvProfitInfo.setText("只要你的朋友通过的你链接购买此商品，你就能赚到至少"+BBCUtil.getDoubleFormat(shareBean.getProfit())+"元的利润");
            tvProfitInfo.setVisibility(View.VISIBLE);
        }else {
            tvProfit.setVisibility(View.GONE);
            tvProfitInfo.setVisibility(View.GONE);
        }

        if(isShowQR&& DateStorage.getLoginStatus()){
            //只有登录的时候显示商品二维码
            ll_qrcode.setVisibility(View.VISIBLE);
//            if(isGift){
//                //礼包商品
//                tv_qrname.setText("礼包二维码");
//            }else {
                //普通商品
                tv_qrname.setText("分享海报");
//            }
        }else {
            ll_qrcode.setVisibility(View.INVISIBLE);
        }

        if(isShowDown&&DateStorage.getLoginStatus()){
            //是否显示下载素材图标
            ll_downsc.setVisibility(View.VISIBLE);
        }else {
            ll_downsc.setVisibility(View.INVISIBLE);
        }
        overdiaView.getBackground().setAlpha(255);
        showDialog();
    }




    private void share(SHARE_MEDIA media) {

        if (shareBean != null) {
            if (BBCUtil.isEmpty(shareBean.getLinkUrl())) {
                shareBean.setLinkUrl("");
            }
            UMWeb umWeb = new UMWeb(shareBean.getLinkUrl());
            if (BBCUtil.isEmpty(shareBean.getImgUrl())) {
                umWeb.setThumb(new UMImage(context, R.mipmap.ic_launcher));
            } else {
                umWeb.setThumb(new UMImage(context, shareBean.getImgUrl()));
            }
            if (SHARE_MEDIA.SINA == media) {
                umWeb.setTitle(shareBean.getTitle() + shareBean.getLinkUrl());
            } else {
                umWeb.setTitle(shareBean.getTitle());
            }
            umWeb.setDescription(shareBean.getDes());
            UMShareUtil.getInstance().umengSharePlatform(context, umWeb, null, media);
        }
        cancelDialog();
    }

    public void showDialog() {
        if (overdialog != null) {
            overdialog.show();
            Window win = overdialog.getWindow();
            win.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = win.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            win.setGravity(Gravity.BOTTOM);
            win.setAttributes(lp);

        }

    }

    public void cancelDialog() {
        if (overdialog != null) {
            overdialog.dismiss();
        }
    }


    @OnClick({R.id.ll_share_wx, R.id.ll_share_wxcircle, R.id.ll_share_qq, R.id.ll_share_qzone, R.id.ll_share_sina, R.id.ll_share_sina2,R.id.iv_close,R.id.ll_copy_link,R.id.ll_qrcode,R.id.ll_downsc})
    public void onClick(View view) {
        if (handler!=null){
            handler.sendEmptyMessage(10);
        }
        switch (view.getId()) {
            case R.id.ll_copy_link:
                String copyStr=shareBean.getCommand();
                BBCUtil.copy(copyStr,context);
                ToastUtil.show(context,"已复制到剪切板");
                cancelDialog();
                break;
            case R.id.ll_qrcode:
//                new ProductQrcodeDialog(context,shareBean);
                cancelDialog();
                break;
            case R.id.ll_share_wx:
                if (BBCUtil.isWXAppInstalledAndSupported(context)) {
                    if (shareBean.getType()==3){
                        UMShareUtil.getInstance().umengShareMin(context,shareBean,SHARE_MEDIA.WEIXIN,0);
                    }else{
                        share(SHARE_MEDIA.WEIXIN);
                    }
                } else {
                    ToastUtil.show(context, "未检测到微信客户端，请安装");
                }
                break;
            case R.id.ll_share_wxcircle:
                if (BBCUtil.isWXAppInstalledAndSupported(context)) {
                    share(SHARE_MEDIA.WEIXIN_CIRCLE);
                } else {
                    ToastUtil.show(context, "未检测到微信客户端，请安装");
                }
                break;
            case R.id.ll_share_qq:
                if (UMShareAPI.get(context).isInstall(context, SHARE_MEDIA.QQ)) {
                    share(SHARE_MEDIA.QQ);
                } else {
                    ToastUtil.show(context, "未检测到QQ客户端，请安装");
                }
                break;
            case R.id.ll_share_qzone:
                if (UMShareAPI.get(context).isInstall(context, SHARE_MEDIA.QQ)) {
                    share(SHARE_MEDIA.QZONE);
                } else {
                    ToastUtil.show(context, "未检测到QQ客户端，请安装");
                }
                break;
            case R.id.ll_share_sina:
            case R.id.ll_share_sina2:
                if (UMShareAPI.get(context).isInstall(context, SHARE_MEDIA.SINA)) {
                    share(SHARE_MEDIA.SINA);
                } else {
                    ToastUtil.show(context, "未检测到微博客户端，请安装");
                }

//                if(params.size()>0){
//                    UMShareUtil.getInstance().umengShareSina3(context,params);
//                    cancelDialog();
//                }else{
//                    ToastUtil.show(context,"分享失败");
//                }
                break;
            case R.id.ll_downsc:
                //素材下载
                if(BBCUtil.checkStoragePer(context)){
                    //有本地存储权限
                    if(newsBeanList!=null&&newsBeanList.size()>0){
                        List<String> imageList=new ArrayList<String>();
                        for (NewsBean bean:newsBeanList) {
                            if(!BBCUtil.isEmpty(bean.getVideoUrl())){
                                //视频下载
                                BBCUtil.saveVideo(context,bean.getVideoUrl());
                            }else if(!BBCUtil.isEmpty(bean.getImgUrl())){
                                imageList.add(bean.getImgUrl());
                            }
                        }
                        //商品二维码下载
                        if(!BBCUtil.isEmpty(shareBean.getShareImgUrl())){
                            imageList.add(shareBean.getShareImgUrl());
                        }
                        //图片下载
                        BBCUtil.save9Img(context,imageList);
                    }
                    if(shareBean!=null) {
                        BBCUtil.copy(shareBean.getCommand(), context);
                    }
                    cancelDialog();
                    ShareTipDialog shareTipDialog=new ShareTipDialog(context);
                }
                break;
            case R.id.iv_close:
                cancelDialog();
                break;
        }
    }


}
