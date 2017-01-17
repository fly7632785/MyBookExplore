package com.jafir.testclassinvoke;

import android.app.Application;

/**
 * Created by jafir on 17/1/16.
 */

public class App extends Application {

    private static App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        MyUncautionCrashHandler myUncautionCrashHandler = MyUncautionCrashHandler.getmInstance();
        myUncautionCrashHandler.init(this);
    }


    public static App getmInstance() {
        return mInstance;
    }
}
