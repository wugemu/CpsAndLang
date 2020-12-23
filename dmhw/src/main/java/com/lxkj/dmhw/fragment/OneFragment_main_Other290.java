package com.lxkj.dmhw.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.CommodityActivity290;
import com.lxkj.dmhw.adapter.BannerHAdapter;
import com.lxkj.dmhw.adapter.MyOtherAdapter;
import com.lxkj.dmhw.adapter.OneFragmentOtherGridAdapter;
import com.lxkj.dmhw.bean.Banner;
import com.lxkj.dmhw.bean.CategoryTwo;
import com.lxkj.dmhw.bean.CommodityList;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.defined.ObserveGridView;
import com.lxkj.dmhw.defined.PopupWindows;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.MyLayoutManager;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 其他品类
 * Created by Administrator on 2017/12/1 0001.
 */

public class OneFragment_main_Other290 extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.fragment_one_other_screen_check)
    CheckBox fragmentOneOtherScreenCheck;
    @BindView(R.id.fragment_one_other_header)
    LinearLayout fragmentOneOtherHeader;
    @BindView(R.id.fragment_one_other_line)
    View fragment_one_other_line;
    @BindView(R.id.fragment_one_other_list)
    RecyclerView fragmentOneOtherList;
    @BindView(R.id.fragment_one_mask)
    ImageView fragmentOneMask;
    @BindView(R.id.fragment_one_new_mask)
    View fragmentOneNewMask;
    @BindView(R.id.return_top)
    ImageView returnTop;
    @BindView(R.id.fragment_one_other_one_text)
    TextView fragmentOneOtherOneText;
    @BindView(R.id.fragment_one_other_one)
    LinearLayout fragmentOneOtherOne;

    @BindView(R.id.fragment_one_other_two_text)
    TextView fragmentOneOtherTwoText;
    @BindView(R.id.fragment_one_other_two_image)
    ImageView fragmentOneOtherTwoImage;
    @BindView(R.id.fragment_one_other_two)
    LinearLayout fragmentOneOtherTwo;
    @BindView(R.id.fragment_one_other_three_text)
    TextView fragmentOneOtherThreeText;
    @BindView(R.id.fragment_one_other_three_image)
    ImageView fragmentOneOtherThreeImage;
    @BindView(R.id.fragment_one_other_three)
    LinearLayout fragmentOneOtherThree;
    @BindView(R.id.fragment_one_other_four_text)
    TextView fragmentOneOtherFourText;
    @BindView(R.id.fragment_one_other_four_image)
    ImageView fragmentOneOtherFourImage;
    @BindView(R.id.fragment_one_other_four)
    LinearLayout fragmentOneOtherFour;
    private String sort = "20";
    private int positionTwo = 3;
    private int positionThree = 3;
    private int positionFour = 3;
    private View
            header,
            headerSort;
    private CommodityList commodityList = new CommodityList();
    private MyOtherAdapter adapter;
    private OneFragmentOtherGridAdapter gridAdapter;
    private CheckBox headerFragmentOneNewSortCheck;
    private ConvenientBanner headerFragmentOneOtherBanner;
    // 天猫or淘宝
    private int
            screen = 0,
            searchScreen = 0;
    // 价格区间
    private String range = "";
    private int position;
    private boolean isCheck = true;
    private TextView
            headerFragmentOneNewSortOneText,
            headerFragmentOneNewSortTwoText,
            headerFragmentOneNewSortThreeText,
            headerFragmentOneNewSortFourText;
    private ImageView
            headerFragmentOneNewSortTwoImage,
            headerFragmentOneNewSortThreeImage,
            headerFragmentOneNewSortFourImage;
    //登录刷新标志
    private  boolean loginRefreshFlag=false;
    public static OneFragment_main_Other290 getInstance(int number) {
        OneFragment_main_Other290 fragment = new OneFragment_main_Other290();
        Bundle bundle = new Bundle();
        bundle.putInt("number", number);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            position = bundle.getInt("number");
        }
    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one_main_other, null);
        header = View.inflate(getActivity(), R.layout.header_fragment_one_other, null);
        headerSort = View.inflate(getActivity(), R.layout.header_fragment_one_other_sort, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        headerSort.setLayoutParams(params);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {
        headerFragmentOneNewSortCheck = findViewHeaderSort(R.id.header_fragment_one_new_screen_check);
        headerFragmentOneNewSortOneText = findViewHeaderSort(R.id.header_fragment_one_new_sort_one_text);
        headerFragmentOneNewSortTwoText = findViewHeaderSort(R.id.header_fragment_one_new_sort_two_text);
        headerFragmentOneNewSortThreeText = findViewHeaderSort(R.id.header_fragment_one_new_sort_three_text);
        headerFragmentOneNewSortFourText = findViewHeaderSort(R.id.header_fragment_one_new_sort_four_text);
        headerFragmentOneNewSortTwoImage = findViewHeaderSort(R.id.header_fragment_one_new_sort_two_image);
        headerFragmentOneNewSortThreeImage = findViewHeaderSort(R.id.header_fragment_one_new_sort_three_image);
        headerFragmentOneNewSortFourImage = findViewHeaderSort(R.id.header_fragment_one_new_sort_four_image);
        ObserveGridView headerFragmentOneOtherGrid = findViewHeader(R.id.header_fragment_one_other_grid);
        headerFragmentOneOtherBanner = findViewHeader(R.id.header_fragment_one_other_banner);

        fragmentOneOtherList.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), false));
        adapter = new MyOtherAdapter(getActivity());
        fragmentOneOtherList.setAdapter(adapter);
        adapter.setHeaderView(header);
        adapter.setHeaderOtherView(headerSort);
        gridAdapter = new OneFragmentOtherGridAdapter(getActivity());
        headerFragmentOneOtherGrid.setAdapter(gridAdapter);
        headerFragmentOneNewSortCheck.setOnClickListener(this);
        findViewHeaderSort(R.id.header_fragment_one_new_sort_one).setOnClickListener(this);
        findViewHeaderSort(R.id.header_fragment_one_new_sort_two).setOnClickListener(this);
        findViewHeaderSort(R.id.header_fragment_one_new_sort_three).setOnClickListener(this);
        findViewHeaderSort(R.id.header_fragment_one_new_sort_four).setOnClickListener(this);
        if (DateStorage.getLoginStatus()) {
            if (Objects.equals(login.getUsertype(), "3")) {
                fragmentOneOtherThree.setVisibility(View.GONE);
                findViewHeaderSort(R.id.header_fragment_one_new_sort_three).setVisibility(View.GONE);
            } else {
                fragmentOneOtherThree.setVisibility(View.VISIBLE);
                findViewHeaderSort(R.id.header_fragment_one_new_sort_three).setVisibility(View.VISIBLE);
            }
        } else {
            fragmentOneOtherThree.setVisibility(View.GONE);
            findViewHeaderSort(R.id.header_fragment_one_new_sort_three).setVisibility(View.GONE);
        }
        //轮播图适配 宽高比例是12:5
        LinearLayout.LayoutParams linearParamsOne =(LinearLayout.LayoutParams)headerFragmentOneOtherBanner.getLayoutParams();
        linearParamsOne.width=width;
        linearParamsOne.height=width*5/12;
        headerFragmentOneOtherBanner.setLayoutParams(linearParamsOne);

         adapter.setOnItemClickListener(new MyOtherAdapter.OnItemClickListener() {
             @Override
             public void onItemClick(int position, CommodityList.CommodityData data) {
                 startActivity(new Intent(getActivity(), CommodityActivity290.class).putExtra("shopId", data.getShopid()).putExtra("source", "all").putExtra("sourceId",""));
             }
         });
        fragmentOneOtherList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = fragmentOneOtherList.getLayoutManager();
                //判断是当前layoutManager是否为LinearLayoutManager
                // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    //获取最后一个可见view的位置
                    int lastItemPosition = linearManager.findLastVisibleItemPosition();
                    //获取第一个可见view的位置
                    int firstItemPosition = linearManager.findFirstVisibleItemPosition();
                    if(firstItemPosition >=1)
                    {
                        fragmentOneOtherHeader.setVisibility(View.VISIBLE);
                        returnTop.setVisibility(View.VISIBLE);
                        if (isCheck) {
                            if (firstItemPosition >= (adapter.getItemCount() - 7)) {
                                page++;
                                isCheck = false;
                                httpPostShop(commodityList.getSearchtime());

                            }
                        }

                    }else{
                        fragmentOneOtherHeader.setVisibility(View.GONE);
                        returnTop.setVisibility(View.GONE);
                    }
                }

            }
        });

    }

    @Override
    public void onCustomized() {
        checkBitmap(false);
        initData(position);
    }

    @Override
    public void mainMessage(Message message) {
        if (message.what == LogicActions.LoginStatusSuccess) {
            if ((Boolean) message.obj) {
                //登录之后刷新页面 该页面标记成可刷新
                 loginRefreshFlag=true;
                if (Objects.equals(login.getUsertype(), "3")) {
                    fragmentOneOtherThree.setVisibility(View.GONE);
                    findViewHeaderSort(R.id.header_fragment_one_new_sort_three).setVisibility(View.GONE);
                } else {
                    fragmentOneOtherThree.setVisibility(View.VISIBLE);
                    findViewHeaderSort(R.id.header_fragment_one_new_sort_three).setVisibility(View.VISIBLE);
                }
            } else {
                fragmentOneOtherThree.setVisibility(View.GONE);
                findViewHeaderSort(R.id.header_fragment_one_new_sort_three).setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        try {
//            loadMorePtrFrame.refreshComplete();
            dismissDialog();
        } catch (Exception e) {
            Logger.e(e.toString());
        }
        if (message.what == LogicActions.CategoryTwoSuccess) {
            gridAdapter.setArrayList((ArrayList<CategoryTwo>) message.obj);
            gridAdapter.setNumber(position);
            gridAdapter.notifyDataSetChanged();
            dismissDialog();
        }
        if (message.what == LogicActions.BannerSuccess) {
            if (message.obj!=null&&((ArrayList<Banner>) message.obj).size()>0){
                headerFragmentOneOtherBanner.setVisibility(View.VISIBLE);
                bannerImage((ArrayList<Banner>) message.obj);
            }else{
                headerFragmentOneOtherBanner.setVisibility(View.GONE);
            }
            dismissDialog();
        }
        if (message.what == LogicActions.ShopCategorySuccess) {
            dismissDialog();
            fragmentOneMask.setVisibility(View.GONE);
            fragmentOneNewMask.setVisibility(View.GONE);
            commodityList = (CommodityList) message.obj;
            if (commodityList.getShopdata().size() > 0) {
                if (page > 1) {
                    adapter.addDatas(commodityList.getShopdata(),1);
                } else {
                    adapter.addDatas(commodityList.getShopdata(),0);
                }
                isCheck = true;
            } else {

                isCheck = false;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T findViewHeader(int id) {
        return (T) header.findViewById(id);
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T findViewHeaderSort(int id) {
        return (T) headerSort.findViewById(id);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    /**
     * 设置图片大小
     */
    private void checkBitmap(boolean isCheck) {
        Drawable drawable;
        Resources resources = getResources();
        if (isCheck) {
            drawable = resources.getDrawable(R.mipmap.main_screen_check);
            drawable.setBounds(0, 0, Utils.dipToPixel(R.dimen.dp_11), Utils.dipToPixel(R.dimen.dp_11));
            fragmentOneOtherScreenCheck.setTextColor(getResources().getColor(R.color.mainColor));
            headerFragmentOneNewSortCheck.setTextColor(getResources().getColor(R.color.mainColor));
            fragmentOneOtherScreenCheck.setCompoundDrawables(null, null, drawable, null);
            headerFragmentOneNewSortCheck.setCompoundDrawables(null, null, drawable, null);
        } else {
            drawable = resources.getDrawable(R.mipmap.main_screen);
            drawable.setBounds(0, 0, Utils.dipToPixel(R.dimen.dp_11), Utils.dipToPixel(R.dimen.dp_11));
            fragmentOneOtherScreenCheck.setTextColor(0xff666666);
            headerFragmentOneNewSortCheck.setTextColor(0xff666666);
            fragmentOneOtherScreenCheck.setCompoundDrawables(null, null, drawable, null);
            headerFragmentOneNewSortCheck.setCompoundDrawables(null, null, drawable, null);
        }
    }

    /**
     * 显示筛选
     *
     */
    private void showScreen() {
        final EditText
                popupScreenLow,
                popupScreenHigh;
        final CheckBox
                popupScreenTmall,
                popupScreenTaoBao;
        LinearLayout popupScreenReset;
        ImageView pop_check_taobao,pop_check_tmall;
        View view = View.inflate(getActivity(), R.layout.popup_screen, null);
        final PopupWindows window = new PopupWindows(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(0x60000000));
        window.setFocusable(true);
        window.setOutsideTouchable(false);
        window.setContentView(view);
        if (fragmentOneOtherHeader.getVisibility()==View.VISIBLE){
            window.showAsDropDown(fragmentOneOtherHeader);
        }else{
            window.showAsDropDown(fragment_one_other_line);
        }
        window.update();
        view.findViewById(R.id.popup_cancel).setOnClickListener(v -> window.dismiss());
        popupScreenLow = (EditText) view.findViewById(R.id.popup_screen_low);
        popupScreenHigh = (EditText) view.findViewById(R.id.popup_screen_high);
        popupScreenTmall = (CheckBox) view.findViewById(R.id.popup_screen_tmall);
        popupScreenTaoBao = (CheckBox) view.findViewById(R.id.popup_screen_taobao);
        pop_check_taobao =  view.findViewById(R.id.pop_check_taobao);
        pop_check_tmall = view.findViewById(R.id.pop_check_tmall);
        popupScreenReset= view.findViewById(R.id.popup_screen_reset);
        LinearLayout popupScreenYes = (LinearLayout) view.findViewById(R.id.popup_screen_yes);
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
            page = 1;
            if (popupScreenLow.getText().toString().equals("") && popupScreenHigh.getText().toString().equals("") && !popupScreenTmall.isChecked() && !popupScreenTaoBao.isChecked()) {
                checkBitmap(false);
                window.dismiss();
                if (searchScreen != 0 || !range.equals("")) {
                    searchScreen = 0;
                    range = "";
                    httpPostShop("");
                }
            } else {
                checkBitmap(true);
                window.dismiss();
                searchScreen = screen;
                if (popupScreenLow.getText().toString().equals("") && popupScreenHigh.getText().toString().equals("")) {
                    range = "";
                } else {
                    if (popupScreenLow.getText().toString().equals("")) {
                        range = "0-" + popupScreenHigh.getText().toString();
                    } else if (popupScreenHigh.getText().toString().equals("")) {
                        range = popupScreenLow.getText().toString() + "-20180118";
                    } else {
                        if (Integer.parseInt(popupScreenLow.getText().toString()) > Integer.parseInt(popupScreenHigh.getText().toString())) {
                            range = popupScreenHigh.getText().toString() + "-" + popupScreenLow.getText().toString();
                        } else {
                            range = popupScreenLow.getText().toString() + "-" + popupScreenHigh.getText().toString();
                        }
                    }
                }
                httpPostShop("");
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
            window.dismiss();
               page = 1;
               if (popupScreenLow.getText().toString().equals("") && popupScreenHigh.getText().toString().equals("") && !popupScreenTmall.isChecked() && !popupScreenTaoBao.isChecked()) {
                   checkBitmap(false);
                   if (searchScreen != 0 || !range.equals("")) {
                       searchScreen = 0;
                       range = "";
                       httpPostShop("");
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
                           range = popupScreenLow.getText().toString() + "-20180118";
                       } else {
                           if (Integer.parseInt(popupScreenLow.getText().toString()) > Integer.parseInt(popupScreenHigh.getText().toString())) {
                               range = popupScreenHigh.getText().toString() + "-" + popupScreenLow.getText().toString();
                           } else {
                               range = popupScreenLow.getText().toString() + "-" + popupScreenHigh.getText().toString();
                           }
                       }
                   }
                   httpPostShop("");
               }
        });
    }

    /**
     * 跳转指定位置
     */
    private void listScrollPosition() {
        fragmentOneOtherList.scrollToPosition(1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_fragment_one_new_sort_one:
                fragmentOneOtherOne.performClick();
                listScrollPosition();
                break;
            case R.id.header_fragment_one_new_sort_two:
                fragmentOneOtherTwo.performClick();
                listScrollPosition();
                break;
            case R.id.header_fragment_one_new_sort_three:
                fragmentOneOtherThree.performClick();
                listScrollPosition();
                break;
            case R.id.header_fragment_one_new_sort_four:
                fragmentOneOtherFour.performClick();
                listScrollPosition();
                break;
            case R.id.header_fragment_one_new_screen_check:
                fragmentOneOtherScreenCheck.setChecked(headerFragmentOneNewSortCheck.isChecked());
                listScrollPosition();
                new Handler().postDelayed(this::showScreen, 30);
                break;
        }
    }

    @OnClick({R.id.fragment_one_other_screen_check, R.id.return_top, R.id.fragment_one_other_one, R.id.fragment_one_other_two, R.id.fragment_one_other_three, R.id.fragment_one_other_four})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_one_other_screen_check:
                fragmentOneOtherScreenCheck.setChecked(headerFragmentOneNewSortCheck.isChecked());
                showScreen();
                break;
            case R.id.return_top:
                fragmentOneOtherList.scrollToPosition(0);
                break;
            case R.id.fragment_one_other_one:
                positionTwo = 3;
                positionThree = 3;
                positionFour = 3;
                fragmentOneOtherTwoImage.setImageResource(R.mipmap.fragment_team_default);
                fragmentOneOtherThreeImage.setImageResource(R.mipmap.fragment_team_default);
                fragmentOneOtherFourImage.setImageResource(R.mipmap.fragment_team_default);
                headerFragmentOneNewSortTwoImage.setImageResource(R.mipmap.fragment_team_default);
                headerFragmentOneNewSortThreeImage.setImageResource(R.mipmap.fragment_team_default);
                headerFragmentOneNewSortFourImage.setImageResource(R.mipmap.fragment_team_default);
                fragmentOneOtherOneText.setTextColor(getResources().getColor(R.color.mainColor));
                fragmentOneOtherTwoText.setTextColor(0xff666666);
                fragmentOneOtherThreeText.setTextColor(0xff666666);
                fragmentOneOtherFourText.setTextColor(0xff666666);
                headerFragmentOneNewSortOneText.setTextColor(getResources().getColor(R.color.mainColor));
                headerFragmentOneNewSortTwoText.setTextColor(0xff666666);
                headerFragmentOneNewSortThreeText.setTextColor(0xff666666);
                headerFragmentOneNewSortFourText.setTextColor(0xff666666);
                sort = "20";
                page = 1;
                httpPostShop("");
                break;
            case R.id.fragment_one_other_two:
                positionThree = 3;
                positionFour = 3;
                fragmentOneOtherThreeImage.setImageResource(R.mipmap.fragment_team_default);
                fragmentOneOtherFourImage.setImageResource(R.mipmap.fragment_team_default);
                headerFragmentOneNewSortThreeImage.setImageResource(R.mipmap.fragment_team_default);
                headerFragmentOneNewSortFourImage.setImageResource(R.mipmap.fragment_team_default);
                fragmentOneOtherOneText.setTextColor(0xff666666);
                fragmentOneOtherTwoText.setTextColor(getResources().getColor(R.color.mainColor));
                fragmentOneOtherThreeText.setTextColor(0xff666666);
                fragmentOneOtherFourText.setTextColor(0xff666666);
                headerFragmentOneNewSortOneText.setTextColor(0xff666666);
                headerFragmentOneNewSortTwoText.setTextColor(getResources().getColor(R.color.mainColor));
                headerFragmentOneNewSortThreeText.setTextColor(0xff666666);
                headerFragmentOneNewSortFourText.setTextColor(0xff666666);
                switch (positionTwo) {
                    case 1:
                        fragmentOneOtherTwoImage.setImageResource(R.mipmap.fragment_team_bottom);
                        headerFragmentOneNewSortTwoImage.setImageResource(R.mipmap.fragment_team_bottom);
                        positionTwo = 2;
                        sort = "11";
                        page = 1;
                        httpPostShop("");
                        break;
                    case 2:
                        fragmentOneOtherTwoImage.setImageResource(R.mipmap.fragment_team_top);
                        headerFragmentOneNewSortTwoImage.setImageResource(R.mipmap.fragment_team_top);
                        positionTwo =3;
                        sort = "10";
                        page = 1;
                        httpPostShop("");
                        break;
                    case 3:
                        fragmentOneOtherTwoImage.setImageResource(R.mipmap.fragment_team_bottom);
                        headerFragmentOneNewSortTwoImage.setImageResource(R.mipmap.fragment_team_bottom);
                        positionTwo = 2;
                        sort = "11";
                        page = 1;
                        httpPostShop("");
                        break;
                }
                break;
            case R.id.fragment_one_other_three:
                positionTwo = 3;
                positionFour = 3;
                fragmentOneOtherTwoImage.setImageResource(R.mipmap.fragment_team_default);
                fragmentOneOtherFourImage.setImageResource(R.mipmap.fragment_team_default);
                headerFragmentOneNewSortTwoImage.setImageResource(R.mipmap.fragment_team_default);
                headerFragmentOneNewSortFourImage.setImageResource(R.mipmap.fragment_team_default);
                fragmentOneOtherOneText.setTextColor(0xff666666);
                fragmentOneOtherTwoText.setTextColor(0xff666666);
                fragmentOneOtherThreeText.setTextColor(getResources().getColor(R.color.mainColor));
                fragmentOneOtherFourText.setTextColor(0xff666666);
                headerFragmentOneNewSortOneText.setTextColor(0xff666666);
                headerFragmentOneNewSortTwoText.setTextColor(0xff666666);
                headerFragmentOneNewSortThreeText.setTextColor(getResources().getColor(R.color.mainColor));
                headerFragmentOneNewSortFourText.setTextColor(0xff666666);
                switch (positionThree) {
                    case 1:
                        fragmentOneOtherThreeImage.setImageResource(R.mipmap.fragment_team_bottom);
                        headerFragmentOneNewSortThreeImage.setImageResource(R.mipmap.fragment_team_bottom);
                        positionThree = 2;
                        sort = "31";
                        page = 1;
                        httpPostShop("");
                        break;
                    case 2:
                        fragmentOneOtherThreeImage.setImageResource(R.mipmap.fragment_team_top);
                        headerFragmentOneNewSortThreeImage.setImageResource(R.mipmap.fragment_team_top);
                        positionThree = 3;
                        sort = "30";
                        page = 1;
                        httpPostShop("");
                        break;
                    case 3:
                        fragmentOneOtherThreeImage.setImageResource(R.mipmap.fragment_team_bottom);
                        headerFragmentOneNewSortThreeImage.setImageResource(R.mipmap.fragment_team_bottom);
                        positionThree = 2;
                        sort = "31";
                        page = 1;
                        httpPostShop("");
                        break;
                }
                break;
            case R.id.fragment_one_other_four:
                positionTwo = 3;
                positionThree = 3;
                fragmentOneOtherTwoImage.setImageResource(R.mipmap.fragment_team_default);
                fragmentOneOtherThreeImage.setImageResource(R.mipmap.fragment_team_default);
                headerFragmentOneNewSortTwoImage.setImageResource(R.mipmap.fragment_team_default);
                headerFragmentOneNewSortThreeImage.setImageResource(R.mipmap.fragment_team_default);
                fragmentOneOtherOneText.setTextColor(0xff666666);
                fragmentOneOtherTwoText.setTextColor(0xff666666);
                fragmentOneOtherThreeText.setTextColor(0xff666666);
                fragmentOneOtherFourText.setTextColor(getResources().getColor(R.color.mainColor));
                headerFragmentOneNewSortOneText.setTextColor(0xff666666);
                headerFragmentOneNewSortTwoText.setTextColor(0xff666666);
                headerFragmentOneNewSortThreeText.setTextColor(0xff666666);
                headerFragmentOneNewSortFourText.setTextColor(getResources().getColor(R.color.mainColor));
                switch (positionFour) {
                    case 1:
                        fragmentOneOtherFourImage.setImageResource(R.mipmap.fragment_team_bottom);
                        headerFragmentOneNewSortFourImage.setImageResource(R.mipmap.fragment_team_bottom);
                        positionFour = 2;
                        sort = "01";
                        page = 1;
                        httpPostShop("");
                        break;
                    case 2:
                        fragmentOneOtherFourImage.setImageResource(R.mipmap.fragment_team_top);
                        headerFragmentOneNewSortFourImage.setImageResource(R.mipmap.fragment_team_top);
                        positionFour = 1;
                        sort = "00";
                        page = 1;
                        httpPostShop("");
                        break;
                    case 3:
                        fragmentOneOtherFourImage.setImageResource(R.mipmap.fragment_team_bottom);
                        headerFragmentOneNewSortFourImage.setImageResource(R.mipmap.fragment_team_bottom);
                        positionFour = 2;
                        sort = "01";
                        page = 1;
                        httpPostShop("");
                        break;
                }
                break;
        }
    }

    /**
     * 设置轮播图
     *
     * @param list
     */
    private void bannerImage(final ArrayList<Banner> list) {
        ArrayList<String> netWorkImage = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            netWorkImage.add(list.get(i).getAdvertisementpic());
        }
        headerFragmentOneOtherBanner.setPages(() -> new BannerHAdapter(), netWorkImage);
        headerFragmentOneOtherBanner.setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused});
        if (netWorkImage.size() > 1) {
            headerFragmentOneOtherBanner.setPointViewVisible(true);
            headerFragmentOneOtherBanner.startTurning(4000);
            headerFragmentOneOtherBanner.setCanLoop(true);
        } else {
            headerFragmentOneOtherBanner.setPointViewVisible(false);
            headerFragmentOneOtherBanner.setCanLoop(false);
        }
        headerFragmentOneOtherBanner.setOnItemClickListener(position -> {
            Utils.getJumpType(getActivity(),list.get(position).getJumptype(),list.get(position).getAdvertisementlink(),list.get(position).getNeedlogin(),list.get(position).getAdvertisemenid());
        });
    }


    private void httpPostShop(String time) {
        // 请求商品搜索（类目）
        paramMap.clear();
        paramMap.put("userid", login.getUserid());
        paramMap.put("shopclassone", position + "");
        paramMap.put("shopclasstwo", "");
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
        NetworkRequest.getInstance().POST(handler, paramMap, "ShopCategory", HttpCommon.ShopCategory);
        if (page == 1) {
            if (fragmentOneNewMask!=null){
                fragmentOneNewMask.setVisibility(View.VISIBLE);
            }
        }
    }

    private void httpPost() {
        // 请求二级类目
        paramMap.clear();
        paramMap.put("shopclassone", position + "");
        NetworkRequest.getInstance().POST(handler, paramMap, "CategoryTwo", HttpCommon.CategoryTwo);
        // 请求广告
        paramMap.clear();
        paramMap.put("advertisementposition", position + "");
        NetworkRequest.getInstance().POST(handler, paramMap, "Banner", HttpCommon.Banner);
    }
    private boolean isfirst=true;
    protected void initData(int pos) {
        position=pos;
        page = 1;
        // 网络请求
        if (page ==1&&isfirst){
            isfirst=false;
            showDialog();
        }
        httpPost();
        httpPostShop("");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (loginRefreshFlag&&OneFragment290.currPos>=2){
            loginRefreshFlag=false;
            onCustomized();
        }
    }
}
