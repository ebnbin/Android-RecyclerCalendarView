package com.ebnbin.recyclercalendarview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import com.ebnbin.eb.base.EBRuntimeException;

/**
 * 资源帮助类.
 */
final class ResHelper {
    private static ResHelper sInstance;

    public static void init(@NonNull Context context) {
        if (sInstance != null) {
            throw new EBRuntimeException();
        }

        sInstance = new ResHelper(context);
    }

    @NonNull
    public static ResHelper getInstance() {
        if (sInstance == null) {
            throw new EBRuntimeException();
        }

        return sInstance;
    }

    private ResHelper(@NonNull Context context) {
        color_text_day = context.getColor(R.color.text_day);
        color_text_day_selected = context.getColor(R.color.text_day_selected);

        drawable_background_day_selected = context.getDrawable(R.drawable.background_day_selected);
    }

    //*****************************************************************************************************************

    public final int color_text_day;
    public final int color_text_day_selected;

    public final Drawable drawable_background_day_selected;
}
