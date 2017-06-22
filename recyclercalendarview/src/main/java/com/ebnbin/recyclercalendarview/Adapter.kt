package com.ebnbin.recyclercalendarview

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import java.util.*

/**
 * 日历 [RecyclerView.Adapter].
 */
internal class Adapter : BaseMultiItemQuickAdapter<Entity, BaseViewHolder>(null) {
    init {
        addItemType(Entity.MONTH, R.layout.item_month)
        addItemType(Entity.DAY, R.layout.item_day)
        addItemType(Entity.EMPTY_DAY, R.layout.item_empty_day)
    }

    override fun convert(helper: BaseViewHolder, item: Entity) {
        when (helper.itemViewType) {
            Entity.MONTH -> {
                val monthEntity = item as Entity.Month

                helper.setText(R.id.month, monthEntity.monthString)
            }
            Entity.DAY -> {
                val dayEntity = item as Entity.Day

                helper.getView<View>(R.id.day).isEnabled = dayEntity.enabled

                helper.setText(R.id.day, dayEntity.dayString)

                helper.itemView.setOnClickListener {
                    if (dayEntity.enabled) {
                        for (listener in listeners) {
                            listener.onDayClick(helper.layoutPosition)
                        }
                    } else return@setOnClickListener
                }

                helper.getView<View>(R.id.day_wrapper).background = dayEntity.background

                helper.setTextColor(R.id.day, dayEntity.textColor)
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        val gridLayoutManager = recyclerView.layoutManager as GridLayoutManager
        val spanSizeLookup = gridLayoutManager.spanSizeLookup
        val spanCount = gridLayoutManager.spanCount

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val itemViewType = getItemViewType(position)
                if (itemViewType == Entity.MONTH) {
                    return spanCount
                }

                if (spanSizeLookup != null) {
                    return spanSizeLookup.getSpanSize(position)
                }

                return 1
            }
        }
    }

    /**
     * 返回月类型实体, 如果不存在或类型不一致则返回 `null`.
     */
    fun getMonthEntity(position: Int): Entity.Month? {
        val entity = getItem(position) as? Entity.Month ?: return null

        return entity
    }

    /**
     * 返回日类型实体, 如果不存在或类型不一致则返回 `null`.
     */
    fun getDayEntity(position: Int): Entity.Day? {
        val entity = getItem(position) as? Entity.Day ?: return null

        return entity
    }

    /**
     * 返回空白日类型实体, 如果不存在或类型不一致则返回 `null`.
     */
    fun getEmptyDayEntity(position: Int): Entity.EmptyDay? {
        val entity = getItem(position) as? Entity.EmptyDay ?: return null

        return entity
    }

    //*****************************************************************************************************************
    // 监听器.

    val listeners = ArrayList<Listener>()

    internal abstract class Listener {
        /**
         * 点击日回调.
         */
        open fun onDayClick(position: Int) {}
    }
}
