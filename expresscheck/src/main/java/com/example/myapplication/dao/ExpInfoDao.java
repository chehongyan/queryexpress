package com.example.myapplication.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.bean.ExpressBean;
import com.example.myapplication.utils.MyOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 59427 on 2016/6/29.
 */
public class ExpInfoDao {
    private Context context;
    MyOpenHelper helper;
    private SQLiteDatabase database;

    public ExpInfoDao(Context context) {
        this.helper = new MyOpenHelper(context);
        this.database = helper.getReadableDatabase();
    }

    public void insert(String expname, String expno) {
        database = helper.getWritableDatabase();
        String sql = "insert into expinfo(expname,expno) values('" + expname + "','" + expno + "')";
        database.execSQL(sql);
    }

    /**
     * 是对数据库中的数据进行操作，那么首先要去获得SQLiteDatabase
     * 该类是通过SQLiteOpenHelperd的对象，调用getReadable...getWriteale....
     *
     * @param
     */
    public void insertData(ExpressBean bean) {
        database = helper.getWritableDatabase();
        String sql = "insert into expinfo(expname,expno,imgUrl) values('" + bean.getExpName() + "','" + bean.getPhone() + "','" + bean.getImgUrl() + "')";
        database.execSQL(sql);
    }

    /**
     * 查询单条数据
     *
     * @return
     */
    public ExpressBean queryExp(String expno) {
        String sql = "select * from expinfo where expname = ?";
        Cursor cursor = database.rawQuery(sql, new String[]{expno});
        if(cursor != null && cursor.getCount() >0) {
            while (cursor.moveToNext()) {
                String exname = cursor.getString(cursor.getColumnIndex("expname"));
                String no = cursor.getString(cursor.getColumnIndex("no"));
                String imgUrl = cursor.getString(cursor.getColumnIndex("imgUrl"));
                ExpressBean bean = new ExpressBean(exname, no, imgUrl);
                return bean;
            }
        }
        return null;
    }

    /**
     * 查询多条数据
     *
     * @return
     */
    public List<ExpressBean> queryAllExpBean() {
        String sql = "select * from expinfo";
        Cursor cursor = database.rawQuery(sql, null);
        List<ExpressBean> beanList = new ArrayList<>();
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("expname"));
            String no = cursor.getString(cursor.getColumnIndex("expno"));
            String imgUrl = cursor.getString(cursor.getColumnIndex("imgUrl"));
            ExpressBean bean = new ExpressBean(name, no, imgUrl);
            beanList.add(bean);
        }
        return beanList;

    }

}
