package com.example.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 59427 on 2016/6/25.
 */
public class SpUtil {

    private static SharedPreferences sp;

    /**
     * 写入
     * @param context
     * @param key
     * @param value
     */
    public static void putBoolean(Context context, String key, Boolean value){
        if (sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key,value).commit();

    }

    /**
     * 读取
     * @param context
     * @param key
     * @param defValue
     * @return  默认值或结果
     */
    public static boolean getBoolean(Context context, String key, Boolean defValue){
        if (sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key,defValue);

    }
    /**
     * 写入
     * @param context
     * @param key
     * @param value
     */
    public static void putString(Context context, String key, String value){
        if (sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putString(key,value).commit();

    }

    /**
     * 读取
     * @param context
     * @param key
     * @param defValue
     * @return  默认值或结果
     */
    public static String getString(Context context, String key, String defValue){
        if (sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getString(key,defValue);

    }
    public static void putInt(Context context, String key, int value){
        if (sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putInt(key,value).commit();

    }

    public static int getInt(Context context, String key,int defValue){
        if (sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getInt(key,defValue);

    }



}
