package com.jafir.mybookexplore;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by jafir on 16/12/12.
 */

public class AnimatorActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_animator);
        init();
    }

    private void init() {
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animator a = AnimatorInflater.loadAnimator(AnimatorActivity.this, R.animator.animator_translate);
                a.setTarget(v);
                a.start();
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animator a = AnimatorInflater.loadAnimator(AnimatorActivity.this, R.animator.animator_scale);
                a.setTarget(v);
                a.start();
            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //不能用这个改变颜色  因为前两位ff是alpha值，后面6为才是颜色的
                //所以需要先把 alpha值拆开然后再改变颜色 才行
//                final ValueAnimator a = ValueAnimator.ofInt(0xff3dffff,0xff000000);

                /**
                 * 第一种 21以上使用
                 */
//                final ValueAnimator a = ValueAnimator.ofArgb(0xff3dffff, 0xff000000);
//
//                a.setDuration(1000).addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        int color = (int) animation.getAnimatedValue();
//                        Log.d("debug", "color:" + color);
//                        v.setBackgroundColor(color);
//
//                    }
//                });
//                a.start();




                /**
                 * 另外一种 自己实现
                 */
                int startColor = 0xffffffff;
                int endColor = 0xff000000;
                ValueAnimator valueAnimator = ValueAnimator.ofObject(new TypeEvaluator() {
                    @Override
                    public Object evaluate(float fraction, Object startValue, Object endValue) {
                        //从初始的int类型的颜色值中解析出Alpha、Red、Green、Blue四个分量
                        int startInt = (Integer) startValue;
                        int startA = (startInt >> 24) & 0xff;
                        int startR = (startInt >> 16) & 0xff;
                        int startG = (startInt >> 8) & 0xff;
                        int startB = startInt & 0xff;

                        //从终止的int类型的颜色值中解析出Alpha、Red、Green、Blue四个分量
                        int endInt = (Integer) endValue;
                        int endA = (endInt >> 24) & 0xff;
                        int endR = (endInt >> 16) & 0xff;
                        int endG = (endInt >> 8) & 0xff;
                        int endB = endInt & 0xff;

                        //分别对Alpha、Red、Green、Blue四个分量进行计算，
                        //最终合成一个完整的int型的颜色值
                        return (int) ((startA + (int) (fraction * (endA - startA))) << 24) |
                                (int) ((startR + (int) (fraction * (endR - startR))) << 16) |
                                (int) ((startG + (int) (fraction * (endG - startG))) << 8) |
                                (int) ((startB + (int) (fraction * (endB - startB))));
                    }
                }, startColor, endColor);
                valueAnimator.setDuration(3000);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int color = (int) animation.getAnimatedValue();
                        v.setBackgroundColor(color);
                    }
                });
                valueAnimator.start();

            }
        });
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator.ofInt(v, "width", 500).setDuration(500).start();
            }
        });
        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator.ofFloat(v, "rotation", 0f,360f).setDuration(500).start();
            }
        });

    }
}
