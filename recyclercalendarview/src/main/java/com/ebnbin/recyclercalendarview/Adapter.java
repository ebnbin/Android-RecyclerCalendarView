package com.ebnbin.recyclercalendarview;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 日历 {@link RecyclerView.Adapter}.
 */
final class Adapter extends BaseMultiItemQuickAdapter<Entity, BaseViewHolder> {
    Adapter() {
        super(null);

        addItemType(Entity.MONTH, R.layout.item_month);
        addItemType(Entity.DAY, R.layout.item_day);
        addItemType(Entity.EMPTY_DAY, R.layout.item_empty_day);
    }

    @Override
    protected void convert(BaseViewHolder helper, Entity item) {
        switch (helper.getItemViewType()) {
            case Entity.MONTH: {
                Entity.Month monthEntity = (Entity.Month) item;

                helper.setText(R.id.month, monthEntity.monthString);

                break;
            }
            case Entity.DAY: {
                Entity.Day dayEntity = (Entity.Day) item;

                final int position = helper.getLayoutPosition();
                helper.convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (Listener listener : listeners) {
                            listener.onDayClick(position);
                        }
                    }
                });

                helper.getView(R.id.day_wrapper).setBackground(dayEntity.getBackground());

                helper.setText(R.id.day, dayEntity.dayString);
                helper.setTextColor(R.id.day, dayEntity.getTextColor());

                break;
            }
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
        final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
        final int spanCount = gridLayoutManager.getSpanCount();

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int itemViewType = getItemViewType(position);
                if (itemViewType == Entity.MONTH) {
                    return spanCount;
                }

                if (spanSizeLookup != null) {
                    return spanSizeLookup.getSpanSize(position);
                }

                return 1;
            }
        });
    }

    /**
     * 返回月类型实体, 如果不存在或类型不一致则返回 {@code null}.
     */
    @Nullable
    public Entity.Month getMonthEntity(int position) {
        Entity entity = getItem(position);
        if (!(entity instanceof Entity.Month)) {
            return null;
        }

        return (Entity.Month) entity;
    }

    /**
     * 返回日类型实体, 如果不存在或类型不一致则返回 {@code null}.
     */
    @Nullable
    public Entity.Day getDayEntity(int position) {
        Entity entity = getItem(position);
        if (!(entity instanceof Entity.Day)) {
            return null;
        }

        return (Entity.Day) entity;
    }

    /**
     * 返回空白日类型实体, 如果不存在或类型不一致则返回 {@code null}.
     */
    @Nullable
    public Entity.EmptyDay getEmptyDayEntity(int position) {
        Entity entity = getItem(position);
        if (!(entity instanceof Entity.EmptyDay)) {
            return null;
        }

        return (Entity.EmptyDay) entity;
    }

    //*****************************************************************************************************************
    // 监听器.

    @NonNull
    public final List<Listener> listeners = new ArrayList<>();

    static abstract class Listener {
        /**
         * 点击日回调.
         */
        public void onDayClick(int position) {
        }
    }
}
