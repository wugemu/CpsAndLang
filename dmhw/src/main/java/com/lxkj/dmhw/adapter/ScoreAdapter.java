package com.lxkj.dmhw.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.DMBDetail;
import com.lxkj.dmhw.bean.ScoreOverview;
import com.orhanobut.logger.Logger;

/**
 * 呆萌币明细表
 */

public class ScoreAdapter extends BaseQuickAdapter<DMBDetail, BaseViewHolder> {
     private String Type;
    public ScoreAdapter(String type) {
        super(R.layout.adapter_score);
        this.Type=type;
    }

    @Override
    protected void convert(BaseViewHolder helper,DMBDetail item) {
        try {
            helper.setText(R.id.adapter_score_name, item.getName());
            helper.setText(R.id.adapter_score_time, item.getCreatetime());
            if (Type.equals("dmb")){
                helper.setText(R.id.adapter_score, item.getScore());
            }else{
                helper.setText(R.id.adapter_score, item.getMny());
            }
        } catch (Exception e) {
            Logger.e(e, "NN明细");
        }
    }
}
