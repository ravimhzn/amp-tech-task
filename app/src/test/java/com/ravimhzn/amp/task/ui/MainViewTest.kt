package com.ravimhzn.amp.task.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import com.ravimhzn.amp.framework.NetworkError
import com.ravimhzn.amp.framework.State
import com.ravimhzn.amp.task.BaseComposeTest
import com.ravimhzn.amp.task.model.Account
import com.ravimhzn.amp.task.model.AccountsResponse
import com.ravimhzn.amp.task.model.Amount
import com.ravimhzn.amp.task.model.FeedItem
import com.ravimhzn.amp.task.model.TransactionResponse
import org.junit.Test
import java.util.UUID

class MainViewTest : BaseComposeTest() {

    @Test
    fun testLoadingState() {
        composeTestRule.setContent {
            MainView(
                navHostController = rememberNavController(),
                state = State.Loading,
                accounts = null,
                transactions = null,
                errorMessage = ""
            )
        }

        composeTestRule.onNodeWithTag("progressBar").assertIsDisplayed()
    }

    @Test
    fun testErrorState() {
        composeTestRule.setContent {
            MainView(
                navHostController = rememberNavController(),
                state = State.Error(NetworkError("", "")),
                accounts = null,
                transactions = null,
                errorMessage = "Network Error"
            )
        }

        composeTestRule.onNodeWithText("Network Error").assertIsDisplayed()
    }

    @Test
    fun testTransactionListDisplayed() {
        val startTime = "2025-03-26T00:05:07.118Z"
        val endTime = "2025-03-28T00:05:07.118Z"
        val randomId = UUID.randomUUID().toString()
        val mockTransaction =
            TransactionResponse(
                listOf(
                    FeedItem(
                        Amount("", 2955),
                        source = "Some Source 1",
                        reference = "Some Reference 1",
                        transactionTime = startTime
                    ),
                    FeedItem(
                        Amount("", 726),
                        source = "Some Source 2",
                        reference = "Some Reference 2",
                        transactionTime = endTime
                    )
                )
            )

        val testAccount = Account(randomId, "Savings", "", "GBP", "", "Savings")
        val mockAccountResponse = AccountsResponse(listOf(testAccount))

        composeTestRule.setContent {
            MainView(
                navHostController = rememberNavController(),
                state = State.Empty,
                accounts = mockAccountResponse,
                transactions = mockTransaction,
                errorMessage = ""
            )
        }

        composeTestRule.onNodeWithText("A/C Type: Savings").assertIsDisplayed()
        composeTestRule.onNodeWithText("Reference: Some Reference 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Some Source 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Reference: Some Reference 2").assertIsDisplayed()
        composeTestRule.onNodeWithText("Some Source 2").assertIsDisplayed()
        composeTestRule.onNodeWithText("£29.55").assertIsDisplayed()
    }
}