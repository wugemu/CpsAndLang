package com.lxkj.dmhw.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.adapter.BrandAdapter;
import com.lxkj.dmhw.bean.BrandList;
import com.lxkj.dmhw.defined.ObserveGridView;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.utils.Utils;

import java.util.ArrayList;

public class RightMenuDialog implements View.OnClickListener, AdapterView.OnItemClickListener {

    private View view;
    private Context context;
    private String range = "";
    private Dialog dialog = null;
    private BrandAdapter adapter;
    private boolean isCheck = false;
    private LinearLayout dialogRightMenuBrand;
    private ImageView dialogRightMenuArrow;
    private EditText
            dialogRightMenuLow,
            dialogRightMenuHigh;

    public RightMenuDialog(Context context) {
        init(context);
        this.context = context;
    }

    private void init(Context context) {
        view = LinearLayout.inflate(context, R.layout.dialog_right_menu, null);
        ObserveGridView dialogRightMenuGrid = findView(R.id.dialog_right_menu_grid);
        dialogRightMenuBrand = findView(R.id.dialog_right_menu_brand);
        dialogRightMenuArrow = findView(R.id.dialog_right_menu_arrow);
        dialogRightMenuLow = findView(R.id.dialog_right_menu_low);
        dialogRightMenuHigh = findView(R.id.dialog_right_menu_high);
        findView(R.id.dialog_right_menu_cancel).setOnClickListener(this);
        findView(R.id.dialog_right_menu_reset).setOnClickListener(this);
        findView(R.id.dialog_right_menu_confirm).setOnClickListener(this);
        adapter = new BrandAdapter(context);
        dialogRightMenuGrid.setAdapter(adapter);
        // 监听item点击事件
        dialogRightMenuGrid.setOnItemClickListener(this);
        dialog = new Dialog(context, R.style.myTransparent);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        // 设置状态栏透明,5.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //设置状态栏
            View decorView = dialog.getWindow().getDecorView();
            int option, color;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                color = Color.TRANSPARENT;
                option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                color = Color.parseColor("#50000000");
                option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            }
            decorView.setSystemUiVisibility(option);
            dialog.getWindow().setStatusBarColor(color);
        }
    }

    @SuppressWarnings("unchecked")
    private  <T extends View> T findView(int id) {
        return (T) view.findViewById(id);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_right_menu_btn:// 折叠品牌列表
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) dialogRightMenuBrand.getLayoutParams();
                if (isCheck) {
                    isCheck = false;
                    dialogRightMenuArrow.setRotation(0);
                    params.height = Utils.dipToPixel(R.dimen.dp_378);
                } else {
                    isCheck = true;
                    dialogRightMenuArrow.setRotation(180);
                    params.height = LinearLayout.LayoutParams.MATCH_PARENT;
                }
                dialogRightMenuBrand.setLayoutParams(params);
                break;
            case R.id.dialog_right_menu_cancel:// 取消
                dialog.dismiss();
                break;
            case R.id.dialog_right_menu_reset:// 重置
                setCancel();
                InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("RightMenuDialog"), ":", 0);
                break;
            case R.id.dialog_right_menu_confirm:// 确定
                dialog.dismiss();
                if (dialogRightMenuLow.getText().toString().equals("") && dialogRightMenuHigh.getText().toString().equals("")) {
                    range = "";
                } else {
                    if (dialogRightMenuLow.getText().toString().equals("")) {
                        range = "0-" + dialogRightMenuHigh.getText().toString();
                    } else if (dialogRightMenuHigh.getText().toString().equals("")) {
                        range = dialogRightMenuLow.getText().toString() + "-20180118";
                    } else {
                        if (Integer.parseInt(dialogRightMenuLow.getText().toString()) > Integer.parseInt(dialogRightMenuHigh.getText().toString())) {
                            range = dialogRightMenuHigh.getText().toString() + "-" + dialogRightMenuLow.getText().toString();
                        } else {
                            range = dialogRightMenuLow.getText().toString() + "-" + dialogRightMenuHigh.getText().toString();
                        }
                    }
                }
                StringBuilder urlBuilder = new StringBuilder();
                for (BrandList item : adapter.getData()) {
                    if (item.isCheck()) {
                        urlBuilder.append(item.getId()).append(",");
                    }
                }
                InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("RightMenuDialog"), (urlBuilder.toString().equals("") ? "" : urlBuilder.toString().substring(0, urlBuilder.toString().length() - 1)) + ":" + range, 0);
                break;
        }
    }

    public void showDialog(ArrayList<BrandList> brandList, String range) {
        dialog.show();
        this.range = range;
        adapter.setData(brandList);
        adapter.notifyDataSetChanged();
        if (brandList.size() > 21 && brandList.size() <= 24) {
            dialogRightMenuArrow.setVisibility(View.GONE);
        } else if (brandList.size() > 18 && brandList.size() <= 21) {
            dialogRightMenuArrow.setVisibility(View.GONE);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) dialogRightMenuBrand.getLayoutParams();
            params.height = Utils.dipToPixel(R.dimen.dp_330);
            dialogRightMenuBrand.setLayoutParams(params);
        } else if (brandList.size() > 15 && brandList.size() <= 18) {
            dialogRightMenuArrow.setVisibility(View.GONE);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) dialogRightMenuBrand.getLayoutParams();
            params.height = Utils.dipToPixel(R.dimen.dp_282);
            dialogRightMenuBrand.setLayoutParams(params);
        } else if (brandList.size() > 12 && brandList.size() <= 15) {
            dialogRightMenuArrow.setVisibility(View.GONE);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) dialogRightMenuBrand.getLayoutParams();
            params.height = Utils.dipToPixel(R.dimen.dp_234);
            dialogRightMenuBrand.setLayoutParams(params);
        } else if (brandList.size() > 9 && brandList.size() <= 12) {
            dialogRightMenuArrow.setVisibility(View.GONE);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) dialogRightMenuBrand.getLayoutParams();
            params.height = Utils.dipToPixel(R.dimen.dp_186);
            dialogRightMenuBrand.setLayoutParams(params);
        } else if (brandList.size() > 6 && brandList.size() <= 9){
            dialogRightMenuArrow.setVisibility(View.GONE);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) dialogRightMenuBrand.getLayoutParams();
            params.height = Utils.dipToPixel(R.dimen.dp_138);
            dialogRightMenuBrand.setLayoutParams(params);
        } else if (brandList.size() > 3 && brandList.size() <= 6) {
            dialogRightMenuArrow.setVisibility(View.GONE);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) dialogRightMenuBrand.getLayoutParams();
            params.height = Utils.dipToPixel(R.dimen.dp_90);
            dialogRightMenuBrand.setLayoutParams(params);
        } else if (brandList.size() > 0 && brandList.size() <= 3){
            dialogRightMenuArrow.setVisibility(View.GONE);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) dialogRightMenuBrand.getLayoutParams();
            params.height = Utils.dipToPixel(R.dimen.dp_42);
            dialogRightMenuBrand.setLayoutParams(params);
        } else {
            dialogRightMenuArrow.setVisibility(View.VISIBLE);
            findView(R.id.dialog_right_menu_btn).setOnClickListener(this);
        }
        if (range.equals("")) {
            dialogRightMenuLow.setText("");
            dialogRightMenuHigh.setText("");
        } else {
            dialogRightMenuLow.setText(range.substring(0, range.indexOf("-")));
            String high = range.substring(range.indexOf("-") + 1);
            if (high.equals("20180118")) {
                dialogRightMenuHigh.setText("");
            } else {
                dialogRightMenuHigh.setText(high);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        adapter.setSelect(position);
        StringBuilder urlBuilder = new StringBuilder();
        for (BrandList item : adapter.getData()) {
            if (item.isCheck()) {
                urlBuilder.append(item.getId()).append(",");
            }
        }
        InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("RightMenuDialog"), (urlBuilder.toString().equals("") ? "" : urlBuilder.toString().substring(0, urlBuilder.toString().length() - 1)) + ":" + range, 0);
    }

    private void setCancel() {
        for (BrandList item : adapter.getData()) {
            item.setCheck(false);
        }
        adapter.notifyDataSetChanged();
        dialogRightMenuLow.setText("");
        dialogRightMenuHigh.setText("");
    }
}
