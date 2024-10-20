package com.app.diary.utils;

import android.widget.Toast;

import com.app.diary.Mapp;

/**
 * 吐司工具
 */
public class ToastUtils {

    /**
     * 短时间吐司
     */
    public static void showShort(CharSequence text) {
        Toast.makeText(Mapp.getInstance(), text, Toast.LENGTH_SHORT).show();
    }

}