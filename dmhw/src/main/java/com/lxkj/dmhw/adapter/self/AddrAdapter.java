package com.lxkj.dmhw.adapter.self;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test.andlang.andlangutil.BaseLangAdapter;
import com.example.test.andlang.andlangutil.BaseLangViewHolder;
import com.example.test.andlang.util.ActivityUtil;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.self.AddrDetailActivity;
import com.lxkj.dmhw.activity.self.AddrListActivity;
import com.lxkj.dmhw.bean.self.AddrBean;
import com.lxkj.dmhw.myinterface.ConfirmOKI;
import com.lxkj.dmhw.presenter.SettingPresenter;
import com.lxkj.dmhw.utils.BBCUtil;
import com.lxkj.dmhw.widget.dialog.ConfirmDialog;

import java.util.List;

public class AddrAdapter extends BaseLangAdapter<AddrBean> {
    private SettingPresenter presenter;
    private boolean selectAddr;//是否是选择地址
    private boolean selectOrderAddr;//是否是订单详情修改地址
    public AddrAdapter(Activity context, List<AddrBean> data,SettingPresenter presenter,boolean selectAddr,boolean selectOrderAddr) {
        super(context, R.layout.listview_addr_item, data);
        this.presenter=presenter;
        this.selectAddr=selectAddr;
        this.selectOrderAddr=selectOrderAddr;
    }

    @Override
    public void convert(BaseLangViewHolder helper, int postion, final AddrBean item) {
        TextView tv_name_phone=helper.getView(R.id.tv_name_phone);
        TextView tv_sfcard=helper.getView(R.id.tv_sfcard);
        ImageView iv_check=helper.getView(R.id.iv_check);
        TextView tv_check=helper.getView(R.id.tv_check);
        LinearLayout ll_def_address=helper.getView(R.id.ll_def_address);
        LinearLayout ll_delete=helper.getView(R.id.ll_delete);
        LinearLayout ll_edit=helper.getView(R.id.ll_edit);

        tv_name_phone.setText(item.getPersonName()+"  "+item.getServNum());
        tv_sfcard.setText(item.getProvince()+" "+item.getCity()+" "+item.getArea()+" "+item.getAddress());
        if(item.getFlag()==1){
            iv_check.setImageResource(R.mipmap.black_checked);
            tv_check.setText("默认地址");
        }else {
            iv_check.setImageResource(R.mipmap.black_uncheck);
            tv_check.setText("设为默认");
        }

        ll_def_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置为默认
                presenter.reqEditAddress(item.getId(),item.getPersonName(), item.getServNum(), item.getProvince(), item.getCity(), item.getArea(), item.getAddress(), 1);
            }
        });

        if(selectAddr) {
            //选择地址 编辑
            ll_edit.setVisibility(View.VISIBLE);
            ll_delete.setVisibility(View.GONE);
            ll_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AddrDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("addrBean", item);
                    intent.putExtras(bundle);
                    ActivityUtil.getInstance().startResult(context, intent, AddrListActivity.REQ_ADDADDR);
                }
            });
        }else if(selectOrderAddr){
            //是订单详情修改地址
            String updateAdrStr=item.getUpdateAdrStr();
            if(!BBCUtil.isEmpty(updateAdrStr)) {
                SpannableString spannableString = new SpannableString(updateAdrStr + tv_sfcard.getText().toString());
                spannableString.setSpan(new ForegroundColorSpan(Color.rgb(255, 19, 65)), 0, updateAdrStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tv_sfcard.setText(spannableString);
            }
            ll_edit.setVisibility(View.VISIBLE);
            ll_delete.setVisibility(View.GONE);
            ll_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AddrDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("addrBean", item);
                    intent.putExtras(bundle);
                    ActivityUtil.getInstance().startResult(context, intent, AddrListActivity.REQ_ADDADDR);
                }
            });

        }else {
            //非选择地址 删除
            ll_edit.setVisibility(View.GONE);
            ll_delete.setVisibility(View.VISIBLE);

            ll_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConfirmDialog confirmDialog = new ConfirmDialog(context, "确定删除该地址吗？", "确认", "取消", new ConfirmOKI() {
                        @Override
                        public void executeOk() {
                            //删除地址
                            presenter.reqDelAddress(item.getId());
                        }

                        @Override
                        public void executeCancel() {

                        }
                    });
                }
            });
        }




    }
}
