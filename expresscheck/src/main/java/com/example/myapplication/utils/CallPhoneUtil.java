package com.example.myapplication.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;

/**
 * Created by 59427 on 2016/7/12.
 */
public class CallPhoneUtil {
    private Context context;
    private String phoneNumber;

    public CallPhoneUtil(Context context,String phoneNumber) {
        this.context = context;
        this.phoneNumber=phoneNumber;
    }

    public void getpermission() {
        int permission = ActivityCompat.checkSelfPermission(context, Manifest.permission.PROCESS_OUTGOING_CALLS);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.PROCESS_OUTGOING_CALLS)) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.PROCESS_OUTGOING_CALLS}, 1);
            } else {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.PROCESS_OUTGOING_CALLS}, 2);
            }
        }else {
            call();
        }
    }

    public void call() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        context.startActivity(intent);

    }



}
