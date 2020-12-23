package com.lxkj.dmhw.adapter.cps;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.adapter.JGQSortAdapter;
import com.lxkj.dmhw.bean.HomePage;
import com.lxkj.dmhw.utils.Utils;

public class SpecilJGQAdapter extends BaseQuickAdapter<HomePage.JGQAppIcon, BaseViewHolder> {

    private SpecilJGQAdapter.OnJGQAppIconClickListener onJGQAppIconClickListener;
    private Context context;
    private int sWidth;
    public SpecilJGQAdapter(Context context,int width) {
        super(R.layout.adapter_sepcil_jgq_layout);
        this.context=context;
        this.sWidth=width;
    }

    public void setOnJGQAppIconClickListener(SpecilJGQAdapter.OnJGQAppIconClickListener onJGQAppIconClickListener) {
        this.onJGQAppIconClickListener = onJGQAppIconClickListener;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomePage.JGQAppIcon item) {
        LinearLayout adapter_jgq_layout = helper.getView(R.id.adapter_jgq_layout);
        RecyclerView.LayoutParams linearParams =(RecyclerView.LayoutParams)adapter_jgq_layout.getLayoutParams();
        ImageView adapter_jgq_image = helper.getView(R.id.adapter_jgq_image);
        LinearLayout.LayoutParams linearParamsOne =(LinearLayout.LayoutParams)adapter_jgq_image.getLayoutParams();
        TextView txt=helper.getView(R.id.adapter_jgq_text);
        TextView adapter_jgq_text_dec=helper.getView(R.id.adapter_jgq_text_dec);
        adapter_jgq_text_dec.setVisibility(View.INVISIBLE);
//        if (helper.getAdapterPosition()<=4){
//            linearParams.setMargins(0,0,0,0);
//            linearParamsOne.height= Utils.dipToPixel(R.dimen.dp_43);
//            linearParamsOne.width=Utils.dipToPixel(R.dimen.dp_43);
//            helper.setTextColor(R.id.adapter_jgq_text, Color.parseColor("#000000"));
//            int dimen = context.getResources().getDimensionPixelSize(R.dimen.sp_11);
//            txt.setTextSize(TypedValue.COMPLEX_UNIT_PX,dimen);
//        }else{
//            linearParams.setMargins(0,Utils.dipToPixel(R.dimen.dp_10),0,0);
//            linearParamsOne.height=Utils.dipToPixel(R.dimen.dp_25);
//            linearParamsOne.width=Utils.dipToPixel(R.dimen.dp_25);
//            helper.setTextColor(R.id.adapter_jgq_text, Color.parseColor("#000000"));
//            int dimen = context.getResources().getDimensionPixelSize(R.dimen.sp_11);
//            txt.setTextSize(TypedValue.COMPLEX_UNIT_PX,dimen);
//        }
//        linearParams.setMargins(0,0,0,0);
        linearParamsOne.height= Utils.dipToPixel(R.dimen.dp_43);
        linearParamsOne.width=Utils.dipToPixel(R.dimen.dp_43);
        helper.setTextColor(R.id.adapter_jgq_text, Color.parseColor("#000000"));
        int dimen = context.getResources().getDimensionPixelSize(R.dimen.sp_11);
        txt.setTextSize(TypedValue.COMPLEX_UNIT_PX,dimen);

//        adapter_jgq_layout.setLayoutParams(linearParams);
        adapter_jgq_image.setLayoutParams(linearParamsOne);
        Utils.displayImage(context, item.getImageurl(), helper.getView(R.id.adapter_jgq_image));
        helper.setText(R.id.adapter_jgq_text, item.getName());
        helper.getView(R.id.adapter_jgq_layout).setOnClickListener(v -> onJGQAppIconClickListener.onJGQAppIconClick(item));
    }

    public interface OnJGQAppIconClickListener {
        /**
         * 点击事件执行方法
         * @param item
         */
        void onJGQAppIconClick(HomePage.JGQAppIcon item);
    }
}