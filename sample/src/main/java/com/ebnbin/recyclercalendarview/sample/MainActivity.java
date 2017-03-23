package com.ebnbin.recyclercalendarview.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.ebnbin.ebapplication.base.EBActivity;
import com.ebnbin.recyclercalendarview.RecyclerCalendarView;

import java.util.Arrays;

public final class MainActivity extends EBActivity {
    private RecyclerCalendarView mRecyclerCalendarView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mRecyclerCalendarView = (RecyclerCalendarView) findViewById(R.id.recycler_calendar_view);
        mRecyclerCalendarView.setRange(new int[]{2014, 6}, new int[]{2017, 3});
        mRecyclerCalendarView.selectDate(new int[]{2016, 6, 20}, true);
        mRecyclerCalendarView.listeners.add(date
                -> Toast.makeText(getContext(), Arrays.toString(date), Toast.LENGTH_SHORT).show());
    }
}
