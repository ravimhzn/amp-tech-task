package com.ravimhzn.amp.util

fun interface Callable<T> {
    fun call(t: T)
}