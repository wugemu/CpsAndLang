package com.lxkj.dmhw.adapter;

import android.content.Context;
import android.text.TextPaint;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.WithdrawalsList;
import com.lxkj.dmhw.bean.WithdrawalsListMorePl;
import com.orhanobut.logger.Logger;

public class WithdrawListTaoAdapter extends BaseQuickAdapter<WithdrawalsList, BaseViewHolder> {

    private Context context;
    TextPaint tp;
    public WithdrawListTaoAdapter(Context context) {
        super(R.layout.adapter_withdrawals);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, WithdrawalsList item) {
        try {
            TextView adapter_withdrawals_money= helper.getView(R.id.adapter_withdrawals_money);
            tp= adapter_withdrawals_money.getPaint();
            tp.setFakeBoldText(true);
            helper.setText(R.id.adapter_withdrawals_money,item.getWithdrawalsmoney());
            helper.setText(R.id.adapter_withdrawals_time,item.getWithdrawalstime());
            helper.setText(R.id.adapter_withdrawals_status,item.getName());
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }
}
