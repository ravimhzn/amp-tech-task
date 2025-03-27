package com.ravimhzn.amp.task.model

import java.io.Serializable

data class TransactionResponse(
    val feedItems: List<FeedItem>
): Serializable