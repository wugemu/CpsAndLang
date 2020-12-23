package com.lxkj.dmhw.data;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lxkj.dmhw.MyApplication;
import com.lxkj.dmhw.Variable;

/**
 * SQLite数据库
 * 每一次改动都要记得升级数据库版本 向下兼容
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper() {
        super(MyApplication.getContext(), Variable.SQLiteName,null,8);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Screen(image varchar(50), link varchar(50), path varchar(50), time varchar(10), type varchar(50),activityid varchar(50))");// 闪屏
        db.execSQL("create table Other(_id integer primary key, status varchar(50))");// 启动信息
        db.execSQL("create table Search(_id integer primary key, content varchar(50),type varchar(10))");// 搜索记录
        // 初始化SQL
        db.execSQL("insert into Other(status) values (?)", new Object[] {"0"});

        //商学院搜索
        db.execSQL("create table ComCollSearch(_id integer primary key, content varchar(50))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                db.execSQL("create table Screen(image varchar(50), link varchar(50), path varchar(50), time varchar(10))");// 添加闪屏
                db.execSQL("alter table Screen add type varchar(50)");// 增加闪屏类型字段
                db.execSQL("drop table Footprint");// 删除足迹表
                break;
            case 2:
                db.execSQL("alter table Screen add type varchar(50)");// 增加闪屏类型字段
                db.execSQL("drop table Footprint");// 删除足迹表
                break;
            case 3:
                db.execSQL("drop table Footprint");// 删除足迹表
                break;

        }
        if(oldVersion<5){
            db.execSQL("alter table Screen add activityid varchar(50)");// 增加闪屏活动id字段
        }
        if (oldVersion<6) {
            db.execSQL("alter table Search add type varchar(10)");// 增加不同平台类型 0-淘宝 1-拼多多 2-京东 3-唯品会
            db.execSQL("delete from Search");
        }
        if (oldVersion<7) {
            db.execSQL("delete from Search where (select count(_id) from Search )>20 and _id in (select _id from Search order by _id desc limit (select count(_id) from Search) offset 20 )");
        }
        if (oldVersion<8) {
             //商学院搜索表
            db.execSQL("create table ComCollSearch(_id integer primary key, content varchar(50))");
        }
    }
}
