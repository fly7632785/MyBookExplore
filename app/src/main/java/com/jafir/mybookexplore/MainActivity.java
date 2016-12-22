package com.jafir.mybookexplore;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jafir.mybookexplore.widget.MyRoundImageView;
import com.jafir.mybookexplore.widget.Xfermodes;

public class MainActivity extends AppCompatActivity {


    private ComponentName mDefault;
    private ComponentName mIcon1;
    private ComponentName mIcon2;
    private PackageManager mPm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initIcomChange();


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
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


    }

    private void initIcomChange() {
        mDefault = getComponentName();
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
