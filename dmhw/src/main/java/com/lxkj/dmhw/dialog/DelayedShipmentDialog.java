package com.lxkj.dmhw.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.lxkj.dmhw.R;
import com.lxkj.dmhw.adapter.self.settle.DelayedShipmentAdapter;
import com.lxkj.dmhw.bean.self.GoodBean;
import com.lxkj.dmhw.myinterface.ConfirmOKI;
import com.lxkj.dmhw.utils.BBCUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created By lhp
 * on 2019/10/25
 */
public class DelayedShipmentDialog {
    @BindView(R.id.lv_tip)
    ListView lvTip;
    private Dialog adDialog;
    private Activity activity;
    private List<GoodBean> goods;
    private ConfirmOKI confirmOKI;
    private View overdiaView;
    public DelayedShipmentDialog(Activity activity, List<GoodBean> goods, ConfirmOKI confirmOKI) {
        this.activity = activity;
        this.confirmOKI=confirmOKI;
        this.goods = goods;
        initView();
    }

    private void initView() {
        if (activity.isDestroyed()) {
            return;
        }
        overdiaView = View.inflate(activity, R.layout.dialog_delayed_shipment, null);
        ButterKnife.bind(this, overdiaView);
        adDialog = new Dialog(activity, R.style.dialog_lhp);
        adDialog.setContentView(overdiaView);
        DelayedShipmentAdapter adapter=new DelayedShipmentAdapter(activity,goods);
        lvTip.setAdapter(adapter);
        if (measureListViewHeight(lvTip)>activity.getResources().getDimension(R.dimen.dp_190)){
            lvTip.getLayoutParams().height = (int) activity.getResources().getDimension(R.dimen.dp_190);
        }
        showDialog();


    }

    public int measureListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return 0;
        }
        int totalHeight = 0;
        int listViewWidth = (int) (BBCUtil.getDisplayWidth(activity)*0.75 - activity.getResources().getDimension(R.dimen.dp_40));//listView在布局时的宽度
        int widthSpec = View.MeasureSpec.makeMeasureSpec(listViewWidth, View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
           listItem.measure(widthSpec, 0);

            int itemHeight = listItem.getMeasuredHeight();
            totalHeight += itemHeight;
        }
        // 减掉底部分割线的高度
        return  totalHeight
                + (listView.getDividerHeight() * listAdapter.getCount() - 1);
    }
    private void showDialog() {
        if (!activity.isFinishing())//xActivity即为本界面的Activity
        {
            if (adDialog != null) {
                adDialog.show();
                Window win = adDialog.getWindow();
                WindowManager m = activity.getWindowManager();
                Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
                WindowManager.LayoutParams p = win.getAttributes(); // 获取对话框当前的参数值
                win.getDecorView().setPadding(0, 0, 0, 0);
                p.height = ViewGroup.LayoutParams.WRAP_CONTENT; // 高度设置为屏幕的0.6
                p.width = (int) (d.getWidth()*0.75); // 宽度设置为屏幕的0.65
                win.setAttributes(p);
            }
        }
    }

    @OnClick({R.id.dialog_cancel_btn, R.id.dialog_ok_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dialog_cancel_btn:
                adDialog.dismiss();
                break;
            case R.id.dialog_ok_btn:
                if (confirmOKI!=null){
                    confirmOKI.executeOk();
                    adDialog.dismiss();
                }
                break;
        }
    }
}
