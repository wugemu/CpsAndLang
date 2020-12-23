package com.lxkj.dmhw.utils;

import android.app.Activity;

import java.util.Stack;

/**
 * Activity管理
 * Created by Zyhant on 2016/10/25.
 */

public class ActivityManagerDefine {

    private volatile Stack<Activity> activityStack = new Stack<>();
    private static volatile ActivityManagerDefine instance;

    private ActivityManagerDefine() {

    }

    /**
     * 创建单例类，提供静态方法调用
     * @return
     */
    public static ActivityManagerDefine getInstance() {
        if (instance == null) {
            instance = new ActivityManagerDefine();
        }
        return instance;
    }

    /**
     * 退出Activity
     * @param activity
     */
    public void popActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
        }
    }

    /**
     * 退出特定Activity
     * @param cls
     */
    public void popClass(Class cls) {
        if (cls == null) {
            return;
        }
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                activity.finish();
                activityStack.remove(activity);
                return;
            }
        }
    }

    /**
     * 将Activity装入栈中
     * @param activity
     */
    public void pushActivity(Activity activity) {
        if (activity != null) {
            activityStack.add(activity);
        }
    }

    /**
     * 退出其他Activity
     * @param cls
     */
    public void popOtherActivity(Class cls) {
        if (cls == null) {
            return;
        }
        for (Activity activity : activityStack) {
            if (activity == null || activity.getClass().equals(cls)) {
                continue;
            }
            activity.finish();
        }
    }

    /**
     * 退出栈中所有Activity
     */
    public void popAllActivity() {
        while (true) {
            if (!activityStack.empty()) {
                Activity activity = activityStack.lastElement();
                activity.finish();
                popActivity(activity);
            } else {
                break;
            }
        }
    }

    /**
     * 查找页面是否存在
     * @param cls
     * @return
     */
    public boolean selectActivity(Class cls) {
        boolean isCheck = false;
        if (cls != null) {
            for (Activity activity : activityStack) {
                if (activity.getClass().equals(cls)) {
                    isCheck = true;
                    break;
                }
            }
            return isCheck;
        } else {
            return false;
        }
    }

    /**
     * 查询页面是否在前台
     * @param activity
     * @return
     */
    public boolean selectReception(String activity) {
        String old = activityStack.get(activityStack.size() - 1).toString();
        if (old.substring(old.indexOf("ty.") + 3, old.indexOf("@")).equals(activity.substring(activity.indexOf("ty.") + 3, activity.indexOf("@")))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 返回当前顶部页面
     * @return
     */
    public Activity getActivity() {
       if (activityStack.size()-1>=0){
           return activityStack.get(activityStack.size() - 1);
       }
       return null;
    }

}
