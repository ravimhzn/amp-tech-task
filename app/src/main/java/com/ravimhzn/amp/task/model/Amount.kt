package com.ravimhzn.amp.task.model

import java.io.Serializable
import java.math.BigDecimal

data class Amount(
    val currency: String,
    val minorUnits: Int
) : Serializable {

    fun convertMinorUnitsToPounds(): BigDecimal {
        return BigDecimal(minorUnits).divide(BigDecimal(100))
    }
}