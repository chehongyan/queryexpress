package com.example.myapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 59427 on 2016/6/26.
 */
public class ResultsAdapter extends BaseAdapter {
    private JSONArray data;
    private Context context;

    public ResultsAdapter(JSONArray data, Context context) {
        this.data = data;
        this.context = context;
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
        View view = null;
        view = View.inflate(context, R.layout.list_item, null);
        TextView tv_address = (TextView) view.findViewById(R.id.tv_address);
        TextView tv_time = (TextView) view.findViewById(R.id.tv_time);
        ImageView iv_img = (ImageView) view.findViewById(R.id.iv_img);
        View view1 = view.findViewById(R.id.view1);
        JSONObject obj = null;
        try {
            obj = data.getJSONObject(position);
            tv_address.setText(obj.getString("context"));
            tv_time.setText(obj.getString("time"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (position == 0) {
            tv_address.setTextColor(Color.argb(0xff, 0xf7, 0x39, 0x38));
            tv_time.setTextColor(Color.argb(0xff, 0xf7, 0x39, 0x38));
            iv_img.setImageResource(R.mipmap.red);
            view1.setVisibility(View.INVISIBLE);
           // view.setPadding(-8,0,0,0);
        }
        return view;
    }
}
