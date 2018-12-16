package com.zjx.xjbw.welcome;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.ActivityUtils;

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityUtils.startActivity(WelcomeActivity.class);
        finish();
    }
}
