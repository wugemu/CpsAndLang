package com.lxkj.dmhw.adapter.cps;

import android.app.Activity;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.self.ActivityBean;
import com.lxkj.dmhw.utils.BBCUtil;
import com.lxkj.dmhw.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created By lhp
 * on 2019/10/14
 */
public class NewHomeGuideAdapter extends RecyclerView.Adapter<NewHomeGuideAdapter.ViewHolder> {
    private List<ActivityBean> guides;
    private LayoutInflater inflater;
    private Activity activity;
    private int width;
    private String textColor;
    public NewHomeGuideAdapter(Activity activity, List<ActivityBean> guides) {
        width=(int)(BBCUtil.getDisplayWidth(activity)-activity.getResources().getDimension(R.dimen.dp_20))/4;
        if(guides!=null){
            this.guides = guides;
        }else{
            this.guides = new ArrayList<ActivityBean>();
        }
        this.activity=activity;
        this.inflater = LayoutInflater.from(activity);
    }

    //新版首页专用，老版慎用
    public void setGuides(List<ActivityBean> guides) {
        this.guides = guides;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.gridview_guide_item,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RelativeLayout rl_home_btn=holder.rl_home_btn;
        ImageView iv_home_btn=holder.ivHomeBtn;
        TextView tv_home_btn=holder.tvHomeBtn;
        ActivityBean item=guides.get(position);
        tv_home_btn.setText(item.getName());
        if(!BBCUtil.isEmpty(textColor)&&BBCUtil.isColorStr(textColor)){
            tv_home_btn.setTextColor(Color.parseColor(textColor));
        }
        Utils.displayImageRounded(activity,item.getImgUrl(),iv_home_btn, 0);
        AbsListView.LayoutParams params=new AbsListView.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        rl_home_btn.setLayoutParams(params);
        rl_home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return guides.size();
    }

    public void setTitleColor(String textColor){
        this.textColor=textColor;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_home_btn)
        ImageView ivHomeBtn;
        @BindView(R.id.tv_home_btn)
        TextView tvHomeBtn;
        @BindView(R.id.rl_home_btn)
        RelativeLayout rl_home_btn;
        @BindView(R.id.iv_guide_tip)
        ImageView iv_guide_tip;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
