package com.lxkj.dmhw.activity.self.coupon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.test.andlang.andlangutil.BaseLangActivity;
import com.example.test.andlang.andlangutil.BaseLangFragment;
import com.example.test.andlang.util.ToastUtil;
import com.example.test.andlang.widget.editview.CleanableEditText;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.self.Coupon;
import com.lxkj.dmhw.model.CouponModel;
import com.lxkj.dmhw.presenter.CouponPresenter;
import com.lxkj.dmhw.utils.BBCUtil;
import com.lxkj.dmhw.widget.dialog.CouponAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class CouponFragment extends BaseLangFragment<CouponPresenter> {
    @BindView(R.id.et_exchange)
    CleanableEditText etExchange;
    @BindView(R.id.ll_coupon_exchange)
    LinearLayout llCouponExchange;
    @BindView(R.id.lv_coupons)
    ListView lvCoupons;
    @BindView(R.id.rl_nodata)
    RelativeLayout rl_nodata;
    @BindView(R.id.iv_no_data)
    ImageView iv_no_data;
    @BindView(R.id.tv_no_data)
    TextView tv_no_data;

    private CouponAdapter adapter;
    private List<Coupon> couponList;
    private int type;//0=未使用，1=已使用，2=已过期

    @Override
    public int getLayoutId() {
        return R.layout.fragment_coupon;
    }

    @Override
    public void initView() {
        type=getArguments().getInt("type");
        RelativeLayout.LayoutParams p= (RelativeLayout.LayoutParams) rl_nodata.getLayoutParams();
        if (type==0){
            llCouponExchange.setVisibility(View.VISIBLE);
            p.addRule(RelativeLayout.ALIGN_TOP,R.id.lv_coupons);
        }else{
            llCouponExchange.setVisibility(View.GONE);
            p.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        }
        couponList=new ArrayList<>();
        tv_no_data.setVisibility(View.VISIBLE);
        tv_no_data.setText("您还没有优惠券噢～");
        iv_no_data.setImageResource(R.mipmap.nocoupon);
    }

    @Override
    public void initPresenter() {
        presenter=new CouponPresenter(this,(BaseLangActivity) activity, CouponModel.class);
    }

    @Override
    public void initData() {
        refreshData();
    }

    @Override
    public void update(Observable o, Object arg) {
        ((BaseLangActivity)activity).dismissWaitDialog();
        if("reqCouponList".equals(arg)){
            couponList.clear();
            couponList.addAll(presenter.model.getCouponList());
            if(adapter==null){
                adapter=new CouponAdapter(getActivity(),couponList,presenter);
                adapter.setType(type);
                lvCoupons.setAdapter(adapter);
            }else {
                adapter.notifyDataSetChanged();
            }
            if(couponList.size()>0){
                rl_nodata.setVisibility(View.GONE);
            }else {
                rl_nodata.setVisibility(View.VISIBLE);
            }
        }else if("reqChangeCoupon".equals(arg)){
            //兑换成功
            ToastUtil.show(activity,"兑换成功");
            presenter.reqCouponList(type,false);
        }
    }



    @OnClick({R.id.et_exchange, R.id.btn_exchange})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_exchange:
                break;
            case R.id.btn_exchange:
                String tattedCode=etExchange.getText().toString().trim();
                if(BBCUtil.isEmpty(tattedCode)){
                    ToastUtil.show(activity,"请输入兑换码");
                    return;
                }
                presenter.reqChangeCoupon(tattedCode);
                break;
        }
    }

    public void refreshData(){
        if(activity!=null&&presenter!=null) {
            ((BaseLangActivity)activity).showWaitDialog();
            presenter.reqCouponList(type, false);
        }
    }
}