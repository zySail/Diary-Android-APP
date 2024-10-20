package com.app.diary;

import android.app.Application;

import com.app.diary.data.DiaryDataSource;
import com.app.diary.data.impl.DiaryDataSourceImpl;
import com.app.diary.db.DbHelper;

public class Mapp extends Application {

    private static Mapp instance;//单例

    private DbHelper dbHelper;//数据库操作工具
    private DiaryDataSource diaryDataSource;//日记数据源

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    /**
     * 获取实例
     */
    public static Mapp getInstance() {
        return instance;
    }

    /**
     * 懒加载获取数据库操作工具
     */
    public DbHelper getDbHelper() {
        if (dbHelper == null) {
            dbHelper = new DbHelper(this);
        }
        return dbHelper;
    }

    /**
     * 懒加载获取日记数据源
     */
    public DiaryDataSource getDiaryDataSource() {
        if (diaryDataSource == null) {
            diaryDataSource = new DiaryDataSourceImpl(getDbHelper());
        }
        return diaryDataSource;
    }

}
