package com.zjx.xjbw.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.zjx.xjbw.R;
import com.zjx.xjbw.XJBWApplication;

public abstract class BaseFragment extends Fragment {

    private Activity mActivity;
    private Context mContext;
    private ViewGroup mBaseView;
    private ViewGroup mLoadingViewGroup;
    private LottieAnimationView mLottieAnimationView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutResID(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = getActivity();
        mContext = XJBWApplication.getInstance();

        initView();
    }

    private void initView() {
        if(getView() == null){
            return;
        }

        mBaseView = getView().findViewById(R.id.base_view);
        if(mBaseView == null){
            throw new IllegalStateException("The subclass of RootActivity must contain a View named 'mBaseView'.");
        }

        if(!(mBaseView.getParent() instanceof ViewGroup)){
            throw new IllegalStateException("mNormalView's ParentView should be a ViewGroup.");
        }

        ViewGroup parent = (ViewGroup) mBaseView.getParent();
        LayoutInflater.from(mActivity).inflate(R.layout.view_loading, parent);
        mLoadingViewGroup = parent.findViewById(R.id.loading_group);
        mLottieAnimationView = mLoadingViewGroup.findViewById(R.id.anim_view);
        mBaseView.setVisibility(View.GONE);
    }

    public abstract int getLayoutResID();
}
