package com.example.myapplication.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by 59427 on 2016/7/2.
 */
public class NetWorkState {
    /**
     * 查看当前网络状态
     * @param context
     * @return true代表可用，false代表不可用
     */
    public static boolean getNetWorkState(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo!=null&&networkInfo.isAvailable()){
            return true;
        }
        return false;
    }

}
