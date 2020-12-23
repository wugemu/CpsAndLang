package com.lxkj.dmhw.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

/**
 * 图片选择
 * Created by zyhant on 18-2-8.
 */

public class ShareAdapter extends BaseQuickAdapter<ShareCheck, BaseViewHolder> {

    private OnClickListener onClickListener;
    private Context context;
    private boolean isfirstPos=true;
    public ArrayList<ShareCheck> tempList=new ArrayList<>();
    public ShareAdapter(Context context) {
        super(R.layout.adapter_share);
        this.context = context;
    }

    public void setOnClickListener(ShareAdapter.OnClickListener li) {
        this.onClickListener = li;
    }

    @Override
    protected void convert(BaseViewHolder helper, ShareCheck item) {
        try {
            if (isfirstPos){
                tempList.add(getData().get(0));
            }
            isfirstPos=false;
            Utils.displayImageRoundedAll(context, item.getImage(), helper.getView(R.id.adapter_share_image),8);
            if (item.getCheck()==1){
             helper.setImageResource(R.id.adapter_share_check,R.mipmap.share_check_selected);
            }else{
             helper.setImageResource(R.id.adapter_share_check,R.mipmap.share_check_unchecked);
            }
            item.setPos(helper.getAdapterPosition());
            if (item.getIsFirstQr().equals("1")){
             helper.getView(R.id.share_qr_layout).setVisibility(View.VISIBLE);
            }else{
             helper.getView(R.id.share_qr_layout).setVisibility(View.GONE);
            }

            helper.getView(R.id.adapter_share_image_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (item.getCheck()==1){
                        tempList.remove(item);
                        item.setCheck(0);
                    }else{
                        tempList.add(item);
                        item.setCheck(1);
                    }
                    for (int i=0;i<getData().size();i++) {
                        getData().get(i).setIsFirstQr("0");
                    }
                    if (tempList.size()>0) {
                        getData().get(tempList.get(0).getPos()).setIsFirstQr("1");
                    }

                    notifyDataSetChanged();
                    if (onClickListener!= null) {
                        onClickListener.onShareClick(tempList.size());
                    }
                }
            });
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }

    /**
     * 点击事件
     */
    public interface OnClickListener {
        void onShareClick(int num);
    }
}
