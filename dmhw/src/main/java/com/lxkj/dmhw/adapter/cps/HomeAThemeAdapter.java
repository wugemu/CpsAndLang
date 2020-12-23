package com.lxkj.dmhw.adapter.cps;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.request.target.SimpleTarget;
import com.example.test.andlang.andlangutil.BaseLangActivity;
import com.example.test.andlang.andlangutil.BaseLangAdapter;
import com.example.test.andlang.andlangutil.BaseLangViewHolder;
import com.example.test.andlang.util.BaseLangUtil;
import com.example.test.andlang.util.LogUtil;
import com.example.test.andlang.util.imageload.GlideUtil;
import com.example.test.andlang.util.imageload.ImageLoadUtils;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.self.ActivityNewBean;
import com.lxkj.dmhw.bean.self.ActivityitemitemBean;
import com.lxkj.dmhw.utils.BBCUtil;
import com.lxkj.dmhw.utils.Utils;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

public class HomeAThemeAdapter extends BaseLangAdapter<ActivityNewBean> {
    public HomeAThemeAdapter(Activity context, List<ActivityNewBean> data) {
        super(context, R.layout.gridview_atheme_item, data);
    }

    @Override
    public void convert(BaseLangViewHolder helper, int postion, ActivityNewBean item) {
        LinearLayout ll_atheme_content=helper.getView(R.id.ll_atheme_content);
        ll_atheme_content.removeAllViews();

        int leftright=(int)context.getResources().getDimension(R.dimen.dp_10);
        leftright=0;//豆腐块去除边距
        int topbottom=(int)context.getResources().getDimension(R.dimen.dp_10);
        if(postion==0&&postion==getCount()-1){
            ll_atheme_content.setPadding(leftright,topbottom,leftright,topbottom);
        } else if(postion==0){
            ll_atheme_content.setPadding(leftright,topbottom,leftright,0);
        }else if(postion==getCount()-1){
            ll_atheme_content.setPadding(leftright,0,leftright,topbottom);
        }else {
            ll_atheme_content.setPadding(leftright,0,leftright,0);
        }
        if(BBCUtil.isColorStr(item.getBgColor())) {
            ll_atheme_content.setBackgroundColor(Color.parseColor(item.getBgColor()));
        }
        List<ActivityitemitemBean> activityBeanList=item.getDetailList();
        if(activityBeanList!=null&&activityBeanList.size()>0){
            float widthall=0f;
            for (int i=0;i<activityBeanList.size();i++){
                ActivityitemitemBean itemBean=activityBeanList.get(i);
                widthall+=itemBean.getWidth();
            }

            float widthitem=(BaseLangUtil.getDisplayWidth(context)-leftright*2)/widthall;

            for (int i=0;i<activityBeanList.size();i++){
                final int itemposi=i;
                final ActivityitemitemBean itemBean=activityBeanList.get(i);
                int newWidth=(int)(widthitem*itemBean.getWidth());
                int newHeight=(int)(newWidth * itemBean.getHeight() / itemBean.getWidth());
                LinearLayout.LayoutParams vlp = new LinearLayout.LayoutParams(
                        (int)newWidth,newHeight);
                ImageView imageView = new ImageView(context);
                imageView.setLayoutParams(vlp);//设置TextView的布局
                Utils.displayImage(context,itemBean.getImgUrl(),imageView);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                ll_atheme_content.addView(imageView);
            }
        }
    }
}
