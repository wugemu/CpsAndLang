package com.lxkj.dmhw.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.MorePlSearchNewActivity;
import com.lxkj.dmhw.activity.VideoActivity;
import com.lxkj.dmhw.adapter.AutoCompleteAdapter;
import com.lxkj.dmhw.adapter.SearchHistoryAdapter;
import com.lxkj.dmhw.adapter.SearchHotAdapter;
import com.lxkj.dmhw.bean.Banner;
import com.lxkj.dmhw.bean.H5Link;
import com.lxkj.dmhw.data.SearchOpen;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.defined.LinearItemDecoration;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.InternalMessage;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.MyLayoutManager;
import com.lxkj.dmhw.utils.Utils;
import com.lxkj.dmhw.view.ScaleLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 搜索APP
 */
public class SearchNewFragment extends BaseFragment implements SearchHotAdapter.OnClickListener, SearchHistoryAdapter.OnClickListener, AdapterView.OnItemClickListener {

    @BindView(R.id.search_recycler)
    RecyclerView searchRecycler;
    @BindView(R.id.search_layout)
    LinearLayout searchLayout;
    @BindView(R.id.search_association_list)
    ListView searchAssociationList;
    @BindView(R.id.search_clean_layout)
    LinearLayout search_clean_layout;

    @BindView(R.id.more_history)
    ImageView more_history;

    @BindView(R.id.search_history_recycler)
    RecyclerView searchHistoryRecycler;
    @BindView(R.id.search_history_layout)
    LinearLayout searchHistoryLayout;
    private SearchHotAdapter hotAdapter;
    private SearchHistoryAdapter historyAdapter;
    private AutoCompleteAdapter autoCompleteAdapter;
    private SearchOpen open = new SearchOpen();

    private String PlatType="0";


    @BindView(R.id.search_recycler_layout)
    LinearLayout search_recycler_layout;
    //视频教程
    @BindView(R.id.search_video)
    ScaleLayout search_video;
    @BindView(R.id.search_video_img)
    ImageView search_video_img;

    @BindView(R.id.search_hot_title)
    TextView search_hot_title;
    @BindView(R.id.search_his_title)
    TextView search_his_title;
    TextPaint textPaint;

    ArrayList<H5Link> h5Links=new ArrayList<>();

    public static SearchNewFragment getInstance() {
        SearchNewFragment fragment = new SearchNewFragment();
        return fragment;
    }

