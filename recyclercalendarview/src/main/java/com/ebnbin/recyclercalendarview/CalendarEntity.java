package com.ebnbin.recyclercalendarview;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 日历实体.
 */
abstract class CalendarEntity implements MultiItemEntity {
    /**
     * 月类型.
     */
    public static final int MONTH = 0;
    /**
     * 日类型.
     */
    public static final int DAY = 1;
    /**
     * 空白日类型.
     */
    public static final int EMPTY_DAY = 2;

    /**
     * 创建新的日历数据.
     *
     * @param yearMonthFrom
     *         开始年月.
     * @param yearMonthTo
     *         结束年月.
     */
    @NonNull
    public static List<CalendarEntity> newCalendarEntities(@NonNull int[] yearMonthFrom, @NonNull int[] yearMonthTo) {
        List<CalendarEntity> calendarEntities = new ArrayList<>();

        for (int year = yearMonthFrom[0]; year <= yearMonthTo[0]; year++) {
            for (int month = 1; month <= 12; month++) {
                if (year == yearMonthFrom[0] && month < yearMonthFrom[1]
                        || year == yearMonthTo[0] && month > yearMonthTo[1]) {
                    continue;
                }

                CalendarEntity monthEntity = new Month(new int[]{year, month});
                calendarEntities.add(monthEntity);

                int week = CalendarUtil.getWeek(new int[]{year, month, 1});
                for (int emptyDay = 0; emptyDay < week; emptyDay++) {
                    CalendarEntity emptyDayEntity = new EmptyDay();
                    calendarEntities.add(emptyDayEntity);
                }

                int daysOfMonth = CalendarUtil.getDaysOfMonth(new int[]{year, month});
                for (int day = 1; day <= daysOfMonth; day++) {
                    CalendarEntity dayEntity = new Day(new int[]{year, month, day});
                    calendarEntities.add(dayEntity);
                }
            }
        }

        return calendarEntities;
    }

    /**
     * 月类型实体.
     */
    static final class Month extends CalendarEntity {
        /**
         * 年月.
         */
        @NonNull
        public final int[] yearMonth;

        /**
         * 月字符串.
         */
        @NonNull
        public final String monthString;

        private Month(@NonNull int[] yearMonth) {
            this.yearMonth = yearMonth;

            monthString = "" + yearMonth[0] + "年" + yearMonth[1] + "月";
        }

        @Override
        public int getItemType() {
            return MONTH;
        }
    }

    /**
     * 日类型实体.
     */
    static final class Day extends CalendarEntity {
        /**
         * 日期.
         */
        @NonNull
        public final int[] date;

        /**
         * 日字符串.
         */
        @NonNull
        public final String dayString;

        /**
         * 是否选中.
         */
        public boolean selected;

        private Day(@NonNull int[] date) {
            this.date = date;

            dayString = "" + date[2];
        }

        @Override
        public int getItemType() {
            return DAY;
        }

        @Nullable
        public Drawable getBackground() {
            return selected ? ResHelper.getInstance().drawable_background_day_selected : null;
        }

        @ColorRes
        public int getTextColor() {
            return selected ? ResHelper.getInstance().color_text_day_selected : ResHelper.getInstance().color_text_day;
        }
    }

    /**
     * 空白日类型实体.
     */
    static final class EmptyDay extends CalendarEntity {
        private EmptyDay() {
        }

        @Override
        public int getItemType() {
            return EMPTY_DAY;
        }
    }
}
