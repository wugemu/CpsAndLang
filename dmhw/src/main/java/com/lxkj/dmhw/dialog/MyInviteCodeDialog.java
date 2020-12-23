package com.lxkj.dmhw.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.bean.MyTeamTotalDetailnfo;
import com.lxkj.dmhw.defined.SimpleDialog;
import com.lxkj.dmhw.utils.ToastUtil;
import com.lxkj.dmhw.utils.Utils;
//个人中心二维码弹框
public class MyInviteCodeDialog extends SimpleDialog<String []> {

    private ImageView image;
    private TextView  code;
    private LinearLayout layout_root,save_qrcode,copy_invite_code;
    private RelativeLayout layout_root_child;
    Bitmap QrBitmap;
    /**
     * 个人中心二维码弹框的弹框
     * @param context  上下文
     * @param data     数据
     */
    public MyInviteCodeDialog(Context context, String [] data) {
        super(context, R.layout.dialog_my_invitecode, data, true, false);
    }

    @Override
    protected void convert(ViewHolder helper) {
        image= helper.getView(R.id.invite_share_qr);
        code= helper.getView(R.id.invite_code);
        layout_root=helper.getView(R.id.layout_root);
        layout_root_child=helper.getView(R.id.layout_root_child);
        save_qrcode=helper.getView(R.id.save_qrcode);
        copy_invite_code=helper.getView(R.id.copy_invite_code);
        code.setText(data[0]);
        QrBitmap = Utils.generateBitmap(data[1], 400, 400);
        Utils.displayImage(context,QrBitmap,image);
        helper.setOnClickListener(R.id.copy_invite_code, this);
        helper.setOnClickListener(R.id.save_qrcode, this);
        helper.setOnClickListener(R.id.layout_root,this);
        helper.setOnClickListener(R.id.layout_root_child,this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_root:
                dismiss();
                break;
            case R.id.layout_root_child:
                break;
            case R.id.copy_invite_code:
                    Utils.copyText(data[0]);
                    ToastUtil.showImageToast(context, "复制成功", R.mipmap.toast_img);
                    dismiss();
                break;
            case R.id.save_qrcode:
                if (!Utils.checkPermision((Activity) context,1001,false)) {
                    return;
                }
                if(QrBitmap!=null){
                  if (!Utils.saveFile(Variable.imagesPath, QrBitmap, 100, true).isEmpty()){
                      ToastUtil.showImageToast(context,"已保存至系统相册",R.mipmap.toast_img);
                  }
                }
                dismiss();
                break;
        }
    }


}
