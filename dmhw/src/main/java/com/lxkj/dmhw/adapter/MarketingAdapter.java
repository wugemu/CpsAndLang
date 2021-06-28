package com.lxkj.dmhw.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.LoginActivity;
import com.lxkj.dmhw.bean.Marketing;
import com.lxkj.dmhw.bean.ShareParams;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.utils.Share;
import com.lxkj.dmhw.utils.ToastUtil;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Objects;

/**
 * 营销素材
 * Created by Android on 2018/5/8.
 */

public class MarketingAdapter extends BaseQuickAdapter<Marketing.MarketingList, BaseViewHolder> {

    private Context context;
    private OnItemImageClickListener mListener;
    public void setOnItemImageClickListener(OnItemImageClickListener li) {
        mListener = li;
    }
    public MarketingAdapter(Context context) {
        super(R.layout.adapter_marketing);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Marketing.MarketingList item) {
        try {
            Utils.displayImageCircular(context, item.getImgpic(), helper.getView(R.id.adapter_marketing_image));
            helper.setText(R.id.adapter_marketing_name, item.getTitle());
            helper.setText(R.id.adapter_marketing_content, item.getContent());
            helper.setText(R.id.adapter_marketing_time, item.getSendtime());

            if (item.getComment().equals("")){
                helper.setGone(R.id.comment_layout, false);
            }else{
                helper.setGone(R.id.comment_layout, true);
                helper.setText(R.id.reason_txt, item.getComment());
            }

            if (item.getImglist().size() == 1) {
                helper.setGone(R.id.adapter_marketing_content_image, true);
                helper.setGone(R.id.adapter_marketing_layout, false);
                if (Objects.equals(item.getVideourl(), "")) {
                    helper.setGone(R.id.adapter_marketing_content_video, false);
                } else {
                    helper.setGone(R.id.adapter_marketing_content_video, true);
                }
                helper.setGone(R.id.adapter_marketing_content_video_one, false);
                imageListOne(helper, item);
            } else {
                helper.setGone(R.id.adapter_marketing_content_image, false);
                helper.setGone(R.id.adapter_marketing_layout, true);
                if (Objects.equals(item.getVideourl(), "")) {
                    helper.setGone(R.id.adapter_marketing_content_video_one, false);
                } else {
                    helper.setGone(R.id.adapter_marketing_content_video_one, true);
                }
                helper.setGone(R.id.adapter_marketing_content_video, false);
                imageList(helper, item);
            }
            helper.getView(R.id.adapter_marketing_content).setOnLongClickListener(v -> {
                Utils.copyText(((TextView) helper.getView(R.id.adapter_marketing_content)).getText().toString());
                ((Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(50);
                ToastUtil.showImageToast(context,"复制成功",R.mipmap.toast_img);
                return false;
            });
            helper.getView(R.id.adapter_marketing_share).setOnClickListener(v -> {
                if (DateStorage.getLoginStatus()) {
                    helper.getView(R.id.adapter_marketing_share).setEnabled(false);
                    share(helper, item);
                } else {
                    context.startActivity(new Intent(context, LoginActivity.class));
                }
            });
            helper.getView(R.id.copy_txt_layout).setOnClickListener(v ->{
                Utils.copyText(item.getComment());
                ((Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(50);
                ToastUtil.showImageToast(context,"复制成功",R.mipmap.toast_img);
            });
            helper.getView(R.id.adapter_marketing_content_image).setOnClickListener(v -> {
                mListener.onItemImageClick(item,0);
            });
            helper.getView(R.id.adapter_marketing_content_image_one).setOnClickListener(v -> {
                mListener.onItemImageClick(item,0);
            });
            helper.getView(R.id.adapter_marketing_content_image_two).setOnClickListener(v -> {
                mListener.onItemImageClick(item,1);
            });
            helper.getView(R.id.adapter_marketing_content_image_three).setOnClickListener(v -> {
                mListener.onItemImageClick(item,2);
            });
            helper.getView(R.id.adapter_marketing_content_image_four).setOnClickListener(v -> {
                mListener.onItemImageClick(item,3);
            });
            helper.getView(R.id.adapter_marketing_content_image_five).setOnClickListener(v -> {
                mListener.onItemImageClick(item,4);
            });
            helper.getView(R.id.adapter_marketing_content_image_six).setOnClickListener(v -> {
                mListener.onItemImageClick(item,5);
            });
            helper.getView(R.id.adapter_marketing_content_image_seven).setOnClickListener(v -> {
                mListener.onItemImageClick(item,6);
            });
            helper.getView(R.id.adapter_marketing_content_image_eight).setOnClickListener(v -> {
                mListener.onItemImageClick(item,7);
            });
            helper.getView(R.id.adapter_marketing_content_image_nine).setOnClickListener(v -> {
                mListener.onItemImageClick(item,8);
            });
            helper.getView(R.id.onekey_layout).setOnClickListener(v -> mListener.onOneKey(item));
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }

    private void imageListOne(BaseViewHolder helper, Marketing.MarketingList item) {
        Utils.displayImage(context, item.getImglist().get(0).getImgurl(), helper.getView(R.id.adapter_marketing_content_image));
    }

    /**
     * 图片列表
     * @param helper
     * @param item
     */
    private void imageList(BaseViewHolder helper, Marketing.MarketingList item) {
        ArrayList<Marketing.MarketingList.ImageUrl> imageUrls = item.getImglist();
        switch (imageUrls.size()) {
            case 2:
                helper.setGone(R.id.adapter_marketing_layout_two, false);
                helper.setGone(R.id.adapter_marketing_layout_three, false);
                helper.setVisible(R.id.adapter_marketing_content_image_three, false);
                Utils.displayImage(context, imageUrls.get(0).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_one));
                Utils.displayImage(context, imageUrls.get(1).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_two));
                break;
            case 3:
                helper.setGone(R.id.adapter_marketing_layout_two, false);
                helper.setGone(R.id.adapter_marketing_layout_three, false);
                Utils.displayImage(context, imageUrls.get(0).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_one));
                Utils.displayImage(context, imageUrls.get(1).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_two));
                Utils.displayImage(context, imageUrls.get(2).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_three));
                break;
            case 4:
                helper.setGone(R.id.adapter_marketing_layout_two, true);
                helper.setGone(R.id.adapter_marketing_layout_three, false);
                helper.setVisible(R.id.adapter_marketing_content_image_five, false);
                helper.setVisible(R.id.adapter_marketing_content_image_six, false);
                Utils.displayImage(context, imageUrls.get(0).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_one));
                Utils.displayImage(context, imageUrls.get(1).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_two));
                Utils.displayImage(context, imageUrls.get(2).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_three));
                Utils.displayImage(context, imageUrls.get(3).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_four));
                break;
            case 5:
                helper.setGone(R.id.adapter_marketing_layout_two, true);
                helper.setGone(R.id.adapter_marketing_layout_three, false);
                helper.setVisible(R.id.adapter_marketing_content_image_six, false);
                Utils.displayImage(context, imageUrls.get(0).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_one));
                Utils.displayImage(context, imageUrls.get(1).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_two));
                Utils.displayImage(context, imageUrls.get(2).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_three));
                Utils.displayImage(context, imageUrls.get(3).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_four));
                Utils.displayImage(context, imageUrls.get(4).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_five));
                break;
            case 6:
                helper.setGone(R.id.adapter_marketing_layout_two, true);
                helper.setGone(R.id.adapter_marketing_layout_three, false);
                Utils.displayImage(context, imageUrls.get(0).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_one));
                Utils.displayImage(context, imageUrls.get(1).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_two));
                Utils.displayImage(context, imageUrls.get(2).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_three));
                Utils.displayImage(context, imageUrls.get(3).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_four));
                Utils.displayImage(context, imageUrls.get(4).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_five));
                Utils.displayImage(context, imageUrls.get(5).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_six));
                break;
            case 7:
                helper.setGone(R.id.adapter_marketing_layout_two, true);
                helper.setGone(R.id.adapter_marketing_layout_three, true);
                helper.setVisible(R.id.adapter_marketing_content_image_eight, false);
                helper.setVisible(R.id.adapter_marketing_content_image_nine, false);
                Utils.displayImage(context, imageUrls.get(0).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_one));
                Utils.displayImage(context, imageUrls.get(1).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_two));
                Utils.displayImage(context, imageUrls.get(2).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_three));
                Utils.displayImage(context, imageUrls.get(3).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_four));
                Utils.displayImage(context, imageUrls.get(4).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_five));
                Utils.displayImage(context, imageUrls.get(5).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_six));
                Utils.displayImage(context, imageUrls.get(6).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_seven));
                break;
            case 8:
                helper.setGone(R.id.adapter_marketing_layout_two, true);
                helper.setGone(R.id.adapter_marketing_layout_three, true);
                helper.setVisible(R.id.adapter_marketing_content_image_nine, false);
                Utils.displayImage(context, imageUrls.get(0).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_one));
                Utils.displayImage(context, imageUrls.get(1).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_two));
                Utils.displayImage(context, imageUrls.get(2).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_three));
                Utils.displayImage(context, imageUrls.get(3).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_four));
                Utils.displayImage(context, imageUrls.get(4).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_five));
                Utils.displayImage(context, imageUrls.get(5).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_six));
                Utils.displayImage(context, imageUrls.get(6).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_seven));
                Utils.displayImage(context, imageUrls.get(7).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_eight));
                break;
            case 9:
                helper.setGone(R.id.adapter_marketing_layout_two, true);
                helper.setGone(R.id.adapter_marketing_layout_three, true);
                Utils.displayImage(context, imageUrls.get(0).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_one));
                Utils.displayImage(context, imageUrls.get(1).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_two));
                Utils.displayImage(context, imageUrls.get(2).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_three));
                Utils.displayImage(context, imageUrls.get(3).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_four));
                Utils.displayImage(context, imageUrls.get(4).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_five));
                Utils.displayImage(context, imageUrls.get(5).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_six));
                Utils.displayImage(context, imageUrls.get(6).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_seven));
                Utils.displayImage(context, imageUrls.get(7).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_eight));
                Utils.displayImage(context, imageUrls.get(8).getImgurl(), helper.getView(R.id.adapter_marketing_content_image_nine));
                break;
        }
    }

    private void share(BaseViewHolder helper, Marketing.MarketingList item) {
        String title = item.getContent() + "\n";
        ShareParams params = new ShareParams();
        params.setShareTag(3);
        params.setContent(title);
        ArrayList<String> image = new ArrayList<>();
        for (Marketing.MarketingList.ImageUrl imageUrl : item.getImglist()) {
            image.add(imageUrl.getImgurl());
        }
        params.setImage(image);
        //图片
        if (item.getVideourl().equals("")){
            Share.getInstance(0).initialize(params,0);
        }else{
            //视频
            params.setVediourl(item.getVideourl());
            Share.getInstance(2).initialize(params,2);
        }

        Utils.copyText(title);
        ToastUtil.showImageToast(context,"复制成功",R.mipmap.toast_img);
        helper.getView(R.id.adapter_marketing_share).setEnabled(true);
    }

    public  interface OnItemImageClickListener {
        void onItemImageClick(Marketing.MarketingList item,int position);
        void onOneKey(Marketing.MarketingList item);
    }
}
