package com.lxkj.dmhw.dialog;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lxkj.dmhw.BaseDialog;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.utils.ToastUtil;
import com.lxkj.dmhw.utils.Utils;


/**
 * 联系客服
 * Created by zyhant on 18-2-27.
 */

public class ServiceDialog extends BaseDialog {

    private ImageView dialogServiceImage;
    private TextView dialogServiceWeChat;
    private LinearLayout dialogServicePreservation;
    private TextView dialogServicePreservationText;
    private String
            id,
            image;

    public ServiceDialog(Activity activity) {
        super(activity);
        dialogServiceImage = findView(R.id.dialog_service_image);
        dialogServiceWeChat = findView(R.id.dialog_service_wechat);
        dialogServicePreservation = findView(R.id.dialog_service_preservation);
        dialogServicePreservationText = findView(R.id.dialog_service_preservation_text);
        findView(R.id.dialog_service_close).setOnClickListener(this);
        findView(R.id.dialog_service_copy).setOnClickListener(this);
        dialogServicePreservation.setOnClickListener(this);
    }

    @Override
    public View onInit() {
        return LinearLayout.inflate(context, R.layout.dialog_service, null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_service_close:
                dialog.dismiss();
                break;
            case R.id.dialog_service_copy:
                Utils.copyText(id);
                ToastUtil.showImageToast(context,"复制成功",R.mipmap.toast_img);
                break;
            case R.id.dialog_service_preservation:

                Glide.with(context).asBitmap()
                        .load(image)
                        .centerCrop()
                        .override(1080, 1080).into(new SimpleTarget<Bitmap>()
                        {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                String content = Utils.saveFile(Variable.picturePath, resource, 100, true);
                                ToastUtil.showImageToast(context,"图片已保存至相册",R.mipmap.toast_img);
                            }
                        });
        }
    }

    public void showDialog(String id, String image) {
        this.id = id;
        this.image = image;
        if (image.equals("")) {
            dialogServiceImage.setVisibility(View.GONE);
            dialogServicePreservation.setEnabled(false);
            dialogServicePreservationText.setTextColor(0xff666666);
        } else {
            dialogServiceImage.setVisibility(View.VISIBLE);
            dialogServicePreservation.setEnabled(true);
            dialogServicePreservationText.setTextColor(context.getResources().getColor(R.color.mainColor));
            Utils.displayImage(context, image, dialogServiceImage);
        }
        dialogServiceWeChat.setText(id);
        dialog.show();
    }
}
