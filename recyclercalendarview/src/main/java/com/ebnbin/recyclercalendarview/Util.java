package com.ebnbin.recyclercalendarview;

import android.support.annotation.NonNull;

import java.util.Calendar;

/**
 * 工具类.
 */
final class Util {
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

    //*****************************************************************************************************************
    // Valid.

    /**
     * 最小年.
     */
    private static final int MIN_YEAR = 1970;
    /**
     * 最大年.
     */
    private static final int MAX_YEAR = 2037;

    /**
     * 日期是否有效.
     */
    public static boolean isDateValid(@NonNull int[] date) {
        return date.length == 3
                && date[0] >= MIN_YEAR
                && date[0] <= MAX_YEAR
                && date[1] >= 1
                && date[1] <= 12
                && date[2] >= 1
                && date[2] <= getDaysOfMonth(new int[]{date[0], date[1]});
    }

    /**
     * 范围是否有效.
     */
    public static boolean isRangeValid(@NonNull int[] yearMonthFrom, @NonNull int[] yearMonthTo) {
        return yearMonthFrom.length == 2
                && yearMonthTo.length == 2
                && yearMonthFrom[0] >= MIN_YEAR
                && yearMonthFrom[0] <= MAX_YEAR
                && yearMonthFrom[1] >= 1
                && yearMonthFrom[1] <= 12
                && (yearMonthTo[0] > yearMonthFrom[0]
                && yearMonthTo[1] >= 1
                || yearMonthTo[0] == yearMonthFrom[0]
                && yearMonthTo[1] >= yearMonthFrom[1])
                && yearMonthTo[0] <= MAX_YEAR
                && yearMonthTo[1] <= 12;
    }
}
