package com.lxkj.dmhw.widget.dialog;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.test.andlang.util.DateUtil;
import com.example.test.andlang.util.TimeCalculate;
import com.example.test.andlang.util.imageload.GlideUtil;
import com.example.test.andlang.util.imageload.ImageLoadUtils;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.self.Group;
import com.lxkj.dmhw.view.CircleImageView;
import com.lxkj.dmhw.widget.xmarquee.XMarqueeView;
import com.lxkj.dmhw.widget.xmarquee.XMarqueeViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created By lhp
 * on 2019/12/9
 */
public class MarqueeGroupAdapter extends XMarqueeViewAdapter<Group> {
    private Activity mContext;
    private StringBuilder sb;
    private Map<Integer, TextView> timeViews;
    private Handler handler;

    public MarqueeGroupAdapter(List<Group> datas, Activity context, Handler handler) {
        super(datas);
        mContext = context;
        this.handler = handler;
        sb = new StringBuilder();
        timeViews = new HashMap<Integer, TextView>();
    }

    @Override
    public View onCreateView(XMarqueeView parent) {
        //跑马灯单个显示条目布局，自定义
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_marqueen_group, null);
    }

    @Override
    public void onBindView(View parent, View view, final int position) {
        CircleImageView head1 = view.findViewById(R.id.civ_group_head1);
        CircleImageView head2 = view.findViewById(R.id.civ_group_head2);
        TextView tvNames = view.findViewById(R.id.tv_group_member_names);
        TextView tvLessNum = view.findViewById(R.id.tv_group_need_num);
        TextView tvDeadline = view.findViewById(R.id.tv_item_group_deadline);
        tvDeadline.setVisibility(View.VISIBLE);
        TextView tvBtn = view.findViewById(R.id.tv_item_group_buy);

        //布局内容填充
        sb.delete(0, sb.length());
        Group group = mDatas.get(position);
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
        tvLessNum.setText(ss);
        timeViews.put(position, tvDeadline);
        tvDeadline.setText("剩余" + TimeCalculate.getTime3(0, group.getLessGroupEndTime()));
//        if (group.getLessGroupEndTime()>0){
//
//        }else{
//            timeViews.remove(position);
//            tvDeadline.setText("");
//        }

        if (group.isIfOwnGroup()) {
            tvBtn.setText("邀请好友");
        } else {
            tvBtn.setText("去拼团");
        }
        tvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = handler.obtainMessage();
                msg.obj = group;
                if (group.isIfOwnGroup()) {
                    msg.what = 12;
                    //去分享
                } else {
                    //去参团
                    msg.what = 11;
                }
                handler.sendMessage(msg);
            }
        });

    }

    @Override
    public void setData(List<Group> datas) {
        timeViews.clear();
        super.setData(datas);
    }

    public void settime() {
//        if (timeViews != null && timeViews.size() > 0) {
//            for (int i = 0; i < timeViews.size(); i++) {
//                Map<String, String> time = TimeCalculate.getTimeMap(0, mDatas.get(i).getLessGroupEndTime());
//                if (time != null && time.size() > 0) {
//                    mDatas.get(i).setLessGroupEndTime(mDatas.get(i).getLessGroupEndTime() - 1);
//                    timeViews.get(i).setText(time.get("hour") + ":" + time.get("min") + ":" + time.get("sec"));
//                } else if (handler != null) {
//                    Message msg=handler.obtainMessage();
//                    msg.what=8;
//                    msg.obj=true;
//                    handler.sendMessage(msg);
//                }
//            }
//        }

        Iterator<Map.Entry<Integer, TextView>> entries = timeViews.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<Integer, TextView> entry = entries.next();
            int key = entry.getKey();
            TextView textView = entry.getValue();
            Group group = null;
            if (mDatas != null && key < mDatas.size()) {
                group = mDatas.get(key);
            }
            if (group != null && group.getLessGroupEndTime() > 0) {
                mDatas.get(key).setLessGroupEndTime(mDatas.get(key).getLessGroupEndTime() - 1);
                textView.setText("剩余" + TimeCalculate.getTime3(0, group.getLessGroupEndTime()));
            } else {
                if (textView != null) {
                    textView.setText("");
                }
//                mDatas.remove(key);
//                if (mDatas.size() > 1 && mDatas.size() % 2 != 0) {//大于1个的奇数情况需要把最后一个去掉变偶数
//                    mDatas.remove(mDatas.size() - 1);
//                }

                if (handler != null) {
                    Message msg = handler.obtainMessage();
                    msg.what = 8;
                    msg.obj = true;
                    handler.sendMessage(msg);
                }
            }
        }

//        if (timeViews != null && timeViews.size() > 0) {
//            for (int i = 0; i < timeViews.size(); i++) {
//                if (mDatas.get(i).getLessGroupEndTime() > 0) {
//                    mDatas.get(i).setLessGroupEndTime(mDatas.get(i).getLessGroupEndTime() - 1);
//                    timeViews.get(i).setText("剩余"+TimeCalculate.getTime3(0, mDatas.get(i).getLessGroupEndTime()));
//                } else if (handler != null) {
//                    TextView textView=timeViews.get(i);
//                    if (textView!=null){
//                        textView.setText("");
//                    }
//                    Message msg = handler.obtainMessage();
//                    msg.what = 8;
//                    msg.obj = true;
//                    handler.sendMessage(msg);
//                }
//            }
//        }
    }

}