    @Override
    public void onData() {
    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {
        searchRecycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity()));
        searchHistoryRecycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity()));
        hotAdapter = new SearchHotAdapter(getActivity());
        historyAdapter = new SearchHistoryAdapter(getActivity());
        autoCompleteAdapter = new AutoCompleteAdapter(getActivity());
        searchRecycler.addItemDecoration(new LinearItemDecoration(Utils.dipToPixel(R.dimen.dp_5), Utils.dipToPixel(R.dimen.dp_15), 0,0));
        searchHistoryRecycler.addItemDecoration(new LinearItemDecoration(Utils.dipToPixel(R.dimen.dp_5), Utils.dipToPixel(R.dimen.dp_15), 0, 0));
        searchRecycler.setAdapter(hotAdapter);
        searchRecycler.setNestedScrollingEnabled(false);
        searchHistoryRecycler.setAdapter(historyAdapter);
        searchHistoryRecycler.setNestedScrollingEnabled(false);

        searchAssociationList.setAdapter(autoCompleteAdapter);
        searchAssociationList.setOnItemClickListener(this);
        hotAdapter.setOnClickListener(this);
        historyAdapter.setOnClickListener(this);

        textPaint=search_hot_title.getPaint();
        textPaint.setFakeBoldText(true);

        textPaint=search_his_title.getPaint();
        textPaint.setFakeBoldText(true);

    }

    @Override
    public void onCustomized() {
        InternalMessage.getInstance().eventMessage(LogicActions.getActionsSuccess("ReadHistoryContent"), false, 0);

        //视频教程
        paramMap.clear();
        paramMap.put("type", "9");
        NetworkRequest.getInstance().POST(handler, paramMap, "MerchantWeb", HttpCommon.GetMerchantWeb);

        //热搜
        paramMap.clear();
        paramMap.put("userid", login.getUserid());
        paramMap.put("advertisementposition", "62");
        NetworkRequest.getInstance().POST(handler, paramMap, "SearchHot", HttpCommon.Banner);
    }

    @Override
    public void mainMessage(Message message) {
        if (message.what == LogicActions.ReadHistoryContentSuccess) {
            history();
        }

    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        if (message.what == LogicActions.SearchHotSuccess) {
            ArrayList<Banner> list= (ArrayList<Banner>) message.obj;
            if(list.size()>0){
                search_recycler_layout.setVisibility(View.VISIBLE);
                hotAdapter.setNewData(list);
                hotAdapter.notifyDataSetChanged();
            }else{
                search_recycler_layout.setVisibility(View.GONE);
            }
        }

        if (message.what == LogicActions.MerchantWebSuccess) {
             h5Links = (ArrayList<H5Link>) message.obj;
            if (h5Links.size()>0){
                search_video.setVisibility(View.VISIBLE);
                if (!h5Links.get(0).getUrl().equals("")){
                    search_video.setVisibility(View.VISIBLE);
                    Utils.displayImage(getActivity(),h5Links.get(0).getUrl(),search_video_img);
                }else{
                    search_video.setVisibility(View.GONE);
                }
            }else{
                search_video.setVisibility(View.GONE);
            }
        }

    }

    /**
     * 历史记录
     */
    public void history() {
        ArrayList<String> search = new ArrayList<>();
        search=open.find(PlatType);
        if (search!=null&&search.size()>0){
            searchHistoryLayout.setVisibility(View.VISIBLE);
            historyAdapter.setNewData(search);
            historyAdapter.notifyDataSetChanged();
        }else{
            searchHistoryLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onHotClick(Banner content) {
        if (content.getJumptype().equals("02")){
            ((MorePlSearchNewActivity) getActivity()).search(true,content.getTitle().trim());
        }else{
           Utils.getJumpType(getActivity(),content.getJumptype(),content.getAdvertisementlink(),content.getNeedlogin(),content.getAdvertisemenid());
        }
    }

    @Override
    public void onHistoryClick(String content) {
        ((MorePlSearchNewActivity) getActivity()).search(true,content.trim());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ((MorePlSearchNewActivity) getActivity()).setText(autoCompleteAdapter.getItem(position) + "");
        ((MorePlSearchNewActivity) getActivity()).search(true,autoCompleteAdapter.getItem(position) + "");
    }

    /**
     * 输入的内容变化
     */
    public void onTextChanged(ArrayList<String> list) {
        if (list == null) {
            searchAssociationList.setVisibility(View.GONE);
        } else {
            if (list.size() > 0) {
                searchAssociationList.setVisibility(View.VISIBLE);
                autoCompleteAdapter.setData(list);
            } else {
                searchAssociationList.setVisibility(View.GONE);
            }
        }
    }

    @OnClick({R.id.search_clean_layout,R.id.more_history,R.id.search_video})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.search_clean_layout:
                open.delete(PlatType);
                historyAdapter.setNewData(new ArrayList<>());
                historyAdapter.notifyDataSetChanged();
                searchHistoryLayout.setVisibility(View.GONE);
                break;
            case R.id.more_history:
                break;
            case R.id.search_video:
                if(h5Links.size()>1&&!h5Links.get(1).getUrl().equals("")) {
                    Intent intent = new Intent(getActivity(), VideoActivity.class);
                    intent.putExtra("videoUrl", h5Links.get(1).getUrl());
                    intent.putExtra("title", h5Links.get(1).getTitle());
                    getActivity().startActivity(intent);
                }
                break;
        }

    }

    //隐藏视频教程
    public void hideVedio(int index){
      if (index!=0){
          search_video.setVisibility(View.GONE);
      }else{
          search_video.setVisibility(View.VISIBLE);
      }
    }

}
