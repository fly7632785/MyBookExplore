package com.jafir.testclassinvoke;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jafir on 17/1/16.
 * 自定实现捕捉异常并且保存到本地和上传服务器
 *
 *
 */

public class MyUncautionCrashHandler implements Thread.UncaughtExceptionHandler {


    private static final String PATH = Environment.getExternalStorageDirectory() + "/crash/";
    private static final String FILE_NAME = "crash/";
    private static final String FILE_SUFFIX = ".trace";


    private static MyUncautionCrashHandler mInstance;
    private Thread.UncaughtExceptionHandler mDefaultHanlder;

    private Context context;


    public static MyUncautionCrashHandler getmInstance() {
        if(mInstance == null){
            mInstance = new MyUncautionCrashHandler();
        }
        return mInstance;
    }

    private MyUncautionCrashHandler() {

    }


    public void init(Context context) {
        this.context = context.getApplicationContext();
        mDefaultHanlder = Thread.getDefaultUncaughtExceptionHandler();
        /**
         * 设置为自己的
         */
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        dumpLog(throwable);

        /**
         * 这里可以上传到服务器
         * 就如同bugly一样
         */
        throwable.printStackTrace();



        /**
         * 如果系统没有提供默认的handler我们就自己处理 并且把log存到本地
         * 如果不设置的话 那么APP为静止不动了 卡死状态
         */
        if (mDefaultHanlder != null) {
            mDefaultHanlder.uncaughtException(thread, throwable);
        } else {
            Process.killProcess(Process.myPid());
        }
    }

    private void dumpLog(Throwable throwable) {

        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            Log.e("debug", "没有内存卡");
            return;
        }

        File directory = new File(PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        long currentTime = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(currentTime));

        File file = new File(directory,time + FILE_SUFFIX);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        PrintWriter pw = null;

        try {
            pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            pw.println(time);
            dumpPhontInfo(pw);
            throwable.printStackTrace(pw);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void dumpPhontInfo(PrintWriter pw) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packinfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            pw.println("app version:");
            pw.println(packinfo.versionName + "|" + packinfo.versionCode);
            pw.println("sdk版本号:");
            pw.println(Build.VERSION.RELEASE + "|" + Build.VERSION.SDK_INT);
            pw.println("厂商:");
            pw.println(Build.MANUFACTURER);
            pw.println("HARDWARE:");
            pw.println(Build.HARDWARE);
            pw.println("支持的abi:");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                for (int i = 0; i < Build.SUPPORTED_ABIS.length; i++) {
                    pw.print("abi:" + Build.SUPPORTED_ABIS[i]);
                }
            }
            pw.println("device:");
            pw.println(Build.DEVICE);
            pw.println("手机类型:");
            pw.println(Build.MODEL);
            pw.println("HOST:");
            pw.println(Build.HOST);
            pw.println("BRAND:");
            pw.println(Build.BRAND);


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


    }


}
