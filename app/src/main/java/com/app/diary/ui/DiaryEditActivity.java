package com.app.diary.ui;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import com.app.diary.Mapp;
import com.app.diary.R;
import com.app.diary.bean.Diary;
import com.app.diary.utils.ToastUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * 编辑日志
 */
public class DiaryEditActivity extends BaseActivity {

    private static final String EXTRA_DIARY_ID = "diaryId";

    private static final String[] WEATHERS = new String[]{"晴天", "雨天", "雪天"};

    private Toolbar toolbar;//标题栏控件
    private TextView dateTextView;//日期文本控件
    private TextView weatherTextView;//天气文本控件
    private EditText titleEditText;//标题输入框控件
    private EditText contentEditText;//内容输入框控件
    private TextView emojiTextView; //表情包

    private DatePickerDialog datePickerDialog;//日期选择对话框
    private AlertDialog weatherPickerDialog;//天气选择对话框
    private AlertDialog emojiPickerDialog; // 表情包选择对话框

    private long diaryId;//日记主键

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_edit);
        initData();
        initView();
        setView();
    }

    /**
     * 关联菜单文件
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_diary_create, menu);
        return true;
    }

    /**
     * 设置菜单点击事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_save: {
                //保存日记
                saveDiary();
            }
            break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在页面销毁前，关闭日期选择对话框，防止对话框未关闭报错
        if (datePickerDialog != null && datePickerDialog.isShowing()) {
            datePickerDialog.dismiss();
        }
        if (weatherPickerDialog != null && weatherPickerDialog.isShowing()) {
            weatherPickerDialog.dismiss();
        }
        if(emojiPickerDialog != null && emojiPickerDialog.isShowing()){
            emojiPickerDialog.dismiss();
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
        dateTextView = findViewById(R.id.date_textView);
        weatherTextView = findViewById(R.id.weather_textView);
        titleEditText = findViewById(R.id.title_editText);
        contentEditText = findViewById(R.id.content_editText);
        emojiTextView = findViewById(R.id.emoji_textView);
    }

    /**
     * 设置控件
     */
    private void setView() {
        //将标题栏关联到页面
        setSupportActionBar(toolbar);
        //显示返回键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //设置日期文本的点击事件
        dateTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }

        });
        //设置天气文本的点击事件
        weatherTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showWeatherPickerDialog();
            }

        });
        //设置表情包文本的点击事件
        emojiTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 弹出选择表情包的对话框
                showEmojiPickerDialog();
            }
        });

        if (diaryId > 0) {//修改日记
            Diary diary = Mapp.getInstance().getDiaryDataSource().selectOne(diaryId);

            if (diary == null) {
                ToastUtils.showShort("未找到该日记");
                finish();
                return;
            }

            //设置该日记数据
            toolbar.setTitle("修改日记");
            dateTextView.setText(new SimpleDateFormat("yyyy年MM月dd日").format(diary.getDate()));
            weatherTextView.setText(diary.getWeather());
            titleEditText.setText(diary.getTitle());
            contentEditText.setText(diary.getContent());

        } else {//创建日记
            //设置默认值
            toolbar.setTitle("创建日记");
            dateTextView.setText(new SimpleDateFormat("yyyy年MM月dd日").format(new Date()));
            weatherTextView.setText(WEATHERS[0]);
        }
    }

    /**
     * 显示日期选择对话框
     */
    private void showDatePickerDialog() {
        //懒加载创建日期选择对话框，取当前日期为默认日期
        if (datePickerDialog == null) {
            Calendar calendar = Calendar.getInstance();
            try {
                String dateStr = dateTextView.getText().toString().trim();
                Date date = new SimpleDateFormat("yyyy年MM月dd日").parse(dateStr);
                calendar.setTime(date);
            } catch (ParseException e) {
            }
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            datePickerDialog = new DatePickerDialog(DiaryEditActivity.this, new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    //将选择的年月日组合成文字，显示在日期文本上
                    Date date = getDate(year, monthOfYear, dayOfMonth);
                    String dateStr = new SimpleDateFormat("yyyy年MM月dd日").format(date);
                    dateTextView.setText(dateStr);
                }

            }, year, month, day);
        }
        if (!datePickerDialog.isShowing()) {
            datePickerDialog.show();
        }
    }

    /**
     * 显示天气选择对话框
     */
    private void showWeatherPickerDialog() {
        if (weatherPickerDialog == null) {
            String weather = weatherTextView.getText().toString().trim();
            int position = Arrays.asList(WEATHERS).indexOf(weather);
            if (position < 0) {
                position = 0;
            }
            weatherPickerDialog = new AlertDialog.Builder(this).setTitle("选择天气").setSingleChoiceItems(WEATHERS, position, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    weatherTextView.setText(WEATHERS[which]);
                    dialog.dismiss();
                }

            }).create();
        }
        if (!weatherPickerDialog.isShowing()) {
            weatherPickerDialog.show();
        }
    }

    /**
     * 显示表情包对话框
     */
    private void showEmojiPickerDialog(){
        if(emojiPickerDialog == null){

        }
        if(!emojiPickerDialog.isShowing()){
            emojiPickerDialog.show();
        }
    }

    /**
     * 根据年月日获取日期函数
     */
    private Date getDate(int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        return calendar.getTime();
    }

    /**
     * 保存日记
     */
    private void saveDiary() {
        //检查输入情况
        String date = dateTextView.getText().toString().trim();
        if (date.isEmpty()) {
            ToastUtils.showShort("未选择日期");
            return;
        }
        String weather = weatherTextView.getText().toString().trim();
        if (weather.isEmpty()) {
            ToastUtils.showShort("未选择天气");
            return;
        }
        String title = titleEditText.getText().toString().trim();
        if (title.isEmpty()) {
            ToastUtils.showShort("未输入标题");
            return;
        }
        String content = contentEditText.getText().toString().trim();
        if (content.isEmpty()) {
            ToastUtils.showShort("未输入内容");
            return;
        }

        Diary diary = new Diary();
        try {
            diary.setDate(new SimpleDateFormat("yyyy年MM月dd日").parse(date));
        } catch (ParseException e) {
            ToastUtils.showShort("保存失败，时间转换错误");
            return;
        }
        diary.setWeather(weather);
        diary.setTitle(title);
        diary.setContent(content);

        if (diaryId > 0) {//修改日记
            Mapp.getInstance().getDiaryDataSource().updateDiary(diaryId, diary);
        } else {//创建日记
            Mapp.getInstance().getDiaryDataSource().insertDiary(diary);
        }
        ToastUtils.showShort("保存成功");
        setResult(RESULT_OK);
        finish();
    }

    /**
     * 构建意图
     */
    public static Intent buildIntent(@NonNull Context context, long diaryId) {
        Intent intent = new Intent(context, DiaryEditActivity.class);
        intent.putExtra(EXTRA_DIARY_ID, diaryId);
        return intent;
    }

}