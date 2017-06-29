package com.ebnbin.recyclercalendarview

import android.content.Context
import android.support.annotation.AttrRes
import android.support.annotation.StyleRes
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.ebnbin.eb.util.Timestamp
import java.util.*

/**
 * 使用 [RecyclerView] 展示日历.
 */
class RecyclerCalendarView : FrameLayout {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int, @StyleRes defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    private val calendarRecyclerView: RecyclerView by lazy {
        val result = findViewById(R.id.calendar) as RecyclerView
        result.layoutManager = layoutManager
        result.adapter = adapter
        result
    }

    private val layoutManager: GridLayoutManager by lazy {
        GridLayoutManager(context, 7)
    }
    private val adapter: Adapter by lazy {
        val result = Adapter()
        result.listeners.add(object : Adapter.Listener() {
            override fun onDayClick(position: Int) {
                super.onDayClick(position)

                selectPosition(position, true)
            }
        })
        result
    }

    private fun init() {
        Res.init(context)

        val rootView = LayoutInflater.from(context).inflate(R.layout.view_recycler_calendar, this, false)
        val width = resources.getDimensionPixelOffset(R.dimen.width_view)
        val height = resources.getDimensionPixelOffset(R.dimen.height_view)
        val params = FrameLayout.LayoutParams(width, height)
        params.gravity = Gravity.CENTER
        addView(rootView, params)

        calendarRecyclerView
    }

    //*****************************************************************************************************************

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        scrollToPosition(scrollToPosition)
    }

    //*****************************************************************************************************************
    // 范围.

    /**
     * 设置年月范围.
     *
     * @param from 开始年月.
     * @param to 结束年月.
     */
    fun setRange(from: Timestamp, to: Timestamp) {
        if (from.calendar.after(to.calendar)) {
            return
        }

        val calendarEntities = Entity.newCalendarEntities(from, to)
        adapter.setNewData(calendarEntities)
    }

    /**
     * 设置年月范围.
     */
    fun setRange(timestamps: List<Timestamp>) {
        val calendarEntities = Entity.newCalendarEntities(timestamps)
        adapter.setNewData(calendarEntities)
    }

    //*****************************************************************************************************************
    // 选中.

    /**
     * 当前选中的位置.
     */
    private var selectedPosition = -1

    /**
     * 选中某日期.
     *
     * @param scrollToSelected 是否滚动到选中的位置.
     */
    @JvmOverloads fun selectDate(date: Timestamp, scrollToSelected: Boolean = false) {
        selectPosition(getPosition(date), false)

        if (scrollToSelected) {
            scrollToSelected()
        }
    }

    /**
     * 返回指定日期的位置, 如果没找到则返回 -1.
     */
    private fun getPosition(date: Timestamp): Int {
        for (i in 0..adapter.itemCount - 1) {
            val dayEntity = adapter.getDayEntity(i)
            if (dayEntity != null && dayEntity.timestamp == date) {
                return i
            }
        }

        return -1
    }

    /**
     * 选中某位置.
     */
    private fun selectPosition(position: Int, callback: Boolean) {
        if (selectedPosition == position) {
            return
        }

        if (selectedPosition != -1) {
            setPositionSelected(selectedPosition, false)
            selectedPosition = -1
        }

        if (position == -1) {
            return
        }

        setPositionSelected(position, true)
        selectedPosition = position

        if (callback) {
            onSelected(position)
        }
    }

    /**
     * 设置位置的选中状态.
     */
    private fun setPositionSelected(position: Int, selected: Boolean) {
        val dayEntity = adapter.getDayEntity(position) ?: return

        if (dayEntity.selected == selected) {
            return
        }

        dayEntity.selected = selected
        adapter.notifyItemChanged(position)
    }

    //*****************************************************************************************************************
    // 滚动.

    /**
     * 要滚动到的位置, 如果为 -1 则不滚动.
     */
    private var scrollToPosition = -1

    /**
     * 滚动到选中到位置, 如果没有选中的位置则不滚动.
     */
    private fun scrollToSelected() {
        scrollToPosition(selectedPosition)
    }

    /**
     * 滚动到指定的位置, 如果为 -1 则不滚动.
     */
    private fun scrollToPosition(position: Int) {
        scrollToPosition = position

        if (scrollToPosition == -1) {
            return
        }

        if (calendarRecyclerView.measuredHeight <= 0) {
            return
        }

        val offset = (calendarRecyclerView.measuredHeight - Res.dimen_size_day) / 2
        layoutManager.scrollToPositionWithOffset(scrollToPosition, offset)
        scrollToPosition = -1
    }

    //*****************************************************************************************************************
    // 监听器.

    val listeners = ArrayList<Listener>()

    /**
     * 监听器.
     */
    interface Listener {
        /**
         * 选中某日期回调.
         *
         * @param date 选中的日期.
         */
        fun onSelected(date: Timestamp)
    }

    /**
     * 选中某日期回调.
     */
    private fun onSelected(position: Int) {
        val dayEntity = adapter.getDayEntity(position) ?: return

        for (listener in listeners) {
            listener.onSelected(dayEntity.timestamp)
        }
    }
}
