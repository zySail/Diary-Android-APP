package com.app.diary.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.app.diary.Mapp;

/**
 * app工具
 */
public class AppUtils {

    /**
     * 获取版本名称
     */
    public static String getVersionName() {
        try {
            Context context = Mapp.getInstance();
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi == null ? "" : pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
    }

}
