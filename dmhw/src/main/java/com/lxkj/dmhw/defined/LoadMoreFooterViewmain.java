package com.lxkj.dmhw.defined;

import android.content.Context;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lxkj.dmhw.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * 下拉刷新样式
 */
public class LoadMoreFooterViewmain extends FrameLayout implements PtrUIHandler {

    @BindView(R.id.view_load_more_text)
    TextView viewLoadMoreText;
    @BindView(R.id.gifimage)
    ImageView gifimage;

    // 下拉刷新视图
    private View view;
    // 刷新下拉文字
    private String pullText = "下拉刷新";
    // 刷新触发文字
    private String triggerText = "释放刷新";
    // 刷新中文字
    private String beginText = "刷新中...";
//    private String beginText = "新内容很快就来了呦...";
    // 刷新完成文字
    private String completeText = "刷新完成";


    private Context context;
    public LoadMoreFooterViewmain(Context context) {
        this(context, null);
    }

    public LoadMoreFooterViewmain(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreFooterViewmain(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {
        this.context=context;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.view_load_more_main, this, true);
        ButterKnife.bind(this, view);
        gifimage.setVisibility(GONE);
        viewLoadMoreText.setText(pullText);
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        gifimage.setVisibility(GONE);
        viewLoadMoreText.setText(pullText);
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        viewLoadMoreText.setText(pullText);
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        gifimage.setVisibility(VISIBLE);
        Glide.with(context).asGif().load(R.mipmap.loading).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(gifimage);
        viewLoadMoreText.setText(beginText);
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        gifimage.setVisibility(GONE);
        viewLoadMoreText.setText(completeText);
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        int mOffsetToRefresh = frame.getOffsetToRefresh();
        int currentPos = ptrIndicator.getCurrentPosY();
        int lastPos = ptrIndicator.getLastPosY();
        if (currentPos < mOffsetToRefresh) {
            //未到达刷新线
            if (status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                viewLoadMoreText.setText(pullText);
            }
        } else if (currentPos > mOffsetToRefresh) {
            //到达或超过刷新线
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                viewLoadMoreText.setText(triggerText);
            }
        }
    }

    /**
     * 设置下拉文字
     * @param content
     */
    public void setPullText(@NonNull String content) {
        if (!TextUtils.isEmpty(content)) {
            pullText = content;
        }
    }

    /**
     * 设置触发文字
     * @param content
     */
    public void settTiggerText(@NonNull String content) {
        if (!TextUtils.isEmpty(content)) {
            triggerText = content;
        }
    }

    /**
     * 设置刷新中文字
     * @param content
     */
    public void setBeginText(@NonNull String content) {
        if (!TextUtils.isEmpty(content)) {
            beginText = content;
        }
    }

    /**
     * 设置完成文字
     * @param content
     */
    public void setCompleteText(@NonNull String content) {
        if (!TextUtils.isEmpty(content)) {
            completeText = content;
        }
    }

    /**
     * 设置字体颜色
     * @param color
     */
    public void setTextColor(@ColorInt int color) {
        viewLoadMoreText.setTextColor(color);
    }





}
