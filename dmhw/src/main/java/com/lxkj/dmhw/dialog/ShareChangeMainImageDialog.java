package com.lxkj.dmhw.dialog;

import android.content.Context;
import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxkj.dmhw.R;
import com.lxkj.dmhw.adapter.ShareCheck;
import com.lxkj.dmhw.adapter.ShareMainAdapter;
import com.lxkj.dmhw.bean.ShareInfo;
import com.lxkj.dmhw.defined.LinearItemDecoration;
import com.lxkj.dmhw.defined.SimpleDialog;
import com.lxkj.dmhw.utils.MyLayoutManager;
import com.lxkj.dmhw.utils.Utils;

import java.util.ArrayList;


public class ShareChangeMainImageDialog extends SimpleDialog<ArrayList<ShareCheck>> {
    private ShareChangeMainImageDialog.OnBtnClickListener mListener;
    private RecyclerView shareMainRecycler;
    private ShareMainAdapter adapter;
    private int currentItemPos=0;
    private Bitmap mresourse=null;
    private ShareInfo shareInfo;
    private ImageView share_main_image;
    private String plat="tb";
    public void setOnClickListener(ShareChangeMainImageDialog.OnBtnClickListener li) {
        mListener = li;
    }
    /**
     * 初始化
     */
    public ShareChangeMainImageDialog(Context context, ArrayList<ShareCheck> list) {
        super(context, R.layout.dialog_share_main, list, true, true);
    }

    @Override
    protected void convert(ViewHolder helper) {
        helper.setOnClickListener(R.id.share_main_close, this);
        helper.setOnClickListener(R.id.share_main_save, this);
        share_main_image=helper.getView(R.id.share_main_image);
        shareMainRecycler=helper.getView(R.id.share_main_recycler);
        shareMainRecycler.setLayoutManager(MyLayoutManager.getInstance().LayoutManager(context, true));
        shareMainRecycler.addItemDecoration(new LinearItemDecoration(Utils.dipToPixel(R.dimen.dp_10), 0, 0, 0));
        adapter = new ShareMainAdapter(context);
        shareMainRecycler.setAdapter(adapter);
        adapter.setNewData(data);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                currentItemPos=position;
                ShareCheck shareCheck= (ShareCheck) adapter.getData().get(position);
                for (int i=0;i<adapter.getData().size();i++) {
                    ShareCheck shareCheck1= (ShareCheck) adapter.getData().get(i);
                    shareCheck1.setMainImageCheck(0);
                    shareCheck1.setIsMainFirstQr("0");
                }
                shareCheck.setMainImageCheck(1);
                shareCheck.setIsMainFirstQr("1");
                adapter.notifyDataSetChanged();
                Glide.with(context).asBitmap().load(shareCheck.getImage()).into(new SimpleTarget<Bitmap>(){
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        if (plat.equals("tb")){
                            mresourse = Utils.jointBitmapNew(context, resource, shareInfo);
                        }else{
                            mresourse = Utils.jointBitmapPJW(context, resource, shareInfo);
                        }
                        helper.setImageBitmap(R.id.share_main_image,mresourse);
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.share_main_close:
                hideDialog();
                break;
            case R.id.share_main_save:
                hideDialog();
                mListener.onClickMainImage(currentItemPos);
                break;
        }
    }

    public  interface OnBtnClickListener {
        void onClickMainImage(int pos);
    }


    public void showShareMainImageDialog(ShareInfo SI,int mainImagePos,String type) {
        shareInfo=SI;
        plat=type;
        currentItemPos=mainImagePos;
        Glide.with(context).asBitmap().load(data.get(currentItemPos).getImage()).into(new SimpleTarget<Bitmap>(){
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                if (type.equals("tb")){
                    mresourse = Utils.jointBitmapNew(context, resource, shareInfo);
                }else{
                    mresourse = Utils.jointBitmapPJW(context, resource, shareInfo);
                }
                share_main_image.setImageBitmap(mresourse);
            }
        });
        if (!isDialog()) {
            showDialog();
        }
    }
}
