package com.lxkj.dmhw.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import com.lxkj.dmhw.R;
import com.lxkj.dmhw.adapter.self.JoinGroupAdapter;
import com.lxkj.dmhw.bean.self.Group;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JoinGroupDialog {

//    @BindView(R.id.lv_groups)
//    ListView lvGroups;

    @BindView(R.id.lv_groups)
    RecyclerView lvGroups;
    private Activity context;
    private Dialog overdialog;
    private List<Group> groupList;
    private JoinGroupAdapter adapter;
    private Handler handler;
    public JoinGroupDialog(Activity context, List<Group> groupList,Handler handler) {
        this.context = context;
        this.handler=handler;
        if (groupList == null) {
            this.groupList = new ArrayList<>();
        } else {
            this.groupList = groupList;
        }
        initView();

    }

    public void setGroupList(List<Group> groupList) {
        if (groupList!=null){
            this.groupList = groupList;
            adapter.setData(groupList);
            adapter.notifyDataSetChanged();
        }
    }

    public boolean isShow(){
        if (overdialog!=null){
            return overdialog.isShowing();
        }
        return false;
    }

    private void initView() {
        View view = View.inflate(context, R.layout.dialog_join_group, null);
        ButterKnife.bind(this, view);
        overdialog = new Dialog(context, R.style.dialog_lhp);
        overdialog.setContentView(view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lvGroups.setLayoutManager(layoutManager);
        adapter=new JoinGroupAdapter(context,groupList,handler);
        lvGroups.setAdapter(adapter);
        showDialog();
    }

    public void settime(){
        if (adapter!=null){
            adapter.settime();
        }
    }

    public void showDialog() {
        if (overdialog != null) {
            overdialog.show();
            Window win = overdialog.getWindow();
            WindowManager m = context.getWindowManager();
            WindowManager.LayoutParams p = win.getAttributes(); // 获取对话框当前的参数值
            Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
            win.getDecorView().setPadding(0, 0, 0, 0);
            p.height = (int) (d.getHeight() * 0.7); // 高度设置为屏幕的0.7
            p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.8
            win.setAttributes(p);
        }
    }

    @OnClick(R.id.iv_close)
    public void cancel() {
        cancelDialog();
    }

    public void cancelDialog() {
        if (overdialog != null) {
            overdialog.dismiss();
        }
    }


}
