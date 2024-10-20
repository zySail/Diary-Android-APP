package com.app.diary.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * 数据库操作工具
 */
public class DbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "diary.db";//数据库的名称
    public static final int DB_VERSION = 1;//数据库的版本号
    public static final String TABLE_DIARY = "diary";//日记表

    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createDiaryTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /**
     * 创建日记表
     */
    private void createDiaryTable(SQLiteDatabase db) {
        //如果表已存在则删除表
        String dropSql = "DROP TABLE IF EXISTS " + TABLE_DIARY + ";";
        db.execSQL(dropSql);

        //创建表
        String createSql = "CREATE TABLE IF NOT EXISTS " + TABLE_DIARY + " ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + "date INTEGER NOT NULL, "
                + "weather VARCHAR NOT NULL, "
                + "title VARCHAR NOT NULL, "
                + "content VARCHAR NOT NULL, "
                + "create_time INTEGER NOT NULL, "
                + "update_time INTEGER NOT NULL"
                + ");";
        db.execSQL(createSql);
    }

}
