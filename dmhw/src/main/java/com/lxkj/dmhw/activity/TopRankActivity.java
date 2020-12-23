package com.lxkj.dmhw.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.test.andlang.andlangutil.BaseLangActivity;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.adapter.TopRankAdapter;
import com.lxkj.dmhw.widget.banner.ShareCardView;

import java.util.Observable;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * top榜
 */
public class TopRankActivity extends BaseLangActivity {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.iv_banner)
    ImageView ivBanner;
    private TopRankAdapter adapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_top_rank;
    }

    @Override
    public void initView() {
        initLoading();
        initTitleBar(true, "TOP榜", R.mipmap.share, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //分享

            }
        });
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvList.setLayoutManager(linearLayoutManager);

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


}