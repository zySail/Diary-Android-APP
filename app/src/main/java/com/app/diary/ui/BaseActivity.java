package com.app.diary.ui;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 页面的基础类
 */
public class BaseActivity extends AppCompatActivity {

    /**
     * 设置菜单点击
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //设置标题栏的返回键点击事件
        if (item.getItemId() == android.R.id.home) {
            //页面返回
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
