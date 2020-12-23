package com.lxkj.dmhw.defined;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lxkj.dmhw.R;

import java.util.Objects;

public abstract class SimpleDialog<T> extends Dialog implements View.OnClickListener {

    protected T data;
    protected Context context;
    /**
     * 初始化
     * @param context 上下文
     * @param resource xml页面
     * @param data 数据
     * @param style true(半透明主题):false(全透明主题)
     * @param key true(关闭返回键):false(打开返回键)
     */
    public SimpleDialog(@NonNull Context context, @LayoutRes int resource, T data, boolean style, boolean key) {
        super(context, style ? R.style.myTransparent : R.style.dialogTransparent);
        this.context = context;
        this.data = data;
        init(resource, key);
    }

    /**
     * 初始化页面
     * @param resource xml页面
     * @param key true(关闭返回键):false(打开返回键)
     */
    private void init(@LayoutRes int resource, boolean key) {
        View view = LinearLayout.inflate(context, resource, null);
        ViewHolder viewHolder = new ViewHolder(view);
        convert(viewHolder);
        if (key) {
            setOnKeyListener(keyListener);
        }
        setCanceledOnTouchOutside(true);
        setContentView(view);
        setCancelable(true);
        // 设置状态栏透明,5.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //设置状态栏
            View decorView = Objects.requireNonNull(getWindow()).getDecorView();
            int option, color;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                color = Color.TRANSPARENT;
                option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                color = Color.parseColor("#50000000");
                option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            }
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(color);
            getWindow().setBackgroundDrawableResource(R.color.dialogTransparent);
            getWindow().setWindowAnimations(R.style.search_animStyle);
        }
    }

    /**
     * 显示弹窗
     */
    public void showDialog() {
        if(context instanceof Activity) {
            try{
                if(!((Activity)context).isFinishing() && !((Activity)context).isDestroyed()) {
                    show();
                }
            }catch (RuntimeException e){

            }
        }
    }

    /**
     * 隐藏弹窗
     */
    public void hideDialog() {
        if(context instanceof Activity) {
            try{
                if(!((Activity)context).isFinishing() && !((Activity)context).isDestroyed()){
                    dismiss();
                }
            }catch (RuntimeException e){

            }
        }
    }


    /**
     * 获取弹窗是否显示
     */
    public boolean isDialog() {
        return isShowing();
    }

    /**
     * 弹出通知
     */
    public void toast(String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }

    /**
     * 跳转页面
     */
    public void startActivity(Intent intent) {
        context.startActivity(intent);
    }

    /**
     * 控件操作事件
     */
    protected abstract void convert(ViewHolder helper);

    /**
     * 监听返回键
     */
    private DialogInterface.OnKeyListener keyListener = (dialog, keyCode, event) -> keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0;

    /**
     * 快速设置
     */
    protected class ViewHolder {

        private View view;

        public ViewHolder(@NonNull View view) {
            this.view = view;
        }

        /**
         * 通过viewId获取控件
         */
        @SuppressWarnings("unchecked")
        public <K extends View> K getView(@IdRes int id) {
            return (K) view.findViewById(id);
        }

        /**
         * 设置TextView值
         */
        public void setText(@IdRes int id, @NonNull String content) {
            TextView textView = getView(id);
            textView.setText(content);
        }

        /**
         * 设置TextView色值
         */
        public void setTextColor(@IdRes int id, @ColorInt int color) {
            TextView textView = getView(id);
            textView.setTextColor(color);
        }

        /**
         * 设置图片
         */
        public void setImageResource(@IdRes int id, @DrawableRes int resId) {
            ImageView imageView = getView(id);
            imageView.setImageResource(resId);
        }

        /**
         * 设置图片
         */
        public void setImageBitmap(@IdRes int id, @NonNull Bitmap bitmap) {
            ImageView imageView = getView(id);
            imageView.setImageBitmap(bitmap);
        }

        /**
         * 设置背景色
         */
        public void setBackgroundColor(@IdRes int id, @ColorInt int color) {
            View view = getView(id);
            view.setBackgroundColor(color);
        }

        /**
         * 设置背景图
         */
        public void setBackgroundResource(@IdRes int id, @DrawableRes int resId) {
            View view = getView(id);
            view.setBackgroundResource(resId);
        }

        /**
         * 设置显示隐藏true(显示):false(隐藏)
         */
        public void setVisible(@IdRes int id, boolean visibility) {
            View view = getView(id);
            view.setVisibility(visibility ? View.VISIBLE : View.GONE);
        }

        /**
         * 设置点击时间
         */
        public void setOnClickListener(@IdRes int id, @Nullable View.OnClickListener listener) {
            View view = getView(id);
            view.setOnClickListener(listener);
        }

    }
}
