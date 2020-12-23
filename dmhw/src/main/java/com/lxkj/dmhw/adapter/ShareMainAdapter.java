package com.lxkj.dmhw.adapter;

import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

/**
 * 更换主图图片选择
 */

public class ShareMainAdapter extends BaseQuickAdapter<ShareCheck, BaseViewHolder> {

    private Context context;
    public ShareMainAdapter(Context context) {
        super(R.layout.adapter_share_main);
        this.context = context;
    }
    @Override
    protected void convert(BaseViewHolder helper, ShareCheck item) {
        try {
            Utils.displayImageRoundedAll(context, item.getImage(), helper.getView(R.id.adapter_share_main_image),8);
            if (item.getMainImageCheck()==1){
             helper.setImageResource(R.id.adapter_share_main_check,R.mipmap.share_check_selected);
            }else{
             helper.setImageResource(R.id.adapter_share_main_check,R.mipmap.share_check_unchecked);
            }
            if (item.getIsMainFirstQr().equals("1")){
             helper.getView(R.id.share_mainqr_layout).setVisibility(View.VISIBLE);
            }else{
             helper.getView(R.id.share_mainqr_layout).setVisibility(View.GONE);
            }

        } catch (Exception e) {
            Logger.e(e, "");
        }
    }

}
