package com.lxkj.dmhw.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.Variable;
import com.lxkj.dmhw.activity.AliAuthWebViewActivity;
import com.lxkj.dmhw.activity.ImageActivity;
import com.lxkj.dmhw.activity.LoginActivity;
import com.lxkj.dmhw.activity.MainActivity;
import com.lxkj.dmhw.activity.VideoActivity;
import com.lxkj.dmhw.adapter.MarketingAdapter;
import com.lxkj.dmhw.bean.Marketing;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.defined.BaseFragment;
import com.lxkj.dmhw.dialog.OneKeyShareDialog;
import com.lxkj.dmhw.logic.HttpCommon;
import com.lxkj.dmhw.logic.LogicActions;
import com.lxkj.dmhw.logic.NetworkRequest;
import com.lxkj.dmhw.utils.MyLayoutManager;
import com.lxkj.dmhw.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 营销素材
 */

public class TwoFragment_MarketingAll extends BaseFragment implements PtrHandler, BaseQuickAdapter.RequestLoadMoreListener,MarketingAdapter.OnItemImageClickListener{

    @BindView(R.id.fragment_two_recycler)
    RecyclerView fragmentTwoRecycler;
    @BindView(R.id.load_more_ptr_frame)
    PtrClassicFrameLayout loadMorePtrFrame;
    private MarketingAdapter adapter;
    private Marketing marketing = new Marketing();
    private String type = "";
    private int  currPos = 100;

