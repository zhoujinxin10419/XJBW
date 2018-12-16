package com.zjx.xjbw;

import android.support.annotation.IntDef;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.zjx.xjbw.base.BaseNotNetActivity;
import com.zjx.xjbw.wan_android.ui.home.HomeFragment;
import com.zjx.xjbw.wan_android.ui.wechat.WxFragment;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class MainActivity extends BaseNotNetActivity {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            TabIndex.INIT,
            TabIndex.HOME_PAGE,
            TabIndex.WE_CHAT
    })
    public @interface TabIndex {
        int INIT = -1;
        int HOME_PAGE = 0;
        int WE_CHAT = 1;
    }

    private static final String HOME_PAGE_TAG = "honme_page";
    private static final String WE_CHAT = "we_chat";
    private FragmentManager fragmentManager = getSupportFragmentManager();
    @TabIndex
    private int selectedTab = TabIndex.INIT;

    @Override
    public int getViewLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);
    }

    @Override
    public void initData() {

    }

    private void hideAllFragments() {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragmentManager.findFragmentByTag(HOME_PAGE_TAG) != null) {
            transaction.hide(fragmentManager.findFragmentByTag(HOME_PAGE_TAG));
        }
        if (fragmentManager.findFragmentByTag(WE_CHAT) != null) {
            transaction.hide(fragmentManager.findFragmentByTag(WE_CHAT));
        }
        transaction.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_home:

                if (selectedTab != TabIndex.HOME_PAGE) {
                    selectedTab = TabIndex.HOME_PAGE;
                }
                hideAllFragments();
                Fragment homeFragment = fragmentManager.findFragmentByTag(HOME_PAGE_TAG);
                if (homeFragment == null) {
                    fragmentManager
                            .beginTransaction()
                            .add(R.id.fragment_container, HomeFragment.getInstance(), HOME_PAGE_TAG)
                            .commit();
                } else {
                    fragmentManager.beginTransaction().show(homeFragment).commit();
                }
                return true;

            case R.id.navigation_we_chat:

                if (selectedTab != TabIndex.WE_CHAT) {
                    selectedTab = TabIndex.WE_CHAT;
                }
                hideAllFragments();
                Fragment weChatFragment = fragmentManager.findFragmentByTag(WE_CHAT);
                if (weChatFragment == null) {
                    fragmentManager
                            .beginTransaction()
                            .add(R.id.fragment_container, WxFragment.getInstance(), WE_CHAT)
                            .commit();
                } else {
                    fragmentManager.beginTransaction().show(weChatFragment).commit();
                }
                return true;
            case R.id.navigation_notifications:
                return true;
        }
        return false;
    };
}
