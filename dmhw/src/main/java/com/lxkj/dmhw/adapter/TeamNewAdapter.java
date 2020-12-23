package com.lxkj.dmhw.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.MyTeamTotalDetailnfo;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

/**
 * 成员/粉丝列表
 * Created by zyhant on 18-2-1.
 */

public class TeamNewAdapter extends BaseQuickAdapter<MyTeamTotalDetailnfo, BaseViewHolder> {

    private Context context;

    public TeamNewAdapter(Context context) {
        super(R.layout.adapter_team_item);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MyTeamTotalDetailnfo item) {
        try {
            helper.setText(R.id.team_time_text, item.getCreatetime());
            Utils.displayImageCircular(context, item.getUserpicurl(), helper.getView(R.id.team_image));
            helper.setText(R.id.member_name, item.getUsername().trim());
            helper.setText(R.id.team_userphone_text, item.getUserphone());

            switch (item.getUsertype()){
                case "2"://会员
                    helper.setText(R.id.team_mingcheng, "呆萌会员");
                    break;
                case "1"://运营商
                    helper.setText(R.id.team_mingcheng, "运营商");
                    break;
                case "4"://店主
                    helper.setText(R.id.team_mingcheng, "呆萌店主");
                    break;
                case "5"://合伙人
                    helper.setText(R.id.team_mingcheng, "合伙人");
                    break;

            }

            helper.setText(R.id.ciount_txt, item.getAcnt());
            helper.setText(R.id.team_money_text, item.getPreamount()+"元");
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }
}
