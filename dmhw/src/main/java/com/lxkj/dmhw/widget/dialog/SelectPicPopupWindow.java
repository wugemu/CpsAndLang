package com.lxkj.dmhw.widget.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;


import com.lxkj.dmhw.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lang on 17-2-27.
 */
public class SelectPicPopupWindow extends PopupWindow {
    private View mMenuView;
    private Handler handler;
    private int takePhoto;//照相
    private int selectPicture;//相册
    public SelectPicPopupWindow(Activity context, Handler handler, int takePhoto, int selectPicture) {
        super(context);
        this.handler=handler;
        this.takePhoto=takePhoto;
        this.selectPicture=selectPicture;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.dialog_select_takephoto_picture, null);

        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LinearLayout.LayoutParams.FILL_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.AnimBottom);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y=(int) event.getY();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }
                return true;
            }
        });
        ButterKnife.bind(this, mMenuView);

    }

    @OnClick(R.id.btn_open_camera)
    public void takePhoto(){
        handler.sendEmptyMessage(takePhoto);
        dismiss();
    }
    @OnClick(R.id.btn_choose_img)
    public void showPicture(){
        handler.sendEmptyMessage(selectPicture);
        dismiss();
    }
    @OnClick(R.id.btn_cancel)
    public void cancel(){
        dismiss();
    }
}