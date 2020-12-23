package com.lxkj.dmhw.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


import com.lxkj.dmhw.R;
import com.lxkj.dmhw.adapter.self.order.DownProductAdapter;
import com.lxkj.dmhw.bean.self.GoodBuyStateBean;
import com.lxkj.dmhw.myinterface.ConfirmOKI;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 1 on 2017/3/22.
 */

public class OrderDownProductDialog {

    @BindView(R.id.dialog_cancel_btn)
    Button dialogCancelBtn;
    @BindView(R.id.dialog_ok_btn)
    Button dialogOkBtn;
    private Activity context;
    private Dialog overdialog;
    @BindView(R.id.id_recyclerview)
    RecyclerView recyclerView;
    private List<GoodBuyStateBean> tradeGoodsList;
    private DownProductAdapter adapter;
    private ConfirmOKI confirmOKI;
    public OrderDownProductDialog(Activity context, List<GoodBuyStateBean> tradeGoodsList, ConfirmOKI confirmOKI) {
        this.context = context;
        this.tradeGoodsList=tradeGoodsList;
        this.confirmOKI=confirmOKI;
        initView();

    }

    private void initView() {
        View view = View.inflate(context, R.layout.dialog_order_down_product, null);
        ButterKnife.bind(this, view);
        overdialog = new Dialog(context, R.style.dialog_lhp);
        adapter=new DownProductAdapter(context,tradeGoodsList);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        overdialog.setContentView(view);
        overdialog.show();
        Window win = overdialog.getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        Display d = win.getWindowManager().getDefaultDisplay(); // 获取屏幕宽、高用
        lp.width = (int) (d.getWidth() * 0.85); // 宽度设置为屏幕的0.85
        win.getDecorView().setPadding(0, 0, 0, 0);
        win.setAttributes(lp);
    }

    @OnClick(R.id.dialog_ok_btn)
    public void ok() {
        if(confirmOKI!=null){
            confirmOKI.executeOk();
        }
        cancelDialog();
    }

    @OnClick(R.id.dialog_cancel_btn)
    public void cancel() {
        if(confirmOKI!=null){
            confirmOKI.executeCancel();
        }
        cancelDialog();

    }

    public void cancelDialog() {
        if (overdialog != null) {
            overdialog.dismiss();
        }
    }
}
