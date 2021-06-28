package com.lxkj.dmhw.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.adapter.ArticleListAdapter;
import com.lxkj.dmhw.bean.ComCollArticle;
import com.lxkj.dmhw.bean.ComCollArticleList;
import com.lxkj.dmhw.bean.ShareParams;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.defined.JzvdStdShowShareButtonAfterFullscreen;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.MyLayoutManager;
import com.lxkj.dmhw.utils.Share;
import com.lxkj.dmhw.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class VideoActivity300 extends BaseActivity {

    @BindView(R.id.video_player300)
    JzvdStdShowShareButtonAfterFullscreen videoPlayer;
    @BindView(R.id.title)
    TextView title_tv;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.share_layout)
    LinearLayout share_layout;
    @BindView(R.id.catelog_layout)
    LinearLayout catelog_layout;
    @BindView(R.id.like_layout)
    LinearLayout like_layout;
    @BindView(R.id.cc_like)
    TextView cc_like;
    @BindView(R.id.cc_share)
    TextView cc_share;
    @BindView(R.id.cc_like_img)
    ImageView cc_like_img;

    @BindView(R.id.catelog_iv)
    ImageView catelog_iv;
    @BindView(R.id.share_iv)
    ImageView share_iv;

    @BindView(R.id.catelog)
    TextView catelog;



    private String id, title;
    private boolean isPlayResume=false;
    private int shareType=0;
    private boolean isListClick=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video300);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O) {
            Utils.isTranslucentOrFloating(this);
        }
        id = getIntent().getExtras().getString("id");
        title = getIntent().getExtras().getString("title");
        TextPaint textPaint=title_tv.getPaint();
        textPaint.setFakeBoldText(true);
        if(title.equals("")){
            title_tv.setText("视频教程");
        }else{
            title_tv.setText(title);
        }
        videoPlayer.replayTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JzvdStd.releaseAllVideos();
                JzvdStd.clearSavedProgress(VideoActivity300.this,article.getVideourl());
                JzvdStd.setVideoImageDisplayType(JzvdStd.VIDEO_IMAGE_DISPLAY_TYPE_ADAPTER);
                videoPlayer.setUp(article.getVideourl(), title, JzvdStd.SCREEN_WINDOW_NORMAL);
                videoPlayer.startButton.performClick();
            }
        });

        //权限全部获取
        if (Utils.checkPermision(this,1005,true)) {
          httpPost();
        }else{
//            videoPlayer.bottomLayout.setVisibility(View.GONE);
        }
    }


    private void httpPost() {
        paramMap.clear();
        paramMap.put("id", id);
        if (isListClick){
        paramMap.put("list", "1");
        }
        NetworkRequest.getInstance().POST(handler, paramMap, "ArticleContentDetail", HttpCommon.ArticleContentDetail);
    }
    @OnClick({R.id.back,R.id.catelog_layout,R.id.share_layout,R.id.like_layout})
    public void onViewClicked(View view) {
        Intent intent=null;
        switch (view.getId()) {
            case R.id.back:
                isFinish();
                break;
            case R.id.catelog_layout:
                if (DateStorage.getLoginStatus()){
                if (articlelist!=null&&articlelist.size()>0){
                    showArticleListDialog(articlelist);
                }
                }else{
                  intent=new Intent(this,LoginActivity.class);
                }

                break;
            case R.id.share_layout:
                if (DateStorage.getLoginStatus()) {
                    shareType = 0;
                    doshareUrl();
                }else{
                    intent=new Intent(this,LoginActivity.class);
                }
                break;
            case R.id.like_layout:
                if (DateStorage.getLoginStatus()) {
                    if (article != null) {
                        if (article.getIslike().equals("1")) {
                            httpPostUnLike();
                        } else {
                            httpPostLike();
                        }
                    }
                }else{
                    intent=new Intent(this,LoginActivity.class);
                }
                break;
        }
        if (intent!=null){
            startActivity(intent);
        }
    }

    private void  doshareUrl(){
        if (article!=null) {
            httpPostShare();
        }
    }

    //分享
    private void httpPostShare() {
        showDialog();
        paramMap.clear();
        paramMap.put("id", id);
        NetworkRequest.getInstance().POST(handler, paramMap, "ShareArticleContent", HttpCommon.ShareArticleContent);
    }

    //赞操作
    private void httpPostLike() {
        showDialog();
        paramMap.clear();
        paramMap.put("id", id);
        NetworkRequest.getInstance().POST(handler, paramMap, "LikeArticleContent", HttpCommon.LikeArticleContent);
    }
    //取消赞操作
    private void httpPostUnLike() {
        showDialog();
        paramMap.clear();
        paramMap.put("id", id);
        NetworkRequest.getInstance().POST(handler, paramMap, "UnlikeArticleContent", HttpCommon.UnlikeArticleContent);
    }

    @Override
    public void mainMessage(Message message) {
        if (message.what == LogicActions.ShareVideoSuccess) {
          shareType=1;
          doshareUrl();
        }
    }

    @Override
    public void childMessage(Message message) {

    }
    private ArrayList<ComCollArticleList> articlelist;
    ComCollArticle article = null;
    @Override
    public void handlerMessage(Message message) {
        if (message.what == LogicActions.ArticleContentDetailSuccess) {
            JSONObject obj= (JSONObject) message.obj;
            try {
                article = JSON.parseObject(obj.getJSONObject("data").toString(), ComCollArticle.class);
                if (article!=null) {
                    if (article.getIslike().equals("1")) {
                        cc_like.setTextColor(Color.parseColor("#F84942"));
                        cc_like_img.setImageResource(R.mipmap.cc_like_check);
                    } else {
                        cc_like.setTextColor(Color.parseColor("#999999"));
                        cc_like_img.setImageResource(R.mipmap.cc_like);
                    }
                    cc_like.setText(article.getLikenum());
                    cc_share.setText(article.getSharenum());
                    title_tv.setText(article.getTitle());
                    JzvdStd.releaseAllVideos();
                    JzvdStd.clearSavedProgress(this, article.getVideourl());
                    JzvdStd.setVideoImageDisplayType(JzvdStd.VIDEO_IMAGE_DISPLAY_TYPE_ADAPTER);
                    videoPlayer.setUp(article.getVideourl(), title, JzvdStd.SCREEN_WINDOW_NORMAL);
                    videoPlayer.startButton.performClick();
                    if (!isListClick) {
                        articlelist = (ArrayList<ComCollArticleList>) JSON.parseArray(obj.getJSONArray("list").toString(), ComCollArticleList.class);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //分享视频连接
        if (message.what == LogicActions.ShareArticleContentSuccess) {
            JSONObject obj = (JSONObject) message.obj;
            if (shareType==0) {
                showshareDialog(obj.optString("sharelink"),obj.optString("data"));
            }else{
                videoPlayer.setdata(article,obj.optString("sharelink"),article.getImgurl());
            }
        }

        //赞操作
        if (message.what == LogicActions.LikeArticleContentSuccess) {
            JSONObject obj= (JSONObject) message.obj;
            cc_like.setText(obj.optString("data"));
                article.setIslike("1");
               cc_like.setTextColor(Color.parseColor("#F84942"));
                cc_like_img.setImageResource(R.mipmap.cc_like_check);
        }
        if (message.what == LogicActions.UnlikeArticleContentSuccess) {
            JSONObject obj= (JSONObject) message.obj;
            cc_like.setText(obj.optString("data"));
            article.setIslike("0");
            cc_like.setTextColor(Color.parseColor("#999999"));
            cc_like_img.setImageResource(R.mipmap.cc_like);
        }
        dismissDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isPlayResume) {
            JzvdStd.goOnPlayOnResume();
            isPlayResume = false;
        }
    }

    @Override
    protected void onPause() {
        try{
        super.onPause();
        JzvdStd.goOnPlayOnPause();
        }catch (Exception e){
        }
        isPlayResume = true;


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        JzvdStd.releaseAllVideos();
    }

    //底部文章列表
    private void showArticleListDialog(ArrayList<ComCollArticleList> dataList) {
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(this, R.style.DialogTheme);
        //2、设置布局
        View view = View.inflate(this, R.layout.dialog_comcoll_article_list, null);
        dialog.setContentView(view);
        TextView cc_title = dialog.findViewById(R.id.cc_title);
        cc_title.setText(article.getTypetitle());
        catelog_iv.setImageResource(R.mipmap.catelog_icon);
        catelog.setTextColor(Color.parseColor("#F84942"));
        RecyclerView activity_article_list = dialog.findViewById(R.id.activity_article_list);
        activity_article_list.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(this, false));
        ArticleListAdapter ListAdapter = new ArticleListAdapter(this, id, 1);
        activity_article_list.setAdapter(ListAdapter);
        ListAdapter.setNewData(dataList);
        if (dataList.size() > 0) {
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i).getId().equals(id)) {
                activity_article_list.scrollToPosition(i);
                break;
            }
        }
    }
        ListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                showDialog();
                dialog.dismiss();
                catelog_iv.setImageResource(R.mipmap.article_list);
                catelog.setTextColor(Color.parseColor("#999999"));
                ComCollArticleList Item= (ComCollArticleList) adapter.getData().get(position);
                id=Item.getId();
                isListClick=true;
                httpPost();
            }
        });
        RelativeLayout free_list_layout=dialog.findViewById(R.id.free_list_layout);

        free_list_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                catelog_iv.setImageResource(R.mipmap.article_list);
                catelog.setTextColor(Color.parseColor("#999999"));
            }
        });

        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle2);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawableResource(R.color.Transparent00000000);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);//设置点击Dialog外部任意区域关闭Dialog
        dialog.setCancelable(false);
    }


    //底部分享
    private void showshareDialog(String sharelink,String num){
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(this,R.style.DialogTheme);
        //2、设置布局
        View view = View.inflate(this,R.layout.dialog_video_share1,null);
        dialog.setContentView(view);
        share_iv.setImageResource(R.mipmap.share_red);
        cc_share.setTextColor(Color.parseColor("#F84942"));
        LinearLayout share_wechat=dialog.findViewById(R.id.share_wechat);
        LinearLayout share_wechat_friends=dialog.findViewById(R.id.share_wechat_friends);
        LinearLayout share_qq=dialog.findViewById(R.id.share_qq);
        LinearLayout share_qq_zone=dialog.findViewById(R.id.share_qq_zone);
        ShareParams params = new ShareParams();
        params.setTitle(article.getTitle());
        params.setContent("呆萌好物商学院");
        params.setThumbData("");
        params.setUrl(sharelink);
        share_wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Share.getInstance(0).WeChat(params, true);
                cc_share.setText(num);
            }
        });
        share_wechat_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Share.getInstance(0).WeChat(params, false);
                cc_share.setText(num);
            }
        });
        share_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Share.getInstance(0).QQ(params, true);
                cc_share.setText(num);
            }
        });
        share_qq_zone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Share.getInstance(0).QQ(params, false);
                cc_share.setText(num);
            }
        });
        RelativeLayout share_layout=dialog.findViewById(R.id.share_layout);

        share_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share_iv.setImageResource(R.mipmap.cc_share);
                cc_share.setTextColor(Color.parseColor("#999999"));
                dialog.dismiss();
            }
        });
        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle2);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawableResource(R.color.Transparent00000000);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);//设置点击Dialog外部任意区域关闭Dialog
        dialog.setCancelable(false);
    }

    @Override
    public void onBackPressedSupport() {
        if (Jzvd.backPress()) {
            return;
        }
      isFinish();
    }


