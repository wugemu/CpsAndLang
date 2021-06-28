package com.lxkj.dmhw.defined;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;

import com.orhanobut.logger.Logger;

/**
 * 懒加载
 * Created by Zyhant on 2018/1/3.
 */

public abstract class LazyFragment extends BaseFragment {

    // Fragment的View加载完毕的标记
    protected boolean isViewCreated;
    // Fragment对用户可见的标记
    private boolean isUIVisible;

    //--------------------系统方法回调------------------------//

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        try {
            super.onViewCreated(view, savedInstanceState);
            isViewCreated = true;
            lazyLoad();
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        try {
            super.setUserVisibleHint(isVisibleToUser);
            // isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
            if (isVisibleToUser) {
                isUIVisible = true;
                lazyLoad();
            } else {
                isUIVisible = false;
            }
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }

    //--------------------------------懒方法---------------------------//

    /**
     * 懒加载
     */
    protected void lazyLoad(){
        try {
            // 这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
            if (isViewCreated && isUIVisible) {
                initData();
                //数据加载完毕,恢复标记,防止重复加载
                isViewCreated = false;
                isUIVisible = false;
            }
        } catch (Exception e) {
            Logger.e(e, "");
        }
    }
    //--------------------------abstract方法------------------------//

    /**
     * 这里获取数据，刷新界面
     */
    protected abstract void initData();
}
