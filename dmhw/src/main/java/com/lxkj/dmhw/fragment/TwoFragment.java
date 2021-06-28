package com.lxkj.dmhw.fragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.bean.H5Link;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.adapter.FragmentAdapter;

import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.myinterface.HomeI;
import com.lxkj.dmhw.utils.NetStateUtils;
import com.lxkj.dmhw.utils.Utils;

import net.lucode.hackware.magicindicator.MagicIndicator;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 首页第二页
 * Created by Administrator on 2017/11/30 0030.
 */

public class TwoFragment extends BaseFragment {

    @BindView(R.id.status_bar)
    View statusBar;
    @BindView(R.id.fragment_two_magic)
    MagicIndicator fragmentTwoMagic;
    @BindView(R.id.fragment_two_content)
    ViewPager fragmentTwoContent;
    @BindView(R.id.network_mask)
    LinearLayout rl_network_mask;
    @BindView(R.id.recommend_btn)
    LinearLayout recommend_btn;
    @BindView(R.id.market_btn)
    LinearLayout market_btn;
    @BindView(R.id.shang_btn)
    LinearLayout shang_btn;
    @BindView(R.id.recommend_btn_txt)
    TextView recommend_btn_txt;
    @BindView(R.id.market_btn_txt)
    TextView market_btn_txt;
    @BindView(R.id.shang_btn_text)
    TextView shang_btn_text;
    @BindView(R.id.top_split_one)
    TextView top_split_one;
    @BindView(R.id.top_split_two)
    TextView top_split_two;

    @BindView(R.id.recommend_btn_arrow)
    LinearLayout recommend_btn_arrow;
    @BindView(R.id.market_btn_arrow)
    LinearLayout market_btn_arrow;
    @BindView(R.id.shang_btn_arrow)
    LinearLayout shang_btn_arrow;
    @BindView(R.id.top_layout)
    RelativeLayout top_layout;
    @BindView(R.id.top_split_one_layout)
    RelativeLayout top_split_one_layout;

    @BindView(R.id.top_split_two_layout)
    RelativeLayout top_split_two_layout;

    // 声明FragmentAdapter对象
    private FragmentAdapter fragmentAdapter;
    // 声明FragmentManager对象
    private FragmentManager fragmentManager;
    // 声明Fragment对象
    private TwoFragment_RecommendAll twoFragmentRecommend;
    private TwoFragment_MarketContent twoFragmentMarketing;
    private CommercialCollegeFragment twoFragmentCommercial;
    private WebViewFragment webViewFragment = null;
    private ArrayList<H5Link> magicName;
    public static int currPos=0;
    private HomeI homeI;

