package com.lxkj.dmhw.dialog;

import android.content.Context;
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
import android.widget.TextView;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.activity.WebViewActivity;
import com.lxkj.dmhw.defined.SimpleDialogNocanel;


//用户协议弹框
public class UserPermissionDialog extends SimpleDialogNocanel<String> {
    private UserPermissionDialog.OnBtnClickListener mListener;

    public void setOnClickListener(UserPermissionDialog.OnBtnClickListener li) {
        mListener = li;
    }
    private TextView  content_tv,content_tv01;
    /**
     * @param context  上下文
     * @param data     数据
     */
    public UserPermissionDialog(Context context, String data) {
        super(context, R.layout.dialog_user_permission, data, true, false);
    }

    @Override
    protected void convert(ViewHolder helper) {
        content_tv= helper.getView(R.id.content_tv);
        content_tv01= helper.getView(R.id.content_tv01);
        helper.setOnClickListener(R.id.noagree, this);
        helper.setOnClickListener(R.id.agree, this);
        helper.setOnClickListener(R.id.content_tv, this);
        content_tv01.setText(R.string.str_user_protol);
        SpannableStringBuilder style=new SpannableStringBuilder(context.getResources().getString(R.string.str_user_protol01));
        style.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra(Variable.webUrl, Variable.protocl_Agreement_User);
                startActivity(intent);
            }
        }, 17, 23, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra(Variable.webUrl, Variable.protocl_Agreement_Secret);
                startActivity(intent);
            }
        }, 24, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        style.setSpan(new NoUnderlineSpan(),17,23, Spanned.SPAN_MARK_MARK);
        style.setSpan(new NoUnderlineSpan(),24,30, Spanned.SPAN_MARK_MARK);
        style.setSpan(new ForegroundColorSpan(Color.parseColor("#FF1944")),17,23, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(new ForegroundColorSpan(Color.parseColor("#FF1944")),24,30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); //设置指定位置textview的背景颜色
        content_tv.setText(style);
        content_tv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_root:
                break;
            case R.id.layout_root_child:
                break;
            case R.id.content_tv:
                break;
            case R.id.noagree:
                mListener.onClick(0);
                dismiss();
                break;
            case R.id.agree:
                mListener.onClick(1);
                dismiss();
                break;
        }
    }

    public  interface OnBtnClickListener {
        void onClick(int pos);
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
