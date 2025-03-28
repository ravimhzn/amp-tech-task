package com.ravimhzn.amp.task.model

import org.junit.Assert.assertEquals
import org.junit.Test

class AmountTest {

    @Test
    fun convertMinorUnitsToPoundsTest() {
        val amount = Amount("GBP", 2955)
        assertEquals(amount.convertMinorUnitsToPounds(), 29.55.toBigDecimal())
    }
}