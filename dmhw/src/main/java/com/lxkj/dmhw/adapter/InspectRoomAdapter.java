package com.lxkj.dmhw.adapter;

import android.content.Context;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.InspectionRoom;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

/**
 * 验货直播间
 */

public class InspectRoomAdapter extends BaseQuickAdapter<InspectionRoom.Detail, BaseViewHolder> {

    private Context context;
    private int width;
    private int height;
    private float rioHtoW;

    public InspectRoomAdapter(Context context,int wid,int hei) {
        super(R.layout.view_inspect_room_layout);
        this.context = context;
        this.width=wid;
        this.height=hei;
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    protected void convert(BaseViewHolder helper, InspectionRoom.Detail item) {
        try {
           switch (item.getType()){
               case "0"://文字
                   helper.setGone(R.id.room_iv_layout, false);
                   helper.setGone(R.id.room_tv, true);
                   helper.setText(R.id.room_tv,item.getContent());
                   break;
               case "1"://图片
                   helper.setGone(R.id.room_iv_layout, true);
                   helper.setGone(R.id.commodity_video,false);
                   helper.setGone(R.id.room_tv, false);
                   helper.setGone(R.id.room_video_iv, false);
                   helper.setGone(R.id.room_iv, true);
                   RelativeLayout.LayoutParams linear_room_iv =(RelativeLayout.LayoutParams)helper.getView(R.id.room_iv).getLayoutParams();
                   linear_room_iv.width=width*2/5;
                   rioHtoW=(float)Integer.parseInt(item.getImgheight())/(float) Integer.parseInt(item.getImgwidth());
                   if (rioHtoW>=1){
                       linear_room_iv.height= (int)((float)linear_room_iv.width*rioHtoW);
                   }else{
                       rioHtoW=(float)Integer.parseInt(item.getImgwidth())/(float)Integer.parseInt(item.getImgheight());
                       linear_room_iv.height=(int)((float)linear_room_iv.width/rioHtoW);
                   }

                   helper.getView(R.id.room_iv).setLayoutParams(linear_room_iv);
                   Utils.displayImage(context,item.getImgurl(), helper.getView(R.id.room_iv));
                   break;

               case "2"://视频
                   helper.setGone(R.id.room_iv_layout, true);
                   helper.setGone(R.id.commodity_video,true);
                   helper.setGone(R.id.room_tv, false);
                   helper.setGone(R.id.room_video_iv, true);
                   helper.setGone(R.id.room_iv, false);
                   RelativeLayout.LayoutParams linear_room_video_iv =(RelativeLayout.LayoutParams)helper.getView(R.id.room_video_iv).getLayoutParams();
                   linear_room_video_iv.width=width*2/5;
                   rioHtoW=(float)Integer.parseInt(item.getImgheight())/(float) Integer.parseInt(item.getImgwidth());
                   if (rioHtoW>=1){
                       linear_room_video_iv.height= (int)((float)linear_room_video_iv.width*rioHtoW);
                   }else{
                       rioHtoW=(float)Integer.parseInt(item.getImgwidth())/(float)Integer.parseInt(item.getImgheight());
                       linear_room_video_iv.height= (int)((float)linear_room_video_iv.width/rioHtoW);
                   }
                   helper.getView(R.id.room_video_iv).setLayoutParams(linear_room_video_iv);
                   Utils.displayImage(context,item.getImgurl(), helper.getView(R.id.room_video_iv));
                   break;
            }
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }



}
