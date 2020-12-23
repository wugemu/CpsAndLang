package com.lxkj.dmhw.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.utils.Utils;


/**
 * Banner
 */

public class BannerMainAdapter implements Holder<String> {

    private ImageView imageView;
    public BannerMainAdapter() {
    }

    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setBackgroundResource(R.mipmap.default_for_pic);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, String data) {
        Utils.displayImage(context,data,imageView);
    }
}
