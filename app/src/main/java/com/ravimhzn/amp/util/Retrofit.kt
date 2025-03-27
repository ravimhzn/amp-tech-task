package com.ravimhzn.amp.util

import okhttp3.Request
import retrofit2.Response

fun Request.Builder.addHeaders(map: Map<String, String>): Request.Builder =
    apply {
        map.forEach {
            addHeader(it.key, it.value)
        }
    }

fun Response<*>.errorMessage() =
    "API Error:: Error code: ${this.code()}. Error message: ${this.message()}"

fun Response<*>.getErrorCode() = this.code()

fun Response<*>.getErrorBody() = this.errorBody()

