package com.frames.spoon.mygreendao.daomanager;

import android.Manifest;
import android.content.Context;
import android.os.Trace;

import com.frames.spoon.mygreendao.entity.User;
import com.frames.spoon.mygreendao.entity.gen.DaoMaster;
import com.frames.spoon.mygreendao.entity.gen.UserDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by zhanxiaolin-n22 on 2016/12/14.
 */
public class CommonUtils {
    private DaoManager manager;

    public CommonUtils(Context context) {
        manager = DaoManager.getInstance();
        manager.init(context);
    }

    public boolean insert(User user) {
        boolean flag = false;
        flag = manager.getDaoSession().insert(user) != -1 ? true : false;
        return flag;
    }

    public boolean insertMult(final List<User> users) {

        boolean flag = false;
        try {
            manager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (User user : users) {
                        manager.getDaoSession().insertOrReplace(user);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean update(User user) {
        boolean flag = false;
        try {
            manager.getDaoSession().update(user);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean delete(User user) {
        boolean flag = false;
        try {
            manager.getDaoSession().delete(user);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public List<User> listAll() {
        return manager.getDaoSession().loadAll(User.class);
    }

    public User listOne(long key) {
        return manager.getDaoSession().load(User.class, key);
    }

    public void load1(long key) {
        //native sql 查询
        manager.getDaoSession().queryRaw(User.class, "where name like ? and _id > ?", new String[]{"%li%", "1001"});
    }

    public void load2(long key) {
        QueryBuilder<User> builder = manager.getDaoSession().queryBuilder(User.class);
        List<User> users = builder.where(UserDao.Properties.Age.ge(23)).where(UserDao.Properties.Name.like("zhang")).list();
    }
}
