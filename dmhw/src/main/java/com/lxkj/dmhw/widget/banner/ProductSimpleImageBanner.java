package com.lxkj.dmhw.widget.banner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.andlang.util.ActivityUtil;
import com.example.test.andlang.util.imageload.ImageLoadUtils;
import com.flyco.banner.widget.Banner.BaseIndicatorBanner;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.self.NewsBean;
import com.lxkj.dmhw.utils.BBCUtil;
import com.lxkj.dmhw.utils.VideoUtil;
import com.lxkj.dmhw.widget.SampleCoverVideo;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1 on 2017/1/23.
 */
public class ProductSimpleImageBanner extends BaseIndicatorBanner<NewsBean, ProductSimpleImageBanner> {
//    private ColorDrawable colorDrawable;
//    private ReleaseBitmap releaseBitmap;


    public ProductSimpleImageBanner(Context context) {
        this(context, null, 0);
    }

    public ProductSimpleImageBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProductSimpleImageBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
//        colorDrawable = new ColorDrawable(Color.parseColor("#555555"));
    }
//
//    public void setReleaseBitmap(ReleaseBitmap releaseBitmap){
//        this.releaseBitmap=releaseBitmap;
//    }
    @Override
    public void onTitleSlect(TextView tv, int position) {


    }

    public void pause(){
        GSYVideoManager.onPause();
    }

    public void stop(){
        GSYVideoManager.releaseAllVideos();
    }

    @Override
    public View onCreateItemView(int position) {
        View inflate=null;
        NewsBean newsBean=mDatas.get(position);
        if (!BBCUtil.isEmpty(newsBean.getVideoUrl())){
//            if (position==0){

            inflate=View.inflate(mContext, R.layout.adapter_simple_video,null);
            SampleCoverVideo player=inflate.findViewById(R.id.video_item_player);
            player.setStartAfterPrepared(true);
            player.getBackButton().setVisibility(GONE);
            //增加封面
            ImageView imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            player.loadCoverImage(newsBean.getImgUrl(),R.mipmap.image_def);
            player.setUp(newsBean.getVideoUrl(),true,null,null);
//            player.setUpLazy(url,true,null,null,null);
            //是否根据视频尺寸，自动选择竖屏全屏或者横屏全屏
            player.setAutoFullWithSize(false);
            //是否可以滑动调整
            player.setIsTouchWiget(true);
            //设置全屏按键功能
            player.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   player.startWindowFullscreen(mContext,false,false);
                }
            });

            player.setPlayingListener(new SampleCoverVideo.PlayingListener() {
                @Override
                public void playing() {

                }
            });
//            player.setStartAfterPrepared(true);
//            player.startPlayLogic();

        }else if(position==mDatas.size()-1){
            inflate   = View.inflate(mContext, R.layout.include_load_more, null);
        }else {
            inflate   = View.inflate(mContext, R.layout.adapter_simple_image, null);
            ImageView iv = inflate.findViewById(R.id.iv);
            final NewsBean item = mDatas.get(position);
            String imgUrl = item.getImgUrl();
            if (!TextUtils.isEmpty(imgUrl)) {
                ImageLoadUtils.doLoadImageUrl(iv,imgUrl);
            } else {
                iv.setImageResource(R.mipmap.image_def);
            }
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    List<String> imgList=new ArrayList<String>();
                    boolean isHaveVideo=false;
                    if(mDatas!=null&&mDatas.size()>0){
                        for (NewsBean newsBean:mDatas
                             ) {
                            if(BBCUtil.isEmpty(newsBean.getVideoUrl())){
                                if(!BBCUtil.isEmpty(newsBean.getImgUrl())) {
                                    imgList.add(newsBean.getImgUrl());
                                }
                            }else {
                                isHaveVideo=true;
                            }
                        }
                        if(imgList.size()>0) {
                            int post=position;
                            if (isHaveVideo) {
                                post=post-1;
                            }
                            BBCUtil.openImgPreview(mContext, post, imgList, false);
                        }
                    }
                }
            });
        }

        return inflate;
    }

    public List<NewsBean> getNewsBean(){
        return mDatas;
    }
}