//    /**
//     * 处理权限返回状态
//     * @param requestCode 请求状态
//     * @param permissions
//     * @param grantResults 授权结果
//     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        try {
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//            if (requestCode == Utils.WRITE_EXTERNAL_STORAGE_REQUEST) {
//                for (int i = 0; i < grantResults.length; i++) {
//                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
//                        // 授权成功
//                        Utils.haspermission=true;
//                        videoPlayer.bottomLayout.setVisibility(View.VISIBLE);
//                        httpPost();
//                    } else {
//                        Utils.haspermission=false;
//                        // 授权失败
//                        switch (permissions[i]) {
//                            case Manifest.permission.READ_PHONE_STATE:// 手机状态
//                                permissionsPrompt("获取手机信息");
//                                break;
//                            case Manifest.permission.WRITE_EXTERNAL_STORAGE:// 储存空间
//                                permissionsPrompt("读写手机存储");
//                                break;
//                        }
//                        return;
//                    }
//                }
//                // 有权限了b
//
//            }
//        } catch (Exception e) {
//            Logger.e(e, "");
//        }
//    }
//
//    /**
//     * 没有权限提醒
//     * @param permissions
//     */
//    private void permissionsPrompt(String permissions) {
//        JurisdictionDialog dialog = new JurisdictionDialog(this, permissions);
//        dialog.showDialog();
//    }
}
