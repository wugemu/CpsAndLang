package com.lxkj.dmhw.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextPaint;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.adapter.BrandDetailListAdapter;
import com.lxkj.dmhw.bean.BigBrandList;
import com.lxkj.dmhw.bean.CommodityDetails290;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.MyLayoutManager;
import com.lxkj.dmhw.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class BigBrandDetailActivity extends BaseActivity implements PtrHandler, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener  {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.brand_detail_list)
    RecyclerView brand_detail_list;
    //下拉刷新
    @BindView(R.id.load_more_ptr_frame)
    PtrClassicFrameLayout loadMorePtrFrame;


    @BindView(R.id.bigbrand_detail_image)
    ImageView bigbrand_detail_image;
    @BindView(R.id.brand_name)
    TextView brand_name;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.brand_desc)
    TextView brand_desc;
    BigBrandList bigBrandList;
    TextPaint tp;
    private BrandDetailListAdapter adapter;

    ArrayList<CommodityDetails290> goodsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bigbrand_detail);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }

        brand_detail_list.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(this, false));
        adapter = new BrandDetailListAdapter(this);
        brand_detail_list.setAdapter(adapter);
        // 设置预加载位置,倒数
        adapter.setPreLoadNumber(5);
        // 监听item点击事件
        adapter.setOnItemClickListener(this);
        // 监听加载更多事件
        adapter.setOnLoadMoreListener(this, brand_detail_list);
        // 取消第一次加载回调
        adapter.disableLoadMoreIfNotFullPage();
        brand_desc.setMovementMethod(ScrollingMovementMethod.getInstance());

        tp= brand_name.getPaint();
        tp.setFakeBoldText(true);
        tp= title.getPaint();
        tp.setFakeBoldText(true);

        ptrFrameLayout();
        showDialog();
        httpPost();
    }

    @Override
    public void mainMessage(Message message) {
        //登录之后刷新页面 该页面标记成可刷新
        if (message.what == LogicActions.LoginStatusSuccess&&(Boolean) message.obj) {
         page=1;
         httpPost();
        }
    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        if (message.what == LogicActions.SingleBrandSuccess) {
            bigBrandList=(BigBrandList)message.obj;
            Utils.displayImage(this, bigBrandList.getLogo(),bigbrand_detail_image);
            brand_name.setText(bigBrandList.getBrandName());
            brand_desc.setText(bigBrandList.getDesc());
            goodsList=bigBrandList.getGoodsList();
            if (goodsList.size()>0){
             if (page>1){
                 adapter.addData(goodsList);
             }else{
                 adapter.setNewData(goodsList);
             }
              adapter.loadMoreComplete();
            }else{
                adapter.loadMoreEnd();
            }
        }
        loadMorePtrFrame.refreshComplete();
        dismissDialog();
    }


    @OnClick(R.id.back)
    public void onViewClicked() {
        isFinish();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        CommodityDetails290 commodityDetails290= (CommodityDetails290)adapter.getData().get(position);
      startActivity(new Intent(this, CommodityActivity290.class).putExtra("shopId", commodityDetails290.getId()).putExtra("source", commodityDetails290.getSource()).putExtra("sourceId", commodityDetails290.getSourceId()));
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        httpPost();

    }

    private void httpPost(){
        paramMap = new HashMap<>();
        paramMap.put("id", getIntent().getStringExtra("id"));
        paramMap.put("startindex", page + "");
        paramMap.put("pagesize", pageSize + "");
        NetworkRequest.getInstance().POST(handler, paramMap, "SingleBrand", HttpCommon.SingleBrand);
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, brand_detail_list, header);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
          page=1;
          httpPost();
    }

    /**
     * 下拉刷新设置
     */
    private void ptrFrameLayout() {
        loadView.setTextColor(Color.WHITE);
        loadMorePtrFrame.setLoadingMinTime(700);
        loadMorePtrFrame.setHeaderView(loadView);
        loadMorePtrFrame.addPtrUIHandler(loadView);
        loadMorePtrFrame.setPtrHandler(this);
    }

}
