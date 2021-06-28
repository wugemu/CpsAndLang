package com.lxkj.dmhw.defined;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.widget.GFImageView;

/**
 * 配置图片加载器
 * Created by Android on 2018/9/26.
 */

public class GlideImageLoader implements ImageLoader {

    @Override
    public void displayImage(Activity activity, String path, GFImageView imageView, Drawable defaultDrawable, int width, int height) {

        Glide.with(activity)
                .asDrawable()
                .load("file://" + path)
                .placeholder(defaultDrawable)
                .error(defaultDrawable)
                .override(width, height)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        imageView.setImageDrawable(resource);
                        return false;
                    }
                }).into(imageView);

    }

    @Override
    public void clearMemoryCache() {

    }
}
