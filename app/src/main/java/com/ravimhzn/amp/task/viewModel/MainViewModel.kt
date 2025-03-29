package com.ravimhzn.amp.task.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.ravimhzn.amp.framework.BaseViewModel
import com.ravimhzn.amp.framework.NetworkError
import com.ravimhzn.amp.task.model.Account
import com.ravimhzn.amp.task.model.AccountsResponse
import com.ravimhzn.amp.task.model.TransactionResponse
import com.ravimhzn.amp.task.repo.AccountDataSource
import com.ravimhzn.amp.util.ACCESS_TOKEN_EXPIRED
import com.ravimhzn.amp.util.NETWORK_ERROR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.Serializable
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val accountDataSource: AccountDataSource) :
    BaseViewModel() {

    var accountsResponse = mutableStateOf<AccountsResponse?>(null)
    var transactionResponse = mutableStateOf<TransactionResponse?>(null)
    var errorMessage = mutableStateOf<String>("")

    fun getAccountDetail() {
        viewModelScope.launch {
            enqueue(accountDataSource.getAccountDetails())
        }
    }

    private fun getTransactionList(account: Account) {
        viewModelScope.launch {
            enqueue(accountDataSource.getTransactions(account))
        }
    }

    override fun onData(data: Serializable) {
        super.onData(data)
        when (data) {
            is AccountsResponse -> {
                accountsResponse.value = data
                getTransactionList(data.accounts[0])
            }

            is TransactionResponse -> {
                transactionResponse.value = data
            }
        }
    }

    override fun onError(error: NetworkError) {
        super.onError(error)
        if (error.statusCode == "403" && error.error == "invalid_token") {
            errorMessage.value = ACCESS_TOKEN_EXPIRED
        } else {
            errorMessage.value = NETWORK_ERROR
        }
    }
}