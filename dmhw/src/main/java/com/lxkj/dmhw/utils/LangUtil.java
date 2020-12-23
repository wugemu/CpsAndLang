package com.lxkj.dmhw.utils;

import android.app.Activity;
import android.content.Intent;

import com.lxkj.dmhw.activity.CommodityActivityPJW;
import com.lxkj.dmhw.activity.LoginActivity;
import com.lxkj.dmhw.data.DateStorage;

public class LangUtil {

    public static void jumpCpsGoodDetail(Activity activity,String cpsType,String goodsId){
        if (!DateStorage.getLoginStatus()){
            activity.startActivity(new Intent(activity, LoginActivity.class));
            return;
        }
        activity.startActivity(new Intent(activity, CommodityActivityPJW.class).putExtra("GoodsId", goodsId).putExtra("type", cpsType));
//        switch (platform){
//            case 1:
//                activity.startActivity(new Intent(activity, CommodityActivityPJW.class).putExtra("GoodsId", goodsId).putExtra("type", "pdd"));
//                break;
//            case 2:
//                activity.startActivity(new Intent(activity, CommodityActivityPJW.class).putExtra("GoodsId", goodsId).putExtra("type", "jd"));
//                break;
//            case 3:
//                activity.startActivity(new Intent(activity, CommodityActivityPJW.class).putExtra("GoodsId", goodsId).putExtra("type", "wph"));
//                break;
//            case 5:
//                activity.startActivity(new Intent(activity, CommodityActivityPJW.class).putExtra("GoodsId", goodsId).putExtra("type", "sn"));
//                break;
//            case 6:
//                activity.startActivity(new Intent(activity, CommodityActivityPJW.class).putExtra("GoodsId", goodsId).putExtra("type", "kl"));
//                break;
//        }
    }
}
