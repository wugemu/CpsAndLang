package com.lxkj.dmhw.adapter;
import android.content.Context;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.HomePage;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

public class JinGangQuIconAdapter extends BaseQuickAdapter<HomePage.JGQAppIcon, BaseViewHolder> {

    private Context context;
    private int iconSize;
    private boolean isBg;
    private JinGangQuIconAdapter.OnItemClickListener mListener;
    public void setOnItemClickListener(JinGangQuIconAdapter.OnItemClickListener li) {
        mListener = li;
    }
    public JinGangQuIconAdapter(Context context,int Size,boolean ishasBg) {
        super(R.layout.adapter_jgq);
        this.context = context;
        this.iconSize=Size;
        this.isBg=ishasBg;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, HomePage.JGQAppIcon item) {
        try {
            RecyclerView.LayoutParams linearParamsOne =(RecyclerView.LayoutParams)viewHolder.getView(R.id.adapter_icon_layout).getLayoutParams();
            linearParamsOne.width=iconSize;
            viewHolder.getView(R.id.adapter_icon_layout).setLayoutParams(linearParamsOne);
            Utils.displayImage(context, item.getImageurl(), viewHolder.getView(R.id.header_fragment_one_new_one_image));
            if (isBg){
            viewHolder.setTextColor(R.id.header_fragment_one_new_one_text, Color.parseColor("#333333"));
            }else{
            viewHolder.setTextColor(R.id.header_fragment_one_new_one_text, Color.parseColor("#333333"));
            }
            viewHolder.setText(R.id.header_fragment_one_new_one_text, item.getName());
            if(mListener != null) {
                viewHolder.getView(R.id.adapter_icon_layout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onItemClick(viewHolder.getAdapterPosition(), item);
                    }
                });
            }
        } catch (Exception e) {
            Logger.e(e, "jingangqu");
        }
    }
    public  interface OnItemClickListener {
        void onItemClick(int position,HomePage.JGQAppIcon data);
    }
}

