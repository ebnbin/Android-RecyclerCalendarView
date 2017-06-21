package com.ebnbin.recyclercalendarview.sample

import android.app.Application

import com.ebnbin.eb.util.EBUtil

class SampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        EBUtil.init(BuildConfig.APPLICATION_ID)
    }
}
