package com.lxkj.dmhw.fragment;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import androidx.recyclerview.widget.RecyclerView;
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
import com.lxkj.dmhw.adapter.ShopGridAdapter;
import com.lxkj.dmhw.adapter.ShopListRecyclerAdapterNew;
import com.lxkj.dmhw.bean.CommodityList;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.defined.PopupWindows;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.MyLayoutManager;
import com.lxkj.dmhw.utils.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 标签
 * Created by Zyhant on 2018/1/16.
 */
public class NewFragment extends BaseFragment implements PtrHandler, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.new_check)
    CheckBox newCheck;
    @BindView(R.id.load_more_ptr_frame)
    PtrClassicFrameLayout loadMorePtrFrame;
    @BindView(R.id.new_screen_layout)
    LinearLayout newScreenLayout;
    @BindView(R.id.new_recycler)
    RecyclerView newRecycler;
    @BindView(R.id.fragment_new_one_text)
    TextView fragmentNewOneText;
    @BindView(R.id.fragment_new_one)
    LinearLayout fragmentNewOne;
    @BindView(R.id.fragment_new_two_text)
    TextView fragmentNewTwoText;
    @BindView(R.id.fragment_new_two_image)
    ImageView fragmentNewTwoImage;
    @BindView(R.id.fragment_new_two)
    LinearLayout fragmentNewTwo;
    @BindView(R.id.fragment_new_three_text)
    TextView fragmentNewThreeText;
    @BindView(R.id.fragment_new_three_image)
    ImageView fragmentNewThreeImage;
    @BindView(R.id.fragment_new_three)
    LinearLayout fragmentNewThree;
    @BindView(R.id.fragment_new_four_text)
    TextView fragmentNewFourText;
    @BindView(R.id.fragment_new_four_image)
    ImageView fragmentNewFourImage;
    @BindView(R.id.fragment_new_four)
    LinearLayout fragmentNewFour;

    @BindView(R.id.more_goods_iv)
    ImageView more_goods_iv;

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
    private CommodityList commodityList = new CommodityList();
    // 适配器
    private ShopListRecyclerAdapterNew adapter;
    // 视频购物适配器
    private ShopGridAdapter gridAdapter;
    // 页面类型
    private String labelType;

    private String topicId;
    public static NewFragment getInstance() {
        NewFragment fragment = new NewFragment();
        return fragment;
    }

    @Override
    public void onData() {

    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {
        labelType = getActivity().getIntent().getExtras().getString("labelType");
        title.setText(getActivity().getIntent().getExtras().getString("name"));
        if (labelType.equals("09")) {
            more_goods_iv.setVisibility(View.VISIBLE);
            newScreenLayout.setVisibility(View.GONE);
         topicId= getActivity().getIntent().getExtras().getString("topicId");
        }else{
            more_goods_iv.setVisibility(View.GONE);
            newScreenLayout.setVisibility(View.VISIBLE);
        }
        // 设置Recycler
        if (labelType.equals("08")||labelType.equals("09")) {
            newRecycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), 2));
            if (labelType.equals("09")){
                gridAdapter = new ShopGridAdapter(getActivity(),9);//去掉视频购物的视频图标
            }else{
                gridAdapter = new ShopGridAdapter(getActivity(),8);
            }
            newRecycler.setAdapter(gridAdapter);
            // 设置预加载位置,倒数
            gridAdapter.setPreLoadNumber(5);
            // 监听item点击事件
            gridAdapter.setOnItemClickListener(this);
            // 监听加载更多事件
            gridAdapter.setOnLoadMoreListener(this, newRecycler);
            // 取消第一次加载回调
            gridAdapter.disableLoadMoreIfNotFullPage();
        } else {
            newRecycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), false));
            adapter = new ShopListRecyclerAdapterNew(getActivity());
            newRecycler.setAdapter(adapter);
            // 设置预加载位置,倒数
            adapter.setPreLoadNumber(5);
            // 监听item点击事件
            adapter.setOnItemClickListener(this);
            // 监听加载更多事件
            adapter.setOnLoadMoreListener(this, newRecycler);
            // 取消第一次加载回调
            adapter.disableLoadMoreIfNotFullPage();
        }
        // 设置排序
        switch (labelType) {
            case "02":// 人气宝贝
                positionThree = 3;
                positionFour = 3;
                fragmentNewThreeImage.setImageResource(R.mipmap.fragment_team_default);
                fragmentNewFourImage.setImageResource(R.mipmap.fragment_team_default);
                fragmentNewOneText.setTextColor(0xff666666);
                fragmentNewTwoText.setTextColor(getResources().getColor(R.color.black));
                fragmentNewThreeText.setTextColor(0xff666666);
                fragmentNewFourText.setTextColor(0xff666666);
                fragmentNewTwoImage.setImageResource(R.mipmap.fragment_team_bottom);
                positionTwo = 2;
                sort = "11";
                page = 1;
                break;
        }
        if (DateStorage.getLoginStatus()) {
            if (Objects.equals(login.getUsertype(), "3")) {
                fragmentNewThree.setVisibility(View.GONE);
            } else {
                fragmentNewThree.setVisibility(View.VISIBLE);
            }
        } else {
            fragmentNewThree.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCustomized() {
        checkBitmap(false);
        ptrFrameLayout();
        if (labelType.equals("09")){
         getapptopic();//获取主题图片和标题
        }
        httpPost("");
    }

    public void refresh(){
        page=1;
        httpPost("");
    }

   private void getapptopic(){
       paramMap.clear();
       paramMap.put("topicid", topicId);
       NetworkRequest.getInstance().POST(handler, paramMap, "GetAppTopic", HttpCommon.getapptopic);
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
        if (message.what == LogicActions.GetAppTopicSuccess) {
         JSONObject object=(JSONObject)message.obj;
         title.setText(object.optString("topicname"));
         Utils.displayImageRounded(getActivity(),object.optString("topicimgurl"),  more_goods_iv, 0);
        }
        if (message.what == LogicActions.ShopLabelSuccess) {
            commodityList = (CommodityList) message.obj;
            if (labelType.equals("08")||labelType.equals("09")) {
                if (commodityList.getShopdata().size() > 0) {
                    if (page > 1) {
                        gridAdapter.addData(commodityList.getShopdata());
//                        gridAdapter.notifyDataSetChanged();
                    } else {
                        gridAdapter.setNewData(commodityList.getShopdata());
//                        gridAdapter.notifyDataSetChanged();
                    }
                    gridAdapter.loadMoreComplete();
                } else {
                    gridAdapter.loadMoreEnd();
                }
            } else {
                if (commodityList.getShopdata().size() > 0) {
                    if (page > 1) {
                        adapter.addData(commodityList.getShopdata());
//                        adapter.notifyDataSetChanged();
                    } else {
                        adapter.setNewData(commodityList.getShopdata());
//                        adapter.notifyDataSetChanged();
                    }
                    adapter.loadMoreComplete();
                } else {
                    adapter.loadMoreEnd();
                }
            }
        }
    }
    boolean isfirst =true;
    private void httpPost(String time) {
        // 请求商品搜索（标签）
        paramMap.clear();
        paramMap.put("userid", login.getUserid());
        paramMap.put("labeltype", labelType);
        if (labelType.equals("09")) {
            paramMap.put("topicid", topicId);
        }
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
        NetworkRequest.getInstance().POST(handler, paramMap, "ShopLabel", HttpCommon.ShopLabel);
        if (page == 1) {
            if (labelType.equals("08")||labelType.equals("09")) {
                gridAdapter.setNewData(new ArrayList<>());
                gridAdapter.notifyDataSetChanged();
                if (isfirst){
                    isfirst=false;
                    showDialog();
                }

            } else {
                adapter.setNewData(new ArrayList<>());
                adapter.notifyDataSetChanged();
                if (isfirst){
                    isfirst=false;
                    showDialog();
                }
            }
        }
    }

    /**
     * 设置图片大小
     */
    private void checkBitmap(boolean isCheck) {
        Drawable drawable;
        Resources resources = getResources();
        if (isCheck) {
            drawable = resources.getDrawable(R.mipmap.main_screen);
            drawable.setBounds(0, 0, Utils.dipToPixel(R.dimen.dp_11), Utils.dipToPixel(R.dimen.dp_11));
            newCheck.setTextColor(getResources().getColor(R.color.black));
            newCheck.setCompoundDrawables(null, null, drawable, null);
        } else {
            drawable = resources.getDrawable(R.mipmap.main_screen);
            drawable.setBounds(0, 0, Utils.dipToPixel(R.dimen.dp_11), Utils.dipToPixel(R.dimen.dp_11));
            newCheck.setTextColor(0xff666666);
            newCheck.setCompoundDrawables(null, null, drawable, null);
        }
    }

    /**
     * 显示筛选
     */
//    private void showScreen() {
//        final EditText
//                popupScreenLow,
//                popupScreenHigh;
//        final CheckBox
//                popupScreenTmall,
//                popupScreenTaoBao;
//        View view = View.inflate(getActivity(), R.layout.popup_screen, null);
//        final PopupWindows window = new PopupWindows(view, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
//        window.setBackgroundDrawable(new ColorDrawable(0x60000000));
//        window.setFocusable(true);
//        window.setOutsideTouchable(false);
//        window.setContentView(view);
//        window.showAsDropDown(newScreenLayout);
//        window.update();
//        view.findViewById(R.id.popup_cancel).setOnClickListener(v -> window.dismiss());
//        popupScreenLow = (EditText) view.findViewById(R.id.popup_screen_low);
//        popupScreenHigh = (EditText) view.findViewById(R.id.popup_screen_high);
//        popupScreenTmall = (CheckBox) view.findViewById(R.id.popup_screen_tmall);
//        popupScreenTaoBao = (CheckBox) view.findViewById(R.id.popup_screen_taobao);
//        LinearLayout popupScreenYes = (LinearLayout) view.findViewById(R.id.popup_screen_yes);
//        if (range.equals("")) {
//            popupScreenLow.setText("");
//            popupScreenHigh.setText("");
//        } else {
//            popupScreenLow.setText(range.substring(0, range.indexOf("-")));
//            String high = range.substring(range.indexOf("-") + 1);
//            if (high.equals("20180118")) {
//                popupScreenHigh.setText("");
//            } else {
//                popupScreenHigh.setText(high);
//            }
//        }
//        switch (searchScreen) {
//            case 0:
//                popupScreenTmall.setChecked(false);
//                popupScreenTaoBao.setChecked(false);
//                break;
//            case 1:
//                popupScreenTmall.setChecked(false);
//                popupScreenTaoBao.setChecked(true);
//                break;
//            case 2:
//                popupScreenTmall.setChecked(true);
//                popupScreenTaoBao.setChecked(false);
//                break;
//        }
//        popupScreenTmall.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (screen == 1) {
//                popupScreenTaoBao.setChecked(false);
//            }
//            if (isChecked) {
//                screen = 2;
//            } else {
//                screen = 0;
//            }
//        });
//        popupScreenTaoBao.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (screen == 2) {
//                popupScreenTmall.setChecked(false);
//            }
//            if (isChecked) {
//                screen = 1;
//            } else {
//                screen = 0;
//            }
//        });
//        popupScreenYes.setOnClickListener(v -> {
//            page = 1;
//            if (popupScreenLow.getText().toString().equals("") && popupScreenHigh.getText().toString().equals("") && !popupScreenTmall.isChecked() && !popupScreenTaoBao.isChecked()) {
//                checkBitmap(false);
//                window.dismiss();
//                if (searchScreen != 0 || !range.equals("")) {
//                    searchScreen = 0;
//                    range = "";
//                    httpPost("");
//                }
//            } else {
//                checkBitmap(true);
//                window.dismiss();
//                searchScreen = screen;
//                if (popupScreenLow.getText().toString().equals("") && popupScreenHigh.getText().toString().equals("")) {
//                    range = "";
//                } else {
//                    if (popupScreenLow.getText().toString().equals("")) {
//                        range = "0-" + popupScreenHigh.getText().toString();
//                    } else if (popupScreenHigh.getText().toString().equals("")) {
//                        range = popupScreenLow.getText().toString() + "-20180118";
//                    } else {
//                        if (Integer.parseInt(popupScreenLow.getText().toString()) > Integer.parseInt(popupScreenHigh.getText().toString())) {
//                            range = popupScreenHigh.getText().toString() + "-" + popupScreenLow.getText().toString();
//                        } else {
//                            range = popupScreenLow.getText().toString() + "-" + popupScreenHigh.getText().toString();
//                        }
//                    }
//                }
//                httpPost("");
//            }
//        });
//    }

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
        window.showAsDropDown(newScreenLayout);
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



    @OnClick({R.id.back, R.id.new_check, R.id.fragment_new_one, R.id.fragment_new_two, R.id.fragment_new_three, R.id.fragment_new_four})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                isFinish();
                break;
            case R.id.new_check:
                showScreen();
                break;
            case R.id.fragment_new_one:
                positionTwo = 3;
                positionThree = 3;
                positionFour = 3;
                fragmentNewTwoImage.setImageResource(R.mipmap.fragment_team_default);
                fragmentNewThreeImage.setImageResource(R.mipmap.fragment_team_default);
                fragmentNewFourImage.setImageResource(R.mipmap.fragment_team_default);
                fragmentNewOneText.setTextColor(getResources().getColor(R.color.black));
                fragmentNewTwoText.setTextColor(0xff666666);
                fragmentNewThreeText.setTextColor(0xff666666);
                fragmentNewFourText.setTextColor(0xff666666);
                sort = "20";
                page = 1;
                httpPost("");
                break;
            case R.id.fragment_new_two:
                positionThree = 3;
                positionFour = 3;
                fragmentNewThreeImage.setImageResource(R.mipmap.fragment_team_default);
                fragmentNewFourImage.setImageResource(R.mipmap.fragment_team_default);
                fragmentNewOneText.setTextColor(0xff666666);
                fragmentNewTwoText.setTextColor(getResources().getColor(R.color.black));
                fragmentNewThreeText.setTextColor(0xff666666);
                fragmentNewFourText.setTextColor(0xff666666);
                switch (positionTwo) {
                    case 1:
                        fragmentNewTwoImage.setImageResource(R.mipmap.fragment_team_bottom);
                        positionTwo = 2;
                        sort = "11";
                        page = 1;
                        httpPost("");
                        break;
                    case 2:
                        fragmentNewTwoImage.setImageResource(R.mipmap.fragment_team_top);
                        positionTwo = 1;
                        sort = "10";
                        page = 1;
                        httpPost("");
                        break;
                    case 3:
                        fragmentNewTwoImage.setImageResource(R.mipmap.fragment_team_bottom);
                        positionTwo = 2;
                        sort = "11";
                        page = 1;
                        httpPost("");
                        break;
                }
                break;
            case R.id.fragment_new_three:
                positionTwo = 3;
                positionFour = 3;
                fragmentNewTwoImage.setImageResource(R.mipmap.fragment_team_default);
                fragmentNewFourImage.setImageResource(R.mipmap.fragment_team_default);
                fragmentNewOneText.setTextColor(0xff666666);
                fragmentNewTwoText.setTextColor(0xff666666);
                fragmentNewThreeText.setTextColor(getResources().getColor(R.color.black));
                fragmentNewFourText.setTextColor(0xff666666);
                switch (positionThree) {
                    case 1:
                        fragmentNewThreeImage.setImageResource(R.mipmap.fragment_team_top);
                        positionThree = 2;
                        sort = "30";
                        page = 1;
                        httpPost("");
                        break;
                    case 2:
                        fragmentNewThreeImage.setImageResource(R.mipmap.fragment_team_bottom);
                        positionThree = 1;
                        sort = "31";
                        page = 1;
                        httpPost("");
                        break;
                    case 3:
                        fragmentNewThreeImage.setImageResource(R.mipmap.fragment_team_top);
                        positionThree = 2;
                        sort = "30";
                        page = 1;
                        httpPost("");
                        break;
                }
                break;
            case R.id.fragment_new_four:
                positionTwo = 3;
                positionThree = 3;
                fragmentNewTwoImage.setImageResource(R.mipmap.fragment_team_default);
                fragmentNewThreeImage.setImageResource(R.mipmap.fragment_team_default);
                fragmentNewOneText.setTextColor(0xff666666);
                fragmentNewTwoText.setTextColor(0xff666666);
                fragmentNewThreeText.setTextColor(0xff666666);
                fragmentNewFourText.setTextColor(getResources().getColor(R.color.black));
                switch (positionFour) {
                    case 1:
                        fragmentNewFourImage.setImageResource(R.mipmap.fragment_team_bottom);
                        positionFour = 2;
                        sort = "01";
                        page = 1;
                        httpPost("");
                        break;
                    case 2:
                        fragmentNewFourImage.setImageResource(R.mipmap.fragment_team_top);
                        positionFour = 1;
                        sort = "00";
                        page = 1;
                        httpPost("");
                        break;
                    case 3:
                        fragmentNewFourImage.setImageResource(R.mipmap.fragment_team_bottom);
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
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, newRecycler, header);
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
        startActivity(new Intent(getActivity(), CommodityActivity290.class).putExtra("shopId", ((List<CommodityList.CommodityData>) adapter.getData()).get(position).getShopid()).putExtra("source", "dmj").putExtra("sourceId",""));
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        httpPost(commodityList.getSearchtime());
    }
}
