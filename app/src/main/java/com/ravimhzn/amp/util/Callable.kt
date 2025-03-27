package com.ravimhzn.domainproperties.util

fun interface Callable<T> {
    fun call(t: T)
}