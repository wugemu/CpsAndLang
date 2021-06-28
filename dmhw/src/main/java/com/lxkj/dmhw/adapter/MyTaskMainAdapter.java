package com.lxkj.dmhw.adapter;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;

import com.lxkj.dmhw.bean.SearchAll;

import java.util.ArrayList;


public class MyTaskMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;

    private ArrayList<SearchAll> mDatas = new ArrayList<>();

    private View mHeaderView;
    private FragmentActivity context;

    public MyTaskMainAdapter(FragmentActivity activity) {
        this.context = activity;
    }


    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public void addDatas(ArrayList<SearchAll> datas, int type) {
        if (type==0){
         mDatas=datas;
        }else{
         mDatas.addAll(datas);
        }
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        if(mHeaderView == null) return TYPE_NORMAL;
        if(position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mHeaderView != null && viewType == TYPE_HEADER)
            return new Holder(mHeaderView);
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(getItemViewType(position) == TYPE_HEADER) return;

    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? mDatas.size() : mDatas.size() + 1;
    }

    class Holder extends RecyclerView.ViewHolder {
        public Holder(View itemView) {
            super(itemView);
            if(itemView == mHeaderView) return;

        }
    }

}
