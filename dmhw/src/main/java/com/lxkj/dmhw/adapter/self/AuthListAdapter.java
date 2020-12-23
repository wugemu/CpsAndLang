package com.lxkj.dmhw.adapter.self;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test.andlang.andlangutil.BaseLangAdapter;
import com.example.test.andlang.andlangutil.BaseLangViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.self.UserAccount;
import com.lxkj.dmhw.myinterface.ConfirmOKI;
import com.lxkj.dmhw.presenter.MinePresenter;
import com.lxkj.dmhw.utils.BBCUtil;
import com.lxkj.dmhw.widget.dialog.ConfirmDialog;

import java.util.List;

public class AuthListAdapter extends BaseLangAdapter<UserAccount> {
    private MinePresenter presenter;
    public AuthListAdapter(Activity context, List<UserAccount> data,MinePresenter presenter) {
        super(context, R.layout.list_auth_item,data);
        this.presenter=presenter;
    }

    @Override
    public void convert(BaseLangViewHolder helper, int postion, final UserAccount item) {
        TextView tv_name_phone=helper.getView(R.id.tv_name_phone);
        TextView tv_first_flag=helper.getView(R.id.tv_first_flag);
        TextView tv_sfcard=helper.getView(R.id.tv_sfcard);
        ImageView iv_check=helper.getView(R.id.iv_check);
        TextView tv_check=helper.getView(R.id.tv_check);
        LinearLayout ll_delete=helper.getView(R.id.ll_delete);
        LinearLayout ll_def_address=helper.getView(R.id.ll_def_address);

        tv_name_phone.setText(item.getRealName());
        if(item.isIfFirst()){
            ll_delete.setVisibility(View.GONE);
            tv_first_flag.setVisibility(View.GONE);//V1.0.8隐藏已认证标签
        }else {
            ll_delete.setVisibility(View.VISIBLE);
            tv_first_flag.setVisibility(View.GONE);
        }
        if(!BBCUtil.isEmpty(item.getCardNo())&&item.getCardNo().length()>4){
            tv_sfcard.setText("身份证  "+item.getCardNo().substring(0,3)+"**********"+item.getCardNo().substring(item.getCardNo().length()-4));
        }
        if(item.isIfFlag()){
            iv_check.setImageResource(R.mipmap.black_checked);
            tv_check.setText("默认实名信息");
        }else {
            iv_check.setImageResource(R.mipmap.black_uncheck);
            tv_check.setText("设为默认");
        }

        ll_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除
                ConfirmDialog confirmDialog=new ConfirmDialog(context, "确定删除该实名信息吗？", "确认", "取消", new ConfirmOKI() {
                    @Override
                    public void executeOk() {
                        if(presenter!=null){
                            presenter.reqEditAuth("",item.getId(),1);
                        }
                    }

                    @Override
                    public void executeCancel() {

                    }
                });
            }
        });

        ll_def_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置为默认
                if(presenter!=null){
                    presenter.reqEditAuth("",item.getId(),2);
                }
            }
        });
    }
}
