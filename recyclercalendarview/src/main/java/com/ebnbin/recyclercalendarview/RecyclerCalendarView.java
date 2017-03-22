package com.ebnbin.recyclercalendarview;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 使用 {@link RecyclerView} 展示日历.
 */
public class RecyclerCalendarView extends FrameLayout {
    public RecyclerCalendarView(@NonNull Context context) {
        super(context);

        init();
    }

    public RecyclerCalendarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public RecyclerCalendarView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    public RecyclerCalendarView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr,
            @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init();
    }

    private RecyclerView mCalendarRecyclerView;

    private GridLayoutManager mLayoutManager;
    private CalendarAdapter mAdapter;

    private void init() {
        ResHelper.init(getContext());

        inflate(getContext(), R.layout.view_recycler_calendar, this);

        mCalendarRecyclerView = (RecyclerView) findViewById(R.id.calendar);

        mLayoutManager = new GridLayoutManager(getContext(), 7);
        mCalendarRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new CalendarAdapter();
        mAdapter.listeners.add(new CalendarAdapter.Listener() {
            @Override
            public void onDayClick(int position) {
                super.onDayClick(position);

                selectPosition(position, true);
            }
        });
        mCalendarRecyclerView.setAdapter(mAdapter);
    }

    //*****************************************************************************************************************
    // Range.

    /**
     * 设置年月范围.
     *
     * @param yearMonthFrom
     *         开始年月.
     * @param yearMonthTo
     *         结束年月.
     */
    public void setRange(@NonNull int[] yearMonthFrom, @NonNull int[] yearMonthTo) {
        List<CalendarEntity> calendarEntities = CalendarEntity.newCalendarEntities(yearMonthFrom, yearMonthTo);
        mAdapter.setNewData(calendarEntities);
    }

    //*****************************************************************************************************************
    // Selected.

    /**
     * 当前选中的位置.
     */
    private int mSelectedPosition = -1;

    /**
     * 选中某日期.
     */
    public void selectDate(@NonNull int[] date) {
        selectPosition(getPosition(date), false);
    }

    /**
     * 返回指定日期的位置, 如果没找到则返回 -1.
     */
    private int getPosition(@NonNull int[] date) {
        for (int i = 0; i < mAdapter.getItemCount(); i++) {
            CalendarEntity calendarEntity = mAdapter.getItem(i);
            if (calendarEntity instanceof CalendarEntity.Day
                    && Arrays.equals(((CalendarEntity.Day) calendarEntity).date, date)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 选中某位置.
     */
    private void selectPosition(int position, boolean callback) {
        if (mSelectedPosition == position) {
            return;
        }

        if (mSelectedPosition != -1) {
            setPositionSelected(mSelectedPosition, false);
            mSelectedPosition = -1;
        }

        if (position == -1) {
            return;
        }

        setPositionSelected(position, true);
        mSelectedPosition = position;

        if (callback) {
            onSelected(position);
        }
    }

    /**
     * 设置位置的选中状态.
     */
    private void setPositionSelected(int position, boolean selected) {
        CalendarEntity calendarEntity = mAdapter.getItem(position);
        if (!(calendarEntity instanceof CalendarEntity.Day)) {
            return;
        }

        CalendarEntity.Day dayEntity = (CalendarEntity.Day) calendarEntity;
        if (dayEntity.selected == selected) {
            return;
        }

        dayEntity.selected = selected;
        mAdapter.notifyItemChanged(position);
    }

    //*****************************************************************************************************************
    // Callbacks and listeners.

    public final List<Listener> listeners = new ArrayList<>();

    /**
     * 监听.
     */
    public interface Listener {
        void onSelected(int[] date);
    }

    /**
     * 回调.
     */
    private void onSelected(int position) {
        CalendarEntity calendarEntity = mAdapter.getItem(position);
        if (!(calendarEntity instanceof CalendarEntity.Day)) {
            return;
        }

        CalendarEntity.Day dayEntity = (CalendarEntity.Day) calendarEntity;
        int[] date = dayEntity.date;

        for (Listener listener : listeners) {
            listener.onSelected(date);
        }
    }
}
