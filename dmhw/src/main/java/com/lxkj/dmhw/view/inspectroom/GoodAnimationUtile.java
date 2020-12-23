package com.lxkj.dmhw.view.inspectroom;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;

import com.lxkj.dmhw.R;

import java.text.NumberFormat;




public class GoodAnimationUtile {

	/*
	 * 动画逻辑    
	 * 动画时长分为 3.5秒  ,
	 * 
	 * 0-3.5 秒 向上 位移
	 * 
	 * 0- 0.8 秒     放大
	 * 
	 * 1-2  秒         左右 位移 
	 * 
	 * 2-3 秒      左右位移
	 *  
	 * 2.6-3.5秒    渐隐消失
	 *   
	 */

	public static AnimationSet createAnimation(Context context,int speedType) {

		AnimationSet animSet = new AnimationSet(false);
		switch (speedType){
			case 0:
				Animation anim = AnimationUtils.loadAnimation(context, R.anim.live_im_goodmsg_base);
				animSet.addAnimation(anim);
				animSet.addAnimation(getTranceAnim01(context));
				animSet.addAnimation(getTranceAnim02(context));
				animSet.addAnimation(getTranceAnim03(context));
				animSet.addAnimation(getTranceAnim04(context));
				break;
			case 1:
				Animation anim1 = AnimationUtils.loadAnimation(context, R.anim.live_im_goodmsg_base1);
				animSet.addAnimation(anim1);
				animSet.addAnimation(getTranceAnim11(context));
				animSet.addAnimation(getTranceAnim12(context));
				animSet.addAnimation(getTranceAnim13(context));
				animSet.addAnimation(getTranceAnim14(context));
				break;
			case 2:
				Animation anim2 = AnimationUtils.loadAnimation(context, R.anim.live_im_goodmsg_base2);
				animSet.addAnimation(anim2);
				animSet.addAnimation(getTranceAnim21(context));
				animSet.addAnimation(getTranceAnim22(context));
				animSet.addAnimation(getTranceAnim23(context));
				animSet.addAnimation(getTranceAnim24(context));
				break;
		}


		return animSet;

	}

	//第一套动画
	public static Animation getTranceAnim01(Context context) {
		TranslateAnimation inAnim = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF, 0.0f, TranslateAnimation.RELATIVE_TO_SELF, getRandParam(), TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, 0f);
		inAnim.setDuration(100);
		inAnim.setStartOffset(0);
		return inAnim;
	}

	public static Animation getTranceAnim02(Context context) {

		TranslateAnimation inAnim = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, getRandParam(), TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, 0f);
		inAnim.setDuration(500);
		inAnim.setStartOffset(100);
		return inAnim;
	}

	public static Animation getTranceAnim03(Context context) {
		TranslateAnimation inAnim = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, getRandParam(), TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, 0f);
		inAnim.setDuration(1000);
		inAnim.setStartOffset(600);
		return inAnim;
	}

	public static Animation getTranceAnim04(Context context) {
		TranslateAnimation inAnim = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, getRandParam(), TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF,  -getRandParam());
		inAnim.setDuration(900);
		inAnim.setStartOffset(1600);
		return inAnim;
	}

	//第二套动画
	public static Animation getTranceAnim11(Context context) {
		TranslateAnimation inAnim = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF, 0.0f, TranslateAnimation.RELATIVE_TO_SELF, getRandParam(), TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, 0f);
		inAnim.setDuration(0);
		inAnim.setStartOffset(0);
		return inAnim;
	}

	public static Animation getTranceAnim12(Context context) {

		TranslateAnimation inAnim = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, getRandParam(), TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, 0f);
		inAnim.setDuration(500);
		inAnim.setStartOffset(100);
		return inAnim;
	}

	public static Animation getTranceAnim13(Context context) {
		TranslateAnimation inAnim = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, getRandParam(), TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, 0f);
		inAnim.setDuration(500);
		inAnim.setStartOffset(600);
		return inAnim;
	}

	public static Animation getTranceAnim14(Context context) {
		TranslateAnimation inAnim = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, getRandParam(), TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF,  -getRandParam());
		inAnim.setDuration(400);
		inAnim.setStartOffset(1100);
		return inAnim;
	}

	//第三套动画
	public static Animation getTranceAnim21(Context context) {
		TranslateAnimation inAnim = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF, 0.0f, TranslateAnimation.RELATIVE_TO_SELF, getRandParam(), TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, 0f);
		inAnim.setDuration(0);
		inAnim.setStartOffset(0);
		return inAnim;
	}

	public static Animation getTranceAnim22(Context context) {

		TranslateAnimation inAnim = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, getRandParam(), TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, 0f);
		inAnim.setDuration(300);
		inAnim.setStartOffset(100);
		return inAnim;
	}

	public static Animation getTranceAnim23(Context context) {
		TranslateAnimation inAnim = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, getRandParam(), TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, 0f);
		inAnim.setDuration(300);
		inAnim.setStartOffset(400);
		return inAnim;
	}

	public static Animation getTranceAnim24(Context context) {
		TranslateAnimation inAnim = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, getRandParam(), TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF,  -getRandParam());
		inAnim.setDuration(300);
		inAnim.setStartOffset(700);
		return inAnim;
	}
	public static float getRandParm01() {
		NumberFormat n = NumberFormat.getInstance();
		n.setMaximumFractionDigits(1);
		float f = Float.parseFloat(n.format(Math.random()));
		float fre = (float) ((f - 0.5) * 0.5);
		return fre;
	}

	public static float getRandParam() {
		NumberFormat n = NumberFormat.getInstance();
		n.setMaximumFractionDigits(1);
		float f = Float.parseFloat(n.format(Math.random()));
		float fre = (float) (f - 0.4);
		return fre;
	}
}
