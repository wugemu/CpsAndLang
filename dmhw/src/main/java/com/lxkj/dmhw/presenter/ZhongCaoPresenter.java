package com.lxkj.dmhw.presenter;

import com.example.test.andlang.andlangutil.BaseLangActivity;
import com.example.test.andlang.andlangutil.BaseLangPresenter;
import com.example.test.andlang.andlangutil.LangHttpInterface;
import com.google.gson.reflect.TypeToken;
import com.lxkj.dmhw.bean.IncomeData;
import com.lxkj.dmhw.bean.ZhongCao;
import com.lxkj.dmhw.bean.self.BasePage;
import com.lxkj.dmhw.bean.self.HomeZcBean;
import com.lxkj.dmhw.logic.Constants;
import com.lxkj.dmhw.model.ZhongCaoModel;
import com.lxkj.dmhw.utils.BBCHttpUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZhongCaoPresenter extends BaseLangPresenter<ZhongCaoModel> {
    private int pageIndex = 1;
    public boolean haveMore = true;

    public ZhongCaoPresenter(BaseLangActivity activity, Class modelClass) {
        super(activity, modelClass);
    }

    @Override
    public void initModel() {

    }


    public void reqMaterialBase(){
        BBCHttpUtil.postHttp(activity, Constants.REQ_quertMaterialBase, null, ZhongCao.class, new LangHttpInterface<ZhongCao>() {
            @Override
            public void success(ZhongCao response) {
                model.setZhongCao(response);
                model.notifyData("reqMaterialBase");
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


    public void findMaterialDetailPage(String materialId, boolean loadMore){
        if (!loadMore) {
            pageIndex = 1;
            haveMore = true;
        } else {
            haveMore = false;
        }
        Type type = new TypeToken<BasePage<HomeZcBean>>() {
        }.getType();
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("materialId",materialId);
        param.put("pageNum", pageIndex);
        BBCHttpUtil.postHttp(activity, Constants.REQ_findMaterialDetailPage, param, type, new LangHttpInterface<BasePage<HomeZcBean>>() {
            @Override
            public void success(BasePage<HomeZcBean> response) {
                if(model.getZcList()==null){
                    model.setZcList(new ArrayList<HomeZcBean>());
                }
                if (pageIndex == 1) {
                    model.getZcList().clear();
                }
                if(response.getList()!=null) {
                    model.getZcList().addAll(response.getList());
                }
                haveMore = response.isHasNextPage();
                pageIndex++;
                model.notifyData("findMaterialDetailPage");
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
