package com.lxkj.dmhw.presenter;

import com.example.test.andlang.andlangutil.BaseLangActivity;
import com.example.test.andlang.andlangutil.BaseLangPresenter;
import com.example.test.andlang.andlangutil.LangHttpInterface;
import com.google.gson.reflect.TypeToken;
import com.lxkj.dmhw.bean.IncomeData;
import com.lxkj.dmhw.bean.self.BasePage;
import com.lxkj.dmhw.bean.self.Coupon;
import com.lxkj.dmhw.bean.self.CouponSettleBean;
import com.lxkj.dmhw.bean.self.TradeGoodsCar;
import com.lxkj.dmhw.logic.Constants;
import com.lxkj.dmhw.model.CashModel;
import com.lxkj.dmhw.utils.BBCHttpUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CashPresenter extends BaseLangPresenter<CashModel> {
    private int pageIndex = 1;
    public boolean haveMore = true;
    public CashPresenter(BaseLangActivity activity, Class modelClass) {
        super(activity, modelClass);
    }

    @Override
    public void initModel() {

    }

    public void reqIncomeData(int selectType){
        Type type = new TypeToken<List<IncomeData>>() {
        }.getType();
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("selectType",selectType);
        BBCHttpUtil.postHttp(activity, Constants.INCOME_DATA, param, type, new LangHttpInterface<List<IncomeData>>() {
            @Override
            public void success(List<IncomeData> response) {
                model.setIncomeDataList(response);
                model.notifyData("reqIncomeData");
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


    public void reqIncomeDataDetialList(int selectType,int preType, boolean loadMore){
        if (!loadMore) {
            pageIndex = 1;
            haveMore = true;
        } else {
            haveMore = false;
        }

        Type type = new TypeToken<BasePage<IncomeData>>() {
        }.getType();
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("selectType",selectType);
        param.put("preType",preType);
        param.put("pageNum", pageIndex);
        BBCHttpUtil.postHttp(activity, Constants.INCOME_DATA_DETIAL_LIST, param, type, new LangHttpInterface<BasePage<IncomeData>>() {
            @Override
            public void success(BasePage<IncomeData> response) {
                if(model.getIncomeDataDetialList()==null){
                    model.setIncomeDataDetialList(new ArrayList<IncomeData>());
                }
                if (pageIndex == 1) {
                    model.getIncomeDataDetialList().clear();
                }
                if(response.getList()!=null) {
                    model.getIncomeDataDetialList().addAll(response.getList());
                }
                haveMore = response.isHasNextPage();
                pageIndex++;
                model.notifyData("reqIncomeDataDetialList");
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
