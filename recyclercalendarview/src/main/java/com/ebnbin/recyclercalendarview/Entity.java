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
abstract class Entity implements MultiItemEntity {
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
     */
    @NonNull
    public static List<Entity> newCalendarEntities(@NonNull int[] yearMonthFrom, @NonNull int[] yearMonthTo) {
        List<Entity> calendarEntities = new ArrayList<>();

        for (int year = yearMonthFrom[0]; year <= yearMonthTo[0]; year++) {
            for (int month = 1; month <= 12; month++) {
                if (year == yearMonthFrom[0] && month < yearMonthFrom[1]
                        || year == yearMonthTo[0] && month > yearMonthTo[1]) {
                    continue;
                }

                Entity monthEntity = new Month(new int[]{year, month});
                calendarEntities.add(monthEntity);

                int week = Util.getWeek(new int[]{year, month, 1});
                for (int emptyDay = 0; emptyDay < week; emptyDay++) {
                    Entity emptyDayEntity = new EmptyDay();
                    calendarEntities.add(emptyDayEntity);
                }

                int daysOfMonth = Util.getDaysOfMonth(new int[]{year, month});
                for (int day = 1; day <= daysOfMonth; day++) {
                    Entity dayEntity = new Day(new int[]{year, month, day});
                    calendarEntities.add(dayEntity);
                }
            }
        }

        return calendarEntities;
    }

    /**
     * 月类型实体.
     */
    static final class Month extends Entity {
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

            monthString = String.format(Res.getInstance().string_format_month, yearMonth[0], yearMonth[1]);
        }

        @Override
        public int getItemType() {
            return MONTH;
        }
    }

    /**
     * 日类型实体.
     */
    static final class Day extends Entity {
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

            dayString = String.format(Res.getInstance().string_format_day, date[2]);
        }

        @Override
        public int getItemType() {
            return DAY;
        }

        /**
         * 背景 {@link Drawable}.
         */
        @Nullable
        public Drawable getBackground() {
            return selected ? Res.getInstance().drawable_background_day_selected
                    : Res.getInstance().drawable_background_day;
        }

        /**
         * 文本颜色.
         */
        @ColorRes
        public int getTextColor() {
            return selected ? Res.getInstance().color_text_day_selected : Res.getInstance().color_text_day;
        }
    }

    /**
     * 空白日类型实体.
     */
    static final class EmptyDay extends Entity {
        private EmptyDay() {
        }

        @Override
        public int getItemType() {
            return EMPTY_DAY;
        }
    }
}
