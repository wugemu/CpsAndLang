package com.lxkj.dmhw.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.self.GoodBean;
import com.lxkj.dmhw.utils.BBCUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 1 on 2016/11/1.
 * 商品信息弹框
 */
public class ProductInfoDialog {
    @BindView(R.id.lv_info_dialog)
    ListView lv_info_dialog;
    @BindView(R.id.tv_info_dialog)
    TextView tv_info_dialog;
    @BindView(R.id.tv_dialog_title)
    TextView tv_dialog_title;

//    @BindView(R.id.ll_tax_info)
//    LinearLayout llTaxInfo;
//    @BindView(R.id.tv_tax_info)
//    TextView tvTaxInfo;
//    @BindView(R.id.tv_postag)
//    TextView tvPostag;
//    @BindView(R.id.ll_un_coupon)
//    LinearLayout llUnCoupon;
//    @BindView(R.id.tv_un_coupon)
//    TextView tvUnCoupon;
//    @BindView(R.id.tv_shouhou_label)
//    TextView tvShouhouLabel;
//    @BindView(R.id.tv_shouhou)
//    TextView tvShouhou;

    private Activity context;
    private Dialog overdialog;
    private GoodBean goodBean;
    private GoodInfoAdapter adapter;
    private String tipStr;
    public ProductInfoDialog(Activity context, GoodBean goodBean) {
        this.context = context;
        this.goodBean = goodBean;
        initView();

    }
    public ProductInfoDialog(Activity context, String tipStr) {
        this.context = context;
        this.tipStr=tipStr;
        initView();

    }

    private void initView() {
        View view = View.inflate(context, R.layout.dialog_product_info, null);
        ButterKnife.bind(this, view);
        overdialog = new Dialog(context, R.style.dialog_lhp);
        overdialog.setContentView(view);
        if(!BBCUtil.isEmpty(tipStr)){
            //邮费说明
            tv_info_dialog.setVisibility(View.VISIBLE);
            lv_info_dialog.setVisibility(View.GONE);
            tv_info_dialog.setText(tipStr);
        }else if(goodBean.getInfo()!=null){
            //商品说明
            tv_info_dialog.setVisibility(View.GONE);
            lv_info_dialog.setVisibility(View.VISIBLE);
            adapter=new GoodInfoAdapter(context,goodBean.getInfo());
            lv_info_dialog.setAdapter(adapter);
        }
//        tvShouhouLabel.setText("售后无忧");
//        tvShouhou.setText("售后无忧：本商品提供优质售后服务，请放心购物");
//        llTaxInfo.setVisibility(View.VISIBLE);
//        if (goodBean.getInfo()!=null&&goodBean.getInfo().size()>0){
//            if (goodBean.getTaxs() == 1||goodBean.getTaxs() == 2) {//有税费
//                tvTaxInfo.setText(goodBean.getInfo().get(0).getDes());
//            } else {//国内发货
//                tvTaxInfo.setVisibility(View.GONE);
//                llTaxInfo.setVisibility(View.GONE);
//                tvShouhouLabel.setText("七天无理由");
//                tvShouhou.setText("七天无理由：本商品本商品支持七天无理由退换货");
//            }
//            tvPostag.setText(goodBean.getInfo().get(1).getDes());//运费
//        }
//        if (!goodBean.isIfCanUseCoupon()) {//是否可以用券
//            llUnCoupon.setVisibility(View.VISIBLE);
//        }else{
//            llUnCoupon.setVisibility(View.GONE);
//        }


        showDialog();
    }

    public void showDialog() {
        if (overdialog != null) {
            overdialog.show();
            Window win = overdialog.getWindow();
            win.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = win.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            win.setGravity(Gravity.BOTTOM);
            win.setAttributes(lp);
        } else {
            overdialog.show();
        }
    }


    public void setTitle(String title){
        if(tv_dialog_title!=null){
            tv_dialog_title.setText(title);
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
