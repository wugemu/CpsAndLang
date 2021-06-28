package com.lxkj.dmhw.fragment;

import android.os.Bundle;
import android.os.Message;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.adapter.JGQSortAdapter;
import com.lxkj.dmhw.bean.HomePage;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.utils.GridSpacingItemDecoration;
import com.lxkj.dmhw.utils.MyLayoutManager;
import com.lxkj.dmhw.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 新金刚区页面
 */

public class JGQSortFragment extends BaseFragment implements JGQSortAdapter.OnJGQAppIconClickListener  {
    @BindView(R.id.fragment_sort_recycler)
    RecyclerView fragment_sort_recycler;
    JGQSortAdapter jgqSortAdapter;
    private String json="";
    ArrayList<HomePage.JGQAppIcon> list;
    public static JGQSortFragment getInstance(String jsonArray) {
        JGQSortFragment fragment = new JGQSortFragment();
        Bundle bundle = new Bundle();
        bundle.putString("json", jsonArray);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            json = bundle.getString("json");
        }
    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jgq_sort, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {
        jgqSortAdapter=new JGQSortAdapter(getActivity(),width);
        fragment_sort_recycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), 5));
        fragment_sort_recycler.addItemDecoration(new GridSpacingItemDecoration(5,0, false));
        fragment_sort_recycler.setAdapter(jgqSortAdapter);
        list = (ArrayList<HomePage.JGQAppIcon>) JSON.parseArray(json, HomePage.JGQAppIcon.class);
        jgqSortAdapter.setNewData(list);
        jgqSortAdapter.setOnJGQAppIconClickListener(this);
    }

    @Override
    public void onCustomized(){
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

    @Override
    public void onJGQAppIconClick(HomePage.JGQAppIcon item) {
        Utils.doJgqClick(getActivity(), item);
    }
}
