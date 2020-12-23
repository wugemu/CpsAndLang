package com.lxkj.dmhw.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.utils.Utils;


/**
 * Banner
 * Created by Zyhant on 2018/1/2.
 */

public class BannerMAdapter implements Holder<String> {

    private ImageView imageView;
    private int width;

    public BannerMAdapter(int width) {

       this.width=width;
    }

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
//        imageView.setBackgroundResource(R.mipmap.transparent_logo_bg);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, String data) {
      Utils.displayImageRounded(context,data,imageView, 5);
    }
}
