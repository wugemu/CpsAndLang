package com.lxkj.dmhw.adapter.cps;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.HomePage;
import com.lxkj.dmhw.utils.GridSpacingItemDecoration;
import com.lxkj.dmhw.utils.MyLayoutManager;
import com.lxkj.dmhw.utils.Utils;

import java.util.ArrayList;

public class SpecilAdapter extends BaseQuickAdapter<HomePage.JGQSort, BaseViewHolder> {
    private ArrayList<HomePage.JGQSort> list;
    private Context context;
    private int sWidth;
    public SpecilAdapter(Context context,int width,ArrayList<HomePage.JGQSort> list) {
        super(R.layout.adapter_specil_layout);
        this.context=context;
        this.sWidth=width;
        this.list=list;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomePage.JGQSort item) {
        RecyclerView fragment_sort_recycler=helper.getView(R.id.fragment_sort_recycler);
        View view_bottom=helper.getView(R.id.view_bottom);
        TextView tv_specil_title=helper.getView(R.id.tv_specil_title);
        tv_specil_title.setText(item.getName());
        SpecilJGQAdapter jgqSortAdapter=new SpecilJGQAdapter(context,sWidth);
        fragment_sort_recycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(context, 5));
        fragment_sort_recycler.addItemDecoration(new GridSpacingItemDecoration(5,(int)context.getResources().getDimension(R.dimen.dp_5), false));
        fragment_sort_recycler.setAdapter(jgqSortAdapter);
        ArrayList<HomePage.JGQAppIcon> appIconlist = (ArrayList<HomePage.JGQAppIcon>) JSON.parseArray(item.getKeyjson(), HomePage.JGQAppIcon.class);
        jgqSortAdapter.setNewData(appIconlist);
        jgqSortAdapter.setOnJGQAppIconClickListener(new SpecilJGQAdapter.OnJGQAppIconClickListener() {
            @Override
            public void onJGQAppIconClick(HomePage.JGQAppIcon item) {
                Utils.doJgqClick(context, item);
            }
        });

        if(helper.getAdapterPosition()==getItemCount()-1){
            view_bottom.setVisibility(View.GONE);
        }else {
            view_bottom.setVisibility(View.GONE);
        }
    }
}
