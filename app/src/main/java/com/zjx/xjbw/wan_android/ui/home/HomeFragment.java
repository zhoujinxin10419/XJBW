package com.zjx.xjbw.wan_android.ui.home;

import com.zjx.xjbw.R;
import com.zjx.xjbw.base.BaseFragment;

public class HomeFragment extends BaseFragment {

    public static HomeFragment getInstance() {
        return new HomeFragment();
    }

    @Override
    public int getLayoutResID() {
        return R.layout.home_fragment_layout;
    }
}
