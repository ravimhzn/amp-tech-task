package com.ravimhzn.amp.task.model

import java.io.Serializable

data class Amount(
    val currency: String,
    val minorUnits: Int
) : Serializable