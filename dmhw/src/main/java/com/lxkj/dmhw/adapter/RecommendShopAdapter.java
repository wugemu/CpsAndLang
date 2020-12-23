package com.lxkj.dmhw.adapter;

import android.app.Activity;
import android.content.Intent;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.CommodityActivity290;
import com.lxkj.dmhw.activity.ImageActivity;
import com.lxkj.dmhw.bean.Recommend;
import com.lxkj.dmhw.bean.ShareInfo;
import com.lxkj.dmhw.utils.Utils;

import java.util.ArrayList;

/**
 * 每日推荐图片适配器
 * Created by Android on 2018/9/21.
 */

public class RecommendShopAdapter extends BaseQuickAdapter<Recommend.RecommendList.RecommendData, BaseViewHolder> {

    private Activity activity;
    private String type;
    private ShareInfo info;
    public RecommendShopAdapter(Activity activity, ShareInfo info, String type) {
        super(R.layout.adapter_shop_recommend);
        this.activity = activity;
        this.type = type;
        this.info = info;
    }

    @Override
    protected void convert(BaseViewHolder helper, Recommend.RecommendList.RecommendData item) {
        switch (type) {
            case "0":// 单商品
                helper.setGone(R.id.adapter_shop_recommend_money_layout, false);
                helper.setGone(R.id.adapter_new_one_fragment_hasnogoods, false);
                Utils.displayImageRoundedAll(activity, item.getImage(), helper.getView(R.id.adapter_shop_recommend_image),10);
                break;
            case "1":// 多商品
                helper.setGone(R.id.adapter_shop_recommend_money_layout, true);
                switch (item.getStatus()) {
                    case "0":
                        helper.setGone(R.id.adapter_new_one_fragment_hasnogoods, false);
                        break;
                    case "1":
                        helper.setGone(R.id.adapter_new_one_fragment_hasnogoods, true);
                        break;
                }


                Utils.displayImageRoundedAll(activity, item.getImage(), helper.getView(R.id.adapter_shop_recommend_image),10);
                helper.setText(R.id.adapter_shop_recommend_money_text, activity.getString(R.string.money) + item.getMoney());
                break;
        }
        helper.getView(R.id.adapter_shop_recommend_layout).setOnClickListener(v -> {
            Intent intent = null;
            switch (type) {
                case "0":
                    ArrayList<String> arrayList = new ArrayList<>();
                    for (int i = 0; i < getData().size(); i++) {
                        arrayList.add(getData().get(i).getImage());
                    }
                    intent = new Intent(activity, ImageActivity.class);
                    intent.putStringArrayListExtra("imageList", arrayList);
                    intent.putExtra("info", info);
                    intent.putExtra("position", helper.getLayoutPosition());
                    intent.putExtra("isCheck", true);
                    break;
                case "1":
                    switch (item.getStatus()) {
                        case "0":
                            intent = new Intent(activity, CommodityActivity290.class);
                            intent.putExtra("shopId", item.getShopid());
                            intent.putExtra("source", "dmj");
                            intent.putExtra("sourceId", "");
                            break;
                        case "1":
                            ArrayList<String> list = new ArrayList<>();
                            list.add(item.getImage());
                            intent = new Intent(activity, ImageActivity.class);
                            intent.putStringArrayListExtra("imageList", list);
                            intent.putExtra("position", 0);
                            break;
                    }
                    break;
            }
            if (intent != null) {
                activity.startActivity(intent);
            }
        });
    }

}
