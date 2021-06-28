package com.lxkj.dmhw.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.AccountDeleteActivity;
import com.lxkj.dmhw.adapter.AccountDelReasonAdapter;
import com.lxkj.dmhw.bean.AccountDel;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.utils.MyLayoutManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 账户注销第二页
 */

public class AccountDelTwo extends BaseFragment {

    @BindView(R.id.reason_recycler)
    RecyclerView reason_recycler;
    private AccountDelReasonAdapter reasonAdapter;
    @BindView(R.id.phone_edit_text)
    EditText phone_edit_text;
    @BindView(R.id.next02)
    LinearLayout next02;
    @BindView(R.id.title)
    TextView title;
    //选项原因
    public static String reason="";
    //选中其他 文本框原因
    public static String otherreason="";
    public static AccountDelTwo getInstance() {
        AccountDelTwo fragment = new AccountDelTwo();
        return fragment;
    }

    @Override
    public void onData() {

    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.accout_del_two, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {
        AccountDeleteActivity accountDeleteActivity= (AccountDeleteActivity)getActivity();
        accountDeleteActivity.setDel_title("注销原因");
        next02.setEnabled(false);
        reason_recycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), false));
        reasonAdapter = new AccountDelReasonAdapter();
        reason_recycler.setAdapter(reasonAdapter);
        reasonAdapter.setNewData(AccountDelOne.accountDel.getReasonList());
        reasonAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                next02.setEnabled(true);
                title.setTextColor(Color.WHITE);
                next02.setBackgroundResource(R.drawable.accountdel_btn_style);
                AccountDel.reasonData data = (AccountDel.reasonData)adapter.getData().get(position);
                reason=data.getName();
                reasonAdapter.reasonCheck(position);
               if (data.getName().equals("其他")){
                   phone_edit_text.setVisibility(View.VISIBLE);
               }else{
                   phone_edit_text.setVisibility(View.GONE);
               }
            }
        });
    }

    @Override
    public void onCustomized() {

    }

    @Override
    public void mainMessage(Message message) {
    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
    }

    @OnClick(R.id.next02)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.next02:
                //切换下一步
                otherreason=phone_edit_text.getText().toString();
                AccountDeleteActivity accountDeleteActivity = (AccountDeleteActivity) getActivity();
                accountDeleteActivity.switchFragment(AccountDelThree.getInstance());
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
