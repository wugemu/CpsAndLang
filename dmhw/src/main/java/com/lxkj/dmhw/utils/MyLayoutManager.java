package com.lxkj.dmhw.utils;

import android.content.Context;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * 布局管理器
 * Created by Zyhant on 2018/1/11.
 */

public class MyLayoutManager {

    private static volatile MyLayoutManager instance;

    public MyLayoutManager() {

    }

    public static MyLayoutManager getInstance() {
        if (instance == null) {
            instance = new MyLayoutManager();
        }
        return instance;
    }

    /**
     * 自动换行布局
     * @return
     */
    public FlowLayoutManager LayoutManager(Context context) {
        return new FlowLayoutManager(context, true);
    }

    /**
     * 列表布局
     * @param context
     * @param isCheck true为横向false为竖向
     * @return
     */
    public LinearLayoutManager LayoutManager(Context context, boolean isCheck) {
        LinearLayoutManager linearManager = new LinearLayoutManager(context);
        if (isCheck) {
            linearManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        } else {
            linearManager.setOrientation(LinearLayoutManager.VERTICAL);
        }
        return linearManager;
    }

    /**
     * 表格布局
     * @param context
     * @param spanCount 设置列数
     * @return
     */
    public GridLayoutManager LayoutManager(Context context, int spanCount) {
        return new GridLayoutManager(context, spanCount);
    }

    /**
     * 瀑布流布局
     * @param spanCount 设置列数
     * @param orientation 设置方向
     * @return
     */
    public StaggeredGridLayoutManager LayoutManager(int spanCount, int orientation) {
        return new StaggeredGridLayoutManager(spanCount, orientation);
    }
}
