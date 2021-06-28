package com.lxkj.dmhw.activity;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.test.andlang.andlangutil.BaseLangActivity;
import com.lxkj.dmhw.R;

import java.util.Observable;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 电子协议列表
 */
public class ElectronicProtocolActivity extends BaseLangActivity {

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.iv_dsh)
    ImageView ivDsh;
    @BindView(R.id.tv_dsh)
    TextView tvDsh;
    @BindView(R.id.iv_shtg)
    ImageView ivShtg;
    @BindView(R.id.tv_shtg)
    TextView tvShtg;
    @BindView(R.id.iv_shbh)
    ImageView ivShbh;
    @BindView(R.id.tv_shbh)
    TextView tvShbh;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.iv_no_data)
    ImageView ivNoData;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;
    @BindView(R.id.rl_nodata)
    RelativeLayout rlNodata;
    private int protecolType;//0=甲方 1=乙方
    private int shType;//0=待审核 1=审核通过 2=审核驳回
    @Override
    public int getLayoutId() {
        return R.layout.activity_electronic_protocol;
    }

    @Override
    public void initView() {
        initTitleBar(true, "我的电子协议", R.mipmap.protocol_sign, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //签订协议

            }
        });
        setStatusBar(1,R.color.black);
        setTitleColor(getResources().getColor(R.color.colorWhite));
        setTitleBackground(R.color.black);
        setWhiteBackIcon();
        protecolType=getIntent().getIntExtra("protecolType",0);
        shType=getIntent().getIntExtra("shType",0);
    }

    private void refershData(){
        if (protecolType==0){
            ivLeft.setImageResource(R.mipmap.protocol_left_click);
            ivRight.setImageResource(R.mipmap.protocol_right_unclick);


        }else{
            ivLeft.setImageResource(R.mipmap.protocol_left_unclick);
            ivRight.setImageResource(R.mipmap.protocol_right_click);
        }

        if (shType==0){
            tvDsh.setTextColor(getResources().getColor(R.color.black));
            tvShtg.setTextColor(getResources().getColor(R.color.colorBlackText2));
            tvShbh.setTextColor(getResources().getColor(R.color.colorBlackText2));
        }else if (shType==1){
            tvDsh.setTextColor(getResources().getColor(R.color.colorBlackText2));
            tvShtg.setTextColor(getResources().getColor(R.color.black));
            tvShbh.setTextColor(getResources().getColor(R.color.colorBlackText2));
        }else{
            tvDsh.setTextColor(getResources().getColor(R.color.colorBlackText2));
            tvShtg.setTextColor(getResources().getColor(R.color.colorBlackText2));
            tvShbh.setTextColor(getResources().getColor(R.color.black));
        }



    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void update(Observable observable, Object o) {

    }



    @OnClick({R.id.iv_left, R.id.iv_right, R.id.ll_dsh, R.id.ll_shtg, R.id.ll_shbh})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                break;
            case R.id.iv_right:
                break;
            case R.id.ll_dsh:
                break;
            case R.id.ll_shtg:
                break;
            case R.id.ll_shbh:
                break;
        }
    }
}