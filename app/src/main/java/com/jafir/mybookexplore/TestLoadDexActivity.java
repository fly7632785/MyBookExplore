package com.jafir.mybookexplore;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * Created by jafir on 17/1/4.
 */

public class TestLoadDexActivity extends AppCompatActivity{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

    }

    private void init() {




        File optimizedDexOutputPath = new File(
                Environment.getExternalStorageDirectory()
                        + File.separator + "test_dexloader.apk");// 外部路径
        Log.d("debug", optimizedDexOutputPath.toString());

        File dexOutputDir = this.getDir("mydex", 0);// 无法直接从外部路径加载.dex文件，需要指定APP内部路径作为缓存目录（.dex文件会被解压到此目录）
        DexClassLoader dexClassLoader = new DexClassLoader(optimizedDexOutputPath.getAbsolutePath(),dexOutputDir.getAbsolutePath(), null, getClassLoader());
        Log.d("debug", dexOutputDir.toString());

        if(dexOutputDir.exists()){
            String[] files = dexOutputDir.list();
            for (int i = 0; i < files.length; i++) {
                Log.d("debug", files.toString());
            }
        }else {
            dexOutputDir.mkdirs();
        }

        Class libProviderClazz = null;
        try {
            libProviderClazz = dexClassLoader.loadClass("com.jafir.testclassinvoke.TestLoadDex");
            // 遍历类里所有方法
            Method[] methods = libProviderClazz.getDeclaredMethods();
            for (int i = 0; i < methods.length; i++) {
                Log.d("debug", methods[i].toString());
            }
            Method start = libProviderClazz.getDeclaredMethod("print",String.class);// 获取方法
            Field a = libProviderClazz.getDeclaredField("a");
            a.setAccessible(true);
            Log.d("debug", "a:"+a.getInt(libProviderClazz.newInstance()));
            Log.d("debug", "a:"+a.getName());
            start.setAccessible(true);// 把方法设为public，让外部可以调用
            String string = (String) start.invoke(libProviderClazz.newInstance(),"加载成功啦?");// 调用方法并获取返回值
            Toast.makeText(this, string, Toast.LENGTH_LONG).show();
        } catch (Exception exception) {
            // Handle exception gracefully here.
            exception.printStackTrace();
        }



        //获取resource来加载资源
//        try {
//            AssetManager assetManager = AssetManager.class.newInstance();
//            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
//            addAssetPath.invoke(assetManager, mDexPath);
//            mAssetManager = assetManager;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Resources superRes = super.getResources();
//        mResources = new Resources(mAssetManager, superRes.getDisplayMetrics(),
//                superRes.getConfiguration());

    }
}
