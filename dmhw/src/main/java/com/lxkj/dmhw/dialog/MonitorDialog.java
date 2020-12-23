package com.lxkj.dmhw.dialog;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.lxkj.dmhw.MyApplication;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.activity.MorePlSearchListActivity;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.SimpleSearchDialog;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;

import java.util.HashMap;

/**
 * 检测到有商品
 * Created by Android on 2018/5/3.
 */

public class MonitorDialog extends SimpleSearchDialog<String> {

    private TextView dialogMonitorTitle;
    public HashMap<String, String> paramMap = new HashMap<>();
    /**
     * 初始化ord
     * @param context  上下文
     * @param data     数据
     */
    public MonitorDialog(Context context, String data) {
        super(context, R.layout.dialog_monitor, data, true, true);
    }

    @Override
    protected void convert(ViewHolder helper) {
        dialogMonitorTitle = helper.getView(R.id.dialog_monitor_title);
        helper.setOnClickListener(R.id.dialog_monitor_tao, this);
        helper.setOnClickListener(R.id.dialog_monitor_jd, this);
        helper.setOnClickListener(R.id.dialog_monitor_pdd, this);
        helper.setOnClickListener(R.id.dialog_monitor_wph, this);
        helper.setOnClickListener(R.id.dialog_monitor_close, this);
        helper.setOnClickListener(R.id.lyaout000, this);
        helper.setOnClickListener(R.id.lyaout001, this);

    }

    @Override
    public void onClick(View v) {
        Intent intent=null;
        switch (v.getId()) {
            case R.id.dialog_monitor_tao:
                intent = new Intent(context, MorePlSearchListActivity.class);
                intent.putExtra("paltform","0");
                ClearClib();
                break;
            case R.id.dialog_monitor_jd:
                intent = new Intent(context, MorePlSearchListActivity.class);
                intent.putExtra("paltform","2");
                ClearClib();
                break;
            case R.id.dialog_monitor_pdd:
                intent = new Intent(context, MorePlSearchListActivity.class);
                intent.putExtra("paltform","1");
                ClearClib();
                break;
            case R.id.dialog_monitor_wph:
                intent = new Intent(context, MorePlSearchListActivity.class);
                intent.putExtra("paltform","3");
                ClearClib();
                break;
            case R.id.dialog_monitor_close:
                hideDialog();
                Variable.AdvertiseShowStatus=true;
                InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ShowAdvertisement"), false, 0);
                break;
            case R.id.lyaout000:
                break;
            case R.id.lyaout001:
                break;
        }
      if (intent!=null){
          hideDialog();
          intent.putExtra("isTask",true);
          intent.putExtra("search", dialogMonitorTitle.getText().toString());
          startActivity(intent);
          Variable.AdvertiseShowStatus=true;
          InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ShowAdvertisement"), false, 0);
      }

    }

    /**
     * 显示弹窗
     */
    public void showDialog(String content) {
        if(content==null||content.length()==0){
            return;
        }
        DateStorage.setClip(content);
        if (isDialog()) {
            dialogMonitorTitle.setText(content);
        } else {
            dialogMonitorTitle.setText(content);
            showDialog();
        }
    }

    //清空剪切板
   private void ClearClib(){
        try{
            ClipboardManager manager = (ClipboardManager) MyApplication.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            assert manager != null;
            manager.setPrimaryClip(ClipData.newPlainText(null, ""));
            DateStorage.setClip("");
        }catch (Exception e){

        }
   }

}
