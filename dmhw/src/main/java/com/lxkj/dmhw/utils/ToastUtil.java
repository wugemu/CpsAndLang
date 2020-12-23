package com.lxkj.dmhw.utils;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lxkj.dmhw.R;

public class ToastUtil {
    public static void showImageToast1(Context context, String message,Integer resourceId){
        if(context instanceof Activity) {
            try{
                if(!((Activity)context).isFinishing() && !((Activity)context).isDestroyed()) {
                    View toastview= LayoutInflater.from(context).inflate(R.layout.toast_image_layout,null);
                    TextView text = toastview.findViewById(R.id.toast_message);
                    ImageView image = toastview.findViewById(R.id.toast_image);
                    image.setImageResource(resourceId);
                    text.setText(message);    //要提示的文本
                    Toast toast=new Toast(context);   //上下文
                    toast.setGravity(Gravity.CENTER,0,0);   //位置居中
                    toast.setDuration(Toast.LENGTH_SHORT);  //设置短暂提示
                    toast.setView(toastview);   //把定义好的View布局设置到Toast里面
                    toast.show();
                }
            }catch (RuntimeException e){

            }
        }
    }

    //复制验证码成功
    public static void showImageToast(Context context, String message,Integer resourceId){
        if(context instanceof Activity) {
            try{
                if(!((Activity)context).isFinishing() && !((Activity)context).isDestroyed()) {
                    View toastview= LayoutInflater.from(context).inflate(R.layout.toast_image_layout,null);
                    TextView text = toastview.findViewById(R.id.toast_message);
                    ImageView image = toastview.findViewById(R.id.toast_image);
                    image.setImageResource(resourceId);
                    text.setText(message);    //要提示的文本
                    Toast toast=new Toast(context);   //上下文
                    toast.setGravity(Gravity.CENTER,0,0);   //位置居中
                    toast.setDuration(Toast.LENGTH_SHORT);  //设置短暂提示
                    toast.setView(toastview);   //把定义好的View布局设置到Toast里面
                    toast.show();
                }
            }catch (RuntimeException e){

            }
        }
    }
    //复制微信
    public static void showWXToast(Context context, String message) {
        if (context instanceof Activity) {
            try {
                if (!((Activity) context).isFinishing() && !((Activity) context).isDestroyed()) {
                    View toastview = LayoutInflater.from(context).inflate(R.layout.toast_wx_layout, null);
                    Toast toast = new Toast(context);   //上下文
                    toast.setGravity(Gravity.CENTER, 0, 0);   //位置居中
                    toast.setDuration(Toast.LENGTH_SHORT);  //设置短暂提示
                    toast.setView(toastview);   //把定义好的View布局设置到Toast里面
                    toast.show();
                }
            } catch (RuntimeException e) {

            }
        }
    }
}
