package com.ravimhzn.amp.task.repo

import com.ravimhzn.amp.network.ApiService
import com.ravimhzn.amp.task.model.Account
import com.ravimhzn.amp.task.model.AccountsResponse
import com.ravimhzn.amp.task.model.TransactionResponse
import retrofit2.Response
import javax.inject.Inject

class AccountRepository @Inject constructor(private val apiService: ApiService) :
    AccountDataSource {

    /**
     * Ideally Token Should be coming from token api during login
     * Please check tokenExpiry if api doesn't work. Token expires in #### minutes at 17:07 28/03/25
     */
    private val accessToken =
        "eyJhbGciOiJQUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_21Ty5KjMAz8lSnOoymeCXCb2_7AfoCw5MQ1YFO2yezU1v77GgwhpHKju_VoWeJvopxL2gRHBcSD-XAeba_0pUP99SHMkLwnbupCRFnnUgqsIWXuoOwqAXWa1yC7vJFZXRZZyiGY_4xJm53LIs_rqqneE4U-ElmZZzOBQphJ-1-mJ7a_Fa21KU0J8jQ7QSlyhK7JKsi4qPJzWtV0Pofa3nyxXjOIMtk0ORRdhlBWOUEnmOFUNKnA9NQVTRUywlifQrBzex8ORuFcVmnoU4SsrEJoSGQFpulJYj0PLMzI86NEp3BdrILGgVvLSG9Pgv8ZnwRFrL2Siu2R75XzB2YFRDaYbJmUv4OoeI_iOvA9csffVnl-w8lfjVUurAyUJnVTNGEfgzvsUYvVmkBLIIz21vSx0cysmtFS2QG9MhqMBDlpWg2IyXkzbHPwgGrNHlATem6Jew4-NriEDewxIGxFgLO44SVzxB_mTYpgLRLBHgRqwMtaM2r7J3iL2qGYPd9p6I0I0--1IwFmfoZnds2yRqp-axV7H6glyrJgNfoDcEcp7sPhLazCwcXsPg7cOuqBW-o8MnE4GZ79RYldfFFrF2NRcWWaeiYIY-9n5Nj7MOA0rnDE7UzC_x-uKByTsfTQ_shufY_si3ww3_rOe54NgHC3Z2okGanHnS6reF5y8u8_7PGWBLIEAAA.r4FbO7OJFe_xtJjYTANjm-0bzs6YOSGqLTpX59nNnU9zy4MIM36Vca1XUctfUyk9TU00ctnNVOUaSoQdTbd4_P7Z6VXB4DWqlSXseHybFlt6vUhDoMROUvC2RF_SivTTGB4uAaXE0YqE-oUl6YVfkNB0W0JqJLhVJ9df2_PMOmN46fIgzZeSQLfbIPb0T1FPDZxuBuba3fdMfWPm9xW4M-ILyFtqqiUQTuHRqvw6NAxbPbas7O_hggGQ-oSN63SSwKDGtioi5DcR3uNFp5zxie6oLCF7CWvcZcy0Zat1LgbLkapH6lj_3RBWD4MyvPg8Qb5rOGrya4GZcXwWTOdLhDtHRDKAWGWg1TRdGsFBipg1sgA04wYw1_FrRrwwUKEzyEzvLdwNxJyZfaXX7aK9f7My-U-Rp765aRaRhmt6luxEWEnI4i6R0NhoLK4UYFVDpoVAswydeSVa6s6weGcqsN3dHcjnD1JZzOtnEGJIf6pHH1UPNEie7bMBmoV6RwkIlwS8Y3fMxDLrPlBzU0feognJXUByKHVRRtA8prBskzItXhKWF5iQ9dsmSyLmGmNSzuh6P07Xp0XCqJ7z9bEjXqogWHd1-XRlwKl5av30-ti8PbwEuGIueGhWsRYLUFyJGfkKi4BqLznBXZPFEGlo0M3qkWKKqb8_6O13hjXP_7E"

    private fun constructedHeaders() = hashMapOf("Authorization" to "Bearer $accessToken")

    override suspend fun getAccountDetails(): Response<AccountsResponse> {
        return apiService.getAccountDetail(constructedHeaders())
    }

    override suspend fun getTransactions(account: Account): Response<TransactionResponse> {
        //Not sure if I should be passing createdAt for changesSince from AccountDetail api - but seems to be working.
        return apiService.getTransactionList(
            constructedHeaders(),
            account.accountUid,
            account.defaultCategory,
            account.createdAt
        )
    }
}