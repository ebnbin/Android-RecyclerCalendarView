package com.ebnbin.recyclercalendarview.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

import com.ebnbin.eb.util.Timestamp
import com.ebnbin.recyclercalendarview.RecyclerCalendarView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val recyclerCalendarView = findViewById<RecyclerCalendarView>(R.id.recycler_calendar_view)
        recyclerCalendarView.setRange(arrayListOf(
                Timestamp.newInstance(2017, 1, 2, true),
                Timestamp.newInstance(2017, 1, 3, true),
                Timestamp.newInstance(2017, 1, 4, true),
                Timestamp.newInstance(2017, 1, 5, true),
                Timestamp.newInstance(2017, 1, 7, true)))
        recyclerCalendarView.selectDate(Timestamp.newInstance(2017, 6, 20, true), true)
        recyclerCalendarView.listeners.add(object : RecyclerCalendarView.Listener {
            override fun onSelected(date: Timestamp) {
                Toast.makeText(this@MainActivity, date.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }
}
