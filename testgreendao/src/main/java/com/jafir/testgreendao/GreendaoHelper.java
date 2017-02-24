package com.jafir.testgreendao;

import android.database.sqlite.SQLiteDatabase;

import com.jafir.testgreendao.gen.DaoMaster;
import com.jafir.testgreendao.gen.DaoSession;

/**
 * Created by jafir on 17/2/21.
 */
public class GreendaoHelper {


    private static DaoMaster.DevOpenHelper mHelper;
    private static SQLiteDatabase mdb;
    private static DaoMaster mDaoMaster;
    private static DaoSession mDaoSession;


    public static  void init(){

        mHelper = new DaoMaster.DevOpenHelper(AppContext.context,"jaifr-db");
        mdb = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(mdb);
        mDaoSession = mDaoMaster.newSession();

    }

    public static DaoMaster getDaoMaster() {
        return mDaoMaster;
    }

    public static DaoSession getDaoSession() {
        return mDaoSession;
    }
}
