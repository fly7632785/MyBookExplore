package com.jafir.testclassinvoke;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.jafir.testclassinvoke.singleton.SimpleSingle;
import com.jafir.testclassinvoke.singleton.SimpleSingle1;
import com.jafir.testclassinvoke.singleton.SimpleSingle2;
import com.jafir.testclassinvoke.singleton.SimpleSingle3;
import com.jafir.testclassinvoke.singleton.SimpleSingle4;

public class MainActivity extends AppCompatActivity {

    private String[] imgs = new String[]{
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_1000&sec=1484566929477&di=35e6ea372632e560ec97ffe64b9aa6b8&imgtype=0&src=http%3A%2F%2Fsc.jb51.net%2Fuploads%2Fallimg%2F150703%2F14-150F3102113Y7.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_1000&sec=1484566929477&di=dbf141a72c7b8b673f5560d64cd559b8&imgtype=0&src=http%3A%2F%2Fimg2.3lian.com%2F2014%2Ff2%2F15%2Fd%2F48.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_1000&sec=1484566929477&di=7bd0705ccf68739b66727717f4395b33&imgtype=0&src=http%3A%2F%2Fpic17.nipic.com%2F20111108%2F3484168_101404023000_2.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_1000&sec=1484566929477&di=fa839b0d55a2ff39d20dc2ff2e4be8c9&imgtype=0&src=http%3A%2F%2Fimg15.3lian.com%2F2015%2Fc2%2F95%2Fd%2F34.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_1000&sec=1484566929477&di=68e8af28d9e819353e538b65b5cdb74a&imgtype=0&src=http%3A%2F%2Fimg10.3lian.com%2Fc1%2Fnewpic%2F12%2F09%2F15.jpg"

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * 模拟操作抛出异常
                 */
                throw new RuntimeException("模拟抛出异常");
            }
        });
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * 模拟操作抛出异常
                 */
                startActivity(new Intent(MainActivity.this, TestImageLoad.class));
            }
        });

        ImageView img1, img2, img3, img4, img5;

        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        img4 = (ImageView) findViewById(R.id.img4);
        img5 = (ImageView) findViewById(R.id.img5);

        img1.setScaleType(ImageView.ScaleType.FIT_XY);
        img2.setScaleType(ImageView.ScaleType.FIT_XY);
        img3.setScaleType(ImageView.ScaleType.FIT_XY);
        img4.setScaleType(ImageView.ScaleType.FIT_XY);
        img5.setScaleType(ImageView.ScaleType.FIT_XY);

//        ImageLoader.getInstance(this).bindBitmap(imgs[0], img1, 10, 10);
//        ImageLoader.getInstance(this).bindBitmap(imgs[1], img2, 10, 10);
//        ImageLoader.getInstance(this).bindBitmap(imgs[2], img3, 10, 10);
//        ImageLoader.getInstance(this).bindBitmap(imgs[3], img4, 10, 10);
//        ImageLoader.getInstance(this).bindBitmap(imgs[4], img5, 10, 10);

        AnimatorSet animSetForSpeaking = new AnimatorSet();
        animSetForSpeaking.setDuration(5000);
        animSetForSpeaking.setInterpolator(new AccelerateDecelerateInterpolator());
        ObjectAnimator animtion1 = ObjectAnimator.ofFloat(img1, "scaleY", 1f, 2f, 1f);
        animtion1.setRepeatCount(ValueAnimator.INFINITE);
        ObjectAnimator animtion2 = ObjectAnimator.ofFloat(img2, "scaleY", 1f, 4f, 1f);
        animtion2.setRepeatCount(ValueAnimator.INFINITE);
        ObjectAnimator animtion3 = ObjectAnimator.ofFloat(img3, "scaleY", 1f, 2f, 1f);
        animtion3.setRepeatCount(ValueAnimator.INFINITE);
        ObjectAnimator animtion4 = ObjectAnimator.ofFloat(img4, "scaleY", 1f, 4f, 1f);
        animtion4.setRepeatCount(ValueAnimator.INFINITE);
        ObjectAnimator animtion5 = ObjectAnimator.ofFloat(img5, "scaleY", 1f, 2f, 1f);
        animtion5.setRepeatCount(ValueAnimator.INFINITE);

        animSetForSpeaking.playTogether(animtion1, animtion2, animtion3, animtion4, animtion5);
        animSetForSpeaking.start();

