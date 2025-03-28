package com.ravimhzn.amp.task.model

import java.io.Serializable
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

//Todo UT
data class TransactionResponse(
    val feedItems: List<FeedItem>
) : Serializable {

    fun calculateRoundUpForDateRange(
        startDateTime: String,
        endDateTime: String
    ): Pair<Int, BigDecimal> {
        val weeklyTransactions = feedItems.filter { feedItem ->
            val transactionTime =
                LocalDateTime.parse(feedItem.transactionTime, DateTimeFormatter.ISO_DATE_TIME)
            !transactionTime.isBefore(getIsoFormattedDate(startDateTime)) && !transactionTime.isAfter(getIsoFormattedDate(endDateTime))
        }
        var totalRoundUp = 0
        weeklyTransactions.forEach { transaction ->
            val amount = transaction.amount.minorUnits // Assuming this is in cents/pence
            if (amount > 0) {
                val remainder = amount % 100
                if (remainder > 0) {
                    totalRoundUp += (100 - remainder)
                }
            }
        }

        return Pair(weeklyTransactions.size, convertMinorUnitsToPounds(totalRoundUp))
    }

    private fun getIsoFormattedDate(date: String) =
        LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME)

    private fun convertMinorUnitsToPounds(minorUnits: Int): BigDecimal {
        return BigDecimal(minorUnits).divide(BigDecimal(100))
    }

    fun testFunction() {
        val weekStart ="2025-03-27T06:08:18.810Z"
        val weekEnd ="2025-03-28T00:05:07.118Z"

        val roundUp = calculateRoundUpForDateRange(weekStart, weekEnd)
        if (roundUp.first > 0) {
            println("debug --> Total Amount that can be Transferred from ${roundUp.first} transaction is $${roundUp.second} ")

        } else {
            println("debug --> No transaction in selected date range.")
        }

    }
}