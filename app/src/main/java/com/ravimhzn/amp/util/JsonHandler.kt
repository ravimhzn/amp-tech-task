package com.ravimhzn.amp.util

import android.util.Log
import com.google.gson.Gson

object JsonHandler {

    fun <T : Any> fromJson(json: String, type: Class<T>): T? =
        try {
            Gson().getAdapter(type).fromJson(json)
        } catch (e: Exception) {
            Log.e(LOG_ERROR, "Unable to parse the json into ${type}.\nJson:\t$json")
            null
        }

    fun <T : Any> toJson(obj: T): String? =
        try {
            Gson().toJson(obj)
        } catch (e: Exception) {
            null
        }
}