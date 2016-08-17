package com.example.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.bean.ExpBean;
import com.example.myapplication.utils.ExpOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 59427 on 2016/6/29.
 */
public class HistoryInfoDao {
    private Context context;
    ExpOpenHelper expOpenHelper;
    private SQLiteDatabase database;

    public HistoryInfoDao(Context context) {
        this.expOpenHelper = new ExpOpenHelper(context);
        this.database = expOpenHelper.getReadableDatabase();
    }

    /**
     * 是对数据库中的数据进行操作，那么首先要去获得SQLiteDatabase
     * 该类是通过SQLiteOpenHelperd的对象，调用getReadable...getWriteale....
     *
     * @param
     */

    public void insertData(ExpBean bean) {
        database = expOpenHelper.getWritableDatabase();
        String sql = "insert into expressinfo(expname,expno,imgUrl,status,number)values('" + bean.getExpName() + "','" + bean.getExpNo() + "','" + bean.getImgUrl() + "','" + bean.getStatus() + "','" + bean.getNumber() + "')";
        //database.execSQL("insert into expressinfo(expname,expno,imgUrl,status)values(?,?,?,?);",new Object[]{bean.getExpName(),bean.getExpNo(),bean.getImgUrl(),bean.getStatus()});
        database.execSQL(sql);
    }

    /**
     * 查询单条数据
     *
     * @return
     */
    public ExpBean queryExp(String expno) {
        String sql = "select * from expinfo where expname = ?";
        Cursor cursor = database.rawQuery(sql, new String[]{expno});
        while (cursor.moveToNext()) {
            String exname = cursor.getString(cursor.getColumnIndex("expname"));
            String imgUrl = cursor.getString(cursor.getColumnIndex("imgUrl"));
            String status = cursor.getString(cursor.getColumnIndex("status"));
            int number = cursor.getInt(cursor.getColumnIndex("number"));
            ExpBean expBean = new ExpBean(exname, expno,imgUrl,status,number);
            return expBean;
        }
        return null;
    }

    /**
     * 查询多条数据
     *
     * @return
     */

    public List<ExpBean> queryAllExpBean() {
        String sql = "select * from expressinfo";
        Cursor cursor = database.rawQuery(sql, null);
        List<ExpBean> beanList = new ArrayList<>();
        if(cursor != null && cursor.getCount() >0) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("expname"));
                String no = cursor.getString(cursor.getColumnIndex("expno"));
                String imgUrl = cursor.getString(cursor.getColumnIndex("imgUrl"));
                String status = cursor.getString(cursor.getColumnIndex("status"));
                int number = cursor.getInt(cursor.getColumnIndex("number"));
                ExpBean bean = new ExpBean(name, no, imgUrl, status, number);
                beanList.add(bean);
            }
        }
        return beanList;
    }

    /**
     * 删除
     *
     * @param expname
     */
    public void delete(String expname) {
        String sql = "delete from expressinfo where expname = '" + expname + "'";
        database.execSQL(sql);
     /*   String  where = "expressno = ?";
        database.delete("expressinfo",where,new String[]{expressNo});*/
    }

    public void update(String expname) {

       /* String  sql = "update expressinfo set expname = '申通' where expno = '"+ expno+"'";

        database.execSQL(sql);*/
        ContentValues values = new ContentValues();
        /*
        expname是表的字段的名字
        "韵达"是expname所对应的新值
        * */
        values.put("expname", "韵达");
        /*
        where指定条件
        new String[]{expno}：提供条件满足的值
        * */
        String where = " expno =?";
        database.update("expressinfo", values, where, new String[]{expname});


    }


}
