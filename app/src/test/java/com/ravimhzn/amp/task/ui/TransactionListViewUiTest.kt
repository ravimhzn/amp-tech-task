package com.ravimhzn.amp.task.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import com.ravimhzn.amp.task.BaseComposeTest
import com.ravimhzn.amp.task.model.Amount
import com.ravimhzn.amp.task.model.FeedItem
import org.junit.Test

class TransactionListViewUiTest: BaseComposeTest() {
    
    @Test
    fun transactionListView_displaysCorrectTransactionDetails() {
        val startTime = "2025-03-26T00:05:07.118Z"
        val feedItem = FeedItem(
            Amount("", 2955),
            source = "Some Source",
            reference = "Some Reference",
            transactionTime = startTime
        )

        composeTestRule.setContent {
            TransactionListView(feed = feedItem)
        }

        composeTestRule.onNodeWithText(feedItem.getFormattedTransactionDate())
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Reference: ${feedItem.reference}")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText(feedItem.source)
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("£${feedItem.amount.convertMinorUnitsToPounds()}")
            .assertIsDisplayed()
    }

}