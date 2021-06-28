package com.lxkj.dmhw.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextPaint;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.activity.BigBrandDetailActivity;
import com.lxkj.dmhw.bean.BigBrandList;
import com.lxkj.dmhw.utils.MyLayoutManager;
import com.lxkj.dmhw.utils.Utils;
import com.orhanobut.logger.Logger;


public class BigBrandAdapter290 extends BaseQuickAdapter<BigBrandList, BaseViewHolder> {

    private Context context;
    TextPaint tp;
    public BigBrandAdapter290(Context context) {
        super(R.layout.adapter_big_brand_list);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, BigBrandList item) {
        try {
            TextView adapter_name=helper.getView(R.id.adapter_name);
            tp= adapter_name.getPaint();
            tp.setFakeBoldText(true);
            Utils.displayImage(context, item.getLogo(),helper.getView(R.id.adapter_image));
            helper.setText(R.id.adapter_name,item.getBrandName());
            helper.setText(R.id.adapter_desc,item.getDesc());
            //填充每一项的recycleView
            // 设置Recycler 天猫超市
            RecyclerView recyclerView=helper.getView(R.id.adapter_recycler);
            recyclerView.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(context, true));
            // 初始化adapter
            BigBrandListItemAdapter bigBrandListItemAdapter = new BigBrandListItemAdapter(context);
            recyclerView.setAdapter(bigBrandListItemAdapter);
            bigBrandListItemAdapter.setNewData(item.getGoodsList());
            helper.getView(R.id.adapter_more_layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, BigBrandDetailActivity.class).putExtra("id",item.getId()));
                }
            });
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }
}
