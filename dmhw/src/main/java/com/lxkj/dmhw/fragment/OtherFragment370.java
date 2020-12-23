package com.lxkj.dmhw.fragment;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.CommodityActivity290;
import com.lxkj.dmhw.adapter.ShopListRecyclerAdapter370;
import com.lxkj.dmhw.adapter.ShopListRecyclerAdapterNew;
import com.lxkj.dmhw.bean.CommodityDetails290;
import com.lxkj.dmhw.bean.CommodityList;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.defined.PopupWindows;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.MyLayoutManager;
import com.lxkj.dmhw.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 超级分类
 */
public class OtherFragment370 extends BaseFragment implements PtrHandler, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.other_check)
    CheckBox otherCheck;
    @BindView(R.id.load_more_ptr_frame)
    PtrClassicFrameLayout loadMorePtrFrame;
    @BindView(R.id.other_screen_layout)
    LinearLayout otherScreenLayout;
    @BindView(R.id.other_recycler)
    RecyclerView otherRecycler;
    @BindView(R.id.fragment_other_one_text)
    TextView fragmentOtherOneText;
    @BindView(R.id.fragment_other_one)
    LinearLayout fragmentOtherOne;
    @BindView(R.id.fragment_other_two_text)
    TextView fragmentOtherTwoText;
    @BindView(R.id.fragment_other_two_image)
    ImageView fragmentOtherTwoImage;
    @BindView(R.id.fragment_other_two)
    LinearLayout fragmentOtherTwo;
    @BindView(R.id.fragment_other_three_text)
    TextView fragmentOtherThreeText;
    @BindView(R.id.fragment_other_three_image)
    ImageView fragmentOtherThreeImage;
    @BindView(R.id.fragment_other_three)
    LinearLayout fragmentOtherThree;
    @BindView(R.id.fragment_other_four_text)
    TextView fragmentOtherFourText;
    @BindView(R.id.fragment_other_four_image)
    ImageView fragmentOtherFourImage;
    @BindView(R.id.fragment_other_four)
    LinearLayout fragmentOtherFour;
    private String sort = "20";
    private int positionTwo = 3;
    private int positionThree = 3;
    private int positionFour = 3;
    // 天猫or淘宝
    private int
            screen = 0,
            searchScreen = 0;
    // 价格区间
    private String range = "";
    // 列表数据
    ArrayList<CommodityDetails290> commodityList;
    // 适配器
    private ShopListRecyclerAdapter370 adapter;
    private String
            categoryTwo;// 二级类目ID

    public static OtherFragment370 getInstance() {
        OtherFragment370 fragment = new OtherFragment370();
        return fragment;
    }

    @Override
    public void onData() {

    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other370, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {
        categoryTwo = getActivity().getIntent().getExtras().getString("CategoryTwo");
        title.setText(getActivity().getIntent().getExtras().getString("title"));
        // 设置Recycler
        otherRecycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), false));
        adapter = new ShopListRecyclerAdapter370(getActivity());
        otherRecycler.setAdapter(adapter);
        // 设置预加载位置,倒数
        adapter.setPreLoadNumber(5);
        // 监听item点击事件
        adapter.setOnItemClickListener(this);
        // 监听加载更多事件
        adapter.setOnLoadMoreListener(this, otherRecycler);
        // 取消第一次加载回调
        adapter.disableLoadMoreIfNotFullPage();
