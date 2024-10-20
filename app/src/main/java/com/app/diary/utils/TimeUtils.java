package com.app.diary.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间工具
 */
public class TimeUtils {

    /**
     * 获取简要时间
     */
    public static String getSimpleTime(long millis) {
        long now = System.currentTimeMillis();
        long span = now - millis;
        if (span < 0) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(millis));
        } else if (span < 1000) {
            return "刚刚";
        } else if (span < 60000) {
            return String.format(Locale.getDefault(), "%d秒前", span / 1000);
        } else if (span < 3600000) {
            return String.format(Locale.getDefault(), "%d分钟前", span / 60000);
        }
        long wee = getWeeOfToday();
        if (millis >= wee) {
            return "今天" + new SimpleDateFormat("HH:mm:ss").format(new Date(millis));
        } else if (millis >= wee - 86400000) {
            return "昨天" + new SimpleDateFormat("HH:mm:ss").format(new Date(millis));
        } else {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(millis));
        }
    }

    /**
     * 获取时分秒归0的当前时间
     */
    private static long getWeeOfToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

}
