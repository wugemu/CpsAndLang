package com.lxkj.dmhw.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.MyTeamTotalDetailnfo;
import com.lxkj.dmhw.defined.SimpleDialog;
import com.lxkj.dmhw.utils.ToastUtil;
import com.lxkj.dmhw.utils.Utils;

public class TeamMenberDetailDialog extends SimpleDialog<MyTeamTotalDetailnfo> {

    private TextView  name;
    private ImageView image;
    private TextView  code;
    private TextView  weixin;
    private TextView  lastmoth;
    private TextView  leiji;
    private TextView retime;
    private LinearLayout layout_root;
    /**
     * 团队成员的弹框
     * @param context  上下文
     * @param data     数据
     */
    public TeamMenberDetailDialog(Context context, MyTeamTotalDetailnfo data) {
        super(context, R.layout.dialog_team_menber_detail, data, true, false);
    }

    @Override
    protected void convert(ViewHolder helper) {
        name= helper.getView(R.id.detail_txt_name);
        image= helper.getView(R.id.menber_image);
        code= helper.getView(R.id.detail_txt_code);
        weixin=helper.getView(R.id.detail_txt_weixin);
        lastmoth=helper.getView(R.id.detail_txt_last);
        leiji=helper.getView(R.id.detail_txt_leiji );
        retime=helper.getView(R.id.detail_txt_regi);
        layout_root=helper.getView(R.id.layout_root);
        helper.setOnClickListener(R.id.copy_code, this);
        helper.setOnClickListener(R.id.copy_weixin, this);

        helper.setOnClickListener(R.id.layout_root,this);

        name.setText(data.getUsername().trim());
        code.setText(data.getExtensionid());
        if (data.getWxcode().equals("")){
            weixin.setText("暂无");
        }else{
            weixin.setText(data.getWxcode());
        }

        lastmoth.setText(data.getMonthamount());
        leiji.setText(data.getProfit());
        retime.setText(data.getCreatetime());
        Utils.displayImageCircular(getContext(), data.getUserpicurl(), image);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_root:
                dismiss();
                break;
            case R.id.copy_code:
                if (data.getExtensionid().length()>0) {
                    Utils.copyText(data.getExtensionid());
                    ToastUtil.showImageToast(context,"复制成功",R.mipmap.toast_img);
                }

                break;
            case R.id.copy_weixin:
                if (data.getWxcode().length()>0){
                    Utils.copyText(data.getWxcode());
                    ToastUtil.showImageToast(context,"复制成功",R.mipmap.toast_img);
                }else{
                    ToastUtil.showImageToast(context,"暂无微信号",R.mipmap.toast_error);
            }

                break;
        }
    }


}
