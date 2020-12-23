package com.lxkj.dmhw.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.PersonalAppIcon;
import com.lxkj.dmhw.bean.Promotion;
import com.lxkj.dmhw.defined.BaseQuickAdapter;
import com.lxkj.dmhw.utils.Utils;
import com.zhy.adapter.abslistview.ViewHolder;

public class PersonalAppIconAdapter extends BaseQuickAdapter<PersonalAppIcon> {

    private OnPersonalAppIconClickListener onPersonalAppIconClickListener;

    public PersonalAppIconAdapter(Context context) {
        super(context, R.layout.adapter_promotion);
    }

    public void setOnPersonalAppIconClickListener(OnPersonalAppIconClickListener onPersonalAppIconClickListener) {
        this.onPersonalAppIconClickListener = onPersonalAppIconClickListener;
    }
    @Override
    protected void convert(ViewHolder viewHolder, PersonalAppIcon item, int position) {
        LinearLayout.LayoutParams linearParamsOne =(LinearLayout.LayoutParams)viewHolder.getView(R.id.adapter_promotion_image).getLayoutParams();
        LinearLayout.LayoutParams linearParamstxt =(LinearLayout.LayoutParams)viewHolder.getView(R.id.adapter_promotion_text).getLayoutParams();
        if (item.getType().equals("1")){
            linearParamstxt.setMargins(0,Utils.dipToPixel(R.dimen.dp_2),0,0);
            linearParamsOne.height=Utils.dipToPixel(R.dimen.dp_43);
            linearParamsOne.width=Utils.dipToPixel(R.dimen.dp_43);
        }else{
            linearParamstxt.setMargins(0,Utils.dipToPixel(R.dimen.dp_10),0,0);
            linearParamsOne.height=Utils.dipToPixel(R.dimen.dp_20);
            linearParamsOne.width=Utils.dipToPixel(R.dimen.dp_20);
        }
        viewHolder.getView(R.id.adapter_promotion_image).setLayoutParams(linearParamsOne);
        viewHolder.getView(R.id.adapter_promotion_text).setLayoutParams(linearParamstxt);
        Utils.displayImage(context, item.getFuncico(), viewHolder.getView(R.id.adapter_promotion_image));
        viewHolder.setText(R.id.adapter_promotion_text, item.getFuncname());
        viewHolder.getView(R.id.adapter_promotion_layout).setOnClickListener(v -> onPersonalAppIconClickListener.onPersonalAppIconClick(item));
    }

    public interface OnPersonalAppIconClickListener {
        /**
         * 点击事件执行方法
         * @param item
         */
        void onPersonalAppIconClick(PersonalAppIcon item);
    }
}
