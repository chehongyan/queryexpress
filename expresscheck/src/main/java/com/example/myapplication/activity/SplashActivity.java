package com.example.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.RelativeLayout;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.utils.SpUtil;

/**
 * Created by 59427 on 2016/7/1.
 */
public class SplashActivity extends Activity {
    private RelativeLayout rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean isFirstEnter = SpUtil.getBoolean(
                        SplashActivity.this, "is_first_enter", true);
                Intent intent;
                if (isFirstEnter) {
                    intent = new Intent(SplashActivity.this,
                            GuideActivity.class);
                    SpUtil.putBoolean(SplashActivity.this, "is_first_enter", false);
                } else {
                    intent = new Intent(getApplicationContext(),
                            MainActivity.class);
                }
                startActivity(intent);
                finish();

            }
        },3000);


   /*     rl = (RelativeLayout) findViewById(R.id.rl);
        // 渐变动画
        AlphaAnimation alpha = new AlphaAnimation(0.5f, 1);
        alpha.setDuration(3000);
        alpha.setFillAfter(true);
        rl.startAnimation(alpha);
        alpha.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                boolean isFirstEnter = SpUtil.getBoolean(
                        SplashActivity.this, "is_first_enter", true);
                Intent intent;
                if (isFirstEnter) {
                    intent = new Intent(SplashActivity.this,
                            GuideActivity.class);
                    SpUtil.putBoolean(SplashActivity.this, "is_first_enter", false);
                } else {
                    intent = new Intent(getApplicationContext(),
                            MainActivity.class);
                }
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });*/
    }
}