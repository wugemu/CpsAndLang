package com.lxkj.dmhw.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * 搜索
 */

public class ComCollSearchOpen {

    private DataBaseHelper helper;
    private SQLiteDatabase db;

    public ComCollSearchOpen() {
        helper = new DataBaseHelper();
    }

    /**
     * 添加搜索记录  添加不同平台的搜索记录
     * @param content
     */
    public void add(String content) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into ComCollSearch (content) values (?)",new Object[] { content});
    }

    /**
     * 删除搜索历史记录
     */
    public void delete() {
        if (count() > 0) {
            db.execSQL("delete from ComCollSearch");
        }
    }

    //删除指定记录
    public void deleteItem(String content) {
            db.execSQL("delete from ComCollSearch where content= ? ",new String[]{content});
    }

    //保留最新20条记录
    public void Save20Content() {
        db.execSQL("delete from ComCollSearch where (select count(_id) from ComCollSearch )>20 and _id in (select _id from ComCollSearch order by _id desc limit (select count(_id) from ComCollSearch) offset 20 )");
    }
    /**
     * 获取搜索记录总数
     * @return
     */
    public long count() {
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select count(_id) from ComCollSearch", null);
        if (cursor.moveToNext()) {
            return cursor.getLong(0);
        }
        return 0;
    }

    /**
     * 获取搜索历史记录
     * @return
     */
    public ArrayList<String> find() {
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select content from ComCollSearch ORDER BY _id DESC ", null);
        ArrayList<String> contents = new ArrayList<>();
        while (cursor.moveToNext()){
           contents.add(cursor.getString(cursor.getColumnIndex("content")));
        }
        return contents;
    }
    /**
     * 获取是否存在该内容
     * @param content
     * @return
     */
    public int ishas(String content) {
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select content from ComCollSearch where content= ?", new String[]{content});
        int i = 0;
        while (cursor.moveToNext()) {
            i++;
        }
        return i;
    }
}
