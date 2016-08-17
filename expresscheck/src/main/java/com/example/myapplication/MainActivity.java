package com.example.myapplication;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.example.myapplication.adapter.ViewPagerAdaper;
import com.example.myapplication.fragment.HistoryFragment;
import com.example.myapplication.fragment.PhoneFragment;
import com.example.myapplication.fragment.QueryFragment;
import com.example.myapplication.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private NoScrollViewPager vp;
    private TabLayout tab;
    private List<Fragment> fragments;
    private int[] img;
    private String[] txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initdata();

    }

    /**
     * viewpager中加载fragment，tablayout加载数据
     */
    private void initdata() {
        fragments = new ArrayList<>();
        fragments.add(new QueryFragment());
        fragments.add(new HistoryFragment());
        fragments.add(new PhoneFragment());
        ViewPagerAdaper adapter = new ViewPagerAdaper(getSupportFragmentManager(), fragments);
        vp.setAdapter(adapter);
        tab.setupWithViewPager(vp);
        img = new int[]{R.drawable.selector_query, R.drawable.selector_history, R.drawable.selector_phone};
        txt = new String[]{"快递查询", "查询历史", "快递电话"};
        tabAddData(img, txt);
    }

    /**
     * @param img 初始化图片
     * @param txt 初始化标题
     */
    private void tabAddData(int[] img, String[] txt) {
        tab.setSelectedTabIndicatorHeight(0);
        tab.setTabTextColors(Color.argb(0xff, 0x99, 0x99, 0x99), Color.argb(0xff, 0xf7, 0x39, 0x38));
        for (int i = 0; i < tab.getTabCount(); i++) {
            TabLayout.Tab t = tab.getTabAt(i);
            t.setIcon(img[i]);
            t.setText(txt[i]);


        }

    }

    /**
     * 初始化布局id
     */
    private void init() {
        vp = (NoScrollViewPager) findViewById(R.id.vp);
        tab = (TabLayout) findViewById(R.id.tab);

    }
}