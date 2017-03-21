package com.ebnbin.recyclercalendarview;

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
     */
    public static List<CalendarEntity> newCalendarEntities() {
        List<CalendarEntity> calendarEntities = new ArrayList<>();

        return calendarEntities;
    }

    /**
     * 月类型实体.
     */
    static final class Month extends CalendarEntity {
        private Month() {
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
        private Day() {
        }

        @Override
        public int getItemType() {
            return DAY;
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
