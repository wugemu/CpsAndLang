package com.lxkj.dmhw.activity.self;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.test.andlang.andlangutil.BaseLangActivity;
import com.example.test.andlang.util.ActivityUtil;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.adapter.self.AuthListAdapter;
import com.lxkj.dmhw.bean.self.UserAccount;
import com.lxkj.dmhw.model.MineModel;
import com.lxkj.dmhw.presenter.MinePresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import butterknife.BindView;
import butterknife.OnItemClick;

public class AuthListActivity extends BaseLangActivity<MinePresenter> {
    private final int REQ_EDITAUTH=100;
    @BindView(R.id.lv_auth_list)
    ListView lv_auth_list;

    @BindView(R.id.rl_nodata)
    RelativeLayout rl_nodata;
    @BindView(R.id.iv_no_data)
    ImageView iv_no_data;
    @BindView(R.id.tv_no_data)
    TextView tv_no_data;

    private AuthListAdapter adapter;
    private List<UserAccount> authBeanList=new ArrayList<UserAccount>();
    private boolean selectAuth;//是否是选择实名认证

    @Override
    public int getLayoutId() {
        return R.layout.activity_auth_list;
    }

    @Override
    public void initView() {
        initLoading();
        initTitleBar(true, "购物实名认证", "添加", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AuthListActivity.this,AuthActivity.class);
                if(authBeanList.size()==0){
                    intent.putExtra("isFirst",true);
                }
                ActivityUtil.getInstance().startResult(AuthListActivity.this,intent,REQ_EDITAUTH);
            }
        });

        tv_no_data.setVisibility(View.VISIBLE);
        tv_no_data.setText("您还没有购物实名认证噢～");
        iv_no_data.setImageResource(R.mipmap.default_empty);
    }

    @Override
    public void initPresenter() {
        presenter=new MinePresenter(AuthListActivity.this, MineModel.class);
    }

    @Override
    public void initData() {
        Intent intent=getIntent();
        if(intent!=null){
            selectAuth=intent.getBooleanExtra("selectAuth",false);
        }
        showWaitDialog();
        presenter.reqAuthList(true);//V1.0.8 购物实名认证中要显示账户实名认证 都不传入参
    }

    @OnItemClick(R.id.lv_auth_list)
    public void clickItem(int position){
        UserAccount userAccount=adapter.getItem(position);
        if(!selectAuth) {
            Intent intent = new Intent(AuthListActivity.this, AuthActivity.class);
            if (userAccount.isIfFirst()) {
                intent.putExtra("isFirst", true);//第一个 标题区分
                intent.putExtra("isNoEdit", true);//设置为不可修改
            }
            Bundle bundle = new Bundle();
            bundle.putSerializable("userAccount", userAccount);
            intent.putExtras(bundle);
            ActivityUtil.getInstance().startResult(AuthListActivity.this, intent, REQ_EDITAUTH);
        }else {
            userAccount.setCardNo(userAccount.getCardNo().substring(0,4)+"**********"+userAccount.getCardNo().substring(userAccount.getCardNo().length()-4));
            Intent intent=new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("userAccount", userAccount);
            intent.putExtras(bundle);
            ActivityUtil.getInstance().exitResult(AuthListActivity.this,intent,RESULT_OK);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if("reqAuthList".equals(arg)){
            authBeanList.clear();
            if(presenter.model.getAuthList()!=null){
                authBeanList.addAll(presenter.model.getAuthList());
                if(adapter==null){
                    adapter=new AuthListAdapter(AuthListActivity.this,authBeanList,presenter);
                    lv_auth_list.setAdapter(adapter);
                }else {
                    adapter.notifyDataSetChanged();
                }
            }

            if (authBeanList.size() > 0) {
                rl_nodata.setVisibility(View.GONE);
            } else {
                rl_nodata.setVisibility(View.VISIBLE);
            }
        }else if("reqEditAuth".equals(arg)){
            presenter.reqAuthList(true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK){
            if(requestCode==REQ_EDITAUTH){
                //修改成功回调
                presenter.reqAuthList(true);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void refreshNet() {
        super.refreshNet();
        showWaitDialog();
        presenter.reqAuthList(true);
    }
}