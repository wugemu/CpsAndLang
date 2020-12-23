package com.lxkj.dmhw.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.util.Util;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.MainActivity;
import com.lxkj.dmhw.data.OtherOpen;

import com.lxkj.dmhw.utils.Utils;
import com.lxkj.dmhw.view.ScaleLayout;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 引导页
 */

public class GuideFragment extends BaseFragment {

    @BindView(R.id.fragment_guide_image)
    ImageView fragmentGuideImage;
    @BindView(R.id.guide_top_image)
    ImageView  guide_top_image;
    @BindView(R.id.guide_split_view)
    View  guide_split_view;
    @BindView(R.id.guide_layout)
    ScaleLayout guide_layout;

    @BindView(R.id.guide_bottom_image)
    ImageView guide_bottom_image;
    @BindView(R.id.guide_bg_img)
    ImageView guide_bg_img;



    private int number;

    public static GuideFragment getInstance(int number) {
        GuideFragment fragment = new GuideFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("number", number);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            number = bundle.getInt("number");
        }
    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide, null);
        ButterKnife.bind(this, view);
        //判断屏幕纵横比 如果大于1.86就是长屏，顶部显示，避免底部留白过大
        float ratio_float=(float)height/(float)width;
        if (ratio_float>=1.86){
            guide_split_view.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams linearParams =(RelativeLayout.LayoutParams) guide_layout.getLayoutParams();
            linearParams.setMargins(0,0,0, Utils.dipToPixel(R.dimen.dp_25));
            guide_layout.setLayoutParams(linearParams);
        }else{
            guide_split_view.setVisibility(View.GONE);
            RelativeLayout.LayoutParams linearParams =(RelativeLayout.LayoutParams) guide_layout.getLayoutParams();
            linearParams.setMargins(0,0,0, Utils.dipToPixel(R.dimen.dp_15));
            guide_layout.setLayoutParams(linearParams);
        }
        return view;
    }

    @Override
    public void onEvent() {
        switch (number) {
            case 1:
                Utils.displayImage(getActivity(), R.mipmap.guide_top_one, guide_top_image);
                Utils.displayImage(getActivity(), R.mipmap.guide_one, fragmentGuideImage);
                Utils.displayImage(getActivity(), R.mipmap.guide_bottom_one, guide_bottom_image);
                Utils.displayImage(getActivity(), R.mipmap.guide_bg_one, guide_bg_img);
                break;
            case 2:
                Utils.displayImage(getActivity(), R.mipmap.guide_top_two, guide_top_image);
                Utils.displayImage(getActivity(), R.mipmap.guide_two, fragmentGuideImage);
                Utils.displayImage(getActivity(), R.mipmap.guide_bottom_two, guide_bottom_image);
                Utils.displayImage(getActivity(), R.mipmap.guide_bg_two, guide_bg_img);
                break;
            case 3:
                Utils.displayImage(getActivity(), R.mipmap.guide_top_three, guide_top_image);
                Utils.displayImage(getActivity(), R.mipmap.guide_three, fragmentGuideImage);
                Utils.displayImage(getActivity(), R.mipmap.guide_bottom_three, guide_bottom_image);
                Utils.displayImage(getActivity(), R.mipmap.guide_bg_three, guide_bg_img);
                break;
            case 4:
                Utils.displayImage(getActivity(), R.mipmap.guide_top_four, guide_top_image);
                Utils.displayImage(getActivity(), R.mipmap.guide_four, fragmentGuideImage);
                Utils.displayImage(getActivity(), R.mipmap.guide_bottom_four, guide_bottom_image);
                Utils.displayImage(getActivity(), R.mipmap.guide_bg_four, guide_bg_img);
                break;
        }
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

    @OnClick({R.id.guide_parent_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.guide_parent_layout:
                if (number==4){
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    OtherOpen open2 = new OtherOpen();
                    open2.updateStatus("1");
                    isFinish();
                }
                break;
        }
    }
}
