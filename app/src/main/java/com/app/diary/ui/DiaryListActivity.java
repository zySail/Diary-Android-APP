package com.app.diary.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.diary.Mapp;
import com.app.diary.R;
import com.app.diary.adapter.DiaryRecyclerAdapter;
import com.app.diary.bean.Diary;
import com.app.diary.utils.SizeUtils;

import java.util.List;

/**
 * 日志列表
 */
public class DiaryListActivity extends BaseActivity {

    private static final int REQUEST_BROWSE = 1;

    private Toolbar toolbar;//标题栏控件
    private RecyclerView recyclerView;//列表控件

    private DiaryRecyclerAdapter diaryRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_list);
        initView();
        setView();
    }

    /**
     * 后一个页面回传的处理
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case REQUEST_BROWSE: {
                if (resultCode == RESULT_OK) {
                    //刷新数据
                    List<Diary> list = Mapp.getInstance().getDiaryDataSource().selectList();
                    diaryRecyclerAdapter.setNewData(list);
                }
            }
            break;

        }
    }

    /**
     * 初始化控件
     */
    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerView);
    }

    /**
     * 设置控件
     */
    private void setView() {
        //将标题栏关联到页面
        setSupportActionBar(toolbar);
        //显示返回键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //设置列表的布局样式
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        //设置列表的间隔距离
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int count = parent.getAdapter().getItemCount();
                int index = parent.getChildAdapterPosition(view);
                if (index < count - 1) {
                    outRect.set(0, 0, 0, SizeUtils.dp2px(30));
                }
            }

        });
        //设置列表的适配器
        diaryRecyclerAdapter = new DiaryRecyclerAdapter();
        diaryRecyclerAdapter.setOnItemClickListener(new DiaryRecyclerAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(Diary diary, int position) {
                Intent intent = DiaryBrowseActivity.buildIntent(DiaryListActivity.this, diary.getId());
                startActivityForResult(intent, REQUEST_BROWSE);
            }

        });
        recyclerView.setAdapter(diaryRecyclerAdapter);

        //将数据加入适配器
        List<Diary> list = Mapp.getInstance().getDiaryDataSource().selectList();
        diaryRecyclerAdapter.setNewData(list);
    }

    /**
     * 构建意图
     */
    public static Intent buildIntent(@NonNull Context context) {
        Intent intent = new Intent(context, DiaryListActivity.class);
        return intent;
    }

}