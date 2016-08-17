package com.example.myapplication.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 59427 on 2016/6/29.
 */
public class ExpOpenHelper extends SQLiteOpenHelper {
     /*
       第一个参数是上下文环境
       第二个参数是数据库的名字，后缀的.db可有可无
       第三个参数是游标工厂：使用默认的，直接传递null
       第四个参数是数据库的版本信息：整数，只能变大不能变小
       */

    public ExpOpenHelper(Context context) {
        super(context, "express", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //第一次创建数据的时候调用，适用于在这里创建表，做初始化操作，自动执行该方法
        String sql="create table expressinfo(expname varchar(20),expno varchar(20),imgUrl varchar(30),status varchar(20),number varchar(5))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //数据库升级的时候会调用：就是版本变大的时候会调用
        /*String  sql ="alter table expressinfo add column image int";
        db.execSQL(sql);*/
    }
}
