package com.jafir.mybookexplore;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jafir.mybookexplore.widget.MyRoundImageView;
import com.jafir.mybookexplore.widget.Xfermodes;

import static android.bluetooth.BluetoothDevice.EXTRA_CLASS;

public class MainActivity extends AppCompatActivity {
    private ComponentName mDefault;
    private ComponentName mIcon1;
    private ComponentName mIcon2;
    private PackageManager mPm;
    private Button button;

    private void change(String x) {
        x += "123";
    }

    private void change(StringBuilder x) {
        x.delete(0, x.length());
        x.append("even");
    }


    /**
     * 这个方法在 onstart （onRestore）之后
     *
     * @param savedInstanceState
     */
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        int width = button.getWidth();
        int height = button.getHeight();

        int width1 = button.getMeasuredWidth();
        int height1 = button.getMeasuredHeight();


        Log.d("debug", "onPostCreate:" + width);
        Log.d("debug", "onPostCreate:" + width1);
    }

    /**
     * 在onREsume之后
     */
    @Override
    protected void onPostResume() {
        super.onPostResume();

        int width = button.getWidth();
        int height = button.getHeight();

        int width1 = button.getMeasuredWidth();
        int height1 = button.getMeasuredHeight();


        Log.d("debug", "onPostResume:" + width);
        Log.d("debug", "onPostResume:" + width1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /**
         * 不能直接通过形参传入修改本来的string
         *
         * 如果非要 建议Stringbuilder
         *
         *
         * 1.如果要操作少量的数据用 = String

         　2.单线程操作字符串缓冲区 下操作大量数据 = StringBuilder

         　3.多线程操作字符串缓冲区 下操作大量数据 = StringBuffer
         *
         *
         *
         */

        String x = new String("goeasyway");
        change(x);
        Log.d("debug", "X:" + x);

        StringBuilder builder = new StringBuilder("jafir");
        change(builder);
        Log.d("debug", "builder:" + builder);


        initIcomChange();


        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toScreen(v);
            }
        });
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSearch(v);
                overridePendingTransition(R.anim.enter_up, R.anim.out_up);
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AnimatorActivity.class));
            }
        });
        final MyRoundImageView myRoundImageView = (MyRoundImageView) findViewById(R.id.myavatar);
        myRoundImageView.setOutBorder(10, getResources().getColor(R.color.colorPrimary));
        myRoundImageView.setInsideColor(getResources().getColor(R.color.colorAccent));
        myRoundImageView.setInsideThickness(0);
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRoundImageView.setOutBorder(15, getResources().getColor(R.color.colorAccent));
                myRoundImageView.setInsideColor(getResources().getColor(R.color.colorPrimary));
                myRoundImageView.setInsideThickness(10);
            }
        });
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Xfermodes.class));
            }
        });
        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TestViewMove.class));
            }
        });
        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TestMyViewpagerActivity.class));
            }
        });
        findViewById(R.id.button7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TestMyViewpagerActivity1.class));
            }
        });
        findViewById(R.id.button8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TestScrollListViewActivity.class));
            }
        });
        findViewById(R.id.button9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TestScrollRecyclerViewActivity.class));
            }
        });
        findViewById(R.id.button10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();
            }
        });
        findViewById(R.id.button14).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TestLoadDexActivity.class));
            }
        });


    }


    private void initIcomChange() {
//        第一种  不行  启动别的之后，再启动 启动不了
//        mDefault = getComponentName();
//        第二种可行
        mDefault = new ComponentName(getBaseContext(), "com.jafir.mybookexplore.MainActivity");
        mIcon1 = new ComponentName(getBaseContext(), "com.jafir.mybookexplore.icon1");
        mIcon2 = new ComponentName(getBaseContext(), "com.jafir.mybookexplore.icon2");

        mPm = getApplicationContext().getPackageManager();

        findViewById(R.id.button11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDefault();
            }
        });
        findViewById(R.id.button12).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeIcon1();
            }
        });
        findViewById(R.id.button13).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeIcon2();
            }
        });


    }

    private void changeDefault() {
        enableComponentName(mDefault);
        disableComponentName(mIcon1);
        disableComponentName(mIcon2);
    }

    private void changeIcon1() {

        enableComponentName(mIcon1);
        disableComponentName(mDefault);
        disableComponentName(mIcon2);

    }

    private void changeIcon2() {
        enableComponentName(mIcon2);
        disableComponentName(mIcon1);
        disableComponentName(mDefault);
    }

    /**
     * 关闭桌面图标
     *
     * @param c
     */
    private void disableComponentName(ComponentName c) {
        mPm.setComponentEnabledSetting(c,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
        );
    }

    private void enableComponentName(ComponentName c) {
        mPm.setComponentEnabledSetting(c,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
        );
    }


    /**
     * 使用TaskStackBuilder
     * 在notification跳转到pending界面之后
     * 返回 回到主界面（无论主界面是否关闭）
     * 原理：设置pending界面的父亲为主界面 （xml里面设置）
     * 返回就会跳转父亲界面
     * <p>
     * 16以上使用
     */
    private void sendNotification() {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher).setTicker("新资讯")
                .setWhen(System.currentTimeMillis())
                .setOngoing(false)
                .setAutoCancel(true);
        /**
         * 关键代码
         */
        Intent intent = new Intent(this, TestScrollRecyclerViewActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(TestScrollRecyclerViewActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);
        /**
         * 关键代码
         */
//       PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
//       intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, mBuilder.build());


    }

    private void toSearch(View v) {
        startActivity(new Intent(this, SearchActivity.class));
    }

    private void toScreen(View view) {
        startActivity(new Intent(this, PreferenceScreenActivity.class));
    }


}