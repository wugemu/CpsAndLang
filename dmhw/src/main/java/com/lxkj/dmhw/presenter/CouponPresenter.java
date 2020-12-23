package com.lxkj.dmhw.presenter;

import com.example.test.andlang.andlangutil.BaseLangActivity;
import com.example.test.andlang.andlangutil.BaseLangFragment;
import com.example.test.andlang.andlangutil.BaseLangPresenter;
import com.example.test.andlang.andlangutil.BaseLangViewModel;
import com.example.test.andlang.andlangutil.LangHttpInterface;
import com.google.gson.reflect.TypeToken;
import com.lxkj.dmhw.bean.self.BasePage;
import com.lxkj.dmhw.bean.self.Coupon;
import com.lxkj.dmhw.logic.Constants;
import com.lxkj.dmhw.model.CouponModel;
import com.lxkj.dmhw.utils.BBCHttpUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CouponPresenter extends BaseLangPresenter<CouponModel> {
    private int pageIndex = 1;
    public boolean haveMore = true;

    public CouponPresenter(BaseLangFragment fragment, BaseLangActivity activity, Class<? extends BaseLangViewModel> modelClass) {
        super(fragment, activity, modelClass);
    }

    @Override
    public void initModel() {

    }

    //优惠券列表
    public void reqCouponList(int status, boolean loadMore){
        if (!loadMore) {
            pageIndex = 1;
            haveMore = true;
        } else {
            haveMore = false;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("status", status);
        params.put("pageNum", pageIndex);
        Type type= new TypeToken<BasePage<Coupon>>(){}.getType();
        BBCHttpUtil.postHttp(activity, Constants.GET_COUPON_LIST, params, type, new LangHttpInterface<BasePage<Coupon>>() {
            @Override
            public void success(BasePage<Coupon> response) {
                if(model.getCouponList()==null){
                    model.setCouponList(new ArrayList<Coupon>());
                }
                if (pageIndex == 1) {
                    model.getCouponList().clear();
                }
                if(response.getList()!=null) {
                    model.getCouponList().addAll(response.getList());
                }
                haveMore = response.isHasNextPage();
                pageIndex++;
                model.notifyData("reqCouponList");
            }

            @Override
            public void empty() {

            }

            @Override
            public void error() {
                model.notifyData("error");
            }

            @Override
            public void fail() {

            }
        });
    }
    //兑换优惠券
    public void reqChangeCoupon(String tattedCode){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("tattedCode", tattedCode);
        BBCHttpUtil.postHttp(activity, Constants.EXCHANGE_COUPON, params, String.class, new LangHttpInterface<String>() {
            @Override
            public void success(String response) {
                model.notifyData("reqChangeCoupon");
            }

            @Override
            public void empty() {

            }

            @Override
            public void error() {
            }

            @Override
            public void fail() {

            }
        });
    }

}
