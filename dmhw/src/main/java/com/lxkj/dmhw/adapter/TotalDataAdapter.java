package com.lxkj.dmhw.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.bean.MyTeamTotalInfo;
import com.lxkj.dmhw.view.RoundProgressBar;
import com.orhanobut.logger.Logger;

/**
 * 推广订单
 * Created by lintenghui on 19-03-06
 */

public class TotalDataAdapter extends BaseQuickAdapter<MyTeamTotalInfo, BaseViewHolder> {

  Context context;
  String mType;
    public TotalDataAdapter(Context context,String type) {
        super(R.layout.activity_item_my_team);
        this.context = context;
        this.mType=type;
    }

    @Override
    protected void convert(BaseViewHolder helper, MyTeamTotalInfo item) {
        try {
            helper.setText(R.id.title_txt,item.getTitle());
            helper.setText(R.id.count_txt,item.getTeam());
            helper.setText(R.id.fenrun_txt,item.getSubtitle());
            helper.setText(R.id.comm_txt,item.getComm());
            if (helper.getLayoutPosition()%2==0){
                helper.setGone(R.id.item_split,true);
            }else{
                helper.setGone(R.id.item_split,false);
            }

            if (mType.equals("0")){//推广奖励
                RoundProgressBar progressBar=helper.getView(R.id.roundProgressBar);
                if (item.getComm()!=null&&item.getComm().contains("%")) {
                    progressBar.setProgress(Integer.parseInt(item.getComm().replace("%", "")));
                }
                helper.setGone(R.id.team_tuiguang_layout, true);
                helper.setGone(R.id.team_my_arr_iv,false);
                helper.setGone(R.id.month_money_layotu,false);
            }else{//本月贡献
                helper.setGone(R.id.team_tuiguang_layout, false);
                helper.setGone(R.id.team_my_arr_iv, true);
                helper.setGone(R.id.month_money_layotu,true);
                helper.setText(R.id.total_money,item.getContribution());
                helper.setText(R.id.total_money_title,item.getContributiontitle());
            }

        } catch (Exception e) {
            Logger.e(e, "");
        }
    }
}
