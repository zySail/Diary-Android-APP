package com.app.diary.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.diary.bean.Diary;

import java.util.List;

/**
 * 日记本数据源
 */
public interface DiaryDataSource {

    /**
     * 新增一条日记本
     */
    void insertDiary(@NonNull Diary diary);

    /**
     * 删除一条日记本
     */
    void deleteDiary(long diaryId);

    /**
     * 修改一条日记本
     */
    void updateDiary(long diaryId, @NonNull Diary diary);

    /**
     * 根据主键查询日记本
     */
    @Nullable
    Diary selectOne(long diaryId);

    /**
     * 查找日记本列表
     */
    @NonNull
    List<Diary> selectList();

}
