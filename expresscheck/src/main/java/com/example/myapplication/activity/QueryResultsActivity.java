package com.example.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.myapplication.R;
import com.example.myapplication.adapter.ResultsAdapter;
import com.example.myapplication.bean.ExpBean;
import com.example.myapplication.dao.HistoryInfoDao;
import com.example.myapplication.utils.MyImageCache;
import com.example.myapplication.utils.NetWorkState;
import com.example.myapplication.utils.RequestUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QueryResultsActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView lv_results;
    private ResultsAdapter adapter;
    private String url = "http://route.showapi.com/64-19?showapi_appid=20070&showapi_sign=104d11a655d34ba6a82233e6cfc87c6d";
    private TextView tv_name;
    private TextView tv_num;
    private String name;
    private String num;
    private NetworkImageView niv_img;
    private LinearLayout ll;
    private RelativeLayout rl;
    private TextView tv_no_result;
    private Button btn_refresh;
    private int code;
    private String expName;
    private String imgUrl;
    private String info;
    private int number;
    private Boolean flag;
    private RelativeLayout loading;
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.query_results);
        initView();
        getExtraData();
        isNetwork();
    }

    /**
     * 获取QueryFragment界面传递的数据
     */
    private void getExtraData() {
        Intent intent = getIntent();
        name = intent.getStringExtra("simpleName");
        expName = intent.getStringExtra("expName");
        num = intent.getStringExtra("num");
        imgUrl = intent.getStringExtra("imgUrl");
        tv_name.setText(expName);
        tv_num.setText(num);
        ImageLoader loader = new ImageLoader(RequestUtil.getRequestQueue(getApplication()), new MyImageCache());
        niv_img.setImageUrl(imgUrl, loader);
    }

    /**
     * 请求网络数据
     */
    private void initData() {
        RequestUtil.exceuteJSONObject1(this, url + "&com=" + name + "&nu=" + num, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject o) {
                loading.setVisibility(View.GONE);
                try {
                    code = o.getInt("showapi_res_code");
                    if (code == 0) {
                        flag = o.getJSONObject("showapi_res_body").getBoolean("flag");
                        int ret_code = o.getJSONObject("showapi_res_body").getInt("ret_code");
                        if (ret_code == -1) {
                            tv_no_result.setText("单号错误，请核实");
                        }
                        getData();
                        if (flag) {
                            number = o.getJSONObject("showapi_res_body").getInt("status");
                            JSONArray data = o.getJSONObject("showapi_res_body").getJSONArray("data");
                            if (data.length() == 0) {
                                flag = false;
                                getData();
                                info = "暂无结果，点击继续查询";
                            } else {
                                number = o.getJSONObject("showapi_res_body").getInt("status");
                                switch (number) {
                                    case 1:
                                        info = "暂无结果，点击继续查询";
                                        break;
                                    case 2:
                                        info = data.getJSONObject(0).getString("context");
                                        break;
                                    case 4:
                                        info = "快递已签收";
                                        break;
                                }
                            }
                            List<ExpBean> beanList = new ArrayList<>();
                            ExpBean expBean = new ExpBean(expName, num, imgUrl, info, number);
                            beanList.add(expBean);
                            HistoryInfoDao dao = new HistoryInfoDao(getApplicationContext());
                            dao.insertData(expBean);
                            adapter = new ResultsAdapter(data, QueryResultsActivity.this);
                            lv_results.setAdapter(adapter);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initView() {
        lv_results = (ListView) findViewById(R.id.lv_results);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_num = (TextView) findViewById(R.id.tv_num);
        niv_img = (NetworkImageView) findViewById(R.id.iv_img);
        back = (ImageButton) findViewById(R.id.back);
        ll = (LinearLayout) findViewById(R.id.ll);
        rl = (RelativeLayout) findViewById(R.id.rl);
        loading = (RelativeLayout) findViewById(R.id.loading);
        tv_no_result = (TextView) findViewById(R.id.tv_no_result);
        btn_refresh = (Button) findViewById(R.id.btn_refresh);
        btn_refresh.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    /**
     * 网络是否连接
     */
    public void isNetwork() {
        if (NetWorkState.getNetWorkState(this)) {
            rl.setVisibility(View.GONE);
            ll.setVisibility(View.GONE);
            tv_no_result.setVisibility(View.GONE);
            loading.setVisibility(View.VISIBLE);
            initData();
        } else {
            rl.setVisibility(View.VISIBLE);
            ll.setVisibility(View.GONE);
            tv_no_result.setVisibility(View.GONE);
        }
    }

    public void getData() {
        if (flag) {
            rl.setVisibility(View.GONE);
            ll.setVisibility(View.VISIBLE);
            tv_no_result.setVisibility(View.GONE);
        } else {
            rl.setVisibility(View.GONE);
            ll.setVisibility(View.GONE);
            tv_no_result.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_refresh:
                isNetwork();
                break;
            case R.id.back:
                finish();
        }
    }
}
