package com.lxkj.dmhw.fragment;

import android.os.Bundle;
import android.os.Message;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.adapter.LimitTimeAdapter;
import com.lxkj.dmhw.bean.HomePage;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.utils.MyLayoutManager;
import com.lxkj.dmhw.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 首页限时抢购
 */

public class MainLimitBuyFragment extends BaseFragment {

    @BindView(R.id.header_fragment_limittime_recycler)
    RecyclerView header_fragment_limittime_recycler;
    private LimitTimeAdapter adapter;
    private HomePage.LimitTimeList limitTimeList;

    public static MainLimitBuyFragment getInstance(HomePage.LimitTimeList limitTime) {
            MainLimitBuyFragment fragment = new MainLimitBuyFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("limitTime", limitTime);
            fragment.setArguments(bundle);
            return fragment;
    }

    @Override
    public void onData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            try{
                limitTimeList = (HomePage.LimitTimeList) bundle.getSerializable("limitTime");
            }catch (Exception e){

            }
        }
    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.limit_view, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {

        // 设置Recycler
        header_fragment_limittime_recycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), true));
        header_fragment_limittime_recycler.setNestedScrollingEnabled(false);
        // 初始化adapter
        adapter = new LimitTimeAdapter(getActivity(),(width-Utils.dipToPixel(R.dimen.dp_60))/3,limitTimeList.getTime());
        header_fragment_limittime_recycler.setAdapter(adapter);


    }

    @Override
    public void onCustomized() {
        adapter.setNewData(limitTimeList.getLimitTimeChildList());
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
