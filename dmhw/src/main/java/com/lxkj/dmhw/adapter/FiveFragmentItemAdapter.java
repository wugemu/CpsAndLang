package com.lxkj.dmhw.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.PersonalAppIcon;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

/**
 * 我的任务适配器
 */

public class FiveFragmentItemAdapter extends BaseQuickAdapter<PersonalAppIcon, BaseViewHolder> {
    private  Context con;
    public String titleOne;
    private String IVcode;
    public FiveFragmentItemAdapter(Context context,String inviteCode) {
        super(R.layout.adapter_five_item);
        this.con=context;
        this.IVcode=inviteCode;
    }
    @Override
    protected void convert(BaseViewHolder helper, PersonalAppIcon item) {
        try {
         Utils.displayImage(con, item.getFuncico(), helper.getView(R.id.adapter_promotion_image));
         helper.setText(R.id.title_txt, item.getFuncname());
         if(helper.getLayoutPosition()==getItemCount()-1){
             helper.setGone(R.id.split_item,false);
         }
         if (DateStorage.getLoginStatus()) {
             switch (item.getFuncname()) {
                 case "个人商城":
                     helper.setText(R.id.item_title_txt, "分享好友");
                     break;
                 case "邀请码":
                     if (IVcode.equals("")) {
                         helper.setText(R.id.item_title_txt, DateStorage.getInformation().getExtensionid());
                     } else {
                         helper.setText(R.id.item_title_txt, IVcode);
                     }
                     break;
                 case "导师微信":
                     helper.setText(R.id.item_title_txt, DateStorage.getInformation().getWxcode());
                     break;
                 default:
                     helper.setText(R.id.item_title_txt, "");
                     break;
             }
         }else{
             switch (item.getFuncname()) {
                 case "个人商城":
                     helper.setText(R.id.item_title_txt, "分享好友");
                     break;
                 default:
                     helper.setText(R.id.item_title_txt, "");
                     break;
             }
         }
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }
}
