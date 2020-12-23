package com.lxkj.dmhw.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.ProfitEveryPL;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

public class PlTimeItemAdapter extends BaseQuickAdapter<ProfitEveryPL, BaseViewHolder> {

    private Context context;

    public PlTimeItemAdapter(Context context) {
        super(R.layout.adapter_time_iteml);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper,ProfitEveryPL item) {
        try {
            helper.setText(R.id.time, item.getTitle());
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)helper.getView(R.id.time).getLayoutParams();
            if (helper.getLayoutPosition()==0){
                lp.leftMargin = 0;
            }else{
                lp.leftMargin = Utils.dipToPixel(R.dimen.dp_9);
            }
            helper.getView(R.id.time).setLayoutParams(lp);
            if (item.isCheck()){
                helper.setTextColor(R.id.time, Color.parseColor("#000000"));
                helper.setBackgroundRes(R.id.time,R.drawable.morepl_background_style_all);
            }else{
                helper.setBackgroundRes(R.id.time,R.drawable.morepl_background_style);
                helper.setTextColor(R.id.time, Color.parseColor("#666666"));
            }
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }
}
