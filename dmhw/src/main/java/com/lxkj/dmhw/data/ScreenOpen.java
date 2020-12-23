package com.lxkj.dmhw.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lxkj.dmhw.bean.Screen;

/**
 * 闪屏
 */
public class ScreenOpen {

    private DataBaseHelper helper;
    private SQLiteDatabase db;

    public ScreenOpen() {
        helper = new DataBaseHelper();
    }

    /**
     * 插入数据
     * @param screen
     */
    public void insert(Screen screen) {
        db = helper.getWritableDatabase();// 初始化SQLiteDatabase对象
        db.execSQL("insert into Screen(image,link,path,time,type,activityid) values(?,?,?,?,?,?)",new Object[]{ screen.getAdvimg(),screen.getJumpvaue(),screen.getPath(),screen.getCountdown(),screen.getJumptype(),screen.getJumpvaue()});
    }

    /**
     * 删除数据
     */
    public void delete() {
        db = helper.getWritableDatabase();// 初始化SQLiteDatabase对象
        if (find() != null) {
            db.execSQL("delete from Screen");
        }
    }
    /**
     * 查找数据
     * @return
     */
    public Screen find() {
        db = helper.getWritableDatabase();// 初始化SQLiteDatabase对象
        Cursor cursor = db.rawQuery("select * from Screen",null);
        if (cursor.moveToNext()) {
            Screen screen = new Screen();
            screen.setAdvimg(cursor.getString(cursor.getColumnIndex("image")));
            screen.setJumpvaue(cursor.getString(cursor.getColumnIndex("link")));
            screen.setPath(cursor.getString(cursor.getColumnIndex("path")));
            screen.setCountdown(cursor.getString(cursor.getColumnIndex("time")));
            screen.setJumptype(cursor.getString(cursor.getColumnIndex("type")));
            return screen;
        }
        return null;
    }

}
