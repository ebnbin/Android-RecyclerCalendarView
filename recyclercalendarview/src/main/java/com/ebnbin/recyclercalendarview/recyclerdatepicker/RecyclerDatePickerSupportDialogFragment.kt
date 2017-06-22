package com.ebnbin.recyclercalendarview.recyclerdatepicker

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.ebnbin.eb.util.EBRuntimeException
import com.ebnbin.eb.util.Timestamp
import com.ebnbin.recyclercalendarview.R
import com.ebnbin.recyclercalendarview.RecyclerCalendarView

/**
 * Shows date picker [Dialog].
 */
class RecyclerDatePickerSupportDialogFragment : DialogFragment() {
    private val callback: Callback by lazy {
        if (parentFragment !is Callback) {
            throw EBRuntimeException()
        }

        parentFragment as Callback
    }

    interface Callback {
        fun onSelected(date: Timestamp)
    }

    private val timestamps: ArrayList<Timestamp> by lazy {
        arguments.getParcelableArrayList<Timestamp>(ARG_TIMESTAMPS)
    }

    private val selectedTimestamp: Timestamp by lazy {
        arguments.getParcelable<Timestamp>(ARG_SELECTED_TIMESTAMP)
    }

    private var rootView: ViewGroup? = null

    private val recyclerCalendarView: RecyclerCalendarView by lazy {
        val result = rootView!!.findViewById<RecyclerCalendarView>(R.id.recycler_calendar_view)
        result.setRange(timestamps)
        result.selectDate(selectedTimestamp, true)
        result.listeners.add(object : RecyclerCalendarView.Listener {
            override fun onSelected(date: Timestamp) {
                currentTimestamp = date

                yearTextView.text = getString(R.string.recycler_date_picker_format_year, currentTimestamp.year)
                monthDayTextView.text = getString(R.string.recycler_date_picker_format_month_day,
                        currentTimestamp.month, currentTimestamp.day)
            }
        })
        result
    }

    private val yearTextView: TextView by lazy {
        val result = rootView!!.findViewById<TextView>(R.id.year)
        result.text = getString(R.string.recycler_date_picker_format_year, currentTimestamp.year)
        result
    }

    private val monthDayTextView: TextView by lazy {
        val result = rootView!!.findViewById<TextView>(R.id.month_day)
        result.text = getString(R.string.recycler_date_picker_format_month_day,
                currentTimestamp.month, currentTimestamp.day)
        result
    }

    private val okButton: Button by lazy {
        val result = rootView!!.findViewById<Button>(R.id.ok)

        result.setOnClickListener {
            callback.onSelected(currentTimestamp)
            dismiss()
        }

        result
    }

    private val cancelButton: Button by lazy {
        val result = rootView!!.findViewById<Button>(R.id.cancel)

        result.setOnClickListener {
            dismiss()
        }

        result
    }

    private lateinit var currentTimestamp: Timestamp

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = Dialog(context)

        currentTimestamp = selectedTimestamp

        rootView = View.inflate(context, R.layout.recycler_date_picker, null) as ViewGroup

        recyclerCalendarView
        yearTextView
        monthDayTextView
        okButton
        cancelButton

        builder.setContentView(rootView!!)

        return builder
    }

    companion object {
        private val ARG_TIMESTAMPS = "timestamps"
        private val ARG_SELECTED_TIMESTAMP = "selected_timestamp"

        fun showDialog(fm: FragmentManager, timestamps: ArrayList<Timestamp>, selectedTimestamp: Timestamp) {
            val args = Bundle()
            args.putParcelableArrayList(ARG_TIMESTAMPS, timestamps)
            args.putParcelable(ARG_SELECTED_TIMESTAMP, selectedTimestamp)

            val fragment = RecyclerDatePickerSupportDialogFragment()
            fragment.arguments = args

            fragment.show(fm, null)
        }
    }
}
