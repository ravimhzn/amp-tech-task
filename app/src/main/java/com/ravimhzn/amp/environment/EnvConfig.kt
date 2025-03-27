package com.ravimhzn.amp.environment

import java.io.Serializable

data class EnvConfig(
    val baseUrl: String = "https://api-sandbox.starlingbank.com",
    val timeout: Int = 500
): Serializable