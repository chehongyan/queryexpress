package com.example.myapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.myapplication.R;
import com.example.myapplication.bean.ExpBean;
import com.example.myapplication.utils.MyImageCache;
import com.example.myapplication.utils.RequestUtil;

import java.util.List;

/**
 * Created by 59427 on 2016/6/14.
 */
public class HistoryAdapter extends BaseAdapter {
    List<ExpBean> beanList;
    private Context context;

    public HistoryAdapter(List<ExpBean> beanList, Context context) {
        this.beanList = beanList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return beanList.size();
    }

    @Override
    public Object getItem(int position) {

        return beanList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.history_item, null);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_num = (TextView) convertView.findViewById(R.id.tv_num);
            holder.tv_status = (TextView) convertView.findViewById(R.id.tv_status);
            holder.iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
            holder.niv_img = (NetworkImageView) convertView.findViewById(R.id.niv_img);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_name.setText(beanList.get(position).getExpName());
        holder.tv_num.setText(beanList.get(position).getExpNo());
        int number = beanList.get(position).getNumber();
        switch (number) {
            case 1:
                holder.tv_status.setTextColor(Color.rgb(0xa6, 0x07, 0x95));
                holder.tv_status.setPadding(0, 0, 0, 0);
                holder.iv_img.setVisibility(View.GONE);
                break;
            case 4:
                holder.tv_status.setTextColor(Color.rgb(0x31, 0xc8, 0x26));
                holder.iv_img.setImageResource(R.mipmap.location_green);
                break;
        }
        holder.tv_status.setText(beanList.get(position).getStatus());
        ImageLoader loader = new ImageLoader(RequestUtil.getRequestQueue(context), new MyImageCache());
        holder.niv_img.setImageUrl(beanList.get(position).getImgUrl(), loader);

        return convertView;
    }

    static class ViewHolder {
        TextView tv_name;
        TextView tv_num;
        NetworkImageView niv_img;
        TextView tv_status;
        ImageView iv_img;

    }

}
