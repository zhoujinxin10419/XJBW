package com.zjx.xjbw.welcome;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.zjx.xjbw.MainActivity;
import com.zjx.xjbw.R;

import java.util.Objects;
import java.util.Random;

import master.flame.danmaku.controller.DrawHandler.Callback;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;

public class WelcomeActivity extends AppCompatActivity {

    private ImageView mPanel;
    private ImageView mPhoto;

    private DanmakuView danmakuView;
    private DanmakuContext danmakuContext;

    private TypedArray damuArray;
    private TypedArray colors;

    private BaseDanmakuParser parser = new BaseDanmakuParser() {
        @Override
        protected IDanmakus parse() {
            return new Danmakus();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setExitTransition(new Slide(Gravity.START));
        setContentView(R.layout.welcom_layout);
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
        decorView.setSystemUiVisibility(option);
        getWindow().setNavigationBarColor(Color.TRANSPARENT);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        mPanel = findViewById(R.id.welcome_panel);
        mPhoto = findViewById(R.id.welcome_photo);

        Bitmap bitmap = ImageUtils.toRoundCorner(ImageUtils.drawable2Bitmap(Objects.requireNonNull(getDrawable(R.mipmap.ic_launcher))),
                ConvertUtils.dp2px(8));
        mPhoto.setImageBitmap(bitmap);

        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator alpha = ObjectAnimator.ofFloat(mPanel, "alpha", 0f, 0.7f);
        alpha.setDuration(5000);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mPanel, "scaleX", 1.2f, 1f);
        scaleX.setDuration(2000);

        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mPanel, "scaleY", 1.2f, 1f);
        scaleY.setDuration(2000);

        animatorSet.play(alpha).with(scaleX).with(scaleY);
        animatorSet.start();
        animatorSet.addListener(new AnimatorAdapter() {
            @Override
            public void onAnimationEnd(Animator animator) {
                if (!isFinishing()) {
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(WelcomeActivity.this).toBundle());
                    new Handler().postDelayed(() -> finish(), 1000);
                }
            }
        });


        damuArray = getResources().obtainTypedArray(R.array.danmu_random);
        colors = getResources().obtainTypedArray(R.array.letter_tile_colors);
        danmakuView = findViewById(R.id.danmaku_view);
        danmakuView.enableDanmakuDrawingCache(true);
        danmakuView.setCallback(new DrawHandlerAdapter() {
            @Override
            public void prepared() {
                danmakuView.start();
                generateSomeDanmaku();
            }
        });
        danmakuContext = DanmakuContext.create();
        danmakuView.prepare(parser, danmakuContext);
    }

    private void generateSomeDanmaku() {
        new Thread(() -> {
            while (true) {
                int time = new Random().nextInt(200);
                String content = pickDanmu(String.valueOf(time));
                int color = pickColor(String.valueOf(time));
                if (isFinishing()) {
                    return;
                }
                int size = new Random().nextInt(20);

                addDanmaku(content, time > 150, size + 10, color);
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private String pickDanmu(String identifier) {
        final int index = Math.abs(identifier.hashCode()) % damuArray.length();
        return damuArray.getString(index);
    }

    private int pickColor(String identifier) {
        final int color = Math.abs(identifier.hashCode()) % colors.length();
        return colors.getColor(color, Color.BLACK);
    }

    private void addDanmaku(String content, boolean withBorder, int size, int color) {
        BaseDanmaku danmaku = danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        danmaku.text = content;
        danmaku.padding = 5;
        danmaku.textSize = ConvertUtils.dp2px(size);
        danmaku.textColor = color;
        danmaku.setTime(danmakuView.getCurrentTime());
        if (withBorder) {
            danmaku.borderColor = Color.GREEN;
        }
        danmakuView.addDanmaku(danmaku);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (danmakuView != null && danmakuView.isPrepared()) {
            danmakuView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (danmakuView != null && danmakuView.isPrepared() && danmakuView.isPaused()) {
            danmakuView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (danmakuView != null) {
            danmakuView.release();
            danmakuView = null;
        }
    }


    public abstract class DrawHandlerAdapter implements Callback {
        @Override
        public void prepared() {

        }

        @Override
        public void updateTimer(DanmakuTimer timer) {

        }

        @Override
        public void danmakuShown(BaseDanmaku danmaku) {

        }

        @Override
        public void drawingFinished() {

        }
    }

    public abstract class AnimatorAdapter implements Animator.AnimatorListener {

        @Override
        public void onAnimationStart(Animator animator) {

        }

        @Override
        public void onAnimationEnd(Animator animator) {

        }

        @Override
        public void onAnimationCancel(Animator animator) {

        }

        @Override
        public void onAnimationRepeat(Animator animator) {

        }
    }
}
