package com.lxkj.dmhw.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.test.andlang.widget.CustomListView;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.adapter.self.settle.TaxListAdapter;
import com.lxkj.dmhw.bean.self.TaxDetailBean;
import com.lxkj.dmhw.myinterface.ConfirmOKI;
import com.lxkj.dmhw.utils.BBCUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TaxListDialog {
    @BindView(R.id.dialog_cancel_btn)
    Button dialogCancelBtn;
    @BindView(R.id.ll_confirm)
    LinearLayout ll_confirm;
    private Activity context;
    private Dialog overdialog;
    private ConfirmOKI oki;
    private String title;
    private String content;
    @BindView(R.id.TextTip)
    TextView tvTitle;
    @BindView(R.id.lv_dialog_tax)
    CustomListView lv_dialog_tax;
    @BindView(R.id.dialog_content)
    TextView dialog_content;

    private TaxListAdapter taxListAdapter;
    private List<TaxDetailBean> taxDetailBeanList;

    public TaxListDialog(Activity context, String title,List<TaxDetailBean> taxDetailBeanList,String content,ConfirmOKI oki) {
        this.context = context;
        this.oki = oki;
        this.title = title;
        this.taxDetailBeanList=taxDetailBeanList;
        this.content=content;
        initView();
    }


    private void initView() {
        View view = View.inflate(context, R.layout.dialog_taxlist, null);
        ButterKnife.bind(this, view);
        overdialog = new Dialog(context, R.style.dialog_lhp);
        if (BBCUtil.isEmpty(title)) {
            title = "税费明细";
        }
        overdialog.setCanceledOnTouchOutside(false);
        tvTitle.setText(title);
        if(!BBCUtil.isEmpty(content)){
            dialog_content.setText(content);
        }
        overdialog.setContentView(view);

        taxListAdapter=new TaxListAdapter(context,taxDetailBeanList);
        lv_dialog_tax.setAdapter(taxListAdapter);
        showDialog();
    }


    public void showDialog() {
        if (overdialog != null) {
            overdialog.show();
            Window win = overdialog.getWindow();
            WindowManager m = context.getWindowManager();
            Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
            WindowManager.LayoutParams p = win.getAttributes(); // 获取对话框当前的参数值
            win.getDecorView().setPadding(0, 0, 0, 0);
            p.height = ViewGroup.LayoutParams.WRAP_CONTENT; // 高度设置为包裹内容
//            p.height = (int) (d.getHeight() * 0.4);
            p.width = (int)(d.getWidth()*0.7); // 宽度设置为屏幕的0.85
            win.setAttributes(p);

        }else {
            overdialog.show();
        }
    }
    @OnClick(R.id.dialog_cancel_btn)
    public void cancel() {
        if (oki != null) {
            oki.executeCancel();
        }
        cancelDialog();

    }

    public void cancelDialog() {
        if (overdialog != null) {
            overdialog.dismiss();
        }
    }

    public void updateData(List<TaxDetailBean> taxDetailBeanList){
        this.taxDetailBeanList=taxDetailBeanList;
        taxListAdapter=new TaxListAdapter(context,this.taxDetailBeanList);
        lv_dialog_tax.setAdapter(taxListAdapter);
    }
}