//        if (DateStorage.getLoginStatus()) {
//            if (Objects.equals(login.getUsertype(), "3")) {
//                fragmentOtherThree.setVisibility(View.GONE);
//            } else {
//                fragmentOtherThree.setVisibility(View.VISIBLE);
//            }
//        } else {
//            fragmentOtherThree.setVisibility(View.GONE);
//        }
        fragmentOtherThree.setVisibility(View.GONE);
    }

    @Override
    public void onCustomized() {
        checkBitmap(false);
        ptrFrameLayout();
        httpPost("");
    }

    public void refresh(){
        page=1;
        httpPost("");
    }

    @Override
    public void mainMessage(Message message) {

    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        loadMorePtrFrame.refreshComplete();
        dismissDialog();
        if (message.what == LogicActions.DtkSearchGoodsListSuccess) {
            commodityList = (ArrayList<CommodityDetails290>) message.obj;
            if (commodityList.size() > 0) {
                if (page > 1) {
                    adapter.addData(commodityList);
                    adapter.notifyDataSetChanged();
                } else {
                    adapter.setNewData(commodityList);
                    adapter.notifyDataSetChanged();
                }
                adapter.loadMoreComplete();
            } else {
                adapter.loadMoreEnd();
            }
        }
    }

    /**
     * 设置图片大小
     */
    private void checkBitmap(boolean ischeck) {
        Drawable drawable;
        Resources resources = getResources();
        if (ischeck) {
            drawable = resources.getDrawable(R.mipmap.main_screen_check);
            drawable.setBounds(0, 0, Utils.dipToPixel(R.dimen.dp_11), Utils.dipToPixel(R.dimen.dp_11));
            otherCheck.setTextColor(getResources().getColor(R.color.mainColor));
            otherCheck.setCompoundDrawables(null, null, drawable, null);
        } else {
            drawable = resources.getDrawable(R.mipmap.main_screen);
            drawable.setBounds(0, 0, Utils.dipToPixel(R.dimen.dp_11), Utils.dipToPixel(R.dimen.dp_11));
            otherCheck.setTextColor(0xff666666);
            otherCheck.setCompoundDrawables(null, null, drawable, null);
        }
    }

    /**
     * 显示筛选
     */
    private void showScreen() {
        final EditText
                popupScreenLow,
                popupScreenHigh;
        final CheckBox
                popupScreenTmall,
                popupScreenTaoBao;
        ImageView pop_check_taobao,pop_check_tmall;
        LinearLayout popupScreenYes,popupScreenReset,shoptype_layout;
        TextView shoptype;
        View view = View.inflate(getActivity(), R.layout.popup_screen, null);
        final PopupWindows window = new PopupWindows(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(0x60000000));
        window.setFocusable(true);
        window.setOutsideTouchable(false);
        window.setContentView(view);
        window.showAsDropDown(otherScreenLayout);
        window.update();
        view.findViewById(R.id.popup_cancel).setOnClickListener(v -> window.dismiss());
        popupScreenLow = view.findViewById(R.id.popup_screen_low);
        popupScreenHigh =  view.findViewById(R.id.popup_screen_high);
        popupScreenTmall =  view.findViewById(R.id.popup_screen_tmall);
        popupScreenTaoBao =view.findViewById(R.id.popup_screen_taobao);
        pop_check_taobao =  view.findViewById(R.id.pop_check_taobao);
        pop_check_tmall = view.findViewById(R.id.pop_check_tmall);
        popupScreenYes = view.findViewById(R.id.popup_screen_yes);
        popupScreenReset= view.findViewById(R.id.popup_screen_reset);
        shoptype=view.findViewById(R.id.shoptype);
        shoptype_layout=view.findViewById(R.id.shoptype_layout);
        if (range.equals("")) {
            popupScreenLow.setText("");
            popupScreenHigh.setText("");
        } else {
            popupScreenLow.setText(range.substring(0, range.indexOf("-")));
            String high = range.substring(range.indexOf("-") + 1);
            if (high.equals("20180118")) {
                popupScreenHigh.setText("");
            } else {
                popupScreenHigh.setText(high);
            }
        }
        switch (searchScreen) {
            case 0:
                popupScreenTmall.setChecked(false);
                popupScreenTaoBao.setChecked(false);
                pop_check_tmall.setVisibility(View.GONE);
                pop_check_taobao.setVisibility(View.GONE);
                break;
            case 1:
                popupScreenTmall.setChecked(false);
                popupScreenTaoBao.setChecked(true);
                pop_check_tmall.setVisibility(View.GONE);
                pop_check_taobao.setVisibility(View.VISIBLE);
                break;
            case 2:
                popupScreenTmall.setChecked(true);
                popupScreenTaoBao.setChecked(false);
                pop_check_tmall.setVisibility(View.VISIBLE);
                pop_check_taobao.setVisibility(View.GONE);
                break;
        }
        popupScreenTmall.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (screen == 1) {
                popupScreenTaoBao.setChecked(false);
            }
            if (isChecked) {
                screen = 2;
                pop_check_taobao.setVisibility(View.GONE);
                pop_check_tmall.setVisibility(View.VISIBLE);
            } else {
                screen = 0;
                pop_check_taobao.setVisibility(View.GONE);
                pop_check_tmall.setVisibility(View.GONE);
            }
        });
        popupScreenTaoBao.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (screen == 2) {
                popupScreenTmall.setChecked(false);
            }
            if (isChecked) {
                screen = 1;
                pop_check_taobao.setVisibility(View.VISIBLE);
                pop_check_tmall.setVisibility(View.GONE);
            } else {
                screen = 0;
                pop_check_taobao.setVisibility(View.GONE);
                pop_check_tmall.setVisibility(View.GONE);
            }
        });
        popupScreenYes.setOnClickListener(v -> {
            page=1;
            window.dismiss();
            if (popupScreenLow.getText().toString().equals("") && popupScreenHigh.getText().toString().equals("") && !popupScreenTmall.isChecked() && !popupScreenTaoBao.isChecked()) {
                checkBitmap(false);
                if (searchScreen != 0 || !range.equals("")) {
                    searchScreen = 0;
                    range = "";
                    httpPost("");
                }
            } else {
                checkBitmap(true);
                searchScreen = screen;
                if (popupScreenLow.getText().toString().equals("") && popupScreenHigh.getText().toString().equals("")) {
                    range = "";
                } else {
                    if (popupScreenLow.getText().toString().equals("")) {
                        range = "0-" + popupScreenHigh.getText().toString();
                    } else if (popupScreenHigh.getText().toString().equals("")) {
                        range = popupScreenLow.getText().toString() + "-999999";
                    } else {
                        if (Integer.parseInt(popupScreenLow.getText().toString()) > Integer.parseInt(popupScreenHigh.getText().toString())) {
                            range = popupScreenHigh.getText().toString() + "-" + popupScreenLow.getText().toString();
                        } else {
                            range = popupScreenLow.getText().toString() + "-" + popupScreenHigh.getText().toString();
                        }
                    }
                }
                httpPost("");
            }

        });

        //重置
        popupScreenReset.setOnClickListener(v -> {
            checkBitmap(false);
            popupScreenTmall.setChecked(false);
            popupScreenTaoBao.setChecked(false);
            pop_check_taobao.setVisibility(View.GONE);
            pop_check_tmall.setVisibility(View.GONE);
            popupScreenLow.setText("");
            popupScreenHigh.setText("");
            page=1;
            window.dismiss();
            if (popupScreenLow.getText().toString().equals("") && popupScreenHigh.getText().toString().equals("") && !popupScreenTmall.isChecked() && !popupScreenTaoBao.isChecked()) {
                checkBitmap(false);
                if (searchScreen != 0 || !range.equals("")) {
                    searchScreen = 0;
                    range = "";
                    httpPost("");
                }
            } else {
                checkBitmap(true);
                searchScreen = screen;
                if (popupScreenLow.getText().toString().equals("") && popupScreenHigh.getText().toString().equals("")) {
                    range = "";
                } else {
                    if (popupScreenLow.getText().toString().equals("")) {
                        range = "0-" + popupScreenHigh.getText().toString();
                    } else if (popupScreenHigh.getText().toString().equals("")) {
                        range = popupScreenLow.getText().toString() + "-999999";
                    } else {
                        if (Integer.parseInt(popupScreenLow.getText().toString()) > Integer.parseInt(popupScreenHigh.getText().toString())) {
                            range = popupScreenHigh.getText().toString() + "-" + popupScreenLow.getText().toString();
                        } else {
                            range = popupScreenLow.getText().toString() + "-" + popupScreenHigh.getText().toString();
                        }
                    }
                }
                httpPost("");
            }
        });
    }

    private void httpPost(String time) {
        // 请求商品搜索（类目）
        paramMap = new HashMap<>();
        paramMap.put("userid", login.getUserid());
        paramMap.put("shopclasstwo", categoryTwo);
        paramMap.put("startindex", page + "");
        paramMap.put("searchtime", time);
        paramMap.put("pagesize", pageSize + "");
        paramMap.put("sortdesc", sort);
        switch (searchScreen) {
            case 0:
                paramMap.put("screendesc", "");
                break;
            case 1:
                paramMap.put("screendesc", "00");
                break;
            case 2:
                paramMap.put("screendesc", "01");
                break;
        }
        paramMap.put("pricerange", range);
        NetworkRequest.getInstance().POST(handler, paramMap, "DtkSearchGoodsList", HttpCommon.DTKSearchGoodsList);
        if (page == 1) {
            showDialog();
        }
    }

    @OnClick({R.id.back, R.id.other_check, R.id.fragment_other_one, R.id.fragment_other_two, R.id.fragment_other_three, R.id.fragment_other_four})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                isFinish();
                break;
            case R.id.other_check:
                showScreen();
                break;
            case R.id.fragment_other_one:
                positionTwo = 3;
                positionThree = 3;
                positionFour = 3;
                fragmentOtherTwoImage.setImageResource(R.mipmap.fragment_team_default);
                fragmentOtherThreeImage.setImageResource(R.mipmap.fragment_team_default);
                fragmentOtherFourImage.setImageResource(R.mipmap.fragment_team_default);
                fragmentOtherOneText.setTextColor(getResources().getColor(R.color.black));
                fragmentOtherTwoText.setTextColor(0xff666666);
                fragmentOtherThreeText.setTextColor(0xff666666);
                fragmentOtherFourText.setTextColor(0xff666666);
                sort = "20";
                page = 1;
                httpPost("");
                break;
            case R.id.fragment_other_two:
                positionThree = 3;
                positionFour = 3;
                fragmentOtherThreeImage.setImageResource(R.mipmap.fragment_team_default);
                fragmentOtherFourImage.setImageResource(R.mipmap.fragment_team_default);
                fragmentOtherOneText.setTextColor(0xff666666);
                fragmentOtherTwoText.setTextColor(getResources().getColor(R.color.black));
                fragmentOtherThreeText.setTextColor(0xff666666);
                fragmentOtherFourText.setTextColor(0xff666666);
                fragmentOtherTwoImage.setImageResource(R.mipmap.fragment_team_bottom);
                positionTwo = 2;
                sort = "11";
                page = 1;
                httpPost("");
