package com.lxkj.dmhw.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import androidx.viewpager.widget.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.Poster;
import com.lxkj.dmhw.utils.Utils;

import java.util.ArrayList;

/**
 * 海报
 * Created by Android on 2018/7/13.
 */

public class UltraPagerAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<Poster.PosterList> list;

    private Bitmap iBitmap;
    private String iCode;

    public UltraPagerAdapter(Context context, ArrayList<Poster.PosterList> list, Bitmap bitmap,String code) {
        this.context = context;
        this.list = list;
        this.iBitmap=bitmap;
        this.iCode=code;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View convertView = View.inflate(context, R.layout.adapter_pager_ultra, null);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.adapter_pager_ultra_image);
        Utils.displayImageRoundedAll(context, list.get(position).getPosterurl(), imageView,8);
        ImageView qrimageView = (ImageView) convertView.findViewById(R.id.invite_share_qr);
        TextView txtCode = (TextView) convertView.findViewById(R.id.invite_code);
        qrimageView.setImageBitmap(iBitmap);
        txtCode.setText(iCode);
        container.addView(convertView);
        return convertView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        LinearLayout view = (LinearLayout) object;
        container.removeView(view);
    }
}
