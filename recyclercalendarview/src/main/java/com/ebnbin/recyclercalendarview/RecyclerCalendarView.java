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
        inflate(getContext(), R.layout.view_recycler_calendar, this);

        mCalendarRecyclerView = (RecyclerView) findViewById(R.id.calendar);

        mLayoutManager = new GridLayoutManager(getContext(), 7);
        mCalendarRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new CalendarAdapter();
        List<CalendarEntity> calendarEntities = CalendarEntity.newCalendarEntities(new int[]{2014, 6},
                new int[]{2017, 3});
        mAdapter.setNewData(calendarEntities);
        mCalendarRecyclerView.setAdapter(mAdapter);
    }
}
