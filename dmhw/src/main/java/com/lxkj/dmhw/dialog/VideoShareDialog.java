package com.lxkj.dmhw.dialog;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.ComCollArticle;
import com.lxkj.dmhw.bean.ShareParams;
import com.lxkj.dmhw.defined.JzvdStdShowShareButtonAfterFullscreen;
import com.lxkj.dmhw.defined.SimpleDialog;
import com.lxkj.dmhw.utils.Share;


public class VideoShareDialog extends SimpleDialog<ComCollArticle> {

    private RelativeLayout share_layout;
    private LinearLayout share_wechat;
    private LinearLayout share_wechat_friends;
    private LinearLayout share_qq;
    private LinearLayout share_qq_zone;
    ShareParams params;
    /**
     * 初始化
     * @param context  上下文
     * @param data     数据
     */
    public VideoShareDialog(Context context, ComCollArticle data) {
        super(context, R.layout.dialog_video_share, data, true, true);
    }

    @Override
    protected void convert(ViewHolder helper) {
        share_layout = helper.getView(R.id.share_layout);
        share_layout.setOnClickListener(this);
        share_wechat = helper.getView(R.id.share_wechat);
        share_wechat.setOnClickListener(this);
        share_wechat_friends = helper.getView(R.id.share_wechat_friends);
        share_wechat_friends.setOnClickListener(this);
        share_qq = helper.getView(R.id.share_qq);
        share_qq.setOnClickListener(this);
        share_qq_zone = helper.getView(R.id.share_qq_zone);
        share_qq_zone.setOnClickListener(this);
        params = new ShareParams();
        params.setTitle(JzvdStdShowShareButtonAfterFullscreen.title);
        params.setContent("NN俱乐部商学院");
        params.setUrl(JzvdStdShowShareButtonAfterFullscreen.url);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share_layout:
                hideDialog();
                break;
            case R.id.share_wechat:
                Share.getInstance(0).WeChat(params, true);
                break;
            case R.id.share_wechat_friends:
                Share.getInstance(0).WeChat(params, false);
                break;
            case R.id.share_qq:
                Share.getInstance(0).QQ(params, true);
                break;
            case R.id.share_qq_zone:
                Share.getInstance(0).QQ(params, false);
                break;
        }
    }

}