//                switch (positionTwo) {
//                    case 1:
//                        fragmentOtherTwoImage.setImageResource(R.mipmap.fragment_team_bottom);
//                        positionTwo = 2;
//                        sort = "11";
//                        page = 1;
//                        httpPost("");
//                        break;
//                    case 2:
//                        fragmentOtherTwoImage.setImageResource(R.mipmap.fragment_team_top);
//                        positionTwo = 1;
//                        sort = "10";
//                        page = 1;
//                        httpPost("");
//                        break;
//                    case 3:
//                        fragmentOtherTwoImage.setImageResource(R.mipmap.fragment_team_bottom);
//                        positionTwo = 2;
//                        sort = "11";
//                        page = 1;
//                        httpPost("");
//                        break;
//                }
                break;
            case R.id.fragment_other_three:
                positionTwo = 3;
                positionFour = 3;
                fragmentOtherTwoImage.setImageResource(R.mipmap.fragment_team_default);
                fragmentOtherFourImage.setImageResource(R.mipmap.fragment_team_default);
                fragmentOtherOneText.setTextColor(0xff666666);
                fragmentOtherTwoText.setTextColor(0xff666666);
                fragmentOtherThreeText.setTextColor(getResources().getColor(R.color.black));
                fragmentOtherFourText.setTextColor(0xff666666);
                switch (positionThree) {
                    case 1:
                        fragmentOtherThreeImage.setImageResource(R.mipmap.fragment_team_bottom);
                        positionThree = 2;
                        sort = "31";
                        page = 1;
                        httpPost("");
                        break;
                    case 2:
                        fragmentOtherThreeImage.setImageResource(R.mipmap.fragment_team_top);
                        positionThree = 1;
                        sort = "30";
                        page = 1;
                        httpPost("");
                        break;
                    case 3:
                        fragmentOtherThreeImage.setImageResource(R.mipmap.fragment_team_bottom);
                        positionThree = 2;
                        sort = "31";
                        page = 1;
                        httpPost("");
                        break;
                }
                break;
            case R.id.fragment_other_four:
                positionTwo = 3;
                positionThree = 3;
                fragmentOtherTwoImage.setImageResource(R.mipmap.fragment_team_default);
                fragmentOtherThreeImage.setImageResource(R.mipmap.fragment_team_default);
                fragmentOtherOneText.setTextColor(0xff666666);
                fragmentOtherTwoText.setTextColor(0xff666666);
                fragmentOtherThreeText.setTextColor(0xff666666);
                fragmentOtherFourText.setTextColor(getResources().getColor(R.color.black));
                switch (positionFour) {
                    case 1:
                        fragmentOtherFourImage.setImageResource(R.mipmap.fragment_team_bottom);
                        positionFour = 2;
                        sort = "01";
                        page = 1;
                        httpPost("");
                        break;
                    case 2:
                        fragmentOtherFourImage.setImageResource(R.mipmap.fragment_team_top);
                        positionFour = 1;
                        sort = "00";
                        page = 1;
                        httpPost("");
                        break;
                    case 3:
                        fragmentOtherFourImage.setImageResource(R.mipmap.fragment_team_bottom);
                        positionFour = 2;
                        sort = "01";
                        page = 1;
                        httpPost("");
                        break;
                }
                break;
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

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, otherRecycler, header);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        page = 1;
        httpPost("");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        CommodityDetails290 data= (CommodityDetails290) adapter.getData().get(position);
        startActivity(new Intent(getActivity(), CommodityActivity290.class).putExtra("shopId", data.getId()).putExtra("source", data.getSource()).putExtra("sourceId", data.getSourceId()));

    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        httpPost("");
    }
}
