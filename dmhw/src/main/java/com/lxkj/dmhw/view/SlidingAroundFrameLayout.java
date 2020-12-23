package com.lxkj.dmhw.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

public class SlidingAroundFrameLayout extends FrameLayout {
    private boolean isLeftOut = false;
    private boolean isRightOut = false;

    public enum TypeEnum {
        LEFT,
        RIGHT,
    }

    public SlidingAroundFrameLayout(Context context) {
        super(context, null);
    }

    public SlidingAroundFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public SlidingAroundFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTranslateAnimation(TypeEnum type) {
        TranslateAnimation ta;
        if (type == TypeEnum.LEFT) {
            if (isLeftOut){
                return;
            }
            isLeftOut = true;
            isRightOut = false;
            this.setClickable(true);
            ta = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0,
                    Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
            ta.setDuration(500);
            ta.setFillAfter(true);
            this.startAnimation(ta);
        } else if (type == TypeEnum.RIGHT) {
            if (isRightOut){
                return;
            }
            isLeftOut = false;
            isRightOut = true;
            ta = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1f,
                    Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
            ta.setDuration(500);
            ta.setFillAfter(true);
            this.startAnimation(ta);
            this.setClickable(false);
        } else {
            this.setVisibility(VISIBLE);
        }
    }

}
