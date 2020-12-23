package com.lxkj.dmhw.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.bean.CpsType;
import com.lxkj.dmhw.bean.MainBottomListItem;
import com.lxkj.dmhw.bean.UserInfo;
import com.lxkj.dmhw.data.DateStorage;
import com.lxkj.dmhw.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;


/**
 * 首页精选适配器
 */

public class MyAdapter290 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;

    private ArrayList<MainBottomListItem> mDatas = new ArrayList<>();

    private View mHeaderView;
    private FragmentActivity context;
    private OnItemClickListener mListener;
    TextPaint tp;
    private String mType;
    public MyAdapter290(FragmentActivity activity,String type) {
        this.context = activity;
        this.mType=type;
    }

    public void setOnItemClickListener(OnItemClickListener li) {
        mListener = li;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public void addDatas(ArrayList<MainBottomListItem> datas, int type) {
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
        if(position ==0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        //为GridLayoutManager 合并头布局的跨度
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                /**
                 * 抽象方法  返回当前index位置的item所占用的跨度的数量
                 * ##单元格合并  就是相当于占据了设定列spanCount的数量
                 * ##不合并     就是相当于占据了原来1个跨度
                 *
                 * @param position
                 * @return
                 */
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_HEADER ? gridLayoutManager.getSpanCount() : 1;
                }
            });
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mHeaderView != null && viewType == TYPE_HEADER) return new Holder(mHeaderView);
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_list_shop_gridnew, parent, false);
        return new Holder(layout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(getItemViewType(position) == TYPE_HEADER) return;
        final int pos = getRealPosition(viewHolder);
        MainBottomListItem item = mDatas.get(pos);
        Object CpsTypeObject = JSON.parseObject(item.getCpsType(), CpsType.class);
        CpsType cpsType = (CpsType) CpsTypeObject;
        mType=cpsType.getCode();
        if(viewHolder instanceof Holder) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ((Holder) viewHolder).adapter_new_onelist_fragment_layout.getLayoutParams();
//            if (viewHolder.getLayoutPosition() % 2 == 0) {//右边item
//                params.setMargins( Utils.dipToPixel(R.dimen.dp_10), 0,0, 0);
//            } else {//左边item
//                params.setMargins(0,0,Utils.dipToPixel(R.dimen.dp_10),0);
//            }

            ((Holder) viewHolder).adapter_new_onelist_fragment_layout.setLayoutParams(params);
            Utils.displayImageRoundedNew(context, item.getImageUrl(),((Holder) viewHolder).adapter_new_one_fragment_image, 7);
            tp=((Holder) viewHolder).adapter_new_one_fragment_title.getPaint();
            tp.setFakeBoldText(true);
            tp=((Holder) viewHolder).adapter_new_one_fragment_discount.getPaint();
            tp.setFakeBoldText(true);
            tp=((Holder) viewHolder).adapter_new_one_fragment_estimate_text.getPaint();
            tp.setFakeBoldText(true);
            tp=((Holder) viewHolder).adapter_new_one_fragment_price.getPaint();
            tp.setFakeBoldText(true);
            ((Holder) viewHolder).adapter_new_one_fragment_title.setText("     "+item.getName());

            ((Holder) viewHolder).adapter_new_one_fragment_discount_layout.setVisibility(View.GONE);
            ((Holder) viewHolder).adapter_new_one_fragment_discount_wph.setVisibility(View.GONE);

            if(mType.equals("wph")||mType.equals("kl")){
                ((Holder) viewHolder).adapter_new_one_fragment_discount_wph.setVisibility(View.VISIBLE);
                ((Holder) viewHolder).adapter_new_one_text_discount_wph.setText(item.getDiscount()+"折");
            }else if (mType.equals("pjw")){
                ((Holder) viewHolder).adapter_new_one_fragment_discount_layout.setVisibility(View.VISIBLE);
                ((Holder) viewHolder).adapter_new_one_fragment_discount.setText(item.getSave()+"元");
            }else{
                ((Holder) viewHolder).adapter_new_one_fragment_discount_layout.setVisibility(View.VISIBLE);
                ((Holder) viewHolder).adapter_new_one_fragment_discount.setText(item.getCoupon()+"元");
            }
            ((Holder) viewHolder).adapter_new_one_fragment_number.setText("已售" + item.getSales());
            ((Holder) viewHolder).adapter_new_one_fragment_estimate_text.setText("奖 "+context.getResources().getString(R.string.money)+item.getNormalCommission());
            ((Holder) viewHolder).adapter_new_one_fragment_price.setText(item.getPrice());
            ((Holder) viewHolder).adapter_new_one_fragment_original_price.setText("原价 " + context.getResources().getString(R.string.money) +item.getCost());
             UserInfo login = DateStorage.getInformation();
            if (DateStorage.getLoginStatus()) {
                if (!Objects.equals(login.getUsertype(), "3")) {
                    ((Holder) viewHolder).adapter_new_one_fragment_estimate.setVisibility(View.VISIBLE);
                } else {
                    ((Holder) viewHolder).adapter_new_one_fragment_estimate.setVisibility(View.GONE);
                }
            } else {
                ((Holder) viewHolder).adapter_new_one_fragment_estimate.setVisibility(View.GONE);
            }

            try {
                JSONObject jsonObject = new JSONObject(item.getCpsType());
                Utils.displayImage(context,jsonObject.optString("logo"),((Holder) viewHolder).adapter_new_one_fragment_check);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(mListener == null) return;
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(pos, item);
                }
            });
        }
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
        ImageView adapter_new_one_fragment_image;
        TextView adapter_new_one_fragment_title;
        TextView adapter_new_one_fragment_discount;
        TextView adapter_new_one_fragment_number;
        TextView adapter_new_one_fragment_estimate_text;
        TextView adapter_new_one_fragment_price;
        TextView adapter_new_one_fragment_original_price;
        LinearLayout adapter_new_one_fragment_estimate;
        LinearLayout adapter_new_onelist_fragment_layout;
        LinearLayout adapter_new_one_fragment_discount_layout;
        LinearLayout adapter_new_one_fragment_discount_wph;
        ImageView adapter_new_one_fragment_check;
        TextView adapter_new_one_text_discount_wph;
        public Holder(View itemView) {
            super(itemView);
            if(itemView == mHeaderView) return;
            adapter_new_one_fragment_image =  itemView.findViewById(R.id.adapter_new_one_fragment_image);
            adapter_new_one_fragment_title =  itemView.findViewById(R.id.adapter_new_one_fragment_title);
            adapter_new_one_fragment_discount =  itemView.findViewById(R.id.adapter_new_one_fragment_discount);
            adapter_new_one_fragment_number =  itemView.findViewById(R.id.adapter_new_one_fragment_number);
            adapter_new_one_fragment_estimate_text =  itemView.findViewById(R.id.adapter_new_one_fragment_estimate_text);
            adapter_new_one_fragment_price =  itemView.findViewById(R.id.adapter_new_one_fragment_price);
            adapter_new_one_fragment_original_price =  itemView.findViewById(R.id.adapter_new_one_fragment_original_price);
            adapter_new_one_fragment_estimate =  itemView.findViewById(R.id.adapter_new_one_fragment_estimate);
            adapter_new_onelist_fragment_layout=  itemView.findViewById(R.id.adapter_new_onelist_fragment_layout);
            adapter_new_one_fragment_check =  itemView.findViewById(R.id.adapter_new_one_fragment_check);
            adapter_new_one_text_discount_wph=itemView.findViewById(R.id.adapter_new_one_text_discount_wph);
            adapter_new_one_fragment_discount_layout=itemView.findViewById(R.id.adapter_new_one_fragment_discount_layout);
            adapter_new_one_fragment_discount_wph=itemView.findViewById(R.id.adapter_new_one_fragment_discount_wph);
        }
    }
   public  interface OnItemClickListener {
        void onItemClick(int position, MainBottomListItem data);
    }
}