    public static TwoFragment_MarketingAll getInstance(int pos,String key) {
        TwoFragment_MarketingAll fragment = new TwoFragment_MarketingAll();
        Bundle bundle = new Bundle();
        bundle.putInt("currPos",pos);
        bundle.putString("key", key);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            currPos=bundle.getInt("currPos");
            type = bundle.getString("key");
        }
    }

    @Override
    public View onInit(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two_marketing, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onEvent() {
        // 设置Recycler
        fragmentTwoRecycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(getActivity(), false));
        adapter = new MarketingAdapter(getActivity());
        fragmentTwoRecycler.setAdapter(adapter);
        // 设置预加载位置,倒数
        adapter.setPreLoadNumber(1);
        // 监听加载更多事件
        adapter.setOnLoadMoreListener(this, fragmentTwoRecycler);
        // 取消第一次加载回调
        adapter.disableLoadMoreIfNotFullPage();
        adapter.setOnItemImageClickListener(this);
    }

    @Override
    public void onCustomized() {
        ptrFrameLayout();
        httpPost("");
    }
    @Override
    public void onResume() {
        super.onResume();
        if (loginRefreshFlag&&currPos== TwoFragment_MarketContent.currentPos&&MainActivity.currentPos==3&&TwoFragment.currPos==1){
            loginRefreshFlag=false;
            page=1;
            httpPost("");
        }
    }
    //登录刷新标志
    private  boolean loginRefreshFlag=false;
    @Override
    public void mainMessage(Message message) {
        if (message.what == LogicActions.LoginStatusSuccess&&(Boolean) message.obj) {
            if (currPos == TwoFragment_MarketContent.currentPos && TwoFragment.currPos ==1) {
                loginRefreshFlag=false;
                page=1;
                httpPost("");
            }else{
            loginRefreshFlag=true;
            }
        }
        //弹出一键发圈对话框
        if (message.what == LogicActions.oneKeyShareSuccess&&message.arg1==2) {
            dismissDialog();
            JSONObject obj =  JSON.parseObject(message.obj.toString());
            String msgstr=obj.getString("msgstr");
            String url=obj.getString("url");
            OneKeyShareDialog oneKeyShareDialog=new OneKeyShareDialog(getActivity(),msgstr);
            oneKeyShareDialog.setOneKeyClickListener(new OneKeyShareDialog.OnOneKeyClickListener() {
                @Override
                public void onClick() {
                    if (oneKeyShareDialog!=null&&oneKeyShareDialog.isShowing()){
                        oneKeyShareDialog.hideDialog();
                    }
                    Intent intent = new Intent(getActivity(), AliAuthWebViewActivity.class);
                    intent.putExtra(Variable.webUrl, url);
                    intent.putExtra("isTitle", true);
                    startActivity(intent);
                }
            });
            oneKeyShareDialog.show();
        }
    }

    @Override
    public void childMessage(Message message) {

    }

    @Override
    public void handlerMessage(Message message) {
        loadMorePtrFrame.refreshComplete();
        dismissDialog();
        if (message.what == LogicActions.MarketingSuccess) {
            marketing = (Marketing) message.obj;
            if (marketing.getMarketingdata().size() > 0) {
                if (page > 1) {
                    adapter.addData(marketing.getMarketingdata());
                } else {
                    adapter.setNewData(marketing.getMarketingdata());
                }
                adapter.loadMoreComplete();
            } else {
                if (page==1){
                 adapter.setNewData(marketing.getMarketingdata());
                }
                adapter.loadMoreEnd();
            }
        }
        //一键发圈
        if (message.what == LogicActions.SendCircleMarketingAllSuccess) {
         ToastUtil.showImageToast(getActivity(),message.obj+"",R.mipmap.toast_img);
        }
    }
   private boolean isfirst=true;
    private void httpPost(String time) {
        if (page ==1&&isfirst){
            isfirst=false;
            showDialog();
        }
        // 营销素材
        paramMap = new HashMap<>();
        paramMap.put("userid", login.getUserid());
        paramMap.put("type", type);
        paramMap.put("startindex", page + "");
        paramMap.put("searchtime", time);
        paramMap.put("pagesize", "3");
        NetworkRequest.getInstance().POST(handler, paramMap, "Marketing", HttpCommon.Marketing);
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
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        httpPost(marketing.getSearchtime());
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return PtrDefaultHandler.checkContentCanBePulledDown(frame, fragmentTwoRecycler, header);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        page = 1;
        httpPost("");
    }


    @Override
    public void onItemImageClick(Marketing.MarketingList item,int position) {
      ArrayList<Marketing.MarketingList.ImageUrl> imageUrls = item.getImglist();
      int size=item.getImglist().size();
      ArrayList<String> arrayList = new ArrayList<>();
      for (int i=0;i<imageUrls.size();i++){
      arrayList.add(item.getImglist().get(i).getImgurl());
      }
      switch(size) {
          case 1:
              if (Objects.equals(item.getVideourl(), "")) {
                  startActivity(new Intent(getActivity(), ImageActivity.class).putStringArrayListExtra("imageList", arrayList).putExtra("position", position));
              } else {
                  Intent intent = new Intent(getActivity(), VideoActivity.class);
                  intent.putExtra("videoUrl", item.getVideourl());
                  intent.putExtra("title", item.getContent());
                  startActivity(intent);
              }
              break;
          default:
              if (Objects.equals(item.getVideourl(), "")) {
                  startActivity(new Intent(getActivity(), ImageActivity.class).putStringArrayListExtra("imageList", arrayList).putExtra("position", position));
              } else {
                  switch (position) {
                      case 0:
                          Intent intent = new Intent(getActivity(), VideoActivity.class);
                          intent.putExtra("videoUrl", item.getVideourl());
                          intent.putExtra("title", item.getContent());
                          startActivity(intent);
                          break;
                      default:
                          startActivity(new Intent(getActivity(), ImageActivity.class).putStringArrayListExtra("imageList", arrayList).putExtra("position", position - 1));
                          break;
                  }
              }
              break;

      }
      }

    @Override
    public void onOneKey(Marketing.MarketingList item) {
            if (DateStorage.getLoginStatus()) {
                //调用一键分享接口
                showDialog();
                paramMap.clear();
                paramMap.put("marketingid", item.getMarketingid());
                NetworkRequest.getInstance().POST(handler, paramMap, "SendCircleMarketingAll", HttpCommon.SendCircleMarketing);
            } else {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        }
}
