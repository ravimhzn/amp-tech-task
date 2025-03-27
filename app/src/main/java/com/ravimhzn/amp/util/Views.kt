package com.ravimhzn.amp.util

import android.content.Context

class Views private constructor() {
    init {
        throw IllegalStateException("This is a utility class and should not be instantiated")
    }

    companion object {
        private lateinit var appContext: Context

        fun init(context: Context) {
            appContext = context.applicationContext
        }

        fun context() = appContext
    }
}