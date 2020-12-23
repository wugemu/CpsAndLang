package com.lxkj.dmhw.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.adapter.FragmentAdapter;
import com.lxkj.dmhw.bean.CommodityRatio;
import com.lxkj.dmhw.data.SearchOpen;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.defined.ScaleTransitionPagerTitleView;
import com.lxkj.dmhw.dialog.SearchTaskDialog;
import com.lxkj.dmhw.fragment.SearchListFragment;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.Utils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MorePlSearchListActivity extends BaseActivity implements TextView.OnEditorActionListener {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    View back;
    @BindView(R.id.search_list_btn)
    View search_list_btn;
    @BindView(R.id.del_img)
    View del_img;

    @BindView(R.id.search_content)
    ViewPager searchContent;
    @BindView(R.id.search_magic)
    MagicIndicator searchMagic;
    @BindView(R.id.network_list_edit)
    EditText network_list_edit;

    // 声明Fragment对象
    private SearchListFragment searchFragment0;
    private SearchListFragment searchFragment1;
    private SearchListFragment searchFragment2;
    private SearchListFragment searchFragment3;
    private SearchListFragment searchFragment5;
    // 声明FragmentAdapter对象
    private FragmentAdapter fragmentAdapter;
    // 声明FragmentManager对象
    private FragmentManager fragmentManager;
    private String[] magicName = {"淘宝", "拼多多","京东","唯品会","苏宁"};
    private String platform="0";
    private int posIndex=0;
    public static MorePlSearchListActivity morePlSearchListActivity=null;
    public static int currentType=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_pl_search_list);
        ButterKnife.bind(this);
        morePlSearchListActivity=this;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        if (!Objects.equals(login.getUsertype(), "3")) {
            //获取收入比例
            paramMap.clear();
            paramMap.put("userid", login.getUserid());
            NetworkRequest.getInstance().POST(handler, paramMap, "GetRatio", HttpCommon.GetRatio);
        }
        if (getIntent().getBooleanExtra("isTask",false)){
            finishTask("search");
        }
        network_list_edit.setOnEditorActionListener(this);
        network_list_edit.setText(getIntent().getExtras().getString("search"));
        if (!getIntent().getExtras().getString("search").equals("")){
          del_img.setVisibility(View.VISIBLE);
          network_list_edit.setSelection(network_list_edit.getText().length());//将光标移至文字末尾
        }else{
          del_img.setVisibility(View.GONE);
        }
        platform=getIntent().getStringExtra("paltform");
        posIndex=Integer.parseInt(platform);
        currentType=Integer.parseInt(platform);
        String content = getIntent().getExtras().getString("search");

        if (!content.equals("")){//保存历史记录
            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("RefreshSearchContent"), content, 0);
        }
        network_list_edit.setFilters(new InputFilter[]{new SpaceFilter()});
        network_list_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
             if (s.length()>0){
                 del_img.setVisibility(View.VISIBLE);
             }else{
                 del_img.setVisibility(View.GONE);
             }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        magic(content.trim());
    }
    private void magic(String content) {
        // 初始化FragmentManager对象
        fragmentManager = getSupportFragmentManager();
        // 初始化Fragment页面

        ArrayList<Fragment> fragments = new ArrayList<>();
        searchFragment0 = SearchListFragment.getInstance(0,content);
        searchFragment1 = SearchListFragment.getInstance(1,content);
        searchFragment2 = SearchListFragment.getInstance(2,content);
        searchFragment3 = SearchListFragment.getInstance(3,content);
        searchFragment5 = SearchListFragment.getInstance(5,content);
        fragments.add(searchFragment0);
        fragments.add(searchFragment1);
        fragments.add(searchFragment2);
        fragments.add(searchFragment3);
        fragments.add(searchFragment5);
        fragmentAdapter = new FragmentAdapter(fragmentManager, fragments);
        searchContent.setOffscreenPageLimit(5);
        searchContent.setAdapter(fragmentAdapter);
        searchContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }
            @Override
            public void onPageSelected(int index) {
                posIndex=index;
                switch (index){
                    case 0:
                        currentType=0;
                        searchFragment0.onResume();
                        break;
                    case 1:
                        currentType=1;
                        searchFragment1.onResume();
                        break;
                    case 2:
                        currentType=2;
                        searchFragment2.onResume();
                        break;
                    case 3:
                        currentType=3;
                        searchFragment3.onResume();
                        break;
                    case 4:
                        currentType=5;
                        searchFragment5.onResume();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return magicName.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(magicName[index]);
                simplePagerTitleView.setNormalColor(Color.parseColor("#333333"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#000000"));
                simplePagerTitleView.setTextSize(17);
                ((ScaleTransitionPagerTitleView) simplePagerTitleView).setMinScale(0.88f);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        searchContent.setCurrentItem(index);
                        posIndex=index;
                        switch (index){
                            case 0:
                              currentType=0;
                              searchFragment0.onResume();
                                break;
                            case 1:
                              currentType=1;
                              searchFragment1.onResume();
                                break;
                            case 2:
                              currentType=2;
                              searchFragment2.onResume();
                                break;
                            case 3:
                               currentType=3;
                               searchFragment3.onResume();
                                break;
                            case 4:
                               currentType=5;
                               searchFragment5.onResume();
                                break;
                        }
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineWidth(Utils.dipToPixel(R.dimen.dp_25));
                indicator.setLineHeight(Utils.dipToPixel(R.dimen.dp_2));
                indicator.setRoundRadius(R.dimen.dp_1);
                indicator.setColors(Color.parseColor("#000000"));
                return indicator;
            }
        });
        searchMagic.setNavigator(commonNavigator);
        ViewPagerHelper.bind(searchMagic, searchContent);
      switch (platform){
          case "0":
              searchContent.setCurrentItem(0);
          break;
          case "1":
              searchContent.setCurrentItem(1);
              break;
          case "2":
              searchContent.setCurrentItem(2);
              break;
          case "3":
              searchContent.setCurrentItem(3);
              break;
          case "5":
              searchContent.setCurrentItem(4);
              break;
      }

    }
    @Override
    public void mainMessage(Message message) {
        if (message.what == LogicActions.ShareStatusSuccess&&message.arg1==1) {
            paramMap.clear();
            paramMap.put("type", "shareshop");
            NetworkRequest.getInstance().POST(handler, paramMap, "DayBuy", HttpCommon.finishTask);
        }

        if(message.what==LogicActions.RefreshSearchContentSuccess){
            insertandSaveLast20Item(message.obj.toString().trim());
        }
    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        if (message.what == LogicActions.GetRatioSuccess) {
            CommodityRatio ratio = (CommodityRatio) message.obj;
            Variable.ration = ratio.getRatio();
            Variable.superRatio = ratio.getSuperratio();
        }

        //搜索标题完成任务接口
        if (message.what == LogicActions.SearchTaskSuccess) {
            //搜索弹框
            JSONObject obj=(JSONObject) message.obj;
            try {
                if (!obj.getString("alertmsg").equals("")){
                    SearchTaskDialog dialog1 = new SearchTaskDialog(this);
                    dialog1.showDialog(obj.getString("alertmsg"));
                    InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ShareFinish"), false, 0);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        //完成任务
        if (message.what == LogicActions.DayBuySuccess) {
            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ShareFinish"), false, 0);
        }
    }

    @OnClick({R.id.back,R.id.search_list_btn,R.id.del_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                isFinish();
                break;
            case R.id.search_list_btn:
                String content = network_list_edit.getText().toString();
                if (content.equals("")) {
                    toast("请输入商品名或关键字");
                    return;
                }
                magic(content.trim());
                searchContent.setCurrentItem(posIndex);
                InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("RefreshSearchContent"), content, 0);
                break;
            case R.id.del_img:
                network_list_edit.setText("");
                break;
        }
    }

    //完成搜索任务
    public  void finishTask(String type){
        paramMap.clear();
        paramMap.put("type", type);
        NetworkRequest.getInstance().POST(handler, paramMap, "SearchTask", HttpCommon.finishTask);
    }

    //保存搜索数据
    private  String contentType="0";
    //插入数据库
    private void insertandSaveLast20Item(String content){
        SearchOpen open = new SearchOpen();
        boolean isCheck=open.ishas(content, "0") == 0;
        if (!isCheck){
            open.deleteItem(content,"0");
        }
        open.add(content,"0");

        if (open.count("0") >= 21) {
            open.Save20Content();
        }
        InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ReadHistoryContent"), false, 0);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        String content = network_list_edit.getText().toString();
        if (content.equals("")) {
            toast("请输入商品名或关键字");
            return false;
        }
        magic(content.trim());
        searchContent.setCurrentItem(posIndex);
        InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("RefreshSearchContent"), content, 0);
        return true;
    }

    /**
     * 禁止输入空格
     *
     * @return
     */
    public class SpaceFilter implements InputFilter {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            //返回null表示接收输入的字符,返回空字符串表示不接受输入的字符
            if (source.equals(" ")&&network_list_edit.getText().length()<=0)
                return "";
            return null;
        }
    }
}
