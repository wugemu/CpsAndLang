package com.lxkj.dmhw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.ComCollegeData;
import com.lxkj.dmhw.bean.Headlines;
import com.lxkj.dmhw.utils.MyLayoutManager;
import com.lxkj.dmhw.utils.ToastUtil;
import com.lxkj.dmhw.utils.Utils;
import com.lxkj.dmhw.view.LimitScrollerView;

import java.util.List;

//TODO 修改适配器绑定数据
    public  class LimitScrollViewAdapter implements LimitScrollerView.LimitScrollAdapter{
        private HeadlinesAdapter adapter;
        private List<ComCollegeData.Question> datas;
        private Context context;
        private LimitScrollerView limitScroll;
        public void setDatas(Context con,List<ComCollegeData.Question> datas,LimitScrollerView mLimitScroll){
            this.datas = datas;
            this.context=con;
            this.limitScroll=mLimitScroll;
            //API:2、开始滚动
            limitScroll.startScroll();
        }
        @Override
        public int getCount() {
            return datas==null?0:datas.size();
        }

        @Override
        public View getView(int index) {
            View itemView = LayoutInflater.from(context).inflate(R.layout.adapter_comcoll_vertical_banner, null, false);
            //绑定数据
            ComCollegeData.Question data = datas.get(index);
            itemView.setTag(data);
           TextView title_text= itemView.findViewById(R.id.title);
           title_text.setText(datas.get(index).getTitle());
            return itemView;
        }
}
