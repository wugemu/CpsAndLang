package com.lxkj.dmhw.adapter;

import android.content.Context;
import android.widget.LinearLayout;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.PersonalAppIcon;
import com.lxkj.dmhw.defined.BaseQuickAdapter;
import com.lxkj.dmhw.utils.Utils;
import com.zhy.adapter.abslistview.ViewHolder;

public class PersonalGridFourAdapter extends BaseQuickAdapter<PersonalAppIcon> {

    private OnPersonalAppIconClickListener onPersonalAppIconClickListener;

    public PersonalGridFourAdapter(Context context) {
        super(context, R.layout.adapter_girdfour);
    }

    public void setOnPersonalAppIconClickListener(OnPersonalAppIconClickListener onPersonalAppIconClickListener) {
        this.onPersonalAppIconClickListener = onPersonalAppIconClickListener;
    }
    @Override
    protected void convert(ViewHolder viewHolder, PersonalAppIcon item, int position) {
        Utils.displayImage(context, item.getFuncico(), viewHolder.getView(R.id.adapter_promotion_image));
        viewHolder.setText(R.id.adapter_promotion_text, item.getFuncname());
        viewHolder.setText(R.id.adapter_promotion_text2, item.getDesc());
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
