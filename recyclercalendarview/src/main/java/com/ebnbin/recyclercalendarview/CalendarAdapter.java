package com.ebnbin.recyclercalendarview;

import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * 日历 {@link RecyclerView.Adapter}.
 */
final class CalendarAdapter extends BaseMultiItemQuickAdapter<CalendarEntity, BaseViewHolder> {
    public CalendarAdapter() {
        super(null);

        addItemType(CalendarEntity.MONTH, R.layout.item_month);
        addItemType(CalendarEntity.DAY, R.layout.item_day);
        addItemType(CalendarEntity.EMPTY_DAY, R.layout.item_empty_day);
    }

    @Override
    protected void convert(BaseViewHolder helper, CalendarEntity item) {
        switch (helper.getItemViewType()) {
            case CalendarEntity.MONTH: {
                CalendarEntity.Month month = (CalendarEntity.Month) item;

                break;
            }
            case CalendarEntity.DAY: {
                CalendarEntity.Day day = (CalendarEntity.Day) item;

                break;
            }
            case CalendarEntity.EMPTY_DAY: {
                CalendarEntity.EmptyDay emptyDay = (CalendarEntity.EmptyDay) item;

                break;
            }
        }
    }
}
