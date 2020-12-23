package com.lxkj.dmhw.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxkj.dmhw.R;


/**
 * Created by lenovo on 2018/9/26.
 */

public class TextPrograssBar extends RelativeLayout {
    private Context context;
    private TextView tvLeft,tvRight,tvCenter;
    private ProgressBar progressBar;
    public TextPrograssBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        initView();
    }
    public TextPrograssBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        initView();
    }
    public TextPrograssBar(Context context) {
        super(context);
        this.context=context;
        initView();
    }

    private void initView(){
        tvLeft=new TextView(context);
        tvRight=new TextView(context);
        tvCenter=new TextView(context);
        tvLeft.setTextColor(Color.WHITE);
        tvLeft.setTextSize(11);
        tvLeft.setPadding((int) context.getResources().getDimension(R.dimen.dp_5),0,0,0);
        tvRight.setTextColor(Color.WHITE);
        tvRight.setTextSize(11);
        tvRight.setPadding(0,0,(int) context.getResources().getDimension(R.dimen.dp_5),0);
        tvCenter.setTextColor(Color.WHITE);
        tvCenter.setTextSize(11);
        progressBar=new ProgressBar(context,null,android.R.attr.progressBarStyleHorizontal);
        progressBar.setProgressDrawable(context.getResources().getDrawable(R.drawable.special_prograss_bar_bg));
        progressBar.setMax(100);
        LayoutParams prograssP=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        progressBar.setLayoutParams(prograssP);
        LayoutParams tvLeftP=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        tvLeftP.addRule(RelativeLayout.CENTER_VERTICAL);
        tvLeft.setLayoutParams(tvLeftP);
        LayoutParams tvRightP=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        tvRightP.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        tvRightP.addRule(RelativeLayout.CENTER_VERTICAL);
        tvRight.setLayoutParams(tvRightP);
//        tvRight.setGravity(Gravity.CENTER);
        LayoutParams tvCenterP=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        tvCenterP.addRule(RelativeLayout.CENTER_IN_PARENT);
        tvCenter.setLayoutParams(tvCenterP);

//        tvCenter.setGravity(Gravity.CENTER);
        addView(progressBar);
        addView(tvLeft);
        addView(tvRight);
        addView(tvCenter);
        setPrograss(30);
        tvLeft.setText("已抢2222件");
        tvRight.setText("仅剩11124件");
        tvCenter.setText("已抢光");
    }

    public void setCenterText(String text){
        if (tvCenter!=null){
            tvCenter.setText(text);
        }
    }

    public void setPrograss(float prograss) {

        if (progressBar!=null){
            if (prograss>8){
                ValueAnimator animator=ValueAnimator.ofFloat(0,prograss);
                animator.setDuration(500);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float currentValue = (float) animation.getAnimatedValue();
                        progressBar.setProgress((int) currentValue);
                    }
                });
                animator.start();
            }else if (prograss==0){
                progressBar.setProgress(0);
            }else{
                ValueAnimator animator=ValueAnimator.ofFloat(0,8);
                animator.setDuration(500);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float currentValue = (float) animation.getAnimatedValue();
                        progressBar.setProgress((int) currentValue);
                    }
                });
                animator.start();
            }

//            progressBar.invalidate();
        }
    }

    public void setLeftText(String text){
        if (tvLeft!=null){
            tvLeft.setText(text);
        }
    }
    public void setLeftText(int resId){
        if (tvLeft!=null){
            tvLeft.setText(resId);
        }
    }
    public void setRightText(String text){
        if (tvRight!=null){
            tvRight.setText(text);
        }
    }
    public void setRightText(int resId){
        if (tvRight!=null){
            tvRight.setText(resId);
        }
    }

    public void setBgStype(int resource){
        progressBar.setProgressDrawable(context.getResources().getDrawable(resource));
    }
}
