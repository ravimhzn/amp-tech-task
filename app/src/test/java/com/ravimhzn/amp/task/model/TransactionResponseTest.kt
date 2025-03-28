package com.ravimhzn.amp.task.model

import org.junit.Assert.assertEquals
import org.junit.Test

class TransactionResponseTest {

    @Test
    fun calculateRoundUpForDateRangeTest() {
        val startTime = "2025-03-26T00:05:07.118Z"
        val endTime = "2025-03-28T00:05:07.118Z"
        val transactionResponse =
            TransactionResponse(
                listOf(
                    FeedItem(
                        Amount("", 2955),
                        source = "Some Source",
                        reference = "Some Reference",
                        transactionTime = startTime
                    ),
                    FeedItem(
                        Amount("", 726),
                        source = "Some Source",
                        reference = "Some Reference",
                        transactionTime = endTime
                    )
                )
            )

        val roundUpData = transactionResponse.calculateRoundUpForDateRange(startTime, endTime)
        assertEquals(roundUpData.first, 2)
        assertEquals(roundUpData.second, 1.19.toBigDecimal())
    }
}