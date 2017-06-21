package com.ebnbin.recyclercalendarview

import android.content.Context
import android.graphics.drawable.Drawable
import com.ebnbin.eb.util.EBUtil

/**
 * 资源帮助类.
 */
internal object Res {
    private lateinit var context: Context

    fun init(context: Context) {
        this.context = context
    }

    val color_text_day: Int by lazy {
        EBUtil.getColorAttr(context, android.R.attr.textColorPrimary)
    }
    val color_text_day_selected: Int by lazy {
        EBUtil.getColorAttr(context, android.R.attr.textColorPrimaryInverse)
    }

    val dimen_size_day: Int by lazy {
        context.resources.getDimensionPixelSize(R.dimen.size_day)
    }

    val drawable_background_day: Drawable? by lazy {
        null
    }
    val drawable_background_day_selected: Drawable by lazy {
        context.getDrawable(R.drawable.background_day_selected)
    }

    val string_format_month: String by lazy {
        context.getString(R.string.format_month)
    }
    val string_format_day: String by lazy {
        context.getString(R.string.format_day)
    }
}
