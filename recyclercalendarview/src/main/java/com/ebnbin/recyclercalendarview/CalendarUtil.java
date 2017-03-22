package com.ebnbin.recyclercalendarview;

import android.support.annotation.NonNull;

import java.util.Calendar;

/**
 * 日历工具类.
 */
final class CalendarUtil {
    /**
     * 星期.
     */
    public static int getWeek(@NonNull int[] date) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();

        calendar.set(date[0], date[1] - 1, date[2]);

        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 某月天数.
     */
    public static int getDaysOfMonth(@NonNull int[] yearMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();

        calendar.set(yearMonth[0], yearMonth[1] - 1, 1);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        return calendar.get(Calendar.DAY_OF_MONTH);
    }
}
