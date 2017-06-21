package com.ebnbin.recyclercalendarview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import com.ebnbin.eb.util.EBRuntimeException;

/**
 * 资源帮助类.
 */
final class Res {
    private static Res sInstance;

    public static void init(@NonNull Context context) {
        if (sInstance != null) {
            return;
        }

        sInstance = new Res(context);
    }

    @NonNull
    public static Res getInstance() {
        if (sInstance == null) {
            throw new EBRuntimeException();
        }

        return sInstance;
    }

    private Res(@NonNull Context context) {
        color_text_day = context.getColor(R.color.text_day);
        color_text_day_selected = context.getColor(R.color.text_day_selected);

        dimen_size_day = context.getResources().getDimensionPixelSize(R.dimen.size_day);

        drawable_background_day = null;
        drawable_background_day_selected = context.getDrawable(R.drawable.background_day_selected);

        string_format_month = context.getString(R.string.format_month);
        string_format_day = context.getString(R.string.format_day);
    }

    //*****************************************************************************************************************

    public final int color_text_day;
    public final int color_text_day_selected;

    public final int dimen_size_day;

    public final Drawable drawable_background_day;
    public final Drawable drawable_background_day_selected;

    public final String string_format_month;
    public final String string_format_day;
}
