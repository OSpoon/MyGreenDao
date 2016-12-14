package com.frames.spoon.mygreendao.daomanager;


import android.content.Context;

import com.frames.spoon.mygreendao.entity.gen.DaoMaster;
import com.frames.spoon.mygreendao.entity.gen.DaoSession;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * 创建数据库,创建数据表,包含对数据库的crud操作
 * <p>
 * Created by zhanxiaolin-n22 on 2016/12/12.
 */
public class DaoManager {

    private static final String TAG = DaoManager.class.getSimpleName();
    private static final String DB_NAME = "mydb.sqllite";
    private volatile static DaoManager manager;
    private static DaoMaster.DevOpenHelper helper;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;
    private Context context;

    public static DaoManager getInstance() {
        DaoManager instance = null;
        if (manager == null) {
            synchronized (DaoManager.class) {
                if (instance == null) {
                    instance = new DaoManager();
                    manager = instance;
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        this.context = context;
    }

    public DaoMaster getDaoMaster() {
        if (daoMaster == null) {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    public DaoSession getDaoSession() {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster();
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    public void setDebug() {
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }

    public void closeDaoSession() {
        if (daoSession != null) {
            daoSession.clear();
            daoSession = null;
        }
    }

    public void closeHelper() {
        if (helper != null) {
            helper.close();
            helper = null;
        }
    }

    public void closeConnection() {
        closeHelper();
        closeDaoSession();
    }
}
