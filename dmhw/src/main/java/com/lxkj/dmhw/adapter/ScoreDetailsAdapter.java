package com.lxkj.dmhw.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.ScoreDetails;
import com.orhanobut.logger.Logger;

/**
 * 积分明细
 * Created by zyhant on 18-3-8.
 */

public class ScoreDetailsAdapter extends BaseQuickAdapter<ScoreDetails.ScoreDetailsList, BaseViewHolder> {

    private String type;

    public ScoreDetailsAdapter(String type) {
        super(R.layout.adapter_score_details);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, ScoreDetails.ScoreDetailsList item) {
        try {
            switch (type) {
                case "00":
                    helper.setImageResource(R.id.adapter_score_details_image, R.mipmap.score_one);
                    helper.setText(R.id.adapter_score_details_name, "心得奖励");
                    helper.setText(R.id.adapter_score_details_content, "奖励时间：" + item.getLasttime());
                    break;
                case "01":
                    helper.setImageResource(R.id.adapter_score_details_image, R.mipmap.score_two);
                    helper.setText(R.id.adapter_score_details_name, "点赞奖励");
                    helper.setText(R.id.adapter_score_details_content, "奖励时间：" + item.getLasttime());
                    break;
                case "02":
                    helper.setImageResource(R.id.adapter_score_details_image, R.mipmap.score_three);
                    helper.setText(R.id.adapter_score_details_name, "转发奖励");
                    helper.setText(R.id.adapter_score_details_content, "奖励时间：" + item.getLasttime());
                    break;
                case "03":
                    helper.setImageResource(R.id.adapter_score_details_image, R.mipmap.score_four);
                    helper.setText(R.id.adapter_score_details_name, "签到奖励");
                    helper.setText(R.id.adapter_score_details_content, "奖励时间：" + item.getLasttime());
                    break;
                case "04":
                    helper.setImageResource(R.id.adapter_score_details_image, R.mipmap.score_five);
                    helper.setText(R.id.adapter_score_details_name, "兑换记录");
                    helper.setText(R.id.adapter_score_details_content, "兑换时间：" + item.getLasttime());
                    break;
            }
            if (type.equals("04")) {
                helper.setText(R.id.adapter_score_details_number, item.getScore());
            } else {
                helper.setText(R.id.adapter_score_details_number, "+" + item.getScore());
            }
        } catch (Exception e) {
            Logger.e(e, "积分明细");
        }
    }
}
