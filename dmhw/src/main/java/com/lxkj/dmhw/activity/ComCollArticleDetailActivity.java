package com.lxkj.dmhw.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
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
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.adapter.ArticleListAdapter;
import com.lxkj.dmhw.bean.ComCollArticle;
import com.lxkj.dmhw.bean.ComCollArticleList;
import com.lxkj.dmhw.bean.ShareParams;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.MyLayoutManager;
import com.lxkj.dmhw.utils.Share;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ComCollArticleDetailActivity extends BaseActivity  {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.mTitle)
    TextView mTitle;
    @BindView(R.id.web)
    WebView web;
    @BindView(R.id.articleTitle)
    TextView articleTitle;
    @BindView(R.id.questime)
    TextView questime;
    @BindView(R.id.quesreadnum)
    TextView quesreadnum;

    @BindView(R.id.ques_layout)
    LinearLayout ques_layout;
    @BindView(R.id.article_layout)
    LinearLayout article_layout;
    @BindView(R.id.bottom_layout)
    RelativeLayout bottom_layout;

    @BindView(R.id.likenum)
    TextView likenum;
    @BindView(R.id.articlereadnum)
    TextView articlereadnum;
    @BindView(R.id.articletime)
    TextView articletime;

    @BindView(R.id.cc_share)
    TextView cc_share;
    @BindView(R.id.like_layout)
    LinearLayout like_layout;
    @BindView(R.id.cc_like)
    TextView cc_like;
    @BindView(R.id.cc_like_img)
    ImageView cc_like_img;
    @BindView(R.id.catelog_layout)
    LinearLayout catelog_layout;
    @BindView(R.id.share_layout)
    LinearLayout share_layout;
    @BindView(R.id.catelog_iv)
    ImageView catelog_iv;
    @BindView(R.id.share_iv)
    ImageView share_iv;

    @BindView(R.id.catelog)
    TextView catelog;
    //文章ID
    private String id;
    //常见问题没有目录
    private String from;
    private boolean isListClick=false;
    private String shareNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comcoll_artticle_detail);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams();
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        id=getIntent().getStringExtra("articleId");
        from=getIntent().getStringExtra("from");
        if (from.equals("Ques")){
            bottom_layout.setVisibility(View.GONE);
//            ques_layout.setVisibility(View.VISIBLE);
//            article_layout.setVisibility(View.GONE);
        }else{
            bottom_layout.setVisibility(View.VISIBLE);
//            ques_layout.setVisibility(View.GONE);
//            article_layout.setVisibility(View.VISIBLE);
        }
        TextPaint textPaint=mTitle.getPaint();
        textPaint.setFakeBoldText(true);
        httpPost("");

    }
    @Override
    public void mainMessage(Message message) {
    }
    @Override
    protected void onResume() {
        super.onResume();

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
                    mTitle.setText(article.getTitle());
                    cc_like.setText(article.getLikenum());
                    cc_share.setText(article.getSharenum());
                    if (article.getIslike().equals("1")) {
                        cc_like.setTextColor(Color.parseColor("#F84942"));
                        cc_like_img.setImageResource(R.mipmap.cc_like_check);
                    } else {
                        cc_like.setTextColor(Color.parseColor("#999999"));
                        cc_like_img.setImageResource(R.mipmap.cc_like);
                    }
                    doInit(article.getHtmlurl());
                    if (!from.equals("Ques")) {
                        if (!isListClick) {
                            articlelist = (ArrayList<ComCollArticleList>) JSON.parseArray(obj.getJSONArray("list").toString(), ComCollArticleList.class);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (message.what == LogicActions.ShareArticleContentSuccess) {
                   JSONObject obj = (JSONObject) message.obj;
//                       ShareParams params = new ShareParams();
//                       params.setTitle(article.getTitle());
//                       params.setContent("");
//                       params.setThumbData(article.getImgurl());
//                       params.setUrl(obj.optString("sharelink"));
//                       Share.getInstance(0).initialize(params, 1);
            shareNum=obj.optString("data");
            showshareDialog(obj.optString("sharelink"));
        }

        //赞操作
        if (message.what == LogicActions.LikeArticleContentSuccess) {
            JSONObject obj= (JSONObject) message.obj;
            article.setIslike("1");
            cc_like_img.setImageResource(R.mipmap.cc_like_check);
            cc_like.setTextColor(Color.parseColor("#F84942"));
            cc_like.setText(obj.optString("data"));
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

    private void httpPost(String time) {
        showDialog();
        paramMap.clear();
        paramMap.put("id", id);
        if (isListClick){
         paramMap.put("list", "1");
          }
        NetworkRequest.getInstance().POST(handler, paramMap, "ArticleContentDetail", HttpCommon.ArticleContentDetail);
    }

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
    @OnClick({R.id.back,R.id.catelog_layout,R.id.share_layout,R.id.like_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                isFinish();
                break;
            case R.id.catelog_layout:
                if (articlelist!=null&&articlelist.size()>0){
                    showArticleListDialog(articlelist);
                }
                break;
            case R.id.share_layout:
                if (article!=null) {
                    httpPostShare();
                }
                break;
            case R.id.like_layout:
                if (article!=null) {
                    if (article.getIslike().equals("1")){
                        httpPostUnLike();
                    }else {
                        httpPostLike();
                    }
                }
                break;
        }
    }

    /**
     * 设置WebView
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void doInit(String content) {
        WebSettings settings = web.getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // chromium, enable hardware acceleration
            web.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            // older android version, disable hardware acceleration
            web.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setJavaScriptEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        settings.setBlockNetworkImage(false);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setAppCacheEnabled(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        web.clearCache(false);
        web.getSettings().setDomStorageEnabled(true);
        web.setInitialScale(100);
        web.setDrawingCacheEnabled(true);
        web.post(() -> {
            web.loadUrl(content);
        });
    }


    //底部文章列表
    private void showArticleListDialog(ArrayList<ComCollArticleList> dataList){
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(this,R.style.DialogTheme);
        //2、设置布局
        View view = View.inflate(this,R.layout.dialog_comcoll_article_list,null);
        dialog.setContentView(view);
        TextView cc_title = dialog.findViewById(R.id.cc_title);
        cc_title.setText(article.getTypetitle());
        catelog_iv.setImageResource(R.mipmap.catelog_icon);
        catelog.setTextColor(Color.parseColor("#F84942"));
        RecyclerView  activity_article_list=dialog.findViewById(R.id.activity_article_list);
        activity_article_list.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(this, false));
        ArticleListAdapter ListAdapter=new ArticleListAdapter(this,id,0);
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
                httpPost("");
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
    private void showshareDialog(String sharelink){
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
        params.setContent("NN俱乐部商学院");
        params.setThumbData("");
        params.setUrl(sharelink);
        share_wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Share.getInstance(0).WeChat(params, true);
                cc_share.setText(shareNum);
            }
        });
        share_wechat_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Share.getInstance(0).WeChat(params, false);
                cc_share.setText(shareNum);
            }
        });
        share_qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Share.getInstance(0).QQ(params, true);
                cc_share.setText(shareNum);
            }
        });
        share_qq_zone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Share.getInstance(0).QQ(params, false);
                cc_share.setText(shareNum);
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

}
