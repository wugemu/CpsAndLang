package com.lxkj.dmhw.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 其他信息
 * Created by zyhant on 18-2-11.
 */

public class OtherOpen {

    private DataBaseHelper helper;
    private SQLiteDatabase db;

    public OtherOpen(){
        helper = new DataBaseHelper();
    }

    /**
     * 查询是否第一次启动
     * @return
     */
    public boolean findStatus() {
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select status from Other where _id = 1", null);
        if (cursor.moveToNext()) {
            return !cursor.getString(cursor.getColumnIndex("status")).equals("1");
        } else {
            return true;
        }
    }

    /**
     * 查询是否输入过邀请码
     * @return
     */
    public boolean findInvitation() {
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select invitation from Other where _id = 1", null);
        if (cursor.moveToNext()) {
            if (cursor.getString(cursor.getColumnIndex("invitation")).equals("1")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 更新启动状态
     * @param key
     */
    public void updateStatus(String key) {
        db = helper.getWritableDatabase();
        db.execSQL("update Other set status = ? where _id = 1", new Object[] { key });
    }

    /**
     * 更新邀请码
     * @param key
     */
    public void updateInvitation(String key) {
        db = helper.getWritableDatabase();
        db.execSQL("update Other set invitation = ? where _id = 1", new Object[] { key });
    }

}
