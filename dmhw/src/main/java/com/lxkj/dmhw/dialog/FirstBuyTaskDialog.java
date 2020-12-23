package com.lxkj.dmhw.dialog;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.dmhw.BaseDialogNew;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.ScoreActivity;
import com.lxkj.dmhw.bean.Sign;

/**
 * 签到
 */

public class FirstBuyTaskDialog extends BaseDialogNew {

    private Sign sign;
    private LinearLayout dialog_register_goto;
    private TextView give_bi_txt,count,give_bi_txt1;
    private LinearLayout self_buy,friend_buy;
    public FirstBuyTaskDialog(Activity activity) {
        super(activity);
        give_bi_txt=findView(R.id.give_bi_txt);
        give_bi_txt1=findView(R.id.give_bi_txt1);
        count=findView(R.id.count);
        self_buy=findView(R.id.self_buy);
        friend_buy=findView(R.id.friend_buy);
        findView(R.id.dialog_register_goto).setOnClickListener(this);
        findView(R.id.dialog_sign_clean).setOnClickListener(this);
    }

    @Override
    public View onInit() {
        return LinearLayout.inflate(context, R.layout.dialog_firstbuy_task, null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_register_goto:
                startActivity(new Intent(context, ScoreActivity.class).putExtra("positionname","money"));
                dialog.dismiss();
                break;
            case R.id.dialog_sign_clean:
                dialog.dismiss();
                break;
        }
    }
    /**
     * pos 弹框类型 自购 还是首购
     * count 好友首购人数
     * value 奖励数量
     */
    public void showDialog(int pos,String mount,String value) {
        if (pos==0){
        //自身首购
         give_bi_txt.setText(value);
         self_buy.setVisibility(View.VISIBLE);
         friend_buy.setVisibility(View.GONE);
        }else{
            count.setText(mount);
            give_bi_txt1.setText(value);
            self_buy.setVisibility(View.GONE);
            friend_buy.setVisibility(View.VISIBLE);
        }
        dialog.show();
    }
}
