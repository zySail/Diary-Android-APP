package com.app.diary.data.impl;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.diary.bean.Diary;
import com.app.diary.data.DiaryDataSource;
import com.app.diary.db.DbHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 日志数据源的实现类
 */
public class DiaryDataSourceImpl implements DiaryDataSource {

    @NonNull
    private DbHelper dbHelper;

    public DiaryDataSourceImpl(@NonNull DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public void insertDiary(@NonNull Diary diary) {
        ContentValues values = new ContentValues();
        values.put("date", diary.getDate().getTime());
        values.put("weather", diary.getWeather());
        values.put("title", diary.getTitle());
        values.put("content", diary.getContent());
        values.put("create_time", System.currentTimeMillis());
        values.put("update_time", System.currentTimeMillis());
        dbHelper.getWritableDatabase().insert(DbHelper.TABLE_DIARY, null, values);
    }

    @Override
    public void deleteDiary(long diaryId) {
        dbHelper.getWritableDatabase().delete(DbHelper.TABLE_DIARY, "id=?", new String[]{"" + diaryId});
    }

    @Override
    public void updateDiary(long diaryId, @NonNull Diary diary) {
        ContentValues values = new ContentValues();
        values.put("date", diary.getDate().getTime());
        values.put("weather", diary.getWeather());
        values.put("title", diary.getTitle());
        values.put("content", diary.getContent());
        values.put("update_time", System.currentTimeMillis());
        dbHelper.getWritableDatabase().update(DbHelper.TABLE_DIARY, values, "id=?", new String[]{"" + diaryId});
    }

    @Nullable
    @Override
    public Diary selectOne(long diaryId) {
        String[] columns = new String[]{"id", "date", "weather", "title", "content", "create_time", "update_time"};
        Cursor cursor = dbHelper.getReadableDatabase().query(DbHelper.TABLE_DIARY, columns, "id=?", new String[]{"" + diaryId}, null, null, null);

        Diary diary = null;
        if (cursor.moveToNext()) {
            diary = createDiary(cursor);
        }
        cursor.close();
        return diary;
    }

    @SuppressLint("Range")
    @NonNull
    @Override
    public List<Diary> selectList() {
        String[] columns = new String[]{"id", "date", "weather", "title", "content", "create_time", "update_time"};
        Cursor cursor = dbHelper.getReadableDatabase().query(DbHelper.TABLE_DIARY, columns, null, null, null, null, "date DESC");

        List<Diary> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            Diary diary = createDiary(cursor);
            list.add(diary);
        }
        cursor.close();
        return list;
    }

    @SuppressLint("Range")
    private Diary createDiary(@NonNull Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndex("id"));
        long date = cursor.getLong(cursor.getColumnIndex("date"));
        String weather = cursor.getString(cursor.getColumnIndex("weather"));
        String title = cursor.getString(cursor.getColumnIndex("title"));
        String content = cursor.getString(cursor.getColumnIndex("content"));
        long createTime = cursor.getLong(cursor.getColumnIndex("create_time"));
        long updateTime = cursor.getLong(cursor.getColumnIndex("update_time"));

        Diary diary = new Diary();
        diary.setId(id);
        diary.setDate(new Date(date));
        diary.setWeather(weather);
        diary.setTitle(title);
        diary.setContent(content);
        diary.setCreateTime(new Date(createTime));
        diary.setUpdateTime(new Date(updateTime));
        return diary;
    }

}
