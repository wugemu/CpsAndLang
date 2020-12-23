package com.lxkj.dmhw.presenter;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.test.andlang.andlangutil.BaseLangActivity;
import com.example.test.andlang.andlangutil.BaseLangFragment;
import com.example.test.andlang.andlangutil.BaseLangPresenter;
import com.example.test.andlang.andlangutil.BaseLangViewModel;
import com.example.test.andlang.andlangutil.LangHttpInterface;
import com.google.gson.reflect.TypeToken;
import com.lxkj.dmhw.bean.self.Coupon;
import com.lxkj.dmhw.bean.self.GoodSku;
import com.lxkj.dmhw.bean.self.Group;
import com.lxkj.dmhw.bean.self.Info;
import com.lxkj.dmhw.bean.self.Product;
import com.lxkj.dmhw.bean.self.TradeSkuVO;
import com.lxkj.dmhw.logic.Constants;
import com.lxkj.dmhw.model.HomeModel;
import com.lxkj.dmhw.model.ProductModel;
import com.lxkj.dmhw.utils.BBCHttpUtil;
import com.lxkj.dmhw.utils.BBCUtil;
import com.lxkj.dmhw.utils.JsonParseUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductPresenter extends BaseLangPresenter<ProductModel> {
    public ProductPresenter(BaseLangActivity activity, Class<? extends BaseLangViewModel> modelClass) {
        super(activity, modelClass);
    }

    public ProductPresenter(BaseLangFragment fragment, BaseLangActivity activity, Class<? extends BaseLangViewModel> modelClass) {
        super(fragment, activity, modelClass);
    }

    @Override
    public void initModel() {

    }

    public void getProductInfo(String goodsId,long withinbuyId,boolean ifNewUserGoodsAdd) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("goodsId", goodsId);
        params.put("ifNewUserGoodsAdd",ifNewUserGoodsAdd);
        Type type = new TypeToken<Product>() {
        }.getType();
        BBCHttpUtil.getHttp(activity, Constants.GET_PRODUCT_INFO, params, type, new LangHttpInterface<Product>() {
            @Override
            public void success(Product response) {
                model.setProduct(response);
                model.notifyData("getProductInfo");
            }

            @Override
            public void empty() {
                Log.i("sudian", "");
            }

            @Override
            public void error() {
                Log.i("sudian", "");
            }

            @Override
            public void fail() {
                Log.i("sudian", "");
            }
        });

    }

    public void getCollection(String goodsId) {

    }
    public void getLastMaterial(String goodsId){

    }
    public void getCartCount(){
        Type type = new TypeToken<Integer>() {}.getType();
        BBCHttpUtil.getHttp(activity, Constants.GET_CART_COUNT, null, type, new LangHttpInterface<Integer>() {
            @Override
            public void success(Integer response) {
                model.setCartCount(response);
                model.notifyData("getCartCount");
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
    public void reqJoinGroupList(String goodsId, String groupId, int groupType, boolean ifGoodsFind) {
        Type type = new TypeToken<List<Group>>() {
        }.getType();
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("ifGoodsFind", ifGoodsFind);
        param.put("groupType", groupType);
        param.put("groupId", groupId);
        param.put("goodsId", goodsId);
//        BBCHttpUtil.postHttp(activity, Constants.REQ_GROUP_CREATE_LIST, param, type, new LangHttpInterface<List<Group>>() {
//            @Override
//            public void success(List<Group> map) {
//                if (ifGoodsFind) {
//                    model.setJoinGroupList(map);
//                    model.notifyData("reqJoinGroupList");
//                } else {
//                    model.setAllGroupList(map);
//                    model.notifyData("reqAllGroupList");
//                }
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

    public void addToCollection(String goodsId) {

    }

    public void delToCollection(String goodsId) {

    }

    public void addToJingxuan(String goodsId) {
    }

    public void delToJingxuan(String goodsId) {
    }
    public void reqFindNotifyTel(String goodsId) {

    }

    public void getGroupJoinInfo(String goodsId) {

    }
    public void addRealLoveNum(String activityGoodsLoveId) {

    }
    public void reqShareInfo(String goodsId, String shareType) {
        model.notifyData("");
    }

    public void addToCart(GoodSku sku, int productNum, boolean ifNewUserGoodsAdd, String videoId) {
        Map<String, Object> cart = new HashMap<String, Object>();
        Map<String, Object> p = new HashMap<String, Object>();
        p.put("skuId", sku.getSkuId());
        p.put("num", productNum);
        p.put("videoId",videoId);
        p.put("ifNewUserGoodsAdd",ifNewUserGoodsAdd);
        if(BBCUtil.isBigVer121(activity)&&sku.getWithinBuyId()>0) {
            p.put("withinBuyId", sku.getWithinBuyId());
        }
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(p);
        cart.put("tradeSkuVO", JSON.toJSONString(list));
        BBCHttpUtil.postHttp(activity, Constants.BATCH_ADD_CART, cart, String.class, new LangHttpInterface() {
            @Override
            public void success(Object map) {
                model.notifyData("addToCart");
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

    public void reqCheckStock(List<GoodSku> goodSkuList,Map<String,Object> groupParams,String videoId) {
        String tradeSkuVOStr = "";
        List<TradeSkuVO> tradeSkuVOList = getTradeSkuVOList(goodSkuList,videoId);
        if (tradeSkuVOList != null && tradeSkuVOList.size() > 0) {
            tradeSkuVOStr = JsonParseUtil.gson3.toJson(tradeSkuVOList);
        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("tradeSkuVO", tradeSkuVOStr);
        if (groupParams!=null){
            param.putAll(groupParams);
        }

        model.notifyData("reqCheckStock");
//        BBCHttpUtil.postHttp(activity, Constants.REQ_CHECKSTOCK, param, String.class, new LangHttpInterface() {
//            @Override
//            public void success(Object map) {
//                if (groupParams!=null){
//                    model.notifyData("reqGroupCheckStock");
//                }else{
//                    model.notifyData("reqCheckStock");
//                }
//            }
//
//            @Override
//            public void empty() {
//            }
//
//            @Override
//            public void error() {
//            }
//
//            @Override
//            public void fail() {
//            }
//        });
    }

    //库存校验
    public void reqCheckStock(List<GoodSku> goodSkuList,String videoId) {
        reqCheckStock(goodSkuList, null,videoId);
    }

    public List<TradeSkuVO> getTradeSkuVOList(List<GoodSku> goodSkuList,String videoId) {
        List<TradeSkuVO> tradeSkuVOList = new ArrayList<TradeSkuVO>();
        if (goodSkuList != null) {
            for (GoodSku sku : goodSkuList
            ) {
                TradeSkuVO tradeSkuVO = new TradeSkuVO();
                tradeSkuVO.setSkuId(sku.getSkuId() + "");
                tradeSkuVO.setVideoId(videoId);
                tradeSkuVO.setNum(sku.getNum());
                if (sku.getCartId() > 0) {
                    //购物车来的
                    tradeSkuVO.setCartId(sku.getCartId() + "");
                }
                tradeSkuVOList.add(tradeSkuVO);
            }
        }

        return tradeSkuVOList;

    }

    public void reqCanGetCouponList(String goodsId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("goodsId", goodsId);
        Type type = new TypeToken<List<Coupon>>() {
        }.getType();
//        BBCHttpUtil.postHttp(activity, Constants.GET_CAN_GET_COUPON_LIST, params, type, new LangHttpInterface<List<Coupon>>() {
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
    public void reqGetCoupon() {

    }
    public void addNoticePhoneNumber(String goodsId, String mobile) {

    }

    public void reqProductInfo(String goodsId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("goodsId", goodsId);
        Type type = new TypeToken<List<Info>>() {
        }.getType();
//        BBCHttpUtil.postHttp(activity, Constants.REQ_PRODUCT_INFO, params, type, new LangHttpInterface<List<Info>>() {
//            @Override
//            public void success(List<Info> map) {
//                model.setInfos(map);
//                model.notifyData("reqProductInfo");
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
