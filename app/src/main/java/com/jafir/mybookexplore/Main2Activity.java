package com.jafir.mybookexplore;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    private View moveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        init();
    }

    private void init() {

        moveButton = findViewById(R.id.move);
        moveButton.post(new Runnable() {
            @Override
            public void run() {
                Log.d("debug",
                        "old\n"+
                                "l:" + moveButton.getLeft() + "\t" +
                                "t:" + moveButton.getTop() + "\t" +
                                "r:" + moveButton.getRight() + "\t" +
                                "b:" + moveButton.getBottom() + "\n" +
                                "getX:" + moveButton.getX() + "\n" +
                                "getY:" + moveButton.getY() + "\n" +
                                "getScrollX:" + moveButton.getScrollX() + "\n" +
                                "getScrollY:" + moveButton.getScrollY() + "\n" +
                                "getTranslationX:" + moveButton.getTranslationX() + "\n" +
                                "getTranslationX:" + moveButton.getTranslationY());
            }
        });

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveButton.setTranslationY(100);
//                moveButton.setTranslationZ(100);
                moveButton.setTranslationX(100);
                Log.d("debug",
                        "setTranslationX\n"+
                        "l:" + moveButton.getLeft() + "\t" +
                                "t:" + moveButton.getTop() + "\t" +
                                "r:" + moveButton.getRight() + "\t" +
                                "b:" + moveButton.getBottom() + "\n" +
                                "getX:" + moveButton.getX() + "\n" +
                                "getY:" + moveButton.getY() + "\n" +
                                "getScrollX:" + moveButton.getScrollX() + "\n" +
                                "getScrollY:" + moveButton.getScrollY() + "\n" +
                                "getTranslationX:" + moveButton.getTranslationX() + "\n" +
                                "getTranslationX:" + moveButton.getTranslationY());
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveButton.scrollBy(100, 100);
                moveButton.invalidate();
                Log.d("debug",
                        "scrollBy\n"+
                        "l:" + moveButton.getLeft() + "\t" +
                                "t:" + moveButton.getTop() + "\t" +
                                "r:" + moveButton.getRight() + "\t" +
                                "b:" + moveButton.getBottom() + "\n" +
                                "getX:" + moveButton.getX() + "\n" +
                                "getY:" + moveButton.getY() + "\n" +
                                "getScrollX:" + moveButton.getScrollX() + "\n" +
                                "getScrollY:" + moveButton.getScrollY() + "\n" +
                                "getTranslationX:" + moveButton.getTranslationX() + "\n" +
                                "getTranslationX:" + moveButton.getTranslationY());
            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveButton.offsetLeftAndRight(100);
                moveButton.offsetTopAndBottom(100);
                Log.d("debug",
                        "offsetLeftAndRight\n"+
                        "l:" + moveButton.getLeft() + "\t" +
                                "t:" + moveButton.getTop() + "\t" +
                                "r:" + moveButton.getRight() + "\t" +
                                "b:" + moveButton.getBottom() + "\n" +
                                "getX:" + moveButton.getX() + "\n" +
                                "getY:" + moveButton.getY() + "\n" +
                                "getScrollX:" + moveButton.getScrollX() + "\n" +
                                "getScrollY:" + moveButton.getScrollY() + "\n" +
                                "getTranslationX:" + moveButton.getTranslationX() + "\n" +
                                "getTranslationX:" + moveButton.getTranslationY());
            }
        });
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TranslateAnimation animation = new TranslateAnimation(0,100,0,100);
                animation.setDuration(2000);
                animation.setFillAfter(true);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Log.d("debug",
                                "onAnimationEnd\n"+
                                        "l:" + moveButton.getLeft() + "\t" +
                                        "t:" + moveButton.getTop() + "\t" +
                                        "r:" + moveButton.getRight() + "\t" +
                                        "b:" + moveButton.getBottom() + "\n" +
                                        "getX:" + moveButton.getX() + "\n" +
                                        "getY:" + moveButton.getY() + "\n" +
                                        "getScrollX:" + moveButton.getScrollX() + "\n" +
                                        "getScrollY:" + moveButton.getScrollY() + "\n" +
                                        "getTranslationX:" + moveButton.getTranslationX() + "\n" +
                                        "getTranslationX:" + moveButton.getTranslationY());
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                moveButton.startAnimation(animation);
                Log.d("debug",
                        "animation\n"+
                        "l:" + moveButton.getLeft() + "\t" +
                                "t:" + moveButton.getTop() + "\t" +
                                "r:" + moveButton.getRight() + "\t" +
                                "b:" + moveButton.getBottom() + "\n" +
                                "getX:" + moveButton.getX() + "\n" +
                                "getY:" + moveButton.getY() + "\n" +
                                "getScrollX:" + moveButton.getScrollX() + "\n" +
                                "getScrollY:" + moveButton.getScrollY() + "\n" +
                                "getTranslationX:" + moveButton.getTranslationX() + "\n" +
                                "getTranslationX:" + moveButton.getTranslationY());
            }
        });
        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.MarginLayoutParams l = (ViewGroup.MarginLayoutParams) moveButton.getLayoutParams();
                l.setMargins(100,100,0,0);
                moveButton.setLayoutParams(l);
                Log.d("debug",
                        "margin\n"+
                        "l:" + moveButton.getLeft() + "\t" +
                                "t:" + moveButton.getTop() + "\t" +
                                "r:" + moveButton.getRight() + "\t" +
                                "b:" + moveButton.getBottom() + "\n" +
                                "getX:" + moveButton.getX() + "\n" +
                                "getY:" + moveButton.getY() + "\n" +
                                "getScrollX:" + moveButton.getScrollX() + "\n" +
                                "getScrollY:" + moveButton.getScrollY() + "\n" +
                                "getTranslationX:" + moveButton.getTranslationX() + "\n" +
                                "getTranslationX:" + moveButton.getTranslationY());
            }
        });

//        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                moveButton = findViewById(R.id.move);
//            }
//        });

        moveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(Main2Activity.this, "点击了", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
