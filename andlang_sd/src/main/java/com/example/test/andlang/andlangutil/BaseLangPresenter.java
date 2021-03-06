package com.example.test.andlang.andlangutil;

import android.util.Log;
import android.view.View;

import com.example.test.andlang.util.LogUtil;

/**
 * Created by root on 18-3-8.
 */

public abstract class BaseLangPresenter<T extends BaseLangViewModel> {
    public static String TAG=BaseLangApplication.TAG;
    public BaseLangActivity activity;
    public BaseLangFragment fragment;
    public T model;
    public BaseLangPresenter(BaseLangActivity activity, Class<? extends BaseLangViewModel> modelClass){
        try {
            this.activity=activity;
            model=(T) modelClass.newInstance();
        }catch (Exception e){
            Log.d("0.0",e.getMessage());
        }
    }
    public BaseLangPresenter(BaseLangFragment fragment,BaseLangActivity activity, Class<? extends BaseLangViewModel> modelClass){
        try {
            this.activity=activity;
            this.fragment=fragment;
            model=(T) modelClass.newInstance();
        }catch (Exception e){
            Log.d("0.0",e.getMessage());
        }
    }

    public abstract void initModel();


}
