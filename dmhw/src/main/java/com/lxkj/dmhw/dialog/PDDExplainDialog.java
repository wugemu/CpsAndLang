package com.lxkj.dmhw.dialog;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.dmhw.BaseDialogNew;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.activity.WebViewActivity;

/**
 * 拼多多比价规则说明
 */

public class PDDExplainDialog extends BaseDialogNew {

    private TextView give_bi_txt;
    private int startLength=30;
    private int endLength=39;
    public PDDExplainDialog(Activity activity) {
        super(activity);
        give_bi_txt=findView(R.id.explain_txt);
        findView(R.id.dialog_btn).setOnClickListener(this);
    }

    @Override
    public View onInit() {
        return LinearLayout.inflate(context, R.layout.dialog_pdd_explain, null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_sign_clean:
            case R.id.dialog_btn:
                dialog.dismiss();
                break;
        }
    }

    public void showDialog(String value) {
        SpannableStringBuilder style=new SpannableStringBuilder(context.getResources().getString(R.string.str_pdd_expalin));
        style.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra(Variable.webUrl,value);
                startActivity(intent);
                dialog.dismiss();
            }
        }, startLength, endLength, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(new NoUnderlineSpan(),startLength,endLength, Spanned.SPAN_MARK_MARK);
        style.setSpan(new ForegroundColorSpan(Color.parseColor("#FF1944")),startLength,endLength, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        give_bi_txt.setText(style);
        give_bi_txt.setMovementMethod(LinkMovementMethod.getInstance());
        dialog.show();
    }

    // 创建一个自定义的去除下划线的 Span  内部类或者外部类,自行选择
    class NoUnderlineSpan extends UnderlineSpan {

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(ds.linkColor);
            ds.setUnderlineText(false);
        }
    }
}
