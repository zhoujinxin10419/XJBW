package com.zjx.xjbw.wan_android.ui.wechat;

import com.zjx.xjbw.R;
import com.zjx.xjbw.base.BaseFragment;

public class WxFragment extends BaseFragment {

    public static WxFragment getInstance() {
        return new WxFragment();
    }

    @Override
    public int getLayoutResID() {
        return R.layout.home_fragment_layout;
    }
}
