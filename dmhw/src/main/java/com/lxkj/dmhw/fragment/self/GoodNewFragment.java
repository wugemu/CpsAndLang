package com.lxkj.dmhw.fragment.self;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.self.HomeZcBean;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 */
public class GoodNewFragment extends BaseFragment {
    @BindView(R.id.iv_goodnew_top)
    ImageView iv_goodnew_top;
    @BindView(R.id.tv_goodnew_title)
    TextView tv_goodnew_title;
    @BindView(R.id.tv_goodnew_dec)
    TextView tv_goodnew_dec;

    private HomeZcBean homeZcBean;
    public static GoodNewFragment getInstance(HomeZcBean jsonArray) {
        GoodNewFragment fragment = new GoodNewFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("HomeZcBean", jsonArray);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            homeZcBean = (HomeZcBean)bundle.getSerializable("HomeZcBean");
        }
    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_good_new, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {

    }

    @Override
    public void onCustomized(){
        if(homeZcBean!=null) {
            Utils.displayImage(getActivity(), homeZcBean.getImgUrl(), iv_goodnew_top);
            tv_goodnew_title.setText(homeZcBean.getName());
            tv_goodnew_dec.setText(homeZcBean.getDescription());
        }
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
}