package com.lxkj.dmhw.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.activity.OtherActivity370;
import com.lxkj.dmhw.bean.SuperSort;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.OtherActivity;
import com.lxkj.dmhw.adapter.ClassificationAdapter;
import com.lxkj.dmhw.adapter.ClassificationGridAdapter;
import com.lxkj.dmhw.bean.ClassificationOne;
import com.lxkj.dmhw.bean.ClassificationTwo;
import com.lxkj.dmhw.defined.ObserveGridView;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.MyLayoutManager;
import com.lxkj.dmhw.utils.NetStateUtils;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 首页第三页
 * Created by Administrator on 2017/12/12 0012.
 */

public class ThreeFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener, AdapterView.OnItemClickListener {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.fragment_three_search)
    LinearLayout fragmentThreeSearch;
    @BindView(R.id.fragment_three_recycler)
    RecyclerView fragmentThreeRecycler;
    @BindView(R.id.fragment_three_grid_text)
    TextView fragmentThreeGridText;
    @BindView(R.id.fragment_three_grid)
    ObserveGridView fragmentThreeGrid;
    @BindView(R.id.network_mask)
    LinearLayout rl_network_mask;
    @BindView(R.id.content_layout)
    LinearLayout content_layout;
    private String categoryOne = "";
    private ClassificationAdapter adapterOne;
    private ClassificationGridAdapter adapterTwo;
    private ArrayList<SuperSort.dataTotal.Datatwo> listTwo;

    private ArrayList<SuperSort.dataTotal> list;

    SuperSort superSort;

    public static ThreeFragment getInstance() {
        ThreeFragment fragment = new ThreeFragment();
        return fragment;
    }

    @Override
    public void onData() {

    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams();
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        // 设置Recycler
        fragmentThreeRecycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), false));
        adapterOne = new ClassificationAdapter(getActivity());
        fragmentThreeRecycler.setAdapter(adapterOne);
        adapterOne.setOnItemClickListener(this);
        // 设置GridView
        adapterTwo = new ClassificationGridAdapter(getActivity());
        fragmentThreeGrid.setAdapter(adapterTwo);
        fragmentThreeGrid.setOnItemClickListener(this);
        rl_network_mask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCustomized();
            }
        });
    }

    @Override
    public void onCustomized() {
        if (NetStateUtils.isNetworkConnected(getActivity())) {
            showDialog();
                rl_network_mask.setVisibility(View.GONE);
            // 加载一级类目
            paramMap.clear();
            NetworkRequest.getInstance().POST(handler, paramMap, "ClassificationTotal", HttpCommon.ClassificationTotal);
        }else{
            toast(getResources().getString(R.string.net_work_unconnect));
            rl_network_mask.setVisibility(View.VISIBLE);
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
//        if (message.what == LogicActions.ClassificationOneSuccess) {
//            listOne = (ArrayList<ClassificationOne>) message.obj;
//            categoryOne = listOne.get(0).getShopclassone();
//            adapterOne.setNewData(listOne);
//            adapterOne.notifyDataSetChanged();
//            http(0);
//        }
//        if (message.what == LogicActions.ClassificationTwoSuccess) {
//            listTwo = (ArrayList<ClassificationTwo>) message.obj;
//            adapterTwo.setArrayList(listTwo);
//            adapterTwo.notifyDataSetInvalidated();
//        }
        //数据全部返回
        if (message.what == LogicActions.ClassificationTotalSuccess) {
            superSort =(SuperSort)message.obj;
            list=superSort.getSubVoList();
            adapterOne.setNewData(list);
            adapterTwo.setArrayList(superSort.getSubVoList().get(0).getTwoList());
            adapterTwo.notifyDataSetInvalidated();
            listTwo=superSort.getSubVoList().get(0).getTwoList();
        }

        dismissDialog();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        SuperSort.dataTotal item = (SuperSort.dataTotal) adapter.getItem(position);
        if (position==0){
            content_layout.setBackgroundResource(R.drawable.super_sort_item_bg2);
        }else{
            content_layout.setBackgroundResource(R.drawable.personal_grid_bgtop);
        }
        if (!item.isCheck()) {
            adapterOne.setSelectItem(position);
            adapterOne.notifyDataSetChanged();
            adapterTwo.setArrayList(new ArrayList<>());
            adapterTwo.notifyDataSetInvalidated();
            adapterTwo.setArrayList(item.getTwoList());
            listTwo=item.getTwoList();
            categoryOne = item.getId();
        }
    }

    @OnClick(R.id.fragment_three_search)
    public void onViewClicked() {
//        Intent intent = new Intent(getActivity(), SearchActivity.class);
//        intent.putExtra("isCheck", false);
//        startActivity(intent);
    }

//    private void http(int position) {
//        paramMap.clear();
//        paramMap.put("shopclassone", listOne.get(position).getShopclassone());
//        NetworkRequest.getInstance().POST(handler, paramMap, "ClassificationTwo", HttpCommon.ClassificationTwo);
//        fragmentThreeGridText.setText(listOne.get(position).getShopclassname());
//    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), OtherActivity370.class);
        intent.putExtra("CategoryTwo", listTwo.get(position).getId());
        intent.putExtra("title", listTwo.get(position).getName());
        startActivity(intent);
    }
}
