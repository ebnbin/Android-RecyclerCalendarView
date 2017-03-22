package com.ebnbin.recyclercalendarview.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ebnbin.ebapplication.base.EBActivity;
import com.ebnbin.recyclercalendarview.RecyclerCalendarView;

public final class MainActivity extends EBActivity {
    private RecyclerCalendarView mRecyclerCalendarView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mRecyclerCalendarView = (RecyclerCalendarView) findViewById(R.id.recycler_calendar_view);
    }
}
