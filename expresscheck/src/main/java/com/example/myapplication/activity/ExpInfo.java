package com.example.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.android.volley.Response;
import com.example.myapplication.R;
import com.example.myapplication.adapter.ExpInfoAdapter;
import com.example.myapplication.utils.NetWorkState;
import com.example.myapplication.utils.RequestUtil;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 59427 on 2016/7/4.
 */
public class ExpInfo extends Activity implements View.OnClickListener {
    private PullToRefreshListView lv_exp;
    private int count = 1;
    private ExpInfoAdapter adapter;
    private String url = "http://route.showapi.com/64-20?showapi_appid=20070&showapi_sign=104d11a655d34ba6a82233e6cfc87c6d";
    private JSONArray data;
    private String simpleName;
    private String expName;
    private String imgUrl;
    private RelativeLayout rl;
    private Button btn_refresh;
    private RelativeLayout loading;
    private ImageButton back;
    private ILoadingLayout proxy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expinfo);
        initView();
        initData();
        returnData();

    }


    private void initData() {
        boolean state = NetWorkState.getNetWorkState(this);
        if (state) {
            loading.setVisibility(View.VISIBLE);
            rl.setVisibility(View.GONE);
            lv_exp.setVisibility(View.GONE);
            RequestUtil.exceuteJSONObject(this, url + "&maxSize=20", new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject o) {
                    loading.setVisibility(View.GONE);
                    lv_exp.setVisibility(View.VISIBLE);
                    try {
                        adapter = new ExpInfoAdapter(o.getJSONObject("showapi_res_body").getJSONArray("expressList"), getApplicationContext());
                        data = o.getJSONObject("showapi_res_body").getJSONArray("expressList");
                        lv_exp.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            lv_exp.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
            lv_exp.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
                @Override
                public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                    loadingMore();

                }
            });
        } else {
            lv_exp.setVisibility(View.GONE);
            rl.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 返回数据给QueryFragment界面
     */
    private void returnData() {
        lv_exp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    JSONObject o = data.getJSONObject(position - 1);
                    simpleName = o.getString("simpleName");
                    expName = o.getString("expName");
                    imgUrl = o.getString("imgUrl");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("simpleName", simpleName);
                bundle.putString("expName", expName);
                bundle.putString("imgUrl", imgUrl);
                intent.putExtras(bundle);
                setResult(1, intent);
                finish();

            }
        });
    }

    private void initView() {
        lv_exp = (PullToRefreshListView) findViewById(R.id.lv_exp);
        rl = (RelativeLayout) findViewById(R.id.rl);
        back = (ImageButton) findViewById(R.id.back);
        loading = (RelativeLayout) findViewById(R.id.loading);
        btn_refresh = (Button) findViewById(R.id.btn_refresh);
        lv_exp.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        btn_refresh.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    /**
     * 下拉加载更多数据
     */
    public void loadingMore() {
        count++;
        RequestUtil.exceuteJSONObject(this, url + "&maxSize=20&page=" + count, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject o) {
                try {
                    JSONArray data = o.getJSONObject("showapi_res_body").getJSONArray("expressList");
                    adapter.addData(data);
                    adapter.notifyDataSetChanged();
                    if (data.length() < 20) {
                        proxy = lv_exp.getLoadingLayoutProxy();
                        proxy.setRefreshingLabel("暂无更多");
                    }
                    lv_exp.onRefreshComplete();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_refresh:
                GradientDrawable drawable = new GradientDrawable();
                drawable.setCornerRadius(10);
                drawable.setStroke(1, Color.argb(0xff, 0x66, 0x66, 0x66));
                drawable.setColor(Color.argb(0xff, 0xf9, 0xf9, 0xf9));
                btn_refresh.setBackground(drawable);
                initData();
                break;
            case R.id.back:
                finish();
                break;
        }

    }
}
