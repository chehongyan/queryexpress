package com.example.myapplication.utils;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 59427 on 2016/7/4.
 */
public class SaveImage {
    private Context context;

    public SaveImage(Context context) {
        this.context = context;
    }
    /**
     * 保存图片到本地
     *
     * @param path
     * @param expName
     */

    public void saveImg(String path, String expName) {
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) ;
            {
                InputStream inputStream = connection.getInputStream();
                File file = new File(context.getFilesDir(), expName + ".jpg");
                FileOutputStream outputStream = new FileOutputStream(file);
                int len = -1;
                while ((len = inputStream.read()) != -1) {
                    outputStream.write(len);
                }
                inputStream.close();
                outputStream.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
