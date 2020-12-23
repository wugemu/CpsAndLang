package com.lxkj.dmhw.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.ComCollegeData;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;


public class ComCollNewHandAdapter extends BaseQuickAdapter<ComCollegeData.NewHand, BaseViewHolder> {

    private Context context;
    private String fromType;
    public ComCollNewHandAdapter(Context context,String from,int hotCourse) {
        super(R.layout.adapter_comcoll_newhand);
        this.context = context;
        this.fromType=from;
    }

    @Override
    protected void convert(BaseViewHolder helper, ComCollegeData.NewHand item) {
        try {
            if (fromType.equals("partOneQues")){
                helper.setGone(R.id.adapter_new_one_fragment_layout,false);
                helper.setGone(R.id.adapter_parttwo_question_layout,true);
                helper.getView(R.id. adapter_parttwo_quesimg).setBackgroundResource(R.mipmap.ques_icon);
                helper.setText(R.id.adapter_parttwo_itemnum,"Q"+(helper.getAdapterPosition()+1));
                helper.setText(R.id.adapter_parttwo_questitle, "       "+item.getTitle());
                helper.setText(R.id.adapter_parttwo_quesnumber,  item.getClicknum()+"人已读");
                helper.setText(R.id.adapter_parttwo_questime,  item.getDate());
            }else{
                helper.setGone(R.id.adapter_new_one_fragment_layout,true);
                helper.setGone(R.id.adapter_parttwo_question_layout,false);
                if (item.getType().equals("1")){
                    helper.getView(R.id. adapter_parttwo_check).setBackgroundResource(R.mipmap.vedio_logo);
                }else {
                    helper.getView(R.id. adapter_parttwo_check).setBackgroundResource(R.mipmap.txt_logo);
                }
                if (fromType.equals("partThree")){
                    helper.setGone(R.id.list_share_layout,false);
                    helper.setGone(R.id.adapter_parttwo_type,true);
                    helper.setText(R.id.adapter_parttwo_type, item.getTypetitle());
                }else{
                    helper.setGone(R.id.list_share_layout,true);
                    helper.setGone(R.id.adapter_parttwo_type,false);
                }
                Utils.displayImageRoundedAll(context, item.getImgurl(), helper.getView(R.id.adapter_parttwo_image),8);
                helper.setText(R.id.adapter_parttwo_title, "       "+item.getTitle());
                helper.setText(R.id.adapter_parttwo_number,  item.getClicknum()+"人已学");
                helper.setText(R.id.adapter_share_number,  item.getSharenum());
            }

        } catch (Exception e) {
            Logger.e(e, "");
        }
    }
}
