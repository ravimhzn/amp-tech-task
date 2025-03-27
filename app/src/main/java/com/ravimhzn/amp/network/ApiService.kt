package com.ravimhzn.amp.network

import com.ravimhzn.amp.task.model.AccountsResponse
import com.ravimhzn.amp.task.model.TransactionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    /**
     * https://api-sandbox.starlingbank.com/api/v2/accounts
     * https://api-sandbox.starlingbank.com/api/v2/feed/account/482f096a-c5bc-42d1-803f-cd62d9f83ee9/category/482f026f-83c9-4578-b8f2-5df984b64d56?changesSince=2025-03-26T00:00:00Z
     */

    @GET("/api/v2/accounts")
    suspend fun getAccountDetail(@HeaderMap headers: Map<String, String>): Response<AccountsResponse>

    @GET("/api/v2/feed/account/{accountUid}/category/{categoryUid}")
    suspend fun getTransactionList(
        @HeaderMap headers: Map<String, String>,
        @Path("accountUid") accountUid: String,
        @Path("categoryUid") categoryUid: String,
        @Query("changesSince") changesSince: String
    ): Response<TransactionResponse>
}