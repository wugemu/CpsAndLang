package com.lxkj.dmhw.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
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
import com.lxkj.dmhw.data.SearchOpen;
import com.lxkj.dmhw.defined.BaseActivity;
import com.lxkj.dmhw.defined.ScaleTransitionPagerTitleView;
import com.lxkj.dmhw.fragment.SearchNewFragment;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.Utils;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MorePlSearchNewActivity extends BaseActivity implements TextView.OnEditorActionListener, TextWatcher {

    @BindView(R.id.bar)
    View bar;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.search_magic)
    MagicIndicator searchMagic;
    @BindView(R.id.search_edit)
    EditText searchEdit;
    @BindView(R.id.search_btn)
    LinearLayout searchBtn;
    @BindView(R.id.search_close_btn)
    LinearLayout searchCloseBtn;
    // 声明Fragment对象
    private SearchNewFragment searchNewFragment;
    private String[] magicName = {"淘宝", "拼多多","京东","唯品会","苏宁"};
    private int lastPos=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_search);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bar.setVisibility(View.GONE);
        }
        if (Variable.statusBarHeight>0) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) bar.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = Variable.statusBarHeight;
            bar.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        if (getIntent().hasExtra("title")) {
            setText(getIntent().getExtras().getString("title"));
            if (getIntent().getExtras().getString("title").equals("")) {
                searchCloseBtn.setVisibility(View.GONE);
            } else {
                searchCloseBtn.setVisibility(View.VISIBLE);
            }
        }
        plattype=getIntent().getStringExtra("platType");
        magic();
        searchEdit.setFocusable(true);
        searchEdit.setFocusableInTouchMode(true);
        searchEdit.requestFocus();

        searchNewFragment=SearchNewFragment.getInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment,searchNewFragment).commit();
        searchEdit.setOnEditorActionListener(this);
        searchEdit.addTextChangedListener(this);
        searchEdit.setFilters(new InputFilter[]{new SpaceFilter()});
    }

    @Override
    public void mainMessage(Message message) {
       if(message.what==LogicActions.RefreshSearchContentSuccess){
           insertandSaveLast20Item(message.obj.toString().trim());
       }
    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        if (message.what == LogicActions.AssociationSuccess) {
            ArrayList<String> list = (ArrayList<String>) message.obj;
            searchNewFragment.onTextChanged(list);
        }

    }
    private void magic() {
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
                        searchNewFragment.hideVedio(index);
                        //上一个控件自然沉降状态
                        searchMagic.onPageScrolled(lastPos,0f,0);
                        searchMagic.onPageSelected(index);
                        searchMagic.onPageScrollStateChanged(ViewPager.SCROLL_STATE_IDLE);
                        //当前控件释放状态（空闲状态）
                        searchMagic.onPageScrolled(index,0f,0);
                        searchMagic.onPageSelected(index);
                        searchMagic.onPageScrollStateChanged(ViewPager.SCROLL_STATE_SETTLING);

                        lastPos=index;

                        switch (index){
                            case 0:
                                plattype="0";
                                break;
                            case 1:
                                plattype="1";
                                break;
                            case 2:
                                plattype="2";
                                break;
                            case 3:
                                plattype="3";
                                break;
                            case 4:
                                plattype="5";
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
        switch (plattype){
            case "0":
                lastPos=0;
                searchMagic.onPageScrolled(0,0f,0);
                searchMagic.onPageSelected(0);
                searchMagic.onPageScrollStateChanged(ViewPager.SCROLL_STATE_IDLE);
                break;
            case "1":
                lastPos=1;
                searchMagic.onPageScrolled(1,0f,0);
                searchMagic.onPageSelected(1);
                searchMagic.onPageScrollStateChanged(ViewPager.SCROLL_STATE_IDLE);
                break;
            case "2":
                lastPos=2;
                searchMagic.onPageScrolled(2,0f,0);
                searchMagic.onPageSelected(2);
                searchMagic.onPageScrollStateChanged(ViewPager.SCROLL_STATE_IDLE);
                break;
            case "3":
                lastPos=3;
                searchMagic.onPageScrolled(3,0f,0);
                searchMagic.onPageSelected(3);
                searchMagic.onPageScrollStateChanged(ViewPager.SCROLL_STATE_IDLE);
                break;
            case "5":
                lastPos=4;
                searchMagic.onPageScrolled(4,0f,0);
                searchMagic.onPageSelected(4);
                searchMagic.onPageScrollStateChanged(ViewPager.SCROLL_STATE_IDLE);
                break;
        }
        searchMagic.onPageScrollStateChanged(ViewPager.SCROLL_STATE_IDLE);

    }

    @OnClick({R.id.back, R.id.search_btn, R.id.search_close_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                isFinish();
                break;
            case R.id.search_btn:
                search(false,"");
                break;
            case R.id.search_close_btn:
                setText("");
                break;
        }
    }

    /**
     * 设置按钮是否可用
     *
     * @param isCheck
     */
    public void textEnabled(boolean isCheck) {
        searchEdit.setEnabled(isCheck);
    }

    /**
     * 写入查询
     *
     * @param content
     */
    public void setText(String content) {
        searchEdit.setText(content.trim());
        searchEdit.setSelection(searchEdit.getText().length());
    }

    /**
     * 搜索
     */
    public String plattype="0";
    public void search(Boolean IsGetSearchThinkList,String otherContent) {
        String content="";
        if (IsGetSearchThinkList){
            content= otherContent;
        }else{
            content = searchEdit.getText().toString();
        }

        if (content.equals("")) {
            toast("请输入商品名或关键字");
        } else {
           Intent intent = null;
           intent = new Intent(this, MorePlSearchListActivity.class);
           intent.putExtra("search", content.trim());
           intent.putExtra("paltform",plattype);
           Utils.hideSoftInput(this, null);
           startActivity(intent);

           //先跳转搜索再插入数据库
            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("RefreshSearchContent"), content, 0);
    }

    }

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
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // 输入前的监听
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // 输入的内容变化的监听
    }

    @Override
    public void afterTextChanged(Editable s) {
        // 输入后的监听
        if (s.toString().equals("")) {
            searchCloseBtn.setVisibility(View.GONE);
            searchNewFragment.onTextChanged(null);
        } else {
            searchCloseBtn.setVisibility(View.VISIBLE);
            paramMap = new HashMap<>();
            paramMap.put("search", s.toString());
            NetworkRequest.getInstance().POST(handler, paramMap, "Association", HttpCommon.Association);
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        search(false,"");
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
            if (source.equals(" ")&&searchEdit.getText().length()<=0)
                return "";
            return null;
        }
    }
}
