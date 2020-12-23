package com.lxkj.dmhw.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.InvitationActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ApplyFragment_Tourist extends BaseFragment {

    @BindView(R.id.fragment_apply_tourist_btn)
    LinearLayout fragmentApplyTouristBtn;

    public static ApplyFragment_Tourist getInstance() {
        ApplyFragment_Tourist fragment = new ApplyFragment_Tourist();
        return fragment;
    }

    @Override
    public void onData() {

    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apply_tourist, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @OnClick(R.id.fragment_apply_tourist_btn)
    public void onViewClicked() {
        Intent intent = new Intent(getActivity(), InvitationActivity.class);
        intent.putExtra("isCheck", false);
        startActivity(intent);
        isFinish();
    }
}
