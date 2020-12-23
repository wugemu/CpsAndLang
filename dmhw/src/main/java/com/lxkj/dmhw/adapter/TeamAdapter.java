package com.lxkj.dmhw.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.AgentList;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

/**
 * 成员/粉丝列表
 * Created by zyhant on 18-2-1.
 */

public class TeamAdapter extends BaseQuickAdapter<AgentList.AgentData, BaseViewHolder> {

    private Context context;

    public TeamAdapter(Context context) {
        super(R.layout.adapter_team);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, AgentList.AgentData item) {
        try {
            if (helper.getLayoutPosition()%2 == 0) {
                helper.setBackgroundColor(R.id.adapter_team_layout, 0xfff0f0f0);
            } else {
                helper.setBackgroundColor(R.id.adapter_team_layout, 0xffffffff);
            }
            helper.setText(R.id.adapter_team_time, item.getJointime());
            Utils.displayImageCircular(context, item.getUserpicurl(), helper.getView(R.id.adapter_team_avatar));
            helper.setText(R.id.adapter_team_name, item.getUsername());
            helper.setText(R.id.adapter_team_estimate, item.getThismonthcontributor());
            helper.setText(R.id.adapter_team_contribution, item.getAcccontributor());
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }
}
