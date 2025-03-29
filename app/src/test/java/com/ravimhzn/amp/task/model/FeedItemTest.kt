package com.ravimhzn.amp.task.model

import org.junit.Assert.assertEquals
import org.junit.Test

class FeedItemTest {

    @Test
    fun testGetFormattedTransactionDate_validDate() {
        val transactionTime = "2024-03-29T15:45:30.123Z"
        val feedItem = FeedItem(
            amount = Amount("GBP", 2955),
            transactionTime = transactionTime
        )
        val formattedDate = feedItem.getFormattedTransactionDate()
        assertEquals("Sat 30 Mar", formattedDate)
    }
}