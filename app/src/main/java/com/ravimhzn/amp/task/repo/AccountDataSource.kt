package com.ravimhzn.amp.task.repo

import com.ravimhzn.amp.task.model.Account
import com.ravimhzn.amp.task.model.AccountsResponse
import com.ravimhzn.amp.task.model.TransactionResponse
import retrofit2.Response

interface AccountDataSource {

    suspend fun getAccountDetails(): Response<AccountsResponse>

    suspend fun getTransactions(account: Account): Response<TransactionResponse>
}