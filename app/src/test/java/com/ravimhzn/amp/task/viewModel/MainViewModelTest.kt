package com.ravimhzn.amp.task.viewModel

import com.ravimhzn.amp.task.model.Account
import com.ravimhzn.amp.task.model.AccountsResponse
import com.ravimhzn.amp.task.model.Amount
import com.ravimhzn.amp.task.model.FeedItem
import com.ravimhzn.amp.task.model.TransactionResponse
import com.ravimhzn.amp.task.repo.AccountDataSource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.util.UUID

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private val mockAccountDataSource: AccountDataSource = mockk()
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MainViewModel(mockAccountDataSource)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testViewModel() {
        assertNotNull(viewModel)
    }

    @Test
    fun `initial state should have null accountsResponse and transactionResponse`() {
        assertEquals(null, viewModel.accountsResponse.value)
        assertEquals(null, viewModel.transactionResponse.value)
    }

    @Test
    fun testOnDataReturnsTransactionResponse() {
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
        viewModel.onData(transactionResponse)
        assertNotNull(viewModel.transactionResponse.value)
        assertEquals(viewModel.transactionResponse.value?.feedItems?.size, 2)
        assertEquals(viewModel.transactionResponse.value?.feedItems[0]?.amount?.minorUnits, 2955)
    }

    @Test
    fun testOnDataReturnsAccountsResponse() = runTest {
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
        val randomId = UUID.randomUUID().toString()
        val testAccount = Account(randomId, "Savings", "", "GBP", "", "")
        val testAccountsResponse = AccountsResponse(listOf(testAccount))
        val mockTransactionResponse = Response.success(
            transactionResponse
        )
        coEvery { mockAccountDataSource.getTransactions(testAccount) } returns mockTransactionResponse
        viewModel.onData(testAccountsResponse)
        assertEquals(testAccountsResponse, viewModel.accountsResponse.value)
    }
}