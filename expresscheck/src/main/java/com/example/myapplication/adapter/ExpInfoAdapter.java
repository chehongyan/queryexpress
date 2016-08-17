package com.example.myapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.myapplication.R;
import com.example.myapplication.utils.MyImageCache;
import com.example.myapplication.utils.RequestUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 59427 on 2016/7/4.
 */
public class ExpInfoAdapter extends BaseAdapter {
    private JSONArray data;
    private Context context;

    public ExpInfoAdapter(JSONArray data, Context context) {
        this.data = data;
        this.context = context;
    }
    public void addData(JSONArray data){
        for (int i=0;i<data.length();i++){
            try {
                this.data.put(data.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getCount() {
        return data.length();
    }

    @Override
    public Object getItem(int position) {
        try {
            return data.getJSONArray(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView=View.inflate(context, R.layout.expinfo_item,null);
        }
        ViewHandler handler=new  ViewHandler();
        if (convertView.getTag()!=null){
            handler=(ViewHandler)convertView.getTag();
        }else {
            handler.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
            handler.simpleName= (TextView) convertView.findViewById(R.id.simpleName);
            handler.img= (NetworkImageView) convertView.findViewById(R.id.niv_img);
            convertView.setTag(handler);
        }
        try {
            JSONObject obj=data.getJSONObject(position);
            handler.tv_name.setText(obj.getString("expName"));
            handler.simpleName.setText(obj.getString("simpleName"));
            ImageLoader loader=new ImageLoader(RequestUtil.getRequestQueue(context),new MyImageCache());
            handler.img.setDefaultImageResId(R.drawable.loading);
            handler.img.setErrorImageResId(R.drawable.faild);
            handler.img.setImageUrl(obj.getString("imgUrl"),loader);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return convertView;
    }
    static class ViewHandler{
        public TextView tv_name;
        public TextView simpleName;
        public NetworkImageView img;
    }
}
