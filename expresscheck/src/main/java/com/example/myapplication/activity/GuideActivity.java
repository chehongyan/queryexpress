package com.example.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.adapter.GuideAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 59427 on 2016/7/1.
 */
public class GuideActivity extends Activity {
    private ViewPager paper;
    private int [] imgs=new int[]{R.mipmap.guide1,R.mipmap.guide2,R.mipmap.guide3,R.mipmap.guide4};
    private List<ImageView> imageViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide);
        initView();
        initData();
        paper.setAdapter(new GuideAdapter(imageViews));

    }

    private void initData() {
        imageViews = new ArrayList<>();
        for (int i = 0; i < imgs.length; i++) {
            ImageView view=new ImageView(this);
            view.setBackgroundResource(imgs[i]);
            imageViews.add(view);
            if (i==3){
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        }
    }

    private void initView() {
        paper = (ViewPager) findViewById(R.id.paper);
    }
}
