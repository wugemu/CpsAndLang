package com.lxkj.dmhw.dialog;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
import com.lxkj.dmhw.bean.WeChatAbout;
import com.lxkj.dmhw.utils.ToastUtil;
import com.lxkj.dmhw.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 微信弹窗
 * Created by Android on 2018/5/23.
 */

public class WechatDialog extends BaseDialog {

    @BindView(R.id.dialog_wechat_image)
    ImageView dialogWechatImage;
    @BindView(R.id.dialog_wechat_id)
    TextView dialogWechatId;
    @BindView(R.id.dialog_wechat_copy)
    ImageView dialogWechatCopy;
    @BindView(R.id.dialog_wechat_cancel)
    LinearLayout dialogWechatCancel;
    @BindView(R.id.dialog_wechat_save)
    LinearLayout dialogWechatSave;
    private WeChatAbout about;

    public WechatDialog(Context context) {
        super(context);
        ButterKnife.bind(this, view);
    }

    @Override
    public View onInit() {
        return LinearLayout.inflate(context, R.layout.dialog_wechat, null);
    }

    @Override
    public void onClick(View v) {

    }

    @OnClick({R.id.dialog_wechat_copy, R.id.dialog_wechat_cancel, R.id.dialog_wechat_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dialog_wechat_copy:
                if (!TextUtils.isEmpty(dialogWechatId.getText().toString())) {
                    Utils.copyText(dialogWechatId.getText().toString());
                    ToastUtil.showImageToast(context,"复制成功",R.mipmap.toast_img);
                }
                break;
            case R.id.dialog_wechat_cancel:
                dialog.dismiss();
                break;
            case R.id.dialog_wechat_save:
                Glide.with(context).asBitmap()
                        .load(about.getImg()).into(new SimpleTarget<Bitmap>()
                        {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                Utils.saveFile(Variable.picturePath, resource, 100, true);
                                toast("图片已保存到相册");
                            }
                        });
                break;
        }
    }


    public void showDialog(WeChatAbout about) {
        this.about = about;
        Utils.displayImage(context, about.getImg(), dialogWechatImage);
        dialogWechatId.setText(about.getWechatId());
        dialog.show();
    }
}
