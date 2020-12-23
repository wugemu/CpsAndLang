package com.lxkj.dmhw.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.AccountDel;
import com.orhanobut.logger.Logger;

/**
 * 注销账号原因列表
 */

public class AccountDelReasonAdapter extends BaseQuickAdapter<AccountDel.reasonData, BaseViewHolder> {
    public AccountDelReasonAdapter() {
        super(R.layout.adapter_account_del_reson);
    }

    @Override
    protected void convert(BaseViewHolder helper,AccountDel.reasonData item) {
        try {
           helper.setText(R.id.adapter_reason, item.getName());
           if (item.isCheck()){
            helper.setImageResource(R.id.checkbox_iv,R.mipmap.account_reason_check);
           }else{
            helper.setImageResource(R.id.checkbox_iv,R.mipmap.account_del_uncheck);
           }
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }

    //单选
    public void reasonCheck(int pos){
        for(int i=0;i<this.mData.size();i++){
            if (i==pos){
             this.mData.get(i).setCheck(true);
            }else{
             this.mData.get(i).setCheck(false);
            }
            notifyDataSetChanged();
        }
    }
}