    @Override
    public void onData() {

    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, null);
        ButterKnife.bind(this, view);
        recommend_btn_arrow.setVisibility(View.VISIBLE);
        market_btn_arrow.setVisibility(View.INVISIBLE);
        shang_btn_arrow.setVisibility(View.INVISIBLE);
        homeI=(HomeI) getActivity();
        return view;
    }

    @Override
    public void onEvent() {
        rl_network_mask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCustomized();
            }
        });

    }

    @Override
    public void onCustomized() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            statusBar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0){
            LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) statusBar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            statusBar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }

        if (NetStateUtils.isNetworkConnected(getActivity())) {
            recommend_btn.setEnabled(true);
            market_btn.setEnabled(true);
            shang_btn.setEnabled(true);
            rl_network_mask.setVisibility(View.GONE);
            //批量存图可见
            Variable.isVisible = 1;
            // 获取H5地址
            paramMap.clear();
            paramMap.put("type", "1");
            NetworkRequest.getInstance().POST(handler, paramMap, "GetH5", HttpCommon.GetH5);
        }else{
            toast(getResources().getString(R.string.net_work_unconnect));
            rl_network_mask.setVisibility(View.VISIBLE);
            recommend_btn.setEnabled(false);
            market_btn.setEnabled(false);
            shang_btn.setEnabled(false);
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
        // 获取H5地址
        if (message.what == LogicActions.GetH5Success) {
            ArrayList<H5Link> h5Links = (ArrayList<H5Link>) message.obj;
            magicName = new ArrayList<>();
            H5Link h5Link = new H5Link();
            h5Link.setTitle("爆款推荐");
            magicName.add(h5Link);
            h5Link = new H5Link();
            h5Link.setTitle("营销素材");
            magicName.add(h5Link);
            magicName.addAll(h5Links);

            recommend_btn_txt.setText(magicName.get(0).getTitle());
            market_btn_txt.setText(magicName.get(1).getTitle());
            // 初始化FragmentManager对象
            fragmentManager = getChildFragmentManager();
            // 初始化Fragment页面
            ArrayList<Fragment> fragments = new ArrayList<>();
            twoFragmentRecommend = TwoFragment_RecommendAll.getInstance();
            twoFragmentMarketing = TwoFragment_MarketContent.getInstance();
            twoFragmentCommercial= new CommercialCollegeFragment();
            fragments.add(twoFragmentRecommend);
            fragments.add(twoFragmentMarketing);

            if (magicName!=null&&magicName.size()<3){
                LinearLayout.LayoutParams linearParamsOne =(LinearLayout.LayoutParams)top_layout.getLayoutParams();
                linearParamsOne.setMargins(Utils.dipToPixel(R.dimen.dp_20),0,Utils.dipToPixel(R.dimen.dp_20),0);
                top_layout.setLayoutParams(linearParamsOne);
                top_split_one_layout.setVisibility(View.GONE);
                top_split_two_layout.setVisibility(View.GONE);
                shang_btn.setVisibility(View.GONE);
                shang_btn_arrow.setVisibility(View.GONE);
                market_btn.setBackgroundResource(R.drawable.recommend_tab_right_bg);
            }
            for (int i = 2; i < magicName.size(); i++) {
                shang_btn_text.setText(magicName.get(2).getTitle());
//                webViewFragment = WebViewFragment.getInstance(magicName.get(i).getUrl());
                fragments.add(twoFragmentCommercial);
            }
            fragmentAdapter = new FragmentAdapter(fragmentManager, fragments);
            fragmentTwoContent.setAdapter(fragmentAdapter);
            fragmentTwoContent.setOffscreenPageLimit(fragments.size());
            fragmentTwoContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (position==2){
                    WebViewFragment.isTouchPager=false;
                    }
                 changeTopTab(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });


        }
        //完成任务
        if (message.what == LogicActions.DayBuySuccess) {
            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ShareFinish"), false, 0);
        }
    }

    @OnClick({R.id.recommend_btn, R.id.market_btn, R.id.shang_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.recommend_btn:
                currPos=0;
                isshare=true;
                changeTopTab(0);
                if (twoFragmentRecommend!=null){
                    twoFragmentRecommend.refresh();
                }
                break;
            case R.id.market_btn:
                currPos=1;
                isshare=false;
                changeTopTab(1);
                if (twoFragmentMarketing!=null){
                    twoFragmentMarketing.refresh();
                }
                break;
            case R.id.shang_btn:
//                if(homeI!=null){
//                    homeI.click(3);
//                }
                currPos=2;
                isshare=false;
                changeTopTab(2);
                break;
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    private void  changeTopTab(int pos) {
        switch (pos) {
            case 0:
                fragmentTwoContent.setCurrentItem(pos);
                recommend_btn_arrow.setVisibility(View.VISIBLE);
                market_btn_arrow.setVisibility(View.INVISIBLE);
                top_split_one.setVisibility(View.GONE);
                top_split_two.setVisibility(View.VISIBLE);
                if (magicName!=null&&magicName.size()<3) {
                    shang_btn_arrow.setVisibility(View.GONE);
                    top_split_two.setVisibility(View.GONE);
                    market_btn.setBackgroundResource(R.drawable.recommend_tab_right_bg);
                }else{
                    shang_btn_arrow.setVisibility(View.INVISIBLE);
                    market_btn.setBackgroundResource(R.drawable.recommend_tab_middle_bg);
                }
                recommend_btn.setBackgroundResource(R.drawable.recommend_tab_check_left_bg);
                shang_btn.setBackgroundResource(R.drawable.recommend_tab_right_bg);
                recommend_btn_txt.setTextColor(Color.parseColor("#ffffff"));
                market_btn_txt.setTextColor(Color.parseColor("#666666"));
                shang_btn_text.setTextColor(Color.parseColor("#666666"));

                break;
            case 1:
                fragmentTwoContent.setCurrentItem(pos);
                recommend_btn_arrow.setVisibility(View.INVISIBLE);
                market_btn_arrow.setVisibility(View.VISIBLE);
                top_split_one.setVisibility(View.GONE);
                top_split_two.setVisibility(View.GONE);
                recommend_btn.setBackgroundResource(R.drawable.recommend_tab_left_bg);
                if (magicName!=null&&magicName.size()<3){
                    shang_btn_arrow.setVisibility(View.GONE);
                    market_btn.setBackgroundResource(R.drawable.recommend_tab_right_check_bg);
                }else{
                    shang_btn_arrow.setVisibility(View.INVISIBLE);
                    market_btn.setBackgroundResource(R.drawable.recommend_tab_middle_check_bg);
                }
                shang_btn.setBackgroundResource(R.drawable.recommend_tab_right_bg);
                recommend_btn_txt.setTextColor(Color.parseColor("#666666"));
                market_btn_txt.setTextColor(Color.parseColor("#ffffff"));
                shang_btn_text.setTextColor(Color.parseColor("#666666"));
                break;
            case 2:
                fragmentTwoContent.setCurrentItem(pos);
                recommend_btn_arrow.setVisibility(View.INVISIBLE);
                market_btn_arrow.setVisibility(View.INVISIBLE);
                shang_btn_arrow.setVisibility(View.VISIBLE);
                top_split_one.setVisibility(View.VISIBLE);
                top_split_two.setVisibility(View.GONE);
                market_btn.setBackgroundResource(R.drawable.recommend_tab_middle_bg);
                recommend_btn.setBackgroundResource(R.drawable.recommend_tab_left_bg);
                shang_btn.setBackgroundResource(R.drawable.recommend_tab_right_check_bg);
                recommend_btn_txt.setTextColor(Color.parseColor("#666666"));
                market_btn_txt.setTextColor(Color.parseColor("#666666"));
                shang_btn_text.setTextColor(Color.parseColor("#ffffff"));
                break;

        }
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            // fragment隐藏时调用
            //控制推荐接受广播不弹授权框
            Variable.AuthShowStatus=true;
            isshare=false;
        } else {
            // fragment显示时调用
            //控制推荐接受广播不弹授权框
            Variable.AuthShowStatus=false;
            isshare=true;
        }
    }
}
