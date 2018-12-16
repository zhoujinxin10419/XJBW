package com.zjx.xjbw;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

public class XJBWApplication extends Application {

    private static XJBWApplication myApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        myApplication = this;
    }

    public static synchronized XJBWApplication getInstance() {
        return myApplication;
    }
}
