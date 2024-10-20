package com.app.diary.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;

import com.app.diary.Mapp;
import com.app.diary.R;
import com.app.diary.bean.Diary;
import com.app.diary.utils.ToastUtils;

import java.text.SimpleDateFormat;

/**
 * 日志浏览
 */
public class DiaryBrowseActivity extends BaseActivity {

    private static final String EXTRA_DIARY_ID = "diaryId";

    private static final int REQUEST_EDIT = 1;

    private Toolbar toolbar;//标题栏控件
    private NestedScrollView scrollView;//滑动控件
    private TextView dateTextView;//日期文本控件
    private TextView weekTextView;//星期文本控件
    private TextView weatherTextView;//天气文本控件
    private TextView titleTextView;//标题文本控件
    private TextView contentTextView;//内容文本控件
    private TextView errorTextView;//错误文本控件

    private long diaryId;//日记主键

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_browse);
        initData();
        initView();
        setView();
    }

    /**
     * 关联菜单文件
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_diary_browse, menu);
        return true;
    }

    /**
     * 设置菜单点击事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_update: {
                //跳转到日记编辑页面
                Intent intent = DiaryEditActivity.buildIntent(DiaryBrowseActivity.this, diaryId);
                startActivityForResult(intent, REQUEST_EDIT);
            }
            break;

            case R.id.action_delete: {
                //弹窗提示是否删除日记
                new AlertDialog.Builder(DiaryBrowseActivity.this).setTitle("提示").setMessage("确定要删除日记吗?").setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Mapp.getInstance().getDiaryDataSource().deleteDiary(diaryId);
                        ToastUtils.showShort("删除成功");
                        setResult(RESULT_OK);
                        finish();
                    }

                }).setNegativeButton("取消", null).create().show();
            }
            break;

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 后一个页面回传的处理
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case REQUEST_EDIT: {
                if (resultCode == RESULT_OK) {
                    //将结果返回给前一个页面
                    setResult(RESULT_OK);
                    //刷新数据
                    Diary diary = Mapp.getInstance().getDiaryDataSource().selectOne(diaryId);
                    setDiaryView(diary);
                }
            }
            break;

        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        diaryId = getIntent().getLongExtra(EXTRA_DIARY_ID, 0);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        scrollView = findViewById(R.id.scrollView);
        dateTextView = findViewById(R.id.date_textView);
        weekTextView = findViewById(R.id.week_textView);
        weatherTextView = findViewById(R.id.weather_textView);
        titleTextView = findViewById(R.id.title_textView);
        contentTextView = findViewById(R.id.content_textView);
        errorTextView = findViewById(R.id.error_textView);
    }

    /**
     * 设置控件
     */
    private void setView() {
        //将标题栏关联到页面
        setSupportActionBar(toolbar);
        //显示返回键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //获取日记详情并设置控件
        Diary diary = Mapp.getInstance().getDiaryDataSource().selectOne(diaryId);
        setDiaryView(diary);
    }

    /**
     * 设置日记控件
     */
    private void setDiaryView(@Nullable Diary diary) {
        if (diary == null) {
            errorTextView.setText("未找到该日记");
            errorTextView.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);

        } else {
            errorTextView.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
            dateTextView.setText(new SimpleDateFormat("yyyy年MM月dd日").format(diary.getDate()));
            weekTextView.setText(new SimpleDateFormat("EEEE").format(diary.getDate()));
            weatherTextView.setText(diary.getWeather());
            titleTextView.setText(diary.getTitle());
            //处理内容格式，开头空两格，换行空两格
            String content = "\t\t\t\t" + diary.getContent().replace("\n", "\n\t\t\t\t");
            contentTextView.setText(content);
        }
    }

    /**
     * 构建意图
     */
    public static Intent buildIntent(@NonNull Context context, long diaryId) {
        Intent intent = new Intent(context, DiaryBrowseActivity.class);
        intent.putExtra(EXTRA_DIARY_ID, diaryId);
        return intent;
    }

}