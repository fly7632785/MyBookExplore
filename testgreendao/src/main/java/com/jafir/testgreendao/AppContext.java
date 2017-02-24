package com.jafir.testgreendao;

import android.app.Application;
import android.content.Context;

/**
 * Created by jafir on 17/2/21.
 */

public class AppContext extends Application{


    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;
        GreendaoHelper.init();

    }
}
