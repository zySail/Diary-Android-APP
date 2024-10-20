package com.app.diary.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.app.diary.R;
import com.app.diary.utils.AppUtils;

/**
 * 主页面
 */
public class MainActivity extends BaseActivity {

    private Toolbar toolbar;//标题栏控件
    private Button browseButton;//查看日记按钮控件
    private Button createButton;//创建日记按钮控件
    private TextView versionTextView;//当前版本文本控件

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        browseButton = findViewById(R.id.browse_button);
        createButton = findViewById(R.id.create_button);
        versionTextView = findViewById(R.id.version_textView);
    }

    /**
     * 设置控件
     */
    private void setView() {
        //将标题栏关联到页面
        setSupportActionBar(toolbar);

        //设置查看日记按钮的点击事件
        browseButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = DiaryListActivity.buildIntent(MainActivity.this);
                startActivity(intent);
            }

        });

        //设置创建日记按钮的点击事件
        createButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = DiaryEditActivity.buildIntent(MainActivity.this, 0);
                startActivity(intent);
            }

        });

        //将当前版本名称显示在文本上
        versionTextView.setText("当前版本:v" + AppUtils.getVersionName());
    }

}