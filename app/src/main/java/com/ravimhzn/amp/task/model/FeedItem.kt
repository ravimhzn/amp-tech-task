package com.ravimhzn.amp.task.model

import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

data class FeedItem(
    val amount: Amount,
    val batchPaymentDetails: Any,
    val categoryUid: String,
    val counterPartyName: String,
    val counterPartySubEntityIdentifier: String,
    val counterPartySubEntityName: String,
    val counterPartySubEntitySubIdentifier: String,
    val counterPartySubEntityUid: String,
    val counterPartyType: String,
    val counterPartyUid: String,
    val country: String,
    val direction: String,
    val feedItemUid: String,
    val hasAttachment: Boolean,
    val hasReceipt: Boolean,
    val reference: String,
    val settlementTime: String,
    val source: String,
    val sourceAmount: SourceAmount,
    val spendingCategory: String,
    val status: String,
    val transactingApplicationUserUid: String,
    val transactionTime: String,
    val updatedAt: String
) : Serializable {

    fun getFormattedTransactionDate(): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")

        val outputFormat = SimpleDateFormat("EEE dd MMM", Locale.ENGLISH) // Desired format

        val date = inputFormat.parse(transactionTime) ?: return "Invalid date"
        return outputFormat.format(date)
    }
}