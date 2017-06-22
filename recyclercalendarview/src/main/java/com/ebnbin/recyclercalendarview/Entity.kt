package com.ebnbin.recyclercalendarview

import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.ebnbin.eb.util.Timestamp
import java.util.*

/**
 * 日历实体.
 */
internal abstract class Entity : MultiItemEntity {
    /**
     * 月类型实体.
     *
     * @param timestamp 年月.
     */
    internal class Month internal constructor(val timestamp: Timestamp) : Entity() {
        /**
         * 月字符串.
         */
        val monthString: String by lazy {
            String.format(Res.string_format_month, timestamp.year, timestamp.month)
        }

        override fun getItemType() = MONTH
    }

    /**
     * 日类型实体.
     *
     * @param timestamp 日期.
     * @param enabled 是否有效.
     */
    internal class Day internal constructor(val timestamp: Timestamp, val enabled: Boolean) : Entity() {
        /**
         * 日字符串.
         */
        val dayString: String by lazy {
            String.format(Res.string_format_day, timestamp.day)
        }

        /**
         * 是否选中.
         */
        var selected: Boolean = false

        override fun getItemType() = DAY

        /**
         * 背景 [Drawable].
         */
        val background: Drawable?
            get() = if (enabled && selected) Res.drawable_background_day_selected else Res.drawable_background_day

        /**
         * 文本颜色.
         */
        val textColor: Int
            @ColorInt get() = if (!enabled) Res.color_text_day_disabled
            else if (selected) Res.color_text_day_selected
            else Res.color_text_day
    }

    /**
     * 空白日类型实体.
     */
    internal class EmptyDay internal constructor() : Entity() {
        override fun getItemType() = EMPTY_DAY
    }

    companion object {
        /**
         * 月类型.
         */
        val MONTH = 0
        /**
         * 日类型.
         */
        val DAY = 1
        /**
         * 空白日类型.
         */
        val EMPTY_DAY = 2

        /**
         * 创建新的日历数据.
         */
        fun newCalendarEntities(from: Timestamp, to: Timestamp): List<Entity> {
            val calendarEntities = ArrayList<Entity>()

            for (year in from.year..to.year) {
                for (month in 1..12) {
                    if (year == from.year && month < from.month || year == to.year && month > to.month) {
                        continue
                    }

                    val monthTimestamp = Timestamp.newInstance(year, month, 1, true)

                    val monthEntity = Month(monthTimestamp)
                    calendarEntities.add(monthEntity)

                    val week = monthTimestamp.week
                    for (emptyDay in 0..week - 1) {
                        val emptyDayEntity = EmptyDay()
                        calendarEntities.add(emptyDayEntity)
                    }

                    val daysOfMonth = monthTimestamp.daysOfMonth
                    for (day in 1..daysOfMonth) {
                        val dayEntity = Day(Timestamp.newInstance(year, month, day, true), Random().nextBoolean())
                        calendarEntities.add(dayEntity)
                    }
                }
            }

            return calendarEntities
        }

        /**
         * 创建新的日历数据.
         */
        fun newCalendarEntities(timestamps: List<Timestamp>): List<Entity> {
            val calendarEntities = ArrayList<Entity>()

            val from = timestamps.first()
            val to = timestamps.last()

            for (year in from.year..to.year) {
                for (month in 1..12) {
                    if (year == from.year && month < from.month || year == to.year && month > to.month) {
                        continue
                    }

                    val monthTimestamp = Timestamp.newInstance(year, month, 1, true)

                    val monthEntity = Month(monthTimestamp)
                    calendarEntities.add(monthEntity)

                    val week = monthTimestamp.week
                    for (emptyDay in 0..week - 1) {
                        val emptyDayEntity = EmptyDay()
                        calendarEntities.add(emptyDayEntity)
                    }

                    val daysOfMonth = monthTimestamp.daysOfMonth
                    for (day in 1..daysOfMonth) {
                        val dayTimestamp = Timestamp.newInstance(year, month, day, true)
                        val dayEntity = Day(dayTimestamp, timestamps.contains(dayTimestamp))
                        calendarEntities.add(dayEntity)
                    }
                }
            }

            return calendarEntities
        }
    }
}
