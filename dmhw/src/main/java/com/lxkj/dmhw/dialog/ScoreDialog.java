package com.lxkj.dmhw.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.dmhw.BaseDialog;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;

/**
 * 积分兑换
 * Created by zyhant on 18-3-8.
 */

public class ScoreDialog extends BaseDialog {

    private TextView
            dialogScoreOne,
            dialogScoreTwo,
            dialogScoreThree;
    private LinearLayout dialogScoreBtn;

    public ScoreDialog(Activity activity) {
        super(activity);
        dialogScoreOne = findView(R.id.dialog_score_one);
        dialogScoreTwo = findView(R.id.dialog_score_two);
        dialogScoreThree = findView(R.id.dialog_score_three);
        dialogScoreBtn = findView(R.id.dialog_score_btn);
        dialogScoreBtn.setOnClickListener(this);
        findView(R.id.dialog_score_cancel_one).setOnClickListener(this);
        findView(R.id.dialog_score_cancel_two).setOnClickListener(this);
        findView(R.id.dialog_score_cancel_three).setOnClickListener(this);
        findView(R.id.dialog_score_cancel_four).setOnClickListener(this);
    }

    @Override
    public View onInit() {
        return LinearLayout.inflate(context, R.layout.dialog_score, null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_score_btn:
                InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ScoreDialog"), "", 0);
                dialog.dismiss();
                break;
            case R.id.dialog_score_cancel_one:
                dialog.dismiss();
                break;
            case R.id.dialog_score_cancel_two:
                dialog.dismiss();
                break;
            case R.id.dialog_score_cancel_three:
                dialog.dismiss();
                break;
            case R.id.dialog_score_cancel_four:
                dialog.dismiss();
                break;
        }
    }

    public void showDialog(String score, String money, String proportion) {
        dialogScoreOne.setText(score);
        dialogScoreTwo.setText(money);
        dialogScoreThree.setText(proportion);
        if (score.equals("0")) {
            dialogScoreBtn.setVisibility(View.GONE);
        } else {
            dialogScoreBtn.setVisibility(View.VISIBLE);
        }
        dialog.show();
    }
}
