package com.lxkj.dmhw.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.self.Coupon;
import com.lxkj.dmhw.myinterface.CouponSelectI;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CouponSelectDialog {
    @BindView(R.id.lv_coupon_dialog)
    ListView lv_coupon_dialog;
    @BindView(R.id.tv_keyong)
    TextView tv_keyong;
    @BindView(R.id.view_keyong)
    View view_keyong;
    @BindView(R.id.tv_bukeyong)
    TextView tv_bukeyong;
    @BindView(R.id.view_bukeyong)
    View view_bukeyong;
    @BindView(R.id.rl_nodata)
    RelativeLayout rl_nodata;
    @BindView(R.id.iv_no_data)
    ImageView iv_no_data;
    @BindView(R.id.tv_no_data)
    TextView tv_no_data;

    private Activity context;
    private Dialog overdialog;
    private CouponAdapter adapter;
    private List<Coupon> couponList=new ArrayList<Coupon>();
    private List<Coupon> canUseList;
    private List<Coupon> noUseList;
    private CouponSelectI couponSelectI;
    private int position=-1;

    public CouponSelectDialog(Activity context, List<Coupon> canUseList, List<Coupon> noUseList, CouponSelectI couponSelectI){
        this.context = context;
        this.canUseList=canUseList;
        this.noUseList=noUseList;
        if(this.canUseList==null){
            this.canUseList=new ArrayList<Coupon>();
        }
        if(this.noUseList==null){
            this.noUseList=new ArrayList<Coupon>();
        }
        this.couponSelectI=couponSelectI;
        initView();
    }

    public void initView(){
        View view = View.inflate(context, R.layout.dialog_coupon_select, null);
        ButterKnife.bind(this, view);
        overdialog = new Dialog(context, R.style.dialog_lhp);
        overdialog.setContentView(view);
        tv_keyong.setText("可用优惠券("+canUseList.size()+")");
        tv_bukeyong.setText("不可用优惠券("+noUseList.size()+")");
        showDialog();
        selectKeyong();
        tv_no_data.setVisibility(View.VISIBLE);
        tv_no_data.setText("您还没有优惠券噢～");
        iv_no_data.setImageResource(R.mipmap.nocoupon);
    }
    public void showDialog() {
        if (overdialog != null) {
            overdialog.show();
            if(adapter!=null){
                adapter.setSelectPostion(position);
            }
            Window win = overdialog.getWindow();
            WindowManager m = context.getWindowManager();
            Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
            WindowManager.LayoutParams p = win.getAttributes(); // 获取对话框当前的参数值
            win.getDecorView().setPadding(0, 0, 0, 0);
            p.height = (int) (d.getHeight() * 0.7);
            p.width = d.getWidth();
            win.setGravity(Gravity.BOTTOM);
            win.setAttributes(p);

        }else {
            if(adapter!=null){
                adapter.setSelectPostion(position);
            }
            overdialog.show();
        }
    }

    public void setData(List<Coupon> couponL,int type){
        couponList.clear();
        couponList.addAll(couponL);
        if(adapter==null){
            adapter=new CouponAdapter(context,couponList,type);
            lv_coupon_dialog.setAdapter(adapter);
        }else {
            adapter.setType(type);
            adapter.notifyDataSetChanged();
        }
        if(couponList.size()>0){
            rl_nodata.setVisibility(View.GONE);
        }else {
            rl_nodata.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.ll_keyong)
    public void selectKeyong(){
        tv_keyong.setTextColor(context.getResources().getColor(R.color.colorBlackText));
        view_keyong.setVisibility(View.VISIBLE);
        view_bukeyong.setVisibility(View.GONE);
        tv_bukeyong.setTextColor(context.getResources().getColor(R.color.colorBlackText2));
        setData(canUseList,3);
    }
    @OnClick(R.id.ll_bukeyong)
    public void selectBukeyong(){
        tv_keyong.setTextColor(context.getResources().getColor(R.color.colorBlackText2));
        view_keyong.setVisibility(View.GONE);
        view_bukeyong.setVisibility(View.VISIBLE);
        tv_bukeyong.setTextColor(context.getResources().getColor(R.color.colorBlackText));
        setData(noUseList,4);
    }

    @OnClick(R.id.iv_coupon_close)
    public void closeDialog(){
        cancelDialog();
    }

    public void cancelDialog() {
        if (overdialog != null) {
            overdialog.dismiss();
        }
    }
    @OnClick(R.id.dialog_ok_btn)
    public void selectCoupon(){
        if(adapter!=null){
            position=adapter.getSelectPostion();
            if(couponSelectI!=null){
                if(position>=0){
                    couponSelectI.selectCoupon(canUseList.get(position));
                }else {
                    couponSelectI.selectCoupon(null);
                }
            }
        }
        cancelDialog();
    }

}
