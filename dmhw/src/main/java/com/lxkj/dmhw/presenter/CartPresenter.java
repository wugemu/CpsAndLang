package com.lxkj.dmhw.presenter;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.example.test.andlang.andlangutil.BaseLangActivity;
import com.example.test.andlang.andlangutil.BaseLangFragment;
import com.example.test.andlang.andlangutil.BaseLangPresenter;
import com.example.test.andlang.andlangutil.BaseLangViewModel;
import com.example.test.andlang.andlangutil.LangHttpInterface;
import com.google.gson.reflect.TypeToken;
import com.lxkj.dmhw.bean.self.BasePage;
import com.lxkj.dmhw.bean.self.CartResult;
import com.lxkj.dmhw.bean.self.CartResultNewBean;
import com.lxkj.dmhw.bean.self.Coupon;
import com.lxkj.dmhw.bean.self.GoodSku;
import com.lxkj.dmhw.bean.self.TopNotifyBean;
import com.lxkj.dmhw.bean.self.TradeGoodsCar;
import com.lxkj.dmhw.bean.self.TradeSkuVO;
import com.lxkj.dmhw.logic.Constants;
import com.lxkj.dmhw.model.CartModel;
import com.lxkj.dmhw.utils.BBCHttpUtil;
import com.lxkj.dmhw.utils.BBCUtil;
import com.lxkj.dmhw.utils.JsonParseUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartPresenter extends BaseLangPresenter<CartModel> {
    public int page=1;
    public boolean haveMore = true;
    public CartPresenter(BaseLangActivity activity, Class<? extends BaseLangViewModel> modelClass) {
        super(activity, modelClass);
    }

    public CartPresenter(BaseLangFragment fragment, BaseLangActivity activity, Class<? extends BaseLangViewModel> modelClass) {
        super(fragment, activity, modelClass);
    }

    @Override
    public void initModel() {

    }

    public void reqCartList(Activity activity, Handler handler,final String cancelPickOnShopCarIds, final String pickOnShopCarIds) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("cancelPickOnShopCarIds",cancelPickOnShopCarIds);
        params.put("pickOnShopCarIds",pickOnShopCarIds);
        BBCHttpUtil.getHttp(activity, Constants.GET_CART_LIST_NEW, params, CartResultNewBean.class, new LangHttpInterface<CartResultNewBean>() {
            @Override
            public void success(CartResultNewBean result) {
                if(BBCUtil.isEmpty(cancelPickOnShopCarIds)&&BBCUtil.isEmpty(pickOnShopCarIds)){
                    model.setIfUseInterface(true);//不是人为点击选择操作 使用接口返回的领券状态
                }else {
                    model.setIfUseInterface(false);
                }
                List<CartResult> cartList=result.getShopDtoList();//仓库列表
                model.setCartResultNewBean(result);
                if (cartList!=null&&cartList.size()>0){
                    model.setCartList(cartList);
                    if(handler!=null){
                        Message message=new Message();
                        message.obj="reqCartList";
                        handler.sendMessage(message);
                    }
                }else{
                    model.setCartList(cartList);
                    if(handler!=null){
                        Message message=new Message();
                        message.obj="reqCartListEmpty";
                        handler.sendMessage(message);
                    }
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

    public void reqInvalidCartList(Activity activity, Handler handler,boolean loadMore) {
        if (!loadMore) {
            page = 1;
            haveMore = true;
        } else {
            haveMore = false;
        }
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("page", page);
        Type type = new TypeToken<BasePage<TradeGoodsCar>>() {
        }.getType();
        BBCHttpUtil.getHttp(activity, Constants.GET_INVALID_CART_LIST, params, type, new LangHttpInterface<BasePage<TradeGoodsCar>>() {
            @Override
            public void success(BasePage<TradeGoodsCar> response) {
                if (response.getList() != null) {
                    model.setInvalidList(response.getList());
                }
                model.setInvalidCount(response.getTotal());
                haveMore = response.isHasNextPage();
                page++;
                if(handler!=null){
                    Message message=new Message();
                    message.obj="reqInvalidCartList";
                    handler.sendMessage(message);
                }
            }

            @Override
            public void empty() {

            }

            @Override
            public void error() {
                if(handler!=null){
                    Message message=new Message();
                    message.obj="error";
                    handler.sendMessage(message);
                }
            }

            @Override
            public void fail() {

            }
        });
    }

    public void reqCartLogisticsInfo(Activity activity, Handler handler){
//        BBCHttpUtil.getHttp(activity, Constants.REQ_PARCODE_CAR, null, TopNotifyBean.class, new LangHttpInterface<TopNotifyBean>() {
//            @Override
//            public void success(TopNotifyBean response) {
//                model.setInfo(response);
//                model.notifyData("reqCartLogisticsInfo");
//            }
//
//            @Override
//            public void empty() {
//
//            }
//
//            @Override
//            public void error() {
//                model.notifyData("reqCartLogisticsInfoError");
//            }
//
//            @Override
//            public void fail() {
//                model.notifyData("reqCartLogisticsInfoError");
//            }
//        });

        if(handler!=null){
            Message message=new Message();
            message.obj="reqCartLogisticsInfo";
            handler.sendMessage(message);
        }

    }

    public void reqDeleteCart(Activity activity, Handler handler,String cartIds){
        Map<String,Object> params=new HashMap<>();
        params.put("cartIds",cartIds);
        BBCHttpUtil.postHttp(activity, Constants.DELETE_CART_BY_ID, params, String.class, new LangHttpInterface() {
            @Override
            public void success(Object map) {
                if(handler!=null){
                    Message message=new Message();
                    message.obj="reqDeleteCart";
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


    public void reqChangeCart(Activity activity, Handler handler){
        Map<String, Object> cart = new HashMap<String, Object>();
        Map<String, Long> p = new HashMap<String, Long>();
        p.put("skuId",model.getCar().getSkuId());
        p.put("num",Long.valueOf(model.getChangeNum()));
        if(BBCUtil.isBigVer121(activity)&&model.getCar().getWithinBuyId()>0) {
            p.put("withinBuyId", model.getCar().getWithinBuyId());
        }
        List<Map<String,Long>> list=new ArrayList<>();
        list.add(p);
        cart.put("addType", 1);//addType 只有购物车加减商品时 需要传此参数
        cart.put("tradeSkuVO", JSON.toJSONString(list));
        BBCHttpUtil.postHttp(activity, Constants.BATCH_ADD_CART, cart, String.class, new LangHttpInterface() {
            @Override
            public void success(Object map) {
                if(handler!=null){
                    Message message=new Message();
                    message.obj="reqChangeCart";
                    handler.sendMessage(message);
                }
            }

            @Override
            public void empty() {
//                model.notifyData("addToCart");
            }

            @Override
            public void error() {
//                model.notifyData("addToCart");
            }

            @Override
            public void fail() {
//                model.notifyData("addToCart");
            }
        });

    }

    public void reqClearInvalidList(Activity activity, Handler handler){
        BBCHttpUtil.postHttp(activity, Constants.CLEAR_INVALID_CART_LIST, null, String.class, new LangHttpInterface() {
            @Override
            public void success(Object map) {
                if(handler!=null){
                    Message message=new Message();
                    message.obj="reqClearInvalidList";
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

    //库存校验
    public void reqCheckStock(Activity activity,Handler handler,List<GoodSku> goodSkuList){
        String tradeSkuVOStr="";
        List<TradeSkuVO> tradeSkuVOList=getTradeSkuVOList(goodSkuList);
        if(tradeSkuVOList!=null&&tradeSkuVOList.size()>0){
            tradeSkuVOStr= JsonParseUtil.gson3.toJson(tradeSkuVOList);
        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("tradeSkuVO",tradeSkuVOStr);

        if(handler!=null){
            Message message=new Message();
            message.obj="reqCheckStock";
            handler.sendMessage(message);
        }

//        BBCHttpUtil.postHttp(activity, Constants.REQ_CHECKSTOCK, param, String.class, new LangHttpInterface() {
//            @Override
//            public void success(Object map) {
//                model.notifyData("reqCheckStock");
//            }
//
//            @Override
//            public void empty() {
//
//            }
//
//            @Override
//            public void error() {
//
//            }
//
//            @Override
//            public void fail() {
//
//            }
//        });
    }


    public List<TradeSkuVO> getTradeSkuVOList(List<GoodSku> goodSkuList){
        List<TradeSkuVO> tradeSkuVOList=new ArrayList<TradeSkuVO>();
        if(goodSkuList!=null){
            for (GoodSku sku:goodSkuList
            ) {
                TradeSkuVO tradeSkuVO=new TradeSkuVO();
                tradeSkuVO.setSkuId(sku.getSkuId()+"");
                tradeSkuVO.setNum(sku.getNum());
                if(sku.getCartId()>0){
                    //购物车来的
                    tradeSkuVO.setCartId(sku.getCartId()+"");
                }
                if(sku.getWithinBuyId()>0){
                    tradeSkuVO.setWithinBuyId(sku.getWithinBuyId());
                }
                tradeSkuVOList.add(tradeSkuVO);
            }
        }

        return tradeSkuVOList;

    }

    public void reqCanGetCouponList(String goodsId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("goodsIdsStr", goodsId);
        Type type = new TypeToken<List<Coupon>>() {
        }.getType();
//        BBCHttpUtil.postHttp(activity, Constants.GET_CART_GET_COUPON_LIST, params, type, new LangHttpInterface<List<Coupon>>() {
//            @Override
//            public void success(List<Coupon> response) {
//                model.setCouponList(response);
//                model.notifyData("reqCanGetCouponList");
//            }
//
//            @Override
//            public void empty() {
//
//            }
//
//            @Override
//            public void error() {
//
//            }
//
//            @Override
//            public void fail() {
//
//            }
//        });
    }

    public void reqGetCoupon(String couponId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("couponId", couponId);
//        BBCHttpUtil.postHttp(activity, Constants.GET_COUPON, params, String.class, new LangHttpInterface() {
//            @Override
//            public void success(Object response) {
//                model.notifyData("reqGetCoupon");
//            }
//
//            @Override
//            public void empty() {
//
//            }
//
//            @Override
//            public void error() {
//
//            }
//
//            @Override
//            public void fail() {
//
//            }
//        });
    }
}
