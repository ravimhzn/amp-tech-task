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
        "eyJhbGciOiJQUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_21Ty5KjMAz8lSnOoynzCiS3ue0P7AcIS05cAzZlm8xObe2_r4khhFRudLceLUv8zbT32SnDUQPxYD98QNdrc-7QfH1IO2TvmZ-6GFG1hVISWxDMHVRdLaEVRQuqK44qb6syFxyD-c-YnfImwqoQzeE90xgSIer6MBMopZ1M-GV7Yvdb01KbhCAoRH6AShYI3TGvIeeyLhpRt9Q0sXawX2y2jPpQdnBs2goqKpv4JY5QCs6VPHDR5m3MiGN9Ssneb1kcjUJT1SL2KQm6vEY4ksxLFOKgcM7y0o48P0pyCpebVTA48Mkx0tuTEH7GJ0ETm6CVZrfne-3DjlkAkYsmT0w63EFSQkB5GfgeueFvpwO_4RQu1mkfVwbakL5qmrBPwR32aORiTaIjkNYEZ_vUaGYWzRql3YBBWwNWgZoMLQbk5IMd1jl4QL1kD2gIA5-Ie44-VngLGzhgRHiSEc7iim-ZI_4wr1ICS5EEtiDQA56XmknbPiE4NB7l7PlOQ29lnH6rnQiw8zM8s0uWs0r3a6vUe0fdohxL1mPYAb-X0j48XuMqPJzt5mPHLaPuuFudRyYNp-KzvyixiS9qbWIqKi9MU88EceztjDyHEAecxgWOuJ5J_P_jFcVjso4e2u_Zte-efZEP9tvc-cCzAZD--kyNpBL1uNPbKp6XnP37D8biS5CyBAAA.YeG9jK6MRk14VadujYuhUd2tyZLKDlYMm0a7CtKznpWMGBUnDYx2r24N2hKWc5c_1uFfut3E0YY2Fc86fujV-6B_376WhCA8fjMSNJR9T0i2nLetM91ZlhoYDJkePyt8O35HPUpmXV7OAXKYqv3m5Is0ZnplTTLc5KmY23ZgyK-Cdq8MoY_PsxBlNxUoeqWwr1vRmlylGN__8xtL_uBpBJm7lwKICkSMRRJtz5PJWgK83S1tVigi7C07H88j9na7L6X0NllM_Bt1vdWAvgs-RU-8gcHYHjUTOnZZHuruCdomUfqb1lffpxAz8yjct_ymYkecTcTNuGoYUBh9PUU4iURhMahzagXRe5S3McDfGnZckBrIb3bv45Rmy_NaIxpH_f1SIIJMcgkRejhOqLRoULhutOur_HkAQVtnJZlFBjPh3rXALafKFTXAXvtkKpUWslAUCk6KZnWIvJ9XPHJ1RjIDsw_c5AxRqSk3H7bCpsAUQDHC4H9djqavjW-CzFgt3QM3OBsG0QL0aaBn6nlYfuS-bXC34fcg7Ym6DbqOb7n14I5UEFlDTQtc3pTix1i1FVvIhhUpxlnMXeHLm23qzaQ0EUSnJL31S5OlCr343HVu8qzT_DcxEDrqvpZ-B7lPerP-CBJFKndnk6jwRyCDSkMsvh4wPVrBVSgp70WupFk"

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