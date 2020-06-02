package com.junlong0716.erouter

import android.app.Application
import com.junlong0716.erouter.support.utils.Logger

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Logger.isDebug(true)
        }
    }
}