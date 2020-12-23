package com.lxkj.dmhw.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.CommodityActivity290;
import com.lxkj.dmhw.adapter.ShopListRecyclerAdapterNew;
import com.lxkj.dmhw.bean.BrandList;
import com.lxkj.dmhw.bean.CommodityList;
import com.lxkj.dmhw.defined.LazyFragment;
import com.lxkj.dmhw.dialog.RightMenuDialog;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.MyLayoutManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class BrandFragment extends LazyFragment implements PtrHandler, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.fragment_brand_one_text)
    TextView fragmentBrandOneText;
    @BindView(R.id.fragment_brand_one)
    LinearLayout fragmentBrandOne;
    @BindView(R.id.fragment_brand_two_text)
    TextView fragmentBrandTwoText;
    @BindView(R.id.fragment_brand_two)
    LinearLayout fragmentBrandTwo;
    @BindView(R.id.fragment_brand_three_text)
    TextView fragmentBrandThreeText;
    @BindView(R.id.fragment_brand_three)
    LinearLayout fragmentBrandThree;
    @BindView(R.id.fragment_brand_four_text)
    TextView fragmentBrandFourText;
    @BindView(R.id.fragment_brand_four_image)
    ImageView fragmentBrandFourImage;
    @BindView(R.id.fragment_brand_two_image)
    ImageView fragmentBrandTwoImage;
    @BindView(R.id.fragment_brand_three_image)
    ImageView fragmentBrandThreeImage;

    @BindView(R.id.fragment_brand_four)
    LinearLayout fragmentBrandFour;
    @BindView(R.id.fragment_brand_check)
    CheckBox fragmentBrandCheck;
    @BindView(R.id.fragment_brand_screen_layout)
    LinearLayout fragmentBrandScreenLayout;
    @BindView(R.id.fragment_brand_recycler)
    RecyclerView fragmentBrandRecycler;
    @BindView(R.id.load_more_ptr_frame)
    PtrClassicFrameLayout loadMorePtrFrame;
    @BindView(R.id.fragment_brand_check_image)
    ImageView fragmentBrandCheckImage;
    private String id;
    private String sort = "20";
    private int positionTwo = 3;
    private int positionThree = 3;
    private int positionFour = 3;
    // 价格区间
    private String range = "";
    // 品牌
    private String ids = "";
    // 适配器
    private ShopListRecyclerAdapterNew adapter;
    private ArrayList<BrandList> brandList;
    private CommodityList commodityList = new CommodityList();

    public static BrandFragment getInstance(String id) {
        BrandFragment fragment = new BrandFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initData() {
        httpPostShop("");
        paramMap.clear();
        paramMap.put("shopclassone", id);
        NetworkRequest.getInstance().POST(handler, paramMap, "GetBrand", HttpCommon.GetBrand);
    }

    @Override
    public void onData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getString("id");
        }
    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_brand, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {
        fragmentBrandRecycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), false));
        adapter = new ShopListRecyclerAdapterNew(getActivity());
        fragmentBrandRecycler.setAdapter(adapter);
        // 设置预加载位置,倒数
        adapter.setPreLoadNumber(5);
        // 监听item点击事件
        adapter.setOnItemClickListener(this);
        // 监听加载更多事件
        adapter.setOnLoadMoreListener(this, fragmentBrandRecycler);
        // 取消第一次加载回调
        adapter.disableLoadMoreIfNotFullPage();
    }

    @Override
    public void onCustomized() {
        checkBitmap(false);
        ptrFrameLayout();
    }

    @Override
    public void mainMessage(Message message) {
        if (message.what == LogicActions.RightMenuDialogSuccess) {
            String value = (String) message.obj;
            ids = value.substring(0, value.indexOf(":"));
            page = 1;
            range = value.substring(value.indexOf(":") + 1 , value.length());
            httpPostShop("");
        }
    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        loadMorePtrFrame.refreshComplete();
        dismissDialog();
        if (message.what == LogicActions.ShopCategorySuccess) {
            commodityList = (CommodityList) message.obj;
            if (commodityList.getShopdata().size() > 0) {
                if (page > 1) {
                    adapter.addData(commodityList.getShopdata());
//                    adapter.notifyDataSetChanged();//不用再刷新数据了。会造成闪烁
                } else {
                    adapter.setNewData(commodityList.getShopdata());
//                    adapter.notifyDataSetChanged();
                }
                adapter.loadMoreComplete();
            } else {
                adapter.loadMoreEnd();
            }
        }
        if (message.what == LogicActions.GetBrandSuccess) {
            brandList = (ArrayList<BrandList>) message.obj;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @OnClick({R.id.fragment_brand_one, R.id.fragment_brand_two, R.id.fragment_brand_three, R.id.fragment_brand_four, R.id.fragment_brand_check})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_brand_one:// 综合
                positionFour = 3;
                fragmentBrandFourImage.setImageResource(R.mipmap.fragment_team_default);
                fragmentBrandOneText.setTextColor(getResources().getColor(R.color.mainColor));
                fragmentBrandTwoText.setTextColor(0xff666666);
                fragmentBrandThreeText.setTextColor(0xff666666);
                fragmentBrandFourText.setTextColor(0xff666666);
                sort = "20";
                page = 1;
                httpPostShop("");
                break;
            case R.id.fragment_brand_two:// 销量
                positionFour = 3;
                fragmentBrandFourImage.setImageResource(R.mipmap.fragment_team_default);
                fragmentBrandThreeImage.setImageResource(R.mipmap.fragment_team_default);
                fragmentBrandOneText.setTextColor(0xff666666);
                fragmentBrandTwoText.setTextColor(getResources().getColor(R.color.mainColor));
                fragmentBrandThreeText.setTextColor(0xff666666);
                fragmentBrandFourText.setTextColor(0xff666666);
                switch (positionTwo) {
                    case 1:
                        fragmentBrandTwoImage.setImageResource(R.mipmap.fragment_team_bottom);
                        positionTwo = 2;
                        sort = "11";
                        page = 1;
                        httpPostShop("");
                        break;
                    case 2:
                        fragmentBrandTwoImage.setImageResource(R.mipmap.fragment_team_top);
                        positionTwo = 1;
                        sort = "10";
                        page = 1;
                        httpPostShop("");
                        break;
                    case 3:
                        fragmentBrandTwoImage.setImageResource(R.mipmap.fragment_team_bottom);
                        positionTwo = 2;
                        sort = "11";
                        page = 1;
                        httpPostShop("");
                        break;
                }

                break;
            case R.id.fragment_brand_three:// 佣金
                positionFour = 3;
                fragmentBrandFourImage.setImageResource(R.mipmap.fragment_team_default);
                fragmentBrandTwoImage.setImageResource(R.mipmap.fragment_team_default);
                fragmentBrandOneText.setTextColor(0xff666666);
                fragmentBrandTwoText.setTextColor(0xff666666);
                fragmentBrandThreeText.setTextColor(getResources().getColor(R.color.mainColor));
                fragmentBrandFourText.setTextColor(0xff666666);
                switch (positionThree) {
                    case 1:
                        fragmentBrandThreeImage.setImageResource(R.mipmap.fragment_team_top);
                        positionThree = 2;
                        sort = "31";
                        page = 1;
                        httpPostShop("");
                        break;
                    case 2:
                        fragmentBrandThreeImage.setImageResource(R.mipmap.fragment_team_bottom);
                        positionThree = 1;
                        sort = "30";
                        page = 1;
                        httpPostShop("");
                        break;
                    case 3:
                        fragmentBrandThreeImage.setImageResource(R.mipmap.fragment_team_top);
                        positionThree = 2;
                        sort = "31";
                        page = 1;
                        httpPostShop("");
                        break;
                }
                break;
            case R.id.fragment_brand_four:// 价格
                fragmentBrandTwoImage.setImageResource(R.mipmap.fragment_team_default);
                fragmentBrandThreeImage.setImageResource(R.mipmap.fragment_team_default);
                fragmentBrandOneText.setTextColor(0xff666666);
                fragmentBrandTwoText.setTextColor(0xff666666);
                fragmentBrandThreeText.setTextColor(0xff666666);
                fragmentBrandFourText.setTextColor(getResources().getColor(R.color.mainColor));
                switch (positionFour) {
                    case 1:
                        fragmentBrandFourImage.setImageResource(R.mipmap.fragment_team_bottom);
                        positionFour = 2;
                        sort = "01";
                        page = 1;
                        httpPostShop("");
                        break;
                    case 2:
                        fragmentBrandFourImage.setImageResource(R.mipmap.fragment_team_top);
                        positionFour = 1;
                        sort = "00";
                        page = 1;
                        httpPostShop("");
                        break;
                    case 3:
                        fragmentBrandFourImage.setImageResource(R.mipmap.fragment_team_bottom);
                        positionFour = 2;
                        sort = "01";
                        page = 1;
                        httpPostShop("");
                        break;
                }
                break;
            case R.id.fragment_brand_check:// 选品牌
                if (brandList != null) {
                    RightMenuDialog dialog = new RightMenuDialog(getActivity());
                    dialog.showDialog(brandList, range);
                }
                break;
        }
    }
    boolean isfirst =true;
    private void httpPostShop(String time) {
        // 请求商品搜索（类目）
        paramMap.clear();
        paramMap.put("userid", login.getUserid());
        paramMap.put("shopclassone", id);
        paramMap.put("shopclasstwo", "");
        paramMap.put("startindex", page + "");
        paramMap.put("searchtime", time);
        paramMap.put("pagesize", pageSize + "");
        paramMap.put("sortdesc", sort);
        paramMap.put("screendesc", "");
        paramMap.put("pricerange", range);
        paramMap.put("isbrand", "1");
        paramMap.put("brandid", ids);
        NetworkRequest.getInstance().POST(handler, paramMap, "ShopCategory", HttpCommon.ShopCategory);
        if (page ==1&&isfirst){
            isfirst=false;
            showDialog();
        }
    }

    /**
     * 下拉刷新设置
     */
    private void ptrFrameLayout() {
        loadMorePtrFrame.setLoadingMinTime(700);
        loadMorePtrFrame.setHeaderView(loadView);
        loadMorePtrFrame.addPtrUIHandler(loadView);
        loadMorePtrFrame.setPtrHandler(this);
    }

    /**
     * 设置图片大小
     */
    private void checkBitmap(boolean isCheck) {
        if (isCheck) {
            fragmentBrandCheckImage.setImageResource(R.mipmap.main_screen_check);
            fragmentBrandCheck.setTextColor(getResources().getColor(R.color.mainColor));
        } else {
            fragmentBrandCheckImage.setImageResource(R.mipmap.main_screen);
            fragmentBrandCheck.setTextColor(0xff666666);
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        startActivity(new Intent(getActivity(), CommodityActivity290.class).putExtra("shopId", ((CommodityList.CommodityData) adapter.getItem(position)).getId()).putExtra("source", "dmj").putExtra("sourceId",""));
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        httpPostShop(commodityList.getSearchtime());
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, fragmentBrandRecycler, header);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        page = 1;
        httpPostShop("");
    }
}
