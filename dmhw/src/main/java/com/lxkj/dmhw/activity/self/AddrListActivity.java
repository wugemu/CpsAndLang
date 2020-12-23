package com.lxkj.dmhw.activity.self;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.test.andlang.andlangutil.BaseLangActivity;
import com.example.test.andlang.util.ActivityUtil;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.adapter.self.AddrAdapter;
import com.lxkj.dmhw.bean.self.AddrBean;
import com.lxkj.dmhw.model.SettingModel;
import com.lxkj.dmhw.myinterface.ConfirmOKI;
import com.lxkj.dmhw.presenter.SettingPresenter;
import com.lxkj.dmhw.utils.BBCUtil;
import com.lxkj.dmhw.widget.dialog.ConfirmDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import butterknife.BindView;
import butterknife.OnItemClick;

public class AddrListActivity extends BaseLangActivity<SettingPresenter> {

    public static final int REQ_ADDADDR=100;
    @BindView(R.id.lv_addr_list)
    ListView lv_addr_list;
    @BindView(R.id.rl_nodata)
    RelativeLayout rl_nodata;
    @BindView(R.id.iv_no_data)
    ImageView iv_no_data;
    @BindView(R.id.tv_no_data)
    TextView tv_no_data;

    private boolean selectAddr;//是否是选择地址
    private boolean selectOrderAddr;//是否是订单详情修改地址
    private String tradeId;//订单id
    private AddrAdapter adapter;
    private List<AddrBean> addrBeanList=new ArrayList<AddrBean>();
    @Override
    public int getLayoutId() {
        return R.layout.activity_addr_list;
    }

    @Override
    public void initView() {
        initLoading();
        initTitleBar(true, "收货地址", "添加", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddrListActivity.this,AddrDetailActivity.class);
                ActivityUtil.getInstance().startResult(AddrListActivity.this,intent,REQ_ADDADDR);
            }
        });
        iv_no_data.setImageResource(R.mipmap.no_order);
        tv_no_data.setVisibility(View.VISIBLE);
        tv_no_data.setText("您还没有收货地址哦~");
    }

    @Override
    public void initPresenter() {
        presenter=new SettingPresenter(AddrListActivity.this, SettingModel.class);
    }

    @Override
    public void initData() {
        Intent intent=getIntent();
        if(intent!=null){
            selectAddr=intent.getBooleanExtra("selectAddr",false);
            selectOrderAddr=intent.getBooleanExtra("selectOrderAddr",false);
            tradeId=intent.getStringExtra("tradeId");
        }
        showWaitDialog();
        presenter.reqAddrList(tradeId);
    }

    @OnItemClick(R.id.lv_addr_list)
    public void onClickItem(int position){
        final AddrBean addrBean=adapter.getItem(position);
        if(selectAddr) {
            //选择地址
            Intent intent=new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("addrBean", addrBean);
            intent.putExtras(bundle);
            ActivityUtil.getInstance().exitResult(AddrListActivity.this,intent,RESULT_OK);
        }else if(selectOrderAddr){
            //订单修改地址
            String updateAdrStr=addrBean.getUpdateAdrStr();
            if(!BBCUtil.isEmpty(updateAdrStr)) {
                //有特殊说明文案 点击没有反应
                return;
            }
            ConfirmDialog confirmDialog=new ConfirmDialog(AddrListActivity.this, "是否确定变更为此收货地址", "确定", "取消", new ConfirmOKI() {
                @Override
                public void executeOk() {
                    showWaitDialog();
                    presenter.reqUpdateTradeAddress(tradeId,addrBean.getAddress(),addrBean.getProvince(),addrBean.getCity(),addrBean.getArea(),addrBean.getPersonName(),addrBean.getServNum());
                }

                @Override
                public void executeCancel() {

                }
            });
        }else {
            Intent intent = new Intent(AddrListActivity.this, AddrDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("addrBean", addrBean);
            intent.putExtras(bundle);
            ActivityUtil.getInstance().startResult(AddrListActivity.this, intent, REQ_ADDADDR);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if("reqAddrList".equals(arg)){
            addrBeanList.clear();
            if(presenter.model.getAddrBeanList()!=null){
                addrBeanList.addAll(presenter.model.getAddrBeanList());
            }
            if(adapter==null) {
                adapter = new AddrAdapter(AddrListActivity.this, addrBeanList,presenter,selectAddr,selectOrderAddr);
                lv_addr_list.setAdapter(adapter);
            }else {
                adapter.notifyDataSetChanged();
            }
            if(addrBeanList.size()>0){
                rl_nodata.setVisibility(View.GONE);
            }else {
                rl_nodata.setVisibility(View.VISIBLE);
            }
        }else if("reqEditAddress".equals(arg)){
            //修改成功
            presenter.reqAddrList(tradeId);
        }else if("reqDelAddress".equals(arg)){
            //删除成功
            presenter.reqAddrList(tradeId);
        }else if("reqUpdateTradeAddress".equals(arg)){
            //修改订单地址成功
            ActivityUtil.getInstance().exitResult(AddrListActivity.this,null,RESULT_OK);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK){
            if(requestCode==REQ_ADDADDR){
                showWaitDialog();
                presenter.reqAddrList(tradeId);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void refreshNet() {
        super.refreshNet();
        showWaitDialog();
        presenter.reqAddrList(tradeId);
    }
}