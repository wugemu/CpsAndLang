package com.lxkj.dmhw.adapter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.ComCollegeData;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

/**
 * 商学院 新手视频
 */

public class ComCollPartTwoAdapter extends BaseQuickAdapter<ComCollegeData.NewHand, BaseViewHolder> {

    private Context context;

    public ComCollPartTwoAdapter(Context context) {
        super(R.layout.adapter_comcollparttwo);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ComCollegeData.NewHand item) {
        try {
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) helper.getView(R.id.adapter_parttwo_layout).getLayoutParams();
            switch (helper.getLayoutPosition()) {
                case 0:
                    params.setMargins(0, 0, 0, 0);
                    break;
                default:
                    params.setMargins(Utils.dipToPixel(R.dimen.dp_10), 0, 0, 0);
                    break;
            }
            if (item.getType().equals("1")){
                helper.getView(R.id. adapter_parttwo_check).setBackgroundResource(R.mipmap.vedio_logo);
            }else {
                helper.getView(R.id. adapter_parttwo_check).setBackgroundResource(R.mipmap.txt_logo);
            }
            helper.getView(R.id.adapter_parttwo_layout).setLayoutParams(params);
            Utils.displayImageRoundedNew(context, item.getImgurl(), helper.getView(R.id.adapter_parttwo_image),8);
            helper.setText(R.id.adapter_parttwo_title,  "      "+ item.getTitle());
            helper.setText(R.id.adapter_parttwo_number,  item.getClicknum()+"人已学");
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }

}
