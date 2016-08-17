package com.example.myapplication.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.example.myapplication.R;
import com.example.myapplication.bean.ExpressBean;
import com.example.myapplication.dao.ExpInfoDao;
import com.example.myapplication.utils.CallPhoneUtil;
import com.example.myapplication.utils.NetWorkState;
import com.example.myapplication.utils.RequestUtil;
import com.example.myapplication.utils.SaveImage;
import com.sortlistview.CharacterParser;
import com.sortlistview.ClearEditText;
import com.sortlistview.PinyinComparator;
import com.sortlistview.SideBar;
import com.sortlistview.SortAdapter;
import com.sortlistview.SortModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by 59427 on 2016/6/24.
 */
public class PhoneFragment extends Fragment {
    private String url = "http://route.showapi.com/64-20?showapi_appid=20070&showapi_sign=104d11a655d34ba6a82233e6cfc87c6d";
    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog;
    private SortAdapter adapter;
    private ClearEditText mClearEditText;
    private List<SortModel> SourceDateList;
    private PinyinComparator pinyinComparator;
    private List<ExpressBean> beanList;
    private View view;
    private LinearLayout ll;
    private RelativeLayout loading;
    private View wifi;
    private Button btn_refresh;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    beanList = (List<ExpressBean>) msg.obj;
                    if (beanList.size() > 0) {
                        initViews();
                        loading.setVisibility(View.GONE);
                        ll.setVisibility(View.VISIBLE);
                        adapter = new SortAdapter(getActivity(), SourceDateList);
                        sortListView.setAdapter(adapter);
                    }
            }
        }
    };
    private String phoneNumber;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sort_list, container, false);
        ExpInfoDao dao = new ExpInfoDao(getActivity());
        beanList = dao.queryAllExpBean();
        if (beanList.size() > 0) {
            initViews();
            adapter = new SortAdapter(getActivity(), SourceDateList);
            sortListView.setAdapter(adapter);
        } else {
            refresh();
        }
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (permissions[0].equals(Manifest.permission.PROCESS_OUTGOING_CALLS) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    new CallPhoneUtil(getActivity(), phoneNumber).call();
                }
                break;
        }
    }

    private void refresh() {
        boolean state = NetWorkState.getNetWorkState(getContext());
        initViews();
        if (state) {
            ll.setVisibility(View.GONE);
            loading.setVisibility(View.VISIBLE);
            wifi.setVisibility(View.GONE);
            initData();
        } else {
            ll.setVisibility(View.GONE);
            loading.setVisibility(View.GONE);
            wifi.setVisibility(View.VISIBLE);
        }
    }

    private void initData() {
        RequestUtil.exceuteJSONObject(getActivity(), url + "&maxSize=50", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject o) {
                try {
                    beanList = new ArrayList<>();
                    final JSONArray jsonArray = o.getJSONObject("showapi_res_body").getJSONArray("expressList");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {
                                    String expName = jsonArray.getJSONObject(i).getString("expName");
                                    String phone = jsonArray.getJSONObject(i).getString("phone");
                                    String imgUrl = jsonArray.getJSONObject(i).getString("imgUrl");
                                    ExpressBean bean = new ExpressBean(expName, phone, imgUrl);
                                    beanList.add(bean);
                                    new SaveImage(getContext()).saveImg(imgUrl, expName);
                                    ExpInfoDao dao = new ExpInfoDao(getActivity());
                                    dao.insertData(bean);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            Message message = handler.obtainMessage();
                            message.what = 1;
                            message.obj = beanList;
                            handler.sendMessage(message);
                        }
                    }).start();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initViews() {
        ll = (LinearLayout) view.findViewById(R.id.ll);
        loading = (RelativeLayout) view.findViewById(R.id.loading);
        wifi = view.findViewById(R.id.wifi);
        btn_refresh = (Button) wifi.findViewById(R.id.btn_refresh);
        sideBar = (SideBar) view.findViewById(R.id.sidrbar);
        dialog = (TextView) view.findViewById(R.id.dialog);
        sortListView = (ListView) view.findViewById(R.id.lv_phone);
        mClearEditText = (ClearEditText) view.findViewById(R.id.filter_edit);
        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });
        //实例化汉字转拼音类
        pinyinComparator = new PinyinComparator();
        sideBar.setTextView(dialog);
        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }
            }
        });

        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                phoneNumber = SourceDateList.get(position).getNum();
                new CallPhoneUtil(getActivity(), phoneNumber).getpermission();


            }
        });
        SourceDateList = filledData(beanList);
        // 根据a-z进行排序源数据
        Collections.sort(SourceDateList, pinyinComparator);

        //根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * 为ListView填充数据
     *
     * @param
     * @return
     */
    private List<SortModel> filledData(List<ExpressBean> beanList) {
        List<SortModel> mSortList = new ArrayList<SortModel>();
        for (int i = 0; i < beanList.size(); i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(beanList.get(i).getExpName());
            sortModel.setNum(beanList.get(i).getPhone());
            sortModel.setImgUrl(beanList.get(i).getImgUrl());
            //汉字转换成拼音
            String pinyin = CharacterParser.converCnToPY(beanList.get(i).getExpName());
            String sortString = pinyin.substring(0, 1).toUpperCase();
            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }
            mSortList.add(sortModel);
        }
        return mSortList;
    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<SortModel> filterDateList = new ArrayList<SortModel>();
        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
        } else {
            filterDateList.clear();
            for (SortModel sortModel : SourceDateList) {
                String name = sortModel.getName();
                name = name.toUpperCase();
                String pname = CharacterParser.converCnToPY(name).toUpperCase();
                if (name.indexOf(filterStr.toString().toUpperCase()) != -1 || pname.startsWith(filterStr.toString().toUpperCase())) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        if (adapter != null) {
            adapter.updateListView(filterDateList);
        }
    }

}
