package com.lxkj.dmhw.presenter;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.example.test.andlang.andlangutil.BaseLangActivity;
import com.example.test.andlang.andlangutil.BaseLangFragment;
import com.example.test.andlang.andlangutil.BaseLangPresenter;
import com.example.test.andlang.andlangutil.BaseLangViewModel;
import com.example.test.andlang.andlangutil.LangHttpInterface;
import com.google.gson.reflect.TypeToken;
import com.lxkj.dmhw.bean.self.ActivityGoods;
import com.lxkj.dmhw.bean.self.BasePage;
import com.lxkj.dmhw.bean.self.GoodBean;
import com.lxkj.dmhw.bean.self.HomeBean;
import com.lxkj.dmhw.bean.self.HomeLimitBean;
import com.lxkj.dmhw.bean.self.HomeZcBean;
import com.lxkj.dmhw.logic.Constants;
import com.lxkj.dmhw.model.HomeModel;
import com.lxkj.dmhw.utils.BBCHttpUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomePresenter extends BaseLangPresenter<HomeModel> {
    private int pageIndex = 1;
    public boolean haveMore = true;

    public HomePresenter(BaseLangActivity activity, Class<? extends BaseLangViewModel> modelClass) {
        super(activity, modelClass);
    }

    public HomePresenter(BaseLangFragment fragment, BaseLangActivity activity, Class<? extends BaseLangViewModel> modelClass) {
        super(fragment, activity, modelClass);
    }

    @Override
    public void initModel() {

    }

    public void reqHomeIndexOne(Activity activity, Handler handler){
        Map<String, Object> params = new HashMap<String, Object>();
        BBCHttpUtil.postHttp(activity, Constants.HOME_INDEX_ONE, params, HomeBean.class, new LangHttpInterface<HomeBean>() {
            @Override
            public void success(HomeBean response) {
                model.setHomeBean(response);
                if(handler!=null){
                    Message message=new Message();
                    message.obj="reqHomeIndexOne";
                    handler.sendMessage(message);
                }
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

    public void reqHomeIndexTwo(Activity activity, Handler handler){
        Map<String, Object> params = new HashMap<String, Object>();
        Type type= new TypeToken<List<HomeZcBean>>(){}.getType();
        BBCHttpUtil.postHttp(activity, Constants.HOME_INDEX_TWO, params, type, new LangHttpInterface<List<HomeZcBean>>() {
            @Override
            public void success(List<HomeZcBean> response) {
                model.setHomeZcBeanList(response);
                if(handler!=null){
                    Message message=new Message();
                    message.obj="reqHomeIndexTwo";
                    handler.sendMessage(message);
                }
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

    public void reqHomeIndexThree(Activity activity, Handler handler){
        Map<String, Object> params = new HashMap<String, Object>();
        BBCHttpUtil.postHttp(activity, Constants.HOME_INDEX_THREE, params, HomeLimitBean.class, new LangHttpInterface<HomeLimitBean>() {
            @Override
            public void success(HomeLimitBean response) {
                model.setHomeLimitBean(response);
                if(handler!=null){
                    Message message=new Message();
                    message.obj="reqHomeIndexThree";
                    handler.sendMessage(message);
                }
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

    //热门搜索接口
    public void reqHotGoods(Activity activity, Handler handler,boolean loadMore){
        if (!loadMore) {
            pageIndex = 1;
            haveMore = true;
        } else {
            haveMore = false;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("pageNum", pageIndex);
        final int thePageIndex=pageIndex;
        Type type= new TypeToken<BasePage<GoodBean>>(){}.getType();
        BBCHttpUtil.getHttp(activity, Constants.REQ_HOT_GOODS, params, type, new LangHttpInterface<BasePage<GoodBean>>() {
            @Override
            public void success(BasePage<GoodBean> response) {
                if(model.getGoodBeanList()==null){
                    model.setGoodBeanList(new ArrayList<GoodBean>());
                }
                if (thePageIndex == 1) {
                    model.getGoodBeanList().clear();
                    pageIndex=1;
                }
                if(response.getList()!=null) {
                    model.getGoodBeanList().addAll(response.getList());
                    model.setAddHotGoodList(response.getList());
                }
                haveMore = response.isHasNextPage();
                pageIndex++;
                if(handler!=null){
                    Message message=new Message();
                    message.obj="reqHotGoods";
                    handler.sendMessage(message);
                }
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
    //查询秒杀场次商品
    public void reqActivityGoods(Activity activity, Handler handler,String activityId){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("activityId",activityId);
        BBCHttpUtil.getHttp(activity, Constants.REQ_ACTIVITYGOODS, params, HomeLimitBean.class, new LangHttpInterface<HomeLimitBean>() {
            @Override
            public void success(HomeLimitBean response) {
                model.setNowActivityGoods(new ActivityGoods());
                model.setNowActivityGoods(response.getNowActivityGoods());
                if(handler!=null){
                    Message message=new Message();
                    message.obj="reqActivityGoods";
                    handler.sendMessage(message);
                }
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
