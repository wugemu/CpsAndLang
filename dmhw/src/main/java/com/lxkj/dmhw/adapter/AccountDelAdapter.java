package com.lxkj.dmhw.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.AccountDel;
import com.lxkj.dmhw.bean.DMBDetail;
import com.orhanobut.logger.Logger;

/**
 * 注销账号数据
 */

public class AccountDelAdapter extends BaseQuickAdapter<AccountDel.Accontdata, BaseViewHolder> {
    public AccountDelAdapter() {
        super(R.layout.adapter_account_del);
    }

    @Override
    protected void convert(BaseViewHolder helper,AccountDel.Accontdata item) {
        try {
            helper.setText(R.id.adapter_name, item.getName());
            helper.setText(R.id.adapter_data, item.getValue());
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }
}
