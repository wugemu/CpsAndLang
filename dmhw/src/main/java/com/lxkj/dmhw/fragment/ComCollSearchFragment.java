package com.lxkj.dmhw.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.NewHandListActivity;
import com.lxkj.dmhw.adapter.SearchComCollHotAdapter;
import com.lxkj.dmhw.adapter.SearchHistoryAdapter;
import com.lxkj.dmhw.bean.Banner;
import com.lxkj.dmhw.data.ComCollSearchOpen;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.defined.LinearItemDecoration;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.MyLayoutManager;
import com.lxkj.dmhw.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商学院搜索
 */
public class ComCollSearchFragment extends BaseFragment implements SearchComCollHotAdapter.OnClickListener, SearchHistoryAdapter.OnClickListener,TextView.OnEditorActionListener{

    @BindView(R.id.search_recycler)
    RecyclerView searchRecycler;
    @BindView(R.id.search_layout)
    LinearLayout searchLayout;

    @BindView(R.id.search_clean_layout)
    LinearLayout search_clean_layout;

    @BindView(R.id.more_history)
    ImageView more_history;

    @BindView(R.id.search_history_recycler)
    RecyclerView searchHistoryRecycler;
    @BindView(R.id.search_history_layout)
    LinearLayout searchHistoryLayout;
    private SearchComCollHotAdapter hotAdapter;
    private SearchHistoryAdapter historyAdapter;
    private ComCollSearchOpen open = new ComCollSearchOpen();

    @BindView(R.id.search_recycler_layout)
    LinearLayout search_recycler_layout;
    @BindView(R.id.cancel_btn)
    LinearLayout cancel_btn;
    @BindView(R.id.search_edit)
    EditText searchEdit;

    @BindView(R.id.hot_title)
    TextView hot_title;
    @BindView(R.id.history_title)
    TextView history_title;
    TextPaint tp;
    public static ComCollSearchFragment getInstance() {
        ComCollSearchFragment fragment = new ComCollSearchFragment();
        return fragment;
    }

    @Override
    public void onData() {
    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comcoll_search, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {
        tp=hot_title.getPaint();
        tp.setFakeBoldText(true);
        tp=history_title.getPaint();
        tp.setFakeBoldText(true);
        searchRecycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity()));
        searchHistoryRecycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity()));
        hotAdapter = new SearchComCollHotAdapter(getActivity());
        historyAdapter = new SearchHistoryAdapter(getActivity());
        searchRecycler.addItemDecoration(new LinearItemDecoration(Utils.dipToPixel(R.dimen.dp_5), Utils.dipToPixel(R.dimen.dp_15), 0, 0));
        searchHistoryRecycler.addItemDecoration(new LinearItemDecoration(Utils.dipToPixel(R.dimen.dp_5), Utils.dipToPixel(R.dimen.dp_15), 0, 0));
        searchRecycler.setAdapter(hotAdapter);
        searchRecycler.setNestedScrollingEnabled(false);
        searchHistoryRecycler.setAdapter(historyAdapter);
        searchHistoryRecycler.setNestedScrollingEnabled(false);

        hotAdapter.setOnClickListener(this);
        historyAdapter.setOnClickListener(this);

        searchEdit.setOnEditorActionListener(this);
        searchEdit.setFocusable(true);
        searchEdit.setFocusableInTouchMode(true);
        searchEdit.requestFocus();
        searchEdit.setFilters(new InputFilter[]{new SpaceFilter()});
    }

    @Override
    public void onCustomized() {
        InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ReadHistoryContent"), false, 0);

        //热搜
        paramMap = new HashMap<>();
        paramMap.put("userid", login.getUserid());
        paramMap.put("advertisementposition", "64");
        NetworkRequest.getInstance().POST(handler, paramMap, "SearchHot", HttpCommon.Banner);
    }

    @Override
    public void mainMessage(Message message) {
        if (message.what == LogicActions.ReadHistoryContentSuccess) {
            history();
        }
        if(message.what==LogicActions.RefreshSearchContentSuccess){
            insertandSaveLast20Item(message.obj.toString());
        }
    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        if (message.what == LogicActions.SearchHotSuccess) {
            ArrayList<Banner> list = (ArrayList<Banner>) message.obj;
            if (list.size() > 0) {
                search_recycler_layout.setVisibility(View.VISIBLE);
                hotAdapter.setNewData(list);
                hotAdapter.notifyDataSetChanged();
            } else {
                search_recycler_layout.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 历史记录
     */
    public void history() {
        ArrayList<String> search = new ArrayList<>();
        search = open.find();
        if (search != null && search.size() > 0) {
            searchHistoryLayout.setVisibility(View.VISIBLE);
            historyAdapter.setNewData(search);
        } else {
            searchHistoryLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onHotClick(Banner content) {
        if (content.getJumptype().equals("02")) {
            search(content.getTitle().trim());
        } else {
            Utils.getJumpType(getActivity(), content.getJumptype(), content.getAdvertisementlink(), content.getNeedlogin(), content.getAdvertisemenid());
        }
    }

    @Override
    public void onHistoryClick(String content) {
        search(content.trim());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


    @OnClick({R.id.search_clean_layout,R.id.cancel_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search_clean_layout:
                open.delete();
                historyAdapter.setNewData(new ArrayList<>());
                historyAdapter.notifyDataSetChanged();
                searchHistoryLayout.setVisibility(View.GONE);
                break;
            case R.id.cancel_btn:
              isFinish();
                break;

        }

    }

    //搜索
    public void search(String content) {
        if (content.equals("")) {
            toast("请输入关键字");
        } else {
            Intent intent=new Intent(getActivity(), NewHandListActivity.class);
            intent.putExtra("from","partSearch");
            intent.putExtra("id",content);
            intent.putExtra("title",content);
            startActivity(intent);

            //先跳转搜索再插入数据库
            InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("RefreshSearchContent"), content, 0);
        }
    }

    //插入数据库
    private void insertandSaveLast20Item(String content){
        boolean isCheck = open.ishas(content) == 0;
        if (!isCheck) {
            open.deleteItem(content);
        }
        open.add(content);

        if (open.count() >= 21) {
            open.Save20Content();
        }
        InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ReadHistoryContent"), false, 0);
    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        search(searchEdit.getText().toString().trim());
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
