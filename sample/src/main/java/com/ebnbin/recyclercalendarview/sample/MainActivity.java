package com.ebnbin.recyclercalendarview.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ebnbin.recyclercalendarview.RecyclerCalendarView;

import java.util.Arrays;

public final class MainActivity extends AppCompatActivity {
    private RecyclerCalendarView mRecyclerCalendarView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mRecyclerCalendarView = (RecyclerCalendarView) findViewById(R.id.recycler_calendar_view);
        mRecyclerCalendarView.setRange(new int[]{2015, 6}, new int[]{2018, 6});
        mRecyclerCalendarView.selectDate(new int[]{2016, 6, 20}, true);
        mRecyclerCalendarView.listeners.add(date
                -> Toast.makeText(this, Arrays.toString(date), Toast.LENGTH_SHORT).show());
    }
}
