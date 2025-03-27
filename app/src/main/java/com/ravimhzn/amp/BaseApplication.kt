package com.ravimhzn.amp

import android.app.Application
import com.ravimhzn.amp.util.Views
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Views.init(this)
    }
}