package com.lxkj.dmhw.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * 搜索
 * Created by Zyhant on 2018/1/8.
 */

public class SearchOpen {

    private DataBaseHelper helper;
    private SQLiteDatabase db;

    public SearchOpen() {
        helper = new DataBaseHelper();
    }

    /**
     * 添加搜索记录  添加不同平台的搜索记录
     * @param content
     */
    public void add(String content,String type) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into Search (content,type) values (?,?)",new Object[] { content,type });
    }

    /**
     * 删除搜索历史记录
     */
    public void delete(String type) {
        if (count(type) > 0) {
            db.execSQL("delete from Search where type= ?",new String[]{type});
        }
    }

    //删除指定记录
    public void deleteItem(String content,String type) {
            db.execSQL("delete from Search where content= ? and type= ? ",new String[]{content,type});
    }

    //保留最新20条记录
    public void Save20Content() {
        db.execSQL("delete from Search where (select count(_id) from Search )>20 and _id in (select _id from Search order by _id desc limit (select count(_id) from Search) offset 20 )");
    }
    /**
     * 获取搜索记录总数
     * @return
     */
    public long count(String type) {
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select count(_id) from Search where type = ? ", new String[] { type});
        if (cursor.moveToNext()) {
            return cursor.getLong(0);
        }
        return 0;
    }

    /**
     * 获取搜索历史记录
     * @param type
     * @return
     */
    public ArrayList<String> find(String type) {
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select content from Search where type = ? ORDER BY _id DESC ", new String[] {type});
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
    public int ishas(String content,String type) {
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select content from Search where content= ? and type= ?", new String[]{content, type});
        int i = 0;
        while (cursor.moveToNext()) {
            i++;
        }
        return i;
    }
}
