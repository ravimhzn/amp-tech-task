package com.ravimhzn.amp.util

import android.util.Log

object Logger {

    fun d(tag: String = "", message: String) {
        try {
            Log.d(tag, message)
        } catch (e: Exception) {
            print(ERROR_MESSAGE + e.message)
        }
    }

    fun e(tag: String, message: String?) {
        try {
            Log.e(tag, message ?: "")
        } catch (e: Exception) {
            print(ERROR_MESSAGE + e.message)
        }
    }

    private const val ERROR_MESSAGE = "ERROR - "
}