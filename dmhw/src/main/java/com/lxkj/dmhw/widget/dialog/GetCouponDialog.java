package com.lxkj.dmhw.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.lxkj.dmhw.R;
import com.lxkj.dmhw.adapter.self.CouponGetAdapter;
import com.lxkj.dmhw.bean.self.Coupon;
import com.lxkj.dmhw.utils.BBCUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 1 on 2016/11/1.
 * 领取优惠券弹框
 */
public class GetCouponDialog {

    @BindView(R.id.lv_coupons)
    ListView lvCoupons;
    @BindView(R.id.ll_nodata)
    LinearLayout ll_nodata;

    private Activity context;
    private Dialog overdialog;
    private Handler handler;
    List<Coupon> coupons;
    private CouponGetAdapter adapter;
    public GetCouponDialog(Activity context,List<Coupon> coupons, Handler handler) {
        this.context = context;
        this.handler = handler;
        this.coupons=coupons;
        initView();

    }

    private void initView() {
        View view = View.inflate(context, R.layout.dialog_get_coupon, null);
        ButterKnife.bind(this, view);
        overdialog = new Dialog(context, R.style.dialog_lhp);
        overdialog.setContentView(view);
        lvCoupons.getLayoutParams().height= (int) (BBCUtil.getDisplayHeight(context)*0.5);
        adapter=new CouponGetAdapter(context,coupons,handler);
        lvCoupons.setAdapter(adapter);

        if(coupons!=null&&coupons.size()>0){
            lvCoupons.setVisibility(View.VISIBLE);
            ll_nodata.setVisibility(View.GONE);
        }else {
            lvCoupons.setVisibility(View.GONE);
            ll_nodata.setVisibility(View.VISIBLE);
        }

        showDialog();
    }

    public void refershData(String couponId){
       adapter.refershData(couponId);
    }

    public void showDialog() {
        if (overdialog != null) {
            overdialog.show();
            Window win = overdialog.getWindow();
            WindowManager m = context.getWindowManager();
            Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
            WindowManager.LayoutParams p = win.getAttributes(); // 获取对话框当前的参数值
            win.getDecorView().setPadding(0, 0, 0, 0);
            p.height = ViewGroup.LayoutParams.WRAP_CONTENT; // 高度设置为包裹内容
            p.width = ViewGroup.LayoutParams.MATCH_PARENT; // 宽度设置为屏幕的0.85
            win.setGravity(Gravity.BOTTOM);
            win.setAttributes(p);

        } else {
            overdialog.show();
        }
    }

    @OnClick(R.id.iv_close)
    public void cancel() {
        cancelDialog();

    }

    public void cancelDialog() {
        if (overdialog != null) {
            overdialog.dismiss();
        }
    }

}
