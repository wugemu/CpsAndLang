package com.example.test.andlang.andlangutil;

import android.app.Activity;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.test.andlang.util.ActivityUtil;
import com.example.test.andlang.util.imageload.ImageLoadUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

/**
 * Created by root on 18-3-7.
 */

public  class BaseLangViewModel extends Observable{


    public void notifyData(final Object tag){
        Activity activity= ActivityUtil.getInstance().getLastActivity();
        if(activity!=null){
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setChanged();    //改变通知者的状态
                    notifyObservers(tag);
                }
            });
        }else {
            setChanged();    //改变通知者的状态
            notifyObservers(tag);
        }
    }
}
