package com.lxkj.dmhw.presenter;

import com.example.test.andlang.andlangutil.BaseLangActivity;
import com.example.test.andlang.andlangutil.BaseLangPresenter;
import com.example.test.andlang.andlangutil.BaseLangViewModel;
import com.example.test.andlang.andlangutil.LangHttpInterface;
import com.google.gson.reflect.TypeToken;
import com.lxkj.dmhw.bean.self.AddrBean;
import com.lxkj.dmhw.bean.self.ProvinceBean;
import com.lxkj.dmhw.bean.self.UserAccount;
import com.lxkj.dmhw.logic.Constants;
import com.lxkj.dmhw.model.SettingModel;
import com.lxkj.dmhw.utils.BBCHttpUtil;
import com.lxkj.dmhw.utils.BBCUtil;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingPresenter extends BaseLangPresenter<SettingModel> {
    public SettingPresenter(BaseLangActivity activity, Class<? extends BaseLangViewModel> modelClass) {
        super(activity, modelClass);
    }

    @Override
    public void initModel() {

    }


    //获取在线地址选择列表
    public void reqAddrOnlineList(){
        HashMap<String, Object> params = new HashMap<String, Object>();
        Type type=new TypeToken<List<ProvinceBean>>(){}.getType();
        BBCHttpUtil.postHttp(activity, Constants.REQ_ONLINEADDR, params, type, new LangHttpInterface<List<ProvinceBean>>() {
            @Override
            public void success(List<ProvinceBean> response) {
                model.setProvinceBeanList(response);
                model.notifyData("reqAddrOnlineList");
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

    //获取地址列表
    public void reqAddrList(String tradeId){
        HashMap<String, Object> params = new HashMap<String, Object>();
        if(!BBCUtil.isEmpty(tradeId)) {
            params.put("tradeId", tradeId);
        }
        Type type=new TypeToken<List<AddrBean>>(){}.getType();
        BBCHttpUtil.postHttp(activity, Constants.REQ_ADDRLIST, params, type, new LangHttpInterface<List<AddrBean>>() {
            @Override
            public void success(List<AddrBean> response) {
                model.setAddrBeanList(response);
                model.notifyData("reqAddrList");
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

    //添加地址
    public void reqAddAddress(String personName,String servNum,String province,String city,String area,String address,int flag){
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("personName",personName);
        param.put("servNum",servNum);
        param.put("province",province);
        param.put("city",city);
        param.put("area",area);
        param.put("address",address);
        param.put("flag",flag);
        BBCHttpUtil.postHttp(activity, Constants.ADD_ADDRESS, param, String.class, new LangHttpInterface<String>() {
            @Override
            public void success(String response) {
                model.notifyData("reqAddAddress");
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

    //修改地址
    public void reqEditAddress(String addrId,String personName,String servNum,String province,String city,String area,String address,int flag){
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id",addrId);
        param.put("personName",personName);
        param.put("servNum",servNum);
        param.put("province",province);
        param.put("city",city);
        param.put("area",area);
        param.put("address",address);
        param.put("flag",flag);
        BBCHttpUtil.postHttp(activity, Constants.EDIT_ADDRESS, param, String.class, new LangHttpInterface<String>() {
            @Override
            public void success(String response) {
                model.notifyData("reqEditAddress");
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

    //删除地址
    public void reqDelAddress(String addrId){
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userAddrId",addrId);
        BBCHttpUtil.postHttp(activity, Constants.DELETE_ADDRESS, param, String.class, new LangHttpInterface<String>() {
            @Override
            public void success(String response) {
                model.notifyData("reqDelAddress");
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

    //修改订单地址
    public void reqUpdateTradeAddress(String tradeId,String adr,String province,String city,String town,String sndTo,String tel){
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("tradeId",tradeId);
        param.put("country","中国");
        param.put("adr",adr);
        param.put("province",province);
        param.put("city",city);
        param.put("town",town);
        param.put("sndTo",sndTo);
        param.put("tel",tel);
        BBCHttpUtil.postHttp(activity, Constants.UPDATE_TRADEADDRESS, param, String.class, new LangHttpInterface<String>() {
            @Override
            public void success(String response) {
                model.notifyData("reqUpdateTradeAddress");
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
