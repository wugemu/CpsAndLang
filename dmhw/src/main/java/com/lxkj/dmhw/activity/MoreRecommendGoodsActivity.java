package com.lxkj.dmhw.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.LinearLayout;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.adapter.MyAdapter290;
import com.lxkj.dmhw.bean.MainBottomListItem;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.MyItemDecoration;
import com.lxkj.dmhw.utils.MyLayoutManager;
import com.lxkj.dmhw.utils.NetStateUtils;
import com.lxkj.dmhw.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoreRecommendGoodsActivity extends BaseActivity {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;

    @BindView(R.id.commodity_more_recycler)
    RecyclerView commodityShopRecycler;
    //猜你喜欢商品数据
    private ArrayList<MainBottomListItem> list;
    // 推荐商品adapter
    private MyAdapter290 adapter;

    private boolean isCheck = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_recommend);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams);
        }

        // 设置Recycler
        commodityShopRecycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(this, 2));
        commodityShopRecycler.addItemDecoration(new MyItemDecoration(this,Utils.dipToPixel(R.dimen.dp_5),Utils.dipToPixel(R.dimen.dp_10)));
        commodityShopRecycler.setNestedScrollingEnabled(false);//禁止滑动
        // 初始化adapter
        adapter = new MyAdapter290(this,"pjw");
        commodityShopRecycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyAdapter290.OnItemClickListener() {
            @Override
            public void onItemClick(int position, MainBottomListItem data) {
                Intent intent=null;
                switch (getIntent().getStringExtra("platType")) {
                    case "tb":
                    case "tm":
                        startActivity(new Intent(MoreRecommendGoodsActivity.this, CommodityActivity290.class).putExtra("shopId", data.getId()).putExtra("source", data.getSource()).putExtra("sourceId", data.getSourceId()));
                        break;
                    case "pdd":
                        intent=new Intent(MoreRecommendGoodsActivity.this, CommodityActivityPJW.class).putExtra("GoodsId", data.getId()).putExtra("type", "pdd");
                        break;
                    case "jd":
                        intent=new Intent(MoreRecommendGoodsActivity.this, CommodityActivityPJW.class).putExtra("GoodsId", data.getId()).putExtra("type", "jd");
                        break;
                    case "wph":
                        intent=new Intent(MoreRecommendGoodsActivity.this, CommodityActivityPJW.class).putExtra("GoodsId", data.getId()).putExtra("type", "wph");
                        break;
                    case "sn":
                        intent=new Intent(MoreRecommendGoodsActivity.this, CommodityActivityPJW.class).putExtra("GoodsId", data.getId()).putExtra("type", "sn");
                        break;
                    case "kl":
                        intent=new Intent(MoreRecommendGoodsActivity.this, CommodityActivityPJW.class).putExtra("GoodsId", data.getId()).putExtra("type", "kl");
                        break;
                }
                if (intent!=null){
                    startActivity(intent);
                }
            }
        });
        commodityShopRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = commodityShopRecycler.getLayoutManager();
                //判断是当前layoutManager是否为LinearLayoutManager
                // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    //获取最后一个可见view的位置
                    int lastItemPosition = linearManager.findLastVisibleItemPosition();
                    //获取第一个可见view的位置
                    int firstItemPosition = linearManager.findFirstVisibleItemPosition();
                    if(firstItemPosition >0)
                    {
                        if (isCheck) {
                            if (firstItemPosition >= (adapter.getItemCount() -8)) {
                                page++;
                                isCheck = false;
                                httpPost();
                            }
                        }

                    }
                }
            }
        });

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
        if (message.what == LogicActions.ShopLike290Success) {
            list =(ArrayList<MainBottomListItem>) message.obj;
            if (list!=null&&list.size()>0){
                if (page > 1) {
                    adapter.addDatas(list,1);
                } else {
                    adapter.addDatas(list,0);
                }
                isCheck = true;
            }
            else {
                isCheck = false;
            }
        }

    }

    @OnClick({R.id.back})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.back:
                isFinish();
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    private void httpPost() {
        // 商品搜索（猜你喜欢）
        if (NetStateUtils.isNetworkConnected(this)) {
            paramMap.clear();
            paramMap.put("pagesize", pageSize + "");
            switch (getIntent().getStringExtra("platType")) {
                case "tb":
                case "tm":
                 paramMap.put("startindex", page + "");
                 paramMap.put("shopid", getIntent().getStringExtra("shopid"));
                 paramMap.put("material",getIntent().getStringExtra("material"));
                 NetworkRequest.getInstance().POST(handler, paramMap, "ShopLike290", HttpCommon.MainBottomList);
                 break;
                case "pdd":
                    paramMap.put("page", page + "");
                    paramMap.put("goodsId", getIntent().getStringExtra("shopid"));
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "ShopLike290", HttpCommon.PDDRecommendByGoodsId);
                    break;
                case "jd":
                    paramMap.put("page", page + "");
                    paramMap.put("goodsId", getIntent().getStringExtra("shopid"));
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "ShopLike290", HttpCommon.JDRecommendByGoodsId);
                    break;
                case "wph":
                    paramMap.put("page", page + "");
                    paramMap.put("goodsId", getIntent().getStringExtra("shopid"));
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "ShopLike290", HttpCommon.WPHRecommendByGoodsId);
                    break;
                case "sn":
                    paramMap.put("page", page + "");
                    paramMap.put("goodsId", getIntent().getStringExtra("shopid"));
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "ShopLike290", HttpCommon.SNRecommendByGoodsId);
                    break;
                case "kl":
                    paramMap.put("page", page + "");
                    paramMap.put("goodsId", getIntent().getStringExtra("shopid"));
                    NetworkRequest.getInstance().GETNew(handler, paramMap, "ShopLike290", HttpCommon.KLRecommendByGoodsId);
                    break;
            }
        }
    }
}
