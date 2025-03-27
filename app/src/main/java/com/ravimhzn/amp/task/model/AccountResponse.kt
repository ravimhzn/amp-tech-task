package com.ravimhzn.amp.task.model

import java.io.Serializable

data class AccountsResponse(
    val accounts: List<Account>
) : Serializable

data class Account(
    val accountUid: String,
    val accountType: String,
    val defaultCategory: String,
    val currency: String,
    val createdAt: String,
    val name: String
) : Serializable