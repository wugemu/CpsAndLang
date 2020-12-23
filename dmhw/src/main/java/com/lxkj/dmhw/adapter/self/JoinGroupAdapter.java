package com.lxkj.dmhw.adapter.self;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test.andlang.util.LogUtil;
import com.example.test.andlang.util.TimeCalculate;
import com.example.test.andlang.util.imageload.ImageLoadUtils;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.self.Group;
import com.lxkj.dmhw.view.CircleImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created By lhp
 * on 2019/12/24
 */
public class JoinGroupAdapter extends RecyclerView.Adapter<JoinGroupAdapter.ViewHolder> {
    private Activity activity;
    private List<Group> groupList;
    private StringBuilder sb;
    private Handler handler;
    private Map<Integer, TextView> timeViews;

    public JoinGroupAdapter(Activity activity, List<Group> groupList, Handler handler) {
        this.handler = handler;
        this.activity = activity;
        if (groupList == null) {
            this.groupList = new ArrayList<>();
        } else {
            this.groupList = groupList;
        }
        sb = new StringBuilder();
        timeViews = new HashMap<>();
    }

    public void setData(List<Group> groups) {
        if (timeViews != null) {
            timeViews.clear();
        }
        this.groupList = groups;
    }

    public void settime() {
        if (timeViews != null && timeViews.size() > 0) {
            Iterator<Map.Entry<Integer, TextView>> entries = timeViews.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<Integer, TextView> entry = entries.next();
                int key = entry.getKey();
                TextView textView = entry.getValue();
                Group group = null;
                if (groupList != null && key < groupList.size()) {
                    group = groupList.get(key);
                }
                if (group != null && group.getLessGroupEndTime() > 0) {
                    groupList.get(key).setLessGroupEndTime(groupList.get(key).getLessGroupEndTime() - 1);
                    textView.setText("剩余" + TimeCalculate.getTime3(0, group.getLessGroupEndTime()));
                } else {
                    if (textView != null) {
                        textView.setText("");
                    }
                    if (handler != null) {
                        Message msg = handler.obtainMessage();
                        msg.what = 8;
                        msg.obj = false;
                        handler.sendMessage(msg);
                    }
                }
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.adapter_marqueen_group, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.tvItemGroupDeadline2.setVisibility(View.VISIBLE);
        TextView tvBtn = holder.tvItemGroupBuy;
        Group group = groupList.get(position);
        CircleImageView head1 = holder.civGroupHead1;
        CircleImageView head2 = holder.civGroupHead2;
        TextView tvNames = holder.tvGroupMemberNames;
        sb.delete(0, sb.length());
        //布局内容填充
        if (group.getHeardUrls().size() > 1) {
            head2.setVisibility(View.VISIBLE);
            ImageLoadUtils.doLoadCircleImageUrl(head1, group.getHeardUrls().get(0));
            ImageLoadUtils.doLoadCircleImageUrl(head2, group.getHeardUrls().get(1));
            for (int i = 0; i < 2; i++) {
                String name = group.getNickNames().get(i);
                if (name.length() > 4) {
                    sb.append(name.substring(0, 4));
                    sb.append("...");
                } else {
                    sb.append(name);
                }
                sb.append("、");
            }
            sb.deleteCharAt(sb.length() - 1);
        } else {
            head2.setVisibility(View.INVISIBLE);
            ImageLoadUtils.doLoadCircleImageUrl(head1, group.getHeardUrls().get(0));
            String name = group.getNickNames().get(0);
            sb.append(name);
        }
        tvNames.setText(sb.toString());
        SpannableString ss = new SpannableString("还差" + group.getLessGroupUserCount() + "人拼成");
        ss.setSpan(new ForegroundColorSpan(Color.parseColor("#FF1341")), 2, ss.length() - 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        holder.tvGroupNeedNum.setText(ss);

        if (group.isIfOwnGroup()) {
            tvBtn.setText("邀请好友");
        } else {
            tvBtn.setText("去拼团");
        }
        tvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (handler != null) {
                    Message message = handler.obtainMessage();
                    message.obj = group;
                    if (group.isIfOwnGroup()) {
                        //去分享
                        message.what = 12;
                    } else {
                        //去参团
                        message.what = 11;
                    }
                    handler.sendMessage(message);

                }
            }
        });
        holder.tvItemGroupDeadline2.setText("剩余" + TimeCalculate.getTime3(0, group.getLessGroupEndTime()));
        timeViews.put(position, holder.tvItemGroupDeadline2);
//        if (group.getLessGroupEndTime()>0){
//
//        }else{
//            timeViews.remove(position);
//            holder.tvItemGroupDeadline2.setText("");
//        }


    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.civ_group_head1)
        CircleImageView civGroupHead1;
        @BindView(R.id.civ_group_head2)
        CircleImageView civGroupHead2;
        @BindView(R.id.tv_group_member_names)
        TextView tvGroupMemberNames;
        @BindView(R.id.tv_group_need_num)
        TextView tvGroupNeedNum;
        @BindView(R.id.tv_item_group_deadline)
        TextView tvItemGroupDeadline;
        @BindView(R.id.tv_item_group_buy)
        TextView tvItemGroupBuy;
        @BindView(R.id.tv_item_group_deadline2)
        TextView tvItemGroupDeadline2;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