//        只有simplesingle 不支持多线程
        Log.d("debug","\nsingle");
        testSingleThread();
//        Log.d("debug","\nsingle1");
//        testSingleThread1();
//        Log.d("debug","\nsingle2");
//        testSingleThread2();
//        Log.d("debug","\nsingle3");
//        testSingleThread3();
//        Log.d("debug","\nsingle4");
//        testSingleThread4();

    }

    private void testSingleThread3() {

        for (int i = 0; i < 10; i++) {
            SimpleSingle3.getInstance().i++;
            Log.d("debug","3+:"+SimpleSingle3.getInstance().i);
        }

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    SimpleSingle3.getInstance().i--;
                    Log.d("debug","3-:"+SimpleSingle3.getInstance().i);
                }
            }
        };
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    SimpleSingle3.getInstance().i++;
                    Log.d("debug","3+:"+SimpleSingle3.getInstance().i);
                }
            }
        };

        new Thread(runnable).start();
        new Thread(runnable).start();
        new Thread(runnable1).start();


//        结果到最后应该还是=0
    }
    private void testSingleThread2() {

        for (int i = 0; i < 10; i++) {
            SimpleSingle2.getInstance().i++;
            Log.d("debug","2+:"+SimpleSingle2.getInstance().i);
        }

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    SimpleSingle2.getInstance().i--;
                    Log.d("debug","2-:"+SimpleSingle2.getInstance().i);
                }
            }
        };
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    SimpleSingle2.getInstance().i++;
                    Log.d("debug","2+:"+SimpleSingle2.getInstance().i);
                }
            }
        };

        new Thread(runnable).start();
        new Thread(runnable).start();
        new Thread(runnable1).start();


//        结果到最后应该还是=0
    }
    private void testSingleThread1() {

        for (int i = 0; i < 10; i++) {
            SimpleSingle1.getInstance().i++;
            Log.d("debug","1+:"+SimpleSingle1.getInstance().i);
        }

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    SimpleSingle1.getInstance().i--;
                    Log.d("debug","1-:"+SimpleSingle1.getInstance().i);
                }
            }
        };
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    SimpleSingle1.getInstance().i++;
                    Log.d("debug","1+:"+SimpleSingle1.getInstance().i);
                }
            }
        };

        new Thread(runnable).start();
        new Thread(runnable).start();
        new Thread(runnable1).start();


//        结果到最后应该还是=0
    }
    private void testSingleThread() {

        for (int i = 0; i < 10; i++) {
            SimpleSingle.getInstance().i++;
            Log.d("debug","+:"+SimpleSingle.getInstance().i);
        }

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    SimpleSingle.getInstance().i--;
                    Log.d("debug","-:"+SimpleSingle.getInstance().i);
                }
            }
        };
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    SimpleSingle.getInstance().i++;
                    Log.d("debug","+:"+SimpleSingle.getInstance().i);
                }
            }
        };

        new Thread(runnable).start();
        new Thread(runnable).start();
        new Thread(runnable1).start();


//        结果到最后应该还是=0
    }


    private void testSingleThread4() {

        for (int i = 0; i < 10; i++) {
            SimpleSingle4.getInstance().i++;
            Log.d("debug","4+:"+SimpleSingle4.getInstance().i);
        }

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    SimpleSingle4.getInstance().i--;
                    Log.d("debug","4-:"+SimpleSingle4.getInstance().i);
                }
            }
        };
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    SimpleSingle4.getInstance().i++;
                    Log.d("debug","4+:"+SimpleSingle4.getInstance().i);
                }
            }
        };

        new Thread(runnable).start();
        new Thread(runnable).start();
        new Thread(runnable1).start();


//        结果到最后应该还是=0
    }
}
