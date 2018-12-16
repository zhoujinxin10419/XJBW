package com.zjx.xjbw.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.View;

public abstract class BaseNotNetActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setEnterTransition(new Explode());
        getWindow().setExitTransition(new Explode());
        setContentView(getViewLayoutId());
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
        decorView.setSystemUiVisibility(option);

        initView();
        initData();
    }

    public abstract int getViewLayoutId();

    public abstract void initView();

    public abstract void initData();
}
